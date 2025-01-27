package model;

import java.util.Objects;

public class Teacher {
    private Integer userId;
    private Integer stdId;
    private String subjectId;
    private String grade;

    // Constructors
    public Teacher() {}

    public Teacher(Integer userId, Integer stdId, String subjectId, String grade) {
        this.userId = userId;
        this.stdId = stdId;
        this.subjectId = subjectId;
        this.grade = grade;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStdId() {
        return stdId;
    }

    public void setStdId(Integer stdId) {
        this.stdId = stdId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userId=" + userId +
                ", stdId=" + stdId +
                ", subjectId='" + subjectId + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(userId, teacher.userId) && Objects.equals(stdId, teacher.stdId) && Objects.equals(subjectId, teacher.subjectId) && Objects.equals(grade, teacher.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, stdId, subjectId, grade);
    }
}
