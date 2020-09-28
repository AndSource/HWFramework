package com.android.internal.telephony.latlongtotimezone;

import com.android.internal.telephony.HwCallManagerReference;
import com.android.internal.telephony.HwHiCureDetection;
import com.android.internal.telephony.HwServiceStateTrackerEx;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimezoneMapper {
    private static TzPolygon[] poly = initPolyArray();
    static String[] timezoneStrings = {"unknown", "unusedtimezone", "Australia/Lord_Howe", "America/Manaus", "America/Phoenix", "Australia/Darwin", "America/Godthab", "America/Sitka", "America/Danmarkshavn", "Asia/Jakarta", "America/Boise", "America/Santiago", "America/Merida", "America/Bahia_Banderas", "Australia/Perth", "Asia/Sakhalin", "Asia/Vladivostok", "America/Los_Angeles", "Australia/Currie", "America/Vancouver", "Asia/Magadan", "America/North_Dakota/New_Salem", "America/Indiana/Petersburg", "America/Inuvik", "Europe/Lisbon", "Pacific/Chatham", "Antarctica/Macquarie", "America/Araguaina", "Atlantic/Madeira", "Australia/Adelaide", "America/North_Dakota/Beulah", "America/Scoresbysund", "America/Swift_Current", "America/Moncton", "Australia/Eucla", "America/Glace_Bay", "Asia/Novokuznetsk", "America/Indiana/Indianapolis", "America/Indiana/Tell_City", "America/Detroit", "America/Menominee", "Asia/Novosibirsk", "America/Yakutat", "Europe/Volgograd", "Pacific/Marquesas", "Asia/Khandyga", "America/North_Dakota/Center", "Pacific/Guam", "America/Cambridge_Bay", "America/Kentucky/Monticello", "Australia/Hobart", "Pacific/Galapagos", "Asia/Oral", "America/Dawson_Creek", "America/Halifax", "Asia/Aqtau", "Asia/Hovd", "Asia/Ulaanbaatar", "Australia/Melbourne", "Australia/Broken_Hill", "America/Kentucky/Louisville", "Asia/Jayapura", "America/Ojinaga", "America/Nome", "Pacific/Wake", "America/Iqaluit", "Asia/Ust-Nera", "Asia/Yakutsk", "America/Yellowknife", "America/Fortaleza", "Asia/Irkutsk", "Pacific/Tarawa", "America/Resolute", "Europe/Samara", "America/Adak", "America/Chicago", "America/New_York", "America/Pangnirtung", "Pacific/Port_Moresby", "America/Bahia", "Asia/Nicosia", "Europe/Kaliningrad", "Asia/Yekaterinburg", "America/Rio_Branco", "America/Goose_Bay", "Europe/Moscow", "America/Chihuahua", "America/Recife", "America/Mexico_City", "America/Tijuana", "America/Metlakatla", "Pacific/Midway", "Europe/Simferopol", "Asia/Choibalsan", "Australia/Sydney", "Australia/Lindeman", "Pacific/Chuuk", "Pacific/Pohnpei", "America/Atikokan", "America/Edmonton", "Australia/Brisbane", "Pacific/Kiritimati", "America/Toronto", "Asia/Qyzylorda", "Asia/Aqtobe", "America/Eirunepe", "America/Blanc-Sablon", "Pacific/Honolulu", "Pacific/Kosrae", "America/Indiana/Winamac", "America/Creston", "America/Indiana/Knox", "Pacific/Easter", "Atlantic/Canary", "America/Denver", "Pacific/Enderbury", "Atlantic/Azores", "America/Winnipeg", "Asia/Krasnoyarsk", "America/St_Johns", "America/Noronha", "Pacific/Gambier", "America/Thule", "America/Rankin_Inlet", "America/Regina", "America/Indiana/Vincennes", "America/Santarem", "Pacific/Tahiti", "Africa/Ceuta", "Asia/Singapore", "America/Campo_Grande", "Asia/Pontianak", "America/Whitehorse", "America/Maceio", "America/Sao_Paulo", "America/Dawson", "Africa/Kinshasa", "Europe/Madrid", "America/Nipigon", "America/Porto_Velho", "Asia/Almaty", "America/Boa_Vista", "Africa/Lubumbashi", "America/Thunder_Bay", "America/Juneau", "America/Cuiaba", "Asia/Anadyr", "America/Anchorage", "Asia/Kamchatka", "America/Matamoros", "Pacific/Auckland", "America/Rainy_River", "Asia/Omsk", "America/Guayaquil", "Asia/Makassar", "America/Monterrey", "America/Indiana/Marengo", "Pacific/Saipan", "America/Indiana/Vevay", "America/Belem", "America/Cancun", "America/Hermosillo", "America/Mazatlan"};

    public static String latLngToTimezoneString(double lat, double lng) {
        String tzId = timezoneStrings[getTzInt((float) lat, (float) lng)];
        if (tzId == null || tzId.equals("unusedtimezone") || tzId.equals("unknown")) {
            return TimezoneMapperDependencies.latLngToTimezoneString(lat, lng);
        }
        return tzId;
    }

    private static int getTzInt(float lat, float lng) {
        if (lng < 4.268278f) {
            if (lat >= 50.982723f) {
                return call49(lat, lng);
            }
            if (lat < 12.893453f) {
                return call25(lat, lng);
            }
            if (lng < -79.66875f) {
                return call23(lat, lng);
            }
            return call24(lat, lng);
        } else if (lng >= 104.94967f) {
            return call79(lat, lng);
        } else {
            if (lng < 40.77761f) {
                return call66(lat, lng);
            }
            if (lat < 19.266027f) {
                if (lat >= 5.916833f || lng < 86.93091f) {
                    return 1;
                }
                return call55(lat, lng);
            } else if (lat < 40.805862f) {
                return 1;
            } else {
                if (lat < 72.25183f) {
                    if (lat < 68.38711f) {
                        if (lng >= 56.750275f) {
                            return call64(lat, lng);
                        }
                        if (lat < 46.07625f) {
                            return call59(lat, lng);
                        }
                        if (lat < 67.72238f) {
                            return call58(lat, lng);
                        }
                        return 85;
                    } else if (lng < 61.267807f) {
                        return 85;
                    } else {
                        return call65(lat, lng);
                    }
                } else if (lng >= 79.20914f) {
                    return 118;
                } else {
                    if (lng < 63.16486f) {
                        return 85;
                    }
                    if (lng < 76.08814f) {
                        if (lat < 73.526054f) {
                            return 82;
                        }
                        return 85;
                    } else if (lat < 72.60284f) {
                        if (lng >= 78.3407f && poly[0].contains(lat, lng)) {
                            return 118;
                        }
                        return 82;
                    } else if (lng >= 76.746025f || lat >= 73.46723f) {
                        return 118;
                    } else {
                        return 82;
                    }
                }
            }
        }
    }

    private static int call0(float lat, float lng) {
        if (lng < -73.58916f) {
            return 11;
        }
        if (lng >= -66.01463f) {
            return 1;
        }
        if (lng < -73.292854f) {
            if (lat >= -49.149796f || lat < -52.15103f || !poly[1].contains(lat, lng)) {
                return 11;
            }
            return 1;
        } else if (lat < -52.45612f) {
            return 11;
        } else {
            if (lat < -50.52227f) {
                if (lat < -52.416225f) {
                    return 11;
                }
                if (lng < -72.88555f) {
                    if (lat >= -51.246307f && !poly[2].contains(lat, lng)) {
                        return 1;
                    }
                    return 11;
                } else if (lat < -51.962368f) {
                    if (lng >= -72.54042f && poly[3].contains(lat, lng)) {
                        return 1;
                    }
                    return 11;
                } else if (poly[4].contains(lat, lng)) {
                    return 1;
                } else {
                    return 11;
                }
            } else if (lng >= -71.297264f) {
                return 1;
            } else {
                if (lng < -72.797134f) {
                    if (lat >= -48.937122f || poly[5].contains(lat, lng)) {
                        return 11;
                    }
                    return 1;
                } else if (poly[6].contains(lat, lng)) {
                    return 11;
                } else {
                    return 1;
                }
            }
        }
    }

    private static int call1(float lat, float lng) {
        if (lat < -38.927124f) {
            if (lng < -71.38628f) {
                return lat < -42.306767f ? poly[7].contains(lat, lng) ? 11 : 1 : lat < -39.284485f ? poly[8].contains(lat, lng) ? 11 : 1 : poly[9].contains(lat, lng) ? 1 : 11;
            }
            return 1;
        } else if (lat < -28.409985f) {
            if (lng >= -69.63204f) {
                return 1;
            }
            if (lng < -71.474365f) {
                return 11;
            }
            return lat < -33.668552f ? poly[10].contains(lat, lng) ? 11 : 1 : (!poly[11].contains(lat, lng) && !poly[12].contains(lat, lng)) ? 11 : 1;
        } else if (lat < -19.919102f) {
            if (lng >= -66.98794f) {
                return 1;
            }
            if (lng < -70.59943f) {
                return 11;
            }
            return lat < -22.992395f ? poly[13].contains(lat, lng) ? 11 : 1 : (!poly[14].contains(lat, lng) && !poly[15].contains(lat, lng)) ? 11 : 1;
        } else if (lng < -68.407265f) {
            return poly[16].contains(lat, lng) ? 11 : 1;
        } else {
            if (lng < -64.69972f) {
                if (poly[17].contains(lat, lng)) {
                    return 139;
                }
                return 1;
            } else if (poly[18].contains(lat, lng)) {
                return 139;
            } else {
                return 1;
            }
        }
    }

    private static int call2(float lat, float lng) {
        if (lat < -14.118273f) {
            if (lat < -22.100248f) {
                if (lng < -56.390266f) {
                    if (poly[19].contains(lat, lng) || poly[20].contains(lat, lng) || poly[21].contains(lat, lng)) {
                        return 130;
                    }
                    if (poly[22].contains(lat, lng)) {
                        return 134;
                    }
                    return 1;
                } else if (lat < -26.091236f) {
                    if (poly[23].contains(lat, lng)) {
                        return 134;
                    }
                    return 1;
                } else if (poly[24].contains(lat, lng)) {
                    return 130;
                } else {
                    if (poly[25].contains(lat, lng)) {
                        return 134;
                    }
                    return 1;
                }
            } else if (lng < -56.390266f) {
                if (poly[26].contains(lat, lng)) {
                    return 130;
                }
                return poly[27].contains(lat, lng) ? 145 : 1;
            } else if (lat < -18.10926f) {
                return poly[28].contains(lat, lng) ? 130 : 134;
            } else {
                if (poly[29].contains(lat, lng)) {
                    return 130;
                }
                if (poly[30].contains(lat, lng)) {
                    return 134;
                }
                return 145;
            }
        } else if (poly[31].contains(lat, lng)) {
            return 1;
        } else {
            if (poly[32].contains(lat, lng)) {
                return 145;
            }
            return 139;
        }
    }

    private static int call3(float lat, float lng) {
        if (lat < -42.364952f) {
            if (lng < -72.82374f || lng < -72.698524f) {
                return 11;
            }
            if (lng < -71.11016f) {
                return lat < -44.52303f ? poly[33].contains(lat, lng) ? 1 : 11 : poly[34].contains(lat, lng) ? 1 : 11;
            }
            return 1;
        } else if (lng < -61.857124f) {
            if (lng < -72.12493f) {
                return lat < -26.271572f ? 11 : 1;
            }
            return call1(lat, lng);
        } else if (lng < -45.453236f) {
            if (lat < -30.082224f) {
                if (lng < -58.08305f) {
                    return 1;
                }
                if (lng >= -53.073933f) {
                    return 134;
                }
                if (lat < -34.48238f || lng < -57.98136f) {
                    return 1;
                }
                if (lat < -32.746323f) {
                    if (poly[35].contains(lat, lng)) {
                        return 134;
                    }
                    return 1;
                } else if (poly[36].contains(lat, lng)) {
                    return 134;
                } else {
                    return 1;
                }
            } else if (lng < -48.69024f) {
                if (lng < -50.92341f) {
                    return call2(lat, lng);
                }
                if (lat < -26.282333f) {
                    return 134;
                }
                if (poly[37].contains(lat, lng)) {
                    return 27;
                }
                if (poly[38].contains(lat, lng)) {
                    return 145;
                }
                return 134;
            } else if (lat < -24.088102f) {
                return 134;
            } else {
                if (poly[39].contains(lat, lng)) {
                    return 27;
                }
                return poly[40].contains(lat, lng) ? 79 : 134;
            }
        } else if (lng < -39.202183f) {
            if (lat < -20.238016f) {
                return 134;
            }
            return (lng >= -39.65679f || poly[41].contains(lat, lng)) ? 79 : 134;
        } else if (lng < -37.704918f) {
            return 79;
        } else {
            return 1;
        }
    }

    private static int call4(float lat, float lng) {
        if (lng >= -64.7466f) {
            return lat < -12.098567f ? lng < -64.68261f ? poly[55].contains(lat, lng) ? 1 : 139 : poly[56].contains(lat, lng) ? 139 : 1 : poly[57].contains(lat, lng) ? 139 : 3;
        }
        if (lat < -6.075534f) {
            if (lng < -69.96559f) {
                if (poly[42].contains(lat, lng)) {
                    return 83;
                }
                if (poly[43].contains(lat, lng)) {
                    return 105;
                }
                return 1;
            } else if (lat < -9.099981f) {
                if (lng < -67.356094f) {
                    if (poly[44].contains(lat, lng)) {
                        return 3;
                    }
                    if (poly[45].contains(lat, lng)) {
                        return 83;
                    }
                    if (poly[46].contains(lat, lng)) {
                        return 105;
                    }
                    return 1;
                } else if (poly[47].contains(lat, lng)) {
                    return 1;
                } else {
                    if (poly[48].contains(lat, lng)) {
                        return 3;
                    }
                    if (poly[49].contains(lat, lng)) {
                        return 83;
                    }
                    return 139;
                }
            } else if (poly[50].contains(lat, lng)) {
                return 83;
            } else {
                if (poly[51].contains(lat, lng)) {
                    return 105;
                }
                return poly[52].contains(lat, lng) ? 139 : 3;
            }
        } else if (poly[53].contains(lat, lng)) {
            return 3;
        } else {
            if (poly[54].contains(lat, lng)) {
                return 105;
            }
            return 1;
        }
    }

    private static int call5(float lat, float lng) {
        if (lat < -2.231925f) {
            if (lat >= -7.178177f) {
                return (!poly[63].contains(lat, lng) && !poly[64].contains(lat, lng)) ? 3 : 126;
            }
            if (lng < -60.489967f) {
                if (poly[58].contains(lat, lng)) {
                    return 3;
                }
                return poly[59].contains(lat, lng) ? 145 : 139;
            } else if (poly[60].contains(lat, lng)) {
                return 3;
            } else {
                if (poly[61].contains(lat, lng)) {
                    return 126;
                }
                return poly[62].contains(lat, lng) ? 139 : 145;
            }
        } else if (lng < -60.489967f) {
            if (lat < 1.633574f) {
                if (poly[65].contains(lat, lng)) {
                    return 1;
                }
                if (poly[66].contains(lat, lng)) {
                    return 141;
                }
                return 3;
            } else if (poly[67].contains(lat, lng)) {
                return 3;
            } else {
                return poly[68].contains(lat, lng) ? 141 : 1;
            }
        } else if (lat < 1.633574f) {
            if (poly[69].contains(lat, lng)) {
                return 1;
            }
            if (poly[70].contains(lat, lng)) {
                return 3;
            }
            if (poly[71].contains(lat, lng)) {
                return 141;
            }
            return 126;
        } else if (poly[72].contains(lat, lng)) {
            return 126;
        } else {
            return poly[73].contains(lat, lng) ? 141 : 1;
        }
    }

    private static int call6(float lat, float lng) {
        if (lng < -77.30605f) {
            if (lat >= 1.43902f || lat < -9.017232f) {
                return 1;
            }
            return lat < -2.821393f ? poly[74].contains(lat, lng) ? 153 : 1 : (lng >= -78.83352f && poly[75].contains(lat, lng)) ? 1 : 153;
        } else if (lng < -75.184586f) {
            return (lat >= 0.441074f || lat < -12.072618f || !poly[76].contains(lat, lng)) ? 1 : 153;
        } else {
            if (lng < -64.674255f) {
                if (lat < -0.026641f) {
                    return call4(lat, lng);
                }
                if (poly[77].contains(lat, lng)) {
                    return 3;
                }
                if (poly[78].contains(lat, lng)) {
                    return 141;
                }
                return 1;
            } else if (lng < -56.305687f) {
                return call5(lat, lng);
            } else {
                if (lat >= 4.397033f) {
                    return 1;
                }
                if (lat < -0.409487f) {
                    if (poly[79].contains(lat, lng)) {
                        return 126;
                    }
                    if (poly[80].contains(lat, lng)) {
                        return 145;
                    }
                    return 159;
                } else if (lng >= -51.61395f) {
                    return 159;
                } else {
                    if (lat < 1.993773f) {
                        if (poly[81].contains(lat, lng)) {
                            return 1;
                        }
                        return poly[82].contains(lat, lng) ? 159 : 126;
                    } else if (poly[83].contains(lat, lng)) {
                        return 126;
                    } else {
                        if (poly[84].contains(lat, lng)) {
                            return 159;
                        }
                        return 1;
                    }
                }
            }
        }
    }

    private static int call7(float lat, float lng) {
        if (lng < -138.67421f) {
            if (lng < -159.37619f) {
                if (lat < -8.553614f) {
                    return 1;
                }
                if (lng < -171.07794f) {
                    return 115;
                }
                if (lat < -0.368995f) {
                    return 1;
                }
                return 101;
            } else if (lng >= -150.20836f) {
                return 44;
            } else {
                if (lng >= -157.90714f || lng < -159.26158f) {
                    return 101;
                }
                return 1;
            }
        } else if (lng < -81.578545f) {
            if (lng < -90.64139f) {
                if (lng < -138.59352f) {
                    return 44;
                }
                return 51;
            } else if (lng < -89.23986f) {
                return 51;
            } else {
                return 1;
            }
        } else if (lat >= -3.404815f) {
            return 153;
        } else {
            if (lng < -80.83873f) {
                return 1;
            }
            if (lng < -80.23094f) {
                if (lat < -3.462763f) {
                    if (lat < -3.970826f) {
                        if (poly[85].contains(lat, lng)) {
                            return 153;
                        }
                        return 1;
                    } else if (poly[86].contains(lat, lng)) {
                        return 153;
                    } else {
                        return 1;
                    }
                } else if (poly[87].contains(lat, lng)) {
                    return 1;
                } else {
                    return 153;
                }
            } else if (lat >= -3.433036f) {
                return 153;
            } else {
                if (lat < -4.285207f) {
                    if (lng < -80.13631f) {
                        if (poly[88].contains(lat, lng)) {
                            return 153;
                        }
                        return 1;
                    } else if (poly[89].contains(lat, lng)) {
                        return 153;
                    } else {
                        return 1;
                    }
                } else if (poly[90].contains(lat, lng)) {
                    return 1;
                } else {
                    return 153;
                }
            }
        }
    }

    private static int call8(float lat, float lng) {
        if (lat < -1.282514f) {
            if (lng < -48.77489f) {
                if (lat >= -6.697871f) {
                    return 159;
                }
                if (lat < -7.745792f) {
                    if (poly[91].contains(lat, lng)) {
                        return 159;
                    }
                    return 27;
                } else if (lat < -6.998296f) {
                    if (poly[92].contains(lat, lng)) {
                        return 159;
                    }
                    return 27;
                } else if (poly[93].contains(lat, lng)) {
                    return 159;
                } else {
                    return 27;
                }
            } else if (lat < -5.164167f) {
                if (lat < -10.330833f) {
                    if (poly[94].contains(lat, lng)) {
                        return 79;
                    }
                    return 27;
                } else if (poly[95].contains(lat, lng)) {
                    return 69;
                } else {
                    if (poly[96].contains(lat, lng)) {
                        return 159;
                    }
                    return 27;
                }
            } else if (lng < -48.52081f) {
                return 159;
            } else {
                return lng < -48.50529f ? (lat >= -5.155559f || !poly[97].contains(lat, lng)) ? 159 : 69 : poly[98].contains(lat, lng) ? 69 : 159;
            }
        } else if (lng < -46.18288f) {
            return 159;
        } else {
            if (lng < -46.04942f) {
                return (lat >= -1.15275f || !poly[99].contains(lat, lng)) ? 159 : 69;
            }
            return 69;
        }
    }

    private static int call9(float lat, float lng) {
        if (lng < -45.708477f) {
            if (lat >= -1.032339f) {
                return 159;
            }
            if (lng >= -49.20737f) {
                return call8(lat, lng);
            }
            if (lat >= -6.92845f) {
                return 159;
            }
            if (lat < -8.085886f) {
                if (poly[100].contains(lat, lng)) {
                    return 159;
                }
                return 27;
            } else if (lat < -7.283911f) {
                if (poly[101].contains(lat, lng)) {
                    return 27;
                }
                return 159;
            } else if (poly[102].contains(lat, lng)) {
                return 27;
            } else {
                return 159;
            }
        } else if (lat >= -2.13911f) {
            return lng < -44.438354f ? 69 : 1;
        } else {
            if (lat >= -3.804812f) {
                return 69;
            }
            if (lng < -34.790123f) {
                if (lng < -40.2493f) {
                    if (poly[103].contains(lat, lng)) {
                        return 69;
                    }
                    return poly[104].contains(lat, lng) ? 87 : 79;
                } else if (lat < -7.96462f) {
                    if (lng < -37.538258f) {
                        if (poly[105].contains(lat, lng)) {
                            return 87;
                        }
                        if (poly[106].contains(lat, lng)) {
                            return 133;
                        }
                        return 79;
                    } else if (poly[107].contains(lat, lng)) {
                        return 69;
                    } else {
                        if (poly[108].contains(lat, lng)) {
                            return 79;
                        }
                        if (poly[109].contains(lat, lng)) {
                            return 87;
                        }
                        return 133;
                    }
                } else if (!poly[110].contains(lat, lng) && !poly[111].contains(lat, lng)) {
                    return 69;
                } else {
                    return 87;
                }
            } else if (lng < -32.382004f) {
                return HwCallManagerReference.HWBuffer.BUFFER_SIZE;
            } else {
                return 1;
            }
        }
    }

    private static int call10(float lat, float lng) {
        if (lat < 24.727892f) {
            if (lng >= -102.50731f) {
                return lng < -97.68436f ? lat < 24.606182f ? poly[117].contains(lat, lng) ? 88 : 155 : (lng >= -101.0113f || poly[118].contains(lat, lng)) ? 155 : 88 : lat < 21.937117f ? 88 : 155;
            }
            if (lng < -106.33564f) {
                return (lat >= 21.819218f && poly[112].contains(lat, lng)) ? 155 : 162;
            }
            if (lng < -105.89513f) {
                return (lat >= 21.8899f && poly[113].contains(lat, lng)) ? 155 : 162;
            }
            if (poly[114].contains(lat, lng)) {
                return 13;
            }
            if (poly[115].contains(lat, lng)) {
                return 155;
            }
            return poly[116].contains(lat, lng) ? 162 : 88;
        } else if (lng >= -101.1596f) {
            return 155;
        } else {
            if (lng < -107.9825f) {
                return 162;
            }
            return lng < -106.777725f ? poly[119].contains(lat, lng) ? 155 : 162 : poly[120].contains(lat, lng) ? 88 : 155;
        }
    }

    private static int call11(float lat, float lng) {
        if (lng < -103.05771f) {
            if (lng < -107.10085f) {
                if (lat < 27.061386f) {
                    if (poly[121].contains(lat, lng)) {
                        return 86;
                    }
                    if (poly[122].contains(lat, lng)) {
                        return 161;
                    }
                    return 162;
                } else if (poly[123].contains(lat, lng)) {
                    return 4;
                } else {
                    if (poly[124].contains(lat, lng)) {
                        return 62;
                    }
                    if (poly[125].contains(lat, lng)) {
                        return 114;
                    }
                    if (poly[126].contains(lat, lng)) {
                        return 161;
                    }
                    return 86;
                }
            } else if (lat < 29.019276f) {
                if (poly[127].contains(lat, lng)) {
                    return 62;
                }
                if (poly[128].contains(lat, lng)) {
                    return 75;
                }
                if (poly[129].contains(lat, lng)) {
                    return 149;
                }
                if (poly[130].contains(lat, lng)) {
                    return 155;
                }
                return 86;
            } else if (poly[131].contains(lat, lng)) {
                return 75;
            } else {
                if (poly[132].contains(lat, lng)) {
                    return 86;
                }
                if (poly[133].contains(lat, lng)) {
                    return 114;
                }
                if (poly[134].contains(lat, lng)) {
                    return 149;
                }
                return 62;
            }
        } else if (lat < 26.857126f) {
            if (!poly[135].contains(lat, lng) && !poly[136].contains(lat, lng) && !poly[137].contains(lat, lng) && !poly[138].contains(lat, lng)) {
                return poly[139].contains(lat, lng) ? 155 : 149;
            }
            return 75;
        } else if (lng >= -99.298256f || poly[140].contains(lat, lng)) {
            return 75;
        } else {
            return poly[141].contains(lat, lng) ? 155 : 149;
        }
    }

    private static int call12(float lat, float lng) {
        if (lng < -111.42113f) {
            if (lat < 26.733488f) {
                if (lng >= -154.80994f) {
                    return 162;
                }
                if (lat < 16.741133f) {
                    return 1;
                }
                return 107;
            } else if (lat < 27.946997f) {
                return 162;
            } else {
                if (lat < 28.73526f) {
                    if (lng < -115.14983f) {
                        if (lng < -177.31932f) {
                            return 91;
                        }
                        return 1;
                    } else if (lng >= -112.753685f) {
                        return 161;
                    } else {
                        if (lat >= 28.002499f || poly[142].contains(lat, lng)) {
                            return 1;
                        }
                        return 162;
                    }
                } else if (lat < 29.586538f) {
                    if (lng < -113.02877f) {
                        return 1;
                    }
                    return 161;
                } else if (lng < -116.79796f) {
                    return 89;
                } else {
                    if (lat < 29.996002f) {
                        if (lng < -114.01334f) {
                            return 1;
                        }
                        return 161;
                    } else if (poly[143].contains(lat, lng)) {
                        return 4;
                    } else {
                        if (poly[144].contains(lat, lng)) {
                            return 89;
                        }
                        if (poly[145].contains(lat, lng)) {
                            return 161;
                        }
                        return 1;
                    }
                }
            }
        } else if (lat < 25.260832f) {
            if (lng < -108.08908f) {
                return 162;
            }
            return call10(lat, lng);
        } else if (lng < -109.92352f) {
            if (lat < 26.371784f) {
                return 162;
            }
            return (lat >= 27.986944f && poly[146].contains(lat, lng)) ? 4 : 161;
        } else if (lat >= 25.528921f) {
            return call11(lat, lng);
        } else {
            if (lng < -108.801315f) {
                return 162;
            }
            if (lng >= -107.10744f || poly[147].contains(lat, lng)) {
                return 155;
            }
            return 162;
        }
    }

    private static int call13(float lat, float lng) {
        if (lat < 37.00426f) {
            if (lng < -114.04724f) {
                if (lng < -118.29917f) {
                    return 17;
                }
                if (poly[148].contains(lat, lng)) {
                    return 4;
                }
                if (poly[149].contains(lat, lng)) {
                    return 89;
                }
                return 17;
            } else if (lng < -111.08237f) {
                if (!poly[150].contains(lat, lng) && !poly[151].contains(lat, lng) && !poly[152].contains(lat, lng)) {
                    return 4;
                }
                return 114;
            } else if (lat < 35.1676f) {
                if (poly[153].contains(lat, lng)) {
                    return 114;
                }
                return 4;
            } else if (!poly[154].contains(lat, lng) && !poly[155].contains(lat, lng)) {
                return 114;
            } else {
                return 4;
            }
        } else if (lat < 46.03821f) {
            if (poly[156].contains(lat, lng)) {
                return 17;
            }
            if (!poly[157].contains(lat, lng) && !poly[158].contains(lat, lng) && !poly[159].contains(lat, lng)) {
                return 10;
            }
            return 114;
        } else if (lng < -114.30163f) {
            if (lat < 48.510468f) {
                return poly[160].contains(lat, lng) ? 114 : 17;
            }
            if (poly[161].contains(lat, lng)) {
                return 17;
            }
            if (poly[162].contains(lat, lng)) {
                return 19;
            }
            if (poly[163].contains(lat, lng)) {
                return 110;
            }
            if (poly[164].contains(lat, lng)) {
                return 114;
            }
            return 99;
        } else if (poly[165].contains(lat, lng)) {
            return 114;
        } else {
            return 99;
        }
    }

    private static int call14(float lat, float lng) {
        if (lat < 47.5753f) {
            if (lng < -103.43427f) {
                if (lng >= -109.04576f) {
                    return poly[168].contains(lat, lng) ? 75 : 114;
                }
                if (lat < 35.165997f) {
                    if (poly[166].contains(lat, lng)) {
                        return 114;
                    }
                    return 4;
                } else if (poly[167].contains(lat, lng)) {
                    return 4;
                } else {
                    return 114;
                }
            } else if (lng < -102.4196f) {
                return lat < 36.99899f ? lng < -103.00087f ? poly[169].contains(lat, lng) ? 75 : 114 : poly[170].contains(lat, lng) ? 114 : 75 : poly[171].contains(lat, lng) ? 75 : 114;
            } else {
                if (lat < 40.042465f) {
                    return poly[172].contains(lat, lng) ? 114 : 75;
                }
                if (poly[173].contains(lat, lng)) {
                    return 21;
                }
                if (poly[174].contains(lat, lng)) {
                    return 30;
                }
                if (poly[175].contains(lat, lng)) {
                    return 46;
                }
                return poly[176].contains(lat, lng) ? 114 : 75;
            }
        } else if (lng < -103.61013f) {
            if (lat < 48.99935f) {
                if (poly[177].contains(lat, lng)) {
                    return 75;
                }
                if (poly[178].contains(lat, lng)) {
                    return 124;
                }
                return 114;
            } else if (poly[179].contains(lat, lng)) {
                return 32;
            } else {
                return 124;
            }
        } else if (lat < 47.67401f) {
            return lng < -103.03053f ? poly[180].contains(lat, lng) ? 114 : 75 : lng < -102.69964f ? poly[181].contains(lat, lng) ? 75 : 114 : poly[182].contains(lat, lng) ? 114 : 75;
        } else {
            if (lat < 48.992775f) {
                if (poly[183].contains(lat, lng)) {
                    return 124;
                }
                return 75;
            } else if (poly[184].contains(lat, lng)) {
                return 117;
            } else {
                return 124;
            }
        }
    }

    private static int call15(float lat, float lng) {
        if (lng < -122.74365f) {
            if (lat >= 49.010925f) {
                return 19;
            }
            if (lat < 48.307865f) {
                return 17;
            }
            if (lng < -123.16302f) {
                if (lat < 48.6906f) {
                    return lng < -123.263084f ? (lng >= -124.40343f || lat >= 48.456745f) ? 19 : 17 : lat < 48.42922f ? 19 : 17;
                }
                return 19;
            } else if (lat < 48.72209f) {
                return 17;
            } else {
                return lng < -123.022484f ? (lat >= 48.81667f && !poly[185].contains(lat, lng)) ? 17 : 19 : (lat >= 48.98873f && poly[186].contains(lat, lng)) ? 19 : 17;
            }
        } else if (lng < -118.60326f) {
            if (lat < 48.74488f) {
                return 17;
            }
            return (lat >= 49.002666f || poly[187].contains(lat, lng)) ? 19 : 17;
        } else if (lng < -100.26087f) {
            if (lng < -110.0f) {
                return call13(lat, lng);
            }
            return call14(lat, lng);
        } else if (lat < 46.977013f) {
            if (lng >= -90.43818f && !poly[188].contains(lat, lng)) {
                return 40;
            }
            return 75;
        } else if (lat < 47.07519f) {
            return 75;
        } else {
            if (poly[189].contains(lat, lng)) {
                return 98;
            }
            if (poly[190].contains(lat, lng)) {
                return 102;
            }
            if (poly[191].contains(lat, lng)) {
                return 117;
            }
            if (poly[192].contains(lat, lng)) {
                return 151;
            }
            return 75;
        }
    }

    private static int call16(float lat, float lng) {
        if (lat < 36.99782f) {
            if (lng < -85.625534f) {
                return 75;
            }
            if (lat < 29.760227f || lng >= -84.57584f) {
                return 76;
            }
            if (lng < -85.3765f) {
                if (lat < 29.861256f) {
                    return 76;
                }
                return lat < 30.028301f ? poly[193].contains(lat, lng) ? 76 : 75 : (lat >= 30.310158f && poly[194].contains(lat, lng)) ? 76 : 75;
            } else if (lat < 33.82744f) {
                if (lng < -84.85841f) {
                    return lat < 30.699812f ? poly[195].contains(lat, lng) ? 75 : 76 : poly[196].contains(lat, lng) ? 76 : 75;
                }
                return 76;
            } else if (poly[197].contains(lat, lng)) {
                return 49;
            } else {
                return poly[198].contains(lat, lng) ? 75 : 76;
            }
        } else if (lat < 38.929596f) {
            if (lng < -87.07226f) {
                if (poly[199].contains(lat, lng)) {
                    return 22;
                }
                if (poly[200].contains(lat, lng)) {
                    return 37;
                }
                if (poly[201].contains(lat, lng)) {
                    return 125;
                }
                return 75;
            } else if (poly[202].contains(lat, lng)) {
                return 37;
            } else {
                if (poly[203].contains(lat, lng)) {
                    return 38;
                }
                if (poly[204].contains(lat, lng)) {
                    return 60;
                }
                if (poly[205].contains(lat, lng)) {
                    return 75;
                }
                if (poly[206].contains(lat, lng)) {
                    return 125;
                }
                if (poly[207].contains(lat, lng)) {
                    return HwHiCureDetection.CHINA_MCC;
                }
                if (poly[208].contains(lat, lng)) {
                    return 158;
                }
                return 76;
            }
        } else if (lng < -87.69708f) {
            return 75;
        } else {
            if (lng < -86.46635f) {
                if (lat < 41.760456f) {
                    if (poly[209].contains(lat, lng)) {
                        return 37;
                    }
                    if (poly[210].contains(lat, lng)) {
                        return 109;
                    }
                    if (poly[211].contains(lat, lng)) {
                        return 111;
                    }
                    return 75;
                } else if (lng < -87.55037f) {
                    return 75;
                } else {
                    return 39;
                }
            } else if (poly[212].contains(lat, lng)) {
                return 37;
            } else {
                if (poly[213].contains(lat, lng)) {
                    return 76;
                }
                return 39;
            }
        }
    }

    private static int call17(float lat, float lng) {
        if (lng < -85.38794f) {
            if (lng >= -86.80018f) {
                return lat < 46.70353f ? 39 : 102;
            }
            if (lat >= 46.767605f) {
                return 39;
            }
            if (lng < -86.9722f) {
                if (lng < -87.65212f) {
                    if (lat < 45.24034f) {
                        if (!poly[214].contains(lat, lng) && !poly[215].contains(lat, lng) && !poly[216].contains(lat, lng)) {
                            return 75;
                        }
                        return 40;
                    } else if (poly[217].contains(lat, lng)) {
                        return 39;
                    } else {
                        if (poly[218].contains(lat, lng)) {
                            return 75;
                        }
                        return 40;
                    }
                } else if (lat < 44.892063f) {
                    return 75;
                } else {
                    if (lng < -87.580826f) {
                        if (lat < 45.177055f) {
                            if (poly[219].contains(lat, lng)) {
                                return 39;
                            }
                            if (poly[220].contains(lat, lng)) {
                                return 40;
                            }
                            return 75;
                        } else if (poly[221].contains(lat, lng)) {
                            return 39;
                        } else {
                            return 40;
                        }
                    } else if (poly[222].contains(lat, lng)) {
                        return 40;
                    } else {
                        if (!poly[223].contains(lat, lng) && !poly[224].contains(lat, lng)) {
                            return 39;
                        }
                        return 75;
                    }
                }
            } else if (lat < 45.4248f) {
                return 75;
            } else {
                return 39;
            }
        } else if (lat < 45.814972f) {
            return (lng >= -83.18218f && lat >= 44.073376f) ? 102 : 39;
        } else {
            if (lng < -84.55734f) {
                if (lat < 45.97387f) {
                    return 39;
                }
                if (lat < 46.770527f) {
                    return lng < -84.706245f ? poly[225].contains(lat, lng) ? 102 : 39 : lat < 46.480656f ? 39 : 102;
                }
                return 102;
            } else if (lng >= -83.43291f) {
                return 102;
            } else {
                if (lat < 45.998745f) {
                    return (lng >= -84.25634f && poly[226].contains(lat, lng)) ? 102 : 39;
                }
                if (lat < 46.518467f) {
                    return lng < -84.10887f ? poly[227].contains(lat, lng) ? 102 : 39 : poly[228].contains(lat, lng) ? 102 : 39;
                }
                return 102;
            }
        }
    }

    private static int call18(float lat, float lng) {
        if (lat < 43.835323f) {
            if (lng < -87.75389f) {
                return 75;
            }
            if (lng < -84.33076f) {
                return call16(lat, lng);
            }
            if (lat < 41.697075f) {
                return 76;
            }
            if (lng < -83.17306f) {
                if (lat >= 41.73395f) {
                    return 39;
                }
                if (lng >= -83.47369f || poly[229].contains(lat, lng)) {
                    return 76;
                }
                return 39;
            } else if (lng < -82.40782f) {
                if (lat >= 41.829945f) {
                    return poly[230].contains(lat, lng) ? 39 : 102;
                }
                if (lng < -82.74297f) {
                    return 76;
                }
                return 102;
            } else if (lat >= 42.322132f || lng < -81.81831f) {
                return 102;
            } else {
                return 76;
            }
        } else if (lng >= -81.83102f) {
            return 102;
        } else {
            if (lat < 47.81607f) {
                return call17(lat, lng);
            }
            if (lng >= -88.23448f) {
                return 102;
            }
            if (lat < 48.269608f) {
                if (lng >= -89.03518f) {
                    return 39;
                }
                if (lat >= 48.020103f) {
                    return 102;
                }
                if (lng < -89.50582f) {
                    return lng < -89.75503f ? poly[231].contains(lat, lng) ? 102 : 75 : poly[232].contains(lat, lng) ? 102 : 75;
                }
                return 39;
            } else if (lng < -88.56342f) {
                if (lng >= -89.15141f || poly[233].contains(lat, lng)) {
                    return 102;
                }
                return 143;
            } else if (lat >= 48.616455f && poly[234].contains(lat, lng)) {
                return 138;
            } else {
                return 102;
            }
        }
    }

    private static int call19(float lat, float lng) {
        if (lng < -74.32299f) {
            if (lat < 44.19856f) {
                if (lng < -76.605095f) {
                    if (lat >= 43.415874f || poly[235].contains(lat, lng)) {
                        return 102;
                    }
                    return 76;
                } else if (lat < 44.056385f) {
                    return 76;
                } else {
                    if (lng < -76.49867f) {
                        return 102;
                    }
                    if (lng >= -76.3285f || poly[236].contains(lat, lng)) {
                        return 76;
                    }
                    return 102;
                }
            } else if (lat < 44.36635f) {
                if (lng < -76.20735f) {
                    return 102;
                }
                if (lat < 44.236736f) {
                    if (poly[237].contains(lat, lng)) {
                        return 102;
                    }
                    return 76;
                } else if (!poly[238].contains(lat, lng) && !poly[239].contains(lat, lng)) {
                    return 76;
                } else {
                    return 102;
                }
            } else if (lat < 45.18945f) {
                if (lng < -75.91214f) {
                    return 102;
                }
                if (poly[240].contains(lat, lng)) {
                    return 1;
                }
                if (!poly[241].contains(lat, lng) && !poly[242].contains(lat, lng)) {
                    return 76;
                }
                return 102;
            } else if (poly[243].contains(lat, lng)) {
                return 1;
            } else {
                return 102;
            }
        } else if (lat < 43.951973f) {
            return 76;
        } else {
            if (lat >= 47.327045f) {
                return 1;
            }
            if (lng >= -70.71763f) {
                return lng < -70.5789f ? (lat >= 45.624844f || poly[246].contains(lat, lng)) ? 1 : 76 : poly[247].contains(lat, lng) ? 76 : 1;
            }
            if (lat < 45.418625f) {
                return lng < -70.79979f ? poly[244].contains(lat, lng) ? 1 : 76 : poly[245].contains(lat, lng) ? 1 : 76;
            }
            return 1;
        }
    }

    private static int call20(float lat, float lng) {
        if (lat < 45.83255f) {
            if (lat < 44.419464f) {
                return 54;
            }
            if (lng < -66.82084f) {
                if (lat < 44.747192f) {
                    if (lng < -67.108826f) {
                        return 76;
                    }
                    return 33;
                } else if (lng >= -66.95481f) {
                    return 33;
                } else {
                    if (lat < 45.026657f) {
                        if (lat < 44.89772f) {
                            if (poly[248].contains(lat, lng)) {
                                return 33;
                            }
                            return 76;
                        } else if (lng >= -67.00247f || poly[249].contains(lat, lng)) {
                            return 33;
                        } else {
                            return 76;
                        }
                    } else if (lng >= -67.075386f || !poly[250].contains(lat, lng)) {
                        return 33;
                    } else {
                        return 76;
                    }
                }
            } else if (lng < -66.69622f) {
                return 33;
            } else {
                if (lng < -64.388885f) {
                    return (!poly[251].contains(lat, lng) && !poly[252].contains(lat, lng)) ? 54 : 33;
                }
                return 54;
            }
        } else if (lat >= 48.1135f) {
            return 1;
        } else {
            if (lat < 47.132732f) {
                if (lng < -64.534706f) {
                    return 33;
                }
                if (lat >= 46.338512f || lng >= -63.777946f) {
                    return 54;
                }
                return lat < 46.234543f ? (lat >= 46.00659f || !poly[253].contains(lat, lng)) ? 33 : 54 : lng < -64.16715f ? 33 : 54;
            } else if (lng >= -65.021416f || lat < 47.23499f) {
                return 33;
            } else {
                if (lng < -66.47475f) {
                    if (!poly[254].contains(lat, lng) && !poly[255].contains(lat, lng)) {
                        return 1;
                    }
                    return 33;
                } else if (lat < 48.0f) {
                    return 33;
                } else {
                    if (lng >= -66.30693f || poly[256].contains(lat, lng)) {
                        return 1;
                    }
                    return 33;
                }
            }
        }
    }

    private static int call21(float lat, float lng) {
        if (lat < 49.67717f) {
            if (lat < 47.0482f) {
                if (lng < -61.416565f) {
                    return 54;
                }
                if (lng >= -60.24838f || poly[257].contains(lat, lng) || poly[258].contains(lat, lng)) {
                    return 35;
                }
                return 54;
            } else if (lat < 47.562107f) {
                return 54;
            } else {
                if (lng >= -61.394016f) {
                    return 119;
                }
                if (lng < -61.671837f) {
                    return 1;
                }
                return 54;
            }
        } else if (lng >= -59.317802f) {
            return 106;
        } else {
            if (lng < -59.88368f) {
                if (lng < -60.513313f) {
                    if (lng < -61.582493f) {
                        if (!poly[259].contains(lat, lng) && !poly[260].contains(lat, lng)) {
                            return 1;
                        }
                        return 106;
                    } else if (poly[261].contains(lat, lng)) {
                        return 1;
                    } else {
                        return 106;
                    }
                } else if (lat >= 50.269417f && poly[262].contains(lat, lng)) {
                    return 1;
                } else {
                    return 106;
                }
            } else if (lat < 50.50939f) {
                return 106;
            } else {
                if (lng < -59.396664f) {
                    if (lng < -59.591976f) {
                        if (poly[263].contains(lat, lng)) {
                            return 1;
                        }
                        return 106;
                    } else if (lat >= 50.581497f && poly[264].contains(lat, lng)) {
                        return 1;
                    } else {
                        return 106;
                    }
                } else if (lat >= 50.639603f && poly[265].contains(lat, lng)) {
                    return 1;
                } else {
                    return 106;
                }
            }
        }
    }

    private static int call22(float lat, float lng) {
        if (lng < -6.182694f) {
            if (lng < -25.015833f) {
                return 116;
            }
            if (lat >= 42.145638f) {
                return 137;
            }
            if (lng < -8.984417f) {
                return 24;
            }
            if (lat < 36.930965f) {
                return 137;
            }
            if (lng < -8.676639f) {
                if (lat >= 40.990673f && poly[266].contains(lat, lng)) {
                    return 137;
                }
                return 24;
            } else if (lng < -7.869916f) {
                if (lat >= 37.001835f && poly[267].contains(lat, lng)) {
                    return 137;
                }
                return 24;
            } else if (!poly[268].contains(lat, lng) && !poly[269].contains(lat, lng) && !poly[270].contains(lat, lng)) {
                return 137;
            } else {
                return 24;
            }
        } else if (lat < 39.11889f) {
            if (lat >= 36.926167f) {
                return 137;
            }
            if (lng >= -1.915801f) {
                return 1;
            }
            if (lng >= -5.339639f || !poly[271].contains(lat, lng)) {
                return 137;
            }
            return 1;
        } else if (lat < 40.09722f) {
            return 137;
        } else {
            if (lat >= 43.66111f) {
                return 1;
            }
            if (lng < 1.780389f) {
                if (!poly[272].contains(lat, lng) && !poly[273].contains(lat, lng) && !poly[274].contains(lat, lng)) {
                    return 137;
                }
                return 1;
            } else if (lat >= 42.497776f || poly[275].contains(lat, lng) || poly[276].contains(lat, lng)) {
                return 1;
            } else {
                return 137;
            }
        }
    }

    private static int call23(float lat, float lng) {
        if (lng < -89.902f) {
            if (lat < 32.50963f) {
                if (lng < -97.47694f) {
                    return call12(lat, lng);
                }
                if (lat >= 26.368143f) {
                    return 75;
                }
                if (lng < -96.04899f) {
                    if (lat < 21.696547f) {
                        return 88;
                    }
                    if (lat >= 25.953009f) {
                        return 75;
                    }
                    if (lat < 25.643694f) {
                        if (poly[277].contains(lat, lng)) {
                            return 149;
                        }
                        return 155;
                    } else if (poly[278].contains(lat, lng)) {
                        return 75;
                    } else {
                        return 149;
                    }
                } else if (lng < -92.93702f) {
                    return 88;
                } else {
                    if (lat >= 18.647652f) {
                        return 12;
                    }
                    if (lat < 14.019691f) {
                        return 1;
                    }
                    if (lat < 17.8174f) {
                        if (poly[279].contains(lat, lng)) {
                            return 1;
                        }
                        return 88;
                    } else if (poly[280].contains(lat, lng)) {
                        return 88;
                    } else {
                        return 12;
                    }
                }
            } else if (lng < -124.752014f) {
                return 19;
            } else {
                return call15(lat, lng);
            }
        } else if (lat >= 28.805384f) {
            return call18(lat, lng);
        } else {
            if (lat >= 24.0f) {
                return 76;
            }
            if (lng >= -86.638756f || lat < 17.8174f) {
                return 1;
            }
            if (lng >= -87.53919f) {
                return 160;
            }
            if (lng < -88.06724f) {
                if (lng < -89.63324f) {
                    return 12;
                }
                if (lat < 18.511965f) {
                    if (lng >= -88.08086f) {
                        return 160;
                    }
                    if (lng < -89.42593f) {
                        if (poly[281].contains(lat, lng)) {
                            return 160;
                        }
                        return 12;
                    } else if (poly[282].contains(lat, lng)) {
                        return 160;
                    } else {
                        return 1;
                    }
                } else if (poly[283].contains(lat, lng)) {
                    return 12;
                } else {
                    return 160;
                }
            } else if (lat < 18.167719f) {
                return 1;
            } else {
                if (lat >= 19.379875f && poly[284].contains(lat, lng)) {
                    return 12;
                }
                return 160;
            }
        }
    }

    private static int call24(float lat, float lng) {
        if (lat < 35.937668f) {
            if (lng < -76.953766f) {
                return lat < 32.860725f ? 1 : 76;
            }
            if (lat < 19.850515f) {
                return 1;
            }
            if (lng < -74.78429f) {
                if (lat >= 23.893375f) {
                    return lat < 25.563457f ? 1 : 76;
                }
                if (lat >= 19.97403f || lng < -75.87307f) {
                    return 1;
                }
                return lng < -75.149796f ? poly[285].contains(lat, lng) ? 1 : 76 : poly[286].contains(lat, lng) ? 1 : 76;
            } else if (lat < 27.298073f) {
                return 1;
            } else {
                if (lng < -16.118988f) {
                    if (lat >= 32.393833f) {
                        return 28;
                    }
                    if (lng < -64.65199f) {
                        return 1;
                    }
                    return 113;
                } else if (lng < -13.417682f) {
                    return lng < -15.85775f ? 28 : 113;
                } else {
                    if (lng < -8.670276f || lng >= -2.926722f || lat < 32.02278f) {
                        return 1;
                    }
                    if (lng < -5.275472f) {
                        if (poly[287].contains(lat, lng)) {
                            return 128;
                        }
                        return 1;
                    } else if (poly[288].contains(lat, lng)) {
                        return 128;
                    } else {
                        return 1;
                    }
                }
            }
        } else if (lng < -62.785923f) {
            if (lat < 42.32393f) {
                return 76;
            }
            if (lng >= -67.64821f) {
                return call20(lat, lng);
            }
            if (lng < -69.35749f) {
                return call19(lat, lng);
            }
            if (lat < 44.558117f) {
                return 76;
            }
            if (lat >= 47.999695f || poly[289].contains(lat, lng)) {
                return 1;
            }
            if (poly[290].contains(lat, lng)) {
                return 33;
            }
            return 76;
        } else if (lng < -54.914135f) {
            if (lng < -58.942024f) {
                if (lat < 45.63788f) {
                    return 54;
                }
                return call21(lat, lng);
            } else if (lng >= -56.124744f) {
                return 119;
            } else {
                if (lng < -57.341618f) {
                    if (lat < 50.723553f) {
                        return 119;
                    }
                    return 106;
                } else if (lat < 47.146286f) {
                    return 1;
                } else {
                    return 119;
                }
            }
        } else if (lng < -31.26575f) {
            return 119;
        } else {
            if (lat < 44.691193f) {
                return call22(lat, lng);
            }
            return 1;
        }
    }

    private static int call25(float lat, float lng) {
        if (lat < -44.961937f) {
            if (lat >= -52.620758f) {
                return call0(lat, lng);
            }
            if (lat < -55.979496f) {
                return 1;
            }
            if (lng < -68.626526f || lat < -55.124863f) {
                return 11;
            }
            if (lng >= -66.787865f) {
                return 1;
            }
            if (lat < -54.995926f) {
                return 11;
            }
            if (lng < -68.37514f) {
                return (lat >= -54.922653f && !poly[291].contains(lat, lng)) ? 1 : 11;
            }
            if (lat >= -54.896095f) {
                return 1;
            }
            if (lng < -68.262054f) {
                return 11;
            }
            return lng < -67.417274f ? poly[292].contains(lat, lng) ? 1 : 11 : poly[293].contains(lat, lng) ? 1 : 11;
        } else if (lat < -12.124428f) {
            if (lng >= -80.111374f) {
                return call3(lat, lng);
            }
            if (lng < -146.43999f) {
                if (lng >= -154.64058f) {
                    return 127;
                }
                if (lng < -178.73463f) {
                    return 1;
                }
                if (lng < -174.3733f) {
                    if (lat >= -29.241096f) {
                        return 1;
                    }
                    if (lng < -177.88065f) {
                        return 150;
                    }
                    return 25;
                } else if (lng < -157.31213f) {
                    return 1;
                } else {
                    return 127;
                }
            } else if (lng < -137.07198f) {
                return 127;
            } else {
                if (lng < -135.51184f) {
                    if (lat < -21.293276f) {
                        return HwServiceStateTrackerEx.EVENT_DELAY_RAT_CHANGED;
                    }
                    return 127;
                } else if (lng < -134.87834f) {
                    return HwServiceStateTrackerEx.EVENT_DELAY_RAT_CHANGED;
                } else {
                    if (lng < -124.77285f) {
                        return 1;
                    }
                    if (lat < -30.451662f) {
                        return 11;
                    }
                    return 112;
                }
            }
        } else if (lat >= 5.499074f) {
            return 1;
        } else {
            if (lng >= -49.909996f) {
                return call9(lat, lng);
            }
            if (lng < -79.047226f) {
                return call7(lat, lng);
            }
            if (lng < -51.38282f) {
                return call6(lat, lng);
            }
            if (lat >= -1.844347f || lat >= -9.126894f) {
                return 159;
            }
            if (poly[294].contains(lat, lng)) {
                return 145;
            }
            if (poly[295].contains(lat, lng)) {
                return 159;
            }
            return 27;
        }
    }

    private static int call26(float lat, float lng) {
        if (lat < 58.54154f) {
            if (lat >= 58.01058f || lng >= -135.3706f) {
                return 144;
            }
            if (lng < -135.90253f) {
                if (poly[296].contains(lat, lng)) {
                    return 7;
                }
                return 144;
            } else if (poly[297].contains(lat, lng)) {
                return 144;
            } else {
                return 7;
            }
        } else if (lat < 58.77521f) {
            return 144;
        } else {
            if (lng < -139.22838f) {
                if (lng < -146.29626f) {
                    return 147;
                }
                return 42;
            } else if (lng < -136.47536f) {
                if (lng < -137.49272f) {
                    if (lat < 58.900513f) {
                        if (poly[298].contains(lat, lng)) {
                            return 144;
                        }
                        return 42;
                    } else if (poly[299].contains(lat, lng)) {
                        return 19;
                    } else {
                        return 42;
                    }
                } else if (lat >= 58.897247f && lat >= 58.90422f && poly[300].contains(lat, lng)) {
                    return 19;
                } else {
                    return 144;
                }
            } else if (lng < -135.84451f) {
                if (lat >= 58.905876f && poly[301].contains(lat, lng)) {
                    return 19;
                }
                return 144;
            } else if (lng >= -135.28467f && lng >= -135.25185f && poly[302].contains(lat, lng)) {
                return 19;
            } else {
                return 144;
            }
        }
    }

    private static int call27(float lat, float lng) {
        if (lng < -133.17166f) {
            if (lat < 57.121727f) {
                return 7;
            }
            return lat < 57.456635f ? (lat >= 57.2359f || !poly[303].contains(lat, lng)) ? 144 : 7 : (lat >= 58.059586f && poly[304].contains(lat, lng)) ? 19 : 144;
        } else if (lng < -132.38786f) {
            if (lat < 57.022583f) {
                return 7;
            }
            return lat < 57.34693f ? (!poly[305].contains(lat, lng) && !poly[306].contains(lat, lng)) ? 7 : 144 : poly[307].contains(lat, lng) ? 19 : 144;
        } else if (lat < 56.62438f) {
            return (lng >= -132.03267f && poly[308].contains(lat, lng)) ? 19 : 7;
        } else {
            if (lat < 56.70606f) {
                return (lng >= -132.1326f && poly[309].contains(lat, lng)) ? 19 : 7;
            }
            if (poly[310].contains(lat, lng)) {
                return 19;
            }
            return poly[311].contains(lat, lng) ? 144 : 7;
        }
    }

    private static int call28(float lat, float lng) {
        if (lng < -128.8281f) {
            if (lat < 54.69715f || lng >= -129.98987f) {
                return 19;
            }
            if (lat < 54.969955f) {
                if (lng >= -130.28795f) {
                    return 19;
                }
                if (lat < 54.759205f) {
                    if (lng < -130.63234f) {
                        return 7;
                    }
                    return 19;
                } else if (lat >= 54.957565f || lng < -130.63f) {
                    return 7;
                } else {
                    if (!poly[312].contains(lat, lng) && !poly[313].contains(lat, lng)) {
                        return 7;
                    }
                    return 19;
                }
            } else if (lat < 55.25092f) {
                if (lng < -130.56406f) {
                    return 7;
                }
                if (lng >= -130.05516f) {
                    return 19;
                }
                if (lng < -130.14769f) {
                    if (poly[314].contains(lat, lng)) {
                        return 19;
                    }
                    return 7;
                } else if (poly[315].contains(lat, lng)) {
                    return 19;
                } else {
                    return 7;
                }
            } else if (lat < 55.914852f) {
                if (lng >= -130.88818f && poly[316].contains(lat, lng)) {
                    return 19;
                }
                return 7;
            } else if (poly[317].contains(lat, lng)) {
                return 7;
            } else {
                return 19;
            }
        } else if (lng < -126.91517f || lng < -126.120544f) {
            return 19;
        } else {
            if (lng < -117.1345f) {
                if (lat < 51.0178f) {
                    if (lng >= -124.911514f && poly[318].contains(lat, lng)) {
                        return 99;
                    }
                    return 19;
                } else if (lng < -121.62752f) {
                    if (poly[319].contains(lat, lng)) {
                        return 53;
                    }
                    return 19;
                } else if (poly[320].contains(lat, lng)) {
                    return 53;
                } else {
                    if (poly[321].contains(lat, lng)) {
                        return 99;
                    }
                    return 19;
                }
            } else if (poly[322].contains(lat, lng)) {
                return 124;
            } else {
                return 99;
            }
        }
    }

    private static int call29(float lat, float lng) {
        if (lng < -134.4836f) {
            if (lng < -141.0f) {
                return 147;
            }
            if (lat < 69.20628f) {
                if (lng < -136.52672f) {
                    if (lat < 60.0f) {
                        if (lng >= -139.30002f && poly[323].contains(lat, lng)) {
                            return 19;
                        }
                        return 42;
                    } else if (lng >= -139.05324f) {
                        return 132;
                    } else {
                        if (poly[324].contains(lat, lng)) {
                            return 42;
                        }
                        if (poly[325].contains(lat, lng)) {
                            return 135;
                        }
                        if (poly[326].contains(lat, lng)) {
                            return 147;
                        }
                        return 132;
                    }
                } else if (lat >= 68.89853f) {
                    return 68;
                } else {
                    if (lat >= 60.0f) {
                        return poly[328].contains(lat, lng) ? 132 : 68;
                    }
                    if (poly[327].contains(lat, lng)) {
                        return 144;
                    }
                    return 19;
                }
            } else if (lng >= -138.19891f) {
                return 68;
            } else {
                if (lng >= -140.99222f || poly[329].contains(lat, lng)) {
                    return 132;
                }
                return 147;
            }
        } else if (lat >= 68.41698f) {
            return 68;
        } else {
            if (lat < 60.0f) {
                return 19;
            }
            if (lat < 67.00358f) {
                return lng < -128.75116f ? poly[330].contains(lat, lng) ? 68 : 132 : poly[331].contains(lat, lng) ? 132 : 68;
            }
            if (poly[332].contains(lat, lng)) {
                return 68;
            }
            return 23;
        }
    }

    private static int call30(float lat, float lng) {
        if (lat >= 72.17126f) {
            return (lng >= -109.97257f && lng >= -109.971375f) ? 48 : 68;
        }
        if (lat >= 70.47105f) {
            return lng < -109.971375f ? 68 : 48;
        }
        if (lng < -113.46177f) {
            return lat < 69.17502f ? (lng >= -120.59595f || !poly[333].contains(lat, lng)) ? 48 : 68 : lng < -118.50597f ? poly[334].contains(lat, lng) ? 48 : 68 : (lat >= 69.65307f && !poly[335].contains(lat, lng)) ? 68 : 48;
        }
        if (lat < 68.762314f) {
            return 48;
        }
        return lng < -112.62099f ? (lat >= 70.03414f || poly[336].contains(lat, lng)) ? 68 : 48 : lng < -112.567856f ? (lat >= 69.78516f || poly[337].contains(lat, lng)) ? 68 : 48 : poly[338].contains(lat, lng) ? 48 : 68;
    }

    private static int call31(float lat, float lng) {
        if (lng < -123.018715f) {
            if (lng >= -145.73842f) {
                return call29(lat, lng);
            }
            if (lng >= -162.0f) {
                return 147;
            }
            if (lng >= -169.00237f || lat < 63.787884f) {
                return 63;
            }
            return 146;
        } else if (lat >= 68.37665f) {
            return call30(lat, lng);
        } else {
            if (lng < -109.516396f) {
                if (lat >= 67.99398f) {
                    return (lng >= -120.19185f || !poly[344].contains(lat, lng)) ? 48 : 68;
                }
                if (lat >= 67.8447f) {
                    return (lng >= -120.03421f || !poly[343].contains(lat, lng)) ? 48 : 68;
                }
                if (lat >= 67.77868f) {
                    return (lng >= -119.90586f || poly[342].contains(lat, lng)) ? 48 : 68;
                }
                if (lat >= 60.0f) {
                    return lng < -113.69033f ? poly[340].contains(lat, lng) ? 68 : 48 : (lat >= 65.558174f || poly[341].contains(lat, lng)) ? 48 : 68;
                }
                if (lng < -120.0f) {
                    return 19;
                }
                if (poly[339].contains(lat, lng)) {
                    return 124;
                }
                return 99;
            } else if (lat >= 66.79599f) {
                return 48;
            } else {
                if (lat < 60.0f) {
                    return 124;
                }
                return (lat >= 64.660286f || poly[345].contains(lat, lng)) ? 48 : 68;
            }
        }
    }

    private static int call32(float lat, float lng) {
        if (lat < 61.80579f) {
            if (lat >= 60.0f) {
                return lng < -102.008194f ? 68 : 123;
            }
            if (lng < -93.7694f) {
                if (lng < -101.50901f) {
                    if (poly[346].contains(lat, lng)) {
                        return 124;
                    }
                    return 117;
                } else if (lat < 59.07264f) {
                    if (lng < -94.795906f) {
                        if (poly[347].contains(lat, lng)) {
                            return 123;
                        }
                        return 117;
                    } else if (poly[348].contains(lat, lng)) {
                        return 123;
                    } else {
                        return 117;
                    }
                } else if (lat < 59.34849f) {
                    if (lng >= -94.781494f || !poly[349].contains(lat, lng)) {
                        return 117;
                    }
                    return 123;
                } else if (poly[350].contains(lat, lng)) {
                    return 123;
                } else {
                    return 117;
                }
            } else if (lng < -93.57213f) {
                if (lng < -93.62988f) {
                    if (poly[351].contains(lat, lng)) {
                        return 123;
                    }
                    return 117;
                } else if (poly[352].contains(lat, lng)) {
                    return 123;
                } else {
                    return 117;
                }
            } else if (lng >= -92.85267f) {
                return 117;
            } else {
                if (lng < -93.029205f) {
                    if (poly[353].contains(lat, lng)) {
                        return 123;
                    }
                    return 117;
                } else if (poly[354].contains(lat, lng)) {
                    return 123;
                } else {
                    return 117;
                }
            }
        } else if (lat < 62.165585f) {
            return lng < -102.008194f ? 68 : 123;
        } else {
            if (lng < -93.469185f) {
                if (lat >= 67.0f) {
                    return 48;
                }
                if (lng >= -102.0f) {
                    return 123;
                }
                if (lng < -102.008194f) {
                    if (poly[355].contains(lat, lng)) {
                        return 68;
                    }
                    return 48;
                } else if (poly[356].contains(lat, lng)) {
                    return 48;
                } else {
                    return 123;
                }
            } else if (lat >= 63.97121f && poly[357].contains(lat, lng)) {
                return 48;
            } else {
                return 123;
            }
        }
    }

    private static int call33(float lat, float lng) {
        if (lat < 64.017044f) {
            if (lat >= 63.038517f) {
                return lng < -88.55368f ? 123 : 1;
            }
            if (lat >= 59.697903f) {
                return lng < -90.670746f ? 123 : 1;
            }
            if (lat >= 57.67234f) {
                return 65;
            }
            if (lng < -87.47753f) {
                if (lat < 56.85917f) {
                    if (lng >= -88.5f) {
                        return 102;
                    }
                    if (lat < 53.375f) {
                        if (poly[358].contains(lat, lng)) {
                            return 102;
                        }
                        return 117;
                    } else if (poly[359].contains(lat, lng)) {
                        return 102;
                    } else {
                        if (poly[360].contains(lat, lng)) {
                            return 123;
                        }
                        return 117;
                    }
                } else if (lng >= -92.43536f && poly[361].contains(lat, lng)) {
                    return 123;
                } else {
                    return 117;
                }
            } else if (lng < -82.722824f) {
                if (lng < -82.93801f) {
                    if (poly[362].contains(lat, lng)) {
                        return 65;
                    }
                    return 102;
                } else if (poly[363].contains(lat, lng)) {
                    return 65;
                } else {
                    return 102;
                }
            } else if (lng < -82.24768f || lng < -82.1176f) {
                return 102;
            } else {
                if (lat >= 52.861526f || poly[364].contains(lat, lng)) {
                    return 65;
                }
                return 102;
            }
        } else if (lat < 66.054146f) {
            if (lng < -87.966995f) {
                return 123;
            }
            if (lng >= -84.512566f) {
                return lat < 65.476295f ? 1 : 65;
            }
            if (lng >= -85.978485f) {
                return 1;
            }
            if (lng < -86.90071f) {
                return 123;
            }
            return (lat >= 64.95483f && !poly[365].contains(lat, lng)) ? 123 : 1;
        } else if (lng >= -84.99151f) {
            return 65;
        } else {
            if (lng < -87.16163f) {
                if (lng >= -89.0f) {
                    return 123;
                }
                if (lat >= 67.0f || poly[366].contains(lat, lng)) {
                    return 48;
                }
                return 123;
            } else if (lng < -85.9008f) {
                return 123;
            } else {
                return lat < 66.17272f ? lat < 66.11509f ? 1 : 123 : lng < -85.0f ? 123 : 65;
            }
        }
    }

    private static int call34(float lat, float lng) {
        if (lat < 53.8335f) {
            if (lng < -79.17429f) {
                return 65;
            }
            if (lat >= 53.443897f) {
                return lng < -79.100655f ? (lat >= 53.495567f && lat >= 53.646114f) ? 65 : 1 : lat < 53.54456f ? poly[369].contains(lat, lng) ? 65 : 1 : poly[370].contains(lat, lng) ? 65 : 1;
            }
            if (lng < -79.11523f) {
                return 65;
            }
            return lng < -79.08246f ? lat < 53.434776f ? 1 : 65 : lng < -78.99675f ? (lat >= 53.413036f || !poly[367].contains(lat, lng)) ? 1 : 65 : poly[368].contains(lat, lng) ? 65 : 1;
        } else if (lat < 54.096584f) {
            return (lng >= -79.185135f && lng >= -79.17493f && !poly[371].contains(lat, lng)) ? 1 : 65;
        } else {
            if (lng >= -79.23705f) {
                return lng < -79.17379f ? lat < 54.133778f ? 65 : 1 : poly[373].contains(lat, lng) ? 65 : 1;
            }
            if (lng < -79.43023f) {
                return 65;
            }
            return (lat >= 54.174984f || !poly[372].contains(lat, lng)) ? 1 : 65;
        }
    }

    private static int call35(float lat, float lng) {
        if (lat < 52.447598f) {
            if (lng < -79.18712f) {
                if (lat >= 51.66524f) {
                    return 65;
                }
                if (poly[374].contains(lat, lng)) {
                    return 1;
                }
                return 102;
            } else if (lng >= -78.94168f) {
                return lng < -78.85205f ? lat < 51.935123f ? 1 : 65 : lng < -78.70372f ? lat < 52.046337f ? 1 : 65 : lat < 52.395023f ? poly[375].contains(lat, lng) ? 65 : 1 : lng < -78.55294f ? 1 : 65;
            } else {
                if (lat < 51.80547f) {
                    return (lng >= -79.05113f || lat < 51.591484f) ? 1 : 65;
                }
                return 65;
            }
        } else if (lng < -79.00434f) {
            return 65;
        } else {
            if (lng >= -78.9112f) {
                return lng < -78.84449f ? lat < 52.76288f ? poly[377].contains(lat, lng) ? 1 : 65 : (lat >= 52.940254f && lat >= 52.981068f) ? 65 : 1 : lng < -78.775826f ? (lat >= 52.725773f && lat < 52.77623f && poly[378].contains(lat, lng)) ? 65 : 1 : lng < -78.7536f ? (lat >= 52.66527f || lat < 52.558495f || !poly[379].contains(lat, lng)) ? 1 : 65 : poly[380].contains(lat, lng) ? 65 : 1;
            }
            if (lat < 52.978027f) {
                return (lng >= -78.92035f && lat < 52.942326f) ? 1 : 65;
            }
            if (lat < 53.207043f) {
                return 1;
            }
            return lat < 53.286568f ? lng < -78.96156f ? 1 : 65 : poly[376].contains(lat, lng) ? 1 : 65;
        }
    }

    private static int call36(float lat, float lng) {
        return lat < 58.64431f ? lng < -78.33361f ? lat < 58.579838f ? (lng >= -78.49569f && lat >= 58.508587f) ? 1 : 65 : (lng >= -78.58399f && !poly[381].contains(lat, lng) && !poly[382].contains(lat, lng)) ? 1 : 65 : lat < 58.369335f ? lat < 58.337955f ? poly[383].contains(lat, lng) ? 65 : 1 : poly[384].contains(lat, lng) ? 65 : 1 : lng < -78.293175f ? lat < 58.50188f ? 1 : 65 : lat < 58.41115f ? lng < -78.07861f ? 1 : 65 : poly[385].contains(lat, lng) ? 65 : 1 : lat < 58.82987f ? lng < -78.56252f ? lat < 58.677063f ? 1 : 65 : poly[386].contains(lat, lng) ? 65 : 1 : lng < -78.31001f ? lng < -78.58226f ? lat < 58.931965f ? 1 : 65 : lng < -78.39252f ? poly[387].contains(lat, lng) ? 65 : 1 : poly[388].contains(lat, lng) ? 65 : 1 : lng < -78.15382f ? lat < 59.110462f ? poly[389].contains(lat, lng) ? 65 : 1 : poly[390].contains(lat, lng) ? 1 : 65 : poly[391].contains(lat, lng) ? 65 : 1;
    }

    private static int call37(float lat, float lng) {
        if (lat < 60.825294f) {
            if (lng < -78.25725f) {
                return 65;
            }
            return lat < 60.20689f ? lng < -77.6262f ? lat < 59.716255f ? poly[392].contains(lat, lng) ? 65 : 1 : (lng >= -77.66927f && lat < 60.153416f) ? 1 : 65 : lng < -77.50214f ? (lat >= 59.78048f || lat < 59.70622f || poly[393].contains(lat, lng)) ? 1 : 65 : lat < 59.88076f ? poly[394].contains(lat, lng) ? 65 : 1 : poly[395].contains(lat, lng) ? 65 : 1 : lng < -77.96252f ? (lat >= 60.775932f && !poly[396].contains(lat, lng)) ? 1 : 65 : lat < 60.274155f ? lng < -77.61686f ? 65 : 1 : poly[397].contains(lat, lng) ? 65 : 1;
        } else if (lat >= 62.59398f) {
            return lng < -80.14743f ? 1 : 65;
        } else {
            if (lng >= -77.85999f) {
                return lat < 61.579624f ? lng < -77.80133f ? lat < 61.459908f ? 1 : 65 : lng < -77.75713f ? lat < 61.450546f ? 1 : 65 : poly[400].contains(lat, lng) ? 65 : 1 : lat < 61.632744f ? (lng >= -77.8283f && !poly[401].contains(lat, lng)) ? 1 : 65 : lat < 61.66197f ? lng < -77.79781f ? 1 : 65 : poly[402].contains(lat, lng) ? 65 : 1;
            }
            if (lng < -79.26989f) {
                return 65;
            }
            if (lat < 61.679256f) {
                return lat < 61.34524f ? 1 : 65;
            }
            if (lat < 62.444374f) {
                return lat < 62.40651f ? (!poly[398].contains(lat, lng) && !poly[399].contains(lat, lng)) ? 1 : 65 : lng < -77.98772f ? 1 : 65;
            }
            return 65;
        }
    }

    private static int call38(float lat, float lng) {
        if (lat < 55.36321f) {
            if (lat < 53.39302f) {
                return call35(lat, lng);
            }
            if (lat < 54.185486f) {
                return call34(lat, lng);
            }
            if (lat < 54.73038f) {
                if (lat >= 54.416172f) {
                    return lat < 54.4775f ? (lng >= -79.55187f && !poly[404].contains(lat, lng)) ? 1 : 65 : lat < 54.592674f ? poly[405].contains(lat, lng) ? 65 : 1 : poly[406].contains(lat, lng) ? 65 : 1;
                }
                if (lng < -79.52016f) {
                    return 65;
                }
                return lng < -79.47242f ? lat < 54.19675f ? 65 : 1 : lat < 54.24717f ? lng < -79.39684f ? 1 : 65 : poly[403].contains(lat, lng) ? 65 : 1;
            } else if (lng < -79.557785f) {
                return 65;
            } else {
                return lat < 54.76863f ? poly[407].contains(lat, lng) ? 1 : 65 : (lng >= -79.4412f && !poly[408].contains(lat, lng)) ? 1 : 65;
            }
        } else if (lng < -77.701225f) {
            return 65;
        } else {
            return lat < 56.352753f ? lng < -77.144196f ? lat < 55.65385f ? (lng >= -77.671234f && poly[409].contains(lat, lng)) ? 1 : 65 : lat < 55.72137f ? 1 : 65 : lat < 56.183613f ? poly[410].contains(lat, lng) ? 65 : 1 : lng < -76.670586f ? 65 : 1 : lng < -76.620346f ? 65 : 1;
        }
    }

    private static int call39(float lat, float lng) {
        if (lat < 74.20003f) {
            if (lng < -95.85819f) {
                if (lat < 72.136444f || lng < -101.909744f) {
                    return 48;
                }
                if (lat >= 73.19901f) {
                    return 123;
                }
                if (lng < -100.58557f) {
                    return 48;
                }
                if (lng < -98.208565f) {
                    if (lat < 72.80212f) {
                        return 48;
                    }
                    return (!poly[411].contains(lat, lng) && !poly[412].contains(lat, lng)) ? 48 : 123;
                } else if (lat < 72.869064f) {
                    return lng < -97.04814f ? poly[413].contains(lat, lng) ? 123 : 48 : (lat >= 72.679504f || poly[414].contains(lat, lng)) ? 123 : 48;
                } else {
                    return 123;
                }
            } else if (lng >= -85.0f) {
                return 65;
            } else {
                if (lng >= -91.70013f) {
                    return 123;
                }
                if (lat < 72.09619f) {
                    return 48;
                }
                return (lat >= 72.43683f || poly[415].contains(lat, lng)) ? 123 : 48;
            }
        } else if (lat >= 77.11f) {
            return lng < -93.0817f ? lng < -102.0f ? 48 : 123 : lng < -85.0f ? 123 : 65;
        } else {
            if (lng < -97.779976f) {
                return lng < -102.0f ? 48 : 123;
            }
            if (lng < -96.68568f) {
                return 123;
            }
            if (lng >= -93.3935f) {
                return lng < -85.0f ? 123 : 65;
            }
            if (lat >= 75.642746f) {
                return 123;
            }
            if (lat < 74.74611f) {
                if (lng < -96.132774f) {
                    return 123;
                }
                if (lng >= -95.22935f || lat < 74.69793f) {
                    return 72;
                }
                return 123;
            } else if (lat < 74.84374f) {
                if (lng < -96.12126f) {
                    return 72;
                }
                return 123;
            } else if (!poly[416].contains(lat, lng) && !poly[417].contains(lat, lng)) {
                return 72;
            } else {
                return 123;
            }
        }
    }

    private static int call40(float lat, float lng) {
        if (lat < 68.36039f) {
            if (lng < -80.49455f) {
                if (lng >= -92.63556f) {
                    return call33(lat, lng);
                }
                if (lat < 67.20077f) {
                    return call32(lat, lng);
                }
                return 48;
            } else if (lat < 56.992775f) {
                return call38(lat, lng);
            } else {
                if (lat < 59.490208f) {
                    if (lng < -78.62693f) {
                        return 65;
                    }
                    if (lat >= 58.276955f) {
                        return call36(lat, lng);
                    }
                    if (lng < -78.021866f) {
                        return 65;
                    }
                    if (lng < -77.20554f) {
                        if (lat < 58.16977f) {
                            if (lat < 58.00473f) {
                                return 1;
                            }
                            if (lng < -77.5274f) {
                                if (poly[418].contains(lat, lng)) {
                                    return 1;
                                }
                                return 65;
                            } else if (poly[419].contains(lat, lng)) {
                                return 65;
                            } else {
                                return 1;
                            }
                        } else if (lng < -77.70132f) {
                            if (poly[420].contains(lat, lng)) {
                                return 1;
                            }
                            return 65;
                        } else if (!poly[421].contains(lat, lng) && !poly[422].contains(lat, lng)) {
                            return 1;
                        } else {
                            return 65;
                        }
                    } else if (lat < 57.274315f) {
                        if (lng < -76.65046f) {
                            return 65;
                        }
                        return 1;
                    } else if (lng < -77.010284f) {
                        if (lat < 57.787895f) {
                            return 65;
                        }
                        return 1;
                    } else if (lng < -76.866554f) {
                        if (lat < 57.637794f) {
                            return 1;
                        }
                        return 65;
                    } else if (poly[423].contains(lat, lng)) {
                        return 65;
                    } else {
                        return 1;
                    }
                } else if (lat < 63.892357f) {
                    return call37(lat, lng);
                } else {
                    return 65;
                }
            }
        } else if (lat >= 70.261765f) {
            return call39(lat, lng);
        } else {
            if (lng < -89.0f) {
                return 48;
            }
            if (lng < -85.0f) {
                return 123;
            }
            return 65;
        }
    }

    private static int call41(float lat, float lng) {
        if (lng < -58.273293f) {
            if (lat < 51.170013f) {
                return (lng >= -58.86449f || !poly[424].contains(lat, lng)) ? 106 : 1;
            }
            if (lat >= 51.4571f) {
                return poly[431].contains(lat, lng) ? 1 : 84;
            }
            if (lng < -58.48899f) {
                return lat < 51.21237f ? (lng >= -58.864346f || poly[425].contains(lat, lng)) ? 106 : 1 : lng < -58.641468f ? (!poly[426].contains(lat, lng) && !poly[427].contains(lat, lng)) ? 1 : 106 : (lat >= 51.276363f && poly[428].contains(lat, lng)) ? 1 : 106;
            }
            if (lat < 51.296436f) {
                return 106;
            }
            return lng < -58.4325f ? poly[429].contains(lat, lng) ? 1 : 106 : poly[430].contains(lat, lng) ? 1 : 106;
        } else if (lat >= 53.630226f) {
            return 84;
        } else {
            if (lng < -57.608196f) {
                if (lat < 51.43933f) {
                    return 106;
                }
                return lat < 51.589436f ? poly[432].contains(lat, lng) ? 1 : 106 : poly[433].contains(lat, lng) ? 84 : 1;
            } else if (lat < 51.39516f) {
                if (lng < -57.11695f) {
                    return 106;
                }
                return 119;
            } else if (lat < 51.47837f) {
                if (lng < -57.257164f) {
                    return 106;
                }
                if (lng >= -57.096817f || poly[434].contains(lat, lng)) {
                    return 119;
                }
                return 106;
            } else if (poly[435].contains(lat, lng)) {
                return 1;
            } else {
                if (poly[436].contains(lat, lng) || poly[437].contains(lat, lng)) {
                    return 84;
                }
                if (poly[438].contains(lat, lng)) {
                    return 106;
                }
                return 119;
            }
        }
    }

    private static int call42(float lat, float lng) {
        if (lng < -65.50671f) {
            return lat < 59.31384f ? lat < 59.059566f ? (lng >= -65.59926f || !poly[439].contains(lat, lng)) ? 1 : 77 : (lng >= -65.59387f || lat < 59.269848f) ? 1 : 77 : lat < 59.453045f ? (lat >= 59.38555f || lng < -65.61893f || poly[440].contains(lat, lng)) ? 77 : 1 : lng < -65.5219f ? 1 : 77;
        }
        if (lng < -65.03066f) {
            if (lng >= -65.40585f || lat < 59.054058f) {
                return 1;
            }
            return lat < 59.407f ? poly[441].contains(lat, lng) ? 77 : 1 : lat < 59.484493f ? 1 : 77;
        } else if (lng >= -63.419415f) {
            return 84;
        } else {
            if (lat < 56.0f) {
                if (poly[442].contains(lat, lng)) {
                    return 84;
                }
                return 1;
            } else if (poly[443].contains(lat, lng)) {
                return 1;
            } else {
                return 84;
            }
        }
    }

    private static int call43(float lat, float lng) {
        if (lat < 58.782413f) {
            if (lng < -68.97591f) {
                if (lng >= -69.17711f || lng < -69.81845f || lng < -69.782776f) {
                    return 1;
                }
                return lng < -69.67138f ? lat < 58.723164f ? 1 : 65 : lng < -69.26977f ? poly[444].contains(lat, lng) ? 65 : 1 : (lat >= 57.839397f && poly[445].contains(lat, lng)) ? 65 : 1;
            } else if (lng < -67.89165f) {
                if (lng < -68.69297f) {
                    return lat < 57.985935f ? poly[446].contains(lat, lng) ? 65 : 1 : poly[447].contains(lat, lng) ? 65 : 1;
                }
                return 1;
            } else if (lng >= -67.493484f) {
                return lng < -67.26459f ? lat < 58.43532f ? 1 : 77 : poly[450].contains(lat, lng) ? 77 : 1;
            } else {
                if (lng < -67.852905f) {
                    return 1;
                }
                return (!poly[448].contains(lat, lng) && !poly[449].contains(lat, lng)) ? 1 : 77;
            }
        } else if (lat < 58.94076f) {
            return lng < -69.66513f ? (lng >= -69.791435f && lng < -69.700226f) ? 65 : 1 : lng < -68.387146f ? (lng >= -69.454346f && lat >= 58.920303f) ? 65 : 1 : lng < -66.20843f ? 1 : 77;
        } else {
            if (lng < -69.34359f) {
                return lng < -69.39182f ? lng < -69.45677f ? poly[451].contains(lat, lng) ? 65 : 1 : lat < 59.005432f ? 65 : 1 : (lat >= 59.032063f || lat < 58.972084f) ? 1 : 65;
            }
            if (lng >= -69.09537f) {
                return 65;
            }
            if (lng < -69.22791f) {
                return 1;
            }
            return lat < 58.9671f ? lng < -69.1575f ? 1 : 65 : poly[452].contains(lat, lng) ? 65 : 1;
        }
    }

    private static int call44(float lat, float lng) {
        if (lat < 55.947544f) {
            if (lat >= 53.837414f) {
                return (lng >= -60.87566f || lng >= -63.322098f || poly[453].contains(lat, lng)) ? 84 : 1;
            }
            if (lng < -56.2984f) {
                return call41(lat, lng);
            }
            if (lat < 52.71006f) {
                if (lng < -55.273384f) {
                    return 119;
                }
                return 1;
            } else if (lng >= -56.276463f || lat >= 53.6284f) {
                return 119;
            } else {
                return 84;
            }
        } else if (lng >= -63.015766f) {
            return 84;
        } else {
            if (lng < -66.12613f) {
                return call43(lat, lng);
            }
            if (lng >= -65.67117f) {
                return call42(lat, lng);
            }
            if (lat < 59.062336f) {
                if (lng < -65.941536f) {
                    if (lat < 58.867237f) {
                        if (lat >= 58.68844f && poly[454].contains(lat, lng)) {
                            return 77;
                        }
                        return 1;
                    } else if (lat < 58.92864f) {
                        return 1;
                    } else {
                        return 77;
                    }
                } else if (lng < -65.81516f) {
                    if (lat >= 58.98971f) {
                        return 77;
                    }
                    if (lng >= -65.92955f && poly[455].contains(lat, lng)) {
                        return 77;
                    }
                    return 1;
                } else if (lat >= 58.97855f && poly[456].contains(lat, lng)) {
                    return 77;
                } else {
                    return 1;
                }
            } else if (lng < -65.75092f || lat < 59.08266f) {
                return 77;
            } else {
                if (lat >= 59.14643f || poly[457].contains(lat, lng)) {
                    return 1;
                }
                return 77;
            }
        }
    }

    private static int call45(float lat, float lng) {
        if (lat >= 62.520065f) {
            return 65;
        }
        if (lng < -71.48927f) {
            return 1;
        }
        if (lng < -69.876434f) {
            if (lat < 60.913162f) {
                if (lng < -70.02524f) {
                    if (poly[458].contains(lat, lng)) {
                        return 65;
                    }
                    return 1;
                } else if (lat >= 59.984997f || lat < 59.966614f) {
                    return 1;
                } else {
                    return 65;
                }
            } else if (lng >= -70.06512f) {
                return 1;
            } else {
                if (lng >= -70.08544f || poly[459].contains(lat, lng)) {
                    return 65;
                }
                return 1;
            }
        } else if (lng >= -69.36341f) {
            return 65;
        } else {
            if (lat < 59.964027f) {
                if (lng < -69.638916f) {
                    if (poly[460].contains(lat, lng)) {
                        return 65;
                    }
                    return 1;
                } else if (lat < 59.699116f) {
                    if (poly[461].contains(lat, lng)) {
                        return 65;
                    }
                    return 1;
                } else if (poly[462].contains(lat, lng)) {
                    return 65;
                } else {
                    return 1;
                }
            } else if (lng < -69.76391f) {
                if (lat < 60.926014f) {
                    return 1;
                }
                return 65;
            } else if (lat < 60.293987f) {
                if (poly[463].contains(lat, lng)) {
                    return 65;
                }
                return 1;
            } else if (lng >= -69.66224f && lng >= -69.626076f && poly[464].contains(lat, lng)) {
                return 65;
            } else {
                return 1;
            }
        }
    }

    private static int call46(float lat, float lng) {
        if (lng < -63.67711f) {
            if (lng < -68.0f) {
                return call45(lat, lng);
            }
            if (lat >= 60.695583f) {
                return 77;
            }
            if (lat < 60.50253f) {
                if (lng >= -64.35322f) {
                    return 84;
                }
                if (lat < 60.227196f) {
                    if (lng < -65.15936f) {
                        return 1;
                    }
                    if (lng < -64.60494f) {
                        if (lat < 60.1161f) {
                            if (poly[465].contains(lat, lng)) {
                                return 84;
                            }
                            return 1;
                        } else if (lng >= -64.921486f && poly[466].contains(lat, lng)) {
                            return 84;
                        } else {
                            return 1;
                        }
                    } else if (lat >= 59.536037f) {
                        return 84;
                    } else {
                        if (lng < -64.44775f) {
                            if (poly[467].contains(lat, lng)) {
                                return 1;
                            }
                            return 84;
                        } else if (lat < 59.51071f) {
                            if (poly[468].contains(lat, lng)) {
                                return 84;
                            }
                            return 1;
                        } else if (poly[469].contains(lat, lng)) {
                            return 84;
                        } else {
                            return 1;
                        }
                    }
                } else if (lat < 60.372677f) {
                    if (lng < -67.901695f) {
                        return 77;
                    }
                    if (!poly[470].contains(lat, lng) && !poly[471].contains(lat, lng)) {
                        return 84;
                    }
                    return 1;
                } else if (lng < -67.828476f) {
                    return 77;
                } else {
                    return 1;
                }
            } else if (lng < -67.89294f) {
                return 77;
            } else {
                return 1;
            }
        } else if (lng < -22.769167f) {
            return 6;
        } else {
            return 1;
        }
    }

    private static int call47(float lat, float lng) {
        if (lat < 77.4442f) {
            if (lat >= 76.447014f) {
                return (lng >= -22.81963f || poly[477].contains(lat, lng)) ? 8 : 6;
            }
            if (lat < 75.42361f) {
                return 6;
            }
            return lng < -20.74267f ? lat < 76.36901f ? lng < -21.152466f ? lat < 76.3397f ? poly[472].contains(lat, lng) ? 6 : 8 : (lng >= -22.890951f || poly[473].contains(lat, lng)) ? 8 : 6 : (lat >= 75.841736f || poly[474].contains(lat, lng)) ? 8 : 6 : (lng >= -22.885777f || poly[475].contains(lat, lng)) ? 8 : 6 : (lat >= 75.81696f || poly[476].contains(lat, lng)) ? 8 : 6;
        } else if (lat < 78.43155f) {
            return (lng >= -21.049835f || lng >= -22.754137f || poly[478].contains(lat, lng)) ? 8 : 6;
        } else {
            if (lat >= 79.653534f) {
                return 6;
            }
            if (lng < -19.27412f) {
                return lng < -20.5303f ? lng < -22.673079f ? poly[479].contains(lat, lng) ? 8 : 6 : (lat >= 78.77851f && poly[480].contains(lat, lng)) ? 6 : 8 : (lat >= 79.34874f && !poly[481].contains(lat, lng)) ? 6 : 8;
            }
            return 8;
        }
    }

    private static int call48(float lat, float lng) {
        if (lat < 73.48585f) {
            if (lat < 70.35147f) {
                if (lng >= -53.576065f) {
                    return 6;
                }
                if (lng < -68.0f) {
                    return 65;
                }
                return lng < -54.993404f ? 77 : 6;
            } else if (lat >= 72.58957f) {
                return 6;
            } else {
                if (lng < -54.626347f) {
                    return lng < -68.27161f ? 65 : 6;
                }
                if (lng < -24.624891f) {
                    return 6;
                }
                if (lat < 71.16384f) {
                    if (lng < -21.36178f) {
                        return 31;
                    }
                    return 1;
                } else if (lng >= -21.925478f) {
                    return 31;
                } else {
                    if (lng < -24.400326f) {
                        if (poly[482].contains(lat, lng)) {
                            return 31;
                        }
                        return 6;
                    } else if (poly[483].contains(lat, lng)) {
                        return 6;
                    } else {
                        return 31;
                    }
                }
            }
        } else if (lng >= -26.608158f) {
            return call47(lat, lng);
        } else {
            if (lng >= -60.919846f) {
                return 6;
            }
            if (lng < -68.0f) {
                if (lat < 77.46977f) {
                    return 122;
                }
                if (lng >= -73.53204f && lat < 79.05277f) {
                    return 122;
                }
                return 65;
            } else if (lat < 77.469055f) {
                if (lng >= -63.73601f) {
                    return 6;
                }
                if (lat >= 76.139275f && lng >= -66.1413f && poly[484].contains(lat, lng)) {
                    return 6;
                }
                return 122;
            } else if (lat < 80.6842f) {
                if (lat >= 79.171616f) {
                    return 6;
                }
                if (lng < -66.60112f) {
                    return 122;
                }
                if (lat >= 79.139244f || poly[485].contains(lat, lng) || poly[486].contains(lat, lng)) {
                    return 6;
                }
                return 122;
            } else if (lng < -66.2869f) {
                return 77;
            } else {
                return lat < 81.547485f ? (lng >= -64.45941f || lat < 81.07923f) ? 6 : 77 : (lng >= -64.431755f && poly[487].contains(lat, lng)) ? 6 : 77;
            }
        }
    }

    private static int call49(float lat, float lng) {
        if (lng < -76.01596f) {
            if (lng >= -107.018616f) {
                return call40(lat, lng);
            }
            if (lat >= 59.70417f) {
                return call31(lat, lng);
            }
            if (lng < -133.98338f) {
                if (lng < -156.46912f) {
                    if (lng >= -162.0f) {
                        return 147;
                    }
                    if (lng < -170.84665f) {
                        return 74;
                    }
                    if (lng >= -169.66287f || lat >= 53.077026f) {
                        return 63;
                    }
                    return 74;
                } else if (lng < -149.59598f) {
                    return 147;
                } else {
                    if (lat >= 57.77133f) {
                        return call26(lat, lng);
                    }
                    if (lng < -134.79362f || lat < 56.95779f) {
                        return 7;
                    }
                    if (lat >= 57.120937f) {
                        return 144;
                    }
                    if (lng >= -134.72372f && lng >= -134.18376f) {
                        return 144;
                    }
                    return 7;
                }
            } else if (lng >= -130.92754f) {
                return call28(lat, lng);
            } else {
                if (lat < 55.61811f) {
                    if (lat < 54.57037f) {
                        return 19;
                    }
                    if (lng < -131.64052f || lat >= 55.283344f) {
                        return 7;
                    }
                    if (lng < -131.29698f) {
                        if (lat < 54.991585f) {
                            return 7;
                        }
                        if (lat < 55.190212f) {
                            if (lng >= -131.5096f || !poly[488].contains(lat, lng)) {
                                return 90;
                            }
                            return 7;
                        } else if (poly[489].contains(lat, lng)) {
                            return 90;
                        } else {
                            return 7;
                        }
                    } else if (lat < 54.622246f) {
                        return 19;
                    } else {
                        return 7;
                    }
                } else if (lat < 56.35021f) {
                    return 7;
                } else {
                    return call27(lat, lng);
                }
            }
        } else if (lat < 63.995365f) {
            if (lat >= 59.509193f) {
                return call46(lat, lng);
            }
            if (lng < -10.478556f) {
                return call44(lat, lng);
            }
            return 1;
        } else if (lat >= 68.32447f) {
            return call48(lat, lng);
        } else {
            if (lng < -64.03071f) {
                if (lng < -68.0f) {
                    return 65;
                }
                return 77;
            } else if (lng < -52.48657f) {
                if (lng >= -53.850292f) {
                    return 6;
                }
                if (lng >= -61.538124f && lng >= -61.25699f) {
                    return 6;
                }
                return 77;
            } else if (lng >= -29.118732f && lng >= -28.86656f) {
                return 1;
            } else {
                return 6;
            }
        }
    }

    private static int call50(float lat, float lng) {
        if (lng < 12.94583f) {
            if (lng < 12.808323f) {
                if (lng < 12.482702f) {
                    return lat < -6.07476f ? 1 : 136;
                }
                if (lat < -13.145263f) {
                    return 1;
                }
                if (lng >= 12.664001f) {
                    return (lat >= -6.012101f && poly[490].contains(lat, lng)) ? 136 : 1;
                }
                if (lat < -6.026062f) {
                    return (lng >= 12.557324f || lat < -6.060052f) ? 1 : 136;
                }
                return 136;
            } else if (lat < -5.980898f) {
                return 1;
            } else {
                return lat < -5.927235f ? lng < 12.86856f ? 136 : 1 : (lng >= 12.876115f || !poly[491].contains(lat, lng)) ? 136 : 1;
            }
        } else if (lng >= 30.774246f || lng < 13.264205f) {
            return 1;
        } else {
            if (lng < 14.67042f) {
                return poly[492].contains(lat, lng) ? 136 : 1;
            }
            if (lng < 22.722334f) {
                if (poly[493].contains(lat, lng)) {
                    return 136;
                }
                if (poly[494].contains(lat, lng)) {
                    return 142;
                }
                return 1;
            } else if (lat < -9.950404f) {
                if (poly[495].contains(lat, lng)) {
                    return 142;
                }
                return 1;
            } else if (poly[496].contains(lat, lng)) {
                return 142;
            } else {
                return 1;
            }
        }
    }

    private static int call51(float lat, float lng) {
        if (lng < 27.413694f) {
            if (lat < -0.258301f) {
                if (poly[497].contains(lat, lng)) {
                    return 142;
                }
                return 136;
            } else if (lng < 23.031767f) {
                if (poly[498].contains(lat, lng)) {
                    return 1;
                }
                if (!poly[499].contains(lat, lng) && !poly[500].contains(lat, lng) && !poly[501].contains(lat, lng) && !poly[502].contains(lat, lng)) {
                    return 136;
                }
                return 142;
            } else if (poly[503].contains(lat, lng) || poly[504].contains(lat, lng)) {
                return 1;
            } else {
                if (!poly[505].contains(lat, lng) && !poly[506].contains(lat, lng) && !poly[507].contains(lat, lng) && !poly[508].contains(lat, lng)) {
                    return 142;
                }
                return 136;
            }
        } else if (lat >= -0.990736f) {
            return poly[511].contains(lat, lng) ? 142 : 1;
        } else {
            if (lng < 29.59868f) {
                return lat < -5.66435f ? poly[509].contains(lat, lng) ? 1 : 142 : poly[510].contains(lat, lng) ? 142 : 1;
            }
            return 1;
        }
    }

    private static int call52(float lat, float lng) {
        if (lat < 26.816055f) {
            if (lat < -5.902699f) {
                if (lat < -13.998108f) {
                    return 1;
                }
                return call50(lat, lng);
            } else if (lng < 11.979548f || lat >= 5.386098f) {
                return 1;
            } else {
                if (lng < 18.64984f) {
                    if (lng < 12.066581f) {
                        return 1;
                    }
                    if (lat < -5.841628f) {
                        if (lng < 13.986273f) {
                            if (poly[512].contains(lat, lng)) {
                                return 1;
                            }
                            return 136;
                        } else if (lng < 14.530292f) {
                            if (poly[513].contains(lat, lng)) {
                                return 136;
                            }
                            return 1;
                        } else if (poly[514].contains(lat, lng)) {
                            return 1;
                        } else {
                            return 136;
                        }
                    } else if (lat < -4.376826f) {
                        if (lng < 14.221724f) {
                            if (poly[515].contains(lat, lng)) {
                                return 136;
                            }
                            return 1;
                        } else if (poly[516].contains(lat, lng)) {
                            return 1;
                        } else {
                            return 136;
                        }
                    } else if (lng < 14.502347f) {
                        if (lat >= -4.279238f || !poly[517].contains(lat, lng)) {
                            return 1;
                        }
                        return 136;
                    } else if (poly[518].contains(lat, lng)) {
                        return 136;
                    } else {
                        return 1;
                    }
                } else if (lng < 31.305912f) {
                    return call51(lat, lng);
                } else {
                    return 1;
                }
            }
        } else if (lat >= 33.904083f && lng >= 27.246471f) {
            return 80;
        } else {
            return 1;
        }
    }

    private static int call53(float lat, float lng) {
        if (lng < 32.325027f) {
            if (lat < 46.57839f) {
                return 1;
            }
            return lat < 53.20811f ? poly[519].contains(lat, lng) ? 85 : 1 : poly[520].contains(lat, lng) ? 85 : 1;
        } else if (lng >= 37.223877f) {
            return lat < 43.5865f ? poly[528].contains(lat, lng) ? 85 : 1 : (lat >= 46.77061f && poly[529].contains(lat, lng)) ? 1 : 85;
        } else {
            if (lat < 45.436085f) {
                if (lng < 36.583805f) {
                    if (poly[521].contains(lat, lng)) {
                        return 85;
                    }
                    return 92;
                } else if (poly[522].contains(lat, lng)) {
                    return 92;
                } else {
                    return 85;
                }
            } else if (lat < 45.476776f) {
                return 92;
            } else {
                if (lat >= 48.130928f) {
                    return lat < 52.36936f ? poly[526].contains(lat, lng) ? 1 : 85 : poly[527].contains(lat, lng) ? 1 : 85;
                }
                if (lat >= 46.282494f) {
                    return 1;
                }
                if (lng < 34.193047f) {
                    if (!poly[523].contains(lat, lng) && !poly[524].contains(lat, lng)) {
                        return 1;
                    }
                    return 92;
                } else if (poly[525].contains(lat, lng)) {
                    return 92;
                } else {
                    return 1;
                }
            }
        }
    }

    private static int call54(float lat, float lng) {
        if (lng >= 33.722027f) {
            return (lat >= 69.30883f && lat < 74.75705f) ? 1 : 85;
        }
        if (lat < 60.7505f) {
            if (lng >= 28.171377f || lat < 60.11067f) {
                return 85;
            }
            if (lng < 27.435055f) {
                return 1;
            }
            if (lat < 60.45739f) {
                return 85;
            }
            return lng < 27.85475f ? lat < 60.525665f ? lng < 27.6749f ? 1 : 85 : poly[530].contains(lat, lng) ? 85 : 1 : (lat >= 60.485054f && poly[531].contains(lat, lng)) ? 1 : 85;
        } else if (lat >= 69.95409f) {
            return 1;
        } else {
            if (lng >= 31.580944f) {
                return 85;
            }
            if (lat < 69.84817f) {
                return lat < 62.908028f ? poly[532].contains(lat, lng) ? 1 : 85 : lng < 30.945389f ? (!poly[533].contains(lat, lng) && !poly[534].contains(lat, lng)) ? 1 : 85 : (lat >= 63.341846f || poly[535].contains(lat, lng)) ? 85 : 1;
            }
            return 1;
        }
    }

    private static int call55(float lat, float lng) {
        if (lng < 103.5575f) {
            if (lng < 99.85386f) {
                return lat < -2.408889f ? 1 : 9;
            }
            if (lng < 101.34136f) {
                if (lat < -2.504945f) {
                    return lat < -65.38302f ? 1 : 9;
                }
                if (lat < 3.078566f) {
                    return (lng >= 101.03975f && lat >= 2.289889f) ? 1 : 9;
                }
                return 1;
            } else if (lat < 1.156361f) {
                return lat < -65.115845f ? 1 : 9;
            } else {
                if (lng >= 102.51364f) {
                    return 1;
                }
                if (lat < 1.927042f) {
                    return 9;
                }
                return (lat >= 2.119222f || lng >= 101.791115f) ? 1 : 9;
            }
        } else if (lat < 0.647444f) {
            return lat < -65.96554f ? 1 : 9;
        } else {
            if (lat < 1.150528f) {
                return 9;
            }
            if (lat >= 1.471278f) {
                return 1;
            }
            if (lat < 1.25988f) {
                if (lng < 103.86724f) {
                    return 129;
                }
                return 9;
            } else if (lat < 1.291056f) {
                return 129;
            } else {
                if (lng < 104.00638f) {
                    if (lng < 103.73019f) {
                        if (poly[536].contains(lat, lng)) {
                            return 1;
                        }
                        return 129;
                    } else if (lng >= 103.73456f && lat >= 1.425806f && poly[537].contains(lat, lng)) {
                        return 1;
                    } else {
                        return 129;
                    }
                } else if (lng >= 104.04159f && !poly[538].contains(lat, lng)) {
                    return 1;
                } else {
                    return 129;
                }
            }
        }
    }

    private static int call56(float lat, float lng) {
        if (lat < 49.213818f) {
            if (lng < 47.171124f) {
                if (poly[539].contains(lat, lng)) {
                    return 52;
                }
                if (!poly[540].contains(lat, lng) && !poly[541].contains(lat, lng)) {
                    return poly[542].contains(lat, lng) ? 85 : 43;
                }
                return 55;
            } else if (poly[543].contains(lat, lng) || poly[544].contains(lat, lng)) {
                return 43;
            } else {
                if (poly[545].contains(lat, lng)) {
                    return 52;
                }
                return 55;
            }
        } else if (lng < 47.171124f) {
            if (poly[546].contains(lat, lng)) {
                return 52;
            }
            return poly[547].contains(lat, lng) ? 85 : 43;
        } else if (lng < 50.367878f) {
            if (lat < 51.94552f) {
                return poly[548].contains(lat, lng) ? 52 : 43;
            }
            if (poly[549].contains(lat, lng)) {
                return 43;
            }
            if (poly[550].contains(lat, lng)) {
                return 85;
            }
            return 73;
        } else if (lat < 51.94552f) {
            if (poly[551].contains(lat, lng)) {
                return 43;
            }
            if (poly[552].contains(lat, lng)) {
                return 73;
            }
            if (poly[553].contains(lat, lng)) {
                return 82;
            }
            return 52;
        } else if (poly[554].contains(lat, lng)) {
            return 43;
        } else {
            if (poly[555].contains(lat, lng)) {
                return 82;
            }
            if (!poly[556].contains(lat, lng) && !poly[557].contains(lat, lng)) {
                return 73;
            }
            return 85;
        }
    }

    private static int call57(float lat, float lng) {
        if (lng < 53.224625f) {
            if (lat < 57.938507f) {
                if (lng < 51.461796f) {
                    if (!poly[558].contains(lat, lng) && !poly[559].contains(lat, lng) && !poly[560].contains(lat, lng) && !poly[561].contains(lat, lng)) {
                        return poly[562].contains(lat, lng) ? 85 : 43;
                    }
                    return 73;
                } else if (poly[563].contains(lat, lng) || poly[564].contains(lat, lng) || poly[565].contains(lat, lng)) {
                    return 43;
                } else {
                    if (poly[566].contains(lat, lng)) {
                        return 82;
                    }
                    return (!poly[567].contains(lat, lng) && !poly[568].contains(lat, lng)) ? 73 : 85;
                }
            } else if (poly[569].contains(lat, lng)) {
                return 73;
            } else {
                if (!poly[570].contains(lat, lng) && !poly[571].contains(lat, lng)) {
                    return poly[572].contains(lat, lng) ? 85 : 43;
                }
                return 82;
            }
        } else if (lat < 57.938507f) {
            if (poly[573].contains(lat, lng)) {
                return 73;
            }
            return (!poly[574].contains(lat, lng) && !poly[575].contains(lat, lng) && !poly[576].contains(lat, lng)) ? 82 : 85;
        } else if (poly[577].contains(lat, lng) || poly[578].contains(lat, lng)) {
            return 43;
        } else {
            if (poly[579].contains(lat, lng)) {
                return 73;
            }
            return poly[580].contains(lat, lng) ? 85 : 82;
        }
    }

    private static int call58(float lat, float lng) {
        if (lat < 46.470276f) {
            if (lng >= 49.434917f) {
                return (lng >= 53.026917f && poly[584].contains(lat, lng)) ? 104 : 55;
            }
            if (lng >= 49.248165f) {
                return 55;
            }
            if (lng < 47.174988f) {
                return (!poly[581].contains(lat, lng) && !poly[582].contains(lat, lng)) ? 43 : 85;
            }
            if (lat < 46.242054f) {
                return 43;
            }
            if (lng >= 49.220917f) {
                return 55;
            }
            if (lat < 46.31542f) {
                return 43;
            }
            return (lng >= 49.20028f || !poly[583].contains(lat, lng)) ? 55 : 43;
        } else if (lat < 46.824722f) {
            if (lng >= 52.352417f) {
                return (lng >= 52.993137f && poly[589].contains(lat, lng)) ? 104 : 55;
            }
            if (lng < 49.005333f) {
                return lng < 47.227768f ? lat < 46.74082f ? poly[585].contains(lat, lng) ? 43 : 85 : poly[586].contains(lat, lng) ? 43 : 85 : (!poly[587].contains(lat, lng) && !poly[588].contains(lat, lng)) ? 43 : 55;
            }
            return 55;
        } else if (lat < 54.677216f) {
            if (lng < 53.564632f) {
                return call56(lat, lng);
            }
            if (poly[590].contains(lat, lng)) {
                return 52;
            }
            if (poly[591].contains(lat, lng)) {
                return 55;
            }
            if (poly[592].contains(lat, lng)) {
                return 82;
            }
            return 104;
        } else if (lng < 42.647667f) {
            return 85;
        } else {
            if (lng < 49.69897f) {
                return poly[593].contains(lat, lng) ? 43 : 85;
            }
            if (lat < 61.1998f) {
                return call57(lat, lng);
            }
            if (poly[594].contains(lat, lng)) {
                return 82;
            }
            return 85;
        }
    }

    private static int call59(float lat, float lng) {
        if (lng < 48.941555f) {
            if (lat < 44.51053f) {
                if (lat >= 43.509445f) {
                    return 85;
                }
                if (lat < 41.113f) {
                    return 1;
                }
                if (lng < 44.99905f) {
                    if (lat >= 41.58789f && poly[595].contains(lat, lng)) {
                        return 85;
                    }
                    return 1;
                } else if (poly[596].contains(lat, lng)) {
                    return 85;
                } else {
                    return 1;
                }
            } else if (lng >= 47.589027f) {
                return 43;
            } else {
                if (lat < 45.17936f) {
                    return 85;
                }
                if (lng < 46.869904f) {
                    if (poly[597].contains(lat, lng)) {
                        return 43;
                    }
                    return 85;
                } else if (poly[598].contains(lat, lng)) {
                    return 43;
                } else {
                    return 85;
                }
            }
        } else if (lng < 50.211113f) {
            if (lat >= 45.055195f) {
                return 43;
            }
            if (lng < 49.51102f) {
                return 1;
            }
            return 55;
        } else if (lng < 50.781612f) {
            return 55;
        } else {
            if (lat < 45.17469f) {
                if (!poly[599].contains(lat, lng) && !poly[600].contains(lat, lng)) {
                    return 55;
                }
                return 1;
            } else if (lng >= 52.795555f && poly[601].contains(lat, lng)) {
                return 104;
            } else {
                return 55;
            }
        }
    }

    private static int call60(float lat, float lng) {
        if (lng < 56.824085f) {
            return lat < 50.99686f ? poly[602].contains(lat, lng) ? 82 : 104 : lat < 51.053818f ? poly[603].contains(lat, lng) ? 104 : 82 : poly[604].contains(lat, lng) ? 104 : 82;
        }
        if (lng < 62.46773f) {
            if (lat < 51.4014f) {
                if (poly[605].contains(lat, lng)) {
                    return 82;
                }
                if (!poly[606].contains(lat, lng) && !poly[607].contains(lat, lng) && !poly[608].contains(lat, lng) && !poly[609].contains(lat, lng)) {
                    return 104;
                }
                return 103;
            } else if (poly[610].contains(lat, lng)) {
                return 103;
            } else {
                return 82;
            }
        } else if (lat < 51.4014f) {
            if (poly[611].contains(lat, lng) || poly[612].contains(lat, lng) || poly[613].contains(lat, lng) || poly[614].contains(lat, lng)) {
                return 104;
            }
            if (poly[615].contains(lat, lng)) {
                return 140;
            }
            return 103;
        } else if (poly[616].contains(lat, lng) || poly[617].contains(lat, lng)) {
            return 82;
        } else {
            if (poly[618].contains(lat, lng)) {
                return 140;
            }
            return 103;
        }
    }

    private static int call61(float lat, float lng) {
        if (lng < 78.99068f) {
            if (lat < 55.580475f) {
                if (lng < 73.551025f) {
                    if (poly[619].contains(lat, lng)) {
                        return 82;
                    }
                    return (!poly[620].contains(lat, lng) && !poly[621].contains(lat, lng)) ? 140 : 152;
                } else if (poly[622].contains(lat, lng)) {
                    return 41;
                } else {
                    return (!poly[623].contains(lat, lng) && !poly[624].contains(lat, lng)) ? 152 : 140;
                }
            } else if (lng < 73.551025f) {
                return poly[625].contains(lat, lng) ? 152 : 82;
            } else {
                if (poly[626].contains(lat, lng)) {
                    return 82;
                }
                return poly[627].contains(lat, lng) ? 152 : 41;
            }
        } else if (lat < 55.580475f) {
            if (lng < 84.43033f) {
                if (poly[628].contains(lat, lng)) {
                    return 41;
                }
                return poly[629].contains(lat, lng) ? 140 : 152;
            } else if (lat < 52.78183f) {
                if (poly[630].contains(lat, lng)) {
                    return 36;
                }
                if (poly[631].contains(lat, lng)) {
                    return 118;
                }
                return poly[632].contains(lat, lng) ? 140 : 152;
            } else if (poly[633].contains(lat, lng)) {
                return 41;
            } else {
                if (poly[634].contains(lat, lng)) {
                    return 118;
                }
                if (poly[635].contains(lat, lng)) {
                    return 152;
                }
                return 36;
            }
        } else if (lng < 84.43033f) {
            if (poly[636].contains(lat, lng)) {
                return 36;
            }
            if (poly[637].contains(lat, lng)) {
                return 82;
            }
            if (poly[638].contains(lat, lng)) {
                return 118;
            }
            return 41;
        } else if (lat < 58.37912f) {
            if (!poly[639].contains(lat, lng) && !poly[640].contains(lat, lng)) {
                return poly[641].contains(lat, lng) ? 118 : 36;
            }
            return 41;
        } else if (poly[642].contains(lat, lng)) {
            return 41;
        } else {
            if (poly[643].contains(lat, lng)) {
                return 82;
            }
            return 118;
        }
    }

    private static int call62(float lat, float lng) {
        if (lat >= 59.32083f) {
            return lat < 59.746864f ? poly[658].contains(lat, lng) ? 70 : 118 : poly[659].contains(lat, lng) ? 70 : 118;
        }
        if (lat < 47.88436f) {
            if (lng < 89.995316f) {
                if (poly[644].contains(lat, lng)) {
                    return 56;
                }
                return 1;
            } else if (poly[645].contains(lat, lng)) {
                return 56;
            } else {
                if (poly[646].contains(lat, lng)) {
                    return 57;
                }
                return 1;
            }
        } else if (lng < 97.40982f) {
            if (poly[647].contains(lat, lng)) {
                return 56;
            }
            if (poly[648].contains(lat, lng)) {
                return 57;
            }
            return (!poly[649].contains(lat, lng) && !poly[650].contains(lat, lng)) ? 118 : 70;
        } else if (lat >= 53.602596f) {
            return (!poly[655].contains(lat, lng) && !poly[656].contains(lat, lng) && !poly[657].contains(lat, lng)) ? 70 : 118;
        } else {
            if (lng < 101.17975f) {
                if (poly[651].contains(lat, lng)) {
                    return 56;
                }
                if (poly[652].contains(lat, lng)) {
                    return 57;
                }
                return poly[653].contains(lat, lng) ? 70 : 118;
            } else if (poly[654].contains(lat, lng)) {
                return 70;
            } else {
                return 57;
            }
        }
    }

    private static int call63(float lat, float lng) {
        if (lat < 61.177765f) {
            if (lng >= 89.86998f) {
                return call62(lat, lng);
            }
            if (lat < 45.36664f) {
                if (lng >= 81.68958f) {
                    return poly[662].contains(lat, lng) ? 140 : 1;
                }
                if (lat < 40.810528f) {
                    return 1;
                }
                return lng < 74.900475f ? poly[660].contains(lat, lng) ? 140 : 1 : poly[661].contains(lat, lng) ? 140 : 1;
            } else if (lat >= 49.983185f) {
                return call61(lat, lng);
            } else {
                if (lng < 78.99068f) {
                    return 140;
                }
                if (lng < 84.43033f) {
                    return poly[663].contains(lat, lng) ? 1 : 140;
                }
                if (lng < 87.150154f) {
                    if (poly[664].contains(lat, lng)) {
                        return 1;
                    }
                    if (poly[665].contains(lat, lng)) {
                        return 152;
                    }
                    return 140;
                } else if (poly[666].contains(lat, lng)) {
                    return 1;
                } else {
                    if (poly[667].contains(lat, lng)) {
                        return 118;
                    }
                    if (poly[668].contains(lat, lng)) {
                        return 140;
                    }
                    if (poly[669].contains(lat, lng)) {
                        return 152;
                    }
                    return 56;
                }
            }
        } else if (lng < 72.39066f) {
            return 82;
        } else {
            if (lng < 86.0311f) {
                if (lat < 64.27527f) {
                    if (poly[670].contains(lat, lng)) {
                        return 118;
                    }
                    return 82;
                } else if (poly[671].contains(lat, lng)) {
                    return 118;
                } else {
                    return 82;
                }
            } else if (poly[672].contains(lat, lng)) {
                return 70;
            } else {
                return 118;
            }
        }
    }

    private static int call64(float lat, float lng) {
        if (lat < 66.44342f) {
            if (lng >= 68.111374f) {
                return call63(lat, lng);
            }
            if (lat < 44.89215f) {
                if (lng < 59.26145f) {
                    return 1;
                }
                if (poly[673].contains(lat, lng)) {
                    return 103;
                }
                return poly[674].contains(lat, lng) ? 140 : 1;
            } else if (lat < 47.82583f) {
                if (lng < 61.927216f) {
                    if (lng < 58.58015f) {
                        if (poly[675].contains(lat, lng)) {
                            return 103;
                        }
                        if (poly[676].contains(lat, lng)) {
                            return 104;
                        }
                        return 1;
                    } else if (lat < 45.436764f) {
                        if (lng < 59.4092f) {
                            return lng < 59.239082f ? (lat >= 44.974434f && poly[677].contains(lat, lng)) ? 103 : 1 : poly[678].contains(lat, lng) ? 103 : 1;
                        }
                        return 103;
                    } else if (lat >= 45.51124f && poly[679].contains(lat, lng)) {
                        return 104;
                    } else {
                        return 103;
                    }
                } else if (poly[680].contains(lat, lng)) {
                    return 104;
                } else {
                    return poly[681].contains(lat, lng) ? 140 : 103;
                }
            } else if (lat < 54.97697f) {
                return call60(lat, lng);
            } else {
                return poly[682].contains(lat, lng) ? 85 : 82;
            }
        } else if (lng < 71.14855f) {
            if (lng < 66.21054f) {
                return lat < 67.69609f ? poly[683].contains(lat, lng) ? 82 : 85 : poly[684].contains(lat, lng) ? 82 : 85;
            }
            return 82;
        } else if (lng >= 80.010056f && poly[685].contains(lat, lng)) {
            return 118;
        } else {
            return 82;
        }
    }

    private static int call65(float lat, float lng) {
        if (lng >= 83.042755f) {
            return 118;
        }
        if (lat < 70.4892f) {
            if (lng < 77.31255f) {
                if (lng >= 65.66165f || poly[686].contains(lat, lng)) {
                    return 82;
                }
                return 85;
            } else if (lng < 82.68378f) {
                if (lat < 70.09844f) {
                    if (lat < 68.61303f) {
                        if (poly[687].contains(lat, lng)) {
                            return 118;
                        }
                        return 82;
                    } else if (!poly[688].contains(lat, lng) && !poly[689].contains(lat, lng)) {
                        return 82;
                    } else {
                        return 118;
                    }
                } else if (lng >= 80.79108f || !poly[690].contains(lat, lng)) {
                    return 118;
                } else {
                    return 82;
                }
            } else if (lat >= 68.8072f) {
                return 118;
            } else {
                if (lat < 68.46683f) {
                    if (poly[691].contains(lat, lng)) {
                        return 82;
                    }
                    return 118;
                } else if (lat < 68.667755f) {
                    if (poly[692].contains(lat, lng)) {
                        return 82;
                    }
                    return 118;
                } else if (poly[693].contains(lat, lng)) {
                    return 118;
                } else {
                    return 82;
                }
            }
        } else if (lng >= 80.75937f) {
            return 118;
        } else {
            if (lng < 77.29931f) {
                return 82;
            }
            if (lat < 70.654465f) {
                if (poly[694].contains(lat, lng)) {
                    return 118;
                }
                return 82;
            } else if (poly[695].contains(lat, lng)) {
                return 118;
            } else {
                return 82;
            }
        }
    }

    private static int call66(float lat, float lng) {
        if (lat < 59.500805f) {
            if (lat < 38.722973f) {
                if (lng >= 34.647827f || lat >= 35.796417f) {
                    return 1;
                }
                return call52(lat, lng);
            } else if (lat < 55.337112f) {
                if (lng < 13.351194f) {
                    if (lat >= 39.9424f || lng >= 4.315389f) {
                        return 1;
                    }
                    return 137;
                } else if (lng < 19.621935f || lat < 43.079533f) {
                    return 1;
                } else {
                    if (lng >= 30.135445f) {
                        return call53(lat, lng);
                    }
                    if (lat < 51.977196f || lng >= 22.892805f) {
                        return 1;
                    }
                    if (lat < 54.81372f) {
                        if (!poly[696].contains(lat, lng) && !poly[697].contains(lat, lng) && !poly[698].contains(lat, lng)) {
                            return 81;
                        }
                        return 1;
                    } else if (lng < 21.042534f) {
                        if (poly[699].contains(lat, lng)) {
                            return 1;
                        }
                        return 81;
                    } else if (poly[700].contains(lat, lng)) {
                        return 1;
                    } else {
                        return 81;
                    }
                }
            } else if (lng >= 24.149445f && lng >= 26.62839f && poly[701].contains(lat, lng)) {
                return 85;
            } else {
                return 1;
            }
        } else if (lng >= 22.028723f && lng >= 26.91664f) {
            return call54(lat, lng);
        } else {
            return 1;
        }
    }

    private static int call67(float lat, float lng) {
        if (lng < 124.0f) {
            return (lat >= -35.179195f || lat >= -65.646324f) ? 14 : 1;
        }
        if (lng < 125.34456f) {
            return lat < -66.45085f ? 1 : 14;
        }
        if (lng < 128.25081f) {
            if (lat >= -31.3f) {
                return 14;
            }
            if (lat < -66.26966f) {
                return 1;
            }
            if (poly[702].contains(lat, lng)) {
                return 14;
            }
            return 34;
        } else if (lat < -31.3f) {
            if (lat < -33.186306f) {
                if (lat < -65.27472f) {
                    return 1;
                }
                return 29;
            } else if (lng >= 128.99318f || poly[703].contains(lat, lng)) {
                return 29;
            } else {
                return 34;
            }
        } else if (lng < 128.47098f) {
            return 14;
        } else {
            if (lat >= -14.760972f) {
                return 5;
            }
            if (lng < 129.63191f) {
                if (lng < 129.25983f) {
                    if (poly[704].contains(lat, lng)) {
                        return 14;
                    }
                    if (poly[705].contains(lat, lng)) {
                        return 29;
                    }
                    return 5;
                } else if (lat >= -25.999517f || poly[706].contains(lat, lng)) {
                    return 5;
                } else {
                    return 29;
                }
            } else if (lat >= -25.99891f || !poly[707].contains(lat, lng)) {
                return 5;
            } else {
                return 29;
            }
        }
    }

    private static int call68(float lat, float lng) {
        if (lng < 150.10522f) {
            if (lat < -25.998417f) {
                if (lng < 136.3559f) {
                    if (lat >= -34.507416f && poly[708].contains(lat, lng)) {
                        return 5;
                    }
                    return 29;
                } else if (lng < 138.13142f) {
                    if (lat < -32.5725f) {
                        return 29;
                    }
                    if (poly[709].contains(lat, lng)) {
                        return 5;
                    }
                    if (poly[710].contains(lat, lng)) {
                        return 100;
                    }
                    return 29;
                } else if (lng < 139.71837f) {
                    if (lat >= -34.771526f && poly[711].contains(lat, lng)) {
                        return 100;
                    }
                    return 29;
                } else if (lat < -34.00964f) {
                    if (lng < 140.9848f) {
                        if (poly[712].contains(lat, lng)) {
                            return 58;
                        }
                        return 29;
                    } else if (lat >= -37.546555f && !poly[713].contains(lat, lng)) {
                        return 94;
                    } else {
                        return 58;
                    }
                } else if (poly[714].contains(lat, lng)) {
                    return 29;
                } else {
                    if (poly[715].contains(lat, lng)) {
                        return 59;
                    }
                    return poly[716].contains(lat, lng) ? 100 : 94;
                }
            } else if (lng >= 138.00272f || poly[717].contains(lat, lng)) {
                return 100;
            } else {
                return 5;
            }
        } else if (lat >= -28.264166f) {
            return 100;
        } else {
            if (lat >= -32.20325f && lat >= -32.18486f) {
                return lng < 152.46895f ? poly[718].contains(lat, lng) ? 100 : 94 : poly[719].contains(lat, lng) ? 100 : 94;
            }
            return 94;
        }
    }

    private static int call69(float lat, float lng) {
        if (lng < 152.8605f) {
            if (lat < -20.98889f) {
                if (lat >= -37.56689f) {
                    return call68(lat, lng);
                }
                if (lat < -40.295612f) {
                    if (lat < -65.513824f) {
                        return 1;
                    }
                    return 50;
                } else if (lat < -39.21339f) {
                    if (lng >= 146.99652f) {
                        return 50;
                    }
                    if (lng < 144.13858f) {
                        return 18;
                    }
                    return 58;
                } else if (lng >= 140.97098f || poly[720].contains(lat, lng)) {
                    return 58;
                } else {
                    return 29;
                }
            } else if (lng < 136.9645f) {
                return 5;
            } else {
                if (lng < 147.52081f) {
                    if (lng >= 138.00002f) {
                        return 100;
                    }
                    if (lng >= 137.10435f && poly[721].contains(lat, lng)) {
                        return 100;
                    }
                    return 5;
                } else if (lng < 149.11844f) {
                    if (lng < 148.871f || lat < -20.471966f || lng >= 149.06447f) {
                        return 100;
                    }
                    if (lat < -20.333471f) {
                        if (lng < 148.93723f) {
                            return 100;
                        }
                        return 95;
                    } else if (lng >= 148.89386f || lat >= -20.085667f) {
                        return 100;
                    } else {
                        return 95;
                    }
                } else if (lat < -20.46525f) {
                    return 100;
                } else {
                    return lat < -16.0195f ? 1 : 78;
                }
            }
        } else if (lat >= -22.600111f) {
            return lng < 158.62419f ? 78 : 1;
        } else {
            if (lat < -36.903778f) {
                if (lat >= -52.607582f) {
                    return 150;
                }
                if (lat < -66.270256f) {
                    return 1;
                }
                return 26;
            } else if (lng >= 172.11284f) {
                return 150;
            } else {
                if (lat >= -27.586111f) {
                    return lng < 153.5478f ? 100 : 1;
                }
                if (lat >= -28.16011f) {
                    return 100;
                }
                if (lng < 153.63925f) {
                    if (lat < -29.370111f) {
                        return 94;
                    }
                    if (lng < 153.483f) {
                        if (poly[722].contains(lat, lng)) {
                            return 100;
                        }
                        return 94;
                    } else if (poly[723].contains(lat, lng)) {
                        return 100;
                    } else {
                        return 94;
                    }
                } else if (lng < 159.11128f) {
                    return 2;
                } else {
                    return 1;
                }
            }
        }
    }

    private static int call70(float lat, float lng) {
        if (lat < -6.038f) {
            if (lat < -13.226722f) {
                return call67(lat, lng);
            }
            if (lng < 125.71925f) {
                if (lng < 119.251724f) {
                    if (lng >= 116.26186f) {
                        return 154;
                    }
                    if (lat >= -7.248944f) {
                        return 9;
                    }
                    if (lng >= 114.60167f) {
                        return 154;
                    }
                    if (lng < 112.714195f) {
                        if (lng < 105.71958f) {
                            return 1;
                        }
                        return 9;
                    } else if (lng < 113.42516f) {
                        return 9;
                    } else {
                        return (!poly[724].contains(lat, lng) && !poly[725].contains(lat, lng)) ? 9 : 154;
                    }
                } else if (lng < 124.08839f) {
                    return 154;
                } else {
                    if (lat < -8.467278f) {
                        if (lat >= -8.511828f) {
                            return 154;
                        }
                        if (lng < 124.46358f) {
                            if (lat >= -9.174916f || !poly[726].contains(lat, lng)) {
                                return 154;
                            }
                            return 1;
                        } else if (poly[727].contains(lat, lng)) {
                            return 154;
                        } else {
                            return 1;
                        }
                    } else if (lng < 125.13153f) {
                        return 154;
                    } else {
                        return 1;
                    }
                }
            } else if (lat >= -8.265695f) {
                return 61;
            } else {
                if (lat < -10.902861f) {
                    return 5;
                }
                if (lng < 127.34534f) {
                    return 1;
                }
                return 61;
            }
        } else if (lng >= 125.01156f) {
            return 61;
        } else {
            if (lng < 118.42714f) {
                if (lng < 107.52944f) {
                    return 9;
                }
                if (lng >= 115.7662f) {
                    return 154;
                }
                if (lng < 109.26989f) {
                    if (lat < -2.501333f) {
                        return 9;
                    }
                    return 131;
                } else if (lat < -5.426f) {
                    return 9;
                } else {
                    if (lng < 113.4247f) {
                        return 131;
                    }
                    if (lat < -4.685056f) {
                        return 154;
                    }
                    if (lng < 114.026596f) {
                        if (poly[728].contains(lat, lng)) {
                            return 154;
                        }
                        return 131;
                    } else if (poly[729].contains(lat, lng)) {
                        return 131;
                    } else {
                        return 154;
                    }
                }
            } else if (lng < 124.03369f) {
                return 154;
            } else {
                if (lat < -1.961278f) {
                    return lat < -5.320944f ? 154 : 61;
                }
                if (lng < 124.57044f) {
                    return lat < -1.6285f ? lng < 124.177025f ? 154 : 61 : lat < -0.613941f ? 61 : 154;
                }
                return 61;
            }
        }
    }

    private static int call71(float lat, float lng) {
        if (lng < 111.41603f) {
            if (lng < 109.11855f) {
                if (lat < 1.573319f) {
                    return 131;
                }
                return 9;
            } else if (lat < 2.083333f) {
                return (lng >= 109.267975f && !poly[730].contains(lat, lng)) ? 1 : 131;
            } else {
                return 1;
            }
        } else if (lat < 3.454611f) {
            if (lng >= 115.6487f) {
                return 154;
            }
            if (lng < 114.09579f) {
                if (poly[731].contains(lat, lng) || poly[732].contains(lat, lng)) {
                    return 131;
                }
                if (poly[733].contains(lat, lng)) {
                    return 154;
                }
                return 1;
            } else if (lat < 0.753819f) {
                if (lng < 114.884285f) {
                    if (poly[734].contains(lat, lng)) {
                        return 154;
                    }
                    return 131;
                } else if (poly[735].contains(lat, lng)) {
                    return 154;
                } else {
                    return 131;
                }
            } else if (poly[736].contains(lat, lng)) {
                return 1;
            } else {
                return 154;
            }
        } else if (lng < 115.35944f) {
            return 1;
        } else {
            if (lng < 117.06114f) {
                if (poly[737].contains(lat, lng)) {
                    return 1;
                }
                return 154;
            } else if (lat >= 3.628139f && poly[738].contains(lat, lng)) {
                return 1;
            } else {
                return 154;
            }
        }
    }

    private static int call72(float lat, float lng) {
        if (lat < 50.60112f) {
            if (lat < 46.818195f) {
                if (poly[739].contains(lat, lng)) {
                    return 57;
                }
                return (!poly[740].contains(lat, lng) && !poly[741].contains(lat, lng)) ? 1 : 93;
            } else if (lng < 112.7357f) {
                if (lat < 47.91153f) {
                    return poly[742].contains(lat, lng) ? 93 : 57;
                }
                if (poly[743].contains(lat, lng)) {
                    return 67;
                }
                if (poly[744].contains(lat, lng)) {
                    return 70;
                }
                return poly[745].contains(lat, lng) ? 93 : 57;
            } else if (lat < 50.282917f) {
                if (lng < 114.34905f) {
                    if (poly[746].contains(lat, lng)) {
                        return 67;
                    }
                    return 93;
                } else if (poly[747].contains(lat, lng)) {
                    return 67;
                } else {
                    return poly[748].contains(lat, lng) ? 93 : 1;
                }
            } else if (poly[749].contains(lat, lng)) {
                return 1;
            } else {
                return 67;
            }
        } else if (lng >= 105.49525f) {
            return lng < 112.3867f ? poly[753].contains(lat, lng) ? 70 : 67 : lat < 55.452045f ? poly[754].contains(lat, lng) ? 70 : 67 : lng < 115.832436f ? (!poly[755].contains(lat, lng) && !poly[756].contains(lat, lng) && !poly[757].contains(lat, lng)) ? 70 : 67 : (!poly[758].contains(lat, lng) && !poly[759].contains(lat, lng) && !poly[760].contains(lat, lng)) ? 67 : 70;
        } else {
            if (lat < 59.495953f) {
                if (poly[750].contains(lat, lng)) {
                    return 118;
                }
                return 70;
            } else if (lat < 59.845543f) {
                if (poly[751].contains(lat, lng)) {
                    return 118;
                }
                return 70;
            } else if (poly[752].contains(lat, lng)) {
                return 70;
            } else {
                return 118;
            }
        }
    }

    private static int call73(float lat, float lng) {
        if (lng < 119.27817f) {
            if (lat < 72.99461f) {
                if (lat < 60.302967f) {
                    return call72(lat, lng);
                }
                if (lat < 72.81678f) {
                    if (lng < 112.7258f) {
                        if (lat < 66.559875f) {
                            if (lng < 108.83773f) {
                                if (lat < 63.43142f) {
                                    if (!poly[761].contains(lat, lng) && !poly[762].contains(lat, lng)) {
                                        return 70;
                                    }
                                    return 118;
                                } else if (poly[763].contains(lat, lng)) {
                                    return 67;
                                } else {
                                    if (poly[764].contains(lat, lng)) {
                                        return 70;
                                    }
                                    return 118;
                                }
                            } else if (poly[765].contains(lat, lng)) {
                                return 70;
                            } else {
                                return 67;
                            }
                        } else if (poly[766].contains(lat, lng)) {
                            return 67;
                        } else {
                            return 118;
                        }
                    } else if (poly[767].contains(lat, lng)) {
                        return 70;
                    } else {
                        return 67;
                    }
                } else if (lng >= 106.022835f && !poly[768].contains(lat, lng)) {
                    return 67;
                } else {
                    return 118;
                }
            } else if (lng >= 111.842766f) {
                return 67;
            } else {
                if (lng < 109.3435f) {
                    return 118;
                }
                if (lat < 74.04972f) {
                    if (poly[769].contains(lat, lng)) {
                        return 67;
                    }
                    return 118;
                } else if (lng < 111.21f) {
                    if (poly[770].contains(lat, lng)) {
                        return 67;
                    }
                    return 118;
                } else if (lat < 74.48751f) {
                    return 67;
                } else {
                    return 118;
                }
            }
        } else if (lat >= 53.4985f) {
            return 67;
        } else {
            if (lat < 47.68412f) {
                if (lng >= 119.92431f) {
                    return 1;
                }
                if (lat < 46.75189f) {
                    if (poly[771].contains(lat, lng)) {
                        return 93;
                    }
                    return 1;
                } else if (poly[772].contains(lat, lng)) {
                    return 93;
                } else {
                    return 1;
                }
            } else if (lng < 122.317085f) {
                if (lat < 50.341087f) {
                    if (poly[773].contains(lat, lng)) {
                        return 67;
                    }
                    return 1;
                } else if (poly[774].contains(lat, lng)) {
                    return 67;
                } else {
                    return 1;
                }
            } else if (lat >= 52.277126f && poly[775].contains(lat, lng)) {
                return 67;
            } else {
                return 1;
            }
        }
    }

    private static int call74(float lat, float lng) {
        if (lat < 66.15501f) {
            if (lat < 54.69214f) {
                if (lng >= 134.95996f) {
                    return 16;
                }
                if (lat >= 49.6042f) {
                    return lng < 131.95815f ? poly[780].contains(lat, lng) ? 67 : 16 : poly[781].contains(lat, lng) ? 67 : 16;
                }
                if (lng < 131.95815f) {
                    if (poly[776].contains(lat, lng) || poly[777].contains(lat, lng)) {
                        return 16;
                    }
                    if (poly[778].contains(lat, lng)) {
                        return 67;
                    }
                    return 1;
                } else if (poly[779].contains(lat, lng)) {
                    return 1;
                } else {
                    return 16;
                }
            } else if (lat < 60.423573f) {
                if (lng < 132.72826f) {
                    if (poly[782].contains(lat, lng) || poly[783].contains(lat, lng)) {
                        return 16;
                    }
                    if (poly[784].contains(lat, lng)) {
                        return 45;
                    }
                    return 67;
                } else if (poly[785].contains(lat, lng)) {
                    return 45;
                } else {
                    return poly[786].contains(lat, lng) ? 67 : 16;
                }
            } else if (poly[787].contains(lat, lng)) {
                return 16;
            } else {
                if (poly[788].contains(lat, lng)) {
                    return 67;
                }
                return 45;
            }
        } else if (lat >= 71.61378f) {
            return lng < 129.60867f ? 67 : 16;
        } else {
            if (lng < 133.51804f) {
                return lat < 69.72694f ? poly[789].contains(lat, lng) ? 16 : 67 : (lng >= 130.0477f && poly[790].contains(lat, lng)) ? 16 : 67;
            }
            return 16;
        }
    }

    private static int call75(float lat, float lng) {
        if (lat < 71.50847f) {
            if (lng >= 147.1658f) {
                return 20;
            }
            if (lat >= 71.331665f) {
                return (lng >= 138.00826f && poly[803].contains(lat, lng)) ? 20 : 16;
            }
            if (lat >= 66.15926f) {
                return (lng >= 138.05225f && poly[802].contains(lat, lng)) ? 20 : 16;
            }
            if (lat < 65.59248f) {
                if (lng < 140.81937f) {
                    if (poly[791].contains(lat, lng)) {
                        return 20;
                    }
                    if (poly[792].contains(lat, lng)) {
                        return 45;
                    }
                    if (poly[793].contains(lat, lng)) {
                        return 66;
                    }
                    return 16;
                } else if (lat < 59.63833f) {
                    return poly[794].contains(lat, lng) ? 20 : 16;
                } else {
                    if (lng < 143.99258f) {
                        if (poly[795].contains(lat, lng)) {
                            return 16;
                        }
                        if (poly[796].contains(lat, lng)) {
                            return 20;
                        }
                        return 66;
                    } else if (poly[797].contains(lat, lng)) {
                        return 16;
                    } else {
                        if (poly[798].contains(lat, lng)) {
                            return 66;
                        }
                        return 20;
                    }
                }
            } else if (lng < 136.51685f) {
                if (poly[799].contains(lat, lng)) {
                    return 16;
                }
                return 45;
            } else if (poly[800].contains(lat, lng)) {
                return 20;
            } else {
                if (poly[801].contains(lat, lng)) {
                    return 45;
                }
                return 16;
            }
        } else if (lng >= 141.98941f && lat < 72.33141f) {
            return (lng >= 147.3888f || poly[804].contains(lat, lng)) ? 20 : 16;
        } else {
            return 16;
        }
    }

    private static int call76(float lat, float lng) {
        if (lat < 53.00589f) {
            if (lng >= 158.91888f) {
                return 74;
            }
            if (lat >= 50.924026f || lng >= 156.4988f) {
                return 148;
            }
            return 66;
        } else if (lat < 59.627693f) {
            if (lng < 155.53464f) {
                return 20;
            }
            if (lng >= 155.5977f || lat >= 57.47835f) {
                return 148;
            }
            return 20;
        } else if (lng < 164.16776f) {
            if (lat < 60.907417f) {
                if (lng >= 160.95844f) {
                    return 148;
                }
                if (lng >= 159.89575f && lat >= 60.119286f) {
                    return 148;
                }
                return 20;
            } else if (lng < 163.46606f) {
                if (lat < 64.38693f) {
                    if (lat < 61.465637f) {
                        if (lng < 162.15178f) {
                            return 20;
                        }
                        return 148;
                    } else if (!poly[805].contains(lat, lng) && !poly[806].contains(lat, lng)) {
                        return 20;
                    } else {
                        return 148;
                    }
                } else if (poly[807].contains(lat, lng)) {
                    return 146;
                } else {
                    if (poly[808].contains(lat, lng)) {
                        return 148;
                    }
                    return 20;
                }
            } else if (lat >= 62.34886f && poly[809].contains(lat, lng)) {
                return 146;
            } else {
                return 148;
            }
        } else if (lat < 61.11286f) {
            return 148;
        } else {
            if (lng >= 174.51361f || poly[810].contains(lat, lng)) {
                return 146;
            }
            return 148;
        }
    }

    private static int call77(float lat, float lng) {
        if (lng < 151.03413f) {
            if (lat >= 55.586514f) {
                return call75(lat, lng);
            }
            if (lng < 141.68414f) {
                if (lat >= 52.39146f) {
                    return 16;
                }
                if (lat < 44.440166f) {
                    return 1;
                }
                if (lng >= 141.54631f) {
                    return lat < 45.450085f ? 1 : 15;
                }
                if (lng < 141.06985f) {
                    if (poly[811].contains(lat, lng)) {
                        return 1;
                    }
                    return 16;
                } else if (lat < 45.25711f) {
                    return 1;
                } else {
                    if (lat < 48.98051f) {
                        return 15;
                    }
                    return 16;
                }
            } else if (lng >= 145.82089f) {
                return 66;
            } else {
                if (lat >= 45.52314f) {
                    return 15;
                }
                if (lat >= 43.575165f && lng < 145.37497f) {
                    return 66;
                }
                return 1;
            }
        } else if (lat < 64.93997f) {
            if (lat < 50.806805f) {
                return 66;
            }
            return call76(lat, lng);
        } else if (lat < 70.12775f) {
            if (lng < 161.65567f) {
                return (lat >= 68.41138f || !poly[812].contains(lat, lng)) ? 20 : 146;
            }
            if (lng >= 162.8494f) {
                return 146;
            }
            if (lng >= 161.892f) {
                return lat < 68.869705f ? poly[815].contains(lat, lng) ? 20 : 146 : (lng >= 162.08919f && poly[816].contains(lat, lng)) ? 146 : 20;
            }
            if (lat < 68.38269f) {
                return lat < 65.00844f ? poly[813].contains(lat, lng) ? 20 : 146 : poly[814].contains(lat, lng) ? 20 : 146;
            }
            return 20;
        } else if (lng < 152.31017f) {
            return 20;
        } else {
            if (lng >= 160.79283f) {
                return lng < 162.52281f ? 20 : 146;
            }
            if (lat < 71.09089f) {
                return 20;
            }
            return 16;
        }
    }

    private static int call78(float lat, float lng) {
        if (lng < 120.4213f) {
            if (lat >= 4.794528f) {
                return 1;
            }
            if (lng < 108.696526f) {
                return 9;
            }
            if (lng < 117.50886f) {
                return call71(lat, lng);
            }
            if (lat < 4.147695f) {
                return 154;
            }
            if (lng < 117.90794f) {
                return lng < 117.58581f ? poly[817].contains(lat, lng) ? 154 : 1 : poly[818].contains(lat, lng) ? 154 : 1;
            }
            return 1;
        } else if (lat < 9.828222f) {
            if (lng < 126.183365f) {
                if (lat >= 3.805333f) {
                    return 1;
                }
                if (lng < 125.71011f) {
                    return 154;
                }
                return 61;
            } else if (lng < 151.97878f) {
                if (lat < 2.293f) {
                    return 61;
                }
                if (lng >= 134.72372f) {
                    return 96;
                }
                if (lat >= 4.780778f) {
                    return 1;
                }
                if (lng < 127.14655f) {
                    return 154;
                }
                if (lng < 128.69017f) {
                    return 61;
                }
                return 1;
            } else if (lat >= 7.018389f) {
                return 1;
            } else {
                if (lng >= 172.13797f) {
                    return 71;
                }
                if (lng >= 163.03488f) {
                    return 1;
                }
                if (lat >= 5.582806f) {
                    return 97;
                }
                if (lng < 153.82191f) {
                    return 96;
                }
                return 108;
            }
        } else if (lng < 131.17903f) {
            return 1;
        } else {
            if (lng < 145.8525f) {
                if (lng >= 144.96397f) {
                    return 157;
                }
                if (lat >= 20.542805f) {
                    return 1;
                }
                if (lng < 139.79523f) {
                    return 96;
                }
                if (lat < 17.090555f) {
                    return 47;
                }
                return 157;
            } else if (lat < 14.655167f) {
                return 1;
            } else {
                if (lng >= 153.98523f) {
                    return 64;
                }
                if (lat < 20.151083f) {
                    return 1;
                }
                return 157;
            }
        }
    }

    private static int call79(float lat, float lng) {
        if (lat < 0.621778f) {
            if (lng < 135.3378f) {
                return call70(lat, lng);
            }
            if (lat < -10.905472f) {
                return call69(lat, lng);
            }
            if (lng < 151.86627f) {
                if (lng >= 144.14258f) {
                    return 78;
                }
                if (lng < 140.84286f) {
                    return 61;
                }
                if (lat < -9.33375f) {
                    return 100;
                }
                if (lng >= 142.47464f) {
                    return 78;
                }
                if (lat < -9.182138f) {
                    if (lng < 142.07144f) {
                        if (lng < 141.9271f) {
                            return 78;
                        }
                        return 100;
                    } else if (lng < 142.31572f) {
                        return 100;
                    } else {
                        return 78;
                    }
                } else if (lng >= 141.0218f) {
                    return 78;
                } else {
                    if (lat < -6.611417f) {
                        if (poly[819].contains(lat, lng)) {
                            return 78;
                        }
                        return 61;
                    } else if (poly[820].contains(lat, lng)) {
                        return 61;
                    } else {
                        return 78;
                    }
                }
            } else if (lng < 158.41833f) {
                if (lng >= 157.09544f) {
                    return 1;
                }
                if (lng < 154.74408f) {
                    return 78;
                }
                if (lat < -6.962555f) {
                    return 1;
                }
                if (lng >= 155.96356f && lat < -6.589611f) {
                    return 1;
                }
                return 78;
            } else if (lat < -4.621028f) {
                return 1;
            } else {
                if (lng >= 166.94528f) {
                    return 71;
                }
                if (lat < -2.550833f) {
                    return 78;
                }
                return 1;
            }
        } else if (lat < 25.382833f) {
            return call78(lat, lng);
        } else {
            if (lat < 41.392307f) {
                return 1;
            }
            if (lng >= 136.50017f) {
                return call77(lat, lng);
            }
            if (lng < 122.90853f) {
                if (lat < 74.68439f) {
                    return call73(lat, lng);
                }
                return 118;
            } else if (lat >= 72.749695f) {
                return lng < 135.4635f ? 67 : 16;
            } else {
                if (lat >= 71.95786f) {
                    return 67;
                }
                if (lat < 44.516262f) {
                    if (lng >= 131.3084f) {
                        return 16;
                    }
                    if (lng < 130.28809f) {
                        return 1;
                    }
                    if (lat < 43.399807f) {
                        if (!poly[821].contains(lat, lng) && !poly[822].contains(lat, lng)) {
                            return 16;
                        }
                        return 1;
                    } else if (poly[823].contains(lat, lng)) {
                        return 16;
                    } else {
                        return 1;
                    }
                } else if (lng >= 128.95633f) {
                    return call74(lat, lng);
                } else {
                    if (lat >= 53.56086f) {
                        return 67;
                    }
                    if (lat < 46.995644f) {
                        return 1;
                    }
                    if (!poly[824].contains(lat, lng) && !poly[825].contains(lat, lng)) {
                        return 1;
                    }
                    return 67;
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public static class TzPolygon {
        float[] pts;

        TzPolygon(float... D) {
            this.pts = D;
        }

        /* JADX INFO: Multiple debug info for r4v3 float: [D('i' int), D('yi' float)] */
        public boolean contains(float testy, float testx) {
            boolean inside = false;
            float[] fArr = this.pts;
            int n = fArr.length;
            float yj = fArr[n - 2];
            float xj = fArr[n - 1];
            int i = 0;
            while (i < n) {
                float[] fArr2 = this.pts;
                int i2 = i + 1;
                float yi = fArr2[i];
                int i3 = i2 + 1;
                float xi = fArr2[i2];
                boolean z = false;
                if ((yi > testy) != (yj > testy) && testx < ((((xj - xi) * (testy - yi)) / (yj - yi)) + xi) - 1.0E-4f) {
                    if (!inside) {
                        z = true;
                    }
                    inside = z;
                }
                xj = xi;
                yj = yi;
                i = i3;
            }
            return inside;
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer1 {
        private Initializer1() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[0] = new TzPolygon(72.25183f, 78.647964f, 72.26111f, 78.65915f, 72.34738f, 78.44501f, 72.372665f, 78.58217f, 72.38539f, 78.42408f, 72.355225f, 79.20914f, 72.25183f, 79.20914f);
            TimezoneMapper.poly[1] = new TzPolygon(-50.528664f, -73.292854f, -50.280235f, -73.5725f, -50.04719f, -73.49719f, -49.924343f, -73.5451f, -49.810394f, -73.43428f, -49.523613f, -73.58297f, -49.512962f, -73.50139f, -49.44843f, -73.52271f, -49.377426f, -73.4499f, -49.164494f, -73.483635f, -49.149796f, -73.292854f);
            TimezoneMapper.poly[2] = new TzPolygon(-50.52227f, -73.292854f, -50.683105f, -73.119f, -50.734535f, -73.17067f, -50.775253f, -73.132744f, -50.675858f, -72.88555f, -51.246307f, -72.88555f, -51.246307f, -73.292854f);
            TimezoneMapper.poly[3] = new TzPolygon(-51.962368f, -71.99654f, -51.962368f, -68.725105f, -52.30964f, -68.3682f, -52.400146f, -68.43986f, -52.334953f, -68.426735f, -52.278217f, -68.82405f, -52.15178f, -69.203f, -52.147213f, -69.483986f, -51.998188f, -70.019356f, -51.97502f, -72.00206f);
            TimezoneMapper.poly[4] = new TzPolygon(-51.962368f, -71.99654f, -51.86804f, -71.95533f, -51.698967f, -72.29337f, -51.597576f, -72.34233f, -51.56912f, -72.437805f, -51.254128f, -72.26027f, -51.11865f, -72.40204f, -51.025475f, -72.25039f, -50.855255f, -72.23086f, -50.74471f, -72.32686f, -50.65468f, -72.27752f, -50.602665f, -72.47372f, -50.67614f, -72.60237f, -50.616367f, -72.7376f, -50.68925f, -72.88555f, -50.52227f, -72.88555f, -50.52227f, -69.03929f, -50.56383f, -69.07726f, -50.90101f, -69.12797f, -50.973915f, -69.217995f, -50.992687f, -69.16187f, -51.549587f, -68.94093f, -51.603245f, -69.17662f, -51.66381f, -69.152916f, -51.6956f, -69.22515f, -51.612522f, -69.00441f, -51.637f, -68.95716f, -51.930943f, -68.7574f, -51.962368f, -68.725105f);
            TimezoneMapper.poly[5] = new TzPolygon(-48.937122f, -73.292854f, -48.937122f, -72.797134f, -48.94592f, -72.927925f, -49.134388f, -73.09282f, -49.157276f, -73.292854f);
            TimezoneMapper.poly[6] = new TzPolygon(-44.961937f, -71.66992f, -44.97973f, -71.56018f, -45.300262f, -71.297264f, -45.39858f, -71.50492f, -45.507965f, -71.48328f, -45.562233f, -71.75804f, -45.83456f, -71.7473f, -45.888493f, -71.62981f, -45.96498f, -71.59771f, -46.10033f, -71.73967f, -46.148212f, -71.89174f, -46.2001f, -71.762535f, -46.682537f, -71.64585f, -46.811447f, -71.93131f, -47.012848f, -71.9131f, -47.074905f, -71.98467f, -47.13842f, -71.853874f, -47.231228f, -71.85986f, -47.206486f, -72.02198f, -47.28409f, -72.01929f, -47.410713f, -72.14584f, -47.46354f, -72.33645f, -47.614517f, -72.2807f, -47.65139f, -72.39119f, -47.75771f, -72.42933f, -47.73246f, -72.49909f, -47.799927f, -72.452156f, -47.925236f, -72.51994f, -48.073906f, -72.33434f, -48.349743f, -72.283005f, -48.36793f, -72.37862f, -48.49064f, -72.451164f, -48.48259f, -72.59773f, -48.766567f, -72.54359f, -48.83343f, -72.597305f, -48.936203f, -72.783485f, -48.937122f, -72.797134f, -44.961937f, -72.797134f);
            TimezoneMapper.poly[7] = new TzPolygon(-42.306767f, -72.12493f, -42.364952f, -72.094986f, -42.364952f, -72.12493f);
            TimezoneMapper.poly[8] = new TzPolygon(-39.284485f, -72.12493f, -39.284485f, -71.38628f, -39.40178f, -71.47234f, -39.483807f, -71.46674f, -39.521984f, -71.53309f, -39.590187f, -71.481026f, -39.62507f, -71.50096f, -39.626595f, -71.60567f, -39.571484f, -71.68819f, -39.629517f, -71.71634f, -39.8412f, -71.689354f, -39.903233f, -71.589836f, -40.02588f, -71.68415f, -40.104076f, -71.67126f, -40.087955f, -71.816185f, -40.222424f, -71.83086f, -40.30713f, -71.7492f, -40.300026f, -71.68131f, -40.36865f, -71.654076f, -40.4381f, -71.71929f, -40.41164f, -71.804535f, -40.55814f, -71.88263f, -40.633114f, -71.832756f, -40.739685f, -71.946686f, -41.06088f, -71.828125f, -41.171337f, -71.87856f, -41.256382f, -71.84244f, -41.360573f, -71.9064f, -41.571144f, -71.81888f, -41.70732f, -71.826065f, -41.82104f, -71.74616f, -42.115387f, -71.72849f, -42.18733f, -71.919235f, -42.14136f, -72.02837f, -42.306767f, -72.12493f);
            TimezoneMapper.poly[9] = new TzPolygon(-39.284485f, -71.38628f, -38.929832f, -71.42781f, -38.927124f, -71.42374f, -38.927124f, -71.38628f);
            TimezoneMapper.poly[10] = new TzPolygon(-33.668552f, -71.474365f, -33.668552f, -69.883125f, -33.67991f, -69.88485f, -33.98992f, -69.82988f, -34.125786f, -69.87567f, -34.22364f, -69.815414f, -34.296738f, -69.91417f, -34.25258f, -69.97592f, -34.28701f, -70.04218f, -34.401867f, -70.01145f, -34.49504f, -70.13433f, -34.749275f, -70.314674f, -34.804314f, -70.2623f, -35.030876f, -70.36951f, -35.14241f, -70.36263f, -35.195705f, -70.425606f, -35.205906f, -70.54834f, -35.25788f, -70.57765f, -35.34343f, -70.42433f, -35.64802f, -70.401405f, -35.81065f, -70.32572f, -35.90998f, -70.422516f, -35.923218f, -70.38129f, -36.027435f, -70.371704f, -36.145332f, -70.41908f, -36.17119f, -70.47146f, -36.12971f, -70.577065f, -36.25469f, -70.698784f, -36.407036f, -70.69384f, -36.383263f, -70.88615f, -36.47318f, -70.93564f, -36.46488f, -71.034485f, -36.663647f, -71.024376f, -36.69514f, -71.06576f, -36.659355f, -71.111885f, -36.839394f, -71.17023f, -36.949608f, -71.101776f, -36.974865f, -71.2026f, -37.103165f, -71.09187f, -37.297554f, -71.21278f, -37.471928f, -71.116196f, -37.672318f, -71.20245f, -37.81885f, -71.13633f, -37.863625f, -71.190865f, -37.89375f, -71.124535f, -38.094086f, -70.99876f, -38.133102f, -71.04253f, -38.410442f, -71.00228f, -38.56734f, -70.83173f, -38.745373f, -70.94752f, -38.80585f, -71.24127f, -38.927124f, -71.42374f, -38.927124f, -71.474365f);
            TimezoneMapper.poly[11] = new TzPolygon(-33.668552f, -69.63204f, -33.668552f, -69.883125f, -33.547287f, -69.864746f, -33.407787f, -69.74717f, -33.22215f, -69.888115f, -33.32141f, -69.96358f, -33.31369f, -70.02461f, -33.041874f, -70.0929f, -32.9016f, -69.99737f, -32.731236f, -70.1457f, -32.61261f, -70.169365f, -32.531826f, -70.12961f, -32.46473f, -70.157005f, -32.4325f, -70.23359f, -32.309845f, -70.24437f, -32.261116f, -69.66556f, -32.172787f, -69.63204f);
            TimezoneMapper.poly[12] = new TzPolygon(-32.172787f, -69.63204f, -32.261116f, -69.66556f, -32.309845f, -70.24437f, -32.260864f, -70.32107f, -32.144966f, -70.29893f, -32.050953f, -70.385635f, -32.043724f, -70.27756f, -31.95696f, -70.20664f, -31.890007f, -70.28109f, -31.848299f, -70.44023f, -31.482286f, -70.561775f, -31.148432f, -70.50563f, -31.089745f, -70.44455f, -31.170847f, -70.39112f, -31.023941f, -70.266884f, -30.891106f, -70.304955f, -30.456715f, -70.1292f, -30.34923f, -70.15387f, -30.398163f, -69.9746f, -30.186663f, -69.806625f, -30.123415f, -69.82742f, -30.090162f, -69.94f, -29.692238f, -69.881584f, -29.324593f, -70.01793f, -29.169048f, -69.94977f, -29.108662f, -69.784836f, -28.892117f, -69.76641f, -28.772778f, -69.67233f, -28.682852f, -69.70601f, -28.449776f, -69.63204f);
            TimezoneMapper.poly[13] = new TzPolygon(-25.511286f, -70.59943f, -25.481459f, -70.53857f, -25.397362f, -70.52179f, -25.351143f, -70.448425f, -25.192207f, -70.44054f, -25.110462f, -70.50773f, -24.994965f, -70.470604f, -24.715754f, -70.58021f, -24.54476f, -70.578865f, -24.16664f, -70.499344f, -23.861216f, -70.515015f, -23.612078f, -70.391304f, -23.532162f, -70.40371f, -23.465748f, -70.47886f, -23.534206f, -70.54662f, -23.520523f, -70.59943f, -23.262316f, -70.59943f, -23.244438f, -70.59771f, -23.18116f, -70.557396f, -23.083422f, -70.5786f, -23.021387f, -70.50888f, -23.098124f, -70.47869f, -23.08492f, -70.40815f, -22.992395f, -70.343834f, -22.992395f, -66.98794f, -24.034197f, -67.33754f, -24.379583f, -68.21353f, -24.48403f, -68.31661f, -24.474066f, -68.39136f, -24.546375f, -68.402916f, -24.747684f, -68.567375f, -24.834064f, -68.57169f, -25.072287f, -68.3374f, -25.133516f, -68.50546f, -25.227903f, -68.55127f, -25.32928f, -68.53344f, -25.398954f, -68.59751f, -25.65709f, -68.48693f, -26.190495f, -68.39045f, -26.306953f, -68.57038f, -26.509184f, -68.5877f, -26.9169f, -68.27213f, -27.046822f, -68.326904f, -27.06377f, -68.48055f, -27.1878f, -68.544136f, -27.083498f, -68.68447f, -27.132984f, -68.79672f, -27.204268f, -68.848656f, -27.29071f, -68.81993f, -27.642384f, -69.06155f, -27.888725f, -69.104164f, -27.995932f, -69.24202f, -28.201044f, -69.369896f, -28.17159f, -69.436905f, -28.312134f, -69.48173f, -28.377625f, -69.63151f, -28.409985f, -69.63204f, -28.409985f, -70.59943f);
            TimezoneMapper.poly[14] = new TzPolygon(-22.992395f, -66.98794f, -22.804693f, -67.1775f, -22.71084f, -67.12217f, -22.644667f, -67.013504f, -22.533396f, -67.02109f, -22.518967f, -66.98794f);
            TimezoneMapper.poly[15] = new TzPolygon(-19.919102f, -66.98794f, -22.518967f, -66.98794f, -22.533396f, -67.02109f, -22.644667f, -67.013504f, -22.71084f, -67.12217f, -22.80561f, -67.17774f, -22.896133f, -67.57222f, -22.873558f, -67.7939f, -22.825804f, -67.87513f, -22.54646f, -67.8481f, -22.32144f, -67.94613f, -22.090786f, -67.94265f, -21.975716f, -68.06444f, -21.76316f, -68.05695f, -21.599545f, -68.17713f, -21.296675f, -68.17242f, -20.937944f, -68.40059f, -20.93256f, -68.51543f, -20.879955f, -68.54726f, -20.726265f, -68.54917f, -20.632399f, -68.43936f, -20.498291f, -68.67821f, -20.392424f, -68.75569f, -20.324041f, -68.663246f, -20.239777f, -68.712875f, -20.14174f, -68.70467f, -20.082048f, -68.780464f, -20.044783f, -68.55966f, -19.927412f, -68.52397f, -19.919102f, -68.52455f);
            TimezoneMapper.poly[16] = new TzPolygon(-19.919102f, -70.13302f, -19.655796f, -70.168884f, -19.602276f, -70.23494f, -19.502893f, -70.20014f, -19.305538f, -70.28453f, -19.14325f, -70.267654f, -18.795506f, -70.3596f, -18.445957f, -70.30267f, -18.349728f, -70.37419f, -18.310644f, -70.32787f, -18.319487f, -70.17463f, -18.24803f, -69.947876f, -17.981972f, -69.75266f, -17.778137f, -69.779915f, -17.747467f, -69.81888f, -17.650354f, -69.798996f, -17.657827f, -69.6619f, -17.507553f, -69.46265f, -17.605848f, -69.46149f, -17.801f, -69.30818f, -17.950356f, -69.296295f, -18.067204f, -69.06861f, -18.156723f, -69.13856f, -18.324337f, -69.03617f, -18.749912f, -68.97799f, -18.87579f, -68.90938f, -18.940805f, -68.95712f, -19.045353f, -68.88409f, -19.407597f, -68.407265f, -19.739092f, -68.690575f, -19.84282f, -68.52985f, -19.919102f, -68.52455f);
            TimezoneMapper.poly[17] = new TzPolygon(-12.124428f, -64.69972f, -12.16237f, -64.72484f, -12.124428f, -64.7466f);
            TimezoneMapper.poly[18] = new TzPolygon(-13.462453f, -61.857124f, -13.442552f, -61.865635f, -13.256899f, -62.10808f, -13.148521f, -62.108517f, -13.112125f, -62.16526f, -13.145263f, -62.367844f, -13.069615f, -62.46445f, -13.041165f, -62.637207f, -12.9647f, -62.674408f, -13.022641f, -62.764473f, -12.853908f, -62.92334f, -12.849501f, -62.996193f, -12.652854f, -63.08235f, -12.704915f, -63.326565f, -12.651011f, -63.426514f, -12.557039f, -63.481968f, -12.457861f, -63.712124f, -12.468522f, -63.87504f, -12.55716f, -63.93673f, -12.493826f, -64.12723f, -12.52885f, -64.1516f, -12.475866f, -64.21483f, -12.511484f, -64.27783f, -12.365522f, -64.49297f, -12.229553f, -64.48612f, -12.186312f, -64.65425f, -12.124428f, -64.674255f, -12.124428f, -61.857124f);
            TimezoneMapper.poly[19] = new TzPolygon(-22.104658f, -56.390266f, -22.100248f, -56.39239f, -22.100248f, -56.390266f);
            TimezoneMapper.poly[20] = new TzPolygon(-22.100248f, -56.506214f, -22.103062f, -56.51894f, -22.261166f, -56.642826f, -22.216257f, -56.69845f, -22.26049f, -56.727657f, -22.249907f, -56.807827f, -22.300434f, -56.83113f, -22.22545f, -57.000504f, -22.247795f, -57.09943f, -22.209871f, -57.203026f, -22.244143f, -57.242958f, -22.18097f, -57.553135f, -22.100248f, -57.613384f);
            TimezoneMapper.poly[21] = new TzPolygon(-22.100248f, -57.6311f, -22.147953f, -57.80349f, -22.100248f, -57.943077f);
            TimezoneMapper.poly[22] = new TzPolygon(-30.082224f, -57.434975f, -30.001225f, -57.341965f, -29.794395f, -57.253567f, -29.751858f, -57.105194f, -29.638693f, -56.97189f, -29.321186f, -56.67567f, -29.122995f, -56.593063f, -29.072884f, -56.424786f, -29.004015f, -56.390266f, -30.082224f, -56.390266f);
            TimezoneMapper.poly[23] = new TzPolygon(-29.004015f, -56.390266f, -28.793924f, -56.28496f, -28.770824f, -56.184864f, -28.610699f, -56.016163f, -28.509132f, -56.01075f, -28.479164f, -55.88802f, -28.362265f, -55.874763f, -28.375694f, -55.749134f, -28.425304f, -55.69562f, -28.333199f, -55.670597f, -28.247013f, -55.77309f, -28.122692f, -55.604652f, -28.16401f, -55.554302f, -28.081087f, -55.49579f, -28.097296f, -55.44441f, -27.928085f, -55.322998f, -27.863062f, -55.195995f, -27.895418f, -55.133827f, -27.853176f, -55.11698f, -27.853931f, -55.031883f, -27.78873f, -55.08216f, -27.775112f, -54.94052f, -27.5359f, -54.808735f, -27.586494f, -54.781147f, -27.57551f, -54.683308f, -27.513931f, -54.67785f, -27.549913f, -54.623844f, -27.458294f, -54.57641f, -27.50687f, -54.524673f, -27.412132f, -54.414948f, -27.467508f, -54.353207f, -27.405546f, -54.33897f, -27.447117f, -54.282375f, -27.260693f, -54.175957f, -27.302652f, -54.089058f, -27.158316f, -53.949463f, -27.177027f, -53.901962f, -27.133757f, -53.86375f, -27.172464f, -53.830185f, -27.103771f, -53.813004f, -27.104921f, -53.775322f, -27.047829f, -53.795074f, -27.069778f, -53.762745f, -27.034369f, -53.777596f, -26.9503f, -53.675056f, -26.86853f, -53.662308f, -26.745457f, -53.75148f, -26.65814f, -53.715584f, -26.646215f, -53.752872f, -26.441349f, -53.702736f, -26.410627f, -53.64753f, -26.264887f, -53.591835f, -26.129519f, -53.722187f, -26.091236f, -53.727158f, -26.091236f, -50.92341f, -30.082224f, -50.92341f, -30.082224f, -51.234173f, -30.04303f, -51.229927f, -30.006798f, -51.294514f, -30.082224f, -51.305286f, -30.082224f, -56.390266f);
            TimezoneMapper.poly[24] = new TzPolygon(-22.104658f, -56.390266f, -22.100248f, -56.390266f, -22.100248f, -52.39183f, -22.193336f, -52.462784f, -22.482224f, -52.97528f, -22.705822f, -53.156845f, -22.885265f, -53.572174f, -23.300282f, -53.72139f, -23.446945f, -53.968895f, -23.94806f, -54.080284f, -24.07127f, -54.28378f, -23.928808f, -54.411194f, -23.815378f, -54.66784f, -23.861084f, -54.701153f, -23.899275f, -54.887566f, -23.964281f, -54.932472f, -23.991236f, -55.060833f, -23.962997f, -55.112206f, -24.012663f, -55.226208f, -23.95894f, -55.31489f, -23.99325f, -55.341537f, -23.961388f, -55.41258f, -23.714037f, -55.434086f, -23.61376f, -55.535294f, -23.197105f, -55.52169f, -23.141083f, -55.596447f, -22.994284f, -55.63997f, -22.99151f, -55.685066f, -22.65852f, -55.612797f, -22.532196f, -55.73557f, -22.396128f, -55.73975f, -22.281366f, -55.854023f, -22.280878f, -56.208412f, -22.20598f, -56.341457f);
            TimezoneMapper.poly[25] = new TzPolygon(-26.091236f, -53.727158f, -26.047f, -53.7329f, -25.968618f, -53.837315f, -25.793463f, -53.823723f, -25.709263f, -53.881348f, -25.686064f, -53.84587f, -25.569143f, -54.010685f, -25.578829f, -54.07972f, -25.625378f, -54.101093f, -25.5044f, -54.11059f, -25.554499f, -54.212704f, -25.5912f, -54.18403f, -25.57081f, -54.23304f, -25.60249f, -54.251537f, -25.562471f, -54.28855f, -25.607546f, -54.38745f, -25.70594f, -54.430195f, -25.629606f, -54.49052f, -25.59497f, -54.596363f, -25.459059f, -54.616676f, -25.157377f, -54.436733f, -25.031471f, -54.45776f, -24.649237f, -54.33581f, -24.492779f, -54.333027f, -24.363293f, -54.259968f, -24.23637f, -54.328236f, -24.13097f, -54.328705f, -23.94806f, -54.080284f, -23.446945f, -53.968895f, -23.300282f, -53.72139f, -22.885265f, -53.572174f, -22.705822f, -53.156845f, -22.482224f, -52.97528f, -22.193336f, -52.462784f, -22.100248f, -52.39183f, -22.100248f, -50.92341f, -26.091236f, -50.92341f);
            TimezoneMapper.poly[26] = new TzPolygon(-22.100248f, -57.943077f, -22.084568f, -57.988956f, -22.031996f, -58.00527f, -21.88131f, -57.919155f, -21.84392f, -57.968826f, -21.792522f, -57.912655f, -21.746536f, -57.947025f, -21.687214f, -57.893806f, -21.52808f, -57.96752f, -21.33877f, -57.854916f, -21.278404f, -57.918194f, -21.225834f, -57.85381f, -21.04134f, -57.865738f, -20.976418f, -57.817802f, -20.897243f, -57.926666f, -20.835226f, -57.85523f, -20.784086f, -57.960804f, -20.735619f, -57.868908f, -20.666462f, -57.922924f, -20.695845f, -57.987507f, -20.44831f, -57.99416f, -20.372423f, -58.082138f, -20.257095f, -58.091972f, -20.263853f, -58.153812f, -20.188107f, -58.12504f, -20.162462f, -58.163456f, -19.985384f, -57.87358f, -19.76053f, -58.134026f, -19.051062f, -57.790283f, -19.03399f, -57.708553f, -18.858095f, -57.756557f, -18.262976f, -57.55973f, -18.228182f, -57.458096f, -18.127602f, -57.57306f, -17.833399f, -57.712646f, -17.727474f, -57.72123f, -17.740017f, -57.634354f, -17.82156f, -57.5998f, -17.89167f, -57.473618f, -17.950993f, -57.470192f, -17.867172f, -57.42704f, -17.763512f, -57.082893f, -17.314724f, -56.752502f, -17.32389f, -56.43167f, -17.30536f, -56.390266f, -22.100248f, -56.390266f, -22.100248f, -56.39239f, -22.077505f, -56.403347f, -22.100248f, -56.506214f, -22.100248f, -57.613384f, -22.096186f, -57.616417f, -22.100248f, -57.6311f);
            TimezoneMapper.poly[27] = new TzPolygon(-17.30536f, -56.390266f, -17.32389f, -56.43167f, -17.314724f, -56.752502f, -17.763512f, -57.082893f, -17.867172f, -57.42704f, -17.950993f, -57.470192f, -17.89167f, -57.473618f, -17.82156f, -57.5998f, -17.740017f, -57.634354f, -17.720024f, -57.735073f, -17.64549f, -57.785053f, -17.561592f, -57.800304f, -17.557655f, -57.769547f, -17.478554f, -57.84646f, -17.45998f, -57.90204f, -17.519537f, -58.00255f, -17.354225f, -58.255753f, -17.196997f, -58.394608f, -16.989662f, -58.420788f, -16.938763f, -58.47036f, -16.70194f, -58.467598f, -16.61312f, -58.442f, -16.541655f, -58.34046f, -16.269693f, -58.30934f, -16.266338f, -58.387653f, -16.324831f, -58.47073f, -16.264633f, -60.172977f, -15.476063f, -60.235813f, -15.10499f, -60.56529f, -15.10063f, -60.252533f, -14.624276f, -60.256886f, -14.554859f, -60.337593f, -14.181334f, -60.48159f, -14.118273f, -60.472027f, -14.118273f, -56.390266f);
            TimezoneMapper.poly[28] = new TzPolygon(-18.10926f, -56.390266f, -18.10926f, -53.047596f, -18.371948f, -53.029724f, -18.346947f, -52.895004f, -18.412502f, -52.758057f, -18.678059f, -52.890007f, -18.708336f, -52.47278f, -18.81667f, -52.349167f, -18.836945f, -52.188614f, -19.053612f, -51.841667f, -19.158058f, -51.423058f, -19.258892f, -51.306396f, -19.290836f, -51.114174f, -19.45668f, -50.92341f, -19.475002f, -50.962784f, -19.556667f, -50.932503f, -19.571667f, -50.981117f, -19.722504f, -51.023613f, -19.920002f, -50.985558f, -20.233334f, -51.03389f, -20.31528f, -51.26056f, -20.53917f, -51.466118f, -20.60028f, -51.580833f, -20.88028f, -51.630005f, -21.14917f, -51.871117f, -21.35028f, -51.87056f, -21.49778f, -51.9625f, -21.50278f, -52.04973f, -21.555836f, -52.09195f, -21.646389f, -52.037224f, -21.718891f, -52.053337f, -21.783337f, -52.150284f, -22.100248f, -52.39183f, -22.100248f, -56.390266f);
            TimezoneMapper.poly[29] = new TzPolygon(-17.30536f, -56.390266f, -17.18528f, -56.12195f, -17.195278f, -56.038338f, -17.265835f, -55.98945f, -17.355003f, -55.787224f, -17.392223f, -55.60028f, -17.51139f, -55.52945f, -17.685837f, -55.180557f, -17.655834f, -54.9075f, -17.546112f, -54.773895f, -17.503891f, -54.606117f, -17.579723f, -54.418617f, -17.675003f, -54.394173f, -17.629448f, -54.12195f, -17.309723f, -53.829445f, -17.250751f, -53.678757f, -17.651947f, -53.751114f, -17.704723f, -53.877502f, -17.934448f, -53.98139f, -18.019203f, -53.78656f, -18.01667f, -53.053894f, -18.10926f, -53.047596f, -18.10926f, -56.390266f);
            TimezoneMapper.poly[30] = new TzPolygon(-18.10926f, -50.92341f, -18.10926f, -53.042526f, -18.056393f, -53.04223f, -17.88889f, -53.11389f, -17.671669f, -53.126114f, -17.498058f, -53.227783f, -17.284725f, -53.201668f, -17.052502f, -53.039726f, -16.861115f, -53.011673f, -16.664722f, -52.72084f, -16.341667f, -52.577507f, -16.271389f, -52.434723f, -16.096947f, -52.44056f, -16.042503f, -52.33056f, -15.876945f, -52.239174f, -15.875557f, -52.037506f, -15.801111f, -51.965836f, -15.800556f, -51.876945f, -15.630835f, -51.779724f, -15.534168f, -51.788612f, -15.481945f, -51.703613f, -15.175556f, -51.636673f, -14.997223f, -51.412506f, -14.962502f, -51.318336f, -15.021389f, -51.257225f, -14.891111f, -51.097504f, -14.530834f, -50.96778f, -14.405556f, -50.99917f, -14.119722f, -50.92695f, -14.118273f, -50.92341f);
            TimezoneMapper.poly[31] = new TzPolygon(-13.462453f, -61.857124f, -13.539249f, -61.824284f, -13.494131f, -61.577538f, -13.554156f, -61.485764f, -13.473181f, -61.329777f, -13.525144f, -61.249054f, -13.520514f, -61.139336f, -13.474869f, -61.126755f, -13.463427f, -61.069042f, -13.477406f, -61.028282f, -13.541252f, -61.04353f, -13.538824f, -60.962227f, -13.686163f, -60.792664f, -13.800958f, -60.467716f, -13.886312f, -60.46804f, -13.992455f, -60.378464f, -14.092428f, -60.468105f, -14.118273f, -60.472027f, -14.118273f, -61.857124f);
            TimezoneMapper.poly[32] = new TzPolygon(-14.118273f, -60.472027f, -14.092428f, -60.468105f, -13.992455f, -60.378464f, -13.886312f, -60.46804f, -13.800958f, -60.467716f, -13.691502f, -60.752197f, -13.425835f, -60.38195f, -13.047779f, -60.266945f, -12.884724f, -60.048058f, -12.714445f, -59.997505f, -12.598057f, -59.89473f, -12.408056f, -59.82084f, -12.267502f, -59.887222f, -12.124428f, -59.888954f, -12.124428f, -50.92341f, -14.118273f, -50.92341f);
            TimezoneMapper.poly[33] = new TzPolygon(-44.961937f, -71.66992f, -44.90075f, -72.04735f, -44.768257f, -72.06971f, -44.78472f, -71.63175f, -44.735786f, -71.47536f, -44.80802f, -71.30264f, -44.73931f, -71.219f, -44.605824f, -71.20947f, -44.52303f, -71.11016f, -44.961937f, -71.11016f);
            TimezoneMapper.poly[34] = new TzPolygon(-44.52303f, -71.11016f, -44.424725f, -71.22403f, -44.406082f, -71.806435f, -44.376446f, -71.83716f, -44.212086f, -71.79031f, -44.105453f, -71.84404f, -43.938183f, -71.6576f, -43.80951f, -71.75487f, -43.660248f, -71.58293f, -43.630005f, -71.693405f, -43.54514f, -71.77494f, -43.55196f, -71.86035f, -43.484837f, -71.84613f, -43.45695f, -71.93305f, -43.32774f, -71.89136f, -43.317333f, -71.73392f, -43.211407f, -71.72886f, -43.17142f, -71.739624f, -43.085655f, -71.97871f, -42.92873f, -72.10292f, -42.599533f, -72.13353f, -42.49351f, -72.02883f, -42.364952f, -72.094986f, -42.364952f, -71.11016f);
            TimezoneMapper.poly[35] = new TzPolygon(-32.746323f, -53.073933f, -33.52294f, -53.073933f, -33.750706f, -53.368324f, -33.689034f, -53.52446f, -33.16332f, -53.51495f, -33.05384f, -53.433838f, -32.93585f, -53.243134f, -32.910892f, -53.294228f);
            TimezoneMapper.poly[36] = new TzPolygon(-32.746323f, -53.073933f, -32.605328f, -53.24374f, -32.582527f, -53.38982f, -32.479908f, -53.459667f, -32.431534f, -53.59493f, -32.156906f, -53.729744f, -32.10408f, -53.71415f, -32.05776f, -53.826855f, -31.91941f, -53.964783f, -31.905582f, -54.09551f, -31.665781f, -54.46569f, -31.540012f, -54.49112f, -31.44847f, -54.590782f, -31.446281f, -54.835564f, -31.392017f, -54.94468f, -31.284863f, -55.014458f, -31.32566f, -55.0912f, -31.256735f, -55.245102f, -31.037638f, -55.374077f, -30.847813f, -55.593533f, -30.857454f, -55.635387f, -30.956448f, -55.657135f, -31.079561f, -55.869793f, -31.062643f, -56.007816f, -30.78441f, -56.0214f, -30.738832f, -56.12743f, -30.614134f, -56.17693f, -30.49527f, -56.38393f, -30.387398f, -56.459232f, -30.364494f, -56.544647f, -30.312326f, -56.539963f, -30.302534f, -56.61723f, -30.205074f, -56.64434f, -30.16646f, -56.776314f, -30.106834f, -56.80361f, -30.087744f, -57.068336f, -30.281454f, -57.206776f, -30.259735f, -57.313053f, -30.302553f, -57.395607f, -30.274714f, -57.538372f, -30.179155f, -57.617966f, -30.143063f, -57.504837f, -30.082224f, -57.434975f, -30.082224f, -53.073933f);
            TimezoneMapper.poly[37] = new TzPolygon(-13.139934f, -48.69024f, -13.18861f, -48.72513f, -13.18525f, -48.79803f, -12.75778f, -48.9085f, -12.63933f, -48.97537f, -12.62161f, -49.05218f, -12.55672f, -49.04892f, -12.7685f, -49.21833f, -13.14181f, -49.32309f, -13.15616f, -49.36321f, -12.95875f, -49.82238f, -12.93363f, -50.07426f, -12.8439f, -50.36849f, -12.57829f, -50.25704f, -12.4708f, -50.255f, -12.5397f, -50.3578f, -12.844881f, -50.48409f, -12.812223f, -50.59556f, -12.656389f, -50.625557f, -12.605278f, -50.677505f, -12.384167f, -50.613617f, -12.124428f, -50.668015f, -12.124428f, -48.69024f);
            TimezoneMapper.poly[38] = new TzPolygon(-12.124428f, -50.668015f, -12.384167f, -50.613617f, -12.628056f, -50.673615f, -12.656389f, -50.625557f, -12.810556f, -50.59806f, -12.87639f, -50.477226f, -13.077223f, -50.59028f, -13.296946f, -50.591667f, -13.537779f, -50.75834f, -13.668056f, -50.789726f, -13.718613f, -50.87056f, -14.08639f, -50.84556f, -14.118273f, -50.92341f, -12.124428f, -50.92341f);
            TimezoneMapper.poly[39] = new TzPolygon(-13.139934f, -48.69024f, -12.124428f, -48.69024f, -12.124428f, -46.33733f, -12.398056f, -46.33278f, -12.504723f, -46.158615f, -12.600279f, -46.15695f, -12.789247f, -46.23939f, -12.87818f, -46.5828f, -13.03171f, -46.85843f, -13.09461f, -47.24751f, -13.3008f, -47.5389f, -13.1811f, -47.5897f, -13.10024f, -47.68462f, -13.30698f, -47.71754f, -13.31232f, -47.76144f, -13.1775f, -48.1767f, -13.0328f, -48.1553f, -13.04463f, -48.62193f);
            TimezoneMapper.poly[40] = new TzPolygon(-14.936896f, -45.453236f, -14.934168f, -45.55667f, -15.147501f, -45.747223f, -15.122223f, -45.916946f, -15.243057f, -46.08139f, -15.0f, -45.974724f, -14.861113f, -46.02112f, -14.70389f, -45.91278f, -14.502224f, -45.97084f, -14.360279f, -45.91639f, -14.189724f, -46.12584f, -13.88139f, -46.258896f, -13.656668f, -46.27278f, -13.424168f, -46.202507f, -13.30139f, -46.07389f, -13.250557f, -46.081947f, -13.209446f, -46.182785f, -13.061668f, -46.16639f, -12.958334f, -46.060562f, -12.779167f, -46.24417f, -12.600279f, -46.15695f, -12.495001f, -46.162224f, -12.431112f, -46.310562f, -12.343334f, -46.34556f, -12.124428f, -46.333138f, -12.124428f, -45.453236f);
            TzPolygon[] tzPolygonArr = TimezoneMapper.poly;
            float[] fArr = new float[HwCallManagerReference.HWBuffer.BUFFER_SIZE];
            // fill-array-data instruction
            fArr[0] = -18.332932f;
            fArr[1] = -39.65679f;
            fArr[2] = -18.01028f;
            fArr[3] = -40.18834f;
            fArr[4] = -17.93f;
            fArr[5] = -40.230835f;
            fArr[6] = -17.82917f;
            fArr[7] = -40.183334f;
            fArr[8] = -17.751114f;
            fArr[9] = -40.216667f;
            fArr[10] = -17.633057f;
            fArr[11] = -40.376396f;
            fArr[12] = -17.562778f;
            fArr[13] = -40.403336f;
            fArr[14] = -17.55917f;
            fArr[15] = -40.48806f;
            fArr[16] = -17.430836f;
            fArr[17] = -40.483063f;
            fArr[18] = -17.425556f;
            fArr[19] = -40.53945f;
            fArr[20] = -17.359726f;
            fArr[21] = -40.515556f;
            fArr[22] = -17.398613f;
            fArr[23] = -40.61f;
            fArr[24] = -16.920834f;
            fArr[25] = -40.52112f;
            fArr[26] = -16.867226f;
            fArr[27] = -40.476112f;
            fArr[28] = -16.884724f;
            fArr[29] = -40.30417f;
            fArr[30] = -16.844448f;
            fArr[31] = -40.245834f;
            fArr[32] = -16.755558f;
            fArr[33] = -40.28334f;
            fArr[34] = -16.583336f;
            fArr[35] = -40.26973f;
            fArr[36] = -16.549168f;
            fArr[37] = -40.139168f;
            fArr[38] = -16.483059f;
            fArr[39] = -40.130836f;
            fArr[40] = -16.321392f;
            fArr[41] = -39.94667f;
            fArr[42] = -16.151947f;
            fArr[43] = -39.86139f;
            fArr[44] = -16.002224f;
            fArr[45] = -39.933617f;
            fArr[46] = -15.89889f;
            fArr[47] = -40.173058f;
            fArr[48] = -15.823057f;
            fArr[49] = -40.21334f;
            fArr[50] = -15.773056f;
            fArr[51] = -40.47056f;
            fArr[52] = -15.802223f;
            fArr[53] = -40.542503f;
            fArr[54] = -15.720001f;
            fArr[55] = -40.63417f;
            fArr[56] = -15.744167f;
            fArr[57] = -40.75389f;
            fArr[58] = -15.684168f;
            fArr[59] = -40.81195f;
            fArr[60] = -15.672224f;
            fArr[61] = -40.93389f;
            fArr[62] = -15.784445f;
            fArr[63] = -41.146667f;
            fArr[64] = -15.751112f;
            fArr[65] = -41.327507f;
            fArr[66] = -15.498611f;
            fArr[67] = -41.35695f;
            fArr[68] = -15.109167f;
            fArr[69] = -41.79084f;
            fArr[70] = -15.176111f;
            fArr[71] = -41.947227f;
            fArr[72] = -15.183613f;
            fArr[73] = -42.091667f;
            fArr[74] = -15.105278f;
            fArr[75] = -42.16639f;
            fArr[76] = -15.108334f;
            fArr[77] = -42.266945f;
            fArr[78] = -14.931667f;
            fArr[79] = -42.563614f;
            fArr[80] = -14.940834f;
            fArr[81] = -42.636116f;
            fArr[82] = -14.683334f;
            fArr[83] = -42.94278f;
            fArr[84] = -14.635557f;
            fArr[85] = -43.151672f;
            fArr[86] = -14.6525f;
            fArr[87] = -43.28195f;
            fArr[88] = -14.788612f;
            fArr[89] = -43.4925f;
            fArr[90] = -14.73889f;
            fArr[91] = -43.70862f;
            fArr[92] = -14.643333f;
            fArr[93] = -43.87056f;
            fArr[94] = -14.515556f;
            fArr[95] = -43.87195f;
            fArr[96] = -14.342232f;
            fArr[97] = -43.789444f;
            fArr[98] = -14.236113f;
            fArr[99] = -44.213615f;
            fArr[100] = -14.244167f;
            fArr[101] = -44.321396f;
            fArr[102] = -14.39889f;
            fArr[103] = -44.660835f;
            fArr[104] = -14.515556f;
            fArr[105] = -44.836395f;
            fArr[106] = -14.601112f;
            fArr[107] = -44.880836f;
            fArr[108] = -14.715557f;
            fArr[109] = -45.091667f;
            fArr[110] = -14.731668f;
            fArr[111] = -45.21306f;
            fArr[112] = -14.936945f;
            fArr[113] = -45.451393f;
            fArr[114] = -14.936896f;
            fArr[115] = -45.453236f;
            fArr[116] = -12.124428f;
            fArr[117] = -45.453236f;
            fArr[118] = -12.124428f;
            fArr[119] = -39.65679f;
            tzPolygonArr[41] = new TzPolygon(fArr);
            TimezoneMapper.poly[42] = new TzPolygon(-10.938203f, -69.96559f, -11.04221f, -70.15838f, -11.070407f, -70.308716f, -11.038018f, -70.43848f, -10.934457f, -70.52003f, -10.999892f, -70.620865f, -9.82079f, -70.624374f, -9.765681f, -70.53693f, -9.562513f, -70.600685f, -9.571807f, -70.55501f, -9.533077f, -70.57123f, -9.50499f, -70.50576f, -9.423923f, -70.49684f, -9.441596f, -70.59545f, -9.748659f, -70.96323f, -9.816863f, -70.99363f, -9.871908f, -71.157684f, -9.967955f, -71.21454f, -10.000378f, -72.179756f, -9.798193f, -72.15152f, -9.751422f, -72.26254f, -9.616149f, -72.25287f, -9.494834f, -72.35678f, -9.491327f, -72.5204f, -9.411629f, -72.71613f, -9.411645f, -73.20057f, -9.23669f, -73.07511f, -9.223105f, -73.00961f, -9.178299f, -73.02616f, -9.146063f, -72.95775f, -8.985424f, -72.942085f, -8.90579f, -73.0595f, -8.698244f, -73.166626f, -8.690625f, -73.25364f, -8.616658f, -73.3413f, -8.474128f, -73.33131f, -8.349349f, -73.54261f, -8.268299f, -73.52414f, -8.240333f, -73.58723f, -8.03447f, -73.61598f, -7.909289f, -73.772385f, -7.861796f, -73.76283f, -7.85777f, -73.67805f, -7.781493f, -73.67951f, -7.717088f, -73.818474f, -7.534207f, -73.98282f, -7.474086f, -73.91013f, -7.357299f, -73.951225f, -7.389232f, -73.86313f, -7.353753f, -73.853004f, -7.311821f, -73.69553f, -7.114283f, -73.79893f, -7.603333f, -72.63779f, -8.160557f, -70.35722f, -8.369236f, -69.96559f);
            TimezoneMapper.poly[43] = new TzPolygon(-8.369236f, -69.96559f, -8.160557f, -70.35722f, -7.603333f, -72.63779f, -7.114283f, -73.79893f, -6.936004f, -73.76074f, -6.754025f, -73.63865f, -6.593249f, -73.352615f, -6.588794f, -73.22752f, -6.510502f, -73.13539f, -6.410998f, -73.10395f, -6.149736f, -73.24752f, -6.075534f, -73.241005f, -6.075534f, -69.96559f);
            TimezoneMapper.poly[44] = new TzPolygon(-9.621224f, -67.356094f, -9.537873f, -67.55774f, -9.099981f, -67.754585f, -9.099981f, -67.356094f);
            TimezoneMapper.poly[45] = new TzPolygon(-9.099981f, -68.57842f, -9.621032f, -67.356094f, -10.373344f, -67.356094f, -10.37151f, -67.41449f, -10.449774f, -67.44314f, -10.52308f, -67.59352f, -10.70659f, -67.71538f, -10.662381f, -67.81092f, -10.660545f, -68.02066f, -10.726215f, -68.11908f, -10.986765f, -68.28334f, -11.143052f, -68.738495f, -11.132725f, -68.77481f, -11.006585f, -68.750626f, -10.989958f, -68.786606f, -11.023437f, -68.870674f, -10.928989f, -69.3929f, -10.975946f, -69.717636f, -10.929839f, -69.769325f, -10.920966f, -69.93364f, -10.938203f, -69.96559f, -9.099981f, -69.96559f);
            TimezoneMapper.poly[46] = new TzPolygon(-9.099981f, -67.754585f, -9.537873f, -67.55774f, -9.099981f, -68.57889f);
            TimezoneMapper.poly[47] = new TzPolygon(-10.373344f, -67.356094f, -10.374251f, -67.32719f, -10.316207f, -67.31515f, -10.339109f, -67.18588f, -10.254179f, -67.029045f, -10.080641f, -66.88339f, -9.934791f, -66.62318f, -9.89316f, -66.616264f, -9.89528f, -66.42207f, -9.831524f, -66.32279f, -9.827606f, -66.20501f, -9.785023f, -66.16126f, -9.8094f, -66.100914f, -9.772218f, -66.06422f, -9.806714f, -66.01839f, -9.756597f, -65.87239f, -9.793812f, -65.85994f, -9.750115f, -65.80975f, -9.786165f, -65.7982f, -9.727339f, -65.77677f, -9.770289f, -65.748405f, -9.748788f, -65.688576f, -9.80292f, -65.700424f, -9.778728f, -65.66905f, -9.834455f, -65.562904f, -9.680567f, -65.43382f, -9.715076f, -65.359665f, -9.85074f, -65.28971f, -9.937713f, -65.32691f, -10.225882f, -65.28074f, -10.479697f, -65.43104f, -10.643403f, -65.42495f, -10.672103f, -65.37486f, -10.814088f, -65.39498f, -10.873999f, -65.31431f, -10.980406f, -65.2893f, -11.043823f, -65.33638f, -11.105047f, -65.317566f, -11.166992f, -65.3905f, -11.239082f, -65.34947f, -11.269267f, -65.38455f, -11.340607f, -65.3104f, -11.392063f, -65.33806f, -11.499572f, -65.299034f, -11.521255f, -65.21768f, -11.602897f, -65.204414f, -11.622048f, -65.16079f, -11.745192f, -65.20118f, -11.776802f, -65.1348f, -11.697483f, -65.106125f, -11.741384f, -65.11199f, -11.75661f, -65.04752f, -11.999366f, -64.993f, -12.035035f, -64.79786f, -12.124428f, -64.7466f, -12.124428f, -67.356094f);
            TimezoneMapper.poly[48] = new TzPolygon(-9.621224f, -67.356094f, -9.099981f, -67.356094f, -9.099981f, -64.91367f, -9.108334f, -64.923065f, -9.224167f, -64.90611f, -9.433056f, -65.076675f, -9.396389f, -65.16362f, -9.249723f, -65.21918f, -9.32139f, -65.34778f, -9.4575f, -65.447235f, -9.411945f, -65.56778f, -9.574446f, -65.75029f, -9.4025f, -65.953064f, -9.399168f, -66.39917f, -9.51889f, -66.39584f, -9.634445f, -66.49306f, -9.675556f, -66.62195f, -9.753334f, -66.68973f, -9.741945f, -66.745285f, -9.838335f, -66.83084f);
            TimezoneMapper.poly[49] = new TzPolygon(-9.621032f, -67.356094f, -9.933536f, -66.62299f, -10.080641f, -66.88339f, -10.254179f, -67.029045f, -10.339109f, -67.18588f, -10.316207f, -67.31515f, -10.374251f, -67.32719f, -10.373344f, -67.356094f);
            TimezoneMapper.poly[50] = new TzPolygon(-8.369236f, -69.96559f, -9.066389f, -68.65723f, -9.099981f, -68.57842f, -9.099981f, -69.96559f);
            TimezoneMapper.poly[51] = new TzPolygon(-6.075534f, -69.11413f, -9.099981f, -67.754585f, -9.099981f, -68.57889f, -9.066389f, -68.65723f, -8.369236f, -69.96559f, -6.075534f, -69.96559f);
            TimezoneMapper.poly[52] = new TzPolygon(-9.099981f, -64.7466f, -9.099981f, -64.91367f, -8.985834f, -64.78528f, -8.993941f, -64.7466f);
            TimezoneMapper.poly[53] = new TzPolygon(-6.075534f, -69.11413f, -4.225544f, -69.94573f, -1.477611f, -69.452515f, -1.333702f, -69.36835f, -0.997919f, -69.42193f, -0.93641f, -69.50787f, -0.754771f, -69.60874f, -0.648225f, -69.551125f, -0.516707f, -69.5933f, -0.315769f, -69.90787f, -0.134779f, -70.06802f, -0.026641f, -70.0632f, -0.026641f, -64.7466f, -6.075534f, -64.7466f);
            TimezoneMapper.poly[54] = new TzPolygon(-6.075534f, -69.11413f, -6.075534f, -73.241005f, -6.036728f, -73.2376f, -6.00554f, -73.18689f, -5.868042f, -73.152145f, -5.657653f, -72.95985f, -5.466101f, -72.95904f, -5.271058f, -72.86043f, -5.144009f, -72.86868f, -5.051294f, -72.72891f, -5.051067f, -72.62092f, -4.99424f, -72.60943f, -4.933682f, -72.52259f, -4.952075f, -72.48396f, -4.890558f, -72.4701f, -4.900901f, -72.41555f, -4.779973f, -72.32725f, -4.798799f, -72.27135f, -4.711623f, -72.14019f, -4.734656f, -72.125175f, -4.622751f, -72.0419f, -4.645379f, -72.00561f, -4.60808f, -71.94733f, -4.529864f, -71.919075f, -4.537737f, -71.885345f, -4.51468f, -71.90571f, -4.502906f, -71.76465f, -4.467003f, -71.75126f, -4.510228f, -71.70665f, -4.470958f, -71.65578f, -4.502782f, -71.648895f, -4.466942f, -71.6169f, -4.509994f, -71.634674f, -4.528489f, -71.60249f, -4.463025f, -71.53717f, -4.485116f, -71.49354f, -4.437187f, -71.507515f, -4.435384f, -71.412636f, -4.476455f, -71.434006f, -4.426182f, -71.34569f, -4.46343f, -71.30938f, -4.417793f, -71.31945f, -4.438925f, -71.27576f, -4.383639f, -71.26728f, -4.378686f, -71.200806f, -4.423379f, -71.19372f, -4.381007f, -71.14878f, -4.385564f, -70.99681f, -4.342616f, -70.9916f, -4.381441f, -70.93577f, -4.224252f, -70.814064f, -4.218067f, -70.84551f, -4.155879f, -70.75544f, -4.204496f, -70.67858f, -4.122895f, -70.62908f, -4.175806f, -70.633545f, -4.171512f, -70.563576f, -4.209821f, -70.57288f, -4.135607f, -70.550735f, -4.148835f, -70.507f, -4.198311f, -70.50514f, -4.131001f, -70.43232f, -4.178426f, -70.3401f, -4.142276f, -70.32199f, -4.170878f, -70.285614f, -4.28774f, -70.29178f, -4.272726f, -70.24789f, -4.304113f, -70.26529f, -4.298046f, -70.21535f, -4.361327f, -70.195244f, -4.256014f, -70.10736f, -4.291994f, -70.042496f, -4.314863f, -70.07888f, -4.366479f, -70.03f, -4.386697f, -69.95688f, -4.225544f, -69.94573f);
            TimezoneMapper.poly[55] = new TzPolygon(-12.124428f, -64.69972f, -12.098567f, -64.68261f, -12.124428f, -64.68261f);
            TimezoneMapper.poly[56] = new TzPolygon(-12.124428f, -64.674255f, -12.098567f, -64.68261f, -12.098567f, -64.674255f);
            TimezoneMapper.poly[57] = new TzPolygon(-8.993941f, -64.7466f, -9.009102f, -64.674255f, -12.098567f, -64.674255f, -12.098567f, -64.7466f);
            TimezoneMapper.poly[58] = new TzPolygon(-7.178177f, -60.489967f, -8.761196f, -60.489967f, -8.768612f, -61.611343f, -8.702223f, -61.625f, -8.681112f, -61.726395f, -8.742779f, -61.83889f, -8.848612f, -61.867783f, -8.875557f, -61.91639f, -8.796946f, -62.021667f, -8.7775f, -62.13417f, -8.600557f, -62.169724f, -8.584446f, -62.30056f, -8.364723f, -62.39028f, -8.366667f, -62.543335f, -8.275835f, -62.55945f, -8.240002f, -62.623337f, -8.054724f, -62.733612f, -8.000557f, -62.850838f, -8.000557f, -63.538338f, -8.080002f, -63.591667f, -8.16889f, -63.585556f, -8.200001f, -63.744446f, -8.279167f, -63.73639f, -8.321945f, -63.910835f, -8.428612f, -63.990562f, -8.554445f, -63.9225f, -8.69278f, -64.00917f, -8.723333f, -64.09056f, -8.694168f, -64.13722f, -8.967779f, -64.125565f, -8.930557f, -64.17473f, -8.934446f, -64.37361f, -8.974445f, -64.418625f, -8.958057f, -64.504456f, -9.027224f, -64.58778f, -9.009102f, -64.674255f, -7.178177f, -64.674255f);
            TimezoneMapper.poly[59] = new TzPolygon(-11.001619f, -60.489967f, -11.000834f, -61.52723f, -10.782223f, -61.523056f, -10.7775f, -61.4775f, -10.716391f, -61.461113f, -10.690001f, -61.507782f, -10.425835f, -61.46945f, -10.144724f, -61.583336f, -9.875f, -61.517227f, -9.726946f, -61.57084f, -9.626945f, -61.4775f, -9.468334f, -61.556396f, -9.403891f, -61.544174f, -9.322779f, -61.61528f, -9.239723f, -61.596115f, -9.226112f, -61.527504f, -9.105835f, -61.560562f, -8.903891f, -61.482506f, -8.768612f, -61.611343f, -8.761196f, -60.489967f);
            TimezoneMapper.poly[60] = new TzPolygon(-7.178177f, -58.191296f, -7.339168f, -58.13556f, -7.449722f, -58.22278f, -7.622223f, -58.223335f, -7.818056f, -58.379723f, -8.105001f, -58.30417f, -8.246668f, -58.33362f, -8.412224f, -58.44056f, -8.541668f, -58.41723f, -8.701345f, -58.46821f, -8.748335f, -58.54528f, -8.761196f, -60.489967f, -7.178177f, -60.489967f);
            TimezoneMapper.poly[61] = new TzPolygon(-9.489585f, -56.305687f, -9.480907f, -56.44586f, -9.374168f, -56.678337f, -9.401945f, -56.776947f, -9.263334f, -56.847504f, -9.228889f, -57.059174f, -9.055557f, -57.09056f, -8.951946f, -57.30945f, -8.782778f, -57.48945f, -8.757223f, -57.596115f, -8.446112f, -57.683334f, -8.2075f, -57.63806f, -8.035833f, -57.78945f, -7.667778f, -57.903336f, -7.408334f, -58.06028f, -7.303056f, -58.17195f, -7.178177f, -58.20037f, -7.178177f, -56.305687f);
            TimezoneMapper.poly[62] = new TzPolygon(-11.001619f, -60.489967f, -11.001667f, -60.42695f, -11.044724f, -60.446945f, -11.112501f, -60.370003f, -11.075556f, -60.28945f, -11.142223f, -59.996117f, -11.384724f, -59.90973f, -11.598333f, -60.10389f, -11.753334f, -60.10167f, -11.898335f, -60.06945f, -11.905556f, -59.98667f, -12.106668f, -59.889168f, -12.124428f, -59.888634f, -12.124428f, -60.489967f);
            TimezoneMapper.poly[63] = new TzPolygon(-7.178177f, -56.305687f, -7.178177f, -58.20037f, -7.101667f, -58.21778f, -6.923612f, -58.41639f, -6.723612f, -58.47223f, -6.483334f, -58.287224f, -2.353333f, -56.371674f, -2.303611f, -56.399445f, -2.231925f, -56.305687f);
            TimezoneMapper.poly[64] = new TzPolygon(-2.231925f, -56.476837f, -2.231925f, -56.305687f, -2.260556f, -56.468056f);
            TimezoneMapper.poly[65] = new TzPolygon(1.633574f, -64.674255f, 1.633574f, -64.09979f, 1.494397f, -64.186325f, 1.452302f, -64.29341f, 1.38001f, -64.343735f, 1.418941f, -64.381645f, 1.527704f, -64.3442f, 1.527459f, -64.38705f, 1.469739f, -64.48967f, 1.355377f, -64.52997f, 1.285774f, -64.63761f, 1.270204f, -64.674255f);
            TimezoneMapper.poly[66] = new TzPolygon(1.633574f, -62.77964f, 1.633574f, -60.489967f, -0.771659f, -60.489967f, -0.830556f, -60.513336f, -0.843056f, -60.757225f, -0.686111f, -60.807503f, -0.553056f, -60.926117f, -0.489444f, -61.116394f, -0.493611f, -61.218613f, -0.555278f, -61.25306f, -0.650556f, -61.474167f, -0.908889f, -61.582504f, -1.046526f, -61.559097f, -1.285052f, -61.623764f, -1.426773f, -61.595497f, -1.357222f, -61.748894f, -1.379167f, -61.872223f, -1.15f, -62.01973f, -1.052778f, -62.199173f, -0.968333f, -62.238335f, -0.778611f, -62.50695f, -0.694445f, -62.498894f, -0.718333f, -62.375282f, -0.596944f, -62.303894f, -0.461389f, -62.37667f, -0.307778f, -62.380005f, -0.213889f, -62.487785f, 0.002778f, -62.593338f, 0.095f, -62.524727f, 0.246667f, -62.569725f, 0.518611f, -62.480278f, 0.723611f, -62.545837f, 0.825f, -62.438896f, 0.918056f, -62.503334f, 1.408056f, -62.608894f, 1.610833f, -62.79667f);
            TimezoneMapper.poly[67] = new TzPolygon(1.633574f, -64.09979f, 1.707904f, -64.05358f, 1.93279f, -64.05655f, 1.96693f, -64.01804f, 1.989471f, -63.78098f, 2.124453f, -63.603935f, 2.154375f, -63.41923f, 2.223162f, -63.374554f, 2.157222f, -63.286118f, 2.181389f, -63.13862f, 2.105833f, -63.127785f, 2.042222f, -63.058617f, 2.021667f, -62.847504f, 1.936666f, -62.69667f, 1.833333f, -62.735f, 1.725833f, -62.710556f, 1.633574f, -62.77964f);
            TimezoneMapper.poly[68] = new TzPolygon(1.633574f, -62.77964f, 1.725833f, -62.710556f, 1.833333f, -62.735f, 1.936666f, -62.69667f, 2.021667f, -62.847504f, 2.042222f, -63.058617f, 2.105833f, -63.127785f, 2.181389f, -63.13862f, 2.174444f, -63.329445f, 2.223162f, -63.374554f, 2.289375f, -63.347633f, 2.44196f, -63.382137f, 2.402544f, -63.755684f, 2.509034f, -64.03103f, 2.771976f, -63.98673f, 3.153313f, -64.216385f, 3.476489f, -64.2364f, 3.634167f, -64.18564f, 3.796913f, -64.324165f, 3.881664f, -64.542755f, 4.063378f, -64.674255f, 4.274995f, -64.674255f, 4.155511f, -64.5631f, 4.175617f, -64.134735f, 3.9518f, -64.01685f, 3.929609f, -63.966766f, 4.018855f, -63.806293f, 4.009868f, -63.622784f, 3.909822f, -63.45527f, 4.02576f, -63.41083f, 4.039675f, -63.35759f, 3.951764f, -63.2032f, 3.872282f, -63.189156f, 3.583891f, -62.860306f, 3.641585f, -62.748917f, 3.724185f, -62.70509f, 3.933027f, -62.760284f, 4.026201f, -62.74102f, 4.055555f, -62.63498f, 4.019764f, -62.556587f, 4.135681f, -62.512238f, 4.186056f, -62.417156f, 4.083876f, -62.128765f, 4.180132f, -61.960762f, 4.106117f, -61.926704f, 4.261574f, -61.723473f, 4.273405f, -61.527603f, 4.398041f, -61.506702f, 4.471363f, -61.27445f, 4.549633f, -61.293354f, 4.508224f, -61.14061f, 4.549125f, -60.951878f, 4.707716f, -60.907093f, 4.777442f, -60.73077f, 4.950978f, -60.58978f, 5.209053f, -60.74411f, 5.182377f, -60.489967f, 1.633574f, -60.489967f);
            TimezoneMapper.poly[69] = new TzPolygon(1.633574f, -59.463852f, 1.633574f, -58.00164f, 1.500673f, -58.00851f, 1.501474f, -58.12984f, 1.557843f, -58.166733f, 1.590138f, -58.32467f, 1.53643f, -58.38098f, 1.470647f, -58.38206f, 1.458016f, -58.497753f, 1.317985f, -58.46773f, 1.264406f, -58.50711f, 1.2883f, -58.69612f, 1.207113f, -58.73541f, 1.17508f, -58.80411f, 1.233826f, -58.90361f, 1.29851f, -58.913773f, 1.397388f, -59.25275f);
            TimezoneMapper.poly[70] = new TzPolygon(-0.766862f, -60.489967f, -0.744444f, -60.47917f, -0.689445f, -60.312782f, -0.463333f, -60.386673f, -0.215278f, -60.30667f, 0.229444f, -60.055283f, 0.225278f, -58.870598f, -0.0825f, -58.84195f, -0.266111f, -58.870003f, -0.372222f, -58.845f, -0.44f, -58.72445f, -0.631389f, -58.752502f, -0.766945f, -58.628334f, -0.764167f, -58.559174f, -0.836111f, -58.451668f, -1.018333f, -58.41195f, -1.110278f, -58.327507f, -1.131389f, -58.243896f, -1.233611f, -58.171112f, -1.42f, -57.91278f, -1.451945f, -57.804726f, -1.581389f, -57.678337f, -1.713611f, -57.39556f, -1.713056f, -57.25167f, -1.764445f, -57.253616f, -1.810833f, -57.082504f, -1.945278f, -56.99334f, -2.028889f, -56.844727f, -2.028056f, -56.756393f, -2.178056f, -56.75f, -2.225833f, -56.634445f, -2.161111f, -56.493057f, -2.231925f, -56.48761f, -2.231925f, -60.489967f);
            TimezoneMapper.poly[71] = new TzPolygon(1.633574f, -60.489967f, 1.633574f, -59.463852f, 1.397388f, -59.25275f, 1.309811f, -58.972088f, 0.225278f, -58.870598f, 0.229444f, -60.055283f, -0.215278f, -60.30667f, -0.463333f, -60.386673f, -0.681944f, -60.307228f, -0.744444f, -60.47917f, -0.771659f, -60.489967f);
            TimezoneMapper.poly[72] = new TzPolygon(1.633574f, -58.00164f, 1.665301f, -58.0f, 1.645396f, -57.91931f, 1.718252f, -57.7704f, 1.700689f, -57.544678f, 1.894799f, -57.433746f, 1.990075f, -57.3024f, 1.943744f, -57.247444f, 2.021116f, -57.08518f, 1.918004f, -57.0116f, 1.863873f, -56.800163f, 1.919642f, -56.723507f, 1.944612f, -56.453724f, 1.911571f, -56.305687f, 1.633574f, -56.305687f);
            TimezoneMapper.poly[73] = new TzPolygon(5.182377f, -60.489967f, 5.176927f, -60.438046f, 5.264877f, -60.203144f, 5.226561f, -60.176434f, 5.243235f, -60.12056f, 5.148698f, -60.085426f, 5.073079f, -59.969418f, 4.703719f, -60.03018f, 4.52116f, -60.16332f, 4.509368f, -59.927216f, 4.381637f, -59.673985f, 4.191309f, -59.726337f, 4.137568f, -59.6284f, 4.077602f, -59.655487f, 3.939903f, -59.51631f, 3.90113f, -59.580925f, 3.795994f, -59.59363f, 3.776826f, -59.66592f, 3.702916f, -59.663784f, 3.565341f, -59.86348f, 3.495798f, -59.80438f, 3.426218f, -59.8368f, 3.354882f, -59.79868f, 3.062944f, -59.959f, 2.682594f, -59.98975f, 2.364981f, -59.897724f, 2.276611f, -59.72432f, 1.847162f, -59.748257f, 1.848647f, -59.654217f, 1.763815f, -59.668766f, 1.728509f, -59.548702f, 1.633574f, -59.463852f, 1.633574f, -60.489967f);
            TimezoneMapper.poly[74] = new TzPolygon(-2.821393f, -79.047226f, -2.821393f, -77.30605f, -2.997541f, -77.82405f, -3.219529f, -78.090576f, -3.354459f, -78.18971f, -3.522452f, -78.13655f, -3.534078f, -78.231964f, -3.428695f, -78.248566f, -3.429298f, -78.31948f, -3.787185f, -78.3766f, -3.9492f, -78.49901f, -4.129074f, -78.57099f, -4.255292f, -78.57174f, -4.319386f, -78.62747f, -4.496534f, -78.62084f, -4.653685f, -78.73607f, -4.720123f, -78.88143f, -4.998823f, -79.036835f, -4.99736f, -79.047226f);
            TimezoneMapper.poly[75] = new TzPolygon(1.43902f, -78.8215f, 1.43902f, -77.30605f, 0.365865f, -77.30605f, 0.361558f, -77.40308f, 0.394914f, -77.464554f, 0.63114f, -77.50088f, 0.728262f, -77.697075f, 0.789353f, -77.651695f, 0.81724f, -77.67379f, 0.817894f, -77.97404f, 1.26522f, -78.64261f);
            TimezoneMapper.poly[76] = new TzPolygon(0.365865f, -77.30605f, 0.373783f, -77.1277f, 0.273499f, -77.03617f, 0.241906f, -76.84743f, 0.285671f, -76.72886f, 0.232862f, -76.59544f, 0.247996f, -76.40438f, 0.384156f, -76.40261f, 0.441074f, -76.26915f, 0.349403f, -76.040146f, 0.084979f, -75.79058f, 0.073483f, -75.617386f, 0.04368f, -75.57353f, -0.012861f, -75.57774f, -0.093691f, -75.2882f, -0.133623f, -75.30429f, -0.149056f, -75.38765f, -0.107056f, -75.62058f, -0.25192f, -75.62618f, -0.326349f, -75.53599f, -0.328686f, -75.44915f, -0.423733f, -75.41451f, -0.534989f, -75.24495f, -0.710882f, -75.28017f, -0.970688f, -75.184586f, -0.946761f, -75.35867f, -1.536144f, -75.53837f, -1.58611f, -75.619064f, -2.128139f, -76.0447f, -2.590585f, -76.62732f, -2.821393f, -77.30605f);
            TimezoneMapper.poly[77] = new TzPolygon(-0.026641f, -70.0632f, 0.553025f, -70.03735f, 0.584742f, -69.79837f, 0.672076f, -69.664055f, 0.623053f, -69.605736f, 0.734351f, -69.46821f, 0.620539f, -69.35712f, 0.648283f, -69.294975f, 0.609028f, -69.28765f, 0.601751f, -69.20154f, 0.638649f, -69.18642f, 0.642764f, -69.11193f, 0.730424f, -69.1835f, 0.868457f, -69.136986f, 1.025505f, -69.25672f, 1.064014f, -69.34206f, 1.023687f, -69.41638f, 1.073588f, -69.608215f, 1.055641f, -69.70034f, 1.097106f, -69.720764f, 1.069752f, -69.85407f, 1.714919f, -69.855194f, 1.724115f, -69.660774f, 1.775197f, -69.55574f, 1.725671f, -69.39456f, 1.730217f, -68.12775f, 1.841322f, -68.28955f, 2.037632f, -68.205444f, 1.784398f, -68.02657f, 1.750918f, -67.92353f, 1.812339f, -67.79746f, 2.075562f, -67.577995f, 2.14129f, -67.4098f, 1.732161f, -67.11682f, 1.532232f, -67.068146f, 1.170079f, -67.096664f, 1.228388f, -66.854576f, 0.738179f, -66.32214f, 0.754315f, -66.09506f, 0.810287f, -65.95151f, 0.939189f, -65.853676f, 1.004873f, -65.732765f, 1.007569f, -65.582855f, 0.877193f, -65.470375f, 0.705491f, -65.55657f, 0.626311f, -65.5111f, 0.698704f, -65.39319f, 0.916445f, -65.30489f, 0.942731f, -65.15247f, 1.114882f, -65.120705f, 1.143756f, -65.05523f, 1.111735f, -64.968025f, 1.248545f, -64.90301f, 1.237145f, -64.84323f, 1.313104f, -64.7827f, 1.250451f, -64.72073f, 1.270204f, -64.674255f, -0.026641f, -64.674255f);
            TimezoneMapper.poly[78] = new TzPolygon(4.063378f, -64.674255f, 4.244174f, -64.805084f, 4.329103f, -64.79049f, 4.335419f, -64.73046f, 4.274995f, -64.674255f);
            TimezoneMapper.poly[79] = new TzPolygon(-0.409487f, -56.305687f, -0.409487f, -52.63199f, -0.572778f, -52.627228f, -0.658333f, -52.523613f, -0.879722f, -52.519173f, -0.875556f, -52.388893f, -1.065556f, -52.3675f, -1.142222f, -52.25167f, -1.155278f, -52.11389f, -1.220278f, -52.06945f, -1.135278f, -51.9775f, -1.157693f, -51.84624f, -1.181115f, -51.910168f, -1.434417f, -51.954487f, -1.524329f, -51.88947f, -1.613239f, -52.204006f, -1.698821f, -52.281467f, -2.085176f, -52.21257f, -2.46717f, -52.01198f, -2.762822f, -52.01372f, -3.077662f, -51.84453f, -3.11342f, -51.766037f, -3.175341f, -51.760803f, -3.227669f, -51.81139f, -3.392502f, -51.8393f, -3.456167f, -51.94308f, -3.245111f, -52.065178f, -3.215459f, -52.16024f, -3.405584f, -52.222164f, -3.661642f, -52.37845f, -3.831184f, -52.590553f, -4.178642f, -52.637997f, -4.320974f, -52.732185f, -4.468189f, -52.65404f, -5.016586f, -52.917076f, -5.381486f, -52.862656f, -5.451954f, -52.71823f, -5.86081f, -52.53404f, -6.003142f, -52.543804f, -6.172684f, -52.446823f, -6.333157f, -52.429382f, -6.551538f, -52.243793f, -6.555027f, -52.081226f, -6.72736f, -51.977966f, -6.909461f, -52.065178f, -6.976441f, -52.39031f, -7.178776f, -52.595436f, -7.321805f, -52.592644f, -7.489255f, -52.72521f, -7.537396f, -52.70079f, -7.935786f, -52.878704f, -8.188356f, -52.86126f, -8.264406f, -52.790096f, -8.46674f, -52.792187f, -8.601397f, -52.62125f, -8.593025f, -52.455894f, -8.701169f, -52.343563f, -8.786987f, -52.325424f, -8.901411f, -52.13844f, -9.071651f, -52.066574f, -9.179795f, -52.087505f, -9.189563f, -52.044945f, -9.27259f, -52.014942f, -9.440737f, -52.158672f, -9.636793f, -52.205418f, -9.65284f, -52.32891f, -9.586558f, -52.536827f, -9.717024f, -52.631844f, -9.489585f, -56.305687f);
            TimezoneMapper.poly[80] = new TzPolygon(-9.489103f, -56.305687f, -9.776915f, -51.38282f, -12.124428f, -51.38282f, -12.124428f, -56.305687f);
            TimezoneMapper.poly[81] = new TzPolygon(1.993773f, -56.305687f, 1.993773f, -55.910675f, 1.889429f, -55.90648f, 1.831145f, -55.99497f, 1.919644f, -56.305687f);
            TimezoneMapper.poly[82] = new TzPolygon(-0.409487f, -52.63199f, -0.382222f, -52.632782f, -0.178056f, -52.812225f, -0.186389f, -52.92334f, 0.035f, -53.01584f, 0.2475f, -53.04139f, 0.392222f, -53.130005f, 0.742222f, -53.117226f, 0.790278f, -53.2825f, 0.942222f, -53.418617f, 1.123055f, -53.466118f, 1.169722f, -53.460556f, 1.1875f, -53.405838f, 1.264444f, -53.438614f, 1.243889f, -53.54889f, 1.367778f, -53.553062f, 1.363055f, -53.656395f, 1.429444f, -53.66417f, 1.385556f, -53.86139f, 1.520555f, -53.98806f, 1.508055f, -54.086945f, 1.613055f, -54.101395f, 1.763055f, -54.361115f, 1.768611f, -54.735f, 1.986944f, -54.76306f, 1.993773f, -54.771427f, 1.993773f, -51.61395f, -0.409487f, -51.61395f);
            TimezoneMapper.poly[83] = new TzPolygon(1.993773f, -55.910675f, 2.040511f, -55.912556f, 2.176807f, -56.047974f, 2.226485f, -56.048256f, 2.262532f, -56.133774f, 2.352037f, -56.090218f, 2.344939f, -56.044266f, 2.404775f, -55.99518f, 2.524166f, -55.97815f, 2.401214f, -55.738914f, 2.442657f, -55.48084f, 2.407456f, -55.370182f, 2.516481f, -55.335915f, 2.494835f, -55.2392f, 2.575308f, -55.137f, 2.522386f, -55.102707f, 2.632479f, -54.93989f, 2.555437f, -54.969406f, 2.444036f, -54.86976f, 2.458907f, -54.779675f, 2.415f, -54.73445f, 2.274722f, -54.711113f, 2.163611f, -54.78945f, 2.076666f, -54.750282f, 2.017777f, -54.800835f, 1.993773f, -54.771427f);
            TimezoneMapper.poly[84] = new TzPolygon(1.993773f, -54.771427f, 2.017777f, -54.800835f, 2.076666f, -54.750282f, 2.163611f, -54.78945f, 2.274722f, -54.711113f, 2.425833f, -54.73723f, 2.458907f, -54.779675f, 2.476372f, -54.75315f, 2.45078f, -54.68915f, 2.40044f, -54.724384f, 2.33164f, -54.696304f, 2.336314f, -54.549095f, 2.274894f, -54.54137f, 2.226049f, -54.47033f, 2.214883f, -54.372974f, 2.157763f, -54.314297f, 2.177571f, -54.181725f, 2.127094f, -54.114597f, 2.196785f, -54.078136f, 2.36406f, -53.78421f, 2.286815f, -53.694283f, 2.332068f, -53.471794f, 2.27231f, -53.397404f, 2.292224f, -53.273476f, 2.222989f, -53.188385f, 2.224297f, -53.083473f, 2.169751f, -52.987366f, 2.192049f, -52.901848f, 2.285073f, -52.853474f, 2.373743f, -52.672802f, 2.523372f, -52.554726f, 2.645522f, -52.555515f, 2.912198f, -52.38153f, 3.073557f, -52.327984f, 3.133795f, -52.347202f, 3.29574f, -52.192142f, 3.780786f, -51.92019f, 3.889529f, -51.795406f, 3.996106f, -51.753742f, 4.049279f, -51.658463f, 4.165922f, -51.61395f, 1.993773f, -51.61395f);
            TimezoneMapper.poly[85] = new TzPolygon(-4.373424f, -80.23094f, -4.470112f, -80.33467f, -4.482847f, -80.38831f, -4.436912f, -80.443405f, -4.370888f, -80.44346f, -4.200802f, -80.30513f, -4.194497f, -80.44397f, -4.054242f, -80.47536f, -3.984066f, -80.39755f, -4.017959f, -80.28613f, -3.970826f, -80.23094f);
            TimezoneMapper.poly[86] = new TzPolygon(-3.508836f, -80.23094f, -3.465259f, -80.239456f, -3.462763f, -80.23094f);
            TimezoneMapper.poly[87] = new TzPolygon(-3.462763f, -80.34232f, -3.404815f, -80.30429f, -3.433036f, -80.23094f, -3.462763f, -80.23094f);
            TimezoneMapper.poly[88] = new TzPolygon(-4.285207f, -80.13631f, -4.373424f, -80.23094f, -4.285207f, -80.23094f);
            TimezoneMapper.poly[89] = new TzPolygon(-4.99736f, -79.047226f, -4.967359f, -79.26035f, -4.798517f, -79.41622f, -4.528807f, -79.48183f, -4.527785f, -79.54116f, -4.434916f, -79.63059f, -4.486319f, -79.8119f, -4.393893f, -79.894875f, -4.285207f, -80.13631f, -4.285207f, -79.047226f);
            TimezoneMapper.poly[90] = new TzPolygon(-3.433036f, -80.23094f, -3.437626f, -80.21902f, -3.90478f, -80.15361f, -3.970826f, -80.23094f);
            TimezoneMapper.poly[91] = new TzPolygon(-7.745792f, -49.20737f, -7.791389f, -49.15445f, -7.996098f, -49.20737f);
            TimezoneMapper.poly[92] = new TzPolygon(-6.998296f, -49.20737f, -7.25139f, -49.18611f, -7.283911f, -49.20737f);
            TimezoneMapper.poly[93] = new TzPolygon(-6.697871f, -49.20737f, -6.697871f, -48.77796f, -6.798889f, -49.038895f, -6.92283f, -49.20737f);
            TimezoneMapper.poly[94] = new TzPolygon(-12.124428f, -46.333138f, -12.098612f, -46.331673f, -11.92f, -46.06778f, -11.835279f, -46.15667f, -11.841946f, -46.26139f, -11.753613f, -46.272507f, -11.659445f, -46.090836f, -11.609724f, -46.08f, -11.544724f, -46.19056f, -11.495556f, -46.442223f, -11.32889f, -46.572227f, -10.980001f, -46.38028f, -10.903334f, -46.22973f, -10.765001f, -46.30945f, -10.582779f, -46.085007f, -10.460279f, -45.84556f, -10.330833f, -45.760002f, -10.33439f, -45.708477f, -12.124428f, -45.708477f);
            TimezoneMapper.poly[95] = new TzPolygon(-10.326993f, -45.708477f, -10.317688f, -45.945984f, -10.195557f, -46.119728f, -10.178612f, -46.34278f, -10.013889f, -46.467506f, -9.873335f, -46.48889f, -9.752224f, -46.668335f, -9.682779f, -46.665283f, -9.652779f, -46.599167f, -9.512222f, -46.537506f, -9.385557f, -46.790558f, -9.109724f, -46.882225f, -9.044168f, -47.08139f, -8.976667f, -47.06417f, -8.822779f, -46.899727f, -8.736389f, -46.923058f, -8.467224f, -46.827507f, -8.406389f, -46.718895f, -8.397223f, -46.48723f, -8.315279f, -46.547226f, -8.068056f, -46.46306f, -7.966111f, -46.48667f, -7.898056f, -46.60028f, -7.959445f, -46.870834f, -8.043056f, -47.020004f, -7.735001f, -47.28167f, -7.639723f, -47.313614f, -7.658334f, -47.348335f, -7.533056f, -47.402504f, -7.536112f, -47.46528f, -7.446667f, -47.503616f, -7.446112f, -47.589172f, -7.38f, -47.479446f, -7.289722f, -47.498894f, -7.272779f, -47.58445f, -7.308612f, -47.640556f, -7.211112f, -47.736946f, -7.161389f, -47.726112f, -7.157223f, -47.63778f, -6.988056f, -47.501396f, -6.671945f, -47.483894f, -6.276112f, -47.370003f, -6.106112f, -47.427223f, -5.870556f, -47.413338f, -5.746945f, -47.478615f, -5.542778f, -47.487503f, -5.475556f, -47.543617f, -5.385f, -47.83889f, -5.255556f, -47.89528f, -5.266945f, -48.15223f, -5.1675f, -48.33806f, -5.199445f, -48.519173f, -5.328334f, -48.602226f, -5.303889f, -48.663338f, -5.353333f, -48.750557f, -5.164167f, -48.51597f, -5.164167f, -45.708477f);
            TimezoneMapper.poly[96] = new TzPolygon(-5.164167f, -48.77489f, -5.164167f, -48.51597f, -5.353333f, -48.750557f, -5.410833f, -48.569168f, -5.398612f, -48.371674f, -5.516389f, -48.298058f, -5.610001f, -48.142784f, -5.697779f, -48.16889f, -5.759167f, -48.302223f, -5.936389f, -48.22834f, -5.983056f, -48.33667f, -6.103333f, -48.28945f, -6.182222f, -48.434174f, -6.364722f, -48.38195f, -6.355f, -48.50695f, -6.437778f, -48.603058f, -6.525001f, -48.662224f, -6.655001f, -48.66723f, -6.696681f, -48.77489f);
            TimezoneMapper.poly[97] = new TzPolygon(-5.164167f, -48.51597f, -5.155559f, -48.50529f, -5.164167f, -48.50529f);
            TimezoneMapper.poly[98] = new TzPolygon(-5.155559f, -48.50529f, -4.593334f, -47.80806f, -4.610556f, -47.66195f, -4.555f, -47.5875f, -4.333889f, -47.454445f, -4.260556f, -47.356674f, -4.077223f, -47.320282f, -3.893889f, -47.086113f, -3.569167f, -47.034172f, -3.524167f, -46.968338f, -3.401667f, -46.94445f, -3.329167f, -46.820282f, -3.18f, -46.716667f, -3.006111f, -46.636116f, -2.894722f, -46.65528f, -2.855f, -46.568893f, -2.722778f, -46.661392f, -2.635278f, -46.522224f, -2.551111f, -46.48806f, -2.540833f, -46.428894f, -2.366111f, -46.403893f, -2.376389f, -46.453056f, -2.253889f, -46.423615f, -2.254445f, -46.37195f, -2.175f, -46.291946f, -1.925833f, -46.210556f, -1.8175f, -46.210556f, -1.809445f, -46.301117f, -1.745833f, -46.31945f, -1.688056f, -46.20556f, -1.486111f, -46.203613f, -1.353889f, -46.12195f, -1.299722f, -46.16945f, -1.282514f, -46.155396f, -1.282514f, -45.885113f, -1.293271f, -45.89069f, -1.282514f, -45.877148f, -1.282514f, -45.838676f, -1.294247f, -45.84254f, -1.328639f, -45.81603f, -1.282514f, -45.796253f, -1.282514f, -45.730766f, -1.344332f, -45.708477f, -5.164167f, -45.708477f, -5.164167f, -48.50529f);
            TimezoneMapper.poly[99] = new TzPolygon(-1.282514f, -46.155396f, -1.15275f, -46.04942f, -1.282514f, -46.04942f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer2 {
        private Initializer2() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[100] = new TzPolygon(-8.085886f, -49.909996f, -8.085886f, -49.230583f, -8.378056f, -49.306114f, -8.441668f, -49.387505f, -8.852222f, -49.597504f, -8.929724f, -49.766113f, -9.126065f, -49.909996f);
            TimezoneMapper.poly[101] = new TzPolygon(-7.745792f, -49.20737f, -7.653056f, -49.315002f, -7.529723f, -49.368057f, -7.283911f, -49.20737f);
            TimezoneMapper.poly[102] = new TzPolygon(-7.041938f, -49.20737f, -6.936667f, -49.218056f, -6.92845f, -49.20737f);
            TimezoneMapper.poly[103] = new TzPolygon(-3.804812f, -40.2493f, -7.415676f, -40.2493f, -7.399723f, -40.651947f, -7.486945f, -40.695557f, -7.655834f, -40.61834f, -7.756111f, -40.663063f, -7.860834f, -40.5325f, -8.108334f, -40.578896f, -8.245834f, -40.753334f, -8.363335f, -40.824173f, -8.343334f, -40.885834f, -8.428057f, -40.903954f, -8.415556f, -41.01584f, -8.630556f, -41.203056f, -8.711945f, -41.370834f, -8.934168f, -41.49778f, -8.976946f, -41.56556f, -8.981112f, -41.740005f, -9.139446f, -41.73278f, -9.253056f, -41.849724f, -9.204445f, -42.03945f, -9.295557f, -42.149445f, -9.307222f, -42.313614f, -9.497778f, -42.483612f, -9.484167f, -42.58528f, -9.569168f, -42.62584f, -9.52f, -42.748894f, -9.551668f, -42.848618f, -9.512222f, -42.935562f, -9.449722f, -42.93611f, -9.370279f, -43.12361f, -9.419724f, -43.18445f, -9.432222f, -43.35917f, -9.299168f, -43.459167f, -9.362223f, -43.52112f, -9.345001f, -43.643333f, -9.450834f, -43.704727f, -9.431667f, -43.821114f, -9.491945f, -43.83362f, -9.863611f, -43.68f, -10.064445f, -43.70723f, -10.440556f, -43.91889f, -10.448057f, -43.989174f, -10.589445f, -44.118614f, -10.630001f, -44.21695f, -10.585835f, -44.403336f, -10.635834f, -44.552505f, -10.778334f, -44.750282f, -10.872501f, -44.803062f, -10.896946f, -45.057228f, -10.778334f, -45.326393f, -10.618612f, -45.44056f, -10.468056f, -45.47973f, -10.331112f, -45.60334f, -10.326993f, -45.708477f, -3.804812f, -45.708477f);
            TimezoneMapper.poly[104] = new TzPolygon(-7.415676f, -40.2493f, -9.085158f, -40.2493f, -9.364723f, -40.334167f, -9.36278f, -40.41723f, -9.488335f, -40.63417f, -9.452951f, -40.7583f, -9.345001f, -40.68917f, -9.22139f, -40.702507f, -9.154167f, -40.85389f, -8.845001f, -40.897224f, -8.829723f, -40.973618f, -8.771946f, -41.002228f, -8.785557f, -41.093895f, -8.713612f, -41.11306f, -8.711945f, -41.370834f, -8.630556f, -41.203056f, -8.415556f, -41.01584f, -8.428057f, -40.903954f, -8.343334f, -40.885834f, -8.363335f, -40.824173f, -8.245834f, -40.753334f, -8.108334f, -40.578896f, -7.860834f, -40.5325f, -7.756111f, -40.663063f, -7.655834f, -40.61834f, -7.486945f, -40.695557f, -7.399723f, -40.651947f);
            TimezoneMapper.poly[105] = new TzPolygon(-8.954312f, -37.538258f, -8.992779f, -37.662224f, -8.853334f, -37.754173f, -8.892502f, -37.81167f, -8.99889f, -37.820007f, -9.155834f, -37.981674f, -9.194445f, -38.10917f, -9.332411f, -38.23471f, -9.136112f, -38.313896f, -9.025278f, -38.286667f, -8.988611f, -38.31806f, -9.037224f, -38.408615f, -9.007502f, -38.47445f, -8.947224f, -38.510002f, -8.845835f, -38.473618f, -8.830833f, -38.568336f, -8.967779f, -38.607506f, -8.978613f, -38.643616f, -8.845835f, -38.70195f, -8.7875f, -38.79528f, -8.79389f, -38.955f, -8.685278f, -39.21528f, -8.5625f, -39.284447f, -8.532223f, -39.38639f, -8.659445f, -39.68611f, -8.786112f, -39.68528f, -8.829445f, -39.89167f, -8.958889f, -39.88862f, -9.045557f, -39.968895f, -9.105001f, -40.115837f, -9.069168f, -40.244446f, -9.085158f, -40.2493f, -7.96462f, -40.2493f, -7.96462f, -37.538258f);
            TimezoneMapper.poly[106] = new TzPolygon(-8.954312f, -37.538258f, -11.540355f, -37.538258f, -11.521667f, -37.63417f, -11.576113f, -37.67334f, -11.515001f, -37.808334f, -11.403612f, -37.894173f, -11.378056f, -37.99945f, -11.212778f, -37.98667f, -11.167778f, -38.067505f, -11.021946f, -38.10778f, -10.913334f, -38.23278f, -10.832779f, -38.24556f, -10.714169f, -38.19278f, -10.712223f, -38.07695f, -10.761391f, -37.998894f, -10.696667f, -37.841393f, -10.625341f, -37.778f, -10.570835f, -37.82556f, -10.413057f, -37.843338f, -10.292501f, -37.77945f, -10.086668f, -37.776115f, -9.910002f, -37.898056f, -9.891111f, -37.95639f, -9.744446f, -38.03028f, -9.646667f, -37.988617f, -9.616945f, -38.048058f, -9.480278f, -38.014725f, -9.41889f, -38.203896f, -9.330833f, -38.23584f, -9.194445f, -38.10917f, -9.155834f, -37.981674f, -8.99889f, -37.820007f, -8.892502f, -37.81167f, -8.855835f, -37.765007f, -8.992779f, -37.662224f);
            TimezoneMapper.poly[107] = new TzPolygon(-7.96462f, -37.324394f, -7.96462f, -36.62501f, -8.013056f, -36.66028f, -8.093889f, -36.630836f, -8.216946f, -36.767227f, -8.283611f, -36.962784f, -8.175556f, -37.125557f, -7.981945f, -37.14028f, -7.9925f, -37.348335f);
            TimezoneMapper.poly[108] = new TzPolygon(-11.540355f, -37.538258f, -11.454485f, -37.361378f, -11.825295f, -37.538258f);
            TimezoneMapper.poly[109] = new TzPolygon(-8.954312f, -37.538258f, -7.96462f, -37.538258f, -7.96462f, -37.324394f, -7.9925f, -37.348335f, -7.981945f, -37.14028f, -8.175556f, -37.125557f, -8.282778f, -36.968895f, -8.216946f, -36.767227f, -8.093889f, -36.630836f, -8.013056f, -36.66028f, -7.96462f, -36.62501f, -7.96462f, -34.829456f, -8.003979f, -34.827213f, -8.913097f, -35.149227f, -8.886391f, -35.410004f, -8.828056f, -35.477783f, -8.92639f, -35.727783f, -8.865557f, -35.795563f, -8.912779f, -35.972504f, -8.886391f, -36.00528f, -9.209167f, -36.358337f, -9.207779f, -36.447502f, -9.301945f, -36.587227f, -9.273335f, -36.88945f, -9.355835f, -36.938614f, -9.207224f, -37.23417f, -8.946667f, -37.51362f);
            TimezoneMapper.poly[110] = new TzPolygon(-7.415676f, -40.2493f, -7.419445f, -40.154167f, -7.354445f, -39.917503f, -7.371112f, -39.66584f, -7.481668f, -39.528336f, -7.551667f, -39.341667f, -7.677778f, -39.25695f, -7.745001f, -39.115005f, -7.857779f, -39.070282f, -7.816945f, -39.007782f, -7.854167f, -38.970284f, -7.659167f, -38.81195f, -7.61889f, -38.65806f, -7.685278f, -38.641113f, -7.769167f, -38.522224f, -7.7025f, -38.341118f, -7.830278f, -38.303062f, -7.851111f, -38.249725f, -7.765834f, -38.14834f, -7.822779f, -38.081116f, -7.752778f, -38.056396f, -7.769444f, -37.95806f, -7.665278f, -37.875282f, -7.475556f, -37.5475f, -7.364445f, -37.480278f, -7.362779f, -37.401115f, -7.268889f, -37.255005f, -7.394167f, -37.022507f, -7.489445f, -36.9925f, -7.579167f, -37.204727f, -7.786112f, -37.171112f, -7.96462f, -37.324394f, -7.96462f, -40.2493f);
            TimezoneMapper.poly[111] = new TzPolygon(-7.96462f, -36.62501f, -7.961945f, -36.623062f, -7.915833f, -36.562782f, -7.913611f, -36.45056f, -7.811945f, -36.412224f, -7.826112f, -36.262222f, -7.780556f, -36.211113f, -7.823056f, -36.160835f, -7.771389f, -36.100563f, -7.831389f, -36.06861f, -7.842501f, -35.92417f, -7.801945f, -35.91889f, -7.803889f, -35.85917f, -7.725f, -35.89028f, -7.757778f, -35.855278f, -7.654445f, -35.555557f, -7.458889f, -35.501945f, -7.465834f, -35.38195f, -7.382778f, -35.28528f, -7.411945f, -35.053894f, -7.52f, -34.968613f, -7.551979f, -34.839684f, -7.622023f, -34.80903f, -7.69046f, -34.845066f, -7.96462f, -34.829456f);
            TimezoneMapper.poly[112] = new TzPolygon(24.329374f, -106.33564f, 24.283054f, -106.4014f, 24.311108f, -106.53195f, 24.588608f, -106.65085f, 24.727892f, -106.7774f, 24.727892f, -106.33564f);
            TimezoneMapper.poly[113] = new TzPolygon(23.787209f, -105.89513f, 23.789444f, -105.89639f, 24.055275f, -105.90807f, 24.21194f, -106.00223f, 24.390274f, -106.24918f, 24.329374f, -106.33564f, 24.727892f, -106.33564f, 24.727892f, -105.89513f);
            TimezoneMapper.poly[114] = new TzPolygon(20.89851f, -105.42842f, 20.735115f, -105.23929f, 20.675657f, -105.27859f, 20.771147f, -105.34713f, 20.794825f, -105.54663f);
            TimezoneMapper.poly[115] = new TzPolygon(23.787209f, -105.89513f, 24.727892f, -105.89513f, 24.727892f, -102.50731f, 24.45222f, -102.51363f, 24.476109f, -103.2675f, 24.275555f, -103.6125f, 24.182499f, -103.60057f, 24.073055f, -103.85085f, 23.861385f, -103.87556f, 23.67472f, -103.80833f, 23.623333f, -103.91972f, 23.447498f, -104.07806f, 23.195831f, -104.09612f, 23.062775f, -104.20113f, 22.421944f, -104.25862f, 22.319212f, -104.31143f, 22.451385f, -104.345f, 22.41111f, -104.49139f, 22.4725f, -104.6125f, 22.624443f, -104.66057f, 22.676666f, -104.75751f, 22.541943f, -104.98529f, 22.678886f, -105.0f, 22.783054f, -104.88f, 22.92472f, -104.91473f, 23.039719f, -105.17195f, 23.067455f, -105.40208f, 23.14722f, -105.41667f, 23.144444f, -105.5289f, 23.27972f, -105.67751f, 23.47472f, -105.71806f);
            TimezoneMapper.poly[116] = new TzPolygon(22.718983f, -105.89513f, 23.787209f, -105.89513f, 23.47472f, -105.71806f, 23.28722f, -105.68417f, 23.144444f, -105.5289f, 23.14722f, -105.41667f, 23.067455f, -105.40208f, 23.039719f, -105.17195f, 22.927776f, -104.91751f, 22.783054f, -104.88f, 22.678886f, -105.0f, 22.548332f, -105.0f, 22.676666f, -104.75751f, 22.624443f, -104.66057f, 22.4725f, -104.6125f, 22.41111f, -104.49139f, 22.451385f, -104.345f, 22.319212f, -104.31143f, 22.076385f, -104.40279f, 21.785831f, -104.09361f, 21.547222f, -104.20723f, 21.374973f, -103.94493f, 21.287777f, -103.9614f, 21.211388f, -104.04251f, 21.177734f, -104.22766f, 20.978054f, -104.21001f, 20.860832f, -104.27501f, 20.708054f, -104.28557f, 20.916111f, -104.535f, 20.92361f, -104.625f, 21.012775f, -104.72195f, 21.018608f, -104.79529f, 20.925552f, -104.9489f, 20.925278f, -105.08334f, 20.735115f, -105.23929f, 20.89851f, -105.42842f, 21.062578f, -105.24357f, 21.219957f, -105.21799f, 21.338266f, -105.25556f, 21.458124f, -105.19528f, 21.524822f, -105.241135f, 21.620245f, -105.45475f, 21.989218f, -105.65478f, 22.377317f, -105.684616f, 22.677776f, -105.837654f);
            TimezoneMapper.poly[117] = new TzPolygon(15.954266f, -97.68436f, 15.965178f, -97.79529f, 16.231947f, -98.214966f, 16.324604f, -98.56893f, 16.545958f, -98.770004f, 16.532644f, -98.87094f, 16.587828f, -98.9818f, 16.700722f, -99.69207f, 16.894114f, -99.96442f, 17.268505f, -101.05811f, 17.31283f, -101.06075f, 17.415651f, -101.19564f, 17.531147f, -101.460335f, 17.622038f, -101.50737f, 17.664167f, -101.65895f, 17.874493f, -101.7978f, 17.965527f, -101.959076f, 17.982733f, -102.05417f, 17.909813f, -102.18634f, 18.006529f, -102.50731f, 24.606182f, -102.50731f, 24.606182f, -101.0113f, 24.575275f, -100.94667f, 24.597221f, -100.86057f, 24.48611f, -100.74057f, 24.220554f, -100.56668f, 23.940552f, -100.59418f, 23.747498f, -100.41724f, 23.610832f, -100.46806f, 23.234165f, -100.44417f, 23.194443f, -100.37195f, 23.248055f, -100.30334f, 23.241108f, -100.05724f, 23.12083f, -100.08917f, 23.12722f, -100.02585f, 23.00222f, -99.908066f, 22.838886f, -100.04861f, 22.776943f, -99.97696f, 22.614998f, -99.53056f, 22.729721f, -99.54945f, 22.63361f, -99.42723f, 22.679443f, -99.37807f, 22.448887f, -99.23584f, 22.340553f, -98.879456f, 22.423332f, -98.66972f, 22.440277f, -98.490005f, 22.39833f, -98.3139f, 22.468609f, -98.293335f, 22.472221f, -98.20056f, 22.382774f, -98.10196f, 22.326385f, -97.913345f, 22.271942f, -97.92557f, 22.221313f, -97.87613f, 22.264013f, -97.786766f, 21.964146f, -97.70421f, 21.937117f, -97.68436f);
            TimezoneMapper.poly[118] = new TzPolygon(24.606182f, -101.0113f, 24.630276f, -101.06168f, 24.727892f, -101.1596f, 24.727892f, -101.0113f);
            TimezoneMapper.poly[119] = new TzPolygon(24.72826f, -106.777725f, 24.752777f, -106.8f, 24.77333f, -106.88918f, 24.952774f, -107.02112f, 25.260832f, -107.11454f, 25.260832f, -106.777725f);
            TimezoneMapper.poly[120] = new TzPolygon(24.727892f, -102.50731f, 24.82861f, -102.505f, 25.118053f, -102.66585f, 25.155552f, -102.25723f, 25.026943f, -101.83751f, 24.90583f, -101.7464f, 24.858055f, -101.58556f, 24.754444f, -101.57973f, 24.761387f, -101.44528f, 24.82111f, -101.36029f, 24.77861f, -101.32112f, 24.810276f, -101.24223f, 24.727892f, -101.1596f);
            TimezoneMapper.poly[121] = new TzPolygon(25.63632f, -107.10085f, 26.115276f, -107.36639f, 26.200275f, -107.78445f, 26.64f, -107.84668f, 26.819721f, -108.00389f, 26.947498f, -108.03557f, 26.972775f, -108.22057f, 27.059998f, -108.29056f, 27.03083f, -108.40501f, 26.96133f, -108.47134f, 27.053333f, -108.61751f, 27.061386f, -108.62108f, 27.061386f, -107.10085f);
            TimezoneMapper.poly[122] = new TzPolygon(27.061386f, -109.92352f, 27.061386f, -108.61669f, 27.038055f, -108.60417f, 26.976944f, -108.47557f, 26.923054f, -108.46751f, 26.831944f, -108.48639f, 26.338314f, -109.14372f, 26.314888f, -109.25164f, 26.514244f, -109.271126f, 26.659548f, -109.40524f, 26.677876f, -109.69159f, 26.768236f, -109.83939f, 26.99657f, -109.92352f);
            TimezoneMapper.poly[123] = new TzPolygon(32.50963f, -109.04856f, 31.331833f, -109.04998f, 31.330523f, -109.92352f, 32.50963f, -109.92352f);
            TimezoneMapper.poly[124] = new TzPolygon(31.563892f, -107.10085f, 31.583878f, -107.99948f, 31.485987f, -107.96054f, 31.179129f, -107.98543f, 31.08845f, -108.06098f, 31.131586f, -108.23592f, 31.128193f, -108.427315f, 31.064356f, -108.45879f, 31.124352f, -108.60469f, 31.082376f, -108.74843f, 31.204998f, -108.79807f, 31.156944f, -108.83612f, 31.192219f, -108.8914f, 31.330776f, -108.8327f, 31.331347f, -108.205894f, 31.78346f, -108.203476f, 31.784002f, -107.84926f, 31.780737f, -107.10085f);
            TimezoneMapper.poly[125] = new TzPolygon(31.780018f, -107.10085f, 31.78346f, -108.203476f, 31.331347f, -108.205894f, 31.331833f, -109.049965f, 32.50963f, -109.048676f, 32.50963f, -107.10085f);
            TimezoneMapper.poly[126] = new TzPolygon(31.329815f, -109.92352f, 31.330776f, -108.8327f, 31.192219f, -108.8914f, 31.156944f, -108.83612f, 31.204998f, -108.79807f, 30.6325f, -108.735f, 30.575832f, -108.67778f, 29.992775f, -108.55806f, 29.401108f, -108.61362f, 29.400833f, -108.70778f, 28.77111f, -108.6239f, 28.696388f, -108.68945f, 28.289165f, -108.56557f, 28.21222f, -108.65251f, 28.298332f, -108.88084f, 28.299164f, -109.05585f, 28.17583f, -109.14639f, 27.785f, -108.91389f, 27.728611f, -108.82085f, 27.599442f, -108.77501f, 27.519997f, -108.65529f, 27.151943f, -108.66528f, 27.061386f, -108.61669f, 27.061386f, -109.92352f);
            TimezoneMapper.poly[127] = new TzPolygon(29.019276f, -103.95685f, 29.019276f, -103.310844f, 29.000433f, -103.28852f, 28.75188f, -103.44604f, 28.778845f, -103.52782f, 28.829865f, -103.54211f, 28.84573f, -103.64544f, 28.899635f, -103.65999f, 28.88817f, -103.74206f, 28.982801f, -103.8454f, 28.983448f, -103.93203f);
            TimezoneMapper.poly[128] = new TzPolygon(29.019276f, -103.309845f, 29.019276f, -103.09419f, 28.98764f, -103.11527f, 28.984957f, -103.27841f);
            TimezoneMapper.poly[129] = new TzPolygon(29.019276f, -103.09419f, 29.019276f, -103.05771f, 28.717384f, -103.05771f, 28.702139f, -103.086914f, 28.702164f, -103.283714f, 28.760904f, -103.43156f, 29.000433f, -103.28852f, 28.98764f, -103.11527f);
            TimezoneMapper.poly[130] = new TzPolygon(25.528921f, -107.094154f, 25.606087f, -107.08409f, 25.622498f, -106.74028f, 25.789165f, -106.53362f, 26.02111f, -106.52084f, 26.079998f, -106.40306f, 26.1475f, -106.36751f, 26.376389f, -106.45001f, 26.414997f, -106.23946f, 26.75222f, -106.15334f, 26.734997f, -106.09195f, 26.838608f, -106.02751f, 26.654999f, -105.75389f, 26.662777f, -105.63695f, 26.587776f, -105.58528f, 26.458885f, -105.32613f, 26.541386f, -105.13751f, 26.459442f, -105.01001f, 26.492775f, -104.8439f, 26.43333f, -104.79668f, 26.450554f, -104.72557f, 26.351665f, -104.58694f, 26.353886f, -104.53445f, 26.756386f, -104.18834f, 26.728886f, -103.84418f, 26.66108f, -103.63086f, 27.870552f, -103.95473f, 28.760904f, -103.43156f, 28.702164f, -103.283714f, 28.702139f, -103.086914f, 28.717384f, -103.05771f, 25.528921f, -103.05771f);
            TimezoneMapper.poly[131] = new TzPolygon(29.019276f, -103.309845f, 29.040213f, -103.32902f, 29.04136f, -103.42894f, 29.15373f, -103.55269f, 29.178549f, -103.71571f, 29.260885f, -103.782295f, 29.320852f, -104.03947f, 29.392506f, -104.16061f, 29.48166f, -104.20908f, 29.635252f, -104.50618f, 29.926056f, -104.6785f, 30.237196f, -104.705284f, 30.381113f, -104.85247f, 30.56434f, -104.8957f, 30.627632f, -104.983025f, 30.66471f, -104.91411f, 30.813017f, -104.90358f, 32.002937f, -104.92005f, 31.999643f, -103.06416f, 32.50963f, -103.05771f, 29.074041f, -103.05771f, 29.019276f, -103.09419f);
            TimezoneMapper.poly[132] = new TzPolygon(31.565256f, -107.10085f, 31.5531f, -106.615585f, 31.232855f, -106.3481f, 31.171795f, -106.145515f, 31.104189f, -106.11782f, 31.03711f, -105.96752f, 30.932945f, -105.91954f, 30.925213f, -105.79437f, 30.852457f, -105.79976f, 30.755991f, -105.63638f, 30.663021f, -105.60446f, 30.637543f, -105.48695f, 30.563206f, -105.435616f, 30.537771f, -105.30625f, 30.428991f, -105.14842f, 30.28176f, -105.11809f, 30.143017f, -104.97103f, 29.872292f, -104.95156f, 29.453272f, -104.718056f, 29.29063f, -104.503174f, 29.303284f, -104.4319f, 29.178764f, -104.3445f, 29.110697f, -104.17047f, 29.061943f, -104.14764f, 29.042978f, -103.97327f, 29.019276f, -103.95685f, 29.019276f, -107.10085f);
            TimezoneMapper.poly[133] = new TzPolygon(31.780018f, -107.10085f, 32.50963f, -107.10085f, 32.50963f, -103.05771f, 31.999643f, -103.06416f, 32.002937f, -104.92005f, 30.813017f, -104.90358f, 30.66471f, -104.91411f, 30.628256f, -104.98325f, 30.680363f, -105.00295f, 30.796827f, -105.19822f, 30.851542f, -105.39361f, 31.090715f, -105.608635f, 31.170973f, -105.7697f, 31.360714f, -105.94983f, 31.469688f, -106.2056f, 31.729677f, -106.375084f, 31.778206f, -106.52114f);
            TimezoneMapper.poly[134] = new TzPolygon(29.019276f, -103.09419f, 29.074041f, -103.05771f, 29.019276f, -103.05771f);
            TimezoneMapper.poly[135] = new TzPolygon(26.854313f, -97.553566f, 26.844515f, -97.5335f, 26.84271f, -97.534355f);
            TimezoneMapper.poly[136] = new TzPolygon(26.762495f, -97.47694f, 26.797678f, -97.48456f, 26.800837f, -97.483116f, 26.772764f, -97.47694f);
            TimezoneMapper.poly[137] = new TzPolygon(26.857126f, -97.510025f, 26.847223f, -97.49282f, 26.844763f, -97.494484f, 26.854841f, -97.5104f);
            TimezoneMapper.poly[138] = new TzPolygon(25.864368f, -97.47694f, 25.882858f, -97.525116f, 26.031235f, -97.66282f, 26.05368f, -98.20526f, 26.217936f, -98.45148f, 26.2574f, -98.593315f, 26.232075f, -98.66492f, 26.364073f, -98.80686f, 26.419394f, -99.11189f, 26.477089f, -99.09309f, 26.53862f, -99.17171f, 26.790249f, -99.234604f, 26.857126f, -99.298256f, 26.857126f, -97.56666f, 26.847569f, -97.586205f, 26.749369f, -97.47694f);
            TimezoneMapper.poly[139] = new TzPolygon(26.857126f, -103.05771f, 26.857126f, -99.6521f, 26.841002f, -99.613556f, 26.71339f, -99.555786f, 26.72534f, -99.47093f, 26.556913f, -99.40895f, 26.50321f, -99.44729f, 26.470554f, -99.40154f, 26.405634f, -99.42133f, 26.153133f, -99.221664f, 26.167686f, -99.15552f, 26.114412f, -99.10947f, 26.135338f, -99.037994f, 26.087788f, -98.99134f, 26.08048f, -98.87548f, 26.025812f, -98.85224f, 26.010033f, -98.74983f, 25.951506f, -98.700714f, 25.960629f, -98.54389f, 25.841505f, -98.40382f, 25.765957f, -98.18567f, 25.794514f, -97.87343f, 25.73f, -97.73357f, 25.639502f, -97.66822f, 25.576746f, -97.50158f, 25.589432f, -97.47694f, 25.528921f, -97.47694f, 25.528921f, -103.05771f);
            TimezoneMapper.poly[140] = new TzPolygon(26.857126f, -99.298256f, 27.017824f, -99.4512f, 27.247444f, -99.445305f, 27.311853f, -99.53936f, 27.399113f, -99.48883f, 27.563145f, -99.51466f, 27.63418f, -99.59576f, 27.653168f, -99.71119f, 27.794563f, -99.87388f, 27.976643f, -99.93139f, 27.997364f, -99.994255f, 28.141123f, -100.07892f, 28.27747f, -100.28723f, 28.475183f, -100.362465f, 28.49443f, -100.33257f, 28.67237f, -100.50092f, 29.102413f, -100.674675f, 29.442814f, -101.05659f, 29.523605f, -101.25326f, 29.623137f, -101.25363f, 29.570261f, -101.30621f, 29.638956f, -101.29892f, 29.644072f, -101.35961f, 29.767847f, -101.4006f, 29.756044f, -101.53324f, 29.809868f, -101.54302f, 29.751009f, -101.64365f, 29.809793f, -101.81414f, 29.78544f, -102.06992f, 29.879465f, -102.314255f, 29.763218f, -102.38483f, 29.784315f, -102.48269f, 29.743233f, -102.54293f, 29.771544f, -102.563866f, 29.744328f, -102.667465f, 29.521042f, -102.801704f, 29.395704f, -102.81168f, 29.341852f, -102.88f, 29.255547f, -102.90371f, 29.218758f, -102.86524f, 29.172953f, -102.991806f, 29.074041f, -103.05771f, 32.50963f, -103.05771f, 32.50963f, -99.298256f);
            TimezoneMapper.poly[141] = new TzPolygon(28.717384f, -103.05771f, 28.795853f, -102.90739f, 28.895578f, -102.87572f, 29.128206f, -102.59698f, 29.40925f, -102.56298f, 29.435469f, -102.50323f, 29.482046f, -102.5113f, 29.515854f, -102.42208f, 29.484215f, -102.33838f, 29.557915f, -102.210815f, 29.502611f, -102.06721f, 29.532265f, -102.03812f, 29.497149f, -101.93809f, 29.537037f, -101.888596f, 29.481115f, -101.75285f, 29.495209f, -101.580605f, 29.289396f, -101.411835f, 29.278002f, -101.27314f, 29.137041f, -101.17339f, 29.097872f, -101.04183f, 28.985498f, -100.98427f, 28.921873f, -100.8672f, 28.788063f, -100.87173f, 28.602032f, -100.72454f, 28.49122f, -100.71821f, 28.343563f, -100.57151f, 28.268198f, -100.59656f, 28.209055f, -100.50339f, 28.117418f, -100.52043f, 28.105766f, -100.457794f, 28.049015f, -100.46211f, 27.863995f, -100.19704f, 27.581087f, -100.059425f, 27.585169f, -100.00112f, 27.477745f, -99.93306f, 27.457333f, -99.82879f, 27.384556f, -99.76744f, 27.304312f, -99.822105f, 27.030825f, -99.68808f, 26.972008f, -99.73031f, 26.875284f, -99.6955f, 26.857126f, -99.6521f, 26.857126f, -103.05771f);
            TimezoneMapper.poly[142] = new TzPolygon(28.002499f, -112.769585f, 27.998886f, -112.76415f, 28.000336f, -114.203705f, 28.002499f, -114.2021f);
            TimezoneMapper.poly[143] = new TzPolygon(31.44027f, -111.42113f, 32.501625f, -114.81461f, 32.50963f, -114.813866f, 32.50963f, -111.42113f);
            TimezoneMapper.poly[144] = new TzPolygon(31.96829f, -116.79796f, 32.50963f, -116.79796f, 32.50963f, -114.80368f, 32.38841f, -116.40535f, 31.694956f, -116.54172f, 31.685986f, -116.66314f, 31.741388f, -116.73152f, 31.73262f, -116.64274f, 31.846441f, -116.607285f);
            TimezoneMapper.poly[145] = new TzPolygon(29.996002f, -112.74682f, 30.186115f, -112.755226f, 30.26659f, -112.86107f, 30.408913f, -112.86302f, 30.7035f, -113.094284f, 31.05333f, -113.12323f, 30.977306f, -113.08056f, 31.169142f, -113.03586f, 31.230377f, -113.123604f, 31.217794f, -113.157906f, 31.190153f, -113.11119f, 31.327068f, -113.62065f, 31.503302f, -113.66229f, 31.595978f, -113.92797f, 31.656334f, -113.97208f, 31.568125f, -113.99234f, 31.56689f, -113.93956f, 31.491383f, -114.02593f, 31.497679f, -114.169975f, 31.764387f, -114.593025f, 31.764305f, -114.69369f, 31.957062f, -115.028946f, 32.02804f, -114.96495f, 32.230785f, -114.98686f, 32.495926f, -114.81422f, 31.43974f, -111.42113f, 29.996002f, -111.42113f);
            TimezoneMapper.poly[146] = new TzPolygon(32.50963f, -109.92352f, 31.330523f, -109.92352f, 31.328808f, -111.06475f, 31.44027f, -111.42113f, 32.50963f, -111.42113f);
            TimezoneMapper.poly[147] = new TzPolygon(25.260832f, -107.11454f, 25.294441f, -107.12473f, 25.42707f, -107.10744f, 25.260832f, -107.10744f);
            TimezoneMapper.poly[148] = new TzPolygon(32.50963f, -114.813866f, 32.618076f, -114.8037f, 32.74333f, -114.70239f, 32.750896f, -114.53718f, 32.846184f, -114.466f, 33.02543f, -114.509384f, 33.03331f, -114.66067f, 33.088184f, -114.7036f, 33.26474f, -114.67008f, 33.30228f, -114.728806f, 33.409515f, -114.72229f, 33.42346f, -114.63424f, 33.557877f, -114.52468f, 33.706165f, -114.49084f, 33.940723f, -114.53153f, 34.109634f, -114.41492f, 34.26721f, -114.129486f, 34.356014f, -114.1802f, 34.45884f, -114.382996f, 34.7152f, -114.47078f, 34.880173f, -114.63471f, 35.067707f, -114.60365f, 35.106956f, -114.64924f, 35.130695f, -114.579346f, 35.179996f, -114.570724f, 35.50053f, -114.68049f, 35.6081f, -114.655334f, 35.808628f, -114.712746f, 35.871895f, -114.66272f, 35.944523f, -114.73192f, 36.09186f, -114.75301f, 36.150917f, -114.57294f, 36.147324f, -114.396706f, 36.023148f, -114.258125f, 36.03052f, -114.147385f, 36.195095f, -114.04865f, 36.99999f, -114.050964f, 36.999992f, -114.04724f, 32.50963f, -114.04724f);
            TimezoneMapper.poly[149] = new TzPolygon(32.50963f, -117.1147f, 32.534622f, -117.12358f, 32.71676f, -114.71703f, 32.618076f, -114.8037f, 32.50963f, -114.80368f);
            TimezoneMapper.poly[150] = new TzPolygon(37.00426f, -111.08237f, 36.14447f, -111.082855f, 36.123295f, -111.171005f, 37.00426f, -111.34996f);
            TimezoneMapper.poly[151] = new TzPolygon(35.96488f, -111.246925f, 36.051495f, -111.29503f, 36.162804f, -111.20526f, 36.123295f, -111.171005f);
            TimezoneMapper.poly[152] = new TzPolygon(37.00426f, -111.08237f, 35.157917f, -111.08237f, 35.15654f, -111.23646f, 35.49701f, -111.24209f, 35.613194f, -111.31787f, 35.75841f, -111.31558f, 35.75853f, -111.53195f, 35.640182f, -111.55306f, 35.672962f, -111.74084f, 35.746017f, -111.74172f, 35.746346f, -111.68902f, 35.979126f, -111.70568f, 36.00828f, -111.76016f, 36.206963f, -111.75953f, 36.368607f, -111.87387f, 36.39304f, -111.8281f, 36.50417f, -111.84158f, 36.68581f, -111.724724f, 36.670296f, -111.68577f, 36.8555f, -111.60045f, 36.8417f, -111.5459f, 36.87079f, -111.564125f, 36.966995f, -111.376945f, 36.86959f, -111.232796f, 36.990246f, -111.40211f, 37.00426f, -111.345024f);
            TimezoneMapper.poly[153] = new TzPolygon(35.1676f, -111.08237f, 35.1676f, -110.0f, 35.157917f, -111.08237f);
            TimezoneMapper.poly[154] = new TzPolygon(35.71973f, -110.236206f, 35.809635f, -110.1462f, 35.79276f, -110.08342f, 35.702568f, -110.08157f, 35.500008f, -110.75108f);
            TimezoneMapper.poly[155] = new TzPolygon(35.500008f, -110.75108f, 35.500008f, -110.88633f, 35.5784f, -110.80348f, 35.548992f, -111.000694f, 36.281406f, -111.0007f, 36.277718f, -110.8757f, 36.374046f, -110.84503f, 36.4102f, -110.753426f, 36.374996f, -110.72179f, 36.374996f, -110.57729f, 36.43065f, -110.32127f, 36.163868f, -110.444336f, 36.14259f, -110.56925f, 36.041477f, -110.61674f, 36.01575f, -110.50069f, 36.07051f, -110.500694f, 36.09915f, -110.42502f, 36.043076f, -110.40285f, 36.04313f, -110.291725f, 36.000004f, -110.32147f, 35.96525f, -110.16063f, 35.993855f, -110.12416f, 35.911896f, -110.11919f, 35.925056f, -110.01974f, 35.84517f, -110.05472f, 35.84427f, -110.00067f, 35.648975f, -110.00067f, 35.64903f, -110.146416f, 35.57928f, -110.12568f, 35.59085f, -110.27782f);
            TimezoneMapper.poly[156] = new TzPolygon(46.03821f, -118.60326f, 46.03821f, -114.50151f, 46.03257f, -114.505646f, 45.977318f, -114.41284f, 45.88628f, -114.38599f, 45.77731f, -114.565544f, 45.671497f, -114.500114f, 45.470894f, -114.65555f, 45.508602f, -114.795395f, 45.397205f, -114.73325f, 45.394676f, -114.813995f, 45.570072f, -115.20785f, 45.378918f, -115.51012f, 45.469177f, -115.80256f, 45.401085f, -116.21015f, 45.41708f, -116.31511f, 45.86182f, -116.29784f, 46.036537f, -116.61453f, 45.96659f, -116.744606f, 45.826912f, -116.78328f, 45.75208f, -116.546715f, 45.609306f, -116.46164f, 45.028683f, -116.84127f, 44.881615f, -116.855194f, 44.730198f, -117.05739f, 44.48453f, -117.221634f, 44.319607f, -117.19672f, 44.29987f, -117.483765f, 44.387012f, -117.48501f, 44.445923f, -117.59057f, 44.44275f, -117.971565f, 44.27144f, -118.154144f, 44.256397f, -118.23317f, 42.448505f, -118.214355f, 42.45221f, -117.029724f, 42.00031f, -117.029274f, 41.993378f, -114.04038f, 41.537422f, -114.039345f, 37.00426f, -114.04724f, 37.00426f, -118.60326f);
            TimezoneMapper.poly[157] = new TzPolygon(44.368866f, -112.823425f, 44.44395f, -112.82843f, 44.50548f, -112.72069f, 44.449276f, -112.38342f, 44.569122f, -112.28623f, 44.52402f, -112.10492f, 44.566185f, -111.869354f, 44.511135f, -111.822334f, 44.558655f, -111.70125f, 44.540604f, -111.46544f, 44.64209f, -111.515594f, 44.753716f, -111.377525f, 44.622036f, -111.217514f, 44.58032f, -111.22852f, 44.47723f, -111.04873f, 42.000484f, -111.04628f, 41.997635f, -112.24747f);
            TimezoneMapper.poly[158] = new TzPolygon(37.00426f, -111.34996f, 41.790535f, -112.322205f, 41.997635f, -112.24747f, 37.00426f, -110.910576f);
            TimezoneMapper.poly[159] = new TzPolygon(37.00426f, -111.345024f, 41.790535f, -112.322205f, 44.403873f, -112.8917f, 44.456535f, -113.007256f, 44.7754f, -113.13465f, 44.82288f, -113.247505f, 44.788002f, -113.3444f, 44.869984f, -113.45604f, 44.942776f, -113.4969f, 44.958603f, -113.4462f, 45.055286f, -113.451385f, 45.257942f, -113.687416f, 45.519234f, -113.77138f, 45.519623f, -113.83386f, 45.591225f, -113.8006f, 45.61915f, -113.902534f, 45.694187f, -113.93361f, 45.695206f, -114.014404f, 45.590244f, -114.08946f, 45.546688f, -114.247284f, 45.458687f, -114.33496f, 45.562954f, -114.46202f, 45.561474f, -114.564674f, 45.701405f, -114.495056f, 45.77731f, -114.565544f, 45.88628f, -114.38599f, 45.977318f, -114.41284f, 46.03257f, -114.505646f, 46.03821f, -114.50151f, 46.03821f, -110.0f, 37.00426f, -110.0f);
            TimezoneMapper.poly[160] = new TzPolygon(46.03821f, -114.50151f, 46.094448f, -114.46027f, 46.147076f, -114.52436f, 46.173927f, -114.446884f, 46.25929f, -114.47086f, 46.64977f, -114.323975f, 46.637863f, -114.61282f, 46.739887f, -114.67025f, 46.697716f, -114.76395f, 46.76369f, -114.77124f, 46.80341f, -114.899895f, 46.915195f, -114.923256f, 46.970745f, -115.04988f, 47.25811f, -115.320145f, 47.287327f, -115.50294f, 47.374165f, -115.59552f, 47.425583f, -115.75974f, 47.47463f, -115.63491f, 47.54529f, -115.7536f, 47.592598f, -115.68984f, 47.69995f, -115.72541f, 47.97301f, -116.047104f, 48.510468f, -116.048996f, 48.510468f, -114.30163f, 46.03821f, -114.30163f);
            TimezoneMapper.poly[161] = new TzPolygon(49.002666f, -118.60326f, 49.000374f, -116.05072f, 48.510468f, -116.048996f, 48.510468f, -118.60326f);
            TimezoneMapper.poly[162] = new TzPolygon(50.982723f, -117.157776f, 50.9759f, -117.1623f, 50.9619f, -117.0426f, 50.9231f, -117.0691f, 50.7073f, -116.7867f, 50.6495f, -116.5713f, 50.5131f, -116.6924f, 50.331f, -116.6207f, 50.3197f, -116.5419f, 50.2484f, -116.5516f, 50.1338f, -116.4421f, 50.053f, -116.4976f, 49.9974f, -116.4299f, 49.883f, -116.6885f, 49.8028f, -116.6788f, 49.8092f, -116.8697f, 49.6377f, -116.9111f, 49.5892f, -116.8323f, 49.2719f, -116.6837f, 49.1775f, -116.8302f, 49.2135f, -116.861f, 49.1499f, -116.9402f, 49.1558f, -117.0392f, 49.0594f, -117.0386f, 49.064f, -116.9095f, 49.000614f, -116.92137f, 49.00011f, -118.60326f, 50.982723f, -118.60326f);
            TimezoneMapper.poly[163] = new TzPolygon(49.8145f, -116.7058f, 49.7365f, -116.5882f, 49.6421f, -116.6774f, 49.5704f, -116.6832f, 49.5559f, -116.6206f, 49.4722f, -116.6641f, 49.457f, -116.5576f, 49.5338f, -116.4324f, 49.5227f, -116.2969f, 49.3549f, -116.1507f, 49.288f, -116.1669f, 49.2611f, -116.0709f, 49.2916f, -116.0268f, 49.1964f, -116.0027f, 49.166f, -115.8614f, 49.0194f, -115.727f, 49.000328f, -116.04201f, 49.000614f, -116.92137f, 49.064f, -116.9095f, 49.0594f, -117.0386f, 49.1015f, -117.0498f, 49.1558f, -117.0392f, 49.1499f, -116.9402f, 49.2135f, -116.861f, 49.1775f, -116.8302f, 49.2719f, -116.6837f, 49.5892f, -116.8323f, 49.6377f, -116.9111f, 49.8092f, -116.8697f);
            TimezoneMapper.poly[164] = new TzPolygon(48.510468f, -116.048996f, 49.000374f, -116.05072f, 49.00051f, -114.30163f, 48.510468f, -114.30163f);
            TimezoneMapper.poly[165] = new TzPolygon(49.00051f, -114.30163f, 49.00077f, -110.964806f, 48.99935f, -110.0f, 46.03821f, -110.0f, 46.03821f, -114.30163f);
            TimezoneMapper.poly[166] = new TzPolygon(32.50963f, -109.048676f, 35.165997f, -109.04576f, 32.50963f, -109.04576f);
            TimezoneMapper.poly[167] = new TzPolygon(35.1676f, -110.0f, 35.170948f, -109.625725f, 35.257645f, -109.62417f, 35.2315f, -109.19868f, 35.16748f, -109.199326f, 35.165997f, -109.04576f, 35.165997f, -110.0f);
            TimezoneMapper.poly[168] = new TzPolygon(47.5753f, -103.43745f, 47.52705f, -103.43427f, 47.515476f, -103.60963f, 47.5753f, -103.61013f);
            TimezoneMapper.poly[169] = new TzPolygon(32.50963f, -103.05771f, 36.99899f, -103.00087f, 32.50963f, -103.00087f);
            TimezoneMapper.poly[170] = new TzPolygon(36.99489f, -102.4196f, 36.99899f, -103.00087f, 36.99899f, -102.4196f);
            TimezoneMapper.poly[171] = new TzPolygon(47.5753f, -102.4196f, 47.515064f, -102.43829f, 47.511234f, -102.50549f, 47.52902f, -102.678f, 47.57419f, -102.69378f, 47.5753f, -102.69751f);
            TimezoneMapper.poly[172] = new TzPolygon(40.042465f, -102.4196f, 40.042465f, -101.32356f, 40.004246f, -101.32242f, 40.00391f, -102.04855f, 39.56886f, -102.04755f, 39.56855f, -101.39016f, 39.134537f, -101.39128f, 39.13385f, -101.47789f, 38.700176f, -101.484985f, 38.700375f, -101.56935f, 37.736435f, -101.527054f, 37.738407f, -102.043236f, 36.992226f, -102.042274f, 36.99489f, -102.4196f);
            TimezoneMapper.poly[173] = new TzPolygon(46.982574f, -100.935104f, 46.69162f, -100.788506f, 46.65637f, -100.73464f, 46.67767f, -100.639626f, 46.634357f, -100.661606f, 46.531155f, -100.54321f, 46.42736f, -100.59201f, 46.3791f, -100.7184f, 46.407135f, -100.88915f, 46.27561f, -101.03633f, 46.37039f, -101.04896f, 46.370533f, -101.29921f, 46.629963f, -101.299706f, 46.631027f, -101.71692f, 46.717266f, -101.7152f, 46.71706f, -102.09391f, 46.9809f, -102.09699f);
            TimezoneMapper.poly[174] = new TzPolygon(47.5753f, -102.088524f, 47.495255f, -101.8175f, 47.561863f, -101.42987f, 47.519905f, -101.374985f, 47.462914f, -101.441185f, 47.29162f, -101.34455f, 47.26823f, -101.2568f, 47.24202f, -101.76367f, 46.98207f, -101.76332f, 46.9809f, -102.09699f, 46.98218f, -102.144226f, 47.32763f, -102.14423f, 47.329155f, -102.20776f, 47.573383f, -102.20343f);
            TimezoneMapper.poly[175] = new TzPolygon(47.295284f, -101.07811f, 47.25796f, -100.985664f, 47.025436f, -100.87952f, 46.982574f, -100.935104f, 46.981815f, -101.38242f, 46.98207f, -101.76332f, 47.24202f, -101.76367f, 47.24204f, -101.25716f);
            TimezoneMapper.poly[176] = new TzPolygon(47.5753f, -102.20556f, 47.573383f, -102.20343f, 47.329155f, -102.20776f, 47.32763f, -102.14423f, 46.71706f, -102.09391f, 46.717266f, -101.7152f, 46.631027f, -101.71692f, 46.629963f, -101.299706f, 46.370533f, -101.29921f, 46.37039f, -101.04896f, 46.280594f, -101.050186f, 46.14944f, -101.18955f, 46.077507f, -101.35596f, 45.94369f, -101.34969f, 45.91441f, -100.42344f, 45.70265f, -100.29896f, 45.654686f, -100.33949f, 45.653843f, -100.42094f, 45.536617f, -100.48649f, 45.475014f, -100.341255f, 45.238674f, -100.26087f, 45.095367f, -100.32857f, 45.023617f, -100.28099f, 44.98406f, -100.42624f, 44.8608f, -100.39805f, 44.75907f, -100.55827f, 44.831036f, -100.691536f, 44.798576f, -100.73004f, 44.722065f, -100.62313f, 44.57745f, -100.63761f, 44.541946f, -100.5158f, 44.460754f, -100.5785f, 44.45208f, -100.402504f, 44.371544f, -100.371284f, 44.16767f, -100.554504f, 44.169754f, -101.045906f, 43.8444f, -101.06804f, 43.783474f, -101.23355f, 42.995293f, -101.2277f, 42.995083f, -100.904144f, 42.812885f, -100.90282f, 42.783344f, -100.845474f, 42.75531f, -100.8908f, 42.611336f, -100.891045f, 42.610268f, -100.77328f, 42.351803f, -100.829094f, 42.088654f, -100.74896f, 42.089584f, -100.8444f, 41.74037f, -100.84389f, 41.743195f, -101.40446f, 41.39544f, -101.40549f, 41.395943f, -101.27018f, 41.04763f, -101.248695f, 40.697426f, -101.245575f, 40.69734f, -101.343185f, 40.042465f, -101.32356f, 40.042465f, -102.4196f, 47.5753f, -102.4196f);
            TimezoneMapper.poly[177] = new TzPolygon(47.5753f, -103.61013f, 47.589474f, -103.61025f, 47.58887f, -103.73709f, 47.675842f, -103.73872f, 47.675797f, -103.79923f, 47.84893f, -103.80061f, 47.848114f, -103.965576f, 47.959892f, -103.96594f, 47.997276f, -104.04467f, 48.990585f, -104.049545f, 48.99084f, -103.61013f);
            TimezoneMapper.poly[178] = new TzPolygon(48.99935f, -110.0f, 48.99935f, -103.61013f, 48.99112f, -103.61013f, 48.990303f, -104.02368f, 48.996967f, -105.0f, 48.99657f, -110.0f);
            TimezoneMapper.poly[179] = new TzPolygon(50.310158f, -107.76962f, 50.27379f, -107.75804f, 50.25922f, -107.79193f, 50.283604f, -107.82911f);
            TimezoneMapper.poly[180] = new TzPolygon(47.5753f, -103.442375f, 47.591843f, -103.43854f, 47.576626f, -103.06945f, 47.634605f, -103.03053f, 47.5753f, -103.03053f);
            TimezoneMapper.poly[181] = new TzPolygon(47.57593f, -102.69964f, 47.67401f, -103.02897f, 47.67401f, -102.69964f);
            TimezoneMapper.poly[182] = new TzPolygon(47.5753f, -102.42387f, 47.59081f, -102.41479f, 47.615253f, -102.25002f, 47.5753f, -102.20556f);
            TimezoneMapper.poly[183] = new TzPolygon(48.992775f, -103.61013f, 48.992775f, -102.77201f, 48.99112f, -103.61013f);
            TimezoneMapper.poly[184] = new TzPolygon(48.995583f, -100.26087f, 48.995552f, -101.36642f, 50.982723f, -101.52202f, 50.982723f, -100.26087f);
            TimezoneMapper.poly[185] = new TzPolygon(49.010925f, -123.06908f, 49.001205f, -123.091866f, 49.010925f, -123.10135f);
            TimezoneMapper.poly[186] = new TzPolygon(49.001175f, -122.74365f, 49.00119f, -122.75615f, 49.010925f, -122.8035f, 49.010925f, -122.74365f);
            TimezoneMapper.poly[187] = new TzPolygon(49.002666f, -122.74365f, 49.002666f, -118.60326f, 49.00011f, -118.60326f, 48.999363f, -121.09305f, 49.001175f, -122.74365f);
            TimezoneMapper.poly[188] = new TzPolygon(46.554348f, -90.43818f, 46.505497f, -90.21679f, 46.336567f, -90.11775f, 46.294758f, -89.902f, 32.50963f, -89.902f, 32.50963f, -90.43818f);
            TimezoneMapper.poly[189] = new TzPolygon(49.0f, -90.9639f, 48.5f, -90.9639f, 48.25f, -90.0f, 48.040512f, -89.996376f, 48.10593f, -90.149895f, 48.086983f, -90.758835f, 48.245285f, -90.86729f, 48.079113f, -91.256355f, 48.049377f, -91.58295f, 48.10846f, -91.57756f, 48.112865f, -91.70737f, 48.197414f, -91.74153f, 48.258404f, -92.02037f, 48.35132f, -92.06236f, 48.4056f, -91.9558f, 48.4056f, -92.1509f, 48.4641f, -92.2672f, 48.6171f, -92.0124f, 48.6614f, -91.7528f, 49.0f, -91.7528f);
            TimezoneMapper.poly[190] = new TzPolygon(47.992462f, -89.902f, 47.989857f, -89.91586f, 48.024788f, -89.99619f, 48.25f, -90.0f, 48.5f, -90.9639f, 49.25f, -91.0f, 49.5f, -90.0f, 50.75f, -90.0f, 50.982723f, -90.46545f, 50.982723f, -89.902f);
            TimezoneMapper.poly[191] = new TzPolygon(48.995583f, -100.26087f, 50.982723f, -100.26087f, 50.982723f, -90.46545f, 50.75f, -90.0f, 49.5f, -90.0f, 49.25f, -91.0f, 49.0f, -91.0f, 49.0f, -91.7528f, 48.6614f, -91.7528f, 48.6171f, -92.0124f, 48.4641f, -92.2672f, 48.3776f, -91.9558f, 48.354294f, -92.298355f, 48.253456f, -92.273315f, 48.229298f, -92.37381f, 48.44019f, -92.50803f, 48.455845f, -92.71514f, 48.546158f, -92.649254f, 48.63298f, -92.98031f, 48.644295f, -93.27722f, 48.538094f, -93.507416f, 48.518383f, -93.768776f, 48.633f, -93.84273f, 48.651356f, -94.234344f, 48.709248f, -94.292336f, 48.720657f, -94.59069f, 48.77627f, -94.68934f, 49.31594f, -94.82763f, 49.388367f, -95.160706f, 48.99572f, -95.16467f);
            TimezoneMapper.poly[192] = new TzPolygon(48.73136f, -94.556755f, 48.72307f, -94.548676f, 48.714146f, -94.54876f, 48.72607f, -94.590004f);
            TimezoneMapper.poly[193] = new TzPolygon(29.907967f, -85.3765f, 29.926186f, -85.38918f, 30.028301f, -85.39188f, 29.973703f, -85.3765f);
            TimezoneMapper.poly[194] = new TzPolygon(33.82744f, -85.3765f, 34.98553f, -85.605515f, 34.984474f, -85.47421f, 35.047222f, -85.38169f, 35.147583f, -85.38706f, 35.160454f, -85.3765f);
            TimezoneMapper.poly[195] = new TzPolygon(30.699812f, -85.3765f, 30.699812f, -84.85841f, 30.58006f, -84.965965f, 30.312794f, -85.03425f, 30.172195f, -85.139175f, 30.04316f, -85.13581f, 29.94911f, -85.016f, 29.778986f, -85.040184f, 29.828482f, -85.19169f, 29.973673f, -85.3765f);
            TimezoneMapper.poly[196] = new TzPolygon(30.699812f, -84.85841f, 31.193531f, -85.10995f, 31.54432f, -85.04544f, 31.838377f, -85.14355f, 31.97319f, -85.07041f, 32.134758f, -85.06268f, 32.2646f, -84.893486f, 32.32907f, -85.008286f, 32.430126f, -84.966194f, 32.861954f, -85.18558f, 33.82744f, -85.3765f, 33.82744f, -84.85841f);
            TimezoneMapper.poly[197] = new TzPolygon(36.99782f, -84.835594f, 36.958f, -84.770294f, 36.982834f, -84.68305f, 36.847397f, -84.57584f, 36.606102f, -84.77771f, 36.61817f, -84.98862f, 36.858303f, -85.06401f);
            TimezoneMapper.poly[198] = new TzPolygon(36.99782f, -84.835594f, 36.858303f, -85.06401f, 36.633614f, -85.00338f, 36.60629f, -84.78417f, 36.39491f, -84.65953f, 36.2896f, -84.87205f, 36.20832f, -84.91261f, 35.907455f, -84.6795f, 35.77362f, -84.90521f, 35.571564f, -85.10772f, 35.47633f, -85.11731f, 35.160454f, -85.3765f, 36.99782f, -85.3765f);
            TimezoneMapper.poly[199] = new TzPolygon(38.55252f, -87.203186f, 38.51467f, -87.07226f, 38.232304f, -87.072975f, 38.23195f, -87.298546f, 38.377327f, -87.31599f, 38.3794f, -87.40759f, 38.53217f, -87.4642f, 38.51022f, -87.30218f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer3 {
        private Initializer3() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[200] = new TzPolygon(38.906654f, -87.07226f, 38.905216f, -87.51765f, 38.929596f, -87.53215f, 38.929554f, -87.07226f);
            TimezoneMapper.poly[201] = new TzPolygon(38.20647f, -87.07226f, 38.537617f, -87.107735f, 38.518406f, -87.54049f, 38.450817f, -87.59888f, 38.41612f, -87.72172f, 38.46137f, -87.75389f, 38.50797f, -87.648026f, 38.64117f, -87.61936f, 38.743126f, -87.49708f, 38.896416f, -87.54421f, 38.910355f, -87.24213f, 38.90961f, -87.07226f);
            TimezoneMapper.poly[202] = new TzPolygon(38.906654f, -87.07226f, 38.929554f, -87.07226f, 38.92938f, -85.20341f, 38.69111f, -85.20235f, 38.73772f, -85.26281f, 38.735657f, -85.41679f, 38.696697f, -85.455154f, 38.58614f, -85.42792f, 38.60464f, -85.79394f, 38.503147f, -85.88489f, 38.488686f, -85.993774f, 38.418438f, -85.99484f, 38.397114f, -86.30679f, 38.39758f, -86.681435f, 38.90791f, -86.68449f);
            TimezoneMapper.poly[203] = new TzPolygon(38.264446f, -86.571495f, 38.208244f, -86.57202f, 38.20826f, -86.46132f, 38.077904f, -86.430984f, 38.037567f, -86.51789f, 37.92885f, -86.50821f, 37.916786f, -86.59323f, 37.840164f, -86.64397f, 37.908627f, -86.65466f, 37.892727f, -86.73115f, 37.99844f, -86.82061f, 37.999924f, -86.77156f, 38.249157f, -86.79358f);
            TimezoneMapper.poly[204] = new TzPolygon(38.607117f, -85.4971f, 38.55385f, -85.414856f, 38.467373f, -85.49996f, 38.43424f, -85.60982f, 38.29073f, -85.69133f, 38.274315f, -85.82823f, 38.025654f, -85.922356f, 37.95861f, -86.04644f, 38.051384f, -86.26026f, 38.180027f, -86.33136f, 38.29142f, -86.25313f, 38.316414f, -86.28941f, 38.4224f, -86.25445f, 38.418438f, -85.99484f, 38.488686f, -85.993774f, 38.503147f, -85.88489f, 38.60464f, -85.79394f);
            TimezoneMapper.poly[205] = new TzPolygon(38.220776f, -87.07226f, 38.207005f, -86.79341f, 37.999924f, -86.77156f, 37.99844f, -86.82061f, 37.892727f, -86.73115f, 37.908627f, -86.65466f, 37.841488f, -86.656364f, 37.916786f, -86.59323f, 37.92885f, -86.50821f, 38.04459f, -86.489334f, 37.798916f, -86.15172f, 37.593296f, -86.275375f, 37.56657f, -86.1131f, 37.448532f, -86.05647f, 37.422054f, -85.657f, 37.47063f, -85.58462f, 37.192234f, -85.353226f, 37.31203f, -85.17651f, 37.256798f, -85.05648f, 37.19694f, -85.05337f, 37.10224f, -84.95411f, 37.1165f, -84.90148f, 37.047607f, -84.905495f, 36.99782f, -84.835594f, 36.99782f, -87.07226f);
            TimezoneMapper.poly[206] = new TzPolygon(38.20647f, -87.07226f, 38.90961f, -87.07226f, 38.90791f, -86.68449f, 38.24847f, -86.679886f, 38.206264f, -87.016556f);
            TimezoneMapper.poly[207] = new TzPolygon(38.422497f, -86.30908f, 38.38794f, -86.24576f, 38.201748f, -86.29032f, 38.183525f, -86.3752f, 38.138283f, -86.32325f, 38.10489f, -86.39971f, 38.113705f, -86.463104f, 38.20826f, -86.46132f, 38.208244f, -86.57202f, 38.264446f, -86.571495f, 38.263424f, -86.679924f, 38.39758f, -86.681435f);
            TimezoneMapper.poly[208] = new TzPolygon(38.929596f, -85.13892f, 38.882298f, -84.787674f, 38.78688f, -84.8152f, 38.7777f, -84.99233f, 38.68779f, -85.17767f, 38.92938f, -85.20341f);
            TimezoneMapper.poly[209] = new TzPolygon(38.929596f, -87.53215f, 39.1364f, -87.65518f, 39.21617f, -87.57475f, 39.308002f, -87.6181f, 39.3516f, -87.53045f, 40.737804f, -87.52628f, 40.73827f, -87.09867f, 40.839874f, -87.09953f, 40.84081f, -86.98716f, 40.91339f, -86.987656f, 40.909332f, -86.46718f, 41.760254f, -86.524025f, 41.760456f, -86.46635f, 38.929596f, -86.46635f);
            TimezoneMapper.poly[210] = new TzPolygon(41.172783f, -86.69896f, 41.17112f, -86.46706f, 40.997345f, -86.46653f, 40.909332f, -86.46718f, 40.9136f, -86.92929f, 41.086f, -86.930954f, 41.172207f, -86.93087f);
            TimezoneMapper.poly[211] = new TzPolygon(41.43388f, -86.52451f, 41.433876f, -86.46694f, 41.374874f, -86.46635f, 41.17112f, -86.46706f, 41.172207f, -86.93087f, 41.2371f, -86.93004f, 41.28516f, -86.78424f, 41.404224f, -86.69839f);
            TimezoneMapper.poly[212] = new TzPolygon(41.760456f, -86.46635f, 41.76232f, -85.931984f, 41.760498f, -84.80756f, 40.728138f, -84.80226f, 39.305557f, -84.81909f, 39.30818f, -85.06522f, 38.929596f, -85.13892f, 38.929596f, -86.46635f);
            TimezoneMapper.poly[213] = new TzPolygon(38.929596f, -85.13847f, 39.30818f, -85.06522f, 39.305557f, -84.81909f, 41.6958f, -84.80667f, 41.709152f, -84.33076f, 38.929596f, -84.33076f);
            TimezoneMapper.poly[214] = new TzPolygon(45.210075f, -87.73087f, 45.19065f, -87.74327f, 45.19339f, -87.74411f);
            TimezoneMapper.poly[215] = new TzPolygon(45.24034f, -87.71537f, 45.21235f, -87.728676f, 45.216557f, -87.72991f);
            TimezoneMapper.poly[216] = new TzPolygon(45.133938f, -87.65212f, 45.187965f, -87.735085f, 45.24034f, -87.70951f, 45.24034f, -87.65212f);
            TimezoneMapper.poly[217] = new TzPolygon(46.24752f, -87.65212f, 46.24712f, -88.1178f, 46.419403f, -88.11741f, 46.418163f, -88.99272f, 46.331093f, -88.99207f, 46.332916f, -89.36612f, 46.504402f, -89.36499f, 46.50707f, -89.73881f, 46.59195f, -89.737686f, 46.59344f, -89.86603f, 46.767605f, -89.876045f, 46.767605f, -88.470985f, 46.748173f, -88.47325f, 46.767605f, -88.45728f, 46.767605f, -87.65212f);
            TimezoneMapper.poly[218] = new TzPolygon(46.294758f, -89.902f, 46.136528f, -89.08544f, 45.98911f, -88.66484f, 46.01971f, -88.5178f, 45.95012f, -88.180595f, 45.872143f, -88.0728f, 45.80857f, -88.12754f, 45.75364f, -87.87747f, 45.68218f, -87.7841f, 45.65299f, -87.82975f, 45.56647f, -87.79289f, 45.56239f, -87.836395f, 45.49294f, -87.785645f, 45.355076f, -87.891045f, 45.37219f, -87.66559f, 45.24034f, -87.71597f, 45.24034f, -89.902f);
            TimezoneMapper.poly[219] = new TzPolygon(45.177055f, -87.580826f, 45.11713f, -87.580826f, 45.098225f, -87.595085f, 45.150517f, -87.60317f);
            TimezoneMapper.poly[220] = new TzPolygon(45.177055f, -87.65212f, 45.177055f, -87.580826f, 45.124187f, -87.580826f, 45.099403f, -87.5991f, 45.133938f, -87.65212f);
            TimezoneMapper.poly[221] = new TzPolygon(45.985115f, -87.580826f, 45.985188f, -87.62034f, 46.24755f, -87.61887f, 46.24752f, -87.65212f, 46.767605f, -87.65212f, 46.767605f, -87.580826f);
            TimezoneMapper.poly[222] = new TzPolygon(45.985115f, -87.580826f, 45.98473f, -87.37089f, 45.549538f, -87.33008f, 45.548992f, -87.26763f, 45.124187f, -87.580826f);
            TimezoneMapper.poly[223] = new TzPolygon(44.892063f, -87.38881f, 44.908077f, -87.40235f, 45.16744f, -87.23178f, 45.151474f, -87.1763f, 45.2953f, -87.013626f, 45.27483f, -86.9722f, 45.21459f, -86.98374f, 45.24102f, -87.04712f, 45.08824f, -87.04665f, 44.892063f, -87.22361f);
            TimezoneMapper.poly[224] = new TzPolygon(45.21097f, -87.33299f, 45.15912f, -87.326385f, 45.156094f, -87.328514f, 45.1811f, -87.376366f);
            TimezoneMapper.poly[225] = new TzPolygon(46.710274f, -84.72895f, 46.64694f, -84.706245f, 46.638012f, -84.71388f, 46.69677f, -84.73725f);
            TimezoneMapper.poly[226] = new TzPolygon(45.862656f, -83.43291f, 45.869312f, -83.46791f, 45.933266f, -83.49056f, 45.998745f, -83.43291f);
            TimezoneMapper.poly[227] = new TzPolygon(46.518467f, -84.10887f, 46.494644f, -84.407295f, 46.449223f, -84.468925f, 46.50808f, -84.55734f, 46.518467f, -84.55734f);
            TimezoneMapper.poly[228] = new TzPolygon(45.998745f, -83.43291f, 46.10106f, -83.56605f, 46.1145f, -83.821205f, 46.050617f, -83.94845f, 46.224247f, -84.0969f, 46.518467f, -84.10887f, 46.518467f, -83.43291f);
            TimezoneMapper.poly[229] = new TzPolygon(41.709152f, -84.33076f, 41.733196f, -83.47369f, 41.697075f, -83.47369f, 41.697075f, -84.33076f);
            TimezoneMapper.poly[230] = new TzPolygon(43.835323f, -82.63273f, 43.207886f, -82.51511f, 42.972534f, -82.40782f, 42.5946f, -82.52442f, 42.543655f, -82.64797f, 42.370102f, -82.82848f, 42.302513f, -83.07272f, 42.045593f, -83.14279f, 42.031166f, -83.17306f, 43.835323f, -83.17306f);
            TimezoneMapper.poly[231] = new TzPolygon(48.020103f, -89.902f, 48.020103f, -89.75503f, 47.992462f, -89.902f);
            TimezoneMapper.poly[232] = new TzPolygon(48.020103f, -89.51687f, 47.99274f, -89.56746f, 48.020103f, -89.75503f);
            TimezoneMapper.poly[233] = new TzPolygon(50.982723f, -89.15141f, 48.46896f, -89.15141f, 48.465614f, -89.16358f, 48.51452f, -89.18257f, 48.51488f, -89.42785f, 48.28952f, -89.38861f, 48.356525f, -89.279236f, 48.331005f, -89.21091f, 48.269608f, -89.2409f, 48.269608f, -89.902f, 50.982723f, -89.902f);
            TimezoneMapper.poly[234] = new TzPolygon(49.039253f, -88.23624f, 48.952995f, -88.23448f, 49.010757f, -88.25822f, 48.950222f, -88.26092f, 48.952778f, -88.432816f, 49.03917f, -88.433f);
            TimezoneMapper.poly[235] = new TzPolygon(43.22872f, -79.66875f, 43.19382f, -79.519684f, 43.182983f, -79.31148f, 43.25868f, -79.05867f, 43.07743f, -79.065704f, 42.893017f, -78.9067f, 42.83396f, -79.09239f, 42.87325f, -79.14878f, 42.873375f, -79.43932f, 42.838406f, -79.471985f, 42.853924f, -79.66875f);
            TimezoneMapper.poly[236] = new TzPolygon(44.076958f, -76.3285f, 44.105877f, -76.37634f, 44.12978f, -76.3285f);
            TimezoneMapper.poly[237] = new TzPolygon(44.2349f, -76.20735f, 44.236736f, -76.19251f, 44.23048f, -76.18599f, 44.22625f, -76.20735f);
            TimezoneMapper.poly[238] = new TzPolygon(44.36635f, -75.96706f, 44.356976f, -75.93712f, 44.341293f, -75.96277f, 44.343163f, -76.018654f);
            TimezoneMapper.poly[239] = new TzPolygon(44.36635f, -75.99051f, 44.303368f, -76.20735f, 44.36635f, -76.20735f);
            TimezoneMapper.poly[240] = new TzPolygon(45.18945f, -74.32299f, 44.994213f, -74.32299f, 44.99332f, -74.75389f, 45.008446f, -74.79109f, 45.064983f, -74.50262f);
            TimezoneMapper.poly[241] = new TzPolygon(44.4364f, -75.85379f, 44.425137f, -75.846375f, 44.387505f, -75.91026f, 44.389935f, -75.911835f);
            TimezoneMapper.poly[242] = new TzPolygon(45.18945f, -74.32299f, 45.055695f, -74.52894f, 44.983494f, -74.98691f, 44.85649f, -75.2768f, 44.40607f, -75.91214f, 45.18945f, -75.91214f);
            TimezoneMapper.poly[243] = new TzPolygon(45.18945f, -74.32299f, 45.309635f, -74.47002f, 45.569614f, -74.39253f, 45.636433f, -74.64216f, 45.64604f, -74.92908f, 45.463463f, -75.67427f, 45.373966f, -75.823204f, 45.52275f, -76.11805f, 45.45647f, -76.36002f, 45.567688f, -76.656975f, 45.650513f, -76.688446f, 45.663464f, -76.63116f, 45.746754f, -76.609085f, 45.829456f, -76.6999f, 45.80905f, -76.76137f, 45.883316f, -76.80208f, 45.894947f, -76.9232f, 45.807133f, -76.91005f, 45.786255f, -76.97718f, 45.87815f, -77.199684f, 45.95661f, -77.28794f, 46.01262f, -77.27051f, 46.20571f, -77.70161f, 46.316574f, -78.70059f, 46.37772f, -78.723976f, 46.519714f, -78.96438f, 47.08852f, -79.43238f, 47.253525f, -79.430725f, 47.431763f, -79.58591f, 47.537216f, -79.50941f, 50.982723f, -79.499825f, 50.982723f, -74.32299f);
            TimezoneMapper.poly[244] = new TzPolygon(45.418625f, -74.32299f, 45.418625f, -70.79979f, 45.230778f, -70.86777f, 45.351353f, -71.00161f, 45.247246f, -71.14336f, 45.299866f, -71.2938f, 45.237125f, -71.43209f, 45.132526f, -71.42022f, 45.0f, -71.53727f, 44.994213f, -74.32299f);
            TimezoneMapper.poly[245] = new TzPolygon(45.39561f, -70.71763f, 45.418625f, -70.79979f, 45.418625f, -70.71763f);
            TimezoneMapper.poly[246] = new TzPolygon(45.624844f, -70.71763f, 45.624844f, -70.5981f, 45.51629f, -70.71364f, 45.37519f, -70.64472f, 45.39561f, -70.71763f);
            TimezoneMapper.poly[247] = new TzPolygon(45.624844f, -70.5789f, 45.719433f, -70.396866f, 45.795044f, -70.40974f, 45.889854f, -70.25917f, 45.946175f, -70.233864f, 45.969105f, -70.31119f, 46.023743f, -70.31153f, 46.144096f, -70.24464f, 46.192192f, -70.30109f, 46.350216f, -70.197426f, 46.426f, -70.056816f, 46.699497f, -69.98644f, 47.327045f, -69.35749f, 43.979782f, -69.35749f, 43.9735f, -69.370346f, 44.02619f, -69.362144f, 43.97685f, -69.39289f, 44.000954f, -69.43306f, 43.951973f, -69.45741f, 43.951973f, -70.5789f);
            TimezoneMapper.poly[248] = new TzPolygon(44.84552f, -66.95481f, 44.86806f, -66.97939f, 44.89772f, -66.95481f);
            TimezoneMapper.poly[249] = new TzPolygon(44.94035f, -67.00247f, 44.964836f, -67.02549f, 44.98497f, -67.00247f);
            TimezoneMapper.poly[250] = new TzPolygon(45.617863f, -67.64821f, 45.57441f, -67.41887f, 45.478832f, -67.49562f, 45.37427f, -67.42104f, 45.244106f, -67.46207f, 45.12369f, -67.33676f, 45.19023f, -67.264755f, 45.15724f, -67.14924f, 45.026657f, -67.075386f, 45.026657f, -67.64821f);
            TimezoneMapper.poly[251] = new TzPolygon(45.83255f, -64.57331f, 45.820568f, -64.57464f, 45.59124f, -64.78236f, 45.624165f, -64.87831f, 45.444294f, -65.34486f, 45.3001f, -65.5782f, 45.257385f, -65.812035f, 45.193893f, -65.90491f, 45.21824f, -66.01452f, 45.282604f, -66.05216f, 45.1596f, -66.19204f, 45.13329f, -66.3519f, 45.058346f, -66.46417f, 45.152374f, -66.49287f, 45.075733f, -66.69622f, 45.83255f, -66.69622f);
            TimezoneMapper.poly[252] = new TzPolygon(45.83255f, -64.388885f, 45.717777f, -64.52155f, 45.83255f, -64.537575f);
            TimezoneMapper.poly[253] = new TzPolygon(45.83255f, -64.2595f, 45.97663f, -64.1491f, 45.9689f, -64.05571f, 46.00659f, -64.03343f, 45.891266f, -63.777946f, 45.83255f, -63.777946f);
            TimezoneMapper.poly[254] = new TzPolygon(48.1135f, -66.7455f, 48.014088f, -66.67935f, 48.0093f, -66.7015f, 48.0947f, -66.805f);
            TimezoneMapper.poly[255] = new TzPolygon(47.999695f, -67.64821f, 47.99973f, -67.604965f, 47.926342f, -67.594055f, 47.84115f, -67.364716f, 47.891197f, -67.350716f, 47.872444f, -67.21442f, 47.93155f, -67.06186f, 47.889957f, -66.96141f, 47.983433f, -66.91303f, 48.049408f, -66.47475f, 47.23499f, -66.47475f, 47.23499f, -67.64821f);
            TimezoneMapper.poly[256] = new TzPolygon(48.06057f, -66.30693f, 48.1135f, -66.46479f, 48.1135f, -66.30693f);
            TimezoneMapper.poly[257] = new TzPolygon(46.20479f, -60.41709f, 46.199745f, -60.416473f, 46.177383f, -60.432487f);
            TimezoneMapper.poly[258] = new TzPolygon(45.72867f, -60.24838f, 45.826885f, -60.70429f, 46.03978f, -60.368492f, 45.91623f, -60.672497f, 45.956455f, -60.808285f, 46.21407f, -60.33644f, 46.255123f, -60.312675f, 46.19029f, -60.505894f, 46.341152f, -60.306168f, 46.291325f, -60.24838f, 46.232822f, -60.24838f, 46.15835f, -60.320595f, 46.190884f, -60.24838f);
            TimezoneMapper.poly[259] = new TzPolygon(50.176544f, -61.58803f, 50.15948f, -61.582493f, 50.15043f, -61.59287f, 50.150826f, -61.597504f);
            TimezoneMapper.poly[260] = new TzPolygon(50.21884f, -61.582493f, 50.120842f, -61.582493f, 50.08135f, -61.7451f, 50.100685f, -61.797318f, 50.145954f, -61.585964f, 50.2187f, -61.5833f);
            TimezoneMapper.poly[261] = new TzPolygon(50.36155f, -60.513313f, 50.3611f, -61.2633f, 50.3222f, -61.2764f, 50.275f, -61.1746f, 50.275f, -61.4991f, 50.19439f, -61.582493f, 50.982723f, -61.582493f, 50.982723f, -60.513313f);
            TimezoneMapper.poly[262] = new TzPolygon(50.547756f, -59.88368f, 50.5478f, -59.965f, 50.4291f, -59.9639f, 50.4292f, -60.2189f, 50.3617f, -60.2579f, 50.36155f, -60.513313f, 50.982723f, -60.513313f, 50.982723f, -59.88368f);
            TimezoneMapper.poly[263] = new TzPolygon(50.982723f, -59.591976f, 50.647064f, -59.591976f, 50.547604f, -59.606228f, 50.547756f, -59.88368f, 50.982723f, -59.88368f);
            TimezoneMapper.poly[264] = new TzPolygon(50.86664f, -59.396664f, 50.8837f, -59.4323f, 50.669315f, -59.4324f, 50.686897f, -59.58627f, 50.647064f, -59.591976f, 50.982723f, -59.591976f, 50.982723f, -59.396664f);
            TimezoneMapper.poly[265] = new TzPolygon(50.982723f, -59.317802f, 50.8454f, -59.3523f, 50.86664f, -59.396664f, 50.982723f, -59.396664f);
            TimezoneMapper.poly[266] = new TzPolygon(42.012318f, -8.676639f, 41.870224f, -8.869695f, 42.112583f, -8.892805f, 42.118446f, -8.811806f, 42.145638f, -8.831536f, 42.145638f, -8.676639f);
            TimezoneMapper.poly[267] = new TzPolygon(41.853355f, -7.869916f, 41.852165f, -7.874055f, 41.925583f, -7.892667f, 41.831333f, -8.002778f, 41.81064f, -8.129556f, 41.90814f, -8.211861f, 42.055637f, -8.080916f, 42.066387f, -8.175472f, 42.14517f, -8.191139f, 42.048443f, -8.627556f, 42.012318f, -8.676639f, 42.145638f, -8.676639f, 42.145638f, -7.869916f);
            TimezoneMapper.poly[268] = new TzPolygon(36.993973f, -7.833806f, 36.99186f, -7.829916f, 36.974167f, -7.864972f, 36.97597f, -7.865972f);
            TimezoneMapper.poly[269] = new TzPolygon(37.018696f, -7.830695f, 37.01536f, -7.828166f, 37.000137f, -7.855111f, 37.00561f, -7.8555f);
            TimezoneMapper.poly[270] = new TzPolygon(41.853355f, -7.869916f, 41.90536f, -7.689333f, 41.880585f, -7.574695f, 41.8315f, -7.578166f, 41.8625f, -7.452333f, 41.811306f, -7.423584f, 41.88636f, -7.191722f, 41.987362f, -7.160361f, 41.938526f, -6.882722f, 41.988777f, -6.785806f, 41.940834f, -6.732611f, 41.967472f, -6.571472f, 41.86925f, -6.517334f, 41.69158f, -6.5545f, 41.658f, -6.496611f, 41.676083f, -6.334722f, 41.57839f, -6.182694f, 41.315556f, -6.428389f, 41.239887f, -6.645916f, 41.044167f, -6.80675f, 41.034f, -6.930361f, 40.856945f, -6.799056f, 40.568554f, -6.837778f, 40.532555f, -6.789583f, 40.447056f, -6.840389f, 40.370167f, -6.776083f, 40.266945f, -6.857944f, 40.198807f, -7.015639f, 40.124863f, -7.000333f, 40.013f, -6.863889f, 39.680054f, -7.006028f, 39.656418f, -7.524917f, 39.456055f, -7.294389f, 39.385223f, -7.329778f, 39.281193f, -7.232528f, 39.200863f, -7.229639f, 39.16711f, -7.133417f, 39.1045f, -7.134361f, 39.116806f, -7.035722f, 39.063473f, -6.96325f, 38.86772f, -7.0335f, 38.718834f, -7.254806f, 38.62375f, -7.240889f, 38.437363f, -7.330611f, 38.176193f, -7.082611f, 38.211193f, -6.933861f, 38.02811f, -6.993639f, 38.039944f, -7.102167f, 38.00075f, -7.125945f, 37.986168f, -7.250834f, 37.878834f, -7.274389f, 37.757195f, -7.415916f, 37.55722f, -7.520833f, 37.480778f, -7.460667f, 37.170887f, -7.404611f, 37.1245f, -7.599111f, 37.15525f, -7.56225f, 36.988167f, -7.869916f);
            TimezoneMapper.poly[271] = new TzPolygon(36.159805f, -5.353222f, 36.11525f, -5.339639f, 36.112415f, -5.343778f, 36.146946f, -5.35725f);
            TimezoneMapper.poly[272] = new TzPolygon(42.658695f, 1.554417f, 42.57164f, 1.780389f, 42.4925f, 1.701611f, 42.43508f, 1.515417f, 42.448277f, 1.452528f, 42.554943f, 1.422111f, 42.649776f, 1.470056f);
            TimezoneMapper.poly[273] = new TzPolygon(42.489975f, 1.780389f, 42.50386f, 1.7235f, 42.57164f, 1.780389f);
            TimezoneMapper.poly[274] = new TzPolygon(42.57164f, 1.780389f, 42.634388f, 1.695417f, 42.65372f, 1.482194f, 42.6065f, 1.439917f, 42.72075f, 1.347944f, 42.712696f, 1.160333f, 42.78389f, 1.086056f, 42.867474f, 0.703f, 42.791332f, 0.647333f, 42.687305f, 0.678361f, 42.72425f, 0.360861f, 42.676582f, 0.288722f, 42.737335f, 0.191278f, 42.694f, -0.013778f, 42.808887f, -0.146167f, 42.846584f, -0.290222f, 42.79122f, -0.561222f, 42.97211f, -0.76725f, 42.95739f, -0.931889f, 43.010223f, -1.133028f, 43.077694f, -1.305833f, 43.124306f, -1.290028f, 43.03639f, -1.356528f, 43.068832f, -1.459361f, 43.263638f, -1.398528f, 43.29161f, -1.511361f, 43.258804f, -1.617583f, 43.310055f, -1.630778f, 43.30111f, -1.726917f, 43.3555f, -1.788528f, 43.437946f, -1.601667f, 43.66111f, -1.42461f, 43.66111f, 1.780389f);
            TimezoneMapper.poly[275] = new TzPolygon(42.497776f, 2.034421f, 42.497776f, 2.045668f, 42.475613f, 1.984575f);
            TimezoneMapper.poly[276] = new TzPolygon(42.497776f, 1.780389f, 42.497776f, 3.144723f, 42.44422f, 3.169528f, 42.438194f, 3.077917f, 42.484196f, 3.031889f, 42.46539f, 2.858583f, 42.408165f, 2.685611f, 42.34264f, 2.659167f, 42.34536f, 2.497555f, 42.43514f, 2.259667f, 42.36222f, 2.006444f, 42.454945f, 1.923889f, 42.489975f, 1.780389f);
            TimezoneMapper.poly[277] = new TzPolygon(25.643694f, -97.47694f, 25.643694f, -97.21629f, 25.599363f, -97.2263f, 25.555017f, -97.40472f, 25.603125f, -97.45034f, 25.589432f, -97.47694f);
            TimezoneMapper.poly[278] = new TzPolygon(25.953009f, -97.47694f, 25.953009f, -97.20687f, 25.926956f, -97.346436f, 25.835548f, -97.40183f, 25.864368f, -97.47694f);
            TimezoneMapper.poly[279] = new TzPolygon(14.019691f, -91.450584f, 14.183593f, -91.7807f, 14.539617f, -92.24149f, 14.661709f, -92.14651f, 14.828277f, -92.18416f, 14.887989f, -92.13703f, 14.985678f, -92.15154f, 15.07038f, -92.05945f, 15.2607f, -92.2105f, 16.10002f, -90.45478f, 16.164074f, -90.426f, 16.248198f, -90.45609f, 16.33817f, -90.38321f, 16.362316f, -90.4117f, 16.363947f, -90.372314f, 16.411982f, -90.38853f, 16.480036f, -90.63296f, 16.577991f, -90.626595f, 16.582394f, -90.66768f, 16.72478f, -90.71204f, 16.798157f, -90.803375f, 16.821487f, -90.921455f, 16.901375f, -90.96392f, 16.863277f, -90.98795f, 16.903051f, -91.06666f, 17.003038f, -91.12299f, 17.108896f, -91.26692f, 17.1707f, -91.26547f, 17.160032f, -91.34868f, 17.247925f, -91.43944f, 17.2541f, -90.9895f, 17.8174f, -90.9895f, 17.8174f, -89.902f, 14.019691f, -89.902f);
            TimezoneMapper.poly[280] = new TzPolygon(18.44285f, -92.93702f, 18.44288f, -92.92597f, 18.528095f, -92.743744f, 18.613234f, -92.67987f, 18.647652f, -92.48192f, 18.513054f, -92.42168f, 18.511944f, -92.15306f, 18.157219f, -92.15779f, 17.951385f, -91.855286f, 17.950832f, -91.6264f, 18.096664f, -91.60918f, 18.103054f, -91.50084f, 18.063332f, -91.32112f, 17.976109f, -91.188614f, 17.967777f, -90.98306f, 17.8174f, -90.98442f, 17.8174f, -92.93702f);
            TimezoneMapper.poly[281] = new TzPolygon(17.8174f, -89.43055f, 18.511965f, -89.42593f, 17.8174f, -89.42593f);
            TimezoneMapper.poly[282] = new TzPolygon(18.511965f, -88.26488f, 18.487413f, -88.27946f, 18.494751f, -88.47221f, 18.462267f, -88.51293f, 18.063341f, -88.69909f, 17.927467f, -88.84743f, 17.8665f, -88.84317f, 18.003077f, -89.038704f, 17.944826f, -89.14537f, 17.8174f, -89.146866f, 17.8174f, -89.42593f, 18.511965f, -89.42593f);
            TimezoneMapper.poly[283] = new TzPolygon(21.286951f, -89.63324f, 21.397688f, -88.84671f, 21.534739f, -88.59792f, 21.539324f, -88.30058f, 21.609877f, -88.13116f, 21.596973f, -88.06724f, 20.472252f, -88.06724f, 19.651943f, -89.418335f, 18.511965f, -89.42593f, 18.511965f, -89.63324f);
            TimezoneMapper.poly[284] = new TzPolygon(21.596973f, -88.06724f, 21.490385f, -87.53919f, 21.02472f, -87.54056f, 20.662498f, -87.75389f, 20.472252f, -88.06724f);
            TimezoneMapper.poly[285] = new TzPolygon(19.97403f, -75.86505f, 19.97403f, -75.16769f, 19.887905f, -75.29101f, 19.896395f, -75.58786f, 19.961916f, -75.702515f, 19.97008f, -75.86663f);
            TimezoneMapper.poly[286] = new TzPolygon(19.97403f, -74.92495f, 19.927782f, -74.97834f, 19.901798f, -75.07828f, 19.973766f, -75.087006f, 19.97403f, -75.08874f);
            TimezoneMapper.poly[287] = new TzPolygon(35.924137f, -5.35625f, 35.903667f, -5.275472f, 35.877388f, -5.336972f, 35.919918f, -5.3845f);
            TimezoneMapper.poly[288] = new TzPolygon(35.33714f, -2.956944f, 35.283443f, -2.926722f, 35.281445f, -2.960833f, 35.302723f, -2.975222f);
            TimezoneMapper.poly[289] = new TzPolygon(47.999695f, -69.35749f, 47.999317f, -68.11935f, 47.915886f, -68.12201f, 47.913044f, -68.38093f, 47.551445f, -68.382324f, 47.425274f, -68.57195f, 47.297367f, -69.05431f, 47.428074f, -69.04532f, 47.45865f, -69.22559f, 47.327045f, -69.35749f);
            TimezoneMapper.poly[290] = new TzPolygon(45.617863f, -67.64821f, 45.68663f, -67.717705f, 45.683716f, -67.81347f, 45.819256f, -67.76147f, 45.879948f, -67.80858f, 45.91418f, -67.7551f, 47.06619f, -67.78654f, 47.357296f, -68.2367f, 47.35664f, -68.36114f, 47.28723f, -68.38931f, 47.287064f, -68.58315f, 47.17887f, -68.89502f, 47.252182f, -69.049385f, 47.297367f, -69.05431f, 47.425274f, -68.57195f, 47.551445f, -68.382324f, 47.913044f, -68.38093f, 47.915886f, -68.12201f, 47.999317f, -68.11935f, 47.999695f, -67.64821f);
            TimezoneMapper.poly[291] = new TzPolygon(-52.650223f, -68.626526f, -52.665375f, -68.60475f, -54.895893f, -68.61058f, -54.889854f, -68.626526f);
            TimezoneMapper.poly[292] = new TzPolygon(-54.896095f, -67.53926f, -54.896095f, -67.45777f, -54.903206f, -67.417274f, -54.910446f, -67.471924f, -54.898914f, -67.54236f);
            TimezoneMapper.poly[293] = new TzPolygon(-54.945023f, -66.787865f, -54.896095f, -67.226105f, -54.896095f, -66.787865f);
            TimezoneMapper.poly[294] = new TzPolygon(-9.776915f, -51.38282f, -9.844168f, -50.232506f, -10.110279f, -50.381668f, -10.307501f, -50.408615f, -10.393612f, -50.48278f, -10.563334f, -50.52278f, -10.655834f, -50.606674f, -10.739445f, -50.582504f, -10.932501f, -50.63778f, -11.065279f, -50.60945f, -11.142502f, -50.664726f, -11.243057f, -50.659172f, -11.471111f, -50.74195f, -11.603334f, -50.64834f, -11.749428f, -50.714485f, -11.885557f, -50.6425f, -12.048613f, -50.68389f, -12.124428f, -50.668015f, -12.124428f, -51.38282f);
            TimezoneMapper.poly[295] = new TzPolygon(-9.126894f, -51.38282f, -9.126894f, -49.910603f, -9.321667f, -50.053337f, -9.703335f, -50.148613f, -9.844168f, -50.232506f, -9.783211f, -51.38282f);
            TimezoneMapper.poly[296] = new TzPolygon(57.77133f, -136.2965f, 57.82477f, -136.35999f, 57.835415f, -136.0057f, 58.01058f, -135.90253f, 57.77133f, -135.90253f, 57.77133f, -136.1938f, 57.782726f, -136.19124f, 57.77133f, -136.20665f, 57.77133f, -136.22331f, 57.79771f, -136.21304f);
            TimezoneMapper.poly[297] = new TzPolygon(57.845055f, -135.3706f, 58.01058f, -135.90253f, 58.01058f, -135.63493f, 57.995876f, -135.62619f, 57.979694f, -135.78835f, 57.94781f, -135.64667f, 58.01058f, -135.5649f, 58.01058f, -135.3706f);
            TimezoneMapper.poly[298] = new TzPolygon(58.77521f, -137.89246f, 58.805206f, -137.93622f, 58.900513f, -137.49272f, 58.77521f, -137.49272f);
            TimezoneMapper.poly[299] = new TzPolygon(58.91444f, -137.49272f, 58.916744f, -137.52083f, 59.241577f, -137.59822f, 59.70417f, -138.49323f, 59.70417f, -137.49272f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer4 {
        private Initializer4() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[300] = new TzPolygon(58.91444f, -137.49272f, 59.70417f, -137.49272f, 59.70417f, -136.47536f, 59.26482f, -136.47536f, 59.17006f, -136.57578f, 59.15792f, -136.82341f, 58.910595f, -137.4458f);
            TimezoneMapper.poly[301] = new TzPolygon(59.70417f, -135.90091f, 59.602013f, -136.34622f, 59.54335f, -136.22379f, 59.47095f, -136.29454f, 59.46553f, -136.46404f, 59.28142f, -136.45776f, 59.26482f, -136.47536f, 59.70417f, -136.47536f);
            TimezoneMapper.poly[302] = new TzPolygon(58.778606f, -133.98338f, 58.864365f, -134.25545f, 59.132103f, -134.47997f, 59.175976f, -134.64723f, 59.24871f, -134.69974f, 59.2839f, -134.96002f, 59.350227f, -135.03354f, 59.389515f, -134.99046f, 59.431946f, -135.09486f, 59.569237f, -135.02888f, 59.698383f, -135.23433f, 59.70417f, -135.24849f, 59.70417f, -133.98338f);
            TimezoneMapper.poly[303] = new TzPolygon(57.121727f, -133.35786f, 57.173866f, -133.50775f, 57.2359f, -133.29056f, 57.209743f, -133.17166f, 57.154427f, -133.17166f, 57.131653f, -133.23102f, 57.175198f, -133.24234f, 57.121727f, -133.28362f);
            TimezoneMapper.poly[304] = new TzPolygon(58.108963f, -133.17166f, 58.313625f, -133.38441f, 58.388897f, -133.45377f, 58.42699f, -133.37311f, 58.735752f, -133.84743f, 58.778606f, -133.98338f, 59.70417f, -133.98338f, 59.70417f, -133.17166f);
            TimezoneMapper.poly[305] = new TzPolygon(57.312572f, -133.17166f, 57.34693f, -133.06375f, 57.34693f, -132.93045f, 57.327858f, -132.98927f, 57.191505f, -133.08878f, 57.208942f, -133.17166f);
            TimezoneMapper.poly[306] = new TzPolygon(57.34693f, -133.07646f, 57.328327f, -133.17166f, 57.34693f, -133.17166f);
            TimezoneMapper.poly[307] = new TzPolygon(57.354977f, -132.38786f, 58.108963f, -133.17166f, 59.70417f, -133.17166f, 59.70417f, -132.38786f);
            TimezoneMapper.poly[308] = new TzPolygon(56.390076f, -130.92754f, 56.41284f, -131.09349f, 56.61451f, -131.58322f, 56.60091f, -131.83096f, 56.62438f, -131.83563f, 56.62438f, -130.92754f);
            TimezoneMapper.poly[309] = new TzPolygon(56.62438f, -131.83563f, 56.70606f, -131.8519f, 56.70606f, -130.92754f, 56.62438f, -130.92754f);
            TimezoneMapper.poly[310] = new TzPolygon(56.70606f, -131.8519f, 56.808132f, -131.87222f, 56.879803f, -132.12003f, 57.04644f, -132.04227f, 57.09218f, -132.3669f, 57.21424f, -132.24156f, 57.354977f, -132.38786f, 59.70417f, -132.38786f, 59.70417f, -130.92754f, 56.70606f, -130.92754f);
            TimezoneMapper.poly[311] = new TzPolygon(57.365986f, -132.38786f, 57.343872f, -132.36461f, 57.349106f, -132.38786f);
            TimezoneMapper.poly[312] = new TzPolygon(54.759205f, -130.55516f, 54.818798f, -130.48456f, 54.759205f, -130.43571f);
            TimezoneMapper.poly[313] = new TzPolygon(54.957565f, -130.28795f, 54.818336f, -130.28795f, 54.762882f, -130.41563f, 54.82028f, -130.4753f);
            TimezoneMapper.poly[314] = new TzPolygon(54.969955f, -130.27104f, 55.02708f, -130.19308f, 54.969955f, -130.14769f);
            TimezoneMapper.poly[315] = new TzPolygon(55.20215f, -130.05516f, 55.040604f, -130.05516f, 54.989647f, -130.11908f, 55.14324f, -130.13402f);
            TimezoneMapper.poly[316] = new TzPolygon(55.359047f, -129.98987f, 55.556423f, -130.1116f, 55.74217f, -130.14343f, 55.914852f, -129.98987f);
            TimezoneMapper.poly[317] = new TzPolygon(56.38843f, -130.92754f, 56.37039f, -130.78404f, 56.27191f, -130.6256f, 56.245045f, -130.46909f, 56.140877f, -130.41725f, 56.097202f, -130.25067f, 56.124535f, -130.10733f, 55.914852f, -130.00815f, 55.914852f, -130.92754f);
            TimezoneMapper.poly[318] = new TzPolygon(50.982723f, -117.157776f, 51.0178f, -117.1345f, 50.982723f, -117.1345f);
            TimezoneMapper.poly[319] = new TzPolygon(54.846806f, -121.62752f, 54.8881f, -121.7246f, 54.8363f, -121.7901f, 54.8678f, -121.8724f, 54.922f, -121.8426f, 55.1369f, -122.1838f, 55.1338f, -122.2658f, 55.1958f, -122.2572f, 55.4098f, -122.596f, 55.3413f, -122.6599f, 55.4171f, -122.8773f, 55.402f, -122.9823f, 55.592f, -122.9486f, 55.5504f, -123.0228f, 55.5764f, -123.0658f, 55.6465f, -123.1581f, 55.7368f, -123.1519f, 55.7009f, -123.2779f, 55.7287f, -123.3465f, 55.7992f, -123.3074f, 55.8841f, -123.4531f, 56.2071f, -123.4954f, 56.2317f, -123.5745f, 56.3576f, -123.5624f, 56.3535f, -123.6173f, 56.4873f, -123.5513f, 56.5841f, -123.7379f, 56.5854f, -123.6666f, 56.6677f, -123.6849f, 56.7699f, -123.8021f, 56.925f, -123.818f, 56.9984f, -123.5049f, 57.1061f, -123.3817f, 57.0884f, -123.2838f, 57.1606f, -122.9416f, 57.0712f, -122.874f, 57.0924f, -122.7968f, 57.0472f, -122.7485f, 57.0358f, -122.4591f, 56.98f, -122.2765f, 57.0209f, -122.1837f, 56.9572f, -121.9113f, 57.0514f, -121.7784f, 57.2364f, -121.6919f, 57.231625f, -121.62752f);
            TimezoneMapper.poly[320] = new TzPolygon(54.846806f, -121.62752f, 57.231625f, -121.62752f, 57.2111f, -121.3508f, 57.3284f, -120.9865f, 57.3888f, -121.0098f, 57.4296f, -120.4949f, 57.4642f, -120.4792f, 57.3514f, -120.3364f, 57.2317f, -120.3858f, 57.1898f, -120.1832f, 57.249977f, -120.0f, 53.804226f, -119.998634f, 53.7987f, -120.0013f, 53.9798f, -120.0783f, 53.9405f, -120.1705f, 54.0014f, -120.263f, 54.1234f, -120.1672f, 54.1278f, -120.5066f, 54.2892f, -120.6052f, 54.2991f, -120.7305f, 54.3569f, -120.6526f, 54.3978f, -120.7779f, 54.4942f, -120.8189f, 54.4503f, -121.0812f, 54.5486f, -121.1181f, 54.5308f, -121.4f, 54.6519f, -121.5288f, 54.7221f, -121.48f, 54.818f, -121.5598f);
            TimezoneMapper.poly[321] = new TzPolygon(51.0178f, -117.1345f, 51.2833f, -117.3331f, 51.2735f, -117.2173f, 51.3292f, -117.2012f, 51.3677f, -117.3618f, 51.4671f, -117.432f, 51.4183f, -117.7174f, 51.4882f, -117.8475f, 51.4514f, -117.9237f, 51.4732f, -117.976f, 51.6238f, -117.9801f, 51.656f, -117.9133f, 51.7221f, -117.943f, 51.7326f, -118.1097f, 51.7865f, -118.1846f, 51.8979f, -118.1133f, 51.9868f, -118.2881f, 51.9808f, -118.3895f, 52.0311f, -118.4004f, 52.0541f, -118.5884f, 52.1503f, -118.6782f, 52.1905f, -118.5782f, 52.2855f, -118.5484f, 52.388012f, -118.217865f, 52.447765f, -118.24609f, 52.479126f, -118.19228f, 52.61216f, -118.351715f, 52.676807f, -118.28639f, 52.77199f, -118.41312f, 52.85158f, -118.39693f, 52.901512f, -118.466606f, 52.881523f, -118.60472f, 53.034363f, -118.65328f, 53.052856f, -118.77344f, 53.116924f, -118.727936f, 53.233597f, -118.931946f, 53.230076f, -119.0112f, 53.12438f, -119.026955f, 53.18991f, -119.149994f, 53.17519f, -119.247246f, 53.36035f, -119.390045f, 53.36698f, -119.67577f, 53.491985f, -119.78511f, 53.51546f, -119.89631f, 53.612865f, -119.92315f, 53.587822f, -119.74689f, 53.616787f, -119.71444f, 53.70225f, -119.796425f, 53.71332f, -119.91152f, 53.77574f, -119.886566f, 53.804497f, -120.0f, 59.70417f, -120.0f, 59.70417f, -117.1345f);
            TimezoneMapper.poly[322] = new TzPolygon(50.982723f, -110.0f, 52.66731f, -110.0f, 52.6754f, -109.4352f, 52.74f, -109.3908f, 52.7399f, -109.3068f, 52.8203f, -109.3183f, 52.8202f, -109.2457f, 52.9297f, -109.2467f, 52.9359f, -109.1249f, 52.8855f, -109.1252f, 52.8854f, -109.0041f, 52.8047f, -109.0038f, 52.8043f, -108.883f, 53.0165f, -108.8829f, 53.0165f, -108.8189f, 53.2791f, -109.0743f, 53.3957f, -109.2887f, 53.3755f, -109.4601f, 53.4296f, -109.566f, 53.4888f, -109.5442f, 53.5872f, -109.6384f, 53.5995f, -109.7635f, 53.5518f, -109.8342f, 53.59894f, -110.0f, 59.70417f, -110.0f, 59.70417f, -107.018616f, 50.982723f, -107.018616f);
            TimezoneMapper.poly[323] = new TzPolygon(59.70417f, -138.49323f, 59.77163f, -138.62375f, 59.911114f, -138.70576f, 60.0f, -139.05322f, 60.0f, -136.52672f, 59.70417f, -136.52672f);
            TimezoneMapper.poly[324] = new TzPolygon(60.321346f, -141.0f, 60.22574f, -140.52194f, 60.31152f, -140.45546f, 60.184658f, -139.9773f, 60.33947f, -139.67455f, 60.35199f, -139.06902f, 60.092613f, -139.18619f, 60.0f, -139.05324f, 60.0f, -139.43279f, 60.055073f, -139.50497f, 60.0f, -139.57466f, 60.0f, -141.0f);
            TimezoneMapper.poly[325] = new TzPolygon(64.0744f, -139.4314f, 64.07369f, -139.36165f, 64.04294f, -139.42957f, 64.0465f, -139.45848f);
            TimezoneMapper.poly[326] = new TzPolygon(69.20628f, -140.99257f, 60.0f, -141.0f, 69.20628f, -141.0f);
            TimezoneMapper.poly[327] = new TzPolygon(59.70417f, -135.90091f, 59.80002f, -135.48311f, 59.70417f, -135.29446f);
            TimezoneMapper.poly[328] = new TzPolygon(68.877594f, -136.52672f, 68.89853f, -136.44197f, 67.65892f, -136.42953f, 67.581985f, -136.18246f, 67.40968f, -136.1996f, 67.30765f, -136.08055f, 67.17463f, -136.2211f, 67.00681f, -136.14499f, 67.00358f, -134.4836f, 60.0f, -134.4836f, 60.0f, -136.52672f);
            TimezoneMapper.poly[329] = new TzPolygon(69.20628f, -140.99283f, 69.64477f, -140.99222f, 69.20628f, -140.99222f);
            TimezoneMapper.poly[330] = new TzPolygon(62.075397f, -128.75116f, 62.05196f, -128.77756f, 62.13542f, -128.9908f, 62.110374f, -129.14386f, 62.151806f, -129.2769f, 62.21889f, -129.19016f, 62.27207f, -129.29948f, 62.37408f, -129.22087f, 62.41613f, -129.31094f, 62.48629f, -129.18755f, 62.58609f, -129.43294f, 62.56669f, -129.54909f, 62.668804f, -129.51314f, 62.867306f, -129.77693f, 63.074066f, -129.62096f, 63.085827f, -129.85236f, 63.182964f, -129.89194f, 63.17894f, -129.99648f, 63.253258f, -130.0555f, 63.24846f, -130.18236f, 63.30802f, -130.14969f, 63.375015f, -129.89621f, 63.466293f, -129.90654f, 63.45495f, -129.79999f, 63.615204f, -130.00153f, 63.612305f, -130.08357f, 63.69228f, -130.09808f, 63.656704f, -130.25139f, 63.69496f, -130.33678f, 63.758556f, -130.12279f, 63.861366f, -130.13966f, 63.87284f, -130.24574f, 63.825607f, -130.3531f, 63.92919f, -130.52928f, 63.969444f, -130.75822f, 64.04378f, -130.74928f, 64.04574f, -130.90141f, 64.129845f, -130.99124f, 64.17904f, -130.87549f, 64.278824f, -131.09778f, 64.328415f, -131.04776f, 64.41707f, -131.15512f, 64.456635f, -131.40785f, 64.367226f, -131.61281f, 64.381096f, -131.85693f, 64.52925f, -131.69177f, 64.53597f, -131.84961f, 64.70303f, -132.03886f, 64.78949f, -132.34831f, 64.777145f, -132.54362f, 64.83318f, -132.61684f, 64.88149f, -132.47247f, 64.96493f, -132.49606f, 65.07557f, -132.31767f, 65.09655f, -132.52501f, 65.20137f, -132.55086f, 65.16992f, -132.72906f, 65.23764f, -132.76291f, 65.293724f, -132.54237f, 65.600075f, -132.15518f, 65.78418f, -132.36798f, 65.85173f, -132.56154f, 65.9943f, -132.3156f, 65.9776f, -132.47629f, 66.03008f, -132.60687f, 65.91252f, -132.92369f, 66.03055f, -132.9483f, 66.03625f, -133.10315f, 65.9397f, -133.42873f, 65.96511f, -133.6009f, 66.06162f, -133.59554f, 66.07167f, -133.67897f, 66.16822f, -133.53883f, 66.282005f, -133.55112f, 66.32662f, -133.81738f, 66.4345f, -133.7458f, 66.44662f, -133.59766f, 66.52956f, -133.67534f, 66.56643f, -133.55873f, 66.7304f, -133.83037f, 66.81468f, -133.74821f, 66.95867f, -134.10062f, 67.00228f, -133.81447f, 67.00358f, -134.4836f, 67.00358f, -128.75116f);
            TimezoneMapper.poly[331] = new TzPolygon(62.075397f, -128.75116f, 62.127438f, -128.69254f, 62.12409f, -128.56514f, 61.945335f, -128.27887f, 61.840405f, -128.21762f, 61.84029f, -128.06938f, 61.70689f, -127.999855f, 61.61264f, -127.81985f, 61.50437f, -127.53838f, 61.510513f, -127.30416f, 61.46625f, -127.15178f, 61.198566f, -127.00804f, 61.066345f, -127.11375f, 61.03509f, -127.07143f, 61.055855f, -126.94121f, 60.75966f, -126.84197f, 60.782284f, -126.75153f, 60.742928f, -126.67243f, 60.812313f, -126.52465f, 60.776302f, -126.306854f, 60.852856f, -126.241615f, 60.86274f, -126.115326f, 60.81202f, -126.10016f, 60.80032f, -126.00361f, 60.893402f, -125.88178f, 60.82322f, -125.71059f, 60.782814f, -125.31742f, 60.850246f, -125.18715f, 60.855087f, -124.87189f, 60.973274f, -124.81674f, 60.96167f, -124.6125f, 60.787067f, -124.49017f, 60.680565f, -124.629875f, 60.467064f, -124.4303f, 60.45584f, -124.22546f, 60.33351f, -124.20845f, 60.09066f, -124.00138f, 60.0f, -124.01001f, 60.0f, -128.75116f);
            TimezoneMapper.poly[332] = new TzPolygon(67.00358f, -123.018715f, 67.00358f, -134.4836f, 68.41698f, -134.4836f, 68.41698f, -123.018715f);
            TimezoneMapper.poly[333] = new TzPolygon(69.17502f, -123.018715f, 69.17502f, -121.43903f, 68.37665f, -120.59595f, 68.37665f, -123.018715f);
            TimezoneMapper.poly[334] = new TzPolygon(69.17502f, -121.96273f, 69.66682f, -121.958374f, 69.66346f, -120.95993f, 69.40367f, -120.27158f, 69.23533f, -118.70242f, 69.17502f, -118.50597f);
            TimezoneMapper.poly[335] = new TzPolygon(69.65307f, -113.46177f, 69.65307f, -116.854515f, 69.67624f, -116.97916f, 69.676956f, -116.87703f, 70.04355f, -117.20289f, 70.03414f, -113.46177f);
            TimezoneMapper.poly[336] = new TzPolygon(70.03414f, -113.46177f, 70.03414f, -112.62099f, 69.78392f, -112.62099f, 69.77829f, -112.86337f, 70.03287f, -112.955025f);
            TimezoneMapper.poly[337] = new TzPolygon(69.78516f, -112.62099f, 69.78516f, -112.567856f, 69.78392f, -112.62099f);
            TimezoneMapper.poly[338] = new TzPolygon(69.78516f, -112.567856f, 69.78847f, -112.42551f, 69.890305f, -112.26257f, 70.02268f, -112.34404f, 70.02268f, -109.971375f, 70.47105f, -109.971375f, 70.47105f, -107.018616f, 69.17531f, -107.018616f, 69.00006f, -107.39404f, 68.93821f, -107.95418f, 68.94483f, -108.62762f, 68.892815f, -108.522385f, 68.762314f, -108.91434f, 68.762314f, -112.567856f);
            TimezoneMapper.poly[339] = new TzPolygon(59.70417f, -110.0f, 60.0f, -110.0f, 60.0f, -109.516396f, 59.70417f, -109.516396f);
            TimezoneMapper.poly[340] = new TzPolygon(67.77868f, -119.90586f, 65.558174f, -113.69033f, 60.0f, -113.69033f, 60.0f, -123.018715f, 67.77868f, -123.018715f);
            TimezoneMapper.poly[341] = new TzPolygon(64.660286f, -109.516396f, 64.71638f, -110.42866f, 65.4801f, -111.56917f, 65.48157f, -113.4759f, 65.558174f, -113.69029f, 65.558174f, -109.516396f);
            TimezoneMapper.poly[342] = new TzPolygon(67.7791f, -119.90586f, 67.8447f, -120.08948f, 67.8447f, -119.90586f);
            TimezoneMapper.poly[343] = new TzPolygon(67.99398f, -120.19185f, 67.8447f, -120.03421f, 67.8447f, -123.018715f, 67.99398f, -123.018715f);
            TimezoneMapper.poly[344] = new TzPolygon(68.37665f, -120.59595f, 67.99398f, -120.19185f, 67.99398f, -123.018715f, 68.37665f, -123.018715f);
            TimezoneMapper.poly[345] = new TzPolygon(64.506714f, -107.018616f, 64.660286f, -109.516396f, 64.660286f, -107.018616f);
            TimezoneMapper.poly[346] = new TzPolygon(60.0f, -107.018616f, 60.0f, -102.00035f, 55.795475f, -102.00054f, 55.791813f, -101.94811f, 55.1025f, -101.90919f, 55.0f, -102.25f, 54.75f, -102.5f, 54.5f, -102.5f, 54.25f, -102.25f, 54.05576f, -101.76265f, 50.982723f, -101.50901f, 50.982723f, -107.018616f);
            TimezoneMapper.poly[347] = new TzPolygon(59.07264f, -94.833755f, 59.026657f, -94.795906f, 59.019638f, -94.80545f, 59.054077f, -95.004196f);
            TimezoneMapper.poly[348] = new TzPolygon(58.806713f, -93.77651f, 58.79539f, -93.7694f, 58.78846f, -93.77024f, 58.803806f, -93.77948f);
            TimezoneMapper.poly[349] = new TzPolygon(59.34849f, -94.8007f, 59.33562f, -94.781494f, 59.33034f, -94.785225f, 59.34433f, -94.80108f);
            TimezoneMapper.poly[350] = new TzPolygon(59.672005f, -94.78913f, 59.666016f, -94.76225f, 59.662773f, -94.7665f, 59.668324f, -94.79691f);
            TimezoneMapper.poly[351] = new TzPolygon(58.798927f, -93.64084f, 58.794907f, -93.62988f, 58.790726f, -93.648544f, 58.79499f, -93.663536f);
            TimezoneMapper.poly[352] = new TzPolygon(58.810036f, -93.59474f, 58.79835f, -93.57213f, 58.790695f, -93.57375f, 58.80624f, -93.59711f);
            TimezoneMapper.poly[353] = new TzPolygon(58.43822f, -93.03173f, 58.43628f, -93.029205f, 58.42146f, -93.04193f, 58.4315f, -93.05244f);
            TimezoneMapper.poly[354] = new TzPolygon(58.182117f, -92.86316f, 58.16106f, -92.85267f, 58.158833f, -92.8562f, 58.181896f, -92.8679f);
            TimezoneMapper.poly[355] = new TzPolygon(64.506714f, -107.018616f, 64.37015f, -104.79741f, 64.38125f, -103.769875f, 64.27942f, -102.008194f, 62.165585f, -102.008194f, 62.165585f, -107.018616f);
            TimezoneMapper.poly[356] = new TzPolygon(64.27942f, -102.008194f, 67.0f, -102.008194f, 67.0f, -102.0f);
            TimezoneMapper.poly[357] = new TzPolygon(67.0f, -92.63556f, 67.0f, -93.469185f, 67.20077f, -93.469185f, 67.20077f, -92.63556f);
            TimezoneMapper.poly[358] = new TzPolygon(50.982723f, -90.46545f, 51.0f, -90.5f, 51.25f, -90.75f, 51.5f, -90.75f, 51.75f, -90.5f, 52.0f, -90.0f, 52.75f, -90.0f, 52.75f, -89.0f, 53.0f, -89.0f, 53.375f, -88.5f, 50.982723f, -88.5f);
            TimezoneMapper.poly[359] = new TzPolygon(53.625f, -88.5f, 54.0f, -89.0f, 54.0f, -90.0f, 56.218323f, -90.0f, 56.84754f, -88.99126f, 56.85917f, -88.89057f, 56.698887f, -88.66055f, 56.643063f, -88.5f);
            TimezoneMapper.poly[360] = new TzPolygon(56.77998f, -88.68715f, 56.745186f, -88.627754f, 56.743122f, -88.628494f, 56.777218f, -88.68868f);
            TimezoneMapper.poly[361] = new TzPolygon(57.2468f, -90.672966f, 57.237038f, -90.6399f, 57.235775f, -90.64399f, 57.239292f, -90.69456f);
            TimezoneMapper.poly[362] = new TzPolygon(55.266537f, -82.98747f, 55.24916f, -82.93801f, 55.235752f, -82.9481f, 55.262333f, -83.01717f);
            TimezoneMapper.poly[363] = new TzPolygon(55.199287f, -82.75071f, 55.19027f, -82.722824f, 55.18588f, -82.727684f, 55.198246f, -82.75294f);
            TimezoneMapper.poly[364] = new TzPolygon(52.861526f, -81.478264f, 52.861526f, -80.73014f, 52.735806f, -80.66996f, 52.666702f, -80.7123f);
            TimezoneMapper.poly[365] = new TzPolygon(64.95483f, -86.16356f, 64.99077f, -86.2081f, 65.50947f, -86.09036f, 65.70376f, -85.978485f, 64.95483f, -85.978485f);
            TimezoneMapper.poly[366] = new TzPolygon(67.0f, -92.63556f, 67.0f, -89.0f, 67.0f, -92.63556f);
            TimezoneMapper.poly[367] = new TzPolygon(53.413036f, -79.00419f, 53.400192f, -78.99675f, 53.396484f, -78.99838f, 53.40249f, -79.03968f);
            TimezoneMapper.poly[368] = new TzPolygon(53.400196f, -78.89979f, 53.398407f, -78.89813f, 53.39302f, -78.947365f, 53.39373f, -78.95543f);
            TimezoneMapper.poly[369] = new TzPolygon(53.54456f, -79.04768f, 53.539204f, -79.04269f, 53.5354f, -79.04814f, 53.540123f, -79.0937f);
            TimezoneMapper.poly[370] = new TzPolygon(53.8335f, -79.00214f, 53.812943f, -78.95705f, 53.809776f, -78.96452f, 53.82124f, -79.05943f);
            TimezoneMapper.poly[371] = new TzPolygon(53.87467f, -79.13916f, 53.869144f, -79.1067f, 53.867226f, -79.11002f, 53.871304f, -79.153564f);
            TimezoneMapper.poly[372] = new TzPolygon(54.174984f, -79.26193f, 54.170193f, -79.23705f, 54.165802f, -79.23864f, 54.172073f, -79.267914f);
            TimezoneMapper.poly[373] = new TzPolygon(54.116554f, -79.12479f, 54.109035f, -79.106735f, 54.10554f, -79.134796f, 54.108734f, -79.13504f);
            TimezoneMapper.poly[374] = new TzPolygon(50.982723f, -79.499825f, 51.475544f, -79.49845f, 51.55601f, -79.54535f, 51.66524f, -79.35505f, 51.64973f, -79.230865f, 51.52f, -79.2804f, 51.527687f, -79.18712f, 50.982723f, -79.18712f);
            TimezoneMapper.poly[375] = new TzPolygon(52.395023f, -78.558105f, 52.392563f, -78.55572f, 52.388126f, -78.57893f, 52.390537f, -78.583565f);
            TimezoneMapper.poly[376] = new TzPolygon(53.286568f, -78.9112f, 53.286568f, -78.947014f, 53.341278f, -78.98401f, 53.385715f, -78.9112f);
            TimezoneMapper.poly[377] = new TzPolygon(52.733807f, -78.84449f, 52.73593f, -78.87654f, 52.75924f, -78.84449f);
            TimezoneMapper.poly[378] = new TzPolygon(52.77623f, -78.79432f, 52.764336f, -78.775826f, 52.760098f, -78.78983f, 52.769695f, -78.79952f);
            TimezoneMapper.poly[379] = new TzPolygon(52.66527f, -78.75711f, 52.6633f, -78.7536f, 52.64901f, -78.7669f, 52.64979f, -78.7722f);
            TimezoneMapper.poly[380] = new TzPolygon(52.531868f, -78.701385f, 52.52592f, -78.70136f, 52.514782f, -78.717094f, 52.52454f, -78.72971f);
            TimezoneMapper.poly[381] = new TzPolygon(58.63984f, -78.58399f, 58.620667f, -78.50989f, 58.604378f, -78.58399f);
            TimezoneMapper.poly[382] = new TzPolygon(58.615105f, -78.50596f, 58.611446f, -78.50289f, 58.5972f, -78.51348f, 58.604774f, -78.54384f);
            TimezoneMapper.poly[383] = new TzPolygon(58.337955f, -77.99845f, 58.280254f, -77.78658f, 58.278996f, -77.78823f, 58.33151f, -78.01767f);
            TimezoneMapper.poly[384] = new TzPolygon(58.369335f, -78.07587f, 58.343006f, -78.00459f, 58.338737f, -78.04123f, 58.365776f, -78.09698f);
            TimezoneMapper.poly[385] = new TzPolygon(58.46215f, -78.24457f, 58.431282f, -78.185585f, 58.43071f, -78.19057f, 58.461025f, -78.26465f);
            TimezoneMapper.poly[386] = new TzPolygon(58.741207f, -78.559746f, 58.729362f, -78.53077f, 58.720543f, -78.55023f);
            TimezoneMapper.poly[387] = new TzPolygon(58.960453f, -78.39642f, 58.945908f, -78.39252f, 58.917305f, -78.44967f, 58.949f, -78.489426f);
            TimezoneMapper.poly[388] = new TzPolygon(59.01523f, -78.33157f, 58.99993f, -78.31001f, 58.980946f, -78.33541f, 58.98954f, -78.363594f);
            TimezoneMapper.poly[389] = new TzPolygon(59.110462f, -78.195526f, 59.101357f, -78.18714f, 59.09809f, -78.19651f, 59.107895f, -78.21503f);
            TimezoneMapper.poly[390] = new TzPolygon(59.129314f, -78.15382f, 59.14296f, -78.202324f, 59.204647f, -78.15382f);
            TimezoneMapper.poly[391] = new TzPolygon(59.418953f, -77.77176f, 59.417694f, -77.76713f, 59.404354f, -77.770805f, 59.415466f, -77.80351f);
            TimezoneMapper.poly[392] = new TzPolygon(59.713863f, -77.63951f, 59.70275f, -77.6262f, 59.694035f, -77.69397f, 59.7072f, -77.72637f);
            TimezoneMapper.poly[393] = new TzPolygon(59.71703f, -77.50214f, 59.762253f, -77.57824f, 59.77379f, -77.50214f);
            TimezoneMapper.poly[394] = new TzPolygon(59.88076f, -77.42864f, 59.8718f, -77.41976f, 59.86087f, -77.43119f, 59.867252f, -77.43776f);
            TimezoneMapper.poly[395] = new TzPolygon(59.90867f, -77.45044f, 59.899586f, -77.42732f, 59.890095f, -77.44571f, 59.90643f, -77.46397f);
            TimezoneMapper.poly[396] = new TzPolygon(60.775932f, -78.15051f, 60.788784f, -78.11085f, 60.77755f, -78.080475f, 60.775932f, -78.08685f);
            TimezoneMapper.poly[397] = new TzPolygon(60.768257f, -77.79018f, 60.759186f, -77.81918f, 60.762085f, -77.82449f);
            TimezoneMapper.poly[398] = new TzPolygon(62.392963f, -78.099945f, 62.381996f, -78.07932f, 62.37292f, -78.09239f, 62.386436f, -78.113266f);
            TimezoneMapper.poly[399] = new TzPolygon(62.40651f, -78.01951f, 62.400692f, -78.01129f, 62.371037f, -78.05177f, 62.395348f, -78.07939f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer5 {
        private Initializer5() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[400] = new TzPolygon(61.48782f, -77.62606f, 61.47865f, -77.60998f, 61.472168f, -77.62977f, 61.47546f, -77.637474f);
            TimezoneMapper.poly[401] = new TzPolygon(61.59809f, -77.783844f, 61.594986f, -77.754745f, 61.58338f, -77.77414f, 61.58505f, -77.79204f);
            TimezoneMapper.poly[402] = new TzPolygon(62.59398f, -77.776985f, 62.585346f, -77.64163f, 62.535133f, -77.76093f, 62.58678f, -77.84366f);
            TimezoneMapper.poly[403] = new TzPolygon(54.265003f, -79.40097f, 54.261654f, -79.38238f, 54.25181f, -79.41426f, 54.26307f, -79.42029f);
            TimezoneMapper.poly[404] = new TzPolygon(54.475594f, -79.55187f, 54.4775f, -79.54562f, 54.474773f, -79.52972f, 54.47009f, -79.55187f);
            TimezoneMapper.poly[405] = new TzPolygon(54.592674f, -79.58574f, 54.576904f, -79.56674f, 54.56246f, -79.61677f, 54.565342f, -79.62723f);
            TimezoneMapper.poly[406] = new TzPolygon(54.73038f, -79.58322f, 54.727726f, -79.58022f, 54.70808f, -79.60995f, 54.709873f, -79.6113f);
            TimezoneMapper.poly[407] = new TzPolygon(54.73038f, -79.51551f, 54.76863f, -79.31004f, 54.76863f, -76.01596f, 54.73038f, -76.01596f);
            TimezoneMapper.poly[408] = new TzPolygon(54.876186f, -79.4412f, 54.93983f, -79.05089f, 54.92948f, -79.0131f, 54.83087f, -79.4412f);
            TimezoneMapper.poly[409] = new TzPolygon(55.36321f, -77.61023f, 55.64924f, -77.144196f, 55.36321f, -77.144196f);
            TimezoneMapper.poly[410] = new TzPolygon(56.183613f, -76.76509f, 56.13185f, -76.7142f, 56.098312f, -76.78972f, 56.101486f, -76.79173f);
            TimezoneMapper.poly[411] = new TzPolygon(73.0201f, -98.222725f, 73.01747f, -98.208565f, 73.003204f, -98.235245f, 73.005554f, -98.24007f);
            TimezoneMapper.poly[412] = new TzPolygon(72.86974f, -98.208565f, 73.19901f, -100.47132f, 73.19901f, -98.208565f, 73.110916f, -98.208565f, 73.003365f, -98.48784f, 72.90507f, -98.36997f, 72.96986f, -98.208565f);
            TimezoneMapper.poly[413] = new TzPolygon(72.869064f, -98.203896f, 72.869064f, -97.28654f, 72.85358f, -97.28783f, 72.7826f, -97.04814f, 72.70152f, -97.051735f);
            TimezoneMapper.poly[414] = new TzPolygon(72.679504f, -96.900314f, 72.679504f, -96.544075f, 72.63316f, -96.42021f, 72.61055f, -96.42612f);
            TimezoneMapper.poly[415] = new TzPolygon(72.43683f, -95.23143f, 72.43683f, -93.53995f, 72.24243f, -93.894585f);
            TimezoneMapper.poly[416] = new TzPolygon(75.433876f, -96.5257f, 75.420654f, -96.48901f, 75.41699f, -96.498695f, 75.42985f, -96.53293f);
            TimezoneMapper.poly[417] = new TzPolygon(75.54403f, -96.68568f, 75.58513f, -96.38414f, 75.642746f, -96.33186f, 75.642746f, -96.240685f, 75.571144f, -95.89586f, 75.45913f, -96.12702f, 75.54641f, -96.47453f, 75.4601f, -96.460075f, 75.39428f, -96.68568f);
            TimezoneMapper.poly[418] = new TzPolygon(58.159847f, -77.5274f, 58.163628f, -77.53628f, 58.16418f, -77.5274f);
            TimezoneMapper.poly[419] = new TzPolygon(58.024086f, -77.22292f, 58.02086f, -77.20554f, 58.01151f, -77.22732f, 58.02018f, -77.25231f);
            TimezoneMapper.poly[420] = new TzPolygon(58.266327f, -77.70132f, 58.276955f, -77.73799f, 58.276955f, -77.70132f);
            TimezoneMapper.poly[421] = new TzPolygon(58.235916f, -77.680664f, 58.174416f, -77.54163f, 58.173714f, -77.54445f, 58.229973f, -77.69976f);
            TimezoneMapper.poly[422] = new TzPolygon(58.22605f, -77.59274f, 58.212757f, -77.52418f, 58.204567f, -77.53472f, 58.224697f, -77.596176f);
            TimezoneMapper.poly[423] = new TzPolygon(57.436703f, -76.843185f, 57.29239f, -76.73415f, 57.280334f, -76.758125f, 57.430027f, -76.84895f);
            TimezoneMapper.poly[424] = new TzPolygon(51.170013f, -76.01596f, 51.170013f, -58.86449f, 51.1672f, -58.8645f, 51.1697f, -59.1573f, 50.9897f, -59.1029f, 50.9899f, -59.316f, 50.982723f, -59.317802f, 50.982723f, -76.01596f);
            TimezoneMapper.poly[425] = new TzPolygon(51.170013f, -58.86449f, 51.21237f, -58.864346f, 51.170013f, -58.864346f);
            TimezoneMapper.poly[426] = new TzPolygon(51.26338f, -58.641468f, 51.369892f, -58.665348f, 51.369904f, -58.641468f);
            TimezoneMapper.poly[427] = new TzPolygon(51.21237f, -58.864346f, 51.3698f, -58.8638f, 51.36989f, -58.673203f, 51.22788f, -58.670654f, 51.21237f, -58.641468f);
            TimezoneMapper.poly[428] = new TzPolygon(51.369972f, -58.48899f, 51.369904f, -58.641468f, 51.4571f, -58.641468f, 51.4571f, -58.48899f);
            TimezoneMapper.poly[429] = new TzPolygon(51.4571f, -58.4325f, 51.37f, -58.433f, 51.369972f, -58.48899f, 51.4571f, -58.48899f);
            TimezoneMapper.poly[430] = new TzPolygon(51.45583f, -58.273293f, 51.4571f, -58.4325f, 51.4571f, -58.273293f);
            TzPolygon[] tzPolygonArr = TimezoneMapper.poly;
            float[] fArr = new float[HwHiCureDetection.RESULT_FAIL_TIMER_LEHGTH];
            // fill-array-data instruction
            fArr[0] = 53.837414f;
            fArr[1] = -67.5306f;
            fArr[2] = 53.764248f;
            fArr[3] = -67.59795f;
            fArr[4] = 53.71141f;
            fArr[5] = -67.489914f;
            fArr[6] = 53.7336f;
            fArr[7] = -67.44575f;
            fArr[8] = 53.553905f;
            fArr[9] = -67.315315f;
            fArr[10] = 53.533813f;
            fArr[11] = -67.06298f;
            fArr[12] = 53.4254f;
            fArr[13] = -66.88779f;
            fArr[14] = 53.333797f;
            fArr[15] = -67.025894f;
            fArr[16] = 53.30201f;
            fArr[17] = -66.95983f;
            fArr[18] = 53.07324f;
            fArr[19] = -67.027504f;
            fArr[20] = 53.13513f;
            fArr[21] = -67.06838f;
            fArr[22] = 53.113083f;
            fArr[23] = -67.12358f;
            fArr[24] = 53.17776f;
            fArr[25] = -67.27938f;
            fArr[26] = 53.111862f;
            fArr[27] = -67.348175f;
            fArr[28] = 53.134846f;
            fArr[29] = -67.38565f;
            fArr[30] = 53.00298f;
            fArr[31] = -67.36418f;
            fArr[32] = 52.988823f;
            fArr[33] = -67.24876f;
            fArr[34] = 52.950794f;
            fArr[35] = -67.3539f;
            fArr[36] = 52.894337f;
            fArr[37] = -67.33836f;
            fArr[38] = 52.833458f;
            fArr[39] = -67.193954f;
            fArr[40] = 52.89444f;
            fArr[41] = -67.09458f;
            fArr[42] = 52.763073f;
            fArr[43] = -67.04088f;
            fArr[44] = 52.76486f;
            fArr[45] = -66.96281f;
            fArr[46] = 52.68306f;
            fArr[47] = -66.87662f;
            fArr[48] = 52.75343f;
            fArr[49] = -66.84181f;
            fArr[50] = 52.674774f;
            fArr[51] = -66.77184f;
            fArr[52] = 52.799137f;
            fArr[53] = -66.78415f;
            fArr[54] = 52.76416f;
            fArr[55] = -66.712166f;
            fArr[56] = 52.786785f;
            fArr[57] = -66.6558f;
            fArr[58] = 52.960114f;
            fArr[59] = -66.6285f;
            fArr[60] = 52.958817f;
            fArr[61] = -66.49987f;
            fArr[62] = 53.024708f;
            fArr[63] = -66.494415f;
            fArr[64] = 53.028446f;
            fArr[65] = -66.43713f;
            fArr[66] = 52.97063f;
            fArr[67] = -66.32871f;
            fArr[68] = 52.863346f;
            fArr[69] = -66.284096f;
            fArr[70] = 52.86664f;
            fArr[71] = -66.40929f;
            fArr[72] = 52.619827f;
            fArr[73] = -66.28381f;
            fArr[74] = 52.671288f;
            fArr[75] = -66.395294f;
            fArr[76] = 52.62752f;
            fArr[77] = -66.43761f;
            fArr[78] = 52.363087f;
            fArr[79] = -66.34239f;
            fArr[80] = 52.38193f;
            fArr[81] = -66.427574f;
            fArr[82] = 52.32675f;
            fArr[83] = -66.48515f;
            fArr[84] = 52.16016f;
            fArr[85] = -66.36696f;
            fArr[86] = 52.153522f;
            fArr[87] = -66.28469f;
            fArr[88] = 52.319317f;
            fArr[89] = -66.27213f;
            fArr[90] = 52.172222f;
            fArr[91] = -66.083595f;
            fArr[92] = 52.10045f;
            fArr[93] = -66.092476f;
            fArr[94] = 52.066193f;
            fArr[95] = -65.99514f;
            fArr[96] = 52.123314f;
            fArr[97] = -65.85635f;
            fArr[98] = 52.092255f;
            fArr[99] = -65.72683f;
            fArr[100] = 52.11842f;
            fArr[101] = -65.67394f;
            fArr[102] = 51.986565f;
            fArr[103] = -65.660255f;
            fArr[104] = 52.108276f;
            fArr[105] = -65.498764f;
            fArr[106] = 52.020306f;
            fArr[107] = -65.4683f;
            fArr[108] = 51.97922f;
            fArr[109] = -65.36419f;
            fArr[110] = 51.829338f;
            fArr[111] = -65.34451f;
            fArr[112] = 51.874905f;
            fArr[113] = -65.26327f;
            fArr[114] = 51.766945f;
            fArr[115] = -65.181244f;
            fArr[116] = 51.723953f;
            fArr[117] = -64.954926f;
            fArr[118] = 51.776985f;
            fArr[119] = -64.93562f;
            fArr[120] = 51.76051f;
            fArr[121] = -64.715996f;
            fArr[122] = 51.596386f;
            fArr[123] = -64.53895f;
            fArr[124] = 51.750557f;
            fArr[125] = -64.29096f;
            fArr[126] = 52.010525f;
            fArr[127] = -64.36883f;
            fArr[128] = 51.97811f;
            fArr[129] = -64.2459f;
            fArr[130] = 52.075222f;
            fArr[131] = -64.3019f;
            fArr[132] = 52.14719f;
            fArr[133] = -64.24814f;
            fArr[134] = 52.12865f;
            fArr[135] = -64.183136f;
            fArr[136] = 52.277283f;
            fArr[137] = -64.25228f;
            fArr[138] = 52.413326f;
            fArr[139] = -64.12485f;
            fArr[140] = 52.591087f;
            fArr[141] = -64.2391f;
            fArr[142] = 52.600033f;
            fArr[143] = -64.172646f;
            fArr[144] = 52.72946f;
            fArr[145] = -64.14902f;
            fArr[146] = 52.777332f;
            fArr[147] = -63.745872f;
            fArr[148] = 52.88231f;
            fArr[149] = -63.617847f;
            fArr[150] = 52.770523f;
            fArr[151] = -63.6155f;
            fArr[152] = 52.66085f;
            fArr[153] = -63.388676f;
            fArr[154] = 52.62286f;
            fArr[155] = -63.864098f;
            fArr[156] = 52.564396f;
            fArr[157] = -64.035934f;
            fArr[158] = 52.478325f;
            fArr[159] = -64.091125f;
            fArr[160] = 52.364895f;
            fArr[161] = -64.00706f;
            fArr[162] = 52.314117f;
            fArr[163] = -63.7433f;
            fArr[164] = 52.308025f;
            fArr[165] = -63.82793f;
            fArr[166] = 52.04493f;
            fArr[167] = -63.65038f;
            fArr[168] = 52.08245f;
            fArr[169] = -63.833614f;
            fArr[170] = 51.992664f;
            fArr[171] = -63.81485f;
            fArr[172] = 51.99174f;
            fArr[173] = -58.273293f;
            fArr[174] = 51.4571f;
            fArr[175] = -58.273293f;
            fArr[176] = 51.4571f;
            fArr[177] = -76.01596f;
            fArr[178] = 53.837414f;
            fArr[179] = -76.01596f;
            tzPolygonArr[431] = new TzPolygon(fArr);
            TimezoneMapper.poly[432] = new TzPolygon(51.589436f, -58.273293f, 51.589436f, -57.608196f, 51.5877f, -57.9205f, 51.453f, -57.9185f, 51.45583f, -58.273293f);
            TimezoneMapper.poly[433] = new TzPolygon(51.99163f, -57.608196f, 51.99174f, -58.273293f, 53.630226f, -58.273293f, 53.630226f, -57.608196f);
            TimezoneMapper.poly[434] = new TzPolygon(51.41128f, -57.096817f, 51.41247f, -57.112633f, 51.47837f, -57.11237f, 51.47837f, -57.096817f);
            TimezoneMapper.poly[435] = new TzPolygon(51.99163f, -57.608196f, 51.991547f, -57.108913f, 51.592262f, -57.100033f, 51.589436f, -57.608196f);
            TimezoneMapper.poly[436] = new TzPolygon(53.630226f, -57.03374f, 53.630226f, -56.53009f, 53.6165f, -56.478313f, 53.6165f, -57.13255f);
            TimezoneMapper.poly[437] = new TzPolygon(51.99163f, -57.608196f, 53.630226f, -57.608196f, 53.630226f, -57.3788f, 53.577316f, -57.515614f, 53.605858f, -57.336407f, 53.531693f, -57.27125f, 53.46508f, -57.291637f, 53.35f, -57.5f, 53.05f, -57.5f, 52.75f, -57.1073f, 51.991547f, -57.108913f);
            TimezoneMapper.poly[438] = new TzPolygon(51.47837f, -57.22529f, 51.498535f, -57.24028f, 51.47837f, -57.344288f, 51.47837f, -57.608196f, 51.589436f, -57.608196f, 51.592262f, -57.100033f, 51.47837f, -57.0979f);
            TimezoneMapper.poly[439] = new TzPolygon(59.055573f, -65.62086f, 59.036938f, -65.59926f, 59.036015f, -65.60235f, 59.053276f, -65.623474f);
            TimezoneMapper.poly[440] = new TzPolygon(59.38555f, -65.59576f, 59.353523f, -65.564255f, 59.347866f, -65.56721f, 59.380806f, -65.61072f);
            TimezoneMapper.poly[441] = new TzPolygon(59.336494f, -65.41449f, 59.3235f, -65.40585f, 59.321636f, -65.41969f, 59.33083f, -65.43562f);
            TimezoneMapper.poly[442] = new TzPolygon(55.947544f, -63.67323f, 56.0f, -63.419415f, 55.947544f, -63.419415f);
            TimezoneMapper.poly[443] = new TzPolygon(59.509193f, -64.82954f, 59.436302f, -64.69346f, 59.509193f, -64.51388f, 59.509193f, -64.35626f, 59.431496f, -64.512054f, 59.30195f, -64.54398f, 59.107204f, -64.49082f, 59.032913f, -64.27533f, 58.98712f, -64.47692f, 59.079105f, -64.72719f, 59.044888f, -64.8427f, 58.95381f, -64.82469f, 58.941536f, -64.91021f, 58.895195f, -64.3133f, 58.745033f, -64.138885f, 58.826756f, -64.00621f, 58.88448f, -63.69784f, 58.76451f, -63.46897f, 58.690475f, -64.04234f, 58.57517f, -64.11455f, 58.519287f, -64.026474f, 58.570595f, -63.90074f, 58.474705f, -63.827675f, 58.352184f, -64.1786f, 58.225693f, -64.23685f, 58.16894f, -64.43404f, 58.080723f, -64.44353f, 58.04363f, -64.236115f, 57.779015f, -64.07475f, 57.797077f, -63.932144f, 57.72086f, -63.89341f, 57.6966f, -63.811802f, 57.7348f, -63.77173f, 57.667873f, -63.704735f, 57.732315f, -63.67125f, 57.732754f, -63.591286f, 57.662575f, -63.604565f, 57.576164f, -63.767384f, 57.36339f, -63.72937f, 57.33441f, -63.857983f, 57.27678f, -63.885437f, 57.196564f, -63.860207f, 57.28168f, -63.82416f, 57.242275f, -63.749256f, 57.11595f, -63.79708f, 57.08243f, -63.889454f, 56.88656f, -63.875847f, 56.85757f, -64.01774f, 56.796764f, -63.999454f, 56.704235f, -64.14007f, 56.45122f, -63.890377f, 56.430378f, -64.18453f, 56.263012f, -64.09956f, 56.215565f, -63.883266f, 56.16049f, -64.03075f, 56.073692f, -64.02832f, 56.13406f, -63.86128f, 56.053925f, -63.836876f, 56.0f, -63.419415f, 56.0f, -65.03066f, 59.509193f, -65.03066f);
            TimezoneMapper.poly[444] = new TzPolygon(57.816128f, -69.27326f, 57.81556f, -69.26977f, 57.802113f, -69.295166f, 57.80265f, -69.29952f);
            TimezoneMapper.poly[445] = new TzPolygon(57.868465f, -69.17904f, 57.866703f, -69.17711f, 57.84638f, -69.22409f, 57.847317f, -69.23022f);
            TimezoneMapper.poly[446] = new TzPolygon(57.981194f, -68.81102f, 57.980206f, -68.80575f, 57.96996f, -68.857185f, 57.971745f, -68.85867f);
            TimezoneMapper.poly[447] = new TzPolygon(57.99852f, -68.69695f, 57.99749f, -68.69297f, 57.988865f, -68.72999f, 57.991055f, -68.73323f);
            TimezoneMapper.poly[448] = new TzPolygon(58.41071f, -67.62014f, 58.366447f, -67.58292f, 58.402447f, -67.50116f, 58.36318f, -67.493484f, 58.282272f, -67.63278f, 58.300423f, -67.68321f);
            TimezoneMapper.poly[449] = new TzPolygon(58.418457f, -67.58716f, 58.40815f, -67.56549f, 58.401993f, -67.57637f, 58.409008f, -67.591255f);
            TimezoneMapper.poly[450] = new TzPolygon(58.763493f, -66.47864f, 58.75266f, -66.46719f, 58.744606f, -66.47257f, 58.75684f, -66.48144f);
            TimezoneMapper.poly[451] = new TzPolygon(59.036133f, -69.47234f, 59.02225f, -69.45677f, 59.01856f, -69.45763f, 59.03282f, -69.47547f);
            TimezoneMapper.poly[452] = new TzPolygon(59.067776f, -69.09537f, 59.11473f, -69.14369f, 59.142033f, -69.12694f, 59.093033f, -69.09537f);
            TimezoneMapper.poly[453] = new TzPolygon(53.837414f, -67.5306f, 53.920174f, -67.61587f, 54.021774f, -67.816505f, 54.139565f, -67.79078f, 54.155155f, -67.65435f, 54.201572f, -67.61609f, 54.433838f, -67.77249f, 54.496964f, -67.649216f, 54.467487f, -67.62457f, 54.485916f, -67.44381f, 54.53198f, -67.41365f, 54.47953f, -67.2831f, 54.52111f, -67.230316f, 54.5892f, -67.28228f, 54.679066f, -67.07736f, 54.967117f, -67.407936f, 55.07864f, -67.42976f, 55.07042f, -67.30641f, 54.853256f, -67.02533f, 54.71478f, -66.66125f, 54.76846f, -66.71613f, 54.82106f, -66.60906f, 55.006435f, -66.78589f, 54.97843f, -66.63951f, 55.14534f, -66.784134f, 55.219433f, -66.76898f, 55.195827f, -66.686646f, 55.310436f, -66.83204f, 55.36066f, -66.813385f, 55.25676f, -66.584564f, 54.973854f, -66.24614f, 54.917423f, -66.05445f, 54.92066f, -65.85339f, 54.71363f, -65.70084f, 54.74756f, -65.63816f, 54.739246f, -65.451515f, 54.841465f, -65.46319f, 54.816486f, -65.28168f, 54.967976f, -65.07528f, 54.82646f, -64.76106f, 54.7352f, -64.7804f, 54.7236f, -64.54834f, 54.760242f, -64.54003f, 54.78675f, -64.39229f, 54.7267f, -64.19195f, 54.614254f, -64.07996f, 54.634216f, -63.71977f, 54.806877f, -63.905125f, 54.825115f, -63.8233f, 54.954f, -63.813988f, 54.925526f, -63.589638f, 55.125763f, -63.590874f, 55.262775f, -63.40433f, 55.238567f, -63.62297f, 55.272232f, -63.667896f, 55.320442f, -63.535183f, 55.341858f, -63.578274f, 55.394764f, -63.32248f, 55.4679f, -63.78609f, 55.554073f, -63.670326f, 55.640583f, -63.661472f, 55.696922f, -63.742863f, 55.7779f, -63.67126f, 55.79115f, -63.75914f, 55.911304f, -63.84858f, 55.947544f, -63.67323f, 55.947544f, -63.322098f, 53.837414f, -63.322098f);
            TimezoneMapper.poly[454] = new TzPolygon(58.855976f, -66.09961f, 58.84232f, -66.06524f, 58.833153f, -66.10182f, 58.84261f, -66.11419f);
            TimezoneMapper.poly[455] = new TzPolygon(58.98971f, -65.928246f, 58.98971f, -65.87296f, 58.979847f, -65.863495f, 58.97497f, -65.87533f);
            TimezoneMapper.poly[456] = new TzPolygon(59.062336f, -65.72737f, 59.062336f, -65.708115f, 59.05992f, -65.71588f);
            TimezoneMapper.poly[457] = new TzPolygon(59.108196f, -65.67117f, 59.14643f, -65.70896f, 59.14643f, -65.67117f);
            TimezoneMapper.poly[458] = new TzPolygon(60.913162f, -70.04687f, 60.87985f, -70.02524f, 60.87815f, -70.0278f, 60.905746f, -70.055595f);
            TimezoneMapper.poly[459] = new TzPolygon(61.008507f, -70.08544f, 61.015667f, -70.098274f, 61.024628f, -70.091194f, 61.02133f, -70.08544f);
            TimezoneMapper.poly[460] = new TzPolygon(59.957592f, -69.65809f, 59.928047f, -69.638916f, 59.9132f, -69.644615f, 59.922935f, -69.69343f);
            TimezoneMapper.poly[461] = new TzPolygon(59.699116f, -69.5948f, 59.699116f, -69.579926f, 59.696316f, -69.5821f);
            TimezoneMapper.poly[462] = new TzPolygon(59.699116f, -69.5948f, 59.70126f, -69.60453f, 59.71491f, -69.58055f, 59.709724f, -69.571686f, 59.699116f, -69.579926f);
            TimezoneMapper.poly[463] = new TzPolygon(60.08331f, -69.59038f, 60.07386f, -69.55882f, 60.073425f, -69.56473f, 60.081196f, -69.59192f);
            TimezoneMapper.poly[464] = new TzPolygon(60.73333f, -69.54796f, 60.73119f, -69.54346f, 60.718327f, -69.565506f, 60.719265f, -69.57172f);
            TimezoneMapper.poly[465] = new TzPolygon(59.509193f, -64.82954f, 59.565094f, -64.9339f, 59.606285f, -64.81391f, 59.71924f, -64.74193f, 59.82979f, -64.82571f, 59.886227f, -64.76759f, 59.873558f, -64.677155f, 59.925926f, -64.659996f, 59.97331f, -64.854805f, 60.060326f, -64.931435f, 60.04221f, -64.81568f, 60.062664f, -64.7189f, 60.106876f, -64.74327f, 60.1161f, -64.60494f, 60.050045f, -64.60494f, 60.02381f, -64.69978f, 60.039776f, -64.60494f, 59.509193f, -64.60494f);
            TimezoneMapper.poly[466] = new TzPolygon(60.1161f, -64.60494f, 60.171368f, -64.62904f, 60.17446f, -64.73977f, 60.227196f, -64.825584f, 60.227196f, -64.60494f);
            TimezoneMapper.poly[467] = new TzPolygon(59.509193f, -64.51388f, 59.536037f, -64.44775f, 59.509193f, -64.44775f);
            TimezoneMapper.poly[468] = new TzPolygon(59.509193f, -64.35626f, 59.51071f, -64.35322f, 59.509193f, -64.35322f);
            TimezoneMapper.poly[469] = new TzPolygon(59.51071f, -64.35322f, 59.536037f, -64.44775f, 59.536037f, -64.35322f);
            TimezoneMapper.poly[470] = new TzPolygon(60.227196f, -64.94017f, 60.25696f, -64.9576f, 60.319107f, -64.81784f, 60.2816f, -64.78773f, 60.369843f, -64.844246f, 60.30653f, -64.53027f, 60.276737f, -64.83033f, 60.227196f, -64.921486f);
            TimezoneMapper.poly[471] = new TzPolygon(60.372677f, -64.72579f, 60.372677f, -64.447426f, 60.311165f, -64.523735f);
            TimezoneMapper.poly[472] = new TzPolygon(76.3397f, -26.608158f, 76.3397f, -22.892895f, 75.948524f, -22.918842f, 75.841736f, -21.152466f, 75.42361f, -21.152466f, 75.42361f, -21.219921f, 75.550095f, -21.486864f, 75.67069f, -22.178495f, 75.63659f, -22.207506f, 75.454056f, -21.399105f, 75.48634f, -22.157324f, 75.4339f, -21.427864f, 75.42361f, -21.416576f, 75.42361f, -26.608158f);
            TimezoneMapper.poly[473] = new TzPolygon(76.3397f, -22.892895f, 76.36901f, -22.890951f, 76.3397f, -22.890951f);
            TimezoneMapper.poly[474] = new TzPolygon(75.819565f, -20.74267f, 75.841736f, -21.116737f, 75.841736f, -20.74267f);
            TimezoneMapper.poly[475] = new TzPolygon(76.36901f, -22.890951f, 76.447014f, -22.885777f, 76.36901f, -22.885777f);
            TimezoneMapper.poly[476] = new TzPolygon(75.81696f, -19.672276f, 75.74563f, -19.495007f, 75.81696f, -20.698652f);
            TimezoneMapper.poly[477] = new TzPolygon(76.447014f, -22.885777f, 77.4442f, -22.81963f, 76.447014f, -22.81963f);
            TimezoneMapper.poly[478] = new TzPolygon(77.4442f, -22.81963f, 78.43155f, -22.754137f, 77.4442f, -22.754137f);
            TimezoneMapper.poly[479] = new TzPolygon(78.43155f, -22.754137f, 79.653534f, -22.673079f, 78.43155f, -22.673079f);
            TimezoneMapper.poly[480] = new TzPolygon(79.551575f, -20.5303f, 79.653534f, -22.673079f, 79.653534f, -20.5303f);
            TimezoneMapper.poly[481] = new TzPolygon(79.551575f, -20.5303f, 79.4985f, -19.414719f, 79.34874f, -19.294922f, 79.34874f, -20.5303f);
            TimezoneMapper.poly[482] = new TzPolygon(71.16384f, -24.470482f, 71.18707f, -24.514956f, 72.3483f, -24.400326f, 71.16384f, -24.400326f);
            TimezoneMapper.poly[483] = new TzPolygon(72.58957f, -24.34317f, 72.58957f, -22.335497f, 72.53858f, -22.158497f, 72.49474f, -22.222996f, 72.51466f, -22.05307f, 72.48108f, -21.925478f, 72.39678f, -21.989414f, 72.456894f, -22.299664f, 72.44441f, -22.750557f, 72.37603f, -22.70595f, 72.31072f, -22.306427f, 72.26036f, -22.323538f, 72.29861f, -22.213316f, 72.2753f, -22.033676f, 72.21509f, -22.191515f, 72.142395f, -22.161154f, 72.11957f, -22.322367f, 72.15914f, -22.617832f, 72.21306f, -22.539127f, 72.16957f, -22.682697f, 72.34183f, -23.16745f, 72.47009f, -24.026295f, 72.55821f, -24.122921f, 72.566986f, -24.21601f, 72.53447f, -24.18305f);
            TimezoneMapper.poly[484] = new TzPolygon(76.17383f, -63.73601f, 76.159996f, -63.757145f, 77.469055f, -64.91369f, 77.469055f, -63.73601f);
            TimezoneMapper.poly[485] = new TzPolygon(79.13302f, -66.11566f, 79.131065f, -66.106804f, 79.12398f, -66.19806f);
            TimezoneMapper.poly[486] = new TzPolygon(77.469055f, -64.91369f, 79.13161f, -66.382545f, 79.11364f, -66.05612f, 79.139244f, -65.94883f, 79.139244f, -60.919846f, 77.469055f, -60.919846f);
            TimezoneMapper.poly[487] = new TzPolygon(81.55024f, -60.919846f, 81.74422f, -61.446815f, 81.84989f, -60.919846f);
            TimezoneMapper.poly[488] = new TzPolygon(54.991585f, -131.53369f, 54.99245f, -131.53462f, 54.996513f, -131.5123f, 54.991585f, -131.5096f);
            TimezoneMapper.poly[489] = new TzPolygon(55.190212f, -131.59985f, 55.2687f, -131.59157f, 55.24391f, -131.51216f, 55.283344f, -131.56891f, 55.205917f, -131.39755f, 55.190212f, -131.39238f);
            TimezoneMapper.poly[490] = new TzPolygon(-5.946362f, 12.808323f, -5.992188f, 12.664001f, -5.902699f, 12.664001f, -5.902699f, 12.808323f);
            TimezoneMapper.poly[491] = new TzPolygon(-5.912349f, 12.873817f, -5.919939f, 12.876115f, -5.926372f, 12.861409f, -5.92568f, 12.857043f);
            TimezoneMapper.poly[492] = new TzPolygon(-5.902699f, 14.67042f, -5.910247f, 14.595139f, -5.902699f, 14.530292f);
            TimezoneMapper.poly[493] = new TzPolygon(-5.902699f, 16.551239f, -5.902699f, 20.044312f, -5.942223f, 20.019741f, -6.031112f, 19.840275f, -6.187502f, 19.688469f, -6.575556f, 19.736664f, -6.842501f, 19.932499f, -6.999361f, 19.962917f, -6.999424f, 19.543793f, -7.057445f, 19.562788f, -7.177791f, 19.495575f, -7.313495f, 19.48716f, -7.472067f, 19.550465f, -7.502584f, 19.4859f, -7.566804f, 19.473656f, -7.582895f, 19.368631f, -7.700866f, 19.409292f, -7.899361f, 19.343103f, -8.0f, 19.374403f, -8.0f, 18.800198f, -7.921579f, 18.779507f, -7.920376f, 18.53232f, -8.0f, 18.527325f, -8.027326f, 18.37566f, -7.989454f, 18.216976f, -8.040184f, 18.098198f, -8.099057f, 18.10036f, -8.078966f, 17.884922f, -8.129065f, 17.582966f, -8.082833f, 17.505476f, -8.043939f, 17.532593f, -7.927346f, 17.442356f, -7.879313f, 17.453146f, -7.758533f, 17.321444f, -7.636915f, 17.306286f, -7.475055f, 17.156246f, -7.432196f, 17.18526f, -7.422679f, 17.110168f, -7.346213f, 17.10963f, -7.30005f, 17.00551f, -7.203343f, 16.95371f, -7.074109f, 16.928226f, -7.074389f, 16.968035f, -6.986133f, 16.97289f, -6.579175f, 16.751278f, -6.361054f, 16.687502f, -6.356079f, 16.722193f, -6.193947f, 16.73087f, -6.085707f, 16.606726f, -6.067893f, 16.632845f, -6.058835f, 16.599022f, -5.910797f, 16.59072f);
            TimezoneMapper.poly[494] = new TzPolygon(-11.109554f, 22.722334f, -11.030554f, 22.562166f, -11.051505f, 22.497513f, -11.120493f, 22.49493f, -11.174831f, 22.436996f, -11.176506f, 22.351242f, -11.246061f, 22.27194f, -10.874724f, 22.174316f, -10.757748f, 22.33394f, -10.54372f, 22.316511f, -10.516284f, 22.277674f, -10.385121f, 22.327953f, -10.209412f, 22.236195f, -9.926921f, 22.191364f, -9.877662f, 22.073269f, -9.751761f, 22.00342f, -9.653144f, 21.875927f, -9.420432f, 21.79289f, -9.241109f, 21.860544f, -8.909537f, 21.861555f, -8.496954f, 21.95976f, -8.181728f, 21.889292f, -7.938798f, 21.74545f, -7.570119f, 21.852337f, -7.327551f, 21.819744f, -7.283894f, 21.780588f, -7.28143f, 20.541683f, -7.144082f, 20.543612f, -6.913397f, 20.627148f, -6.913004f, 20.317f, -6.998033f, 20.30171f, -6.999361f, 19.962917f, -6.842501f, 19.932499f, -6.575556f, 19.736664f, -6.187502f, 19.688469f, -6.031112f, 19.840275f, -5.942223f, 20.019741f, -5.902699f, 20.044312f, -5.902699f, 22.722334f);
            TzPolygon[] tzPolygonArr2 = TimezoneMapper.poly;
            float[] fArr2 = new float[HwHiCureDetection.RESULT_FAIL_TIMER_LEHGTH];
            // fill-array-data instruction
            fArr2[0] = -9.950404f;
            fArr2[1] = 22.722334f;
            fArr2[2] = -9.950404f;
            fArr2[3] = 28.628181f;
            fArr2[4] = -9.95717f;
            fArr2[5] = 28.62495f;
            fArr2[6] = -10.15705f;
            fArr2[7] = 28.621767f;
            fArr2[8] = -10.230619f;
            fArr2[9] = 28.577156f;
            fArr2[10] = -10.306083f;
            fArr2[11] = 28.636377f;
            fArr2[12] = -10.518403f;
            fArr2[13] = 28.634087f;
            fArr2[14] = -10.662585f;
            fArr2[15] = 28.69679f;
            fArr2[16] = -10.832429f;
            fArr2[17] = 28.553543f;
            fArr2[18] = -11.105466f;
            fArr2[19] = 28.47593f;
            fArr2[20] = -11.371193f;
            fArr2[21] = 28.458448f;
            fArr2[22] = -11.474719f;
            fArr2[23] = 28.393972f;
            fArr2[24] = -11.830667f;
            fArr2[25] = 28.438667f;
            fArr2[26] = -11.99038f;
            fArr2[27] = 28.759361f;
            fArr2[28] = -12.389108f;
            fArr2[29] = 29.058811f;
            fArr2[30] = -12.3642f;
            fArr2[31] = 29.26692f;
            fArr2[32] = -12.412827f;
            fArr2[33] = 29.301735f;
            fArr2[34] = -12.469826f;
            fArr2[35] = 29.499445f;
            fArr2[36] = -12.422435f;
            fArr2[37] = 29.537416f;
            fArr2[38] = -12.333491f;
            fArr2[39] = 29.456163f;
            fArr2[40] = -12.23857f;
            fArr2[41] = 29.489256f;
            fArr2[42] = -12.156498f;
            fArr2[43] = 29.80696f;
            fArr2[44] = -13.455143f;
            fArr2[45] = 29.808962f;
            fArr2[46] = -13.455675f;
            fArr2[47] = 29.712383f;
            fArr2[48] = -13.408811f;
            fArr2[49] = 29.610662f;
            fArr2[50] = -13.264661f;
            fArr2[51] = 29.68233f;
            fArr2[52] = -13.226477f;
            fArr2[53] = 29.670315f;
            fArr2[54] = -13.213718f;
            fArr2[55] = 29.589062f;
            fArr2[56] = -13.313898f;
            fArr2[57] = 29.441837f;
            fArr2[58] = -13.331981f;
            fArr2[59] = 29.316036f;
            fArr2[60] = -13.443042f;
            fArr2[61] = 29.182705f;
            fArr2[62] = -13.369787f;
            fArr2[63] = 29.121506f;
            fArr2[64] = -13.415883f;
            fArr2[65] = 29.003656f;
            fArr2[66] = -13.161983f;
            fArr2[67] = 28.903566f;
            fArr2[68] = -13.145169f;
            fArr2[69] = 28.846952f;
            fArr2[70] = -12.945952f;
            fArr2[71] = 28.790207f;
            fArr2[72] = -12.818785f;
            fArr2[73] = 28.652645f;
            fArr2[74] = -12.8931f;
            fArr2[75] = 28.572529f;
            fArr2[76] = -12.734131f;
            fArr2[77] = 28.465471f;
            fArr2[78] = -12.634151f;
            fArr2[79] = 28.523245f;
            fArr2[80] = -12.516173f;
            fArr2[81] = 28.441603f;
            fArr2[82] = -12.41904f;
            fArr2[83] = 28.31492f;
            fArr2[84] = -12.38277f;
            fArr2[85] = 28.174202f;
            fArr2[86] = -12.435419f;
            fArr2[87] = 28.124369f;
            fArr2[88] = -12.352782f;
            fArr2[89] = 28.07573f;
            fArr2[90] = -12.353865f;
            fArr2[91] = 27.955221f;
            fArr2[92] = -12.240463f;
            fArr2[93] = 27.917715f;
            fArr2[94] = -12.22995f;
            fArr2[95] = 27.818398f;
            fArr2[96] = -12.289334f;
            fArr2[97] = 27.75808f;
            fArr2[98] = -12.275398f;
            fArr2[99] = 27.631338f;
            fArr2[100] = -12.157314f;
            fArr2[101] = 27.514883f;
            fArr2[102] = -11.927835f;
            fArr2[103] = 27.463192f;
            fArr2[104] = -11.786697f;
            fArr2[105] = 27.232048f;
            fArr2[106] = -11.568419f;
            fArr2[107] = 27.20575f;
            fArr2[108] = -11.611302f;
            fArr2[109] = 27.028358f;
            fArr2[110] = -11.698529f;
            fArr2[111] = 27.048574f;
            fArr2[112] = -11.846878f;
            fArr2[113] = 27.007517f;
            fArr2[114] = -11.984588f;
            fArr2[115] = 26.88356f;
            fArr2[116] = -11.963437f;
            fArr2[117] = 26.774708f;
            fArr2[118] = -12.008998f;
            fArr2[119] = 26.707691f;
            fArr2[120] = -11.915171f;
            fArr2[121] = 26.444397f;
            fArr2[122] = -11.966119f;
            fArr2[123] = 26.330044f;
            fArr2[124] = -11.927559f;
            fArr2[125] = 26.212015f;
            fArr2[126] = -11.940619f;
            fArr2[127] = 25.989666f;
            fArr2[128] = -11.800424f;
            fArr2[129] = 25.880493f;
            fArr2[130] = -11.815708f;
            fArr2[131] = 25.71873f;
            fArr2[132] = -11.728622f;
            fArr2[133] = 25.645704f;
            fArr2[134] = -11.785126f;
            fArr2[135] = 25.497442f;
            fArr2[136] = -11.687667f;
            fArr2[137] = 25.477446f;
            fArr2[138] = -11.626969f;
            fArr2[139] = 25.336714f;
            fArr2[140] = -11.370209f;
            fArr2[141] = 25.278252f;
            fArr2[142] = -11.195846f;
            fArr2[143] = 25.351795f;
            fArr2[144] = -11.262112f;
            fArr2[145] = 24.87354f;
            fArr2[146] = -11.306f;
            fArr2[147] = 24.738182f;
            fArr2[148] = -11.456333f;
            fArr2[149] = 24.583494f;
            fArr2[150] = -11.475698f;
            fArr2[151] = 24.445423f;
            fArr2[152] = -11.409216f;
            fArr2[153] = 24.382689f;
            fArr2[154] = -11.389171f;
            fArr2[155] = 24.288721f;
            fArr2[156] = -11.285923f;
            fArr2[157] = 24.39319f;
            fArr2[158] = -11.092475f;
            fArr2[159] = 24.382957f;
            fArr2[160] = -11.036435f;
            fArr2[161] = 24.141476f;
            fArr2[162] = -10.918657f;
            fArr2[163] = 24.129955f;
            fArr2[164] = -10.88936f;
            fArr2[165] = 24.058533f;
            fArr2[166] = -10.891575f;
            fArr2[167] = 23.995695f;
            fArr2[168] = -11.029958f;
            fArr2[169] = 23.830372f;
            fArr2[170] = -10.938204f;
            fArr2[171] = 23.435528f;
            fArr2[172] = -11.102197f;
            fArr2[173] = 23.203173f;
            fArr2[174] = -11.12396f;
            fArr2[175] = 23.084038f;
            fArr2[176] = -11.061874f;
            fArr2[177] = 22.867281f;
            fArr2[178] = -11.109554f;
            fArr2[179] = 22.722334f;
            tzPolygonArr2[495] = new TzPolygon(fArr2);
            TimezoneMapper.poly[496] = new TzPolygon(-5.902699f, 29.587421f, -6.101694f, 29.578022f, -6.304459f, 29.611574f, -6.60899f, 29.791624f, -6.836075f, 30.08506f, -7.048671f, 30.25485f, -8.22436f, 30.774246f, -8.474615f, 28.901918f, -8.636463f, 28.9639f, -8.794926f, 28.89654f, -9.034298f, 28.695614f, -9.222055f, 28.417686f, -9.308095f, 28.363634f, -9.376392f, 28.36138f, -9.419963f, 28.41688f, -9.361122f, 28.527126f, -9.443811f, 28.515633f, -9.557144f, 28.577328f, -9.555313f, 28.622702f, -9.57978f, 28.58555f, -9.730017f, 28.624f, -9.806156f, 28.697048f, -9.950404f, 28.628181f, -9.950404f, 22.722334f, -5.902699f, 22.722334f);
            TzPolygon[] tzPolygonArr3 = TimezoneMapper.poly;
            float[] fArr3 = new float[HwCallManagerReference.HWBuffer.BUFFER_SIZE];
            // fill-array-data instruction
            fArr3[0] = -5.902699f;
            fArr3[1] = 20.044312f;
            fArr3[2] = -5.684445f;
            fArr3[3] = 20.179996f;
            fArr3[4] = -5.403611f;
            fArr3[5] = 20.143055f;
            fArr3[6] = -5.159167f;
            fArr3[7] = 20.191666f;
            fArr3[8] = -4.769167f;
            fArr3[9] = 20.054443f;
            fArr3[10] = -4.595834f;
            fArr3[11] = 20.062222f;
            fArr3[12] = -4.500834f;
            fArr3[13] = 20.118889f;
            fArr3[14] = -4.278056f;
            fArr3[15] = 20.038887f;
            fArr3[16] = -4.27889f;
            fArr3[17] = 20.128609f;
            fArr3[18] = -4.395556f;
            fArr3[19] = 20.262775f;
            fArr3[20] = -4.397779f;
            fArr3[21] = 20.337498f;
            fArr3[22] = -4.295279f;
            fArr3[23] = 20.374443f;
            fArr3[24] = -4.151668f;
            fArr3[25] = 20.669167f;
            fArr3[26] = -3.307222f;
            fArr3[27] = 20.656109f;
            fArr3[28] = -2.848889f;
            fArr3[29] = 20.829441f;
            fArr3[30] = -2.692778f;
            fArr3[31] = 20.954166f;
            fArr3[32] = -2.488965f;
            fArr3[33] = 21.006863f;
            fArr3[34] = -2.512778f;
            fArr3[35] = 21.245552f;
            fArr3[36] = -2.480556f;
            fArr3[37] = 21.44083f;
            fArr3[38] = -2.335278f;
            fArr3[39] = 21.469719f;
            fArr3[40] = -2.529217f;
            fArr3[41] = 21.826885f;
            fArr3[42] = -2.276389f;
            fArr3[43] = 22.13972f;
            fArr3[44] = -2.313334f;
            fArr3[45] = 22.190552f;
            fArr3[46] = -2.434722f;
            fArr3[47] = 22.188332f;
            fArr3[48] = -2.464723f;
            fArr3[49] = 22.264442f;
            fArr3[50] = -2.395556f;
            fArr3[51] = 22.293888f;
            fArr3[52] = -1.918334f;
            fArr3[53] = 22.163887f;
            fArr3[54] = -1.956111f;
            fArr3[55] = 22.520832f;
            fArr3[56] = -1.801111f;
            fArr3[57] = 22.625553f;
            fArr3[58] = -1.8725f;
            fArr3[59] = 22.847775f;
            fArr3[60] = -1.962222f;
            fArr3[61] = 22.936665f;
            fArr3[62] = -1.939445f;
            fArr3[63] = 23.096386f;
            fArr3[64] = -2.085556f;
            fArr3[65] = 23.221107f;
            fArr3[66] = -2.01f;
            fArr3[67] = 23.5f;
            fArr3[68] = -2.018889f;
            fArr3[69] = 23.693886f;
            fArr3[70] = -1.931389f;
            fArr3[71] = 23.698055f;
            fArr3[72] = -1.9175f;
            fArr3[73] = 23.77472f;
            fArr3[74] = -1.806667f;
            fArr3[75] = 23.783054f;
            fArr3[76] = -1.765f;
            fArr3[77] = 23.837776f;
            fArr3[78] = -1.769167f;
            fArr3[79] = 23.985275f;
            fArr3[80] = -1.732778f;
            fArr3[81] = 24.035275f;
            fArr3[82] = -1.786796f;
            fArr3[83] = 24.421398f;
            fArr3[84] = -1.538889f;
            fArr3[85] = 24.375832f;
            fArr3[86] = -1.393056f;
            fArr3[87] = 24.303886f;
            fArr3[88] = -1.384722f;
            fArr3[89] = 24.105831f;
            fArr3[90] = -1.306667f;
            fArr3[91] = 23.948887f;
            fArr3[92] = -1.179167f;
            fArr3[93] = 23.772778f;
            fArr3[94] = -1.083333f;
            fArr3[95] = 23.729721f;
            fArr3[96] = -0.975833f;
            fArr3[97] = 23.526665f;
            fArr3[98] = -0.807222f;
            fArr3[99] = 23.35611f;
            fArr3[100] = -0.635833f;
            fArr3[101] = 23.629444f;
            fArr3[102] = -0.438611f;
            fArr3[103] = 23.467777f;
            fArr3[104] = -0.495278f;
            fArr3[105] = 23.269165f;
            fArr3[106] = -0.425f;
            fArr3[107] = 23.217777f;
            fArr3[108] = -0.406389f;
            fArr3[109] = 22.970276f;
            fArr3[110] = -0.276667f;
            fArr3[111] = 23.140831f;
            fArr3[112] = -0.317222f;
            fArr3[113] = 23.293888f;
            fArr3[114] = -0.258301f;
            fArr3[115] = 23.395903f;
            fArr3[116] = -0.258301f;
            fArr3[117] = 27.413694f;
            fArr3[118] = -5.902699f;
            fArr3[119] = 27.413694f;
            tzPolygonArr3[497] = new TzPolygon(fArr3);
            TimezoneMapper.poly[498] = new TzPolygon(5.386098f, 23.031767f, 4.767837f, 23.031767f, 4.83777f, 22.967215f, 4.817867f, 22.906668f, 4.707766f, 22.865786f, 4.704384f, 22.783422f, 4.471723f, 22.698984f, 4.471738f, 22.599207f, 4.399888f, 22.585594f, 4.358072f, 22.621918f, 4.278484f, 22.545946f, 4.203427f, 22.540604f, 4.124475f, 22.445164f, 4.112094f, 22.28126f, 4.211728f, 22.09528f, 4.240032f, 21.859243f, 4.30386f, 21.73453f, 4.309106f, 21.650578f, 4.247074f, 21.551945f, 4.337938f, 21.28217f, 4.28702f, 21.211266f, 4.392369f, 21.075321f, 4.438874f, 20.911142f, 4.403446f, 20.606243f, 4.502898f, 20.461971f, 4.627389f, 20.453362f, 4.751149f, 20.343103f, 4.965867f, 20.023962f, 4.979547f, 19.9151f, 5.09344f, 19.825197f, 5.14574f, 19.554943f, 5.128375f, 19.422817f, 4.942183f, 19.202444f, 4.922176f, 19.097916f, 4.75334f, 19.009724f, 4.558328f, 18.831814f, 4.386818f, 18.762772f, 4.349532f, 18.64984f, 5.386098f, 18.64984f);
            TimezoneMapper.poly[499] = new TzPolygon(0.328795f, 23.031767f, 0.413333f, 22.9725f, 0.570833f, 22.935276f, 0.696389f, 22.852497f, 0.996389f, 22.81472f, 1.361666f, 22.624165f, 1.489722f, 22.433887f, 1.503333f, 22.286388f, 1.693889f, 22.472775f, 2.120555f, 22.583885f, 2.010278f, 22.757221f, 2.045833f, 22.860832f, 2.184166f, 22.97583f, 2.140042f, 23.031767f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer6 {
        private Initializer6() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[500] = new TzPolygon(2.567253f, 23.031767f, 2.672222f, 22.96f, 2.951774f, 23.031767f);
            TimezoneMapper.poly[501] = new TzPolygon(3.156188f, 23.031767f, 3.161666f, 23.00111f, 2.996111f, 22.81472f, 3.063889f, 22.77972f, 3.259444f, 22.796387f, 3.362222f, 22.624996f, 3.453888f, 22.678333f, 3.548055f, 22.928608f, 3.547529f, 23.031767f);
            TimezoneMapper.poly[502] = new TzPolygon(3.881139f, 23.031767f, 3.858611f, 22.858055f, 3.905f, 22.765553f, 3.991389f, 22.75861f, 4.136067f, 22.462902f, 4.35047f, 22.61941f, 4.471738f, 22.599207f, 4.471723f, 22.698984f, 4.704384f, 22.783422f, 4.707766f, 22.865786f, 4.817867f, 22.906668f, 4.83777f, 22.967215f, 4.767837f, 23.031767f);
            TimezoneMapper.poly[503] = new TzPolygon(5.386098f, 27.254896f, 5.088183f, 27.413694f, 5.206707f, 27.112179f, 5.134194f, 26.935108f, 5.033753f, 26.872164f, 5.103511f, 26.74996f, 5.046394f, 26.488008f, 5.133f, 26.414156f, 5.140808f, 26.303291f, 5.258524f, 26.160934f, 5.194083f, 26.067915f, 5.23966f, 25.985167f, 5.165109f, 25.905045f, 5.21867f, 25.883776f, 5.186775f, 25.8359f, 5.272717f, 25.792496f, 5.23669f, 25.749224f, 5.385851f, 25.540785f, 5.354577f, 25.547544f, 5.315548f, 25.367708f, 5.187434f, 25.320646f, 5.149122f, 25.355207f, 5.043161f, 25.327354f, 5.003779f, 25.191195f, 5.028268f, 25.15416f, 4.937803f, 25.088419f, 4.993096f, 24.983345f, 4.906936f, 24.79165f, 4.916743f, 24.661749f, 4.969501f, 24.67391f, 5.030459f, 24.613525f, 5.104392f, 24.46724f, 5.059179f, 24.434172f, 5.112711f, 24.396036f, 5.060027f, 24.357918f, 5.031252f, 24.410818f, 5.002935f, 24.298317f, 4.936987f, 24.275902f, 4.959544f, 24.236322f, 4.894931f, 24.160479f, 4.877122f, 23.959911f, 4.809446f, 23.949842f, 4.829398f, 23.833492f, 4.73297f, 23.582073f, 4.650168f, 23.430956f, 4.588927f, 23.420927f, 4.625576f, 23.280485f, 4.738208f, 23.171587f, 4.715507f, 23.08007f, 4.767837f, 23.031767f, 5.386098f, 23.031767f);
            TimezoneMapper.poly[504] = new TzPolygon(5.124463f, 27.413694f, 5.15258f, 27.396597f, 5.228182f, 27.300915f, 5.386098f, 27.247078f, 5.386098f, 27.413694f);
            TimezoneMapper.poly[505] = new TzPolygon(3.875627f, 23.031767f, 3.896111f, 23.14722f, 3.809444f, 23.358887f, 3.825833f, 23.515f, 3.615833f, 23.36972f, 3.678055f, 23.171108f, 3.5475f, 23.037498f, 3.547529f, 23.031767f);
            TimezoneMapper.poly[506] = new TzPolygon(3.156188f, 23.031767f, 3.147222f, 23.081944f, 2.951774f, 23.031767f);
            TimezoneMapper.poly[507] = new TzPolygon(2.567253f, 23.031767f, 2.558055f, 23.038055f, 2.5025f, 23.126663f, 2.523889f, 23.359444f, 2.330833f, 23.627499f, 2.1925f, 23.651386f, 2.251389f, 23.343887f, 2.093889f, 23.090275f, 2.140042f, 23.031767f);
            TimezoneMapper.poly[508] = new TzPolygon(0.328795f, 23.031767f, -0.208333f, 23.408333f, -0.25f, 23.410275f, -0.258301f, 23.395903f, -0.258301f, 23.031767f);
            TimezoneMapper.poly[509] = new TzPolygon(-5.902699f, 29.589113f, -5.66435f, 29.59868f, -5.902699f, 29.59868f);
            TimezoneMapper.poly[510] = new TzPolygon(-0.990736f, 29.589231f, -1.392395f, 29.595316f, -1.501625f, 29.454336f, -1.514576f, 29.361584f, -1.638326f, 29.265423f, -1.882589f, 29.126463f, -2.147989f, 29.1686f, -2.280179f, 29.087776f, -2.381294f, 28.89257f, -2.529938f, 28.856794f, -2.663625f, 28.895725f, -2.749662f, 29.032166f, -2.816053f, 28.996944f, -3.070362f, 29.246155f, -3.308666f, 29.200918f, -3.790512f, 29.231802f, -3.931963f, 29.259054f, -4.100027f, 29.367756f, -4.338088f, 29.388851f, -4.853596f, 29.337156f, -5.66435f, 29.59868f, -5.66435f, 27.413694f, -0.990736f, 27.413694f);
            TimezoneMapper.poly[511] = new TzPolygon(5.085673f, 27.413694f, 5.077729f, 27.43156f, 4.889299f, 27.573925f, 4.868381f, 27.683994f, 4.796947f, 27.703667f, 4.790739f, 27.758713f, 4.736298f, 27.787395f, 4.597051f, 27.783825f, 4.547898f, 27.868265f, 4.547835f, 28.041918f, 4.410721f, 28.075243f, 4.436015f, 28.13224f, 4.347533f, 28.201092f, 4.349585f, 28.351091f, 4.274636f, 28.3787f, 4.419617f, 28.66222f, 4.503731f, 28.688585f, 4.558043f, 28.77811f, 4.473744f, 28.829952f, 4.492551f, 29.009434f, 4.336439f, 29.216095f, 4.483301f, 29.408733f, 4.689459f, 29.498571f, 4.558717f, 29.82029f, 4.368258f, 29.798374f, 4.314814f, 29.94181f, 4.234662f, 29.947914f, 4.188783f, 30.038128f, 4.112746f, 30.078886f, 4.096993f, 30.155676f, 3.949463f, 30.209131f, 3.86815f, 30.5466f, 3.680238f, 30.584925f, 3.596019f, 30.558643f, 3.624311f, 30.734562f, 3.674711f, 30.771461f, 3.590437f, 30.79934f, 3.563118f, 30.862043f, 3.48639f, 30.853569f, 3.496357f, 30.928576f, 3.40154f, 30.935602f, 3.263587f, 30.832882f, 3.047981f, 30.763294f, 2.871878f, 30.882761f, 2.5882f, 30.754951f, 2.452595f, 30.747871f, 2.426479f, 30.833559f, 2.344041f, 30.885963f, 2.33769f, 30.93474f, 2.408754f, 30.985323f, 2.269178f, 31.124405f, 2.296254f, 31.199127f, 2.224187f, 31.203491f, 2.153676f, 31.305912f, 1.991877f, 31.22819f, 1.749802f, 31.02577f, 1.494337f, 30.699884f, 1.213292f, 30.468384f, 1.200461f, 30.362999f, 1.117035f, 30.265215f, 0.885302f, 30.14459f, 0.864802f, 30.028868f, 0.806247f, 29.962223f, 0.517733f, 29.973263f, 0.392724f, 29.872433f, 0.164289f, 29.81499f, 0.165491f, 29.772099f, 0.065743f, 29.711206f, -0.030166f, 29.74319f, -0.46851f, 29.653698f, -0.566212f, 29.681017f, -0.705782f, 29.626041f, -0.898091f, 29.632484f, -0.904934f, 29.587933f, -0.990736f, 29.589231f, -0.990736f, 27.413694f);
            TimezoneMapper.poly[512] = new TzPolygon(-5.902699f, 12.993113f, -5.873306f, 13.028182f, -5.900707f, 13.359085f, -5.841628f, 13.986273f, -5.902699f, 13.986273f);
            TimezoneMapper.poly[513] = new TzPolygon(-5.902699f, 14.530292f, -5.861608f, 14.177258f, -5.884214f, 14.038134f, -5.841628f, 13.986273f, -5.841628f, 14.530292f);
            TimezoneMapper.poly[514] = new TzPolygon(-5.902699f, 14.67042f, -5.873365f, 14.963f, -5.864694f, 16.36594f, -5.902699f, 16.551239f);
            TimezoneMapper.poly[515] = new TzPolygon(-5.841628f, 12.264952f, -5.765666f, 12.204144f, -5.741293f, 12.523775f, -5.151568f, 12.524532f, -5.087976f, 12.457781f, -5.030629f, 12.599125f, -4.951977f, 12.619432f, -4.865057f, 12.810996f, -4.743191f, 12.860463f, -4.641644f, 13.097989f, -4.58872f, 13.114317f, -4.77142f, 13.28461f, -4.798558f, 13.372733f, -4.919622f, 13.434644f, -4.868803f, 13.425069f, -4.813753f, 13.594442f, -4.739534f, 13.635248f, -4.768347f, 13.690184f, -4.711761f, 13.684933f, -4.709084f, 13.729198f, -4.44858f, 13.738337f, -4.422534f, 13.819195f, -4.501235f, 13.88348f, -4.518221f, 13.959691f, -4.424786f, 14.025829f, -4.399586f, 14.178457f, -4.376826f, 14.221724f, -5.841628f, 14.221724f);
            TimezoneMapper.poly[516] = new TzPolygon(-4.376826f, 15.177628f, -4.480533f, 15.118428f, -4.682193f, 14.912355f, -4.817784f, 14.849721f, -4.917224f, 14.69051f, -4.848159f, 14.489867f, -4.893814f, 14.41206f, -4.655138f, 14.429112f, -4.564119f, 14.349339f, -4.437007f, 14.482252f, -4.376826f, 14.42262f);
            TimezoneMapper.poly[517] = new TzPolygon(-4.376826f, 14.221724f, -4.279238f, 14.407242f, -4.34122f, 14.38734f, -4.376826f, 14.42262f);
            TimezoneMapper.poly[518] = new TzPolygon(-4.376826f, 15.177628f, -4.347605f, 15.194308f, -4.281693f, 15.302176f, -4.269551f, 15.463757f, -4.04695f, 15.561501f, -3.934902f, 15.915409f, -3.296272f, 16.222395f, -3.250495f, 16.184074f, -3.102118f, 16.178284f, -2.624788f, 16.23737f, -2.382887f, 16.18428f, -2.197611f, 16.200893f, -1.828789f, 16.538164f, -1.263571f, 16.8458f, -1.074295f, 17.111403f, -0.990493f, 17.341194f, -0.540646f, 17.709652f, -0.380971f, 17.74185f, -0.128134f, 17.713694f, 0.107583f, 17.775185f, 0.388754f, 17.960018f, 0.489938f, 17.955078f, 0.594404f, 17.876255f, 0.775074f, 17.895277f, 1.012438f, 17.850117f, 1.521395f, 18.06796f, 2.23824f, 18.09457f, 2.34502f, 18.185284f, 2.506999f, 18.241749f, 2.598863f, 18.338797f, 2.777928f, 18.432959f, 2.958175f, 18.467852f, 3.20894f, 18.64174f, 3.751367f, 18.587137f, 4.076398f, 18.648848f, 4.316255f, 18.549046f, 4.352764f, 18.64984f, -4.376826f, 18.64984f);
            TimezoneMapper.poly[519] = new TzPolygon(52.12035f, 32.325027f, 52.104443f, 32.296276f, 52.02561f, 31.974083f, 52.086277f, 31.948778f, 52.10736f, 31.796806f, 52.1855f, 31.779638f, 52.196724f, 31.702528f, 52.262585f, 31.74564f, 52.319973f, 31.609112f, 52.48486f, 31.629583f, 52.48811f, 31.572027f, 52.54828f, 31.677029f, 52.64986f, 31.554806f, 52.762974f, 31.602057f, 52.810307f, 31.512945f, 52.854362f, 31.543777f, 53.033833f, 31.256805f, 53.104973f, 31.405722f, 53.20811f, 31.4395f, 53.20061f, 31.721695f, 53.086388f, 31.943417f, 53.099804f, 32.234665f, 53.144417f, 32.325027f);
            TimezoneMapper.poly[520] = new TzPolygon(53.741863f, 32.325027f, 53.81542f, 32.131443f, 53.776974f, 31.90261f, 53.79817f, 31.770195f, 54.044945f, 31.887638f, 54.256668f, 31.323334f, 54.465195f, 31.236889f, 54.50161f, 31.087944f, 54.65653f, 31.212444f, 54.672585f, 30.997694f, 54.717415f, 31.004694f, 54.807693f, 30.760027f, 54.94614f, 30.837667f, 54.95961f, 30.943777f, 55.029f, 30.93375f, 55.053223f, 31.026777f, 55.14225f, 31.031279f, 55.289696f, 30.830305f, 55.337112f, 30.875814f, 55.337112f, 32.325027f);
            TimezoneMapper.poly[521] = new TzPolygon(45.286446f, 36.51725f, 45.245304f, 36.583805f, 45.24228f, 36.578945f, 45.28367f, 36.516888f);
            TimezoneMapper.poly[522] = new TzPolygon(45.435986f, 36.583805f, 45.434193f, 36.594444f, 45.376f, 36.647972f, 45.3668f, 36.583805f);
            TimezoneMapper.poly[523] = new TzPolygon(45.476776f, 32.64314f, 45.727417f, 33.13925f, 45.807167f, 33.177113f, 45.7565f, 33.246056f, 45.85692f, 33.48308f, 45.837082f, 33.547443f, 45.89339f, 33.6125f, 45.849277f, 33.686554f, 45.911556f, 33.685833f, 45.930195f, 33.765804f, 45.951973f, 33.624084f, 46.2322f, 33.641106f, 46.231102f, 33.88472f, 46.116096f, 34.125824f, 46.18387f, 34.078606f, 46.204163f, 34.137215f, 46.14582f, 34.151093f, 46.17083f, 34.193047f, 45.476776f, 34.193047f);
            TimezoneMapper.poly[524] = new TzPolygon(46.17083f, 34.193047f, 46.282494f, 34.161934f, 46.2418f, 34.193047f);
            TimezoneMapper.poly[525] = new TzPolygon(46.17083f, 34.193047f, 46.2418f, 34.193047f, 46.0961f, 34.304436f, 46.21443f, 34.316666f, 46.13999f, 34.475815f, 46.180275f, 34.552216f, 46.09166f, 34.55555f, 46.12137f, 34.476646f, 46.032204f, 34.46499f, 46.01777f, 34.402206f, 45.957764f, 34.473595f, 45.99138f, 34.577766f, 46.165543f, 34.625824f, 46.158916f, 34.81511f, 45.894974f, 34.896584f, 45.672333f, 35.040417f, 45.476776f, 35.270176f, 45.476776f, 34.193047f);
            TimezoneMapper.poly[526] = new TzPolygon(52.12028f, 32.325027f, 52.128807f, 32.35511f, 52.33511f, 32.388027f, 52.243805f, 32.9055f, 52.36936f, 33.20125f, 52.358334f, 33.493584f, 52.302307f, 33.545887f, 52.3585f, 33.83389f, 52.140167f, 34.12925f, 51.998196f, 34.110054f, 51.855083f, 34.265f, 51.838085f, 34.409138f, 51.760612f, 34.45425f, 51.663387f, 34.09164f, 51.50511f, 34.302307f, 51.40586f, 34.224472f, 51.365917f, 34.338055f, 51.255585f, 34.273582f, 51.247112f, 34.65614f, 51.175972f, 34.72614f, 51.23517f, 34.962833f, 51.221863f, 35.12803f, 51.05086f, 35.215305f, 51.06047f, 35.41617f, 51.001972f, 35.33236f, 50.676807f, 35.50353f, 50.65514f, 35.412f, 50.59075f, 35.408443f, 50.459362f, 35.58875f, 50.37847f, 35.617916f, 50.368168f, 35.724472f, 50.439335f, 35.824276f, 50.450695f, 36.130722f, 50.418694f, 36.231083f, 50.29403f, 36.315388f, 50.334026f, 36.452835f, 50.30036f, 36.59475f, 50.25033f, 36.5795f, 50.23308f, 36.643417f, 50.35375f, 36.931946f, 50.352f, 37.155556f, 50.370316f, 37.223877f, 48.130928f, 37.223877f, 48.130928f, 32.325027f);
            TimezoneMapper.poly[527] = new TzPolygon(53.741863f, 32.325027f, 53.725723f, 32.3675f, 53.734917f, 32.459084f, 53.688137f, 32.50408f, 53.644028f, 32.4215f, 53.552223f, 32.479668f, 53.450863f, 32.770805f, 53.331585f, 32.738888f, 53.29239f, 32.507084f, 53.19978f, 32.43717f, 53.15171f, 32.325027f);
            TimezoneMapper.poly[528] = new TzPolygon(43.5865f, 39.722786f, 43.5865f, 40.77761f, 43.508232f, 40.77761f, 43.559193f, 40.66275f, 43.51611f, 40.571835f, 43.585777f, 40.267887f, 43.56647f, 40.103085f, 43.38325f, 40.01025f, 43.398834f, 39.947918f);
            TimezoneMapper.poly[529] = new TzPolygon(50.370316f, 37.223877f, 50.44147f, 37.489334f, 50.34903f, 37.50136f, 50.30286f, 37.6415f, 50.176918f, 37.652332f, 50.04039f, 37.91953f, 49.907417f, 38.042694f, 49.95514f, 38.1995f, 50.080334f, 38.206417f, 50.08614f, 38.328304f, 49.989082f, 38.409363f, 49.971832f, 38.671444f, 50.007915f, 38.700974f, 49.940388f, 38.70425f, 49.865444f, 38.929417f, 49.811085f, 38.946693f, 49.819557f, 39.082584f, 49.881306f, 39.172943f, 49.76164f, 39.282276f, 49.73997f, 39.611668f, 49.639946f, 39.64275f, 49.563835f, 39.836388f, 49.619415f, 40.14236f, 49.563137f, 40.20739f, 49.49967f, 40.06736f, 49.24311f, 40.19964f, 49.181f, 40.033417f, 49.05286f, 39.931694f, 49.04464f, 39.669167f, 48.895916f, 39.82039f, 48.87314f, 39.994946f, 48.90886f, 40.04475f, 48.867f, 40.07089f, 48.794582f, 40.0f, 48.837776f, 39.80036f, 48.619057f, 39.666557f, 48.570805f, 39.850056f, 48.47211f, 39.85189f, 48.370056f, 39.94603f, 48.322083f, 39.850304f, 48.3155f, 39.986362f, 48.251083f, 40.018055f, 48.049057f, 39.818943f, 47.832333f, 39.75022f, 47.82914f, 39.52961f, 47.877777f, 39.35928f, 47.838528f, 39.11883f, 47.862057f, 38.83475f, 47.68414f, 38.76478f, 47.69539f, 38.66678f, 47.638668f, 38.614277f, 47.63736f, 38.457417f, 47.56167f, 38.31075f, 47.382668f, 38.301807f, 47.31311f, 38.224415f, 47.30075f, 38.325638f, 47.25303f, 38.33422f, 47.22836f, 38.245888f, 47.11536f, 38.23436f, 47.025528f, 38.103943f, 47.066277f, 38.138306f, 47.10911f, 38.09275f, 47.09628f, 37.590527f, 46.867443f, 37.310333f, 46.95067f, 37.24686f, 46.942345f, 37.223877f, 47.45916f, 37.223877f);
            TimezoneMapper.poly[530] = new TzPolygon(60.535442f, 27.85475f, 60.54939f, 27.801805f, 60.57885f, 27.85475f);
            TimezoneMapper.poly[531] = new TzPolygon(60.7505f, 28.171377f, 60.56438f, 27.85475f, 60.7505f, 27.85475f);
            TimezoneMapper.poly[532] = new TzPolygon(62.908028f, 26.91664f, 62.908028f, 31.580944f, 62.507057f, 31.229834f, 62.312695f, 30.948444f, 62.205917f, 30.65825f, 61.27342f, 29.245556f, 61.12583f, 28.828861f, 60.957943f, 28.667694f, 60.924778f, 28.467861f, 60.7505f, 28.171377f, 60.7505f, 26.91664f);
            TimezoneMapper.poly[533] = new TzPolygon(63.341846f, 30.945389f, 63.404556f, 30.790861f, 63.46186f, 30.505138f, 63.75447f, 29.980944f, 63.81828f, 30.239805f, 64.11019f, 30.55125f, 64.25491f, 30.484972f, 64.40425f, 30.053f, 64.59172f, 30.018278f, 64.65275f, 30.143f, 64.72789f, 30.043333f, 64.775024f, 30.085861f, 64.788055f, 29.754417f, 64.989944f, 29.59625f, 65.059166f, 29.622833f, 65.10345f, 29.89286f, 65.14042f, 29.82289f, 65.21325f, 29.877583f, 65.25108f, 29.609528f, 65.334114f, 29.768583f, 65.46608f, 29.741972f, 65.56409f, 29.881222f, 65.627914f, 29.734417f, 65.68992f, 30.00664f, 65.6645f, 30.122723f, 65.832054f, 30.101334f, 66.12789f, 29.917528f, 66.91658f, 29.02975f, 67.30295f, 29.539528f, 67.517334f, 29.959167f, 67.66936f, 30.034527f, 67.79375f, 29.694529f, 68.07469f, 29.334667f, 68.195114f, 28.659805f, 68.54767f, 28.450361f, 68.87495f, 28.803528f, 68.90842f, 28.43475f, 69.05739f, 28.926945f, 69.03222f, 29.093805f, 69.119804f, 29.258862f, 69.30278f, 29.314945f, 69.31528f, 29.547222f, 69.46272f, 30.109888f, 69.546776f, 30.194666f, 69.66339f, 30.111279f, 69.54147f, 30.5355f, 69.541664f, 30.820139f, 69.58616f, 30.945389f);
            TimezoneMapper.poly[534] = new TzPolygon(69.58654f, 30.945389f, 69.786026f, 30.84275f, 69.77194f, 30.945389f);
            TimezoneMapper.poly[535] = new TzPolygon(62.908028f, 31.580944f, 63.113304f, 31.278555f, 63.225834f, 31.23125f, 63.341846f, 30.945389f, 63.341846f, 31.580944f);
            TimezoneMapper.poly[536] = new TzPolygon(1.471278f, 103.73019f, 1.462861f, 103.687386f, 1.343778f, 103.6098f, 1.345892f, 103.5575f, 1.471278f, 103.5575f);
            TimezoneMapper.poly[537] = new TzPolygon(1.471278f, 104.00638f, 1.436889f, 104.002975f, 1.471278f, 103.86062f);
            TimezoneMapper.poly[538] = new TzPolygon(1.387095f, 104.04159f, 1.410003f, 104.04159f, 1.432389f, 104.0637f, 1.408556f, 104.09006f, 1.355027f, 104.07059f);
            TimezoneMapper.poly[539] = new TzPolygon(48.245987f, 47.171124f, 48.249947f, 47.11499f, 48.443f, 46.49186f, 48.938526f, 46.7785f, 49.003666f, 46.93761f, 49.119335f, 47.038887f, 49.20889f, 47.021446f, 49.213818f, 47.011936f, 49.213818f, 47.171124f);
            TimezoneMapper.poly[540] = new TzPolygon(47.77299f, 47.171124f, 47.7885f, 47.138805f, 47.836834f, 47.16186f, 48.0f, 47.01764f, 48.060085f, 47.171124f);
            TimezoneMapper.poly[541] = new TzPolygon(48.08438f, 47.171124f, 48.104916f, 47.09711f, 48.249947f, 47.11499f, 48.245987f, 47.171124f);
            TimezoneMapper.poly[542] = new TzPolygon(49.213818f, 40.77761f, 49.213818f, 42.060925f, 49.153877f, 42.064438f, 49.15249f, 42.344437f, 49.04972f, 42.39277f, 48.967766f, 42.663605f, 48.80638f, 42.7461f, 48.70527f, 42.65027f, 48.61805f, 42.70166f, 48.546944f, 42.630547f, 48.498604f, 42.276657f, 48.408882f, 42.072495f, 48.28221f, 42.15193f, 48.259163f, 42.045f, 48.16388f, 42.102776f, 48.034996f, 42.019157f, 48.013054f, 42.27555f, 48.047775f, 42.326385f, 48.01055f, 42.498604f, 47.855553f, 42.7286f, 47.51361f, 42.939713f, 47.575554f, 43.135544f, 47.449715f, 43.22499f, 47.489716f, 43.558327f, 47.442215f, 43.653313f, 47.476555f, 43.731773f, 47.55915f, 43.629433f, 47.684433f, 43.705826f, 47.7575f, 43.84137f, 47.766388f, 43.97499f, 47.910812f, 44.06582f, 47.868317f, 44.389153f, 48.040833f, 44.466377f, 48.05999f, 44.323044f, 48.260277f, 44.318886f, 48.206383f, 44.53582f, 48.058327f, 44.549713f, 48.09166f, 44.64415f, 48.05249f, 44.800537f, 48.143883f, 44.942207f, 48.211937f, 44.956657f, 48.113052f, 45.127487f, 48.098328f, 45.39499f, 47.991936f, 45.551926f, 48.0375f, 45.7136f, 47.865273f, 45.719704f, 47.723038f, 45.829163f, 47.71221f, 45.907204f, 47.51693f, 46.088875f, 47.436646f, 46.296654f, 47.444427f, 46.388596f, 47.632484f, 46.680817f, 47.576096f, 46.830544f, 47.43499f, 46.523872f, 47.39193f, 46.51555f, 46.996384f, 46.796387f, 46.978325f, 46.927773f, 46.850822f, 46.99082f, 46.85833f, 47.127213f, 46.824722f, 47.15597f, 46.824722f, 40.77761f);
            TimezoneMapper.poly[543] = new TzPolygon(48.087093f, 47.171124f, 48.074306f, 47.207443f, 48.060085f, 47.171124f);
            TimezoneMapper.poly[544] = new TzPolygon(47.784733f, 47.171124f, 47.74875f, 47.196777f, 47.67625f, 47.376972f, 47.81503f, 47.408222f, 47.74503f, 47.68725f, 47.762722f, 48.052334f, 47.433582f, 48.380695f, 47.41447f, 48.60775f, 47.086723f, 48.73364f, 46.824722f, 48.956398f, 46.824722f, 47.171124f);
            TimezoneMapper.poly[545] = new TzPolygon(48.245987f, 47.171124f, 49.213818f, 47.171124f, 49.213818f, 53.564632f, 49.20227f, 53.564632f, 49.116936f, 53.414993f, 49.058327f, 53.46305f, 49.024437f, 53.221657f, 49.06166f, 52.861664f, 49.013885f, 52.781937f, 48.769714f, 52.740273f, 48.74666f, 52.64805f, 48.612213f, 52.57222f, 48.51944f, 52.595543f, 48.48999f, 52.41249f, 48.565826f, 52.12249f, 48.530823f, 52.077774f, 48.57361f, 51.8161f, 48.70527f, 51.768326f, 48.733604f, 51.39805f, 48.26999f, 50.24666f, 48.11277f, 50.019157f, 48.05005f, 49.42124f, 47.978867f, 49.43637f, 48.17527f, 48.17388f);
            TimezoneMapper.poly[546] = new TzPolygon(49.213818f, 47.011936f, 49.33267f, 46.782665f, 49.556946f, 46.81989f, 49.861027f, 46.907444f, 49.925888f, 47.161724f, 49.9351f, 47.171124f, 49.213818f, 47.171124f);
            TimezoneMapper.poly[547] = new TzPolygon(54.677216f, 47.171124f, 52.595535f, 47.171124f, 52.609993f, 47.153603f, 52.566383f, 47.068604f, 52.63833f, 46.9461f, 52.621933f, 46.726654f, 52.664993f, 46.61194f, 52.73388f, 46.5886f, 52.68055f, 46.298332f, 52.61805f, 46.2761f, 52.60527f, 46.159714f, 52.54972f, 46.14583f, 52.56388f, 46.087494f, 52.495827f, 45.971375f, 52.40721f, 45.98082f, 52.458885f, 45.71805f, 52.525826f, 45.73555f, 52.52027f, 45.641106f, 52.400543f, 45.441933f, 52.411102f, 45.11138f, 52.317215f, 45.050545f, 52.376656f, 44.90555f, 52.446655f, 44.867493f, 52.479355f, 44.664894f, 52.55249f, 44.54805f, 52.526657f, 44.493607f, 52.429436f, 44.54361f, 52.413048f, 44.41082f, 52.3161f, 44.346382f, 52.3461f, 44.055267f, 52.434715f, 43.77749f, 52.391106f, 43.340546f, 52.44554f, 43.3086f, 52.465546f, 43.181107f, 52.40193f, 43.122765f, 52.374992f, 42.92527f, 52.305267f, 42.92222f, 52.190544f, 42.757217f, 52.010277f, 42.811104f, 51.99138f, 42.710823f, 51.824997f, 42.66638f, 51.67666f, 42.478874f, 51.575554f, 42.529716f, 51.38166f, 42.861664f, 51.316666f, 42.83943f, 51.24083f, 42.91221f, 51.23694f, 42.684433f, 51.165268f, 42.567215f, 51.13166f, 42.267212f, 51.208603f, 41.963326f, 51.19388f, 41.8686f, 51.017212f, 41.7286f, 50.903603f, 41.411934f, 50.830276f, 41.382767f, 50.776382f, 41.15638f, 50.719154f, 41.327774f, 50.61944f, 41.407494f, 50.60166f, 41.528328f, 50.505272f, 41.417496f, 50.381104f, 41.512497f, 50.226288f, 41.362206f, 50.11055f, 41.460823f, 50.055267f, 41.591103f, 49.963608f, 41.61361f, 49.90777f, 41.866104f, 49.686104f, 42.180275f, 49.40499f, 42.04972f, 49.213818f, 42.060925f, 49.213818f, 40.77761f, 54.677216f, 40.77761f);
            TimezoneMapper.poly[548] = new TzPolygon(49.9351f, 47.171124f, 50.09397f, 47.333168f, 50.198917f, 47.260918f, 50.242527f, 47.326637f, 50.33178f, 47.31961f, 50.33989f, 47.407917f, 50.456165f, 47.5095f, 50.43833f, 47.62389f, 50.09139f, 48.096474f, 49.873055f, 48.21792f, 49.81511f, 48.39489f, 49.92675f, 48.735f, 50.02336f, 48.877556f, 50.085667f, 48.852196f, 50.1105f, 48.75525f, 50.572887f, 48.650223f, 50.64842f, 48.558334f, 50.600334f, 48.82475f, 50.783474f, 49.09297f, 50.863804f, 49.418194f, 50.997944f, 49.323334f, 51.12958f, 49.4205f, 51.114723f, 49.736f, 51.23425f, 49.934193f, 51.340305f, 50.367638f, 51.43325f, 50.349804f, 51.4379f, 50.367878f, 49.213818f, 50.367878f, 49.213818f, 47.171124f);
            TimezoneMapper.poly[549] = new TzPolygon(52.595535f, 47.171124f, 52.566666f, 47.2061f, 52.567215f, 47.438324f, 52.618324f, 47.55638f, 52.57833f, 47.715546f, 52.72721f, 47.844994f, 52.6661f, 47.92527f, 52.681107f, 48.028328f, 52.728325f, 48.09249f, 52.752495f, 47.988327f, 52.81082f, 48.288887f, 52.78055f, 48.43007f, 52.67083f, 48.37332f, 52.64805f, 48.6911f, 52.55777f, 48.73472f, 52.55388f, 48.838043f, 52.411102f, 48.89083f, 52.493324f, 48.97416f, 52.50666f, 49.24916f, 52.453323f, 49.361107f, 52.38555f, 49.321663f, 52.405266f, 49.42138f, 52.32805f, 49.479156f, 52.394714f, 49.519714f, 52.396385f, 49.593323f, 52.261383f, 49.648605f, 52.293053f, 49.719437f, 52.193604f, 49.809715f, 52.204994f, 50.01111f, 52.157494f, 50.053604f, 52.13388f, 50.21721f, 52.02166f, 50.162766f, 52.019714f, 50.296387f, 52.059433f, 50.33194f, 52.010593f, 50.367878f, 51.94552f, 50.367878f, 51.94552f, 47.171124f);
            TimezoneMapper.poly[550] = new TzPolygon(54.677216f, 50.367878f, 54.479836f, 50.367878f, 54.440544f, 50.276657f, 54.50361f, 50.225266f, 54.51667f, 50.07688f, 54.430275f, 50.11721f, 54.42166f, 50.211105f, 54.289436f, 50.262215f, 54.037216f, 50.197487f, 53.96138f, 49.974434f, 53.82527f, 50.037773f, 53.833603f, 49.857216f, 53.89083f, 49.838043f, 53.81138f, 49.61805f, 53.854996f, 49.60916f, 53.870827f, 49.422493f, 53.83333f, 49.31694f, 53.859436f, 49.03694f, 53.71277f, 48.863327f, 53.77083f, 48.472214f, 53.66944f, 48.603325f, 53.6661f, 48.4461f, 53.629158f, 48.42694f, 53.655823f, 48.314156f, 53.581383f, 48.440826f, 53.471375f, 48.378326f, 53.458885f, 48.236107f, 53.42444f, 48.22249f, 53.48249f, 48.16638f, 53.477707f, 48.048523f, 53.375824f, 47.941933f, 53.14583f, 48.21221f, 53.05916f, 48.144714f, 52.997215f, 48.223602f, 53.05777f, 48.26944f, 53.066383f, 48.36888f, 52.98555f, 48.31833f, 52.97799f, 48.510567f, 52.92222f, 48.588882f, 52.8686f, 48.586937f, 52.78055f, 48.43007f, 52.801384f, 48.109436f, 52.747215f, 47.984993f, 52.728325f, 48.09249f, 52.685547f, 48.043053f, 52.6661f, 47.92527f, 52.73166f, 47.85972f, 52.581383f, 47.73221f, 52.618324f, 47.55638f, 52.566383f, 47.418884f, 52.566666f, 47.2061f, 52.595535f, 47.171124f, 54.677216f, 47.171124f);
            TimezoneMapper.poly[551] = new TzPolygon(51.94552f, 50.367878f, 51.94552f, 50.644226f, 51.92916f, 50.72416f, 51.697613f, 50.734085f, 51.655582f, 50.793335f, 51.59214f, 50.776974f, 51.5715f, 50.68975f, 51.628445f, 50.716805f, 51.642445f, 50.56764f, 51.474888f, 50.511585f, 51.4379f, 50.367878f);
            TimezoneMapper.poly[552] = new TzPolygon(51.94552f, 50.644226f, 51.94552f, 51.124943f, 51.76918f, 50.7733f, 51.92916f, 50.72416f);
            TimezoneMapper.poly[553] = new TzPolygon(51.42319f, 53.564632f, 51.514f, 53.378777f, 51.487f, 53.28203f, 51.53614f, 53.19461f, 51.49136f, 53.16425f, 51.459835f, 52.95739f, 51.52564f, 52.77883f, 51.459305f, 52.69328f, 51.492f, 52.66447f, 51.4645f, 52.550583f, 51.766083f, 52.32814f, 51.656334f, 52.107555f, 51.683277f, 51.857334f, 51.590694f, 51.756527f, 51.52311f, 51.795113f, 51.473778f, 51.67f, 51.561085f, 51.565945f, 51.482582f, 51.42889f, 51.49014f, 51.313557f, 51.55572f, 51.242027f, 51.57039f, 51.360363f, 51.65333f, 51.358555f, 51.689724f, 51.266777f, 51.6805f, 50.889526f, 51.766186f, 50.77164f, 51.94552f, 51.12686f, 51.94552f, 53.564632f);
            TimezoneMapper.poly[554] = new TzPolygon(52.010593f, 50.367878f, 52.00055f, 50.375267f, 51.94552f, 50.644226f, 51.94552f, 50.367878f);
            TimezoneMapper.poly[555] = new TzPolygon(51.94552f, 53.564632f, 51.94552f, 51.12686f, 52.089714f, 51.41249f, 52.227768f, 51.457497f, 52.34916f, 51.406937f, 52.43582f, 51.563606f, 52.527214f, 51.470825f, 52.57972f, 51.473602f, 52.574165f, 51.550545f, 52.674164f, 51.534164f, 52.667496f, 51.733604f, 52.79583f, 51.779434f, 52.871933f, 51.728043f, 52.87388f, 51.824165f, 52.990547f, 52.05388f, 53.07833f, 52.062492f, 53.096657f, 52.159714f, 53.142494f, 52.05471f, 53.174995f, 52.134163f, 53.379433f, 52.196655f, 53.506386f, 52.07527f, 53.655266f, 52.25805f, 53.930275f, 52.414154f, 53.976097f, 52.370544f, 54.05777f, 52.487495f, 54.1286f, 52.39805f, 54.113884f, 52.271935f, 54.226654f, 52.3461f, 54.29722f, 52.194435f, 54.321938f, 52.511665f, 54.377075f, 52.537445f, 54.26805f, 53.01361f, 54.34166f, 53.047493f, 54.249435f, 53.11583f, 54.213608f, 52.936935f, 54.120544f, 53.091377f, 54.069992f, 53.060547f, 54.098328f, 53.194435f, 53.96749f, 53.371933f, 54.045197f, 53.4702f, 54.38694f, 53.344437f, 54.557495f, 53.410545f, 54.677216f, 53.564632f);
            TimezoneMapper.poly[556] = new TzPolygon(54.677216f, 51.395546f, 54.646385f, 51.181107f, 54.548607f, 51.093605f, 54.55166f, 51.00972f, 54.33999f, 50.936104f, 54.42833f, 50.70305f, 54.326385f, 50.5186f, 54.488884f, 50.388885f, 54.479836f, 50.367878f, 54.677216f, 50.367878f);
            TimezoneMapper.poly[557] = new TzPolygon(54.677216f, 53.564632f, 54.557495f, 53.410545f, 54.38694f, 53.344437f, 54.045197f, 53.4702f, 53.968597f, 53.380547f, 54.098328f, 53.194435f, 54.069992f, 53.060547f, 54.120544f, 53.091377f, 54.213608f, 52.936935f, 54.249435f, 53.11583f, 54.34166f, 53.047493f, 54.26805f, 53.01361f, 54.27388f, 52.886383f, 54.31138f, 52.93194f, 54.381935f, 52.52305f, 54.478874f, 52.43943f, 54.414436f, 52.379158f, 54.45916f, 52.363884f, 54.443047f, 52.209717f, 54.33943f, 52.085266f, 54.436653f, 52.01999f, 54.43638f, 51.93277f, 54.53611f, 51.927216f, 54.56166f, 51.679718f, 54.646385f, 51.523323f, 54.59527f, 51.49388f, 54.59305f, 51.393883f, 54.677216f, 51.395546f);
            TimezoneMapper.poly[558] = new TzPolygon(55.930637f, 51.461796f, 55.927216f, 51.430275f, 55.992188f, 51.39721f, 56.02957f, 51.461796f);
            TimezoneMapper.poly[559] = new TzPolygon(56.143955f, 51.461796f, 56.14444f, 51.43667f, 56.197056f, 51.461796f);
            TimezoneMapper.poly[560] = new TzPolygon(56.214897f, 51.461796f, 56.238045f, 51.366104f, 56.289993f, 51.424713f, 56.32583f, 51.31833f, 56.43638f, 51.293053f, 56.43943f, 51.201935f, 56.503326f, 51.13833f, 56.530823f, 51.20027f, 56.66777f, 51.189713f, 56.677773f, 51.389435f, 56.774994f, 51.39805f, 56.822002f, 51.461796f);
            TimezoneMapper.poly[561] = new TzPolygon(56.900032f, 51.461796f, 56.933327f, 51.32083f, 57.298607f, 51.113884f, 57.45388f, 51.194153f, 57.4786f, 51.396942f, 57.46451f, 51.461796f);
            TimezoneMapper.poly[562] = new TzPolygon(57.07272f, 49.69897f, 57.07361f, 49.711937f, 56.89666f, 49.734436f, 56.86721f, 50.014442f, 56.79416f, 50.10166f, 56.622665f, 50.072205f, 56.63999f, 50.298332f, 56.67666f, 50.324715f, 56.585266f, 50.423325f, 56.616386f, 50.493324f, 56.544716f, 50.449997f, 56.40249f, 50.569717f, 56.405266f, 50.631104f, 56.357773f, 50.579994f, 56.32805f, 50.72499f, 56.409157f, 50.77166f, 56.390274f, 50.851387f, 56.29888f, 50.909157f, 56.241104f, 50.83943f, 56.124992f, 51.076385f, 56.139435f, 51.204994f, 56.069717f, 51.314713f, 56.138046f, 51.384438f, 56.141186f, 51.461796f, 56.028526f, 51.461796f, 55.944435f, 51.41471f, 55.91855f, 51.445915f, 55.92068f, 51.461796f, 54.677216f, 51.461796f, 54.677216f, 49.69897f);
            TimezoneMapper.poly[563] = new TzPolygon(57.938507f, 51.874924f, 57.846382f, 51.834435f, 57.826103f, 51.924164f, 57.753326f, 51.92694f, 57.63472f, 51.85694f, 57.541107f, 51.741936f, 57.543327f, 51.629433f, 57.43721f, 51.587494f, 57.46451f, 51.461796f, 57.938507f, 51.461796f);
            TimezoneMapper.poly[564] = new TzPolygon(56.900032f, 51.461796f, 56.881104f, 51.54194f, 56.822002f, 51.461796f);
            TimezoneMapper.poly[565] = new TzPolygon(56.213375f, 51.461796f, 56.21305f, 51.469437f, 56.205975f, 51.461796f);
            TimezoneMapper.poly[566] = new TzPolygon(55.067287f, 53.224625f, 55.09388f, 53.14138f, 55.143883f, 53.138885f, 55.152042f, 53.224625f);
            TimezoneMapper.poly[567] = new TzPolygon(56.141186f, 51.461796f, 56.148605f, 51.644714f, 56.07888f, 51.48999f, 56.028526f, 51.461796f);
            TimezoneMapper.poly[568] = new TzPolygon(55.92068f, 51.461796f, 55.958046f, 51.740273f, 55.88388f, 51.788887f, 55.979156f, 51.960823f, 55.894997f, 52.05638f, 55.89305f, 52.16916f, 55.958046f, 52.251663f, 56.08416f, 52.204712f, 56.028328f, 52.436653f, 56.07749f, 52.467766f, 56.01555f, 52.66221f, 56.064713f, 52.728874f, 56.14138f, 52.700546f, 56.092216f, 52.836937f, 56.20249f, 52.81694f, 56.227486f, 52.57583f, 56.262497f, 52.558327f, 56.39138f, 52.722763f, 56.441658f, 52.952774f, 56.527214f, 52.82749f, 56.52694f, 53.09527f, 56.366936f, 52.9386f, 56.13527f, 53.047493f, 56.16221f, 53.14666f, 56.13175f, 53.224625f, 55.9336f, 53.224625f, 55.91999f, 53.18721f, 55.897053f, 53.224625f, 55.152042f, 53.224625f, 55.143883f, 53.138885f, 55.09388f, 53.14138f, 55.067287f, 53.224625f, 54.677216f, 53.224625f, 54.677216f, 51.461796f);
            TimezoneMapper.poly[569] = new TzPolygon(57.938507f, 51.874924f, 57.97471f, 51.89083f, 58.037773f, 51.78972f, 58.141106f, 51.783333f, 58.17166f, 51.672493f, 58.358887f, 51.789993f, 58.469986f, 51.98388f, 58.493324f, 52.172768f, 58.441658f, 52.330826f, 58.403877f, 52.865273f, 58.530273f, 52.908325f, 58.546387f, 53.08111f, 58.44659f, 53.224625f, 57.938507f, 53.224625f);
            TimezoneMapper.poly[570] = new TzPolygon(59.328087f, 53.224625f, 59.349434f, 53.204712f, 59.380825f, 53.224625f);
            TimezoneMapper.poly[571] = new TzPolygon(60.162796f, 53.224625f, 60.159157f, 53.010826f, 60.238045f, 52.304993f, 60.44638f, 52.34305f, 60.47027f, 52.124992f, 60.545547f, 52.15471f, 60.59694f, 51.773323f, 60.876656f, 51.88777f, 60.83499f, 52.343323f, 60.981377f, 52.42916f, 60.94433f, 52.79499f, 61.094154f, 52.88833f, 61.053345f, 53.224625f);
            TimezoneMapper.poly[572] = new TzPolygon(61.1998f, 53.224625f, 61.053345f, 53.224625f, 61.094154f, 52.88833f, 60.94433f, 52.79499f, 60.981377f, 52.42916f, 60.83499f, 52.343323f, 60.883606f, 51.902214f, 60.59694f, 51.773323f, 60.545547f, 52.15471f, 60.47027f, 52.124992f, 60.44638f, 52.34305f, 60.241707f, 52.298157f, 60.303604f, 52.00889f, 59.947212f, 51.53221f, 59.941658f, 51.413322f, 60.018326f, 51.34388f, 60.06166f, 51.071106f, 59.86277f, 50.828606f, 59.844437f, 50.40332f, 59.752777f, 50.237495f, 59.778877f, 49.99666f, 59.747215f, 49.951935f, 59.6911f, 50.060272f, 59.65471f, 49.842766f, 59.249435f, 49.80388f, 59.208603f, 49.709717f, 59.210403f, 49.69897f, 61.1998f, 49.69897f);
            TimezoneMapper.poly[573] = new TzPolygon(55.94262f, 53.224625f, 55.989716f, 53.378876f, 56.14083f, 53.545f, 56.24388f, 53.5561f, 56.278328f, 53.416664f, 56.254715f, 53.25889f, 56.084717f, 53.344994f, 56.13175f, 53.224625f, 57.938507f, 53.224625f, 57.938507f, 54.11307f, 57.713882f, 54.156097f, 57.666664f, 54.06916f, 57.61194f, 54.1211f, 57.571106f, 53.987495f, 57.51805f, 54.101387f, 57.559715f, 54.16999f, 57.464996f, 54.178604f, 57.46444f, 54.264717f, 57.310272f, 54.116386f, 57.28666f, 54.319992f, 57.10083f, 54.27555f, 57.03656f, 54.40268f, 56.962494f, 54.098045f, 56.791733f, 54.091774f, 56.74916f, 54.01055f, 56.764442f, 53.88999f, 56.815826f, 53.855553f, 56.778877f, 53.8061f, 56.609436f, 53.859436f, 56.6911f, 54.04722f, 56.63527f, 54.097214f, 56.58416f, 54.026657f, 56.537216f, 54.184715f, 56.43582f, 54.236107f, 56.459435f, 54.32833f, 56.262215f, 54.358887f, 56.14749f, 54.107216f, 56.025826f, 54.01805f, 56.04377f, 53.837944f, 55.914154f, 53.638046f, 55.852524f, 53.29725f, 55.90022f, 53.224625f);
            TimezoneMapper.poly[574] = new TzPolygon(56.13175f, 53.224625f, 56.084717f, 53.344994f, 56.24888f, 53.255272f, 56.273323f, 53.339157f, 56.24749f, 53.549995f, 56.181664f, 53.5661f, 55.989716f, 53.378876f, 55.9336f, 53.224625f);
            TimezoneMapper.poly[575] = new TzPolygon(55.897053f, 53.224625f, 55.852524f, 53.29725f, 55.91694f, 53.655266f, 55.880272f, 53.8686f, 55.846657f, 54.001938f, 55.782494f, 53.97027f, 55.800957f, 54.038208f, 55.75666f, 54.0261f, 55.775925f, 54.095276f, 55.691376f, 54.241936f, 55.54972f, 53.996384f, 55.38166f, 53.89833f, 55.333603f, 53.70916f, 55.20777f, 53.586105f, 55.2211f, 53.399437f, 55.16249f, 53.334435f, 55.152042f, 53.224625f);
            TimezoneMapper.poly[576] = new TzPolygon(55.067287f, 53.224625f, 55.05332f, 53.268333f, 55.010277f, 53.266937f, 54.997215f, 53.40638f, 54.881203f, 53.514122f, 54.897217f, 53.643326f, 54.821106f, 53.57805f, 54.71138f, 53.608604f, 54.677216f, 53.564632f, 54.677216f, 53.224625f);
            TimezoneMapper.poly[577] = new TzPolygon(60.16621f, 53.224625f, 60.165543f, 53.38611f, 60.21888f, 53.468323f, 60.003326f, 53.711937f, 59.83777f, 53.663605f, 59.699997f, 53.602493f, 59.689713f, 53.420547f, 59.367924f, 53.224625f);
            TimezoneMapper.poly[578] = new TzPolygon(59.321728f, 53.224625f, 59.149437f, 53.402214f, 59.091934f, 53.723602f, 59.141106f, 53.754166f, 59.098877f, 53.87416f, 58.937492f, 53.719154f, 58.781662f, 53.92833f, 58.716385f, 53.770546f, 58.603325f, 53.6961f, 58.439964f, 53.78463f, 58.450546f, 53.49694f, 58.38916f, 53.307213f, 58.44147f, 53.224625f);
            TimezoneMapper.poly[579] = new TzPolygon(58.44659f, 53.224625f, 58.38916f, 53.307213f, 58.450546f, 53.49694f, 58.439964f, 53.78463f, 58.327217f, 53.887215f, 58.228874f, 53.809715f, 57.977768f, 54.105553f, 57.938507f, 54.11307f, 57.938507f, 53.224625f);
            TimezoneMapper.poly[580] = new TzPolygon(61.1998f, 55.796947f, 61.08146f, 55.72323f, 61.129593f, 55.279213f, 61.004105f, 55.19747f, 61.0194f, 55.061066f, 60.863884f, 54.979713f, 60.987495f, 53.865547f, 60.84777f, 53.80916f, 60.864998f, 53.573883f, 60.893883f, 53.33943f, 61.033882f, 53.384995f, 61.053345f, 53.224625f, 61.1998f, 53.224625f);
            TimezoneMapper.poly[581] = new TzPolygon(46.470276f, 40.77761f, 46.470276f, 46.94594f, 46.428596f, 46.961937f, 46.42804f, 46.66027f, 46.27555f, 46.478325f, 46.248047f, 46.866653f, 46.07625f, 46.83426f, 46.07625f, 40.77761f);
            TimezoneMapper.poly[582] = new TzPolygon(46.07625f, 46.869904f, 46.105553f, 47.174988f, 46.07625f, 47.146107f);
            TimezoneMapper.poly[583] = new TzPolygon(46.470276f, 48.84202f, 46.36972f, 49.15097f, 46.31542f, 49.20028f, 46.31542f, 49.165684f, 46.36475f, 49.045776f, 46.31542f, 49.07639f, 46.31542f, 47.174988f, 46.470276f, 47.174988f);
            TimezoneMapper.poly[584] = new TzPolygon(46.07625f, 56.531475f, 46.470276f, 56.303665f, 46.470276f, 56.750217f, 46.07625f, 56.750214f);
            TimezoneMapper.poly[585] = new TzPolygon(46.470276f, 46.94594f, 46.55526f, 46.913315f, 46.74082f, 47.227768f, 46.470276f, 47.227768f);
            TimezoneMapper.poly[586] = new TzPolygon(46.74082f, 47.227768f, 46.824722f, 47.15597f, 46.824722f, 47.227768f);
            TimezoneMapper.poly[587] = new TzPolygon(46.470276f, 48.841507f, 46.47586f, 48.82486f, 46.60897f, 48.638527f, 46.657665f, 48.480694f, 46.773945f, 48.581223f, 46.684082f, 48.800888f, 46.69314f, 48.914055f, 46.767166f, 49.005333f, 46.470276f, 49.005333f);
            TimezoneMapper.poly[588] = new TzPolygon(46.767166f, 49.005333f, 46.824722f, 48.956398f, 46.824722f, 49.005333f);
            TimezoneMapper.poly[589] = new TzPolygon(46.470276f, 56.303665f, 46.50833f, 56.281662f, 46.740273f, 55.432213f, 46.824722f, 55.319897f, 46.824722f, 56.75022f, 46.470276f, 56.750217f);
            TimezoneMapper.poly[590] = new TzPolygon(51.432564f, 53.564632f, 51.428696f, 53.578445f, 51.227055f, 53.677307f, 51.162918f, 54.033527f, 51.110474f, 54.13989f, 50.884834f, 54.297417f, 50.84839f, 54.51278f, 50.632915f, 54.422638f, 50.514717f, 54.522217f, 50.314438f, 54.356384f, 50.26777f, 54.426384f, 50.11194f, 54.429436f, 50.015274f, 54.574997f, 49.680824f, 54.374435f, 49.616386f, 54.449158f, 49.555267f, 54.241104f, 49.598045f, 54.169716f, 49.613052f, 53.873604f, 49.53472f, 53.722763f, 49.5375f, 53.635826f, 49.468323f, 53.686935f, 49.348328f, 53.649994f, 49.213882f, 53.58499f, 49.20227f, 53.564632f);
            TimezoneMapper.poly[591] = new TzPolygon(49.213818f, 53.564632f, 49.239067f, 53.604683f, 49.171104f, 53.764717f, 49.17916f, 53.86416f, 49.108604f, 53.84527f, 49.156937f, 53.97638f, 48.762215f, 54.03444f, 48.63833f, 54.521103f, 48.77916f, 54.579994f, 48.80694f, 54.658043f, 48.716103f, 54.927216f, 48.393883f, 54.87999f, 48.27694f, 54.970825f, 48.108887f, 54.9411f, 48.024445f, 55.066925f, 47.89361f, 55.12332f, 47.19554f, 55.01277f, 46.878326f, 55.248604f, 46.824722f, 55.319897f, 46.824722f, 53.564632f);
            TimezoneMapper.poly[592] = new TzPolygon(51.42319f, 53.564632f, 54.677216f, 53.564632f, 54.677216f, 56.750275f, 51.08273f, 56.750275f, 51.068222f, 56.73161f, 51.053818f, 56.750275f, 50.973377f, 56.750275f, 50.969833f, 56.73914f, 51.068638f, 56.451916f, 50.918945f, 56.37539f, 50.918583f, 56.173916f, 50.71336f, 56.12114f, 50.55825f, 55.709446f, 50.671276f, 55.4855f, 50.659943f, 55.354946f, 50.803806f, 55.09711f, 50.932056f, 55.051613f, 50.877804f, 54.811638f, 50.89425f, 54.706306f, 50.810528f, 54.655388f, 50.628807f, 54.726223f, 50.539444f, 54.60814f, 50.540028f, 54.508083f, 50.632915f, 54.422638f, 50.84839f, 54.51278f, 50.884834f, 54.297417f, 51.110474f, 54.13989f, 51.162918f, 54.033527f, 51.227055f, 53.677307f, 51.306416f, 53.608418f, 51.389f, 53.63461f);
            TimezoneMapper.poly[593] = new TzPolygon(57.07272f, 49.69897f, 57.053047f, 49.41249f, 57.021935f, 49.41916f, 57.04361f, 49.268326f, 57.159714f, 49.14361f, 57.288887f, 49.228043f, 57.331383f, 49.183327f, 57.28305f, 49.016106f, 57.201103f, 49.046104f, 57.08555f, 48.954712f, 57.0886f, 48.845825f, 57.164436f, 48.83555f, 57.172493f, 48.569443f, 57.120544f, 48.451103f, 57.168602f, 48.30777f, 57.12471f, 48.192215f, 57.10666f, 48.24888f, 57.07972f, 48.163048f, 57.027214f, 48.208603f, 56.99833f, 48.13916f, 57.014442f, 47.98694f, 56.973877f, 47.97193f, 57.009438f, 47.931107f, 56.976654f, 47.916664f, 57.055267f, 47.733604f, 57.00972f, 47.769714f, 56.976097f, 47.61721f, 56.851387f, 47.502495f, 56.88082f, 47.42444f, 56.8086f, 47.17083f, 56.905823f, 47.164436f, 56.932495f, 47.083054f, 56.939713f, 46.707497f, 57.029716f, 46.76805f, 57.199997f, 46.72638f, 57.234436f, 46.851936f, 57.35f, 46.6761f, 57.456657f, 46.79722f, 57.523323f, 46.74444f, 57.49305f, 47.030823f, 57.57083f, 47.250275f, 57.528603f, 47.4711f, 57.80249f, 47.561104f, 57.90027f, 47.77027f, 57.963608f, 47.70083f, 58.02471f, 47.375267f, 58.017494f, 46.449715f, 58.11055f, 46.309715f, 58.332214f, 46.481102f, 58.363884f, 46.411377f, 58.43194f, 46.59166f, 58.558044f, 46.58416f, 58.62249f, 47.006104f, 58.77249f, 47.113327f, 58.754166f, 47.265f, 58.800827f, 47.324997f, 58.916664f, 47.307213f, 58.922493f, 47.45305f, 58.884163f, 47.467766f, 58.914993f, 47.611664f, 59.060272f, 47.576385f, 59.167496f, 47.444992f, 59.147217f, 47.39444f, 59.226097f, 47.22332f, 59.355553f, 47.252495f, 59.3786f, 47.05638f, 59.71888f, 47.141663f, 59.761383f, 47.123047f, 59.78444f, 46.92305f, 59.846657f, 46.92083f, 60.118324f, 47.03694f, 60.06527f, 46.606102f, 60.1211f, 46.36416f, 60.242493f, 46.295f, 60.27388f, 46.430824f, 60.256386f, 46.744995f, 60.515274f, 46.85666f, 60.57666f, 47.06166f, 60.655266f, 46.952774f, 61.064438f, 47.228325f, 61.00972f, 47.95999f, 60.886658f, 47.987495f, 60.85386f, 48.419243f, 60.6f, 48.53916f, 60.524994f, 48.481102f, 60.433327f, 48.617767f, 60.411102f, 48.54138f, 60.364998f, 48.763054f, 60.194153f, 48.45916f, 59.70388f, 48.509995f, 59.65555f, 49.08555f, 59.484436f, 49.034164f, 59.50889f, 49.147774f, 59.41916f, 49.341934f, 59.39305f, 49.545f, 59.2461f, 49.508606f, 59.210823f, 49.638603f, 59.215202f, 49.69897f);
            TimezoneMapper.poly[594] = new TzPolygon(61.1998f, 55.796947f, 61.250572f, 55.828575f, 61.20305f, 56.290276f, 61.43943f, 56.388603f, 61.42888f, 56.545273f, 61.52777f, 56.675552f, 61.52023f, 56.750275f, 61.1998f, 56.750275f);
            TimezoneMapper.poly[595] = new TzPolygon(42.730305f, 44.99905f, 42.74989f, 44.891724f, 42.62475f, 44.777527f, 42.73811f, 44.706833f, 42.7515f, 44.511166f, 42.701332f, 44.50347f, 42.713333f, 44.323f, 42.62675f, 44.209026f, 42.55847f, 43.948082f, 42.627556f, 43.731388f, 42.750084f, 43.79086f, 42.866085f, 43.561695f, 42.934444f, 43.19208f, 43.1825f, 42.88175f, 43.173973f, 42.680084f, 43.13711f, 42.683f, 43.25575f, 42.439888f, 43.194027f, 42.057724f, 43.246944f, 41.871918f, 43.203835f, 41.820057f, 43.22289f, 41.630665f, 43.30578f, 41.425472f, 43.361416f, 41.407806f, 43.333973f, 41.31189f, 43.38233f, 41.06139f, 43.508232f, 40.77761f, 43.509445f, 40.77761f, 43.509445f, 44.99905f);
            TimezoneMapper.poly[596] = new TzPolygon(42.730305f, 44.99905f, 43.509445f, 44.99905f, 43.509445f, 47.503372f, 43.50447f, 47.501472f, 43.305332f, 47.483807f, 43.3575f, 47.526722f, 43.31689f, 47.56536f, 43.00525f, 47.465527f, 42.870388f, 47.69011f, 42.67228f, 47.72922f, 42.573307f, 47.885666f, 42.30686f, 48.11775f, 41.949696f, 48.356945f, 41.88503f, 48.533806f, 41.601223f, 48.39072f, 41.52175f, 48.25189f, 41.4885f, 48.04503f, 41.225304f, 47.89114f, 41.18886f, 47.753223f, 41.23803f, 47.624416f, 41.207195f, 47.535915f, 41.26642f, 47.48447f, 41.318474f, 47.26986f, 41.582695f, 47.116806f, 41.556026f, 47.00975f, 41.630222f, 46.998528f, 41.78539f, 46.781166f, 41.863056f, 46.748695f, 41.807777f, 46.560944f, 41.879696f, 46.53336f, 41.894695f, 46.430946f, 41.959915f, 46.38222f, 41.937443f, 46.309166f, 42.002277f, 46.24125f, 42.026474f, 45.94003f, 42.102417f, 45.88414f, 42.2115f, 45.612583f, 42.28939f, 45.64072f, 42.305332f, 45.745083f, 42.47425f, 45.76786f, 42.555195f, 45.47086f, 42.53264f, 45.331585f, 42.701637f, 45.156166f);
            TimezoneMapper.poly[597] = new TzPolygon(46.07625f, 46.869904f, 46.07276f, 46.833603f, 46.07625f, 46.83426f);
            TimezoneMapper.poly[598] = new TzPolygon(46.07625f, 47.589027f, 45.591396f, 47.589027f, 45.571888f, 47.56364f, 45.472847f, 47.58796f, 45.639435f, 47.467484f, 45.689423f, 47.35971f, 45.6436f, 47.29361f, 45.574715f, 46.893044f, 45.969704f, 47.0411f, 46.07625f, 47.146107f);
            TimezoneMapper.poly[599] = new TzPolygon(40.805862f, 52.86546f, 40.990166f, 52.948f, 41.05511f, 52.857777f, 41.38925f, 52.767277f, 41.536945f, 52.556583f, 41.687027f, 52.581085f, 41.765446f, 52.441444f, 42.13878f, 53.032722f, 42.292667f, 53.586113f, 42.341473f, 54.189056f, 42.02239f, 54.836f, 41.429695f, 55.26675f, 41.253193f, 55.516804f, 41.283806f, 55.567974f, 41.24014f, 55.60847f, 41.322693f, 55.891666f, 41.27425f, 56.750275f, 40.805862f, 56.750275f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer7 {
        private Initializer7() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[600] = new TzPolygon(41.2786f, 56.750275f, 41.318054f, 56.00061f, 44.91514f, 55.99664f, 45.0f, 55.99811f, 45.17469f, 56.750275f);
            TimezoneMapper.poly[601] = new TzPolygon(46.07625f, 56.531475f, 46.07625f, 56.750214f, 45.174786f, 56.750202f, 45.966385f, 56.594994f);
            TimezoneMapper.poly[602] = new TzPolygon(50.99686f, 56.750275f, 50.99686f, 56.824085f, 50.973377f, 56.750275f);
            TimezoneMapper.poly[603] = new TzPolygon(50.99686f, 56.824085f, 51.053818f, 56.750275f, 51.053818f, 56.824085f);
            TimezoneMapper.poly[604] = new TzPolygon(51.053818f, 56.824085f, 51.053818f, 56.750275f, 51.08273f, 56.750275f, 51.09903f, 56.77125f, 51.079414f, 56.824085f);
            TimezoneMapper.poly[605] = new TzPolygon(51.4014f, 56.824085f, 51.4014f, 61.524387f, 51.264805f, 61.676334f, 51.245888f, 61.58703f, 50.794388f, 61.427f, 50.661f, 60.827915f, 50.669918f, 60.476223f, 50.69311f, 60.316223f, 50.844696f, 60.181557f, 50.86558f, 60.069f, 50.584305f, 59.83272f, 50.514473f, 59.531334f, 50.62675f, 59.480415f, 50.692112f, 58.925694f, 50.812557f, 58.766834f, 50.825474f, 58.61336f, 51.072056f, 58.506584f, 51.078445f, 58.371723f, 51.16075f, 58.292362f, 51.068222f, 58.180832f, 51.126167f, 58.089085f, 51.083473f, 58.001335f, 51.122696f, 57.79089f, 50.918026f, 57.72486f, 50.920223f, 57.56386f, 50.879333f, 57.493526f, 50.939888f, 57.31553f, 51.11686f, 57.181915f, 51.048695f, 56.906834f, 51.079414f, 56.824085f);
            TimezoneMapper.poly[606] = new TzPolygon(49.16795f, 62.46773f, 49.176384f, 62.462494f, 49.199715f, 62.336105f, 49.259438f, 62.35666f, 49.254524f, 62.46773f);
            TimezoneMapper.poly[607] = new TzPolygon(49.32489f, 62.46773f, 49.48082f, 62.304436f, 49.522217f, 62.32444f, 49.570274f, 62.381104f, 49.551594f, 62.46773f);
            TimezoneMapper.poly[608] = new TzPolygon(49.620373f, 62.46773f, 49.67888f, 62.454437f, 49.68831f, 62.46773f);
            TimezoneMapper.poly[609] = new TzPolygon(50.550144f, 62.46773f, 50.72249f, 62.382492f, 50.682495f, 62.25444f, 50.70999f, 62.189156f, 50.74138f, 62.235825f, 50.804436f, 62.192764f, 50.85694f, 62.42527f, 51.008606f, 62.164993f, 51.106384f, 62.211662f, 51.147774f, 62.096657f, 51.092766f, 61.993607f, 51.30638f, 61.86055f, 51.266075f, 61.67409f, 51.4014f, 61.524315f, 51.4014f, 62.46773f);
            TimezoneMapper.poly[610] = new TzPolygon(51.4014f, 62.46773f, 51.4014f, 61.524315f, 51.41436f, 61.50997f, 51.46511f, 61.002724f, 51.610832f, 60.919556f, 51.630943f, 60.43525f, 51.6885f, 60.356224f, 51.798557f, 60.519417f, 51.903557f, 60.180862f, 51.865196f, 60.18311f, 51.862442f, 60.00083f, 51.98903f, 60.0105f, 52.012333f, 60.2715f, 52.144333f, 60.484806f, 52.169445f, 60.72425f, 52.33461f, 61.070168f, 52.418446f, 60.97228f, 52.48389f, 60.981667f, 52.508945f, 60.855915f, 52.624474f, 60.838722f, 52.65475f, 60.712917f, 52.75225f, 60.731335f, 52.762722f, 60.823612f, 52.834667f, 60.85261f, 52.921696f, 61.071945f, 52.980778f, 61.027527f, 52.97089f, 61.118446f, 53.032585f, 61.239193f, 52.987446f, 61.37364f, 53.035805f, 61.46203f, 52.96375f, 61.643833f, 53.01189f, 61.87939f, 52.95239f, 62.010334f, 53.006527f, 62.13961f, 53.117584f, 62.112972f, 53.161667f, 61.80136f, 53.25264f, 61.66964f, 53.21467f, 61.520138f, 53.296944f, 61.18508f, 53.38725f, 61.160027f, 53.504223f, 61.25986f, 53.458694f, 61.44703f, 53.525555f, 61.590668f, 53.592194f, 61.505333f, 53.55989f, 61.357388f, 53.6175f, 60.918304f, 53.66772f, 60.921307f, 53.638668f, 61.030056f, 53.796696f, 61.218113f, 53.880917f, 61.136276f, 53.89714f, 60.989887f, 53.95189f, 61.02772f, 53.961582f, 61.1575f, 53.917473f, 61.256416f, 54.03411f, 61.25617f, 54.08917f, 61.394695f, 53.954193f, 61.61314f, 54.02936f, 61.73761f, 53.984222f, 61.792027f, 54.032444f, 61.85428f, 53.965973f, 61.8745f, 53.945805f, 61.982082f, 53.955082f, 62.042305f, 54.028526f, 62.003f, 54.055668f, 62.07314f, 54.056137f, 62.420834f, 54.035603f, 62.46773f, 54.01731f, 62.46773f, 53.995556f, 62.4135f, 53.936443f, 62.441612f, 53.927677f, 62.46773f);
            TimezoneMapper.poly[611] = new TzPolygon(50.550144f, 62.46773f, 50.46805f, 62.50833f, 49.99138f, 62.940826f, 49.894157f, 62.904434f, 49.94777f, 62.7386f, 49.852776f, 62.57666f, 49.77999f, 62.59694f, 49.68831f, 62.46773f);
            TimezoneMapper.poly[612] = new TzPolygon(49.620373f, 62.46773f, 49.54805f, 62.48416f, 49.551594f, 62.46773f);
            TimezoneMapper.poly[613] = new TzPolygon(49.32489f, 62.46773f, 49.251106f, 62.545f, 49.254524f, 62.46773f);
            TimezoneMapper.poly[614] = new TzPolygon(49.16795f, 62.46773f, 49.010826f, 62.56527f, 48.843323f, 62.847214f, 48.911934f, 62.976097f, 48.86055f, 63.100548f, 48.679436f, 63.094154f, 48.45083f, 63.76111f, 47.984993f, 64.13443f, 47.896942f, 64.29332f, 47.82583f, 64.158104f, 47.82583f, 62.46773f);
            TimezoneMapper.poly[615] = new TzPolygon(47.82583f, 64.1712f, 47.88833f, 64.289154f, 47.984993f, 64.13443f, 48.156937f, 64.01221f, 48.30916f, 64.27443f, 48.45027f, 64.21776f, 48.5f, 64.57027f, 48.668884f, 64.42886f, 48.694435f, 64.69609f, 48.929436f, 64.750275f, 48.817215f, 65.49803f, 48.88166f, 65.74637f, 48.999718f, 65.83388f, 49.00305f, 66.07832f, 48.96221f, 66.09526f, 49.21444f, 66.35637f, 49.25583f, 66.54137f, 49.50361f, 66.695816f, 49.596382f, 66.92804f, 50.014442f, 67.356094f, 49.98472f, 67.37804f, 50.14083f, 67.61914f, 50.217804f, 67.59569f, 50.314156f, 67.96388f, 50.41999f, 68.09972f, 50.821663f, 67.71582f, 50.907944f, 67.7159f, 51.022217f, 67.96443f, 51.192764f, 67.88638f, 51.311935f, 67.95915f, 51.4014f, 67.908325f, 51.4014f, 68.111374f, 47.82583f, 68.111374f);
            TimezoneMapper.poly[616] = new TzPolygon(54.97697f, 67.86842f, 54.883026f, 67.762886f, 54.852806f, 67.63758f, 54.86675f, 67.314f, 54.82853f, 67.304886f, 54.774166f, 67.04845f, 54.719166f, 66.43955f, 54.618084f, 66.02006f, 54.630974f, 65.952446f, 54.71258f, 65.945694f, 54.693474f, 65.840225f, 54.603474f, 65.75525f, 54.650276f, 65.491f, 54.572666f, 65.481804f, 54.58089f, 65.3578f, 54.524582f, 65.200775f, 54.34161f, 65.22247f, 54.33989f, 65.11175f, 54.421223f, 64.96089f, 54.35803f, 64.75506f, 54.39528f, 64.62117f, 54.328835f, 64.43439f, 54.31778f, 64.07911f, 54.2815f, 64.03075f, 54.311165f, 63.996193f, 54.275223f, 63.960835f, 54.193527f, 63.41175f, 54.20325f, 63.21525f, 54.107834f, 63.121166f, 54.115833f, 62.81614f, 53.98486f, 62.593945f, 53.899834f, 62.550667f, 53.927677f, 62.46773f, 54.01731f, 62.46773f, 54.026054f, 62.48953f, 54.035603f, 62.46773f, 54.97697f, 62.46773f);
            TimezoneMapper.poly[617] = new TzPolygon(54.92957f, 68.111374f, 54.91122f, 68.088165f, 54.97697f, 67.868935f, 54.97697f, 68.111374f);
            TimezoneMapper.poly[618] = new TzPolygon(51.4014f, 67.908325f, 51.54222f, 67.82832f, 51.586937f, 67.931656f, 51.765274f, 67.90166f, 51.808884f, 67.966934f, 51.95999f, 67.9772f, 52.084717f, 67.91638f, 52.217766f, 67.67526f, 52.318886f, 67.764435f, 52.42749f, 67.466934f, 52.418327f, 67.289154f, 52.218323f, 67.30914f, 52.262497f, 67.095825f, 52.235268f, 67.00249f, 52.278877f, 66.81694f, 52.26497f, 66.54352f, 52.30999f, 66.60277f, 52.36888f, 66.56888f, 52.31305f, 66.24832f, 52.43055f, 66.111374f, 52.436935f, 66.033875f, 52.818604f, 66.052475f, 52.819992f, 66.13443f, 52.88611f, 66.1436f, 52.930824f, 66.41193f, 52.96305f, 66.077774f, 53.062767f, 66.08194f, 53.10444f, 66.2036f, 53.099716f, 66.40526f, 53.168602f, 66.41443f, 53.19388f, 66.26639f, 53.48555f, 66.255264f, 53.492767f, 66.11165f, 53.767212f, 66.168045f, 53.79277f, 66.06581f, 53.915268f, 66.07582f, 53.994713f, 66.00139f, 54.084435f, 66.35693f, 54.19027f, 66.278595f, 54.237495f, 66.11804f, 54.28055f, 66.21944f, 54.636414f, 66.111015f, 54.719166f, 66.43955f, 54.774166f, 67.04845f, 54.82853f, 67.304886f, 54.86675f, 67.314f, 54.852806f, 67.63758f, 54.883026f, 67.762886f, 54.97697f, 67.86753f, 54.91122f, 68.088165f, 54.92957f, 68.111374f, 51.4014f, 68.111374f);
            TimezoneMapper.poly[619] = new TzPolygon(55.580475f, 68.111374f, 55.580475f, 70.75094f, 55.506386f, 70.69664f, 55.49138f, 70.54915f, 55.4386f, 70.48776f, 55.33777f, 70.50665f, 55.182816f, 70.327675f, 55.136333f, 70.228584f, 55.198193f, 69.96005f, 55.290943f, 69.86161f, 55.342945f, 69.66805f, 55.36614f, 69.37314f, 55.336224f, 69.191444f, 55.4505f, 68.95128f, 55.278416f, 68.97808f, 55.35886f, 68.765915f, 55.25089f, 68.64731f, 55.194027f, 68.66333f, 55.187138f, 68.17778f, 55.069168f, 68.28794f, 54.92957f, 68.111374f);
            TimezoneMapper.poly[620] = new TzPolygon(53.874702f, 73.551025f, 53.5259f, 73.551025f, 53.444805f, 73.448975f, 53.477f, 73.37939f, 53.53164f, 73.4008f, 53.572807f, 73.247086f, 53.681084f, 73.259415f, 53.687916f, 73.329475f, 53.79514f, 73.34878f, 53.813526f, 73.45617f, 53.882416f, 73.46508f);
            TimezoneMapper.poly[621] = new TzPolygon(54.028183f, 73.551025f, 54.005974f, 73.51175f, 53.949665f, 73.52742f, 53.99f, 73.40975f, 53.955723f, 73.40161f, 53.953335f, 73.28933f, 53.99597f, 73.06961f, 54.098305f, 72.98114f, 54.134f, 72.600945f, 54.046917f, 72.52064f, 54.046417f, 72.66519f, 53.94947f, 72.72205f, 53.989582f, 72.55414f, 53.909668f, 72.480446f, 53.953304f, 72.377525f, 54.038666f, 72.41989f, 54.0885f, 72.36317f, 54.13939f, 72.492386f, 54.189083f, 72.30822f, 54.320026f, 72.263275f, 54.363834f, 72.137886f, 54.35675f, 72.03819f, 54.288944f, 72.127304f, 54.22978f, 72.08358f, 54.185223f, 72.194695f, 54.133f, 72.17172f, 54.222637f, 72.02336f, 54.248554f, 71.76711f, 54.138527f, 71.74656f, 54.102695f, 71.65564f, 54.108917f, 71.514725f, 54.156307f, 71.50142f, 54.1835f, 71.35403f, 54.11486f, 71.16194f, 54.340443f, 71.01514f, 54.35603f, 71.221725f, 54.585278f, 71.19891f, 54.625446f, 71.30711f, 54.735863f, 71.09186f, 54.89347f, 70.948364f, 55.103333f, 71.01569f, 55.299084f, 70.81633f, 55.25825f, 70.628136f, 55.280582f, 70.47103f, 55.182816f, 70.327675f, 55.33777f, 70.50665f, 55.4386f, 70.48776f, 55.49138f, 70.54915f, 55.506386f, 70.69664f, 55.580475f, 70.75094f, 55.580475f, 73.551025f);
            TimezoneMapper.poly[622] = new TzPolygon(55.580475f, 75.1913f, 55.580475f, 78.99068f, 53.67856f, 78.99068f, 53.656097f, 78.66582f, 53.6261f, 78.59943f, 53.52527f, 78.63081f, 53.4886f, 78.39499f, 53.501106f, 78.24359f, 53.612495f, 78.20943f, 53.578606f, 77.933044f, 53.498047f, 77.927475f, 53.4961f, 78.05109f, 53.45999f, 78.05998f, 53.45027f, 77.93776f, 53.387497f, 77.93942f, 53.348087f, 77.802216f, 54.037224f, 76.50669f, 54.168167f, 76.4267f, 54.12625f, 76.65459f, 54.160362f, 76.74864f, 54.30958f, 76.8715f, 54.378f, 76.78242f, 54.457916f, 76.92825f, 54.41725f, 76.73553f, 54.34636f, 76.65178f, 54.356056f, 76.234024f, 54.274334f, 76.2273f, 54.129284f, 75.69085f, 54.534164f, 75.677765f, 54.565544f, 75.80609f, 54.693047f, 75.80887f, 54.68638f, 75.70972f, 54.80999f, 75.65721f, 54.823326f, 75.51332f, 54.90193f, 75.54082f, 54.914154f, 75.34027f, 55.028046f, 75.40027f, 55.137497f, 75.24803f, 55.254166f, 75.26944f, 55.38221f, 75.09137f, 55.540276f, 75.32222f);
            TimezoneMapper.poly[623] = new TzPolygon(54.028183f, 73.551025f, 54.074722f, 73.63333f, 54.054333f, 73.75036f, 53.86211f, 73.69131f, 53.874702f, 73.551025f);
            TimezoneMapper.poly[624] = new TzPolygon(53.5259f, 73.551025f, 53.624332f, 73.67489f, 53.586304f, 73.833694f, 53.648445f, 73.90336f, 53.625526f, 74.07447f, 53.556168f, 74.06392f, 53.593777f, 74.242165f, 53.488472f, 74.28191f, 53.461082f, 74.403946f, 53.683666f, 74.47186f, 53.667f, 74.64128f, 53.82011f, 74.81161f, 53.791f, 75.03939f, 53.978333f, 75.463585f, 54.085194f, 75.40725f, 54.274334f, 76.2273f, 54.356056f, 76.234024f, 54.34636f, 76.65178f, 54.41725f, 76.73553f, 54.457916f, 76.92825f, 54.378f, 76.78242f, 54.30958f, 76.8715f, 54.160362f, 76.74864f, 54.12625f, 76.65459f, 54.168167f, 76.4267f, 54.037224f, 76.50669f, 53.25936f, 77.93342f, 52.141186f, 78.99068f, 49.983185f, 78.99068f, 49.983185f, 73.551025f);
            TimezoneMapper.poly[625] = new TzPolygon(55.580475f, 70.75094f, 55.59777f, 70.76361f, 55.702774f, 70.542206f, 55.799995f, 70.587204f, 55.833054f, 70.78554f, 55.863327f, 70.759155f, 55.836105f, 70.59137f, 55.85222f, 70.475266f, 55.891663f, 70.47415f, 55.89805f, 70.74803f, 55.985268f, 70.8622f, 56.12249f, 70.91666f, 56.159157f, 70.82666f, 56.24305f, 70.850815f, 56.28666f, 70.76277f, 56.34694f, 70.93359f, 56.41166f, 70.94136f, 56.463882f, 70.86249f, 56.461105f, 70.75972f, 56.55555f, 71.09082f, 56.710274f, 71.05582f, 56.66916f, 71.34888f, 56.733047f, 71.39694f, 56.719154f, 71.54082f, 56.826385f, 71.67554f, 56.95694f, 71.61304f, 57.063606f, 71.37082f, 57.20027f, 71.23387f, 57.220543f, 71.09027f, 57.25139f, 71.147766f, 57.340828f, 71.10248f, 57.32805f, 70.811646f, 57.199158f, 70.78194f, 57.193047f, 70.69443f, 57.199158f, 70.60971f, 57.26416f, 70.6561f, 57.28694f, 70.51721f, 57.38388f, 70.4272f, 57.514442f, 70.687485f, 57.566666f, 70.691086f, 57.578606f, 70.57721f, 57.70916f, 70.57443f, 57.766388f, 70.42554f, 57.91713f, 70.37865f, 58.542496f, 70.87303f, 58.528603f, 71.07887f, 58.448875f, 71.11998f, 58.39749f, 71.30386f, 58.071106f, 71.23859f, 58.12471f, 71.98888f, 58.02305f, 72.12164f, 58.006104f, 72.87221f, 58.149162f, 73.08443f, 58.142693f, 73.551025f, 55.580475f, 73.551025f);
            TimezoneMapper.poly[626] = new TzPolygon(60.80784f, 78.99068f, 60.778877f, 78.807205f, 60.830826f, 78.68942f, 60.77694f, 78.475266f, 60.802216f, 78.120255f, 60.74666f, 77.92137f, 60.82749f, 77.68942f, 60.810272f, 77.408035f, 60.853325f, 77.10498f, 60.72721f, 77.07805f, 60.70999f, 76.96776f, 60.534996f, 77.03749f, 60.476654f, 76.77443f, 60.34249f, 76.744705f, 60.26805f, 76.851654f, 60.107773f, 76.687195f, 60.06082f, 76.75888f, 59.72943f, 76.74136f, 59.69332f, 76.64554f, 59.580276f, 76.64833f, 59.54361f, 76.16304f, 59.419716f, 75.990814f, 59.43721f, 75.89749f, 59.296387f, 75.831375f, 59.241104f, 75.615265f, 59.01194f, 75.69054f, 58.678047f, 75.15027f, 58.6186f, 75.17082f, 58.427216f, 74.87608f, 58.133606f, 74.206375f, 58.142693f, 73.551025f, 61.177765f, 73.551025f, 61.177765f, 78.99068f);
            TimezoneMapper.poly[627] = new TzPolygon(58.142693f, 73.551025f, 58.133606f, 74.206375f, 58.29361f, 74.62053f, 58.339157f, 74.62665f, 58.427216f, 74.87608f, 58.58111f, 75.105255f, 58.472763f, 75.355545f, 58.34471f, 75.05415f, 58.219986f, 75.2397f, 58.118324f, 75.066376f, 57.933876f, 75.56442f, 57.646385f, 75.58276f, 57.63916f, 75.712494f, 57.399162f, 75.8586f, 57.414154f, 75.966385f, 57.30416f, 76.06749f, 56.918327f, 76.25194f, 56.726936f, 76.28943f, 56.63082f, 76.149155f, 56.62166f, 76.0461f, 56.44471f, 75.845535f, 56.482765f, 76.05748f, 56.451103f, 76.14194f, 56.41471f, 76.07805f, 56.314156f, 76.11665f, 56.191376f, 76.31915f, 56.149437f, 76.16304f, 56.18055f, 76.15082f, 56.17833f, 75.99443f, 56.127487f, 75.956375f, 56.141106f, 75.74887f, 56.067497f, 75.70055f, 56.04722f, 75.543045f, 55.891663f, 75.312195f, 55.766663f, 75.370255f, 55.755272f, 75.08276f, 55.65999f, 75.23776f, 55.598877f, 75.13136f, 55.580475f, 75.1913f, 55.580475f, 73.551025f);
            TimezoneMapper.poly[628] = new TzPolygon(55.580475f, 84.43033f, 54.16028f, 84.43033f, 54.190826f, 84.379974f, 54.16999f, 84.2861f, 54.21805f, 84.265f, 54.088326f, 83.90637f, 54.143883f, 83.90637f, 54.13221f, 83.576385f, 54.083054f, 83.48526f, 53.952217f, 83.47221f, 53.940544f, 83.408325f, 54.086655f, 83.2961f, 54.048332f, 83.2561f, 54.069717f, 83.195816f, 53.796104f, 82.73749f, 53.75805f, 82.76361f, 53.555267f, 82.53137f, 53.505554f, 82.604706f, 53.466286f, 82.48236f, 53.60083f, 82.17192f, 53.702568f, 82.204834f, 53.657494f, 82.11943f, 53.71888f, 81.97554f, 53.70777f, 81.83249f, 53.848877f, 81.71832f, 53.899437f, 81.851654f, 53.971375f, 81.85748f, 53.999718f, 81.7186f, 53.928604f, 81.55554f, 54.022766f, 81.585266f, 54.13308f, 81.122925f, 54.39621f, 80.92491f, 54.344063f, 80.80066f, 54.259895f, 80.84593f, 54.274933f, 80.769775f, 54.21118f, 80.59439f, 54.10573f, 80.477066f, 54.132965f, 80.435104f, 54.040565f, 80.20952f, 54.08036f, 80.13927f, 53.995583f, 80.064514f, 53.929436f, 79.859985f, 53.863884f, 79.87581f, 53.835823f, 79.687195f, 53.71527f, 79.52165f, 53.67856f, 78.99068f, 55.580475f, 78.99068f);
            TimezoneMapper.poly[629] = new TzPolygon(52.141186f, 78.99068f, 52.03147f, 79.09441f, 50.757084f, 80.0787f, 50.849472f, 80.06956f, 50.832027f, 80.19264f, 50.906f, 80.19994f, 50.974445f, 80.468834f, 51.205833f, 80.44089f, 51.206944f, 80.622444f, 51.308056f, 80.67147f, 51.25408f, 80.92214f, 51.172028f, 81.06925f, 51.206806f, 81.13722f, 50.945415f, 81.0743f, 50.982334f, 81.40381f, 50.758167f, 81.479385f, 50.772083f, 81.76303f, 50.817196f, 81.780304f, 50.78208f, 81.79603f, 50.79972f, 82.0077f, 50.738277f, 82.1778f, 50.775776f, 82.28211f, 50.745472f, 82.49722f, 50.82811f, 82.71308f, 50.934166f, 82.73922f, 50.894028f, 82.96941f, 51.018444f, 83.11164f, 50.989223f, 83.19f, 51.01603f, 83.43864f, 50.97728f, 83.43361f, 50.950554f, 83.6335f, 50.786556f, 83.96408f, 50.72861f, 83.94789f, 50.51536f, 84.23322f, 50.3025f, 84.23536f, 50.242027f, 84.30025f, 50.231117f, 84.43033f, 49.983185f, 84.43033f, 49.983185f, 78.99068f);
            TimezoneMapper.poly[630] = new TzPolygon(52.78183f, 87.00484f, 52.78183f, 89.19577f, 52.708885f, 89.04054f, 52.596382f, 89.120255f, 52.340546f, 88.65776f, 52.314156f, 88.72638f, 52.24444f, 88.67915f, 52.168007f, 88.36488f, 52.45527f, 88.42221f, 52.411934f, 88.2086f, 52.549164f, 87.922485f, 52.447487f, 87.64166f, 52.507774f, 87.370255f, 52.59749f, 87.35138f, 52.58168f, 87.20504f);
            TimezoneMapper.poly[631] = new TzPolygon(49.983185f, 89.60157f, 50.003326f, 89.60803f, 50.041107f, 89.54582f, 50.097214f, 89.59415f, 50.122765f, 89.51915f, 50.204712f, 89.5161f, 50.206657f, 89.336105f, 50.265f, 89.34749f, 50.36583f, 89.52026f, 50.47027f, 89.50583f, 50.383606f, 89.64305f, 50.422768f, 89.831665f, 50.5f, 89.86665f, 50.56527f, 89.78749f, 50.57805f, 89.66832f, 50.85166f, 89.399155f, 51.051384f, 89.06276f, 51.179718f, 89.039154f, 51.232765f, 88.94275f, 51.430824f, 88.96776f, 51.55948f, 88.735855f, 51.531937f, 88.65248f, 51.423325f, 88.66748f, 51.336105f, 88.55359f, 51.318886f, 88.364426f, 51.48694f, 87.9061f, 51.54944f, 87.86165f, 51.604713f, 87.97998f, 51.761665f, 88.10664f, 51.79361f, 87.8336f, 52.071106f, 88.072495f, 52.101936f, 88.12164f, 52.068054f, 88.26915f, 52.120544f, 88.24776f, 52.24444f, 88.67915f, 52.314156f, 88.72638f, 52.340546f, 88.65776f, 52.596382f, 89.120255f, 52.708885f, 89.04054f, 52.78183f, 89.19103f, 52.78183f, 89.86998f, 49.984074f, 89.86998f, 49.983185f, 89.82994f);
            TimezoneMapper.poly[632] = new TzPolygon(50.231117f, 84.43033f, 50.212696f, 84.65f, 50.096695f, 84.83422f, 50.056084f, 84.998024f, 50.00286f, 85.03411f, 49.983185f, 85.02138f, 49.983185f, 84.43033f);
            TimezoneMapper.poly[633] = new TzPolygon(55.580475f, 84.75077f, 55.54055f, 84.77165f, 55.494995f, 84.71971f, 55.453606f, 84.763885f, 55.408882f, 84.63275f, 55.388603f, 84.875534f, 55.234993f, 84.81415f, 55.16999f, 84.91832f, 54.986107f, 84.8797f, 54.87332f, 85.07944f, 54.73221f, 84.9361f, 54.658325f, 85.048325f, 54.604164f, 84.94859f, 54.469437f, 85.07947f, 54.34749f, 84.70444f, 54.141106f, 84.46193f, 54.16028f, 84.43033f, 55.580475f, 84.43033f);
            TimezoneMapper.poly[634] = new TzPolygon(52.78183f, 89.19103f, 52.805824f, 89.24054f, 52.869713f, 89.17804f, 52.866104f, 89.10803f, 52.95527f, 89.06554f, 52.985268f, 88.8911f, 53.13082f, 89.05386f, 53.34305f, 88.97638f, 53.388885f, 88.88971f, 53.35972f, 88.759155f, 53.464157f, 88.86081f, 53.586105f, 88.82887f, 53.663605f, 89.00499f, 53.747215f, 89.01776f, 53.841103f, 89.27721f, 53.884438f, 89.17221f, 53.93804f, 89.22443f, 53.948875f, 89.06276f, 54.0661f, 88.97916f, 54.14888f, 89.19609f, 54.318604f, 89.19693f, 54.351105f, 89.12886f, 54.287216f, 89.01138f, 54.415825f, 88.836105f, 54.28277f, 88.57887f, 54.341103f, 88.54166f, 54.393883f, 88.37442f, 54.422768f, 88.53221f, 54.7461f, 88.624985f, 54.808884f, 88.760544f, 54.89527f, 88.7486f, 54.8761f, 88.66666f, 54.968597f, 88.671646f, 55.01333f, 88.54443f, 55.284996f, 88.39943f, 55.519714f, 88.68553f, 55.580475f, 88.81606f, 55.580475f, 89.86998f, 52.78183f, 89.86998f);
            TimezoneMapper.poly[635] = new TzPolygon(54.155888f, 84.43033f, 54.13916f, 84.45444f, 54.34749f, 84.70444f, 54.48916f, 85.15109f, 54.195824f, 85.447205f, 54.24305f, 85.604706f, 54.03972f, 85.93027f, 53.98416f, 86.14554f, 53.532494f, 86.57666f, 53.472763f, 86.81944f, 53.509438f, 87.0061f, 53.47721f, 87.022766f, 53.4486f, 86.918045f, 53.41694f, 86.99359f, 53.297775f, 86.869705f, 53.246704f, 86.660355f, 53.080276f, 86.93387f, 53.081383f, 87.081665f, 52.920547f, 87.028046f, 52.853607f, 86.933044f, 52.78183f, 87.00484f, 52.78183f, 84.43033f);
            TimezoneMapper.poly[636] = new TzPolygon(56.040455f, 84.43033f, 56.044266f, 84.410675f, 56.049473f, 84.43033f);
            TimezoneMapper.poly[637] = new TzPolygon(61.177765f, 84.43033f, 60.92503f, 84.43033f, 60.855415f, 84.25964f, 60.824715f, 83.993866f, 61.049164f, 83.50943f, 61.03277f, 83.14499f, 60.71277f, 82.38248f, 60.610275f, 82.390274f, 60.518326f, 82.16666f, 60.650543f, 81.86165f, 60.615547f, 81.49776f, 60.637497f, 81.11116f, 60.75419f, 81.03822f, 60.800163f, 80.69771f, 60.76557f, 80.42099f, 60.661934f, 80.18399f, 60.69554f, 79.71582f, 60.648605f, 79.38666f, 60.719986f, 79.2836f, 60.79666f, 79.31833f, 60.8386f, 79.18553f, 60.80784f, 78.99068f, 61.177765f, 78.99068f);
            TimezoneMapper.poly[638] = new TzPolygon(60.697296f, 84.43033f, 60.855415f, 84.25964f, 60.92503f, 84.43033f);
            TimezoneMapper.poly[639] = new TzPolygon(58.37912f, 84.43033f, 58.37912f, 87.97486f, 58.232765f, 88.019714f, 58.10666f, 88.14638f, 58.033607f, 88.70915f, 57.958046f, 88.85359f, 57.948044f, 89.3486f, 57.88777f, 89.40387f, 57.634438f, 89.39249f, 57.617767f, 89.14638f, 57.509163f, 89.07138f, 57.441933f, 88.8647f, 57.22193f, 88.7272f, 57.12388f, 88.524704f, 57.08499f, 88.73248f, 56.833153f, 88.624146f, 56.77999f, 88.53749f, 56.83416f, 88.39972f, 56.54361f, 87.784424f, 56.659157f, 87.51277f, 56.609993f, 87.38333f, 56.674164f, 87.14804f, 56.54722f, 87.147766f, 56.53444f, 87.02666f, 56.637497f, 86.68831f, 56.54583f, 86.3936f, 56.629433f, 86.31944f, 56.62555f, 86.207214f, 56.487213f, 86.1022f, 56.443604f, 85.861374f, 56.40332f, 85.77666f, 56.36444f, 85.80554f, 56.31527f, 85.58472f, 56.238884f, 85.688034f, 56.210274f, 85.40971f, 56.245827f, 85.24776f, 56.22193f, 85.12276f, 56.14305f, 85.085266f, 56.132767f, 84.99498f, 56.179718f, 84.92192f, 56.052f, 84.43033f);
            TimezoneMapper.poly[640] = new TzPolygon(56.04323f, 84.43033f, 56.016937f, 84.55165f, 55.86666f, 84.50194f, 55.842216f, 84.60803f, 55.76583f, 84.63721f, 55.728043f, 84.543045f, 55.67222f, 84.702774f, 55.580475f, 84.75077f, 55.580475f, 84.43033f);
            TimezoneMapper.poly[641] = new TzPolygon(55.580475f, 88.81606f, 55.688324f, 89.04776f, 55.773605f, 89.37164f, 55.86721f, 89.45166f, 55.918327f, 89.30664f, 56.07888f, 89.26721f, 56.211937f, 89.0486f, 56.302773f, 89.066666f, 56.37999f, 88.70665f, 56.44249f, 88.58832f, 56.533882f, 88.6297f, 56.588882f, 88.50194f, 56.62638f, 88.50722f, 56.638046f, 88.67915f, 56.714157f, 88.74136f, 56.742767f, 88.644714f, 56.833153f, 88.624146f, 57.08499f, 88.73248f, 57.12388f, 88.524704f, 57.22193f, 88.7272f, 57.441933f, 88.8647f, 57.509163f, 89.07138f, 57.617767f, 89.14638f, 57.634438f, 89.39249f, 57.88777f, 89.40387f, 57.948044f, 89.3486f, 57.958046f, 88.85359f, 58.033607f, 88.70915f, 58.10666f, 88.14638f, 58.232765f, 88.019714f, 58.37912f, 87.97486f, 58.37912f, 89.86998f, 55.580475f, 89.86998f);
            TimezoneMapper.poly[642] = new TzPolygon(60.705284f, 84.43033f, 60.466385f, 84.701935f, 60.356384f, 84.78526f, 59.97943f, 84.52748f, 59.90027f, 84.69386f, 59.89083f, 85.49165f, 59.9561f, 85.93887f, 59.956657f, 86.57138f, 59.88166f, 87.09776f, 59.68499f, 87.182755f, 59.672768f, 87.522766f, 59.263885f, 87.90332f, 59.301384f, 88.63054f, 59.225548f, 88.585266f, 59.025826f, 88.83443f, 58.911102f, 88.384155f, 58.52555f, 87.92998f, 58.37912f, 87.97486f, 58.37912f, 84.43033f);
            TimezoneMapper.poly[643] = new TzPolygon(61.177765f, 85.269295f, 61.0f, 84.61415f, 60.92503f, 84.43033f, 61.177765f, 84.43033f);
            TimezoneMapper.poly[644] = new TzPolygon(47.88436f, 89.995316f, 47.81686f, 89.94414f, 47.822235f, 89.86998f, 47.88436f, 89.86998f);
            TimezoneMapper.poly[645] = new TzPolygon(47.88436f, 90.00695f, 47.88436f, 98.08673f, 47.74921f, 98.1674f, 47.65769f, 98.34113f, 47.54511f, 98.31347f, 47.24825f, 98.1021f, 47.07199f, 97.86137f, 46.56517f, 97.55562f, 46.3685f, 97.61436f, 46.15852f, 97.80164f, 45.97168f, 98.18118f, 45.84208f, 98.32463f, 45.40235f, 98.44553f, 45.2261f, 98.43602f, 44.60311f, 98.18233f, 43.77015f, 97.70393f, 43.13073f, 97.72182f, 42.70184f, 97.82729f, 42.773888f, 97.15144f, 42.712166f, 96.93283f, 42.742416f, 96.67472f, 42.717724f, 96.36367f, 42.882446f, 96.32445f, 43.266f, 95.894f, 43.402084f, 95.88106f, 43.988667f, 95.55078f, 44.045166f, 95.345085f, 44.289307f, 95.423615f, 44.254555f, 94.9905f, 44.344696f, 94.70386f, 44.448334f, 94.57597f, 44.527195f, 94.325584f, 44.66247f, 94.19739f, 44.965332f, 93.50828f, 45.018417f, 93.06983f, 45.06161f, 91.59255f, 45.14428f, 91.46081f, 45.10475f, 91.374306f, 45.202168f, 91.13253f, 45.19197f, 90.87864f, 45.262474f, 90.89525f, 45.28636f, 90.813225f, 45.378777f, 90.79625f, 45.516804f, 90.66356f, 45.70933f, 90.70428f, 46.015667f, 91.01986f, 46.305084f, 90.91178f, 46.563946f, 91.07764f, 46.59253f, 91.02758f, 46.70933f, 91.06136f, 46.953304f, 90.91358f, 47.0015f, 90.73434f, 47.244167f, 90.51786f, 47.48711f, 90.45686f, 47.65703f, 90.33745f, 47.729805f, 90.12817f, 47.85858f, 90.07311f);
            TimezoneMapper.poly[646] = new TzPolygon(41.64114f, 104.94967f, 41.645306f, 104.94486f, 41.662613f, 104.534f, 41.883804f, 104.54375f, 41.807888f, 103.86239f, 41.9075f, 103.32208f, 42.134167f, 102.73905f, 42.140472f, 102.41458f, 42.218666f, 102.06331f, 42.47361f, 101.8168f, 42.64525f, 100.91158f, 42.683334f, 100.32647f, 42.630806f, 100.28267f, 42.64761f, 99.995026f, 42.570084f, 99.49628f, 42.63786f, 98.24789f, 42.70184f, 97.82729f, 43.13073f, 97.72182f, 43.77015f, 97.70393f, 44.60311f, 98.18233f, 45.2261f, 98.43602f, 45.40235f, 98.44553f, 45.84208f, 98.32463f, 45.97168f, 98.18118f, 46.15852f, 97.80164f, 46.3685f, 97.61436f, 46.56517f, 97.55562f, 47.07199f, 97.86137f, 47.24825f, 98.1021f, 47.54511f, 98.31347f, 47.65769f, 98.34113f, 47.74921f, 98.1674f, 47.88436f, 98.08673f, 47.88436f, 104.94967f);
            TimezoneMapper.poly[647] = new TzPolygon(49.983185f, 89.86998f, 49.985416f, 89.93056f, 50.037613f, 90.08672f, 50.015667f, 90.26414f, 50.144417f, 90.368225f, 50.16825f, 90.675415f, 50.266666f, 90.77461f, 50.336777f, 90.66803f, 50.487915f, 90.737724f, 50.45772f, 91.42917f, 50.546585f, 91.44072f, 50.576332f, 91.645584f, 50.65961f, 91.65578f, 50.720417f, 91.75664f, 50.681026f, 92.14391f, 50.71622f, 92.2758f, 50.894638f, 92.3718f, 50.77675f, 92.47114f, 50.757557f, 92.615166f, 50.705166f, 92.61614f, 50.70933f, 92.748024f, 50.78964f, 92.818695f, 50.784f, 93.00925f, 50.6305f, 92.983055f, 50.566807f, 93.10403f, 50.601807f, 93.46958f, 50.575306f, 94.276276f, 50.21375f, 94.40703f, 50.033943f, 94.61906f, 50.06167f, 94.964226f, 49.956417f, 95.094444f, 49.92064f, 95.497f, 49.965332f, 95.73766f, 50.04125f, 95.86275f, 49.97975f, 95.97378f, 50.019665f, 96.07383f, 49.99136f, 96.29303f, 49.898388f, 96.42711f, 49.982f, 96.61175f, 49.96625f, 96.675835f, 49.90439f, 96.68056f, 49.94611f, 96.80295f, 49.9145f, 97.002975f, 49.784462f, 97.17303f, 49.6056f, 97.09158f, 49.51917f, 96.95351f, 49.371f, 97.07001f, 49.26435f, 97.40982f, 47.88436f, 97.40982f, 47.88436f, 90.00695f, 47.88786f, 89.99797f, 47.88436f, 89.995316f, 47.88436f, 89.86998f);
            TimezoneMapper.poly[648] = new TzPolygon(49.267838f, 97.40982f, 49.40308f, 97.00143f, 49.51917f, 96.95351f, 49.6056f, 97.09158f, 49.784462f, 97.17303f, 49.756695f, 97.3035f, 49.785175f, 97.40982f);
            TimezoneMapper.poly[649] = new TzPolygon(53.500393f, 97.40982f, 53.6f, 97.332214f, 53.609436f, 97.12276f, 53.728874f, 96.88666f, 53.706657f, 96.72777f, 53.640274f, 96.692474f, 53.675827f, 96.6086f, 53.645546f, 96.571655f, 53.995544f, 96.04942f, 54.09027f, 96.05859f, 54.234436f, 95.67192f, 54.29361f, 95.65193f, 54.418327f, 95.78305f, 54.399994f, 95.92331f, 54.576103f, 96.05582f, 54.512497f, 96.57083f, 54.696938f, 96.56192f, 54.92138f, 96.71527f, 55.02027f, 96.577774f, 55.080826f, 96.68692f, 55.255272f, 96.70055f, 55.324715f, 96.92026f, 55.437492f, 96.84137f, 55.594437f, 96.85582f, 55.651382f, 96.74637f, 55.697487f, 96.82471f, 55.77916f, 96.77249f, 55.855827f, 96.96971f, 56.022217f, 96.980545f, 56.02555f, 97.0611f, 56.116386f, 97.1236f, 56.048607f, 97.3522f, 56.122574f, 97.40982f);
            TimezoneMapper.poly[650] = new TzPolygon(57.00305f, 97.40982f, 57.043175f, 97.34009f, 57.135464f, 97.40982f);
            TimezoneMapper.poly[651] = new TzPolygon(49.26435f, 97.40982f, 49.20113f, 97.61127f, 49.03085f, 97.83469f, 49.01986f, 97.91782f, 49.08019f, 98.28249f, 49.00054f, 98.59711f, 49.13386f, 98.91248f, 49.13755f, 99.05325f, 48.88155f, 99.22124f, 48.47759f, 99.04506f, 48.33106f, 99.10564f, 48.21694f, 98.55273f, 48.03551f, 98.08526f, 47.94886f, 98.04823f, 47.88436f, 98.08673f, 47.88436f, 97.40982f);
            TimezoneMapper.poly[652] = new TzPolygon(47.88436f, 98.08673f, 47.94886f, 98.04823f, 48.03551f, 98.08526f, 48.21694f, 98.55273f, 48.33106f, 99.10564f, 48.47759f, 99.04506f, 48.88155f, 99.22124f, 49.13755f, 99.05325f, 49.13386f, 98.91248f, 49.00054f, 98.59711f, 49.08019f, 98.28249f, 49.03085f, 97.83469f, 49.20113f, 97.61127f, 49.267838f, 97.40982f, 49.785175f, 97.40982f, 49.795666f, 97.449f, 49.88697f, 97.60817f, 49.948223f, 97.58344f, 49.987835f, 97.79422f, 49.95175f, 97.87442f, 50.091667f, 98.106445f, 50.255474f, 98.25439f, 50.547443f, 98.3232f, 50.652805f, 98.04886f, 50.78186f, 97.955025f, 50.867027f, 98.01964f, 51.012222f, 97.82831f, 51.41997f, 98.02278f, 51.462223f, 98.23425f, 51.717834f, 98.31522f, 51.829807f, 98.710915f, 52.15425f, 98.92047f, 52.07197f, 99.04853f, 52.02011f, 99.27317f, 51.961555f, 99.32128f, 51.901527f, 99.727585f, 51.753307f, 99.99347f, 51.75039f, 100.51564f, 51.543083f, 101.143f, 51.54426f, 101.17975f, 47.88436f, 101.17975f);
            TimezoneMapper.poly[653] = new TzPolygon(51.54426f, 101.17975f, 51.543083f, 101.143f, 51.75039f, 100.51564f, 51.753307f, 99.99347f, 51.901527f, 99.727585f, 51.961555f, 99.32128f, 52.02011f, 99.27317f, 52.07197f, 99.04853f, 52.195824f, 98.927475f, 52.186935f, 98.8136f, 52.277214f, 98.79999f, 52.261665f, 98.64444f, 52.369987f, 98.6797f, 52.424164f, 98.625534f, 52.520546f, 98.81526f, 52.611664f, 98.80415f, 52.66027f, 98.93526f, 52.79722f, 98.94081f, 52.791664f, 98.865265f, 52.83194f, 98.84444f, 52.924995f, 98.93109f, 52.856102f, 99.22026f, 52.95378f, 99.24997f, 53.038887f, 98.98193f, 53.102493f, 99.06526f, 53.153877f, 98.9236f, 53.11944f, 98.8772f, 53.156097f, 98.68637f, 53.101387f, 98.60248f, 53.100548f, 98.29999f, 53.22721f, 98.28581f, 53.25055f, 98.03027f, 53.359993f, 97.96304f, 53.37416f, 97.59221f, 53.43888f, 97.587494f, 53.460274f, 97.441086f, 53.500393f, 97.40982f, 53.602596f, 97.40982f, 53.602596f, 101.17975f);
            TimezoneMapper.poly[654] = new TzPolygon(51.54426f, 101.17975f, 53.602596f, 101.17975f, 53.602596f, 104.94967f, 50.37192f, 104.94967f, 50.305f, 104.40511f, 50.186695f, 104.22842f, 50.15436f, 104.061f, 50.208473f, 103.80341f, 50.138584f, 103.669945f, 50.21561f, 103.43928f, 50.204056f, 103.313225f, 50.312695f, 103.20461f, 50.314304f, 102.915695f, 50.429417f, 102.64211f, 50.53289f, 102.5302f, 50.599777f, 102.30172f, 50.739582f, 102.35828f, 50.84133f, 102.22459f, 50.965527f, 102.26689f, 51.264f, 102.15125f, 51.32264f, 102.21306f, 51.39264f, 102.083336f, 51.45172f, 101.656944f, 51.499832f, 101.57767f, 51.458416f, 101.377335f, 51.546196f, 101.24047f);
            TimezoneMapper.poly[655] = new TzPolygon(59.32083f, 103.01367f, 59.211937f, 102.8286f, 59.191658f, 102.53526f, 59.072495f, 102.40443f, 58.943047f, 102.43692f, 58.897774f, 102.53998f, 58.71666f, 102.51138f, 58.64527f, 102.31053f, 58.52527f, 102.19443f, 58.418884f, 101.68498f, 58.2211f, 101.52805f, 58.218597f, 101.27638f, 58.115273f, 101.10971f, 58.02471f, 101.15833f, 57.89805f, 101.03526f, 57.88611f, 100.80775f, 57.773605f, 100.8911f, 57.668327f, 100.79694f, 57.50083f, 100.96277f, 57.40193f, 100.74193f, 57.54805f, 100.40359f, 57.682495f, 100.32805f, 57.731102f, 100.37303f, 57.865547f, 100.12303f, 58.079437f, 100.04414f, 58.08111f, 99.68193f, 57.793327f, 99.421646f, 57.774162f, 99.08415f, 57.721657f, 98.97415f, 57.728043f, 98.85416f, 57.791664f, 98.76944f, 57.80694f, 97.917206f, 57.135464f, 97.40982f, 59.32083f, 97.40982f);
            TimezoneMapper.poly[656] = new TzPolygon(57.00305f, 97.40982f, 56.96444f, 97.47693f, 56.95527f, 97.62303f, 56.891106f, 97.480545f, 56.810272f, 97.48581f, 56.78055f, 97.77609f, 56.5586f, 97.74498f, 56.559433f, 97.879425f, 56.392494f, 97.88193f, 56.387497f, 97.55664f, 56.18943f, 97.58693f, 56.14666f, 97.42859f, 56.122574f, 97.40982f);
            TimezoneMapper.poly[657] = new TzPolygon(58.949516f, 104.94967f, 58.894432f, 104.83859f, 58.81526f, 104.75583f, 58.72165f, 104.87331f, 58.668594f, 104.79498f, 58.600204f, 104.44638f, 58.69915f, 104.39915f, 58.75866f, 104.2364f, 58.734985f, 103.85582f, 58.76583f, 103.94582f, 58.848602f, 103.95416f, 58.90555f, 103.89333f, 58.913048f, 103.59276f, 58.986107f, 103.53831f, 59.021378f, 103.38916f, 59.179436f, 103.24443f, 59.260826f, 103.32639f, 59.28972f, 103.20638f, 59.26416f, 103.08221f, 59.32083f, 103.01682f, 59.32083f, 104.94967f);
            TimezoneMapper.poly[658] = new TzPolygon(59.495953f, 104.94967f, 59.69332f, 104.79443f, 59.746864f, 104.94967f);
            TimezoneMapper.poly[659] = new TzPolygon(60.302612f, 104.94967f, 60.30388f, 104.8772f, 60.40249f, 104.65387f, 60.63333f, 104.51666f, 60.743324f, 104.6022f, 60.839714f, 104.45694f, 60.893883f, 104.63443f, 60.94554f, 104.55971f, 61.07444f, 104.6147f, 61.177765f, 104.83499f, 61.177586f, 104.94967f);
            TimezoneMapper.poly[660] = new TzPolygon(45.36664f, 74.900475f, 42.983513f, 74.900475f, 42.99439f, 74.74047f, 43.184113f, 74.485725f, 43.237694f, 74.16942f, 43.18497f, 74.15808f, 43.158165f, 74.05192f, 43.19197f, 73.95439f, 43.09725f, 73.890976f, 43.108776f, 73.778305f, 43.02286f, 73.5582f, 42.626278f, 73.42394f, 42.409084f, 73.491196f, 42.427223f, 73.34617f, 42.506084f, 73.29811f, 42.534973f, 72.910774f, 42.681446f, 72.60675f, 42.759277f, 72.28203f, 42.7365f, 72.12036f, 42.832806f, 71.86364f, 42.76478f, 71.62742f, 42.809082f, 71.51761f, 42.780388f, 71.27714f, 42.609417f, 71.16541f, 42.57814f, 71.00972f, 42.500195f, 71.09578f, 42.46436f, 71.06844f, 42.47314f, 70.964836f, 42.320057f, 70.871864f, 42.258945f, 70.8795f, 42.259193f, 70.94022f, 42.18661f, 70.76256f, 42.00083f, 70.626976f, 42.130474f, 70.4105f, 41.975613f, 70.33089f, 41.763416f, 70.03895f, 41.78589f, 69.99025f, 41.70564f, 69.926f, 41.71908f, 69.8328f, 41.658028f, 69.621086f, 41.522415f, 69.42089f, 41.454887f, 69.42314f, 41.44589f, 69.18483f, 40.936333f, 68.64525f, 40.983112f, 68.6725f, 41.027054f, 68.6067f, 41.01311f, 68.53492f, 41.10989f, 68.43661f, 41.17913f, 68.111374f, 45.36664f, 68.111374f);
            TimezoneMapper.poly[661] = new TzPolygon(45.36664f, 81.68958f, 45.275196f, 81.48497f, 45.219166f, 81.10539f, 45.17439f, 81.075195f, 45.1305f, 80.89064f, 45.17372f, 80.729f, 45.110332f, 80.60606f, 45.117805f, 80.47017f, 45.04464f, 80.38103f, 45.049557f, 80.08183f, 45.010334f, 80.08303f, 44.899723f, 79.84544f, 44.878944f, 79.96042f, 44.801304f, 80.03028f, 44.83972f, 80.279526f, 44.73286f, 80.48236f, 44.65389f, 80.36022f, 44.609165f, 80.40411f, 44.481304f, 80.342834f, 44.149918f, 80.41186f, 43.834194f, 80.51975f, 43.47117f, 80.74211f, 43.30561f, 80.66258f, 43.293278f, 80.75878f, 43.17975f, 80.79986f, 43.03947f, 80.38875f, 42.986057f, 80.40378f, 42.891224f, 80.588f, 42.828445f, 80.25833f, 42.636585f, 80.167336f, 42.515778f, 80.27753f, 42.427723f, 80.20853f, 42.234943f, 80.27856f, 42.205612f, 80.11828f, 42.32836f, 80.08475f, 42.435585f, 79.939415f, 42.47697f, 79.6638f, 42.45636f, 79.509445f, 42.65425f, 79.21822f, 42.783943f, 79.177025f, 42.74025f, 79.07283f, 42.903778f, 78.49161f, 42.838333f, 78.059f, 42.91064f, 77.78533f, 42.91364f, 77.259865f, 42.959835f, 77.175804f, 42.985085f, 76.859665f, 42.905193f, 76.72322f, 42.918724f, 76.514084f, 42.86553f, 76.347084f, 42.919224f, 76.26847f, 42.909916f, 76.03547f, 42.954445f, 75.92711f, 42.933693f, 75.81509f, 42.796528f, 75.71069f, 42.84992f, 75.1573f, 42.98275f, 74.91167f, 42.983513f, 74.900475f, 45.36664f, 74.900475f);
            TimezoneMapper.poly[662] = new TzPolygon(45.36664f, 82.60005f, 45.231445f, 82.59253f, 45.120667f, 82.48619f, 45.236f, 82.252914f, 45.156807f, 81.95453f, 45.20925f, 81.89772f, 45.204887f, 81.81834f, 45.30689f, 81.78817f, 45.36664f, 81.68958f);
            TimezoneMapper.poly[663] = new TzPolygon(45.36664f, 82.59722f, 45.431667f, 82.60367f, 45.471474f, 82.41697f, 45.57064f, 82.27197f, 45.983387f, 82.50445f, 46.18403f, 82.52903f, 46.517693f, 82.73903f, 47.212112f, 83.03089f, 47.223667f, 83.17467f, 47.05842f, 83.580475f, 46.965363f, 84.03822f, 46.989136f, 84.43033f, 45.36664f, 84.43033f);
            TimezoneMapper.poly[664] = new TzPolygon(46.989136f, 84.43033f, 47.00464f, 84.68606f, 46.90836f, 84.78586f, 46.83989f, 84.695335f, 46.826694f, 84.80595f, 46.87164f, 84.962585f, 47.05364f, 85.2232f, 47.059387f, 85.529724f, 47.190666f, 85.5765f, 47.23086f, 85.69683f, 47.375526f, 85.70717f, 47.519665f, 85.6035f, 47.939445f, 85.54158f, 48.371f, 85.73544f, 48.425777f, 85.8407f, 48.43547f, 86.214386f, 48.500973f, 86.318054f, 48.543f, 86.588554f, 48.714863f, 86.763054f, 48.842834f, 86.812386f, 48.990665f, 86.72322f, 49.11261f, 86.86475f, 49.150806f, 87.1347f, 49.147118f, 87.150154f, 45.36664f, 87.150154f, 45.36664f, 84.43033f);
            TimezoneMapper.poly[665] = new TzPolygon(49.26217f, 87.150154f, 49.303417f, 86.942085f, 49.544167f, 86.801865f, 49.581665f, 86.63416f, 49.641277f, 86.621635f, 49.711f, 86.78342f, 49.803165f, 86.80322f, 49.803444f, 86.61383f, 49.638695f, 86.4265f, 49.583f, 86.23872f, 49.461166f, 86.16928f, 49.51675f, 86.10555f, 49.48639f, 85.972305f, 49.552418f, 85.931335f, 49.556835f, 85.67467f, 49.610195f, 85.5895f, 49.56372f, 85.42083f, 49.604637f, 85.35394f, 49.589695f, 85.278275f, 49.642334f, 85.198944f, 49.87472f, 85.104774f, 49.920807f, 84.981026f, 49.983185f, 85.02138f, 49.983185f, 87.150154f);
            TimezoneMapper.poly[666] = new TzPolygon(49.147118f, 87.150154f, 49.083168f, 87.41825f, 49.14164f, 87.51203f, 49.175f, 87.832306f, 49.048084f, 87.832085f, 48.985916f, 87.914474f, 48.936832f, 87.76667f, 48.884583f, 87.749664f, 48.850224f, 87.821556f, 48.813026f, 87.80339f, 48.567978f, 88.009895f, 48.478306f, 88.33669f, 48.391724f, 88.41236f, 48.413277f, 88.51639f, 48.33411f, 88.607834f, 48.230446f, 88.588196f, 48.080276f, 88.985725f, 47.947304f, 89.041f, 47.951973f, 89.20656f, 48.04322f, 89.36894f, 48.048695f, 89.56589f, 47.945835f, 89.61728f, 47.818165f, 89.79642f, 47.817516f, 89.86998f, 45.36664f, 89.86998f, 45.36664f, 87.150154f);
            TimezoneMapper.poly[667] = new TzPolygon(49.983185f, 89.60157f, 49.983185f, 89.82994f, 49.978943f, 89.63878f, 49.90619f, 89.638245f, 49.92888f, 89.58415f);
            TimezoneMapper.poly[668] = new TzPolygon(49.26043f, 87.150154f, 49.231693f, 87.30386f, 49.11628f, 87.31267f, 49.147808f, 87.150154f);
            TimezoneMapper.poly[669] = new TzPolygon(49.26217f, 87.150154f, 49.983185f, 87.150154f, 49.983185f, 89.60157f, 49.92888f, 89.58415f, 49.819168f, 89.64064f, 49.7945f, 89.738525f, 49.70972f, 89.68183f, 49.658833f, 89.444725f, 49.594666f, 89.377335f, 49.653526f, 89.239975f, 49.546585f, 89.22075f, 49.47711f, 88.99172f, 49.555805f, 88.89111f, 49.450165f, 88.86328f, 49.475f, 88.679f, 49.527637f, 88.63228f, 49.4825f, 88.5515f, 49.48986f, 88.22322f, 49.374443f, 88.09695f, 49.299973f, 88.19639f, 49.254055f, 88.148865f, 49.26636f, 88.055275f, 49.179054f, 87.98492f, 49.14164f, 87.51203f, 49.083084f, 87.410194f, 49.099693f, 87.312584f, 49.231693f, 87.30386f);
            TimezoneMapper.poly[670] = new TzPolygon(61.177765f, 85.269295f, 61.297493f, 85.71054f, 61.388046f, 85.6436f, 61.469154f, 85.966385f, 61.543053f, 85.96915f, 61.59777f, 85.85193f, 61.57361f, 85.72276f, 61.6286f, 85.46776f, 61.70166f, 85.38582f, 61.685265f, 85.24832f, 61.784164f, 85.00804f, 61.8061f, 84.687485f, 61.939156f, 84.51666f, 62.1836f, 84.43526f, 62.44777f, 84.859146f, 62.54222f, 84.89055f, 62.638603f, 85.0986f, 62.814438f, 85.2372f, 62.93277f, 85.52083f, 63.079994f, 85.586655f, 63.138046f, 85.466385f, 63.35444f, 85.673874f, 63.35f, 85.40776f, 63.51583f, 85.2961f, 63.536385f, 85.08194f, 63.586487f, 85.03203f, 63.591377f, 85.176926f, 63.646385f, 85.26721f, 63.929993f, 85.51639f, 64.05443f, 85.95804f, 64.27527f, 86.0311f, 61.177765f, 86.0311f);
            TimezoneMapper.poly[671] = new TzPolygon(64.27527f, 86.0311f, 64.45888f, 85.84082f, 64.58888f, 85.918045f, 64.64749f, 85.80859f, 64.78471f, 85.82054f, 64.83028f, 85.546936f, 64.75f, 85.16081f, 64.812195f, 85.09444f, 64.826385f, 84.932205f, 64.92859f, 84.92082f, 64.90915f, 84.278046f, 64.993866f, 84.39665f, 65.0986f, 84.3111f, 65.29166f, 84.58554f, 65.50221f, 84.503876f, 65.59444f, 84.19054f, 65.61665f, 84.29999f, 65.68776f, 84.32666f, 65.7f, 84.17665f, 65.79387f, 84.032486f, 65.773605f, 83.591095f, 65.82944f, 83.50638f, 65.904434f, 83.528046f, 66.06694f, 83.28943f, 66.19525f, 83.5397f, 66.44342f, 83.14958f, 66.44342f, 86.0311f);
            TimezoneMapper.poly[672] = new TzPolygon(61.281796f, 104.94967f, 61.319435f, 104.9272f, 61.349426f, 104.78499f, 61.3731f, 104.94967f);
            TimezoneMapper.poly[673] = new TzPolygon(44.89215f, 60.916496f, 44.89215f, 67.49616f, 44.36361f, 67.47887f, 44.26416f, 67.798325f, 44.232765f, 67.765f, 44.159714f, 67.82887f, 44.005272f, 68.08276f, 43.905266f, 67.941925f, 43.69554f, 67.97388f, 43.74749f, 67.84665f, 43.588043f, 67.84221f, 43.371284f, 67.66199f, 43.30249f, 67.6797f, 43.372765f, 67.72026f, 43.337494f, 67.7961f, 43.278046f, 67.77832f, 43.249435f, 67.82971f, 42.423607f, 66.21887f, 42.423805f, 66.106415f, 42.962196f, 66.0992f, 42.868137f, 65.827194f, 43.303528f, 65.651085f, 43.545277f, 65.294136f, 43.775585f, 65.11442f, 43.600334f, 64.51386f, 43.67339f, 63.381363f, 43.486526f, 62.028694f, 43.51228f, 61.97564f, 44.268665f, 61.121418f, 44.35775f, 61.111195f, 44.58444f, 60.726994f, 44.622673f, 60.727974f, 44.682854f, 60.91236f, 44.757122f, 60.954617f);
            TimezoneMapper.poly[674] = new TzPolygon(44.89215f, 67.49616f, 44.89215f, 68.111374f, 41.17913f, 68.111374f, 41.19661f, 68.029274f, 41.16025f, 67.84636f, 41.202194f, 67.77908f, 41.251415f, 66.60611f, 41.896305f, 66.53075f, 41.9435f, 66.00855f, 42.378418f, 66.01644f, 42.347332f, 66.10567f, 42.423805f, 66.106415f, 42.423607f, 66.21887f, 43.249435f, 67.82971f, 43.278046f, 67.77832f, 43.337494f, 67.7961f, 43.372765f, 67.72026f, 43.30249f, 67.6797f, 43.371284f, 67.66199f, 43.588043f, 67.84221f, 43.74749f, 67.84665f, 43.69554f, 67.97388f, 43.905266f, 67.941925f, 44.005272f, 68.08276f, 44.159714f, 67.82887f, 44.232765f, 67.765f, 44.26416f, 67.798325f, 44.36361f, 67.47887f);
            TimezoneMapper.poly[675] = new TzPolygon(45.601833f, 58.58015f, 45.59929f, 58.578686f, 45.602848f, 58.58015f);
            TimezoneMapper.poly[676] = new TzPolygon(47.82583f, 58.58015f, 45.602848f, 58.58015f, 45.59929f, 58.578686f, 45.1748f, 56.750275f, 47.82583f, 56.750275f);
            TimezoneMapper.poly[677] = new TzPolygon(45.436764f, 58.94178f, 45.436764f, 59.202473f, 45.417805f, 59.236732f, 45.276928f, 59.239082f);
            TimezoneMapper.poly[678] = new TzPolygon(45.22798f, 59.4092f, 45.20008f, 59.407715f, 45.280815f, 59.267994f, 45.313133f, 59.290474f, 45.252014f, 59.335434f, 45.333504f, 59.343163f);
            TimezoneMapper.poly[679] = new TzPolygon(47.82583f, 61.927216f, 47.821106f, 61.37777f, 47.785828f, 61.30638f, 47.200798f, 60.96411f, 47.25805f, 60.388603f, 46.925346f, 60.354584f, 46.909714f, 60.097214f, 46.524437f, 59.57527f, 46.00444f, 59.043358f, 45.908924f, 58.706078f, 45.602848f, 58.58015f, 47.82583f, 58.58015f);
            TimezoneMapper.poly[680] = new TzPolygon(47.82583f, 64.158104f, 47.138885f, 62.85192f, 47.188324f, 62.653046f, 47.34147f, 62.593414f, 47.54138f, 62.34777f, 47.53916f, 62.250275f, 47.82583f, 61.927216f);
            TimezoneMapper.poly[681] = new TzPolygon(44.89215f, 67.49616f, 45.161934f, 67.50499f, 45.17916f, 67.36276f, 45.29341f, 67.17833f, 45.771103f, 67.35832f, 45.9411f, 67.18193f, 46.1408f, 67.15135f, 46.181664f, 65.629425f, 46.48082f, 65.11914f, 46.472763f, 64.97026f, 46.696655f, 64.388885f, 46.920273f, 63.418053f, 47.089714f, 63.086105f, 47.042564f, 62.693054f, 47.82583f, 64.1712f, 47.82583f, 68.111374f, 44.89215f, 68.111374f);
            TimezoneMapper.poly[682] = new TzPolygon(66.44342f, 56.750275f, 66.44342f, 63.361923f, 66.326385f, 63.22332f, 66.23276f, 63.29055f, 66.24609f, 63.093605f, 66.071106f, 62.853607f, 65.86748f, 62.846657f, 65.70221f, 61.87027f, 65.31888f, 61.24916f, 65.28888f, 61.333603f, 65.18553f, 61.239433f, 65.17221f, 61.111664f, 65.04166f, 60.96277f, 65.0486f, 60.811935f, 64.883606f, 60.63138f, 65.051926f, 60.42749f, 65.067215f, 60.158043f, 64.783325f, 59.65416f, 64.65166f, 59.70249f, 64.48193f, 59.479713f, 64.460266f, 59.604713f, 64.23248f, 59.588043f, 64.1375f, 59.85972f, 63.999435f, 59.770546f, 63.924713f, 59.56916f, 63.073883f, 59.222214f, 62.96666f, 59.276657f, 62.897774f, 59.487213f, 62.729713f, 59.39138f, 62.54722f, 59.50972f, 62.517212f, 59.650543f, 62.14222f, 59.404434f, 61.988045f, 59.48555f, 61.85694f, 59.341377f, 61.682213f, 59.348877f, 61.544716f, 59.010826f, 61.503883f, 58.77083f, 61.485268f, 57.096657f, 61.52023f, 56.750275f);
            TimezoneMapper.poly[683] = new TzPolygon(66.44342f, 63.363903f, 66.481094f, 63.409157f, 66.460266f, 63.537216f, 66.653595f, 63.982765f, 66.66443f, 64.312485f, 66.7272f, 64.53998f, 66.79915f, 64.576096f, 66.89055f, 65.10138f, 67.03943f, 65.07054f, 67.076935f, 65.221375f, 67.15082f, 65.18248f, 67.30609f, 65.658875f, 67.38666f, 65.69054f, 67.39526f, 65.96138f, 67.47859f, 66.10664f, 67.576935f, 66.0397f, 67.5647f, 65.79193f, 67.65193f, 65.840546f, 67.67915f, 65.99275f, 67.64554f, 66.105255f, 67.69609f, 66.21054f, 66.44342f, 66.21054f);
            TimezoneMapper.poly[684] = new TzPolygon(67.69609f, 66.21054f, 67.793045f, 66.01805f, 67.93442f, 66.086105f, 67.96332f, 65.91081f, 67.918045f, 65.43471f, 68.01193f, 65.28499f, 68.235535f, 65.27582f, 68.38711f, 65.455215f, 68.38711f, 66.21054f);
            TimezoneMapper.poly[685] = new TzPolygon(66.44342f, 83.14958f, 66.4861f, 83.08249f, 66.610535f, 83.086655f, 66.66165f, 83.30582f, 66.69525f, 83.2836f, 66.885544f, 83.05693f, 67.23888f, 82.11943f, 67.33055f, 82.3647f, 67.42082f, 82.2336f, 67.53499f, 82.39665f, 67.59943f, 82.11415f, 67.72887f, 82.105545f, 67.77777f, 81.903046f, 67.92415f, 81.72443f, 67.96332f, 81.98888f, 67.9447f, 82.36693f, 68.177475f, 82.39276f, 68.17276f, 82.543594f, 68.25583f, 82.57361f, 68.29332f, 82.70665f, 68.38711f, 82.69428f, 68.38711f, 104.94967f, 66.44342f, 104.94967f);
            TimezoneMapper.poly[686] = new TzPolygon(68.38711f, 65.455215f, 68.556366f, 65.65555f, 68.65387f, 65.41443f, 68.80664f, 65.312195f, 68.80887f, 65.08055f, 68.893326f, 64.78943f, 68.855255f, 64.74582f, 68.87053f, 64.54999f, 69.02916f, 64.533875f, 69.25511f, 64.948105f, 69.15188f, 65.66165f, 69.120964f, 65.66165f, 69.131386f, 65.569f, 69.09887f, 65.66165f, 68.38711f, 65.66165f);
            TimezoneMapper.poly[687] = new TzPolygon(68.46683f, 82.68378f, 68.49136f, 82.68054f, 68.57832f, 82.4561f, 68.61303f, 82.68378f);
            TimezoneMapper.poly[688] = new TzPolygon(70.09844f, 82.21225f, 70.09656f, 82.22178f, 70.081024f, 82.22017f, 70.09519f, 82.20664f);
            TimezoneMapper.poly[689] = new TzPolygon(68.8072f, 82.68378f, 68.81833f, 82.5522f, 68.98776f, 82.49721f, 69.05386f, 82.316376f, 69.10832f, 82.504715f, 69.16054f, 82.46388f, 69.18498f, 81.84387f, 69.25249f, 81.75972f, 69.34665f, 81.9361f, 69.447754f, 81.68359f, 69.27582f, 81.58249f, 69.25444f, 81.44414f, 69.28804f, 81.335815f, 69.206665f, 80.95596f, 69.30504f, 80.76669f, 69.36325f, 80.14598f, 69.3461f, 79.859146f, 69.653595f, 79.50583f, 69.69693f, 79.004166f, 69.74304f, 79.13971f, 69.88165f, 78.87526f, 69.96416f, 79.187485f, 70.05165f, 79.19443f, 70.04498f, 79.34943f, 70.09844f, 79.338425f, 70.09844f, 82.68378f);
            TimezoneMapper.poly[690] = new TzPolygon(70.4892f, 80.62194f, 70.415054f, 80.79108f, 70.39101f, 80.7074f, 70.46188f, 80.57176f, 70.436554f, 80.176834f, 70.27777f, 79.88136f, 70.28055f, 79.715546f, 70.110535f, 79.49887f, 70.14888f, 79.32805f, 70.09844f, 79.338425f, 70.09844f, 77.31255f, 70.4892f, 77.31255f);
            TimezoneMapper.poly[691] = new TzPolygon(68.46683f, 82.68378f, 68.38711f, 82.69428f, 68.38711f, 82.68378f);
            TimezoneMapper.poly[692] = new TzPolygon(68.667755f, 82.68378f, 68.667755f, 83.042755f, 68.61303f, 82.68378f);
            TimezoneMapper.poly[693] = new TzPolygon(68.667755f, 83.042755f, 68.740814f, 82.80165f, 68.79881f, 82.78288f, 68.8072f, 82.68378f, 68.8072f, 83.042755f);
            TimezoneMapper.poly[694] = new TzPolygon(70.4892f, 80.629585f, 70.49536f, 80.60788f, 70.654465f, 80.75937f, 70.4892f, 80.75937f);
            TimezoneMapper.poly[695] = new TzPolygon(70.654465f, 80.75937f, 70.71675f, 80.592285f, 70.801865f, 80.69017f, 70.85477f, 80.56647f, 71.05757f, 80.55005f, 71.13388f, 79.95332f, 71.2872f, 79.532486f, 71.32083f, 79.21832f, 71.4111f, 79.14249f, 71.49414f, 79.3586f, 71.61638f, 79.380264f, 71.600266f, 79.869705f, 71.67453f, 80.13541f, 71.83147f, 80.03505f, 71.886f, 80.348785f, 72.05914f, 79.69693f, 72.03554f, 79.42276f, 72.1147f, 78.91081f, 72.20972f, 78.59721f, 72.25183f, 78.647964f, 72.25183f, 79.73688f, 72.1552f, 80.01283f, 72.09981f, 80.60555f, 72.024666f, 80.75937f);
            TimezoneMapper.poly[696] = new TzPolygon(54.413776f, 22.892805f, 54.42425f, 22.8755f, 54.374817f, 22.770086f, 54.588444f, 22.686083f, 54.72333f, 22.739584f, 54.81372f, 22.892805f);
            TimezoneMapper.poly[697] = new TzPolygon(54.360844f, 19.621935f, 54.435307f, 19.7565f, 54.320694f, 21.471472f, 54.360832f, 22.769167f, 54.425724f, 22.864944f, 54.41349f, 22.892805f, 51.977196f, 22.892805f, 51.977196f, 19.621935f);
            TimezoneMapper.poly[698] = new TzPolygon(54.445526f, 19.621935f, 54.454834f, 19.662167f, 54.444042f, 19.621935f);
            TimezoneMapper.poly[699] = new TzPolygon(55.337112f, 20.979553f, 55.337112f, 21.042534f, 55.27225f, 20.988361f, 55.277943f, 20.941528f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer8 {
        private Initializer8() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[700] = new TzPolygon(54.81372f, 22.892805f, 54.90061f, 22.854778f, 54.97272f, 22.661055f, 55.070026f, 22.585583f, 55.026196f, 22.071056f, 55.08553f, 22.034527f, 55.1945f, 21.502083f, 55.29067f, 21.38864f, 55.25011f, 21.266527f, 55.29872f, 21.321888f, 55.337112f, 21.28908f, 55.337112f, 22.892805f);
            TimezoneMapper.poly[701] = new TzPolygon(55.337112f, 30.875814f, 55.39947f, 30.935667f, 55.61661f, 30.939945f, 55.592335f, 30.774221f, 55.80739f, 30.488861f, 55.867195f, 30.254667f, 55.83139f, 30.1165f, 55.878056f, 30.013056f, 55.776222f, 29.802694f, 55.78847f, 29.668417f, 55.70397f, 29.484333f, 55.765415f, 29.366528f, 55.917583f, 29.476418f, 55.96436f, 29.425638f, 55.9905f, 29.215694f, 56.05572f, 29.095638f, 55.956806f, 28.842278f, 55.96686f, 28.73775f, 56.097332f, 28.66411f, 56.105972f, 28.411667f, 56.062363f, 28.374777f, 56.068417f, 28.298027f, 56.14761f, 28.171556f, 56.282555f, 28.241167f, 56.52261f, 28.102556f, 56.577168f, 28.133528f, 56.595055f, 28.039778f, 56.747166f, 27.908112f, 56.823776f, 27.971806f, 56.875668f, 27.846333f, 56.837166f, 27.66686f, 57.304863f, 27.851028f, 57.44086f, 27.52114f, 57.53886f, 27.551195f, 57.54839f, 27.322111f, 57.68072f, 27.393778f, 57.708637f, 27.531027f, 57.82033f, 27.542555f, 57.8585f, 27.826889f, 58.01803f, 27.615305f, 58.415916f, 27.439638f, 58.861668f, 27.548166f, 58.998028f, 27.742027f, 59.24375f, 27.90425f, 59.294224f, 28.117361f, 59.36711f, 28.208666f, 59.470806f, 28.04889f, 59.500805f, 28.062572f, 59.500805f, 40.77761f, 55.337112f, 40.77761f);
            TimezoneMapper.poly[702] = new TzPolygon(-31.3f, 125.34456f, -31.3f, 125.5f, -32.564728f, 125.5f, -32.621685f, 125.34456f);
            TimezoneMapper.poly[703] = new TzPolygon(-31.692734f, 128.99318f, -31.693056f, 128.98987f, -31.3f, 128.99028f, -31.3f, 128.99318f);
            TimezoneMapper.poly[704] = new TzPolygon(-15.026819f, 128.47098f, -15.007083f, 128.48398f, -14.984552f, 128.47098f, -14.957807f, 128.47098f, -15.011111f, 128.51138f, -14.943661f, 128.47098f, -14.782169f, 128.47098f, -14.760972f, 128.55367f, -14.803056f, 128.71587f, -14.852612f, 128.73741f, -14.820723f, 128.75717f, -14.868472f, 128.99716f, -16.0f, 129.00586f, -31.3f, 128.99318f, -31.3f, 128.47098f);
            TimezoneMapper.poly[705] = new TzPolygon(-31.3f, 128.99028f, -25.999584f, 128.9959f, -25.999556f, 129.25983f, -31.3f, 129.25983f);
            TimezoneMapper.poly[706] = new TzPolygon(-26.000744f, 129.63191f, -25.999517f, 129.25983f, -25.999517f, 129.63191f);
            TimezoneMapper.poly[707] = new TzPolygon(-25.999517f, 129.63191f, -25.99891f, 135.3378f, -31.3f, 135.3378f, -31.3f, 129.63191f);
            TimezoneMapper.poly[708] = new TzPolygon(-25.998417f, 136.3559f, -26.00377f, 136.3559f, -26.004642f, 135.3378f, -25.998417f, 135.3378f);
            TimezoneMapper.poly[709] = new TzPolygon(-25.998417f, 137.9989f, -26.002361f, 137.99889f, -26.00377f, 136.3559f, -25.998417f, 136.3559f);
            TimezoneMapper.poly[710] = new TzPolygon(-26.002375f, 138.13142f, -26.002361f, 137.99889f, -25.998417f, 137.99889f, -25.998417f, 138.13142f);
            TimezoneMapper.poly[711] = new TzPolygon(-26.002522f, 139.71837f, -26.002375f, 138.13142f, -25.998417f, 138.13142f, -25.998417f, 139.71837f);
            TimezoneMapper.poly[712] = new TzPolygon(-37.56689f, 140.96727f, -34.0415f, 140.95456f, -34.00964f, 140.96011f, -34.028492f, 140.9848f, -37.56689f, 140.9848f);
            TimezoneMapper.poly[713] = new TzPolygon(-34.028492f, 140.9848f, -34.058918f, 141.02464f, -34.06586f, 141.21536f, -34.137054f, 141.32947f, -34.129276f, 141.41014f, -34.212944f, 141.52411f, -34.10239f, 141.69902f, -34.14022f, 141.93483f, -34.10372f, 142.04134f, -34.1765f, 142.08719f, -34.152943f, 142.157f, -34.186417f, 142.2196f, -34.32897f, 142.289f, -34.340973f, 142.39677f, -34.504696f, 142.36366f, -34.599667f, 142.4718f, -34.760777f, 142.51772f, -34.789528f, 142.63806f, -34.73964f, 142.61887f, -34.729973f, 142.69888f, -34.607777f, 142.70155f, -34.55561f, 142.79669f, -34.684f, 142.88123f, -34.68611f, 143.11858f, -34.802277f, 143.348f, -34.98464f, 143.32045f, -35.196445f, 143.39581f, -35.239056f, 143.56209f, -35.337723f, 143.5653f, -35.402416f, 143.64206f, -35.39308f, 143.7525f, -35.558277f, 144.04713f, -35.780224f, 144.35097f, -36.00261f, 144.52083f, -36.119835f, 144.72563f, -36.121807f, 144.81236f, -36.065582f, 144.86548f, -36.074055f, 144.98064f, -35.99411f, 144.93127f, -35.870335f, 144.96844f, -35.83025f, 145.10672f, -35.87003f, 145.36314f, -35.833f, 145.54086f, -35.995945f, 145.81737f, -35.959446f, 145.88963f, -35.993557f, 145.9676f, -35.962723f, 145.96461f, -36.014057f, 145.99692f, -36.045887f, 146.18341f, -36.018112f, 146.25237f, -36.050804f, 146.37259f, -35.97075f, 146.42625f, -35.982666f, 146.60844f, -36.033554f, 146.65431f, -36.115723f, 146.94722f, -36.087166f, 146.95708f, -36.11453f, 147.04016f, -36.021526f, 147.10295f, -36.057472f, 147.33295f, -35.951805f, 147.40614f, -35.95128f, 147.49777f, -36.003307f, 147.55078f, -35.973026f, 147.54912f, -35.937443f, 147.70877f, -36.00325f, 147.90845f, -36.14897f, 148.0352f, -36.38636f, 148.03967f, -36.463223f, 148.12186f, -36.563026f, 148.13539f, -36.60278f, 148.21872f, -36.79525f, 148.10486f, -36.795055f, 148.19975f, -37.505333f, 149.97797f, -37.546555f, 149.81682f, -37.546555f, 149.7839f, -37.485332f, 149.81122f, -37.521362f, 149.7411f, -37.461445f, 149.68117f, -37.546555f, 149.74704f, -37.546555f, 140.9848f);
            TimezoneMapper.poly[714] = new TzPolygon(-25.998447f, 139.71837f, -25.998417f, 140.0f, -26.00264f, 141.00359f, -29.001362f, 141.00426f, -34.00964f, 140.9848f, -34.00964f, 139.71837f);
            TimezoneMapper.poly[715] = new TzPolygon(-31.494495f, 140.99994f, -31.497162f, 141.17598f, -31.583654f, 141.17598f, -31.586445f, 141.35176f, -31.533434f, 141.34618f, -31.533434f, 141.49405f, -31.605974f, 141.49963f, -31.600393f, 141.9572f, -31.97147f, 141.95161f, -31.96868f, 141.83165f, -32.292324f, 141.82048f, -32.218727f, 141.0f);
            TimezoneMapper.poly[716] = new TzPolygon(-26.002522f, 139.71837f, -25.998417f, 139.71837f, -25.998417f, 150.10522f, -28.57109f, 150.10522f, -28.64025f, 149.69597f, -28.577305f, 149.59087f, -28.584194f, 149.4872f, -28.696583f, 149.39272f, -28.840944f, 149.09442f, -29.002722f, 148.96703f, -29.001362f, 141.00426f, -26.00264f, 141.00359f);
            TimezoneMapper.poly[717] = new TzPolygon(-25.998417f, 137.99889f, -20.98889f, 137.99655f, -20.98889f, 138.00272f, -25.998417f, 138.00272f);
            TimezoneMapper.poly[718] = new TzPolygon(-28.264166f, 150.10522f, -28.264166f, 152.46895f, -28.37325f, 152.38359f, -28.469f, 152.06822f, -28.528694f, 152.01134f, -28.514278f, 151.95863f, -28.650778f, 151.99695f, -28.712944f, 152.0725f, -28.915138f, 152.00444f, -28.9155f, 151.8473f, -28.973362f, 151.81873f, -28.875195f, 151.72447f, -28.95836f, 151.54608f, -29.075834f, 151.50339f, -29.184334f, 151.34056f, -29.096806f, 151.27641f, -28.942944f, 151.27303f, -28.844084f, 151.10434f, -28.852278f, 151.04828f, -28.743305f, 151.00642f, -28.640833f, 150.7654f, -28.665638f, 150.43811f, -28.540194f, 150.28806f, -28.57109f, 150.10522f);
            TimezoneMapper.poly[719] = new TzPolygon(-28.316212f, 152.8605f, -28.315445f, 152.85594f, -28.362638f, 152.74728f, -28.276278f, 152.61552f, -28.33825f, 152.57872f, -28.265028f, 152.52817f, -28.264166f, 152.46895f, -28.264166f, 152.8605f);
            TimezoneMapper.poly[720] = new TzPolygon(-38.062523f, 140.97098f, -38.061638f, 140.96906f, -37.56689f, 140.96727f, -37.56689f, 140.97098f);
            TimezoneMapper.poly[721] = new TzPolygon(-20.98889f, 137.99655f, -17.52036f, 137.99495f, -16.54464f, 137.99603f, -16.547173f, 138.00002f, -20.98889f, 138.00002f);
            TimezoneMapper.poly[722] = new TzPolygon(-28.16011f, 152.8605f, -28.16011f, 153.483f, -28.251972f, 153.35713f, -28.253166f, 153.19269f, -28.357779f, 153.10773f, -28.316212f, 152.8605f);
            TimezoneMapper.poly[723] = new TzPolygon(-28.16011f, 153.54031f, -28.169306f, 153.55312f, -28.16011f, 153.483f);
            TimezoneMapper.poly[724] = new TzPolygon(-8.405083f, 114.60167f, -8.404528f, 114.589165f, -8.171638f, 114.430725f, -8.167027f, 114.46511f, -8.093778f, 114.44986f, -8.153472f, 114.52233f, -8.119f, 114.5862f, -8.123847f, 114.60167f);
            TimezoneMapper.poly[725] = new TzPolygon(-8.091222f, 114.511696f, -8.095139f, 114.52736f, -8.099972f, 114.52f, -8.095612f, 114.50569f);
            TimezoneMapper.poly[726] = new TzPolygon(-9.397474f, 124.0898f, -9.337306f, 124.08839f, -9.262639f, 124.18608f, -9.174916f, 124.45253f, -9.178817f, 124.46358f, -9.428965f, 124.3685f, -9.463627f, 124.2934f, -9.404364f, 124.2494f);
            TimezoneMapper.poly[727] = new TzPolygon(-9.178817f, 124.46358f, -9.180139f, 124.544556f, -9.124778f, 124.6498f, -9.049778f, 124.68939f, -8.964142f, 124.95056f, -9.037271f, 124.9653f, -9.024118f, 125.1399f, -9.109211f, 125.1659f, -9.221311f, 124.9663f, -9.274391f, 124.971f, -9.433906f, 125.128044f, -9.55475f, 124.97097f, -9.648528f, 124.9735f, -9.741694f, 124.83661f, -9.882472f, 124.73725f, -10.015417f, 124.5295f, -10.104417f, 124.48575f, -10.108471f, 124.46358f);
            TimezoneMapper.poly[728] = new TzPolygon(0.621778f, 114.026596f, 0.548834f, 113.8302f, 0.580395f, 113.7172f, 0.621778f, 113.74839f);
            TimezoneMapper.poly[729] = new TzPolygon(0.621778f, 114.09579f, 0.540448f, 114.3686f, 0.610082f, 114.6021f, 0.621778f, 114.6224f, 0.621778f, 114.99279f, 0.412744f, 115.0867f, 0.29191f, 114.8871f, 0.092438f, 114.8278f, -0.133192f, 114.8777f, -0.200595f, 114.9889f, -0.04423f, 115.141f, -0.00166f, 115.2686f, -0.073063f, 115.3156f, -0.297366f, 115.2359f, -0.589665f, 115.3609f, -0.804203f, 115.3883f, -0.880429f, 115.5407f, -1.084614f, 115.7112f, -1.26098f, 115.7566f, -1.493488f, 115.6233f, -1.475131f, 115.506f, -1.539372f, 115.3981f, -1.63432f, 115.3619f, -1.907042f, 115.375f, -2.011019f, 115.3252f, -2.299944f, 115.1564f, -2.322189f, 115.0745f, -2.541959f, 114.9682f, -2.959252f, 114.5782f, -3.460374f, 114.375244f, -3.403356f, 114.25416f, -3.332695f, 114.280304f, -3.396028f, 114.17936f, -3.375333f, 114.11428f, -3.326444f, 114.07631f, -3.275028f, 114.09808f, -3.355611f, 114.03639f, -3.360328f, 114.026596f, 0.621778f, 114.026596f);
            TimezoneMapper.poly[730] = new TzPolygon(1.706834f, 109.267975f, 1.948694f, 109.331474f, 1.993861f, 109.57422f, 2.083333f, 109.63981f, 1.984444f, 109.613525f, 1.921444f, 109.536026f, 1.810556f, 109.578f, 1.781083f, 109.67853f, 1.619639f, 109.659225f, 1.462278f, 109.80167f, 1.481583f, 109.82816f, 1.421556f, 109.83405f, 1.399861f, 109.95578f, 1.297278f, 109.97872f, 1.266194f, 110.0527f, 1.208472f, 110.058556f, 1.178694f, 110.18775f, 0.993528f, 110.274025f, 0.992917f, 110.39389f, 0.880639f, 110.47791f, 0.855222f, 110.571915f, 0.910278f, 110.63478f, 0.879194f, 110.66644f, 0.93075f, 110.76583f, 0.903083f, 110.80411f, 1.029778f, 110.907555f, 1.088417f, 111.21861f, 1.010972f, 111.38939f, 1.016178f, 111.41603f, 0.621778f, 111.41603f, 0.621778f, 109.267975f);
            TimezoneMapper.poly[731] = new TzPolygon(1.016178f, 111.41603f, 1.03025f, 111.48805f, 0.948972f, 111.54147f, 1.039889f, 111.665695f, 0.987667f, 111.82831f, 1.117444f, 111.93533f, 1.139944f, 112.145f, 1.386611f, 112.22195f, 1.441417f, 112.20089f, 1.577361f, 112.49242f, 1.537417f, 112.81275f, 1.585611f, 112.889946f, 1.578f, 112.99431f, 1.531694f, 113.06739f, 1.407028f, 112.97386f, 1.44275f, 113.10191f, 1.386222f, 113.144806f, 1.37575f, 113.3165f, 1.285583f, 113.41605f, 1.316417f, 113.5432f, 1.210528f, 113.658165f, 1.301667f, 113.809135f, 1.373306f, 113.82714f, 1.46283f, 114.085945f, 1.253112f, 114.0952f, 1.0028f, 113.7933f, 0.885854f, 113.7928f, 0.821215f, 113.8987f, 0.621778f, 113.74839f, 0.621778f, 111.41603f);
            TimezoneMapper.poly[732] = new TzPolygon(0.621778f, 114.026596f, 0.63322f, 114.0574f, 0.621778f, 114.09579f);
            TimezoneMapper.poly[733] = new TzPolygon(0.621778f, 113.74839f, 0.821215f, 113.8987f, 0.885854f, 113.7928f, 1.0028f, 113.7933f, 1.253112f, 114.0952f, 1.46283f, 114.085945f, 1.45872f, 114.09579f, 0.621778f, 114.09579f, 0.63322f, 114.0574f, 0.621778f, 114.026596f);
            TimezoneMapper.poly[734] = new TzPolygon(0.753819f, 114.09579f, 0.753819f, 114.884285f, 0.621778f, 114.56516f, 0.621778f, 114.09579f);
            TimezoneMapper.poly[735] = new TzPolygon(0.621778f, 114.97523f, 0.753819f, 114.90481f, 0.753819f, 115.6487f, 0.621778f, 115.6487f);
            TimezoneMapper.poly[736] = new TzPolygon(3.454611f, 115.64321f, 3.439639f, 115.6487f, 3.408806f, 115.61755f, 3.449111f, 115.58489f, 3.359667f, 115.54161f, 3.201972f, 115.51636f, 3.159528f, 115.561806f, 3.0435f, 115.503914f, 2.987889f, 115.40122f, 2.981778f, 115.326805f, 3.050889f, 115.28589f, 2.823333f, 115.08906f, 2.793444f, 115.1497f, 2.607278f, 115.08544f, 2.610861f, 115.16417f, 2.511139f, 115.237305f, 2.474667f, 115.12725f, 2.364528f, 115.020584f, 2.370472f, 114.958885f, 2.284444f, 114.95625f, 2.264972f, 114.8113f, 2.056944f, 114.78528f, 2.030722f, 114.8827f, 1.966833f, 114.84361f, 1.922111f, 114.87292f, 1.853083f, 114.790726f, 1.85775f, 114.71445f, 1.678833f, 114.71028f, 1.568389f, 114.61061f, 1.433472f, 114.56633f, 1.520083f, 114.39694f, 1.410389f, 114.2115f, 1.463917f, 114.143524f, 1.459076f, 114.09579f, 3.454611f, 114.09579f);
            TimezoneMapper.poly[737] = new TzPolygon(4.794528f, 117.06114f, 4.331553f, 117.06114f, 4.320472f, 117.01456f, 4.369805f, 116.906136f, 4.327917f, 116.83447f, 4.389917f, 116.747025f, 4.338333f, 116.699585f, 4.339917f, 116.62578f, 4.407889f, 116.5668f, 4.328055f, 116.535835f, 4.288278f, 116.43736f, 4.390917f, 116.350945f, 4.363083f, 116.26842f, 4.389278f, 116.163445f, 4.277083f, 116.06555f, 4.350389f, 115.99717f, 4.389806f, 115.87622f, 4.298944f, 115.86953f, 4.23125f, 115.81536f, 4.24725f, 115.76942f, 4.197139f, 115.702805f, 3.971861f, 115.63914f, 3.917917f, 115.55947f, 3.874917f, 115.61503f, 3.656139f, 115.569336f, 3.454611f, 115.64321f, 3.454611f, 115.35944f, 4.794528f, 115.35944f);
            TimezoneMapper.poly[738] = new TzPolygon(4.172614f, 117.50886f, 4.178f, 117.46486f, 4.373806f, 117.23878f, 4.331553f, 117.06114f, 4.794528f, 117.06114f, 4.794528f, 117.50886f);
            TimezoneMapper.poly[739] = new TzPolygon(46.818195f, 104.94967f, 46.818195f, 111.483795f, 46.45377f, 111.3607f, 46.22387f, 111.4972f, 46.11473f, 111.8711f, 46.03814f, 111.9127f, 45.56435f, 111.8713f, 45.15252f, 112.0857f, 45.07887f, 112.083664f, 45.074833f, 111.88825f, 44.962055f, 111.7405f, 44.72314f, 111.55981f, 44.347305f, 111.38683f, 44.063362f, 111.63167f, 43.987083f, 111.85819f, 43.80633f, 112.01172f, 43.75297f, 111.98908f, 43.695057f, 111.76058f, 43.4895f, 111.57445f, 43.34342f, 111.04703f, 43.056446f, 110.70247f, 42.929974f, 110.62195f, 42.637917f, 110.15356f, 42.624973f, 109.834724f, 42.468224f, 109.53333f, 42.42275f, 109.27172f, 42.456528f, 108.96831f, 42.387333f, 108.81142f, 42.455334f, 108.52536f, 42.41203f, 107.912415f, 42.461834f, 107.452835f, 42.407223f, 107.294914f, 42.356667f, 107.26647f, 42.259388f, 106.68364f, 41.957584f, 105.80175f, 41.738693f, 105.26542f, 41.56764f, 105.03445f, 41.64114f, 104.94967f);
            TimezoneMapper.poly[740] = new TzPolygon(46.818195f, 111.483795f, 46.818195f, 118.97833f, 46.781387f, 118.801476f, 46.804474f, 118.67039f, 46.762917f, 118.59541f, 46.808f, 118.513725f, 46.787556f, 118.27006f, 46.73714f, 118.25808f, 46.518806f, 117.817085f, 46.513943f, 117.67284f, 46.585556f, 117.600136f, 46.57964f, 117.438774f, 46.34864f, 117.36569f, 46.381f, 116.86136f, 46.31575f, 116.76439f, 46.28711f, 116.58511f, 45.950195f, 116.26195f, 45.827946f, 116.284615f, 45.703835f, 116.22339f, 45.61178f, 115.93422f, 45.438362f, 115.72103f, 45.37239f, 115.06875f, 45.453056f, 114.751915f, 45.43089f, 114.64339f, 45.374195f, 114.51303f, 45.16525f, 114.379364f, 44.951805f, 114.04869f, 44.75536f, 113.57631f, 44.870472f, 112.75808f, 45.048f, 112.4612f, 45.07887f, 112.083664f, 45.30708f, 112.0395f, 45.56435f, 111.8713f, 46.03814f, 111.9127f, 46.14936f, 111.8143f, 46.22387f, 111.4972f, 46.45377f, 111.3607f);
            TimezoneMapper.poly[741] = new TzPolygon(46.699574f, 119.27817f, 46.73436f, 119.211975f, 46.720554f, 119.12625f, 46.780445f, 119.09394f, 46.78475f, 118.97261f, 46.818195f, 118.97833f, 46.818195f, 119.27817f);
            TimezoneMapper.poly[742] = new TzPolygon(46.818195f, 111.483795f, 47.22589f, 111.6215f, 47.36932f, 111.991f, 47.762f, 112.232f, 47.804f, 112.728f, 47.91153f, 112.7357f, 46.818195f, 112.7357f);
            TimezoneMapper.poly[743] = new TzPolygon(50.60112f, 112.7357f, 49.511642f, 112.7357f, 49.54647f, 112.51317f, 49.395527f, 111.9345f, 49.404415f, 111.694916f, 49.34375f, 111.50189f, 49.373474f, 111.37967f, 49.184307f, 110.955025f, 49.14511f, 110.76628f, 49.162083f, 110.59625f, 49.258415f, 110.399475f, 49.16511f, 110.23411f, 49.22603f, 109.53964f, 49.288f, 109.48461f, 49.362305f, 109.20353f, 49.33433f, 108.55558f, 49.52875f, 108.284225f, 49.61525f, 108.025276f, 49.67514f, 108.012886f, 49.690304f, 107.93731f, 49.920223f, 107.97567f, 49.921555f, 107.85508f, 49.980007f, 107.75403f, 50.152214f, 107.7711f, 50.206657f, 107.82054f, 50.222214f, 107.93748f, 50.33721f, 107.98553f, 50.353325f, 108.0761f, 50.314438f, 108.13248f, 50.411102f, 108.20416f, 50.404434f, 108.33859f, 50.462494f, 108.43692f, 50.452217f, 108.60387f, 50.504166f, 108.68526f, 50.60112f, 108.368835f);
            TimezoneMapper.poly[744] = new TzPolygon(50.60112f, 104.94967f, 50.60112f, 108.368835f, 50.504166f, 108.68526f, 50.452217f, 108.60387f, 50.462494f, 108.43692f, 50.404434f, 108.33859f, 50.411102f, 108.20416f, 50.314438f, 108.13248f, 50.353325f, 108.0761f, 50.33721f, 107.98553f, 50.222214f, 107.93748f, 50.157494f, 107.77361f, 50.02388f, 107.79721f, 49.953415f, 107.67889f, 50.019665f, 107.14647f, 50.222168f, 106.96653f, 50.321415f, 106.75789f, 50.347057f, 106.56731f, 50.295696f, 106.2792f, 50.35103f, 106.09214f, 50.411694f, 106.0473f, 50.47922f, 105.387695f, 50.392776f, 105.11939f, 50.37192f, 104.94967f);
            TimezoneMapper.poly[745] = new TzPolygon(47.91153f, 112.7357f, 48.16006f, 112.5755f, 48.62623f, 112.5905f, 49.11525f, 112.3769f, 49.28209f, 112.4653f, 49.543068f, 112.49179f, 49.491104f, 112.7357f);
            TimezoneMapper.poly[746] = new TzPolygon(50.282917f, 112.7357f, 50.282917f, 114.34905f, 49.974777f, 113.49333f, 49.812668f, 113.198364f, 49.614223f, 113.10061f, 49.503f, 112.79095f, 49.511642f, 112.7357f);
            TimezoneMapper.poly[747] = new TzPolygon(50.072834f, 119.27817f, 49.983776f, 119.073586f, 49.925724f, 118.56264f, 49.834362f, 118.484474f, 49.824055f, 118.38678f, 49.77611f, 118.37947f, 49.50539f, 117.85455f, 49.628387f, 117.47847f, 49.631554f, 117.27428f, 49.725193f, 116.93339f, 49.83375f, 116.706696f, 49.940334f, 116.6067f, 50.03239f, 116.23392f, 49.887054f, 115.75916f, 49.902332f, 115.400696f, 50.19336f, 114.964386f, 50.282917f, 114.34905f, 50.282917f, 119.27817f);
            TimezoneMapper.poly[748] = new TzPolygon(50.282917f, 114.34905f, 50.19336f, 114.964386f, 49.902332f, 115.400696f, 49.887054f, 115.75916f, 50.03239f, 116.23392f, 49.940334f, 116.6067f, 49.839832f, 116.70028f, 48.883335f, 116.05495f, 48.79264f, 116.11097f, 48.531696f, 115.821556f, 48.270473f, 115.82608f, 48.180332f, 115.510025f, 48.0f, 115.549805f, 47.984333f, 115.462135f, 47.6855f, 115.915695f, 47.876667f, 116.25628f, 47.842472f, 116.49525f, 47.90003f, 116.80395f, 47.80975f, 117.10819f, 47.63861f, 117.386665f, 48.025444f, 117.84186f, 48.0f, 118.478615f, 47.806557f, 118.77586f, 47.70322f, 119.124054f, 47.53947f, 119.151665f, 47.54443f, 119.27817f, 46.818195f, 119.27817f, 46.818195f, 114.34905f);
            TimezoneMapper.poly[749] = new TzPolygon(50.353973f, 119.27817f, 50.38475f, 119.1163f, 50.450527f, 119.24106f, 50.60112f, 119.27817f);
            TimezoneMapper.poly[750] = new TzPolygon(59.495953f, 104.94967f, 59.46306f, 104.97554f, 59.469437f, 105.18942f, 59.363052f, 105.24887f, 59.310547f, 105.18414f, 59.26111f, 105.2961f, 59.094994f, 105.36276f, 59.04583f, 105.14388f, 58.949516f, 104.94967f);
            TimezoneMapper.poly[751] = new TzPolygon(59.845543f, 104.94967f, 59.845543f, 105.49525f, 59.83721f, 105.01915f, 59.77083f, 105.01915f, 59.746864f, 104.94967f);
            TimezoneMapper.poly[752] = new TzPolygon(59.845543f, 105.49525f, 60.109993f, 105.42082f, 60.239433f, 105.48387f, 60.296104f, 105.32249f, 60.302967f, 104.94967f, 60.302967f, 105.49525f);
            TimezoneMapper.poly[753] = new TzPolygon(60.302967f, 109.82406f, 60.226097f, 109.72638f, 60.11194f, 109.69359f, 60.028877f, 109.76944f, 59.750275f, 109.49471f, 59.645546f, 109.53387f, 59.451103f, 109.25943f, 59.416664f, 109.35582f, 59.363052f, 109.24498f, 59.31694f, 109.26054f, 59.29194f, 109.51666f, 59.06166f, 109.64055f, 59.071938f, 109.74109f, 58.978043f, 109.83028f, 59.009163f, 109.93553f, 58.97416f, 110.18193f, 59.03694f, 110.49971f, 59.191658f, 110.57887f, 59.259995f, 110.70139f, 59.208046f, 110.99693f, 59.239433f, 111.08305f, 59.183327f, 111.18221f, 59.27388f, 111.43526f, 59.2061f, 111.6461f, 59.278046f, 111.76805f, 59.271103f, 111.94193f, 59.50444f, 112.27388f, 59.41694f, 112.33415f, 59.303047f, 112.28387f, 59.305344f, 112.3867f, 52.209633f, 112.3867f, 52.240547f, 112.20888f, 52.30388f, 112.16832f, 52.283333f, 112.07471f, 51.927773f, 111.60776f, 51.78221f, 111.015f, 51.722214f, 111.00194f, 51.66916f, 110.84137f, 51.592216f, 110.86388f, 51.502777f, 110.69609f, 51.494713f, 110.54471f, 51.522217f, 110.42053f, 51.579437f, 110.39471f, 51.619987f, 110.04915f, 51.46527f, 109.6861f, 51.4161f, 109.42886f, 51.346657f, 109.37997f, 51.39138f, 109.33859f, 51.362213f, 109.25221f, 51.50666f, 109.07416f, 51.439713f, 108.92747f, 51.445824f, 108.74304f, 51.50972f, 108.59721f, 51.27083f, 108.35277f, 51.10972f, 108.57083f, 51.05916f, 108.49971f, 51.04888f, 108.33221f, 50.83394f, 108.25146f, 50.75805f, 108.06554f, 50.659157f, 108.17943f, 50.60112f, 108.368835f, 50.60112f, 105.49525f, 60.302967f, 105.49525f);
            TimezoneMapper.poly[754] = new TzPolygon(55.452045f, 112.3867f, 55.452045f, 115.92623f, 55.396942f, 115.92998f, 55.34749f, 116.11443f, 55.373604f, 116.37608f, 55.227486f, 116.46944f, 55.113884f, 116.77777f, 54.972214f, 116.87164f, 54.795547f, 116.92387f, 54.535828f, 116.72331f, 54.509438f, 116.5372f, 54.464073f, 116.52275f, 54.507217f, 116.18498f, 54.42388f, 115.67082f, 54.364716f, 115.50916f, 54.301384f, 115.49927f, 54.184433f, 115.18332f, 53.992493f, 114.97581f, 53.90277f, 114.59583f, 53.80916f, 114.50305f, 53.814713f, 114.38165f, 53.74666f, 114.2961f, 53.716934f, 113.99471f, 53.64444f, 113.97443f, 53.616432f, 113.8147f, 53.53472f, 113.89554f, 53.389717f, 113.83221f, 53.356384f, 113.94331f, 53.233047f, 113.98221f, 53.252495f, 114.04803f, 53.196655f, 114.19832f, 53.071938f, 114.12692f, 53.103607f, 114.23137f, 53.067772f, 114.32527f, 52.96721f, 114.31721f, 52.96055f, 114.22665f, 52.823883f, 114.2422f, 52.718323f, 114.1236f, 52.577217f, 113.73804f, 52.45027f, 113.57916f, 52.494156f, 113.53415f, 52.478043f, 113.27332f, 52.336655f, 112.93027f, 52.349716f, 112.53888f, 52.20694f, 112.40221f, 52.209633f, 112.3867f);
            TimezoneMapper.poly[755] = new TzPolygon(55.975216f, 115.832436f, 56.041107f, 115.75861f, 56.143223f, 115.832436f);
            TimezoneMapper.poly[756] = new TzPolygon(56.153774f, 115.832436f, 56.296944f, 115.68414f, 56.397217f, 115.6797f, 56.429672f, 115.7988f, 56.492767f, 115.67554f, 56.56527f, 115.68193f, 56.589714f, 115.46471f, 56.656097f, 115.43332f, 56.711105f, 115.58582f, 56.87249f, 115.58638f, 56.95655f, 115.7368f, 56.92063f, 115.832436f);
            TimezoneMapper.poly[757] = new TzPolygon(59.305344f, 112.3867f, 60.302967f, 112.3867f, 60.302967f, 114.998245f, 60.18055f, 114.81387f, 60.219986f, 114.69664f, 60.113884f, 114.5461f, 59.997772f, 114.56667f, 59.883606f, 114.31554f, 59.749718f, 114.17303f, 59.741104f, 114.04332f, 59.68055f, 114.04193f, 59.688324f, 113.84915f, 59.609993f, 113.7836f, 59.576385f, 113.60248f, 59.49527f, 113.60971f, 59.376656f, 113.4211f, 59.260277f, 113.46304f, 59.153046f, 113.24721f, 59.15499f, 112.91165f, 59.032494f, 112.64526f, 59.071686f, 112.57422f, 58.96118f, 112.62762f, 58.871376f, 112.51999f, 58.92305f, 112.44693f, 59.124992f, 112.44971f, 59.31082f, 112.6322f);
            TimezoneMapper.poly[758] = new TzPolygon(60.302967f, 116.7022f, 60.15777f, 116.9736f, 60.035828f, 117.0486f, 60.01944f, 117.29749f, 59.908882f, 117.05721f, 59.829437f, 117.22748f, 59.65499f, 117.20082f, 59.58721f, 117.08443f, 59.52777f, 117.2361f, 59.470825f, 117.59888f, 59.540833f, 117.75833f, 59.441658f, 117.88361f, 59.58333f, 118.05721f, 59.61055f, 118.2836f, 59.51777f, 118.39027f, 59.40999f, 118.75583f, 59.33333f, 118.75526f, 59.28611f, 118.85138f, 59.21305f, 118.70139f, 59.065544f, 118.70248f, 59.014412f, 118.84779f, 58.887497f, 118.78665f, 58.814713f, 118.89694f, 58.69554f, 118.8875f, 58.612495f, 118.76305f, 58.458885f, 119.13554f, 58.363884f, 119.06554f, 58.22267f, 119.10838f, 58.21666f, 118.80304f, 58.16916f, 118.67221f, 58.211105f, 118.48915f, 58.351105f, 118.32887f, 58.38138f, 118.20833f, 58.337494f, 118.07555f, 58.39833f, 117.98943f, 58.400543f, 117.67221f, 58.313606f, 117.47916f, 58.185547f, 117.59055f, 58.135826f, 117.57388f, 58.15721f, 117.43997f, 58.127213f, 117.40749f, 57.906654f, 117.41748f, 57.80416f, 117.08443f, 57.738884f, 117.17831f, 57.639717f, 117.13777f, 57.613052f, 117.28777f, 57.51333f, 117.25221f, 57.327774f, 117.36499f, 57.32972f, 117.44443f, 57.25444f, 117.43137f, 57.259995f, 117.54637f, 57.323326f, 117.59637f, 57.293327f, 117.79721f, 57.1911f, 117.74136f, 57.15332f, 117.59694f, 57.014442f, 117.68887f, 56.935547f, 117.56999f, 56.847214f, 117.57277f, 56.871376f, 117.49803f, 56.82583f, 117.41026f, 56.9486f, 117.3311f, 56.8011f, 117.13554f, 56.815544f, 117.02222f, 56.776382f, 117.04027f, 56.72332f, 116.79582f, 56.760826f, 116.73859f, 56.75889f, 116.55942f, 56.848602f, 116.36388f, 56.81221f, 116.12109f, 56.92063f, 115.832436f, 60.302967f, 115.832436f);
            TimezoneMapper.poly[759] = new TzPolygon(56.153774f, 115.832436f, 56.149437f, 115.83693f, 56.143223f, 115.832436f);
            TimezoneMapper.poly[760] = new TzPolygon(55.975216f, 115.832436f, 55.916664f, 115.89804f, 55.67222f, 115.84109f, 55.646103f, 115.91304f, 55.452045f, 115.92623f, 55.452045f, 115.832436f);
            TimezoneMapper.poly[761] = new TzPolygon(63.43142f, 104.94967f, 63.43142f, 106.61925f, 63.3686f, 106.73637f, 63.29666f, 106.67415f, 63.33111f, 106.44414f, 63.271378f, 106.36192f, 63.110275f, 106.40471f, 63.069717f, 106.12192f, 62.896103f, 106.21609f, 62.843605f, 106.4447f, 62.747215f, 106.52582f, 62.681664f, 106.46915f, 62.599716f, 106.81442f, 62.58277f, 106.70721f, 62.47721f, 106.71443f, 62.37249f, 106.53082f, 62.38388f, 106.42747f, 62.317215f, 106.47916f, 62.26194f, 106.31915f, 62.24166f, 106.44775f, 62.030823f, 106.41666f, 61.99749f, 106.2f, 61.843605f, 105.91277f, 61.683327f, 105.94914f, 61.617767f, 105.80276f, 61.649437f, 105.6947f, 61.565544f, 105.55748f, 61.53277f, 105.36638f, 61.41999f, 105.27582f, 61.3731f, 104.94967f);
            TimezoneMapper.poly[762] = new TzPolygon(61.281796f, 104.94967f, 61.17749f, 105.01193f, 61.177586f, 104.94967f);
            TimezoneMapper.poly[763] = new TzPolygon(63.539883f, 108.83773f, 63.599716f, 108.55859f, 63.572495f, 108.12804f, 63.62249f, 108.11638f, 63.67222f, 108.26582f, 63.808044f, 108.28859f, 63.785828f, 108.64888f, 63.858604f, 108.76944f, 63.9086f, 108.68387f, 63.98082f, 108.73831f, 64.1147f, 108.48997f, 64.23776f, 108.53804f, 64.30081f, 108.35693f, 64.24693f, 108.29776f, 64.24054f, 108.04054f, 64.16914f, 107.9686f, 64.296646f, 107.62082f, 64.24721f, 107.32332f, 64.331375f, 107.23415f, 64.41832f, 106.73442f, 64.510544f, 106.63361f, 64.44443f, 106.58582f, 64.39638f, 106.18665f, 64.42082f, 106.06609f, 64.48526f, 106.14749f, 64.5161f, 106.03888f, 64.47804f, 105.79248f, 64.658875f, 105.70387f, 64.658035f, 105.8772f, 64.79166f, 105.84721f, 64.818054f, 106.00972f, 64.889984f, 105.94275f, 64.85721f, 106.22331f, 65.028046f, 105.98248f, 65.20499f, 106.3736f, 65.20387f, 106.51166f, 65.30887f, 106.4422f, 65.39276f, 106.61165f, 65.37608f, 106.84137f, 65.49942f, 106.96527f, 65.671646f, 106.41832f, 66.004715f, 106.48888f, 66.15082f, 106.35304f, 66.18858f, 106.11748f, 66.37804f, 106.10081f, 66.47554f, 106.32693f, 66.559875f, 106.30171f, 66.559875f, 108.83773f);
            TimezoneMapper.poly[764] = new TzPolygon(63.43142f, 106.61925f, 63.49166f, 106.50694f, 63.61361f, 106.68498f, 63.698326f, 106.62831f, 63.844154f, 106.79721f, 63.98555f, 106.70277f, 63.97721f, 106.86026f, 63.88527f, 107.07138f, 63.953606f, 107.18193f, 63.95083f, 107.29109f, 63.867767f, 107.35915f, 63.85694f, 107.56276f, 63.98999f, 107.67915f, 63.99083f, 108.26193f, 64.03943f, 108.32222f, 64.20833f, 108.2211f, 64.223465f, 108.02654f, 64.26221f, 108.13998f, 64.249146f, 108.30748f, 64.30081f, 108.35693f, 64.23776f, 108.53804f, 64.1147f, 108.48997f, 63.98082f, 108.73831f, 63.9086f, 108.68387f, 63.864716f, 108.77193f, 63.785828f, 108.64888f, 63.808044f, 108.28859f, 63.67888f, 108.27026f, 63.62249f, 108.11638f, 63.567772f, 108.13136f, 63.599716f, 108.55859f, 63.539883f, 108.83773f, 63.43142f, 108.83773f);
            TimezoneMapper.poly[765] = new TzPolygon(63.539883f, 108.83773f, 63.535828f, 108.85664f, 63.55416f, 109.04332f, 63.347214f, 109.27165f, 63.36194f, 109.36304f, 62.933876f, 109.46832f, 62.82444f, 109.65054f, 62.74666f, 109.47165f, 62.583054f, 109.38693f, 62.57277f, 109.24443f, 62.5186f, 109.26694f, 62.40499f, 109.4711f, 62.41082f, 109.91388f, 62.353325f, 109.8911f, 62.287773f, 110.00082f, 62.23999f, 109.93221f, 62.163605f, 109.99971f, 61.823326f, 109.58638f, 61.713936f, 109.61263f, 61.533333f, 109.87415f, 61.327217f, 109.78749f, 61.27027f, 110.09027f, 61.15638f, 110.1797f, 61.159714f, 110.48305f, 61.086655f, 110.51999f, 60.81916f, 110.22998f, 60.691933f, 110.26776f, 60.66916f, 110.03998f, 60.57972f, 110.11775f, 60.458885f, 109.91638f, 60.403603f, 109.95193f, 60.302967f, 109.82406f, 60.302967f, 108.83773f);
            TimezoneMapper.poly[766] = new TzPolygon(66.559875f, 106.30171f, 66.69942f, 106.25999f, 66.72693f, 106.1461f, 66.798325f, 106.19525f, 66.793594f, 105.99693f, 66.89526f, 106.06833f, 66.94664f, 105.6436f, 67.018326f, 105.52083f, 67.056366f, 105.94803f, 67.168045f, 106.05386f, 67.28638f, 106.5036f, 67.243866f, 106.66776f, 67.34888f, 106.80443f, 68.86026f, 106.89082f, 69.42442f, 106.14415f, 69.572495f, 106.45471f, 69.51666f, 106.59109f, 69.51361f, 106.8147f, 69.663605f, 107.60526f, 69.67499f, 107.90221f, 69.85443f, 108.23665f, 69.841095f, 108.87248f, 69.78526f, 108.95387f, 69.7686f, 109.21443f, 70.03499f, 109.54387f, 70.07944f, 109.38109f, 70.22443f, 109.32805f, 70.265f, 109.56944f, 70.384995f, 109.64166f, 70.43414f, 110.29887f, 70.48305f, 110.13499f, 70.63611f, 110.1236f, 70.716934f, 110.51361f, 70.8022f, 110.56415f, 70.855255f, 111.36249f, 70.946045f, 111.49995f, 70.98276f, 111.9772f, 71.061646f, 112.07748f, 71.0f, 112.2561f, 71.104706f, 112.59749f, 71.16388f, 112.52193f, 71.24832f, 112.7233f, 71.40694f, 112.01109f, 72.13333f, 112.01221f, 72.14499f, 111.77499f, 72.31944f, 111.58055f, 72.37221f, 111.08832f, 72.51277f, 111.31554f, 72.644714f, 110.73027f, 72.78665f, 110.6322f, 72.81678f, 110.70135f, 72.81678f, 112.7258f, 66.559875f, 112.7258f);
            TimezoneMapper.poly[767] = new TzPolygon(60.302967f, 114.998245f, 60.429718f, 115.18915f, 60.526657f, 115.6861f, 60.40416f, 116.07805f, 60.357216f, 116.60081f, 60.302967f, 116.7022f);
            TimezoneMapper.poly[768] = new TzPolygon(72.86776f, 106.022835f, 72.99461f, 106.1634f, 72.99461f, 110.78894f, 72.94859f, 110.87553f, 72.89554f, 110.64665f, 72.81678f, 110.663086f, 72.81678f, 106.022835f);
            TimezoneMapper.poly[769] = new TzPolygon(72.99461f, 110.78894f, 73.00055f, 110.77777f, 73.03888f, 110.94165f, 73.173035f, 110.79776f, 73.18997f, 110.42303f, 73.268875f, 110.6272f, 73.31944f, 110.56667f, 73.40248f, 110.16443f, 73.40637f, 110.56415f, 73.56554f, 110.84833f, 73.58804f, 111.1086f, 73.63777f, 111.14333f, 73.71828f, 110.84167f, 73.85924f, 111.29342f, 73.7331f, 111.842766f, 72.99461f, 111.842766f);
            TimezoneMapper.poly[770] = new TzPolygon(74.324f, 111.14328f, 74.305885f, 111.21f, 74.2693f, 111.203415f, 74.314476f, 111.06358f);
            TimezoneMapper.poly[771] = new TzPolygon(46.75189f, 119.27817f, 46.75189f, 119.92431f, 46.622f, 119.774414f, 46.55064f, 119.597694f, 46.41942f, 119.513275f, 46.475304f, 119.38953f, 46.56047f, 119.4585f, 46.63361f, 119.403694f, 46.699574f, 119.27817f);
            TimezoneMapper.poly[772] = new TzPolygon(47.54443f, 119.27817f, 47.545944f, 119.31683f, 47.43264f, 119.3222f, 47.336166f, 119.47511f, 47.279057f, 119.81036f, 47.161583f, 119.8717f, 47.15453f, 119.813225f, 47.21825f, 119.762886f, 47.193054f, 119.70136f, 47.122055f, 119.84139f, 46.92317f, 119.80389f, 46.75189f, 119.92431f, 46.75189f, 119.27817f);
            TimezoneMapper.poly[773] = new TzPolygon(50.341087f, 119.27817f, 50.31686f, 119.36797f, 50.079277f, 119.29297f, 50.072834f, 119.27817f);
            TimezoneMapper.poly[774] = new TzPolygon(53.4985f, 119.27817f, 53.4985f, 122.317085f, 53.42114f, 122.07606f, 53.423138f, 121.859085f, 53.281307f, 121.2185f, 53.286415f, 120.870804f, 52.866306f, 120.28044f, 52.772194f, 120.02722f, 52.584057f, 120.061226f, 52.641304f, 120.4525f, 52.543083f, 120.72097f, 52.347694f, 120.60997f, 52.244473f, 120.75633f, 51.99789f, 120.70775f, 51.68161f, 120.17447f, 51.677166f, 120.09253f, 51.35475f, 119.92772f, 51.218304f, 119.75911f, 51.09028f, 119.74494f, 50.901028f, 119.51089f, 50.73947f, 119.48031f, 50.609695f, 119.28028f, 50.60112f, 119.27817f);
            TimezoneMapper.poly[775] = new TzPolygon(53.472027f, 122.90853f, 53.452915f, 122.83356f, 53.441666f, 122.41372f, 53.4985f, 122.317085f, 53.4985f, 122.90853f);
            TimezoneMapper.poly[776] = new TzPolygon(44.516262f, 131.15723f, 44.691555f, 131.10266f, 44.854168f, 130.95422f, 44.930195f, 131.10036f, 44.92425f, 131.27711f, 44.981888f, 131.34425f, 44.96786f, 131.49136f, 45.114193f, 131.68675f, 45.216946f, 131.67819f, 45.211918f, 131.77611f, 45.33603f, 131.89148f, 45.2825f, 131.95815f, 44.516262f, 131.95815f);
            TimezoneMapper.poly[777] = new TzPolygon(47.66551f, 131.95815f, 47.707443f, 131.69728f, 47.66064f, 131.57956f, 47.741554f, 131.44545f, 47.685276f, 131.02972f, 47.720276f, 130.9542f, 47.93211f, 130.86394f, 48.043167f, 130.6783f, 48.109333f, 130.65205f, 48.300083f, 130.82553f, 48.422222f, 130.73198f, 48.493195f, 130.7562f, 48.486084f, 130.61989f, 48.579445f, 130.6f, 48.613834f, 130.5245f, 48.849693f, 130.67595f, 48.88333f, 130.62384f, 48.983604f, 130.82526f, 48.964714f, 130.9744f, 49.240547f, 131.15665f, 49.24347f, 131.39584f, 49.42083f, 131.50333f, 49.6042f, 131.50464f, 49.6042f, 131.95815f);
            TimezoneMapper.poly[778] = new TzPolygon(49.6042f, 128.95633f, 49.604164f, 131.5047f, 49.42083f, 131.50333f, 49.24347f, 131.39584f, 49.240547f, 131.15665f, 48.964714f, 130.9744f, 48.983604f, 130.82526f, 48.852417f, 130.53055f, 48.901806f, 130.4242f, 48.867f, 130.22261f, 49.03747f, 129.92183f, 49.277832f, 129.71445f, 49.29117f, 129.5505f, 49.43161f, 129.47142f, 49.431473f, 129.38405f, 49.351387f, 129.3405f, 49.398945f, 129.20581f, 49.352806f, 129.08502f, 49.4505f, 128.99516f, 49.45486f, 128.95633f);
            TimezoneMapper.poly[779] = new TzPolygon(47.66551f, 131.95815f, 47.664196f, 131.96631f, 47.705387f, 131.99953f, 47.705166f, 132.24509f, 47.751583f, 132.31798f, 47.71522f, 132.56206f, 47.946583f, 132.66966f, 47.927166f, 132.80531f, 48.0f, 132.87659f, 48.105694f, 133.16031f, 48.074444f, 133.50095f, 48.198833f, 133.78098f, 48.1865f, 133.87009f, 48.26139f, 133.89597f, 48.278805f, 134.01086f, 48.333195f, 134.04942f, 48.385693f, 134.38928f, 48.36628f, 134.56972f, 48.275806f, 134.71184f, 47.993637f, 134.55133f, 47.729137f, 134.77391f, 47.49422f, 134.56755f, 47.443806f, 134.49353f, 47.421112f, 134.29556f, 47.323418f, 134.18124f, 47.246693f, 134.14911f, 47.11861f, 134.22145f, 47.062416f, 134.11417f, 46.65436f, 134.01472f, 46.471f, 133.85005f, 46.39339f, 133.94211f, 46.34083f, 133.86842f, 46.32247f, 133.92033f, 46.26525f, 133.90988f, 46.1465f, 133.68814f, 46.04736f, 133.73445f, 45.937946f, 133.66328f, 45.937054f, 133.6075f, 45.865696f, 133.58356f, 45.892277f, 133.52109f, 45.858696f, 133.48016f, 45.781723f, 133.49892f, 45.693027f, 133.42528f, 45.6835f, 133.47687f, 45.61264f, 133.46712f, 45.607224f, 133.40544f, 45.493168f, 133.31778f, 45.521526f, 133.2668f, 45.465195f, 133.15594f, 45.236195f, 133.08522f, 45.12125f, 133.13097f, 45.013363f, 132.942f, 45.248833f, 132.00008f, 45.2825f, 131.95815f);
            TimezoneMapper.poly[780] = new TzPolygon(54.69214f, 131.338f, 54.60972f, 131.19275f, 54.528046f, 131.22385f, 54.320274f, 131.11856f, 54.281662f, 131.05191f, 54.32749f, 130.92719f, 54.281662f, 130.74078f, 54.120544f, 130.50607f, 53.98166f, 130.4794f, 53.921104f, 130.39053f, 53.879715f, 130.44885f, 53.88999f, 130.65137f, 53.83499f, 130.84106f, 53.76277f, 130.92499f, 53.814156f, 131.05637f, 53.753883f, 131.4336f, 53.308884f, 131.53442f, 53.218323f, 131.44357f, 53.23388f, 131.86136f, 53.1661f, 131.83273f, 53.12471f, 131.89026f, 53.13174f, 131.95815f, 51.738964f, 131.95815f, 51.756943f, 131.85745f, 51.679718f, 131.7319f, 51.68721f, 131.41275f, 51.593605f, 131.3883f, 51.35833f, 131.5022f, 51.377487f, 131.33136f, 51.250275f, 131.1847f, 51.24388f, 131.06079f, 51.028328f, 130.93219f, 50.997215f, 130.7836f, 50.87638f, 130.84412f, 50.656937f, 130.64444f, 50.597214f, 130.7244f, 50.667213f, 130.75665f, 50.656937f, 130.95245f, 50.57277f, 131.01471f, 50.475548f, 130.86969f, 50.384163f, 130.92276f, 50.434563f, 131.12689f, 50.328606f, 131.17358f, 50.36944f, 131.30469f, 50.260826f, 131.32776f, 50.187492f, 131.4447f, 50.14138f, 131.46912f, 50.061935f, 131.30081f, 49.976936f, 131.29025f, 49.96305f, 131.45331f, 49.90277f, 131.50247f, 49.731102f, 131.4844f, 49.70305f, 131.35135f, 49.6042f, 131.50464f, 49.6042f, 128.95633f, 54.69214f, 128.95633f);
            TimezoneMapper.poly[781] = new TzPolygon(53.13174f, 131.95815f, 53.138603f, 132.02441f, 53.225266f, 132.11606f, 53.223602f, 132.58969f, 53.26055f, 132.64749f, 53.228043f, 132.8883f, 53.28694f, 132.93387f, 53.28972f, 133.16138f, 53.425827f, 133.21497f, 53.513054f, 133.4708f, 53.4961f, 133.53192f, 53.54583f, 133.56415f, 53.543327f, 133.64108f, 53.46666f, 133.72302f, 53.48916f, 133.80359f, 53.432495f, 134.00638f, 53.47027f, 134.21637f, 53.52083f, 134.2233f, 53.520546f, 134.43246f, 53.59305f, 134.44135f, 53.62027f, 134.5647f, 53.401657f, 134.93219f, 53.34249f, 134.80997f, 53.26777f, 134.95996f, 53.1886f, 134.85745f, 53.079163f, 134.87411f, 52.94027f, 134.65833f, 52.87027f, 134.69553f, 52.855553f, 134.6258f, 52.78666f, 134.63776f, 52.76055f, 134.71579f, 52.718597f, 134.65997f, 52.714157f, 134.7572f, 52.647217f, 134.79193f, 52.568054f, 134.64664f, 52.42666f, 134.63858f, 52.418602f, 134.54471f, 52.476654f, 134.12607f, 52.540833f, 134.00247f, 52.519157f, 133.87466f, 52.68194f, 133.3158f, 52.644714f, 133.24023f, 52.528046f, 133.28693f, 52.45388f, 133.24106f, 52.26999f, 133.43774f, 52.200546f, 133.34164f, 52.150826f, 133.00583f, 52.179718f, 132.82635f, 52.08194f, 132.58524f, 51.951385f, 132.51913f, 51.965828f, 132.40027f, 51.86444f, 132.42886f, 51.790276f, 132.2811f, 51.820274f, 132.21912f, 51.766388f, 132.07831f, 51.805824f, 132.04971f, 51.736656f, 131.97107f, 51.738964f, 131.95815f);
            TimezoneMapper.poly[782] = new TzPolygon(54.69214f, 131.338f, 54.71721f, 131.38217f, 54.723877f, 131.5611f, 54.788605f, 131.6258f, 54.794716f, 131.76721f, 54.840828f, 131.78693f, 54.893883f, 131.98303f, 54.98694f, 131.95523f, 55.045273f, 132.06192f, 55.063606f, 132.38916f, 55.19249f, 132.3533f, 55.20249f, 132.61414f, 55.36944f, 132.68137f, 55.370502f, 132.72826f, 54.69214f, 132.72826f);
            TimezoneMapper.poly[783] = new TzPolygon(55.43848f, 132.72826f, 55.514442f, 132.61441f, 55.56082f, 132.66302f, 55.678604f, 132.5422f, 55.706383f, 132.39526f, 55.70916f, 132.22498f, 55.63527f, 132.11746f, 55.658325f, 131.96109f, 55.61277f, 131.81693f, 55.658325f, 131.61191f, 55.609436f, 131.58331f, 55.647774f, 131.44553f, 55.612495f, 131.1033f, 55.677773f, 130.92303f, 55.898605f, 130.8583f, 55.936935f, 130.92609f, 56.129433f, 130.96274f, 56.22332f, 131.10358f, 56.400543f, 131.11636f, 56.498604f, 131.30664f, 56.419716f, 131.40137f, 56.48221f, 131.38971f, 56.55027f, 131.49939f, 56.501106f, 131.61911f, 56.448326f, 131.60913f, 56.454437f, 131.69998f, 56.650543f, 131.66666f, 56.715828f, 131.80359f, 56.844154f, 131.75388f, 57.037216f, 131.43692f, 57.07305f, 131.49051f, 57.164436f, 131.27664f, 57.132492f, 131.18414f, 57.233604f, 131.09329f, 57.234993f, 131.18414f, 57.30777f, 131.25885f, 57.249435f, 131.34552f, 57.241936f, 131.5072f, 57.287773f, 131.50803f, 57.290833f, 131.6008f, 57.42888f, 131.58524f, 57.50889f, 131.73746f, 57.565544f, 131.69302f, 57.580276f, 131.97662f, 57.63138f, 131.96191f, 57.649437f, 132.04776f, 57.809433f, 131.89026f, 57.844154f, 131.75247f, 57.92083f, 131.79999f, 57.995544f, 131.6094f, 58.06388f, 131.6308f, 58.163322f, 131.46024f, 58.17388f, 131.71692f, 58.016106f, 132.04608f, 58.04944f, 132.13025f, 58.114998f, 132.00247f, 58.24527f, 132.09497f, 58.237213f, 132.19635f, 58.48777f, 132.12051f, 58.4786f, 132.41885f, 58.520546f, 132.36469f, 58.641106f, 132.5708f, 58.78444f, 132.51694f, 58.888603f, 132.59164f, 58.880455f, 132.72826f);
            TimezoneMapper.poly[784] = new TzPolygon(60.423573f, 132.72826f, 58.88161f, 132.72826f, 58.891106f, 132.5994f, 58.811897f, 132.54851f, 58.78647f, 132.36191f, 58.853153f, 132.23512f, 58.972164f, 132.2273f, 59.056473f, 132.12756f, 59.156925f, 131.76276f, 59.30934f, 131.70302f, 59.977383f, 131.8623f, 59.993683f, 132.0498f, 60.271904f, 132.32619f, 60.423573f, 132.4057f);
            TimezoneMapper.poly[785] = new TzPolygon(59.38643f, 136.50017f, 59.436935f, 136.30331f, 59.42305f, 136.0794f, 59.48555f, 136.00525f, 59.53055f, 135.75415f, 59.38777f, 135.42108f, 59.109993f, 135.14636f, 59.15277f, 134.94385f, 59.125824f, 134.8472f, 59.213608f, 134.7269f, 59.14444f, 134.51886f, 59.255554f, 134.0586f, 59.23333f, 133.613f, 59.29583f, 133.53857f, 59.218597f, 133.31415f, 59.204994f, 133.10358f, 59.088882f, 132.93246f, 59.106102f, 132.83441f, 59.01333f, 132.81387f, 58.98555f, 132.87912f, 58.87555f, 132.81052f, 58.88161f, 132.72826f, 60.423573f, 132.72826f, 60.423573f, 136.50017f);
            TimezoneMapper.poly[786] = new TzPolygon(55.462055f, 132.72826f, 55.37555f, 132.9508f, 55.370502f, 132.72826f);
            TimezoneMapper.poly[787] = new TzPolygon(66.15501f, 130.29755f, 66.15501f, 136.50017f, 66.06027f, 136.1285f, 66.08903f, 136.02303f, 65.9475f, 135.92786f, 65.81186f, 135.64474f, 65.840256f, 135.4599f, 65.70684f, 135.16074f, 65.69918f, 134.9539f, 65.74037f, 134.82457f, 65.37924f, 134.34752f, 65.39023f, 134.20772f, 65.18889f, 133.59642f, 65.21689f, 133.31975f, 65.09538f, 132.86702f, 64.9171f, 132.81108f, 64.62742f, 133.06776f, 64.57495f, 132.88152f, 64.6284f, 132.24615f, 64.86056f, 132.25914f, 65.36671f, 131.81595f, 65.42907f, 131.63452f, 65.73703f, 131.60506f, 65.799904f, 131.2278f, 65.96877f, 130.95357f, 66.014114f, 130.67213f);
            TimezoneMapper.poly[788] = new TzPolygon(66.15501f, 130.29755f, 66.014114f, 130.67213f, 65.96877f, 130.95357f, 65.799904f, 131.2278f, 65.73703f, 131.60506f, 65.42907f, 131.63452f, 65.36671f, 131.81595f, 64.86056f, 132.25914f, 64.6284f, 132.24615f, 64.578224f, 132.6957f, 64.39482f, 132.7109f, 64.34079f, 132.87778f, 64.269424f, 132.71156f, 64.20828f, 132.83345f, 64.18981f, 132.72223f, 64.04089f, 132.55223f, 63.974937f, 132.6136f, 63.66612f, 132.08807f, 63.601322f, 132.43594f, 63.362488f, 132.82748f, 63.35994f, 133.12709f, 63.316563f, 133.22684f, 63.172955f, 133.11763f, 63.090965f, 133.1779f, 63.045166f, 133.32765f, 63.138554f, 133.88454f, 62.97632f, 133.985f, 62.953083f, 134.13637f, 62.829346f, 134.23943f, 62.58111f, 135.18324f, 62.644035f, 135.33667f, 62.55818f, 135.51102f, 62.432884f, 135.57101f, 62.22943f, 135.84317f, 62.122623f, 135.60516f, 62.049164f, 135.71938f, 61.692627f, 135.13535f, 61.575485f, 135.37543f, 61.411987f, 134.93289f, 61.44091f, 134.65804f, 61.74382f, 134.21272f, 61.7827f, 133.99414f, 61.800922f, 133.72069f, 61.674942f, 133.53197f, 61.633556f, 133.38971f, 61.69984f, 133.33458f, 61.55372f, 132.94032f, 61.464848f, 133.14328f, 61.339863f, 133.02492f, 60.85802f, 133.15408f, 60.775864f, 133.09413f, 60.687607f, 132.88074f, 60.62769f, 132.64537f, 60.65951f, 132.52939f, 60.423573f, 132.4057f, 60.423573f, 128.95633f, 66.15501f, 128.95633f);
            TimezoneMapper.poly[789] = new TzPolygon(66.15501f, 130.29755f, 66.22647f, 130.10754f, 66.49224f, 130.04378f, 67.027176f, 131.86784f, 67.959625f, 133.10449f, 68.41757f, 133.14119f, 68.88476f, 132.71799f, 69.03455f, 132.24672f, 69.18289f, 132.62173f, 69.25765f, 132.53914f, 69.37737f, 132.8868f, 69.48288f, 132.9109f, 69.56499f, 133.19347f, 69.635864f, 133.19354f, 69.64965f, 133.50859f, 69.72694f, 133.51804f, 66.15501f, 133.51804f);
            TimezoneMapper.poly[790] = new TzPolygon(69.72694f, 133.51804f, 69.83677f, 133.15935f, 69.95272f, 133.1719f, 70.01699f, 132.95459f, 70.22385f, 133.24931f, 70.340324f, 133.09848f, 70.412445f, 132.11574f, 70.767586f, 132.03247f, 70.88973f, 132.21536f, 71.00126f, 132.14536f, 71.03734f, 131.78189f, 71.128975f, 131.77328f, 71.61378f, 132.29047f, 71.61378f, 133.16063f, 71.47722f, 133.50146f, 71.47433f, 133.51804f);
            TimezoneMapper.poly[791] = new TzPolygon(65.59248f, 140.81937f, 65.524826f, 140.43756f, 65.57929f, 140.06163f, 65.59248f, 140.03992f);
            TimezoneMapper.poly[792] = new TzPolygon(65.59248f, 136.50017f, 65.59248f, 140.03992f, 65.57929f, 140.06163f, 65.52509f, 140.43517f, 65.50265f, 140.0352f, 65.39275f, 139.79993f, 65.255486f, 139.60999f, 64.96007f, 139.42732f, 64.82926f, 139.56506f, 64.537186f, 140.37123f, 64.38424f, 140.04431f, 64.31815f, 140.24869f, 64.21491f, 140.26498f, 64.15654f, 140.53386f, 63.97678f, 140.61996f, 63.748817f, 140.50491f, 63.69062f, 140.63506f, 63.539806f, 140.50937f, 63.52056f, 140.12444f, 63.36711f, 139.71564f, 63.164307f, 139.56796f, 62.910484f, 139.59637f, 62.677837f, 139.89401f, 62.51056f, 140.28668f, 62.058884f, 140.29303f, 61.97249f, 140.13693f, 61.975822f, 139.98914f, 61.832497f, 139.95386f, 61.65555f, 139.67221f, 61.477768f, 139.54553f, 61.448044f, 139.09692f, 61.316383f, 138.92163f, 61.311104f, 138.74579f, 61.344437f, 138.73273f, 61.271378f, 138.63776f, 61.188324f, 138.70245f, 61.099434f, 138.39694f, 61.111664f, 138.2872f, 60.99527f, 138.34442f, 60.958885f, 138.20859f, 60.885826f, 138.17996f, 60.680275f, 138.43887f, 60.494156f, 138.23886f, 60.39083f, 138.32996f, 60.231102f, 138.1994f, 59.948044f, 138.28665f, 59.83055f, 138.17108f, 59.71277f, 138.26276f, 59.684433f, 138.0336f, 59.799164f, 137.99661f, 59.75083f, 137.9483f, 59.737213f, 137.59692f, 59.64444f, 137.44302f, 59.549995f, 137.46274f, 59.55388f, 137.35245f, 59.44027f, 137.18887f, 59.430275f, 136.96301f, 59.344994f, 136.66165f, 59.38643f, 136.50017f);
            TimezoneMapper.poly[793] = new TzPolygon(62.524376f, 140.81937f, 62.576942f, 140.71884f, 62.415825f, 140.62497f, 62.43138f, 140.44302f, 62.37598f, 140.33589f, 62.51056f, 140.28668f, 62.794727f, 139.72786f, 63.04564f, 139.54764f, 63.36711f, 139.71564f, 63.52056f, 140.12444f, 63.539806f, 140.50937f, 63.616493f, 140.6313f, 63.69062f, 140.63506f, 63.748817f, 140.50491f, 63.97678f, 140.61996f, 64.15654f, 140.53386f, 64.21491f, 140.26498f, 64.31815f, 140.24869f, 64.38424f, 140.04431f, 64.537186f, 140.37123f, 64.82926f, 139.56506f, 64.96007f, 139.42732f, 65.255486f, 139.60999f, 65.50265f, 140.0352f, 65.59248f, 140.81937f);
            TimezoneMapper.poly[794] = new TzPolygon(59.340195f, 147.1658f, 59.36416f, 146.8883f, 59.41221f, 147.10245f, 59.63833f, 147.1658f);
            TimezoneMapper.poly[795] = new TzPolygon(62.523067f, 140.81937f, 62.49749f, 140.87079f, 62.487495f, 141.05276f, 62.40277f, 141.09525f, 62.440826f, 141.29413f, 62.170273f, 141.55554f, 62.079437f, 141.72913f, 62.085823f, 141.88135f, 62.028603f, 141.84607f, 62.02471f, 142.00583f, 62.09305f, 142.10162f, 62.085266f, 142.18414f, 61.86666f, 142.41693f, 61.96971f, 142.66498f, 61.90721f, 142.99164f, 62.09749f, 143.11969f, 62.1186f, 143.21579f, 62.020546f, 143.36911f, 62.028877f, 143.60385f, 61.939987f, 143.55774f, 61.869987f, 143.63498f, 61.991104f, 143.92996f, 61.8161f, 143.90915f, 61.731857f, 143.99258f, 59.63833f, 143.99258f, 59.63833f, 140.81937f);
            TimezoneMapper.poly[796] = new TzPolygon(64.957436f, 143.99258f, 64.96263f, 143.86269f, 65.18379f, 143.3194f, 65.1744f, 143.0973f, 65.38864f, 141.96028f, 65.3751f, 141.60999f, 65.50123f, 140.9545f, 65.59248f, 140.81937f, 65.59248f, 143.99258f);
            TimezoneMapper.poly[797] = new TzPolygon(61.731857f, 143.99258f, 61.726097f, 143.99829f, 61.728043f, 144.21884f, 61.855553f, 144.54108f, 61.983604f, 144.58914f, 62.071663f, 144.84775f, 61.964157f, 145.1633f, 61.994713f, 145.29248f, 61.95388f, 145.48135f, 62.06138f, 145.74551f, 62.007217f, 145.79721f, 62.025826f, 145.89609f, 61.906937f, 146.21329f, 61.631935f, 146.58887f, 61.428604f, 146.61523f, 61.405266f, 146.77277f, 61.300545f, 146.67831f, 61.10694f, 146.74939f, 61.05999f, 146.6622f, 61.001938f, 146.73523f, 60.95083f, 146.68857f, 60.938324f, 146.49106f, 60.665825f, 146.4047f, 60.73916f, 146.05746f, 60.615547f, 145.8858f, 60.558884f, 145.61801f, 60.415543f, 145.52304f, 60.394997f, 145.63025f, 60.32527f, 145.57718f, 60.193604f, 145.79663f, 60.27249f, 146.37634f, 60.12999f, 146.4483f, 60.099434f, 146.68246f, 59.988884f, 146.64194f, 59.903877f, 146.76804f, 59.948326f, 147.0022f, 59.882492f, 147.11969f, 59.74694f, 147.14859f, 59.686653f, 147.08304f, 59.63833f, 147.1658f, 59.63833f, 143.99258f);
            TimezoneMapper.poly[798] = new TzPolygon(61.731857f, 143.99258f, 64.957436f, 143.99258f, 64.94635f, 144.26941f, 64.788864f, 144.55843f, 64.66822f, 144.59041f, 64.52587f, 144.87929f, 64.44142f, 145.35893f, 64.45108f, 145.6849f, 64.38202f, 145.92056f, 64.08693f, 146.32913f, 63.938324f, 146.10025f, 63.824997f, 145.54776f, 63.66777f, 145.64471f, 63.522766f, 145.44635f, 63.196655f, 145.2472f, 62.911102f, 145.36829f, 62.771935f, 145.30664f, 62.548607f, 145.48969f, 62.49166f, 145.12079f, 62.271378f, 145.18054f, 62.2461f, 144.9744f, 62.1511f, 144.9708f, 62.092216f, 144.71469f, 62.04683f, 144.74539f, 61.983604f, 144.58914f, 61.855553f, 144.54108f, 61.728043f, 144.21884f, 61.726097f, 143.99829f);
            TimezoneMapper.poly[799] = new TzPolygon(66.15926f, 136.51685f, 66.15501f, 136.50017f, 66.15926f, 136.50017f);
        }
    }

    /* access modifiers changed from: private */
    public static class Initializer9 {
        private Initializer9() {
        }

        /* access modifiers changed from: private */
        public static void init() {
            TimezoneMapper.poly[800] = new TzPolygon(65.59248f, 147.1658f, 65.59248f, 140.03992f, 65.64624f, 139.9514f, 65.81064f, 140.20586f, 65.98445f, 139.91951f, 66.07437f, 139.92346f, 66.15324f, 139.68205f, 66.15926f, 139.67868f, 66.15926f, 147.1658f);
            TimezoneMapper.poly[801] = new TzPolygon(66.15926f, 136.51685f, 66.1574f, 137.12231f, 66.05463f, 137.57977f, 65.917145f, 138.67697f, 65.97365f, 139.12465f, 65.82388f, 139.61214f, 65.8106f, 140.2058f, 65.64624f, 139.9514f, 65.59248f, 140.03992f, 65.59248f, 136.51685f);
            TimezoneMapper.poly[802] = new TzPolygon(66.15926f, 147.1658f, 66.15926f, 139.67868f, 66.26704f, 139.61826f, 66.38628f, 139.74744f, 66.45851f, 139.97864f, 66.44834f, 140.42595f, 66.608116f, 140.45036f, 66.60954f, 140.2641f, 66.825195f, 139.8133f, 66.87523f, 139.54771f, 67.24271f, 138.9031f, 67.71401f, 140.1725f, 67.88227f, 141.2665f, 68.03984f, 141.15804f, 68.179276f, 141.24733f, 68.351616f, 141.02748f, 68.44658f, 141.20581f, 68.49372f, 141.7205f, 68.63441f, 141.95784f, 68.82753f, 141.95775f, 68.891975f, 141.74092f, 69.15593f, 141.5267f, 69.243774f, 141.70164f, 69.203636f, 142.78308f, 69.42239f, 142.59624f, 69.47241f, 142.44206f, 69.54743f, 142.4054f, 69.562706f, 142.53075f, 69.59912f, 142.08614f, 69.82309f, 142.3402f, 69.85493f, 142.15038f, 69.78259f, 141.77477f, 69.92151f, 141.3848f, 69.97314f, 141.37715f, 69.94838f, 141.08742f, 70.00751f, 140.91978f, 70.13257f, 140.91985f, 70.84606f, 142.03458f, 70.901535f, 142.34622f, 71.002235f, 142.43407f, 71.053894f, 142.63792f, 71.26982f, 142.5298f, 71.331665f, 142.56703f, 71.331665f, 147.1658f);
            TimezoneMapper.poly[803] = new TzPolygon(71.331665f, 147.1658f, 71.331665f, 142.56703f, 71.46614f, 142.64801f, 71.50847f, 142.72409f, 71.50847f, 147.1658f);
            TimezoneMapper.poly[804] = new TzPolygon(71.50847f, 142.72409f, 71.95332f, 143.52364f, 72.07826f, 143.53935f, 72.15169f, 143.7722f, 72.05268f, 144.53021f, 72.19812f, 145.1911f, 72.15847f, 145.7903f, 72.16743f, 146.87935f, 72.31842f, 147.3888f, 71.50847f, 147.3888f);
            TimezoneMapper.poly[805] = new TzPolygon(61.58536f, 162.57791f, 61.56239f, 162.66025f, 61.556557f, 162.57642f, 61.564335f, 162.51636f);
            TimezoneMapper.poly[806] = new TzPolygon(64.38693f, 163.46606f, 62.54529f, 163.46606f, 62.516335f, 163.28934f, 62.442444f, 163.25677f, 62.40736f, 163.1362f, 62.41625f, 163.22736f, 62.33089f, 163.38823f, 62.21903f, 163.07805f, 62.071445f, 163.11942f, 62.05661f, 163.20212f, 61.922333f, 163.02434f, 61.803944f, 162.98642f, 61.704582f, 163.3449f, 61.66275f, 163.34244f, 61.601696f, 163.23128f, 61.612446f, 163.1184f, 61.51064f, 163.04355f, 61.599335f, 162.85786f, 61.642082f, 162.94603f, 61.70878f, 162.84564f, 61.65975f, 162.6358f, 61.587696f, 162.77621f, 61.65569f, 162.51147f, 61.766106f, 162.41885f, 61.824997f, 162.4708f, 61.935547f, 162.30331f, 61.971657f, 162.50693f, 62.093323f, 162.5383f, 62.12471f, 162.27332f, 62.182213f, 162.15775f, 62.28305f, 162.57858f, 62.26333f, 162.67441f, 62.324997f, 162.75693f, 62.494156f, 162.77664f, 62.57833f, 162.69165f, 62.708046f, 162.82663f, 62.706383f, 162.67969f, 62.845825f, 162.54971f, 62.93499f, 162.26886f, 63.120827f, 162.37802f, 63.181107f, 162.76749f, 63.246384f, 162.81079f, 63.38777f, 162.67609f, 63.50055f, 162.97052f, 63.621933f, 162.98163f, 63.81527f, 162.71497f, 63.94027f, 162.93054f, 64.138596f, 162.7786f, 64.19803f, 162.91998f, 64.18082f, 163.15082f, 64.32222f, 163.25443f);
            TimezoneMapper.poly[807] = new TzPolygon(64.93997f, 163.46606f, 64.909706f, 163.46606f, 64.81749f, 163.35385f, 64.80138f, 163.11578f, 64.653076f, 163.04321f, 64.658875f, 162.75f, 64.74942f, 162.54193f, 64.73831f, 162.31885f, 64.82971f, 161.79776f, 64.93997f, 161.72609f);
            TimezoneMapper.poly[808] = new TzPolygon(64.38693f, 163.46606f, 64.55081f, 163.16885f, 64.715546f, 163.26443f, 64.78998f, 163.10385f, 64.81749f, 163.35385f, 64.90431f, 163.46606f);
            TimezoneMapper.poly[809] = new TzPolygon(64.93997f, 164.16776f, 64.89583f, 164.13416f, 64.86249f, 163.86551f, 64.932755f, 163.49411f, 64.909706f, 163.46606f, 64.93997f, 163.46606f);
            TimezoneMapper.poly[810] = new TzPolygon(64.93997f, 174.51361f, 61.81583f, 174.51361f, 61.809834f, 174.49472f, 61.98388f, 174.47162f, 62.093605f, 174.33136f, 62.07361f, 174.10385f, 62.18888f, 174.11523f, 62.278328f, 174.00943f, 62.33999f, 174.09525f, 62.447212f, 174.08191f, 62.436935f, 173.87024f, 62.518326f, 173.80359f, 62.53833f, 173.67831f, 62.5375f, 173.4972f, 62.391937f, 173.16443f, 62.39138f, 172.88776f, 62.32833f, 172.84634f, 62.44471f, 172.48108f, 62.4011f, 172.28748f, 62.467766f, 172.04135f, 62.41471f, 171.8569f, 62.377213f, 171.87189f, 62.366936f, 171.64053f, 62.309715f, 171.48941f, 62.361664f, 171.36386f, 62.322495f, 171.26416f, 62.37027f, 171.23053f, 62.348602f, 171.05331f, 62.292496f, 171.08829f, 62.244156f, 170.85217f, 62.311935f, 170.52469f, 62.28527f, 170.26025f, 62.54277f, 169.9783f, 62.659157f, 170.09274f, 62.654434f, 169.84302f, 62.97332f, 169.1319f, 62.941376f, 168.92053f, 63.08277f, 168.44443f, 63.19027f, 168.60358f, 63.186653f, 168.71637f, 63.241104f, 168.68552f, 63.23999f, 168.77887f, 63.300545f, 168.78552f, 63.516388f, 169.20496f, 63.57277f, 169.5022f, 63.656097f, 169.59998f, 63.824997f, 169.5397f, 63.866104f, 169.3794f, 63.951385f, 169.3533f, 63.952217f, 169.11191f, 64.02304f, 168.86136f, 64.18915f, 168.77441f, 64.21471f, 168.61551f, 64.31749f, 168.50055f, 64.35054f, 168.23773f, 64.28638f, 168.14359f, 64.28526f, 168.02637f, 64.35332f, 167.8233f, 64.34027f, 167.70691f, 64.48248f, 167.50638f, 64.490814f, 167.36969f, 64.62608f, 167.24551f, 64.52971f, 166.78748f, 64.61554f, 166.18915f, 64.55054f, 165.86414f, 64.587204f, 165.80914f, 64.615814f, 165.90637f, 64.7625f, 165.66998f, 64.74498f, 165.43246f, 64.84804f, 165.27942f, 64.85443f, 165.04471f, 64.69359f, 165.02136f, 64.68442f, 164.87466f, 64.7686f, 164.81192f, 64.93997f, 164.16776f);
            TimezoneMapper.poly[811] = new TzPolygon(45.4635f, 140.97186f, 45.42178f, 141.06985f, 45.269196f, 141.03609f, 45.462666f, 140.96729f);
            TimezoneMapper.poly[812] = new TzPolygon(65.00823f, 161.65567f, 65.13304f, 161.33414f, 65.163605f, 160.52277f, 65.51776f, 160.02747f, 65.52777f, 159.82663f, 65.74304f, 158.91193f, 65.89653f, 158.92706f, 66.21785f, 159.19452f, 66.2836f, 158.77469f, 66.44331f, 158.3533f, 66.72499f, 158.69553f, 66.79193f, 158.90332f, 66.97748f, 158.80609f, 67.05914f, 158.69498f, 67.1172f, 158.33664f, 67.26694f, 157.86746f, 67.341934f, 157.77832f, 67.441086f, 157.88748f, 67.53943f, 157.68857f, 67.7061f, 157.80414f, 67.7486f, 158.32745f, 67.83249f, 158.26025f, 67.84804f, 158.05832f, 67.91693f, 158.05719f, 67.95499f, 158.19247f, 68.07054f, 158.2547f, 68.12164f, 158.46387f, 68.14221f, 158.94302f, 68.23831f, 159.52554f, 68.20665f, 159.79166f, 68.27916f, 159.85107f, 68.29721f, 160.97745f, 68.41138f, 161.36578f, 68.38269f, 161.65567f);
            TimezoneMapper.poly[813] = new TzPolygon(65.00844f, 161.65567f, 64.994705f, 161.69052f, 64.93997f, 161.74156f, 64.93997f, 161.65567f);
            TimezoneMapper.poly[814] = new TzPolygon(68.3593f, 161.892f, 68.38269f, 161.65567f, 68.38269f, 161.892f);
            TimezoneMapper.poly[815] = new TzPolygon(68.869705f, 161.892f, 68.869705f, 162.8494f, 68.792755f, 162.73663f, 68.80554f, 162.59247f, 68.60637f, 162.72412f, 68.30554f, 162.43524f, 68.3593f, 161.892f);
            TimezoneMapper.poly[816] = new TzPolygon(68.869705f, 162.8494f, 69.0036f, 162.53082f, 69.1172f, 162.49911f, 69.19414f, 162.78247f, 69.42053f, 162.3544f, 69.61192f, 162.33026f, 69.65268f, 162.43752f, 69.61017f, 162.56009f, 69.663055f, 162.56013f, 69.63183f, 162.8494f);
            TimezoneMapper.poly[817] = new TzPolygon(4.177828f, 117.50886f, 4.177528f, 117.58581f, 4.147695f, 117.5556f, 4.147695f, 117.50886f);
            TimezoneMapper.poly[818] = new TzPolygon(4.147695f, 117.70436f, 4.165972f, 117.67956f, 4.169944f, 117.75925f, 4.167695f, 117.89528f, 4.147695f, 117.90794f);
            TimezoneMapper.poly[819] = new TzPolygon(-6.611417f, 140.84286f, -6.611417f, 141.0218f, -6.898334f, 141.0218f, -6.902972f, 140.95274f, -6.860139f, 140.95117f, -6.84525f, 140.8993f, -6.748278f, 140.8972f, -6.717889f, 140.84789f, -6.616528f, 140.87581f);
            TimezoneMapper.poly[820] = new TzPolygon(-2.598529f, 140.84286f, -2.60875f, 140.999f, -6.313056f, 141.00034f, -6.326667f, 140.96005f, -6.428722f, 140.95145f, -6.426f, 140.9159f, -6.477778f, 140.9569f, -6.557556f, 140.93031f, -6.611417f, 140.84286f);
            TimezoneMapper.poly[821] = new TzPolygon(43.399807f, 130.28809f, 43.399807f, 131.3084f, 43.20789f, 131.19078f, 43.13608f, 131.20172f, 43.020416f, 131.09659f, 42.914806f, 131.10655f, 42.916668f, 131.01016f, 42.856056f, 131.01736f, 42.871887f, 130.82124f, 42.82964f, 130.77133f, 42.810333f, 130.55356f, 42.722305f, 130.40514f, 42.679474f, 130.45967f, 42.697556f, 130.53058f, 42.622665f, 130.62303f, 42.544666f, 130.61765f, 42.535446f, 130.56122f, 42.429436f, 130.61261f, 42.440224f, 130.56479f, 42.615948f, 130.5138f, 42.54782f, 130.44197f, 42.60621f, 130.43471f, 42.60017f, 130.37976f, 42.67775f, 130.28809f);
            TimezoneMapper.poly[822] = new TzPolygon(42.678207f, 130.28809f, 42.60017f, 130.37976f, 42.60621f, 130.43471f, 42.54782f, 130.44197f, 42.615402f, 130.51442f, 42.28361f, 130.67487f, 42.25403f, 130.59209f, 42.313026f, 130.57634f, 42.32814f, 130.38567f, 42.246918f, 130.39738f, 42.179443f, 130.31131f, 42.235332f, 130.29991f, 42.22787f, 130.28809f);
            TimezoneMapper.poly[823] = new TzPolygon(43.399807f, 131.3084f, 43.4685f, 131.30078f, 43.55714f, 131.18405f, 44.079693f, 131.2931f, 44.516262f, 131.15723f, 44.516262f, 131.3084f);
            TimezoneMapper.poly[824] = new TzPolygon(49.45486f, 128.95633f, 49.478027f, 128.7499f, 49.585777f, 128.77603f, 49.58758f, 128.38194f, 49.499584f, 128.25061f, 49.614166f, 127.951805f, 49.586082f, 127.81528f, 49.66325f, 127.69586f, 49.764f, 127.664276f, 49.83464f, 127.51514f, 50.01797f, 127.48658f, 50.22547f, 127.59417f, 50.327915f, 127.333336f, 50.425194f, 127.35772f, 50.47747f, 127.28761f, 50.58328f, 127.35936f, 50.74708f, 127.294586f, 51.058f, 126.92286f, 51.188362f, 126.89792f, 51.31989f, 126.974335f, 51.310028f, 126.87319f, 51.243637f, 126.902885f, 51.255917f, 126.841446f, 51.322193f, 126.81861f, 51.343304f, 126.91503f, 51.385777f, 126.91583f, 51.42758f, 126.78953f, 51.526554f, 126.83278f, 51.595165f, 126.67594f, 51.713417f, 126.72356f, 51.942722f, 126.46186f, 52.021862f, 126.44483f, 52.033417f, 126.515724f, 52.120888f, 126.55644f, 52.206417f, 126.298805f, 52.28472f, 126.43961f, 52.30639f, 126.32839f, 52.395943f, 126.3437f, 52.468918f, 126.25847f, 52.46583f, 126.18911f, 52.535168f, 126.19392f, 52.622555f, 125.96547f, 52.763832f, 126.11278f, 52.796417f, 126.049225f, 52.760834f, 125.9677f, 52.89522f, 125.8325f, 52.8575f, 125.67139f, 52.979362f, 125.73872f, 53.07461f, 125.60047f, 53.046417f, 125.50836f, 53.10108f, 125.46272f, 53.197388f, 125.123f, 53.17378f, 124.917336f, 53.094276f, 124.875084f, 53.189056f, 124.70431f, 53.21672f, 124.43205f, 53.3655f, 124.24575f, 53.343693f, 124.115f, 53.488724f, 123.84441f, 53.48925f, 123.67783f, 53.547554f, 123.57978f, 53.495888f, 123.52433f, 53.55414f, 123.507225f, 53.496777f, 123.484726f, 53.56086f, 123.257f, 53.56086f, 128.95633f);
            TimezoneMapper.poly[825] = new TzPolygon(53.56086f, 123.257f, 53.472027f, 122.90853f, 53.56086f, 122.90853f);
        }
    }

    static TzPolygon[] initPolyArray() {
        poly = new TzPolygon[826];
        Initializer1.init();
        Initializer2.init();
        Initializer3.init();
        Initializer4.init();
        Initializer5.init();
        Initializer6.init();
        Initializer7.init();
        Initializer8.init();
        Initializer9.init();
        return poly;
    }

    /* access modifiers changed from: private */
    public static class TimezoneMapperDependencies {
        private static TzPolygon[] poly = initPolyArray();
        static String[] timezoneStrings = {"unknown", "unusedtimezone", "Pacific/Wallis", "Pacific/Pago_Pago", "America/Miquelon", "Arctic/Longyearbyen", "America/Kralendijk", "America/St_Barthelemy", "America/Guadeloupe", "America/Martinique", "America/St_Thomas", "Indian/Mayotte", "America/Cayenne", "Pacific/Noumea", "America/Puerto_Rico", "America/Marigot"};

        private TimezoneMapperDependencies() {
        }

        public static String latLngToTimezoneString(double lat, double lng) {
            return timezoneStrings[getTzInt((float) lat, (float) lng)];
        }

        private static int getTzInt(float lat, float lng) {
            if (lng < 4.268278f) {
                return call1(lat, lng);
            }
            if (lng < 104.94967f) {
                if (lng >= 45.548084f) {
                    return 1;
                }
                if (lat < 58.776028f) {
                    if (lat >= 20.265944f || lng < 44.921604f || lat >= -12.633837f || lat < -15.934506f) {
                        return 1;
                    }
                    return 11;
                } else if (lat < 64.98783f || lat < 71.09136f) {
                    return 1;
                } else {
                    if (lng < 24.703777f) {
                        return 5;
                    }
                    if (lat < 79.006165f) {
                        if (lat < 71.18811f) {
                            return 1;
                        }
                        return 5;
                    } else if (lng < 36.815277f) {
                        return 5;
                    } else {
                        return 1;
                    }
                }
            } else if (lat >= -1.419889f || lat >= -13.654695f || lng < 153.46805f || lng >= 169.22058f || lat < -22.698f || lat >= -19.103806f) {
                return 1;
            } else {
                return 13;
            }
        }

        private static int call0(float lat, float lng) {
            if (lng < -68.13299f || lng >= -56.153835f || lat >= 47.192554f) {
                return 1;
            }
            if (lat < 44.114185f) {
                if (lng < -64.55561f) {
                    if (lat >= 18.520166f) {
                        return 1;
                    }
                    if (lng < -65.24274f) {
                        return 14;
                    }
                    if (lng < -64.83363f) {
                        return 10;
                    }
                    if (lat >= 18.379963f) {
                        return 1;
                    }
                    if (lat >= 17.795403f && lng >= -64.66243f) {
                        return 1;
                    }
                    return 10;
                } else if (lat >= 18.130697f) {
                    return 1;
                } else {
                    if (lng < -61.54626f) {
                        if (lng < -62.543266f) {
                            if (lat < 17.656101f) {
                                if (lng < -62.94367f) {
                                    return 6;
                                }
                                return 1;
                            } else if (lng < -63.012993f) {
                                if (poly[0].contains(lat, lng)) {
                                    return 15;
                                }
                                return 1;
                            } else if (lat < 18.027061f) {
                                return 7;
                            } else {
                                return 8;
                            }
                        } else if (lat < 16.368206f) {
                            return 8;
                        } else {
                            return 1;
                        }
                    } else if (lat < 14.103245f) {
                        return 1;
                    } else {
                        if (lat >= 15.631809f) {
                            return 8;
                        }
                        if (lng < -61.244152f) {
                            return 1;
                        }
                        return 9;
                    }
                }
            } else if (lng >= -60.24838f && lng >= -59.692562f) {
                return 4;
            } else {
                return 1;
            }
        }

        private static int call1(float lat, float lng) {
            if (lat < 50.982723f) {
                if (lat < 12.893453f) {
                    if (lat < -25.330093f || lng >= -51.397778f || lat < -14.418878f) {
                        return 1;
                    }
                    if (lat < 7.197906f) {
                        if (lng < -81.578545f) {
                            if (lng >= -165.83275f || lat >= -11.0497f) {
                                return 1;
                            }
                            if (lng >= -171.40932f) {
                                return 3;
                            }
                            if (lng < -176.12878f) {
                                return 2;
                            }
                            return 1;
                        } else if (lng < -56.48025f || lat < -2.231925f) {
                            return 1;
                        } else {
                            if (!poly[1].contains(lat, lng) && !poly[2].contains(lat, lng)) {
                                return 1;
                            }
                            return 12;
                        }
                    } else if (lat >= 11.984823f && lng >= -68.64149f && lng < -68.19231f) {
                        return 6;
                    } else {
                        return 1;
                    }
                } else if (lng < -77.475334f) {
                    return 1;
                } else {
                    return call0(lat, lng);
                }
            } else if (lng >= -72.27274f && lng >= -51.139267f && lat >= 70.67755f && lng >= -16.850788f && lng >= -11.312319f) {
                return 5;
            } else {
                return 1;
            }
        }

        /* access modifiers changed from: private */
        public static class TzPolygon {
            float[] pts;

            TzPolygon(float... D) {
                this.pts = D;
            }

            /* JADX INFO: Multiple debug info for r4v3 float: [D('i' int), D('yi' float)] */
            public boolean contains(float testy, float testx) {
                boolean inside = false;
                float[] fArr = this.pts;
                int n = fArr.length;
                float yj = fArr[n - 2];
                float xj = fArr[n - 1];
                int i = 0;
                while (i < n) {
                    float[] fArr2 = this.pts;
                    int i2 = i + 1;
                    float yi = fArr2[i];
                    int i3 = i2 + 1;
                    float xi = fArr2[i2];
                    boolean z = false;
                    if ((yi > testy) != (yj > testy) && testx < ((((xj - xi) * (testy - yi)) / (yj - yi)) + xi) - 1.0E-4f) {
                        if (!inside) {
                            z = true;
                        }
                        inside = z;
                    }
                    xj = xi;
                    yj = yi;
                    i = i3;
                }
                return inside;
            }
        }

        /* access modifiers changed from: private */
        public static class Initializer1 {
            private Initializer1() {
            }

            /* access modifiers changed from: private */
            public static void init() {
                TimezoneMapperDependencies.poly[0] = new TzPolygon(18.130354f, -63.039284f, 18.060326f, -63.012993f, 18.05223f, -63.09742f, 18.065594f, -63.152767f);
                TimezoneMapperDependencies.poly[1] = new TzPolygon(4.897255f, -52.19365f, 4.89354f, -52.17941f, 4.887943f, -52.18621f, 4.89456f, -52.197155f);
                TimezoneMapperDependencies.poly[2] = new TzPolygon(5.776496f, -53.87309f, 5.561208f, -53.485634f, 5.501859f, -53.17656f, 5.395037f, -52.95788f, 5.468842f, -53.01439f, 5.443977f, -52.90986f, 5.158696f, -52.633358f, 5.123224f, -52.68202f, 5.124388f, -52.587074f, 4.991682f, -52.40259f, 4.91801f, -52.363705f, 4.942519f, -52.287174f, 4.694666f, -52.010468f, 4.508541f, -51.944534f, 4.660141f, -51.907917f, 4.608623f, -51.79938f, 4.41854f, -51.7434f, 4.387076f, -51.689564f, 4.314177f, -51.710365f, 4.172863f, -51.61395f, 4.049279f, -51.658463f, 3.996106f, -51.753742f, 3.889529f, -51.795406f, 3.707367f, -51.969246f, 3.29574f, -52.192142f, 3.170184f, -52.326225f, 2.912198f, -52.38153f, 2.645522f, -52.555515f, 2.523372f, -52.554726f, 2.373743f, -52.672802f, 2.285073f, -52.853474f, 2.189559f, -52.905342f, 2.169751f, -52.987366f, 2.224297f, -53.083473f, 2.222989f, -53.188385f, 2.292224f, -53.273476f, 2.27231f, -53.397404f, 2.332068f, -53.471794f, 2.286815f, -53.694283f, 2.363611f, -53.789696f, 2.196785f, -54.078136f, 2.127094f, -54.114597f, 2.178963f, -54.18949f, 2.162159f, -54.332806f, 2.280185f, -54.54251f, 2.340313f, -54.432972f, 2.43486f, -54.441803f, 2.499406f, -54.362827f, 2.776747f, -54.20699f, 2.963052f, -54.15801f, 3.146966f, -54.210514f, 3.308968f, -54.06686f, 3.601097f, -53.977493f, 4.046188f, -54.362503f, 4.143001f, -54.32117f, 4.189804f, -54.389164f, 4.903628f, -54.474247f, 5.239499f, -54.300774f, 5.357258f, -54.14074f, 5.543812f, -54.014526f, 5.742936f, -53.953545f);
            }
        }

        static TzPolygon[] initPolyArray() {
            poly = new TzPolygon[3];
            Initializer1.init();
            return poly;
        }
    }

    public static List<String> getAffiliatedIslands() {
        ArrayList<String> affiliatedIslands = new ArrayList<>(Arrays.asList(TimezoneMapperDependencies.timezoneStrings));
        affiliatedIslands.add("Pacific/Guam");
        affiliatedIslands.add("Pacific/Saipan");
        return affiliatedIslands;
    }
}
