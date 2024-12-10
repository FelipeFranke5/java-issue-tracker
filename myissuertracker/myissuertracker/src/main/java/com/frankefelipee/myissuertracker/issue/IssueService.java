package com.frankefelipee.myissuertracker.issue;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {

        this.issueRepository = issueRepository;

    }

    public long getIssuesCount() {

        return issueRepository.count();

    }

    public CollectionModel<EntityModel<Issue>> getAllIssues() {

        List<EntityModel<Issue>> issues = issueRepository.findAll().stream()
                .map(issue -> EntityModel.of(
                        issue,
                        linkTo(methodOn(IssueController.class).oneIssue(issue.getId())).withSelfRel(),
                        linkTo(methodOn(IssueController.class).changeIssue(issue, issue.getId())).withRel("modify"),
                        linkTo(methodOn(IssueController.class).deleteIssue(issue.getId())).withRel("delete"),
                        linkTo(methodOn(IssueController.class).finishIssue(issue.getId())).withRel("complete"),
                        linkTo(methodOn(IssueController.class).allIssues()).withRel("issues")))
                .toList();

        return CollectionModel.of(issues, linkTo(methodOn(IssueController.class).allIssues()).withSelfRel());

    }

    public EntityModel<Issue> getOneIssue(String id) {

        Issue issue = issueRepository.findById(id)
                .orElseThrow(() -> new IssueNotFoundException(id));

        return EntityModel.of(
                issue,
                linkTo(methodOn(IssueController.class).oneIssue(id)).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(issue, id)).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(id)).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(id)).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

    public EntityModel<Issue> generateNewIssue(Issue issue) {

        Issue issueCreated = issueRepository.save(issue);

        return EntityModel.of(
                issueCreated,
                linkTo(methodOn(IssueController.class).oneIssue(issueCreated.getId())).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(issueCreated, issueCreated.getId())).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(issueCreated.getId())).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(issueCreated.getId())).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

    public EntityModel<Issue> modifyIssue(Issue issue, String id) {

        Issue issueModified = (Issue) issueRepository.findById(id)
                .map(issue1 -> {
                    issue1.setSalesForce(issue.getSalesForce());
                    issue1.setDescription(issue.getDescription());
                    issue1.setTicket(issue.getTicket());
                    issue1.setDone(issue.isDone());
                    return null;
                })
                .orElseGet(() -> issueRepository.save(issue));

        return EntityModel.of(
                issueModified,
                linkTo(methodOn(IssueController.class).oneIssue(issueModified.getId())).withSelfRel(),
                linkTo(methodOn(IssueController.class).changeIssue(issueModified, issueModified.getId())).withRel("modify"),
                linkTo(methodOn(IssueController.class).deleteIssue(issueModified.getId())).withRel("delete"),
                linkTo(methodOn(IssueController.class).finishIssue(issueModified.getId())).withRel("complete"),
                linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

    }

    public Issue getIssueMarkedAsDone(String id) {

        Issue issueFound = issueRepository.findById(id).orElseThrow(() -> new IssueNotFoundException(id));

        if (!issueFound.isDone()) {

            issueFound.setDone(true);
            issueRepository.save(issueFound);
            return issueFound;

        }

        return null;

    }

    public EntityModel<Issue> markIssueAsDone(String id) {

        Issue finishedIssue = this.getIssueMarkedAsDone(id);

        if (finishedIssue != null) {

            return EntityModel.of(
                    finishedIssue,
                    linkTo(methodOn(IssueController.class).oneIssue(finishedIssue.getId())).withSelfRel(),
                    linkTo(methodOn(IssueController.class).changeIssue(finishedIssue, finishedIssue.getId())).withRel("modify"),
                    linkTo(methodOn(IssueController.class).deleteIssue(finishedIssue.getId())).withRel("delete"),
                    linkTo(methodOn(IssueController.class).allIssues()).withRel("issues"));

        }

        return null;

    }

    public void deleteIssueById(String id) {

        issueRepository.deleteById(id);

    }

}
