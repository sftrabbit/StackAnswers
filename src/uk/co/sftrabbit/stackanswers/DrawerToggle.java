package uk.co.sftrabbit.stackanswers;

import android.app.Activity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

public class DrawerToggle extends ActionBarDrawerToggle {
  private DrawerLayout drawerLayout;

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
