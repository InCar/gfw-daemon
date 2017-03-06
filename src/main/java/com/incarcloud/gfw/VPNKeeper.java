package com.incarcloud.gfw;

import org.slf4j.*;

import java.io.IOException;

class VPNKeeper {
    private int checkInterval = 5; //seconds
    private static Logger s_logger = LoggerFactory.getLogger(VPNKeeper.class);

    public void keep(String vpn, String ping) throws IOException, InterruptedException{
        while (true) {
            PingTask pingTask = new PingTask();
            pingTask.exec(ping);
            if (pingTask.isOkay()) {
                s_logger.info("PING OK");
            } else {
                s_logger.info("PING FAILED");
            }

            // wait for next checking
            Thread.sleep(1000 * checkInterval);
        }
    }
}
