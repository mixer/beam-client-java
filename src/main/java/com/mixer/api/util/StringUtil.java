package com.mixer.api.util;


import java.util.Iterator;

public class StringUtil {
    //XXX: Java 8 has a built in way of doing this, We still support Java 7.
    /**
     * Join an Iterable of String into one string with a separator
     * @param source
     * @param separator
     */
    public static String join( Iterable<String> source, String separator ) {

        Iterator<String> iterator = null;
        if(source == null) {
            return "";
        }
        
        iterator = source.iterator();
        if(!(iterator.hasNext())) {
            return "";
        }

        StringBuilder builder = new StringBuilder(iterator.next());
        while (iterator.hasNext()) {
            builder.append(separator).append(iterator.next());
        }
        return builder.toString();
    }
}
