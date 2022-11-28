package com.ema.util;

import com.ema.model.Attempt;
import com.ema.model.Frame;

import java.util.ArrayList;

public class Utils {

    public static ArrayList<Attempt> removeDuplicates(ArrayList<Attempt> data) {
        ArrayList<Attempt> result = new ArrayList<>();
        data.forEach(p -> {
            if(!result.contains(p))
                result.add(p);
        });
        return result;
    }

    public static int getFrameValue(Frame frame) {
        int f, s;
        f = s = 0;
        if(frame.getFirst() != null && !frame.getFirst().equals("F"))
            f = Integer.parseInt(frame.getFirst());

        if(frame.getSecond() != null)
            s = frame.getSecond();

        return f+s;
    }

    public static int getLastFrameValue(Frame frame) {
        int f, s, t;
        f = s = t = 0;
        if(frame.getFirst() != null && !frame.getFirst().equals("F"))
            f = Integer.parseInt(frame.getFirst());

        if(frame.getSecond() != null)
            s = frame.getSecond();

        if(frame.getLast() != null)
            t = frame.getLast();

        return f+s+t;
    }
}
