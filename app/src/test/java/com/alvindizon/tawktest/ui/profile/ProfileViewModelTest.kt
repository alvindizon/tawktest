package com.alvindizon.tawktest.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvindizon.tawktest.data.db.GithubUser
import com.alvindizon.tawktest.data.networking.model.SearchUserResponse
import com.alvindizon.tawktest.domain.GetNoteUseCase
import com.alvindizon.tawktest.domain.InsertUserUseCase
import com.alvindizon.tawktest.domain.SearchUserUseCase
import com.alvindizon.tawktest.ui.RxSchedulerRule
import com.alvindizon.tawktest.ui.testObserver
import com.google.common.truth.Truth
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class ProfileViewModelTest {

    // region constants

    // endregion constants

    // region helper fields
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @Mock lateinit var searchUserUseCase: SearchUserUseCase

    @Mock lateinit var getNoteUseCase: GetNoteUseCase

    @Mock lateinit var insertUserUseCase: InsertUserUseCase

    private lateinit var SUT: ProfileViewModel
    // endregion helper fields

    @Before
    fun setUp() {
        SUT = ProfileViewModel(searchUserUseCase, insertUserUseCase, getNoteUseCase)
    }

    @Test
    fun `uiState is empty`() {
        val uiState = SUT.uiState?.testObserver()

        uiState?.observedValues?.isEmpty()?.let { assert(it) }
    }

    @Test
    fun `dbState is empty`() {
        val dbState = SUT.dbState?.testObserver()

        dbState?.observedValues?.isEmpty()?.let { assert(it) }
    }

    @Test
    fun `fetchUserDetails - correct UI states`() {
        val uiStatus = SUT.uiState?.testObserver()

        `when`(searchUserUseCase.searchForUser("username")).thenReturn(Single.just(
            SearchUserResponse()
        ))

        SUT.fetchUserDetails("username")

        Truth.assert_()
            .that(uiStatus?.observedValues)
            .isEqualTo(listOf(LOADING, SUCCESS))
    }

    @Test
    fun `saveNoteToDb - correct DB states`() {
        val dbStatus = SUT.dbState?.testObserver()
        val githubUser = GithubUser("", "")

        `when`(insertUserUseCase.insert(githubUser))
            .thenReturn(Completable.complete())

        SUT.saveNoteToDb("", "")

        Truth.assert_()
            .that(dbStatus?.observedValues)
            .isEqualTo(listOf(DB_SAVING, NOTE_SAVED))
    }


    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}