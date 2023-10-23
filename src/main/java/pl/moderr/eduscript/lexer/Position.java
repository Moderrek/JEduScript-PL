package pl.moderr.eduscript.lexer;

public class Position implements Cloneable {

  private int index;
  private int row;
  private int col;

  public Position() {
    index = 0;
    row = 0;
    col = 0;
  }

  public void advance(char c) {
    col += 1;
    index += 1;
    if (c == '\n') {
      row += 1;
      col = 0;
    }
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getCol() {
    return col;
  }

  public void setCol(int col) {
    this.col = col;
  }

  @Override
  public String toString() {
    return (row+1) + ":" + (col+1);
  }

  @Override
  public Position clone() {
    try {
      Position clone = (Position) super.clone();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
