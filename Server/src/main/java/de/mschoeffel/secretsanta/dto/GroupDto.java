package de.mschoeffel.secretsanta.dto;

import java.util.List;

public class GroupDto extends HasIdDto{

    private String name;
    private Integer rerolls;
    private String token;
    private List<GroupMemberDto> members;

    public GroupDto() {
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<GroupMemberDto> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberDto> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }

}
