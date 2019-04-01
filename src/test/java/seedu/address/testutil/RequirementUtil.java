package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Set;

import seedu.address.logic.commands.RequirementRemoveCommand;
import seedu.address.model.module.Code;
import seedu.address.model.module.Name;

/**
 * A utility class for RequirementCategory.
 */
public class RequirementUtil {

    /**
     * Returns an remove command string for removing the {@code code}.
     */
    public static String getRequirementRemoveCommand(Name requirementCategoryName, Set<Code> codeSet) {
        StringBuilder sb = new StringBuilder();
        sb.append(RequirementRemoveCommand.COMMAND_WORD).append(" ");
        sb.append(PREFIX_NAME).append(requirementCategoryName.toString()).append(" ");
        codeSet.stream().forEach(s -> sb.append(PREFIX_CODE).append(s.value).append(" "));
        return sb.toString();
    }

}
