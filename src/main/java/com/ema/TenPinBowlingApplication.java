package com.ema;

import com.ema.service.IGame;
import com.ema.service.impl.IGameImpl;


public class TenPinBowlingApplication {

    private static IGame IGame = new IGameImpl();

    public static void main(String[] args) {

        IGame.startGame("bowling-game.txt");

    }
}
