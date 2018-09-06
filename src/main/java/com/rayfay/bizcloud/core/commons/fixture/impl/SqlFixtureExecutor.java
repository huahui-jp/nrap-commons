package com.rayfay.bizcloud.core.commons.fixture.impl;
import com.rayfay.bizcloud.core.commons.autoconfig.AppStartedAutoConfiguration;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeTypes;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.core.commons.fixture.Fixture;
import com.rayfay.bizcloud.core.commons.fixture.FixtureEvolutionChecker;
import com.rayfay.bizcloud.core.commons.fixture.FixtureExecutor;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by STZHANG on 2017/6/8.
 */
public class SqlFixtureExecutor implements FixtureExecutor{

    private static AtomicBoolean hasExecuted = new AtomicBoolean(false);
    private static Logger logger = LoggerFactory.getLogger(AppStartedAutoConfiguration.class);
    private static FixtureEvolutionChecker evolutionChecker = null;
    private JdbcTemplate jdbcTemplate;
    private String sqlFileDicrectory = "META-INF/fixture/script/";
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    @Override
    public boolean execute(Set<Fixture> fixtures) {
        if(hasExecuted.get()){
            return true;
        }
        if(fixtures == null || fixtures.isEmpty()){
            throw NRAPException.createNewInstance(ErrorCodeTypes.E_1004, "fixtures");
        }

        if(jdbcTemplate == null){
            throw NRAPException.createNewInstance(ErrorCodeTypes.E_1004, "jdbcTemplate");
        }

        ClassLoader thisClassLoader = Thread.currentThread().getContextClassLoader();
        //开始启用多线程执行
        for (Fixture fixture : fixtures) {
            if(fixture instanceof SqlFixtureScript) {
                SqlFixtureScript sqlFixture = (SqlFixtureScript) fixture;

                //如果已经执行成功过
                if(beforeSqlExecute(sqlFixture)){
                    continue;
                }

                // prepared to execute
                fixedThreadPool.execute(new Runnable() {
                    @Override
                    public void run(){
                        String sqlFile = sqlFixture.getFixtureSql();
                        logger.info("prepared to execute sql file: {}", sqlFile);
                        try {
                            Enumeration<URL> urls = thisClassLoader.getResources(sqlFileDicrectory + sqlFile);
                            while(urls.hasMoreElements()) {
                                URL url = (URL)urls.nextElement();
                                InputStream sqlFileIn = new UrlResource(url).getInputStream();
                                executeSqlFile(sqlFileIn, sqlFixture);
                            }
                        } catch (IOException e) {
                            logger.error("sql文件读取异常：IOException", e);
                        }

                    }
                });
            }
        }
        hasExecuted.set(true);
        return true;
    }


    /**
     * 执行一个sql内容; 考虑成功，失败，回滚等情况。
     * @param sqlFileIn
     * @param sqlFixture
     */
    private void executeSqlFile(InputStream sqlFileIn, SqlFixtureScript sqlFixture){
        String sqlErrorMessage = "";
        Connection conn = null;
        Statement statement = null;
        boolean isAutoCommit = false;
        try {
            conn = jdbcTemplate.getDataSource().getConnection();
            isAutoCommit = conn.getAutoCommit();
            if(isAutoCommit) {
                conn.setAutoCommit(false);
            }
            StringBuilder sqlSb = new StringBuilder();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead, "utf-8"));
            }
            String[] sqlArr = sqlSpliter(sqlSb.toString());
            //批量更新
            statement = conn.createStatement();
            for (String sql : sqlArr) {
                statement.addBatch(sql);
            }
            statement.executeBatch();
            conn.commit();
        }catch(UnsupportedEncodingException e){
            logger.error("sql文件编码异常：UnsupportedEncodingException", e);
            sqlErrorMessage = e.getMessage();
        }catch(IOException e){
            logger.error("sql文件读取异常：IOException", e);
            sqlErrorMessage = e.getMessage();
        }catch (DataAccessException e){
            logger.error("sql文件执行异常：DataAccessException", e);
            sqlErrorMessage = e.getMessage();
        } catch (SQLException e){
            logger.error("sql文件执行异常：SQLException", e);
            sqlErrorMessage = e.getMessage();
        } finally {
            try {
                boolean failure = StringUtils.isNotBlank(sqlErrorMessage);
                if(conn != null && failure) {
                    conn.rollback();
                }
                if(conn != null && isAutoCommit) {
                    conn.setAutoCommit(true);
                }
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error("",e);
            }
            // 执行完毕后，记录执行状态
            afterSqlExecute(sqlFixture, StringUtils.isBlank(sqlErrorMessage), sqlErrorMessage);
        }
    }

    /**
     * 设置jdbcTemplate
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 更新是否执行成功
    private void afterSqlExecute(SqlFixtureScript fixtureScript, boolean success, String message ){
         logger.info("prepared to do afterSqlExecute: {}, {}", success, message);
         if(success){
             evolutionChecker.setFixtureExecutedSuccessful(fixtureScript);
         }else {
             evolutionChecker.setFixtureExecutedFailure(fixtureScript, message);
         }
    }


    /**
     * 判断是否可以执行，如果已经执行，则不需要再执行了
     * @param fixtureScript
     * @return 已经执行过，则true， 尚未执行成功过，则false
     */
    private boolean beforeSqlExecute(SqlFixtureScript fixtureScript){
        return evolutionChecker.ifFixtureExecutedSuccessful(fixtureScript);
    }

    /**
     * 设置EvolutionChecker
     * @param fixtureEvolutionChecker
     */
    public void setEvolutionChecker(FixtureEvolutionChecker fixtureEvolutionChecker) {
        evolutionChecker = fixtureEvolutionChecker;
    }
    /**
     * 从文件中读取sql语句
     * @param sqlFileContent
     * @return
     */
    private String[] sqlSpliter(String sqlFileContent){
        String[] sqlArr = sqlFileContent.split("(;\\s*\\r\\n)|(;\\s*\\n)");
        for (int i = 0; i < sqlArr.length; i++) {
            // 忽略注释
            String sql = sqlArr[i].replaceAll("--.*", "").trim();
            if(sql.endsWith(";")){
                sql = sql.substring(0, sql.length() - 1);
            }
            sqlArr[i] = sql;
        }
        return sqlArr;
    }
}
