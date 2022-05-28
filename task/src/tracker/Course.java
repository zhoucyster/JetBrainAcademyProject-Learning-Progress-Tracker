package tracker;

import java.util.*;

public class Course {
    private CourseNames name;
    private Set<Student> enrolledStudents = new HashSet<>();
    private List<Integer> submissions = new ArrayList<>();
    private int totalPoints = 0;
    public static Map<CourseNames,Integer> pointsMap = new HashMap<>();

    {
        pointsMap.put(CourseNames.Java, 600);
        pointsMap.put(CourseNames.DSA, 400);
        pointsMap.put(CourseNames.Databases, 480);
        pointsMap.put(CourseNames.Spring, 550);
    }

    public Course(CourseNames name) {
        this.name = name;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void addStudent(Student student) {
        enrolledStudents.add(student);
    }

    public Double getEnrolledStudentsNumber() {
        return (double) enrolledStudents.size();
    }

    public Double getCourseSubmission() {
        return (double) submissions.size();
    }

    public Double getAvgScore() {
        double sum = 0;
        for (int point:submissions) {
            sum += point;
        }
        if (submissions.size() == 0) {
            return 0.00;
        }
        return sum/submissions.size();
    }

    public void updateCourseSubmission(Integer point) {
        this.submissions.add(point) ;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    @Override
    public String toString() {
        return name.toString();
    }
}
