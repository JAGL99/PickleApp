package com.jagl.pickleapp.core.remote.model

import com.google.gson.annotations.SerializedName

object GetEpisodes {

    data class Response(
        val info: ApiInfo?,
        @SerializedName("results")
        val episodes: List<RemoteEpisode>?
    )

    data class RemoteEpisode(
        @SerializedName("air_date")
        val airDate: String,
        val characters: List<String>,
        val created: String,
        val episode: String,
        val id: Long,
        val name: String,
        val url: String
    )
}