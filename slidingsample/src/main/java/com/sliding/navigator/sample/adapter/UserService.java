package com.sliding.navigator.sample.adapter;

import com.sliding.navigator.sample.constructor.CheckWallet;
import com.sliding.navigator.sample.constructor.ImgUpload;
import com.sliding.navigator.sample.constructor.LoginRequest;
import com.sliding.navigator.sample.constructor.LoginResponse;
import com.sliding.navigator.sample.constructor.SendTransferRequest;
import com.sliding.navigator.sample.constructor.SetPassword;
import com.sliding.navigator.sample.constructor.TokenRequest;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface UserService {
    @POST("login")
    Call<LoginResponse> uerLogin(@Body LoginRequest loginRequest);

    @GET("user")
    Call<TokenRequest> userTokenRequest(@Header("Authorization") String token);

    @GET("orders")
    Call<Object> userOrderRequest(@Query("page") int param, @Header("Authorization") String orders);

    @GET("users")
    Call<Object> userSearchRequest(@Header("Authorization") String users);

    @POST("wallet/transfer")
    Call<Object> saveVotes(@Header("Authorization") String token, @Body SendTransferRequest transferRequest);

    @GET("transfers")
    Call<Object> userCheskWallet(@Query("page") int param, @Header("Authorization") String token);

    @Multipart
    @POST("/api/user/photo")
    Call<ImgUpload> userSetPhoto(@Header("Authorization") String token, @Part MultipartBody.Part photo);

    @POST("user/password")
    Call<Object> userChangePassword(@Header("Authorization") String token, @Body SetPassword setPassword);
    @POST("wallet")
    Call<Object> userWalletname(@Header("Authorization") String token, @Body CheckWallet checkWallet);
}