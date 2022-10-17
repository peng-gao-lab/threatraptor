package st2g;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ST2G {
    private static ST2G instance = new ST2G(); // singleton
    private static boolean use_WSL = false; // for windows
    public static String temp_output_loc;
    private ST2G() {
        temp_output_loc = "output/tmp/st2g_temp";
    }
    public static ST2G getInstance() {
        return instance;
    }
    public String process(String input_loc) throws IOException, InterruptedException {
        // "st2g --input %s --output %s"
        List<String> cmds = new ArrayList<>();
        String st2gCmdStr = "";
        if (use_WSL) {
            cmds.add("bash");
            // cmds.add("sh");
            cmds.add("-lic");
            st2gCmdStr = String.format("\"st2g --input %s --output %s\"", input_loc, temp_output_loc);
        }
        else {
//        	cmds.add("bash");
            cmds.add("sh");
            cmds.add("-lic");
            st2gCmdStr = String.format("st2g --input %s --output %s", input_loc, temp_output_loc);
        }
        
        cmds.add(st2gCmdStr);

        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {}
        };
        
        Utils.executeCommands(cmds);
        return temp_output_loc;
    }
}