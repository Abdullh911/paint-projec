package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"radius","fill","x","y","id","index","type","stroke","strokeWidth"})
public class Circle extends Shape{

    private float radius;
    private String fill;

    public Circle() {
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    @Override
    public String toString() {
        return "Circle{" +
                "id=" + getId() +
                ", radius=" + radius +
                ", fill='" + fill + '\'' +
                ", stroke='" + getStroke() + '\'' +
                ", strokeWidth=" + getStrokeWidth() +
                ", x=" + getX() +
                ", y=" + getY() +
                '}';
    }
}
