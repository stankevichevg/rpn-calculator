package com.xxx.calculator.cli;

import com.xxx.calculator.processor.Operator;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.xxx.calculator.processor.Operators.clear;
import static com.xxx.calculator.processor.Operators.divide;
import static com.xxx.calculator.processor.Operators.minus;
import static com.xxx.calculator.processor.Operators.multiply;
import static com.xxx.calculator.processor.Operators.plus;
import static com.xxx.calculator.processor.Operators.pushNumber;
import static com.xxx.calculator.processor.Operators.sqrt;
import static com.xxx.calculator.processor.Operators.undo;
import static java.util.Map.of;

/**
 * Factory to create operators by their string representations.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class OperatorFactory implements Function<String, Optional<Operator>> {

    public static final OperatorFactory INSTANCE = new OperatorFactory();

    private OperatorFactory() {
    }

    private static final Map<String, Operator> OPERATORS = of(
        "+", plus(),
        "-", minus(),
        "*", multiply(),
        "/", divide(),
        "sqrt", sqrt(),
        "undo", undo(),
        "clear", clear()
    );

    @Override
    public Optional<Operator> apply(final String token) {
        if (OPERATORS.containsKey(token)) {
            return Optional.of(OPERATORS.get(token));
        } else {
            try {
                return Optional.of(pushNumber(Double.parseDouble(token)));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
    }
}
