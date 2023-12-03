import java.util.ArrayList;
import java.util.Arrays;

public class TSPSolver {
	

	private ArrayList<Graph> graph;
	private ArrayList<int[]> solnPath;
	private double[] solnCost;
	private double[] compTime;
	private boolean[] solnFound;
	private boolean resultsExist;
	
	//constructors
	public TSPSolver() {
		
	}
	
	public TSPSolver(ArrayList<Graph> G) {
		solnPath = new ArrayList<>();
	    solnCost = new double[G.size()];
	    compTime = new double[G.size()];
	    solnFound = new boolean[G.size()];
	    Arrays.fill(solnCost, -1); //initialize with -1 to indicate no solution
	    Arrays.fill(compTime, -1); //initialize with -1 to indicate no computation
	    Arrays.fill(solnFound, false); //initialize with false to indicate no solution found
	    init(G);
	}
	
	//getters
	public int[] getSolnPath(int i) {
		//to check index validity
		if(i >= 0 && i < solnPath.size()) {
			//returning soln path for the index i
			return solnPath.get(i);
		} else {
			return null;
		}
	}
	
	public double getSolnCost(int i) {
		//to check index validity
		if(i >= 0 && i < solnCost.length) {
			//returning soln cost for the index i
			return solnCost[i];
		} else {
			return -1;
		}
		
	}
	public double getCompTime(int i) {
		//to check index validity
		if(i >= 0 && i < compTime.length) {
			//returning computation time for index i
			return compTime[i];
		} else {
			return 0.0;
		}
		
	}
	public boolean getSolnFound(int i) {
		//to check index validity
		if(i >= 0 && i < solnFound.length) {
			//returning soln for index i
			return solnFound[i];
		} else {
			return false;
		}
		
	}
	public boolean hasResults(){
		//to check whether the ArrayList is empty or not
		return resultsExist;
	}
	
	//setters
	public void setSolnPath(int i, int[] solnPath){
		if(i >= 0 && i < this.solnPath.size()) {
			this.solnPath.set(i, solnPath);
		}
		
	}
	public void setSolnCost(int i, double solnCost){
		if(i >= 0 && i < this.solnCost.length) {
	        this.solnCost[i] = solnCost; //set solution cost for graph i
	    }
	}
	
	public void setCompTime(int i, double compTime){
		if(i >= 0 && i < this.compTime.length) {
	        this.compTime[i] = compTime; //set computation time for graph i
	    }
	}
	
	public void setSolnFound(int i, boolean solnFound){
		if(i >= 0 && i < this.solnFound.length) {
	        this.solnFound[i] = solnFound; //set solution found flag for graph i
	    }
	}
	
	public void setHasResults(boolean b){
		//setting results
		resultsExist = b;
	}
	
	public int getSolnFoundLength() {
	    return solnFound.length;
	}
	
	public void init(ArrayList<Graph> G){
		//initialize the ArrayList of Graphs
		 graph = new ArrayList<>(G);
	     solnPath = new ArrayList<>();
	     solnCost = new double[G.size()];
	     compTime = new double[G.size()]; 
	     solnFound = new boolean[G.size()]; 
	     
	    }
	
	
	public void reset(){
		//resetting the graph
		graph.clear();
	}
	
	public void run(ArrayList<Graph> G, int i) {
	    if (i >= 0 && i < G.size()) {
	        Graph graph = G.get(i);

	        long start = System.currentTimeMillis();
	        int[] solutionPath = solveTSP(graph);
	        long elapsedTime = System.currentTimeMillis() - start;

	        if (solutionPath != null) {
	            if (i < solnPath.size()) {
	                solnPath.set(i, solutionPath); // Replace existing element
	            } else {
	                solnPath.add(solutionPath); // Add new element
	            }
	            solnCost[i] = graph.pathCost(solutionPath);
	            compTime[i] = elapsedTime;
	            solnFound[i] = true;
	            
	            System.out.println("Graph " + (i + 1) + " done in " + compTime[i] + "ms.");
	            
	        } else {
	            solnFound[i] = false;
	            System.out.println("ERROR: No TSP route found for Graph " + (i + 1) + "!");
	            
	        }
	    } else {
	        System.out.printf("ERROR: Input must be an integer in [0,%d]%n", G.size() - 1);
	        System.out.println();
	    }
	    
	    //System.out.println();
	}

	
	public void runSingleGraph(ArrayList<Graph> G, Graph graph) {
		int index = 0;
		run(G, index);
	}
	
	private int[] solveTSP(Graph graph) {
	    int n = graph.getN(); //get the number of nodes in the graph
	    boolean[] visited = new boolean[n]; //track visited nodes
	    int[] path = new int[n + 1]; //path including the return to the starting node

	    int currentCity = 0; //start from the first city (index 0)
	    visited[currentCity] = true; //mark the start city as visited
	    path[0] = currentCity; //start the path with the first city

	    for (int i = 1; i < n; i++) {
	        int nearestNeighbor = -1;
	        double nearestDistance = Double.MAX_VALUE;

	        for (int j = 0; j < n; j++) {
	            if (!visited[j] && graph.isArcPresent(currentCity, j)) {
	                Double distance = graph.getCost(currentCity, j);
	            	if (nearestDistance > distance) {
	                nearestNeighbor = j;
	            	nearestDistance = distance;
	            	}
	            }
	        }

	        if (nearestNeighbor == -1) {
	            return null; //exit if there are no more unvisited neighbors
	        }
	        

	        visited[nearestNeighbor] = true; //mark the nearest city as visited
	        currentCity = nearestNeighbor; //move to the nearest city
	        path[i] = currentCity; //add city to the path
	    }

	    //check if there is a path back to the start city
	    if (!graph.isArcPresent(currentCity, 0)) {
	        return null; //exit if there's no path back to the start city
	    }

	    path[n] = 0; //complete the loop by returning to the start city
	    return path; //return the found path
	}



	
	
	public int nearestNeighbor(Graph G, int city, boolean[] visited) {
		int n = G.getN(); 
		int nearestNode = -1;
		double minDist = Double.MAX_VALUE;
		
		for(int i = 0; i < n; i++) {
			if(!visited[i] && i != city) {
				double dist = G.getCost(city, i);
				
				if(dist < minDist) {
					minDist = dist;
					nearestNode = i;
				}
			}
		}
		
		return nearestNode;
	}

	
	public void printSingleResult(int i, boolean rowOnly) {
	    if (i >= 0 && i < graph.size()) {
	        System.out.printf("%3d", i + 1);
	        if (i < solnFound.length && solnFound[i]) {
	            // Print cost and computation time if available
	            String costStr = (i < solnCost.length) ? String.format("%15.2f", solnCost[i]) : String.format("%15s", "-");
	            String timeStr = (i < compTime.length) ? String.format("%15.3f", compTime[i]) : String.format("%15s", "-");
	            System.out.print(costStr + timeStr);

	            // Print the route if not rowOnly and solution path exists
	            if (!rowOnly && i < solnPath.size() && solnPath.get(i) != null) {
	                String formattedPath = formatSolutionPath(solnPath.get(i));
	                System.out.printf("%25s", formattedPath);
	            } else if (!rowOnly) {
	                System.out.printf("%25s", "-");
	            }
	        } else {
	            // Print dashes if no solution found or index out of bounds
	            System.out.printf("%15s%15s", "-", "-");
	            if (!rowOnly) {
	                System.out.printf("%25s", "-");
	            }
	        }
	        System.out.println();
	    } else {
	        System.out.println("Invalid graph index: " + i);
	    }
	}



	private String formatSolutionPath(int[] solutionPath) {
	    if (solutionPath == null || solutionPath.length == 0) {
	        return "-";
	    }
	    
	    StringBuilder pathBuilder = new StringBuilder();
	    for (int i = 0; i < solutionPath.length; i++) {
	        pathBuilder.append(solutionPath[i] + 1); // Convert city index to city number
	        if (i < solutionPath.length - 1) {
	            pathBuilder.append("-");
	        }
	    }
	    return pathBuilder.toString();
	}



	
	public void printAll(ArrayList<Graph> graph){
		for(Graph graphPrintAll : graph){
			graphPrintAll.print();
			System.out.println();
		}
	}
	public void printStats() {
	    //check if the arrays have been initialized and have data
	    if (solnCost.length == 0 || compTime.length == 0) {
	        System.out.println("No data available to display stats.");
	        return;
	    }

	    System.out.println("\nStatistical summary:");
	    System.out.println("--------------------------------------------");
	    System.out.printf("%-15s%-15s%-15s%n", " ", "Cost (km)", "Comp time (ms)");
	    System.out.println("--------------------------------------------");

	    double totalCost = 0.0;
	    double totalTime = 0.0;
	    double minCost = Double.MAX_VALUE;
	    double maxCost = 0;
	    double minTime = Double.MAX_VALUE;
	    double maxTime = 0;
	    int successfulSolutions = 0;

	    for (int i = 0; i < solnCost.length; i++) {
	        if (solnFound[i]) {
	            totalCost += solnCost[i];
	            totalTime += compTime[i];
	            minCost = Math.min(minCost, solnCost[i]);
	            maxCost = Math.max(maxCost, solnCost[i]);
	            minTime = Math.min(minTime, compTime[i]);
	            maxTime = Math.max(maxTime, compTime[i]);
	            successfulSolutions++;
	        }
	    }

	    double avgCost = successfulSolutions > 0 ? totalCost / successfulSolutions : 0;
	    double avgTime = successfulSolutions > 0 ? totalTime / successfulSolutions : 0;
	    double stDevCost = calculateStandardDeviation(solnCost, avgCost, successfulSolutions);
	    double stDevTime = calculateStandardDeviation(compTime, avgTime, successfulSolutions);
	    double successRate = successRate();
	    System.out.printf("%-15s%15.2f%15.2f%n", "Average", avgCost, avgTime);
	    System.out.printf("%-15s%15.2f%15.2f%n",  "St Dev", stDevCost, stDevTime);
	    System.out.printf("%-15s%15.2f%15.2f%n",  "Min", minCost, minTime);
	    System.out.printf("%-15s%15.2f%15.2f%n",  "Max", maxCost, maxTime);


	    System.out.println();
	    System.out.printf("Success rate: %.1f%%%n", successRate);
	    System.out.println();
	}



	private double calculateStandardDeviation(double[] values, double average, int count) {
	    if (count < 2) {
	    	return Double.NaN;
	    }

	    double sumSquareDiff = 0.0;
	    for (int i = 0; i < values.length; i++) {
	        if (solnFound[i]) {
	            sumSquareDiff += Math.pow(values[i] - average, 2);
	        }
	    }

	    return Math.sqrt(sumSquareDiff / (count - 1));
	}

	
	
	public double calcAvg(double[] values) {
	    double sum = 0.0;
	    int count = 0;
	    for (int i = 0; i < values.length; i++) {
	        if (solnFound[i]) {
	            sum += values[i];
	            count++;
	        }
	    }
	    return count > 0 ? sum / count : 0.0;
	}
	
	public String formatRoute(int[] path) {
	    if (path == null || path.length == 0) {
	        return "-";
	    }
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < path.length; i++) {
	        sb.append(path[i] + 1); //add 1 to index to match the city number
	        if (i < path.length - 1) {
	            sb.append("-"); //separator between cities
	        }
	    }
	    return sb.toString();
	}

	
	public double successRate() {
	    if (solnFound.length == 0) {
	        return 0.0;
	    }

	    int totalGraphs = solnFound.length;
	    int successfulGraphs = 0;

	    for (boolean found : solnFound) {
	        if (found) {
	            successfulGraphs++;
	        }
	    }

	    return ((double) successfulGraphs / totalGraphs) * 100;
	}


}
	
	
	
	