package com.alvindizon.tawktest.domain

import com.alvindizon.tawktest.data.db.GithubUser
import io.reactivex.Completable
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun insert(user: GithubUser): Completable = profileRepository.insert(user)
}