package com.jagl.pickleapp.core.utils.extensions

fun String.getLastNumberOfUrl(): String {
    return this.substringAfterLast("/")
}

val String.Companion.SEARCH_BY_NAME: String
    get() = "name"

val String.Companion.SEARCH_BY_EPISODE: String
    get() = "episode"

