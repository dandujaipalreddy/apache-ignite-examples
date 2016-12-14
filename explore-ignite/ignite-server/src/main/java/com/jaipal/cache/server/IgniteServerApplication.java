package com.jaipal.cache.server;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;


public class IgniteServerApplication {

    @Bean(name = "ServerInstance", destroyMethod = "close")
    private Ignite startIgnite() {
        Ignite ignite = null;
        ignite = Ignition.start("example-ignite.xml");
        return ignite;
    }
    public static void main(String[] args) {
        SpringApplication.run(IgniteServerApplication.class);

    }
}
