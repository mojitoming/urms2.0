package com.dhcc.urms.condfilter.entity;

import java.io.Serializable;

public class NumberRange implements Serializable {
    private static final long serialVersionUID = 3919361616097130261L;

    private Double min;
    private Double max;

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }
}
