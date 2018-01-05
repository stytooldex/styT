package nico.styTool;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//Battery 集成层
public class BatteryWrapper 
{
    
    private int currentBatteryLevel = 68;
    private int scale = 100;
    private int health = 0;    
    private int status = 0;
    private double temperature = 32.6;
    private double voltage = 3.98;
    private int technology = 0;
    private int plug = 0;
    
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){  
	      @Override 
	      public void onReceive(Context arg0, Intent intent)
	      {  
	    	  String action=intent.getAction();
	    	  if(action.equals(Intent.ACTION_BATTERY_CHANGED))
	    	  {
	    		  //Toast.makeText(arg0, "test", 5).show();
	    	  currentBatteryLevel = intent.getIntExtra("level" , 0);  
	    		  scale = intent.getIntExtra("scale", 100);
	          health = intent.getIntExtra("health" , 0);
	    		  plug = intent.getIntExtra("plugged", 0);
	          status = intent.getIntExtra("status", 0);
	          temperature = intent.getIntExtra("temperature", 0);
	          voltage = intent.getIntExtra("voltage", 0);
	          technology = intent.getIntExtra("technology", 0);
	    	  }  
	      }  
	};  
	
	private Context mcontext;	
	
	public BatteryWrapper(Context _context)
	{
		mcontext = _context;
		mcontext.registerReceiver(mBatInfoReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));  		
	}

	public void setBattery(int iLevel, int iScale, int iHealth, int iPlug, int iStatus, double dTemperature, double dVoltage, int iTechnology)
	{
		currentBatteryLevel = iLevel;
		scale = iScale;
		health = iHealth;
		plug = iPlug;
		status = iStatus;
		temperature = dTemperature;
		voltage = dVoltage;
		technology = iTechnology;
	}
			
	public int getBatteryLevel()
	{
		return currentBatteryLevel;
	}
	public int getBatteryScale()
	{
		return scale;
	}
	public int getHealth()
	{
		return health;
	}
	public int getPlug()
	{
		return plug;
	}
	public int getStatus()
	{
		return status;
	}
	public double getTemperature()
	{
		return temperature;
	}
	public double getVoltage()
	{
		return voltage;
	}
	public int getTechnology()
	{
		return technology;
	}
	public int getBatteryLevelRating()
	{
		int batteryLevelRating = 0;
		if(getBatteryLevel() >= 80)
			batteryLevelRating = 5;
		else if(getBatteryLevel() < 80 && getBatteryLevel() >= 60)
			batteryLevelRating = 4;
		else if(getBatteryLevel() < 60 && getBatteryLevel() >= 40)
			batteryLevelRating = 3;
		else if(getBatteryLevel() < 40 && getBatteryLevel() >= 20)
			batteryLevelRating = 2;
		else
			batteryLevelRating = 1;
		return batteryLevelRating;
	}
}
