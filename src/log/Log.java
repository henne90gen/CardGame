package log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by henne on 20.05.16.
 */
public class Log {

    public static boolean verbose = true;

    public static void setVerbosity(boolean verbosity) {
        verbose = verbosity;
    }

    public static boolean getVerbosity() {
        return verbose;
    }

    /**
     * Prints the prefix and the message in the form '[prefix]: msg'
     * @param msg Log message
     */
    public static void w(String prefix, String msg) {
        String tmp = "[" + prefix + "]: " + msg;
        if (verbose) System.out.println(tmp);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true));
            writer.write(tmp + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
