package de.mschoeffel.secretsanta.controller;

public class TestData {

    private String name;
    private Integer rerolls;

    public TestData() {
    }

    public TestData(String name, Integer rerolls) {
        this.name = name;
        this.rerolls = rerolls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }
}
