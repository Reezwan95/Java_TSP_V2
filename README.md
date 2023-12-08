# Java_TSP_V2
Version 2 of the program to solve the Traveling Salesman Problem using Java, that builds upon V1


This program asks the user to enter specific menu options to load a graph from a set of graphs, display the graph, run the nearest neighbor greedy algorithm.


The program also contains error handling for inccorectly loading graphs, non-existent files and results, in addition to the error handling in V1


Implemented using 5 class files:

-Main class file: contains the main function, the global buffered reader object and helper functions

-Node class file: gets and prints the node data to the console

-Graph class file: makes and prints the graph data to the console

-TSPSolver class file: runs the nearest neighbor algorithm on the graphs, calculates the computation time required for each of them, the statistical summary (average, standard deviation, minimum and maximum) of the path cost

-GraphLoader class file: loads the graph files from the command line using the global buffered reader file reader object

The Pro4_samireez.java/class is the main class file

The .txt files are graphs used for testing

