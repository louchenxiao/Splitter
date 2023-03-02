package com.example.splitter.domain;

import java.util.List;
import java.util.Objects;

public class Group {
    private int GroupId;
    private List<Person> personList;
    private List<Rechnung> rechnungList;
    private List<Result> results;

    public Group(int groupId, List<Person> personList, List<Rechnung> rechnungList, List<Result> results) {
        GroupId = groupId;
        this.personList = personList;
        this.rechnungList = rechnungList;
        this.results = results;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public List<Rechnung> getRechnungList() {
        return rechnungList;
    }

    public void setRechnungList(List<Rechnung> rechnungList) {
        this.rechnungList = rechnungList;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Group{" +
                "GroupId=" + GroupId +
                ", personList=" + personList +
                ", rechnungList=" + rechnungList +
                ", results=" + results +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group group)) return false;
        return getGroupId() == group.getGroupId() && Objects.equals(getPersonList(), group.getPersonList()) && Objects.equals(getRechnungList(), group.getRechnungList()) && Objects.equals(getResults(), group.getResults());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGroupId(), getPersonList(), getRechnungList(), getResults());
    }
}
