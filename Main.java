import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.Scanner;
import java.*;
public class Main {
    private static Scanner scan;

    public static void main(String[] args) {
        scan = new Scanner(System.in);
        open();

    }

    static void open() {
        JFrame frame = new JFrame();
        String userName = (String) JOptionPane.showInputDialog(
                frame,
                "Enter your name",
                "Introduce yourself!",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "Node A");
        JOptionPane.showMessageDialog(frame, userName + ", Welcome to the UPenn Course Catalog " +
                "Info Center!\n" + "\n" +
                "Created by Ethan Eisenberg, Charlie Gottlieb, and Hussain Zaidi\n" +

                " To begin, please choose a question");


        askQuestions();
    }


    static void askQuestions() {
        String[] optionSet = new String[]{"5", "4", "3", "2", "1"};
        JFrame frame = new JFrame();
        int choice = JOptionPane.showOptionDialog(
                frame,
                "1. What does a particular subject code mean (i.e. CIS, NURS, DIGC)?\n" +
                        "2. Provide some information about a given course (i.e. CIS 121)\n" +
                        "3. Provide some information about CIS Tech Elective courses\n" +
                        "4. Provide some information about CIS Humanities courses\n" +
                        "5. What course would I be interested in within a particular subject?\n",

                "What are you interested in learning about?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionSet,
                optionSet[4]
        );


//        int choice = scan.nextInt();
//        scan.nextLine(); //this is to bring the scanner carriage to end of line for next scan


        if (choice < 0 || choice > 4) {
            JOptionPane.showMessageDialog(frame, "That is not a valid option, please choose again");

        } else {
            switch (choice) {
                case 0: {
                    question5();
                    break;
                }
                case 1: {
                    question4();
                    break;
                }
                case 2: {
                    question3();
                    break;
                }
                case 3: {
                    question2();
                    break;
                }
                case 4: {
                    question1();
                    break;
                }
            }
        }
//        System.out.println("\n\nAsk another question? (y/n)");
//        String askAgain = scan.nextLine();
        String[] yesNoSet = new String[]{"Yes!", "No, thank you"};

        int again = JOptionPane.showOptionDialog(
                frame,
                "Ask another question?",

                "Ask again?",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                yesNoSet,
                yesNoSet[0]
        );

        if (again == 0) {
            askQuestions();
        } else {
            JOptionPane.showMessageDialog(frame, "Thank you for using our system. Have a nice day!");
        }
    }

    static void question1() {
        JFrame frame = new JFrame();
        String code = (String) JOptionPane.showInputDialog(
                frame,
                "Type the subject code: ",
                "Provide Subject Information",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "NETS");

//        System.out.print("Type the subject code: ");
//        String code = scan.nextLine();
        String title = CourseInfo.getSubjectTitle(code);
        if (title != null) {
            JOptionPane.showMessageDialog(frame, code.toUpperCase() + " refers to " + title);
//            System.out.println(code.toUpperCase() + " refers to " + title);
        }
    }

    static void question2() {
//        System.out.print("Type the course ID (i.e. CIS 110): ");
//        String id = scan.nextLine();

        JFrame frame = new JFrame();
        Course course = null;
        String id = "";
        boolean correctCourse = false;
        while (!correctCourse) {
            id = (String) JOptionPane.showInputDialog(
                    frame,
                    "Type the course ID (i.e. NETS 150): ",
                    "Provide Subject Information",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "NETS 150");


             course = CourseInfo.getCourseObj(id);
             if (course!=null){
                 correctCourse = true;
             }
             else{
                 JOptionPane.showMessageDialog(frame, "Sorry, that is an incorrect course. Please enter it again!");
             }
        }

        String prereqsString = "And its prerequisites are:\n\t" + course.getPrereqs();
        if (course.getPrereqs().isEmpty()) {
            prereqsString = "Further, there are no prerequisites. Get ready to learn!";
        }

        String postreqsString = "And it is a prerequisite for the follow courses:\n\t" + CourseInfo.getPostreqs(id);
        if (CourseInfo.getPostreqs(course.getID()).isEmpty()) {
            postreqsString = "Further, there are no courses that have it as a prerequisite. Get ready to learn!";
        }

        JOptionPane.showMessageDialog(frame, "\n" + id.toUpperCase() + " is: " + course.getTitle() +
                "\nIts description is:\n\t" + course.getDescriptionNewlines() +
                "\n\n" + prereqsString +
                "\n\n" + postreqsString);


////            System.out.print("\n" + id.toUpperCase() + " is " + course.getTitle() +
////            "\nIts description is:\n\t" + course.getDescriptionNewlines() +
////            "\nAnd its prerequisites are:\n\t");
//            if (course.getPrereqs().size() == 0) {
////                System.out.println("No prerequisites");
//                JOptionPane.showMessageDialog(frame, "Further, there are no prerequisites. Get " +
//                        "ready to learn!");
   }


    static void question3() {

    }

    static void question4() {

    }

    static void question5() {

        VectorSpaceModelTester test = new VectorSpaceModelTester();
        test.recommendationQuestion();

    }
}
