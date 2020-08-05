package com.alvindizon.tawktest.features.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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

//    @Inject
//    lateinit var viewModelFactory: ProfileVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(ProfileViewModel::class.java)

        setupToolbar()

        loadAvatar()
    }


    private fun setupToolbar() {
        binding.toolbarTitle.text = intent.extras?.getString(Const.USERNAME_KEY)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun loadAvatar() {
        Glide.with(binding.avatar)
            .load(intent.extras?.getString(Const.AVATAR_URL_KEY))
            .into(binding.avatar)
    }
}