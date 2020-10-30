package android.security;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import com.teslasoft.libraries.support.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.view.Menu;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.view.CustomViewGroup;
import android.widget.SmartToast;
import android.provider.Settings;
import android.os.Build;
import android.view.Gravity;
import android.graphics.PixelFormat;
import android.net.Uri;

public class ForceLockActivity extends Activity
{
	public static final int OVERLAY_PERMISSION_REQ_CODE = 4545;
	protected CustomViewGroup blockingView = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.shadow);
		// Intent intents = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,Uri.parse("package:" + getPackageName()));
		// startActivityForResult(intents, OVERLAY_PERMISSION_REQ_CODE);
		
		Intent intent = new Intent(this, android.security.BiometricAuthenticatorCallback.class);
		startActivityForResult(intent, 1);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finishAndRemoveTask();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		} else if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
			if (!Settings.canDrawOverlays(this)) {
				SmartToast.create("User can access system settings without this permission!", this);
			}
			else
			{
				disableStatusBar();
			}
		} else {
			DevicePolicyManager dpm = (DevicePolicyManager)
			getSystemService(Context.DEVICE_POLICY_SERVICE);
			dpm.lockNow();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finishAndRemoveTask();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}
	}
	
	protected void disableStatusBar() {
		WindowManager manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
		localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
		localLayoutParams.gravity = Gravity.TOP;
		localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |

            // this is to enable the notification to receive touch events
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

            // Draws over status bar
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

		localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
		localLayoutParams.height = (int) (40 * getResources().getDisplayMetrics().scaledDensity);
		localLayoutParams.format = PixelFormat.TRANSPARENT;

		blockingView = new CustomViewGroup(this);
		manager.addView(blockingView, localLayoutParams);
	}

	@Override
	public void onBackPressed()
	{
		DevicePolicyManager dpm = (DevicePolicyManager)
		getSystemService(Context.DEVICE_POLICY_SERVICE);
		dpm.lockNow();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		/*"if (blockingView!=null) {
			WindowManager manager = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE));
			manager.removeView(blockingView);
		}*/
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}
	
	@Override
	public void onAttachedToWindow() {
		// super.onAttachedToWindow();
		// this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
		lock.disableKeyguard();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_HOME)
		{
			DevicePolicyManager dpm = (DevicePolicyManager)
			getSystemService(Context.DEVICE_POLICY_SERVICE);
			dpm.lockNow();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finishAndRemoveTask();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			return true;
		} else {
			return false;
		}
	}
}
