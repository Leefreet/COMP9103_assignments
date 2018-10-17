package POS;

import java.io.IOException;
import java.util.*;

public class POS {
    public static void main(String args[]) throws IOException {
        FilesIO test = new FilesIO(
                "/Users/liwenxuan/Desktop/samples/members4.txt",
                "/Users/liwenxuan/Desktop/samples/instruction4.txt",
                "/Users/liwenxuan/Desktop/Java projects/COMP9103_Work_Space/POS18s2/src/POS/results.txt",
                "/Users/liwenxuan/Desktop/Java projects/COMP9103_Work_Space/POS18s2/src/POS/reports.txt");

        String record_buffer = FilesIO.readFile(test.getRECORD_FILE_PATH());
        Vector<HashMap<String, String>> members = FilesIO.getInfo(record_buffer);

        String instruction_buffer = FilesIO.readFile(test.getINSTRUCTION_FILE_PATH());

        ArrayList<Resident> residentList = new ArrayList<>();
        for (HashMap<String, String> member : members)
            if (FilesIO.IsValid(member))
                residentList.add(new Resident(member));

        String[] instructions = FilesIO.getInstruction(instruction_buffer);
        Manipulations executive = new Manipulations(residentList);

        executive.executeCommand(instructions);

        String output_report = executive.getReport_txt();
        FilesIO.writeFile(test.getREPORT_FILE_PATH(),output_report);
        System.out.println(output_report);

        String output_result = FilesIO.getOutputReport(residentList);
        FilesIO.writeFile(test.getRESULT_FILE_PATH(),output_result);
    }
}
