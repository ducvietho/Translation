package com.example.vdconfigppclinkadsandroid.delegate;


import com.example.vdconfigppclinkadsandroid.data.GetServerConfigResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by HungNX on 5/9/16.
 */
public interface ServerConfigAPI {
    @GET("notifications/get_notifications.php?")
    Call<GetServerConfigResult> getServerConfig(@Query("appid") String appid,
                                                @Query("vconfig") int vconfig,
                                                @Query("version") String version,
                                                @Query("target") int target,
                                                @Query("product") int product);
}
