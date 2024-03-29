package com.piashcse.experiment.mvvm_hilt.data.model

import com.google.gson.annotations.SerializedName


data class RepoSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val repositories: List<Repository>,
    @SerializedName("total_count")
    val totalCount: Int
)

data class Repository(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("default_branch")
    val defaultBranch: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("fork")
    val fork: Boolean,
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("language")
    val language: String,
    @SerializedName("master_branch")
    val masterBranch: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("private")
    val `private`: Boolean,
    @SerializedName("pushed_at")
    val pushedAt: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("size")
    val size: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("watchers_count")
    val watchersCount: Int
)

data class Owner(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("gravatar_id")
    val gravatarId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("node_id")
    val nodeId: String,
    @SerializedName("received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)