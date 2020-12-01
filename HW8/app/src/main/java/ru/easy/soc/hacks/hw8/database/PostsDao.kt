package ru.easy.soc.hacks.hw8.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.easy.soc.hacks.hw8.posts.Post

@Dao
interface PostsDao {
    @Query(value = "select * from post")
    fun getAll() : List<Post>

    @Query(value = "select * from post where id = (:id)")
    fun getPostById(id : Int) : Post

    @Query(value = "delete from post")
    fun deleteAllPosts() : Unit

    @Query(value = "delete from post where id = (:id)")
    fun deletePostById(id : Int) : Unit

    @Insert
    fun insertAll(vararg posts: Post) : Unit
}