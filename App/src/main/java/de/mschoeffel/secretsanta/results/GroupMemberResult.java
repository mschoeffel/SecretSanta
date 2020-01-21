package de.mschoeffel.secretsanta.results;

public class GroupMemberResult {

    private String name;
    private Integer rerolls;
    private Boolean drawAccepted;
    private String partner;

    public GroupMemberResult() {
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

    public Boolean getDrawAccepted() {
        return drawAccepted;
    }

    public void setDrawAccepted(Boolean drawAccepted) {
        this.drawAccepted = drawAccepted;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
