package assignment01;

import java.util.LinkedList;
import java.util.Random;

public class canvasGraph {

    // Attributes
    private int maxWidth;
    private int maxHeight;
    private cell[][] vertexArray;
    private LinkedList<cell> walls = new LinkedList<>();
    private LinkedList<cell> path = new LinkedList<>();

    // Constructor Methods
     canvasGraph() {
        vertexArray = new cell[50][50];
        for (int yIndex = 0; yIndex < 50; yIndex++)
            for (int xIndex = 0; xIndex < 50; xIndex++) {
                vertexArray[xIndex][yIndex] = new cell(xIndex, yIndex);
            }
    }
    canvasGraph(int xSize, int ySize) {
        // constructor uses integer inputs to create a collection of cells equal to xSize * ySize
        vertexArray = new cell[xSize][ySize];
        this.maxWidth = xSize;
        this.maxHeight = ySize;
        for (int yIndex = 0; yIndex < ySize; yIndex++)
            for (int xIndex = 0; xIndex < xSize; xIndex++) {
                vertexArray[xIndex][yIndex] = new cell((xIndex+1), (yIndex+1));
            }
    }

    public cell getCell(int x, int y) {
        // method gets a particular cell given the input coordinates
        return vertexArray[x-1][y-1];
    }

    public void generateMaze() {
         /* this method generates the collection cells that comprise the path within the maze. The initial starting cell
            is chosen as a random spot on the first column of the graph. The method uses the Randomized Prim's Algorithm
            as described on 'https://en.wikipedia.org/wiki/Maze_generation_algorithm#Randomized_Prim's_algorithm'

            The end point is also randomly chosen as a point on the last column of the graph.
          */

        // Add the initially chosen cell to the path list as the first entry
        Random randomY = new Random();
        int startY = (randomY.nextInt(50))+1;
        cell startCell = getCell(1,startY);
        startCell.increaseVisitCount();
        path.add(startCell);

        // Add adjacent cells to walls list
        walls.add(getCell(1,startY-1));
        walls.add(getCell(1,startY+1));
        walls.add(getCell(2,startY));

    }

    public LinkedList<cell> getPath() {
         // this method returns the linked list of cells that compose the path
        return this.path;
    }

    public LinkedList<cell> getWalls() {
         // this method returns the linked list of cells that compose the path
        return this.walls;
    }


    private class cell {

        // Attributes
        private int xCoord;
        private int yCoord;
        private int visitCount = 0;


        // Constructor method
        cell(int x, int y) {
            // constructor method using (x,y) coordinates as inputs
            this.setxCoord(x);
            this.setyCoord(y);
        }

        private void setxCoord(int x) {
            // sets the x coordinate parameter
            this.xCoord = x;
        }

        protected int getxCoord(){
            // returns the x coordinate of the cell
            return this.xCoord;
        }

        private void setyCoord(int y) {
            this.yCoord = y;
        }

        protected int getyCoord(){
            // returns the y coordinate of the cell
            return this.yCoord;
        }

        protected void increaseVisitCount(){
            // increases the visitCount parameter by 1 every time method is called
            this.visitCount++;
        }

        protected int getVisitCount(){
            // returns the visit count number
            return this.visitCount;
        }
    }
}
