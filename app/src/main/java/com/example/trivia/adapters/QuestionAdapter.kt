package com.example.trivia.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trivia.R
import com.example.trivia.models.Question

class QuestionAdapter( val questionList: ArrayList<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionLabel = itemView.findViewById<TextView>(R.id.question)
        val answer = itemView.findViewById<TextView>(R.id.answer)
        val value = itemView.findViewById<TextView>(R.id.value)
        val created_at = itemView.findViewById<TextView>(R.id.created_at)
        val category = itemView.findViewById<TextView>(R.id.category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val questionView = inflater.inflate(R.layout.question_element, parent, false)

        return ViewHolder(questionView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val question: Question = questionList.get(position)
        val questionLabel = viewHolder.questionLabel
        questionLabel.setText(question.question)
        val answer = viewHolder.answer
        answer.setText(question.answer)
        val value = viewHolder.value
        value.setText(question.value.toString())
        val created_at = viewHolder.created_at
        created_at.setText(question.created_at.toString())
        val category = viewHolder.category
        category.setText(question.category)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }
}