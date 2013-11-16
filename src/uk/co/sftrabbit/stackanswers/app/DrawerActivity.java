package uk.co.sftrabbit.stackanswers.app;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import uk.co.sftrabbit.stackanswers.R;
import uk.co.sftrabbit.stackanswers.view.NavigationDrawer;

public class DrawerActivity extends Activity
		implements DrawerLayout.DrawerListener {
	private ActionBar actionBar;
	private CharSequence title;
	private DrawerLayout drawerLayout;
	private NavigationDrawer navigationDrawer;
	private DrawerToggle drawerToggle;
	private boolean isTouchEnabled = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		super.setContentView(R.layout.navigation_drawer);

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
			(ViewGroup) findViewById(R.id.drawer_activity_content);
		assert contentFrame != null : "No drawer_activity_content in layout";

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
	public void onDrawerClosed(View drawerView) {
		drawerToggle.onDrawerClosed(drawerView);
		navigationDrawer.onDrawerClosed(drawerView);

		if (drawerView.getId() == R.id.navigation_drawer) {
			actionBar.setTitle(title);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		drawerToggle.onDrawerOpened(drawerView);
		navigationDrawer.onDrawerOpened(drawerView);

		if (drawerView.getId() == R.id.navigation_drawer) {
			actionBar.setTitle(R.string.application_name);
		}
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
