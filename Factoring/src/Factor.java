import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Assignment One

public class Factor {
	
	/* This determines whether or not the do-while loop runs; must be static because it has to be accessed by main and parseInt() */
	public static boolean run = true;
	
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 100;
	
	/* The value that is returned from the parseInt() method when invalid input is passed as a parameter*/
	public static final int PARSE_FAIL_ID = -1;
	
	public static void main (String[] args){
		//Declare scanner
		Scanner console = new Scanner(System.in);
		
		//While (the user has not typed quit)
		do {
			//Ask for input
			System.out.println("Input up to two integers from " + MIN_VALUE + "-" + MAX_VALUE + ": ");
			
			//Take whole line of input
			String inputLine = console.nextLine();
			
			//Split input into different tokens
			String[] inputs = inputLine.split(" ", 3);//Splits line into three different tokens; the first one to be factored, the second to be gCDed and the third containing the rest of the line to be ignored
			
			//if only one input
			if (inputs.length == 1){
				//Take first token and parse/see if its quit
				int input = parseInt(inputs[0]);
				//FACTOR only if input != -1
				if (input != PARSE_FAIL_ID){
					int[] factors = sortArrList(factor(input));
					//print output
					System.out.println("Factors of " + input + ": " + Arrays.toString(factors)+ "\n");
				}
			
			//if more than one input
			} else if (inputs.length > 1){
				//Take first token and parse/see if it's quit
				int inputA = parseInt(inputs[0]);
				//Take second token parse/see if it's quit
				int inputB = parseInt(inputs[1]);
				//GCD
				if (inputA != PARSE_FAIL_ID && inputB != PARSE_FAIL_ID){
					int gCD = greatestDenom(inputA, inputB);
					//print output
					System.out.println("Greatest Common Denominator of " + inputA + " and " + inputB + ": " + gCD + "\n");
				}
			}
		} while (run);
		
		console.close();
	}
	
	/* Ideally there would be multiple different types of FAIL_ID's that would be error-specific
	 * and ideally nothing would print from this method, however time constraints were considered.
	 *  */
	public static int parseInt (String input){
		//try parsing an integer
		try { 
			int intInput = Integer.parseInt(input);
			//if out of bounds
			if (intInput < MIN_VALUE || intInput > MAX_VALUE){
				System.out.println("Error: Integer must be between " + MIN_VALUE + " and " + MAX_VALUE + ".\n");
				return PARSE_FAIL_ID;
			}
			return intInput;
		//if input is not of type integer
		} catch (NumberFormatException e) {
			if (input.equalsIgnoreCase("quit")) {
				run = false; //Stops running program
			} else {
				System.out.println("Error: Input must be of type integer. \n");
			}
			return PARSE_FAIL_ID;
		}
	}
	
	public static ArrayList<Integer> factor (int input){ 
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for (int i = 1; i <= Math.sqrt(input); i++){
			if (input % i == 0){
				factors.add(i);
				//Accounts for square roots
				if (input / i != Math.sqrt(input))
					factors.add(input / i);
			}
		}
		return factors;
	}
	
	public static int greatestDenom (int inputA, int inputB){
		ArrayList<Integer> factA = factor(inputA);
		ArrayList<Integer> factB = factor(inputB);
		
		factA.retainAll(factB);//removes all factors in A not contained in B
		int[] commonFacs = sortArrList(factA);

		return commonFacs[commonFacs.length - 1];
	} 
	
	private static int[] sortArrList (ArrayList<Integer> arrList) {
		int index = 0;
		int[] sorted = new int[arrList.size()]; 
		for (int i : arrList) {
			sorted[index] = i;
			index++;
		}
		Arrays.sort(sorted);
		return sorted;
	}
}
