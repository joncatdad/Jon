import info.gridworld.actor.Bug;

public class ZBug extends Bug {
    private int steps;
    private int sideLength;
    private int stage; // 0 = moving right, 1 = diagonal, 2 = moving left
    public ZBug(int length) {
        steps = 0;
        sideLength = length;
        stage = 0;
        setDirection(90); // Start facing east
    }
    public void act() {
        if (steps < sideLength && canMove()){
            move();
            steps++;
        }
        else if (stage == 0){ // First turn: down-right diagonal
            setDirection(225);
            steps = 0;
            stage++;
        }
        else if (stage == 1){ // Second turn: left
            setDirection(90);
            steps = 0;
            stage++;
        }
    }
}
