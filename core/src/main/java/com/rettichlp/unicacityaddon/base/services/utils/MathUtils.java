package com.rettichlp.unicacityaddon.base.services.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * The following code is based on MPL-licensed code by Paul Zhang.
 * The original code is available at: <a href="https://github.com/paulzhng/UCUtils">https://github.com/paulzhng/UCUtils</a>.
 * Copyright (c) 2019/2020 Paul Zhang
 * <p>
 * The following code is subject to the MPL Version 2.0.
 *
 * @author Fuzzlemann
 */
public class MathUtils {

    public static final DecimalFormat HEART_DECIMAL_FORMAT = new DecimalFormat("###.#");
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###.##");
    private static final DecimalFormat CALCULATOR_DECIMAL_FORMAT = new DecimalFormat("###,###.###", DecimalFormatSymbols.getInstance(Locale.GERMAN));
    private static double lastResult = 0;
    private double result = Double.NaN;
    private String expression;
    private int pos = -1;
    private int ch;

    static {
        CALCULATOR_DECIMAL_FORMAT.setMaximumFractionDigits(5);
    }

    public MathUtils(String expression) {
        this.expression = expression;
    }

    public String parse() {
        if (Double.isNaN(result))
            evaluate();

        return CALCULATOR_DECIMAL_FORMAT.format(result);
    }

    public double evaluate() {
        replaceVariables();
        nextChar();

        double x = parseExpression();
        if (pos < expression.length()) {
            throw new ExpressionException("Unexpected character: " + (char) ch);
        }

        this.result = x;
        lastResult = x;
        return x;
    }

    private void replaceVariables() {
        expression = expression.replaceAll("PI", String.valueOf(Math.PI));
        expression = expression.replaceAll("E", String.valueOf(Math.E));
        expression = expression.replaceAll("ANS", String.valueOf(lastResult));
    }

    private double parseExpression() throws ExpressionException {
        double x = parseTerm();
        while (true) {
            if (eat('+'))
                x += parseTerm();
            else if (eat('-'))
                x -= parseTerm();
            else
                return x;
        }
    }

    private double parseTerm() throws ExpressionException {
        double x = parseFactor();
        while (true) {
            if (eat('*'))
                x *= parseFactor();
            else if (eat('/'))
                x /= parseFactor();
            else
                return x;
        }
    }

    private double parseFactor() throws ExpressionException {
        if (eat('+'))
            return parseFactor();
        if (eat('-'))
            return -parseFactor();

        double x;
        int startPos = this.pos;
        if (eat('(')) {
            x = parseExpression();
            eat(')');
        } else if ((ch >= '0' && ch <= '9') || ch == '.') {
            while ((ch >= '0' && ch <= '9') || ch == '.')
                nextChar();
            x = Double.parseDouble(expression.substring(startPos, this.pos));
        } else if (ch >= 'a' && ch <= 'z') {
            while (ch >= 'a' && ch <= 'z')
                nextChar();
            String func = expression.substring(startPos, this.pos);
            x = parseFactor();
            x = switch (func) {
                case "sqrt" -> Math.sqrt(x);
                case "sin" -> Math.sin(Math.toRadians(x));
                case "cos" -> Math.cos(Math.toRadians(x));
                case "tan" -> Math.tan(Math.toRadians(x));
                default -> throw new ExpressionException("Unknown function: " + func);
            };
        } else {
            char wrongChar;

            if (ch == -1) {
                wrongChar = expression.charAt(expression.length() - 1);
            } else {
                wrongChar = (char) ch;
            }

            throw new ExpressionException("Unexpected character: " + wrongChar);
        }

        if (eat('^'))
            x = Math.pow(x, parseFactor());

        return x;
    }

    private void nextChar() {
        ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
    }

    private boolean eat(int charToEat) {
        while (ch == ' ')
            nextChar();
        if (ch == charToEat) {
            nextChar();
            return true;
        }
        return false;
    }

    public static boolean isInteger(String s) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
                else
                    continue;
            }
            if (Character.digit(s.charAt(i), 10) < 0)
                return false;
        }
        return true;
    }

    public static class ExpressionException extends ArithmeticException {

        ExpressionException(String message) {
            super(message);
        }
    }
}
