import java.util.Scanner;

public class Stylus implements Boardable{
	private Board board;
	private Scanner input = new Scanner(System.in);
	
	public Stylus(Board board)
	{
		this.board = board;
	}
	
	public boolean move()
	{
		String userIn;
		userIn = input.next();
		if(userIn.compareTo("q") == 0)
		{
			board.move(Direction.UP_LEFT, this);
			return true;
		}if(userIn.compareTo("w") == 0)
		{
			board.move(Direction.UP, this);
			return true;
		}if(userIn.compareTo("e") == 0)
		{
			board.move(Direction.UP_RIGHT, this);
			return true;
		}if(userIn.compareTo("a") == 0)
		{
			board.move(Direction.LEFT, this);
			return true;
		}if(userIn.compareTo("d") == 0)
		{
			board.move(Direction.RIGHT, this);
			return true;
		}if(userIn.compareTo("z") == 0)
		{
			board.move(Direction.DOWN_LEFT, this);
			return true;
		}if(userIn.compareTo("x") == 0)
		{
			board.move(Direction.DOWN, this);
			return true;
		}if(userIn.compareTo("c") == 0)
		{
			board.move(Direction.DOWN_RIGHT, this);
			return true;
		}
		return false;
	}
	
	public String toString()
	{
		return "*";
	}
	@Override
	public boolean isVisible() {
		
		return false;
	}
	

}
