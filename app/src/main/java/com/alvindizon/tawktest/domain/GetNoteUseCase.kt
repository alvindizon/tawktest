package com.alvindizon.tawktest.domain

import io.reactivex.Single
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun getNoteByUserName(userName: String): Single<String> =
        profileRepository.getNoteByUserName(userName)
}