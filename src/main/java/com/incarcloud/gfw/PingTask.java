package com.incarcloud.gfw;

import java.io.IOException;
import java.util.regex.*;

class PingTask extends CmdTask {
    static int times = 4;

    public void exec(String cmd) throws IOException{
        if(s_bWin)
            super.exec(String.format("ping -n %d %s", times, cmd));
        else
            super.exec(String.format("ping -c %d %s", times, cmd));
    }

    public boolean isOkay() throws IOException, InterruptedException{
        boolean bOk = super.isOkay();
        if(!bOk) return bOk;

        Pattern rgx;
        if(s_bWin) rgx = s_rgxWin;
        else rgx = s_rgxLinux;

        for(String line : _output){
            Matcher m = rgx.matcher(line);
            if(m.find()){
                int recv = Integer.parseInt(m.group(1));
                return (recv > 0);
            }
        }

        return false;
    }

    private static Pattern s_rgxWin = Pattern.compile("已接收\\s*=\\s*(\\d+)");
    private static Pattern s_rgxLinux = Pattern.compile("(\\d+)\\s*received");
}
