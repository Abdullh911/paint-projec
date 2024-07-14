package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"radiusX","radiusY","fill","x","y","id","index","type","stroke","strokeWidth"})
public class Ellipse extends Shape{
    private float  radiusX;

    public Ellipse() {
    }

    private float  radiusY;

    public void setRadiusX(float radiusX) {
        this.radiusX = radiusX;
    }

    public void setRadiusY(float radiusY) {
        this.radiusY = radiusY;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    private String fill;
}
