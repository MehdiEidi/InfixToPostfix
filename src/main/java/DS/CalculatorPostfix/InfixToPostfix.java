package DS.CalculatorPostfix;

import java.util.Stack;

public class InfixToPostfix {

    //Returns true if the given character is an operator.
    private boolean isOperator(char character) {
        switch (character) {
            case '+':
            case '-':
            case '*':
            case '/':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private boolean isOperatorS(String str) {
        switch (str) {
            case "+":
            case "-":
            case "*":
            case "/":
            case "^":
                return true;
            default:
                return false;
        }
    }

    //Returns true if the given character is an operand
    private boolean isOperand(char character) {
        switch (character) {
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                return true;
            default:
                return false;
        }
    }

    public static boolean isOperandS(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");

    }

    //Returns true if the operator1 has higher precedence than operator2.
    private boolean hasHigherPrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else if (operator1 == '^' && (operator2 == '*' || operator2 == '/' || operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else return (operator1 == '+' || operator1 == '-') && operator2 == '(';
    }

    //Returns true if both operators have the same precedence.
    private boolean hasSamePrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '/' || operator2 == '*')) {
            return true;
        } else return (operator1 == '-' || operator1 == '+') && (operator2 == '-' || operator2 == '+');
    }

    private String[] convertToArray(String expression) {
        String[] arrayExp = new String[expression.length()];
        int k = 0;

        for(int i = 0; i < expression.length(); i++) {
            if (i == expression.length() - 1) {
                arrayExp[k] = String.valueOf(expression.charAt(i));

                k++;
            } else if(isOperator(expression.charAt(i))) {
                arrayExp[k] = String.valueOf(expression.charAt(i));

                k++;
            } else if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                StringBuilder temp = new StringBuilder();

                temp.append(expression.charAt(i));
                int j = i + 1;
                while (expression.charAt(j) >= '0' && expression.charAt(j) <= '9') {
                    temp.append(expression.charAt(j));
                    j++;
                    i++;
                }

                arrayExp[k] = temp.toString();

                k++;
            } else if (expression.charAt(i) == '(') {
                arrayExp[k] = String.valueOf(expression.charAt(i));

                k++;
            } else if (expression.charAt(i) == ')') {
                arrayExp[k] = String.valueOf(expression.charAt(i));

                k++;
            }
        }
        String[] resultArray = new String[k];
        System.arraycopy(arrayExp, 0,resultArray, 0, k);
        return resultArray;
    }

    /**
     * Converts an infix expression to a postfix expression.
     * @param infixExpression the infix expression.
     * @return the postfix expression.
     */
    public String infixToPostfix(String infixExpression) {
        Stack<String> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        String[] infixExpArray = convertToArray(infixExpression);
        //Traversing the given string.
        for (String element : infixExpArray) {
            //If the character is an operand we append it to the result string.
            //else if it is an operator and the stack is empty, we push it to the stack.
            //else if it is an operator and stack is not empty and the top operator in stack has lower precedence, we push
            //the character to the stack.
            //else if it is an operator and stack is not empty and the top operator in stack has higher precedence,
            //while the stack is not empty and the top operator in the stack has higher precedence than the character or they have
            //the same precedence, we append the top operator of the stack into the result and pop it from the stack. Then
            //we push the character in the expression to the stack.
            //else if the character is an opening bracket, we push it to the stack.
            //else if the character is a closing bracket, while the stack is not empty and we haven't reached an opening
            //bracket, we append everything to the result string and pop them from the stack. Then we pop the opening bracket
            //from the stack.
            if (isOperandS(element)) {
                result.append(element).append(" ");

            } else if (isOperatorS(element) && stack.isEmpty()) {
                stack.push(element);
            } else if (isOperatorS(element) && hasHigherPrecedence(element.charAt(0), stack.peek().charAt(0))) {
                stack.push(element);
            } else if (isOperatorS(element) && !hasHigherPrecedence(element.charAt(0), stack.peek().charAt(0))) {
                while (!stack.isEmpty() && (hasHigherPrecedence(stack.peek().charAt(0), element.charAt(0)) || hasSamePrecedence(element.charAt(0), stack.peek().charAt(0)))) {
                    result.append(stack.peek()).append(" ");
                    stack.pop();
                }

                stack.push(element);
            } else if (element.equals("(")) {
                stack.push(element);
            } else if (element.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(stack.peek()).append(" ");
                    stack.pop();
                }

                stack.pop();
            }
        }

        //Appending the remaining operators to the result.
        while (!stack.isEmpty()) {
            result.append(stack.peek()).append(" ");
            stack.pop();
        }

        return result.toString();
    }
}