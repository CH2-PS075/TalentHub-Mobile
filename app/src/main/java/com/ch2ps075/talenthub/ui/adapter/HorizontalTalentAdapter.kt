package com.ch2ps075.talenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps075.talenthub.data.network.api.response.Talent
import com.ch2ps075.talenthub.databinding.HorizontalItemRowTalentBinding
import com.ch2ps075.talenthub.helper.loadImage

class HorizontalTalentAdapter: ListAdapter<Talent, HorizontalTalentAdapter.ViewHolder>(HorizontalTalentDiffCallback()) {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ViewHolder(private val binding: HorizontalItemRowTalentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(talent: Talent) {
            binding.tvItemName.text = talent.talentName
            binding.tvItemCategory.text = talent.category
            binding.tvItemLocation.text = talent.address
            binding.ivItemPhoto.loadImage(talent.picture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HorizontalItemRowTalentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val talent = getItem(position)
        if (talent != null) {
            holder.bind(talent)
            holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(getItem(position)) }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(talent: Talent)
    }
}

class HorizontalTalentDiffCallback : DiffUtil.ItemCallback<Talent>() {
    override fun areItemsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem.talentId == newItem.talentId
    }

    override fun areContentsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem == newItem
    }
}