package com.maheshmanseta.mvvm.ui.mahesh.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Keep
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.maheshmanseta.mvvm.R
import com.maheshmanseta.mvvm.datamodels.BeansDataCard

class AdapterMaheshDashboard(
    mContext: Context,
    moviesList: List<BeansDataCard>?,
    onClickListener: View.OnClickListener,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mDataList: List<BeansDataCard>? = moviesList
    private val mContext: Context = mContext
    private val mOnClickListener: View.OnClickListener = onClickListener

    @Keep
    internal inner class MyViewHolder(v: View?) : RecyclerView.ViewHolder(v!!) {
        var imageView: ImageView = itemView.findViewById(R.id.ivCard)
        var tvSelector: TextView = itemView.findViewById(R.id.tvSelector)
        var tvTitle: TextView = itemView.findViewById(R.id.tvCardTitle)
        var tvDescription: TextView = itemView.findViewById(R.id.tvCardDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_dashboard, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is MyViewHolder) {
            val dataItem: BeansDataCard = mDataList!![position]
            viewHolder.tvSelector.setTag(R.string.tag_index, position)
            viewHolder.tvSelector.setTag(R.string.tag_data, dataItem)
            viewHolder.tvSelector.setOnClickListener(mOnClickListener)
            viewHolder.tvTitle.text = dataItem.title
            viewHolder.tvDescription.text = dataItem.desc

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

}