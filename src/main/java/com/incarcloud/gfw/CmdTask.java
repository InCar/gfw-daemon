package com.incarcloud.gfw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class CmdTask {
    // 执行并等待
    public void exec(String cmd) throws IOException {
        ProcessBuilder builder = new ProcessBuilder(cmd.split("\\s+"));
        builder.redirectErrorStream(true);
        _proc = builder.start();
    }

    // 是否成功
    public boolean isOkay() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(_proc.getInputStream()));
        String line;
        do{
            line = reader.readLine();
            if(line != null) _output.add(line);
        }while(line != null);
        return true;
    }

    private Process _proc;
    protected List<String> _output = new ArrayList<>();
    protected static boolean s_bWin = System.getProperty("os.name").toLowerCase().contains("win");
}
