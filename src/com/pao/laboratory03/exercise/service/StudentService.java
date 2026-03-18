package com.pao.laboratory03.exercise.service;

import com.pao.laboratory03.exercise.exception.StudentNotFoundException;
import com.pao.laboratory03.exercise.model.Student;
import com.pao.laboratory03.exercise.model.Subject;

import java.util.*;

public class StudentService {
    private static StudentService instance;
    private List<Student> students;

    private StudentService() {
        this.students = new ArrayList<>();
    }
    public static StudentService getInstance() {
        if (instance == null) {
            instance = new StudentService();
        }
        return instance;
    }
    public void addStudent(String name, int age) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                throw new RuntimeException("Un student cu acest nume există deja!");
            }
        }
        students.add(new Student(name, age));
    }

    public Student findByName(String name) {
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        throw new StudentNotFoundException("Studentul '" + name + "' nu a fost găsit.");
    }

    public void addGrade(String studentName, Subject subject, double grade) {
        Student student = findByName(studentName);
        student.addGrade(subject, grade);
    }

    public void printAllStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți în sistem.");
            return;
        }
        for (Student s : students) {
            System.out.println(s + " | Note: " + s.getGrades());
        }
    }

    public void printTopStudents() {
        if (students.isEmpty()) {
            System.out.println("Nu există studenți.");
            return;
        }
        List<Student> sortedStudents = new ArrayList<>(students);
        sortedStudents.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));

        System.out.println("--- Top Studenți ---");
        for (Student s : sortedStudents) {
            System.out.println(s);
        }
    }
    public Map<Subject, Double> getAveragePerSubject() {
        Map<Subject, Double> sumMap = new HashMap<>();
        Map<Subject, Integer> countMap = new HashMap<>();

        for (Student s : students) {
            for (Map.Entry<Subject, Double> entry : s.getGrades().entrySet()) {
                Subject subject = entry.getKey();
                sumMap.put(subject, sumMap.getOrDefault(subject, 0.0) + entry.getValue());
                countMap.put(subject, countMap.getOrDefault(subject, 0) + 1);
            }
        }

        Map<Subject, Double> avgMap = new HashMap<>();
        for (Subject subject : sumMap.keySet()) {
            avgMap.put(subject, sumMap.get(subject) / countMap.get(subject));
        }

        return avgMap;
    }
}