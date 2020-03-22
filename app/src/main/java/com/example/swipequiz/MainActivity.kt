package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: QuestionAdapter
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    private fun initViews(){
        rvQuestions.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        viewAdapter = QuestionAdapter(questions)
        recyclerView = findViewById(R.id.rvQuestions)
        recyclerView.adapter = viewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))

        }
        questionAdapter.notifyDataSetChanged()

    }


    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {



            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                println(questions[position].answer)
                if ((direction == ItemTouchHelper.RIGHT && questions[position].answer) || (direction == ItemTouchHelper.LEFT && !questions[position].answer)){
                    questions.removeAt(position)
                    rvQuestions.removeViewAt(position)
                    viewAdapter.notifyDataSetChanged()
                    Snackbar.make(
                        recyclerView,
                        "This answer is correct",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                else{
                    viewAdapter.notifyItemChanged(viewHolder.getAdapterPosition())
                    viewAdapter.notifyDataSetChanged()
                    Snackbar.make(
                        recyclerView,
                        "The correct answer is: " + questions[position].answer,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }

        return ItemTouchHelper(callback)
    }
}
