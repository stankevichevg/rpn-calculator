package com.xxx.calculator.processor;


import java.util.Optional;

/**
 * Operator interface. Operates on calculator's execution stack or operations history states.
 * To create an operator use factory {@link Operators}, there are no other way by design.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public interface Operator {

    /**
     * Operates on the given instance of the calculator.
     *
     * @param calculator calculator to operate on
     * @return {@link Optional#of(Object)} operation result as revertible stack change or {@link Optional#empty()}.
     * @throws InsufficientStackSizeException if there are no required number of operands on the stack.
     */
    Optional<RevertibleStackChange> operate(CalculatorProcessor calculator) throws InsufficientStackSizeException;

}
