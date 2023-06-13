package test;

import datastorage.DAOFactory;
import model.LoginData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Hashing;

import java.sql.SQLException;

/**
 * Test methods for hashing.
 */
public class HashingTest {

    /**
     * Hashing test.
     */
    @Test
    public void TestHashing() {
        String salt = Hashing.CreateSalt(10);
        String password = "123";

        String hash = Hashing.CalculateHash(password, salt);

        System.out.printf("Salt: %s%n", salt);
        System.out.printf("Hash: %s%n", hash);

        Assertions.assertEquals(Hashing.CalculateHash(password, salt), hash);
    }
}
