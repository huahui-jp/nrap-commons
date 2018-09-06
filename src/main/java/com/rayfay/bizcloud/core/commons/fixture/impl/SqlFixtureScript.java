package com.rayfay.bizcloud.core.commons.fixture.impl;

import com.rayfay.bizcloud.core.commons.fixture.SqlFixture;

/**
 * Created by STZHANG on 2017/6/8.
 */
public class SqlFixtureScript implements SqlFixture{
   private String sqlFile = null;
   private String fixturePrefix = "SqlFixture:";
   public SqlFixtureScript(String sqlFile){
      this.sqlFile  = sqlFile;
   }
   @Override
   public String getFixtureSql() {
      return sqlFile;
   }

   @Override
   public String toString() {
      return fixturePrefix + sqlFile;
   }

   @Override
   public String getFixtureType() {
      return fixturePrefix;
   }

   @Override
   public String getFixtureKey() {
      return fixturePrefix + sqlFile;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result
              + ((sqlFile == null)? 0 : sqlFile.hashCode());
      result = prime * result + fixturePrefix.hashCode();
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj ==this){
         return true;
      }
      if (obj == null || obj.getClass()!= this.getClass()){
         return false;
      }
      SqlFixtureScript guest = (SqlFixtureScript) obj;
      if(this.getFixtureSql() == null && guest.getFixtureSql() == null){
         return true;
      }
      if(this.getFixtureSql() == null && guest.getFixtureSql() != null){
         return false;
      }
      return this.getFixtureSql().equals(guest.getFixtureSql());
   }

}
