package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private DegreePlannerStorage degreePlannerStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, DegreePlannerStorage degreePlannerStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.degreePlannerStorage = degreePlannerStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ DegreePlanner methods ==============================

    @Override
    public Path getDegreePlannerFilePath() {
        return degreePlannerStorage.getDegreePlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readDegreePlanner() throws DataConversionException, IOException {
        return readDegreePlanner(degreePlannerStorage.getDegreePlannerFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readDegreePlanner(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return degreePlannerStorage.readDegreePlanner(filePath);
    }

    @Override
    public void saveDegreePlanner(ReadOnlyAddressBook degreePlanner) throws IOException {
        saveDegreePlanner(degreePlanner, degreePlannerStorage.getDegreePlannerFilePath());
    }

    @Override
    public void saveDegreePlanner(ReadOnlyAddressBook degreePlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        degreePlannerStorage.saveDegreePlanner(degreePlanner, filePath);
    }

}
