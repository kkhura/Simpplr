package com.kkhura.simppler.sampleproject.ui.home.model

import android.os.Parcel
import android.os.Parcelable

data class Album(val album_type: String,
                 val artists: List<Artist>,
                 val available_markets: List<String>,
                 val genres: List<Any>,
                 val id: String,
                 val images: List<Image>,
                 val name: String,
                 val popularity: Int,
                 val release_date: String,
                 val release_date_precision: String,
                 val tracks: Tracks,
                 val type: String,
                 val uri: String)

data class Artist(val external_urls: ExternalUrls,
                  val href: String,
                  val id: String,
                  val name: String,
                  val type: String,
                  val uri: String)

data class Artist_(val href: String,
                   val id: String,
                   val name: String,
                   val type: String,
                   val uri: String):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(href)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Artist_> {
        override fun createFromParcel(parcel: Parcel): Artist_ {
            return Artist_(parcel)
        }

        override fun newArray(size: Int): Array<Artist_?> {
            return arrayOfNulls(size)
        }
    }

}

data class Item(val added_at: String,
                val album: Album)

data class Tracks(val href: String,
                  val items: List<Item_>,
                  val limit: Int,
                  val next: Any,
                  val previous: Any,
                  val total: Int)

data class Item_(val artists: List<Artist_>,
                 val available_markets: List<String>,
                 val disc_number: Int,
                 val duration_ms: Int,
                 val explicit: Boolean,
                 val href: String,
                 val id: String,
                 val name: String,
                 val preview_url: String,
                 val track_number: Int,
                 val type: String,
                 val uri: String) : Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.createTypedArrayList(Artist_),
            parcel.createStringArrayList(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(artists)
        parcel.writeStringList(available_markets)
        parcel.writeInt(disc_number)
        parcel.writeInt(duration_ms)
        parcel.writeByte(if (explicit) 1 else 0)
        parcel.writeString(href)
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(preview_url)
        parcel.writeInt(track_number)
        parcel.writeString(type)
        parcel.writeString(uri)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item_> {
        override fun createFromParcel(parcel: Parcel): Item_ {
            return Item_(parcel)
        }

        override fun newArray(size: Int): Array<Item_?> {
            return arrayOfNulls(size)
        }
    }
}