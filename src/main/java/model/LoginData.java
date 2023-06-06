package model;

public class LoginData {

    private long lid;
    private String username;
    private String salt;
    private String hash;

    public LoginData(long lid, String username, String salt, String hash) {
        this.lid = lid;
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    public LoginData(String username, String salt, String hash) {
        this.username = username;
        this.salt = salt;
        this.hash = hash;
    }

    public String getUsername() { return username; }

    public String getSalt() { return salt; }

    public String getHash() { return hash; }

    public long getLid() { return lid; }
}
