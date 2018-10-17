package week8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sort {
    private String filePath;
    public Sort(String filePath){this.filePath = filePath;}

    public void __sort(boolean whether) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        ArrayList<Double> doubles = new ArrayList<>();
        while (scanner.hasNextDouble()){
            doubles.add(scanner.nextDouble());
        }
        if (whether){
            doubles.sort((o1, o2) -> {
                if (o1 > o2)
                    return 1;
                else if (o1 < o2)
                    return -1;
                else
                    return 0;
            });
            PrintWriter printWriter = new PrintWriter(file);
            for (double obj :
                    doubles) {
                printWriter.printf("%f",obj);
                printWriter.println();
            }
            printWriter.close();

        }
        else {
            doubles.sort((o1, o2) -> {
                if (o1 > o2)
                    return -1;
                else if (o1 < o2)
                    return 1;
                else
                    return 0;
            });
            PrintWriter printWriter = new PrintWriter(file);
            for (double obj :
                    doubles) {
                printWriter.printf("%f",obj);
                printWriter.println();
            }
        }
        scanner.close();
    }
}
