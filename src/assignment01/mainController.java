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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get the Graphics Context of the center canvas
        canvasGc = centerCanvas.getGraphicsContext2D();

        // draw the grid upon startup
        setPixelSize10();
        System.out.println(canvasGc.getCanvas().getHeight());
        System.out.println(canvasGc.getCanvas().getWidth());
    }

    // Public Event Handling Methods
    public void generateMaze() {
        // method used to generate the maze object and display it on the mainCanvas object

    }

    public void clearMaze() {
        // method used to reset the application to its initial state
        canvasGc.clearRect(0,0,centerCanvas.getWidth(),centerCanvas.getHeight());
        //drawGrid(canvasGc);
    }

    public void solveMaze() {
        // this method invokes the AI agent that will solve the maze
        gridGraph graph = new gridGraph((int)canvasGc.getCanvas().getWidth()/this.pixelSize,
                (int)canvasGc.getCanvas().getHeight()/this.pixelSize);
        updateNotificationArea("The gridGraph object has been successfully created.");
        System.out.println("Number of Cells: " + graph.getCellsSize());
        System.out.println("Number of Walls: " + graph.getWallsSize());
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

    private void drawGrid(GraphicsContext contextInput) {
        // draws a grid with cell size of 10x10 pixels, size of pixel can be changed with the parameters given below

        // Define the "pixel" size (in actual pixels)
        int pixelHeight = this.pixelSize;
        int pixelWidth = this.pixelSize;

        // Determine the maximum sizes for the canvas area
        double verticalSize = contextInput.getCanvas().getHeight();
        double horizontalSize = contextInput.getCanvas().getWidth();

        // Set formatting parameters
        contextInput.setLineWidth(0.5);

        // Draw the horizontal lines
        for (int vIndex = 0; vIndex <= verticalSize; vIndex++) {
            if (vIndex%pixelWidth == 0) {
                contextInput.strokeLine(0,vIndex,horizontalSize,vIndex);
            }
        }

        // Draw the vertical lines
        for (int hIndex = 0; hIndex <= horizontalSize; hIndex++) {
            if (hIndex%pixelHeight == 0) {
                contextInput.strokeLine(hIndex,0,hIndex,verticalSize);
            }
        }
        
    }

    private void drawCell(GraphicsContext context, gridGraph.cell inputCell, Color colorInput) {
        // this method takes a cell as input and draws it on the canvas at the appropriate coordinates

        // Set the fill color
        context.setFill(colorInput);

        // define the size of the pixel
        int pixelHeight = this.pixelSize;
        int pixelWidth = this.pixelSize;

        // get coordinates from cell
        int xPos = inputCell.getX();
        int yPos = inputCell.getY();

        // transform cell coordinates to canvas coordinates
        int canvasXpos = (xPos - 1) * pixelWidth;
        int canvasYpos = (yPos - 1) * pixelHeight;

        // draw actual pixel to the canvas
        context.fillRect(canvasXpos, canvasYpos,pixelWidth, pixelHeight);
    }

    int wallCount = 0;

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
            wallCount++;

            // for bottom walls
            if (yPos1 != context.getCanvas().getHeight()/pixelHeight) {
                int bottomXpos = (xPos1 - 1) * pixelWidth;
                int bottomYpos = (yPos1 - 1) * pixelHeight + pixelHeight;
                int bottomXpos2 = bottomXpos + pixelWidth;
                context.strokeLine(bottomXpos, bottomYpos, bottomXpos2, bottomYpos);
                wallCount++;
            }
        }

        if (xPos1 == context.getCanvas().getWidth()/pixelWidth &&
                !(yPos1 == context.getCanvas().getHeight()/pixelHeight || yPos1 == 0)) {
            int bottomXpos = (xPos1 -1 ) * pixelWidth;
            int bottomYpos = (yPos1 - 1) * pixelHeight + pixelHeight;
            int bottomXpos2 = bottomXpos + pixelWidth;
            context.strokeLine(bottomXpos, bottomYpos, bottomXpos2, bottomYpos);
            wallCount++;
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

