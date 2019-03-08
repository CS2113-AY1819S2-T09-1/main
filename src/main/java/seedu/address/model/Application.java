package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.planner.DegreePlanner;
import seedu.address.model.planner.UniqueDegreePlannerList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameModule comparison)
 */
public class Application implements ReadOnlyApplication {

    private final UniqueModuleList modules;
    private final UniqueDegreePlannerList degreePlanners;
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
        degreePlanners = new UniqueDegreePlannerList();
    }

    public Application() {}

    /**
     * Creates an Application using the Modules in the {@code toBeCopied}
     */
    public Application(ReadOnlyApplication toBeCopied) {
        this();
        resetData(toBeCopied);
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
     * Resets the existing data of this {@code Application} with {@code newData}.
     */
    public void resetData(ReadOnlyApplication newData) {
        requireNonNull(newData);
        setModules(newData.getModuleList());
        setDegreePlanner(newData.getDegreePlannerList());
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
     * Removes {@code key} from this {@code Application}.
     * {@code key} must exist in the address book.
     */
    public void removeModule(Module key) {
        modules.remove(key);
        indicateModified();
    }

    /**
     * Replaces the contents of the degreePlanner list with {@code degreePlanners}.
     * {@code degreePlanners} must not contain duplicate degreePlanners.
     */
    public void setDegreePlanners(List<DegreePlanner> degreePlanners) {
        this.degreePlanners.setDegreePlanners(degreePlanners);
    }

    //// planner-level operations

    /**
     * Returns true if an degreePlanner with the same identity as {@code degreePlanner} exists in the degreePlanner.
     */
    public boolean hasDegreePlanner(DegreePlanner degreePlanner) {
        requireNonNull(degreePlanner);
        return degreePlanners.contains(degreePlanner);
    }

    /**
     * Adds a degreePlanner to the degreePlanner list.
     * The degreePlanner must not already exist in the degreePlanner list.
     */
    public void addDegreePlanner(DegreePlanner p) {
        degreePlanners.add(p);
    }

    /**
     * Replaces the contents of the degreePlanner list with {@code degreePlanners}.
     * {@code degreePlanners} must not contain duplicate degreePlanners.
     */
    private void setDegreePlanner(List<DegreePlanner> degreePlanners) {
        this.degreePlanners.setDegreePlanners(degreePlanners);
    }

    /**
     * Replaces the given planner {@code target} in the list with {@code editedDegreePlanner}.
     * {@code target} must exist in the degreePlanner list.
     * The identity of {@code editedDegreePlanner} must not be the same as another existing degreePlanner in the
     * degreePlanner list.
     */
    public void setDegreePlanner(DegreePlanner target, DegreePlanner editedDegreePlanner) {
        requireNonNull(editedDegreePlanner);
        degreePlanners.setDegreePlanner(target, editedDegreePlanner);
    }

    /**
     * Removes {@code key} from this {@code DegreePlannerList}.
     * {@code key} must exist in the degreePlanner list.
     */
    public void removeDegreePlanner(DegreePlanner key) {
        degreePlanners.remove(key);
    }

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
        return modules.asUnmodifiableObservableList().size() + " modules"
                + degreePlanners.asUnmodifiableObservableList().size() + "degreePlanners";
        // TODO: refine later
    }

    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<DegreePlanner> getDegreePlannerList() {
        return degreePlanners.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Application // instanceof handles nulls
                && modules.equals(((Application) other).modules)
                && degreePlanners.equals(((Application) other).degreePlanners));
    }

    @Override
    public int hashCode() {
        return Objects.hash(modules.hashCode(), degreePlanners.hashCode());
    }
}
