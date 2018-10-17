public class Instructor extends Person{
    private String salary ;

    public Instructor(String name, String birthday, String salary){
        super(name,birthday);
        this.salary = salary;
    }

    public String toString(){
        return super.toString()+" salary :"+salary;
    }
}
