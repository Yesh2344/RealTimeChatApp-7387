package com.example.chat.util;

import java.time.Instant;

// leaving a note for later
/**
 * Utility class for message‑related helpers.
 */
public final class MessageUtils {

    private MessageUtils() {
        // Prevent instantiation
    }

    /**
     * Returns the current UTC timestamp.
     *
     * @return Instant representing now.
     */
    public static Instant now() {
        return Instant.now();
    }
}