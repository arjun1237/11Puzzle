// login name: ak750
// surname: Krishna Prasad
// first name: Arjun

import java.util.LinkedList;

// Project class is the engine for the entire package. 
// This class is connected to Puzzle class.
public class Project 
{
	private Puzzle puzzle; 
	
	// option accepts user input (either 1 or 2).
	private int option;
	
	// enigma contains the .txt files that contains the 11 tile puzzle to be solved.
	private String[] enigma = {"+cd+babd+dd_+bda2+db+bcdd+ada+db_.txt", "+d_+bdba+bdd+adc2+dd+bbdd+aac+_db.txt", 
			"+ca+bdbd+bd_+dad2+ad+bbdd+bcd+ad_.txt", "+bc+bdbd+a_d+add2+bc+bbdd+da_+add.txt",
			"+da+b_dd+bbd+cad2+dd+b_bd+dac+dab.txt", "+ab+bdda+ddc+_bd2+dd+baac+ddd+bb_.txt",
			"+a_+bddc+bad+dbd2+ad+bd_a+bdc+dbd.txt", "+dd+bbdd+c_b+aad2+dd+bcbd+ad_+abd.txt",
			"+ad+bab_+ddc+bdd2+ab+bddc+dad+db_.txt", "+dd+bd_b+aca+bdd2+dd+b_ad+bdd+abc.txt",
			"+dc+bbda+a_b+ddd2+dc+bddd+adb+ba_.txt", "+da+bdac+db_+bdd2+ad+bdca+_bb+ddd.txt",
			"+aa+bcdd+dbd+bd_2+da+bd_d+cda+bdb.txt", "+dd+bcab+dd_+bda2+dd+bcda+db_+bda.txt",
			"+dd+bcad+d_a+bdb2+_d+bcda+bdd+bda.txt", "+bd+b_ad+dbd+dca2+dd+bbd_+aac+bdd.txt"};
	
	public Project(int option)
	{
		this.option = option;
		start(); 
	}
	
	// decodes the puzzle one by one and prints out the route from start to end.
	private void start()
	{				
		for(String problem: enigma){
			String start = problem.substring(0,16);  // stores the starting route of each puzzle.
			String end = problem.substring(17,33);  // stores the ending route of each puzzle.
			
			if(option == 1){
				puzzle = new Puzzle(start, end); // connects to iterative deepening method
			}
			else if(option == 2){
				puzzle = new Puzzle(start, end, true); // connects to aStar method
			}
			LinkedList<String> route = puzzle.getRoute(); // extracts the route traveled from each Puzzle.
			
			int count = 0;
			// looping for printing the route traveled in matrix format.
			while(count<15){
				int routeCount = route.size();
				for(String solution: route){  // iterates through elements of each configurations.
					for(int j=count; j<count+4; j++){
						System.out.print(solution.charAt(j));
					}
					routeCount--;
					if(routeCount != 0)
						System.out.print(" ");
				}
				System.out.println();
				count+=4;
			}
			System.out.println();
		}
	}
}
