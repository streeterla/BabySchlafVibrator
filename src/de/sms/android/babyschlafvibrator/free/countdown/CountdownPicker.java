package de.sms.android.babyschlafvibrator.free.countdown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.sms.android.babyschlafvibrator.free.R;


/**
 * @author streeter
 *
 * picker for each time unit
 */
public class CountdownPicker extends LinearLayout 
{
	
	/** number of time unit */
	private int number;
	/** button to increment number */
	private Button addButton;
	/** button to decrement number */
	private Button minusButton;
	/** textbox to show current number */
	private EditText editText;
	/** time unit */
	private TextView timeUnit;

	
	/**
	 * constructor to set values and define click event
	 * 
	 * @param context
	 * @param attrs
	 */
    public CountdownPicker(Context context, AttributeSet attrs)
    {
            super(context, attrs);
            LayoutInflater inflater = LayoutInflater.from(context);
            inflater.inflate(R.layout.countdown_picker, this, true);
            
            timeUnit = (TextView) findViewById(R.id.timeUnit);
            
            editText = (EditText) findViewById(R.id.countdown_edittext);
            editText.setClickable(false);
            editText.setOnClickListener(null);
            editText.setText(String.valueOf(0));
            
            addButton = (Button) findViewById(R.id.add_button);
            minusButton = (Button) findViewById(R.id.minus_button);
            
            addButton.setOnClickListener(new OnClickListener()
            {
				 
				@Override
				public void onClick(View v)
				{
					number = Integer.parseInt(editText.getText().toString());
					if(number == 59)
					{
						number = 0;
					}
					else
					{
						number++;
					}
					editText.setText(String.valueOf(number));
				}
			});
            
            minusButton.setOnClickListener(new OnClickListener()
            {
				
				@Override
				public void onClick(View v)
				{
					number = Integer.parseInt(editText.getText().toString());
					if(number > 0)
					{
						number--;
					}
					else
					{
						number = 59;
					}
					editText.setText(String.valueOf(number));
				}
			});
    }

    
    // getters and setters
	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number) 
	{
		this.number = number;
	}

	public TextView getTimeUnit()
	{
		return timeUnit;
	}

	public void setTimeUnit(int hour)
	{
		this.timeUnit.setText(hour);
	}

}
