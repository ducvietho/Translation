package com.example.vdconfigppclinkadsandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ResourceManager {
	public static final int DEVICE_PHONE = 0;
	public static final int DEVICE_TABLE = 1;
	
	public static final int ORIENTATION_PORT = 0;
	public static final int ORIENTATION_LAND = 1;
	public static ResourceManager instance;
	public int 	screenWidth, screenHeight, statusbarHeight;
	public int 	dpi, advWidth, advHeight;
	public int 	apiLevel, versionCode;
	public String deviceName, packageName, appversion, appName;
	public float density;
	public int deviceType, productType;

	public ResourceManager() {

	}
	public static boolean isTablet(Activity context) {
		DisplayMetrics dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels;
		int height=dm.heightPixels;
		int dens=dm.densityDpi;
		double wi=(double)width/(double)dens;
		double hi=(double)height/(double)dens;
		double x = Math.pow(wi,2);
		double y = Math.pow(hi,2);
		double screenInches = Math.sqrt(x+y);
		if(screenInches > 6) return true;
	    else return false;
	
	}
	@SuppressWarnings("deprecation")
	public void initResource(Context mContext) {
		apiLevel 	= android.os.Build.VERSION.SDK_INT;
		deviceName 	= android.os.Build.MODEL;
		packageName = mContext.getPackageName();

		PackageInfo packageInfo = null;
		try {
			packageInfo = mContext.getPackageManager().getPackageInfo(
					packageName, 0);
		} catch (NameNotFoundException ex) {
			ex.printStackTrace();
		}
		if (packageInfo != null) {
			appversion = packageInfo.versionName;
			versionCode = packageInfo.versionCode;
		} else {
			appversion = "1.0";
			versionCode = 1;
		}

//		appName = mContext.getString(R.string.app_name);

		WindowManager windowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		statusbarHeight = Utils.getStatusBarHeight(mContext);

		DisplayMetrics dm = new DisplayMetrics();
		display.getMetrics(dm);
		dpi = dm.densityDpi;
		density = dm.density;
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		

		advWidth = 320 * dpi / 160;
		advHeight = 50 * dpi / 160;
		
		if(isTablet((Activity)mContext)){  /*may tinh bang*/
			deviceType = DEVICE_TABLE;
		}else{
			deviceType = DEVICE_PHONE;
		}
	}

	public static ResourceManager getInstance() {
		if (instance == null)
			instance = new ResourceManager();
		return instance;
	}
}
