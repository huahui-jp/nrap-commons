package com.rayfay.bizcloud.core.commons.fixture;

/**
 * Created by STZHANG on 2017/6/9.
 */
public class DbContext {
    public static DbTypes currentDataBase = DbTypes.Mysql;
    /**
     * 数据库类型
     */
    public static enum DbTypes{
        Mysql("mysql", "mariadb"), Oracle("oracle","oracle11g"), hsqldb("hsqldb");
        private String[] keywords;
        DbTypes(String... keys){
            this.keywords = keys;
        }
    }

    // determinate
    public static void determinateDbTypesByDriverClassName(String driverClassName){
        for (DbTypes dbTypes : DbTypes.values()) {
            String[] keywords = dbTypes.keywords;
            if(keywords != null){
                for (String keyword : keywords) {
                    if(driverClassName.indexOf(keyword.toLowerCase()) > -1){
                        currentDataBase = dbTypes;
                        return;
                    }
                }
            }
        }
    }
}
