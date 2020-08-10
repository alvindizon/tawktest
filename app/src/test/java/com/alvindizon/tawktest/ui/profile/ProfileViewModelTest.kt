package com.alvindizon.tawktest.ui.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alvindizon.tawktest.any
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

        `when`(insertUserUseCase.insert(any()))
            .thenReturn(Completable.complete())

        SUT.saveNoteToDb("", "")

        Truth.assert_()
            .that(dbStatus?.observedValues)
            .isEqualTo(listOf(DB_SAVING, NOTE_SAVED))
    }

    @Test
    fun `fetchNotesIfAny - correct LiveData string returned`() {

        `when`(getNoteUseCase.getNoteByUserName("username"))
            .thenReturn(Single.just("note"))

        val fetchNotesIfAny = SUT.fetchNotesIfAny("username").testObserver()

        Truth.assert_()
            .that(fetchNotesIfAny.observedValues[0])
            .isEqualTo("note")
    }


    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}