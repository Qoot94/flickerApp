package com.example.flickerapp

data class Photo(
    var farm: Int =0,
    val height_h: Int=0,
    val id: String="",
    val isfamily: Int=0,
    val isfriend: Int=0,
    val ispublic: Int=0,
    val owner: String="",
    val secret: String="",
    val server: String="",
    val tags: String="",
    val title: String,
    val url_h: String,
    val width_h: Int=0
)