package week8;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Display {
    String filePath;
    public Display(String filePath){this.filePath = filePath;}
    public void display() throws IOException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextDouble()){
            System.out.println(scanner.nextDouble());
        }
        scanner.close();
    }
}
