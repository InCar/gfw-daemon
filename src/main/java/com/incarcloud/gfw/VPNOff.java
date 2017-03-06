package com.incarcloud.gfw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class VPNOff extends CmdTask {
    public void exec(String cmd) throws IOException{
        super.exec("poff");
    }

    public boolean isOkay() throws IOException, InterruptedException{
        boolean bOk = super.isOkay();
        if(!bOk) return bOk;

        // check ifconfig
        int nRetry = 0;
        do {
            PPPCheckTask taskCheck = new PPPCheckTask();
            taskCheck.exec("ifconfig");
            bOk = taskCheck.isOkay();

            // Still there, wait and check
            if(bOk) Thread.sleep(500);
            nRetry++;
            if(nRetry > s_nRetryMax){
                if(s_logger == null) s_logger = LoggerFactory.getLogger(VPNOff.class);
                s_logger.error("VPNOff failed!");
                throw new InterruptedException("VPN Off failed!");
            }
        }while(bOk);

        return true;
    }

    private static int s_nRetryMax = 5;
    private Logger s_logger = null;
}
