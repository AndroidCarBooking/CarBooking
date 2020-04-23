package com.example.androiduber.Common;

import android.location.Location;

import com.example.androiduber.Remote.FCMClient;
import com.example.androiduber.Remote.IFCMService;
import com.example.androiduber.Remote.IGoogleAPI;
import com.example.androiduber.Remote.RetrofitClient;

import retrofit2.Retrofit;

public class Common {

    public static String currentToken = "";

    public static final String driver_tbl = "Drivers";
    public static final String user_driver_tbl = "DriversInfomation";
    public static final String user_rider_btl = "RidersInfomation";
    public static final String pickup_request_tbl = "PickupRequest";
    public static final String token_tbl = "Tokens";

    public static final String baseURL = "https://maps.googleapis.com";
    public static final String fcmURL = "https://fcm.googleapis.com/";

    public static Location mLastLocation = null;

    public static IGoogleAPI getGoogleAPI(){

        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);

    }

    public static IFCMService getFCMService(){

        return FCMClient.getClient(fcmURL).create(IFCMService.class);

    }
}
