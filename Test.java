
public class Test {
    String test = "this wotks";
    public Test (String s){
        test = s;
    }

    public Test(String s, int n) {
        test = s;
        System.out.println("Hey also here a number: " + n);
    }

    public void testPrint(){
        System.out.println(test);
    }
        }

