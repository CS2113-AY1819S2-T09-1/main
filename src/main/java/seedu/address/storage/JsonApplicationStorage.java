package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyApplication;

/**
 * A class to access Application data stored as a json file on the hard disk.
 */
public class JsonApplicationStorage implements ApplicationStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonApplicationStorage.class);

    private Path filePath;

    public JsonApplicationStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getApplicationFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyApplication> readApplication() throws DataConversionException {
        return readApplication(filePath);
    }

    /**
     * Similar to {@link #readApplication()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyApplication> readApplication(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableApplication> jsonApplication = JsonUtil.readJsonFile(
                filePath, JsonSerializableApplication.class);
        if (!jsonApplication.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonApplication.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveApplication(ReadOnlyApplication application) throws IOException {
        saveApplication(application, filePath);
    }

    /**
     * Similar to {@link #saveApplication(ReadOnlyApplication)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveApplication(ReadOnlyApplication application, Path filePath) throws IOException {
        requireNonNull(application);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableApplication(application), filePath);
    }

}
