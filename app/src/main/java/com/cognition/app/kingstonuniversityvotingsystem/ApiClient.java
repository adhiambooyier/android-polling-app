package com.cognition.app.kingstonuniversityvotingsystem;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adhiambo Oyier on 4/8/2018.
 */

public class ApiClient {
    public static final String BASE_URL = "http://192.168.43.211/kingston/"; //kindly use your own network ip address here
    public static Retrofit retrofit = null;

    public  static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
