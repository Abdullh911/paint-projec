package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@XmlRootElement(name = "shapes")
@XmlType(propOrder = {"circles","squares","rectangles","ellipses","triangles","lines"})
public class ToXML{
    private ArrayList<Circle> circles=new ArrayList<>();
    private ArrayList<Square> squares=new ArrayList<>();
    private ArrayList<Rectangle> rectangles=new ArrayList<>();
    private ArrayList<Ellipse> ellipses=new ArrayList<>();
    private ArrayList<Triangle> triangles=new ArrayList<>();
    private ArrayList<Line> lines=new ArrayList<>();
    ToXML(ArrayList<Circle> circles,ArrayList<Square> squares,ArrayList<Rectangle> rectangles,ArrayList<Ellipse> ellipses,ArrayList<Triangle> triangles,ArrayList<Line> lines){
        this.circles=circles;
        this.squares=squares;
        this.rectangles=rectangles;
        this.ellipses=ellipses;
        this.triangles=triangles;
        this.lines=lines;
    }
   // @XmlElementWrapper(name = "circles")
    @XmlElement(name = "circle")
    public ArrayList<Circle> getCircles() {
        return circles;
    }
    //@XmlElementWrapper(name = "squares")
    @XmlElement(name = "square")
    public ArrayList<Square> getSquares() {
        return squares;
    }
    //@XmlElementWrapper(name = "rectangles")
    @XmlElement(name = "rectangle")
    public ArrayList<Rectangle> getRectangles() {
        return rectangles;
    }
    //@XmlElementWrapper(name = "ellipses")
    @XmlElement(name = "ellipse")
    public ArrayList<Ellipse> getEllipses() {
        return ellipses;
    }
    //@XmlElementWrapper(name = "triangles")
    @XmlElement(name = "triangle")
    public ArrayList<Triangle> getTriangles() {
        return triangles;
    }
    //@XmlElementWrapper(name = "lines")
    @XmlElement(name = "line")
    public ArrayList<Line> getLines() {
        return lines;
    }
}