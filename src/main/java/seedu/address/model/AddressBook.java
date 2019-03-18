package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.requirement.RequirementCategory;
import seedu.address.model.requirement.UniqueRequirementCategoryList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueModuleList modules;
    private final UniqueRequirementCategoryList requirementCategories;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        modules = new UniqueModuleList();
        requirementCategories = new UniqueRequirementCategoryList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Modules in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setModules(newData.getModuleList());
        setRequirementCategories(newData.getRequirementCategoryList());
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the module list with {@code modules}.
     * {@code modules} must not contain duplicate modules.
     */
    public void setModules(List<Module> modules) {
        this.modules.setModules(modules);
        indicateModified();
    }

    /**
     * Replaces the contents of the requirement list with {@code requirement}.
     * {@code requirement} must not contain duplicate requirement.
     */
    public void setRequirementCategories(List<RequirementCategory> requirementCategories) {
        this.requirementCategories.setRequirementCategories(requirementCategories);
        indicateModified();
    }

    //// module-level operations

    /**
     * Returns true if a module with the same identity as {@code module} exists in the address book.
     */
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return modules.contains(module);
    }

    /**
     * Adds a module to the address book.
     * The module must not already exist in the address book.
     */
    public void addModule(Module p) {
        modules.add(p);
        indicateModified();
    }

    /**
     * Replaces the given module {@code target} in the list with {@code editedModule}.
     * {@code target} must exist in the address book.
     * The module identity of {@code editedModule} must not be the same as another existing module in the address book.
     */
    public void setModule(Module target, Module editedModule) {
        requireNonNull(editedModule);

        modules.setModule(target, editedModule);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
        indicateModified();
    }

    //// planner-level operations

    /**
     * Returns true if an requirement with the same identity as {@code requirement} exists in the
     * requirement.
     */
    public boolean hasRequirementCategory(RequirementCategory requirementCategory) {
        requireNonNull(requirementCategory);
        return requirementCategories.contains(requirementCategory);
    }

    /**
     * Adds a requirement to the requirementCategoryList.
     * The requirement must not already exist in the requirementCategoryList.
     */
    public void addRequirementCategory(RequirementCategory requirementCategory) {
        requirementCategories.add(requirementCategory);
    }

    /**
     * Replaces the given requirement {@code target} in the list with {@code editedRequirementCategory}.
     * {@code target} must exist in the requirement list.
     * The identity of {@code editedRequirementCategory} must not be the same as another existing requirement
     * in the
     * requirement list.
     */
    public void setRequirementCategory(RequirementCategory target, RequirementCategory editedRequirementCategory) {
        requireNonNull(editedRequirementCategory);

        requirementCategories.setRequirementCategory(target, editedRequirementCategory);
    }

    /**
     * Removes {@code key} from this {@code RequirementCategoryList}.
     * {@code key} must exist in the requirement list.
     */
    public void removeRequirementCategory(RequirementCategory key) {
        requirementCategories.remove(key);
    }

    /**
     * Adds module to the given requirement category.
     * {@code requirementCategoryModule} must not already exist in the requirementCategoryList.
     */
    public void addModuleToRequirementCategory(RequirementCategory requirementCategoryModule) {
        requireNonNull(requirementCategoryModule);
        requirementCategories.addModuleToRequirementCategory(requirementCategoryModule);
    }

    /**
     * Returns true if a module with the same identity as {@code requirementCategory} exists in the
     * requirement category to be added to.
     */
    public boolean isModuleInRequirementCategory(RequirementCategory requirementCategory) {
        requireNonNull(requirementCategory);
        return requirementCategories.isModuleInRequirementCategory(requirementCategory);
    }

    /**
     * Returns false if a module with the same identity as {@code requirementCategory} does not exists
     * in the current moduleList.
     */
    //TODO refine this method to use methods in PR#91
    public boolean doesModuleExistInApplication(RequirementCategory requirementCategory, Model model) {
        requireNonNull(requirementCategory);
        return requirementCategories.doesModuleExistInApplication(requirementCategory, model);
    }

    //// listener methods

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return modules.asUnmodifiableObservableList().size() + " modules \n"
                + requirementCategories.asUnmodifiableObservableList().size() + " requirementCategories";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<RequirementCategory> getRequirementCategoryList() {
        return requirementCategories.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && modules.equals(((AddressBook) other).modules)
                && requirementCategories.equals(((AddressBook) other).requirementCategories));
    }

    @Override
    public int hashCode() {
        return Objects.hash(modules.hashCode(), requirementCategories.hashCode());
    }
}
