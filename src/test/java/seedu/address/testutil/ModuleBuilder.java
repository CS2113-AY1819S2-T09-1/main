package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.module.Code;
import seedu.address.model.module.Email;
import seedu.address.model.module.Module;
import seedu.address.model.module.Name;
import seedu.address.model.module.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Module objects.
 */
public class ModuleBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "alice@gmail.com";
    public static final String DEFAULT_CODE = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Phone phone;
    private Email email;
    private Code code;
    private Set<Tag> tags;

    public ModuleBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        code = new Code(DEFAULT_CODE);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ModuleBuilder with the data of {@code moduleToCopy}.
     */
    public ModuleBuilder(Module moduleToCopy) {
        name = moduleToCopy.getName();
        phone = moduleToCopy.getPhone();
        email = moduleToCopy.getEmail();
        code = moduleToCopy.getCode();
        tags = new HashSet<>(moduleToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Module} that we are building.
     */
    public ModuleBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Module} that we are building.
     */
    public ModuleBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Code} of the {@code Module} that we are building.
     */
    public ModuleBuilder withCode(String code) {
        this.code = new Code(code);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Module} that we are building.
     */
    public ModuleBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Module} that we are building.
     */
    public ModuleBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Module build() {
        return new Module(name, phone, email, code, tags);
    }

}
