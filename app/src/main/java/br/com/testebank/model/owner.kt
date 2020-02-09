package br.com.testebank.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class owner (
    @SerializedName("id") val id: Long,
    @SerializedName("login") var login: String,
    @SerializedName("avatar_url") var foto: String
) : Parcelable