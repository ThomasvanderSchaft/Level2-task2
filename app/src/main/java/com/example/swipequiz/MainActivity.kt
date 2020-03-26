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
        //calls the createItemTouchHelper for the recyclerView
        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        viewAdapter = QuestionAdapter(questions)
        //gets layout item and assigns it to variable
        recyclerView = findViewById(R.id.rvQuestions)
        //assigns the adapter items to the recyclerView
        recyclerView.adapter = viewAdapter
        //assigns a specific layout to the recycler view
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        //loops through all questions
        for (i in Question.QUESTIONS.indices) {
            //adds the question and answer of the question to the adapter
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))

        }
        //notifies adapter that there has been a change
        questionAdapter.notifyDataSetChanged()

    }


    private fun createItemTouchHelper(): ItemTouchHelper {
        //creates callback value that's able to swipe left and right
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {



            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //gets the position of the swiped item
                val position = viewHolder.adapterPosition
                //prints the answer of the swiped item
                println(questions[position].answer)
                //checks if the question was swiped in the correct direction
                if ((direction == ItemTouchHelper.RIGHT && questions[position].answer) || (direction == ItemTouchHelper.LEFT && !questions[position].answer)){
                    //removes question from ArrayList
                    questions.removeAt(position)
                    //removes question from recyclerView
                    rvQuestions.removeViewAt(position)
                    //notifies adapter that there has been a change
                    viewAdapter.notifyDataSetChanged()
                    //creates snackbar message
                    Snackbar.make(
                        recyclerView,
                        "This answer is correct",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
                //doesn't remove item if the answer wasn't correct
                else{
                    viewAdapter.notifyItemChanged(viewHolder.getAdapterPosition())
                    viewAdapter.notifyDataSetChanged()
                    //creates snackbar message with correct answer
                    Snackbar.make(
                        recyclerView,
                        "The correct answer is: " + questions[position].answer,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
        //returns result
        return ItemTouchHelper(callback)
    }
}
