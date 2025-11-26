public class SudokuBoard {
    private int[][] board;

    public SudokuBoard(int[][] board) {
        this.board = board;
    }

    public int getIndex(int row, int col) {
        return board[row][col];
    }

    /*public int getRowNum(int row) {
        return row + 1;
    }

    public int getColNum(int col) {
        return col + 1;
    }

    public int getBoxNum(int row, int col) {
        return (row / 3) * 3 + (col / 3) + 1;
    }*/

    public int[][] getBoard() {
        return board;
    }

}
