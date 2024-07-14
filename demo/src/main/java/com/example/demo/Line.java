package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"points","x","y","id","index","type","stroke","strokeWidth"})
public class Line extends Shape {
    public Line() {
    }
//    @XmlTransient
//    public int getId() {
//        return id;
//    }

    private float [] points;
    public float [] getPoints() {
        return points;
    }
    public void setPoints(float[] points) {
        this.points = points;
    }
}
