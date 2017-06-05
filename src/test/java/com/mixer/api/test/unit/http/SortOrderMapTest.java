package com.mixer.api.test.unit.http;

import com.mixer.api.http.SortOrderMap;
import com.mixer.api.response.channels.ShowChannelsResponse;
import org.junit.Assert;
import org.junit.Test;

public class SortOrderMapTest {
    @Test public void itBuildsAValidOrderParameter() {
        SortOrderMap<ShowChannelsResponse.Attributes,ShowChannelsResponse.Ordering> map = new SortOrderMap<>();
        map.put(ShowChannelsResponse.Attributes.VIEWERS_TOTAL, ShowChannelsResponse.Ordering.ASCENDING);
        map.put(ShowChannelsResponse.Attributes.FOLLOWERS, ShowChannelsResponse.Ordering.DESCENDING);
        String res = map.build();
        Assert.assertEquals("viewersTotal:asc,followers:desc",res);
    }
}
