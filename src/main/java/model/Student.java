package model;

public class Student {
  private long id;
  private String firstName;
  private String lastName;
  private int classNumber;

  public long getId() {
    return id;
  }

  public void setId(int id) {
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
}
