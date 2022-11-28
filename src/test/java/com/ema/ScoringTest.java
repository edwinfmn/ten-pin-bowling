package com.ema;

import com.ema.service.IGame;
import com.ema.service.impl.IGameImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ScoringTest {

    Logger logger = LoggerFactory.getLogger(ScoringTest.class);

    @Test
    public void testPrintPerfectGame() {
        logger.info(() -> "Start Test - Perfect Game - 300 as final score");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        PrintStream old = System.out;
        System.setOut(ps);

        IGame game = new IGameImpl();
        game.startGame("perfect-game.txt");

        System.out.flush();
        System.setOut(old);

        Assertions.assertTrue(baos.toString().contains("300"));
    }

    @Test
    public void testPrintAllFoulGame() {
        logger.info(() -> "Start Test - All Foul Game - 0 as final score");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        PrintStream old = System.out;
        System.setOut(ps);

        IGame game = new IGameImpl();
        game.startGame("all-foul-game.txt");

        System.out.flush();
        System.setOut(old);

        Assertions.assertTrue(baos.toString().contains("0"));
    }
}
