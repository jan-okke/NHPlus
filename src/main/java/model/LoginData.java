package model;

/**
 * The LoginData object, storing its primary key, username, salt and hash.
 */
public class LoginData {

    private long lid;
    private String username;
    private String salt;
    private String hash;

    /**
     * Constructor with all arguments.
     * @param lid The primary key of the LoginData.
     * @param username The username of the LoginData.
     * @param salt The salt of the LoginData.
     * @param hash The hash of the LoginData.
     */
    public LoginData(long lid, String username, String salt, String hash) {
        this.lid = lid;
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    /**
     * Constructor without the primary key, if not known yet.
     * @param username The username of the LoginData.
     * @param salt The salt of the LoginData.
     * @param hash The hash of the LoginData.
     */
    public LoginData(String username, String salt, String hash) {
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    /**
     * Gets the username.
     * @return The username.
     */
    public String getUsername() { return username; }

    /**
     * Gets the salt.
     * @return The salt.
     */
    public String getSalt() { return salt; }

    /**
     * Gets the hash.
     * @return The hash.
     */
    public String getHash() { return hash; }

    /**
     * Gets the LoginData primary key (lid).
     * @return The LoginData primary key (lid).
     */
    public long getLid() { return lid; }
}
