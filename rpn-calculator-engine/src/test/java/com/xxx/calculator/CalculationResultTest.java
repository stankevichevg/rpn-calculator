package com.xxx.calculator;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.xxx.calculator.processor.Operators.pushNumber;
import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CalculationResultTest {

    @Test
    public void whenResultHasFailedCommandThenItIsFailed() {
        final Command command = new Command(pushNumber(10));
        final CalculationResult<Command> calculationResult = new CalculationResult<Command>(new Stack<>(), command);
        assertThat(calculationResult.isFailed(), is(true));
        assertThat(calculationResult.failedCommand().isPresent(), is(true));
        assertThat(calculationResult.failedCommand().get(), is(equalTo(command)));
    }

    @Test
    public void whenResultHasNoFailedCommandThenItIsNotFailed() {
        final CalculationResult<Command> calculationResult = new CalculationResult<Command>(new Stack<>());
        assertThat(calculationResult.isFailed(), is(false));
        assertThat(calculationResult.failedCommand().isPresent(), is(false));
        assertThat(calculationResult.failedCommand(), is(equalTo(empty())));
    }

}
