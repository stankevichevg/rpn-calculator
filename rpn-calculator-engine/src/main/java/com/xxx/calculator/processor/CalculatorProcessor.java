package com.xxx.calculator.processor;

import java.util.List;
import java.util.Stack;

import static java.util.Collections.unmodifiableList;

/**
 * Models calculator processor. State can be changed by executing of operators using {@link #execute(Operator)}.
 * At any time current state of execution stack can be observed using {@link #stackElementsView()} method.
 *
 * Internally it holds two data structures:
 * 1. Current execution stack with Double numbers (to have required at least 15 decimal places of precision);
 * 2. Stack changes history as {@link RevertibleStackChange} objects.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class CalculatorProcessor {

    private final Stack<Double> executionStack;
    private final Stack<RevertibleStackChange> history;

    /**
     * Creates calculator processor with empty state.
     */
    public CalculatorProcessor() {
        this(new Stack<>(), new Stack<>());
    }

    CalculatorProcessor(final Stack<Double> executionStack, final Stack<RevertibleStackChange> history) {
        this.executionStack = executionStack;
        this.history = history;
    }

    /**
     * Executes given operator and stores side effect to the internal history log.
     *
     * @param operator operator to apply
     */
    public void execute(final Operator operator) throws InsufficientStackSizeException {
        operator.operate(this).ifPresent(history::push);
    }

    /**
     * List view of the execution stack elements in order from bottom to top.
     * Returned object can be used to observe execution stack state at different moments,
     * if execution stack state changes view will mimic these changes automatically.
     *
     * Note that view does not provide access to change execution stack state,
     * it can be achieved only by executing an operator.
     *
     * @return list of the execution stack elements
     */
    public List<Double> stackElementsView() {
        return unmodifiableList(executionStack);
    }

    void undo() {
        history.pop().undo(executionStack);
    }

    void clear() {
        executionStack.clear();
        history.clear();
    }

    void push(final Double number) {
        executionStack.push(number);
    }

    Double pop() {
        return executionStack.pop();
    }

    int executionStackSize() {
        return executionStack.size();
    }

}
