package week8;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Create {
    String filePath;
    public Create(String filePath){this.filePath = filePath;}
    public void write() throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            boolean newFile = file.createNewFile();
        }
        PrintWriter printWriter = new PrintWriter(file);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            printWriter.printf("%f",scanner.nextDouble());
            printWriter.println();
        }
        scanner.close();
        printWriter.close();
    }
}
