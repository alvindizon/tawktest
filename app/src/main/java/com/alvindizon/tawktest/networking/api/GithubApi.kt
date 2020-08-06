package com.alvindizon.tawktest.networking.api

import com.alvindizon.tawktest.networking.model.Item
import com.alvindizon.tawktest.networking.model.SearchUserResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApi {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users")
    fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") itemsPerPage: Int
    ): Single<List<Item>>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users/{username}")
    fun searchForUser(
        @Path("username") username: String
    ): Single<SearchUserResponse>
}