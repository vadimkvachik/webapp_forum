package com.vpd.courseproject.forum.utils.api;

import java.util.Date;

public interface IFormatter {

    String formatDateWithTime(Date date);

    String formatDateWithoutTime(Date date);

    String formatTextForPreview(String text, int size);
}
