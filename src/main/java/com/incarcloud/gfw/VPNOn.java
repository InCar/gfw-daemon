package com.incarcloud.gfw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class VPNOn extends CmdTask {
    public void exec(String cmd) throws IOException {
        super.exec(String.format("pon %s", cmd));
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
            if(!bOk) Thread.sleep(500);
            nRetry++;
            if(nRetry > s_nRetryMax){
                return false;
            }
        }while(!bOk);

        return true;
    }

    private static int s_nRetryMax = 5;
}