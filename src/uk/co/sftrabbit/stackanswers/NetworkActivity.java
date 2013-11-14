package uk.co.sftrabbit.stackanswers;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

public class NetworkActivity extends Activity {
  private ActionBar actionBar;
  private ViewPager tabPager;
  private DrawerLayout drawerLayout;
  private DrawerToggle drawerToggle;

  //TODO - give content descriptions for accessibility
  private static final List<TabSpec> TAB_SPECS =
    Collections.unmodifiableList(Arrays.asList(
      new TabSpec(R.string.tab_hot, 0, null),
      new TabSpec(R.string.tab_sites, 0, SitesFragment.class)
    ));

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_network);

    actionBar = getActionBar();
    assert actionBar != null : "No action bar in activity";

    initTabPager();
    initNavigationDrawer();
    initTabs();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.actions_network, menu);

    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (drawerToggle.onOptionsItemSelected(item)) {
      return false;
    }

    return super.onOptionsItemSelected(item);
  }

  public void onDropdownClick(View v) {
    //TODO - Inflate this menu instead of hardcoding it
    final PopupMenu popupMenu = new PopupMenu(this, v);
    final Menu menu = popupMenu.getMenu();
    menu.add("Meta Site");
    menu.add("Open in Browser");
    popupMenu.show();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  private void initTabPager() {
    tabPager = (ViewPager) findViewById(R.id.tab_pager);
    assert tabPager != null : "No tab pager in activity";

    tabPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
    tabPager.setOnPageChangeListener(new TabPagerListener(actionBar));
  }

  private void initNavigationDrawer() {
    actionBar.setDisplayHomeAsUpEnabled(true);

    drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    assert drawerLayout != null : "No navigation drawer in activity";

    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow_left, Gravity.START);
    drawerLayout.setDrawerShadow(R.drawable.drawer_shadow_right, Gravity.END);
    drawerToggle = new DrawerToggle(this, drawerLayout,
      R.drawable.icon_drawer, R.string.action_open_navigation_drawer,
      R.string.action_close_navigation_drawer);
    drawerLayout.setDrawerListener(drawerToggle);
  }

  private void initTabs() {
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    for (TabSpec tabSpec : TAB_SPECS) {
      addTab(tabSpec);
    }
  }

  private void addTab(TabSpec tabSpec) {
    final ActionBar.Tab tab = actionBar.newTab();
    tab.setTabListener(new TabListener(tabPager, drawerLayout));
    actionBar.addTab(tabSpec.applyTo(tab));
  }

  private static class TabSpec {
    private final int tabTextResourceId;
    private final int contentDescriptionResourceId;
    private final Class fragmentClass;

    public TabSpec(int tabTextResourceId, int contentDescriptionResourceId,
        Class fragmentClass) {
      this.tabTextResourceId = tabTextResourceId;
      this.contentDescriptionResourceId = contentDescriptionResourceId;
      this.fragmentClass = fragmentClass;
    }

    public ActionBar.Tab applyTo(ActionBar.Tab tab) {
      if (tabTextResourceId != 0) {
        tab.setText(tabTextResourceId);
      }
      if (contentDescriptionResourceId != 0) {
        tab.setContentDescription(contentDescriptionResourceId);
      }
      return tab;
    }

    public Fragment createFragment() {
      try {
        if (fragmentClass == null) {
          return new Fragment();
        } else {
          return (Fragment) fragmentClass.getConstructor().newInstance();
        }
      } catch (Exception exception) {
        return null;
      }
    }
  }

  private static class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public int getCount() {
      return TAB_SPECS.size();
    }

    @Override
    public Fragment getItem(int position) {
      return TAB_SPECS.get(position).createFragment();
    }
  }

  private static class TabPagerListener extends
      ViewPager.SimpleOnPageChangeListener {
    private final ActionBar actionBar;

    public TabPagerListener(ActionBar actionBar) {
      this.actionBar = actionBar;
    }

    @Override
    public void onPageSelected(int position) {
      actionBar.setSelectedNavigationItem(position);
    }
  }

  private static class TabListener implements ActionBar.TabListener {
    private final ViewPager tabPager;
    private final DrawerLayout drawerLayout;

    public TabListener(ViewPager tabPager, DrawerLayout drawerLayout) {
      this.tabPager = tabPager;
      this.drawerLayout = drawerLayout;
    }

    public void onTabReselected(ActionBar.Tab tab,
        FragmentTransaction transaction) {
      drawerLayout.closeDrawers();
    }

    public void onTabSelected(ActionBar.Tab tab,
        FragmentTransaction transaction) {
      drawerLayout.closeDrawers();
      tabPager.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(ActionBar.Tab tab,
        FragmentTransaction transaction) { }
  }
}
