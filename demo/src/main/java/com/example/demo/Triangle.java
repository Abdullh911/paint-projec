package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"radius","sides","fill","x","y","id","index","type","stroke","strokeWidth"})
public class Triangle extends Shape{
    private float  radius;
    private int sides;

    public Triangle() {
    }

    private String fill;

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setSides(int sides) {
        this.sides = sides;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
