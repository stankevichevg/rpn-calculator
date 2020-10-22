package com.xxx.calculator.processor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.xxx.calculator.processor.Operators.clear;
import static com.xxx.calculator.processor.Operators.divide;
import static com.xxx.calculator.processor.Operators.minus;
import static com.xxx.calculator.processor.Operators.multiply;
import static com.xxx.calculator.processor.Operators.plus;
import static com.xxx.calculator.processor.Operators.pushNumber;
import static com.xxx.calculator.processor.Operators.sqrt;
import static com.xxx.calculator.processor.Operators.undo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class OperatorsTest {

    private final Stack<Double> executionStack = new Stack<>();
    private final Stack<RevertibleStackChange> history = new Stack<>();

    private final CalculatorProcessor processor = new CalculatorProcessor(executionStack, history);

    @BeforeEach
    public void reset() {
        executionStack.clear();
        history.clear();
    }

    @Test
    public void whenPushNumberOperatorAppliedThenNumberOnStack() throws InsufficientStackSizeException {
        processor.execute(pushNumber(100500));
        assertThat(executionStack, hasSize(1));
        assertThat(executionStack, contains(100500.0));
    }

    @Test
    public void whenPlusOperatorAppliedThenResultOnStack() throws InsufficientStackSizeException {
        executionStack.push(3.0);
        executionStack.push(5.0);
        processor.execute(plus());
        assertThat(executionStack, hasSize(1));
        assertThat(executionStack, contains(8.0));
    }

    @Test
    public void whenInsufficientParamsAndBinaryOperatorAppliedThenException() throws InsufficientStackSizeException {
        executionStack.push(3.0);
        assertThrows(InsufficientStackSizeException.class, () -> {
            processor.execute(plus());
        });
    }

    @Test
    public void whenInsufficientParamsAndUnaryOperatorAppliedThenException() throws InsufficientStackSizeException {
        assertThrows(InsufficientStackSizeException.class, () -> {
            processor.execute(sqrt());
        });
    }

    @Test
    public void whenMinusOperatorAppliedThenResultOnStack() throws InsufficientStackSizeException {
        executionStack.push(3.0);
        executionStack.push(5.0);
        processor.execute(minus());
        assertThat(executionStack, hasSize(1));
        assertThat(executionStack, contains(-2.0));
    }

    @Test
    public void whenMultiplyOperatorAppliedThenResultOnStack() throws InsufficientStackSizeException {
        executionStack.push(3.0);
        executionStack.push(5.0);
        processor.execute(multiply());
        assertThat(executionStack, hasSize(1));
        assertThat(executionStack, contains(15.0));
    }

    @Test
    public void whenDivideOperatorAppliedThenResultOnStack() throws InsufficientStackSizeException {
        executionStack.push(5.0);
        executionStack.push(2.0);
        processor.execute(divide());
        assertThat(executionStack, hasSize(1));
        assertThat(executionStack, contains(2.5));
    }

    @Test
    public void whenSqrtOperatorAppliedThenResultOnStack() throws InsufficientStackSizeException {
        executionStack.push(9.0);
        executionStack.push(9.0);
        processor.execute(sqrt());
        assertThat(executionStack, hasSize(2));
        assertThat(executionStack, contains(9.0, 3.0));
    }

    @Test
    public void whenUndoBinaryOperationThenStackStateIsRestored() throws InsufficientStackSizeException {
        executionStack.push(9.0);
        executionStack.push(9.0);
        processor.execute(plus());
        assertThat(history, hasSize(1));
        processor.execute(undo());
        assertThat(history, empty());
        assertThat(executionStack, hasSize(2));
        assertThat(executionStack, contains(9.0, 9.0));
    }

    @Test
    public void whenUndoUnaryOperationThenStackStateIsRestored() throws InsufficientStackSizeException {
        executionStack.push(9.0);
        executionStack.push(9.0);
        processor.execute(sqrt());
        assertThat(history, hasSize(1));
        assertThat(executionStack, contains(9.0, 3.0));
        processor.execute(undo());
        assertThat(history, empty());
        assertThat(executionStack, hasSize(2));
        assertThat(executionStack, contains(9.0, 9.0));
    }

    @Test
    public void whenClearOperatorAppliedThenCalculatorStateIsClean() throws InsufficientStackSizeException {
        executionStack.push(9.0);
        executionStack.push(9.0);
        processor.execute(sqrt());
        assertThat(history, not(empty()));
        assertThat(executionStack, not(empty()));
        processor.execute(clear());
        assertThat(history, empty());
        assertThat(executionStack, empty());
    }

}
