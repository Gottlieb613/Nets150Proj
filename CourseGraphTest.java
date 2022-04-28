public class CourseGraphTest {
    public static void main(String[] args) {
//        CourseGraph courses = new CourseGraph("cis");
//        courses.makeGraph();

        CisElectivesAttempt2 c = new CisElectivesAttempt2();
        c.printElectiveList();

        System.out.println(c.coursesInDept("CIS"));
    }
}
