<template>
  <div class="bts">
    <button
      :style="{ backgroundColor: drawing === 'circle' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Circle)"
    >
      <i class="fa-solid fa-circle"></i>
    </button>
    <button
      :style="{ backgroundColor: drawing === 'ellipse' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Ellipse)"
    >
      ellipse
    </button>
    <button
      :style="{ backgroundColor: drawing === 'rectangle' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Rectangle)"
    >
      <i class="fa-solid fa-rectangle-list"></i>
    </button>
    <button
      :style="{ backgroundColor: drawing === 'line' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Line)"
    >
      <i class="fa-solid fa-grip-lines"></i>
    </button>
    <button
      :style="{ backgroundColor: drawing === 'square' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Square)"
    >
      <i class="fa-solid fa-square"></i>
    </button>
    <button
      :style="{ backgroundColor: drawing === 'triangle' ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="createShape(Triangle)"
    >
      <i class="fa-solid fa-caret-up"></i>
    </button>
    <button class="button-89" role="button" @click="getPathLoad()">
      <i class="fas fa-upload"></i>
    </button>
    <button class="button-89" role="button" @click="getPathSave()">
      <i class="fas fa-download"></i>
    </button>
    <div v-show="getPath == 'save'" class="path">
      <label>Path:</label>
      <input type="text" v-model="filePath" />
      <button class="btn" @click="save(this.filePath)">Confirm</button>
    </div>
    <div v-show="getPath == 'load'" class="path">
      <label>Path:</label>
      <input type="text" v-model="filePath" />
      <button class="btn" @click="load(this.filePath)">Confirm</button>
    </div>
    <button class="button-89" role="button" @click="undo()">
      <i class="fa-solid fa-rotate-left"></i>
    </button>
    <button class="button-89" role="button" @click="redo()">
      <i class="fa-solid fa-rotate-right"></i>
    </button>
    <button
      :style="{ backgroundColor: copy ? 'green' : '' }"
      class="button-89"
      role="button"
      @click="ChangeToCopy()"
    >
      COPY
    </button>
    <button
      :style="{ backgroundColor: deletee ? 'red' : '' }"
      class="button-89"
      role="button"
      @click="ChangeToDelete()"
    >
      Delete
    </button>
    <button
      class="button-89"
      role="button"
      @click="clear"
    >
      clear
    </button>
  </div>
  <div class="all">
    <div class="stage">
      <div class="clrs">
        <div class="colors">
          <div
            v-for="(color, index) in colorPalette"
            :key="index"
            @click="setColor(color)"
          >
            <div :style="{ backgroundColor: color }" class="color-square"></div>
          </div>
        </div>
        <div>Selected Color: {{ selectedColor }}</div>
      </div>
      <v-stage
        :config="configKonva"
        ref="stage"
        :key="stageKey"
        @mousedown="mousedownhandler"
        @mousemove="mousemovehandler"
        @mouseup="mouseuphandler"
      >
        <v-layer>
          <v-circle
            v-for="(circle, index) in shapes.circles"
            :key="circle.id"
            draggable="true"
            @dragstart="newInd(index)"
            :config="circle"
            @dragend="drageNew"
            @dblclick="showResizeForm(index, circle)"
            @click="changeColor(circle)"
          ></v-circle>
          <v-rect
            v-for="(rect, index) in shapes.rectangles"
            :key="rect.id"
            draggable="true"
            @dragstart="newInd(index)"
            :config="rect"
            @dragend="drageNewR"
            @dblclick="showResizeForm(index, rect)"
            @click="changeColor(rect)"
          ></v-rect>
          <v-line
            v-for="(line, index) in shapes.lines"
            :key="line.id"
            draggable="true"
            @dragstart="newInd(index)"
            :config="line"
            @dragend="drageNewL"
            @dblclick="showResizeForm(index, line)"
            @click="changeColor(line)"
          ></v-line>
          <v-rect
            v-for="(sq, index) in shapes.squares"
            :key="sq.id"
            draggable="true"
            @dragstart="newInd(index)"
            :config="sq"
            @dragend="drageNewS"
            @dblclick="showResizeForm(index, sq)"
            @click="changeColor(sq)"
          ></v-rect>
          <v-ellipse
            v-for="(ellipse, index) in shapes.ellipses"
            :key="ellipse.id"
            draggable="true"
            @dragstart="newInd(index)"
            :config="ellipse"
            @dragend="drageNewE"
            @dblclick="showResizeForm(index, ellipse)"
            @click="changeColor(ellipse)"
          ></v-ellipse>
          <v-regular-polygon
            v-for="triangle in shapes.triangles"
            :key="triangle.id"
            :config="triangle"
            draggable="true"
            @dragstart="newInd(triangle.index)"
            @dragend="drageNewT"
            @click="changeColor(triangle)"
          />
        </v-layer>
      </v-stage>
    </div>
  </div>
  <div v-if="showForm">
    <label v-if="selectedShapeType === 'rectangle'">
      Width:
      <input type="number" v-model="resizeForm.width" />
    </label>
    <label v-if="selectedShapeType === 'rectangle'">
      Height:
      <input type="number" v-model="resizeForm.height" />
    </label>
    <label v-if="selectedShapeType === 'square'">
      side length:
      <input type="number" v-model="resizeForm.width" />
    </label>
    <label v-if="selectedShapeType === 'circle'">
      radius:
      <input type="number" v-model="resizeForm.width" />
    </label>
    <label v-if="selectedShapeType === 'ellipse'">
      radiusX:
      <input type="number" v-model="resizeForm.width" />
    </label>
    <label v-if="selectedShapeType === 'ellipse'">
      radiusY:
      <input type="number" v-model="resizeForm.height" />
    </label>
    <button @click="applyResize">Apply</button>
  </div>
</template>

<script>
import { VRegularPolygon, VStage } from "vue-konva";

export default {
  name: "App",
  components: {
    VRegularPolygon,
    VStage,
  },
  data() {
    return {
      isdrawing: false,
      drawing: "none",
      stageKey: 0,
      getPath: "",
      filePath: "",
      deletee: false,
      copy: false,
      Circle: "circle",
      Ellipse: "ellipse",
      Square: "square",
      Line: "line",
      Triangle: "triangle",
      Rectangle: "rectangle",
      colorPalette: [
        "#FF0000",
        "#FF7F00",
        "#FFFF00",
        "#7FFF00",
        "#00FF00",
        "#00FFFF",
        "#007FFF",
        "#0000FF",
        "#7F00FF",
        "#FF00FF",
        "#FFFFFF",
        "#C0C0C0",
        "#808080",
        "#000000",
      ],
      selectedColor: null,
      draggedShapeIndex: null,
      ind: null,
      initID: 0,
      shapes: {
        rectangles: [],
        circles: [],
        lines: [],
        squares: [],
        ellipses: [],
        triangles: [],
      },
      showForm: false,
      selectedShapeIndex: null,
      selectedShapeType: null,
      resizeForm: {
        width: 0,
        height: 0,
      },
      shapeIdCounter: 1,
      configKonva: {
        width: 1400,
        height: 600,
      },
      undostack: [
        {
          rectangles: [],
          circles: [],
          lines: [],
          squares: [],
          ellipses: [],
          triangles: [],
        },
      ],
      redostack: [],
    };
  },
  methods: {
    ChangeToCopy() {
      this.copy = !this.copy;
      this.deletee = false;
    },
    ChangeToDelete() {
      this.deletee = !this.deletee;
      this.copy = false;
    },
    copyShape(shape) {
      if (shape.type === "triangle") {
        this.shapes.triangles.push({
          index: this.shapes.triangles.length,
          id: this.shapeIdCounter++,
          type: "triangle",
          x: 100,
          y: 100,
          sides: 3,
          radius: shape.radius,
          fill: shape.fill,
          stroke: shape.stroke,
          strokeWidth: 4,
        });
      }
      if (shape.type === "square") {
        this.shapes.squares.push({
          index: this.shapes.squares.length,
          id: this.shapeIdCounter++,
          type: "square",
          x: 100,
          y: 100,
          width: shape.width,
          height: shape.height,
          fill: shape.fill,
          stroke: shape.stroke,
          strokeWidth: 4,
        });
      }
      if (shape.type === "rectangle") {
        this.shapes.rectangles.push({
          index: this.shapes.rectangles.length,
          id: this.shapeIdCounter++,
          type: "rectangle",
          x: 100,
          y: 100,
          width: shape.width,
          height: shape.height,
          fill: shape.fill,
          stroke: shape.stroke,
          strokeWidth: 4,
        });
      }
      if (shape.type === "line") {
        this.shapes.lines.push({
          index: this.shapes.lines.length,
          type: "line",
          x: 100,
          y: 100,
          points: shape.points,
          stroke: shape.stroke,
          strokeWidth: 5,
        });
      }
      if (shape.type === "circle") {
        this.shapes.circles.push({
          index: this.shapes.circles.length,
          id: this.initID++,
          type: "circle",
          x: 100,
          y: 100,
          radius: shape.radius,
          fill: shape.fill,
          stroke: "black",
          strokeWidth: 4,
        });
      }
      if (shape.type === "ellipse") {
        this.shapes.ellipses.push({
          index: this.shapes.ellipses.length,
          id: this.shapeIdCounter++,
          type: "ellipse",
          x: 100,
          y: 100,
          radiusX: shape.radiusX,
          radiusY: shape.radiusY,
          fill: shape.fill,
          stroke: "black",
          strokeWidth: 4,
        });
      }
      this.copy = false;
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    deleteShape(shape) {
      if (shape.type === "ellipse") {
        this.shapes.ellipses = this.shapes.ellipses.filter(
          (item) => item.id !== shape.id
        );
      }
      if (shape.type === "square") {
        this.shapes.squares = this.shapes.squares.filter(
          (item) => item.id !== shape.id
        );
      }
      if (shape.type === "rectangle") {
        this.shapes.rectangles = this.shapes.rectangles.filter(
          (item) => item.id !== shape.id
        );
      }
      if (shape.type === "circle") {
        this.shapes.circles = this.shapes.circles.filter(
          (item) => item.id !== shape.id
        );
      }
      if (shape.type === "triangle") {
        this.shapes.triangles = this.shapes.triangles.filter(
          (item) => item.id !== shape.id
        );
      }
      if (shape.type === "line") {
        this.shapes.lines = this.shapes.lines.filter(
          (item) => item.id !== shape.id
        );
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    createShape(s) {
      if (s === "triangle") {
        this.addTriangle();
      }
      if (s === "square") {
        this.addSquare();
      }
      if (s === "rectangle") {
        this.addRectangle();
      }
      if (s === "line") {
        this.addLine();
      }
      if (s === "circle") {
        this.addCircle();
      }
      if (s === "ellipse") {
        this.addEllipse();
      }
    },
    showResizeForm(index, shape) {
      this.selectedShapeIndex = index;
      this.showForm = true;
      this.selectedShapeType = shape.type;
    },
    changeColor(shape) {
      if (this.selectedColor) {
        if (shape.type === "rectangle") {
          if (
            this.shapes.rectangles[shape.index].fill !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.rectangles[shape.index].fill =
              this.selectedColor.toLowerCase();
          }
        }
        if (shape.type === "circle") {
          if (
            this.shapes.circles[shape.index].fill !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.circles[shape.index].fill =
              this.selectedColor.toLowerCase();
          }
        }
        if (shape.type === "ellipse") {
          if (
            this.shapes.ellipses[shape.index].fill !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.ellipses[shape.index].fill =
              this.selectedColor.toLowerCase();
          }
        }
        if (shape.type === "line") {
          if (
            this.shapes.lines[shape.index].stroke !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.lines[shape.index].stroke =
              this.selectedColor.toLowerCase();
          }
        }
        if (shape.type === "square") {
          if (
            this.shapes.squares[shape.index].fill !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.squares[shape.index].fill =
              this.selectedColor.toLowerCase();
          }
        }
        if (shape.type === "triangle") {
          if (
            this.shapes.triangles[shape.index].fill !=
            this.selectedColor.toLowerCase()
          ) {
            this.shapes.triangles[shape.index].fill =
              this.selectedColor.toLowerCase();
          }
        }
        this.selectedColor = null;
        this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
        this.redostack = [];
      } else if (this.copy) {
        this.copyShape(shape);
        console.log(this.copy, shape);
      } else if (this.deletee) {
        this.deleteShape(shape);
        this.deletee = false;
        console.log(shape);
      }
    },
    applyResize() {
      if (this.selectedShapeIndex !== null) {
        if (this.selectedShapeType === "rectangle") {
          if (this.resizeForm.width) {
            this.shapes.rectangles[this.selectedShapeIndex].width =
              this.resizeForm.width;
          }
          if (this.resizeForm.height) {
            this.shapes.rectangles[this.selectedShapeIndex].height =
              this.resizeForm.height;
          }
          this.showForm = false;
        }
        if (this.selectedShapeType === "circle") {
          if (this.resizeForm.width) {
            this.shapes.circles[this.selectedShapeIndex].radius =
              this.resizeForm.width;
          }
          this.showForm = false;
        }
        if (this.selectedShapeType === "square") {
          if (this.resizeForm.width) {
            this.shapes.squares[this.selectedShapeIndex].width =
              this.resizeForm.width;
            this.shapes.squares[this.selectedShapeIndex].height =
              this.resizeForm.width;
          }
          this.showForm = false;
        }
        if (this.selectedShapeType === "ellipse") {
          if (this.resizeForm.width) {
            this.shapes.ellipses[this.selectedShapeIndex].radiusX =
              this.resizeForm.width;
          }
          if (this.resizeForm.height) {
            this.shapes.ellipses[this.selectedShapeIndex].radiusY =
              this.resizeForm.height;
          }
          this.showForm = false;
        }
        this.resizeForm.width = 0;
        this.resizeForm.height = 0;
        this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
        this.redostack = [];
      }
    },
    newInd(index) {
      this.draggedShapeIndex = index;
    },
    addTriangle() {
      this.drawing = "triangle";
    },
    addEllipse() {
      this.drawing = "ellipse";
    },

    addRectangle() {
      this.drawing = "rectangle";
    },
    addCircle() {
      this.drawing = "circle";
    },
    addLine() {
      this.drawing = "line";
    },
    addSquare() {
      this.drawing = "square";
    },
    drageNew(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.circles[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.circles[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(this.shapes.circles);
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    drageNewR(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.rectangles[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.rectangles[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(this.shapes.rectangles);
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    drageNewL(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.lines[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.lines[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(this.shapes.lines);
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    drageNewS(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.squares[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.squares[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(JSON.stringify(this.shapes.squares));
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    drageNewE(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.ellipses[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.ellipses[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(this.shapes.ellipses);
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    drageNewT(e) {
      if (this.draggedShapeIndex !== null) {
        this.shapes.triangles[this.draggedShapeIndex].x = e.target.attrs.x;
        this.shapes.triangles[this.draggedShapeIndex].y = e.target.attrs.y;
        console.log(this.shapes.triangles);
      }
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    },
    undo() {
      if (this.undostack.length > 1) {
        this.redostack.push(JSON.parse(JSON.stringify(this.undostack.pop())));
        this.shapes = JSON.parse(
          JSON.stringify(this.undostack[this.undostack.length - 1])
        );
        this.stageKey += 1;
      }
    },
    redo() {
      if (this.redostack.length > 0) {
        this.shapes = JSON.parse(
          JSON.stringify(this.redostack[this.redostack.length - 1])
        );
        this.undostack.push(JSON.parse(JSON.stringify(this.redostack.pop())));
        this.stageKey += 1;
      }
    },
    getPathSave() {
      this.getPath = "save";
    },
    getPathLoad() {
      this.getPath = "load";
    },
    save(filePath) {
      this.getPath = "";
      fetch("http://localhost:8080/circles", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.circles),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/squares", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.squares),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/rectangles", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.rectangles),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/triangles", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.triangles),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/ellipses", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.ellipses),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/lines", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(this.shapes.lines),
      }).catch((err) => {
        console.log(err);
      });
      fetch("http://localhost:8080/write", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(filePath),
      }).catch((err) => {
        console.log(err);
      });
    },
    load(filePath) {
      this.getPath = "";
      fetch("http://localhost:8080/readJson", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(filePath),
      })
        .then((res) => res.json())
        .then((data) => {
          this.clear() ;
          if (filePath.includes("json")) {
            //circles
            for (let i = 0; i < data[0].length; i++) {
              this.shapes.circles.push(data[0][i]);
            }
            //squares
            for (let i = 0; i < data[1].length; i++) {
              this.shapes.squares.push(data[1][i]);
            }
            //rectangles
            for (let i = 0; i < data[2].length; i++) {
              this.shapes.rectangles.push(data[2][i]);
            }
            //ellipses
            for (let i = 0; i < data[3].length; i++) {
              this.shapes.ellipses.push(data[3][i]);
            }
            //triangles
            for (let i = 0; i < data[4].length; i++) {
              this.shapes.triangles.push(data[4][i]);
              console.log(data[4][i]);
            }
            //lines
            for (let i = 0; i < data[5].length; i++) {
              this.shapes.lines.push(data[5][i]);
            }
          } else {
            console.log(data);
            for (let i = 0; i < data.length; i++) {
              if (data[i].type === "triangle") {
                this.shapes.triangles.push({
                  index: this.shapes.triangles.length,
                  id: this.shapeIdCounter++,
                  type: "triangle",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  sides: 3,
                  radius: parseFloat(data[i].radius),
                  fill: data[i].fill,
                  stroke: data[i].stroke,
                  strokeWidth: parseInt(data[i].strokeWidth),
                });
              }
              if (data[i].type === "square") {
                this.shapes.squares.push({
                  index: this.shapes.squares.length,
                  id: this.shapeIdCounter++,
                  type: "square",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  width: parseFloat(data[i].width),
                  height: parseFloat(data[i].height),
                  fill: data[i].fill,
                  stroke: data[i].stroke,
                  strokeWidth: parseInt(data[i].strokeWidth),
                });
              }
              if (data[i].type === "rectangle") {
                this.shapes.rectangles.push({
                  index: this.shapes.rectangles.length,
                  id: this.shapeIdCounter++,
                  type: "rectangle",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  width: parseFloat(data[i].width),
                  height: parseFloat(data[i].height),
                  fill: data[i].fill,
                  stroke: data[i].stroke,
                  strokeWidth: parseInt(data[i].strokeWidth),
                });
              }
              if (data[i].type === "line") {
                this.shapes.lines.push({
                  index: this.shapes.lines.length,
                  type: "line",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  points: data[i].points.map((str) => parseFloat(str)),
                  stroke: data[i].stroke,
                  strokeWidth: parseInt(data[i].strokeWidth),
                });
              }
              if (data[i].type === "circle") {
                this.shapes.circles.push({
                  index: this.shapes.circles.length,
                  id: this.shapeIdCounter++,
                  type: "circle",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  radius: parseFloat(data[i].radius),
                  fill: data[i].fill,
                  stroke: data[i].stroke,
                  strokeWidth: parseFloat(data[i].strokeWidth),
                });
              }
              if (data[i].type === "ellipse") {
                this.shapes.ellipses.push({
                  index: this.shapes.ellipses.length,
                  id: this.shapeIdCounter++,
                  type: "ellipse",
                  x: parseFloat(data[i].x),
                  y: parseFloat(data[i].y),
                  radiusX: parseFloat(data[i].radiusX),
                  radiusY: parseFloat(data[i].radiusY),
                  fill: data[i].fill,
                  stroke: data[i].stroke,
                  strokeWidth: parseInt(data[i].strokeWidth),
                });
              }
            }
          }
          this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
        this.redostack = [];
        })
        .catch((err) => {
          console.log(err);
        });
    },
    setColor(color) {
      this.selectedColor = this.hexToColorNameWithComparison(color);
    },
    hexToColorNameWithComparison(hexCode) {
      let colorMappings = {
        "#FF0000": "Red",
        "#FF7F00": "Orange",
        "#FFFF00": "Yellow",
        "#7FFF00": "Chartreuse",
        "#00FF00": "Lime",
        "#00FF7F": "Spring Green",
        "#00FFFF": "Cyan",
        "#007FFF": "Azure",
        "#0000FF": "Blue",
        "#7F00FF": "Violet",
        "#FF00FF": "Magenta",
        "#FF007F": "Rose",
        "#FFFFFF": "White",
        "#C0C0C0": "Silver",
        "#808080": "Gray",
        "#000000": "Black",
      };

      let colorName = colorMappings[hexCode.toUpperCase()];
      return colorName ? colorName : "Unknown";
    },
    mousedownhandler() {
      this.isdrawing = true;
      const stage = this.$refs.stage.getStage();
      const mousePos = stage.getPointerPosition();
      if (this.drawing === "rectangle") {
        this.shapes.rectangles.push({
          index: this.shapes.rectangles.length,
          id: this.shapeIdCounter++,
          type: "rectangle",
          x: mousePos.x,
          y: mousePos.y,
          width: 0,
          height: 0,
          fill: "red",
          stroke: "black",
          strokeWidth: 4,
        });
      } else if (this.drawing === "circle") {
        this.shapes.circles.push({
          index: this.shapes.circles.length,
          id: this.initID++,
          type: "circle",
          x: mousePos.x,
          y: mousePos.y,
          radius: 0,
          fill: "red",
          stroke: "black",
          strokeWidth: 4,
        });
      } else if (this.drawing === "ellipse") {
        this.shapes.ellipses.push({
          index: this.shapes.ellipses.length,
          id: this.shapeIdCounter++,
          type: "ellipse",
          x: mousePos.x,
          y: mousePos.y,
          radiusX: 0,
          radiusY: 0,
          fill: "blue",
          stroke: "black",
          strokeWidth: 4,
        });
      } else if (this.drawing === "line") {
        this.shapes.lines.push({
          index: this.shapes.lines.length,
          id: this.shapeIdCounter++,
          type: "line",
          x: mousePos.x,
          y: mousePos.y,
          points: [0, 0, 0, 0],
          stroke: "green",
          strokeWidth: 5,
        });
      } else if (this.drawing === "triangle") {
        this.shapes.triangles.push({
          index: this.shapes.triangles.length,
          id: this.shapeIdCounter++,
          type: "triangle",
          x: mousePos.x,
          y: mousePos.y,
          sides: 3,
          radius: 0,
          fill: "blue",
          stroke: "black",
          strokeWidth: 4,
        });
      } else if (this.drawing === "square") {
        this.shapes.squares.push({
          index: this.shapes.squares.length,
          id: this.shapeIdCounter++,
          type: "square",
          x: mousePos.x,
          y: mousePos.y,
          width: 0,
          height: 0,
          fill: "pink",
          stroke: "black",
          strokeWidth: 4,
        });
      }
    },
    mouseuphandler() {
      if (this.drawing != "none") {
        this.drawing = "none";
        this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
        this.redostack = [];
      }
      this.isdrawing = false;
    },
    mousemovehandler() {
      if (this.isdrawing && this.drawing != "none") {
        const stage = this.$refs.stage.getStage();
        const mousePos = stage.getPointerPosition();
        if (this.drawing === "rectangle") {
          let newwidth =
            mousePos.x -
            this.shapes.rectangles[this.shapes.rectangles.length - 1].x;
          let newheight =
            mousePos.y -
            this.shapes.rectangles[this.shapes.rectangles.length - 1].y;
          this.shapes.rectangles[this.shapes.rectangles.length - 1].width =
            newwidth;
          this.shapes.rectangles[this.shapes.rectangles.length - 1].height =
            newheight;
        } else if (this.drawing === "circle") {
          let newwidth =
            mousePos.x - this.shapes.circles[this.shapes.circles.length - 1].x;
          let newheight =
            mousePos.y - this.shapes.circles[this.shapes.circles.length - 1].y;
          let rad = Math.sqrt(Math.pow(newheight, 2) + Math.pow(newwidth, 2));
          this.shapes.circles[this.shapes.circles.length - 1].radius = rad;
        } else if (this.drawing === "ellipse") {
          let newwidth =
            mousePos.x -
            this.shapes.ellipses[this.shapes.ellipses.length - 1].x;
          let newheight =
            mousePos.y -
            this.shapes.ellipses[this.shapes.ellipses.length - 1].y;
          this.shapes.ellipses[this.shapes.ellipses.length - 1].radiusX =
            Math.abs(newwidth);
          this.shapes.ellipses[this.shapes.ellipses.length - 1].radiusY =
            Math.abs(newheight);
        } else if (this.drawing === "line") {
          let newwidth =
            mousePos.x - this.shapes.lines[this.shapes.lines.length - 1].x;
          let newheight =
            mousePos.y - this.shapes.lines[this.shapes.lines.length - 1].y;
          this.shapes.lines[this.shapes.lines.length - 1].points = [
            0,
            0,
            newwidth,
            newheight,
          ];
        } else if (this.drawing === "triangle") {
          let newwidth =
            mousePos.x -
            this.shapes.triangles[this.shapes.triangles.length - 1].x;
          let newheight =
            mousePos.y -
            this.shapes.triangles[this.shapes.triangles.length - 1].y;
          let rad = Math.sqrt(Math.pow(newheight, 2) + Math.pow(newwidth, 2));
          this.shapes.triangles[this.shapes.triangles.length - 1].radius = rad;
        } else if (this.drawing === "square") {
          let newwidth =
            mousePos.x - this.shapes.squares[this.shapes.squares.length - 1].x;
          this.shapes.squares[this.shapes.squares.length - 1].width = newwidth;
          this.shapes.squares[this.shapes.squares.length - 1].height = newwidth;
        }
      }
    },
    clear(){
      this.shapes={
        rectangles: [],
        circles: [],
        lines: [],
        squares: [],
        ellipses: [],
        triangles: [],
      } ;
      this.undostack.push(JSON.parse(JSON.stringify(this.shapes)));
      this.redostack = [];
    }
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 0px;
}
body {
  background-image: url("vecteezy_simple-childish-scribble-backdrop-colorful-doodle-art_8362482.jpg");
}
.all {
  display: flex;
  justify-content: center;
  align-items: center;
}
.stage {
  border: #2c3e50 1px solid;
  display: flex;
  flex-flow: column wrap;
  align-items: center;
}

.stage {
  border: #2c3e50 1px solid;
  display: flex;
  flex-flow: column wrap;
  justify-content: center;
  align-items: center;
}
button {
  width: 100px;
  height: 50px;
}
.bts {
  width: 100%;
  display: flex;
  flex-flow: row wrap;
  justify-content: space-evenly;
}
.color-square {
  width: 10px;
  height: 10px;
  border: 1px solid #000;
  cursor: pointer;
  margin: 5px;
}
.clrs {
  display: flex;
  flex-flow: column wrap;
  border: #000 1px solid;
}
.stage {
  background-color: white;
}

.button-89 {
  --b: 3px;
  --s: 0.45em;
  --color: #373b44;

  padding: calc(0.5em + var(--s)) calc(0.9em + var(--s));
  color: var(--color);
  --_p: var(--s);
  background: conic-gradient(
      from 90deg at var(--b) var(--b),
      #0000 90deg,
      var(--color) 0
    )
    var(--_p) var(--_p) / calc(100% - 2 * var(--_p)) calc(100% - 2 * var(--_p)); /* Modified this line */
  transition: 0.3s linear, color 0s, background-color 0s;
  outline: var(--b) solid #0000;
  outline-offset: 0.6em;
  font-size: 16px;

  border: 0;

  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-89:hover,
.button-89:focus-visible {
  --_p: 0px;
  outline-color: var(--color);
  outline-offset: 0.05em;
}

.button-89:active {
  background: var(--color);
  color: #fff;
}
.colors {
  display: flex;
}
input {
  margin-right: 5px;
  display: block;
  padding: 2px 1px;
  width: 100%;
  border: none;
  border-bottom: 2px solid #ddd;
}
label {
  margin: 25px 0px 15px;
  font-size: 16px;
  letter-spacing: 1px;
  font-weight: bold;
}
.path {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 150px;
  z-index: 1;
  padding: 100px 150px;
  background-color: black;
  color: white;
  opacity: 0.7;
  border-radius: 10px;
  border: 2px solid transparent;
  outline: #2c3e50 3px solid;
}
</style>
