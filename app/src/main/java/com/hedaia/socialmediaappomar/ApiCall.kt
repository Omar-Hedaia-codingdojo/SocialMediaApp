package com.hedaia.socialmediaappomar

import retrofit2.Response
import retrofit2.http.*

interface ApiCall {

    @GET("/users/")
    suspend fun getUsers():Response<User>

    @GET("/users/{apiKey}")
    suspend fun getUserDetails(@Path("apiKey") apiKey:String):Response<UserDetails>

    @POST("/users/")
    suspend fun addNewUser(@Body user: UserDetails):Response<UserItem>

    @GET("/login/{username}/{password}")
    suspend fun getUserApiKey(@Path("username") username:String,@Path("password") password:String):Response<String>

    @GET("/posts/")
    suspend fun getAllPosts():Response<Posts>

    @POST("/posts/")
    suspend fun addNewPost(@Body post: PostsItem):Response<PostsItem>

    @GET("/posts/{id}")
    suspend fun getPostDetails(@Path("id") id:Int):Response<PostsItem>

    @PUT("/posts/{id}")
    suspend fun updatePostDetails(@Path("id") id:Int,@Body post:PostsItem):Response<PostsItem>





}