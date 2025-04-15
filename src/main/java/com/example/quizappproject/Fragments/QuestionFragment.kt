package com.example.quizappproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.quizappproject.AppDatabase
import com.example.quizappproject.Entities.QuestionEntity
import com.example.quizappproject.R
import kotlinx.coroutines.launch

class QuestionFragment : Fragment() {

    private var categoryName: String? = null

    private lateinit var questionText: TextView
    private lateinit var questionImage: ImageView
    private lateinit var radioGroup: RadioGroup
    private lateinit var answerButton: Button

    private var questions: List<QuestionEntity> = emptyList()
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryName = arguments?.getString(ARG_CATEGORY_NAME)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questionText = view.findViewById(R.id.questionTextView)
        questionImage = view.findViewById(R.id.question_img)
        radioGroup = view.findViewById(R.id.answersRadioGroup)
        answerButton = view.findViewById(R.id.submitAnswerButton)

        lifecycleScope.launch {
            val db = AppDatabase.getDatabase(requireContext())
            questions = db.questionDao().getQuestionsByCategory(categoryName ?: "")

            if (questions.isNotEmpty()) {
                showQuestion(questions[currentIndex])
            } else {
                questionText.text = "No questions available for this category"
                answerButton.visibility = View.GONE
            }
        }

        answerButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show()
            } else {
                checkAnswer(selectedId)
            }
        }
    }

    private fun showQuestion(question: QuestionEntity) {
        questionText.text = question.questionText

        // Load image by name from drawable
        if (!question.imageUrl.isNullOrEmpty()) {
            val resId = resources.getIdentifier(question.imageUrl, "drawable", requireContext().packageName)
            if (resId != 0) {
                questionImage.setImageResource(resId)
                questionImage.visibility = View.VISIBLE
            } else {
                questionImage.visibility = View.GONE
            }
        } else {
            questionImage.visibility = View.GONE
        }

        val answers = listOf(
            question.answer1,
            question.answer2,
            question.answer3,
            question.answer4
        )

        radioGroup.removeAllViews()
        answers.forEachIndexed { index, answer ->
            val radioButton = RadioButton(requireContext()).apply {
                text = answer
                id = View.generateViewId()
                tag = index // This keeps track of the position
                textSize = 28f
                setPadding(16, 24, 16, 24)
                minHeight = 72
                setTextColor(resources.getColor(R.color.white, null))
                buttonTintList = resources.getColorStateList(R.color.white, null)
                gravity = Gravity.CENTER_VERTICAL
                layoutParams = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 12
                    bottomMargin = 12
                    marginStart = 8
                    marginEnd = 8
                }
            }
            radioGroup.addView(radioButton)
        }
    }

    private fun checkAnswer(selectedId: Int) {
        val selectedRadioButton = view?.findViewById<RadioButton>(selectedId)
        val selectedIndex = selectedRadioButton?.tag as? Int

        val correctAnswerIndex = questions[currentIndex].correctAnswerIndex-1

        if (selectedIndex == correctAnswerIndex) {
            Toast.makeText(requireContext(), "Correct! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            val correctAnswerText = listOf(
                questions[currentIndex].answer1,
                questions[currentIndex].answer2,
                questions[currentIndex].answer3,
                questions[currentIndex].answer4
            )[correctAnswerIndex]

            Toast.makeText(requireContext(), "Wrong! Correct answer: $correctAnswerText", Toast.LENGTH_SHORT).show()
        }

        currentIndex++
        if (currentIndex < questions.size) {
            showQuestion(questions[currentIndex])
        } else {
            Toast.makeText(requireContext(), "Quiz Finished!", Toast.LENGTH_LONG).show()
            answerButton.isEnabled = false
        }
    }

    companion object {
        private const val ARG_CATEGORY_NAME = "category_name"

        fun newInstance(categoryName: String): QuestionFragment {
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY_NAME, categoryName)
            fragment.arguments = args
            return fragment
        }
    }
}
