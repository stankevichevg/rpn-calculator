package com.xxx.calculator.cli;

import com.xxx.calculator.CalculationResult;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import static com.xxx.calculator.processor.Operators.plus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CalculatorResultPrinterTest {

    @Test
    public void whenSuccessCalcThenStackPrinted() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(new BufferedOutputStream(outputStream));
        final CalculatorResultPrinter printer = new CalculatorResultPrinter(printStream);
        printer.accept(new CalculationResult<ParsedCommand>(List.of(1.0, 2.0, 3.0)));
        printStream.flush();
        assertThat(new String(outputStream.toByteArray()), is("stack: 1 2 3\n"));
    }

    @Test
    public void whenFailedCalcThenWarningAndStackPrinted() throws IOException {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(new BufferedOutputStream(outputStream));
        final CalculatorResultPrinter printer = new CalculatorResultPrinter(printStream);
        printer.accept(new CalculationResult<ParsedCommand>(List.of(1.0), new ParsedCommand(plus(), "+", 13)));
        printStream.flush();
        assertThat(
            new String(outputStream.toByteArray()),
            is("operator + (position: 13): insufficient parameters\nstack: 1\n")
        );
    }

}
