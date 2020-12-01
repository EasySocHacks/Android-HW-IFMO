package ru.easy.soc.hacks.hw8.posts.service

import retrofit2.Call
import retrofit2.http.*
import ru.easy.soc.hacks.hw8.posts.Post

interface PostService {
    @GET("posts")
    fun getPostList() : Call<List<Post>>

    @POST("posts")
    fun createPost(
        @Body newPost : Post
    ) : Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(
        @Path("id") id :Int
    ) : Call<Void>
}