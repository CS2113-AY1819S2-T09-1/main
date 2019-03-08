package seedu.address.storage;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.Application;
import seedu.address.testutil.TypicalModules;

public class JsonSerializableApplicationTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_MODULES_FILE = TEST_DATA_FOLDER.resolve("typicalModulesAddressBook.json");
    private static final Path INVALID_MODULE_FILE = TEST_DATA_FOLDER.resolve("invalidModuleAddressBook.json");
    private static final Path DUPLICATE_MODULE_FILE = TEST_DATA_FOLDER.resolve("duplicateModuleAddressBook.json");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_typicalModulesFile_success() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(TYPICAL_MODULES_FILE,
                JsonSerializableApplication.class).get();
        Application applicationFromFile = dataFromFile.toModelType();
        Application typicalModulesApplication = TypicalModules.getTypicalApplication();
        assertEquals(applicationFromFile, typicalModulesApplication);
    }

    @Test
    public void toModelType_invalidModuleFile_throwsIllegalValueException() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(INVALID_MODULE_FILE,
                JsonSerializableApplication.class).get();
        thrown.expect(IllegalValueException.class);
        dataFromFile.toModelType();
    }

    @Test
    public void toModelType_duplicateModules_throwsIllegalValueException() throws Exception {
        JsonSerializableApplication dataFromFile = JsonUtil.readJsonFile(DUPLICATE_MODULE_FILE,
                JsonSerializableApplication.class).get();
        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(JsonSerializableApplication.MESSAGE_DUPLICATE_MODULE);
        dataFromFile.toModelType();
    }

}
