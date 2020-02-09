package br.com.testebank.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemLista (
    @SerializedName("id") val id: Long,
    @SerializedName("name") var nomeRepositorio: String?,
    @SerializedName("stargazers_count") var stargazersCount: Long,
    @SerializedName("forks_count") var forksCount: Long,
    @SerializedName("owner") var owner: owner
) : Parcelable