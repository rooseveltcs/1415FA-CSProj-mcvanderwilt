import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

// Test for Assignment One 

public class FactorTest {
	
	/* The value that is returned from the parseInt() method when out of range input is passed as a parameter*/
	public static final int RANGE_FAIL_ID = -1;
	/* The value that is returned from parseInt() method when invalid (type) input is passed as a parameter */
	public static final int TYPE_FAIL_ID = -2;
	/* The value that is returned from parseInt() if the user types "quit" (case-insensitive) as the first or second token. */
	public static final int QUIT_ID = -3;
	
	@Test
	/* Tests whether or not the do/while loop continues when "quit" is typed by assessing the 
	 * value of output after the input is parsed with (parseInt() method). QUIT_ID is a static instance field in 
	 * both this class and in the original Factor class. The do while loop only performs when the variable run
	 * is true. When QUIT_ID is returned from parseInt() run is immediately set to false and the do-while loop
	 * quits.
	 * Whether or not quit is the first or second token of input, it is evaluated by parseInt() and will
	 * terminate the program. There was no good way of testing this with JUnit without completely refactoring
	 * my code.
	 */
	public void testQuitInput() {
		//Different cases:
		//Lowercase
		int lowerCaseIn = Factor.parseInt("quit");
		Assert.assertEquals("Program quits running when \"quit\" is typed", QUIT_ID, lowerCaseIn);
		//Uppercase
		int upperCaseIn = Factor.parseInt("QUIT");
		Assert.assertEquals("Program quits running when \"QUIT\" is typed", QUIT_ID, upperCaseIn);
		//Mixed Case
		int mixedCaseIn = Factor.parseInt("qUiT");
		Assert.assertEquals("Program quits running when \"quit\" is typed in a mixed case", QUIT_ID, mixedCaseIn);
	}
	
	@Test
	public void testInputOutOfBounds() {
		//Negative number
		int negativeIn = Factor.parseInt("-1");
		Assert.assertEquals("Negative input is unable to be parsed", RANGE_FAIL_ID, negativeIn);
		//0
		int zeroIn = Factor.parseInt("0");
		Assert.assertEquals("Input of zero is unable to be parsed", RANGE_FAIL_ID, zeroIn);
		//101
		int aboveRangeIn = Factor.parseInt("101");
		Assert.assertEquals("Input of above 100 is unable to be parsed", RANGE_FAIL_ID, aboveRangeIn);
	}
	
	@Test
	public void testInvalidInputType() {
		//String (not quit)
		int stringIn = Factor.parseInt("String");
		Assert.assertEquals("String input is unable to be parsed", TYPE_FAIL_ID, stringIn);
		//Double
		int doubleIn = Factor.parseInt("50.0");
		Assert.assertEquals("Double input (within givin range) is unable to be"
				+ " parsed", TYPE_FAIL_ID, doubleIn);
	}
	
	@Test
	public void testFactorNum() {
		//test correct number of factors
		ArrayList<Integer> factors = Factor.factor(100);
		Assert.assertEquals("Correct number of factors for 100", 9, factors.size());
		ArrayList<Integer> oneFactors = Factor.factor(1);
		Assert.assertEquals("Correct number of factors for 1", 1, oneFactors.size());
	}
	
	@Test
	public void testFactorContents() {
		ArrayList<Integer> factors = Factor.factor(100);
		List<Integer> correctFacts = Arrays.asList(1, 100, 2, 50, 4, 25, 5, 20);
		Assert.assertTrue("All factors of 100 are listed", factors.containsAll(correctFacts));
	}
	
	@Test
	public void testGCD() {
		int gCD = Factor.greatestDenom(50, 100);
		Assert.assertEquals("Correct Greatest Common Denominator of 50 and 100", 50, gCD);
		int gCD2 = Factor.greatestDenom(17, 19);
		Assert.assertEquals("Correct Greatest Common Denominator of 17 and 19", 1, gCD2);

	}
	
}
