/*
 * Copyright (C) 2013 Joseph Mansfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uk.co.sftrabbit.stackanswers.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;
import uk.co.sftrabbit.stackanswers.R;

public class NotificationsActionView extends FrameLayout
		implements View.OnLongClickListener, View.OnClickListener {
	private final Context context;

	public NotificationsActionView(Context context) {
		this(context, null);
	}

	public NotificationsActionView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		final LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.action_notifications, this);

		final ImageButton button =
			(ImageButton) findViewById(R.id.button_notifications);
		assert button != null : "No button in notifications action layout";
		button.setOnClickListener(this);
		button.setOnLongClickListener(this);
	}

	@Override
	public void onClick(View view) {
		final View rootView = view.getRootView();
		DrawerLayout drawerLayout = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
		View notificationDrawer = rootView.findViewById(R.id.notification_drawer);

		final boolean isDrawerOpen = drawerLayout.isDrawerOpen(notificationDrawer);
		drawerLayout.closeDrawers();
		if (!isDrawerOpen) {
			drawerLayout.openDrawer(notificationDrawer);
		}
	}

	@Override
	public boolean onLongClick(View view) {
		final View rootView = view.getRootView();
		DrawerLayout drawerLayout =
			(DrawerLayout) rootView.findViewById(R.id.drawer_layout);
		View notificationDrawer = rootView.findViewById(R.id.notification_drawer);

		final int[] screenPosition = new int[2];
		view.getLocationOnScreen(screenPosition);
		final Rect displayFrame = new Rect();
		view.getWindowVisibleDisplayFrame(displayFrame);

		final int width = view.getWidth();
		final int height = view.getHeight();
		final int centerY = screenPosition[1] + height / 2;
		final int screenWidth = context.getResources()
			.getDisplayMetrics().widthPixels;

		final int actionDescription =
			drawerLayout.isDrawerOpen(notificationDrawer) ?
			R.string.action_notifications_close :
			R.string.action_notifications_open;

		final Toast tooltip = Toast.makeText(context, actionDescription,
		                                     Toast.LENGTH_SHORT);
		if (centerY < displayFrame.height()) {
			tooltip.setGravity(Gravity.TOP | Gravity.END,
			                   screenWidth - screenPosition[0] - width / 2, height);
		} else {
			tooltip.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, height);
		}
		tooltip.show();
		
		return true;
	}
}
