package com.xxx.calculator.cli;

import com.xxx.calculator.CalculationResult;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

/**
 * Consumes calculations results and print them.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class CalculatorResultPrinter implements Consumer<CalculationResult<ParsedCommand>> {

    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT = new DecimalFormat("#.##########");

    private final PrintStream printStream;
    private final DecimalFormat decimalFormat;

    /**
     * Creates printer with default decimal formatter.
     *
     * @param printStream print stream to print to
     */
    public CalculatorResultPrinter(final PrintStream printStream) {
        this(printStream, DEFAULT_DECIMAL_FORMAT);
    }

    /**
     * Creates printer with the given decimal formatter.
     *
     * @param printStream print stream to print to
     * @param decimalFormat decimal formatter
     */
    public CalculatorResultPrinter(final PrintStream printStream, final DecimalFormat decimalFormat) {
        this.printStream = printStream;
        this.decimalFormat = decimalFormat;
    }

    @Override
    public void accept(final CalculationResult<ParsedCommand> calculationResult) {
        if (calculationResult.isFailed()) {
            final ParsedCommand failedCommand = calculationResult.failedCommand().get();
            printStream.printf(
                "operator %s (position: %s): insufficient parameters\n",
                failedCommand.getToken(), failedCommand.getPosition()
            );
        }
        printStack(calculationResult.getStackState());
    }

    private void printStack(final List<Double> stackState) {
        printStream.print("stack:");
        stackState.forEach(element -> printStream.print(" " + decimalFormat.format(element)));
        printStream.println();
    }
}
