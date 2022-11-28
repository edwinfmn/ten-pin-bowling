package com.ema;

import com.ema.exception.InvalidPinKnockedException;
import com.ema.model.Frame;
import com.ema.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

public class UtilsTest {

    Logger logger = LoggerFactory.getLogger(UtilsTest.class);

    @Test
    public void testGetFrameValue() {
        logger.info(() -> "Start Test - Frame Unit Score");
        Frame f = new Frame();
        f.setFirst("F");
        f.setSecond(2);

        Assertions.assertEquals(2, Utils.getFrameValue(f));
    }

    @Test
    public void testThrowExceptionGetFrameValue() {
        logger.info(() -> "Start Test - Throws exception");
        Frame f = new Frame();
        f.setFirst("A");
        f.setSecond(2);

        Throwable exception = Assertions.assertThrows(NumberFormatException.class, () -> {
            Utils.getFrameValue(f);
        });

        Assertions.assertEquals("For input string: \"A\"", exception.getMessage());

    }
}
