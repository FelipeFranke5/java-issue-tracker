package com.frankefelipee.myissuertracker.issue;

import jakarta.validation.Valid;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/issues")
public class IssueController {

    private final IssueService issueService;

    @Value("${openai.api.key}")
    private String openAIKey;

    public IssueController(IssueService issueService) {

        this.issueService = issueService;

    }

    private ResponseEntity<?> get404Message(String id) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Not Found")
                        .withDetail("Could not find an ISSUE with given id.")
                );

    }

    private ResponseEntity<?> get405Message() {

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(Problem.create()
                        .withTitle("Method Not Allowed")
                        .withDetail("This method is not allowed.")
                );

    }

    @GetMapping("/all")
    public ResponseEntity<HashMap<String, Object>> allIssues() {

        long issuesCount = issueService.getIssuesCount();
        CollectionModel<EntityModel<Issue>> allIssues = issueService.getAllIssues();
        HashMap<String, Object> data = new HashMap<>();
        data.put("issuesCount", issuesCount);
        data.put("issues", allIssues);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(data);

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> oneIssue(@PathVariable String id) {

        try {
            EntityModel<Issue> issue = issueService.getOneIssue(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(issue);
        } catch (IssueNotFoundException issueNotFoundException) {
            return this.get404Message(id);
        }

    }

    @PostMapping("/create")
    ResponseEntity<IssueResponse> createIssue(@Valid @RequestBody IssueRequest issueRequest) {

        Issue issue = new Issue();
        issue.setSalesForce(issueRequest.salesForce());
        issue.setDescription(issueRequest.description());
        issue.setDescription(issueRequest.description());
        IssueResponse issueResponse = new IssueResponse(
                true,
                "sucessful",
                "created",
                issueService.generateNewIssue(issue));

        return ResponseEntity.ok(issueResponse);

    }

    @PatchMapping("/mark_done/{id}")
    ResponseEntity<?> finishIssue(@PathVariable String id) {

        try {
            EntityModel<Issue> issue = issueService.markIssueAsDone(id);

            if (issue != null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(issue);
            }
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(Problem.create()
                            .withTitle("Issue Already Done")
                            .withDetail("This Issue is already marked as done")
                    );
        } catch (IssueNotFoundException issueNotFoundException) {
            return this.get404Message(id);
        }

    }

    @PutMapping("/modify/{id}")
    ResponseEntity<?> changeIssue(@Valid @RequestBody Issue issue, @PathVariable String id) {

        try {
            EntityModel<Issue> modifiedIssue = issueService.modifyIssue(issue, id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(modifiedIssue);
        } catch (IssueNotFoundException issueNotFoundException) {
            return this.get404Message(id);
        }

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteIssue(@PathVariable String id) {

        try {
            issueService.deleteIssueById(id);
            return this.get405Message();
        } catch (IssueNotFoundException issueNotFoundException) {
            return this.get404Message(id);
        }

    }

}
