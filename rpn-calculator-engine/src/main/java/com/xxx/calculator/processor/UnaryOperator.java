package com.xxx.calculator.processor;

import java.util.Optional;
import java.util.Stack;

import static java.util.Optional.of;

/**
 * Base class for all unary operators. Pop from the applied execution stack one element, operate on it
 * producing result, push result back to the execution stack.
 * To implement unary operator extend this class and define {@link #operate(Double)}.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
abstract class UnaryOperator implements Operator {

    static final Operator SQRT_OPERATOR = new UnaryOperator() {
        @Override
        protected Double operate(final Double number) {
            return Math.sqrt(number);
        }
    };

    @Override
    public Optional<RevertibleStackChange> operate(final CalculatorProcessor calculator)
        throws InsufficientStackSizeException {

        if (calculator.executionStackSize() < 1) {
            throw new InsufficientStackSizeException();
        }
        final Double poppedElement = calculator.pop();
        final Double operatorResult = operate(poppedElement);
        calculator.push(operatorResult);
        return of(new UnaryOperatorChange(poppedElement));
    }

    /**
     * Operates on execution stack element and produce result that should be pushed to stack back.
     *
     * @param number operand
     * @return operation result
     */
    protected abstract Double operate(Double number);

    private static final class UnaryOperatorChange implements RevertibleStackChange {

        private final Double poppedElement;

        UnaryOperatorChange(final Double poppedElement) {
            this.poppedElement = poppedElement;
        }

        @Override
        public void undo(final Stack<Double> executionStack) {
            executionStack.pop();
            executionStack.push(poppedElement);
        }
    }
}
