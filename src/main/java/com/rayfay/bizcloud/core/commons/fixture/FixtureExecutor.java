package com.rayfay.bizcloud.core.commons.fixture;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;
import java.util.Set;

/**
 * Created by STZHANG on 2017/6/8.
 */
public interface FixtureExecutor {

    boolean execute(Set<Fixture> fixtures);
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
}
