package de.mschoeffel.secretsanta.results;

public class GroupMemberCreationResult {

    private String name;
    private String key;
    private Integer rerolls;
    private Boolean drawAccepted;

    public GroupMemberCreationResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }

    public Boolean getDrawAccepted() {
        return drawAccepted;
    }

    public void setDrawAccepted(Boolean drawAccepted) {
        this.drawAccepted = drawAccepted;
    }
}
