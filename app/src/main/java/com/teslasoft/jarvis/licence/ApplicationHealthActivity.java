package com.teslasoft.jarvis.licence;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.ComponentName;

public class ApplicationHealthActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		ForcePersistentComponentsIntegrity();
	}
	
	public void ForcePersistentComponentsIntegrity() {
		// Protect Jarvis Services from disables and modifications
		PackageManager pm = getPackageManager();
		
		pm.setComponentEnabledSetting(new ComponentName(this, android.security.BiometricAuthenticator.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, android.security.BiometricAuthenticatorCallback.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, android.security.ProtectActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.android.phone.GSMDriverRestartActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.android.server.RestartActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.auth.AuthActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.auth.PreAuthActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.auth.AuthEntryActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.auth.AuthCompleteActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.auth.AuthCompleteEntryActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.crashreport.BugReport.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.crashreport.Detail.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.crashreport.Report.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.CoreActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.ServicesActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.easter.EasterEgg.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.easter.JarvisPlatLogo.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.AutoRunActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.AutoRunService.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.AutoRunForegroundService.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.CoreServiceSettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.CoreServiceSettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.DPSSettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.ErrorServiceActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.LCSSettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NSettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.corex.Corex.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.updater.DownloadUpdateActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.DialogLoadingDark.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.DialogLoadingLight.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.JHActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.Jarvis.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.OptIn.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.vip.VipActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.vip.VipPurchaseActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.ui.DialogConfirm.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.Settings.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.AppsList.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		// pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.BootAnimation.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, net.jarvis.engine.TaskCdhActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.AppsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.BootCompletedReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.InitActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.LoadActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.RateActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.SettingsActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.libraries.support.WebActivity.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
		finishAndRemoveTask();
	}
}
