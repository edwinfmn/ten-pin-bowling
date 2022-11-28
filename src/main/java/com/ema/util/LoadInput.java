package com.ema.util;

import com.ema.exception.InvalidPinKnockedException;
import com.ema.model.Attempt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;

public class LoadInput {

    private Logger logger = Logger.getLogger(LoadInput.class.getName());

    private ArrayList<Attempt> attempts = new ArrayList<>();

    public ArrayList<Attempt> readFile(String filename) {
        logger.info("> Start reading file");
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(filename);
        InputStreamReader sr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        try {
            BufferedReader reader = new BufferedReader(sr);
            for (String line; (line = reader.readLine()) != null;) {
                String[] data = line.split(" ");
                Attempt a = new Attempt();
                a.playerName = data[0];
                a.pinsKnocked = validateScore(data[1]);
                attempts.add(a);
            }
        } catch (IOException ex) {
            logger.severe(ex.getMessage());
            ex.printStackTrace();
            return null;
        } catch (InvalidPinKnockedException ie) {
            logger.severe("Invalid data on input file, please check your data and try again.");
            logger.severe(ie.getMessage());
            return null;
        }

        return attempts;
    }

    public String validateScore(String score) throws InvalidPinKnockedException {

        if(score.equals("F")) {
            return "F";
        } else if(score.matches("^\\d+$")) {
            if (Integer.parseInt(score) > 10)
                throw new InvalidPinKnockedException("Invalid Score " + score);
            return score;
        } else {
            throw new InvalidPinKnockedException("Invalid Score " + score);
        }
    }
}
