package com.example.trivia

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.trivia.adapters.QuestionAdapter
import com.example.trivia.models.Question
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatterBuilder
import java.util.*
import kotlin.concurrent.schedule


class MainActivity : AppCompatActivity(),OnClickListener {

    val questionList:ArrayList<Question> = ArrayList()
    val recyclerAdapter= QuestionAdapter(questionList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val downloadButton= findViewById<Button>(R.id.download)
        val recyclerView= findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter=recyclerAdapter
        recyclerView.layoutManager= LinearLayoutManager(this)
        downloadButton.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(p0: View?) {
        val progressBar = findViewById<ProgressBar>(R.id.progress_Bar) as ProgressBar
        val error= findViewById<TextView>(R.id.numberError)
        val number = findViewById<EditText>(R.id.number)
        val value =number.text.toString().toIntOrNull();
        if(value==null)
        {
             error.setText("Not a number")
        }
        else
        {
            if(value>=1 && value<=100)
            {   questionList.removeAll(questionList)
                recyclerAdapter.notifyDataSetChanged()
                progressBar.visibility = View.VISIBLE
                var progressTimer= Timer("Download",false).schedule(1000){
                    android.os.Handler(mainLooper).post{
                        //val question= Question("Cine e?","ma-ta",13,Date(),"Smecherie")
                        parseJson(value,progressBar)
                        error.setText("")
                    }
                }
            }
            else
            {
                error.setText("The number should be in the interval [1,100]")
            }
        }
    }


   @RequiresApi(Build.VERSION_CODES.O)
   fun  parseJson(number: Int, progressBar: ProgressBar)
    {   val requestQueue = Volley.newRequestQueue(this)
        val url= "https://jservice.io/api/random?count="+number
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response->

                for(i in 0..response.length()-1)
                {
                    val questionItem =response.getJSONObject(i)
                    val question = questionItem.getString("question")
                    val answer = questionItem.getString("answer")
                    //println(questionItem.getString("value"))
                    var value=0
                    var string = questionItem.getString("value")
                    if(string!="null")
                    {
                        value=string.toInt()
                    }

                    val created_at = questionItem.getString("created_at")
                    val category = questionItem.getJSONObject("category").getString("title")
                    questionList.add(Question(question,answer,value,created_at,category))

                }
                recyclerAdapter.notifyDataSetChanged()
                progressBar.visibility = View.INVISIBLE
            },
            { })
        requestQueue.add(jsonArrayRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate ( R.menu.my_menu, menu )
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected ( item: MenuItem): Boolean {
        val id = item.itemId
        if ( id == R.id.add_question )
        {
            // code for Option
            val intent = Intent ( this, AddQuestionActivity::class.java )

            resultLauncher.launch(intent)
            return true
        }
        return super.onOptionsItemSelected ( item );
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            val quest= data?.getStringExtra("question")
            val answer= data?.getStringExtra("answer")
            val value=data?.getIntExtra("value",0)
            val category = data?.getStringExtra("category")
            if(quest!=null && answer!=null && value!=null && category!=null)
            {
                val question = Question( quest,answer,value,Date().toString(),category)
                questionList.add(0,question)
                recyclerAdapter.notifyItemInserted(0)
            }

        }
    }




}