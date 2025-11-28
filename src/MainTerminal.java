public class MainTerminal {
    public static void main(String[] args) {
         if (args.length != 2) {
            System.out.println("Usage: java Main <sudoku_file.csv> <mode>");
            System.out.println("Modes: 0 - Sequential, 3 - 3 Threads, 27 - 27 Threads");

          return;
        }
        String file = args[0];
        int mode;
        try {
            mode = Integer.parseInt(args[1]);
            if (mode != 0 && mode != 3 && mode != 27) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid mode. Please enter 0, 3, or 27.");
            return;
        }
        int[][] grid = CSVFileReader.readFromFile(file);
        SudokuBoard board = new SudokuBoard(grid);
        Validator validator = ModeFactory.getValidator(mode);
        ValidationResult result = validator.validate(board);
        result.printFinalResult();
        System.out.flush();
    }
}
