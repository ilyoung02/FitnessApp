package com.example.fitnessapp.config;

import com.example.fitnessapp.domain.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 스프링 서버에 호출할 api 메소드
 * */
public interface ApiService {

    @POST("/join")
    Call<User> join(@Body User user);

    @POST("/login")
    Call<User> loginUser(@Body User user);

}
