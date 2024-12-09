package com.frankefelipee.myissuertracker.issue;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private final IssueService issueService;

    @GetMapping("/all")
    public ResponseEntity<IssueResponse> allIssues() {

        CollectionModel<EntityModel<Issue>> allIssues = issueService.getAllIssues();
        IssueResponse issueResponse = new IssueResponse(
                false,
                "successfull",
                "Query Successfull",
                null,
                allIssues
        );

        return ResponseEntity.ok(issueResponse);

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> oneIssue(@PathVariable String id) {

        try {
            EntityModel<Issue> issue = issueService.getOneIssue(id);
            IssueResponse issueResponse = new IssueResponse(
                    false,
                    "successful",
                    "Query Successfull",
                    issue,
                    null
            );
            return ResponseEntity.ok(issueResponse);
        } catch (IssueNotFoundException issueNotFoundException) {
            return ResponseEntity.notFound().build();
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
                issueService.generateNewIssue(issue),
                null);

        return ResponseEntity.status(HttpStatus.CREATED).body(issueResponse);

    }

    @PatchMapping("/mark_done/{id}")
    ResponseEntity<?> finishIssue(@PathVariable String id) {

        try {
            EntityModel<Issue> issue = issueService.markIssueAsDone(id);

            if (issue != null) {
                IssueResponse issueResponse = new IssueResponse(
                        false,
                        "Successful",
                        "Marked as Done",
                        issue,
                        null
                );

                return ResponseEntity.ok(issueResponse);
            }

            IssueResponse issueResponse = new IssueResponse(
                    false,
                    "Failed",
                    "The Issue you are trying to mark as done is already done.",
                    null,
                    null
            );

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(issueResponse);
        } catch (IssueNotFoundException issueNotFoundException) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/modify/{id}")
    ResponseEntity<?> changeIssue(@Valid @RequestBody Issue issue, @PathVariable String id) {

        try {
            EntityModel<Issue> modifiedIssue = issueService.modifyIssue(issue, id);
            IssueResponse issueResponse = new IssueResponse(
                    false,
                    "Successful",
                    "Modified Successfully",
                    modifiedIssue,
                    null
            );

            return ResponseEntity.ok(issueResponse);
        } catch (IssueNotFoundException issueNotFoundException) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteIssue(@PathVariable String id) {

        try {
            issueService.deleteIssueById(id);
            return ResponseEntity.noContent().build();
        } catch (IssueNotFoundException issueNotFoundException) {
            return ResponseEntity.notFound().build();
        }

    }

}
