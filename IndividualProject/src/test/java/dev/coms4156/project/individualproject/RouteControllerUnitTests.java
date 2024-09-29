package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit testing for RouteController.java.
 */
public class RouteControllerUnitTests {

  /**
   * We must setup the courses for the different departments.
   */
  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);

    departmentMapping = new HashMap<>();

    Map<String, Course> comsCourses = new HashMap<>();
    comsCourses.put("1004", new Course("Adam Cannon", "417 IAB", "11:40-12:55", 400));
    comsCourses.put("3134", new Course("Brian Borowski", "301 URIS", "4:10-5:25", 700));
    comsCourses.put("3157", new Course("Jae Lee", "417 IAB", "4:10-5:25", 400));
    departmentMapping = new HashMap<>();
    Department comsDept = new Department("COMS", comsCourses, "Luca Carloni", 2700);
    departmentMapping.put("COMS", comsDept);

    Map<String, Course> wgsCourses = new HashMap<>();
    wgsCourses.put("1000", new Course("Peggy Elsberg", "301 MIL", "4:10-5:25", 200));
    wgsCourses.put("1004", new Course("John Snow", "102 DIA", "10:10-11:25", 125));
    wgsCourses.put("3000", new Course("Alex Honnold", "301 URIS", "10:10-11:25", 100));
    Department wgsDept = new Department("WGS", wgsCourses, "Peggy Elsberg", 800);
    departmentMapping.put("Peggy Elsberg", wgsDept);

    String[] locations = {"417 IAB", "309 HAV", "301 URIS"};
    Map<String, Course> chemCourses = new HashMap<>();
    chemCourses.put("1403", new Course("Ruben M Savizky", locations[1], "6:10-7:25", 120));
    chemCourses.put("1004", new Course("Joseph C Ulichny", "302 HAV", "6:10-9:50", 46));
    chemCourses.put("2045", new Course("Luis M Campos", "209 HAV", "1:10-2:25", 50));
    Department chemDept = new Department("CHEM", chemCourses, "Laura J. Kaufman", 250);
    departmentMapping.put("CHEM", chemDept);


    String[] times = {"11:40-12:55", "4:10-5:25", "10:10-11:25", "2:40-3:55"};
    Map<String, Course> physCourses = new HashMap<>();
    physCourses.put("1001", new Course("Szabolcs Marka", "301 PUP", times[3], 150));
    physCourses.put("1201", new Course("Eric Raymer", "428 PUP", times[3], 145));
    physCourses.put("1602", new Course("Kerstin M Perez", "428 PUP", times[2], 140));
    Department physDept = new Department("PHYS", physCourses, "Laura J. Kaufman", 250);
    departmentMapping.put("PHYS", physDept);
    
    when(mockFileDatabase.getDepartmentMapping()).thenReturn(departmentMapping);

    IndividualProjectApplication.myFileDatabase = mockFileDatabase;
  }


  @Test
  public void retrieveDepartmentTestExists() {
    ResponseEntity<?> response = routeController.retrieveDepartment("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(departmentMapping.get("COMS").toString(), response.getBody());
  }

  @Test
  public void retrieveDepartmentTestDoesNotExist() {
    ResponseEntity<?> response = routeController.retrieveDepartment("ANTH");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void enrollStudentInCourseTestNotFound() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("ANTH", 1002);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void identifyDeptChair() {
    ResponseEntity<?> response = routeController.identifyDeptChair("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Luca Carloni is the department chair.", response.getBody());
  }

  @Test
  public void testChangeCourseTecher() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("COMS", 1004, "Kamala Harris");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseTecherFalse() {
    ResponseEntity<?> response = routeController.changeCourseTeacher("COMS", 3005, "Kamala Harris");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void testChangeCourseTime() {
    ResponseEntity<?> response = routeController.changeCourseTime("COMS", 1004, "5:00am-5:00pm");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Attributed was updated successfully.", response.getBody());
  }

  @Test
  public void testChangeCourseTimeFalse() {
    ResponseEntity<?> response = routeController.changeCourseTime("COMS", 3005, "5:00am-5:00pm");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void changeCourseLocTest() {
    ResponseEntity<?> response = routeController.changeCourseLocation("COMS", 1004, "BAR 301");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Attributed was updated successfully.", response.getBody());
  }


  @Test
  public void changeCourseLocFail() {
    ResponseEntity<?> response = routeController.changeCourseLocation("COMS", 3005, "BAR 301");
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }


  @Test
  public void enrollStudentInCourseTestBadReqest() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("COMS", 1004);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()); 
  }

  @Test
  public void enrollStudentInCourseTestGood() {
    ResponseEntity<?> response = routeController.enrollStudentInCourse("COMS", 3134);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void findCourseInstructorTest() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Adam Cannon is the instructor for the course.", response.getBody());
  }

  @Test
  public void findCourseInstructorTestFail() {
    ResponseEntity<?> response = routeController.findCourseInstructor("COMS", 3000);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found.", response.getBody());
  }

  @Test
  public void findCourseTimeTest() {
    ResponseEntity<?> response = routeController.findCourseTime("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("The course meets at: 11:40-12:55", response.getBody());
  }

  @Test
  public void findCourseTimeTestFail() {
    ResponseEntity<?> response = routeController.findCourseTime("COMS", 3000);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void findCourseLocationTest() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("417 IAB is where the course is located.", response.getBody());
  }

  @Test
  public void findCourseLocTestFail() {
    ResponseEntity<?> response = routeController.findCourseLocation("COMS", 3000);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }

  @Test
  public void addMajorToDeptTest() {
    ResponseEntity<?> response = routeController.addMajorToDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }

  @Test
  public void removeMajorFromDept() {
    ResponseEntity<?> response = routeController.removeMajorFromDept("COMS");
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
  }


  @Test
  public void dropStudentFromCourseTest() {
    ResponseEntity<?> response = routeController.dropStudent("COMS", 1004);
    assertEquals(HttpStatus.OK, response.getStatusCode()); 
    assertEquals("Student has been dropped.", response.getBody());
  }

  @Test
  public void dropStudentFromCourseFailTest() {
    ResponseEntity<?> response = routeController.dropStudent("COMS", 2020);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("Course Not Found", response.getBody());
  }
  

  @Test
  public void retrieveCourseTest1() {
    ResponseEntity<?> response = routeController.retrieveCourse("PHYS", 1000);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  @Test
  public void retrieveCourseTest2() {
    ResponseEntity<?> response = routeController.retrieveCourse("IEOR", 9);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
  }

  // @Test 
  // public void retrieveCoursesTestExistsWorks() {
  //   ResponseEntity<?> response = routeController.retrieveCourses(1004);
  //   assertEquals(HttpStatus.OK, response.getStatusCode()); 
  //   assertEquals("Courses found: 1. \n" +
  //   "Instructor: Joseph C Ulichny; Location: 302 HAV; Time: 6:10-9:50,2. \n" +
  //   "Instructor: Adam Cannon; Location: 417 IAB; Time: 11:40-12:5", response.getBody());
  // }

  @Test
  public void retrieveCoursesTestNoCourses() {
    ResponseEntity<?> response = routeController.retrieveCourses(9);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); 
    assertEquals("No courses found.", response.getBody());
  }


  /** Variables for database and route controller. */
  @Mock
  private MyFileDatabase mockFileDatabase;

  @InjectMocks
  private RouteController routeController;

  private Map<String, Department> departmentMapping;

}