package android.view;

import android.view.ViewGroup;
import android.content.Context;

public class CustomViewGroup extends ViewGroup {
	public CustomViewGroup(Context context) {
		super(context);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return true;
	}
}
