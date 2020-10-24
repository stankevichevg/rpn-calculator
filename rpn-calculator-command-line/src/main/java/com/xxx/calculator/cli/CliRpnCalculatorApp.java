package com.xxx.calculator.cli;

import com.xxx.calculator.RpnCalculator;
import com.xxx.calculator.processor.CalculatorProcessor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Command line based RPN calculator.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CliRpnCalculatorApp {

    private final InputStream inputStream;
    private final PrintStream printStream;

    /**
     * Creates command line rpn calculator application.
     *
     * @param inputStream input stream to read from
     * @param printStream print stream to print to
     */
    public CliRpnCalculatorApp(final InputStream inputStream, final PrintStream printStream) {
        this.inputStream = inputStream;
        this.printStream = printStream;
    }

    /**
     * Starts the process of commands processing.
     */
    public void start() {
        try {
            new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(new CommandParser(OperatorFactory.INSTANCE))
                .map(new RpnCalculator<>(new CalculatorProcessor()))
                .forEach(new CalculatorResultPrinter(printStream));
        } catch (UnknownOperationException e) {
            printStream.println("Calculator was stopped: " + e.getMessage());
        }
    }

    /**
     * Application entry point.
     *
     * @param args application arguments
     */
    public static void main(final String[] args) {
        new CliRpnCalculatorApp(System.in, System.out).start();
    }

}
