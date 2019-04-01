package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COREQUISITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CREDITS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditModuleDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Code;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        if (args.isEmpty()) {
            throw new ParseException(EditCommand.MESSAGE_USAGE);
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_NAME, PREFIX_CREDITS, PREFIX_CODE, PREFIX_TAG, PREFIX_COREQUISITE
        );

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditModuleDescriptor editModuleDescriptor = new EditCommand.EditModuleDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editModuleDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_CREDITS).isPresent()) {
            editModuleDescriptor.setCredits(ParserUtil.parseCredits(argMultimap.getValue(PREFIX_CREDITS).get()));
        }
        if (argMultimap.getValue(PREFIX_CODE).isPresent()) {
            editModuleDescriptor.setCode(ParserUtil.parseCode(argMultimap.getValue(PREFIX_CODE).get()));
        }
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editModuleDescriptor::setTags);
        parseCorequisitesForEdit(argMultimap.getAllValues(PREFIX_COREQUISITE))
                .ifPresent(editModuleDescriptor::setCorequisites);

        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editModuleDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

    /**
     * Parses {@code Collection<String> corequisites} into a {@code Set<Code>} if {@code corequisites} is non-empty.
     * If {@code corequisites} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Code>} containing zero tags.
     */
    private Optional<Set<Code>> parseCorequisitesForEdit(Collection<String> corequisites) throws ParseException {
        assert corequisites != null;

        if (corequisites.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> corequisitesSet = (corequisites.size() == 1 && corequisites.contains(""))
                ? Collections.emptySet()
                : corequisites;
        return Optional.of(ParserUtil.parseCorequisites(corequisitesSet));
    }
}
