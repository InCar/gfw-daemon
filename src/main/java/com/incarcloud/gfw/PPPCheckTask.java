package com.incarcloud.gfw;

import org.slf4j.*;

import java.io.*;
import java.util.regex.*;

public class PPPCheckTask extends CmdTask {
    public void exec(String cmd) throws IOException {
        super.exec(cmd == null ? "ifconfig" : cmd);
    }

    public boolean isOkay() throws IOException, InterruptedException {
        super.isOkay();
        for(String line : _output){
            if(s_rgx.matcher(line).find()){
                return true;
            }
        }

        return false;
    }

    private static Pattern s_rgx = Pattern.compile("^ppp0");
}
