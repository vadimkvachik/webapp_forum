package com.vpd.courseproject.forum.utils;

import com.vpd.courseproject.forum.utils.api.IEncoder;

import java.nio.charset.StandardCharsets;

public class FromISO88591toUTF8Encoder implements IEncoder {
    private static FromISO88591toUTF8Encoder instance = new FromISO88591toUTF8Encoder();
    private FromISO88591toUTF8Encoder() {}

    public static FromISO88591toUTF8Encoder getInstance() {
        return instance;
    }

    public String encode(String text) {
        byte[] bytes = text.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
