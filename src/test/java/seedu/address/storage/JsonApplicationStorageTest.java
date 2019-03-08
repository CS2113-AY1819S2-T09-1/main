package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalModules.ALICE;
import static seedu.address.testutil.TypicalModules.HOON;
import static seedu.address.testutil.TypicalModules.IDA;
import static seedu.address.testutil.TypicalModules.getTypicalApplication;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Application;
import seedu.address.model.ReadOnlyApplication;

public class JsonApplicationStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readApplication_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readApplication(null);
    }

    private java.util.Optional<ReadOnlyApplication> readApplication(String filePath) throws Exception {
        return new JsonApplicationStorage(Paths.get(filePath)).readApplication(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readApplication("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() throws Exception {

        thrown.expect(DataConversionException.class);
        readApplication("notJsonFormatAddressBook.json");

        // IMPORTANT: Any code below an exception-throwing line (like the one above) will be ignored.
        // That means you should not have more than one exception test in one method
    }

    @Test
    public void readApplication_invalidModuleApplication_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readApplication("invalidModuleAddressBook.json");
    }

    @Test
    public void readApplication_invalidAndValidModuleApplication_throwDataConversionException() throws Exception {
        thrown.expect(DataConversionException.class);
        readApplication("invalidAndValidModuleAddressBook.json");
    }

    @Test
    public void readAndSaveApplication_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve("TempAddressBook.json");
        Application original = getTypicalApplication();
        JsonApplicationStorage jsonApplicationStorage = new JsonApplicationStorage(filePath);

        // Save in new file and read back
        jsonApplicationStorage.saveApplication(original, filePath);
        ReadOnlyApplication readBack = jsonApplicationStorage.readApplication(filePath).get();
        assertEquals(original, new Application(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addModule(HOON);
        original.removeModule(ALICE);
        jsonApplicationStorage.saveApplication(original, filePath);
        readBack = jsonApplicationStorage.readApplication(filePath).get();
        assertEquals(original, new Application(readBack));

        // Save and read without specifying file path
        original.addModule(IDA);
        jsonApplicationStorage.saveApplication(original); // file path not specified
        readBack = jsonApplicationStorage.readApplication().get(); // file path not specified
        assertEquals(original, new Application(readBack));

    }

    @Test
    public void saveApplication_nullApplication_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveApplication(null, "SomeFile.json");
    }

    /**
     * Saves {@code Application} at the specified {@code filePath}.
     */
    private void saveApplication(ReadOnlyApplication application, String filePath) {
        try {
            new JsonApplicationStorage(Paths.get(filePath))
                    .saveApplication(application, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveApplication_nullFilePath_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveApplication(new Application(), null);
    }
}
