package org.realcool.base;

public abstract class BaseTask {
    private Long delay;

    private String description;

    public Long getDelay() {
        return delay;
    }

    public void setDelay(Long delay) {
        this.delay = delay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void exec();

    public abstract void stop();
}
