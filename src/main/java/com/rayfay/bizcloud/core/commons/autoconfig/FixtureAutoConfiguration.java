package com.rayfay.bizcloud.core.commons.autoconfig;

import com.rayfay.bizcloud.core.commons.exception.ErrorCodeTypes;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.core.commons.fixture.DbContext;
import com.rayfay.bizcloud.core.commons.fixture.FixtureApplicationStarter;
import com.rayfay.bizcloud.core.commons.fixture.FixtureEvolutionChecker;
import com.rayfay.bizcloud.core.commons.fixture.SqlFixture;
import com.rayfay.bizcloud.core.commons.fixture.impl.SqlFixtureExecutor;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.cloud.Cloud;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by STZHANG on 2017/6/8.
 */
@Configuration
@ConditionalOnBean(value = {DataSource.class, JdbcTemplate.class})
@AutoConfigureAfter(value = DataSourceAutoConfiguration.class)
public class FixtureAutoConfiguration {
    private static Logger logger = LoggerFactory.getLogger(FixtureAutoConfiguration.class);


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment env;

    @Bean
    public FixtureApplicationStarter createFixtureApplicationStarter(){
        //determinate db types
        DataSource basicDataSource = jdbcTemplate.getDataSource();
        BeanWrapper target = new BeanWrapperImpl(basicDataSource);
        String driverClassName = (String) target.getPropertyValue("driverClassName");
        logger.info("found dataSourceProperties. driverClassName: {}", driverClassName);
        if (driverClassName != null) {
            DbContext.determinateDbTypesByDriverClassName(driverClassName);
            logger.info("found determinateDbTypesByDriverClassName. dbTypes: {}", DbContext.currentDataBase);
        } else   {
            throw NRAPException.createNewInstance(ErrorCodeTypes.E_1004, "driverClassName");
        }
        // execute on application started.
        logger.info("FixtureAutoConfiguration createFixtureApplicationStarter");
        FixtureEvolutionChecker evolutionChecker = new FixtureEvolutionChecker();
        evolutionChecker.setJdbcTemplate(jdbcTemplate);
        SqlFixtureExecutor sqlFixtureExecutor = new SqlFixtureExecutor();
        sqlFixtureExecutor.setJdbcTemplate(jdbcTemplate);
        sqlFixtureExecutor.setEvolutionChecker(evolutionChecker);
        FixtureApplicationStarter starter = new FixtureApplicationStarter(evolutionChecker);
        starter.registerFixtureExecutor(SqlFixture.class, sqlFixtureExecutor);

        return starter;
    }


}
