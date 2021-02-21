# mazeSolver
The AI agent implemented within this assignment is designed to operate in a specific environment, and complete one task. The agent will solve for a path through a maze, given a starting point, and a goal, or end point. There are four elements to an AI agent: Performance metric(s), Environment, Actuators, and Sensors (or PEAS, as given in the textbook). 

The performance metric provides the agent a framework from which to make decisions. In this instance, we are to solve a maze from an arbitrary starting point. The overall idea behind this task is we want to move closer to the goal. In other words, we want to reduce our distance between us and the goal as much as possible with each step taken in the maze. To accomplish this, the agent uses a straight-line distance between the current location and the goal. When deciding where to move next, the agent picks the location with the shortest distance to the goal. Details on how this distance is implemented and computed are described alongside the Environment the agent is designed to work within. 

The environment, in its most basic sense, is a grid maze with randomly generated paths. This is implemented using a custom gridGraph class. This data structure is based on a graph, where each node of the graph is a “cell” within the grid maze, and each cell has four “walls” which are given as edges between each node. The initial cells and walls are built using the constructor method such that the initial state is a full grid, with each “wall” present around and between each “cell.” Using this initial state, the maze is generated by using a randomized version of Prim’s Algorithm based off the description given in source [1]. This produces a spanning tree that produces “passages” through the “walls” of a “cell” when two are connected through this tree. The effect, when drawn onto the screen, shows a random maze with many short branching corridors. The agent utilizes attributes of each instance of “cell” to determine its state, and which direction to move. The way in which these attributes are examined and implemented is explained subsequently. 

The “actuators” implemented within the agent is the process of moving into a cell closer to the goal cell. The choice is made to move into the cell based on the process described in the performance metric. 

Lastly, the sensors of this agent include the ability to determine if a cell has been visited before, if a wall is a passage or not, and whether or not the current cell is the goal cell. The combination of these sensing abilities gives the agent the proper percept such that the appropriate decisions with the goal in mind is made. 

The way in which the agent works is akin to a greedy algorithm. It makes the best choice based on what is available to it at the moment, though does has some prescience since the location of the goal cell with respect to the current location is known. However, the agent will not know whether a path will result in a dead end until a dead end is reached. Additionally, the agent has issues when the path finding must cross the start cell to begin a new direction. The application handles the error by telling the user to reset the maze and try again. The pseudocode of the algorithm used to implement the agent is given below. Note that straight-line distance is defined as the distance using the Pythagorean theorem, and the differences in the X and Y coordinates between the current cell and the goal cell. This quantity is used to compute the best option and subsequent cell to move forward to.

AI Agent Pseudocode.

	WHILE the current cell is not equal to the goal cell
		Mark the current cell as visited
		Determine all possible moves
		IF there are possible moves available THEN
			Compute all distances
			Move to cell with shortest distance
			PUSH the cell to the path stack
		ELSE
			POP the current cell off the path stack
		END IF
	END WHILE