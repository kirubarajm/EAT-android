package com.tovo.eat.api;



import com.tovo.eat.data.model.api.LoginRequest;
import com.tovo.eat.data.model.api.LoginResponse;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
    @POST("users")
    Call<LoginResponse> users(
            @Field("name") String name,
            @Field("job") String job
    );

    Single<LoginResponse> doServerLoginApiCall(LoginRequest.ServerLoginRequest request);
}
