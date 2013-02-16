package uk.co.sftrabbit.stacksync;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.lang.String;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class SitesFragment extends ListFragment {
  private ArrayList<Site> sites = new ArrayList<Site>() {{
    add(new Site("Stack Overflow",
      "Professional and enthusiast programmers", 0));
    add(new Site("Super User",
      "Computer enthusiasts and power users", 0));
    add(new Site("Webmasters",
      "Pro webmasters", 0));
    add(new Site("Game Development",
      "Professional and independent game developers", 0));
    add(new Site("Photography",
      "Professional, enthusiast and amateur photographers", 0));
    add(new Site("Mathematics", "People studying math at any" +
      "level and professionals in related fields", 0));
    add(new Site("Home Improvement",
      "Contractors and serious DIYers", 0));
  }};

  private static final int LIST_PADDING_HORIZONTAL = 16;
  private static final int LIST_PADDING_VERTICAL = 0;

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
    ListView listView =
      (ListView) view.findViewById(android.R.id.list);
    listView.setPadding(LIST_PADDING_HORIZONTAL, LIST_PADDING_VERTICAL,
      LIST_PADDING_HORIZONTAL, LIST_PADDING_VERTICAL);
    listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

    return view; 
  }

  private class SitesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Site> sites;
    private int itemLayoutResource;

    public SitesAdapter(Context context, ArrayList<Site> sites,
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
      final Site site = sites.get(position);

      if (convertView == null) {
        final LayoutInflater inflater = (LayoutInflater)
          context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(itemLayoutResource, parent, false);
      }

      final TextView nameView = (TextView)
        convertView.findViewById(R.id.site_name);
      if (nameView != null) {
        nameView.setText(site.getName());
      }

      final TextView audienceView = (TextView)
        convertView.findViewById(R.id.site_audience);
      if (audienceView != null) {
        audienceView.setText(site.getAudience());
      }

      final ImageView iconView = (ImageView)
        convertView.findViewById(R.id.site_icon);
      if (iconView != null) {
        iconView.setImageResource(site.getIconResource());
      }

      return convertView;
    }
  }

  private class Site {
    private String name;
    private String audience;
    private int iconResource;

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
