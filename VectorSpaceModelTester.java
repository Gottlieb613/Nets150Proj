

import javax.swing.*;
import java.io.*;
import java.io.FileWriter;

import java.nio.charset.Charset;
import java.util.*;
import java.*;


/**
 * the tester class.
 * @author swapneel
 */
public class VectorSpaceModelTester {






    public static void main(String[] args) {

        CourseInfo courseInfo = new CourseInfo();
        JFrame frame = new JFrame();

        //source for the code:
        //https://www.tutorialspoint.com/swingexamples/show_input_dialog_text.htm


        //would you want to filter by course code or course description
        JOptionPane.showMessageDialog(frame, "Welcome to Course Recommender! ");
        String[] optionSet = new String[] { "Filter by Course Code", "Filter by Course Description" };
        int  n = JOptionPane.showOptionDialog(
                frame,
                "How do you want to receive your course recommendations?",
                "Choose your method of recommendation!",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionSet,
                optionSet[1]
        );

        if (n == 0){

            //Retrieve the department that the user inputs
            JOptionPane.showMessageDialog(frame, "First, please enter the department code that you want to " +
                    "receive recommendations for!");
            String departmentListing = (String)JOptionPane.showInputDialog(
                    frame,
                    "Enter the  Department Category",
                    "Recommendation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "cis");
            JOptionPane.showMessageDialog(frame, "You selected the " + departmentListing.toUpperCase()
                    + " department");
                JOptionPane.showMessageDialog(frame, "Please enter the " +
                        " course code that you want to receive similar courses for! ");
                String result = (String) JOptionPane.showInputDialog(
                        frame,
                        "Enter the Course Code",
                        "Recommendation",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        "CIS 099"
                );

                //assume for now that they entered a code that is cis
            Course  selectedCourse = CourseInfo.getCourseObj(result);
            JOptionPane.showMessageDialog(frame, "We will now receive recommendations for: " + " \n" +
                    selectedCourse.getTitle());
            ArrayList<Course> coursesInDepartment = courseInfo.getAllCoursesInSubject(departmentListing.toLowerCase());
            ArrayList<Document> documentsArrayList = new ArrayList<>();
            ArrayList<String> documentNames = new ArrayList<>();
            //Write the course query (based on the code to a new file)
            try {
                //relative path to write to a file that is not specified
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./Classes/classDescription.txt"));
                writer.write(selectedCourse.getDescription());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document queryCourseInput =  new Document("./Classes/classDescription.txt");
            documentsArrayList.add(queryCourseInput);
            documentNames.add(selectedCourse.getTitle());




           //add all the descriptions of the courses in the department
            for (Course c: coursesInDepartment){
                //make sure that we are not adding the same course description as the one that the user
                //is trying to receive recommendations for
                if (!c.getTitle().equals(selectedCourse.getTitle())) {
                    //Write the course query (based on the code to a new file)
                    try {
                        //relative path to write to a file that is not specified
                        BufferedWriter writer = new BufferedWriter(
                                new FileWriter("./Classes/classDescription.txt"));
                        writer.write(c.getDescription());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Document add =  new Document("./Classes/classDescription.txt");
                    documentsArrayList.add(add);
                    documentNames.add(c.getTitle());
                }
            }


            //
            TreeMap< Double, String> mapFromCourseToSimilarity = new TreeMap<>();

            Corpus corpus = new Corpus(documentsArrayList);
            VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
            for(int i = 1; i < documentsArrayList.size(); i++) {
                Document doc = documentsArrayList.get(i);
                String name = documentNames.get(i);
                System.out.println("\nComparing to " + name);
                System.out.println(vectorSpace.cosineSimilarity(queryCourseInput, doc));

                //put the values to the tree map
                mapFromCourseToSimilarity.put( vectorSpace.cosineSimilarity(queryCourseInput, doc), name);
            }

          Set<Double> values = mapFromCourseToSimilarity.keySet();
            ArrayList<Double> valuesToSort = new ArrayList<>();
            for (Double val: values){
                valuesToSort.add(val);
            }
            Collections.sort(valuesToSort);
            Collections.reverse(valuesToSort);

            String displayString = "";
            for (int i = 0; i < 5; i++){
                if (valuesToSort.size() > i) {
                    displayString += "\n" + (i + 1) + ". " + mapFromCourseToSimilarity.get(valuesToSort.get(i)) +
                            ";" + " " + valuesToSort.get(i);
                }
            }




            JOptionPane.showMessageDialog(frame, "Beep Bop.. The Top Courses That Are The Most Similar To " +
                    " \n" + selectedCourse.getTitle() + " are: " + "\n" + displayString);



        }

        else if (n == 1){


            //Retrieve the department that the user inputs
            JOptionPane.showMessageDialog(frame, "First, please enter the department code that you want to " +
                    "receive recommendations for!");
            String departmentListing = (String)JOptionPane.showInputDialog(
                    frame,
                    "Enter the  Department Category",
                    "Recommendation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "cis");
            JOptionPane.showMessageDialog(frame, "You selected the " + departmentListing.toUpperCase()
                    + " department");


            JOptionPane.showMessageDialog(frame, "Now, please enter text that matches your interests");
            String result = (String)JOptionPane.showInputDialog(
                    frame,
                    "Enter the Course Description ",
                    "Recommendation",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    null,
                    "Artificial Intelligence and  Machine Learning"
            );


            ArrayList<Course> coursesInDepartment = courseInfo.getAllCoursesInSubject(departmentListing.toLowerCase());
            ArrayList<Document> documentsArrayList = new ArrayList<>();
            ArrayList<String> documentNames = new ArrayList<>();
            //Write the course query (based on the code to a new file)
            try {
                //relative path to write to a file that is not specified
                BufferedWriter writer = new BufferedWriter(
                        new FileWriter("./Classes/classDescription.txt"));
                writer.write(result);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Document queryCourseInput =  new Document("./Classes/classDescription.txt");
            documentsArrayList.add(queryCourseInput);
            documentNames.add(result);




            //add all the descriptions of the courses in the department
            for (Course c: coursesInDepartment){
                    //Write the course query (based on the code to a new file)
                    try {
                        //relative path to write to a file that is not specified
                        BufferedWriter writer = new BufferedWriter(
                                new FileWriter("./Classes/classDescription.txt"));
                        writer.write(c.getDescription());
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Document add =  new Document("./Classes/classDescription.txt");
                    documentsArrayList.add(add);
                    documentNames.add(c.getTitle());
                }



            //
            TreeMap< Double, String> mapFromCourseToSimilarity = new TreeMap<>();

            Corpus corpus = new Corpus(documentsArrayList);
            VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
            for(int i = 1; i < documentsArrayList.size(); i++) {
                Document doc = documentsArrayList.get(i);
                String name = documentNames.get(i);
                System.out.println("\nComparing to " + name);
                System.out.println(vectorSpace.cosineSimilarity(queryCourseInput, doc));

                //put the values to the tree map
                mapFromCourseToSimilarity.put( vectorSpace.cosineSimilarity(queryCourseInput, doc), name);
            }

            Set<Double> values = mapFromCourseToSimilarity.keySet();
            ArrayList<Double> valuesToSort = new ArrayList<>();
            for (Double val: values){
                valuesToSort.add(val);
            }
            Collections.sort(valuesToSort);
            Collections.reverse(valuesToSort);

            String displayString = "";
            for (int i = 0; i < 5; i++){
                if (i < valuesToSort.size()) {
                    displayString += "\n" + (i + 1) + ". " + mapFromCourseToSimilarity.get(valuesToSort.get(i)) +
                            ";" + " " + valuesToSort.get(i);
                }
            }




            JOptionPane.showMessageDialog(frame, "Beep Bop.. " +
                    "The Top  Courses In The  " + departmentListing.toUpperCase() + " department that" +
                    " are the most similar to the query,  " +    result   +
                    ", \n" + " are: " + "\n" + displayString);

        }
    }

}