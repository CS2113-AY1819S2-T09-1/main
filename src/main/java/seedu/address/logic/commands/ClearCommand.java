package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.InitialDataUtil.getInitialAddressBook;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Module list has been cleared!";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.setAddressBook(getInitialAddressBook());
        model.commitAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
