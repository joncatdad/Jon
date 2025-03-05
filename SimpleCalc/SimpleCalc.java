import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 *	<Description goes here>
 *
 *	@author	Jonathan Chen and Mr Greenstein
 *	@since	February 26, 2025
 */
public class SimpleCalc{
    private ExprUtils utils;
    private ArrayStack<Double> valueStack;
    private ArrayStack<String> operatorStack;
    private List<Identifier> variables;
    public SimpleCalc(){
        utils = new ExprUtils();
        valueStack = new ArrayStack<>();
        operatorStack = new ArrayStack<>();
        variables = new ArrayList<>();
        // Initialize constants
        variables.add(new Identifier("pi", Math.PI));
        variables.add(new Identifier("e", Math.E));
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
    public void runCalc(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("\n -> ");
        String input = scanner.nextLine();
        while(!input.equals("q")){
            if(input.equals("h")){
                printHelp();
            }
            else if(input.equals("l")){
                listVariables();
            }
            else if(input.contains("=")){
                processAssignment(input);
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
    private void listVariables(){
        System.out.println("\nIdentifiers:");
        for(Identifier id : variables){
            System.out.println("  " + id.getName() + " = " + id.getValue());
        }
    }
    private void processAssignment(String input){
        String[] parts = input.split("=", 2);
        if(parts.length == 2){
            String varName = parts[0].trim();
            String expression = parts[1].trim();
            if(!varName.matches("[a-zA-Z]+")){
                System.out.println("Invalid variable name.");
                return;
            }
            List<String> tokens = utils.tokenizeExpression(expression);
            double value = evaluateExpression(tokens);
            Identifier existing = findVariable(varName);
            if(existing != null){
                existing.setValue(value);
            }
            else{
                variables.add(new Identifier(varName, value));
            }
            System.out.println(varName + " = " + value);
        }
    }
    private Identifier findVariable(String name){
		for(Identifier id : variables){
			if(name.equals(id.getName())){
				return id;
			}
		}
		return null;
    }
    public double evaluateExpression(List<String> tokens){
        for(int i = 0; i < tokens.size(); i++){
            String token = tokens.get(i);
            if(token.matches("[a-zA-Z]+")){
                Identifier id = findVariable(token);
                if (id != null){
				    tokens.set(i, String.valueOf(id.getValue()));
				}
				else{
				    tokens.set(i, "0");
				}
            }
        }
        Stack<Double> values = new Stack<>();
        Stack<String> operators = new Stack<>();
        for(String token : tokens){
            if(token.matches("\\d+(\\.\\d+)?")){
                values.push(Double.parseDouble(token));
            }
            else if(token.equals("(")){
                operators.push(token);
            }
            else if(token.equals(")")){
                while(!operators.isEmpty() && !operators.peek().equals("(")){
                    applyOperator(values, operators);
                }
                operators.pop();
            }
            else{
                while(!operators.isEmpty() && hasPrecedence(token, operators.peek())){
                    applyOperator(values, operators);
                }
                operators.push(token);
            }
        }
        while(!operators.isEmpty()){
            applyOperator(values, operators);
        }
        return values.pop();
    }
    private void applyOperator(Stack<Double> values, Stack<String> operators){
        double b = values.pop();
        double a = values.pop();
        String op = operators.pop();
        switch(op){
            case "+":
				values.push(a + b);
				break;
            case "-":
				values.push(a - b);
				break;
            case "*":
				values.push(a * b);
				break;
            case "/":
				values.push(a / b);
				break;
            case "%":
				values.push(a % b);
				break;
            case "^":
				values.push(Math.pow(a, b));
				break;
        }
    }
    private boolean hasPrecedence(String op1, String op2){
        if(op1.equals("^")){
			return false;
		}
        if(op2.equals("(") || op2.equals(")")){
			return false;
		}
        if((op1.equals("*") || op1.equals("/") || op1.equals("%"))
				&& (op2.equals("+") || op2.equals("-"))){
				return false;
			}
        return true;
    }
    public void printHelp(){
        System.out.println("Help:");
        System.out.println("  h - this message\n  q - quit\n");
        System.out.println("Expressions can contain:");
        System.out.println("  integers or decimal numbers");
        System.out.println("  arithmetic operators +, -, *, /, %, ^");
        System.out.println("  parentheses '(' and ')'");
    }
}
