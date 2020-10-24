package com.xxx.calculator.cli;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public class CommandParserTest {

    private final CommandParser commandParser = new CommandParser(OperatorFactory.INSTANCE);

    @Test
    public void whenGivenCorrectLineThenParsedCorrectly() {
        final List<ParsedCommand> commands = commandParser.apply("1  2 10.45 + -");
        assertThat(commands, Matchers.hasSize(5));
        assertThat(commands.get(0).getPosition(), is(0));
        assertThat(commands.get(0).getToken(), is("1"));
        assertThat(commands.get(1).getPosition(), is(2));
        assertThat(commands.get(1).getToken(), is("2"));
        assertThat(commands.get(2).getPosition(), is(4));
        assertThat(commands.get(2).getToken(), is("10.45"));
        assertThat(commands.get(3).getPosition(), is(10));
        assertThat(commands.get(3).getToken(), is("+"));
        assertThat(commands.get(4).getPosition(), is(12));
        assertThat(commands.get(4).getToken(), is("-"));
    }

    @Test
    public void whenUnknownCommandThenThrowsException() {
        assertThrows(UnknownOperationException.class, () -> {
            commandParser.apply("1 2 3 unknown");
        });
    }

}
