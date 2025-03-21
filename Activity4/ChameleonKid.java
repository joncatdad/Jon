import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class ChameleonKid extends ChameleonCritter{
    public ArrayList<Actor> getActors(){
        ArrayList<Actor> actors = new ArrayList<>();
        // Get locations directly in front and behind
        Location front = getLocation().getAdjacentLocation(getDirection());
        Location back = getLocation().getAdjacentLocation(getDirection() + Location.HALF_CIRCLE);
        // Add actors if they exist
        if(getGrid().isValid(front)){
            Actor frontActor = getGrid().get(front);
            if(frontActor != null){
                actors.add(frontActor);
            }
        }
        if(getGrid().isValid(back)){
            Actor backActor = getGrid().get(back);
            if(backActor != null){
                actors.add(backActor);
            }
        }
        return actors;
    }
} 
