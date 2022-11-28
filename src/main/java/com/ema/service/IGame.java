package com.ema.service;

import com.ema.model.Frame;

import java.util.Map;

public interface IGame {

    static final int startingFrame = 1;

    static final int STRIKE = 10;

    void startGame(String inputFile);

    Frame checkRoll(Map<Integer, Frame> frameMap, int roll);

    void calculateScore(Map<Integer, Frame> frameMap);

}
