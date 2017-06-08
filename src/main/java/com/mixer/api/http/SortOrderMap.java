package com.mixer.api.http;

import com.mixer.api.util.Enums;
import com.mixer.api.util.StringUtil;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class SortOrderMap<K,V> extends LinkedHashMap<Enum,Enum> {
    public String build() {
        String[] result = new String[this.size()];
        int i = 0;
        for (Map.Entry<Enum, Enum> entry : this.entrySet()) {
            result[i] = Enums.serializedName(entry.getKey()) + ":" +Enums.serializedName(entry.getValue());
            i++;
        }
        return StringUtil.join(Arrays.asList(result),",");
    }
}
