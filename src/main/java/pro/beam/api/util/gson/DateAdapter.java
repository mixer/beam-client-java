package pro.beam.api.util.gson;

import com.google.common.collect.ImmutableList;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * DateAdapter allows multiple time formats to be serialized and deserialized in
 * correspondence with Google's GSON library. It makes use of a defaultFormat which
 * is what all dates serialize to, and a <code>List</code> of <code>DateFormat</code>
 * which will be attempted until one is successful (or an error is returned).
 */
public class DateAdapter extends TypeAdapter<Date> {
    /**
     * The list of all date formats to try in deserializing a Date.
     */
    protected final List<DateFormat> formats;

    /**
     * The <code>DateFormat</code> to use when serializing a Date.
     */
    protected final DateFormat defaultFormat;

    /**
     * Default constructor for the DateAdapter type.
     *
     * @param formats The <code>List</code> of <code>DateFormats</code> to use when
     *                deserializing JSON-encoded Dates.
     * @param defaultFormat The <code>DateFormat</code> with which to serialize all
     *                      outgoing <code>DateFormat</code>s.
     */
    public DateAdapter(List<DateFormat> formats, DateFormat defaultFormat) {
        this.formats = formats;
        this.defaultFormat = defaultFormat;
    }

    /**
     * Implements the <code>abstract write</code> method from TypeAdapter. Uses the
     * <code>defaultFormat</code> to serialize the date, and writes it as a JSON value.
     *
     * @param w The <code>JsonWriter</code> to write to.
     * @param date The date to encode.
     * @throws IOException If a write error occurred.
     */
    @Override public void write(JsonWriter w, Date date) throws IOException {
        w.value(this.defaultFormat.format(date));
    }

    /**
     * Implements the <code>abstract read</code> method from the TypeAdapter. Iterates
     * through the list of DateFormats (excluding the defaultFormat) and attempts to de-
     * serialize the date in-order. If none succeed, an IOException will be thrown.
     *
     * @param r The <code>JsonReader</code> to deserialize from.
     * @return The derserialized date.
     * @throws IOException See above.
     */
    @Override public Date read(JsonReader r) throws IOException {
        String str = r.nextString();

        for (DateFormat format : this.formats) {
            try {
                return format.parse(str);
            } catch (ParseException ignored) {  }
        }

        throw new IOException("beam: unable to parse date ("+str+")");
    }

    /**
     * Returns a DateAdapter that is compliant with the public V1 Beam API.
     *
     * It uses a simple date format in the default, but ISO8601 as well.
     *
     * @return A v1-compliant DateAdapter.
     */
    public static DateAdapter v1() {
        DateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return new DateAdapter(
                ImmutableList.of(defaultFormat, newFormat),
                defaultFormat
        );
    }
}
