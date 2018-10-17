public class Person {
    protected String name;
    protected String birthday;

    public Person(String name,String birthday){
        this.name = name;
        this.birthday = birthday;
    }
    public Person(){}

    public String toString(){
        return "name :"+name+"  birthday :"+birthday;
    }
}
