package com.xxx.calculator.cli;

/**
 * Indicates that input contains unknown operation.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class UnknownOperationException extends IllegalArgumentException {

    /**
     * Creates instance of the exception.
     *
     * @param message short description of the issue
     */
    public UnknownOperationException(final String message) {
        super(message);
    }
}
