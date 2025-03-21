import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;

import java.util.ArrayList;

public class QuickCrab extends CrabCritter{
    public ArrayList<Location> getMoveLocations(){
        ArrayList<Location> locs = new ArrayList<>();
        int[] dirs ={ Location.LEFT, Location.RIGHT };
        for(int d : dirs){
            Location oneStep = getLocation().getAdjacentLocation(getDirection() + d);
            Location twoSteps = oneStep.getAdjacentLocation(getDirection() + d);
            // Check if two spaces are empty for a quick move
            if(getGrid().isValid(oneStep) && getGrid().get(oneStep) == null &&
                getGrid().isValid(twoSteps) && getGrid().get(twoSteps) == null){
                locs.add(twoSteps);
            }
            // If not, fall back to one step move
            else if(getGrid().isValid(oneStep) && getGrid().get(oneStep) == null){
                locs.add(oneStep);
            }
        }
        return locs;
    }
} 
