import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
	private int height;
	private int width;
	private HashMap<Boardable, Cell> elementPlace = new HashMap<Boardable, Cell>();
	
	public Board(int height, int width)
	{
		for(int indexWidth = 0; indexWidth < width; indexWidth++)
		{
			ArrayList<Cell> temp = new ArrayList<Cell>();
			
			for(int indexHeight = 0; indexHeight < height; indexHeight++)
			{
				temp.add(new Cell(indexHeight, indexWidth));
			}
			board.add(temp);
			
		}
		
	}
	
	public boolean move(Direction dir, Boardable elem)
	{
		
		
		return true;
	}
	
	public boolean placeElement(Boardable elem, int row, int col)
	{
		
		
		return true;
	}
	
	public void printBoard()
	{
		System.out.println(board);
	}
	
	
	private class Cell {
		private int row;
		private int col;
		private boolean isVisible;
		private ArrayList<Boardable> elements = new ArrayList<Boardable>();
		
		public Cell(int row, int column)
		{
			this.row = row;
			this.col = column;
			this.isVisible = true;
		}
		
		public void addElement(Boardable elem)
		{
			elements.add(elem);
		}
		
		public boolean removeElement(Boardable elem)
		{
			
			return true;
		}
		
		public String toString()
		{
			if(this.isVisible)
			{
				return "";
			}else if(elements.size() != 0)
			{
				return "#";
			}
		}
	}
}
