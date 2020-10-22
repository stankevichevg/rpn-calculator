package com.xxx.calculator.processor;

import java.util.Optional;
import java.util.Stack;

import static java.util.Optional.of;

/**
 * Base class for all binary operators.
 * Pop from the applied execution stack two elements, operate on them producing
 * result, push result back to the execution stack.
 * To implement binary operator extend this class and define
 * {@link #operate(Double, Double)}.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public abstract class BinaryOperator implements Operator {

    /**
     * Plus operator instance.
     */
    static final Operator PLUS_OPERATOR = new BinaryOperator() {
        @Override
        protected Double operate(final Double left, final Double right) {
            return left + right;
        }
    };

    /**
     * Minus operator instance.
     */
    static final Operator MINUS_OPERATOR = new BinaryOperator() {
        @Override
        protected Double operate(final Double left, final Double right) {
            return left - right;
        }
    };

    /**
     * Multiply operator instance.
     */
    static final Operator MULTIPLY_OPERATOR = new BinaryOperator() {
        @Override
        protected Double operate(final Double left, final Double right) {
            return left * right;
        }
    };

    /**
     * Divide operator instance.
     */
    static final Operator DIVIDE_OPERATOR = new BinaryOperator() {
        @Override
        protected Double operate(final Double left, final Double right) {
            return left / right;
        }
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<RevertibleStackChange> operate(final CalculatorProcessor calculator)
        throws InsufficientStackSizeException {

        if (calculator.executionStackSize() < 2) {
            throw new InsufficientStackSizeException();
        }
        final Double rightOperand = calculator.pop();
        final Double leftOperand = calculator.pop();
        final Double operatorResult = operate(leftOperand, rightOperand);
        calculator.push(operatorResult);
        return of(new BinaryOperatorChange(rightOperand, leftOperand));
    }

    /**
     * Operates on two execution stack elements and produce result that should be pushed to stack back.
     *
     * @param left left operand
     * @param right right operand
     * @return operation result
     */
    protected abstract Double operate(Double left, Double right);

    private static final class BinaryOperatorChange implements RevertibleStackChange {

        private final Double firstPoppedElement;
        private final Double secondPoppedElement;

        BinaryOperatorChange(final Double firstPoppedElement, final Double secondPoppedElement) {
            this.firstPoppedElement = firstPoppedElement;
            this.secondPoppedElement = secondPoppedElement;
        }

        @Override
        public void undo(final Stack<Double> executionStack) {
            executionStack.pop();
            executionStack.push(secondPoppedElement);
            executionStack.push(firstPoppedElement);
        }
    }
}
