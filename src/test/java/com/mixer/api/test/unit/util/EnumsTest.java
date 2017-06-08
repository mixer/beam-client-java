package com.mixer.api.test.unit.util;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.util.Enums;
import org.junit.Assert;
import org.junit.Test;

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
