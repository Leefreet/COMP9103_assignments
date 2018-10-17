public class Student extends Person {
    private String major ;

    public Student(String name, String birthday, String major){
        super(name,birthday);
        this.major = major;
    }

    public String toString(){
        return super.toString()+" major :"+major;
    }
}
