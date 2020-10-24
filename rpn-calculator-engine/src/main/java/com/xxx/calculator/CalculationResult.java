package com.xxx.calculator;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * Contains state of calculator execution stack as a result of calculation.
 * If calculation failed then method {@link #isFailed()} will return {@code true}
 * and failed command can be retrieved from the result using {@link #failedCommand()}.
 *
 * @param <C> type of commands
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CalculationResult<C extends Command> {

    private final List<Double> stackState;
    private final C failedCommand;

    /**
     * Creates result object for successful calculation.
     *
     * @param stackState stack state
     */
    public CalculationResult(final List<Double> stackState) {
        this(stackState, null);
    }

    /**
     * Creates result object for failed calculation.
     *
     * @param stackState stack state
     * @param failedCommand command on which calculation failed
     */
    public CalculationResult(final List<Double> stackState, final C failedCommand) {
        this.stackState = stackState;
        this.failedCommand = failedCommand;
    }

    /**
     * Execution stack state.
     *
     * @return execution stack state
     */
    public List<Double> getStackState() {
        return stackState;
    }

    /**
     * Returns {@link Optional} with failed command for failed calculation result.
     *
     * @return failed command optional
     */
    public Optional<C> failedCommand() {
        return ofNullable(failedCommand);
    }

    /**
     * Returns {@code true} if this result is result of a failed calculation.
     *
     * @return {@code true} if this result is result of a failed calculation.
     */
    public boolean isFailed() {
        return failedCommand != null;
    }

}
