package tracker;

import java.util.*;
import java.util.function.Function;

public class Statistics {

    private final List<Course> courses = new ArrayList<>();
    private final boolean noCourseFlag;
    private String topResults = ""; // save mostPopular/highestActivity/Easiest courses
    private String bottomResults = ""; // save lestPopular/leastActivity/Hardes courses

    public Statistics(Map<CourseNames,Course> courseMap) {

        courses.addAll(courseMap.values());
        noCourseFlag = noCourseCheck(courses);
    }

    //The most popular has the biggest number of enrolled students
    public  String mostPopular() {
        courses.sort(Comparator.comparing(Course::getEnrolledStudentsNumber).reversed());
        if(noCourseFlag){
            return "n/a";
        }
        else {
            topResults = getFirstOneOrMore(courses,Course::getEnrolledStudentsNumber);
            return topResults;
        }
    }

    //The least popular has the smallest number of enrolled students
    public  String leastPopular(){
        courses.sort(Comparator.comparing(Course::getEnrolledStudentsNumber));
        bottomResults = getFirstOneOrMore(courses, Course::getEnrolledStudentsNumber);
//        System.out.println("topString: " + topString);
//        System.out.println("BottomResult " + bottomResult);
        if(noCourseFlag){
            return "n/a";
        }
        else if (topResults.equals(bottomResults)){
            return "n/a";
        }
        else {
            return bottomResults;
        }
    }

    //highest activity course has the most submissions
    public String highestActivity() {

        courses.sort(Comparator.comparing(Course::getCourseSubmission).reversed());
        if (noCourseFlag) {
            return "n/a";
        }
        else {
            topResults = getFirstOneOrMore(courses,Course::getCourseSubmission);
            return topResults;
        }

    }

    //least activity course has the least submissions
    public String lowestActivity() {
        courses.sort(Comparator.comparing(Course::getCourseSubmission));
        bottomResults = getFirstOneOrMore(courses,Course::getCourseSubmission);
        if (noCourseFlag) {
            return "n/a";
        }
        else if (topResults.equals(bottomResults)){
            return "n/a";
        }
        else {
            return bottomResults;
        }
    }

    //easiest course has the biggest avg score.
    public String easiestCourse() {
        courses.sort(Comparator.comparing(Course::getAvgScore).reversed());
        if (noCourseFlag) {
            return "n/a";
        }
        else {
            topResults = getFirstOneOrMore(courses,findAvgNonZeroCourse(courses), Course::getAvgScore);
            return topResults;
        }
    }

    //hardest course has smallest avg score
    public String hardestCourse() {
        courses.sort(Comparator.comparing(Course::getAvgScore));
        bottomResults = getFirstOneOrMore(courses,findAvgNonZeroCourse(courses), Course::getAvgScore);
        if (noCourseFlag) {
            return "n/a";
        }
        else if(topResults.equals(bottomResults)){
            return "n/a";
        }
        else {
            return bottomResults;
        }
    }

    private int findAvgNonZeroCourse(List<Course> courses) {
        for (Course crs: courses) {
            if (crs.getAvgScore() !=0) {
                return courses.indexOf(crs);
            }
        }
        return 0;
    }

    // check to see if there is no course at all, meaning no points were added to any courses.
    private boolean noCourseCheck(List<Course> courses) {
        boolean noCourse = true;
        for (Course crs: courses) {
            if (crs.getCourseSubmission() !=0) {
                noCourse = false;
            }
        }
        return noCourse;
    }

    private String getFirstOneOrMore(List<Course> courses, Function<Course, Double> courseMethod) {
        return this.getFirstOneOrMore(courses,0,courseMethod);
    }

    private String getFirstOneOrMore(List<Course> courses, int startIndex, Function<Course, Double> courseMethod) {
        StringBuilder firstString = new StringBuilder(courses.get(startIndex).toString());
        Double firstValue = courseMethod.apply(courses.get(startIndex));
        Course course;
        for (int i = startIndex + 1 ; i < courses.size(); i++ ){
            course = courses.get(i);
            if (firstValue.equals(courseMethod.apply(course))) {
                firstString.append(", ").append(course.toString());
            }
        }
        return firstString.toString();
    }
}
