import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        /*String file = "sudoku.csv"; // Hardcoded path for checkk
        int[][] grid = CSVFileReader.readFromFile(file);
        SudokuBoard board = new SudokuBoard(grid);

        Scanner s = new Scanner(System.in);
        System.out.println("Choose validation mode:");
        System.out.println("0- Sequential(No Threads)");
        System.out.println("3- 3 Threads");
        System.out.println("27- 27 Threads");
        int mode = s.nextInt();
        int mode = 0; //to check mode 0

        Validator validator = ModeFactory.getValidator(mode);
        ValidationResult result = validator.validate(board);
        result.printFinalResult();*/


        if (args.length != 2) {
            System.out.println("Usage: java Main <mode> <file>");
            return;
        }

        int mode = Integer.parseInt(args[0]);
        String fileName = args[1];

        int[][] grid = CSVFileReader.readFromFile(fileName);
        SudokuBoard board = new SudokuBoard(grid);

        Validator validator = ModeFactory.getValidator(mode);

        ValidationResult result = validator.validate(board);

        if (result.isValid()) {
            System.out.println("Sudoku is valid!");
        } else {
            System.out.println("Sudoku is invalid!");
            for (DuplicateInfo d : result.getDuplicates()) {
                System.out.println(d);
            }
        }
    }
}
