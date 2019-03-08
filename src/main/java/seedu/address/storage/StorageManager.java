package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyApplication;
import seedu.address.model.ReadOnlyRequirementCategoryList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Application data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ApplicationStorage applicationStorage;
    private UserPrefsStorage userPrefsStorage;
    private DegreePlannerListStorage degreePlannerListStorage;
    private RequirementCategoryListStorage requirementCategoryListStorage;

    public StorageManager(ApplicationStorage applicationStorage, DegreePlannerListStorage degreePlannerListStorage,
            RequirementCategoryListStorage requirementCategoryListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.applicationStorage = applicationStorage;
        this.degreePlannerListStorage = degreePlannerListStorage;
        this.requirementCategoryListStorage = requirementCategoryListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Application methods ==============================

    @Override
    public Path getApplicationFilePath() {
        return applicationStorage.getApplicationFilePath();
    }

    @Override
    public Optional<ReadOnlyApplication> readApplication() throws DataConversionException, IOException {
        return readApplication(applicationStorage.getApplicationFilePath());
    }

    @Override
    public Optional<ReadOnlyApplication> readApplication(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return applicationStorage.readApplication(filePath);
    }

    @Override
    public void saveApplication(ReadOnlyApplication application) throws IOException {
        saveApplication(application, applicationStorage.getApplicationFilePath());
    }

    @Override
    public void saveApplication(ReadOnlyApplication application, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        applicationStorage.saveApplication(application, filePath);
    }

    // ================ DegreePlannerList methods ========================

    @Override
    public Path getDegreePlannerListFilePath() {
        return degreePlannerListStorage.getDegreePlannerListFilePath();
    }

    @Override
    public Optional<ReadOnlyApplication> readDegreePlannerList() throws DataConversionException, IOException {
        return readDegreePlannerList(degreePlannerListStorage.getDegreePlannerListFilePath());
    }

    @Override
    public Optional<ReadOnlyApplication> readDegreePlannerList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return degreePlannerListStorage.readDegreePlannerList(filePath);
    }

    @Override
    public void saveDegreePlannerList(ReadOnlyApplication degreePlannerList) throws IOException {
        saveDegreePlannerList(degreePlannerList, degreePlannerListStorage.getDegreePlannerListFilePath());
    }

    @Override
    public void saveDegreePlannerList(ReadOnlyApplication degreePlannerList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        degreePlannerListStorage.saveDegreePlannerList(degreePlannerList, filePath);
    }

    // ================ RequirementCategoryList methods ============================================================

    @Override
    public Path getRequirementCategoryListFilePath() {
        return requirementCategoryListStorage.getRequirementCategoryListFilePath();
    }

    @Override
    public Optional<ReadOnlyRequirementCategoryList> readRequirementCategoryList()
            throws DataConversionException, IOException {
        return readRequirementCategoryList(requirementCategoryListStorage.getRequirementCategoryListFilePath());
    }

    @Override
    public Optional<ReadOnlyRequirementCategoryList> readRequirementCategoryList(Path filePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return requirementCategoryListStorage.readRequirementCategoryList(filePath);
    }

    @Override
    public void saveRequirementCategoryList(ReadOnlyRequirementCategoryList requirementCategoryList)
            throws IOException {
        saveRequirementCategoryList(requirementCategoryList,
                requirementCategoryListStorage.getRequirementCategoryListFilePath());
    }

    @Override
    public void saveRequirementCategoryList(ReadOnlyRequirementCategoryList requirementCategoryList, Path filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        requirementCategoryListStorage.saveRequirementCategoryList(requirementCategoryList, filePath);
    }
}
