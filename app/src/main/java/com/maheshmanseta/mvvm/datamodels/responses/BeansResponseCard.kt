package com.maheshmanseta.mvvm.datamodels.responses

import com.maheshmanseta.mvvm.datamodels.BeansDataCard
import java.io.Serializable

data class BeansResponseCard(
    val resultCount: Int,
    val results: ArrayList<BeansDataCard>?,
    var isSelected: Boolean
) : Serializable