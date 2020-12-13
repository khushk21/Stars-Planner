package STARSapp;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class add_dummy_data {

	public static void main(String[] args) {
		
		GetUserInput g = new GetUserInput();
		AdminController ad = new AdminController();
		Scanner sc = new Scanner(System.in);
		
		Admin newAd = new Admin("Admin1", "password");
		Admin.saveData(newAd);

		
		/*email login
	     id: cz2002testreceive@gmail.com
	     password: c2002testreceivez
	     */
		
		Student student1 = new Student("schoolId", "Berry", "001", convertDate("1990-01-01"), 
				'M', "Singaporean", "password1", "cz2002testreceive@gmail.com");
		Student student2 = new Student("schoolId", "Terry", "002", convertDate("1990-02-02"), 
				'M', "Singaporean", "password2", "cz2002testreceive@gmail.com");
		Student student3 = new Student("schoolId", "Denise", "003", convertDate("1991-01-15"), 
				'F', "Singaporean", "password3", "cz2002testreceive@gmail.com");
		Student student4 = new Student("schoolId", "Jane", "004", convertDate("1993-05-21"), 
				'F', "Singaporean", "password4", "cz2002testreceive@gmail.com");
		Student student5 = new Student("schoolId", "John", "005", convertDate("1991-03-26"), 
				'M', "Singaporean", "password5", "cz2002testreceive@gmail.com");
		Student student6 = new Student("schoolId", "Tim", "006", convertDate("1992-05-26"), 
				'M', "Singaporean", "password6", "cz2002testreceive@gmail.com");
		Student student7 = new Student("schoolId", "Tosh", "007", convertDate("1991-11-05"), 
				'M', "Singaporean", "password7", "cz2002testreceive@gmail.com");
		Student student8 = new Student("schoolId", "Jasmine", "008", convertDate("1992-07-12"), 
				'F', "Singaporean", "password8", "cz2002testreceive@gmail.com");
		Student student9 = new Student("schoolId", "James", "009", convertDate("1992-10-02"), 
				'M', "Singaporean", "password9", "cz2002testreceive@gmail.com");
		Student student10 = new Student("schoolId", "Crystal", "010", convertDate("1991-09-02"), 
				'F', "Singaporean", "password10", "cz2002testreceive@gmail.com");
		Student student11 = new Student("schoolId", "Ken", "011", convertDate("1990-03-04"), 
				'M', "Singaporean", "password11", "cz2002testreceive@gmail.com");
		Student student12 = new Student("schoolId", "Thomas", "012", convertDate("1990-02-22"), 
				'M', "Singaporean", "password12", "cz2002testreceive@gmail.com");
		Student student13 = new Student("schoolId", "May", "013", convertDate("1991-12-29"), 
				'F', "Singaporean", "password13", "cz2002testreceive@gmail.com");
		Student student14 = new Student("schoolId", "Joseph", "014", convertDate("1993-04-24"), 
				'M', "Singaporean", "password14", "cz2002testreceive@gmail.com");
		Student student15 = new Student("schoolId", "Joyce", "015", convertDate("1991-08-01"), 
				'F', "Singaporean", "password15", "cz2002testreceive@gmail.com");
		
		//String m2 = student2.getMatric();
		Student.saveData(student1);
		Student.saveData(student2);
		Student.saveData(student3);
		Student.saveData(student4);
		Student.saveData(student5);
		Student.saveData(student6);
		Student.saveData(student7);
		Student.saveData(student8);
		Student.saveData(student9);
		Student.saveData(student10);
		Student.saveData(student11);
		Student.saveData(student12);
		Student.saveData(student13);
		Student.saveData(student14);
		Student.saveData(student15);
		
		
		//CZ2002 index 1
		Lesson cl1 = new Lesson(1, convertTime("08:30"), convertTime("09:30"), "LT-11", "1011");
		Lesson ct1 = new Lesson(3, convertTime("10:30"), convertTime("11:30"), "TR+16", "1011");
		Lesson clab1 = new Lesson(2, convertTime("08:30"), convertTime("10:30"), "SWL1", "1011");
		Index ci1 = new Index("1011", "CZ2002", 10);
		ci1.addLecture(cl1);
		ci1.setTut(ct1);
		ci1.setLab(clab1);
		//CZ2002 index 2
		Lesson ct2 = new Lesson(2, convertTime("14:30"), convertTime("15:30"), "TR+16", "1012");
		Lesson clab2 = new Lesson(4, convertTime("11:30"), convertTime("13:30"), "SWL1", "1012");
		Index ci2 = new Index("1012", "CZ2002", 10);
		ci2.regStu(student1.getMatric());
		ci2.addLecture(cl1);
		ci2.setTut(ct2);
		ci2.setLab(clab2);
		//CZ2002 index 3
		Lesson ct3 = new Lesson(4, convertTime("14:30"), convertTime("15:30"), "TR+16", "1013");
		Lesson clab3 = new Lesson(5, convertTime("09:30"), convertTime("11:30"), "SWL1", "1013");
		Index ci3 = new Index("1013", "CZ2002", 10);
		ci3.regStu(student2.getMatric());
		ci3.addLecture(cl1);
		ci3.setTut(ct3);
		ci3.setLab(clab3);
		
		ArrayList<Index> i_list = new ArrayList<Index>();
		i_list.add(ci1);
		i_list.add(ci2);
		i_list.add(ci3);
		
		Course c1 = new Course("OODP", "CZ2002", "SCSE", 3, i_list); 
		Course.saveData(c1);
		
		//CZ2005 index 1
		Lesson c2l1 = new Lesson(1, convertTime("11:30"), convertTime("12:30"), "LT-12", "1021");
		Lesson c2t1 = new Lesson(2, convertTime("12:30"), convertTime("13:30"), "TR+17", "1021");
		Lesson c2lab1 = new Lesson(3, convertTime("14:30"), convertTime("16:30"), "SWL3", "1021");
		Index c2i1 = new Index("1021", "CZ2005", 10);
		c2i1.addLecture(c2l1);
		c2i1.setTut(c2t1);
		c2i1.setLab(c2lab1);
		//CZ2005 index 2
		Lesson c2t2 = new Lesson(3, convertTime("12:30"), convertTime("13:30"), "TR+17", "1022");
		Lesson c2lab2 = new Lesson(4, convertTime("12:30"), convertTime("14:30"), "SWL3", "1022");
		Index c2i2 = new Index("1022", "CZ2005", 10);
		c2i2.addLecture(c2l1);
		c2i2.setTut(c2t2);
		c2i2.setLab(c2lab2);
		//CZ2005 index 3
		Lesson c2t3 = new Lesson(1, convertTime("14:30"), convertTime("15:30"), "TR+17", "1023");
		Lesson c2lab3 = new Lesson(4, convertTime("08:30"), convertTime("10:30"), "SWL3", "1023");
		Index c2i3 = new Index("1023", "CZ2005", 10);
		c2i3.addLecture(c2l1);
		c2i3.setTut(c2t3);
		c2i3.setLab(c2lab3);
		
		ArrayList<Index> i2_list = new ArrayList<Index>();
		i2_list.add(c2i1);
		i2_list.add(c2i2);
		i2_list.add(c2i3);
		
		Course c2 = new Course("OS", "CZ2005", "SCSE", 3, i2_list); 
		Course.saveData(c2);
		
		//CZ2001 index 1
		Lesson c3l1 = new Lesson(1, convertTime("12:30"), convertTime("13:30"), "LT-13", "1031");
		Lesson c3t1 = new Lesson(2, convertTime("10:30"), convertTime("11:30"), "TR+18", "1031");
		Lesson c3lab1 = new Lesson(3, convertTime("12:30"), convertTime("14:30"), "SWL2", "1031");
		Index c3i1 = new Index("1031", "CZ2001", 10);
		c3i1.addLecture(c3l1);
		c3i1.setTut(c3t1);
		c3i1.setLab(c3lab1);
		//CZ2001 index 2
		Lesson c3t2 = new Lesson(3, convertTime("14:30"), convertTime("15:30"), "TR+18", "1032");
		Lesson c3lab2 = new Lesson(4, convertTime("10:30"), convertTime("12:30"), "SWL2", "1032");
		Index c3i2 = new Index("1032", "CZ2001", 10);
		c3i2.addLecture(c3l1);
		c3i2.setTut(c3t2);
		c3i2.setLab(c3lab2);
		//CZ2001 index 3
		Lesson c3t3 = new Lesson(4, convertTime("14:30"), convertTime("15:30"), "TR+18", "1033");
		Lesson c3lab3 = new Lesson(5, convertTime("10:30"), convertTime("12:30"), "SWL2", "1033");
		Index c3i3 = new Index("1033", "CZ2001", 10);
		c3i3.addLecture(c3l1);
		c3i3.setTut(c3t3);
		c3i3.setLab(c3lab3);
		
		ArrayList<Index> i3_list = new ArrayList<Index>();
		i3_list.add(c3i1);
		i3_list.add(c3i2);
		i3_list.add(c3i3);
		
		Course c3 = new Course("Algorithms", "CZ2001", "SCSE", 3, i3_list); 
		Course.saveData(c3);
		
		//CZ2006 index 1
		Lesson c4l1 = new Lesson(1, convertTime("14:30"), convertTime("15:30"), "LT-14", "1041");
		Lesson c4t1 = new Lesson(2, convertTime("12:30"), convertTime("13:30"), "TR+15", "1041");
		Lesson c4lab1 = new Lesson(3, convertTime("12:30"), convertTime("14:30"), "HWL2", "1041");
		Index c4i1 = new Index("1041", "CZ2006", 10);
		c4i1.addLecture(c4l1);
		c4i1.setTut(c4t1);
		c4i1.setLab(c4lab1);
		//CZ2006 index 2
		Lesson c4t2 = new Lesson(3, convertTime("14:30"), convertTime("15:30"), "TR+15", "1042");
		Lesson c4lab2 = new Lesson(4, convertTime("11:30"), convertTime("13:30"), "HWL2", "1042");
		Index c4i2 = new Index("1042", "CZ2006", 10);
		c4i2.addLecture(c4l1);
		c4i2.setTut(c4t2);
		c4i2.setLab(c4lab2);
		//CZ2006 index 3
		Lesson c4t3 = new Lesson(4, convertTime("14:30"), convertTime("15:30"), "TR+15", "1043");
		Lesson c4lab3 = new Lesson(5, convertTime("10:30"), convertTime("12:30"), "HWL2", "1043");
		Index c4i3 = new Index("1043", "CZ2006", 0);
		c4i3.addLecture(c4l1);
		c4i3.setTut(c4t3);
		c4i3.setLab(c4lab3);
		c4i3.addStu_waitlist(student4.getMatric());
		c4i3.addStu_waitlist(student5.getMatric());
		c4i3.addStu_waitlist(student6.getMatric());
		
		ArrayList<Index> i4_list = new ArrayList<Index>();
		i4_list.add(c4i1);
		i4_list.add(c4i2);
		i4_list.add(c4i3);
		
		Course c4 = new Course("Software Engineering", "CZ2006", "SCSE", 3, i4_list); 
		Course.saveData(c4);
		
		
		Time AccessPeriod = new Time(convertDate("2020-11-17"), convertDate("2020-11-20"), 
				convertTime("10:00"), convertTime("22:00"));
		Time.saveData(AccessPeriod);
		
		
		
	}
	
	public static LocalTime convertTime(String s){ //should be moved to dummy_data class
		LocalTime t = null;
		try {
			t = LocalTime.parse(s) ;
		} catch (DateTimeParseException e) {
			System.out.println(s);
			System.out.println("Error!! Please Enter the Correct Time Format");
			System.out.println();
			return null;
		}
		return t;
	}
	
	public static LocalDate convertDate(String s){ //should be moved to dummy_data class
		LocalDate d = null;
		try {
			d = LocalDate.parse(s);
		} catch (DateTimeParseException e) {
			System.out.println("Error!! Please Enter the Correct Date Format");
			System.out.println();
			return null;
		}
		return d;
	}
	
	
}
