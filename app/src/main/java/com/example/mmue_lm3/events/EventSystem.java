

package com.example.mmue_lm3.events;

import android.util.Log;

import com.example.mmue_lm3.GameSurfaceView;
import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;

import java.util.Stack;

/**
 * System for handling Events
 *
 * @author Mathias Schwengerer
 */
public class EventSystem {
    private static final String TAG = EventSystem.class.getSimpleName();

    private static final EventSystem eventSystem = new EventSystem();

    public static void subscribe(EventListener listener) {
        Log.d(TAG, String.format("%s subscribed", listener));
        eventSystem.addListener(listener);
    }

    public static void unsubscribe(EventListener listener) {
        Log.d(TAG, String.format("%s unsubscribed", listener));
        eventSystem.removeListener(listener);
    }

    public static void onEvent(Event event) {
        eventSystem.event(event);
    }


    private final Stack<EventListener> listeners;

    private EventSystem() {
        listeners = new Stack<>();
    }

    private void addListener(EventListener listener) {
        listeners.add(listener);
    }

    private void removeListener(EventListener listener) {
        listeners.remove(listener);
    }

    private void event(Event event) {
        for (EventListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}


