import java.util.ArrayList;
import java.util.HashMap;

public class CourseInfoTest {

    public static void main(String[] args) {
        ArrayList<Course> cisCourses = CourseInfo.getAllCoursesInSubject("meam");
        for (Course c : cisCourses) {
            System.out.println(c + ":\n\t" + c.getDescription());
            System.out.println("\tprereqs: " + c.getPrereqs());
        }


        Course cis121 = CourseInfo.getCourseObj("nets 150");
        System.out.println(cis121 + ":\n\t" + cis121.getDescriptionNewlines());
        System.out.println("\tprereqs: " + cis121.getPrereqs());
    }
}
