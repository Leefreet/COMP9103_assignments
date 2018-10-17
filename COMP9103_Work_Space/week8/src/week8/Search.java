package week8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Search {
    private String filePath;
    public Search(String filePath){
        this.filePath = filePath;
    }
    public void search(double target){
        File file = new File(filePath);
        int times = 0;
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()){
                if (scanner.nextDouble()==target) {
                    times++;
                }
            }
            System.out.println(target+" appears "+times+" times");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
