package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyApplication;
import seedu.address.model.ReadOnlyRequirementCategoryList;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */

public interface Storage
        extends ApplicationStorage, UserPrefsStorage, DegreePlannerListStorage, RequirementCategoryListStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getApplicationFilePath();

    @Override
    Optional<ReadOnlyApplication> readApplication() throws DataConversionException, IOException;

    @Override
    void saveApplication(ReadOnlyApplication application) throws IOException;

    @Override
    Path getDegreePlannerListFilePath();

    @Override
    void saveDegreePlannerList(ReadOnlyApplication degreePlannerList) throws IOException;

    @Override
    Path getRequirementCategoryListFilePath();

    @Override
    Optional<ReadOnlyRequirementCategoryList> readRequirementCategoryList() throws DataConversionException, IOException;

    @Override
    void saveRequirementCategoryList(ReadOnlyRequirementCategoryList requirementCategoryList) throws IOException;


}
