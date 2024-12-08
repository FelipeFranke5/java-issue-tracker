package com.frankefelipee.myissuertracker.issue;

public class IssueNotFoundException extends RuntimeException {

    public IssueNotFoundException(String id) {

        super("Opss, could not find any Issue with given ID " + id);

    }
}
