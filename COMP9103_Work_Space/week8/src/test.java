import java.io.IOException;
import week8.*;
public class test {
    public static void main(String[] args) throws IOException {
        Create create = new Create(args[0]);
        create.write();
        Search search = new Search(args[0]);
        search.search(3456);
        Sort __sort = new Sort(args[0]);
        __sort.__sort(3<6);
        Display display = new Display(args[0]);
        display.display();
    }
}
