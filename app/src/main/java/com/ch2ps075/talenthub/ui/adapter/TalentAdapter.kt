package com.ch2ps075.talenthub.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps075.talenthub.data.local.Talent
import com.ch2ps075.talenthub.databinding.ItemRowTalentBinding
import com.ch2ps075.talenthub.helper.loadImage

class TalentAdapter : ListAdapter<Talent, TalentAdapter.ViewHolder>(TalentDiffCallback()) {

    inner class ViewHolder(private val binding: ItemRowTalentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(talent: Talent) {
            binding.tvItemName.text = talent.name
            binding.tvItemCategory.text = talent.category
            binding.tvItemLocation.text = talent.location
            binding.ivItemPhoto.loadImage(talent.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemRowTalentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val talent = getItem(position)
        if (talent != null) {
            holder.bind(talent)
        }
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
