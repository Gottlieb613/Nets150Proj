import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        open();

    }

    public static void exit(JFrame frame){
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    static void open() {
        JFrame frame = new JFrame();
        exit(frame);

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
            System.exit(0);
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
        //TODO: allow for 2 questions
        // 1. Check if a given course is a valid TE
        // 2. We print all subjects that have a TE, they choose one and it outputs all courses in that subj that are TEs

        CisElectivesAttempt2 te = new CisElectivesAttempt2();

        String[] question3Set = new String[]{"2", "1"};
        JFrame frame = new JFrame();
        int choiceQ3 = JOptionPane.showOptionDialog(
                frame,
                "Please choose the correct option for you: " +
                        "\n 1. Check if a particular course is a valid CIS Tech Elective"
                        + "\n 2. See which courses in a given subject are valid Tech Electives",

                "Retrieve Valid Tech Electives",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                question3Set,
                question3Set[1]
        );

        //Check if given course is TE
        if (choiceQ3 == 1){
            Course course = null;
            String id = "";
            boolean correctCourse = false;
            while (!correctCourse) {
                id = (String) JOptionPane.showInputDialog(
                        frame,
                        "Type the course ID (i.e. NETS 150):",
                        "Check Tech Elective status",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "NETS 150");


                course = CourseInfo.getCourseObj(id);
                if (course!=null) {
                    correctCourse = true;
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Sorry, that is an incorrect course. Please enter it again!");
                }
            }

            String subjCode = course.getSubject();

            String isTechElec = "not currently a valid CIS tech elective ";

            for (Course elec : te.coursesInDept(subjCode)) {
                if (course.getID().equals(elec.getID())) {
                    isTechElec = "a valid CIS tech elective!";
                    break;
                }
            }

            JOptionPane.showMessageDialog(frame, "\n" + id.toUpperCase() + " is " + isTechElec);

        //Ask for TE in subject
        } else {

            String subjectsVertical = "";
            int i = 0;
            for (String c : te.getElecMap().keySet()) {
                if (i == te.getElecMap().keySet().size() - 1) {
                    subjectsVertical += c;
                } else if (i % 5 == 4) {
                    subjectsVertical += c + "\n";
                } else {
                    subjectsVertical += c + ", ";
                }
                i++;
            }

            String subj = null;
            boolean repeat = true;
            while (repeat) {
                subj = (String) JOptionPane.showInputDialog(
                        frame,
                        "Here is the list of subjects that contain valid Tech Electives:\n" + subjectsVertical
                        + "\n\nNow, type a Subject:",
                        "Check subject for tech electives",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "NETS");
                subj = subj.toUpperCase();
                if (te.getElecMap().containsKey(subj)) {
                    repeat = false;
                } else {
                    JOptionPane.showMessageDialog(frame, "Sorry, that subject " +
                            "does not contain any Tech Electives. " +
                            "Please enter again!");
                }
            }

            ArrayList<Course> electives = te.getElecMap().get(subj);
            ArrayList<String> electiveNames = new ArrayList<>();
            for (Course c : electives) {
                electiveNames.add(c.getID() + ": " + c.getTitle());
            }

            String electivesVertical = "";
            for (String c : electiveNames) {
                electivesVertical += c + "\n";
            }

            JOptionPane.showMessageDialog(frame, "Here are courses in " + subj + " with valid Tech Electives:\n" +
                    electivesVertical);
        }
    }

    static void question4() {
        ExtractHumanities eh = new ExtractHumanities();
        String[] question4Set = new String[]{"1", "2", "3", "4"};
        JFrame frame = new JFrame();

        int choiceQ4 = JOptionPane.showOptionDialog(
                frame,
                "Please choose the correct option for you: " +
                        "\n 1. Print all subjects that have courses that count towards SS or H"
                        + "\n 2. Which subjects have limited number of valid SS or H courses?" +
                        "\n 3. Find a valid SS or H course for a specific subject i.e. ANTH, CIS" +
                        "\n 4. Find if a course counts as SS or H",

                "Humanities information",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                question4Set,
                question4Set[3]
        );
        if (choiceQ4==0){
            String subjectList = eh.printGivenList(eh.getAllValidHumanities());
                    JOptionPane.showMessageDialog(frame,"List of all valid humanities subject codes is below\n\t" +
                            subjectList);

        }else if (choiceQ4==1){
            String subjectList = eh.printGivenList(eh.getRestrictedHumanities());
            JOptionPane.showMessageDialog(frame,"List of all subjects with restricted SS or H courses\n\t" +
                    subjectList);

        }else if (choiceQ4==2){
            String id = "";
            String answer = "";
            boolean correctCourse = false;
            while (!correctCourse) {
                id = (String) JOptionPane.showInputDialog(
                        frame,
                        "Type the humanities subject code to check i.e. DSGN, EAS, PHIL ",
                        "Check valid courses given a humanity subject",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "DSGN");


                answer = eh.getValidHumanityGivenSubject(id);
                if (answer!=null) {
                    correctCourse = true;
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Sorry, that subject does not have any courses valid for SS or H");
                }
            }
            JOptionPane.showMessageDialog(frame, "Following information is provided for valid courses in this subject\n"+
                    answer);

        }else if (choiceQ4==3){
            String id = "";
            String answer = "";
            boolean correctCourse = false;
            while (!correctCourse) {
                id = (String) JOptionPane.showInputDialog(
                        frame,
                        "Type the course code to check if its a valid humanity i.e. DSGN 233, NURS 313",
                        "Check if the course counts towards SS or H credit",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "DSGN 236");
                String [] arr = eh.getCourseCodeAndSubjectCode(id);
                answer = eh.isHumanityValid(arr[0], arr[1]);

                if (answer!=null) {
                    correctCourse = true;
                }
                else {
                    JOptionPane.showMessageDialog(frame, "Sorry, that subject does not have any courses valid for SS or H");
                }
            }
            JOptionPane.showMessageDialog(frame, "This is what I found for this course\n"+
                    answer);
        }

    }

    static void question5() {

        VectorSpaceModelTester test = new VectorSpaceModelTester();
        test.recommendationQuestion();

    }
}
