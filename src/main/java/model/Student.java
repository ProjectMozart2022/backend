package model;

import java.util.List;

public class Student {
  private long id;
  private String firstName;
  private String lastName;
  private int classNumber;
  private List<Lesson> lessons;

  public Student() {}

  public Student(long id, String firstName, String lastName, int classNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.classNumber = classNumber;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public int getClassNumber() {
    return classNumber;
  }

  public void setClassNumber(int classNumber) {
    this.classNumber = classNumber;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public void setLessons(List<Lesson> lessons) {
    this.lessons = lessons;
  }
}
