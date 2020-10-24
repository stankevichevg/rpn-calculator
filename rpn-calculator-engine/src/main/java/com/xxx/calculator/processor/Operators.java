package com.xxx.calculator.processor;

import java.util.Optional;
import java.util.Stack;

import static com.xxx.calculator.processor.BinaryOperator.DIVIDE_OPERATOR;
import static com.xxx.calculator.processor.BinaryOperator.MINUS_OPERATOR;
import static com.xxx.calculator.processor.BinaryOperator.MULTIPLY_OPERATOR;
import static com.xxx.calculator.processor.BinaryOperator.PLUS_OPERATOR;
import static com.xxx.calculator.processor.UnaryOperator.SQRT_OPERATOR;
import static java.util.Optional.empty;

/**
 * Factory to create operators.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class Operators {

    private Operators() {
    }

    /**
     * Creates operator to push given number to execution stack.
     *
     * @param number to push on stack
     * @return operator to push given number to stack
     */
    public static Operator pushNumber(final Number number) {
        return new PushNumber(number.doubleValue());
    }

    /**
     * Creates plus operator.
     *
     * @return created operator
     */
    public static Operator plus() {
        return PLUS_OPERATOR;
    }

    /**
     * Creates minus operator.
     *
     * @return created operator
     */
    public static Operator minus() {
        return MINUS_OPERATOR;
    }

    /**
     * Creates multiply operator.
     *
     * @return created operator
     */
    public static Operator multiply() {
        return MULTIPLY_OPERATOR;
    }

    /**
     * Creates divide operator.
     *
     * @return created operator
     */
    public static Operator divide() {
        return DIVIDE_OPERATOR;
    }

    /**
     * Creates SQRT operator.
     *
     * @return created operator
     */
    public static Operator sqrt() {
        return SQRT_OPERATOR;
    }

    /**
     * Creates undo operator. Unapply previous recorded operation result.
     * Note that this operator can not undo CLEAR operator created by {@link #undo()}.
     *
     * @return created operator
     */
    public static Operator undo() {
        return UndoOperator.INSTANCE;
    }

    /**
     * Creates clear operator. After this operator is applied stack and operations history will be cleaned.
     *
     * @return created operator
     */
    public static Operator clear() {
        return ClearOperator.INSTANCE;
    }

    /**
     * Undo operator. Calls {@link CalculatorProcessor#undo()} for the given processor.
     */
    private static final class UndoOperator implements Operator {

        private static final Operator INSTANCE = new UndoOperator();

        private UndoOperator() {
        }

        @Override
        public Optional<RevertibleStackChange> operate(final CalculatorProcessor processor) {
            processor.undo();
            return empty();
        }
    }

    /**
     * Clear operator. Calls {@link CalculatorProcessor#clear()} for the given processor.
     */
    private static final class ClearOperator implements Operator {

        private static final Operator INSTANCE = new ClearOperator();

        private ClearOperator() {
        }

        @Override
        public Optional<RevertibleStackChange> operate(final CalculatorProcessor processor) {
            processor.clear();
            return empty();
        }
    }

    /**
     * Operator to push number on top of the calculator's execution stack.
     */
    private static class PushNumber implements Operator {

        private static final RevertibleStackChange REVERT_CHANGE = Stack::pop;

        private final Double number;

        PushNumber(final Double number) {
            this.number = number;
        }

        @Override
        public Optional<RevertibleStackChange> operate(final CalculatorProcessor calculator) {
            calculator.push(number);
            return Optional.of(REVERT_CHANGE);
        }

    }
}
