package de.skyslycer.connect4.frontend.console;

import de.skyslycer.connect4.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TranslationHandler {

    private final Map<Message, String> messages = new HashMap<>();

    public void loadMessages(Language language) throws IOException {
        var properties = new Properties();
        properties.load(Main.class.getClassLoader().getResourceAsStream(language.getLangFile()));
        for (Message message : Message.values()) {
            messages.put(message, properties.getProperty(message.getKey(),
                    "Couldn't find " + message.getKey() + " in file " + language.getLangFile() + "!"));
        }
    }

    public String getMessage(Message message, Object... placeholders) {
        return String.format(messages.get(message), placeholders);
    }

    public void print(Message message, Object... placeholders) {
        System.out.println(getMessage(message, placeholders));
    }

    public enum Message {
        PLAYER_WON("player-won"),
        CHIP_LOCATION("chip-location"),
        FIELD_SIZE_HORIZONTAL("field-size.horizontal"),
        FIELD_SIZE_VERTICAL("field-size.vertical"),
        ERR_NOT_A_NUMBER("error.not-a-number"),
        ERR_NOT_IN_FIELD("error.not-in-field"),
        ERR_COLUMN_FULL("error.column-full"),
        ERR_TOO_SMALL("error.too-small");

        private final String key;

        Message(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }
    }

    public enum Language {
        EN,
        DE;

        public String getLangFile() {
            return "lang_" + this.name().toLowerCase() + ".properties";
        }
    }

}
