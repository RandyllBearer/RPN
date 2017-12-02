import java.math.BigInteger;	//https://docs.oracle.com/javase/7/docs/api/java/math/BigInteger.html
import java.util.*;	

/*
*
*/
public class RPN {
	//https://docs.oracle.com/javase/7/docs/api/java/util/HashMap.html
	private static HashMap<String, BigInteger> variables = new HashMap<String, BigInteger>();	
	private static int lineNum = 0;
	private static boolean errorFlag = false;
	
	//------------ functional methods ----------------
	
	/*
	* Concatenate all file paths into 1 string
	* read() / eval() each string
	* Mode = 0
	*/
	public static void readFiles() {
		
		
	}

	/*
	* Read(), Eval(), Print()
	* Break out of loop if "QUIT" is consumed
	*/
	public static void runLoop() {
		while (true) {
			
			String[] resultTokens = read(1);
			
			String resultString = "";
			if (resultTokens.length > 0 ) {
				resultString = eval(resultTokens, 1);
			}
			if (resultString.equals("QUIT")) {
				break;
			}
			
			print(resultString, 1);
				
		}
	}

	/*
	* Reads in a line of input from the user
	* Splits it by a whitespace delimiter
	* returns the array of split strings
	*/
	public static String[] read(int mode) {
		//variables
		Scanner scanner = new Scanner(System.in, "UTF-8");
		String[] tokens = null;
		
		//logic
		System.out.print("> ");
		String userString = scanner.nextLine();
		userString = userString.toUpperCase(Locale.ENGLISH);	//allow for case-insenstivity
		tokens = userString.split(" ");
		
		//return
		//LOOKUP: find some way to close() scanner without closing system.in()?
		lineNum = lineNum + 1;
		return tokens;
	}

	/*
	* Accepts an array of split strings, interpret one at a time
	* if num, push to stack / variable, act accordingly / keyword, act accordingly
	* return the String of what should be printed
	*/
	public static String eval(String[] toEval, int mode) {
		//variables
		String result = "";
		Stack stack = new Stack();
		String currentToken = "";
		BigInteger number = null;
		boolean letFlag = false;	//set true if "LET" keyword was parsed
		String toChange = "";
		errorFlag = false;			//set true if need to print to stderr
		
		//logic
		int i = 0;
		while (i < toEval.length) {
			currentToken = toEval[i];
		
			try {
				number = new BigInteger(toEval[i]);
				stack.push(number);
				
			} catch (NumberFormatException nfe) {
				int toCheck = (int)currentToken.charAt(0);
				
				if (currentToken.equals("LET") && i == 0) {					
					//let must be followed by variable + values
					i = i + 1;
					if (i + 1 >= toEval.length) {
						result = "Line " + lineNum + ": Operator LET applied to empty stack";
						errorFlag = true;
						return result;
					}
					
					toChange = toEval[i];
					
					if (toChange.length() != 1 || ( toCheck < 65 || toCheck > 90 ) ) {
						result = "Line " + lineNum + ": Operator LET applied to empty stack";
						errorFlag = true;
						return result;
					}
					letFlag = true;
					
				} else if (currentToken.equals("PRINT") && i == 0 ) {
					//do nothing
					
				} else if (currentToken.equals("QUIT") && i == 0 ) {
					return "QUIT";
					
				} else if (currentToken.equals("LET") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("PRINT") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("QUIT") && i != 0 ) {
					result = "Line " + lineNum + ": Could not evaluate expression";
					return result;
					
				} else if (currentToken.equals("+") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.add(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator + applied to empty stack";
						errorFlag = true;
						return result;
					}
					
				} else if (currentToken.equals("-") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.subtract(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator - applied to empty stack";
						errorFlag = true;
						return result;
					}
					
				} else if (currentToken.equals("/") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.divide(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator / applied to empty stack";
						errorFlag = true;
						return result;
					}
					
				} else if (currentToken.equals("*") ) {
					try {
						BigInteger second = (BigInteger)stack.pop();
						BigInteger first = (BigInteger)stack.pop();
						stack.push(first.multiply(second) );
						
					} catch (EmptyStackException ese) {
						result = "Line " + lineNum + ": Operator * applied to empty stack";
						errorFlag = true;
						return result;
					}
					
				} else if ( currentToken.length() == 1 && toCheck > 64 && toCheck < 91 ) {
					BigInteger variable = variables.get(currentToken);
					if (variable == null) {
						result = "Line " + lineNum + ": Variable ";
						result = result + currentToken + " is not initialized";
						errorFlag = true;
						return result;
					}
					stack.push(variable);
					
				} else {
					result = "Line " + lineNum + ": Unknown keyword " + currentToken;
					errorFlag = true;
					return result;
				}
				
			}
			
			
			i = i + 1;
		}
		
		//return
		if (stack.size() > 1) {
			result = "Line " + lineNum + ": " + stack.size() + " elements in stack after evaluation";
			errorFlag = true;
			return result;
		}
		
		BigInteger resultant = (BigInteger)stack.pop();
		
		if (letFlag == true) {
			//write to variable
			variables.put(toChange, resultant);
		}
		
		result = resultant.toString();
		return result;
	}

	/*
	* Accepts a string and prints it back to user
	*/
	public static void print(String toPrint, int mode) {
		
		if (errorFlag == true) {
			System.err.println(toPrint);
			
		} else {
			System.out.println(toPrint);
		}
		
	}

	/*
	* Main method
	* Determine which mode to run the program in by checking if arguments exist
	* If no arguments, run REPL
	* If arguments, read in from files
	*/
	public static void main(String[] args) {
		
		if (args.length == 0) {
			runLoop();
			
		} else { 
			readFiles();
		}
		
		System.exit(0);
		
	}
	
	//------------ debug code ---------------------
	
	/*
	* To be used as a way to test the runLoop() function
	*/
	public ArrayList<String> debugRunLoop(int i) {
		//will have to have eval() consume a 'QUIT' keyword to break this loop'
		ArrayList<String> debugOutput = new ArrayList<String>();
		lineNum = 0;
		int j = 0;
		while (j < i) {
			
			//https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
			String[] resultTokens = read(1);
			
			String resultString = "";
			if (resultTokens.length > 0) {
				resultString = eval(resultTokens, 1);
			}
			if (resultString.equals("QUIT")) {
				break;
			}
			debugOutput.add(resultString);
			
			print(resultString, 1);
			
			j = j + 1;
		}
		
		return debugOutput;
	}
	
	/*
	* To be used for testing different modes between repl / files
	*/
	public void setErrorFlag (boolean toSet) {
		errorFlag = toSet;
	}
	
	/*
	* To be used for setting lineNum to predict error output messages
	*/
	public void setLineNum (int num) {
		lineNum = num;
	}
	
	//end of program
}

