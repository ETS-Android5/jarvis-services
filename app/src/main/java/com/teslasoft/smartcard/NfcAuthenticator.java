package com.teslasoft.smartcard;

import android.os.Bundle;
import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.content.Intent;
import android.widget.Toast;
import android.os.Parcelable;
import android.widget.TextView;
import android.app.PendingIntent;
import java.nio.charset.Charset;
import java.util.Arrays;
import android.provider.Settings;
import android.util.Log;
import java.util.List;
import android.content.IntentFilter;
import android.content.Context;
import android.os.Handler;
import android.content.ComponentName;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import com.teslasoft.libraries.support.R;

public class NfcAuthenticator extends Activity{
	public TextView nfc_message;
	public NfcAdapter nfcAdapter;
	public PendingIntent pendingIntent;
    IntentFilter writeTagFilters[];
    boolean writeMode;
    Tag myTag;
    Context context;
	String url;
	public LinearLayout loadbar;
	public LinearLayout actions;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
		setContentView(R.layout.nfc_read);
		nfc_message=(TextView) findViewById(R.id.nfc_message);
		loadbar=(LinearLayout) findViewById(R.id.loadbar);
		actions=(LinearLayout) findViewById(R.id.actions);
		context=this;
		try{Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.SmartCardLoader"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);}
		catch(Exception eee){}
		nfcAdapter=NfcAdapter.getDefaultAdapter(this);
		if(nfcAdapter==null){
			toast("This device doesn't support NFC.",this);
            finishAndRemoveTask();
		}
		readFromIntent(getIntent());
		pendingIntent=PendingIntent.getActivity(this,0,new Intent(this,getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
		IntentFilter tagDetected=new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		writeTagFilters=new IntentFilter[]{tagDetected};
	}
	private void readFromIntent(Intent intent){
        String action=intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
			|| NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
			|| NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] rawMsgs=intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			byte[] tagId=intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);	
			String hexdump=new String();
			for(int i=0;i<tagId.length;i++){
				String x=Integer.toHexString(((int) tagId[i] & 0xff));
				if (x.length()==1){x='0'+x;}
				hexdump+=x;
			}
            NdefMessage[] msgs=null;
            if (rawMsgs!=null){
                msgs=new NdefMessage[rawMsgs.length];
                for(int i=0;i<rawMsgs.length;i++) msgs[i]=(NdefMessage) rawMsgs[i];
            }
            buildTagViews(msgs,hexdump);
        }
	}
	private void buildTagViews(NdefMessage[] msgs, String tagId) {
        if (msgs==null||msgs.length==0) return;
        String text="";
		byte[] payload=msgs[0].getRecords()[0].getPayload();
        String textEncoding=((payload[0]&128)==0) ? "UTF-8":"UTF-16";
        int languageCodeLength=payload[0]&0063;
        try{text=new String(payload,languageCodeLength+1,payload.length-languageCodeLength-1,textEncoding);
        }catch(Exception e){Log.e("UnsupportedEncoding",e.toString());}
		Authenticate(text,tagId);
    }
	@Override
    protected void onNewIntent(Intent intent){
        setIntent(intent);
        readFromIntent(intent);
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())){
            myTag=intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        }
   	}
	@Override
    public void onPause(){
        super.onPause();
        writeMode=false;
        nfcAdapter.disableForegroundDispatch(this);
    }
    @Override
    public void onResume(){
        super.onResume();
        writeMode=true;
        nfcAdapter.enableForegroundDispatch(this,pendingIntent,writeTagFilters,null);
    }
	public void Authenticate(String token,String serial){
		try{
			Gson gson=new Gson();
			Map<String,Object> map=new HashMap<String,Object>();
			map=(Map<String,Object>) gson.fromJson(token,map.getClass());
			String user_fwid=map.get("fwid").toString();
			String user_id=map.get("uid").toString();
			String pincode_encrypted=map.get("pin").toString();
			token=user_fwid.trim();
			token=token.toUpperCase();
			nfc_message.setText("SmartCard Info\n\nSerial Number: ".concat(serial.concat("\nSmartCard ID: ".concat(user_fwid.concat("\nUser ID: ".concat(user_id))))));
			url="https://cs.jarvis.studio/smartcard/auth?cbs=".concat(token).concat("&sid=").concat(serial).concat("&pwtoken=").concat(pincode_encrypted).concat("&uid=").concat(user_id);
			loadbar.setVisibility(View.GONE);
			actions.setVisibility(View.VISIBLE);
		}catch(Exception e){throw new RuntimeException("An error occured while data deserializing. Please make sure that you used correct SmartCard (Ndef)");}
	}
	public void Auth(View v){openUrl(url);}
	public void Close(View v){finishAndRemoveTask();}
	public void openUrl(String url){
		final String urls=url;
		final Handler handler=new Handler();
		handler.postDelayed(new Runnable(){
			@Override
			public void run(){
				overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
				Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(urls));
				startActivity(browserIntent);
				final Handler handler=new Handler();
				handler.postDelayed(new Runnable(){
					@Override
					public void run(){
						overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
						finishAndRemoveTask();
					}
				}, 10);
			}
		}, 1000);
	}
	
	public Handler mHandler = new Handler();
	public void toast(final CharSequence text,final Context context){
        mHandler.post(new Runnable(){
			@Override public void run(){
				try{Toast.makeText(context,text,Toast.LENGTH_SHORT).show();}
				catch(Exception e){}
			}
		});
    }
}
