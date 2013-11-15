package uk.co.sftrabbit.stackanswers.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;
import uk.co.sftrabbit.stackanswers.R;

public class NavigationDrawer extends LinearLayout {
  public NavigationDrawer(Context context) {
    this(context, null);
  }

  public NavigationDrawer(Context context, AttributeSet attrs) {
    super(context, attrs);

    List<String> itemList = Arrays.asList(new String[] { "What's hot?",
      "Sites", "Sign in", "Notifications", "Settings" });

    final ListView navigationListView = new ListView(context);

    navigationListView.setLayoutParams(
      new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));

    navigationListView.setAdapter(new NavigationDrawerAdapter(
      context, itemList, R.layout.list_item_navigation));

    addView(navigationListView);
  }

  private class NavigationDrawerAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> items;
    private final int itemLayoutResource;

    public NavigationDrawerAdapter(Context context, List<String> items,
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
    public String getItem(int position) {
      return items.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      View itemView = convertView;
      final String item = items.get(position);

      if (itemView == null) {
        final LayoutInflater inflater = (LayoutInflater)
          context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        itemView = inflater.inflate(itemLayoutResource, parent, false);
      }

      final TextView nameView = (TextView)
        itemView.findViewById(R.id.item_name);
      if (nameView != null) {
        nameView.setText(item);
      }

      return itemView;
    }
  }
}
