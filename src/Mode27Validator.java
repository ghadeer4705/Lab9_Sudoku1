import java.util.ArrayList;
import java.util.List;

// in this mode, 27 threads are created to validate the sudoku board
//where each row, column and box has 9 threads checking them in parallel

public class Mode27Validator implements Validator {
    @Override
    public ValidationResult validate(SudokuBoard board) {
        //remove all errors that can be found in columns and boxes and rows
        ValidationResult result = new ValidationResult();

        List<RowChecker> rowCheckers = new ArrayList<>();
        List<ColumnChecker> columnCheckers = new ArrayList<>();
        List<BoxChecker> boxCheckers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();

        // Rows
        for (int r = 0; r < 9; r++) {
            //to avoid errors that can happen during multithreading as the variable r changes during the loop
            RowChecker rc = new RowChecker(board);
            final int row = r;
            Thread rowThread = new Thread(()->rc.checkSingleRow(row));
            threads.add(rowThread);
            rowCheckers.add(rc);
            rowThread.start();
        }
        // Columns
        for (int c = 0; c < 9; c++) {
            final int col = c;
            ColumnChecker cc = new ColumnChecker(board);
            Thread columnThread = new Thread(()->cc.checkSingleColumn(col));
            threads.add(columnThread);
            columnCheckers.add(cc);
            columnThread.start();
        }
        // Boxes
        int boxNumber = 1;
        for (int sr = 0; sr < 9; sr += 3) {
            for (int sc = 0; sc < 9; sc += 3) {
                final int startRow = sr;
                final int startCol = sc;
                final int currentBoxNumber = boxNumber;
                BoxChecker bc = new BoxChecker(board);
                Thread boxThread = new Thread(()->bc.checkSingleBox(startRow, startCol,
                        currentBoxNumber));
                threads.add(boxThread);
                boxCheckers.add(bc);
                boxThread.start();
                boxNumber++;
            }
        }
        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                // Wait for thread to complete
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // Collect results
        for (RowChecker rc : rowCheckers){
            result.addRowErrors(rc.getResult());
        }
        for (ColumnChecker cc : columnCheckers){
            result.addColumnErrors(cc.getResult());
        }
        for (BoxChecker bc : boxCheckers){
            result.addBoxErrors(bc.getResult());
        }

        return result;
    }
}
