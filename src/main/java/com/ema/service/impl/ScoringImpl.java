package com.ema.service.impl;

import com.ema.model.Frame;
import com.ema.service.IScoring;

import java.util.Map;

public class ScoringImpl implements IScoring {

    @Override
    public void presentScoring(Map<String, Map<Integer, Frame>> data) {
        addHeader("Frames");

        data.entrySet().forEach(p -> {
            scoring.append(newLine).append(p.getKey()).append(newLine);
            addPinfalls(p.getValue());
            addScore(p.getValue());
        });

        // and finally show the magic
        System.out.println(scoring.toString());
    }


    @Override
    public void addHeader(String label) {
        scoring.append(label).append(separator).append(separator);
        int i = 1;
        while (i < maxNumberOfFrames) {
            scoring.append(i).append(separator).append(separator);
            i++;
        }
        scoring.append(maxNumberOfFrames);
    }

    @Override
    public void addPinfalls(Map<Integer, Frame> frameMap) {
        scoring.append("Pinfalls").append(separator);
        frameMap.entrySet().forEach(f -> {
            int first = 0;
            if(f.getValue().getFirst() != null && !f.getValue().getFirst().equals("F"))
                first = Integer.parseInt(f.getValue().getFirst());

            if(f.getKey() == 10) {
                if (f.getValue().getFirst() == null)
                    scoring.append(separator);
                else if (f.getValue().getFirst().equals("F"))
                    scoring.append("F").append(separator);
                else if (first == 10)
                    scoring.append("X").append(separator);
                else if (first < 10)
                    scoring.append(f.getValue().getFirst()).append(separator);

                if (f.getValue().getSecond() == 10)
                    scoring.append("X").append(separator);
//                else if (f.getValue().getSecond().equals("F"))
//                    scoring.append("F").append(separator);
                else if (f.getValue().getSecond() < 10)
                    scoring.append(f.getValue().getSecond()).append(separator);

                if (f.getValue().getLast() == 10)
                    scoring.append("X").append(separator);
//                else if (f.getValue().getLast().equals("F"))
//                    scoring.append("F").append(separator);
                else if (f.getValue().getLast() < 10)
                    scoring.append(f.getValue().getLast()).append(separator);

                scoring.append(newLine);
            } else {
                if (f.getValue().getFirst() == null)
                    scoring.append(separator);
                else if (f.getValue().getFirst().equals("F"))
                    scoring.append("F").append(separator);
                else if (first >= 0)
                    scoring.append(f.getValue().getFirst()).append(separator);

                if (f.getValue().getSecond() == 10)
                    scoring.append("X").append(separator);
                else if (f.getValue().getFirst() != null && first + f.getValue().getSecond() == 10)
                    scoring.append("/").append(separator);
                else if (first + f.getValue().getSecond() < 10)
                    scoring.append(f.getValue().getSecond()).append(separator);

            }
        });
    }

    @Override
    public void addScore(Map<Integer, Frame> frameMap) {
        scoring.append("Score").append(separator).append(separator);
        frameMap.entrySet().forEach(f -> {
            scoring.append(f.getValue().getScore()).append(separator);
            if(f.getKey() < 10)
                scoring.append(separator);

        });
    }
}
