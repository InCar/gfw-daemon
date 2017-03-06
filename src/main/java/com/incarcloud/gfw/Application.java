package com.incarcloud.gfw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;

import java.lang.reflect.*;

@SpringBootApplication
public class Application implements CommandLineRunner{
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    public void run(String... args) throws Exception{
        Logger logger = LoggerFactory.getLogger(Application.class);

        Class<?> VPNKeeper = Class.forName("com.incarcloud.gfw.VPNKeeper");
        Object keeper = VPNKeeper.newInstance();
        Method methodKeep = VPNKeeper.getDeclaredMethod("keep", String.class, String.class);

        while(true) {
            try {
                logger.info("start ...");
                methodKeep.invoke(keeper, config.vpn, config.ping);
            } catch (Exception ex) {
                StringBuilder sbError = new StringBuilder();
                Throwable exx = ex;
                while(exx != null){
                    if(exx != null) sbError.append("\n    ");
                    sbError.append(exx.toString());
                    for(StackTraceElement e: exx.getStackTrace()){
                        sbError.append("\n        ");
                        sbError.append(e.toString().trim());
                    }
                    exx = exx.getCause();
                }
                logger.error(sbError.toString());
                logger.info("Wait for 10 seconds to restart ...");
                Thread.sleep(1000*10);
            }
        }
    }

    @Autowired
    private GfwConfig config;
}
