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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get the Graphics Context of the center canvas
        canvasGc = centerCanvas.getGraphicsContext2D();

        // draw the grid upon startup
        drawGrid(canvasGc);
        System.out.println(canvasGc.getCanvas().getHeight());
        System.out.println(canvasGc.getCanvas().getWidth());
    }

    // Public Event Handling Methods
    public void generateMaze() {
        // method used to generate the maze object and display it on the mainCanvas object
        canvasGraph graph = new canvasGraph(50,50);
    }

    public void clearMaze() {
        // method used to reset the application to its initial state
        canvasGc.clearRect(0,0,centerCanvas.getWidth(),centerCanvas.getHeight());
        drawGrid(canvasGc);
    }

    public void solveMaze() {
        // this method invokes the AI agent that will solve the maze
        for (int xIndex = 1; xIndex <= 50; xIndex++) {
            for (int yIndex = 1; yIndex <=50; yIndex++) {
                if (xIndex%2 == 0 && yIndex%2 != 0) {
                    drawPixel(canvasGc,xIndex,yIndex,"green");
                } else if (xIndex%2 != 0 && yIndex%2 == 0) {
                    drawPixel(canvasGc,xIndex,yIndex,"blue");
                } else if (xIndex%2 != 0 && yIndex%2 != 0) {
                    drawPixel(canvasGc,xIndex,yIndex,"red");
                }
            }
        }
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

    // Private Methods
    private void updateNotificationArea(String notification) {
        // this method takes a string as input and displays it in the notification text field
        notificationText.setText(notification);
    }

    private void drawGrid(GraphicsContext contextInput) {
        // draws a grid with cell size of 10x10 pixels, size of pixel can be changed with the parameters given below

        // Define the "pixel" size (in actual pixels)
        int pixelHeight = 10;
        int pixelWidth = 10;

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
        int pixelHeight = 10;
        int pixelWidth = 10;

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

