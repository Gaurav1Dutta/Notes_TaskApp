package com.example.notes_taskapp

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes_taskapp.databinding.FragmentAddTaskBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add_Task_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add_Task_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentAddTaskBinding
    lateinit var mainActivity: MainActivity
    lateinit var notesDb: NotesDb
    private  var taskId = -1
    var tasks = TaskEntites()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        notesDb = NotesDb.getDataBase(mainActivity)
        arguments?.let {
            taskId = it.getInt("id")
            if (taskId > -1) {
                getEntityInfo()
            }
        }

        binding.saveTask.setOnClickListener {
            if (binding.etTask1.text.toString().isNullOrBlank()) {
                binding.etTask1.error = mainActivity.resources.getString(R.string.enter_task)
            } else {
                if (taskId > -1) {
                    var tasks = TaskEntites(id = tasks.id, Task1 = binding.etTask1.text.toString())

                    class updateClass : AsyncTask<Void, Void, Void>() {
                        override fun doInBackground(vararg params: Void?): Void? {
                            notesDb.notesDbInterface().UpdateTasks(tasks)
                            return null
                        }

                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            mainActivity.navController.popBackStack()
                        }
                    }
                    updateClass().execute()
                } else {
                    var tasks = TaskEntites(Task1 = binding.etTask1.text.toString())
                    var taskId = -1L

                    class insertClass : AsyncTask<Void, Void, Void>() {
                        override fun doInBackground(vararg params: Void?): Void? {
                            taskId = notesDb.notesDbInterface().InsertTasks(tasks)
                            return null
                        }

                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            mainActivity.navController.popBackStack()
                        }
                    }
                    insertClass().execute()
                }
            }

        }
        return binding.root
    }

    fun getEntityInfo(){
        class getEntity : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                tasks = notesDb.notesDbInterface().getTasksById(taskId)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding.etTask1.setText(tasks.Task1)

                binding.saveTask.setText("Update")
            }

        }
        getEntity().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Add_Task_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Add_Task_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}