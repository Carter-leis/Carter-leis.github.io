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
		switch(Direction.values())
		{
		
		}
		
		return true;
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
