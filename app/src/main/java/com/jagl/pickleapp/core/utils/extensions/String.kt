package com.jagl.pickleapp.core.utils.extensions

fun String.getLastNumberOfUrl(): String {
    return this.substringAfterLast("/")
}