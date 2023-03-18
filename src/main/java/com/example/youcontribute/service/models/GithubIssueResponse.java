package com.example.youcontribute.service.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class GithubIssueResponse {
    public String url;
    public String repositoryUrl;
    public String labelsUrl;
    public String commentsUrl;
    public String eventsUrl;
    public String htmlUrl;
    public int id;
    public String nodeId;
    public int number;
    public String title;
    public String state;
    public boolean locked;
    public String authorAssociation;
    public String timelineUrl;
    public String body;
    public int comments;
    public Date createdAt;
    public Date updatedAt;
    public User user;
    public ArrayList<Assignee> assignees;
    public ArrayList<Label> labels;
    public Assignee assignee;
    public Object milestone;
    public Object closedAt;
    public Object activeLockReason;
    public Object performedViaGithubApp;
    public Object stateReason;
    public Reactions reactions;

    public static class Reactions {
        public String url;
        public int totalCount;
        @JsonProperty("+1")
        public int increaseOne;
        @JsonProperty("-1")
        public int decreaseOne;
        public int laugh;
        public int hooray;
        public int confused;
        public int heart;
        public int rocket;
        public int eyes;
    }

    public static class Label {
        public int id;
        public String nodeId;
        public String url;
        public String name;
        public String color;
        @JsonProperty("default")
        public boolean isDefault;
        public String description;
    }

    public static class Assignee {
        public String login;
        public int id;
        public String nodeId;
        public String avatarUrl;
        public String gravatarId;
        public String url;
        public String htmlUrl;
        public String followersUrl;
        public String followingUrl;
        public String gistsUrl;
        public String starredUrl;
        public String subscriptionsUrl;
        public String organizationsUrl;
        public String reposUrl;
        public String eventsUrl;
        public String receivedEventsUrl;
        public String type;
        public boolean siteAdmin;
    }

    public static class User {
        public String login;
        public int id;
        public String nodeId;
        public String avatarUrl;
        public String gravatarId;
        public String url;
        public String htmlUrl;
        public String followersUrl;
        public String followingUrl;
        public String gistsUrl;
        public String starredUrl;
        public String subscriptionsUrl;
        public String organizationsUrl;
        public String reposUrl;
        public String eventsUrl;
        public String receivedEventsUrl;
        public String type;
        public boolean siteAdmin;
    }
}
