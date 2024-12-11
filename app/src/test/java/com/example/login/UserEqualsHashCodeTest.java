package com.example.login;

import org.junit.Test;
import static org.junit.Assert.*;

public class UserEqualsHashCodeTest {

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User("user1", "pass1", false);
        user1.setId(1);

        User user2 = new User("user1", "pass1", false);
        user2.setId(1);

        User user3 = new User("user2", "pass2", true);
        user3.setId(2);

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());

        assertNotEquals(user1, null);
        assertNotEquals(user1, new Object());
    }
}