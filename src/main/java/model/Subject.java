package model;

import java.util.List;

public class Subject {
  private long id;
  private String name;
  private int lessonLength;
  private List<Integer> classRange;
  private boolean itn;

  public Subject() {}

  public Subject(long id, String name, int lessonLength, List<Integer> classRange, boolean itn) {
    this.id = id;
    this.name = name;
    this.lessonLength = lessonLength;
    this.classRange = classRange;
    this.itn = itn;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getLessonLength() {
    return lessonLength;
  }

  public void setLessonLength(int lessonLength) {
    this.lessonLength = lessonLength;
  }

  public List<Integer> getClassRange() {
    return classRange;
  }

  public int[] getClassRangeAsArray() {
    return classRange.stream().mapToInt(Integer::intValue).toArray();
  }

  public void setClassRange(List<Integer> classRange) {
    this.classRange = classRange;
  }

  public boolean isItn() {
    return itn;
  }

  public void setItn(boolean itn) {
    this.itn = itn;
  }
}
