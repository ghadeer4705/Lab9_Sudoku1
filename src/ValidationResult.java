import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private List<DuplicateInfo> rowErrors = new ArrayList<>();
    private List<DuplicateInfo> columnErrors = new ArrayList<>();
    private List<DuplicateInfo> boxErrors = new ArrayList<>();

    public void addRowErrors(List<DuplicateInfo> list) {
        rowErrors.addAll(list);
    }

    public void addColumnErrors(List<DuplicateInfo> list) {
        columnErrors.addAll(list);
    }

    public void addBoxErrors(List<DuplicateInfo> list) {
        boxErrors.addAll(list);
    }

    public static ValidationResult merge(ValidationResult... results) {
        ValidationResult m = new ValidationResult();
        for (ValidationResult r : results) {
            m.addRowErrors(r.rowErrors);
            m.addColumnErrors(r.columnErrors);
            m.addBoxErrors(r.boxErrors);
        }
        return m;
    }

    public boolean isValid() {
        return rowErrors.isEmpty() && columnErrors.isEmpty() && boxErrors.isEmpty();
    }

    public void printFinalResult() {
        if (isValid()) {
            System.out.println("VALID");
            return;
        } else {
            System.out.println("INVALID");
            for (DuplicateInfo d : rowErrors) System.out.println(d.errorFormat());
            System.out.println("----------------------------------------");
            for (DuplicateInfo d : columnErrors) System.out.println(d.errorFormat());
            System.out.println("----------------------------------------");
            for (DuplicateInfo d : boxErrors) System.out.println(d.errorFormat());
        }
    }

    /*public List<DuplicateInfo> getDuplicates() {
        List<DuplicateInfo> allDuplicates = new ArrayList<>();
        allDuplicates.addAll(rowErrors);
        allDuplicates.addAll(columnErrors);
        allDuplicates.addAll(boxErrors);
        return allDuplicates;
    }*/
}