/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backupchecker;

import java.io.*;
import java.util.List;

/**
 *
 * @author lachlan
 */
public class BackupChecker {

    private static final String USAGE = "Usage:\n"
            + "  java -jar BackupChecker.jar -r <root directory> <output>\n"
            + "  java -jar BackupChecker.jar -c <root directory> <input>";
    private static final int FILE = 1;
    private static final int DIR = 2;
    private static DataOutputStream dos;

    /**
     * Usage:
     * java -jar BackupChecker.jar -r <root directory> <output>
     * java -jar BackupChecker.jar -c <root directory> <input>
     */
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException(
                    "Expecting 3 arguments.\n" + USAGE);
        }

        if (args[0].equals("-r")) {
            Recorder recorder = new Recorder(new File(args[2]));
            recorder.record(new File(args[1]));
        } else if (args[0].equals("-c")) {
            Comparer comparer = new Comparer(new File(args[2]));
            comparer.compare(new File(args[1]));

            List<String> errors = comparer.getErrors();

            if (errors.isEmpty()) {
                System.out.println("All is well.");
            } else {
                for (String error : errors) {
                    System.out.println(error);
                }
            }
        } else {
            throw new IllegalArgumentException(
                    "Expecting -r or -c (record or check).\n" + USAGE);
        }
    }
}
