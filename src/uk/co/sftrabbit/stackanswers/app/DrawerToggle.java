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
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import uk.co.sftrabbit.stackanswers.R;

public class DrawerToggle extends ActionBarDrawerToggle {
	private final DrawerLayout drawerLayout;

	public DrawerToggle(Activity activity, DrawerLayout drawerLayout,
	                    int drawerImageRes, int openDrawerContentDescRes,
	                    int closeDrawerContentDescRes) {
		super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
		      closeDrawerContentDescRes);

		this.drawerLayout = drawerLayout;
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		if (drawerView.getId() == R.id.navigation_drawer) {
			super.onDrawerClosed(drawerView);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		if (drawerView.getId() == R.id.navigation_drawer) {
			super.onDrawerOpened(drawerView);
		}
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		if (drawerView.getId() == R.id.navigation_drawer) {
			super.onDrawerSlide(drawerView, slideOffset);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		drawerLayout.closeDrawers();
		return super.onOptionsItemSelected(item);
	}
}
