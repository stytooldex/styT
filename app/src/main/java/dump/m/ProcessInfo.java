package dump.m;

import android.graphics.drawable.Drawable;

public class ProcessInfo {
    private String labelName;
    private Drawable labelIcon;
    private String processName;

    public ProcessInfo(){ }

    public ProcessInfo(String labelName, Drawable labelIcon, String processName) {
        this.labelName = labelName;
        this.labelIcon = labelIcon;
        this.processName = processName;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Drawable getLabelIcon() {
        return labelIcon;
    }

    public void setLabelIcon(Drawable labelIcon) {
        this.labelIcon = labelIcon;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }
}
