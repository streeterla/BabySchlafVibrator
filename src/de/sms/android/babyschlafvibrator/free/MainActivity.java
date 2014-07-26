package de.sms.android.babyschlafvibrator.free;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import de.sms.android.babyschlafvibrator.free.countdown.CountdownPicker;
import de.sms.android.babyschlafvibrator.free.countdown.BabyCountDownTimer;


/**
 * main activity
 * 
 * @author streeter
 *
 */
public class MainActivity extends Activity 
{
	/** vibrator */
	private Vibrator myVib;
	/** player */
	final MediaPlayer mPlayer = new MediaPlayer();
	/** picker to set hours */
	private CountdownPicker hourPicker;
	/** picker to set minutes */
	private CountdownPicker minutePicker;
	/** picker to set seconds */
	private CountdownPicker secondPicker;
	/** button to start buzzer */
	private Button startButton;
	/** button to stop buzzer */
	private Button stopButton;
	/** button to exit app */
	private Button exitButton;
	/** hours to buzz */
	private int hour;
	/** minutes to buzz */
	private int minute;
	/** seconds to buzz */
	private int second;
	/** total seconds  to  buzz */
	private int totalSeconds;
	/** view to show add */
	private AdView adView;
	/** show countdown */
	private TextView countdown;
	/** show toast */
	private Toast toast;
	/** progress bar */
	private ProgressBar mProgress;
	/** full screen ad */
	InterstitialAd interstitial;
	/** timer for own voice */
	BabyCountDownTimer babyTimer;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		//layout
		setContentView(R.layout.activity_main);
		
		//full screen ad
		interstitial = new InterstitialAd(this);
		AdRequest adRequest = new AdRequest.Builder().build();
		interstitial.setAdUnitId("ca-app-pub-9288123704995126/1898186495");
		interstitial.loadAd(adRequest);
		
		//preferences
		final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		//pickers
		hourPicker = (CountdownPicker) findViewById(R.id.hour_picker);
		minutePicker = (CountdownPicker) findViewById(R.id.minute_picker);
		secondPicker = (CountdownPicker) findViewById(R.id.second_picker);
		
		//coundown
		countdown = (TextView) findViewById(R.id.countdown);
		countdown.setVisibility(TextView.GONE);
		
		 String mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/babysleepbuzzer_ownvoice.3gp";
	     try 
	     {
	    	 mPlayer.setDataSource(mFileName);
	    	 mPlayer.prepare();
	     } 
	     catch (Exception e)
	     {
			 toast = Toast.makeText(getApplicationContext(), getText(R.string.error_own_voice), Toast.LENGTH_SHORT);
			 toast.show();
		 }
		
		getUI();
		
		startButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				getNumbers();
				totalSeconds = hour*3600 + minute*60 + second;
				countdown.setVisibility(TextView.VISIBLE);
				if(mProgress != null)
				{
					mProgress.setMax(totalSeconds);
					mProgress.setProgress(totalSeconds);
				}
				babyTimer = new BabyCountDownTimer(totalSeconds*1000+1000, 1000L, countdown, toast, getApplicationContext(), getText(R.string.good_night), getString(R.string.countdown), mPlayer, mProgress);
				babyTimer.start();
				 if(!sharedPrefs.getBoolean("pref_useOwnVoice", false))
				 {
					 toast = Toast.makeText(getApplicationContext(), getText(R.string.vibration), Toast.LENGTH_SHORT);
					 toast.show();
					 myVib.vibrate(totalSeconds * 1000);
				 }
				 else
				 {
//					 try
//					 {

					 	
						 toast = Toast.makeText(getApplicationContext(), getText(R.string.own_voice), Toast.LENGTH_SHORT);
						 toast.show();

			             mPlayer.setLooping(true);
			             mPlayer.start();
			             if(mProgress != null)
			             {
			            	 mProgress.setMax(totalSeconds);
			            	 mProgress.setProgress(totalSeconds);
			             }
//					 }
//					 catch(IOException ioe)
//					 {
//						 Log.e("mplayer", ioe.getMessage());
//					 }
				 }
			}
		});
		stopButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				myVib.cancel();
		        countdown.setVisibility(TextView.GONE);
		        toast = Toast.makeText(getApplicationContext(), getText(R.string.good_night), Toast.LENGTH_LONG);
		        toast.show();
		        babyTimer.onFinish();
		        if(mProgress != null)
	        	{
		        	mProgress.setProgress(0);
	        	}
				clearCountdown();
			}
		});
		exitButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				babyTimer.onFinish();
				finish();
			}
		});
		
        //Google Ad Mop, only if Internet is available
        ConnectivityManager mgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networks = mgr.getAllNetworkInfo();
        boolean isAvailable = false;
        for(NetworkInfo network : networks)
        {
        	if(network.isAvailable() || network.isConnected())
        	{
        		isAvailable = true;
        	}
        }
        if(isAvailable)
        {
//	        AdRequest adRequest = new AdRequest();
//	        adRequest.addTestDevice("BX902YS3S8");
//	        adRequest.addTestDevice("2FB2523517B83D06B260E72C2CC677B1");
//	        adRequest.addTestDevice("AAF007BEB00D3E858DC00FC6B4244851");
//	        adView = new AdView(this, AdSize.BANNER, "a1514a4addebc42");
//	        LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);
//	        layout.addView(adView,0);  
//	        adView.loadAd(adRequest);
        	
        	adView = new AdView(this);
        	adView.setAdUnitId("ca-app-pub-9288123704995126/1898186495");
        	adView.setAdSize(AdSize.BANNER);
        	LinearLayout layout = (LinearLayout) findViewById(R.id.addLayout);
        	layout.addView(adView);
        	adView.loadAd(adRequest);
        }
	}
	
	
	/**
	 * destroy ad
	 */
	@Override
	 public void onDestroy()
	 {
		Log.i("MainActivity", "destroy");
	    if (adView != null)
	    {
	        adView.destroy();
	    }

	    if(mPlayer != null)
	    {
	    	mPlayer.release();
	    }
	    super.onDestroy();
	 }
	
	@Override
	public void onBackPressed()
	{
		if (interstitial.isLoaded())
		{
			interstitial.show();
		}
		super.onBackPressed();
	}
	
	
	  /**
	   * pause ad
	   */
	  @Override
	  public void onPause() 
	  {
		Log.i("MainActivity", "pause");
	    adView.pause();
	    super.onPause();
	  }

	  
	  /**
	   * resume ad
	   */
	  @Override
	  public void onResume() 
	  {
		Log.i("MainActivity", "resume");
	    super.onResume();
	    adView.resume();
	  }

	
	/**
	 * create menu
	 */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
    	//settings
        getMenuInflater().inflate(R.menu.activity_menu, menu);

        //record settings
        MenuItem recorderItem = menu.getItem(0);
        Intent recorderIntent = new Intent(this, Recorder.class);
        recorderItem.setIntent(recorderIntent);
        
        //exit
        MenuItem exitItem = menu.getItem(1);
        exitItem.setOnMenuItemClickListener(new OnMenuItemClickListener()
        {
			
			public boolean onMenuItemClick(MenuItem item) 
			{
				finish();
				return false;
			}
		});
        return true;
    }
	
	
    /**
     * get UI
     */
	private void getUI()
	{
		startButton = (Button) findViewById(R.id.startButton);
		stopButton = (Button) findViewById(R.id.stopButton);
		exitButton = (Button) findViewById(R.id.exitButton);
		myVib = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
		if(!isLandscape(this))
		{
			mProgress = (ProgressBar) findViewById(R.id.progress);
		}
		
		hourPicker.setTimeUnit(R.string.hour);
		minutePicker.setTimeUnit(R.string.minute);
		secondPicker.setTimeUnit(R.string.second);
	}
	
	
	/**
	 * get duration
	 */
	private void getNumbers()
	{
		hour = hourPicker.getNumber();
		minute = minutePicker.getNumber();
		second = secondPicker.getNumber();
	}
	
	
	/**
	 * cleanup
	 */
	private void clearCountdown()
	{
		countdown.setText("0");
		countdown.setVisibility(TextView.GONE);
		hourPicker.setNumber(0);
		minutePicker.setNumber(0);
		secondPicker.setNumber(0);
	}
	
	
	/**
	 * 
	 * @param context
	 * @return true if device is in landscape mode, false if not
	 */
    private boolean isLandscape(Context context)
    {
    	final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        if(rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270)  
        	return true;
        return false;
    }
}
