package model;

import java.util.List;

public class Profile {
  private long id;
  private String name;
  private int lessonLength;
  private List<Integer> classRange;
  private boolean itn;

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
