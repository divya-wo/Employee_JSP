package com.example.model;

import java.util.Arrays;

public class Skill {
    private int id;
    private int employee_id;
    private String[] skillName;

    public Skill() {}
    
    public Skill(int id, int employee_id, String[] skillName) {
        this.id = id;
        this.employee_id = employee_id;
        this.skillName = skillName;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return this.employee_id;
    }

    public void setEmployeeId(int employee_id) {
        this.employee_id = employee_id;
    }

    public String[] getSkillName() {
        return this.skillName;
    }

    public void setSkillName(String[] skillName) {
        this.skillName = skillName;
    }

    @Override
    public String toString() {
        return "Skill [ID=" + id + ", Employee ID=" + employee_id + ", Skills=" + Arrays.toString(skillName) + "]";
    }
}
