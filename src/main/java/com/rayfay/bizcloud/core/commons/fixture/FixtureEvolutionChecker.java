package com.rayfay.bizcloud.core.commons.fixture;

import com.rayfay.bizcloud.core.commons.autoconfig.FixtureAutoConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by STZHANG on 2017/6/9.
 */
public class FixtureEvolutionChecker {
   public static final String EVOLUTION_TABLE_NAME = "T_FIXTURE_EVOLUTION";
   private static Logger logger = LoggerFactory.getLogger(FixtureAutoConfiguration.class);

    //jdbcTemplate 执行Evolution
   private JdbcTemplate jdbcTemplate;

   public void createIfNotFoundEvolutionTable(){
       boolean tableExists = true;
       try {
           // String sql = "select count(*) from " + EVOLUTION_TABLE_NAME;
           // fixed: 读写分离下引导jdbctemplate使用可更新的connection
           String sql = "update " + EVOLUTION_TABLE_NAME + " set status = 0 where 1=2";
           this.jdbcTemplate.execute(sql);
           tableExists = true;
       } catch (Exception ex) {
           tableExists = false;
           logger.warn("exception: EVOLUTION table not found: {}", EVOLUTION_TABLE_NAME);
       }
       logger.info("prepared to create EVOLUTION table. ");
       if (!tableExists) {
           String createTableSql = determinateEvolutionCreateTableSql();
           try {
               this.jdbcTemplate.execute(createTableSql);
           } catch (DataAccessException e) {
               logger.warn("Create evolution table error", e);
           }
       }
   }

    /**
     * Fixture是否已经执行成功
     * @param fixture
     * @return
     */
    public boolean ifFixtureExecutedSuccessful(Fixture fixture){
        String sql = "select count(*) as CC from "+EVOLUTION_TABLE_NAME + " where FIX_KEY= ? and status= 9 ";
        logger.info("检查fixture 是否已经执行成功, {}", sql);
        int rowCount = jdbcTemplate.queryForObject(sql, new String[]{fixture.getFixtureKey()}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("CC");
            }
        });
        logger.info("已经执行成功");
       return rowCount > 0;
    }


    private void setFixtureExecuted(Fixture fixture, boolean success, String message){
        String fixtureKey = fixture.getFixtureKey();
        String sql = "select count(*) as CC from "+EVOLUTION_TABLE_NAME + " where FIX_KEY= ? ";
        logger.info("检查fixture 是否已经执行成功, {}", sql);
        int rowCount = jdbcTemplate.queryForObject(sql, new String[]{fixtureKey}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt("CC");
            }
        });

        if(rowCount > 0){
            sql = "update "+ EVOLUTION_TABLE_NAME + " set status =? , APPLIED_AT=?, LAST_PROBLEM=? where  FIX_KEY= ?";
            jdbcTemplate.update(sql, (success? 9: 1), new java.util.Date(), (success?"": message), fixtureKey);
        }else {
            sql = "insert into "+ EVOLUTION_TABLE_NAME + "(FIX_TYPE, FIX_KEY, status, APPLIED_AT, LAST_PROBLEM) values (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, fixture.getFixtureType(), fixtureKey, (success? 9: 1), new java.util.Date(), (success?"": "failure"));
        }
    }

    public void setFixtureExecutedSuccessful(Fixture fixture){
       setFixtureExecuted(fixture, true, "");

    }

    public void setFixtureExecutedFailure(Fixture fixture, String errorMessage){
        setFixtureExecuted(fixture, false, errorMessage);
    }

    // 根据数据库类型等，决定创建表的语句
    private String determinateEvolutionCreateTableSql(){

        StringBuilder sb = new StringBuilder();
        if(isOracleDataBaseInUse()){
            sb.append("CREATE TABLE ").append(EVOLUTION_TABLE_NAME.toUpperCase()).append(" (");
            sb.append(" FIX_TYPE VARCHAR2(255) NOT NULL, ");
            sb.append(" FIX_KEY  VARCHAR2(255) NOT NULL, ");
            sb.append(" APPLIED_AT TIMESTAMP NOT NULL, ");
            sb.append(" STATUS NUMBER(10) default 0, "); // 9 success, 1 failure, 0 defaultValue.
            sb.append(" LAST_PROBLEM VARCHAR2(4000) NULL, ");
            sb.append(" PRIMARY KEY (FIX_KEY) ");
            sb.append(" )");
        }else {
            sb.append("CREATE TABLE ").append(EVOLUTION_TABLE_NAME.toUpperCase()).append(" (");
            sb.append(" FIX_TYPE VARCHAR(255) NOT NULL, ");
            sb.append(" FIX_KEY  VARCHAR(255) NOT NULL, ");
            sb.append(" APPLIED_AT TIMESTAMP NOT NULL, ");
            sb.append(" STATUS int default 0, "); // 9 success, 1 failure, 0 defaultValue.
            sb.append(" LAST_PROBLEM VARCHAR(4000) NULL, ");
            sb.append(" PRIMARY KEY (FIX_KEY) ");
            sb.append(" )");
        }

        return sb.toString();
    }


    private boolean isOracleDataBaseInUse(){
        return guessCurrentDatabase() == DbContext.DbTypes.Oracle;
    }
    // 根据jpa.dialect 判断当前数据库类型： oracle, mysql
    protected DbContext.DbTypes guessCurrentDatabase() {
        return DbContext.currentDataBase;
    }
    /**
     * 设置jdbcTemplate
     * @param jdbcTemplate
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



}
