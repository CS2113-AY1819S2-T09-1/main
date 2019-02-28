package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Email;
import seedu.address.model.module.Module;
import seedu.address.model.module.Name;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Module[] getSampleModules() {
        return new Module[] {
            new Module(new Name("Alex Yeoh"), new Credits("87438807"), new Email("alexyeoh@example.com"),
                new Code("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("friends")),
            new Module(new Name("Bernice Yu"), new Credits("99272758"), new Email("berniceyu@example.com"),
                new Code("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("colleagues", "friends")),
            new Module(new Name("Charlotte Oliveiro"), new Credits("93210283"), new Email("charlotte@example.com"),
                new Code("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("neighbours")),
            new Module(new Name("David Li"), new Credits("91031282"), new Email("lidavid@example.com"),
                new Code("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("family")),
            new Module(new Name("Irfan Ibrahim"), new Credits("92492021"), new Email("irfan@example.com"),
                new Code("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("classmates")),
            new Module(new Name("Roy Balakrishnan"), new Credits("92624417"), new Email("royb@example.com"),
                new Code("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Module sampleModule : getSampleModules()) {
            sampleAb.addModule(sampleModule);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
