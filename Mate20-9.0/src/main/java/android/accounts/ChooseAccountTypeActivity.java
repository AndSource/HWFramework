package android.accounts;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ChooseAccountTypeActivity extends Activity implements DialogInterface.OnClickListener, DialogInterface.OnDismissListener {
    private static final String TAG = "AccountChooser";
    private ArrayList<AuthInfo> mAuthenticatorInfosToDisplay;
    private Dialog mDialog;
    private HashMap<String, AuthInfo> mTypeToAuthenticatorInfo = new HashMap<>();

    private static class AccountArrayAdapter extends ArrayAdapter<AuthInfo> {
        private ArrayList<AuthInfo> mInfos;
        private LayoutInflater mLayoutInflater;

        public AccountArrayAdapter(Context context, int textViewResourceId, ArrayList<AuthInfo> infos) {
            super(context, textViewResourceId, infos);
            this.mInfos = infos;
            this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = this.mLayoutInflater.inflate(R.layout.choose_account_row, null);
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.account_row_text);
                holder.icon = (ImageView) convertView.findViewById(R.id.account_row_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.text.setText(this.mInfos.get(position).name);
            holder.icon.setImageDrawable(this.mInfos.get(position).drawable);
            return convertView;
        }
    }

    private static class AuthInfo {
        final AuthenticatorDescription desc;
        final Drawable drawable;
        final String name;

        AuthInfo(AuthenticatorDescription desc2, String name2, Drawable drawable2) {
            this.desc = desc2;
            this.name = name2;
            this.drawable = drawable2;
        }
    }

    private static class ViewHolder {
        ImageView icon;
        TextView text;

        private ViewHolder() {
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.onCreate(savedInstanceState=" + savedInstanceState + ")");
        }
        Set<String> setOfAllowableAccountTypes = null;
        String[] validAccountTypes = getIntent().getStringArrayExtra(ChooseTypeAndAccountActivity.EXTRA_ALLOWABLE_ACCOUNT_TYPES_STRING_ARRAY);
        if (validAccountTypes != null) {
            setOfAllowableAccountTypes = new HashSet<>(validAccountTypes.length);
            for (String type : validAccountTypes) {
                setOfAllowableAccountTypes.add(type);
            }
        }
        buildTypeToAuthDescriptionMap();
        this.mAuthenticatorInfosToDisplay = new ArrayList<>(this.mTypeToAuthenticatorInfo.size());
        for (Map.Entry<String, AuthInfo> entry : this.mTypeToAuthenticatorInfo.entrySet()) {
            String type2 = entry.getKey();
            AuthInfo info = entry.getValue();
            if (setOfAllowableAccountTypes == null || setOfAllowableAccountTypes.contains(type2)) {
                this.mAuthenticatorInfosToDisplay.add(info);
            }
        }
        if (this.mAuthenticatorInfosToDisplay.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString(AccountManager.KEY_ERROR_MESSAGE, "no allowable account types");
            setResult(-1, new Intent().putExtras(bundle));
            finish();
        } else if (this.mAuthenticatorInfosToDisplay.size() == 1) {
            setResultAndFinish(this.mAuthenticatorInfosToDisplay.get(0).desc.type);
        } else {
            showHwStyleDialog();
        }
    }

    private void showHwStyleDialog() {
        this.mDialog = new AlertDialog.Builder(this).setTitle((CharSequence) getTitle().toString()).setAdapter(new AccountArrayAdapter(this, 17367043, this.mAuthenticatorInfosToDisplay), this).create();
        this.mDialog.show();
        this.mDialog.setOnDismissListener(this);
    }

    public void onClick(DialogInterface dialog, int position) {
        setResultAndFinish(this.mAuthenticatorInfosToDisplay.get(position).desc.type);
    }

    public void onDismiss(DialogInterface dialog) {
        finish();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mDialog != null) {
            this.mDialog.dismiss();
        }
    }

    private void setResultAndFinish(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, type);
        setResult(-1, new Intent().putExtras(bundle));
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "ChooseAccountTypeActivity.setResultAndFinish: selected account type " + type);
        }
        finish();
    }

    private void buildTypeToAuthDescriptionMap() {
        for (AuthenticatorDescription desc : AccountManager.get(this).getAuthenticatorTypes()) {
            String name = null;
            Drawable icon = null;
            try {
                Context authContext = createPackageContext(desc.packageName, 0);
                icon = authContext.getDrawable(desc.iconId);
                CharSequence sequence = authContext.getResources().getText(desc.labelId);
                if (sequence != null) {
                    String name2 = sequence.toString();
                }
                name = sequence.toString();
            } catch (PackageManager.NameNotFoundException e) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "No icon name for account type " + desc.type);
                }
            } catch (Resources.NotFoundException e2) {
                if (Log.isLoggable(TAG, 5)) {
                    Log.w(TAG, "No icon resource for account type " + desc.type);
                }
            }
            this.mTypeToAuthenticatorInfo.put(desc.type, new AuthInfo(desc, name, icon));
        }
    }
}
