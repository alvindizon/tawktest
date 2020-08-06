package com.alvindizon.tawktest.features.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alvindizon.tawktest.R
import com.alvindizon.tawktest.core.Const
import com.alvindizon.tawktest.databinding.ActivityProfileBinding
import com.alvindizon.tawktest.di.InjectorUtils
import com.bumptech.glide.Glide
import javax.inject.Inject

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    private lateinit var viewModel: ProfileViewModel

    private lateinit var userName: String

    private lateinit var avatarUrl: String

    @Inject
    lateinit var viewModelFactory: ProfileVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        setupDataBinding()

        getExtrasFromIntent()

        setupToolbar()

        loadAvatar()

        displayUserDetails()

        setupSaveButton()

        observeProfileUI()

        observeDb()
    }

    private fun getExtrasFromIntent() {
        userName = intent.extras?.getString(Const.USERNAME_KEY)!!
        avatarUrl = intent.extras?.getString(Const.AVATAR_URL_KEY)!!
    }

    private fun setupSaveButton() {
        binding.save.setOnClickListener {
            val notes = binding.editNotes.text.trim().toString()
            if(notes.isNotEmpty()) {
                viewModel.saveNoteToDb(userName, notes)
            }
        }
    }

    private fun setupDataBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.executePendingBindings()
    }

    private fun setupToolbar() {
        binding.toolbarTitle.text = userName
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun loadAvatar() {
        Glide.with(binding.avatar)
            .load(avatarUrl)
            .into(binding.avatar)
    }

    private fun displayUserDetails() {
        viewModel.fetchUserDetails(userName)
    }

    private fun observeProfileUI() {
        viewModel.uiState?.observe(this, Observer {
            when(it) {
                LOADING -> {
                    binding.profileDetails.layout.visibility = View.INVISIBLE
                    binding.progressBar.show()
                }
                else -> {
                    binding.progressBar.hide()
                    binding.profileDetails.layout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun observeDb() {
        viewModel.dbState?.observe(this, Observer {
            when(it) {
                DB_SAVING -> Toast.makeText(this@ProfileActivity, R.string.saving_note, Toast.LENGTH_SHORT).show()
                NOTE_SAVED -> Toast.makeText(this@ProfileActivity, R.string.note_saved, Toast.LENGTH_SHORT).show()
                is DB_ERROR -> Toast.makeText(this@ProfileActivity, R.string.note_error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}