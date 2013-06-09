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
import java.lang.Integer;
import java.lang.String;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class AccountsFragment extends ListFragment {
  private ArrayList<Account> accounts = new ArrayList<Account>() {{
    add(new Account("Stack Overflow", 13871, 3, 17, 54));
    add(new Account("Game Development", 266, 0, 1, 3));
    add(new Account("Programmers", 191, 0, 0, 1));
  }};

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setListAdapter(new AccountsAdapter(getActivity(), accounts,
      R.layout.list_item_account));
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = super.onCreateView(inflater, container,
      savedInstanceState);
    ListView listView =
      (ListView) view.findViewById(android.R.id.list);
    listView.setPadding(16, 0, 16, 0);
    listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

    return view; 
  }

  private class AccountsAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Account> accounts;
    private int itemLayoutResource;

    public AccountsAdapter(Context context, ArrayList<Account> accounts,
        int itemLayoutResource) {
      this.context = context;
      this.accounts = accounts;
      this.itemLayoutResource = itemLayoutResource; 
    }

    @Override
    public int getCount() {
      return accounts.size();
    }

    @Override
    public Account getItem(int position) {
      return accounts.get(position);
    }

    @Override
    public long getItemId(int position) {
      return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      final Account account = accounts.get(position);

      if (convertView == null) {
        final LayoutInflater inflater = (LayoutInflater)
          context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(itemLayoutResource, parent, false);
      }

      final TextView siteNameView = (TextView)
        convertView.findViewById(R.id.account_site_name);
      if (siteNameView != null) {
        siteNameView.setText(account.getSiteName());
      }

      final TextView reputationView = (TextView)
        convertView.findViewById(R.id.account_reputation);
      if (reputationView != null) {
        int reputation = account.getReputation();
        String formattedReputation = NumberFormat.getInstance()
          .format(reputation);
        reputationView.setText(formattedReputation);
      }

      return convertView;
    }
  }

  private class Account {
    private String siteName;
    private int reputation;
    private int goldBadgeCount;
    private int silverBadgeCount;
    private int bronzeBadgeCount;

    public Account(String siteName, int reputation, int goldBadgeCount,
        int silverBadgeCount, int bronzeBadgeCount) {
      this.siteName = siteName;
      this.reputation = reputation;
      this.goldBadgeCount = goldBadgeCount;
      this.silverBadgeCount = silverBadgeCount;
      this.bronzeBadgeCount = bronzeBadgeCount;
    }

    public String getSiteName() {
      return siteName;
    }

    public int getReputation() {
      return reputation;
    }
  }
}
