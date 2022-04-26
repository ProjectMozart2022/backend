package model.report;

public class TeacherReport {
  private String firebaseId;
  private String firstName;
  private String lastName;
  private long minutesInTotal;

  public TeacherReport() {}

  public TeacherReport(String firebaseId, String firstName, String lastName, long minutesInTotal) {
    this.firebaseId = firebaseId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.minutesInTotal = minutesInTotal;
  }

  public String getFirebaseId() {
    return firebaseId;
  }

  public void setFirebaseId(String firebaseId) {
    this.firebaseId = firebaseId;
  }

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

  public long getMinutesInTotal() {
    return minutesInTotal;
  }

  public void setMinutesInTotal(long minutesInTotal) {
    this.minutesInTotal = minutesInTotal;
  }
}
