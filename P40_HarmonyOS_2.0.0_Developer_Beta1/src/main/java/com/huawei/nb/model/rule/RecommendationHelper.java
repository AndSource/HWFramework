package com.huawei.nb.model.rule;

import android.database.Cursor;
import com.huawei.odmf.database.Statement;
import com.huawei.odmf.model.AEntityHelper;
import java.util.Date;

public class RecommendationHelper extends AEntityHelper<Recommendation> {
    private static final RecommendationHelper INSTANCE = new RecommendationHelper();

    @Override // com.huawei.odmf.model.AEntityHelper
    public int getNumberOfRelationships() {
        return 0;
    }

    public Object getRelationshipObject(String str, Recommendation recommendation) {
        return null;
    }

    private RecommendationHelper() {
    }

    public static RecommendationHelper getInstance() {
        return INSTANCE;
    }

    public void bindValue(Statement statement, Recommendation recommendation) {
        Long id = recommendation.getId();
        if (id != null) {
            statement.bindLong(1, id.longValue());
        } else {
            statement.bindNull(1);
        }
        Long businessId = recommendation.getBusinessId();
        if (businessId != null) {
            statement.bindLong(2, businessId.longValue());
        } else {
            statement.bindNull(2);
        }
        Long itemId = recommendation.getItemId();
        if (itemId != null) {
            statement.bindLong(3, itemId.longValue());
        } else {
            statement.bindNull(3);
        }
        Long ruleId = recommendation.getRuleId();
        if (ruleId != null) {
            statement.bindLong(4, ruleId.longValue());
        } else {
            statement.bindNull(4);
        }
        String message = recommendation.getMessage();
        if (message != null) {
            statement.bindString(5, message);
        } else {
            statement.bindNull(5);
        }
        Date timeStamp = recommendation.getTimeStamp();
        if (timeStamp != null) {
            statement.bindLong(6, timeStamp.getTime());
        } else {
            statement.bindNull(6);
        }
    }

    @Override // com.huawei.odmf.model.AEntityHelper
    public Recommendation readObject(Cursor cursor, int i) {
        return new Recommendation(cursor);
    }

    public void setPrimaryKeyValue(Recommendation recommendation, long j) {
        recommendation.setId(Long.valueOf(j));
    }
}
