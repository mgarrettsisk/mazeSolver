package assignment01;

import java.util.ArrayList;

public class gridGraph {

    // Attributes
    ArrayList<cell> cells = new ArrayList<>();
    ArrayList<wall> walls = new ArrayList<>();
    //ArrayList<wall> wallList = new ArrayList<>();

    // Constructor Methods
    gridGraph(int x, int y) {
        // constructor method that creates the data structure
        generateGraphStructure(x, y);
    }

    // Public Methods

    public cell getCell(int index) {
        // returns a cell object given a particular index on the cells ArrayList.
        return cells.get(index);
    }

    public int getCellsSize() {
        // returns the cardinality of the cells set
        return cells.size();
    }

    public int getWallsSize() {
        // returns the cardinality of the walls set
        return walls.size();
    }


    // Private Methods

    private void generateGraphStructure(int xSize, int ySize) {
        /* this method takes a 2D size parameter (as two separate integer values) and populates the data structure with
            the following arrangement:
                Each "pixel" in the graph structure is a cell. Each cell has at most four walls. To generate every
                "pixel" we must iterate over the size of the canvas given as inputs to this method.
         */
        for (int yIndex = 1; yIndex <= ySize; yIndex++) {
            for (int xIndex = 1; xIndex <= xSize; xIndex++) {
                int[] currentPosition = {xIndex, yIndex};
                cells.add(new cell(currentPosition));
            }
        }
        /*
            The next step in the creation is to generate all the appropriate walls in the grid. There are four walls per
            cell, however the method will place only the right and bottom walls by default. This leaves the outside and
            corner cells requiring special consideration. This arrangement will avoid creation of duplicates and will
            prevent needing to run a search for a particular wall to avoid duplicates.
         */
            for (int listIndex = 0; listIndex < cells.size(); listIndex++) {
                cell workingCell = cells.get(listIndex);
                int cellX = workingCell.getX();
                int cellY = workingCell.getY();
                // Determine special cases and work as appropriate.
                if (cellX == 1 && cellY == 1) {
                    // Create four walls on the top left cell
                    walls.add(new wall(workingCell,null)); // left
                    walls.add(new wall(workingCell, null)); // top
                    walls.add(new wall(workingCell, cells.get(listIndex+1))); // right
                    walls.add(new wall(workingCell, cells.get(listIndex+xSize))); // bottom
                } else if (cellX != 1 && cellX != xSize && cellY == 1) {
                    // Create three walls for all cells on top row
                    walls.add(new wall(workingCell, null)); // top
                    walls.add(new wall(workingCell, cells.get(listIndex+1))); // right
                    walls.add(new wall(workingCell, cells.get(listIndex+xSize))); // bottom
                } else if (cellX == xSize && cellY == 1) {
                    // Create three walls for top right cell
                    walls.add(new wall(workingCell, null)); // top
                    walls.add(new wall(workingCell,null)); // right
                    walls.add(new wall(workingCell, cells.get(listIndex+xSize))); // bottom
                } else if (cellX == 1 && cellY !=ySize) {
                    // add three walls to each cell on the left column
                    walls.add(new wall(workingCell, null));// left
                    walls.add(new wall(workingCell, cells.get(listIndex+1)));// right
                    walls.add(new wall(workingCell, cells.get(listIndex+50)));// bottom
                } else if (cellX == xSize && cellY != 1 && cellY != ySize) {
                    // add walls to all cells on the right column
                    walls.add(new wall(workingCell, null));// right
                    walls.add(new wall(workingCell, cells.get(listIndex+1)));// bottom
                } else if (cellX == 1 && cellY == ySize) {
                    // add walls to bottom left corner
                    walls.add(new wall(workingCell, null)); // left
                    walls.add(new wall(workingCell, cells.get(listIndex+1))); // right
                    walls.add(new wall(workingCell, null));// bottom
                } else if (cellX !=1 && cellX != xSize && cellY == ySize) {
                    // add walls to bottom row cells
                    walls.add(new wall(workingCell, cells.get(listIndex+1))); // right
                    walls.add(new wall(workingCell, null));// bottom
                } else if (cellX == xSize && cellY == ySize) {
                    // create two walls on bottom right cell
                    walls.add(new wall(workingCell, null));
                    walls.add(new wall(workingCell, null));
                } else {
                    // fill all the interior cells with a right and a bottom wall
                    walls.add(new wall(workingCell, cells.get(listIndex+1))); // right
                    walls.add(new wall(workingCell, cells.get(listIndex+xSize))); // bottom
                }

            }
        }

    // Internal Classes

    public class cell {

        // Attributes
        private int[] position;
        private int visitCount = 0;

        // Methods

        cell(int[] coordinates) {
            // sets the coordinates based on the input array. Once set, this cannot be changed from outside the object
            // scope.
            this.setPosition(coordinates);
        }

        private void setPosition(int[] orderedPair) {
            // this method is used solely in the constructor method as the position of individual cells should not
            // change after creation
            this.position = orderedPair;
        }

        protected int[] getPosition() {
            // returns the array containing the coordinates of the cell
            return this.position;
        }

        protected int getX() {
            // returns only the X coordinate of the cell
            return this.position[0];
        }

        protected int getY() {
            // returns only the Y coordinate of the cell
            return this.position[1];
        }

        protected void visit() {
            // increases the visit count by 1 every time the method is called
            this.visitCount++;
        }

        protected int getVisitCount() {
            // returns the visit count when called
            return this.visitCount;
        }

        @Override
        public boolean equals(Object obj) {
            if (this.getX() == ((cell)obj).getX() && this.getY() == ((cell)obj).getY()) {
                return true;
            } else {
                return false;
            }
        }
    }

    public class wall {

        // Attributes
        private final cell cellOne;
        private final cell cellTwo;
        private boolean passage = false;

        // Methods
        wall(cell cellOne, cell cellTwo) {
            // constructor method assigns each cell to an end of an edge. These two cells exist on each side of the
            // "wall" and are divided by this object
            this.cellOne = cellOne;
            this.cellTwo = cellTwo;
        }

        public cell getCellOne() {
            // returns the first cell in the edge (or wall, such as it is)
            return this.cellOne;
        }

        public cell getCellTwo() {
            // returns the second cell in the edge (or wall)
            return this.cellTwo;
        }

        public void setPassage(boolean tf) {
            // sets the passage parameter in the wall, if the wall is meant to be "knocked down" use this to specify
            this.passage = tf;
        }

        public boolean isPassage() {
            // returns the value that determines whether this edge is a passage or not. The default is 'false' which
            // indicates this wall is non-passable.
            return this.passage;
        }

        @Override
        public boolean equals(Object obj) {
            if ((this.getCellOne().equals(((wall)obj).getCellOne()) || this.getCellOne().equals(((wall)obj).getCellTwo())) &&
                    (this.getCellTwo().equals(((wall)obj).getCellOne()) || this.getCellTwo().equals(((wall)obj).getCellTwo()))) {
                return true;
            } else {
                return false;
            }
        }
    }
}
