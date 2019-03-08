package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.module.Module;
import seedu.address.model.module.exceptions.ModuleNotFoundException;
import seedu.address.model.planner.DegreePlanner;
import seedu.address.model.requirement.RequirementCategory;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedApplication versionedApplication;
    private final UserPrefs userPrefs;
    private final SimpleObjectProperty<Module> selectedModule = new SimpleObjectProperty<>();

    private final FilteredList<Module> filteredModules;
    private final FilteredList<DegreePlanner> filteredDegreePlanners;

    private final VersionedRequirementCategoryList versionedRequirementCategoryList;
    private final FilteredList<RequirementCategory> filteredRequirementCategory;

    /**
     * Initializes a ModelManager with the given application and userPrefs.
     */
    public ModelManager(ReadOnlyApplication application,
            ReadOnlyRequirementCategoryList requirementCategoryList, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(application, requirementCategoryList, userPrefs);


        logger.fine("Initializing with address book: " + application + " and user prefs " + userPrefs);

        versionedApplication = new VersionedApplication(application);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredModules = new FilteredList<>(versionedApplication.getModuleList());
        filteredDegreePlanners = new FilteredList<>((versionedApplication.getDegreePlannerList()));

        filteredModules.addListener(this::ensureSelectedModuleIsValid);

        versionedRequirementCategoryList = new VersionedRequirementCategoryList(requirementCategoryList);
        filteredRequirementCategory =
                new FilteredList<>((versionedRequirementCategoryList.getRequirementCategoryList()));
    }

    /**
     * ToDo: Add DegreePlannerList
     */
    public ModelManager() {
        this(new Application(), new RequirementCategoryList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getApplicationFilePath() {
        return userPrefs.getApplicationFilePath();
    }

    @Override
    public void setApplicationFilePath(Path applicationFilePath) {
        requireNonNull(applicationFilePath);
        userPrefs.setApplicationFilePath(applicationFilePath);
    }

    @Override
    public Path getDegreePlannerListFilePath() {
        return userPrefs.getDegreePlannerListFilePath();
    }

    @Override
    public void setDegreePlannerListFilePath(Path degreePlannerListFilePath) {
        requireNonNull(degreePlannerListFilePath);
        userPrefs.setDegreePlannerListFilePath(degreePlannerListFilePath);
    }

    @Override
    public Path getRequirementCategoryListFilePath() {
        return userPrefs.getRequirementCategoryListFilePath();
    }

    @Override
    public void setRequirementCategoryListFilePath(Path requirementCategoryListFilePath) {
        requireNonNull(requirementCategoryListFilePath);
        userPrefs.setDegreePlannerListFilePath(requirementCategoryListFilePath);
    }

    //=========== Application ================================================================================

    @Override
    public void setApplication(ReadOnlyApplication application) {
        versionedApplication.resetData(application);
    }

    @Override
    public ReadOnlyApplication getApplication() {
        return versionedApplication;
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        return versionedApplication.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        versionedApplication.removeModule(target);
    }

    @Override
    public void addModule(Module module) {
        versionedApplication.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        versionedApplication.setModule(target, editedModule);
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     * {@code versionedApplication}
     */
    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoApplication() {
        return versionedApplication.canUndo();
    }

    @Override
    public boolean canRedoApplication() {
        return versionedApplication.canRedo();
    }

    @Override
    public void undoApplication() {
        versionedApplication.undo();
    }

    @Override
    public void redoApplication() {
        versionedApplication.redo();
    }

    @Override
    public void commitApplication() {
        versionedApplication.commit();
    }

    //=========== Selected module ===========================================================================

    @Override
    public ReadOnlyProperty<Module> selectedModuleProperty() {
        return selectedModule;
    }

    @Override
    public Module getSelectedModule() {
        return selectedModule.getValue();
    }

    @Override
    public void setSelectedModule(Module module) {
        if (module != null && !filteredModules.contains(module)) {
            throw new ModuleNotFoundException();
        }
        selectedModule.setValue(module);
    }

    /**
     * Ensures {@code selectedModule} is a valid module in {@code filteredModules}.
     */
    private void ensureSelectedModuleIsValid(ListChangeListener.Change<? extends Module> change) {
        while (change.next()) {
            if (selectedModule.getValue() == null) {
                // null is always a valid selected module, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedModuleReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedModule.getValue());
            if (wasSelectedModuleReplaced) {
                // Update selectedModule to its new value.
                int index = change.getRemoved().indexOf(selectedModule.getValue());
                selectedModule.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedModuleRemoved = change.getRemoved().stream()
                    .anyMatch(removedModule -> selectedModule.getValue().isSameModule(removedModule));
            if (wasSelectedModuleRemoved) {
                // Select the module that came before it in the list,
                // or clear the selection if there is no such module.
                selectedModule.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedApplication.equals(other.versionedApplication)
                && userPrefs.equals(other.userPrefs)
                && filteredModules.equals(other.filteredModules)
                && Objects.equals(selectedModule.get(), other.selectedModule.get());
    }

    //=========== DegreePlannerList Methods =================================================================

    @Override
    public boolean hasDegreePlanner(DegreePlanner planner) {
        requireNonNull(planner);
        return versionedApplication.hasDegreePlanner(planner);
    }

    @Override public void deleteDegreePlanner(DegreePlanner target) {
        versionedApplication.removeDegreePlanner(target);
    }

    @Override public void addDegreePlanner(DegreePlanner degreePlanner) {
        versionedApplication.addDegreePlanner(degreePlanner);
    }

    @Override public void setDegreePlanner(DegreePlanner target, DegreePlanner editedDegreePlanner) {
        requireAllNonNull(target, editedDegreePlanner);
        versionedApplication.setDegreePlanner(target, editedDegreePlanner);
    }

    @Override public ObservableList<DegreePlanner> getFilteredDegreePlannerList() {
        return filteredDegreePlanners;
    }

    @Override public void updateFilteredDegreePlannerList(Predicate<DegreePlanner> predicate) {
        requireNonNull(predicate);
        filteredDegreePlanners.setPredicate(predicate);
    }

    //=========== RequirementCategoryList Methods =================================================================

    @Override
    public ReadOnlyRequirementCategoryList getRequirementCategoryList() {
        return versionedRequirementCategoryList;
    }

    @Override
    public boolean hasRequirementCategory(RequirementCategory planner) {
        requireNonNull(planner);
        return versionedRequirementCategoryList.hasRequirementCategory(planner);
    }

    @Override public void deleteRequirementCategory(RequirementCategory target) {
        versionedRequirementCategoryList.removeRequirementCategory(target);
    }

    @Override public void addRequirementCategory(RequirementCategory degreePlanner) {
        versionedRequirementCategoryList.addRequirementCategory(degreePlanner);
    }

    @Override public void setRequirementCategory(RequirementCategory target,
            RequirementCategory editedRequirementCategory) {
        requireAllNonNull(target, editedRequirementCategory);

        versionedRequirementCategoryList.setRequirementCategory(target, editedRequirementCategory);
    }

    @Override public ObservableList<RequirementCategory> getFilteredRequirementCategoryList() {
        return filteredRequirementCategory;
    }

    @Override public void updateFilteredRequirementCategoryList(Predicate<RequirementCategory> predicate) {
        requireNonNull(predicate);
        filteredRequirementCategory.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================
    @Override public boolean canUndoRequirementCategoryList() {
        return versionedRequirementCategoryList.canUndo();
    }

    @Override public boolean canRedoRequirementCategoryList() {
        return versionedRequirementCategoryList.canRedo();
    }

    @Override public void undoRequirementCategoryList() {
        versionedRequirementCategoryList.undo();
    }

    @Override public void redoRequirementCategoryList() {
        versionedRequirementCategoryList.redo();
    }

    @Override public void commitRequirementCategoryList() {
        versionedRequirementCategoryList.commit();
    }
}
