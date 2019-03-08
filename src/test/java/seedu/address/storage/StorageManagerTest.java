package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static seedu.address.testutil.TypicalModules.getTypicalApplication;

import java.nio.file.Path;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Application;
import seedu.address.model.ReadOnlyApplication;
import seedu.address.model.UserPrefs;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        JsonApplicationStorage applicationStorage = new JsonApplicationStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));

        JsonDegreePlannerListStorage degreePlannerListStorage =
                new JsonDegreePlannerListStorage(getTempFilePath("planner"));
        JsonRequirementCategoryListStorage requirementCategoryListStorage =
                new JsonRequirementCategoryListStorage(getTempFilePath("reqCat"));
        storageManager =
                new StorageManager(applicationStorage, degreePlannerListStorage, requirementCategoryListStorage,
                        userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.getRoot().toPath().resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void applicationReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonApplicationStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonApplicationStorageTest} class.
         */
        Application original = getTypicalApplication();
        storageManager.saveApplication(original);
        ReadOnlyApplication retrieved = storageManager.readApplication().get();
        assertEquals(original, new Application(retrieved));
    }

    @Test
    public void getApplicationFilePath() {
        assertNotNull(storageManager.getApplicationFilePath());
    }

}
