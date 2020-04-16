package com.teslasoft.jarvis;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import java.util.List;
import java.util.Calendar;
import java.util.ArrayList;
import com.teslasoft.libraries.support.R;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Jarvis extends Activity
{
	private List<JarvisMessages> messages;
	private ListView chat;
	private EditText messag_in;
	private Button push;
	private Calendar calend;
	private Calendar calend2;
	private String cp;
	private Timer timer;
		
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jarvis);
		
		messages = new ArrayList<JarvisMessages>();
		chat = (ListView)findViewById(R.id.chatlist);
		chat.setDivider(null);
		chat.setDividerHeight(0);
		chat.setSelector(android.R.color.transparent);
		chat.setVerticalScrollBarEnabled(false);
		
		messag_in = (EditText)findViewById(R.id.messag);
		push = (Button)findViewById(R.id.send);
		
		push.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View push)
			{
				cp = messag_in.getText().toString();
				
				if (cp.equals(""))
				{
					
				} else {
					// push.setEnabled(true);
					JarvisMessages elem = new JarvisMessages();
					elem.message_out = cp;
					elem.message_in = "";
					calend = Calendar.getInstance();
					elem.time_ind = new SimpleDateFormat("dd.MM hh:mm").format(calend.getTime());
					elem.time_outd = new SimpleDateFormat("dd.MM hh:mm").format(calend.getTime());
					messages.add(elem);
					updateChat();
					messag_in.setText("");
					chat.setSelection(chat.getAdapter().getCount()-1);
					
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							send();
						}
					}, 800);
				}
			}
		});
	}
	
	public void send() {
		// push.setEnabled(false);
		JarvisMessages elem2 = new JarvisMessages();
		elem2.message_out = "";

		if (cp.equals("/debug-test")) {
			elem2.message_in = "Debug test completed successfully";
		} else {
			elem2.message_in = "Unrecognized";
		}

		calend2 = Calendar.getInstance();
		elem2.time_ind = new SimpleDateFormat("dd.MM hh:mm").format(calend2.getTime());
		elem2.time_outd = new SimpleDateFormat("dd.MM hh:mm").format(calend2.getTime());
		messages.add(elem2);
		chat.setSelection(chat.getAdapter().getCount()-1);
	}
	
	public void updateChat()
	{
		ArrayAdapter<JarvisMessages> chatprovider = new ArrayAdapter<JarvisMessages>(this, R.layout.chat_list, messages)
		{
			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				if(convertView == null)
				{
					convertView = getLayoutInflater().inflate(R.layout.chat_list, null);
				}
				
				LinearLayout lbi = (LinearLayout)convertView.findViewById(R.id.bubble_in);
				LinearLayout lbo = (LinearLayout)convertView.findViewById(R.id.bubble_out);
				
				/*lbi.setOnTouchListener(new OnTouchListener()
				{
					@Override
					public boolean onTouch(View v, MotionEvent event)
					{
						if(event.getAction() == MotionEvent.ACTION_DOWN)
						{
							
						}
						
						if(event.getAction() == MotionEvent.ACTION_UP)
						{
							
						}
						return false;
					}
				});*/
				

				TextView inb = (TextView)convertView.findViewById(R.id.message_inb);
				inb.setText(messages.get(position).message_in);
				TextView outb = (TextView)convertView.findViewById(R.id.message_outb);
				outb.setText(messages.get(position).message_out);
				TextView intd = (TextView)convertView.findViewById(R.id.time_in);
				intd.setText(messages.get(position).time_ind);
				TextView outtd = (TextView)convertView.findViewById(R.id.time_in);
				outtd.setText(messages.get(position).time_outd);
				TextView nnin = (TextView)convertView.findViewById(R.id.name_in);
				nnin.setText("Bot");
				TextView nnout = (TextView)convertView.findViewById(R.id.name_out);
				nnout.setText("User");
				
				if (inb.getText().equals("") && !(outb.equals("")))
				{
					// toast("out");
					lbi.setVisibility(View.GONE);
					lbo.setVisibility(View.VISIBLE);
				}

				if (outb.getText().equals("") && !(inb.equals("")))
				{
					// toast("in");
					lbo.setVisibility(View.GONE);
					lbi.setVisibility(View.VISIBLE);
				}
				
				return convertView;
			}
		};

		chat.setAdapter(chatprovider); 
	}
	
	final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.Jarvis.this, text, Toast.LENGTH_SHORT).show();
			}
		});
    }
}
