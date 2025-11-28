public class Mode3Validator implements Validator {

    @Override
    public ValidationResult validate(SudokuBoard board) {

        // create the 3 checkers
        RowChecker rowChecker = new RowChecker(board);
        ColumnChecker colChecker = new ColumnChecker(board);
        BoxChecker boxChecker = new BoxChecker(board);

        // each checker inside a thread
        Thread t1 = new Thread(rowChecker);
        Thread t2 = new Thread(colChecker);
        Thread t3 = new Thread(boxChecker);

        t1.start();
        t2.start();
        t3.start();

        // wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // collect results into ValidationResult
        ValidationResult result = new ValidationResult();
        result.addRowErrors(rowChecker.getResult());
        result.addColumnErrors(colChecker.getResult());
        result.addBoxErrors(boxChecker.getResult());

        // return final validation result
        return result;
    }
}

