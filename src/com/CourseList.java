package com;

import java.io.Serializable;
import java.util.*;
import java.lang.*;

public class CourseList implements Serializable {
    private static final long serialVersionUID = 3L;

    HashMap<String,Course> courses;

    public CourseList() {
        courses = new HashMap<String,Course>();
    }

    public Course getCourse(String courseId){
        return courses.get(courseId);
    }

    public Set<String> getCourseIds(){
        return courses.keySet();
    }

    public ArrayList<String> searchCourse(String keyword){
        // return a list of courseId(s) that contain the searched keyword
        Set <String> ids = courses.keySet();
        ArrayList <String> result = new ArrayList<>();
        for (String id: ids) {
            if (id.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(id);
            }
        }
        return result;
    }

    public boolean insertCourse(Course course){
        return (courses.put(course.getCourseId(),course) != null);
    }

    public boolean removeCourse(String courseId){
        return (courses.remove(courseId) != null);
    }
}
