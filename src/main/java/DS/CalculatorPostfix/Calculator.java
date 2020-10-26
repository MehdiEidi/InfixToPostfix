package DS.CalculatorPostfix;

public class Calculator {



    public static void main(String[] args) {
        InfixToPostfix a = new InfixToPostfix();

        System.out.println(a.infixToPostfix("4*((2+16)/3)-2^23^4"));

    }
}
