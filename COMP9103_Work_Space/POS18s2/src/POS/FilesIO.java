package POS;

import java.io.*;
import java.util.*;

class FilesIO {
    //两个输入信息文件，终端键入
    private String RECORD_FILE_PATH ;
    private String INSTRUCTION_FILE_PATH ;

    //两个输出文件名，可以在同一目录，终端键入命名
    private String RESULT_FILE_PATH ;
    private String REPORT_FILE_PATH ;

    String getRECORD_FILE_PATH() {
        return RECORD_FILE_PATH;
    }
    String getINSTRUCTION_FILE_PATH() {
        return INSTRUCTION_FILE_PATH;
    }
    String getRESULT_FILE_PATH() {
        return RESULT_FILE_PATH;
    }
    String getREPORT_FILE_PATH() {
        return REPORT_FILE_PATH;
    }

    FilesIO(String RECORD_FILE_PATH,String INSTRUCTION_FILE_PATH,String RESULT_FILE_PATH,String REPORT_FILE_PATH){
        this.INSTRUCTION_FILE_PATH = INSTRUCTION_FILE_PATH;
        this.RECORD_FILE_PATH = RECORD_FILE_PATH;
        this.REPORT_FILE_PATH = REPORT_FILE_PATH;
        this.RESULT_FILE_PATH = RESULT_FILE_PATH;
    }

    //读入信息并记录到缓冲区
    private static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream inputStream = new FileInputStream(filePath);
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        line = reader.readLine();
        while (line != null){
            buffer.append(line);
            buffer.append("\n");
            line = reader.readLine();
        }
        reader.close();
        inputStream.close();
    }

    //从缓冲区将文件取出并转化为一个String格式的整体段落
    static String readFile(String filePath) throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        FilesIO.readToBuffer(stringBuffer, filePath);
        return stringBuffer.toString();
    }

    //将String段落中的信息以空行为界，每两空行间的信息为一组数据，录入信息数组
    static Vector<HashMap<String,String>> getInfo(String paragraph){
        Vector<HashMap<String,String>> info = new Vector<>();
        String[] text = paragraph.split("\n\n");
        for (String aText : text) {
            HashMap<String, String> each = new HashMap<>();
            String[] temp = aText.split("\n");
            for (String aTemp : temp) {
                if (aTemp.startsWith("name")) {
                    each.put("name", aTemp.substring(5));
                } else if (aTemp.startsWith("postcode")) {
                    each.put("postcode", aTemp.substring(9));
                } else if (aTemp.startsWith("phone")) {
                    each.put("phone", aTemp.substring(6));
                } else if (aTemp.startsWith("address")) {
                    each.put("address", aTemp.substring(8));
                } else if (aTemp.startsWith("pet")) {
                    each.put("pet", aTemp.substring(4));
                } else if (aTemp.startsWith("birthday")) {
                    each.put("birthday", aTemp.substring(9).replace("-","/"));
                } else {
                    each.replace("address", each.get("address") + " " + aTemp.trim());
                }
            }
            info.add(each);
        }
        return info;
    }

    //将instruction文件读取并拆分成各个单独指令
    static String[] getInstruction(String paragraph){ return paragraph.split("\n\n"); }

    //判断读取的数据是否符合要求，是否为有效数据
    static boolean IsValid(HashMap<String,String> member){
        if (member.containsKey("name") && member.containsKey("phone") && member.containsKey("postcode")){
            if (member.containsKey("birthday")){
                if (!isBirthdayValid(member.get("birthday")))
                    return false;
            }
            return isNameValid(member.get("name")) && isPhoneValid(member.get("phone")) && isPostcodeValid(member.get("postcode"));
        }else
            return false;
    }

    private static boolean isNameValid(String name){
        if (name!=null&&name.length()>0){
            for (char ch :
                    name.toCharArray()) {
                if (!((ch >= 'a' && ch <= 'z')||(ch >= 'A'&&ch <= 'Z')||(ch == ' ')))
                    return false;
            }
        }
        return true;
    }

    private static boolean isPhoneValid(String phone){ return (phone.length() == 8 && phone.matches("[0-9]*")); }

    private static boolean isPostcodeValid(String postcode){ return (postcode.matches("[0-9]*") && postcode.length() == 4); }

    private static boolean isBirthdayValid(String birthday){
        String date[] = birthday.split("/");
        if (Integer.parseInt(date[1]) > 12)
            return false;
        if (Integer.parseInt(date[0]) > 31)
            return false;
        if ((Integer.parseInt(date[2])%4==0&&Integer.parseInt(date[2])%100!=0)||Integer.parseInt(date[2])%400==0)
            if (Integer.parseInt(date[1]) == 2 && Integer.parseInt(date[0]) > 29)
                return false;
            else
            if (Integer.parseInt(date[1]) == 2 && Integer.parseInt(date[0]) > 28)
                return false;
        return true;
    }
    //将有效数据记录，得到文本以便输出
    static String getOutputReport(ArrayList<Resident> input_records){
        StringBuilder out_put_report = new StringBuilder();
        for (Resident record:
                input_records) {
            out_put_report.append("name ").append(record.getName()).append("\n");
            if (record.getBirthday()!=null)
                out_put_report.append("birthday ").append(record.getBirthday().replace('-','/')).append("\n");
            if (record.getAddress()!=null)
                out_put_report.append("address ").append(record.getAddress()).append("\n");
            out_put_report.append("postcode ").append(record.getPostcode()).append("\n");
            out_put_report.append("phone ").append(record.getPhone()).append("\n");
            if (record.getPet()!=null)
                out_put_report.append("pet ").append(record.getPet()).append("\n");
            out_put_report.append("\n");
        }
        return out_put_report.toString();
    }

    //将得到的文本写入TXT格式文件
    static void writeFile(String filePath,String output_string){
        try {
            File file = new File(filePath);
            PrintStream printStream = new PrintStream(new FileOutputStream(file));
            printStream.append(output_string);
            printStream.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
