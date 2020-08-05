package com.alvindizon.tawktest.features.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alvindizon.tawktest.R
import com.alvindizon.tawktest.databinding.ItemUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation


class UsersListItem(
    var url: String,
    var userName: String,
    var hasNote: Boolean) {
    override fun toString(): String {
        return "UsersListItem(url='$url', userName='$userName', hasNote=$hasNote)"
    }
}

class UserDiff: DiffUtil.ItemCallback<UsersListItem>() {
    override fun areItemsTheSame(oldItem: UsersListItem, newItem: UsersListItem): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: UsersListItem, newItem: UsersListItem): Boolean {
        return areItemsTheSame(oldItem, newItem)
    }
}

class UsersListAdapter(val listener: (UsersListItem) -> Unit, val viewModel: UsersListViewModel)
    : PagingDataAdapter<UsersListItem, UsersListAdapter.ViewHolder>(UserDiff()) {

    inner class ViewHolder(private val binding: ItemUserBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UsersListItem, position: Int) {
            binding.model = item
            binding.viewModel = viewModel

            var builder = Glide.with(binding.image)
                .load(binding.url)
                .thumbnail(0.33f)
                .centerCrop()

            if(position % 4 == 0) {
                builder = builder.apply(bitmapTransform(InvertFilterTransformation()))
            }

            builder.into(binding.image)
        }
    }

    // note: the value for viewType here comes from getItemViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemUserBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, position)
            listener(it)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user
    }
}