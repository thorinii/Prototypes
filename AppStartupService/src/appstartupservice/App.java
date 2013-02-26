/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstartupservice;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author lachlan
 */
public class App implements Comparable<App> {

    private final String name;
    private final String cmd;
    private final String working;
    private final Condition condition;
    private int order;
    private boolean wait;

    public App(String name, String cmd, String working, Condition condition) {
        this.name = name;
        this.cmd = cmd;
        this.working = working;
        this.condition = condition;
    }

    public void boot() throws IOException {
        if (!condition.shouldBoot()) {
            return;
        }

        ProcessBuilder builder = new ProcessBuilder(getCmd().split(" "));

        if (getWorking() != null) {
            builder.directory(new File(getWorking()));
        }

        builder.redirectOutput(new File(AppBooter.cwd() + "/" + getName()
                + ".out.txt"));
        builder.redirectError(new File(AppBooter.cwd() + "/" + getName()
                + ".err.txt"));

        builder.start();
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @return the working
     */
    public String getWorking() {
        return working;
    }

    /**
     * @return the condition
     */
    public Condition getCondition() {
        return condition;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

    public boolean isWait() {
        return wait;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override
    public int compareTo(App o) {
        return order - o.order;
    }
}
