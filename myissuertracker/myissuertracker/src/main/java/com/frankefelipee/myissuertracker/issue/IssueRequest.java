package com.frankefelipee.myissuertracker.issue;

import jakarta.validation.constraints.NotNull;

public record IssueRequest(@NotNull String salesForce, @NotNull String ticket, @NotNull String description) {

}
