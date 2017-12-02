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
 * This is the test class for Deliverable 5, CS1632 Fall 2017
 * Authors: Randyll Bearer (github: rlb97) and Branden Keck (github: BrandenKeck)
 *
 */

public class Del6Test {
	//global
	RPN testRPN = null;
	InputStream standardIn = System.in;
	PrintStream standardOut = System.out;
	
	// Create a new Element instance before testing
    @Before
    public void setup() {
		RPN testRPN = new RPN();
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
	//
	@Test
	public void testValidVariableInitialization1() {
		ByteArrayInputStream testIn = new ByteArrayInputStream("Let a 10 20 +".getBytes());
		ByteArrayOutputStream testOut = new ByteArrayOutputStream();
		System.setIn(testIn);
		System.setOut(new PrintStream(testOut));
		
		ArrayList<String> testOutput = testRPN.debugRunLoop(1);
		
		String expectedOutput = "30";
		String observedOutput = testOutput.get(0);

		assertEquals(expectedOutput, observedOutput );
		
	}
	
	//------------- read() ---------------
	//
	
	@Test
	public void test2() {
		
		
		assertTrue(true);
	}
	
	/*
	//------------ readFiles() ----------
	//
	@Test
	public void test0() {
		
	}
	
	//------------- eval() ----------------
	//
	@Test
	public void test3() {
		
	}
	
	//------------- print() ---------------
	//
	@Test
	public void test4() {
		
	}
	*/
}