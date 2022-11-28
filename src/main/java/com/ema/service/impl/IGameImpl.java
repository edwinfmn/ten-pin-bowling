package com.ema.service.impl;

import com.ema.model.Attempt;
import com.ema.model.Frame;
import com.ema.service.IGame;
import com.ema.service.IScoring;
import com.ema.util.LoadInput;
import com.ema.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IGameImpl implements IGame {

    IScoring scoring = new ScoringImpl();

    Map<String, Map<Integer, Frame>> LINES = new HashMap<>();

    @Override
    public void startGame(String inputFile) {

        /* Read Input */
        LoadInput loadInput = new LoadInput();
        ArrayList<Attempt> data = loadInput.readFile(inputFile);

        // define how many players are
        ArrayList<Attempt> players = Utils.removeDuplicates(data);
        players.forEach(p -> {
            Map<Integer, Frame> f = new HashMap<>();
            LINES.put(p.getPlayerName(), f);
        });

        data
            .stream()
            .forEach( a -> {

                int roll = 0;
                if(!a.getPinsKnocked().equals("F"))
                    roll = Integer.parseInt(a.getPinsKnocked());

                Map<Integer, Frame> frameMap = LINES.get(a.getPlayerName());

                // get previous frame and validate previous roll
                Frame f = checkRoll(frameMap, roll);

                frameMap.put(f.getNumber(), f);

                // calculate score - Traditional scoring
                calculateScore(frameMap);
            });

        // at the end print scoring table
        scoring.presentScoring(LINES);
    }


    @Override
    public Frame checkRoll(Map<Integer, Frame> frameMap, int roll) {

        Frame frame = new Frame();

        // if it is first frame
        if(frameMap.isEmpty()) {
            frame.setNumber(1);
            if (roll == 10) {
                frame.setSecond(roll);
                frame.setStrike(true);
            }
            else {
                frame.setFirst(String.valueOf(roll));
            }
            return frame;
        }

        Frame lastFrame = frameMap.get(frameMap.size());
        // if it is last frame then
        if(frameMap.size() == 10) {
            if(lastFrame.getSecond() == null)
                lastFrame.setSecond(roll);
            else if(lastFrame.getLast() == null)
                lastFrame.setLast(roll);
            return lastFrame;

        } else if( lastFrame.getFirst() != null && lastFrame.getSecond() == null ) {
            // if previous frame is strike/spare
            lastFrame.setSecond(roll);
            int first = 0;
            if(lastFrame.getFirst() != null && !lastFrame.getFirst().equals("F"))
                first = Integer.parseInt(lastFrame.getFirst());

            if(first + roll == 10)
                lastFrame.setSpare(true);
            return lastFrame;
        } else {
            frame.setNumber(frameMap.size() + 1);
            if (roll > 9) {
                if(frameMap.size() + 1 == 10)
                    frame.setFirst(String.valueOf(roll));
                else
                    frame.setSecond(roll);
                frame.setStrike(true);
            } else {
                frame.setFirst(String.valueOf(roll));
            }
            return frame;
        }

    }

    @Override
    public void calculateScore(Map<Integer, Frame> frameMap) {

        int prevScore, beforeScore = 0;

        Frame currentFrame = frameMap.get(frameMap.size());
        int f = 0;
        if(currentFrame.getFirst() != null && !currentFrame.getFirst().equals("F"))
            f = Integer.parseInt(currentFrame.getFirst());
        // if it is first frame
        if(frameMap.size() == 1 && currentFrame.getSecond() != null) {
            currentFrame.setScore(Utils.getFrameValue(currentFrame));
        } else if (frameMap.size() < 10) { // from 2nd frame til 9th
            if(currentFrame.getSecond() != null ) {
                Frame prevFrame = frameMap.get(frameMap.size() - 1);
                prevScore = prevFrame.getScore();
                if(prevFrame.isStrike()) {
                    if(frameMap.size() > 2) {
                        Frame beforeFrame = frameMap.get(frameMap.size() - 2);
                        if(beforeFrame.isStrike()) {
                            if(currentFrame.isStrike())
                                beforeScore = beforeFrame.getScore() + Utils.getFrameValue(currentFrame);
                            else
                                beforeScore = beforeFrame.getScore() + f;
                            beforeFrame.setScore(beforeScore);

                            prevFrame.setScore(beforeScore +
                                    Utils.getFrameValue(prevFrame) +
                                    Utils.getFrameValue(currentFrame) );

                            currentFrame.setScore(beforeScore +
                                    Utils.getFrameValue(prevFrame) +
                                    Utils.getFrameValue(currentFrame)+
                                    Utils.getFrameValue(currentFrame) );
                        } else {
                            prevFrame.setScore(prevScore + Utils.getFrameValue(currentFrame));
                            currentFrame.setScore(prevScore +
                                    Utils.getFrameValue(currentFrame) +
                                    Utils.getFrameValue(currentFrame));
                        }
                    } else {
                        prevFrame.setScore(prevScore + Utils.getFrameValue(currentFrame));
                        currentFrame.setScore(prevScore +
                                Utils.getFrameValue(currentFrame) +
                                Utils.getFrameValue(currentFrame));
                    }

                } else if(prevFrame.isSpare()) {
                    prevFrame.setScore(prevScore + f);
                    currentFrame.setScore(prevScore + f + Utils.getFrameValue(currentFrame));
                } else {
                    currentFrame.setScore(prevScore + Utils.getFrameValue(currentFrame));
                }
            }
        } else { // last Frame, doesn't calculate until last roll
            if(currentFrame.getLast() != null ) {
                Frame prevFrame = frameMap.get(frameMap.size() - 1);
                prevScore = prevFrame.getScore();
                if(prevFrame.isStrike()) {
                    Frame beforeFrame = frameMap.get(frameMap.size() - 2);
                    if(beforeFrame.isStrike()) {
                        if(currentFrame.isStrike())
                            beforeScore = beforeFrame.getScore() + f;
                        beforeFrame.setScore(beforeScore);

                        prevFrame.setScore(beforeScore +
                                Utils.getFrameValue(prevFrame) +
                                Utils.getFrameValue(currentFrame) );

                        currentFrame.setScore(beforeScore +
                                Utils.getFrameValue(prevFrame) +
                                Utils.getFrameValue(currentFrame)+
                                Utils.getLastFrameValue(currentFrame) );
                    } else {
                        prevFrame.setScore(prevScore + Utils.getFrameValue(currentFrame));
                        currentFrame.setScore(prevScore +
                                Utils.getFrameValue(currentFrame) +
                                Utils.getLastFrameValue(currentFrame));
                    }
                } else if (prevFrame.isSpare()) {
                    prevFrame.setScore(prevScore + f);
                    currentFrame.setScore(prevScore + f +
                            Utils.getLastFrameValue(currentFrame));
                } else {
                    currentFrame.setScore(prevScore + Utils.getLastFrameValue(currentFrame));
                }
            }
        }

    }
}
