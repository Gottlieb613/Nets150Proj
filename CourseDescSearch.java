public class CourseDescSearch {
    //Goes to particular subject page as asked by user
    // gets all courses on that page along with their descriptions
    // (potentially in Course objects, but maybe not idc)
    // Asks user to give a list of relevant search terms
    //    ex: machine graph theory math smart
    // Use document search as we learned in class (vector shenanigans)
    // Gets the top 5 courses with highest cosine similarity, spits em out

    private String subjCode;

    public CourseDescSearch(String subjCode) {
        this.subjCode = subjCode;
        //THEN go to the course page:
        // https://catalog.upenn.edu/courses/SUBJECT_CODE

    }

}
