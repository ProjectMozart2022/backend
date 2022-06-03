package model;

import java.util.List;
import java.util.stream.IntStream;
import model.report.TeacherReport;

public class Teacher {
  private String firebaseId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private List<Lesson> lessons;
  private int minimalNumOfHours;
  private List<Subject> knownSubjects;

  public Teacher() {}

  public Teacher(
          String firebaseId, String firstName, String lastName, String email, String password, int minimalNumOfHours, List<Subject> knownSubjects) {
    this.firebaseId = firebaseId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.minimalNumOfHours = minimalNumOfHours;
    this.knownSubjects = knownSubjects;
  }

  public TeacherReport report() {
    return new TeacherReport(
        firebaseId,
        firstName,
        lastName,
        lessons.stream()
            .flatMapToInt(lesson -> IntStream.of(lesson.getProfile().getLessonLength()))
            .sum());
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

  public int getMinimalNumOfHours() { return this.minimalNumOfHours; }

  public void setMinimalNumOfHours(int minimalNumOfHours) { this.minimalNumOfHours = minimalNumOfHours; }

  public List<Subject> getKnownSubjects() {
    return knownSubjects;
  }

  public void setKnownSubjects(List<Subject> knownSubjects) {
    this.knownSubjects = knownSubjects;
  }
}
