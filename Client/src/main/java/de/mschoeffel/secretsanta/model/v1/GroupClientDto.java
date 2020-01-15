package de.mschoeffel.secretsanta.model.v1;

import java.util.List;

public class GroupClientDto {

    private Long id;
    private String name;
    private Integer rerolls;
    private List<GroupMemberClientDto> members;

    public GroupClientDto() {
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

    public Integer getRerolls() {
        return rerolls;
    }

    public void setRerolls(Integer rerolls) {
        this.rerolls = rerolls;
    }

    public List<GroupMemberClientDto> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberClientDto> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "GroupClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rerolls=" + rerolls +
                ", members=" + members +
                '}';
    }
}
