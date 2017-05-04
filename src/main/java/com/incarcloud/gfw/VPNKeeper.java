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
                s_logger.debug("PING OK");
            } else {
                s_logger.info("PING FAILED");
                VPNOff vpnOffTask = new VPNOff();
                vpnOffTask.exec("ppp0");
                if(vpnOffTask.isOkay()){
                    s_logger.info("VPN ON...");
                    VPNOn vpnOnTask = new VPNOn();
                    vpnOnTask.exec("InCarVPN");
                    if(vpnOnTask.isOkay()){
                        s_logger.info("Add routes...");
                        CmdTask routesTask = new CmdTask();
                        routesTask.exec("/root/p0-route.sh");
                        if(routesTask.isOkay()){
                            s_logger.info("OK!");
                        }
                    }else{
                        s_logger.warn("VPNOn failed!");
                        // wait more time before retry
                        Thread.sleep(1000*checkInterval);
                    }
                }
            }

            // wait for next checking
            Thread.sleep(1000 * checkInterval);
        }
    }
}
