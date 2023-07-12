package com.example.notes_taskapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notes_taskapp.databinding.FragmentAddNotesBinding
import java.text.SimpleDateFormat
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Add_Notes_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Add_Notes_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentAddNotesBinding
    lateinit var mainActivity: MainActivity
    lateinit var notesDb: NotesDb
    private var notesID = -1
    var notes_entites = NotesEntites()

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
        binding = FragmentAddNotesBinding.inflate(layoutInflater)
        notesDb = NotesDb.getDataBase(mainActivity)
        arguments?.let {
            notesID = it.getInt("id")
            if(notesID>-1){
                getEntityInfo()
            }
        }
        binding.btnAdd.setOnClickListener {
            if(binding.etTitle.text.toString().isNullOrBlank()){
                binding.etTitle.error = mainActivity.resources.getString(R.string.enter_title)
            }else if(binding.etDescription.text.toString().isNullOrBlank()){
                binding.etDescription.error = mainActivity.resources.getString(R.string.enter_description)
            }else{
                if(notesID >-1){
                    var notes_entities = NotesEntites(id= notes_entites.id, title= binding.etTitle.text.toString(),
                        description = binding.etDescription.text.toString(),time = binding.tvPickTime.text.toString(),
                    date = binding.tvPickDate.text.toString())
                    class updateClass : AsyncTask<Void, Void, Void>(){
                        override fun doInBackground(vararg params: Void?): Void? {
                            notesDb.notesDbInterface().UpdateNotes(notes_entities)
                            return null
                        }
                        override fun onPostExecute(result: Void?) {
                            super.onPostExecute(result)
                            mainActivity.navController.popBackStack()
                        }
                    }
                    updateClass().execute()
                }else{

                    var notes_entites = NotesEntites(title= binding.etTitle.text.toString(),
                        description = binding.etDescription.text.toString(),time = binding.tvPickTime.text.toString(),
                        date = binding.tvPickDate.text.toString())
                    var notesId = -1L
                    class insertClass : AsyncTask<Void, Void, Void>(){
                        override fun doInBackground(vararg params: Void?): Void? {

                            notesId = notesDb.notesDbInterface().InsertNotes(notes_entites)
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
        binding.tvPickDate.setOnClickListener {
            var datePicker = DatePickerDialog(mainActivity, {_,year, month, date->
                var simpleDateFormat = SimpleDateFormat("dd-MMM-yyyy")
                var calendar = Calendar.getInstance()
                calendar.set(year, month, date)
                var selectedDate = simpleDateFormat.format(calendar.time)
                binding.tvPickDate.setText(selectedDate)
                System.out.println("in selected Date $selectedDate")
            }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DATE))
            datePicker.show()
        }
        binding.tvPickTime.setOnClickListener {
            var timePicker = TimePickerDialog(mainActivity,{_,hours,minutes,->
                var simpleTimePicker = SimpleDateFormat("hh-mm aa")
                var calender = Calendar.getInstance()
                calender.set(Calendar.HOUR,hours)
                calender.set(Calendar.MINUTE,minutes)
                var selectedTime = simpleTimePicker.format(calender.time)
                binding.tvPickTime.setText("$selectedTime")
                System.out.println("in selected time $selectedTime")
            },Calendar.getInstance().get(Calendar.HOUR),
                Calendar.getInstance().get(Calendar.MINUTE),false)
            timePicker.show()
        }
        return binding.root
    }
    fun getEntityInfo(){
        class getEntity : AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg params: Void?): Void? {
                notes_entites = notesDb.notesDbInterface().getNotesById(notesID)
                return null
            }
            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                binding.etTitle.setText(notes_entites.title)
                binding.etDescription.setText(notes_entites.description)
                binding.tvPickDate.setText(notes_entites.date)
                binding.tvPickTime.setText(notes_entites.time)
                binding.btnAdd.setText("Update")
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
         * @return A new instance of fragment Add_Notes_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Add_Notes_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}