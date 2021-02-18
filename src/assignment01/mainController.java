package assignment01;

import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    // GUI Objects
    public BorderPane borderPane;
    public Canvas centerCanvas;
    public TextField notificationText;
    public Button generateMazeButton;
    public Button clearMazeButton;
    public Button solveMazeButton;
    public MenuItem generateMazeMenuButton;
    public MenuItem clearMazeMenuButton;
    public MenuItem solveMazeMenuButton;
    public MenuItem aboutMenuButton;
    public GraphicsContext canvasGc;

    // private attributes
    private int pixelSize;
    private gridGraph graph;
    private ArrayList<gridGraph.cell> mazePath = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get the Graphics Context of the center canvas
        canvasGc = centerCanvas.getGraphicsContext2D();

        // draw the grid upon startup
        setPixelSize20();
        //System.out.println(canvasGc.getCanvas().getHeight());
        //System.out.println(canvasGc.getCanvas().getWidth());
        drawOutline(canvasGc);
    }

    // Public Event Handling Methods
    public void generateMaze() {
        // method used to generate the maze object and display it on the mainCanvas object

        // method variables
        int canvasWidth = (int)canvasGc.getCanvas().getWidth();
        int canvasHeight = (int)canvasGc.getCanvas().getHeight();
        int gridWidth = canvasWidth/this.pixelSize;
        int gridHeight = canvasHeight/this.pixelSize;
        ArrayList<gridGraph.wall> wallList = new ArrayList<>();

        // Start with a grid full of walls.
        graph = new gridGraph(gridWidth, gridHeight);

        // pick a random cell and add it to the maze and add walls to wall list
        // for this, we want the start to be on the left hand row, so will need to pick a cell w/ xPos == 1
        Random random = new Random();
        int startCellXpos = random.nextInt(gridHeight);
        gridGraph.cell startCell = graph.getCell((startCellXpos * gridWidth));
        startCell.visit();
        mazePath.add(startCell);
        addWalls(startCell, wallList);

        // while there are walls left in the list
        while (!(wallList.isEmpty())) {

            // pick a random wall from the list
            Random rand = new Random();
            int choice = rand.nextInt(wallList.size());
            //System.out.println("Random number used: " + choice);
            //System.out.println("Total Cells in Maze: " + mazePath.size());
            //System.out.println("Size of wallList: " + wallList.size());
            gridGraph.wall workingWall = wallList.get(choice);

            gridGraph.cell cellOne = workingWall.getCellOne();
            gridGraph.cell cellTwo = workingWall.getCellTwo();

            if (!(mazePath.contains(cellTwo))) {
                mazePath.add(cellTwo);
                workingWall.setPassage(true);
                addWalls(cellTwo, wallList);
                wallList.remove(workingWall);
                // add the new cell's walls to the wall list
            } else if (!(mazePath.contains(cellOne))) {
                mazePath.add(cellOne);
                workingWall.setPassage(true);
                addWalls(cellOne, wallList);
                wallList.remove(workingWall);
            } else {
                wallList.remove(workingWall);
                // as both cells are already in the path, remove wall from list
                // restart the loop
            }
        }
        updateNotificationArea("Maze Successfully Generated");
        //System.out.println("Total Cells in Maze: " + mazePath.size());
        //System.out.println("Size of wallList: " + wallList.size());


        drawMaze(canvasGc, mazePath);
        drawOutline(canvasGc);

    }

    private void addWalls(gridGraph.cell inputCell, ArrayList<gridGraph.wall> inputList) {
        // this method takes a cell and a list and adds all walls that are not already on the list
        if (!(inputCell.getTopWall().getCellTwo() == null)) {
            gridGraph.wall workingWall = inputCell.getTopWall();
            if (!(inputList.contains(workingWall))) {
                inputList.add(workingWall);
            } else {
                // do nothing
            }
        }
        if (!(inputCell.getRightWall().getCellTwo() == null)) {
            gridGraph.wall workingWall = inputCell.getRightWall();
            if (!(inputList.contains(workingWall))) {
                inputList.add(workingWall);
            } else {
                // do nothing
            }
        }

        if (!(inputCell.getBottomWall().getCellTwo() == null)) {
            gridGraph.wall workingWall = inputCell.getBottomWall();
            if (!(inputList.contains(workingWall))) {
                inputList.add(workingWall);
            } else {
                // do nothing
            }
        }
        if (!(inputCell.getLeftWall().getCellTwo() == null)) {
            gridGraph.wall workingWall = inputCell.getLeftWall();
            if (!(inputList.contains(workingWall))) {
                inputList.add(workingWall);
            } else {
                // do nothing
            }
        }
    }

    public void clearMaze() {
        // method used to reset the application to its initial state
        canvasGc.clearRect(0,0,centerCanvas.getWidth(),centerCanvas.getHeight());
        mazePath.clear();
        drawOutline(canvasGc);
        updateNotificationArea("Maze Cleared");
        //drawGrid(canvasGc);
    }

    public void solveMaze() {
        // this method invokes the AI agent that will solve the maze

        updateNotificationArea("The gridGraph object has been successfully created.");
        System.out.println("Number of Cells: " + graph.getCellsSize());
        System.out.println("Number of Walls: " + graph.getWallsSize());
        System.out.println(randomChoice());
        System.out.println(randomChoice());
    }

    public void showAbout() throws Exception {
        // this method calls the about window and displays the result
        aboutController about = new aboutController();
        about.showWindow();
    }

    public void closeProgram() {
        // this method ensure the program closes appropriately
        Stage activeStage = (Stage) this.borderPane.getScene().getWindow();
        activeStage.close();
    }

    public void setPixelSize5() {
        // used as part of the radio menu selector to set the appropriate pixel size
        this.pixelSize = 5;
        clearMaze();
        //drawGrid(canvasGc);
    }
    public void setPixelSize10() {
        // used as part of the radio menu selector to set the appropriate pixel size
        this.pixelSize = 10;
        clearMaze();
        //drawGrid(canvasGc);
    }
    public void setPixelSize20() {
        // used as part of the radio menu selector to set the appropriate pixel size
        this.pixelSize = 20;
        clearMaze();
        //drawGrid(canvasGc);
    }

    public int getPixelSize() {
        // returns the set pixel size
        return this.pixelSize;
    }

    // Private Methods
    private void updateNotificationArea(String notification) {
        // this method takes a string as input and displays it in the notification text field
        notificationText.setText(notification);
    }

    private int randomChoice() {
        // returns an integer from 0 to 4 at random
        Random output = new Random();
        return output.nextInt(4);
    }

    private void drawMaze(GraphicsContext contextInput, ArrayList<gridGraph.cell> inputMaze) {
        // draws a grid with cell size in pixels, size of pixel can be changed with the parameters given below

        ArrayList<gridGraph.wall> drawnWalls = new ArrayList<>();

        // iterate over each cell in mazePath array
        for (int cellIndex = 0; cellIndex < inputMaze.size(); cellIndex++) {

            // get the particular cell from the mazePath array
            gridGraph.cell workingCell = inputMaze.get(cellIndex);

            // iterate over each wall in the cell
            // top wall
            if (workingCell.getTopWall().getCellTwo() != null && !(workingCell.getTopWall().isPassage())) {
                if (!(drawnWalls.contains(workingCell.getTopWall()))) {
                    // draw the top wall line
                    drawGridLine(canvasGc, workingCell, "top");
                    // add wall to drawn wall list
                    drawnWalls.add(workingCell.getTopWall());
                }
            }
            // right wall
            if (workingCell.getRightWall().getCellTwo() != null && !(workingCell.getRightWall().isPassage())) {
                if (!(drawnWalls.contains(workingCell.getRightWall()))) {
                    drawGridLine(canvasGc, workingCell, "right");
                    drawnWalls.add(workingCell.getRightWall());
                }
            }
            // bottom wall
            if (workingCell.getBottomWall().getCellTwo() != null && !(workingCell.getBottomWall().isPassage())) {
                if (!(drawnWalls.contains(workingCell.getBottomWall()))) {
                    drawGridLine(canvasGc, workingCell, "bottom");
                    drawnWalls.add(workingCell.getBottomWall());
                }
            }
            // left wall
            if (workingCell.getLeftWall().getCellTwo() != null && !(workingCell.getLeftWall().isPassage())) {
                if (!(drawnWalls.contains(workingCell.getLeftWall()))) {
                    drawGridLine(canvasGc, workingCell, "left");
                    drawnWalls.add(workingCell.getLeftWall());
                }
            }
        }
    }

    private void drawGridLine(GraphicsContext inputContext, gridGraph.cell inputCell, String direction) {

        double canvasWidth = inputContext.getCanvas().getWidth();
        double canvasHeight = inputContext.getCanvas().getHeight();
        int pixelSize = this.pixelSize;
        int gridXpos = inputCell.getX()-1;
        int gridYpos = inputCell.getY()-1;
        inputContext.setLineWidth(1.0);
        inputContext.setStroke(Color.BLACK);

        int topLeftXpos = gridXpos * pixelSize;
        int topLeftYpos = gridYpos * pixelSize;

        int topRightXpos = topLeftXpos + pixelSize;
        int topRightYpos = topLeftYpos;

        int bottomLeftXpos = topLeftXpos;
        int bottomLeftYpos = topLeftYpos + pixelSize;

        int bottomRightXpos = topRightXpos;
        int bottomRightYpos = bottomLeftYpos;

        if (direction.equalsIgnoreCase("top")) {
            inputContext.strokeLine(topLeftXpos, topLeftYpos, topRightXpos, topRightYpos);
        }
        if (direction.equalsIgnoreCase("right")) {
            inputContext.strokeLine(topRightXpos, topRightYpos, bottomRightXpos, bottomRightYpos);
        }
        if (direction.equalsIgnoreCase("bottom")) {
            inputContext.strokeLine(bottomRightXpos, bottomRightYpos, bottomLeftXpos, bottomLeftYpos);
        }
        if (direction.equalsIgnoreCase("left")) {
            inputContext.strokeLine(bottomLeftXpos, bottomLeftYpos, topLeftXpos, topLeftYpos);
        }

    }

    private void drawOutline(GraphicsContext context) {
        // this method takes a cell as input and draws it on the canvas at the appropriate coordinates

        // Set the stroke color
        context.setLineWidth(3.0);
        context.setStroke(Color.BLACK);

        context.strokeLine(0, 0, context.getCanvas().getWidth(), 0);
        context.strokeLine(0,0,0, context.getCanvas().getHeight());
        context.strokeLine(context.getCanvas().getWidth(), 0, context.getCanvas().getWidth(), context.getCanvas().getHeight());
        context.strokeLine(0, context.getCanvas().getHeight(), context.getCanvas().getWidth(), context.getCanvas().getHeight());
    }

    private void drawWall(GraphicsContext context, gridGraph.wall inputWall, Color colorInput) {
        // this method takes a wall as input and draws it at the appropriate location on the canvas

        // set the color of the wall and the stroke size
        context.setStroke(colorInput);
        context.setLineWidth(1.0);

        // define the size of the pixel boundaries
        int pixelWidth = this.pixelSize;
        int pixelHeight = this.pixelSize;



        int xPos1 = 0;
        int xPos2 = 0;
        int yPos1 = 0;
        int yPos2 = 0;

        // get the cell end points and load X and Y coordinates
        if (inputWall.getCellOne() != null) {
            xPos1 = inputWall.getCellOne().getX();
            System.out.println(xPos1);
            yPos1 = inputWall.getCellOne().getY();
            System.out.println(yPos1);
        }
        if (inputWall.getCellTwo() != null) {
            xPos2 = inputWall.getCellTwo().getX();
            System.out.println(xPos2);
            yPos2 = inputWall.getCellTwo().getY();
            System.out.println(yPos2);
        }

        if ((xPos1 < xPos2) && !(xPos1 == context.getCanvas().getWidth()/pixelWidth)) {
            // for right walls
            int rightXpos = ((xPos1 - 1) * pixelWidth) + pixelWidth;
            int rightYpos = (yPos1 -1) * pixelHeight;
            int rightYpos2 = rightYpos + pixelHeight;
            context.strokeLine(rightXpos, rightYpos, rightXpos, rightYpos2);

            // for bottom walls
            if (yPos1 != context.getCanvas().getHeight()/pixelHeight) {
                int bottomXpos = (xPos1 - 1) * pixelWidth;
                int bottomYpos = (yPos1 - 1) * pixelHeight + pixelHeight;
                int bottomXpos2 = bottomXpos + pixelWidth;
                context.strokeLine(bottomXpos, bottomYpos, bottomXpos2, bottomYpos);
            }
        }

        if (xPos1 == context.getCanvas().getWidth()/pixelWidth &&
                !(yPos1 == context.getCanvas().getHeight()/pixelHeight || yPos1 == 0)) {
            int bottomXpos = (xPos1 -1 ) * pixelWidth;
            int bottomYpos = (yPos1 - 1) * pixelHeight + pixelHeight;
            int bottomXpos2 = bottomXpos + pixelWidth;
            context.strokeLine(bottomXpos, bottomYpos, bottomXpos2, bottomYpos);
        }

        context.setStroke(Color.RED);
        context.strokeLine(0,0,0,100);
        context.strokeLine(context.getCanvas().getWidth(),0,context.getCanvas().getWidth(),100);
        context.strokeLine(0, context.getCanvas().getHeight(), 100, context.getCanvas().getHeight());

    }

    private void drawPixel(GraphicsContext contextInput, int x, int y, String color) {
        // creates a pixel that is then drawn onto the particular canvas. Pixel size and color can be defined below.

        // Set the color of the pixel
        if (color.equalsIgnoreCase("blue")){
            contextInput.setFill(Color.BLUE);
        } else if (color.equalsIgnoreCase("red")) {
            contextInput.setFill(Color.RED);
        } else if (color.equalsIgnoreCase("green")) {
            contextInput.setFill(Color.GREEN);
        } else {
            contextInput.setFill(Color.BLACK);
        }

        // Define the Size of the pixel
        int pixelHeight = this.pixelSize;
        int pixelWidth = this.pixelSize;

        // Define the maximum dimensions of the intended canvas
        double verticalSize = contextInput.getCanvas().getHeight();
        double horizontalSize = contextInput.getCanvas().getWidth();

        // Determine location of top right hand corner of pixel from input (X,Y)
        int canvasXcoord = (x-1) * pixelWidth;
        int canvasYcoord = (y-1) * pixelHeight;

        // Display error if computed coordinate goes beyond the canvas dimensions
        if ((canvasXcoord > horizontalSize) || (canvasYcoord > verticalSize)) {
            System.out.println("The computed coordinate is beyond the canvas.");
        }

        // Write the actual "pixel" to the canvas
        contextInput.fillRect(canvasXcoord,canvasYcoord,pixelWidth,pixelHeight);
    }

}

