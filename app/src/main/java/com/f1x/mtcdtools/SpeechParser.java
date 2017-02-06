package com.f1x.mtcdtools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by f1x on 2017-02-06.
 */

public class SpeechParser {
    public List<String> parse(List<String> texts, String separator) {
        List<String> extractedObjectsNames = new ArrayList<>();

        for(String text : texts) {
            if(!separator.isEmpty()) {
                parseText(extractedObjectsNames, text, separator);
            } else if(!contains(extractedObjectsNames, text)) {
                extractedObjectsNames.add(text);
            }
        }

        return new ArrayList<>(extractedObjectsNames);
    }

    private void parseText(List<String> output, String text, String separator) {
        String[] parts = text.split(separator);

        for(String part : parts) {
            String trimmedPart = part.trim();

            if(!contains(output, trimmedPart)) {
                output.add(trimmedPart);
            }
        }
    }

    private boolean contains(List<String> list, String item) {
        for(String value : list) {
            if(value.equalsIgnoreCase(item)) {
                return true;
            }
        }

        return false;
    }
}
