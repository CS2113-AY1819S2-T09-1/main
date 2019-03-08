package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.planner.DegreePlanner;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyApplication extends Observable {

    /**
     * Returns an unmodifiable view of the modules list.
     * This list will not contain any duplicate modules.
     */
    ObservableList<Module> getModuleList();

    /**
     * Returns an unmodifiable view of the degreePlanners list.
     * This list will not contain any duplicate degreePlanners.
     */
    ObservableList<DegreePlanner> getDegreePlannerList();
}
