package de.mschoeffel.secretsanta.dto;

import java.time.LocalDateTime;

public class GroupMemberDto extends HasIdDto {

    private String name;
    private String key;
    private Integer rerolls;
    private Boolean drawAccepted;
    private LocalDateTime lastDrawTime;
    private GroupMemberDto partner;

    public GroupMemberDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getLastDrawTime() {
        return lastDrawTime;
    }

    public void setLastDrawTime(LocalDateTime lastDrawTime) {
        this.lastDrawTime = lastDrawTime;
    }

    public GroupMemberDto getPartner() {
        return partner;
    }

    public void setPartner(GroupMemberDto partner) {
        this.partner = partner;
    }

    @Override
    public String toString() {
        return "GroupMemberDto{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", rerolls=" + rerolls +
                ", drawAccepted=" + drawAccepted +
                ", lastDrawTime=" + lastDrawTime +
                ", partner=" + partner +
                ", id=" + id +
                '}';
    }
}
