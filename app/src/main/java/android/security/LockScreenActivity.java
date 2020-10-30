package android.security;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import com.teslasoft.libraries.support.R;

public class LockScreenActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shadow);
		
		Intent intent = new Intent(this, android.security.BiometricAuthenticatorCallback.class);
		startActivityForResult(intent, 1);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finishAndRemoveTask();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		} else {
			Intent main = new Intent(Intent.ACTION_MAIN);
			main.addCategory(Intent.CATEGORY_HOME);
			main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(main);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finishAndRemoveTask();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
	}

	@Override
	public void onBackPressed()
	{
		
	}
}
