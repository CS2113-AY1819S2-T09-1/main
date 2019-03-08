package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.Application;
import seedu.address.model.ReadOnlyApplication;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credits;
import seedu.address.model.module.Module;
import seedu.address.model.module.Name;
import seedu.address.model.planner.DegreePlanner;
import seedu.address.model.planner.Semester;
import seedu.address.model.planner.Year;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Application} with sample data.
 */
public class SampleDataUtil {
    private static final Module CS1010 = new Module(
            new Name("Programming Methodology"),
            new Credits("004"),
            new Code("CS1010"),
            getTagSet("programming", "algorithms", "c", "imperative")
    );

    private static final Module CS1231 = new Module(
            new Name("Discrete Structures"),
            new Credits("004"),
            new Code("CS1231"),
            getTagSet("math", "logic", "proving")
    );

    private static final Module CS2040C = new Module(
            new Name("Data Structures and Algorithms"),
            new Credits("004"),
            new Code("CS2040C"),
            getTagSet("linkedlist", "stack", "queue", "hashtable", "heap", "avltree", "graph", "sssp")
    );

    private static final Module CS2100 = new Module(
            new Name("Computer Organisation"),
            new Credits("004"),
            new Code("CS2100"),
            getTagSet("boolean", "mips", "assembly", "circuit", "flipflop", "pipelining", "cache")
    );

    private static final Module CS2102 = new Module(
            new Name("Database Systems"),
            new Credits("004"),
            new Code("CS2100"),
            getTagSet("database", "rdbms", "entity", "sql", "normalisation")
    );

    public static Module[] getSampleModules() {
        return new Module[] {
            CS1010, CS1231, CS2040C, CS2100, CS2102
        };
    }

    public static DegreePlanner[] getSampleDegreePlanners() {
        return new DegreePlanner[] {
            new DegreePlanner(new Year("1"), new Semester("1"),
                    getCodeSet()),
            new DegreePlanner(new Year("1"), new Semester("2"),
                    getCodeSet()),
            new DegreePlanner(new Year("2"), new Semester("1"),
                    getCodeSet()),
            new DegreePlanner(new Year("2"), new Semester("2"),
                    getCodeSet()),
            new DegreePlanner(new Year("3"), new Semester("1"),
                    getCodeSet()),
            new DegreePlanner(new Year("3"), new Semester("2"),
                    getCodeSet()),
            new DegreePlanner(new Year("4"), new Semester("1"),
                    getCodeSet()),
            new DegreePlanner(new Year("4"), new Semester("2"),
                    getCodeSet())
        };
    }

    public static ReadOnlyApplication getSampleApplication() {
        Application sampleApplication = new Application();
        for (Module sampleModule : getSampleModules()) {
            sampleApplication.addModule(sampleModule);
        }
        for (DegreePlanner sampleDegreePlanner : getSampleDegreePlanners()) {
            sampleApplication.addDegreePlanner(sampleDegreePlanner);
        }
        return sampleApplication;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Code> getCodeSet(String... strings) {
        return Arrays.stream(strings)
                .map(Code::new)
                .collect(Collectors.toSet());
    }
}
