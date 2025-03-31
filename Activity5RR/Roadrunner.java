// Roadrunner.java
import info.gridworld.actor.Critter;
import info.gridworld.grid.Location;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Random;
import info.gridworld.actor.Actor;

public class Roadrunner extends Critter {
    private Random rand = new Random();

    public Roadrunner() {
        setColor(null);
        setDirection(Location.NORTH);
    }

    @Override
    public ArrayList<Actor> getActors() {
        return new ArrayList<Actor>(); // Roadrunner doesn't process actors
    }

    @Override
    public ArrayList<Location> getMoveLocations() {
        ArrayList<Location> locs = new ArrayList<>();
        Location current = getLocation();
        int direction = getDirection();

        Location next = current;
        for (int i = 1; i <= 3; i++) {
            next = next.getAdjacentLocation(direction);
            if (!getGrid().isValid(next)) break;

            Actor neighbor = getGrid().get(next);
            if (neighbor == null || neighbor instanceof Boulder || neighbor instanceof Coyote) {
                locs.add(next);
            }
            if (neighbor instanceof Stone || neighbor instanceof SickCoyote || neighbor instanceof Kaboom) {
                break;
            }
        }
        return locs;
    }
    public Location selectMoveLocation(ArrayList<Location> locs) {
        if (locs.isEmpty()) return getLocation();
        return locs.get(rand.nextInt(locs.size()));
    }

    @Override
    public void makeMove(Location loc) {
        if (loc.equals(getLocation())) return;

        Actor neighbor = getGrid().get(loc);
        if (neighbor instanceof Boulder) {
            getGrid().remove(loc);
            getGrid().put(loc, new Kaboom());
            getGrid().remove(getLocation()); // Remove Roadrunner
        }
        else if (neighbor instanceof Coyote) {
            Location oldLoc = getLocation();
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
