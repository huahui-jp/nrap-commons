package com.rayfay.bizcloud.core.commons.fixture;

import com.google.common.collect.Maps;
import com.rayfay.bizcloud.core.commons.autoconfig.AppStartedAutoConfiguration;
import com.rayfay.bizcloud.core.commons.exception.ErrorCodeTypes;
import com.rayfay.bizcloud.core.commons.exception.NRAPException;
import com.rayfay.bizcloud.core.commons.fixture.impl.SqlFixtureScript;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by STZHANG on 2017/6/8.
 */
public class FixtureApplicationStarter implements CommandLineRunner{
    private static Logger logger = LoggerFactory.getLogger(AppStartedAutoConfiguration.class);
    private static FixtureEvolutionChecker evolutionChecker = null;
    private static Map<Class, FixtureExecutor> executorMap = Maps.newHashMap();
    public static void registerFixtureExecutor(Class clazz, FixtureExecutor fixtureExecutor){
         executorMap.put(clazz, fixtureExecutor);
    }

    public FixtureApplicationStarter(FixtureEvolutionChecker evolutionChecker){
        super();
        FixtureApplicationStarter.evolutionChecker  = evolutionChecker;
    }

    public FixtureApplicationStarter(){
        super();
    }

    private static void execute(Class<?> tClass){
        if(evolutionChecker == null){
            throw NRAPException.createNewInstance(ErrorCodeTypes.E_1004, "evolutionChecker");
        }
        // 如果没发现Evolution table，则创建之.
        evolutionChecker.createIfNotFoundEvolutionTable();
        // 下面开始执行各类Fixture.
        Set<Fixture> fixtureMap = Sets.newHashSet();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<String> factoryNames  = FixtureFactoriesLoader.loadFactoryNames(tClass, classLoader);

        if(factoryNames != null && !factoryNames.isEmpty()){

            if (tClass.isAssignableFrom(SqlFixture.class)) {
                logger.info("found SqlFixture, {}", factoryNames);
                for (String factoryName : factoryNames) {
                    SqlFixtureScript sfs = new SqlFixtureScript(factoryName);
                    if (sfs.accept(sfs.getFixtureSql())) {
                        fixtureMap.add(sfs);
                    }
                }
            }
            // if else ...
            if (executorMap.containsKey(tClass)) {
                logger.info("start to execute SqlFixture, {}", factoryNames);
                FixtureExecutor executor = executorMap.get(tClass);
                executor.execute(fixtureMap);
            }

        }



    }


    public static void setEvolutionChecker(FixtureEvolutionChecker fixtureEvolutionChecker) {
        evolutionChecker = fixtureEvolutionChecker;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("FixtureApplicationStarter start.");
        if(!executorMap.isEmpty()){
            Iterator<Class> it = executorMap.keySet().iterator();
            while (it.hasNext()){
                 Class<?> clazz = it.next();
                 execute(clazz);
            }
        }
    }
}
