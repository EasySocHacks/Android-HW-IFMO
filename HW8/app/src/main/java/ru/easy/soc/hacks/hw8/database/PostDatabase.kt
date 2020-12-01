package ru.easy.soc.hacks.hw8.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.easy.soc.hacks.hw8.posts.Post

@Database(entities = [Post :: class], version = 1)
abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao() : PostsDao
}