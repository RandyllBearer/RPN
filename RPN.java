import java.util.*;
import java.lang.*;
import java.math.BigInteger;

public class RPN {
	
	static HashMap<String, BigInteger> variables = new HashMap<String, BigInteger>();
	
	public static void readFiles(){
		
		
	}

	public static void runLoop(){
		Scanner scanMe = new Scanner(System.in);
		BigInteger res;
		int lineNumber = 0;
		
		while(true){
			System.out.print("\n> ");
			String input = scanMe.nextLine().toUpperCase();
			String[] items = input.split(" ");
			lineNumber++;
			
			if(items.length>0){
				handleKeywords(items, lineNumber);
			}
		}
	}
	
	public static void handleKeywords(String[] items, int lN){
		BigInteger t, res;
		int l = items.length;
		
		switch(items[0]){
			case "LET":
				if(items[1].length()!=1){
					System.out.println("Line "+Integer.toString(lN)+": Unknown keyword "+items[1]);
					System.out.println("Terminating Operation...");
				}else{
					char c = items[1].charAt(0);
					int ascii = (int)c;
					if(ascii>=65&&ascii<=90){
						String tryVar = items[1];
						if(l == 3){
							try{
								t = new BigInteger(items[2]);
								variables.put(tryVar, t);
								System.out.println(""+t);
							}catch(NumberFormatException e2){
								if(variables.get(items[2]) != null){
									t = variables.get(items[2]);
									variables.put(tryVar, t);
									System.out.println(""+t);
								}else{
									System.out.println("Line "+Integer.toString(lN)+": Variable "+items[2]+" is not initialized");
									System.out.println("Terminating Operation...");
								}
							}
						}else{
							String[] calcArray = Arrays.copyOfRange(items, 2, l);
							res = calculate(calcArray, lN);
							if(res != null){
								variables.put(tryVar, res);
								System.out.println(""+res);	
							}
						}
					}else{
						System.out.println("Line "+Integer.toString(lN)+": Variable name "+items[1]+" is not valid.\nValid variable names are single characters a-z.");
						System.out.println("Terminating Operation...");
					}
				}
				break;
			case "PRINT":
				if(l == 2){
					try{
						t = new BigInteger(items[1]);
						System.out.println(""+t);
					}catch(NumberFormatException e2){
						if(variables.get(items[1]) != null){
							t = variables.get(items[1]);
							System.out.println(""+t);
						}else{
							System.out.println("Line "+Integer.toString(lN)+": Variable "+items[1]+" is not initialized");
							System.out.println("Terminating Operation...");
						}
					}
				}else{
					String[] calcArray = Arrays.copyOfRange(items, 1, l);
					res = calculate(calcArray, lN);
					if(res != null){
						System.out.println(""+res);	
					}
				}
				break;
			case "QUIT":
				System.exit(0);
				break;
			default:
				res = calculate(items, lN);
				if(res != null){
					System.out.println(""+res);	
				}
				break;
		}
	}
	
	public static BigInteger calculate(String[] items, int lineNumber){
		Stack stack = new Stack();
		BigInteger t, t1, t2;
		String[] acceptable = {"+", "-", "*", "/"};
		
		if(items.length>0){
			for(int i=0; i<items.length; i++){
				try{
					t = new BigInteger(items[i]);
					stack.push(t);
				}catch(NumberFormatException e){
					if(Arrays.asList(acceptable).contains(items[i])){
						if(stack.size()>1){
							switch(items[i]){
								case "+":
									t1 = (BigInteger) stack.pop();
									t2 = (BigInteger) stack.pop();
									stack.push(t2.add(t1));
									break;
								case "-":
									t1 = (BigInteger) stack.pop();
									t2 = (BigInteger) stack.pop();
									stack.push(t2.subtract(t1));
									break;
								case "*":
									t1 = (BigInteger) stack.pop();
									t2 = (BigInteger) stack.pop();
									stack.push(t2.multiply(t1));
									break;
								case "/":
									t1 = (BigInteger) stack.pop();
									t2 = (BigInteger) stack.pop();
									stack.push(t2.divide(t1));
									break;
							}
						}else{
							System.out.println("Line "+Integer.toString(lineNumber)+": Operator "+items[i]+" applied to empty stack");
							System.out.println("Terminating Operation...");
							return null;
						}
					}else{
						if(variables.get(items[i])!=null){
							t = variables.get(items[i]);
							stack.push(t);
						}else{
							System.out.println("Line "+Integer.toString(lineNumber)+": Variable "+items[i]+" is not initialized");
							System.out.println("Terminating Operation...");
							return null;
						}
					}
				}
				if(i==(items.length-1) && stack.size()>1){
					String output = Integer.toString(stack.size()) + " elements in stack after evaluation";
					System.out.println("Line "+Integer.toString(lineNumber)+": "+output);
					return null;
				}
			}
		}
		
		return (BigInteger) stack.pop();
	}


	public static void main(String[] args) {
		if(args.length==0){
			runLoop();
		}else{
			readFiles();
		}
	}
}