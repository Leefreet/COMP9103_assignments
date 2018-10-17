public class test {
    public static void main(String args[]){
        Instructor instructor = new Instructor("tom","12/12/1980","12341");
        Student student = new Student("ben","11/11/2000","CS");
        System.out.println(student.toString());
        System.out.println(instructor.toString());
    }
}
