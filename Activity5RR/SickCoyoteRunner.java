
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

public class SickCoyoteRunner{
    public static void main(String[] args){
        BoundedGrid<Actor> mygrid = new BoundedGrid<>(5, 5);
        ActorWorld world = new ActorWorld(mygrid);
        world.add(new Location(0, 0), new SickCoyote(15));
        world.add(new Location(1, 1), new SickCoyote(20));
        world.add(new Location(2, 2), new SickCoyote(10));
        world.add(new Location(3, 3), new SickCoyote(5));
        world.show();
    }
}
