import java.awt.Point;
import java.util.LinkedList;
/**
 *	A SinglyLinkedList of Coordinate objects representing
 *	a snake on a two-dimensional grid.
 *
 *	@author	Jonathan Chen
 *	@since	May 5, 2025
 */
public class Snake extends SinglyLinkedList<Coordinate>{
	/**	Constructor for making a Snake that is 5 grids high facing north.
	 *	Places the snake head at Coordinate location and the tail below.
	 *	Precondition: To place the Snake, the board must have at
	 *				least location.getRow() + 4 more rows.
	 */
    private LinkedList<Point> body;
    private String direction = "RIGHT";
    public Snake(){
        body = new LinkedList<>();
        body.add(new Point(5, 5));
        body.add(new Point(4, 5));
        body.add(new Point(3, 5));
    }
    public LinkedList<Point> getBody(){
        return body;
    }
    public void setDirection(String dir){
        if ((dir.equals("LEFT") && !direction.equals("RIGHT")) ||
            (dir.equals("RIGHT") && !direction.equals("LEFT")) ||
            (dir.equals("UP") && !direction.equals("DOWN")) ||
            (dir.equals("DOWN") && !direction.equals("UP"))){
            direction = dir;
        }
    }
    public String getDirection(){
        return direction;
    }
    public Point getHead(){
        return body.getFirst();
    }
    public void move(boolean grow){
        Point head = getHead();
        Point newHead = switch (direction){
            case "UP" -> new Point(head.x, head.y - 1);
            case "DOWN" -> new Point(head.x, head.y + 1);
            case "LEFT" -> new Point(head.x - 1, head.y);
            default -> new Point(head.x + 1, head.y);
        };
        body.addFirst(newHead);
        if(!grow){
			body.removeLast();
		}
    }
    public boolean checkSelfCollision(){
        Point head = getHead();
        return body.stream().skip(1).anyMatch(p -> p.equals(head));
    }
}
