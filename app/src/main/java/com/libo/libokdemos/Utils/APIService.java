package com.libo.libokdemos.Utils;

import com.libo.libokdemos.MVP.Model.PhoneInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.QueryMap;

/**
 * Retrofit参数
 * https://www.jianshu.com/p/7687365aa946
 * Created by libok on 2018-01-09.
 */

public interface APIService {

    @HTTP(method = "GET", path = "", hasBody = false)
    @FormUrlEncoded
    Call<String> get();

    /**
     * 可用
     * @param queryMap
     * @return
     */
    @HTTP(method = "GET", path = "callback", hasBody = false)
    Observable<PhoneInfo> getPhoneInfo(@QueryMap Map<String, String> queryMap);

    /**
     * 可用
     * @param fieldMap
     * @return
     */
    @GET("callback")
    Call<PhoneInfo> getPhoneInfo1(@QueryMap Map<String, String> fieldMap);

    /**
     * @NonNull
     @HTTP(method = "POST", path = "api/user/sms", hasBody = true)
     @FormUrlEncoded Call<FeedBackBean> postFeed(@Query("token") String token, @Field("uid") String uid, @Field("mobile") String mobile, @Field("content") String content);

     @NonNull
     @HTTP(method = "POST", path = "api/user/sms", hasBody = true)
     @FormUrlEncoded Observable<FeedBackBean> postFeed1(@Query("token") String token, @FieldMap Map<String, String> params);
     */

}
