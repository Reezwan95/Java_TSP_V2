import java.io.*;
import java.util.ArrayList;

public class Pro4_samireez {
	
	public static BufferedReader jIn = new BufferedReader(new InputStreamReader(System.in));
	
	public static Graph G = new Graph();
	public static Node node = new Node();
	public static TSPSolver tspSolver = new TSPSolver();

	
	public static void main(String[] args) throws NumberFormatException, IOException{
		
		ArrayList<Graph> graphList = new ArrayList<>();
		
		//menu options using a while loop and a series of switch-breaks
				while(true) {
					//shows menu options
					displayMenu();
					String choice = jIn.readLine().toUpperCase();
					
					switch (choice) {
					  
					//loads the graph
					case "L":
						System.out.println("");
						loadGraph(graphList);
						tspSolver.init(graphList);
						break;
					
					//display graph info
					case "I":
						System.out.println("");
						graphInfo(graphList);
						break;
					
						//clears all graphs
					case "C":
						System.out.println("");
						clearGraph();
						System.out.println();
						break;
					
						//run NN algo
					case "R":
						System.out.println("");
						nearestNeighborAlgo(graphList);
						break;
					
					//display algo performance
					case "D":
						System.out.println("");
						algoPerformance(graphList);
						break;
					
					//quits the program
					case "Q":
						System.out.println();
						System.out.print("Ciao!");
						System.exit(0);
					
					default:
						System.out.println();
						System.out.print("ERROR: Invalid menu choice!");
						System.out.println();
						System.out.println();
					}			
				}	

	}
	
	
	public static void displayMenu() {

		System.out.println(" " + " " + " " + "JAVA TRAVELING SALESMAN PROBLEM V2");
		System.out.println("L - Load graphs from file");
		System.out.println("I - Display graph info");
		System.out.println("C - Clear all graphs");
		System.out.println("R - Run nearest neighbor algorithm");
		System.out.println("D - Display algorithm performance");
		System.out.println("Q - Quit");
		System.out.println();
		System.out.printf("Enter choice: ");
	}
	
	public static void loadGraph(ArrayList<Graph> G) throws IOException, NumberFormatException{
			
				System.out.print("Enter file name (0 to cancel): ");
				String fileName = jIn.readLine();
				System.out.println();
				
				if("0".equals(fileName)) {
					
					System.out.println("File loading process canceled.");
					System.out.println();
					return;
				}
				
			try{
			
				BufferedReader fIn = new BufferedReader(new FileReader(fileName));
				ArrayList<Graph> graph = GraphLoader.loadGraph(fIn);
				
				if (!graph.isEmpty()){
					G.clear();
					G.addAll(graph);
					System.out.println();
				} else {
					System.out.println("ERROR: No graphs found in the file!");
					System.out.println();
				}
			} catch (FileNotFoundException e) {
				System.out.println("ERROR: File not found!");
				System.out.println();
			}
		}

	
	
	public static void graphInfo(ArrayList<Graph> graphList) throws IOException, NumberFormatException {
		printGraphSummary(graphList);

        while (true) {
            System.out.print("Enter graph to see details (0 to quit): ");
            String input = jIn.readLine().trim().toUpperCase();

            if (input.equals("0")) {
            	System.out.println();
                break;
            }

            try {
                int graphIndex = Integer.parseInt(input);

                if (graphIndex >= 1 && graphIndex <= graphList.size()) {
                    printDetails(graphList.get(graphIndex - 1), graphList);
                } else {
                	
                    System.out.println("ERROR: Input must be an integer in [0, " + graphList.size() + "]!");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
            	
                System.out.println("ERROR: Input must be an integer in [0, " + graphList.size() + "]!");
                System.out.println();
            }
        }
	}
	
	private static void printGraphSummary(ArrayList<Graph> graphList) {
	    System.out.println("GRAPH SUMMARY");
	    System.out.printf("%-3s%10s%10s","No.","# nodes","# arcs");
	    System.out.println();
	    System.out.println("------------------------");

	    //iterate over each graph in the list and print its summary
	    for (int i = 0; i < graphList.size(); i++) {
	        Graph graph = graphList.get(i);
	        System.out.printf("%3d%10d%10d%n", i + 1, graph.getN(), graph.getM());
	    }
	    
	    System.out.println();
	}
	
	public static void printDetails(Graph graph, ArrayList<Graph> graphList) {
	    System.out.println();
        System.out.println("Number of nodes: " + graph.getN());
        System.out.println("Number of arcs: " + graph.getM());

	    System.out.println();
        graph.printNodesAndArcs();
        System.out.println();
        printGraphSummary(graphList);
    }

	
	public static void clearGraph() {
		G.reset();
		System.out.print("All graphs cleared.");
		System.out.println();
	}
	
	public static void nearestNeighborAlgo(ArrayList<Graph> graphList) {
	    if (graphList.isEmpty()) {
	        System.out.println("ERROR: No graphs have been loaded!");
	        return;
	    }

	    for (int i = 0; i < graphList.size(); i++) {
	        Graph graph = graphList.get(i);
	        tspSolver.run(graphList, i); //running the TSP solver on graph i
	        
	    }
	    
	    System.out.println();
	    System.out.println("All graphs done.");
	    System.out.println();
	}

	
	public static void algoPerformance(ArrayList<Graph> graphList) {
		
		boolean resultsExist = false;

	    //check if any solutions have been found
	    for (int i = 0; i < tspSolver.getSolnFoundLength(); i++) {
	        if (tspSolver.getSolnFound(i)) {
	            resultsExist = true;
	            break;
	        }
	    }

	    //display error message if no results exist
	    if (!resultsExist) {
	        System.out.println("ERROR: No results exist!");
	        System.out.println();  //adding a new line for cleaner output
	        return;  //return to exit the method
	    }

	    //display detailed results
	    System.out.println("Detailed results:");
	    System.out.println("--------------------------------------------");
	    System.out.printf("%s%15s%15s%15s","No.","Cost (Km)","Comp time (ms)","Route");
	    System.out.println();
	    System.out.println("--------------------------------------------");
	    for (int i = 0; i < graphList.size(); i++) {
	        tspSolver.printSingleResult(i, false); //true for rowOnly, if you want to display the route in a single row
	    }
	    
	    //display statistical summary
	    tspSolver.printStats(); //this will calculate and print the stats
	}
	
	public static double getDouble(String prompt, double LB, double UB) throws IOException{
		
		double value = 0;
		boolean valid;
		
		do {
			try {
				valid = true;
				System.out.print(prompt);
				String input = jIn.readLine();
				value = Double.parseDouble(input);
				
				if (value >= LB && value <= UB) {
					break;
				} else {
	                throw new NumberFormatException();
	            }
			}
			catch (NumberFormatException e) {
				valid = false;
				System.out.printf("ERROR: Input must be a real number in [%.1f, %.1f]!", LB, UB);
                System.out.println("");
			}
		} while (!valid);
		
		return value;	
	}
	
	
	public static int getInteger(String prompt, int LB, int UB) throws IOException{
		
		int value = 0;
		boolean valid;
		
		do {
			try {
				valid = true;
				System.out.print(prompt);
				String input = jIn.readLine();	
				value = Integer.parseInt(input);
				
				if (value >= LB && value <= UB) {
					break;				
				} else {
	                throw new NumberFormatException();
	            }
			}catch (NumberFormatException e){
				valid = false;
				System.out.printf("ERROR: Input must be an integer in [%d, %d]!\n", LB, UB);
				System.out.println("");
			}
		} while (!valid);
		
		return value;

	}
	

}
