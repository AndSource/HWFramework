package com.huawei.android.location;

import android.location.Country;
import android.location.CountryListener;

public class CountryListenerEx {
    private CountryListener mCountryListener = new CountryListener() {
        /* class com.huawei.android.location.CountryListenerEx.AnonymousClass1 */

        public void onCountryDetected(Country country) {
            if (country != null) {
                CountryListenerEx.this.onCountryDetected(country.getCountryIso());
            }
        }
    };

    public void onCountryDetected(String countryIso) {
    }

    public CountryListener getCountryListener() {
        return this.mCountryListener;
    }
}
