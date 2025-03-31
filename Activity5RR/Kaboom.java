
import info.gridworld.actor.Actor;

public class Kaboom extends Actor{
    private int lifetime;
    private static final int THRESHOLD = 3;
    public Kaboom(){
        setColor(null); 
        this.lifetime = THRESHOLD;
    }
    public void act(){
        lifetime--;
        if(lifetime <= 0){
            removeSelfFromGrid();
        }
    }
}
