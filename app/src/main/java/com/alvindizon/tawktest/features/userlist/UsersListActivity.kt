package com.alvindizon.tawktest.features.userlist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import com.alvindizon.tawktest.R
import com.alvindizon.tawktest.core.Const
import com.alvindizon.tawktest.databinding.ActivityUsersListBinding
import com.alvindizon.tawktest.di.InjectorUtils
import com.alvindizon.tawktest.features.profile.ProfileActivity
import javax.inject.Inject

class UsersListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersListBinding

    private lateinit var viewModel: UsersListViewModel

    private lateinit var adapter: UsersListAdapter

    @Inject lateinit var viewModelFactory: UsersListVMFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users_list)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UsersListViewModel::class.java)

        adapter = UsersListAdapter({
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(Const.USERNAME_KEY, it.userName)
            intent.putExtra(Const.URL_KEY, it.url)
            startActivity(intent)
        }, viewModel)

        initUsersList()
    }

    private fun initUsersList() {
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(decoration)

        binding.list.adapter = adapter.withLoadStateHeaderAndFooter(
            header = RetryAdapter { adapter.retry() },
            footer = RetryAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            // Only show the list if refresh succeeds.
            binding.list.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error

        }
    }
}