package com.xxx.calculator.cli;

import com.xxx.calculator.processor.Operator;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Function to map string line of commands to list of parsed commands.
 *
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class CommandParser implements Function<String, List<ParsedCommand>> {

    private final Function<String, Optional<Operator>> operatorFactory;

    /**
     * Create command parser.
     *
     * @param operatorFactory factory to create operators by their string presentations.
     */
    public CommandParser(final Function<String, Optional<Operator>> operatorFactory) {
        this.operatorFactory = operatorFactory;
    }

    @Override
    public List<ParsedCommand> apply(final String input) {
        final List<ParsedCommand> parsedCommands = new LinkedList<>();
        int tokenStartIndex = 0;
        for (String token : input.split(" ")) {
            if ("".equals(token)) {
                continue;
            }
            final Optional<Operator> operator = operatorFactory.apply(token);
            if (operator.isEmpty()) {
                throw new UnknownOperationException("Input has unknown operation: " + token);
            }
            parsedCommands.add(new ParsedCommand(operator.get(), token, tokenStartIndex));
            tokenStartIndex += token.length() + 1;
        }
        return parsedCommands;
    }
}
