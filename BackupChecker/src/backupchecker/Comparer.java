/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backupchecker;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author lachlan
 */
public class Comparer {

    private final File in;
    private final List<String> errors;
    private DataInputStream dis;

    public Comparer(File in) {
        this.in = in;

        errors = new ArrayList<String>();
    }

    public void compare(File root) throws IOException {
        DataInputStream dis = new DataInputStream(
                new GZIPInputStream(
                new FileInputStream(in)));
        try {
            compareProc(root, dis);
        } finally {
            dis.close();
        }
    }

    private void compareProc(File f, DataInputStream dis) throws
            IOException {
        compareProc(f, dis, 0);
    }

    private void compareProc(File f, DataInputStream dis, int depth) throws
            IOException {
        if (f.isFile()) {
            if (dis.readByte() != FileConstants.FILE) {
                errors.add(f + " is not a file");
            } else if (dis.readInt() != depth) {
                errors.add(f + " is not at correct depth");
            } else if (!dis.readUTF().equals(f.getName())) {
                errors.add(f + " exists");
            } else if (dis.readLong() != f.length()) {
                errors.add(f + " is not correct length");
            }
        } else if (f.isDirectory()) {
            File[] subs = f.listFiles();

            boolean v = true;
            int nFiles = -1;
            if (dis.readByte() != FileConstants.DIRECTORY) {
                errors.add(f + " is not a directory");
                v = false;
            } else if ((nFiles = dis.readInt()) != depth) {
                errors.add(f + " is not at correct depth");
                v = false;
            } else if (!f.getName().equals(dis.readUTF())) {
                errors.add(f + " exists");
                v = false;
            } else if (dis.readInt() != subs.length) {
                errors.add(f + " does not have correct subfiles");
                v = false;
            }

            if (v) {
                for (File sub : subs) {
                    compareProc(sub, dis, depth + 1);
                }
            } else if (nFiles > -1) {
                int type = dis.readByte();
                dis.readInt();
                dis.readUTF();

                if (type == FileConstants.FILE) {
                    dis.readLong();
                } else {
                    dis.readInt();
                }
            }
        }
    }

    public List<String> getErrors() {
        return errors;
    }
}
