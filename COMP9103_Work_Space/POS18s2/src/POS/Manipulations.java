package POS;

import java.util.*;

public class Manipulations {

    private ArrayList<Resident> residents;
    private String report_txt = "";

    Manipulations(ArrayList<Resident> residents){
        this.residents = residents;
    }

    String getReport_txt() {
        return report_txt;
    }

    //按关键字执行命令
    void executeCommand(String[] commands){
        for (String command :
                commands) {
            if (command.startsWith("update")){
                String instruction = command.substring(7);
                doUpdating(instruction);
            }else if (command.startsWith("delete")){
                String[] instruction = command.substring(7).split(";");
                String name = instruction[0].trim();
                String phone = instruction[1].trim();
                doDeleting(name,phone);
            }else if (command.startsWith("sort")){
                doSortingByName();
            }else{
                String instruction = command.substring(6);
                report_txt += doQuerying(instruction);
            }
        }
    }

    //更新或添加居民信息
    private void doUpdating(String information){
        Resident resident = new Resident();
        String[] attributes = information.split("; ");
        for (String line :
                attributes) {
            line.trim();
        }
        for (String line :
                attributes) {
            if (line.startsWith("name")){
                resident.setName(line.substring(5));
            }else if (line.startsWith("birthday")){
                resident.setBirthday(line.substring(9));
            }else if (line.startsWith("phone")){
                if (line.substring(5).trim().matches("[0-9]*")) resident.setPhone(line.substring(6));
            }else if (line.startsWith("address")){
                resident.setAddress(line.substring(8));
            }else if (line.startsWith("pet")){
                resident.setPet(line.substring(4));
            }else if (line.startsWith("postcode")){
                resident.setPostcode(line.substring(9));
            }
        }
        for (Resident obj :
                residents) {
            if (obj.getName().equals(resident.getName())&&obj.getPhone().equals(resident.getPhone())
                    &&obj.getPhone().matches("[0-9]*")&&resident.getPhone().matches("[0-9]*")){
                if (resident.getAddress()!=null)
                    obj.setAddress(resident.getAddress());
                if (resident.getBirthday()!=null)
                    obj.setBirthday(resident.getBirthday());
                if (resident.getPet()!=null)
                    obj.setPet(resident.getPet());
                if (resident.getPostcode()!=null)
                    obj.setPostcode(resident.getPostcode());

            }else if (resident.getPhone()!=null&&resident.getName()!=null&&resident.getPostcode()!=null){
                residents.add(resident);
                break;
            }

        }
    }

    //删除指定现有居民信息
    private void doDeleting(String name, String phone){
        for (Resident obj :
                residents) {
            if (obj.getName().equals(name) && obj.getPhone().equals(phone)){
                residents.remove(obj);
                break;
            }
        }
    }

    //按姓名升序对全体居民排序
    private void doSortingByName(){
        residents.sort((o1, o2) -> {
            if (o1.getName().compareTo(o2.getName()) > 0){
                return 1;
            }else if ((o1.getName().compareTo(o2.getName())) < 0){
                return -1;
            }else {
                if (o1.getPhone().compareTo(o2.getPhone()) > 0){
                    return 1;
                }else if (o1.getPhone().compareTo(o2.getPhone()) < 0){
                    return -1;
                }
            }return 0;
        });
    }

    //按关键字查询并输出信息
    private String  doQuerying(String instruction){
        StringBuilder results = new StringBuilder();
        if (instruction.startsWith("name")){
            String name = instruction.substring(5);
            ArrayList<Resident> residentsList = queryByName(name);
            if (!residentsList.isEmpty()){
                results.append("----------query name ").append(name).append("----------\n");
                for (Resident obj :
                        residentsList) {
                    results.append("name ").append(obj.getName()).append("\nphone ").append(obj.getPhone()).append("\npostcode ").append(obj.getPostcode());
                    if (obj.getPet()!=null)
                        results.append("\npet ").append(obj.getPet());
                    if (obj.getAddress()!=null)
                        results.append("\naddress ").append(obj.getAddress());
                    if (obj.getBirthday()!=null)
                        results.append("\nbirthday ").append(obj.getBirthday());
                }
                results.append("\n----------------------------------------\n\n\n\n");
            }
        }else if (instruction.length()>3 && instruction.substring(0,4).matches("[0-9]*")){
            String ageInfo = queryAgeByPostcode(instruction.substring(0,4));
            results.append(ageInfo).append("\n\n\n");
        }else{
            String petPop = queryPetPop();
            results.append(petPop);
        }
        return results.toString();
    }

    //按姓名查找
    private ArrayList<Resident> queryByName(String name){
        ArrayList<Resident> result = new ArrayList<>();
        for (Resident obj :
                residents) {
            if (name.equals(obj.getName())){
                result.add(obj);
            }
        }
        if (result.size() > 1){
            result.sort((o1, o2) -> Integer.compare(o1.getPhone().compareTo(o2.getPhone()), 0));
        }
        return result;
    }

    //按邮政号查询区内居民年龄段比例
    private String queryAgeByPostcode(String postcode){
        String result = "";
        double size = 0;
        double teen = 0;
        double adult = 0;
        double oldPeople =0;
        for (Resident obj :
                residents) {
            if (postcode.equals(obj.getPostcode())&&obj.getPet()!=null){
                size++;
                if (obj.calcAge() < 18)
                    teen++;
                else if (obj.calcAge() >=18 && obj.calcAge() <65)
                    adult++;
                else
                    oldPeople++;
            }
        }
        double teen_rate = teen/size *100;
        double adult_rate = adult/size *100;
        double old_rate = oldPeople/size *100;
        double unknown = (1-teen_rate-adult_rate-old_rate)*100;
        if (unknown <= 0.01)
            unknown = 0;
        result += "--------query  "+postcode+"--------\n"+
                "Available pet owner size:"+Double.toString(size) +
                "\nAge profile\nBelow 18: " +String.format("%2.2f",teen_rate)+
                "%\nOver 18 and Below 65: "+String.format("%2.2f",adult_rate)+
                "%\nOver 65: " +String.format("%2.2f",old_rate)+
                "%\nUnknown: "+String.format("%2.2f",unknown)+
                "%\n---------------------------\n\n";


        return result;
    }

    //查询各邮政区内最受欢迎的宠物种类
    private String queryPetPop(){
        Comparator<Map.Entry<String,Integer>> descendingSort = (o1, o2) -> o2.getValue()-o1.getValue();
        String result = "---------query pet---------\n";

        HashMap<String,HashMap<String,Integer>> ranking = new HashMap<>();
        for (Resident obj :
                residents) {
            if (obj.getPet()!=null){
                if (obj.getPet().contains(" ")){
                    String[] pets = obj.getPet().split(" ");
                    for (String specie :
                            pets) {
                        HashMap<String,Integer> dist = new HashMap<String, Integer>();
                        if (!ranking.containsKey(specie))
                            ranking.put(specie,dist);
                    }
                }else {
                    HashMap<String, Integer> dist = new HashMap<String, Integer>();
                    ranking.put(obj.getPet(), dist);
                }
            }
        }

        for (Resident obj :
                residents) {
            if (obj.getPet()!=null){
                String[] pets = obj.getPet().split(" ");
                for (String specie :
                        pets) {
                    HashMap<String,Integer> dist = ranking.get(specie);
                    dist.put(obj.getPostcode(),0);
                    ranking.replace(specie,dist);
                }
            }
        }
        for (Resident obj :
                residents) {
            if (obj.getPet()!=null){
                String[] pets = obj.getPet().split(" ");
                for (String specie :
                        pets) {
                    HashMap<String,Integer> dist = ranking.get(specie);
                    dist.replace(obj.getPostcode(),dist.get(obj.getPostcode())+1);
                    ranking.replace(specie,dist);
                }
            }
        }
        for (Map.Entry<String, HashMap<String, Integer>> entry : ranking.entrySet()) {
            String key = entry.getKey();
            HashMap<String, Integer> value = entry.getValue();
            List<Map.Entry<String, Integer>> pet = new ArrayList<>(value.entrySet());
            pet.sort(descendingSort);
            String product = key + ": " + pet.get(0).getValue().toString() + " ; Postcode " + pet.get(0).getKey();
            for (int i = 1; i < pet.size(); i++) {
                if (pet.get(i).getValue().equals(pet.get(0).getValue()))
                    product += " " + pet.get(i).getKey();
            }
            product += "\n";
            result += product;
        }
        result +="---------------------------";
        return result;
    }
}