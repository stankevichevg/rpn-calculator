package com.xxx.calculator.processor;

import java.util.Optional;
import java.util.Stack;

/**
 * Operator that pushes number on top of the calculator's execution stack.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
class PushNumber implements Operator {

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
