package com.maheshmanseta.mvvm.datamodels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BeansDataCardParent(
    val title: String?,
    val desc: String?,
    @SerializedName("artworkUrl100")
    val img: String?,
    val artistName: String?,
    val primaryGenreName: String?,
    val list: List<BeansDataCard>,
    var isSelected: Boolean
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeansDataCardParent

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}