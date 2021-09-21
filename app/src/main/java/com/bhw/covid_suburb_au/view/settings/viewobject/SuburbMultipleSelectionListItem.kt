package com.bhw.covid_suburb_au.view.settings.viewobject

data class SuburbMultipleSelectionListItem(
    val postcode: Long,
    val display: String,
    val isSelectable: Boolean = true,
    var isSelected: Boolean = false
)
