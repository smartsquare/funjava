package funjava.block3;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Calendar;

public class CalendarVsLocalDateExamples {
    @Test
    public void modifyDate() {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_MONTH, -1);
    }

    @Test
    public void modifyLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);

        Assert.assertNotEquals(yesterday, now);
    }
}
