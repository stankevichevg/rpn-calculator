package com.xxx.calculator.cli;

import com.xxx.calculator.Command;
import com.xxx.calculator.processor.Operator;

/**
 * @author Evgeny Stankevich {@literal <stankevich.evg@gmail.com>}.
 */
public final class ParsedCommand extends Command {

    private final String token;
    private final int position;

    /**
     * Creates parsed command to apply specified operator.
     *
     * @param operator operator to apply
     * @param token parsed token
     * @param position start token position in the origin string source
     */
    public ParsedCommand(final Operator operator, final String token, final int position) {
        super(operator);
        this.token = token;
        this.position = position;
    }

    public String getToken() {
        return token;
    }

    public int getPosition() {
        return position;
    }
}
