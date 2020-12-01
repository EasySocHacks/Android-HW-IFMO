package ru.easy.soc.hacks.hw8.posts

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.lang.ref.WeakReference

@Entity
@Parcelize
class Post (
    @PrimaryKey
    @Json(name = "id") val id : Int,

    @ColumnInfo(name = "userId")
    @Json(name = "userId") val userId : Int,

    @ColumnInfo(name = "title")
    @Json(name = "title") val title : String,

    @ColumnInfo(name = "body")
    @Json(name = "body") val body : String
) : Parcelable

var postList = WeakReference(ArrayList<Post>())
var nextPostId : Int = 1