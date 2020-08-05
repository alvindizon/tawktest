package com.alvindizon.tawktest.networking.model
import com.squareup.moshi.JsonClass

import com.squareup.moshi.Json


@JsonClass(generateAdapter = true)
data class GetUsersResponse(
    @Json(name = "items")
    val items: List<Item> = listOf()
)

@JsonClass(generateAdapter = true)
data class Item(
    @Json(name = "avatar_url")
    val avatarUrl: String = "",
    @Json(name = "followers_url")
    val followersUrl: String = "",
    @Json(name = "gravatar_id")
    val gravatarId: String = "",
    @Json(name = "html_url")
    val htmlUrl: String = "",
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "login")
    val login: String = "",
    @Json(name = "node_id")
    val nodeId: String = "",
    @Json(name = "organizations_url")
    val organizationsUrl: String = "",
    @Json(name = "received_events_url")
    val receivedEventsUrl: String = "",
    @Json(name = "repos_url")
    val reposUrl: String = "",
    @Json(name = "score")
    val score: Double = 0.0,
    @Json(name = "subscriptions_url")
    val subscriptionsUrl: String = "",
    @Json(name = "type")
    val type: String = "",
    @Json(name = "url")
    val url: String = ""
)