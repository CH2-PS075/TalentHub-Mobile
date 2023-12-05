package com.ch2ps075.talenthub.ui.profile.helpcenter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps075.talenthub.databinding.ItemRowQuestionBinding

class QuestionAdapter(private var questionList: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    inner class QuestionViewHolder(private val binding: ItemRowQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question) {
            binding.tvTitleQuestion.text = question.title
            binding.tvAnswerQuestion.text = question.answer

            val isExpandable: Boolean = question.isExpandable
            binding.tvAnswerQuestion.visibility = if (isExpandable) View.VISIBLE else View.GONE
            binding.constraintLayout.setOnClickListener {
                question.isExpandable = !question.isExpandable
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = ItemRowQuestionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int = questionList.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]
        holder.bind(question)
    }
}