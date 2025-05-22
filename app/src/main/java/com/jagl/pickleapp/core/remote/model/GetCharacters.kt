package com.jagl.pickleapp.core.remote.model

import com.google.gson.annotations.SerializedName

object GetCharacters {

    data class Response(
        val info: ApiInfo?,
        @SerializedName("results")
        val characters: List<ApiCharacters>?
    ) {
        data class ApiInfo(
            val pages: Int?
        )

        data class ApiCharacters(
            val created: String?,
            val episode: List<String>?,
            val gender: String?,
            val id: Long?,
            val image: String?,
            val location: ApiLocation?,
            val name: String?,
            val origin: ApiOrigin?,
            val species: String?,
            val status: String?,
            val type: String?,
            val url: String?
        )

        data class ApiLocation(
            val name: String?
        )

        data class ApiOrigin(
            val name: String?
        )
    }
}