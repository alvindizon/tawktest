package com.alvindizon.tawktest.ui.userlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
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
    var avatarUrl: String,
    var hasNote: Boolean) {
    override fun toString(): String {
        return "UsersListItem(url='$url', userName='$userName', avatarUrl='$avatarUrl', hasNote=$hasNote)"
    }
}

class UserDiff: DiffUtil.ItemCallback<UsersListItem>() {
    override fun areItemsTheSame(oldItem: UsersListItem, newItem: UsersListItem): Boolean {
        return oldItem.url == newItem.url && oldItem.hasNote == newItem.hasNote
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
                .load(item.avatarUrl)
                .thumbnail(0.33f)
                .centerCrop()

            if((position + 1) % 4 == 0) {
                builder = builder.apply(bitmapTransform(InvertFilterTransformation()))
            }

            builder.into(binding.image)

            binding.imgNotes.isVisible = item.hasNote
        }
    }


    // note: the value for viewType here comes from getItemViewType
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemUserBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item, position)
            holder.itemView.setOnClickListener {
                listener(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_user
    }
}

