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

package uk.co.sftrabbit.stackanswers;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import uk.co.sftrabbit.stackanswers.app.BaseActivity;

/**
 * An activity that gives the user a global view of the Stack Exchange network.
 * <p> 
 * <code>NetworkActivity</code> is the root activity of Stack Answers. It is the
 * first activity the user will see and helps them navigate to the content
 * they're interested in. It presents content from all Stack Exchange sites and
 * tailors that content to the user's preferences and prior activity.
 * <p>
 * The activity is divided into two tabs:
 * <ol>
 * <li>The "What's hot" tab aims to provide the user with the latest popular
 *     content from their Stack Exchange sites. It allows the user to quickly
 *     delve into the deep and interesting areas of the application.
 * <li>The "Sites" tab gives a list of all Stack Exchange sites ordered
 *     according to the user's prior activity, together with the users's account
 *     information for those sites. It provides a shallower form of navigation
 *     so the user can choose exactly which sites to browse in detail. This is
 *     implemented in {@link SitesFragment}.
 * </ol>
 * <p>
 * When the user is not logged in, content will be presented entirely according
 * to popularity amongst all Stack Exchange users.
 * 
 * @author Joseph Mansfield
 * @see SitesFragment
 **/
public class NetworkActivity extends BaseActivity {

	/**
	 * Name of extra data for identifying which tab should be selected when this
	 * activity is launched by an {@link android.content.Intent}. The associated
	 * data must be a {@link TabSelection}.
	 * 
	 * @see TabSelection
	 * @see android.content.Intent#putExtra(String,Serializable)
	 */
	public static final String EXTRA_TAB_SELECTION =
		"NetworkActivityTabSelection";

	/**
	 * Extra data for identifying which tab should be selected when the activity
	 * is launched.
	 * 
	 * @see #EXTRA_TAB_SELECTION
	 */
	public enum TabSelection {
		TAB_HOT,
		TAB_SITES
	}

	private ActionBar actionBar;
	private ViewPager tabPager;
	private HashMap<TabSelection, Integer> tabSelectionIndices =
		new HashMap<TabSelection, Integer>();

	private static final List<TabSpec> TAB_SPECS =
		Collections.unmodifiableList(Arrays.asList(
			new TabSpec(R.string.page_hot, R.string.page_hot_desc,
			            null, TabSelection.TAB_HOT),
			new TabSpec(R.string.page_sites, R.string.page_sites_desc,
			            SitesFragment.class, TabSelection.TAB_SITES)
		));

	/**
	 * Initialises the activity layout and navigation tabs.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_network);

		actionBar = getActionBar();
		assert actionBar != null : "No action bar in activity";

		initTabPager();
		initTabs();

		selectRequestedTab();
	}

	/**
	 * Initialises the relevent actions for this activity.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.actions_network, menu);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Presents a site-specific dropdown menu to the user for extra navigation
	 * options.
	 */
	public void onDropdownClick(View v) {
		//TODO - Inflate this menu instead of hardcoding it
		final PopupMenu popupMenu = new PopupMenu(this, v);
		final Menu menu = popupMenu.getMenu();
		menu.add("Meta Site");
		menu.add("Open in Browser");
		popupMenu.show();
	}

	/**
	 * Selects the tab requested by the {@link android.content.Intent}.
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		selectRequestedTab();
	}

	/**
	 * Initialises the tab pager to show the appropriate fragments and listen
	 * for tab selection changes.
	 */
	private void initTabPager() {
		tabPager = (ViewPager) findViewById(R.id.tab_pager);
		assert tabPager != null : "No tab pager in activity";

		tabPager.setAdapter(new TabPagerAdapter(getFragmentManager()));
		tabPager.setOnPageChangeListener(new TabPagerListener(actionBar));
	}

	/**
	 * Adds the appropriate tabs to the activity action bar.
	 */
	private void initTabs() {
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		for (TabSpec tabSpec : TAB_SPECS) {
			addTab(tabSpec);
		}
	}

	/**
	 * Adds a tab to the activity action bar according to the given
	 * <code>TabSpec</code>.
	 * 
	 * @param tabSpec specification of the tab to add
	 */
	private void addTab(TabSpec tabSpec) {
		TabSelection tabSelection = tabSpec.getTabSelection();
		if (tabSelection != null) {
			tabSelectionIndices.put(tabSelection, actionBar.getTabCount());
		}

		final ActionBar.Tab tab = actionBar.newTab();
		tab.setTabListener(new TabListener(tabPager, getDrawerLayout()));
		actionBar.addTab(tabSpec.applyTo(tab));
	}

	/**
	 * Select the tab requested by the intent that launched this activity.
	 * 
	 * @see #EXTRA_TAB
	 */
	private void selectRequestedTab() {
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		if (extras != null) {
			TabSelection tabSelection =
				(TabSelection) extras.getSerializable(EXTRA_TAB_SELECTION);
			if (tabSelection != null) {
				int tabIndex = tabSelectionIndices.get(tabSelection);
				actionBar.selectTab(actionBar.getTabAt(tabIndex));
			}
		}
	}

	/**
	 * A specification that describes an action bar tab. Includes resource IDs for
	 * the tab's text label and content description, and provides a
	 * {@link android.app.Fragment} class to instantiate for that tab.
	 */
	private static class TabSpec {
		private final int tabTextResourceId;
		private final int contentDescriptionResourceId;
		private final Class fragmentClass;
		private final TabSelection tabSelection;

		/**
		 * Constructs a <code>TabSpec</code> with the given properties.
		 * 
		 * @param tabTextResourceId string resource ID for the tab text
		 * @param contentDescriptionResourceId string resource ID for the tab
		 *        content description
		 * @param fragmentClass a subclass of {@link android.app.Fragment} to
		 *        instantiate for this tab
		 * @param tabSelection the corresponding tab selection extra value
		 */
		public TabSpec(int tabTextResourceId, int contentDescriptionResourceId,
		               Class fragmentClass, TabSelection tabSelection) {
			this.tabTextResourceId = tabTextResourceId;
			this.contentDescriptionResourceId = contentDescriptionResourceId;
			this.fragmentClass = fragmentClass;
			this.tabSelection = tabSelection;
		}

		/**
		 * Applies this tab specification to the given tab.
		 * 
		 * @param tab the tab to apply this specification to
		 * @return the modified tab
		 */
		public ActionBar.Tab applyTo(ActionBar.Tab tab) {
			if (tabTextResourceId != 0) {
				tab.setText(tabTextResourceId);
			}
			if (contentDescriptionResourceId != 0) {
				tab.setContentDescription(contentDescriptionResourceId);
			}
			return tab;
		}

		/**
		 * Instantiates the {@link android.app.Fragment} for this tab.
		 * 
		 * @return the instantiated <code>Fragment</code>
		 */
		public Fragment createFragment() {
			try {
				if (fragmentClass == null) {
					return new Fragment();
				} else {
					assert Fragment.class.isAssignableFrom(fragmentClass) :
						"Class " + fragmentClass.getName() + " is not a Fragment subclass.";

					return (Fragment) fragmentClass.getConstructor().newInstance();
				}
			} catch (Exception exception) {
				return null;
			}
		}

		/**
		 * Gets corresponding tab selection extra value.
		 * 
		 * @return corresponding tab selection extra value
		 */
		public TabSelection getTabSelection() {
			return tabSelection;
		}
	}

	/**
	 * A view pager adapter that pages fragments according to the activity's tab
	 * specifications.
	 * 
	 * @see android.support.v4.view.ViewPager
	 * @see TabSpec
	 */
	private static class TabPagerAdapter extends FragmentPagerAdapter {

		public TabPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		/**
		 * Gets the number of fragments to present.
		 *
		 * @return the number of fragments
		 */
		@Override
		public int getCount() {
			return TAB_SPECS.size();
		}

		/**
		 * Gets the fragment corresponding to <code>position</code>.
		 * 
		 * @param position the position
		 * @return the fragment at <code>position</code>
		 */
		@Override
		public Fragment getItem(int position) {
			return TAB_SPECS.get(position).createFragment();
		}
	}

	/**
	 * A view pager listener that selects the corresponding tab when the page
	 * changes.
	 * 
	 * @see android.support.v4.view.ViewPager
	 */
	private static class TabPagerListener extends
			ViewPager.SimpleOnPageChangeListener {
		private final ActionBar actionBar;

		/**
		 * Constructs a listener that will select tabs from the given action bar.
		 * 
		 * @param actionBar the action bar to select tabs from
		 */
		public TabPagerListener(ActionBar actionBar) {
			this.actionBar = actionBar;
		}

		/**
		 * Selects the corresponding tab for the given page position.
		 * 
		 * @param position the page position that has been selected
		 */
		@Override
		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
		}
	}

	/**
	 * An action bar tab listener that listens for tab selections and adjusts the
	 * view pager accordingly, closing any drawers that might get in the way.
	 */
	private static class TabListener implements ActionBar.TabListener {
		private final ViewPager tabPager;
		private final DrawerLayout drawerLayout;

		/**
		 * Constructs a <code>TabListener</code> that will select the corresponding
		 * pages from the given {@link android.support.v4.view.ViewPager} and close
		 * all drawers in the given {@link android.support.v4.widget.DrawerLayout}.
		 * 
		 * @param tabPager the pager to select corresponding pages from
		 * @param drawerLayout the drawer layout to close drawers in
		 */
		public TabListener(ViewPager tabPager, DrawerLayout drawerLayout) {
			this.tabPager = tabPager;
			this.drawerLayout = drawerLayout;
		}

		/**
		 * Closes all open drawers when a tab is reselected.
		 */
		public void onTabReselected(ActionBar.Tab tab,
		                            FragmentTransaction transaction) {
			drawerLayout.closeDrawers();
		}

		/**
		 * Closes all open drawers and changes the current page when a tab is
		 * selected.
		 */
		public void onTabSelected(ActionBar.Tab tab,
		                          FragmentTransaction transaction) {
			drawerLayout.closeDrawers();
			tabPager.setCurrentItem(tab.getPosition());
		}

		public void onTabUnselected(ActionBar.Tab tab,
		                            FragmentTransaction transaction) {
			// Do nothing
		}
	}
}
