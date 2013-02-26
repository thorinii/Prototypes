/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backupchecker;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author lachlan
 */
public class Recorder {

    private final File out;
    private DataOutputStream dos;

    public Recorder(File out) {
        this.out = out;
    }

    public void record(File root) throws IOException {
        dos = new DataOutputStream(
                new GZIPOutputStream(
                new FileOutputStream(out)));

        try {
            recordProc(root);
        } finally {
            dos.close();
        }
    }

    private void recordProc(File f) throws IOException {
        recordProc(f, 0);
    }

    private void recordProc(File f, int depth) throws
            IOException {
        if (f.isFile()) {
            writeFile(f, depth);
        } else if (f.isDirectory()) {
            File[] subs = f.listFiles();
            writeDir(f, depth, subs.length);

            for (File sub : subs) {
                recordProc(sub, depth + 1);
            }
        }
    }

    private void writeFile(File f, int depth) throws IOException {
        dos.writeByte(FileConstants.FILE);
        dos.writeInt(depth);
        dos.writeUTF(f.getName());
        dos.writeLong(f.length());
    }

    private void writeDir(File f, int depth, int count) throws
            IOException {
        dos.writeByte(FileConstants.DIRECTORY);
        dos.writeInt(depth);
        dos.writeUTF(f.getName());
        dos.writeInt(count);
    }
}
