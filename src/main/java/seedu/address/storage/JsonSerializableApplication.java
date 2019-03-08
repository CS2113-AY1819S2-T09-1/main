package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;

import seedu.address.model.Application;
import seedu.address.model.ReadOnlyApplication;
import seedu.address.model.module.Module;

/**
 * An Immutable Application that is serializable to JSON format.
 */
@JsonRootName(value = "application")
class JsonSerializableApplication {

    public static final String MESSAGE_DUPLICATE_MODULE = "Modules list contains duplicate module(s).";

    private final List<JsonAdaptedModule> modules = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableApplication} with the given modules.
     */
    @JsonCreator
    public JsonSerializableApplication(@JsonProperty("modules") List<JsonAdaptedModule> modules) {
        this.modules.addAll(modules);
    }

    /**
     * Converts a given {@code ReadOnlyApplication} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableApplication}.
     */
    public JsonSerializableApplication(ReadOnlyApplication source) {
        modules.addAll(source.getModuleList().stream().map(JsonAdaptedModule::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Application toModelType() throws IllegalValueException {
        Application application = new Application();
        for (JsonAdaptedModule jsonAdaptedModule : modules) {
            Module module = jsonAdaptedModule.toModelType();
            if (application.hasModule(module)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MODULE);
            }
            application.addModule(module);
        }
        return application;
    }

}
