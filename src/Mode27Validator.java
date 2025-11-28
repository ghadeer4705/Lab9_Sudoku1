import java.util.ArrayList;
import java.util.List;

// in this mode, 27 threads are created to validate the sudoku board
//where each row, column and box has 9 threads checking them in parallel

public class Mode27Validator implements Validator {
    @Override
    public ValidationResult validate(SudokuBoard board) {
        //remove all errors that can be found in columns and boxes and rows
        ValidationResult result = new ValidationResult();

        /*List<RowChecker> rowCheckers = new ArrayList<>();
        List<ColumnChecker> columnCheckers = new ArrayList<>();
        List<BoxChecker> boxCheckers = new ArrayList<>();*/
        List<Thread> threads = new ArrayList<>();
        RowChecker rowChecker = new RowChecker(board);
        ColumnChecker columnChecker = new ColumnChecker(board);
        BoxChecker boxChecker = new BoxChecker(board);

        // Rows
       /* for (int r = 0; r < 9; r++) {
            RowChecker rc = new RowChecker(board);
            rowCheckers.add(rc);
            final int row = r;
            Runnable rowTask = new Runnable() {
                @Override
                public void run() {
                    rc.checkSingleRow(row);
                }
            };
            Thread rowThread = new Thread(rowTask);
            threads.add(rowThread);
            rowThread.start();
        }*/
        for (int r = 0; r < 9; r++) {
            final int rowIndex = r;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    rowChecker.checkSingleRow(rowIndex);
                }
            });
            threads.add(t);
            t.start();
        }
        // Columns
        /*for (int c = 0; c < 9; c++) {
            ColumnChecker cc = new ColumnChecker(board);
            columnCheckers.add(cc);
            final int col = c;
            Runnable colTask = new Runnable() {
                @Override
                public void run() {
                    cc.checkSingleColumn(col);
                }
            };
            Thread colThread = new Thread(colTask);
            threads.add(colThread);
            colThread.start();
        }*/
        for (int c = 0; c < 9; c++) {
            final int colIndex = c;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    columnChecker.checkSingleColumn(colIndex);
                }
            });
            threads.add(t);
            t.start();
        }
        // Boxes
        int boxNumber = 1;
        for (int sr = 0; sr < 9; sr += 3) {
            for (int sc = 0; sc < 9; sc += 3) {
                final int startRow = sr;
                final int startCol = sc;
                final int currentBoxNumber = boxNumber;
               /* BoxChecker bc = new BoxChecker(board);
                boxCheckers.add(bc);
                Runnable boxTask = new Runnable() {
                    @Override
                    public void run() {
                        bc.checkSingleBox(startRow, startCol, currentBoxNumber);
                    }
                };
                Thread boxThread = new Thread(boxTask);
                threads.add(boxThread);
                boxThread.start();
                boxNumber++;*/
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        boxChecker.checkSingleBox(startRow, startCol, currentBoxNumber);
                    }
                });
                threads.add(t);
                t.start();
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
          /*  for (RowChecker rc : rowCheckers) {
                result.addRowErrors(rc.getResult());
            }
            for (ColumnChecker cc : columnCheckers) {
                result.addColumnErrors(cc.getResult());
            }
            for (BoxChecker bc : boxCheckers) {
                result.addBoxErrors(bc.getResult());
            }*/
            result.addRowErrors(rowChecker.getResult());
            result.addColumnErrors(columnChecker.getResult());
            result.addBoxErrors(boxChecker.getResult());

            return result;
        }

}

