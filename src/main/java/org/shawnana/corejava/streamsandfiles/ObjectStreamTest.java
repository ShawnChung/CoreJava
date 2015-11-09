package org.shawnana.corejava.streamsandfiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectStreamTest {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		File file = new File("emplyeeObjectStream.dat");
		Employee[] employees = new Employee[3];
		Manager manager = new Manager("Carl Cracker", 75000, 1987, 12, 15);
		employees[0] = manager;
		employees[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
		employees[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);
		manager.setSecretary(employees[1]);
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(employees);
		out.close();
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
		Employee[] newStaff = (Employee[]) in.readObject();
		in.close();
		for (Employee e : newStaff) {
			System.out.println(e);
		}
		if (file.exists())
			file.delete();
	}
	
	
}

class Manager extends Employee {
	public Manager(String n, double s, int year, int month, int day) {
		super(n, s, year, month, day);
		this.secretary = null;
	}
	
	public String toString() {
		return super.toString() + "[secretary=" + this.secretary + "]";
	}
	
	public Employee getSecretary() {
		return secretary;
	}

	public void setSecretary(Employee secretary) {
		this.secretary = secretary;
	}

	private Employee secretary;
}
