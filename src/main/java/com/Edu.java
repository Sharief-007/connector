package com;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
@SuppressWarnings("serial")
public class Edu implements Serializable {
    public Edu(){}

    public void setQ1(String q1) {
        this.q1 = q1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }

    public void setQ2(String q2) {
        this.q2 = q2;
    }

    public void setC2(String c2) {
        this.c2 = c2;
    }

    public void setQ3(String q3) {
        this.q3 = q3;
    }

    public void setC3(String c3) {
        this.c3 = c3;
    }

    public void setQ4(String q4) {
        this.q4 = q4;
    }

    public void setC4(String c4) {
        this.c4 = c4;
    }

    private String q1,c1;
    private String q2,c2;
    private String q3,c3;
    private String q4,c4;

    public void set1(String q1,String c1) {
        this.q1 = q1;
        this.c1 = c1;
    }

    public void set2(String q2,String c2) {
        this.q2=q2;
        this.c2 = c2;
    }

    public void set3(String q3,String c3) {
        this.q3 = q3;
        this.c3 = c3;
    }

    public void set4(String q4,String c4) {
        this.q4=q4;
        this.c4 = c4;
    }
    public String getQ1() {
        return q1;
    }

    public String getC1() {
        return c1;
    }

    public String getQ2() {
        return q2;
    }

    public String getC2() {
        return c2;
    }

    public String getQ3() {
        return q3;
    }

    public String getC3() {
        return c3;
    }

    public String getQ4() {
        return q4;
    }

    public String getC4() {
        return c4;
    }
}
