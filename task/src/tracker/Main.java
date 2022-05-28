package tracker;

import java.util.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Map<Integer,Student> userMap = new TreeMap<>();
    private static Map<CourseNames,Course>  courseMap = new TreeMap<>();
    private static Set<Integer> userIds = new HashSet<>();
    private static Set<String>  userEmails = new HashSet<>();
    private static Integer initUserId = 10000;
    private static int cnt = 0;

    public static void main(String[] args) {
        String command="";
        System.out.println("Learning Progress Tracker");
        // initiate courseMap
        for (CourseNames name: CourseNames.values()) {
            courseMap.put(name,new Course(name));
        }
        while (scanner.hasNextLine()) {
            command = scanner.nextLine();
            if(command.isBlank())
                System.out.println("no input");
            else{
                switch (command.toLowerCase()){
                    case "exit":
                        System.out.println("Bye!");
                        return;
                    case "add students":
                        System.out.println("Enter student credentials or 'back' to return:");
                        verifyCredential();
                        break;
                    case "list":
                        listStudents();
                        break;
                    case "add points":
                        addPoints();
                        break;
                    case "find":
                        findById();
                        break;
                    case "statistics":
                        showStatistics();
                        break;
                    case "notify":
                        notifyStudents();
                        break;
                    case "back":
                        System.out.println("Enter 'exit' to exit the program.");
                        break;
                    default:
                        System.out.println("Unknown command!");
                        break;
                }
            }
        }
    }

    private static void verifyCredential(){
        int uid;
        String email;
        String studentCredential;
        String[] splitCredential;
        while(scanner.hasNextLine()){
            studentCredential = scanner.nextLine();
            if(studentCredential.equalsIgnoreCase("back")) {
                String total = String.format("Total %d students have been added",cnt);
                System.out.println(total);
                break;
            }
            splitCredential = studentCredential.split(" ");
            if (splitCredential.length < 3) {
                System.out.println("Incorrect credentials");
                continue;
            }
            // check credential
            CredentialValidation cv = new CredentialValidation(splitCredential);
            if (!cv.validateFirstName()) {
                System.out.println("Incorrect first name");
                continue;
            }
            if(!cv.validateLastName()) {
                System.out.println("Incorrect last name");
                continue;
            }
            if(!cv.validateEmail()) {
                System.out.println("Incorrect email");
                continue;
            }
            // check if the email is already registered.
            email = cv.geteMail();
            if(userEmails.contains(email)) {
                System.out.println("This email is already taken.");
                continue;
            }
            uid = initUserId++;
            userIds.add(uid);
            userEmails.add(email);
            userMap.put(uid, new Student(uid, cv.getfName(),cv.getlName(),cv.geteMail()));
            System.out.println("The student has been added.");
            cnt += 1;
        }
    }

    private static void listStudents() {
        if(userMap.size() == 0) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("Students:");
        for (Integer uid: userMap.keySet()) {
            System.out.println(uid);
        }
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        String line;
        String[] pointsData;
        Integer uid;
        Student student;
        while(scanner.hasNextLine()){
            line =  scanner.nextLine();
            if (line.equalsIgnoreCase("back")) {
                break;
            }
            pointsData = line.split(" ");
            if (pointsData.length !=5 ){
                System.out.println("Incorrect points format.");
                continue;
            }

            try {
                uid = Integer.parseInt(pointsData[0]);
            } catch (Exception e) {
                System.out.println("No student is found for id=" + pointsData[0]);
                continue;
            }
            if (!userIds.contains(uid)) {
                System.out.printf("No student is found for id=%d%n", uid);
                continue;
            }
            // verify points data, should be all positive integer
            // add points to Student object
            student = userMap.get(uid);
            try {
                student.setPoints(Integer.parseInt(pointsData[1]),
                        Integer.parseInt(pointsData[2]),
                        Integer.parseInt(pointsData[3]),
                        Integer.parseInt(pointsData[4]), courseMap);


                System.out.println("Points updated.");
            } catch (Exception e) {
                System.out.println("Incorrect points format.");
                e.printStackTrace();
            }
        }
    }

    private static void findById() {
        System.out.println("Enter an id or 'back' to return:");
        String line;
        Integer uid;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            if(line.equalsIgnoreCase("back")) {
                break;
            }
            try {
                uid = Integer.parseInt(line);
                if(userIds.contains(uid)){
                    System.out.println(userMap.get(uid));

                }
                else {
                    System.out.println("No student is found for id=" + uid);

                }
            } catch (Exception e) {
                System.out.println("Incorrect userId format");

            }
        }
    }

    private static void showStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        String line;
        Statistics sts = new Statistics(courseMap);

        System.out.println("Most popular: " + sts.mostPopular());
        System.out.println("Least popular: " + sts.leastPopular());
        System.out.println("Highest activity: " + sts.highestActivity());
        System.out.println("Lowest activity: " + sts.lowestActivity());
        System.out.println("Easiest course: " + sts.easiestCourse());
        System.out.println("Hardest course: " + sts.hardestCourse());

        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            if(line.equalsIgnoreCase("back")) {
                break;
            }
            switch (line){
                case "Java":
                    System.out.println("Java");
                    showCourseDetails(CourseNames.Java);
                    break;
                case "DSA":
                    System.out.println("DSA");
                    showCourseDetails(CourseNames.DSA);
                    break;
                case "Databases":
                    System.out.println("Databases");
                    showCourseDetails(CourseNames.Databases);
                    break;
                case "Spring":
                    System.out.println("Spring");
                    showCourseDetails(CourseNames.Spring);
                    break;
                default:
                    System.out.println("Unknown course.");
                    break;
            }
        }
    }

    public static void showCourseDetails(CourseNames courseName) {
        Course course;
        course = courseMap.get(courseName);
        List<Student> students = new ArrayList<>(course.getEnrolledStudents());
        for (Student student: students) {
            student.setComparingCoursePoints(courseName);
        }
        students.sort(Comparator.comparing(Student::getComparingCoursePoint).reversed().thenComparing(Student::getId));
        System.out.println("id    points    completed");
        students.forEach(Student::printCourseDetails);

    }

    private static void notifyStudents() {
        int cnt = 0;
        String totalNotified;
        for (Student student:userMap.values()) {
            if (student.printNofityEmail()){
                cnt++;
            }
        }
        totalNotified = String.format("Total %d students have been notified.", cnt);
        System.out.println(totalNotified);
    }

    public static int showCourseDetails(CourseNames courseName,Map<CourseNames,Course> courseMap) {
        Course course;
        course = courseMap.get(courseName);
        List<Student> students = new ArrayList<>(course.getEnrolledStudents());
        for (Student student: students) {
            student.setComparingCoursePoints(courseName);
        }
        students.sort(Comparator.comparing(Student::getComparingCoursePoint).reversed().thenComparing(Student::getId));
        System.out.println("id    points    completed");
        students.forEach(Student::printCourseDetails);
        return 0;
    }

}
