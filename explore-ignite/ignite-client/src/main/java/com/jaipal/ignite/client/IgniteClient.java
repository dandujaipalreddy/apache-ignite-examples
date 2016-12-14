package com.jaipal.ignite.client;

import com.jaipal.model.Player;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class IgniteClient {

    private static Ignite igniteClient = null;

    static {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("example-default.xml");
        IgniteConfiguration igniteConfiguration = (IgniteConfiguration) ctx.getBean("ignite.cfg");
        igniteClient = Ignition.start(igniteConfiguration);
    }

    public static IgniteCache getIgniteCache(String cache) {
        IgniteCache igniteCache = igniteClient.getOrCreateCache(cache);
        return igniteCache;
    }

    public static void main(String[] args) {

//        Ignite ignite=getIgniteClientInstance();
//        System.out.println(ignite.cacheNames());
//        IgniteCache<String,Player> igniteCache=ignite.getOrCreateCache("playerCache");
        IgniteCache<String, Player> igniteCache = getIgniteCache("playerCache");
        System.out.println(igniteCache.toString());

        System.out.println("**************Before Put  *****************");
        System.out.println(igniteCache.size());
        //System.out.println(ignite.toString());
        for (int i = 0; i < 10; i++) {
            String sessionId = RandomStringUtils.randomAlphabetic(10);
            String userName = "player" + i;
            System.out.print("Trying to put " + userName);
            igniteCache.put(userName, new Player(userName, sessionId, "India"));
        }
        System.out.println("**************After Put  *****************");
        System.out.println(igniteCache.size());
    }
}
