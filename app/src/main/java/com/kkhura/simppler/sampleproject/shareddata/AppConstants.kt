package com.kkhura.simppler.sampleproject.shareddata

object ApplicationConstants {
    lateinit var token: String
    const val REQUEST_CODE = 1337
    const val CLIENT_ID = "a83b71be4c7c4787a3758eee744a0411"
    const val REDIRECT_URI = "simpplrspotify://callback/"
    @JvmField
    val REQUESTED_SCOPE = arrayOf("user-library-read", "user-read-email",
            "user-read-birthdate", "user-read-private")

    const val ALBUM_TITLE = "album title"
    const val SELECTED_ALBUM_INDEX = "selected album index"
    const val ALBUM = "album"
    const val ALBUM_URL = "album url"
}