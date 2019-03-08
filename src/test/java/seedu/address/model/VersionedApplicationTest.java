package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalModules.AMY;
import static seedu.address.testutil.TypicalModules.BOB;
import static seedu.address.testutil.TypicalModules.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ApplicationBuilder;

public class VersionedApplicationTest {

    private final ReadOnlyApplication applicationWithAmy = new ApplicationBuilder().withModule(AMY).build();
    private final ReadOnlyApplication applicationWithBob = new ApplicationBuilder().withModule(BOB).build();
    private final ReadOnlyApplication applicationWithCarl = new ApplicationBuilder().withModule(CARL).build();
    private final ReadOnlyApplication emptyApplication = new ApplicationBuilder().build();

    @Test
    public void commit_singleApplication_noStatesRemovedCurrentStateSaved() {
        VersionedApplication versionedApplication = prepareApplicationList(emptyApplication);

        versionedApplication.commit();
        assertApplicationListStatus(versionedApplication,
                Collections.singletonList(emptyApplication),
                emptyApplication,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleApplicationPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);

        versionedApplication.commit();
        assertApplicationListStatus(versionedApplication,
                Arrays.asList(emptyApplication, applicationWithAmy, applicationWithBob),
                applicationWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleApplicationPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 2);

        versionedApplication.commit();
        assertApplicationListStatus(versionedApplication,
                Collections.singletonList(emptyApplication),
                emptyApplication,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleApplicationPointerAtEndOfStateList_returnsTrue() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);

        assertTrue(versionedApplication.canUndo());
    }

    @Test
    public void canUndo_multipleApplicationPointerAtStartOfStateList_returnsTrue() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 1);

        assertTrue(versionedApplication.canUndo());
    }

    @Test
    public void canUndo_singleApplication_returnsFalse() {
        VersionedApplication versionedApplication = prepareApplicationList(emptyApplication);

        assertFalse(versionedApplication.canUndo());
    }

    @Test
    public void canUndo_multipleApplicationPointerAtStartOfStateList_returnsFalse() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 2);

        assertFalse(versionedApplication.canUndo());
    }

    @Test
    public void canRedo_multipleApplicationPointerNotAtEndOfStateList_returnsTrue() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 1);

        assertTrue(versionedApplication.canRedo());
    }

    @Test
    public void canRedo_multipleApplicationPointerAtStartOfStateList_returnsTrue() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 2);

        assertTrue(versionedApplication.canRedo());
    }

    @Test
    public void canRedo_singleApplication_returnsFalse() {
        VersionedApplication versionedApplication = prepareApplicationList(emptyApplication);

        assertFalse(versionedApplication.canRedo());
    }

    @Test
    public void canRedo_multipleApplicationPointerAtEndOfStateList_returnsFalse() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);

        assertFalse(versionedApplication.canRedo());
    }

    @Test
    public void undo_multipleApplicationPointerAtEndOfStateList_success() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);

        versionedApplication.undo();
        assertApplicationListStatus(versionedApplication,
                Collections.singletonList(emptyApplication),
                applicationWithAmy,
                Collections.singletonList(applicationWithBob));
    }

    @Test
    public void undo_multipleApplicationPointerNotAtStartOfStateList_success() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 1);

        versionedApplication.undo();
        assertApplicationListStatus(versionedApplication,
                Collections.emptyList(),
                emptyApplication,
                Arrays.asList(applicationWithAmy, applicationWithBob));
    }

    @Test
    public void undo_singleApplication_throwsNoUndoableStateException() {
        VersionedApplication versionedApplication = prepareApplicationList(emptyApplication);

        assertThrows(VersionedApplication.NoUndoableStateException.class, versionedApplication::undo);
    }

    @Test
    public void undo_multipleApplicationPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 2);

        assertThrows(VersionedApplication.NoUndoableStateException.class, versionedApplication::undo);
    }

    @Test
    public void redo_multipleApplicationPointerNotAtEndOfStateList_success() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 1);

        versionedApplication.redo();
        assertApplicationListStatus(versionedApplication,
                Arrays.asList(emptyApplication, applicationWithAmy),
                applicationWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleApplicationPointerAtStartOfStateList_success() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 2);

        versionedApplication.redo();
        assertApplicationListStatus(versionedApplication,
                Collections.singletonList(emptyApplication),
                applicationWithAmy,
                Collections.singletonList(applicationWithBob));
    }

    @Test
    public void redo_singleApplication_throwsNoRedoableStateException() {
        VersionedApplication versionedApplication = prepareApplicationList(emptyApplication);

        assertThrows(VersionedApplication.NoRedoableStateException.class, versionedApplication::redo);
    }

    @Test
    public void redo_multipleApplicationPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedApplication versionedApplication = prepareApplicationList(
                emptyApplication, applicationWithAmy, applicationWithBob);

        assertThrows(VersionedApplication.NoRedoableStateException.class, versionedApplication::redo);
    }

    @Test
    public void equals() {
        VersionedApplication versionedApplication = prepareApplicationList(applicationWithAmy, applicationWithBob);

        // same values -> returns true
        VersionedApplication copy = prepareApplicationList(applicationWithAmy, applicationWithBob);
        assertTrue(versionedApplication.equals(copy));

        // same object -> returns true
        assertTrue(versionedApplication.equals(versionedApplication));

        // null -> returns false
        assertFalse(versionedApplication.equals(null));

        // different types -> returns false
        assertFalse(versionedApplication.equals(1));

        // different state list -> returns false
        VersionedApplication differentApplicationList = prepareApplicationList(applicationWithBob, applicationWithCarl);
        assertFalse(versionedApplication.equals(differentApplicationList));

        // different current pointer index -> returns false
        VersionedApplication differentCurrentStatePointer = prepareApplicationList(
                applicationWithAmy, applicationWithBob);
        shiftCurrentStatePointerLeftwards(versionedApplication, 1);
        assertFalse(versionedApplication.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedApplication} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedApplication#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedApplication#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertApplicationListStatus(VersionedApplication versionedApplication,
                                             List<ReadOnlyApplication> expectedStatesBeforePointer,
                                             ReadOnlyApplication expectedCurrentState,
                                             List<ReadOnlyApplication> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new Application(versionedApplication), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedApplication.canUndo()) {
            versionedApplication.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyApplication expectedApplication : expectedStatesBeforePointer) {
            assertEquals(expectedApplication, new Application(versionedApplication));
            versionedApplication.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyApplication expectedApplication : expectedStatesAfterPointer) {
            versionedApplication.redo();
            assertEquals(expectedApplication, new Application(versionedApplication));
        }

        // check that there are no more states after pointer
        assertFalse(versionedApplication.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedApplication.undo());
    }

    /**
     * Creates and returns a {@code VersionedApplication} with the {@code applicationStates} added into it, and the
     * {@code VersionedApplication#currentStatePointer} at the end of list.
     */
    private VersionedApplication prepareApplicationList(ReadOnlyApplication... applicationStates) {
        assertFalse(applicationStates.length == 0);

        VersionedApplication versionedApplication = new VersionedApplication(applicationStates[0]);
        for (int i = 1; i < applicationStates.length; i++) {
            versionedApplication.resetData(applicationStates[i]);
            versionedApplication.commit();
        }

        return versionedApplication;
    }

    /**
     * Shifts the {@code versionedApplication#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedApplication versionedApplication, int count) {
        for (int i = 0; i < count; i++) {
            versionedApplication.undo();
        }
    }
}
