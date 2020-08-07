package com.alvindizon.tawktest.ui.profile

import android.app.Activity
import android.app.Instrumentation
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.tawktest.R
import com.alvindizon.tawktest.core.Const
import com.alvindizon.tawktest.core.extraNotNull
import com.alvindizon.tawktest.databinding.ActivityProfileBinding
import com.alvindizon.tawktest.di.InjectorUtils
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var viewModel: ProfileViewModel

    private val userName by extraNotNull<String>(Const.USERNAME_KEY)

    private val avatarUrl by extraNotNull<String>(Const.AVATAR_URL_KEY)

    @Inject
    lateinit var viewModelFactory: ProfileVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        setupDataBinding()

        setupProfileUi()

        observeProfileUI()

        observeDb()
    }

    private fun setupProfileUi() {
        // setup toolbar
        binding.toolbarTitle.text = userName
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // setup save button
        binding.save.setOnClickListener {
            val notes = binding.editNotes.text.trim().toString()
            if(notes.isNotEmpty()) {
                viewModel.saveNoteToDb(userName, notes)
            }
        }

        // load avatar via avatar URL
        Glide.with(binding.avatar)
            .load(avatarUrl)
            .into(binding.avatar)

        // fetch  user details via GET /users/{username}
        viewModel.fetchUserDetails(userName)

        // fetch notes for user if it exists in the DB
        viewModel.fetchNotesIfAny(userName).observe(this, Observer {
            binding.editNotes.setText(it)
        })

    }

    private fun setupDataBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun observeProfileUI() {
        viewModel.uiState?.observe(this, Observer {
            when(it) {
                LOADING -> {
                    binding.profileDetails.detailVeilLayoutBody.veil()
                }
                else -> {
                    // Uncomment if you want to add a noticeable delay, in order to see the shimmer effect
//                    Handler().postDelayed({
//                        binding.profileDetails.detailVeilLayoutBody.unVeil()
//                    }, 3000)
                    binding.profileDetails.detailVeilLayoutBody.unVeil()
                }
            }
        })
    }

    private fun observeDb() {
        viewModel.dbState?.observe(this, Observer {
            when(it) {
                DB_SAVING -> Toast.makeText(this@ProfileActivity, R.string.saving_note, Toast.LENGTH_SHORT).show()
                NOTE_SAVED -> {
                    Toast.makeText(this@ProfileActivity, R.string.note_saved, Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                }
                is DB_ERROR -> Toast.makeText(this@ProfileActivity, R.string.note_error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}