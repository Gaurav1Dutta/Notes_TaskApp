package com.example.notes_taskapp
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.notes_taskapp.databinding.FragmentNotesListBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Notes_List_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Notes_List_Fragment : Fragment(), ClickInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentNotesListBinding
    lateinit var mainActivity: MainActivity
    lateinit var adapter: NotesListAdapter
    var notesList = arrayListOf<NotesEntites>()
    lateinit var notesDb: NotesDb

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
        binding = FragmentNotesListBinding.inflate(layoutInflater)
        notesDb = NotesDb.getDataBase(mainActivity)
        adapter = NotesListAdapter(notesList, this)
        binding.lvNotes.adapter = adapter
        binding.actionbtnAdd.setOnClickListener {
            mainActivity.navController.navigate(R.id.add_Notes_Fragment)
        }
        getNotes()
        return binding.root
    }

    fun getNotes() {
        notesList.clear()
        adapter.notifyDataSetChanged()  

        class getNotesClass : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg params: Void?): Void? {
                notesList.addAll(notesDb.notesDbInterface().getNotes())
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                adapter.notifyDataSetChanged()
            }
        }
        getNotesClass().execute()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Notes_List_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Notes_List_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDeleteClick(notes: NotesEntites) {

        AlertDialog.Builder(mainActivity)
            .setTitle(mainActivity.resources.getString(R.string.Delete))
            .setMessage(mainActivity.resources.getString(R.string.delete_msg))
            .setPositiveButton(mainActivity.resources.getString(R.string.yes)) { _, _ ->
                class delete : AsyncTask<Void, Void, Void>() {
                    override fun doInBackground(vararg params: Void?): Void? {
                        notesDb.notesDbInterface().DeleteNotes(notes)
                        return null
                    }

                    override fun onPostExecute(result: Void?) {
                        super.onPostExecute(result)
                        //adapter.notifyDataSetChanged()//mam ne nhi likhea
                        getNotes()
                    }
                }
                delete().execute()
            }
            .setNegativeButton(mainActivity.resources.getString(R.string.no)) { _, _ ->
            }
            .show()
    }

    override fun onEditClick(notes: NotesEntites) {
        var bundle = Bundle()
        bundle.putInt("id", notes.id)
        mainActivity.navController.navigate(R.id.add_Notes_Fragment, bundle)

    }
}