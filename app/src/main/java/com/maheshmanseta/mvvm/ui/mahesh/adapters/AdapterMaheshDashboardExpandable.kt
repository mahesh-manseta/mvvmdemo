package com.maheshmanseta.mvvm.ui.mahesh.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Keep
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.maheshmanseta.mvvm.R
import com.maheshmanseta.mvvm.datamodels.BeansDataCard
import com.maheshmanseta.mvvm.datamodels.BeansDataCardParent

class AdapterMaheshDashboardExpandable(
    mContext: Context,
    moviesList: List<BeansDataCardParent>?,
    onClickListener: View.OnClickListener,
    onShuffleListener: View.OnClickListener,
    filter: String
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mDataList: List<BeansDataCardParent>? = moviesList
    private val mContext: Context = mContext
    private val mOnClickListener: View.OnClickListener = onClickListener
    private val mOnShuffleListener: View.OnClickListener = onShuffleListener
    private val mFilter: String = filter

    @Keep
    internal inner class MyViewHolder(v: View?) : RecyclerView.ViewHolder(v!!) {
        var imageView: ImageView = itemView.findViewById(R.id.ivCard)
        var imageShuffle: ImageView = itemView.findViewById(R.id.ivShuffle)
        var textView: TextView = itemView.findViewById(R.id.tvCard)
        var imageIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        var tvSelector: TextView = itemView.findViewById(R.id.tvSelector)
        var tvTitle: TextView = itemView.findViewById(R.id.tvCardTitle)
        var tvDescription: TextView = itemView.findViewById(R.id.tvCardDescription)
        var recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)
        var viewMore: View = itemView.findViewById(R.id.viewMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_expanded, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MyViewHolder) {
            val dataItem: BeansDataCardParent = mDataList!![position]
            viewHolder.tvSelector.setTag(R.string.tag_index, position)
            viewHolder.tvSelector.setTag(R.string.tag_data, dataItem)
            viewHolder.tvSelector.setOnClickListener(mOnClickListener)

            viewHolder.imageShuffle.setTag(R.string.tag_index, position)
            viewHolder.imageShuffle.setTag(R.string.tag_data, dataItem)
            viewHolder.imageShuffle.setOnClickListener(mOnShuffleListener)

            viewHolder.tvTitle.text = if(mFilter=="Group by Artist") dataItem.artistName else dataItem.primaryGenreName
            viewHolder.tvDescription.text = dataItem.desc
            viewHolder.tvDescription.visibility = View.GONE
            viewHolder.textView.text = if(mFilter=="Group by Artist") dataItem.artistName!!.substring(0,1) else dataItem.primaryGenreName!!.substring(0,1)

            if(mFilter=="Group by Artist" && dataItem.artistName=="Radiohead"){
                viewHolder.imageShuffle.visibility = View.VISIBLE
            }else{
                viewHolder.imageShuffle.visibility = View.INVISIBLE
            }

            val layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            viewHolder.recyclerView.layoutManager = layoutManager
            viewHolder.recyclerView.adapter = AdapterMaheshDashboard(
                mContext,
                dataItem.list,
                onClickListenerDetail
            )

            if(dataItem.isSelected){
                viewHolder.viewMore.visibility = View.VISIBLE
                viewHolder.imageIcon.setImageResource(R.drawable.ic_arrow_up)
            }else{
                viewHolder.viewMore.visibility = View.GONE
                viewHolder.imageIcon.setImageResource(R.drawable.ic_arrow_down)
            }

            viewHolder.imageView.visibility = View.GONE
            Glide.with(viewHolder.imageView.context)
                .load(dataItem.img).apply(
                    RequestOptions().fitCenter()
                        .placeholder(R.drawable.ic_notifications_black_24dp)
                )
                .listener(
                    object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any,
                            target: Target<Drawable?>,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            return false
                        }
                    })
                .into(viewHolder.imageView)
        }
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: 0
    }

    private var onClickListenerDetail: View.OnClickListener = View.OnClickListener {
        val dataArtist: BeansDataCard = it.getTag(R.string.tag_data) as BeansDataCard
    }

}