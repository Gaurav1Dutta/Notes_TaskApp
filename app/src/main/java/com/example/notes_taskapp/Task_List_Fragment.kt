package com.example.notes_taskapp

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes_taskapp.databinding.FragmentTaskListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Task_List_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Task_List_Fragment : Fragment(), TaskClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentTaskListBinding
    lateinit var mainActivity: MainActivity
    lateinit var adapter: Recycler
    var taskList = arrayListOf<TaskEntites>()
    lateinit var notesDb : NotesDb



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
        binding = FragmentTaskListBinding.inflate(layoutInflater)
        notesDb = NotesDb.getDataBase(mainActivity)
        adapter = Recycler(taskList, this)
        binding.lvTask.layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false)
        binding.lvTask.adapter = adapter
        binding.actionBtnAddTask.setOnClickListener {
            mainActivity.navController.navigate(R.id.add_Task_Fragment)
        }
        getTask()
        return binding.root
    }
    fun getTask() {
        taskList.clear()
        adapter.notifyDataSetChanged()

        class getTaskClass : AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                taskList.addAll(notesDb.notesDbInterface().getTasks())
                return  null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter.notifyDataSetChanged()
            }
        }
        getTaskClass().execute()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Task_List_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Task_List_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onCompleteClick(tasks : TaskEntites) {

        AlertDialog.Builder(mainActivity)
            .setTitle(mainActivity.resources.getString(R.string.completed))
            .setMessage(mainActivity.resources.getString(R.string.conform_complete))
            .setPositiveButton(mainActivity.resources.getString(R.string.yes)) { _, _ ->
                class delete : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        notesDb.notesDbInterface().DeleteTasks(tasks)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        //adapter.notifyDataSetChanged()//mam ne nhi likhea
                        getTask()
                    }
                }
                delete().execute()
            }
            .setNegativeButton(mainActivity.resources.getString(R.string.no)) { _, _ ->
            }
            .show()
    }

    override fun onEditTask(tasks: TaskEntites) {
        var bundle = Bundle()
        bundle.putInt("id", tasks.id)
        mainActivity.navController.navigate(R.id.add_Task_Fragment, bundle)

    }

}