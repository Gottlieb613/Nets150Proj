public class Main {
    public static void main(String[] args) {
        System.out.println("Hello hello");
        Test n1 = new Test("This code needs to be printed");
        n1.testPrint();

        Test n2 = new Test("Print", 14);
        n2.testPrint();


        int testInt = 14;


        for (int i = 0; i < testInt; i++) {
            System.out.print(i + " ");
        }

    }
}
