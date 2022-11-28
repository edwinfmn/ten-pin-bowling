package com.ema;

import com.ema.exception.InvalidPinKnockedException;
import com.ema.util.LoadInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;


public class LoadInputTest {

    Logger logger = LoggerFactory.getLogger(LoadInputTest.class);

    @Test
    public void testWrongScoreInputException() {
        logger.info(() -> "Start Test - Wrong Score Input Exception");
        LoadInput loadInput = new LoadInput();
        Throwable exception = Assertions.assertThrows(InvalidPinKnockedException.class, () -> {
            loadInput.validateScore("77");
        });

        Assertions.assertEquals("Invalid Score 77", exception.getMessage());
    }

    @Test
    public void testWrongInputFile() {
        logger.info(() -> "Start Test - Wrong Input File");
        LoadInput loadInput = new LoadInput();

        Assertions.assertNull(loadInput.readFile("wrong-input.txt"));
    }

}
