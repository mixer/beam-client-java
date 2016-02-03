package pro.beam.api.test.unit.util;

import com.google.gson.annotations.SerializedName;
import org.junit.Assert;
import org.junit.Test;
import pro.beam.api.util.Enums;

public class EnumsTest {
    enum EnumFixture { @SerializedName("WOOT") FOO, BAR, }

    @Test public void itReturnsSerializedNames() {
        String name = Enums.serializedName(EnumFixture.FOO);

        Assert.assertEquals("WOOT", name);
    }

    @Test public void itReturnsNullWithoutSerializedNames() {
        String name = Enums.serializedName(EnumFixture.BAR);

        Assert.assertNull(name);
    }
}
