package api.security;

public class Account {
  private long id;
  private String email;
  private String password;
  private AccountRole role;
  private String firebaseUid;

  public Account() {}

  public Account(String email, String password, AccountRole role, String firebaseUid) {
    this.email = email;
    this.password = password;
    this.role = role;
    this.firebaseUid = firebaseUid;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AccountRole getRole() {
    return role;
  }

  public void setRole(AccountRole role) {
    this.role = role;
  }

  public String getFirebaseUid() {
    return firebaseUid;
  }

  public void setFirebaseUid(String firebaseUid) {
    this.firebaseUid = firebaseUid;
  }
}
