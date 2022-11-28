package com.ema.model;

public class Attempt {

    public String playerName;

    public String pinsKnocked;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPinsKnocked() {
        return pinsKnocked;
    }

    public void setPinsKnocked(String pinsKnocked) {
        this.pinsKnocked = pinsKnocked;
    }

    @Override
    public boolean equals(final Object o) {
        if(o instanceof Attempt){
            Attempt a = (Attempt) o;
            return this.playerName.equals(a.getPlayerName());
        } else
            return false;
    }
}
