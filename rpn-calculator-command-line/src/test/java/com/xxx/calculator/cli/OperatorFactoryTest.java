package com.xxx.calculator.cli;

import com.xxx.calculator.processor.Operator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static com.xxx.calculator.cli.OperatorFactory.INSTANCE;
import static com.xxx.calculator.processor.Operators.clear;
import static com.xxx.calculator.processor.Operators.divide;
import static com.xxx.calculator.processor.Operators.minus;
import static com.xxx.calculator.processor.Operators.multiply;
import static com.xxx.calculator.processor.Operators.plus;
import static com.xxx.calculator.processor.Operators.pushNumber;
import static com.xxx.calculator.processor.Operators.undo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class OperatorFactoryTest {

    @Test
    public void whenUnknownCommandThenEmptyOperatorOptional() {
        assertThat(INSTANCE.apply("unknown").isPresent(), is(false));
    }

    @Test
    public void whenKnownCommandThenCorrectOperator() {
        assertThat(INSTANCE.apply("+").get(), is(Matchers.equalTo(plus())));
        assertThat(INSTANCE.apply("-").get(), is(Matchers.equalTo(minus())));
        assertThat(INSTANCE.apply("*").get(), is(Matchers.equalTo(multiply())));
        assertThat(INSTANCE.apply("/").get(), is(Matchers.equalTo(divide())));
        assertThat(INSTANCE.apply("undo").get(), is(Matchers.equalTo(undo())));
        assertThat(INSTANCE.apply("clear").get(), is(Matchers.equalTo(clear())));
        final Operator pushNumber = INSTANCE.apply("12.123").get();
        assertThat(pushNumber, Matchers.is(Matchers.instanceOf(pushNumber(12.123).getClass())));
    }

}
