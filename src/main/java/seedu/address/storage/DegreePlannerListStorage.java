package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Application;
import seedu.address.model.ReadOnlyApplication;

/**
 * Represents a storage for {@link Application}.
 */
public interface DegreePlannerListStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getDegreePlannerListFilePath();

    /**
     * Returns DegreePlannerList data as a {@link ReadOnlyApplication}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyApplication> readDegreePlannerList() throws DataConversionException, IOException;

    /**
     * @see #getDegreePlannerListFilePath()
     */
    Optional<ReadOnlyApplication> readDegreePlannerList(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyApplication} to the storage.
     *
     * @param degreePlannerList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDegreePlannerList(ReadOnlyApplication degreePlannerList) throws IOException;

    /**
     * @see #saveDegreePlannerList(ReadOnlyApplication)
     */
    void saveDegreePlannerList(ReadOnlyApplication degreePlannerList, Path filePath) throws IOException;
}
