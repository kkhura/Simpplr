package com.kkhura.simppler.sampleproject.ui.home.model

sealed class Response
data class UserModel(
        val country: String,
        val email: String,
        val birthdate: String,
        val display_name: String,
        val external_urls: ExternalUrls,
        val followers: Followers,
        val href: String,
        val id:String,
        val images: List<Image>,
        val product: String,
        val type: String,
        val uri: String
) : Response()

data class AlbumContainerModel(val items: List<Item>,
                               val limit: Int,
                               val offset: Int,
                               val total: Int): Response()
data class Error(val msg: String) : Response()
data class ExternalUrls(val spotify:String)
data class Image(
        val url: String,
        val width: Int,
        val height: Int
)
data class Followers(val href: String, val total:String)