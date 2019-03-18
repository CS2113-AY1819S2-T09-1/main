package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.module.Code;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Name;
import seedu.address.model.requirement.RequirementCategory;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class RequirementCategoryBuilder {

    public static final String DEFAULT_NAME = "Computing Foundation";
    public static final String DEFAULT_CREDITS = "036";

    private Name name;
    private Credits credits;
    private Set<Code> codeList;

    public RequirementCategoryBuilder() {
        name = new Name(DEFAULT_NAME);
        credits = new Credits(DEFAULT_CREDITS);
        codeList = new HashSet<>();
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public RequirementCategoryBuilder(RequirementCategory requirementCategoryToCopy) {
        name = requirementCategoryToCopy.getName();
        credits = requirementCategoryToCopy.getCredits();
        codeList = new HashSet<Code>(requirementCategoryToCopy.getCodeList());
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public RequirementCategoryBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public RequirementCategoryBuilder withCodes(String ... codes) {
        this.codeList = SampleDataUtil.getCodeSet(codes);
        return this;
    }

    /**
     * Sets the {@code Credits} of the {@code Module} that we are building.
     */
    public RequirementCategoryBuilder withCredits(String credits) {
        this.credits = new Credits(credits);
        return this;
    }

    public RequirementCategory build() {
        return new RequirementCategory(name, credits, codeList);
    }

}
