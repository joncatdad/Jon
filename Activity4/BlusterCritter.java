import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

public class BlusterCritter extends Critter{
    private static final double DARKENING_FACTOR = 0.15;
    private static final double BRIGHTENING_FACTOR = 0.15;
    private int courage;
    public BlusterCritter(int courage){
        this.courage = courage;
    }
    public void processActors(ArrayList<Actor> actors){
        int neighborCount = actors.size();
        Color c = getColor();

        if(neighborCount < courage){
            // Brighten if fewer neighbors than courage level
            int red = Math.min((int)(c.getRed() *(1 + BRIGHTENING_FACTOR)), 255);
            int green = Math.min((int)(c.getGreen() *(1 + BRIGHTENING_FACTOR)), 255);
            int blue = Math.min((int)(c.getBlue() *(1 + BRIGHTENING_FACTOR)), 255);
            setColor(new Color(red, green, blue));
        }
        else{
            // Darken if neighbors exceed courage level
            int red = Math.max((int)(c.getRed() *(1 - DARKENING_FACTOR)), 0);
            int green = Math.max((int)(c.getGreen() *(1 - DARKENING_FACTOR)), 0);
            int blue = Math.max((int)(c.getBlue() *(1 - DARKENING_FACTOR)), 0);
            setColor(new Color(red, green, blue));
        }
    }
    public ArrayList<Actor> getActors(){
        return getGrid().getNeighbors(getLocation());
    }
} 
