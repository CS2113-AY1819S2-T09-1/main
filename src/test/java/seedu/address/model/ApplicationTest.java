package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalModules.ALICE;
import static seedu.address.testutil.TypicalModules.getTypicalApplication;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.DuplicateModuleException;
import seedu.address.model.planner.DegreePlanner;
import seedu.address.testutil.ModuleBuilder;

public class ApplicationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Application application = new Application();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), application.getModuleList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        application.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyApplication_replacesData() {
        Application newData = getTypicalApplication();
        application.resetData(newData);
        assertEquals(newData, application);
    }

    @Test
    public void resetData_withDuplicateModules_throwsDuplicateModuleException() {
        // Two modules with the same identity fields
        Module editedAlice = new ModuleBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Module> newModules = Arrays.asList(ALICE, editedAlice);
        ApplicationStub newData = new ApplicationStub(newModules);

        thrown.expect(DuplicateModuleException.class);
        application.resetData(newData);
    }

    @Test
    public void hasModule_nullModule_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        application.hasModule(null);
    }

    @Test
    public void hasModule_moduleNotInApplication_returnsFalse() {
        assertFalse(application.hasModule(ALICE));
    }

    @Test
    public void hasModule_moduleInApplication_returnsTrue() {
        application.addModule(ALICE);
        assertTrue(application.hasModule(ALICE));
    }

    @Test
    public void hasModule_moduleWithSameIdentityFieldsInApplication_returnsTrue() {
        application.addModule(ALICE);
        Module editedAlice = new ModuleBuilder(ALICE).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(application.hasModule(editedAlice));
    }

    @Test
    public void getModuleList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        application.getModuleList().remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        application.addListener(listener);
        application.addModule(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        application.addListener(listener);
        application.removeListener(listener);
        application.addModule(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyApplication whose modules list can violate interface constraints.
     */
    private static class ApplicationStub implements ReadOnlyApplication {
        private final ObservableList<Module> modules = FXCollections.observableArrayList();
        private final ObservableList<DegreePlanner> degreePlanners = FXCollections.observableArrayList();

        ApplicationStub(Collection<Module> modules) {
            this.modules.setAll(modules);
        }

        @Override
        public ObservableList<Module> getModuleList() {
            return modules;
        }

        @Override public ObservableList<DegreePlanner> getDegreePlannerList() {
            return degreePlanners;
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
