package nico.styTool;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;

//Energy 集成层
public class EnergyWrapper 
{
    
    private Context mcontext;	

    public EnergyWrapper(Context _context)
    {
	mcontext = _context;  		
    }
    public int getBrightness()
    {
    	int mOldBrightness = 0;
    	ContentResolver cr = mcontext.getContentResolver();
        try 
        {
	    mOldBrightness = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        }
	catch (SettingNotFoundException snfe)
	{
	    mOldBrightness = 255;    }

        return mOldBrightness;
    }    
    public int updateBrightness(int progress)
    {
        ContentValues values = new ContentValues(1);
        ContentResolver cr = mcontext.getContentResolver();
        Uri brightnessUri = Settings.System.CONTENT_URI;
        int result = 0;


        Settings.System.putInt(cr, Settings.System.SCREEN_BRIGHTNESS, progress);

        values.put("screen_brightness", progress); 

        try
	{
	    result = cr.update(brightnessUri, values, null, null);
        }
	catch (Exception e)
	{
	    result = 0;
        }  
        return result;
    }      
    public int getBacklightTime()
    {
    	int Time = 0;    	
    	ContentResolver cr = mcontext.getContentResolver();
        try 
        {
	    Time = Settings.System.getInt(cr, Settings.System.SCREEN_OFF_TIMEOUT);
        }
	catch (SettingNotFoundException snfe) 
        {
	    //Log.d(tag,"error()");    
        }

        return Time;
    }      
    public void updateBacklightTime(int time)
    {    	
        ContentValues values = new ContentValues(1);
        ContentResolver cr = mcontext.getContentResolver();
        Uri blTimeUri = Settings.System.CONTENT_URI;
        int result;

        //Log.v("updateBacklightTime", "num:" + time);
        Settings.System.putInt(cr, Settings.System.SCREEN_OFF_TIMEOUT, time);
	//  Log.v("updateBacklightTime", "putINTOK");

        values.put("screen_off_timeout", time); 

        try
	{
	    result = cr.update(blTimeUri, values, null, null);
        }
	catch (Exception e)
	{
	    result = 0;
        }  
	//   Log.v("Result", "result is:" + result);      	
    }

    public int getBluetoothStatus()
    {
    	int iStatus = 0;
    	ContentResolver cr = mcontext.getContentResolver();
        try 
        {
	    iStatus = Settings.Secure.getInt(cr, Settings.Secure.BLUETOOTH_ON);
        }
	catch (SettingNotFoundException snfe)
	{
	    iStatus = 0;    }
        return iStatus;
    }
    /*    
     public boolean updateBluetoothStatus()
     {
     boolean result = false;   
     BluetoothService mbluetoothService = BluetoothService.getInstance();
     mbluetoothService.setContext(mcontext);
     if(mbluetoothService.isBluetoothEnabled()==false)
     {
     result = mbluetoothService.startBluetooth();
     }
     else
     {
     result = mbluetoothService.stopBluetooth();        	
     }
     return result;
     }    
     */    
    public int getWiFiStatus()
    {
    	int iStatus = 0;
    	ContentResolver cr = mcontext.getContentResolver();
        try 
        {
	    iStatus = Settings.Secure.getInt(cr, Settings.Secure.WIFI_ON);
        }
	catch (SettingNotFoundException snfe)
	{
	    iStatus = 0;    }
        return iStatus;
    }

    public boolean getGPSStatus()
    {

    	boolean bStatus = false;
        LocationManager mLocationManager = (LocationManager)mcontext.getSystemService(Context.LOCATION_SERVICE);
        bStatus = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return bStatus;

	/*    	
	 int iStatus = 0;
	 boolean ret = false;
	 ContentResolver cr = mcontext.getContentResolver();
	 try 
	 {
	 iStatus = Settings.Secure.getInt(cr, Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
	 } catch (SettingNotFoundException snfe) {
	 iStatus = 0;    }
	 if(iStatus == 0)
	 {
	 ret = false;
	 }
	 else
	 {
	 ret = true;
	 }
	 return ret;        
	 */      
    }
    public int getEnergyLevelRating()
    {
	int Rating = 1;
	int brightnessRating = 	getbrightnessLevelRating();
	int backlighttimeRating = getbacklighttimeLevelRating();	       
	int connectionRating = getConnectionLevelRating();

	Rating = getcommonrating(((double)(brightnessRating + backlighttimeRating + connectionRating)) / 15.0, true);
	//Log.d(tag,"getEnergyLevelRating");
	return Rating;
    }	  
    public int getbacklighttimeLevelRating()
    {
	int backlighttimeRating = 1;	
	int mOldTime = getBacklightTime();
        if (mOldTime <= 15 * 1000)
	    backlighttimeRating = 5;
        else if ((mOldTime > 15 * 1000) && (mOldTime <= 30 * 1000))
	    backlighttimeRating = 4;
        else if ((mOldTime > 30 * 1000) && (mOldTime <= 1 * 60 * 1000))
	    backlighttimeRating = 3;
        else if ((mOldTime > 1 * 60 * 1000) && (mOldTime <= 10 * 60 * 1000))
	    backlighttimeRating = 2;
        else if (mOldTime > 10 * 60 * 1000)
	    backlighttimeRating = 1;
        else if (mOldTime == -1)
	    backlighttimeRating = 1;
        else
	    backlighttimeRating = 1;	


	//Log.d(tag,"getbacklighttimeLevelRating");
	return backlighttimeRating;
    }	
    public int getbrightnessLevelRating()
    {
	double level = ((double)getBrightness()) / 255.0;	
	int brightnessRating = getcommonrating(level, false);

	//Log.d(tag,"getbrightnessLevelRating");
	return brightnessRating;
    }	
//level在0到1之间
    public int getcommonrating(double level, boolean up)
    {
	int rating = 1;

	if (level > 0.8 && level <= 1)
	{
	    if (up == true)
	    {
		rating = 5;				
	    }
	    else
	    {
		rating = 1;
	    }
	}
	else if (level > 0.6 && level <= 0.8)
	{
	    if (up == true)
	    {
		rating = 4;				
	    }
	    else
	    {
		rating = 2;
	    }
	}
	else if (level > 0.4 && level <= 0.6)
	{
	    rating = 3;
	}
	else if (level > 0.2 && level <= 0.4)
	{
	    if (up == true)
	    {
		rating = 2;				
	    }
	    else
	    {
		rating = 4;
	    }
	}
	else
	{
	    if (up == true)
	    {
		rating = 1;				
	    }
	    else
	    {
		rating = 5;
	    }
	}
	//.d(tag,"getcommonrating");
	return rating;
    }		
    public int getConnectionLevelRating()
    {
	int connectionRating = 1;			
	boolean gpsstatus = getGPSStatus();
	int wifistatus = getWiFiStatus();
	int btstatus = getBluetoothStatus();
	if ((gpsstatus == true) && (wifistatus == 1) && (btstatus == 1))
	{
	    //全开情况为1级
	    connectionRating = 1;
	}
	else if ((gpsstatus == false) && (wifistatus == 0) && (btstatus == 0))
	{
	    //全关情况为5级
	    connectionRating = 5;			
	}
	else if (((gpsstatus == true) || (wifistatus == 1)) && (btstatus == 1))
	{
	    //蓝牙开启,同时gps和wifi有一个开启的情况为2级
	    connectionRating = 2;			
	}
	else if (((gpsstatus == true) && ((wifistatus == 1) || (btstatus == 1)))
		 || (((gpsstatus == true) || (btstatus == 1)) && (wifistatus == 1)))
	{
	    //GPS开启,同时bt和wifi有一个开启,或者wifi开启,同时bt和gps有一个开启的情况为3级.
	    connectionRating = 3;					
	}
	else
	{
	    //有一个开启,其他两个为关闭的情况为4级.
	    connectionRating = 4;				
	}
	//Log.d(tag,"getEnergyLevelRating");
	return connectionRating;
    }		
}
