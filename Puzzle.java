// login name: ak750
// surname: Krishna Prasad
// first name: Arjun

import java.util.LinkedList;
import java.util.PriorityQueue;

//the class calculates the shortest path between....
//... two given configurations in A* as well as iterative deepening methods.
public class Puzzle 
{
	//stores the starting point of the puzzle.
	private String start;
	// stores ending point of the puzzle.
	private String end;
	// stores the configurations of the most optimal route taken from start to end
	private LinkedList<String> solution;
	int count = 1;
	
	public Puzzle(String start, String end)
	{
		this.start = start;
		this.end = end;
		solution = iterativeDejaVu(start, end); // finds optimal route through iterative deepening method.
	}
	
	
	public Puzzle(String start, String end, boolean temp)
	{
		this.start = start;
		this.end = end;
		solution = aStar(start, end); // finds optimal route through aStar method.
	}
	
	// returns the optimal route stored.
	public LinkedList<String> getRoute()
	{
		return solution;
	}
	
	// the dejaVu method has been referenced from UoK Prof. Andy King's Intelligent Systems' lecture slides.
	// checks for route within that particular depth. if no route found, returns null.
	private LinkedList<String> dejaVu(LinkedList<String> route, String finish, int depth)
	{
		if(depth == 0)	return null;
		String last = route.getLast();  // stores the last configuration in the route taken.
		
		if(last.equals(finish))	return route;
		else{
			LinkedList<String> neighbours = search(last);  // stores the valid neighbors of last configuration
			for(String neighbour: neighbours){
				if(!route.contains(neighbour)){
					LinkedList<String> nextRoute = (LinkedList<String>) route.clone(); // makes copy of route
					nextRoute.add(neighbour);
					LinkedList<String> wholeRoute = dejaVu(nextRoute, finish, depth-1); // recursion for exploring depth					
					if(wholeRoute != null)		return wholeRoute;
				}
			}
			return null;
		}
	}

	// the iterativeDejaVu method has been referenced from UoK Prof. Andy King's Intelligent Systems' lecture slides.
	// iterates depth for dejaVu method until optimal solution is found or returns null.
	public LinkedList<String> iterativeDejaVu(String begin, String finish)
	{
		LinkedList<String> path = new LinkedList<String>();
		path.add(begin);
		for(int depth=1; true; depth++){
			LinkedList<String> route = dejaVu(path, finish, depth); // checks for route with each iteration and stores them.
			if(route != null) 	return route;
		}
	}
	
	// returns all the valid neighbors of the given configurations
	public LinkedList<String> search(String temp)
	{	
		int pos = temp.indexOf('_');		
		LinkedList<String> neighbour = new LinkedList<>();  // creates list to store valid neighbors of the given configuration.
		
		switch((pos/4)+1){  // checks for valid neighbors above and below the configuration.
			case 1:
				if(temp.charAt(pos+4) != '+') 	neighbour.add(move(temp, pos, pos+4));
				break;
			case 2: case 3:
				if(temp.charAt(pos+4) != '+')	neighbour.add(move(temp, pos, pos+4));
				if(temp.charAt(pos-4) != '+')	neighbour.add(move(temp, pos, pos-4));
				break;
			case 4:
				if(temp.charAt(pos-4) != '+')	neighbour.add(move(temp, pos, pos-4));
				break;
		}
		switch((pos%4)+1){    // checks for valid neighbors to the right and left of the configuration.
			case 1:
				if(temp.charAt(pos+1) != '+')	neighbour.add(move(temp, pos, pos+1));
				break;
			case 2: case 3:
				if(temp.charAt(pos+1) != '+')	neighbour.add(move(temp, pos, pos+1));
				if(temp.charAt(pos-1) != '+')	neighbour.add(move(temp, pos, pos-1));
				break;
			case 4:
				if(temp.charAt(pos-1) != '+')	neighbour.add(move(temp, pos, pos-1));
				break;
		}		
		return neighbour;
	}
	
	// prints the given configuration in matrix format
	public void print(String temp)
	{
		for(int i=0; i<temp.length(); i++){
			System.out.print(temp.charAt(i)+" ");
			if((i+1)%4==0){				
				System.out.println();
			}
		}
		System.out.println();
	}
	
	// returns moved configuration with respect to the given one.
	public String move(String temp, int currentPos, int nextPos)
	{
		String temp2 = null;
		String temp3 = null; 
		char current = temp.charAt(currentPos); // stores the char value thats to be swapped from the position.
		char next = temp.charAt(nextPos);  // stores the char value thats to be swapped from the position.
		
		if(currentPos != temp.length()-1)   	
			temp2 = temp.substring(0,currentPos)+""+next+""+temp.substring(currentPos+1);
		else
			temp2 = temp.substring(0,currentPos)+""+next;
		if(nextPos != temp.length()-1)
			temp3 = temp2.substring(0,nextPos)+""+current+""+temp2.substring(nextPos+1);
		else
			temp3 = temp2.substring(0,nextPos)+""+current;
		return temp3;
	}

	// the aStar method has been referenced from UoK Prof. Andy King's Intelligent Systems' lecture slides.
	// returns optimal route from given start and end configuration using aStar method.
	private LinkedList<String> aStar(String begin, String finish)
	{
		LinkedList<String> route = new LinkedList<String>(); // creates list to store route.
		route.add(begin); // adding starting configuration to the route list.
		
		PriorityQueue pairs = new PriorityQueue(); // creates list that stores the total estimation of the cost and the route in increasing order.
		pairs.add(new Pair(estimateDistance(begin, finish), route)); 
		
		while(true){
			
			if(pairs.size() == 0) 	return null;
			Pair pair = (Pair) pairs.poll();  // pops out the first element from the pair list and stores it.			
			
			route = pair.getRoute();
			String last = route.getLast(); // stores the last path taken from the route list.
			
			if(last.equals(finish)) 	return route;
			LinkedList<String> nextConfig = search(last); // stores the valid neighbors of the last route
			for(String next: nextConfig){
				if(!route.contains(next)){
					
					LinkedList<String> nextRoute = new LinkedList<String>(route); // makes copy of the path taken until now.
					nextRoute.addLast(next); // adds one of the neighboring element of the last element of the route to the path list			
					
					int distance = nextRoute.size(); // the exact cost from start to the current point in the path.
					distance += estimateDistance(next, finish);  // heuristic value + cost of path until now (total estimation from start to end).
					
					pairs.add(new Pair(distance, nextRoute));
				}
			}
		}
	}
	
	// 1) a heuristic function is said to be admissible if it never overestimates the cost of reaching the goal, 
	// i.e. the cost it estimates to reach the goal is not higher than the lowest possible cost from the current point in the path.
	// 2) the estimateDistance method returns ("heuristic value") distance estimation of the neighbor configurations 
	// with the least value to the end position ensuring admissibility. 
	public int estimateDistance(String current, String finish)
	{
		LinkedList<String> adjConfig = search(current);  // stores the valid neighbors of the given current configuration.
		int distance1 = 0;
        int distance2 = 0;
        int count = 0;
        
        // checking for neighbor with the least distance to the end position.
        for(String config: adjConfig){
            for(int i=0; i<config.length(); i++){
                if(config.charAt(i) != end.charAt(i) && config.charAt(i) != '_')
                    distance1++;
            }
            count++;
            if(count == 1){
                distance2 = distance1;
                distance1 = 0;
            }
            if(count>1){
                distance2 = Math.min(distance1, distance2);  // storing the least distance of given two.
                distance1 = 0;
            }
        }
		return distance2;
	}  
}
