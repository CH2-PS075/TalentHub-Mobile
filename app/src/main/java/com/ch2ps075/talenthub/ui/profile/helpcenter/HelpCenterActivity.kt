package com.ch2ps075.talenthub.ui.profile.helpcenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ch2ps075.talenthub.R
import com.ch2ps075.talenthub.databinding.ActivityHelpCenterBinding

class HelpCenterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpCenterBinding
    private val questionList = ArrayList<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { onSupportNavigateUp() }
        questionList.addAll(getListQuestion())
        initRecyclerView()
    }

    private fun getListQuestion(): ArrayList<Question> {
        val titleQuestion = resources.getStringArray(R.array.question_help_center)
        val answerQuestion = resources.getStringArray(R.array.answer_help_center)
        val listQuestion = ArrayList<Question>()
        for (i in titleQuestion.indices) {
            val question = Question(titleQuestion[i], answerQuestion[i])
            listQuestion.add(question)
        }
        return listQuestion
    }

    private fun initRecyclerView() {
        binding.rvQuestion.apply {
            layoutManager = LinearLayoutManager(this@HelpCenterActivity)
            adapter = QuestionAdapter(questionList)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}