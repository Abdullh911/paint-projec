package com.example.demo;

import lombok.Getter;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@Getter
@XmlTransient
public abstract class Shape {

    public Shape() {
    }
    protected float x;
    protected float y;
    protected int id;
    protected int index;
    protected String type;
    protected String stroke;
    protected int strokeWidth;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStroke(String stroke) {
        this.stroke = stroke;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }


}