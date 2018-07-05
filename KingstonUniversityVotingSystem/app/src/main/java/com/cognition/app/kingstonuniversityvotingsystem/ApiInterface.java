package com.cognition.app.kingstonuniversityvotingsystem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Adhiambo Oyier on 4/8/2018.
 */

public interface ApiInterface {
    @FormUrlEncoded
    @POST("register.php")
    Call<User> createUser(
            @Field("id_number") String id_number,
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<User> loginUser(
            @Field("id_number") String id_number,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("create_post.php")
    Call<Poll> createPoll(
            @Field("id_number") String id_number,
            @Field("topic") String topic,
            @Field("expiry") String expiry,
            @Field("answers") String answers
    );

    @GET("mypolls.php")
    Call<List<Poll>> retrieveMyPolls(
            @Query("id_number") String id_number
    );

    @GET("browse_polls.php")
    Call<List<Poll>> retrievePolls();

    @GET("browse_polls.php")
    Call<List<Poll>> retrieveChoices();

    @GET("browse_forum.php")
    Call<List<Post>> retrievePosts();

    @FormUrlEncoded
    @POST("create_forum_question.php")
    Call<Post> createPost(
            @Field("id_number") String id_number,
            @Field("question") String question
    );
}

