package huawei.android.widget;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import huawei.android.hwutil.SectionLocaleUtils;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HwSortedTextListAdapter extends BaseAdapter {
    private static final String EMPTY_SECTION = "";
    private static final String TAG = "HwSortedTextListAdapter";
    private Context mContext;
    private List<? extends Map<String, ?>> mData;
    /* access modifiers changed from: private */
    public boolean mDigitLast;
    private MySectionIndexer mIndexer;
    private LayoutInflater mInflater;
    private int mItemResource;
    private final Object mLock;
    /* access modifiers changed from: private */
    public Map<String, String> mSectionMap;
    private int mSortKeyId;
    private Map<String, CollationKey> mSortKeyMap;
    /* access modifiers changed from: private */
    public String mSortKeyName;

    public HwSortedTextListAdapter(Context context, int itemLayoutResource, List<? extends Map<String, ?>> objects, String sortKeyName) {
        this(context, itemLayoutResource, 0, 0, objects, sortKeyName, false);
    }

    public HwSortedTextListAdapter(Context context, int itemLayoutResource, int textViewResourceId, List<? extends Map<String, ?>> objects, String sortKeyName, boolean digitLast) {
        this(context, itemLayoutResource, textViewResourceId, 0, objects, sortKeyName, digitLast);
    }

    public HwSortedTextListAdapter(Context context, int itemLayoutResource, int textViewResourceId, int groupLabelId, List<? extends Map<String, ?>> objects, String sortKeyName) {
        this(context, itemLayoutResource, textViewResourceId, groupLabelId, objects, sortKeyName, false);
    }

    public HwSortedTextListAdapter(Context context, int itemLayoutResource, int textViewResourceId, int groupLabelId, List<? extends Map<String, ?>> objects, String sortKeyName, boolean digitLast) {
        this.mSortKeyId = 0;
        this.mLock = new Object();
        this.mDigitLast = false;
        this.mContext = context;
        this.mInflater = (LayoutInflater) context.getSystemService("layout_inflater");
        this.mItemResource = itemLayoutResource;
        this.mSortKeyId = textViewResourceId;
        this.mDigitLast = digitLast;
        this.mData = objects;
        this.mSortKeyName = sortKeyName;
        buildSectionIndexer();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, this.mItemResource);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view;
        TextView text;
        if (convertView == null) {
            view = this.mInflater.inflate(resource, parent, false);
        } else {
            view = convertView;
        }
        try {
            if (this.mSortKeyId == 0) {
                text = (TextView) view;
            } else {
                text = (TextView) view.findViewById(this.mSortKeyId);
            }
            Object item = getItem(position);
            if (item instanceof CharSequence) {
                text.setText((CharSequence) item);
            } else {
                text.setText(item.toString());
            }
            return view;
        } catch (ClassCastException e) {
            Log.e(TAG, "You must supply a resource ID for a TextView");
            throw new IllegalStateException("HwSortedTextListAdapter requires the resource ID to be a TextView", e);
        }
    }

    public void sort(final Comparator<Object> comparator) {
        synchronized (this.mLock) {
            Collections.sort(this.mData, new Comparator<Map<String, ?>>() {
                public int compare(Map<String, ?> map1, Map<String, ?> map2) {
                    String sortKey1 = map1.get(HwSortedTextListAdapter.this.mSortKeyName).toString();
                    String sortKey2 = map2.get(HwSortedTextListAdapter.this.mSortKeyName).toString();
                    String section1 = (String) HwSortedTextListAdapter.this.mSectionMap.get(sortKey1);
                    String section2 = (String) HwSortedTextListAdapter.this.mSectionMap.get(sortKey2);
                    if (HwSortedTextListAdapter.EMPTY_SECTION.equals(section1) && HwSortedTextListAdapter.EMPTY_SECTION.equals(section2)) {
                        return comparator.compare(sortKey1, sortKey2);
                    }
                    if (HwSortedTextListAdapter.EMPTY_SECTION.equals(section1) && !HwSortedTextListAdapter.EMPTY_SECTION.equals(section2)) {
                        return 1;
                    }
                    if (HwSortedTextListAdapter.EMPTY_SECTION.equals(section2) && !HwSortedTextListAdapter.EMPTY_SECTION.equals(section1)) {
                        return -1;
                    }
                    if (HwSortedTextListAdapter.this.mDigitLast && !section1.equals(section2)) {
                        if (AlphaIndexerListView.DIGIT_LABEL.equals(section1)) {
                            return 1;
                        }
                        if (AlphaIndexerListView.DIGIT_LABEL.equals(section2)) {
                            return -1;
                        }
                    }
                    int labelResult = comparator.compare(section1, section2);
                    return labelResult == 0 ? comparator.compare(sortKey1, sortKey2) : labelResult;
                }
            });
        }
        notifyDataSetChanged();
    }

    private void buildSectionIndexer() {
        SectionLocaleUtils util = SectionLocaleUtils.getInstance();
        Map<String, Integer> sectionMapCounts = new LinkedHashMap<>();
        this.mSectionMap = new HashMap();
        this.mSortKeyMap = new HashMap();
        final Collator comparator = Collator.getInstance();
        int size = this.mData.size();
        for (int i = 0; i < size; i++) {
            String title = ((Map) this.mData.get(i)).get(this.mSortKeyName).toString();
            this.mSortKeyMap.put(title, comparator.getCollationKey(title));
            String section = TextUtils.isEmpty(title) ? EMPTY_SECTION : util.getLabel(title);
            this.mSectionMap.put(title, section);
            if (sectionMapCounts.containsKey(section)) {
                sectionMapCounts.put(section, Integer.valueOf(sectionMapCounts.get(section).intValue() + 1));
            } else {
                sectionMapCounts.put(section, 1);
            }
        }
        String[] sections = (String[]) sectionMapCounts.keySet().toArray(new String[0]);
        int len = sections.length;
        Arrays.sort(sections, new Comparator<String>() {
            public int compare(String str1, String str2) {
                if (HwSortedTextListAdapter.EMPTY_SECTION.equals(str1)) {
                    return 1;
                }
                if (HwSortedTextListAdapter.EMPTY_SECTION.equals(str2)) {
                    return -1;
                }
                if (HwSortedTextListAdapter.this.mDigitLast) {
                    if (AlphaIndexerListView.DIGIT_LABEL.equals(str1)) {
                        return 1;
                    }
                    if (AlphaIndexerListView.DIGIT_LABEL.equals(str2)) {
                        return -1;
                    }
                }
                return comparator.compare(str1, str2);
            }
        });
        int[] counts = new int[len];
        for (int i2 = 0; i2 < len; i2++) {
            counts[i2] = sectionMapCounts.get(sections[i2]).intValue();
        }
        this.mIndexer = new MySectionIndexer(sections, counts);
        sort(comparator);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public Context getContext() {
        return this.mContext;
    }

    public LayoutInflater getInflater() {
        return this.mInflater;
    }

    public int getCount() {
        return this.mData.size();
    }

    public Object getItem(int position) {
        return ((Map) this.mData.get(position)).get(this.mSortKeyName);
    }

    public int getPosition(Map<String, ?> item) {
        return this.mData.indexOf(item);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            v.setImageURI(Uri.parse(value));
        }
    }

    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public void setSortKeyName(String name) {
        this.mSortKeyName = name;
    }

    public String getSortKeyName() {
        return this.mSortKeyName;
    }

    public boolean isDigitLast() {
        return this.mDigitLast;
    }

    public Object[] getSections() {
        return this.mIndexer.getSections();
    }

    public int getPositionForSection(int section) {
        return this.mIndexer.getPositionForSection(section);
    }

    public int getSectionForPosition(int position) {
        return this.mIndexer.getSectionForPosition(position);
    }

    public Object getSectionNameForPosition(int position) {
        return this.mIndexer.getSections()[getSectionForPosition(position)];
    }
}
