import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *	<Description goes here>
 *
 *	@author	Jonathan Chen and Mr Greenstein
 *	@since	February 26, 2025
 */
public class SimpleCalc{
	private ExprUtils utils;	// expression utilities
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	// constructor	
	public SimpleCalc(){
		
	}
	public static void main(String[] args){
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}
	public void run(){
		System.out.println("\nWelcome to SimpleCalc!!!");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}
	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc(){
	    Scanner scanner = new Scanner(System.in);
	    System.out.print("\n -> ");
	    String input = scanner.nextLine();
	    while(!input.equals("q")){
	        if(input.equals("h")){
	            printHelp();
	        }
	        else{
	            List<String> tokens = utils.tokenizeExpression(input);
	            double result = evaluateExpression(tokens);
	            System.out.println(result);
	        }
	        System.out.print("\n -> ");
	        input = scanner.nextLine();
	    }
	    scanner.close();
	}
	/**	Print help */
	public void printHelp(){
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}
	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens){
	    for(String token : tokens){
	        if(token.matches("\\d+(\\.\\d+)?")){  // If it's a number
	            valueStack.push(Double.parseDouble(token));
	        }
	        else if(token.equals("(")){
	            operatorStack.push(token);
	        }
	        else if(token.equals(")")){
	            while(!operatorStack.isEmpty() && !operatorStack.peek().equals("(")){
	                applyOperator();
	            }
	            operatorStack.pop();  // Remove '('
	        }
	        else{  // Operator
	            while(!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek())){
	                applyOperator();
	            }
	            operatorStack.push(token);
	        }
	    }
	    while(!operatorStack.isEmpty()){
	        applyOperator();
	    }
	    return valueStack.pop();
	}
	private void applyOperator(){
	    double b = valueStack.pop();
	    double a = valueStack.pop();
	    String op = operatorStack.pop();
	    switch(op){
	        case "+": valueStack.push(a + b); break;
	        case "-": valueStack.push(a - b); break;
	        case "*": valueStack.push(a * b); break;
	        case "/": valueStack.push(a / b); break;
	        case "%": valueStack.push(a % b); break;
	        case "^": valueStack.push(Math.pow(a, b)); break;
	    }
	}
	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and 
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2){
		if(op1.equals("^")) return false;
		if(op2.equals("(") || op2.equals(")")) return false;
		if((op1.equals("*") || op1.equals("/") || op1.equals("%")) 
				&&(op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
}
