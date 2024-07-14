package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;

@CrossOrigin(origins = "*")
@RestController

public class JsonToObject {
    public static List<Circle> circle=new ArrayList<>();
    public static List<Square> square=new ArrayList<>();
    public static List<Rectangle> rectangle=new ArrayList<>();
    public static List<Ellipse> ellipse=new ArrayList<>();
    public static List<Triangle> triangle=new ArrayList<>();
    public static List<Line> line=new ArrayList<>();
    @PostMapping("/circles")
    public void circles(@RequestBody String circles) {
        circles = circles.replaceAll("\"", "");
        String jsonStringFromFrontend = circles;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Circle>>() {}.getType();
        circle = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : circle) {
            System.out.println(c);
        }
    }
    @PostMapping("/squares")
    public void squares(@RequestBody String squares) {
        squares = squares.replaceAll("\"", "");
        String jsonStringFromFrontend = squares;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Square>>() {}.getType();
        square = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : square) {
            System.out.println(c);
        }
    }
    @PostMapping("/rectangles")
    public void rectangles(@RequestBody String rectangles) {
        rectangles = rectangles.replaceAll("\"", "");
        String jsonStringFromFrontend = rectangles;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Rectangle>>() {}.getType();
        rectangle = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : rectangle) {
            System.out.println(c);
        }
    }
    @PostMapping("/ellipses")
    public void ellipses(@RequestBody String ellipses) {
        ellipses = ellipses.replaceAll("\"", "");
        String jsonStringFromFrontend = ellipses;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Ellipse>>() {}.getType();
        ellipse = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : ellipse) {
            System.out.println(c);
        }
    }
    @PostMapping("/triangles")
    public void triangles(@RequestBody String triangles) {
        triangles = triangles.replaceAll("\"", "");
        String jsonStringFromFrontend = triangles;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Triangle>>() {}.getType();
        triangle = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : triangle) {
            System.out.println(c);
        }
    }
    @PostMapping("/lines")
    public void lines(@RequestBody String lines) {
        lines = lines.replaceAll("\"", "");
        String jsonStringFromFrontend = lines;
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Line>>() {}.getType();
        line = gson.fromJson(jsonStringFromFrontend, listType);
        for (Shape c : line) {
            System.out.println(c);
        }
    }

    @PostMapping("/write")
    public void save(@RequestBody String path) throws JAXBException {
        path = path.replaceAll("\"", "");
        if (path.charAt(path.length() - 1) == 'n'){
            writeJson(path);
        }
        else{
           writeXML(path);
        }
    }
    public void writeXML(String path) throws JAXBException {
        ToXML xml = new ToXML((ArrayList<Circle>) circle, (ArrayList<Square>) square,
                (ArrayList<Rectangle>) rectangle, (ArrayList<Ellipse>) ellipse,
                (ArrayList<Triangle>) triangle, (ArrayList<Line>) line);
        JAXBContext jaxbContext = JAXBContext.newInstance(ToXML.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File(path);
        marshaller.marshal(xml, file);
    }
    public void writeJson(String path) {
        path = path.replaceAll("\"", "");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonCircle = gson.toJson(circle);
        String jsonSquare = gson.toJson(square);
        String jsonRectangle = gson.toJson(rectangle);
        String jsonEllipse = gson.toJson(ellipse);
        String jsonTriangle = gson.toJson(triangle);
        String jsonLine = gson.toJson(line);
        try {
            FileWriter writer = new FileWriter(path);
            writer.write("[");
            writer.write(jsonCircle);
            writer.write(",");
            writer.write(jsonSquare);
            writer.write(",");
            writer.write(jsonRectangle);
            writer.write(",");
            writer.write(jsonEllipse);
            writer.write(",");
            writer.write(jsonTriangle);
            writer.write(",");
            writer.write(jsonLine);
            writer.write("]");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/readJson")
    public String load(@RequestBody String path){
        path = path.replaceAll("\"", "");
        if(path.charAt(path.length()-1)=='n'){
            return readJson(path);
        }
        else{
            return readXML(path);
        }
    }
    public String readJson(String path) {
        path = path.replaceAll("\"", "");
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder jsonStringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonStringBuilder.append(line);
            }
            String jsonString = jsonStringBuilder.toString();
            System.out.println("JSON Content: " + jsonString);
            bufferedReader.close();
            fileReader.close();
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public String readXML(String path) {
        path = path.replaceAll("\"", "");
        try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(path)));
            ObjectMapper xmlMapper = new XmlMapper();
            JsonNode jsonNode = xmlMapper.readTree(xmlContent);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode orderedJsonNode = objectMapper.createObjectNode();
            orderedJsonNode.setAll((ObjectNode) jsonNode);
            String jsonString = "[";
            jsonString += orderedJsonNode.toString();
            jsonString= jsonString.replaceAll("\\{\"circle\":", "");
            jsonString= jsonString.replaceAll("\"square\":", "");
            jsonString= jsonString.replaceAll("\"triangle\":", "");
            jsonString= jsonString.replaceAll("\"rectangle\":", "");
            jsonString= jsonString.replaceAll("\"ellipse\":", "");
            jsonString= jsonString.replaceAll("\"line\":", "");
            jsonString= jsonString.replaceAll("\\{\"circle\":\\[", "");
            jsonString= jsonString.replaceAll("\"square\":\\[", "");
            jsonString= jsonString.replaceAll("\"triangle\":\\[", "");
            jsonString= jsonString.replaceAll("\"rectangle\":\\[", "");
            jsonString= jsonString.replaceAll("\"ellipse\":\\[", "");
            jsonString= jsonString.replaceAll("\"line\":\\[", "");
            jsonString= jsonString.substring(0, jsonString.length()-1);
            jsonString=jsonString.replaceAll("\\}]","}");
            jsonString=jsonString.replaceAll("]]","");
            jsonString=jsonString.replaceAll("\\[\\[","");
            jsonString=jsonString.replaceAll("\\[\\{","{");
            jsonString= jsonString.replaceAll("\\{\\{","{");
            jsonString="["+jsonString;
            jsonString= jsonString.concat("]");
            System.out.println(jsonString);
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}