package pwe.planner.logic.parser;

import static pwe.planner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static pwe.planner.logic.commands.CommandTestUtil.CODE_DESC_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.CODE_DESC_BOB;
import static pwe.planner.logic.commands.CommandTestUtil.CREDITS_DESC_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.CREDITS_DESC_BOB;
import static pwe.planner.logic.commands.CommandTestUtil.INVALID_CODE_DESC;
import static pwe.planner.logic.commands.CommandTestUtil.INVALID_CREDITS_DESC;
import static pwe.planner.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static pwe.planner.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static pwe.planner.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static pwe.planner.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_CODE_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_CODE_BOB;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_CREDITS_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_CREDITS_BOB;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static pwe.planner.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static pwe.planner.logic.parser.CliSyntax.PREFIX_TAG;
import static pwe.planner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static pwe.planner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static pwe.planner.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static pwe.planner.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static pwe.planner.testutil.TypicalIndexes.INDEX_THIRD_MODULE;

import org.junit.Test;

import pwe.planner.commons.core.index.Index;
import pwe.planner.logic.commands.EditCommand;
import pwe.planner.logic.commands.EditCommand.EditModuleDescriptor;
import pwe.planner.model.module.Code;
import pwe.planner.model.module.Credits;
import pwe.planner.model.module.Name;
import pwe.planner.model.tag.Tag;
import pwe.planner.testutil.EditModuleDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_CREDITS_DESC, Credits.MESSAGE_CONSTRAINTS); // invalid credits
        assertParseFailure(parser, "1" + INVALID_CODE_DESC, Code.MESSAGE_CONSTRAINTS); // invalid code
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // valid credits followed by invalid credits. The test case for invalid credits followed by valid credits
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + CREDITS_DESC_BOB + INVALID_CREDITS_DESC, Credits.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Module} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CODE_DESC + INVALID_NAME_DESC + VALID_CODE_AMY + VALID_CREDITS_AMY,
                Code.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MODULE;
        String userInput = targetIndex.getOneBased() + CREDITS_DESC_BOB + TAG_DESC_HUSBAND + CODE_DESC_AMY
                + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_AMY)
                .withCredits(VALID_CREDITS_BOB).withCode(VALID_CODE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // credits
        userInput = targetIndex.getOneBased() + CREDITS_DESC_AMY;
        descriptor = new EditModuleDescriptorBuilder().withCredits(VALID_CREDITS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // code
        userInput = targetIndex.getOneBased() + CODE_DESC_AMY;
        descriptor = new EditModuleDescriptorBuilder().withCode(VALID_CODE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditModuleDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + CREDITS_DESC_AMY + CODE_DESC_AMY + TAG_DESC_FRIEND
                + CREDITS_DESC_AMY + CODE_DESC_AMY + TAG_DESC_FRIEND + CREDITS_DESC_BOB + CODE_DESC_BOB
                + TAG_DESC_HUSBAND;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withCredits(VALID_CREDITS_BOB)
                .withCode(VALID_CODE_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + INVALID_CREDITS_DESC + CREDITS_DESC_BOB;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withCredits(VALID_CREDITS_BOB).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_CREDITS_DESC + CODE_DESC_BOB + CREDITS_DESC_BOB;
        descriptor = new EditModuleDescriptorBuilder().withCredits(VALID_CREDITS_BOB).withCode(VALID_CODE_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
