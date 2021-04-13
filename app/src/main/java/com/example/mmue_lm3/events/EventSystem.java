/**
 * System for handling Events
 *
 * @author Mathias Schwengerer
 */

package com.example.mmue_lm3.events;

import com.example.mmue_lm3.interfaces.Event;
import com.example.mmue_lm3.interfaces.EventListener;

import java.util.Stack;

public class EventSystem {

    private static final EventSystem eventSystem = new EventSystem();

    public static void subscribe(EventListener listener) {
        eventSystem.addListener(listener);
    }

    public static void unsubscribe(EventListener listener) {
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


