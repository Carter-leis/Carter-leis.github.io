import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private ArrayList<ArrayList<Cell>> board = new ArrayList<ArrayList<Cell>>();
	private int height;
	private int width;
	private HashMap<Boardable, Cell> elementPlace = new HashMap<Boardable, Cell>();  //when moving, need to get the cell row/column and change it based on the move
	
	public Board(int height, int width)
	{
		if(height <= 100 && height >= 1 && width <= 100 && width >= 1)
		{
			this.height = height;
			this.width = width;
			for(int indexHeight = 0; indexHeight < height; indexHeight++)
			{
				ArrayList<Cell> temp = new ArrayList<Cell>();
				for(int indexWidth = 0; indexWidth < width; indexWidth++)
				{
					temp.add(new Cell(indexHeight, indexWidth));
				}
				board.add(temp);	
			}
			Stylus stylus = new Stylus(this);
			elementPlace.put(stylus, board.get(0).get(0)); //make this work
			board.get(0).get(0).addElement(stylus);
			printBoard();
			int index = 0;
			while(index == 0)
			{
				stylus.move();
			}
		}else
		{
			throw new IllegalArgumentException("the height or width is not in the bounds of 1 - 100");
		}
	}
	
	public boolean move(Direction dir, Boardable elem)
	{
		if(elementPlace.get(elem).row < height && elementPlace.get(elem).col < width)
		{
			int placeRow = elementPlace.get(elem).row;
			int placeCol = elementPlace.get(elem).col;
				if(dir.equals(Direction.DOWN))
				{
					if(placeRow + 1 < height && placeCol < width)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow +1).get(placeCol).addElement(elem);
						elementPlace.put(elem, board.get(placeRow + 1).get(placeCol));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.DOWN_LEFT))
				{
					if(placeRow + 1 < height && placeCol - 1 >= 0)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow +1).get(placeCol -1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow + 1, placeCol - 1));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.DOWN_RIGHT))
				{
					if(placeRow + 1 < height && placeCol + 1 < width)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow +1).get(placeCol +1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow + 1, placeCol + 1));                //0 doesnt allow for stylus to go back to position 0
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.LEFT))
				{
					if(placeRow < height && placeCol - 1 >= 0)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow).get(placeCol -1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow, placeCol - 1));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.RIGHT))
				{
					if(placeRow < height && placeCol + 1 < width)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow).get(placeCol +1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow, placeCol + 1));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.UP))
				{     
					if(placeRow - 1 >= 0  && placeCol < width)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow -1).get(placeCol).addElement(elem);                    //might have to turn to get cell then use that cell and add the element to the cell
						elementPlace.put(elem, new Cell(placeRow - 1, placeCol));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.UP_LEFT))
				{
					if(placeRow - 1 >= 0 && placeCol - 1 >= 0)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow -1).get(placeCol -1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow - 1, placeCol - 1));
						printBoard();
						return true;
					}
				}if(dir.equals(Direction.UP_RIGHT))
				{
					if(placeRow - 1 >= 0 && placeCol + 1 < width)
					{
						elementPlace.remove(elem);
						board.get(placeRow).get(placeCol).removeElement(elem);
						board.get(placeRow -1).get(placeCol +1).addElement(elem);
						elementPlace.put(elem, new Cell(placeRow - 1, placeCol + 1));
						printBoard();
						return true;
					}
				}
		}
		printBoard();
		return false;
	}
	
	public boolean placeElement(Boardable elem, int row, int col)
	{
		if(row < height && col < width)
		{
			board.get(row).get(col).addElement(elem);
			return true;
		}
		return false;
	}
	
	public void printBoard()
	{
		for(int firstIndex = 0; firstIndex < this.height; firstIndex++)
		{
			for(int secondIndex = 0; secondIndex < this.width; secondIndex++)
			{
				System.out.print(board.get(firstIndex).get(secondIndex));
			}
			System.out.println("");
		}
		
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
			this.isVisible = false;
		}
		
		public void addElement(Boardable elem)
		{
			elements.add(elem);
			this.isVisible = true;
		}
		
		public boolean removeElement(Boardable elem)
		{
			if(elements.size() > 0)
			{
				elements.remove(0);
				return true;
			}
			return false;
		}
		public String toString()
		{
			if(this.isVisible && elements.size() == 0)
			{
				return " ";
			}else if(!this.isVisible)
			{
				return "#";
			}else
			{
				return elements.get(0).toString();
			}

		}
	}
}
