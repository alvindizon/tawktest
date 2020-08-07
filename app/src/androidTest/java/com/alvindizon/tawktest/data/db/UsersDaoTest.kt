package com.alvindizon.tawktest.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UsersDaoTest {

    // region constants
    companion object {
        val SAMPLE = GithubUser("username", "note")
    }
    // endregion constants

    // region helper fields
    // region helper fields
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var SUT: UsersDao

    private lateinit var usersDb: UsersDb
    // endregion helper fields


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        usersDb = Room.inMemoryDatabaseBuilder(context, UsersDb::class.java)
            .allowMainThreadQueries()
            .build()

        SUT = usersDb.usersDao()
    }

    @After
    fun tearDown() {
        usersDb.close()
    }

    @Test
    fun userDao_success_insert() {
        // Arrange
        // Act
        SUT.insert(SAMPLE).blockingAwait()

        // Assert
        SUT.getAllUsers().test().assertValue{ list ->
            list.isNotEmpty()
        }
    }

    @Test
    fun userDao_success_getNote() {
        // Arrange
        // Act
        SUT.insert(SAMPLE).blockingAwait()

        // Assert
        SUT.getNoteByUserName("username").test().assertValue{ note ->
            note == "note"
        }
    }

    @Test
    fun userDao_success_returnTrueIfUserExists() {
        // Arrange
        // Act
        SUT.insert(SAMPLE).blockingAwait()

        // Assert
        assert(SUT.checkIfUserExists("username"))
    }

    @Test
    fun userDao_success_returnFalseIfUserDoesNotExist() {
        // Arrange
        // Act
        // Assert
        assertFalse("This will be shown on error", SUT.checkIfUserExists("username"))
    }
    // region helper methods

    // endregion helper methods

    // region helper classes

    // endregion helper classes

}