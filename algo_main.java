// login name: ak750
// surname: Krishna Prasad
// first name: Arjun

import java.util.Scanner;


// Main class
public class algo_main
{
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);   
		System.out.println("Press 1 for Iterative Deepening method or 2 for A Star method.");
		String s = in.nextLine(); // captures user input
		if(s.equals("1")){
			Project start = new Project(1);  // for iterative Deepening method
		}
		else if(s.equals("2")){
			Project start = new Project(2);  // for A star method
		}
		else{
			System.out.println("Wrong input. Run again!");
		}		
	}	
}