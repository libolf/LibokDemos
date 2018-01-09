package com.libo.libokdemos.Utils;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;

/**
 * Created by libok on 2018-01-09.
 */

public interface APIService {

    @HTTP(method = "GET", path = "", hasBody = false)
    @FormUrlEncoded
    Call<String> get();

    /**
     * @NonNull
     @HTTP(method = "POST", path = "api/user/sms", hasBody = true)
     @FormUrlEncoded
     Call<FeedBackBean> postFeed(@Query("token") String token, @Field("uid") String uid, @Field("mobile") String mobile, @Field("content") String content);

     @NonNull
     @HTTP(method = "POST", path = "api/user/sms", hasBody = true)
     @FormUrlEncoded
     Observable<FeedBackBean> postFeed1(@Query("token") String token, @FieldMap Map<String, String> params);
     */

}
