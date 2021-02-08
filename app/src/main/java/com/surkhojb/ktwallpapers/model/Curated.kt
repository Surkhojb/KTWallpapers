package com.surkhojb.ktwallpapers.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CuratedItems(
    @SerializedName("page")
    val currentPage: Int,
    @SerializedName("per_page")
    val itemsPerPage: Int,
    @SerializedName("photos")
    val wallpapers: List<Wallpaper>,
    @SerializedName("next_page")
    val nextPage: String
)

data class Wallpaper(
    @SerializedName("id")
    val id: Int,
    val width: Int,
    val height: Int,
    @SerializedName("url")
    val websiteUrl: String,
    @SerializedName("photographer")
    val author: String,
    @SerializedName("photographer_url")
    val authorWebsite: String,
    @SerializedName("photographer_id")
    val authorId: String,
    @SerializedName("src")
    val sizes: Size

): Serializable

data class Size(
    @SerializedName("original")
    val originalSize: String,
    @SerializedName("large2x")
    val largex2Size: String,
    @SerializedName("large")
    val largeSize: String,
    @SerializedName("medium")
    val mediumSize: String,
    @SerializedName("small")
    val smallSize: String,
    @SerializedName("portrait")
    val portraitSize: String,
    @SerializedName("landscape")
    val landscapeSize: String,
    @SerializedName("tiny")
    val tinySize: String
): Serializable