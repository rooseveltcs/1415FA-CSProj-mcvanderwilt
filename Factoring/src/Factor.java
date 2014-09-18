import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// Assignment One 

public class Factor {
	
	public static final int MIN_VALUE = 1;
	public static final int MAX_VALUE = 100;
	
	/* The value that is returned from the parseInt() method when invalid input is passed as a parameter*/
	public static final int RANGE_FAIL_ID = -1;
	/* The value that is returned from parseInt() method when invalid (type) input is passed as a parameter */
	public static final int TYPE_FAIL_ID = -2;
	/* The value that is returned from parseInt() if the user types "quit" (case-insensitive) as the first or second token. */
	public static final int QUIT_ID = -3;
	
	
	public static void main (String[] args){
		
		System.out.println("This program evaluates the first two tokens of input only.\n");
		
		//Declare scanner
		Scanner console = new Scanner(System.in);
		
		boolean run = true;
		
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
				if (input == RANGE_FAIL_ID){
					System.out.println("Error: Integer must be between " + MIN_VALUE + " and " + MAX_VALUE + ".\n");
				} else if (input == TYPE_FAIL_ID) {
					System.out.println("Error: Input must be of type integer. \n");
				} else if (input == QUIT_ID){
					run = false;
				} else {
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
				if (inputA == QUIT_ID || inputB == QUIT_ID){
					run = false;
				} else if (inputA == RANGE_FAIL_ID || inputB == RANGE_FAIL_ID){
					System.out.println("Error: Each integer must be between " + MIN_VALUE + " and " + MAX_VALUE + ".\n");
				} else if (inputA == TYPE_FAIL_ID || inputB == TYPE_FAIL_ID){
					System.out.println("Error: Each input must be of type integer. \n");
				} else {
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
				return RANGE_FAIL_ID;
			}
			return intInput;
		
			//if input is not of type integer
		} catch (NumberFormatException e) {
			if (input.equalsIgnoreCase("quit")) {
				return QUIT_ID;
			} 
			return TYPE_FAIL_ID;
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
