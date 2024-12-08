package com.frankefelipee.myissuertracker.issue;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class IssueNotFoundAdvice {

    @ExceptionHandler(IssueNotFoundException.class)
    String issueNotFoundHandler(IssueNotFoundException exception) {

        return exception.getMessage();

    }

}
