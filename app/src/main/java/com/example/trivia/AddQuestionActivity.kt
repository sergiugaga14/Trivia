package com.example.trivia

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.trivia.models.Question
import java.util.*

class AddQuestionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
    }

    override fun onBackPressed() {
        val builder =  AlertDialog.Builder(this);
        builder.setMessage("Do you want to save the question ?");
        builder.setCancelable(false);
        val error= findViewById<TextView>(R.id.numberError)

        builder.apply {
            setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, id ->
                    val question_add = findViewById<EditText>(R.id.question_add)
                    val answer_add = findViewById<EditText>(R.id.answer_add)
                    val value_add = findViewById<EditText>(R.id.value_add)
                    val category_add = findViewById<EditText>(R.id.category_add)
                    val questionError= findViewById<TextView>(R.id.questionError)
                    val answerError= findViewById<TextView>(R.id.answerError)
                    val valueError= findViewById<TextView>(R.id.valueError)
                    val categoryError= findViewById<TextView>(R.id.categoryError)
                    var ok=false
                    if(question_add.text.length<6)
                    {   ok=true
                        questionError.setText("Question field is too short")
                    }
                    if(answer_add.text.length<6)
                    {   ok=true
                        answerError.setText("Answer field is too short")
                    }
                    val number= value_add.text.toString().toIntOrNull()
                    var number2=0
                    if(number==null)
                    {   ok=true
                        valueError.setText("Not a number")
                    }
                    else
                    {
                        if(number<50 || number>150)
                        {   ok=true
                            valueError.setText("Value has to be in [50,150]")
                        }
                        number2=number
                    }
                    val category= category_add.text.toString()
                    if(category!="Music" && category!="History" && category!="Geography" && category!="Personalities" )
                    {   ok=true
                        categoryError.setText("Incorrect Category:\nIt must be: \nMusic,\nHistory,\nGeography,\nPersonalities")
                    }
                    if(!ok)
                    {
                        val intent= Intent()
                        intent.putExtra("question",question_add.text.toString())
                        intent.putExtra("answer",answer_add.text.toString())
                        intent.putExtra("value",number2)
                        intent.putExtra("category",category)
                        setResult(RESULT_OK,intent)
                        finish()
                    }

                    //error.setText("Yessss")
                })
            setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, id ->
                    finish();
                    //error.setText("Noooo")
                })
        }
        builder.create().show();
    }
}