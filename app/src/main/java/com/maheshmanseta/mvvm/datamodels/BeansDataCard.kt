package com.maheshmanseta.mvvm.datamodels

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BeansDataCard(
    val artistName: String?,
    val primaryGenreName: String?,
    @SerializedName("trackName")
    val title: String,
    @SerializedName("description")
    val desc: String,
    @SerializedName("artworkUrl100")
    val img: String?,
    val card_no: Int,
    var isSelected: Boolean
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeansDataCard

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}