package de.sms.android.babyschlafvibrator.free;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;


/**
 * button to record own voice
 * 
 * @author streeter
 *
 */
public class RecordButton extends RecorderButton
{
	/** flag to show whether recording is active or not */
    boolean mStartRecording = true;

    /** click listener */
    OnClickListener clicker = new OnClickListener()
    {
        public void onClick(View v)
        {
            onRecord(mStartRecording);
            if (mStartRecording)
            {
                setText(context.getString(R.string.stop_recording));
            } 
            else
            {
            	setText(context.getString(R.string.start_recording));
            }
            mStartRecording = !mStartRecording;
        }
    };

    
    //constructors
    public RecordButton(Context context) 
    {
    	super(context);
        setText(context.getString(R.string.start_recording));
        setText(context.getString(R.string.start_recording));
        setOnClickListener(clicker);
        setTextColor(Color.WHITE);
    }
    
    
    public RecordButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setText(context.getString(R.string.start_recording));
        setOnClickListener(clicker);
        setTextColor(Color.WHITE);
    }
    
    
    public RecordButton(Context context, AttributeSet attrs, int defStyle)
    {
    	super(context, attrs, defStyle);
    	setText(context.getString(R.string.start_recording));
        setOnClickListener(clicker);
        setTextColor(Color.WHITE);
    }

}
