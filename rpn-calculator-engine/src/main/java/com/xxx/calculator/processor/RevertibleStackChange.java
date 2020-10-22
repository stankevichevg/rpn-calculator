package com.xxx.calculator.processor;

import java.util.Stack;

/**
 * Represents revertible stack change, can be applied {@link #undo(Stack)} to the given execution
 * stack to revert represented operation stack change.
 */
interface RevertibleStackChange {

    /**
     * Reverts changes made by represented stack change.
     *
     * @param executionStack execution stack to revert change on
     */
    void undo(Stack<Double> executionStack);

}
