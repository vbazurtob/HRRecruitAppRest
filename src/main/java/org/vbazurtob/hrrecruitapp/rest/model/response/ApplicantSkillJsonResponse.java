package org.vbazurtob.hrrecruitapp.rest.model.response;


import java.io.Serializable;
import java.util.Objects;

public class ApplicantSkillJsonResponse implements Serializable {

    private Long id;
    private String name;
    private Integer proficiency;// 1 - 5 (Beginner, Novice, Intermediate, Upper Intermediate, Expert)

    public ApplicantSkillJsonResponse(Long id, String name, Integer proficiency) {
        this.id = id;
        this.name = name;
        this.proficiency = proficiency;
    }

    public ApplicantSkillJsonResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProficiency(Integer proficiency) {
        this.proficiency = proficiency;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getProficiency() {
        return proficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicantSkillJsonResponse that = (ApplicantSkillJsonResponse) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(proficiency, that.proficiency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, proficiency);
    }

    @Override
    public String toString() {
        return "ApplicantSkillJsonResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", proficiency=" + proficiency +
                '}';
    }
}
