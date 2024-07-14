package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"height","width","fill","x","y","id","index","type","stroke","strokeWidth"})
public class Rectangle extends Shape{


    public Rectangle() {
    }


    @Override
    public String toString() {
        return "Rectangle{" +
                "id=" + getId() +
                ", height=" + height +
                ", width=" + width +
                ", fill='" + fill + '\'' +
                ", x=" + getX() +
                ", y=" + getY() +
                ", index=" + getIndex() +
                ", type='" + getType() + '\'' +
                ", stroke='" + getStroke() + '\'' +
                ", strokeWidth=" + getStrokeWidth() +
                '}';
    }

    private float height;

    private float width;

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    private String fill;

}
