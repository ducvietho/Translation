package com.example.vdconfigppclinkadsandroid.utils;


import com.example.vdconfigppclinkadsandroid.data.Constants;
import com.example.vdconfigppclinkadsandroid.data.GetServerConfigResult;
import com.example.vdconfigppclinkadsandroid.delegate.ServerConfigAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionManager {

    public static boolean getListNotifications(Callback<GetServerConfigResult> callback, String appId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServerConfigAPI serverConfigAPI = retrofit.create(ServerConfigAPI.class);
        Call<GetServerConfigResult> call = serverConfigAPI.getServerConfig(appId,
                1,ResourceManager.getInstance().appversion,
                AppDataManager.getInstance().deviceType,
                AppDataManager.getInstance().productType);
        call.enqueue(callback);
        return true;
    }
}
