package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstModule;
import static seedu.address.testutil.TypicalModules.getTypicalApplication;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RequirementCategoryList;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {

    private final Model model =
            new ModelManager(getTypicalApplication(), new RequirementCategoryList(),
                    new UserPrefs());
    private final Model expectedModel =
            new ModelManager(getTypicalApplication(), new RequirementCategoryList(),
                    new UserPrefs());
    private final CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstModule(model);
        deleteFirstModule(model);
        model.undoApplication();
        model.undoApplication();

        deleteFirstModule(expectedModel);
        deleteFirstModule(expectedModel);
        expectedModel.undoApplication();
        expectedModel.undoApplication();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoApplication();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoApplication();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }
}
