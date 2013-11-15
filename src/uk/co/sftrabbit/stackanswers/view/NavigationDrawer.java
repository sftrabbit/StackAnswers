package uk.co.sftrabbit.stackanswers.view;

import android.content.Context;
import android.content.Intent;
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
import uk.co.sftrabbit.stackanswers.NetworkActivity;
import uk.co.sftrabbit.stackanswers.SettingsActivity;
import uk.co.sftrabbit.stackanswers.R;

public class NavigationDrawer extends LinearLayout
		implements AdapterView.OnItemClickListener {
	private final Context context;
	private final NavigationDrawerAdapter navigationAdapter;

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
			new NavigationItem("What's hot?", hotIntent),
			new NavigationItem("Sites", sitesIntent),
			new NavigationItem("Sign in", null),
			new NavigationItem("Settings", new Intent(context,
			                                          SettingsActivity.class))
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	                        long id) {
		NavigationItem item = navigationAdapter.getItem(position);
		Intent itemIntent = item.getIntent();
		if (itemIntent != null) {
			context.startActivity(itemIntent);
		}
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
				nameView.setText(item.getName());
			}

			return itemView;
		}
	}

	private class NavigationItem {
		private final String name;
		private final Intent intent;

		NavigationItem(String name, Intent intent) {
			this.name = name;
			this.intent = intent;
		}

		String getName() {
			return name;
		}

		Intent getIntent() {
			return intent;
		}
	}
}
