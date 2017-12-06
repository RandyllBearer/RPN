//Java imports
import java.lang.*;
import java.util.ArrayList;
import java.io.*;

//JUnit and Mockito imports
import org.junit.*;
import static org.junit.Assert.*;
import org.mockito.*;

/**
 *
 * This is the test class for Deliverable 6, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del6Test {
	//global
	InputStream standardIn = System.in;
	PrintStream standardOut = System.out;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		System.setIn(standardIn);
		System.setOut(standardOut);
    }
	
	//
	@After
	public void tearDown() {
		System.setIn(standardIn);
		System.setOut(standardOut);
	}
	
	//------------ runLoop() ------------
	
	/*
	* TEST 1
	* Assert that runLoop() will return the proper result of an addition
	* rpn expression.
	*/
	@Test
	public void testRunLoopValidAddition() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		
		// Establish Variable Input
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10".getBytes());
		System.setIn(testIn);
		
		// Comparison strings for setting a variable
		ArrayList<String> testOutput = testRPN.debugRunLoop(1);
		String expectedOutput = "10";
		String observedOutput = testOutput.get(0);
		
		// Establish Addition Command Input
		testIn = new ByteArrayInputStream("A 10 +".getBytes());
		System.setIn(testIn);
		
		// Comparison strings for performing addition
		testOutput = testRPN.debugRunLoop(1);
		String expectedOutput2 = "20";
		String observedOutput2 = testOutput.get(0);
		
		// Assertion for the string comparisons
		assertTrue( expectedOutput.equals(observedOutput) && expectedOutput2.equals(observedOutput2) );
		
	}
	
	/*
	* TEST 2
	* Assert that runLoop() will display the proper error message
	* when there are too many elements left on the stack.
	*/
	@Test
	public void testRunLoopInvalidAddition() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		
		// Establish Variable Input
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10".getBytes());
		System.setIn(testIn);
	
		// Comparison strings for setting a variable
		ArrayList<String> testOutput = testRPN.debugRunLoop(1);
		String expectedOutput = "10";
		String observedOutput = testOutput.get(0);
		
		// Establishing an invalid Addition Command Input
		testIn = new ByteArrayInputStream("A 10 10 +".getBytes());
		System.setIn(testIn);
		
		// Comparison strings for invalid addition
		testOutput = testRPN.debugRunLoop(1);
		String expectedOutput2 = "Line 1: 2 elements in stack after evaluation";
		String observedOutput2 = testOutput.get(0);
		
		// Assertion for the string comparisons
		assertTrue( expectedOutput.equals(observedOutput) && expectedOutput2.equals(observedOutput2) );
		
	}
	
	
	//------------- read() ---------------
	
	
	/*
	* TEST 3
	* Assert that read() returns a properly split array of strings
	* maintaining all characters as uppercase.
	*/
	@Test
	public void testReadUppercase() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		ByteArrayInputStream testIn = new ByteArrayInputStream("LET A 10 20 *".getBytes());
		System.setIn(testIn);
		
		// Comparison string arrays for reading a line
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("", 1);
		
		// Assertion for the string comparisons
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	
	/*
	* TEST 4
	* Assert that read() returns a properly split array of uppercase
	* strings when it reads in lowercase strings.
	*/
	@Test
	public void testReadLowercase() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.resetHashmap();
		ByteArrayInputStream testIn = new ByteArrayInputStream("let a 10 20 *".getBytes());
		System.setIn(testIn);
		
		// Comparison string arrays for reading a line
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("", 1);
		
		// Assertion for the string comparisons
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	
	/*
	* TEST 5
	* Assert that read() returns a properly split array of uppercase
	* strings when it reads in mixedcase strings.
	*/
	@Test
	public void testReadMixedcase() {
		// Create necessary variables
		RPN testRPN = new RPN();
		ByteArrayInputStream testIn = new ByteArrayInputStream("lET a 10 20 *".getBytes());
		System.setIn(testIn);
		
		// Comparison string arrays for reading a line
		String[] expectedOutput = {"LET", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("", 1);
		
		// Assertion for the string comparisons
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	
	/*
	* TEST 6
	* Assert that read() works properly when Mode is set to zero
	* (This means lines were read from a file not user input)
	* The mixedcase string will be used again in this instance
	*/
	@Test
	public void testReadModeZero() {
		// Create necessary variables
		RPN testRPN = new RPN();
		
		// Comparison string arrays for reading a line
		// Mode zero used instead of mode one
		String[] expectedOutput = {"PRINT", "A", "10", "20", "*" };
		String[] observedOutput = testRPN.read("pRiNt a 10 20 *", 0);
		
		// Assertion for the string comparisons
		assertArrayEquals(expectedOutput, observedOutput);
	}
	
	//------------- eval() ----------------
	
	/*
	* TEST 7
	* Assert that when passed a valid division rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidDivision() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "/" };
		
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "20";

		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 8
	* Assert that when passed a valid addition rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidAddition() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "+" };
		
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "105";

		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 9
	* Assert that when passed a valid subtraction rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidSubtraction() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "-" };
		
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "95";

		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	/*
	* TEST 10
	* Assert that when passed a valid multiplication rpn expression,
	* eval() will return the proper result string to be printed
	*/
	@Test
	public void testEvalValidMultiplication() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "*" };
		
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "500";

		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 11
	* Assert that when eval() is passed an rpn expression which
	* includes an uninitialized variable that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidInitialization() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"A", "5", "*" };
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Variable A is not initialized";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 12
	* Assert that when eval() is passed an rpn expression which
	* contains too many operators, that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidEmptyStack() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"5", "5", "*", "/" };
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Operator / applied to empty stack";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 13
	* Assert that when eval() is passed an rpn expression which
	* results in the stack containing more than one element that it
	* returns the proper error string
	*/
	@Test
	public void testEvalInvalidExcessiveStack() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"5", "5", "5","*"};
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: 2 elements in stack after evaluation";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 14
	* Assert that when eval() is passed an rpn expression which
	* contains an invalid keyword, that it returns the proper
	* error string
	*/
	@Test
	public void testEvalInvalidKeyword() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"LOOP"};
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Unknown keyword LOOP";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 15
	* Assert that when eval() is passed an rpn expression which
	* contains a keyword in a non-evaluatable location, that it
	* returns the proper error string.
	*/
	@Test
	public void testEvalInvalidKeywordLocation() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"4", "3", "LET", "+", "a"};
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Could not evaluate expression";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	/*
	* TEST 16
	* Assert that when eval() is passed an rpn expression which
	* contains a keyword in a non-evaluatable location, that it
	* returns the proper error string.
	*/
	@Test
	public void testEvalInvalidInitializationEmpty() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setLineNum(1);
		String[] testArray = {"LET", "A"};
	
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 1);
		String expectedOutput = "Line 1: Operator LET applied to empty stack";
		
		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	/*
	* TEST 17
	* Assert that eval() works properly when Mode is set to zero
	* (This means lines were read from a file not user input)
	* A multiplication command is used to ensure that the method works in both modes
	* The already tested Mode One output is compared to the Mode Zero output to ensure
	* that they are the same.
	*/
	@Test
	public void testEvalModeZero() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArray = {"100", "5", "*" };
		
		// Creating the expect and observed string outputs for the evaluation
		String observedOutput = testRPN.eval(testArray, 0);
		String expectedOutput = testRPN.eval(testArray, 1);

		// Assertion for the string comparisons
		assertEquals(expectedOutput, observedOutput );
	}
	
	
	
	//------------- print() ---------------
	
	
	/*
	* TEST 18
	* Assert that print() will output its given string to the correct
	* system.out stream when errorFlag = false;
	*/
	@Test
	public void testPrintOut() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setErrorFlag(false);
		String testString = "50\n";
		
		// Establish a test program output
		ByteArrayOutputStream testByteOut = new ByteArrayOutputStream();
		PrintStream testOut = new PrintStream(testByteOut);
		System.setOut(testOut);
		
		// Run the print method
		testRPN.print(testString, 1);
		
		// Assert expected matches observed
		assertTrue(testByteOut.toString().contains(testString));
		
	}
	
	
	/*
	* TEST 19
	* Assert that print() will output its given string to the correct
	* system.err stream when errorFlag = true;
	*/
	@Test
	public void testPrintErr() {
		// Create necessary variables
		RPN testRPN = new RPN();
		testRPN.setErrorFlag(true);
		String testString = "Line 1: Unknown keyword LOOP\n";
		
		// Establish a test ERROR output
		ByteArrayOutputStream testErr = new ByteArrayOutputStream();
		System.setErr(new PrintStream(testErr));
		
		// Run the print method
		testRPN.print(testString, 1);
		
		// Assert expected matches observed
		assertTrue(testErr.toString().contains(testString) );
		
	}
	
	//------------ openFile() ----------

	/*
	* TEST 20
	* Assert that openFile() is able to open a valid filepath without error.
	* NOTE: This test requires the appropriate external file!!
	*/
	@Test
	public void testOpenFileValidPath() {
		// Create necessary variables
		RPN testRPN = new RPN();
		BufferedReader inFile = null;
		
		// Try to open the file
		inFile = testRPN.openFile("./test_files/File1.rpn");
		
		// Assert the file was successfuly read
		assertTrue(inFile != null);
	}
	
	/*
	* TEST 21
	* Assert that openFile() will throw an exception when filepath does not exist.
	* NOTE: This test requires the appropriate external file!!
	*/
	@Test
	public void testOpenFileInvalidPath() {
		// Create necessary variables
		RPN testRPN = new RPN();
		BufferedReader inFile = null;

		// Try to open the file
		inFile = testRPN.openFile("./test_files/shouldnt.exist");
		
		// Assert the nonexistant file couldn't be read
		assertTrue(inFile == null);
	}
	
	//-------------- readFiles() ----------
	
	/*
	* TEST 22
	* Assert that readFiles() will return true when its evaluation finishes successfuly
	* NOTE: This test requires the appropriate external file!!
	*/
	@Test
	public void testReadFilesValid() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArguments = new String[1];
		testArguments[0] = "./test_files/File2.rpn";
		
		// Run the file read method
		boolean result = testRPN.readFiles(testArguments);
		
		// Assert the method successfuly evaluated the file
		assertTrue(result);
	}
	
	/*
	* TEST 23
	* Assert that readFiles() will return false when its evaluation comes across an error
	* NOTE: This test requires the appropriate external file!!
	*/
	@Test
	public void testReadFilesInvalid() {
		// Create necessary variables
		RPN testRPN = new RPN();
		String[] testArguments = new String[1];
		testArguments[0] = "./test_files/Bad.rpn";
		
		// Run the file read method
		boolean result = testRPN.readFiles(testArguments);
		
		// Assert the bad file caused an evaluation error
		assertFalse(result);
	}
	
}