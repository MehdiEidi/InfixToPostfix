package DS.SecondAssignment;

import java.util.Stack;

public class InfixToPostfix {
    //Returns true if the given character is an operator.
    static boolean isOperator(char character) {
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

    //Returns true, if the given string is a numeric value.
    static boolean isOperand(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    //Returns true if the operator1 has higher precedence than operator2.
    private static boolean hasHigherPrecedence(char operator1, char operator2) {
        if (operator1 == '^' && (operator2 == '*' || operator2 == '/' || operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-' || operator2 == '(')) {
            return true;
        } else return (operator1 == '+' || operator1 == '-') && operator2 == '(';
    }

    //Returns true if both operators have the same precedence.
    private static boolean hasSamePrecedence(char operator1, char operator2) {
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '*' || operator2 == '/')) {
            return true;
        } else return (operator1 == '-' || operator1 == '+') && (operator2 == '-' || operator2 == '+');
    }

    //Parses the given expression into a String array.
    private static String[] convertToArray(String expression) {
        String[] expressionArray = new String[expression.length()];
        int arrayIndex = 0;

        //Traversing the expression.
        for (int i = 0; i < expression.length(); i++) {
            if (i == expression.length() - 1) {
                expressionArray[arrayIndex++] = String.valueOf(expression.charAt(i));
            } else if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                //For handling the multi digit numbers. Adding the digits to temp and assign the whole number to array.
                StringBuilder temp = new StringBuilder();
                temp.append(expression.charAt(i));

                int j = i + 1;
                while ((j < expression.length()) && (expression.charAt(j) >= '0' && expression.charAt(j) <= '9')) {
                    temp.append(expression.charAt(j++));
                    i++;
                }

                expressionArray[arrayIndex++] = temp.toString();
            } else if (!(expression.charAt(i) >= '0' && expression.charAt(i) <= '9')) {
                expressionArray[arrayIndex++] = String.valueOf(expression.charAt(i));
            }
        }

        //Because of the multi digit numbers, the given string's length might be different from individual elements number.
        //So, there may be null elements in the array. For getting rid of these, we make a new array and copy the
        //none null elements to it as the final result of the methods.
        String[] finalArray = new String[arrayIndex];
        System.arraycopy(expressionArray, 0,finalArray, 0, arrayIndex);

        return finalArray;
    }

    //Converts the given infix expression to postfix expression.
    public static String infixToPostfix(String infixExpression) {
        Stack<String> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        String[] infixExpArray = convertToArray(infixExpression);

        //Traversing the expression array.
        for (String element : infixExpArray) {
            if (isOperand(element)) {
                result.append(element).append(" ");
            } else if (isOperator(element.charAt(0)) && stack.isEmpty()) {
                stack.push(element);
            } else if (isOperator(element.charAt(0)) && hasHigherPrecedence(element.charAt(0), stack.peek().charAt(0))) {
                stack.push(element);
            } else if (isOperator(element.charAt(0)) && !hasHigherPrecedence(element.charAt(0), stack.peek().charAt(0))) {
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