package com.example.quizappproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.quizappproject.R

class QuestionFragment : Fragment() {

    private var categoryName: String? = null

    private lateinit var questionText: TextView
    private lateinit var questionImage: ImageView
    private lateinit var radioGroup: RadioGroup
    private lateinit var answerButton: Button

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
        questionText = view.findViewById(R.id.questionTextView)
        questionImage = view.findViewById(R.id.question_img)
        radioGroup = view.findViewById(R.id.answersRadioGroup)
        answerButton = view.findViewById(R.id.submitAnswerButton)

        // 👇 Placeholder question
        loadMockQuestion()

        answerButton.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(requireContext(), "Please select an answer", Toast.LENGTH_SHORT).show()
            } else {
                val selectedAnswer = view.findViewById<RadioButton>(selectedId)
                checkAnswer(selectedAnswer.text.toString())
            }
        }
    }

    private fun loadMockQuestion() {
        questionText.text = "What is the capital of France?"
        questionImage.setImageResource(R.drawable.flags) // use a dummy image
        val answers = listOf("Paris", "Berlin", "Madrid", "Rome")

        radioGroup.removeAllViews()
        for (answer in answers) {
            val radioButton = RadioButton(requireContext()).apply {
                text = answer
                id = View.generateViewId()
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

    private fun checkAnswer(selected: String) {
        val correctAnswer = "Paris"
        if (selected == correctAnswer) {
            Toast.makeText(requireContext(), "Correct! 🎉", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong! The correct answer was $correctAnswer", Toast.LENGTH_SHORT).show()
        }

        // 👇 Later: Load next question or show results
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
