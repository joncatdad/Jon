import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;

public class DancingBugRunner{
    public static void main(String[] args){
        int[] turns = { 1, 0, 0, 0, 1, 0, 0, 3, 4, 4, 0, 0, 1, 0, 3, 2, 0, 7, 0, 0, 0, 3, 2, 1 };
        ActorWorld world = new ActorWorld();
        DancingBug bug = new DancingBug(turns);
        bug.setColor(Color.GREEN);
        world.add(new Location(5, 5), bug);
        world.show();
    }
}
