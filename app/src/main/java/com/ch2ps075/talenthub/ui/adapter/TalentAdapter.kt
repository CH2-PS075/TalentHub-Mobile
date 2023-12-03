package com.ch2ps075.talenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2ps075.talenthub.data.local.Talent
import com.ch2ps075.talenthub.databinding.ItemRowTalentBinding

class TalentAdapter : ListAdapter<Talent, TalentAdapter.ViewHolder>(TalentDiffCallback()) {

    inner class ViewHolder(private val binding: ItemRowTalentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(talent: Talent) {
            binding.tvItemName.text = talent.name
            binding.tvItemCategory.text = talent.category
            binding.tvItemLocation.text = talent.location
            Glide.with(itemView.context)
                .load(talent.img)
                .skipMemoryCache(true)
                .into(binding.ivItemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowTalentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val talent = getItem(position)
        holder.bind(talent)
    }
}

class TalentDiffCallback : DiffUtil.ItemCallback<Talent>() {
    override fun areItemsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Talent, newItem: Talent): Boolean {
        return oldItem == newItem
    }
}
