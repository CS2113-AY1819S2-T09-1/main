package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalModules.getTypicalApplication;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Application;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RequirementCategoryList;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyApplication_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitApplication();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyApplication_success() {
        Model model = new ModelManager(getTypicalApplication(), new RequirementCategoryList(),
                new UserPrefs());
        Model expectedModel =
                new ModelManager(getTypicalApplication(), new RequirementCategoryList(),
                        new UserPrefs());
        expectedModel.setApplication(new Application());
        expectedModel.commitApplication();

        assertCommandSuccess(new ClearCommand(), model, commandHistory, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
