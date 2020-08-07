package com.alvindizon.tawktest.ui.userlist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvindizon.tawktest.core.Const
import com.alvindizon.tawktest.databinding.ActivityUsersListBinding
import com.alvindizon.tawktest.di.InjectorUtils
import com.alvindizon.tawktest.ui.profile.ProfileActivity
import javax.inject.Inject

class UsersListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUsersListBinding

    private lateinit var viewModel: UsersListViewModel

    private lateinit var adapter: UsersListAdapter

    @Inject lateinit var viewModelFactory: UsersListVMFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InjectorUtils.getAppComponent().inject(this)
        binding = ActivityUsersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(UsersListViewModel::class.java)

        initUsersList()

        setupSearchToolbar()

        setupRetryButton()

        observeUi()
    }

    private fun observeUi() {
        // observe any changes to PagingData emitted by VM and submit new PagingData to adapter
        viewModel.uiState?.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupSearchToolbar() {
        // if search field is not empty, search for username among loaded items
        binding.search.setOnClickListener {
            val search = binding.editUsername.text.trim().toString()
            if (search.isNotEmpty()) {
                viewModel.fetchUsers(search)
            }
        }

        // if user clears search, trigger a new fetch for all users
        binding.editUsername.addTextChangedListener(
            afterTextChanged = {
                if (it.isNullOrBlank()) viewModel.fetchUsers(null)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        // list all users initially AND after ProfileActivity finishes so that note icons are properly displayed
        viewModel.fetchUsers(null)
    }

    private fun initUsersList() {
        // Add a click listener for each list item, and pass ViewModel to the adapter
        adapter = UsersListAdapter({
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra(Const.USERNAME_KEY, it.userName)
            intent.putExtra(Const.URL_KEY, it.url)
            intent.putExtra(Const.AVATAR_URL_KEY, it.avatarUrl)
            startActivity(intent)
        }, viewModel)

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

        // Add a listener for the current state of paging
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