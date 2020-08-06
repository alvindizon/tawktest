package com.alvindizon.tawktest.networking.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "avatar_url")
    val avatarUrl: String = "",
    @Json(name = "events_url")
    val eventsUrl: String? = null,
    @Json(name = "followers_url")
    val followersUrl: String? = null,
    @Json(name = "following_url")
    val followingUrl: String? = null,
    @Json(name = "gists_url")
    val gistsUrl: String? = null,
    @Json(name = "gravatar_id")
    val gravatarId: String? = null,
    @Json(name = "html_url")
    val htmlUrl: String = "",
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "login")
    val login: String = "",
    @Json(name = "node_id")
    val nodeId: String? = null,
    @Json(name = "organizations_url")
    val organizationsUrl: String? = null,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String? = null,
    @Json(name = "repos_url")
    val reposUrl: String? = null,
    @Json(name = "site_admin")
    val siteAdmin: Boolean? = null,
    @Json(name = "starred_url")
    val starredUrl: String? = null,
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String? = null,
    @Json(name = "type")
    val type: String? = null,
    @Json(name = "url")
    val url: String? = null
)

@JsonClass(generateAdapter = true)
data class SearchUserResponse(
    @Json(name = "avatar_url")
    val avatarUrl: String = "",
    @Json(name = "bio")
    val bio: Any? = null,
    @Json(name = "blog")
    val blog: String = "",
    @Json(name = "company")
    val company: Any? = null,
    @Json(name = "created_at")
    val createdAt: String = "",
    @Json(name = "email")
    val email: Any? = null,
    @Json(name = "events_url")
    val eventsUrl: String = "",
    @Json(name = "followers")
    val followers: Int = 0,
    @Json(name = "followers_url")
    val followersUrl: String = "",
    @Json(name = "following")
    val following: Int = 0,
    @Json(name = "following_url")
    val followingUrl: String = "",
    @Json(name = "gists_url")
    val gistsUrl: String = "",
    @Json(name = "gravatar_id")
    val gravatarId: String = "",
    @Json(name = "hireable")
    val hireable: Any? = null,
    @Json(name = "html_url")
    val htmlUrl: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "location")
    val location: Any? = null,
    @Json(name = "login")
    val login: String = "",
    @Json(name = "name")
    val name: Any? = null,
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "organizations_url")
    val organizationsUrl: String = "",
    @Json(name = "public_gists")
    val publicGists: Int = 0,
    @Json(name = "public_repos")
    val publicRepos: Int = 0,
    @Json(name = "received_events_url")
    val receivedEventsUrl: String = "",
    @Json(name = "repos_url")
    val reposUrl: String = "",
    @Json(name = "site_admin")
    val siteAdmin: Boolean = false,
    @Json(name = "starred_url")
    val starredUrl: String = "",
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String = "",
    @Json(name = "twitter_username")
    val twitterUsername: Any? = null,
    @Json(name = "type")
    val type: String = "",
    @Json(name = "updated_at")
    val updatedAt: String = "",
    @Json(name = "url")
    val url: String = ""
)