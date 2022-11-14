package com.tcc.alif.data.model

import android.os.Parcelable
import com.tcc.alif.data.util.emptyIfNull
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    val idCompany: String = "",
    val name: String = "",
    val uid: String = ""
): Parcelable {
    fun toCategoryResponse(
        map: MutableMap<String,Any>?
    ): CategoryResponse {
        return if (map == null) {
            CategoryResponse()
        } else {
            CategoryResponse(
                idCompany = map["idCompany"].toString().emptyIfNull(),
                name = map["name"].toString().emptyIfNull(),
                uid = map["uid"].toString().emptyIfNull()
            )
        }
    }

    companion object{
        fun CategoryResponse.modelToMap() =
            mapOf(
                "uid" to this.uid,
                "name" to this.name,
                "idCompany" to this.idCompany,
            )
    }
}
