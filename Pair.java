// login name: ak750
// surname: Krishna Prasad
// first name: Arjun

import java.util.*;

// this class has been referenced from UoK Prof. Andy King's Intelligent Systems' lecture slides.
// Pair class used for storing the routes taken and their total estimation cost.
// this helps in comparing the route and pick the best one out.
public class Pair implements Comparable<Pair>
{
	private int rank;
	private LinkedList<String> route;
	
	// stores total estimation cost
	public double getRank()
	{
		return rank;
	}
	
	// stores routes of the puzzle
	public LinkedList<String> getRoute()
	{
		return route;
	}
	
	Pair(int rank, LinkedList<String> route)
	{
		this.rank = rank;
		this.route = route;
	}
	
	// compares the total estimation cost of a given pair with the current one.
	public int compareTo(Pair pair)
	{
		if(rank > pair.getRank()) return 1;
		
		else if(rank < pair.getRank()) return -1;
		
		else return 0;
	}
	
	// displays the total estimation cost and the route taken by the given puzzle.
	public String toString()
	{
		return "(" +String.format("%.2f", rank) + ","+route+")";
	}
}
