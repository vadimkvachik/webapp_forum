package com.vpd.courseproject.forum.utils;

import com.vpd.courseproject.forum.utils.api.IFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter implements IFormatter {

    private static final Formatter instance = new Formatter();

    private Formatter() {
    }

    public static Formatter getInstance() {return instance; }

    public String formatDateWithTime(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date);
    }

    public String formatDateWithoutTime(Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    public String formatTextForPreview(String text, int size) {
        if (text.length() < size) {
            return text.replace("<br>", " ");
        } else {
            String cutText = text.substring(0, size).replace("<br>", " ");
            int lastSpace = cutText.lastIndexOf(" ");
            if (lastSpace != -1) {
                return cutText.substring(0, lastSpace) + "...";
            } else {
                return cutText + "...";
            }
        }
    }
}


