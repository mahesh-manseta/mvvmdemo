package com.maheshmanseta.mvvm.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.maheshmanseta.mvvm.databinding.FragmentDashboardBinding
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var mContext: Context

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        dashboardViewModel.textSize.observe(viewLifecycleOwner, Observer {
            for ((key, value) in it) {
                Log.e("SIZE","$key = $value kb")
            }
        })

        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

//    private fun getSize(): HashMap<String, Long> {
//        Log.e("Size Calculate","Check")
//        val map = HashMap<String, Long>()
//
//        val file = File(mContext.getExternalFilesDir(null)!!.absolutePath)
//        if (!file.exists()) {
//            map["Empty"] = 0
//            return map
//        }
//        if (!file.isDirectory) {
//            map["Not a directory"] = file.length()
//            return map
//        }
//        val dirs: LinkedList<File> = LinkedList<File>();
//        dirs.add(file)
//        var result: Long = 0
//        while (!dirs.isEmpty()) {
//            val dir: File = dirs.removeAt(0);
//            Log.e("Size Calculate","Check: ${dir.name} ${dir.path}")
//            if (!dir.exists()) {
//                Log.e("Size Calculate Not","Check: ${dir.name} ${dir.path}")
//                continue;
//            }
//            val listFiles = dir.listFiles()
//            if (listFiles == null || listFiles.isEmpty()) {
//                Log.e("Size Calculate Empty","Check: ${dir.name} ${dir.path}")
//                continue;
//            }
//            for(child in listFiles){
//                result+=child.length()
//                if(child.isDirectory){
//                    dirs.add(child)
//                    map[child.name] = result
//                }
//            }
//        }
//        return map;
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}