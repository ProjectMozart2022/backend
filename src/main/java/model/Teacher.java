package model;

import java.util.List;

public class Teacher {
  private String firebaseId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;

  private List<Profile> availableProfiles;
  private List<Lesson> lessons;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Profile> getAvailableProfiles() {
    return availableProfiles;
  }

  public void setAvailableProfiles(List<Profile> availableProfiles) {
    this.availableProfiles = availableProfiles;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
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

  public String getFirebaseId() {
    return firebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    this.firebaseId = firebaseId;
  }
}
