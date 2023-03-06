package org.realcool.service.event;

public class CheckedEvent {
    private boolean checked;

    public CheckedEvent(boolean checked) {
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
