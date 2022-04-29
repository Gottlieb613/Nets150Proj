import java.util.HashMap;

public class CourseInfoTest {

    public static void main(String[] args) {
        HashMap<String, Course> cisCourses = CourseInfo.getAllCoursesInSubject("nurs");
        for (String c : cisCourses.keySet()) {
            System.out.println(c + ":\n\t" + cisCourses.get(c));
        }
    }
}
