package com.mixer.api.resource.constellation;

import com.google.gson.annotations.SerializedName;
import com.mixer.api.resource.constellation.events.LiveEvent;

public abstract class AbstractConstellationEvent<T extends AbstractConstellationEvent.EventData> extends AbstractConstellationDatagram {
    public AbstractConstellationEvent() {
        this.type = Type.EVENT;
    }

    public EventType event;
    public T data;

    public static abstract class EventData {}
    public static enum EventType {
        @SerializedName("live") LIVE (LiveEvent.class);

        private final Class<? extends AbstractConstellationEvent> correspondingClass;

        private EventType(Class<? extends AbstractConstellationEvent> correspondingClass) {
            this.correspondingClass = correspondingClass;
        }

        public static EventType fromSerializedName(String name) {
            if (name == null) return null;

            for (EventType type : EventType.values()) {
                try {
                    String serializedName = type.getClass().getField(type.name())
                                                           .getAnnotation(SerializedName.class).value();
                    if (name.equals(serializedName)) {
                        return type;
                    }
                } catch (NoSuchFieldException e) {
                    return null;
                }
            }

            return null;
        }

        public Class<? extends AbstractConstellationEvent> getCorrespondingClass() {
            return this.correspondingClass;
        }
    }
}
