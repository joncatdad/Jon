import info.gridworld.actor.Critter;
import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import java.awt.Color;
import java.util.ArrayList;

public class Coyote extends Critter{
    private int steps;
    private static final int MAX_STEPS = 5;
    public Coyote(){
        setColor(null);
        steps = 0;
        setDirection((int)(Math.random() * 8) * 45); // Random direction
    }
    public void act(){
        if(getGrid() == null){
			return;
		}
        if(steps >= MAX_STEPS || !canMove()){
            // Reset and change direction if max steps are reached or can't move
            steps = 0;
            setDirection((int)(Math.random() * 8) * 45);
        }
        else{
            Location next = getLocation().getAdjacentLocation(getDirection());
            Actor neighbor = getGrid().get(next);
            if(neighbor instanceof Boulder){
                // Boulder explodes and Coyote is removed
               ((Boulder) neighbor).act();
                removeSelfFromGrid();
                return;
            }
            else if(canMove()){
                moveTo(next);
                steps++;
            }
        }
        // Drop a Stone every 5 steps
        if(steps == 0 && getGrid() != null){
            for(int dir = 0; dir < 360; dir += 45){
                Location stoneLoc = getLocation().getAdjacentLocation(dir);
                if(getGrid().isValid(stoneLoc) && getGrid().get(stoneLoc) == null){
                    new Stone().putSelfInGrid(getGrid(), stoneLoc);
                    break;
                }
            }
        }
    }
    private boolean canMove(){
        Location next = getLocation().getAdjacentLocation(getDirection());
        return getGrid().isValid(next) && getGrid().get(next) == null;
    }
}
