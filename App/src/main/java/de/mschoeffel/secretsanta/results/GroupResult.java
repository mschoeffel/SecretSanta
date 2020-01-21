package de.mschoeffel.secretsanta.results;

import java.util.List;

public class GroupResult {

    private String name;
    private List<GroupMemberResult> members;

    public GroupResult() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMemberResult> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMemberResult> members) {
        this.members = members;
    }
}
