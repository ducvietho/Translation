package com.example.vdconfigppclinkadsandroid.data;

public class Constants {
	public static final String BASE_URL = "http://deltago.com/";

	public static final String SHARED_PREFERENCE_NAME = "LICH_VIET_PREF";

	public static final String KEY_SERVER_CONFIG = "server_config";

	public static final int MAIN_LAYOUT = 0;

	public static final int BANNER_AD = 0;
	public static final int FORCE_SHOW_INTERSTITIAL_ALL = 1;
	public static final int FORCE_SHOW_INTERSTITIAL_IMAGE_ONLY = 2;
	public static final int FORCE_SHOW_INTERSTITIAL_VIDEO_ONLY = 3;
	public static final int SHOW_INTERSTITIAL_CONSTRAINT_SERVER = 4;
	public static final int NATIVE_MOPUB_AD = 5;
	public static final int REMOVE_ADS = 6;

	// Constants
	public static final String keyUDID = "keyUDID";
	public static final String kCurrentIndexAd = "currentIndexAd";
	public static final String kCountOpenApp = "countOpenApp";
	public static final String kRemindUpdateNewAppVersion = "remindUpdateNewAppVersion";

	public static final String kGetUDIDData_Result = "result";
	public static final String kGetUDIDData_UDID = "udid";

	public static final String kConfig = "config";
	public static final String kConfig_EnableAds = "Ads";
	public static final String kConfig_AdsType = "Ads_type";
	public static final String kConfig_FreeAdsRate = "FreeAdsRate";
	public static final String kConfig_MinFreeAdsRate = "MinFreeAdsRate";
	public static final String kConfig_PaidAdsRate = "PaidAdsRate";
	public static final String kConfig_MinPaidAdsRate = "MinPaidAdsRate";
	public static final String kConfig_AppLatestVersion = "app_latest_version";
	public static final String kConfig_MessageVersion = "message_version";
	public static final String kConfig_Message = "message";
	public static final String kConfig_MessageEnable = "message_enable";
	public static final String kConfig_ShowAdWhenSwitchChannel = "showAdWhenSwitchChannel";
	public static final String kConfig_ServerChannel = "serverChannel";
	public static final String kConfig_AppstoreURL = "appstoreurl";
	public static final String kConfig_ContactUsUIStyle = "contactUsUIStyle";
	public static final String kConfig_AdRectangleRefreshRate = "adRectangleRefreshRate";
	// Cost (in points) per ad-free day/hours
	public static final String kConfig_CostOneDayFreeAd = "CostOneDayFreeAd";
	public static final String kConfig_PercentVideoInterstitalAd = "PercentVideoInterstitalAd";
	public static final String kConfig_ipServer = "ipServer";

	// KEY FOR PPCLINK Ad Network mediation
	public static final String kConfig_PPCLINKAdsMediationBanner = "PPCLINKMediationConfig_AdBanner";
	public static final String kConfig_PPCLINKAdsMediationInterstitial = "PPCLINKMediationConfig_Interstitial";
	public static final String kConfig_PPCLINKAdsMediationVideoInterstitial = "PPCLINKMediationConfig_VideoInterstitial";
	// Khoang thoi gian toi thieu giua 2 lan hien quang cao interstitial
	public static final String kConfig_InterstitialAdFreeMinTimeInterval = "fullScreenAdFreeBetweenAdShowMinTimeInterval";
	// Khoang thoi gian toi thieu giua 2 lan hien quang cao interstitial khi da
	// user click/xem quang cao full/video
	public static final String kConfig_InterstitialAdFreeMinTimeIntervalBonus = "fullScreenAdFreeTimeIntervalBonus";
	// Khoang thoi gian toi thieu tu khi mo app den khi show interstitial ad dau
	// tien
	public static final String kConfig_InterstitialAdFreeWhenOpenAppMinTimeInterval = "fullScreenAdFreeWhenOpenAppTimeInterval";
	// Enable quay vong mang quang cao
	public static final String kConfig_RecirculateAdNetworkEnable = "RecirculateAdNetworkEnable";

	public static final String kNotifications = "notifications";
	public static final String kNotification_Type = "Type";
	public static final String kNotification_ID = "NotificationId";
	public static final String kNotification_Description = "Description";
	public static final String kNotification_Title = "Title";
	public static final String kNotification_TryNowText = "TryNowText";
	public static final String kNotification_CancelText = "CancelText";
	public static final String kNotification_MaxClick = "MaxClick";
	public static final String kNotifcation_MaxImpression = "MaxImpression";
	public static final String kNotification_URL = "URL";
	public static final String kNotification_Target = "Target";
	public static final String kNotification_AppId = "AppId";
	public static final String kNotification_AdImage = "AdImage";
	public static final String kNotification_AdBanner = "AdBanner";
	public static final String kNotification_AdRectangle = "AdRectangle";
	public static final String kNotification_AdFullscreen = "AdFullscreen";
	// Number of points reward to user when they installed the app notification
	public static final String kNotification_InstalledRewardPoints = "installedRewardPoints";
	public static final String kNotification_NumberOfAdFreeHours = "NumberOfAdFreeHours";

	public static final String kCleverNetAdCachedInfo_CurrentURL = "link";
	public static final String kCleverNetAdCachedInfo_Link = "link";
	public static final String kCleverNetAdCachedInfo_Content = "content";
	public static final String kCleverNetAdCachedInfo_Title = "title";
	public static final String kCleverNetAdInfo_Ready = "isCleverNETReady";

	// Post notification when update new config & notification
	public static final String kNotifyDidLoadNewConfigNotification = "notifyDidLoadNewConfigNotification";
	// Post notification when Ad images (adbanner,adrectangle,..) of all
	// notifications just finished!
	public static final String kNotifyDidFinishDownloadAdImages = "notifyDidFinishDownloadAdImages";
	public static final String CLEVERNET_AD_TEXT_ZONE_ID = "7a266402ce2c1f100849c6f6c6a9b648";

	// device target
	public static final int DT_IPHONE = 1;
	public static final int DT_IPAD = 2;
	public static final int DT_ANDROID_PHONE = 4;
	public static final int DT_ANDROID_PAD = 8;

	// product type
	public static final int PT_FREE = 1;
	public static final int PT_PAID = 2;

	// notification type
	// Hiện quảng cáo cho 1 app, hỗ trợ tải app để nhận thưởng
	public static final int NTYPE_APP = 0;
	// Yêu cau user mua inapp
	public static final int NTYPE_INAPP = 1;
	// CLEVERNET text ad
	public static final int NTYPE_CLEVERNET = 2;
	// Gợi ý user xem/click quảng cáo video/full để nhận thưởng
	public static final int NTYPE_VIEWADS = 3;
	// Dùng để hiện các thông điệp, tin tức từ admin...
	public static final int NTYPE_MESSAGE = 4;
	// Hiện quay vòng các loại quảng cáo trả về
	public static final int NTYPE_ALL = 88;

	// ad image type
	public static final int ADIMAGETYPE_ICON = 0;
	public static final int ADIMAGETYPE_BANNER = 1;
	public static final int ADIMAGETYPE_RECTANGLE = 2;
	public static final int ADIMAGETYPE_FULLSCREEN = 3;

	public static final int INTERSTITIAL_AD_TYPE_ALL = 0;
	public static final int INTERSTITIAL_AD_TYPE_IMAGE_ONLY = 1;
	public static final int INTERSTITIAL_AD_TYPE_VIDEO_ONLY = 2;

	// ////////AD NETWORK NAME DEFINITION ///////
	public static final String kCleverNETNetworkName = "clevernet";
	public static final String kMopubNetworkName = "mopub";
	public static final String kAdmobNetworkName = "admob";
	public static final String kMillennialNetworkName = "mmedia";
	public static final String kAdconolyNetworkName = "adcolony";
	public static final String kVungleNetworkName = "vungle";
	public static final String kStartAppNetworkName = "startapp";
	public static final String kNativeFacebook = "facebook";
	
	//AdID configuration key
		public static String kConfig_adid_adcolony_appid 				 = "adid_adcolony_appid";
		public static String kConfig_adid_adcolony_zoneid                = "adid_adcolony_zoneid";

		public static String kConfig_adid_admob_banner                   = "adid_admob_banner";
		public static String kConfig_adid_admob_interstitial_image       = "adid_admob_interstitial_image";
		public static String kConfig_adid_admob_interstitial_video       = "adid_admob_interstitial_video";

		public static String kConfig_adid_mopub_phone_banner             = "adid_mopub_phone_banner";
		public static String kConfig_adid_mopub_phone_full               = "adid_mopub_phone_full";
		public static String kConfig_adid_mopub_tablet_leaderboard       = "adid_mopub_tablet_leaderboard";
		public static String kConfig_adid_mopub_tablet_full              = "adid_mopub_tablet_full";
		public static String kConfig_adid_mopub_video                    = "adid_mopub_video";
		public static String kConfig_adid_mopub_native                   = "adid_mopub_native";

		public static String kConfig_adid_facebook_native                = "adid_facebook_native";
}
