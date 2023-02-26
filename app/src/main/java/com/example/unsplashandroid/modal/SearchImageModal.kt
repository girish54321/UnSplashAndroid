package com.example.unsplashandroid.modal

import com.google.gson.annotations.SerializedName

data class SearchImageModal(
    @SerializedName("total") var total: Int? = null,
    @SerializedName("total_pages") var totalPages: Int? = null,
    @SerializedName("results") var results: List<UnPlashResponse> = arrayListOf()

)