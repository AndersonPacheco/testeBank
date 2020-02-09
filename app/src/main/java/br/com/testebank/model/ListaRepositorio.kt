package br.com.testebank.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListaRepositorio (
    @SerializedName("total_count") val total_count: Long,
    @SerializedName("incomplete_results") var incomplete_results: String?,
    @SerializedName("items") var items: ArrayList<ItemLista>
) : Parcelable