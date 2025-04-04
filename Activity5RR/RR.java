import info.gridworld.actor.Critter;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.util.Random;

public class RR extends Critter{
    private Random rand = new Random();
    public RR(){
        setColor(null); // Roadrunner is invisible
        setDirection(Location.NORTH); // Always faces north at start
    }
    // RR doesn't care about nearby actors
    public ArrayList<Actor> getActors(){
        return new ArrayList<>();
    }
    public void processActors(ArrayList<Actor> actors){
        // Do nothing
    }
    // Get all empty or bumpable locations within 3 steps in a straight line
    public ArrayList<Location> getMoveLocations(){
        ArrayList<Location> locs = new ArrayList<>();
        if(getGrid() == null || getLocation() == null) return locs;
        Location current = getLocation();
        int direction = getDirection();
        Location next = current;
        boolean stopMoving = false;
        for(int i = 1; i <= 3 && !stopMoving; i++){
            next = next.getAdjacentLocation(direction);
            if(!getGrid().isValid(next)){
                stopMoving = true;
            }
            else{
                Actor neighbor = getGrid().get(next);
                if(neighbor == null || neighbor instanceof Boulder || neighbor instanceof Coyote){
                    locs.add(next);
                }
                else if(neighbor instanceof Stone || neighbor instanceof SickCoyote || neighbor instanceof Kaboom){
                    stopMoving = true;
                }
            }
        }
        return locs;
    }
    // Pick one of the valid locations at random
    public Location selectMoveLocation(ArrayList<Location> locs){
        if(locs.isEmpty()) return getLocation();
        return locs.get(rand.nextInt(locs.size()));
    }
    // Special interaction when moving
    public void makeMove(Location loc){
        if(loc == null || getGrid() == null) return;
        Actor neighbor = getGrid().get(loc);
        if(neighbor instanceof Boulder){
            getGrid().remove(loc);
            getGrid().put(loc, new Kaboom());
            getGrid().remove(getLocation()); // Remove the RR
        }
        else if(neighbor instanceof Coyote){
            getGrid().remove(neighbor.getLocation());
            moveTo(loc);

            ArrayList<Location> emptyLocs = getGrid().getEmptyAdjacentLocations(loc);
            if(!emptyLocs.isEmpty()){
                getGrid().put(emptyLocs.get(rand.nextInt(emptyLocs.size())), new SickCoyote());
            }
        }
        else{
            moveTo(loc);
        }
    }
}
