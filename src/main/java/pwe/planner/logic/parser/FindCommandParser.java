package pwe.planner.logic.parser;

import static java.util.Objects.requireNonNull;
import static pwe.planner.commons.core.LogsCenter.getLogger;
import static pwe.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pwe.planner.logic.commands.FindCommand.MESSAGE_USAGE;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_CODE;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_CREDITS;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_SEMESTER;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import pwe.planner.logic.commands.FindCommand;
import pwe.planner.logic.parser.exceptions.BooleanParserException;
import pwe.planner.logic.parser.exceptions.BooleanParserPredicateException;
import pwe.planner.logic.parser.exceptions.ParseException;
import pwe.planner.model.module.Module;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = getLogger(FindCommandParser.class);
    private static final List<Prefix> PREFIXES = List.of(
            PREFIX_NAME,
            PREFIX_CODE,
            PREFIX_CREDITS,
            PREFIX_TAG,
            PREFIX_SEMESTER
    );

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isEmpty()) {
            throw new ParseException(FindCommand.MESSAGE_USAGE);
        }

        try {
            BooleanExpressionParser<Module> expressionParser = new BooleanExpressionParser<>(args, PREFIXES);
            Predicate<Module> predicate = expressionParser.parse();
            return new FindCommand(predicate);
        } catch (BooleanParserPredicateException predicateException) {
            logger.warning(predicateException.getMessage());
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        } catch (BooleanParserException parserException) {
            throw new ParseException(parserException.getMessage());
        }
    }

}
