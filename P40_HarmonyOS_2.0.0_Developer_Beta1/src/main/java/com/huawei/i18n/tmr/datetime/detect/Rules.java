package com.huawei.i18n.tmr.datetime.detect;

import huawei.android.provider.HwSettings;
import java.util.HashMap;

public class Rules {
    private HashMap<Integer, String> filterRegex = new HashMap<Integer, String>() {
        /* class com.huawei.i18n.tmr.datetime.detect.Rules.AnonymousClass3 */

        {
            put(1, "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
            put(5, "[0-9][-0-9.,\\s]+[%‰％℃度]");
            put(6, "(?<!\\d)[0-9]{1,2}\\s*(-|\\.|/)\\s*[0-9]{1,2}(?!\\d)");
            put(7, "(?<!\\d)(1[0-9]{3}|[a-z][0-9]{1,4})\\s*(-|\\.|/)\\s*[0-9]{1,2}\\s*\\2\\s*[0-9]{1,2}|[0-9]{1,2}\\s*(-|\\.|/)\\s*[0-9]{1,2}\\s*\\1\\s*(1[0-9]{3})");
            put(8, "[0-9]{1,2}(-[0-9]{1,2}){4}");
            put(9, "[0-9]+\\s*(일|時|时|点|點|\\s+d\\.|a)");
            put(10, "([0-9]+[param_digit])\\s*(号|日|일|時|时|点|點|\\s+d\\.)");
            put(11, "[param_filtertext]");
            put(12, "((GMT[+-])|(UTC[+-]))([0-1]?[0-9]|2[0-3])(:?[0-5][0-9])?");
        }
    };
    private HashMap<Integer, String> pastRegex = new HashMap<Integer, String>() {
        /* class com.huawei.i18n.tmr.datetime.detect.Rules.AnonymousClass4 */

        {
            put(100, "[param_pastForward]");
            put(101, "(?<![0-9])1[0-9]{3}\\s*[./-]");
        }
    };
    private HashMap<Integer, String> rules = new HashMap<Integer, String>() {
        /* class com.huawei.i18n.tmr.datetime.detect.Rules.AnonymousClass2 */

        {
            put(21002, "(?<![-/.A-Za-z])([regex_y](/|-|(\\.[ ]*)))?([regex_m])(/|-|(\\.[ ]*))[regex_d]\\.?\\s*\\(?([param_EEEE]|[param_E])\\)?");
            put(21003, "(?<![-/.A-Za-z])[regex_d](/|-|(\\.[ ]*))([regex_m])((/|-|(\\.[ ]*))[regex_y])?\\s*([param_EEEE]|[param_E])");
            put(21004, "([param_EEEE]|[param_E])\\s*[regex_d]\\.[regex_m]");
            put(21005, "([param_EEEE]|[param_E])[ ]+den[ ]+[regex_d]\\.[ ]*([param_MMMM]|[param_MMM])([ ]*[regex_y]){0,1}");
            put(21006, "(([param_EEEE]|[param_E])en[ ]+den[ ]+){0,1}[regex_d]:e[ ]*([param_MMMM]|[param_MMM])([ ]*[regex_y]){0,1}");
            put(21007, "([param_EEEE]|[param_E])[ ]+[regex_d]:e[ ]*([param_MMMM]|[param_MMM])([ ]*[regex_y]){0,1}");
            put(21008, "(([param_EEEE]|[param_E])،[ ]+){0,1}[regex_d][ ]*([param_MMMM]|[param_MMM])(،[ ]*[regex_y]){0,1}");
            put(21009, "(([param_EEEE]|[param_E])،[ ]+)[regex_d]\\b");
            put(21010, "(([param_EEEE]|[param_E]),?[ ]+){0,1}[regex_d][ ]+ב([param_MMMM]|[param_MMM])([ ]+[regex_y]){0,1}");
            put(21011, "([param_EEEE]|[param_E])(ที่|[ ]*ທີ|፣)[ ]*[regex_d][ ]+([param_MMMM]|[param_MMM])[ ]+[regex_y]");
            put(21012, "([param_EEEE]|[param_E]),?[ ]*ngày[ ]*[regex_d][ ]+([param_MMMM]|[param_MMM])([ ]+năm[ ]+[regex_y])?");
            put(21013, "ngày[ ]*[regex_d][ ]+tháng[ ]+([regex_m])[ ]+năm[ ]+[regex_y]");
            put(21014, "(([regex_y][param_digitYear][param_textyear])\\s*年\\s*)?(([regex_m][param_digitMonth][param_textmonth])\\s*月)\\s*([regex_d][param_digitDay])\\s*(日|号)(\\s*[\\(（]?([param_EEEE]|[param_E]|[param_days])[\\)）]?){0,1}");
            put(21015, "([regex_y]년[ ]*){0,1}([regex_m])월[ ]*[regex_d]일[ ]*([\\(（]?([param_EEEE]|[param_E])[\\)）]?){0,1}");
            put(21016, "([param_MMMM])[ ]*[regex_d]일([ ]*[\\(（]?([param_EEEE]|[param_E])[\\)）]?){0,1}");
            put(21017, "([param_EEEE]|[param_E])[ ]+(d\\.|den)[ ]+[regex_d]\\b");
            put(21018, "([param_EEEE]|[param_E])[ ]+ה-[regex_d]\\b");
            put(21021, "([regex_y]\\(e\\)ko\\s+)?([param_MMMM])ren\\s+[regex_d]a(\\s*,\\s*([param_EEEE]|[param_E]))?");
            put(21019, "([regex_y]\\s+)?([param_MMM])\\s+[regex_d]a(\\s*,\\s*([param_EEEE]|[param_E]))?");
            put(21020, "(ថ្ងៃ)?ទី[regex_d]\\s+([param_MMM]|[param_MMMM])\\s+ឆ្នាំ[regex_y]");
            put(21022, "([regex_y]\\s+m\\.\\s+)?([param_MMM]|[param_MMMM])\\s+[regex_d]\\s*d\\.(,\\s*([param_EEEE]|[param_E]))?");
            put(21023, "[regex_d](\\s+d\\.|a)(\\s*,\\s*([param_EEEE]|[param_E]))?");
            put(21024, "(((སྤྱི་ལོ་[regex_y]\\s+)?([param_MMMM])འི་ཚེས་)|(([regex_y]\\s+ལོའི་)?([param_MMM])ཚེས་))[regex_d](\\s+([param_EEEE]|[param_E]))?");
            put(21025, "(([param_EEEE]|[param_E])နေ့၊\\s+)?([regex_y]ခုနှစ်\\s+)?([param_MMM]|[param_MMMM])လ\\s{0,3}[regex_d]ရက်");
            put(21026, "([param_EEEE]|[param_E])နေ့၊");
            put(21027, "(([param_EEEE]|[param_E]),\\s+)?([regex_y]\\.?\\s+gada\\s+)[regex_d]\\.?\\s+([param_MMM]|[param_MMMM])");
            put(21028, "([param_MMM])\\s*[-–]\\s*[regex_d]");
            put(21029, "(([param_EEEE]|[param_E]|[param_E2])\\s*,\\s*)?[regex_d]\\s*[-–]?\\s*([param_MMMM]|[param_MMM])(\\s*\\,?\\s*[regex_y]\\s+(ж\\.)?)?");
            put(21030, "(([param_EEEE]|[param_E]|[param_E2])\\s*,{0,1}[ ]*){0,1}[regex_d]([-–](га|е)){0,1}(\\.|/){0,1}[ ]*([param_MMMM]|[param_MMM])((\\.|,|/){0,1}[ ]*[regex_y](\\s*(года|г))?\\.{0,1}){0,1}");
            put(21031, "(([param_EEEE]|[param_E]|[param_E2]),{0,1}[ ]*){0,1}\\s*\\bika\\b\\s*[-–]\\s*[regex_d][ ]*ng[ ]*([param_MMMM]|[param_MMM])[ ]*(\\.|,|/){0,1}([ ]*[regex_y](?!:)){0,1}");
            put(21032, "([param_EEEE]|[param_E])?([ ]*,){0,1}[ ]*[regex_d][ ]+ເດືອນ([param_MMMM]|[param_MMM])([ ]+ປີ[ ]+[regex_y])?");
            put(21033, "([param_EEEE]|[param_E2]|[param_E])");
            put(21034, "(([param_EEEE]|[param_E2]|[param_E])(,[ ]*|[ ]+))[regex_d]\\.?(?![.:\\d])");
            put(21035, "[regex_d]\\s*(/|-|\\.)\\s*[regex_m]([\\s,]*([param_EEEE]|[param_E]|[param_E2]))?");
            put(21036, "([param_EEEE]|[param_E])፣\\s+[regex_d]\\s+([param_MMMM]|[param_MMM])");
            put(21037, "([regex_y]\\s*,?\\s*){0,1}([param_MMMM]|[param_MMM])\\s+[regex_d](\\s*,\\s*([param_EEEE]|[param_E]))?");
            put(21038, "(([param_EEEE]|[param_E])\\s*,\\s+te\\s+){0,1}[regex_d]\\s+o\\s+([param_MMMM]|[param_MMM])(\\s*,\\s*[regex_y]){0,1}");
            put(21039, "(([param_EEEE]|[param_E])\\s*,\\s*)?([regex_y]\\s*оны\\s*)?([regex_m])\\s*сарын\\s+[regex_d](\\s*өдөр)?");
            put(21040, "(([param_EEEE]|[param_E])\\s*,\\s*)?([regex_y]\\s*оны\\s*)?([param_MMMM]|[param_MMM])\\s*сарын\\s+[regex_d](\\s*өдөр)?");
            put(21041, "([regex_y]،\\s+)?([regex_d]-([param_MMMM]|[param_MMM])،?)(\\s+([param_EEEE]|[param_E]))?");
            put(31001, "(([regex_ampm])[ ]*)[regex_hms](\\s*\\(?([regex_zzzz])\\)?(?![.:]?\\d)){0,1}");
            put(31002, "([regex_hms])([ ]*([regex_ampm]))?(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
            put(31003, "(2[0-3]|[0-1]?[0-9])[ ]*h\\b");
            put(31018, "(2[0-3]|[01]?[0-9])[ ]?h[ ]?[0-5][0-9]\\b");
            put(31004, "([param_am]|[param_pm]|am|pm])?\\s*(2[0-3]|[0-1]?[0-9])시([ ]*([0-5][0-9])분)?([ ]*([0-5][0-9]|60)초)?([ ]+([regex_zzzz])){0,1}");
            put(31005, "(?<!周)(([regex_zzzz])[ ]*){0,1}(([param_mm]|[param_am]|[param_pm]|am|pm)[ ]*){0,1}((2[0-3]|[0-1]?[0-9])[param_digitHour])\\s*([時时]|[点點]半?)(整|[一三]刻|((([0-5]?[0-9][param_digitMS2])\\s*分|([0-5][0-9][param_digitMS])(\\s*分)?)(\\s*(([0-5]?[0-9])[param_digitMS])\\s*秒)?))?");
            put(31006, "(([param_am]|[param_pm]|am|pm)[ ]*)?(2[0-3]|[0-1]?[0-9])\\s*(時|时)([ ]*([0-5][0-9])分)?([ ]*([0-5][0-9])秒)?([ ]*[regex_zzzz])?");
            put(31007, "(2[0-3]|[0-1]?[0-9])[ ]*(นาฬิกา|ໂມງ)[ ]*([0-5]?[0-9])[ ]*(นาที|ນາທີ) (([0-5]?[0-9])[ ]*(วินาที|ວິນາທີ)){0,1}([ ]*[regex_zzzz]){0,1}");
            put(31008, "([regex_zzzz][ ]*)?(([regex_ampm])[ ]*)[regex_hms]");
            put(31009, "[param_am]|[param_pm]");
            put(31010, "([regex_hms])([ ]*([regex_ampm]|гадзін))?(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
            put(31011, "(([regex_ampm])[ ]*)?[regex_hms]\\s*(am|pm)?\\s*(টার|बजे|এ|টা)?(\\s*\\(?([regex_zzzz])\\)?(?![.:]?\\d)){0,1}(\\s+(এ|बजे))?");
            put(31012, "([regex_hms])([ ]*([regex_ampm]))(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}(\\s+(এ|बजे))?");
            put(31013, "((\\b(jam|sa[ ]+ganap[ ]+na)\\s+)?[regex_hms])([ ]*([regex_ampm]))?(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
            put(31014, "(([regex_ampm])[ ]*)?jam\\s+[regex_hms]\\s*(am|pm)?\\s*(\\s*\\(?([regex_zzzz])\\)?(?![.:]?\\d)){0,1}");
            put(31015, "([regex_hms])\\s+ng\\s+([ ]*[regex_ampm])?(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
            put(31016, "\\bເວລາ[ ]*([regex_hms])[ ]+(ໂມງ)([ ]*[regex_ampm])?(\\s*\\(?[regex_zzzz]\\)?(?![.:]?\\d)){0,1}");
            put(31017, "[regex_d]\\s+([param_EEEE]|[param_E])");
            put(41001, "([regex_y]\\s*[年|년]\\s*)?([regex_m])\\s*[月|월]\\s*[regex_d]\\s*(日|号|일)?(\\s*\\(([param_EEEE]|[param_E])\\))?\\s*[-~至～]\\s*(([regex_m])\\s*[月|월]\\s*)?[regex_d]\\s*(日|号|일)(\\s*\\(([param_EEEE]|[param_E])\\))?");
            put(41002, "ngày[ ]*[regex_d][ ]+tháng[ ]+([regex_m])\\s*-\\s*ngày[ ]*[regex_d][ ]+tháng[ ]+([regex_m])([ ]+năm[ ]+[regex_y])?");
            put(41003, "(([regex_y])\\s+ལོའི་)?([param_MMM])ཚེས་([regex_d])\\s*[-–]\\s*་?([regex_d])");
            put(41004, "([regex_y]ခုနှစ်\\s+)?([param_MMM]|[param_MMMM])လ\\s{0,3}[regex_d]ရက်\\s{0,3}[-–]\\s{0,3}[regex_d]ရက်");
            put(41005, "(([regex_y])\\.?\\s+gada\\s+)?([regex_d])\\.?\\s*[-–]\\s*([regex_d])\\.?\\s+([param_MMM]|[param_MMMM])");
            put(41006, "(?<![-/.])([param_MMMM]|[param_MMM])([-/]|\\.|[ ]{0,3})([regex_d])\\.?\\s*(?!\\3)(-|–|~)\\s*([regex_d])(\\s*,\\s*[regex_y])?\\.?(?![.\\d–~-])");
            put(41007, "(?<![-/.])([regex_d])\\s*([-–~])\\s*([regex_d])(\\s*o\\s*)([param_MMM]|[param_MMMM])(\\s*,\\s*([regex_y])){0,1}(?![-/.])");
            put(41008, "[regex_d]\\s*–\\s*[regex_d]-([param_MMM]|[param_MMMM])");
        }
    };
    private HashMap<String, String> subRules = new HashMap<String, String>() {
        /* class com.huawei.i18n.tmr.datetime.detect.Rules.AnonymousClass1 */

        {
            put("hms", "(?<!\\d)(2[0-3]|[0-1]?[0-9])(([param_tmark])([0-5][0-9])){1,2}(?!\\d)");
            put("ampm", "[param_am]|[param_pm]|\\bAM\\b|\\bPM\\b|\\bnoon\\b|\\ba\\.m\\.|\\bp\\.m\\.|[paramopt_mm]");
            put("hms2", "(?<!\\d)(1[0-2]|0?[0-9])([:.]([0-5][0-9])){1,2}(?!\\d)");
            put("d", "(?<!\\d)(30|31|0?[1-9]|[1-2][0-9])(?!\\d)");
            put("y", "(?<!\\d)((20){0,1}[0-9]{2})(?!\\d)");
            put(HwSettings.System.FINGERSENSE_KNUCKLE_GESTURE_M_SUFFIX, "(?<!\\d)(1[0-2]|0{0,1}[1-9])(?!\\d)");
            put("zzzz", "((GMT[+-])|\\+)([0-1]?[0-9]|2[0-3])(:?[0-5][0-9])?");
        }
    };

    public HashMap<String, String> getSubRules() {
        return this.subRules;
    }

    public HashMap<Integer, String> getRules() {
        return this.rules;
    }

    public HashMap<Integer, String> getFilterRegex() {
        return this.filterRegex;
    }

    public HashMap<Integer, String> getPastRegex() {
        return this.pastRegex;
    }
}
