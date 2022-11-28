package com.ema;

import com.ema.model.Frame;
import com.ema.service.IGame;
import com.ema.service.impl.IGameImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GameTest {

    Logger logger = LoggerFactory.getLogger(GameTest.class);

    @Test
    public void testCheckStrikeRoll() {
        logger.info(() -> "Start Test - Validate set frame Strike");
        Frame f = new Frame();
        f.setNumber(1);f.setFirst("4");f.setSecond(5);

        Map<Integer, Frame> frameMap = new HashMap<>();
        frameMap.put(1, f);

        IGame game = new IGameImpl();
        Frame result = game.checkRoll(frameMap, 10);

        Assertions.assertTrue(result.isStrike());
    }

    @Test
    public void testCheckSpareRoll() {
        logger.info(() -> "Start Test - Validate set frame Spare");
        Frame f = new Frame();
        f.setNumber(1);f.setFirst("4");

        Map<Integer, Frame> frameMap = new HashMap<>();
        frameMap.put(1, f);

        IGame game = new IGameImpl();
        Frame result = game.checkRoll(frameMap, 6);

        Assertions.assertTrue(result.isSpare());
    }
}
