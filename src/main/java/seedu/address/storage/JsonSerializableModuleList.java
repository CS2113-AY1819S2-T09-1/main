package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Module;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableModuleList {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableModuleList} with the given modules.
     */

    @JsonCreator
    public JsonSerializableModuleList(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableModuleList}.
     */
    public JsonSerializableModuleList(ReadOnlyAddressBook source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public ObservableList<Module> toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (addressBook.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            addressBook.addModule(module);
        }
        return addressBook.getModuleList();
    }

}
