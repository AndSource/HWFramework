package com.huawei.android.speech.tts;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TtsEngines;
import java.util.List;

public class TtsEnginesEx {
    private TtsEngines mTtsEngines;

    public TtsEnginesEx(Context context) {
        this.mTtsEngines = new TtsEngines(context);
    }

    public List<TextToSpeech.EngineInfo> getEngines() {
        return this.mTtsEngines.getEngines();
    }
}
