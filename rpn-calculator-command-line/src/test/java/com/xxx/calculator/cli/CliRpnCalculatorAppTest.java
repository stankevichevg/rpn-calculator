package com.xxx.calculator.cli;

import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CliRpnCalculatorAppTest {

    @Test
    public void successResultTest() {
        testCase("1 2 3 +\n1 * 2", "stack: 1 5\nstack: 1 5 2\n");
    }

    @Test
    public void failedResultTest() {
        testCase("1 +\n", "operator + (position: 2): insufficient parameters\nstack: 1\n");
    }

    @Test
    public void unknownInputTest() {
        testCase("1 + unknown_operator\n", "Calculator was stopped: Input has unknown operation: unknown_operator\n");
    }

    private void testCase(String input, String output) {
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        final PrintStream printStream = new PrintStream(new BufferedOutputStream(outputStream));
        final CliRpnCalculatorApp app = new CliRpnCalculatorApp(
            new ByteArrayInputStream(input.getBytes()),
            printStream
        );
        app.start();
        printStream.flush();
        assertThat(new String(outputStream.toByteArray()), is(output));
    }

}
