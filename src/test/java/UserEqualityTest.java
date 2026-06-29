import org.example.academic.system.model.Role;
import org.example.academic.system.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserEqualityTest {

    @Test
    void usersWithSameUsernameMustBeEqual() {

        User u1 =
                new User("isa", "123", Role.ADMIN);

        User u2 =
                new User("isa", "456", Role.PROFESSOR);

        assertEquals(u1, u2);
    }

    @Test
    void usersWithSameUsernameMustHaveSameHashCode() {

        User u1 =
                new User("isa", "123", Role.ADMIN);

        User u2 =
                new User("isa", "456", Role.PROFESSOR);

        assertEquals(
                u1.hashCode(),
                u2.hashCode()
        );
    }
}