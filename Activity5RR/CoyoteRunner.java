
import info.gridworld.actor.Actor;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;
import info.gridworld.actor.ActorWorld;

public class CoyoteRunner{
    public static void main(String[] args){
        BoundedGrid<Actor> mygrid = new BoundedGrid<Actor>(10,10);
        ActorWorld world = new ActorWorld(mygrid);
        // Place two Coyotes at random locations
        for (int i = 0; i < 2; i++){
            int row = (int) (Math.random() * 10);
            int col = (int) (Math.random() * 10);
            while (mygrid.get(new Location(row, col)) != null){
                row = (int) (Math.random() * 10);
                col = (int) (Math.random() * 10);
            }
            world.add(new Location(row, col), new Coyote());
        }
        world.show();
    }
}
