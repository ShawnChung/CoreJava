package org.shawnana.corejava.streamsandfiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TextFileTest {
	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("emplyee.dat");
		Employee[] employees = new Employee[3];
		employees[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
		employees[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		employees[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);
		
		PrintWriter out = new PrintWriter(file);
		writeDatas(employees, out);
		out.close();
		
		Scanner in = new Scanner(file);
		
		Employee[] newStaff = readDatas(in);
		
		for (Employee employee : newStaff) {
			System.out.println(employee);
		}
		in.close();
		if (file.exists())
			file.delete();
	}

	private static Employee[] readDatas(Scanner in) {
		int count = in.nextInt();
		Employee[] newStaff = new Employee[count];
		in.nextLine();
		for (int i = 0; i < newStaff.length; i++) {
			newStaff[i] = new Employee();
			newStaff[i].readData(in);
		}
		return newStaff;
	}

	private static void writeDatas(Employee[] employees, PrintWriter out) {
		out.println(employees.length);
		for (Employee employee : employees) {
			employee.writeData(out);
		}
	}
}

class Employee implements Serializable {
	private String name;
	private double salary;
	private Date hireDay;
	
	public Employee(String n, double s, int year, int month, int day) {
		this.name = n;
		this.salary = s;
		GregorianCalendar calendar = new GregorianCalendar(year, month, day);
		this.hireDay = calendar.getTime();
	}
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public void raiseSalary(double byPercent) {
		this.salary = this.salary * (100.0 + byPercent) / 100.0;
	}
	
	public String toString() {
		return this.getClass().getSimpleName() + "[name=" + this.name + ",salary=" + this.salary
				+ ",hireDay=" + this.hireDay + "]";
	}
	
	public void writeData(PrintWriter out) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(this.hireDay);
		out.println(name + "|" + salary + "|" + calendar.get(Calendar.YEAR) + "|" 
				+ (calendar.get(Calendar.MONTH) + 1) + "|" + calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	public void readData(Scanner in) {
		String line = in.nextLine();
		String[] tokens = line.split("\\|");
		name = tokens[0];
		salary = Double.parseDouble(tokens[1]);
		int y = Integer.parseInt(tokens[2]);
		int m = Integer.parseInt(tokens[3]);
		int d = Integer.parseInt(tokens[4]);
		GregorianCalendar calendar = new GregorianCalendar(y, m-1, d);
		hireDay = calendar.getTime();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Date getHireDay() {
		return hireDay;
	}

	public void setHireDay(Date hireDay) {
		this.hireDay = hireDay;
	}
}
