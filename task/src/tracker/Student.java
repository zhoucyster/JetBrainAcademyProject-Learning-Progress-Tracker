package tracker;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Student {
    private final Integer id;
    private final String fName;
    private final String lName;
    private final String eMail;
    private Map<CourseNames,Integer> points = new HashMap<>();
    private double comparingCoursePoint = 0;
    private CourseNames comparingCourseName = null;
    private List<CourseNames> notNotifiedCourse = new ArrayList<>();

    public Student(Integer userId,  String fName, String lName, String eMail) {
        this.id = userId;
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        for (CourseNames name: CourseNames.values()) {
            points.put(name, 0);
        }
        notNotifiedCourse.addAll(Arrays.asList(CourseNames.values()));

    }

    public void setPoints(Integer javaPts,
                          Integer dsaPts,
                          Integer dbPts,
                          Integer springPts,
                          Map<CourseNames,Course> courses) throws Exception {
        if ( javaPts < 0 ||
             dsaPts < 0 ||
             dbPts < 0 ||
             springPts < 0 ){
            throw new Exception("points can't be negative");

        }
        Course course;
        this.points.put(CourseNames.Java,javaPts + this.points.get(CourseNames.Java));
        this.points.put(CourseNames.DSA,dsaPts + this.points.get(CourseNames.DSA));
        this.points.put(CourseNames.Databases,dbPts + this.points.get(CourseNames.Databases));
        this.points.put(CourseNames.Spring,springPts + this.points.get(CourseNames.Spring));
        if (javaPts > 0) {
            course = courses.get(CourseNames.Java);
            updateCourseInfo(course, javaPts);
        }
        if (dsaPts > 0) {
            course = courses.get(CourseNames.DSA);
            updateCourseInfo(course, dsaPts);
        }
        if (dbPts > 0) {
            course = courses.get(CourseNames.Databases);
            updateCourseInfo(course, dbPts);
        }
        if (springPts > 0) {
            course = courses.get(CourseNames.Spring);
            updateCourseInfo(course, springPts);
        }
    }

    @Override
    public String toString() {
        return id + " points: " + "Java=" + points.get(CourseNames.Java) + "; "
                                + "DSA=" + points.get(CourseNames.DSA) + "; "
                                + "Databases=" + points.get(CourseNames.Databases) + "; "
                                + "Spring=" + points.get(CourseNames.Spring);
    }

    private void updateCourseInfo(Course course,Integer point) {
        course.addStudent(this);
        course.updateCourseSubmission(point);
    }

    public void setComparingCoursePoints(CourseNames courseName) {
        comparingCoursePoint = this.points.get(courseName);
        comparingCourseName = courseName;
    }

    public double getComparingCoursePoint() {
        return comparingCoursePoint;
    }

    public void printCourseDetails() {
        BigDecimal bd = BigDecimal.valueOf((comparingCoursePoint / Course.pointsMap.get(comparingCourseName)) * 100);
        System.out.println(id + " " + (int) comparingCoursePoint + "    " + bd.setScale(1, RoundingMode.HALF_UP) +  "%" );

    }

    public Integer getId() {
        return id;
    }

    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public String geteMail() {
        return eMail;
    }

    public boolean printNofityEmail() {
        boolean hasCompletion=false;
        String emailBody;
        for (CourseNames name:CourseNames.values()) {
            if(this.points.get(name).equals(Course.pointsMap.get(name)) &&
               notNotifiedCourse.contains(name)) {
                emailBody = String.format("To: %s\nRe: Your Learning Progress\nHello, %s %s! You have accomplished our %s course!",
                        this.eMail,this.fName,this.lName,name);

                System.out.println(emailBody);
                notNotifiedCourse.remove(name);
                hasCompletion = true;
            }
        }
        return hasCompletion;
    }
}
