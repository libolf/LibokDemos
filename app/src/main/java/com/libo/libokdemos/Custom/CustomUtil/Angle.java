package com.libo.libokdemos.Custom.CustomUtil;

/**
 * Created by libok on 2018-01-22.
 */

public class Angle {
    private int startAngle;
    private int sweepAngle;

    public Angle(int startAngle, int sweepAngle) {
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
    }

    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
    }

    public int getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(int sweepAngle) {
        this.sweepAngle = sweepAngle;
    }
}
