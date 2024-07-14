package com.example.demo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import javax.xml.bind.annotation.XmlType;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlType(propOrder = {"height","width","fill","x","y","id","index","type","stroke","strokeWidth"})
public class Square extends Shape{
    private float width;
    private float height;
    private String fill;

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public Square() {
    }



    @Override
    public String toString() {
        return "Square{" +
                "width=" + width +
                ", height=" + height +
                ", fill='" + fill + '\'' +
                ", stroke='" + getStroke() + '\'' +
                ", strokeWidth=" + getStrokeWidth() +
                ", x=" + getX() +
                ", y=" + getY() +
                '}';
    }

}
