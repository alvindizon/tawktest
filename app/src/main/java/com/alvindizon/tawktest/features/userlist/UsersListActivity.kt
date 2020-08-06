package com.alvindizon.tawktest.features.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
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
            intent.putExtra(Const.AVATAR_URL_KEY, it.avatarUrl)
            startActivity(intent)
        }, viewModel)

        initUsersList()

        binding.search.setOnClickListener {
            if(binding.editUsername.text.trim().isEmpty()) {
                // TODO filter search results locally by username/note contents
            }
        }

        binding.retryButton.setOnClickListener {
            adapter.retry()
        }

        // observe any changes with the PagingData for our PagingAdapter
        viewModel.uiState?.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    override fun onResume() {
        super.onResume()
        // list all users initially
        viewModel.fetchUsers()
    }

    private fun initUsersList() {
        // Apply the following settings to our recyclerview
        binding.list.run{
            setAdapter(adapter.withLoadStateHeaderAndFooter(
                header = RetryAdapter {
                    adapter.retry()
                },
                footer = RetryAdapter {
                    adapter.retry()
                }
            ))
            setLayoutManager(LinearLayoutManager(this@UsersListActivity))
            addVeiledItems(20)
        }

        adapter.addLoadStateListener { loadState ->
            // Unveil the list if refresh succeeds
            if(loadState.source.refresh is LoadState.NotLoading) {
                // Uncomment if you want to add a noticeable delay, in order to see the shimmer effect
//                Handler().postDelayed({
//                    binding.list.unVeil()
//                }, 3000)
                binding.list.unVeil()
            }
            // Shimmer during initial load or refresh
            if(loadState.source.refresh is LoadState.Loading) {
                binding.list.veil()
            }
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
            // On error, make the list invisible
            binding.list.isVisible = loadState.source.refresh !is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    this,
                    "\uD83D\uDE28 Wooops ${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}