package com.alvindizon.tawktest.networking.api

import com.alvindizon.tawktest.networking.model.GetUsersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

interface GithubApi {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("users")
    fun getUsers(): Single<List<GetUsersResponse>>

}