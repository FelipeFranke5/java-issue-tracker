package com.frankefelipee.myissuertracker.issue;

import org.springframework.hateoas.EntityModel;

public record IssueResponse(boolean isCreation, String status, String detail, EntityModel<Issue> issueEntityModel) {

}
