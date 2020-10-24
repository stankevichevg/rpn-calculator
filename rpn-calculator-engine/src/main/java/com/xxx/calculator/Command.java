package com.xxx.calculator;

import com.xxx.calculator.processor.Operator;

/**
 * Represents single command of user input. Command knows {@link #operator} which
 * should be executed.
 *
 * Can be extended for a specific calculator implementation to keep additional information.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class Command {

    private final Operator operator;

    /**
     * Creates command to execute specified operator.
     *
     * @param operator operator to execute
     */
    public Command(final Operator operator) {
        this.operator = operator;
    }

    /**
     * Returns operator for this command.
     *
     * @return command operator
     */
    public Operator getOperator() {
        return operator;
    }

}
