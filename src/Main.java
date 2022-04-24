public class Main {
    public static void main(String[] args) {
        Frame frame = new Frame("Калькулятор");

        Calculator calculator = new Calculator();
        String RPNString = calculator.transformToRPN("3 + 4.5 * 2 / (1 - 5)^2");

        System.out.println(calculator.insertMulSigns("2(3+4)(10+4)"));
        System.out.println(calculator.calculateFromRPN("1 2 - 4.333 * 3 + "));
    }
}
