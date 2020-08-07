package com.alvindizon.tawktest.domain

import com.alvindizon.tawktest.data.networking.model.SearchUserResponse
import io.reactivex.Single
import javax.inject.Inject

class SearchUserUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun searchForUser(userName: String): Single<SearchUserResponse> =
        profileRepository.searchForUser(userName)
}