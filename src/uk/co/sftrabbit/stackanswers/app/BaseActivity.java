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
 
package uk.co.sftrabbit.stackanswers.app;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Collection;
import uk.co.sftrabbit.stackanswers.R;
import uk.co.sftrabbit.stackanswers.view.NavigationDrawer;

public abstract class BaseActivity extends Activity
		implements DrawerLayout.DrawerListener {
	private ActionBar actionBar;
	private CharSequence title;
	private DrawerLayout drawerLayout;
	private NavigationDrawer navigationDrawer;
	private DrawerToggle drawerToggle;
	private boolean isTouchEnabled = true;
	private final Collection<MenuItem> activityMenuItems =
		new ArrayList<MenuItem>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		super.setContentView(R.layout.drawer_frame);

		actionBar = getActionBar();
		assert actionBar != null : "No action bar in activity";

		title = getTitle();

		initNavigationDrawer();
	}

	@Override
	public void setContentView(int layoutResId) {
		LayoutInflater layoutInflater =
			(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		assert layoutInflater != null : "LAYOUT_INFLATER_SERVICE not found";

		ViewGroup contentFrame =
			(ViewGroup) findViewById(R.id.activity_content);
		assert contentFrame != null : "No activity_content in layout";

		layoutInflater.inflate(layoutResId, contentFrame);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent pEvent) {
		if (isTouchEnabled) {
			return super.dispatchTouchEvent(pEvent);
		}
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		for (int index = 0; index < menu.size(); index++) {
			activityMenuItems.add(menu.getItem(index));
		}
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_application, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean drawerOpen = drawerLayout.isDrawerOpen(navigationDrawer);
		for (int index = 0; index < menu.size(); index++) {
			MenuItem menuItem = menu.getItem(index);
			if (activityMenuItems.contains(menuItem)) {
				menuItem.setVisible(!drawerOpen);
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		drawerToggle.onDrawerClosed(drawerView);
		navigationDrawer.onDrawerClosed(drawerView);

		if (drawerView.getId() == R.id.navigation_drawer) {
			actionBar.setTitle(title);
		}

		invalidateOptionsMenu();
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		drawerToggle.onDrawerOpened(drawerView);
		navigationDrawer.onDrawerOpened(drawerView);

		if (drawerView.getId() == R.id.navigation_drawer) {
			actionBar.setTitle(R.string.application_name);
		}

		invalidateOptionsMenu();
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		drawerToggle.onDrawerSlide(drawerView, slideOffset);
		navigationDrawer.onDrawerSlide(drawerView, slideOffset);
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		drawerToggle.onDrawerStateChanged(newState);
		navigationDrawer.onDrawerStateChanged(newState);
	}

	public void setTouchEnabled(boolean enable) {
		isTouchEnabled = enable;
	}

	public void setDrawerIndicatorEnabled(boolean enable) {
		drawerToggle.setDrawerIndicatorEnabled(enable);
	}

	public DrawerLayout getDrawerLayout() {
		return drawerLayout;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		drawerToggle.syncState();
	}

	private void initNavigationDrawer() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		assert drawerLayout != null : "No navigation drawer in activity";

		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow_left, Gravity.START);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow_right, Gravity.END);
		drawerLayout.setDrawerListener(this);

		navigationDrawer = (NavigationDrawer) findViewById(R.id.navigation_drawer);
		assert navigationDrawer != null : "No navigation drawer in activity";

		navigationDrawer.setDrawerLayout(drawerLayout);

		drawerToggle = new DrawerToggle(this, drawerLayout,
			R.drawable.icon_drawer, R.string.action_open_navigation_drawer,
			R.string.action_close_navigation_drawer);
	}
}
