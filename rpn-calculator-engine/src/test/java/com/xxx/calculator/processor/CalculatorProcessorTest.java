package com.xxx.calculator.processor;

import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CalculatorProcessorTest {

    private final Stack<Double> executionStack = new Stack<>();
    private final Stack<RevertibleStackChange> history = new Stack<>();

    private final CalculatorProcessor processor = new CalculatorProcessor(executionStack, history);

    @Test
    public void whenClearMethodCalledThenCalculatorReset() {
        processor.push(1.0);
        processor.push(2.0);
        processor.push(3.0);
        processor.clear();
        assertThat(processor.executionStackSize(), is(0));
    }

    @Test
    public void whenElementPushedThenPopReturnIt() {
        processor.push(13.0);
        final Double popped = processor.pop();
        assertThat(popped, is(13.0));
        assertThat(processor.executionStackSize(), is(0));
    }

    @Test
    public void whenProcessorCreatedThenItHasEmptyState() {
        final CalculatorProcessor calculator = new CalculatorProcessor();
        assertThat(calculator.executionStackSize(), is(0));
        assertThat(calculator.stackElements(), empty());
    }

}
