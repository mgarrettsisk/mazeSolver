package assignment01;

import java.util.ArrayList;

public class primsMaze {

    // Attributes
    ArrayList<cell> cells = new ArrayList<>();
    ArrayList<wall> walls = new ArrayList<>();
    ArrayList<wall> wallList = new ArrayList<>();

    // Methods

    // Constructor Methods
    primsMaze(int x, int y) {
        generateGraphStructure(x, y);
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
                int[] currentPosition = {xSize, ySize};
                cells.add(new cell(currentPosition));
            }
        }
        /*
            The next step in the creation is to generate all the appropriate walls in the
         */
    }
    // Internal Classes

    private class cell {

        // Attributes
        private int[] position = {-1, -1};
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

    private class wall {

        // Attributes
        private cell cellOne;
        private cell cellTwo;

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
