package api.dto;

import model.Instrument;
import model.Lesson;
import model.Subject;
import teacherPersistence.SubjectPersistence;

import java.util.List;
import java.util.stream.Collectors;

public class Student {
  private final long id;
  private final String firstName;
  private final String lastName;
  private final int classNumber;
  private final List<Lesson> lessons;
  private final Instrument mainInstrument;
  private final boolean hasAllMandatorySubjectsAssigned;
  private final boolean hasITN;

  public Student(
      long id,
      String firstName,
      String lastName,
      int classNumber,
      List<Lesson> lessons,
      Instrument mainInstrument,
      boolean hasAllMandatorySubjectsAssigned,
      boolean hasITN) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.classNumber = classNumber;
    this.lessons = lessons;
    this.mainInstrument = mainInstrument;
    this.hasAllMandatorySubjectsAssigned = hasAllMandatorySubjectsAssigned;
    this.hasITN = hasITN;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public int getClassNumber() {
    return classNumber;
  }

  public List<Lesson> getLessons() {
    return lessons;
  }

  public Instrument getMainInstrument() {
    return mainInstrument;
  }

  public boolean isHasAllMandatorySubjectsAssigned() {
    return hasAllMandatorySubjectsAssigned;
  }

  public boolean isHasITN() {
    return hasITN;
  }

  public static Student fromStudent(model.Student student, SubjectPersistence subjectPersistence) {
    return new Student(
        student.getId(),
        student.getFirstName(),
        student.getLastName(),
        student.getClassNumber(),
        student.getLessons(),
        student.getMainInstrument(),
        checkMandatorySubjects(student, subjectPersistence),
        checkITN(student.getLessons()));
  }

  private static boolean checkMandatorySubjects(
      model.Student student, SubjectPersistence subjectPersistence) {
    return subjectPersistence.getAllUnassignedByStudent(student).stream()
        .map(Subject::isMandatory)
        .collect(Collectors.toList())
        .contains(true);
  }

  private static boolean checkITN(List<Lesson> lessons) {
    return lessons.stream()
        .map(Lesson::getSubject)
        .map(Subject::isItn)
        .collect(Collectors.toList())
        .contains(true);
  }
}
