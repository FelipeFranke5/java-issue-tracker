package com.frankefelipee.myissuertracker.issue;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public record IssueResponse(
        boolean isCreation,
        String status,
        String detail,
        EntityModel<Issue> issue,
        CollectionModel<EntityModel<Issue>> issues
) {

}
