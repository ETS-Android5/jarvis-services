package android.widget;

import android.os.Handler;
import android.content.Context;

public class SmartToast {
	public static Handler mHandler = new Handler();

	public static void create(final CharSequence text, final Context context) {
        mHandler.post(() -> {
			try { Toast.makeText(context, text, Toast.LENGTH_SHORT).show(); }
			catch (Exception ignored) {}
		});
    }
}
