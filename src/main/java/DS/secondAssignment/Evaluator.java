package DS.secondAssignment;

import java.util.Scanner;
import java.util.Stack;

public class Evaluator {
    //Performs the proper arithmetic operation on the given numbers.
    private static int operate(int firstNumber, int secondNumber, char operation) {
        switch (operation) {
            case '+':
                return firstNumber + secondNumber;
            case '-':
                return firstNumber - secondNumber;
            case '*':
                return firstNumber * secondNumber;
            case '/':
                return firstNumber / secondNumber;
            default: //If none above, the operation is exponentiation.
                return (int) Math.pow(firstNumber, secondNumber);
        }
    }

    //Evaluates a given postfix expression.
    public static int evaluate(String expression) {
        Stack<Integer> stack = new Stack<>();

        //Converting the given string to an array. The delimiter is a space.
        String[] expressionArray = InfixToPostfix.infixToPostfix(expression).split(" ");

        //Traversing the array.
        for (String element : expressionArray) {
            if (InfixToPostfix.isOperand(element)) {
                stack.push(Integer.valueOf(element));
            } else if (InfixToPostfix.isOperator(element.charAt(0))) {
                int secondNumber = stack.pop();
                int firstNumber = stack.pop();

                stack.push(operate(firstNumber, secondNumber, element.charAt(0)));
            }
        }

        //The remaining value in the stack, is the result of the evaluation.
        return stack.peek();
    }

    public static void main(String[] args) {
        System.out.println("Enter a math expression: ");

        Scanner input = new Scanner(System.in);

        String expression = input.nextLine();

        System.out.println(Evaluator.evaluate(expression));
    }
}