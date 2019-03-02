package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DegreePlannerList} that keeps track of its own history.
 */
public class VersionedDegreePlannerList extends DegreePlannerList {

    private final List<ReadOnlyDegreePlannerList> plannerStateList;
    private int currentStatePointer;

    public VersionedDegreePlannerList(ReadOnlyDegreePlannerList initialState) {
        super(initialState);

        plannerStateList = new ArrayList<>();
        plannerStateList.add(new DegreePlannerList(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code DegreePlannerList} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        plannerStateList.add(new DegreePlannerList(this));
        currentStatePointer++;
        indicateModified();
    }

    private void removeStatesAfterCurrentPointer() {
        plannerStateList.subList(currentStatePointer + 1, plannerStateList.size()).clear();
    }

    /**
     * Restores the planner list to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new VersionedDegreePlannerList.NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(plannerStateList.get(currentStatePointer));
    }

    /**
     * Restores the planner list to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new VersionedDegreePlannerList.NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(plannerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has planner list states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has planner states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < plannerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedDegreePlannerList)) {
            return false;
        }

        VersionedDegreePlannerList otherVersionedPlannerList = (VersionedDegreePlannerList) other;

        // state check
        return super.equals(otherVersionedPlannerList)
                && plannerStateList.equals(otherVersionedPlannerList.plannerStateList)
                && currentStatePointer == otherVersionedPlannerList.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of addressBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of addressBookState list, unable to redo.");
        }
    }
}
