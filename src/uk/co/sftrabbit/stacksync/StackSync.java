package uk.co.sftrabbit.stacksync;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;

public class StackSync extends Activity {
  private ActionBar actionBar;
  private ViewPager tabPager;

  private static final List<Integer> TAB_TEXT_RESOURCE_IDS =
    Collections.unmodifiableList(Arrays.asList(
      R.string.tab_dashboard, R.string.tab_sites, R.string.tab_accounts
    ));

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_stacksync);

    actionBar = getActionBar();
    assert actionBar != null : "No action bar available";

    tabPager = (ViewPager) findViewById(R.id.tab_pager);
    assert tabPager != null : "No tab pager in layout";

    initTabPager();
    initTabs();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    final MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.actions_stacksync, menu);

    return super.onCreateOptionsMenu(menu);
  }

  private void initTabPager() {
    tabPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
    tabPager.setOnPageChangeListener(new TabPagerListener(actionBar));
  }

  private void initTabs() {
    actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

    for (int tabTextResourceId : TAB_TEXT_RESOURCE_IDS) {
      addTab(tabTextResourceId);
    }
  }

  private void addTab(int tabTextResourceId) {
    //TODO - setContentDescription on tab for accessibility
    final ActionBar.Tab tab = actionBar.newTab()
      .setText(tabTextResourceId)
      .setTabListener(new TabListener(tabPager));
    actionBar.addTab(tab);
  }

  private static class TabPagerAdapter extends FragmentPagerAdapter {
    public TabPagerAdapter(FragmentManager fragmentManager) {
      super(fragmentManager);
    }

    @Override
    public int getCount() {
      return TAB_TEXT_RESOURCE_IDS.size();
    }

    @Override
    public Fragment getItem(int position) {
      //TODO - Return correct fragments rather than empty fragment
      return new Fragment();
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

    public TabListener(ViewPager tabPager) {
      this.tabPager = tabPager;
    }

    public void onTabReselected(ActionBar.Tab tab,
        FragmentTransaction transaction) { }

    public void onTabSelected(ActionBar.Tab tab,
        FragmentTransaction transaction) {
      tabPager.setCurrentItem(tab.getPosition());
    }

    public void onTabUnselected(ActionBar.Tab tab,
        FragmentTransaction transaction) { }
  }
}
