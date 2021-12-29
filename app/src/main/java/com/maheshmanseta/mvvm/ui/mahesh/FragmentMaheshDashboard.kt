package com.maheshmanseta.mvvm.ui.mahesh

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.maheshmanseta.mvvm.R
import com.maheshmanseta.mvvm.databinding.FragmentMaheshDashboardBinding
import com.maheshmanseta.mvvm.datamodels.BeansDataCard
import com.maheshmanseta.mvvm.datamodels.BeansDataCardParent
import com.maheshmanseta.mvvm.datamodels.responses.BeansResponseCard
import com.maheshmanseta.mvvm.retrofit.RetroEnum
import com.maheshmanseta.mvvm.ui.mahesh.adapters.AdapterMaheshDashboardExpandable
import com.maheshmanseta.mvvm.ui.mahesh.viewmodels.DataModelMaheshDashboard
import com.maheshmanseta.mvvm.utils.AppConstants
import com.maheshmanseta.mvvm.utils.AppLog
import com.maheshmanseta.mvvm.utils.AppPreferences
import com.maheshmanseta.mvvm.utils.AppUtils
import java.util.*


class FragmentMaheshDashboard : Fragment(R.layout.fragment_mahesh_dashboard) {

    private lateinit var homeViewModelDashboard: DataModelMaheshDashboard
    private var _binding: FragmentMaheshDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private var resFinal: BeansResponseCard? = null
    private var resFinalFiltered: ArrayList<BeansDataCardParent>? = null
    private var mAdapterMahesh: AdapterMaheshDashboardExpandable? = null
    private var mCurrentFilterArtist: String = "Group by Artist"
    private var mCurrentFilterGenre: String = "Group by Genre"
    private var mCurrentFilter: String = mCurrentFilterArtist
    private var dialogFilter: AlertDialog? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        homeViewModelDashboard =
            ViewModelProvider(this)[DataModelMaheshDashboard::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMaheshDashboardBinding.bind(view)

        val layoutManagerGenre = LinearLayoutManager(context)
        binding.recyclerViewGenre.layoutManager = layoutManagerGenre
        binding.mSwipeRefreshLayout.setOnRefreshListener {
            loadData()
        }
        binding.mSwipeRefreshLayout.isEnabled = false
        binding.btnRetry.setOnClickListener {
            loadData()
        }

        ViewCompat.setNestedScrollingEnabled(binding.recyclerViewGenre, true)
        loadData()
    }

    private fun loadData() {
        binding.mSwipeRefreshLayout.visibility = View.GONE
        binding.pBar.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.VISIBLE
        binding.ivEmpty.visibility = View.GONE
        binding.tvEmpty.text = mContext.resources.getString(R.string.message_hold_on)
        homeViewModelDashboard.getData(
            AppPreferences.userToken.toString(),
            AppUtils.isConnected(mContext)
        )
            .observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        RetroEnum.SUCCESS -> {
                            binding.btnRetry.visibility = View.GONE
                            binding.tvEmpty.text =
                                mContext.resources.getString(R.string.message_hold_on)
                            resource.data?.let { users -> retrieveList(users) }
                        }
                        RetroEnum.ERROR -> {
                            binding.btnRetry.visibility = View.VISIBLE
                            binding.pBar.visibility = View.GONE
                            binding.mSwipeRefreshLayout.isRefreshing = false
                            binding.mSwipeRefreshLayout.visibility = View.GONE
                            binding.pBar.visibility = View.GONE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.ivEmpty.visibility = View.GONE
                            binding.tvEmpty.text =
                                if (AppUtils.isConnected(mContext)) (
                                        if (AppConstants.IS_LOG_ENABLED) resource.message else mContext.resources.getString(
                                            R.string.message_error
                                        ))
                                else (mContext.resources.getString(
                                    R.string.message_connection
                                ))
                        }
                        RetroEnum.LOADING -> {
                            binding.btnRetry.visibility = View.GONE
                            binding.pBar.visibility = View.VISIBLE
                            binding.mSwipeRefreshLayout.isRefreshing = false
                            binding.mSwipeRefreshLayout.visibility = View.GONE
                            binding.pBar.visibility = View.VISIBLE
                            binding.tvEmpty.visibility = View.VISIBLE
                            binding.ivEmpty.visibility = View.GONE
                            binding.tvEmpty.text =
                                mContext.resources.getString(R.string.message_hold_on)
                        }
                    }
                }
            })
    }

    class CustomComparator : Comparator<BeansDataCard> {
        override fun compare(obj1: BeansDataCard, obj2: BeansDataCard): Int {
            return obj1.card_no.compareTo(obj2.card_no)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_filter_home) {
            showFilter()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun retrieveList(resVariantListing: BeansResponseCard?) {
        try {
            if (resVariantListing != null) {
                setData(resHomeListingFinal = resVariantListing)
            }
        } catch (e: Exception) {
            if (AppConstants.IS_LOG_ENABLED) {
                e.printStackTrace()
            }
            AppLog.e("Data Error", e.message!!)
            setError(mContext.resources.getString(R.string.message_error))
        }
    }

    private fun setError(message: String) {
        binding.pBar.visibility = View.GONE
        binding.mSwipeRefreshLayout.isRefreshing = false
        binding.mSwipeRefreshLayout.visibility = View.GONE
        binding.pBar.visibility = View.GONE
        binding.tvEmpty.visibility = View.VISIBLE
        binding.ivEmpty.visibility = View.GONE
        binding.tvEmpty.text = message
    }

    private fun setData(resHomeListingFinal: BeansResponseCard) {
        this.resFinal = resHomeListingFinal
        val gson = Gson()
        val string: String = gson.toJson(resFinal)
        AppPreferences.userData = string

        binding.pBar.visibility = View.GONE
        binding.mSwipeRefreshLayout.isRefreshing = false
        binding.mSwipeRefreshLayout.visibility = View.VISIBLE
        binding.tvEmpty.visibility = View.GONE
        binding.ivEmpty.visibility = View.GONE

        Collections.sort(resHomeListingFinal.results!!, CustomComparator())

        setDataSorted()

        binding.tvHeaderUser.text =
            mContext.resources.getString(R.string.message_created_by, AppPreferences.userName)

        ViewCompat.setNestedScrollingEnabled(binding.recyclerViewGenre, true)
    }

    private fun setDataSorted() {
        val arrayListItems = ArrayList(
            resFinal!!.results!!.groupBy {
                if (mCurrentFilter == mCurrentFilterArtist) it.artistName else it.primaryGenreName
            }.values
        )
        resFinalFiltered = ArrayList()
        for (i in 0 until arrayListItems.size) {
            val item = arrayListItems[i]!![0]
            AppLog.e(
                "Print: ",
                "Song Name: ${item.title} Artist: ${item.artistName} Category: ${item.primaryGenreName}"
            )
            val itemCardParent = BeansDataCardParent(
                item.title,
                item.desc, item.img, item.artistName, item.primaryGenreName,
                arrayListItems[i], false
            )
            resFinalFiltered!!.add(itemCardParent)
        }
        mAdapterMahesh = AdapterMaheshDashboardExpandable(
            mContext,
            resFinalFiltered,
            onClickListenerGenre,
            onClickListenerShuffle,
            mCurrentFilter
        )
        binding.recyclerViewGenre.adapter = mAdapterMahesh
        binding.tvHeaderMessage.text = mCurrentFilter
    }

    @SuppressLint("NotifyDataSetChanged")
    private var onClickListenerGenre: View.OnClickListener = View.OnClickListener {
        val position = it.getTag(R.string.tag_index) as Int
        for (item in resFinalFiltered!!.indices) {
            if (item == position) {
                resFinalFiltered!![item].isSelected = !resFinalFiltered!![item].isSelected
            } else {
                resFinalFiltered!![item].isSelected = false
            }
        }
        AppLog.e("Selection: $position", resFinalFiltered!![position].isSelected)

        for (item in resFinalFiltered!!.indices) {
            AppLog.e("Selection: $item", resFinalFiltered!![item].isSelected)
        }
        mAdapterMahesh!!.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private var onClickListenerShuffle: View.OnClickListener = View.OnClickListener {
        val position = it.getTag(R.string.tag_index) as Int
        Collections.shuffle(resFinalFiltered!![position].list)
        mAdapterMahesh!!.notifyDataSetChanged()
    }

    private fun showFilter() {
        val layoutInflater1 = layoutInflater
        val prompt1View = layoutInflater1.inflate(R.layout.dialog_filter, null)

        val btnCancel = prompt1View.findViewById<View>(R.id.btnDialogCancel) as Button
        val btnSave = prompt1View.findViewById<View>(R.id.btnDialogSave) as Button
        val checkBoxArtist =
            prompt1View.findViewById<View>(R.id.checkboxFilterArtist) as RadioButton
        val checkBoxGenre = prompt1View.findViewById<View>(R.id.checkboxFilterGenre) as RadioButton

        if (mCurrentFilter == mCurrentFilterArtist) {
            checkBoxArtist.isChecked = true
        } else if (mCurrentFilter == mCurrentFilterGenre) {
            checkBoxGenre.isChecked = true
        }

        btnSave.setOnClickListener {
            dialogFilter!!.cancel()
            if (checkBoxArtist.isChecked) {
                mCurrentFilter = mCurrentFilterArtist
            } else if (checkBoxGenre.isChecked) {
                mCurrentFilter = mCurrentFilterGenre
            }
            setDataSorted()
        }

        btnCancel.setOnClickListener {
            dialogFilter!!.cancel()
        }

        val alertDialogBuilder1 = AlertDialog.Builder(mContext, R.style.MyAlertDialogTheme)
        alertDialogBuilder1.setView(prompt1View)
        dialogFilter = alertDialogBuilder1.create()
        dialogFilter!!.setCancelable(false)
        dialogFilter!!.show()
    }

}