package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Hashing;

public class HashingTest {

    @Test
    public void TestHashing() {
        String salt = Hashing.CreateSalt(10);
        String password = "TestPasswort!";

        String hash = Hashing.CalculateHash(password, salt);

        System.out.printf("Salt: %s%n", salt);
        System.out.printf("Hash: %s%n", hash);

        Assertions.assertEquals(Hashing.CalculateHash(password, salt), hash);
    }
}
