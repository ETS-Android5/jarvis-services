package com.teslasoft.jarvis;

import android.os.*;
import android.app.*;
import android.speech.tts.TextToSpeech;
import com.teslasoft.libraries.support.R;
import android.support.v7.app.*;
import android.support.v7.appcompat.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class Tts extends Activity
{

	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	private TextToSpeech textToSpeech;
	private Button btn;
	private EditText editText;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tts_activity);
		btn = (Button) findViewById(R.id.btn);

		editText = (EditText) findViewById(R.id.et);
		textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
		{
			@Override
			public void onInit(int status)
			{
				if (status == TextToSpeech.SUCCESS)
				{
					Locale locale = new Locale("ru");
					
					int ttsLang = textToSpeech.setLanguage(locale);

					if (ttsLang == TextToSpeech.LANG_MISSING_DATA || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED)
					{
						Log.e("TTS", "The Language is not supported!");
					} else {
						Log.i("TTS", "Language Supported.");
					}
						Log.i("TTS", "Initialization success.");
					} else {
						Toast.makeText(getApplicationContext(), "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
					}
				}
			});

			btn.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					String data = editText.getText().toString();
					Log.i("TTS", "button clicked: " + data);
					int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

					if (speechStatus == TextToSpeech.ERROR)
					{
						Log.e("TTS", "Error in converting Text to Speech!");
					}
				}
			});
		}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (textToSpeech != null)
		{
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
	}
}
