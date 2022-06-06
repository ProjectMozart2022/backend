package model;

public class Lesson {
  private Student student;
  private Teacher teacher;
  private Subject subject;

  public Lesson() {}

  public Lesson(Student student, Teacher teacher, Subject subject) {
    this.student = student;
    this.teacher = teacher;
    this.subject = subject;
  }

  public Subject getSubject() {
    return subject;
  }

  public void setSubject(Subject subject) {
    this.subject = subject;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }
}
