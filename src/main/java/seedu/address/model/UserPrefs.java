package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();

    private Path applicationFilePath = Paths.get("data", "application.json");
    private Path degreePlannerListFilePath = Paths.get("data", "degreePlannerList.json");
    private Path requirementCategoryListFilePath = Paths.get("data", "requirementCategoryList.json");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setApplicationFilePath(newUserPrefs.getApplicationFilePath());
        setDegreePlannerListFilePath(newUserPrefs.getDegreePlannerListFilePath());
        setRequirementCategoryListFilePath(newUserPrefs.getRequirementCategoryListFilePath());

    }

    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getApplicationFilePath() {
        return applicationFilePath;
    }

    public void setApplicationFilePath(Path applicationFilePath) {
        requireNonNull(applicationFilePath);
        this.applicationFilePath = applicationFilePath;
    }

    public Path getDegreePlannerListFilePath() {
        return degreePlannerListFilePath;
    }

    public void setDegreePlannerListFilePath(Path degreePlannerListFilePath) {
        requireNonNull(degreePlannerListFilePath);
        this.degreePlannerListFilePath = degreePlannerListFilePath;
    }

    public Path getRequirementCategoryListFilePath() {
        return requirementCategoryListFilePath;
    }

    public void setRequirementCategoryListFilePath(Path requirementCategoryListFilePath) {
        requireNonNull(requirementCategoryListFilePath);
        this.requirementCategoryListFilePath = requirementCategoryListFilePath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && applicationFilePath.equals(o.applicationFilePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, applicationFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + applicationFilePath);
        return sb.toString();
    }

}
