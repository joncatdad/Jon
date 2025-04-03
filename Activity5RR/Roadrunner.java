
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.util.Random;
import info.gridworld.actor.Actor;

public class Roadrunner extends Actor {
    private Random rand = new Random();
    public Roadrunner() {
        setColor(null);
        setDirection(Location.NORTH);
    }
    public void act() {
        if (getGrid() == null || getLocation() == null) return; // Prevent errors
        Location moveLoc = getMoveLocation();
        if (moveLoc != null) {
            makeMove(moveLoc);
        }
    }
    public Location getMoveLocation() {
        ArrayList<Location> locs = new ArrayList<>();
        if (getGrid() == null || getLocation() == null) return null; // Check for null grid
        Location current = getLocation();
        int direction = getDirection();
        Location next = current;
        boolean stopMoving = false;
		for (int i = 1; i <= 3 && !stopMoving; i++) {
		    next = next.getAdjacentLocation(direction);
		    if (!getGrid().isValid(next)) {
		        stopMoving = true;
		    } else {
		        Actor neighbor = getGrid().get(next);
		        if (neighbor == null || neighbor instanceof Boulder || neighbor instanceof Coyote) {
		            locs.add(next);
		        } else if (neighbor instanceof Stone || neighbor instanceof SickCoyote || neighbor instanceof Kaboom) {
		            stopMoving = true; // Stops further movement
		        }
		    }
		}
        if (locs.isEmpty()) {
		    return null;
		} else {
		    return locs.get(rand.nextInt(locs.size()));
		}

    }
    public void makeMove(Location loc) {
        if (loc == null || getGrid() == null) return;
        Actor neighbor = getGrid().get(loc);

        if (neighbor instanceof Boulder) {
            getGrid().remove(loc);
            getGrid().put(loc, new Kaboom());
            getGrid().remove(getLocation()); // Remove Roadrunner
            return; // Prevent further movement
        }
        else if (neighbor instanceof Coyote) {
            getGrid().remove(neighbor.getLocation()); // Remove Coyote
            moveTo(loc);

            ArrayList<Location> emptyLocs = getGrid().getEmptyAdjacentLocations(loc);
            if (!emptyLocs.isEmpty()) {
                getGrid().put(emptyLocs.get(rand.nextInt(emptyLocs.size())), new SickCoyote());
            }
        }
        else {
            moveTo(loc);
        }
    }
}
