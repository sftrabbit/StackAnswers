package uk.co.sftrabbit.stackanswers.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import uk.co.sftrabbit.stackanswers.app.DrawerActivity;
import uk.co.sftrabbit.stackanswers.AuthenticationActivity;
import uk.co.sftrabbit.stackanswers.NetworkActivity;
import uk.co.sftrabbit.stackanswers.SettingsActivity;
import uk.co.sftrabbit.stackanswers.R;

public class NavigationDrawer extends LinearLayout
		implements AdapterView.OnItemClickListener, DrawerLayout.DrawerListener {
	private final Context context;
	private final NavigationDrawerAdapter navigationAdapter;
	private DrawerLayout drawerLayout;
	private Intent clickedItemIntent;

	public NavigationDrawer(Context context) {
		this(context, null);
	}

	public NavigationDrawer(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		Intent hotIntent = new Intent(context, NetworkActivity.class);
		hotIntent.putExtra(NetworkActivity.EXTRA_TAB, NetworkActivity.TAB_HOT);

		Intent sitesIntent = new Intent(context, NetworkActivity.class);
		sitesIntent.putExtra(NetworkActivity.EXTRA_TAB, NetworkActivity.TAB_SITES);

		final List<NavigationItem> items = Arrays.asList(new NavigationItem[] {
			new NavigationItem(R.string.page_hot, hotIntent),
			new NavigationItem(R.string.page_sites, sitesIntent),
			new NavigationItem(R.string.page_authenticate,
			                   new Intent(context, AuthenticationActivity.class)),
			new NavigationItem(R.string.page_settings,
			                   new Intent(context, SettingsActivity.class))
		});

		final ListView navigationListView = new ListView(context);

		navigationListView.setLayoutParams(
			new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
			                              ViewGroup.LayoutParams.MATCH_PARENT));

		navigationAdapter = new NavigationDrawerAdapter(context, items,
			R.layout.list_item_navigation);
		navigationListView.setAdapter(navigationAdapter);

		navigationListView.setOnItemClickListener(this);

		addView(navigationListView);
	}

	public void setDrawerLayout(DrawerLayout drawerLayout) {
		this.drawerLayout = drawerLayout;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	                        long id) {
		drawerLayout.closeDrawers();

		NavigationItem item = navigationAdapter.getItem(position);
		Intent itemIntent = item.getIntent();
		if (itemIntent != null) {
			clickedItemIntent = itemIntent;

			DrawerActivity drawerActivity =
				(DrawerActivity) context;
			drawerActivity.setTouchEnabled(false);
		}
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		if (clickedItemIntent != null) {
			context.startActivity(clickedItemIntent);
			clickedItemIntent = null;

			DrawerActivity drawerActivity =
				(DrawerActivity) context;
			drawerActivity.setTouchEnabled(true);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		// Do nothing
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		// Do nothing
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		// Do nothing
	}

	private class NavigationDrawerAdapter extends BaseAdapter {
		private final Context context;
		private final List<NavigationItem> items;
		private final int itemLayoutResource;

		public NavigationDrawerAdapter(Context context, List<NavigationItem> items,
				int itemLayoutResource) {
			this.context = context;
			this.items = items;
			this.itemLayoutResource = itemLayoutResource; 
		}

		@Override
		public int getCount() {
			return items.size();
		}

		@Override
		public NavigationItem getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			final NavigationItem item = items.get(position);

			if (itemView == null) {
				final LayoutInflater inflater = (LayoutInflater)
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				itemView = inflater.inflate(itemLayoutResource, parent, false);
			}

			final TextView nameView = (TextView)
				itemView.findViewById(R.id.item_name);
			if (nameView != null) {
				nameView.setText(item.getNameResource());
			}

			return itemView;
		}
	}

	private class NavigationItem {
		private final int nameResource;
		private final Intent intent;

		NavigationItem(int nameResource, Intent intent) {
			this.nameResource = nameResource;
			this.intent = intent;
		}

		int getNameResource() {
			return nameResource;
		}

		Intent getIntent() {
			return intent;
		}
	}
}
