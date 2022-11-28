package com.ema.service;

import com.ema.model.Frame;

import java.util.Map;

public interface IScoring {

    static final int maxNumberOfFrames = 10;

    static final String separator = "\t";
    static final String newLine = "\n";
    static final StringBuilder scoring = new StringBuilder();

    void presentScoring(Map<String, Map<Integer, Frame>> data);

    void addHeader(String label);

    void addPinfalls(Map<Integer, Frame> frameMap);

    void addScore(Map<Integer, Frame> frameMap);
}
