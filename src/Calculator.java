import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    public String transformToRPN(String input) {
        char[] chars = input.toCharArray();

        StringBuilder outString = new StringBuilder();
        StringBuilder currentString = new StringBuilder();
        LinkedList<String> stackSymbols = new LinkedList<>();

        for (int i = 0; i < chars.length; i++) {
            String currentSymbol = String.valueOf(chars[i]);

            if (currentSymbol.matches("[0-9]") || currentSymbol.matches("\\.")) {
                currentString.append(currentSymbol);
            } else {
                if (!currentString.isEmpty()) {
                    outString.append(currentString);
                    outString.append(" ");
                    currentString = new StringBuilder();
                }

                if (currentSymbol.equals("(")) {
                    stackSymbols.push(currentSymbol);
                } else if (currentSymbol.equals(")")) {
                    while (!stackSymbols.isEmpty() && !stackSymbols.getFirst().equals("(")) {
                        outString.append(stackSymbols.pop());
                        outString.append(" ");
                    }
                    stackSymbols.pop();
                } else if (currentSymbol.matches("[+\\-]")) {
                    while (!stackSymbols.isEmpty() && stackSymbols.getFirst().matches("[+\\-*/^]")) {
                        outString.append(stackSymbols.pop());
                        outString.append(" ");
                    }
                    stackSymbols.push(currentSymbol);
                } else if (currentSymbol.matches("[*/]")) {
                    while (!stackSymbols.isEmpty() && stackSymbols.getFirst().matches("[*/^]")) {
                        outString.append(stackSymbols.pop());
                        outString.append(" ");
                    }
                    stackSymbols.push(currentSymbol);
                } else if (currentSymbol.matches("[\\^]")) {
                    while (!stackSymbols.isEmpty() && stackSymbols.getFirst().matches("[\\^]")) {
                        outString.append(stackSymbols.pop());
                        outString.append(" ");
                    }
                    stackSymbols.push(currentSymbol);
                }
            }
        }
        if (!currentString.isEmpty()) {
            outString.append(currentString);
            outString.append(" ");
        }

        for (String s : stackSymbols) {
            outString.append(s);
            outString.append(" ");
        }
        return outString.toString();
    }

    public String insertMulSigns(String stringToInsert) {
        String regex = "[0-9)]\\(";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(stringToInsert);
        String value = stringToInsert;
        int shift = 0;
        while (matcher.find()) {
            int start = matcher.start() + shift;
            int end = matcher.end() + shift;
            value = value.substring(0, start)
                    + value.charAt(start) + "*" + value.charAt(end - 1)
                    + value.substring(end);
            shift++;
        }
        return value;
    }

    public double calculateFromRPN(String textRPN) {
        String[] symbols = textRPN.split(" ");

        LinkedList<String> stackSymbols = new LinkedList<>();

        for (int i = 0; i < symbols.length; i++) {
            if (symbols[i].matches("[0-9]+\\.?[0-9]*")) {
                stackSymbols.push(symbols[i]);
            } else {
                Double symbol1 = Double.valueOf(stackSymbols.pop());
                Double symbol2 = Double.valueOf(stackSymbols.pop());
                Double currentResult = 0.0;
                switch (symbols[i]) {
                    case "^" -> currentResult = Math.pow(symbol2, symbol1);
                    case "*" -> currentResult = symbol2 * symbol1;
                    case "/" -> currentResult = symbol2 / symbol1;
                    case "+" -> currentResult = symbol2 + symbol1;
                    case "-" -> currentResult = symbol2 - symbol1;
                }
                stackSymbols.push(String.valueOf(currentResult));
            }
        }
        return Double.parseDouble(stackSymbols.pop());
    }


}
