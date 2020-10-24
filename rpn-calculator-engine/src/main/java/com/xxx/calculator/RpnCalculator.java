package com.xxx.calculator;

import com.xxx.calculator.processor.CalculatorProcessor;
import com.xxx.calculator.processor.InsufficientStackSizeException;

import java.util.List;
import java.util.function.Function;

/**
 * Implementation of the specified RPN calculator.
 * Calculator modelled as a function of input to output.
 *
 * @param <C> type of commands
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class RpnCalculator<C extends Command> implements Function<List<C>, CalculationResult<C>> {

    private final CalculatorProcessor processor;

    /**
     * Creates RPN calculator instance with provided processor and output.
     *
     * @param processor operations processor
     */
    public RpnCalculator(final CalculatorProcessor processor) {
        this.processor = processor;
    }

    /**
     * Starts calculation for the given input commands.
     *
     * @param input list of commands to do calculation
     * @return result of calculation
     */
    @Override
    public CalculationResult<C> apply(final List<C> input) {
        for (C command : input) {
            try {
                processor.execute(command.getOperator());
            } catch (InsufficientStackSizeException e) {
                return new CalculationResult<>(processor.stackElements(), command);
            }
        }
        return new CalculationResult<>(processor.stackElements());
    }

}
