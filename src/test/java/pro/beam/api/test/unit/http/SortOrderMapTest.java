package pro.beam.api.test.unit.http;

import org.junit.Assert;
import org.junit.Test;
import pro.beam.api.http.SortOrderMap;
import pro.beam.api.response.channels.ShowChannelsResponse;

public class SortOrderMapTest {
    @Test public void itBuildsAValidOrderParameter() {
        SortOrderMap<ShowChannelsResponse.Attributes,ShowChannelsResponse.Ordering> map = new SortOrderMap<>();
        map.put(ShowChannelsResponse.Attributes.VIEWERS_TOTAL, ShowChannelsResponse.Ordering.ASCENDING);
        map.put(ShowChannelsResponse.Attributes.FOLLOWERS, ShowChannelsResponse.Ordering.DESCENDING);
        String res = map.build();
        Assert.assertEquals("viewersTotal:asc,followers:desc",res);
    }
}
