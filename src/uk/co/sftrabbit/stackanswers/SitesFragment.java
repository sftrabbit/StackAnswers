package uk.co.sftrabbit.stackanswers;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

public class SitesFragment extends ListFragment {
	private final List<Site> sites = Arrays.asList(new Site[] {
		new Site("Stack Overflow", "Professional and enthusiast programmers",
		         R.drawable.site_stackoverflow),
		new Site("Super User", "Computer enthusiasts and power users",
		         R.drawable.site_superuser),
		new Site("Webmasters", "Pro webmasters",
		         R.drawable.site_webmasters),
		new Site("Game Development", "Professional and independent game developers",
		         R.drawable.site_gamedevelopment),
		new Site("Photography", "Professional, enthusiast and " +
		         "amateur photographers", R.drawable.site_photography),
		new Site("Mathematics", "People studying math at any level and " +
		         "professionals in related fields", R.drawable.site_mathematics),
		new Site("Home Improvement", "Contractors and serious DIYers",
		         R.drawable.site_homeimprovement)});

	private static final int LIST_PADDING_HORIZONTAL = 8;
	private static final int LIST_PADDING_VERTICAL = 8;
	private static final int LIST_ITEM_SEPERATION = 8;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setListAdapter(new SitesAdapter(getActivity(), sites,
		               R.layout.list_item_site));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View view = super.onCreateView(inflater, container,
		                                     savedInstanceState);
		ListView listView = (ListView) view.findViewById(android.R.id.list);

		Resources resources = getResources();
		DisplayMetrics displayMetrics = resources.getDisplayMetrics();
		int paddingHorizontal = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, LIST_PADDING_HORIZONTAL,
			displayMetrics);
		int paddingVertical = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, LIST_PADDING_VERTICAL,
			displayMetrics);
		int seperation = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_DIP, LIST_ITEM_SEPERATION,
			displayMetrics);

		listView.setPadding(paddingHorizontal, paddingVertical,
		                    paddingHorizontal, paddingVertical);
		listView.setClipToPadding(false);
		listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

		listView.setDivider(resources.getDrawable(android.R.color.transparent));
		listView.setDividerHeight(seperation);

		return view; 
	}

	private class SitesAdapter extends BaseAdapter {
		private final Context context;
		private final List<Site> sites;
		private final int itemLayoutResource;

		public SitesAdapter(Context context, List<Site> sites,
				int itemLayoutResource) {
			this.context = context;
			this.sites = sites;
			this.itemLayoutResource = itemLayoutResource; 
		}

		@Override
		public int getCount() {
			return sites.size();
		}

		@Override
		public Site getItem(int position) {
			return sites.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View itemView = convertView;
			final Site site = sites.get(position);

			if (itemView == null) {
				final LayoutInflater inflater = (LayoutInflater)
					context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				itemView = inflater.inflate(itemLayoutResource, parent, false);
			}

			final TextView nameView = (TextView)
				itemView.findViewById(R.id.site_title);
			if (nameView != null) {
				nameView.setText(site.getName());
			}

			final ImageView iconView = (ImageView)
				itemView.findViewById(R.id.site_image);
			if (iconView != null) {
				iconView.setImageResource(site.getIconResource());
			}

			return itemView;
		}
	}

	private class Site {
		private final String name;
		private final String audience;
		private final int iconResource;

		public Site(String name, String audience, int iconResource) {
			this.name = name;
			this.audience = audience;
			this.iconResource = iconResource;
		}

		public String getName() {
			return name;
		}

		public String getAudience() {
			return audience;
		}

		public int getIconResource() {
			return iconResource;
		}
	}
}
