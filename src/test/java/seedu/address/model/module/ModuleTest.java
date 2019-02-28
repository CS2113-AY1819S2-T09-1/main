package seedu.address.model.module;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalModules.ALICE;
import static seedu.address.testutil.TypicalModules.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.ModuleBuilder;

public class ModuleTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Module module = new ModuleBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        module.getTags().remove(0);
    }

    @Test
    public void isSameModule() {
        // same object -> returns true
        assertTrue(ALICE.isSameModule(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameModule(null));

        // different phone and email -> returns false
        Module editedAlice = new ModuleBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.isSameModule(editedAlice));

        // different name -> returns false
        editedAlice = new ModuleBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameModule(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new ModuleBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withCode(VALID_CODE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameModule(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new ModuleBuilder(ALICE).withPhone(VALID_PHONE_BOB).withCode(VALID_CODE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameModule(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new ModuleBuilder(ALICE).withCode(VALID_CODE_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameModule(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Module aliceCopy = new ModuleBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different module -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Module editedAlice = new ModuleBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ModuleBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ModuleBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different code -> returns false
        editedAlice = new ModuleBuilder(ALICE).withCode(VALID_CODE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ModuleBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
