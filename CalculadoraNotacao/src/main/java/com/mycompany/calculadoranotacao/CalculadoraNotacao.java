/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadoranotacao;

import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Scanner;

/**
 *
 * @author Marcelo
 */
public class CalculadoraNotacao {

   public static double calcularExpressao(String expressao) {
        Stack<Double> operandos = new Stack<>();
        Stack<Character> operadores = new Stack<>();

        StringTokenizer tokenizer = new StringTokenizer(expressao, " +-*/()", true);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            }

            if (isNumero(token)) {
                operandos.push(Double.valueOf(token));
            } else if (isOperador(token.charAt(0)) || token.equals("(")) {
                operadores.push(token.charAt(0));
            } else if (token.equals(")")) {
                while (operadores.peek() != '(') {
                    calcularOperacao(operandos, operadores);
                }
                operadores.pop();
            }
        }

        while (!operadores.isEmpty()) {
            calcularOperacao(operandos, operadores);
        }

        return operandos.pop();
    }

    public static boolean isNumero(String token) {
        try {
            Double.valueOf(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOperador(char token) {
        return "+-*/".indexOf(token) != -1;
    }

    public static void calcularOperacao(Stack<Double> operandos, Stack<Character> operadores) {
        double b = operandos.pop();
        double a = operandos.pop();
        char operador = operadores.pop();
        switch (operador) {
            case '+' -> operandos.push(a + b);
            case '-' -> operandos.push(a - b);
            case '*' -> operandos.push(a * b);
            case '/' -> operandos.push(a / b);
        }
    }

    public static void main(String[] args) {
        String expressaoInfixa;
       try (Scanner scanner = new Scanner(System.in)) {
           System.out.print("Digite a expressão a ser calculada: ");
           expressaoInfixa = scanner.nextLine();
       }

        double resultado = calcularExpressao(expressaoInfixa);
        System.out.println("Expressão Infixa: " + expressaoInfixa);
        System.out.println("Resultado: " + resultado);
    }
}