package org.realcool.service.event;

public class CheckedEvent extends TaskEvent{
    private int type;

    private boolean checked;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public CheckedEvent(boolean checked, int type) {
        this.checked = checked;
        this.type = type;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
