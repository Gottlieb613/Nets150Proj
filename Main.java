import java.util.Scanner;

public class Main {
    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        open();

    }

    static void open() {
        System.out.println(
                "Welcome to the UPenn CIS Course Catalog Info Center!\n" +
                "(actual name TBD)\n" +
                "Created by Ethan Eiseinberg, Charlie Gottlieb, and Hussain Zaidi\n" +
                "Choose a question to ask below by typing in the respective number");
        askQuestions();
    }

    static void askQuestions() {
        System.out.print(
                "1. What does a particular subject code mean (i.e. CIS, NURS, DIGC)?\n" +
                "2. Provide some information about a given course (i.e. CIS 121)\n" +
                "3. Provide some information about CIS Tech Elective courses\n" +
                "4. Provide some information about CIS Humanities courses\n" +
                "5. What course would I be interested in within a particular subject?\n" +
                "Type a question number here: ");
        int choice = scan.nextInt();
        scan.nextLine(); //this is to bring the scanner carriage to end of line for next scan
        if (choice < 1 || choice > 5) {
            System.out.println("That is not a valid option, please choose again");
        } else {
            switch (choice) {
                case 1: {
                    question1();
                    break;
                }
                case 2: {
                    question2();
                    break;
                }
                case 3: {
                    question3();
                    break;
                }
                case 4: {
                    question4();
                    break;
                }
                case 5: {
                    question5();
                    break;
                }
            }
        }
        System.out.println("\n\nAsk another question? (y/n)");
        String askAgain = scan.nextLine();
        if (askAgain.equals("y")) {
            askQuestions();
        } else {
            System.out.println("Thank you for using our system. Have a nice day!");
        }
    }

    static void question1() {
        System.out.print("Type the subject code: ");
        String code = scan.nextLine();
        String title = CourseInfo.getSubjectTitle(code);
        if (title != null) {
            System.out.println(code.toUpperCase() + " refers to " + title);
        }
    }

    static void question2() {
        System.out.print("Type the course ID (i.e. CIS 110): ");
        String id = scan.nextLine();
        Course course = CourseInfo.getCourseObj(id);
        if (course != null) {
            System.out.print("\n" + id.toUpperCase() + " is " + course.getTitle() +
            "\nIts description is:\n\t" + course.getDescriptionNewlines() +
            "\nAnd its prerequisites are:\n\t");
            if (course.getPrereqs().size() == 0) {
                System.out.println("No prerequisites");
            } else {
                System.out.println(course.getPrereqs());
            }
        }
    }

    static void question3() {

    }

    static void question4() {

    }

    static void question5() {

    }
}
