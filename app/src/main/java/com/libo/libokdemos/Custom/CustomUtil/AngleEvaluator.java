package com.libo.libokdemos.Custom.CustomUtil;

import android.animation.TypeEvaluator;

/**
 * Created by libok on 2018-01-22.
 */

public class AngleEvaluator implements TypeEvaluator {
    @Override
    public Object evaluate(float fraction, Object startValue, Object endValue) {
        Angle startAngle = (Angle) startValue;
        Angle endAngle = (Angle) endValue;
        int start = startAngle.getStartAngle();
        int end = (int) (startAngle.getSweepAngle() - fraction * (startAngle.getSweepAngle() - endAngle.getSweepAngle()));
        Angle angle = new Angle(start, end);
        return angle;
    }
}
