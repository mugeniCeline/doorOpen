package dao;

import java.util.List;
import org.hibernate.Session;
import Model.StudentCourse;

public class StudentCourseDao {
    
    public StudentCourse createStudentCourse(StudentCourse studentCourse) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.save(studentCourse);
            session.beginTransaction().commit();
            return studentCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public StudentCourse updateStudentCourse(StudentCourse studentCourse) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.update(studentCourse);
            session.beginTransaction().commit();
            return studentCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public StudentCourse deleteStudentCourse(StudentCourse studentCourse) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.delete(studentCourse);
            session.beginTransaction().commit();
            return studentCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public StudentCourse findStudentCourseById(Long studentCourseId) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            StudentCourse studentCourse = (StudentCourse) session.get(StudentCourse.class, studentCourseId);
            session.close();
            return studentCourse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<StudentCourse> getAllStudentCourses() {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            List<StudentCourse> studentCourses = session.createQuery("FROM StudentCourse").list();
            session.close();
            return studentCourses;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
