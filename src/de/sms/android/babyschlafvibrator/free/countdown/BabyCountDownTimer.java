package de.sms.android.babyschlafvibrator.free.countdown;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * customized countdown timer
 * 
 * @author streeter
 *
 */
public class BabyCountDownTimer extends CountDownTimer 
{
	/** countdown */
	TextView countdown;
	/** toast */
	Toast toast;
	/** context */
	Context context;
	/** string to show good night */
	CharSequence sGoodNight;
	/** string to show the countdown */
	CharSequence sCountdown;
	/** player for own voice */
	MediaPlayer mPlayer;
	/** progress bar to show duration */
	ProgressBar mProgress;
	
	
	/**
	 * @see CountDownTimer#CountDownTimer(long, long)
	 * 
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public BabyCountDownTimer(long millisInFuture, long countDownInterval) 
	{
		super(millisInFuture, countDownInterval);
	}

	
	/**
	 * initialize the countdown
	 * 
	 * @param millisInFuture
	 * @param countDownInterval
	 * @param countdown
	 * @param toast
	 * @param context
	 * @param sGoodNight
	 * @param sCountdown
	 * @param mPlayer
	 * @param mProgress
	 */
	public BabyCountDownTimer(long millisInFuture, long countDownInterval,
			TextView countdown, Toast toast, Context context,
			CharSequence sGoodNight, CharSequence sCountdown, MediaPlayer mPlayer, ProgressBar mProgress)
	{
		super(millisInFuture, countDownInterval);
		this.countdown = countdown;
		this.toast = toast;
		this.context = context;
		this.sGoodNight = sGoodNight;
		this.sCountdown = sCountdown;
		this.mPlayer = mPlayer;
		this.mProgress = mProgress;
	}


	/**
	 * @see CountDownTimer#onFinish()
	 */
	public void onFinish() 
	{
        countdown.setVisibility(TextView.GONE);
        toast = Toast.makeText(context, sGoodNight, Toast.LENGTH_LONG);
		toast.show();
		if(mPlayer.isPlaying())
        {
			 mPlayer.pause();
        }
        if(mProgress != null)
        {
        	mProgress.setProgress(0);
        }
	}

	
	/**
	 * @see CountDownTimer#onTick(long)
	 */
	public void onTick(long millisUntilFinished)
	{
        countdown.setText(String.valueOf(sCountdown) + millisUntilFinished / 1000);
        if(mProgress != null)
	   	{
	      	 mProgress.setProgress((int) (millisUntilFinished/1000));
	      	 Log.i("own voice progress", String.valueOf(millisUntilFinished/1000));
	   	}
	}

}
