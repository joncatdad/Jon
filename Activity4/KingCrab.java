import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class KingCrab extends CrabCritter{
    public void processActors(ArrayList<Actor> actors){
        for(Actor a : actors){
            Location loc = a.getLocation();
            Location moveToLoc = getMoveLocationAwayFrom(loc);
            if(moveToLoc != null){
                a.moveTo(moveToLoc);
            }
            else{
                a.removeSelfFromGrid(); // Remove if no valid moves
            }
        }
    }
    private Location getMoveLocationAwayFrom(Location loc){
        int direction = loc.getDirectionToward(getLocation()) + Location.HALF_CIRCLE;
        ArrayList<Location> locs = getGrid().getEmptyAdjacentLocations(loc);
        for(Location neighborLoc : locs){
            if(neighborLoc.getDirectionToward(loc) == direction){
                return neighborLoc;
            }
        }
        return locs.isEmpty() ? null : locs.get(0);
    }
} 
