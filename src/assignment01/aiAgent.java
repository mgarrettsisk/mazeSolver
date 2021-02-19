package assignment01;

import java.util.ArrayList;
import java.util.LinkedList;

public class aiAgent {

    // attributes

    private gridGraph.cell goalCell;

    private LinkedList<gridGraph.cell> solutionPathStack = new LinkedList<>();
    private ArrayList<gridGraph.cell> possibleMoves = new ArrayList<>();

    // constructor methods

    aiAgent(){
        // null constructor
    }

    aiAgent(gridGraph inputGraph, gridGraph.cell startCell, gridGraph.cell goalCell) {
        // this constructor takes a list of cells on the maze path, along with a start cell and goal cell

        // first, push the start cell onto the solutionPathStack list
        solutionPathStack.push(startCell);

        // second, set the goal cell attribute
        setGoalCell(goalCell);

        // third, extract the mazePath list from the graph object
            // THIS MAY REQUIRE SOME ADDITIONAL THOUGHT. SEE MAINCONTROLLER.JAVA TO SEE HOW THE MAZE PATH IS GIVEN
    }

    // public methods

    public void setGoalCell(gridGraph.cell inputCell) {
        // method sets the attribute for the goal cell
        this.goalCell = inputCell;
    }

    public gridGraph.cell getGoalCell() {
        // returns the cell object of the goal as provided in the input
        return this.goalCell;
    }

    public void setCurrentCell(gridGraph.cell inputCell) {
        // method to set the current cell attribute
        this.solutionPathStack.push(inputCell);
    }

    public gridGraph.cell getCurrentCell() {
        // returns the current cell on which the AI agent is acting
        return this.solutionPathStack.peekLast();
    }

    public void setPreviousCell(gridGraph.cell inputCell) {
        // method to keep track of previous cell. Adds the inputCell to the top of the solutionPathStack
        this.solutionPathStack.push(inputCell);
    }

    public gridGraph.cell getPreviousCell() {
        // method pops the top cell off the solutionPath stack
        return this.solutionPathStack.pop();
    }

    public LinkedList<gridGraph.cell> getSolutionPath() {
        // method returns the list of cells that compose the solution path from start to finish
        return this.solutionPathStack;
    }
    // private methods

    private void determinePossibleMoves(gridGraph.cell inputCell) {
        // this method takes a cell as input, and adds the cells that are possible to move to to the possibleMoves array

        // clear the array first, such that there are no other cells present
        this.possibleMoves.clear();

        // examine each wall and add the neighboring cell to the possible moves list if the wall is a passage
        if (inputCell.getTopWall().isPassage()) {
            this.possibleMoves.add(inputCell.getNeighbors()[0]);
        }
        if (inputCell.getRightWall().isPassage()) {
            this.possibleMoves.add(inputCell.getNeighbors()[1]);
        }
        if (inputCell.getBottomWall().isPassage()) {
            this.possibleMoves.add(inputCell.getNeighbors()[2]);
        }
        if (inputCell.getLeftWall().isPassage()) {
            this.possibleMoves.add(inputCell.getNeighbors()[3]);
        }
    }

    private gridGraph.cell computeBestMove(ArrayList<gridGraph.cell> inputCellList, gridGraph.cell goalCell) {
        // this method takes the current possible moves list, and the goal cell as inputs, and determines which cell
        // should be used next in the path
        int goalXpos = goalCell.getX();
        int goalYpos = goalCell.getY();

        int outputIndex = -1;
        gridGraph.cell outputCell = null;
        double shortestDistance = -1.0;

        // find the index of the cell with the shortest straight line distance to goal
        for (int listIndex = 0; listIndex < inputCellList.size(); listIndex++) {
            // get the current (x,y) coordinates of neighbor cell
            int currentXpos = inputCellList.get(listIndex).getX();
            int currentYpos = inputCellList.get(listIndex).getY();
            // compute the pythagorean distance between the current cell and the goal cell
            double radicand = Math.pow((goalXpos - currentXpos),2) + Math.pow((goalYpos - currentYpos),2);
            double distance = Math.sqrt(radicand);
            if (shortestDistance == -1.0) {
                // this is the first cell, and we set the shortest distance as the distance
                shortestDistance = distance;
                outputIndex = listIndex;
            } else if (distance < shortestDistance) {
                // the newly computed distance is shorter, so set the shortest distance and the output index
                shortestDistance = distance;
                outputIndex = listIndex;
            }
        }
        if (outputIndex == -1) {
            return null;
        } else {
            outputCell = inputCellList.get(outputIndex);
            return outputCell;
        }
    }
}
