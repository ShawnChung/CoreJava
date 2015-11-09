package org.shawnana.corejava.streamsandfiles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ByteFileTest {
	public static final int MAX_LENGTH = 40;
	public static void main(String[] args) throws IOException {
		File file = new File("emplyeeByte.dat");
		ByteFileTest bft = new ByteFileTest();
		Employee[] employees = new Employee[3];
		employees[0] = bft.new Employee("Carl Cracker", 75000, 1987, 12, 15);
		employees[1] = bft.new Employee("Harry Hacker", 50000, 1989, 10, 1);
		employees[2] = bft.new Employee("Tony Tester", 40000, 1990, 3, 15);
		
		DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
		writeDatas(out, employees);
		out.close();
		
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		Employee[] newStaff = readDatas(in, bft);
		in.close();
		for (Employee e : newStaff) 
			System.out.println(e);
		if (file.exists())
			file.delete();
	}
	
	private static Employee[] readDatas(DataInputStream in, ByteFileTest bft) throws IOException {
		int length = in.readInt();
		Employee[] newStaff = new Employee[length];
		for (int i = 0; i < newStaff.length; i++) {
			newStaff[i] = bft.new Employee();
			newStaff[i].readData(in);
		}
		return newStaff;
	}

	private static void writeDatas(DataOutputStream out, Employee[] employees) throws IOException {
		out.writeInt(employees.length);
		for (Employee employee : employees) {
			employee.writeData(out);
		}
	}

	class Employee {
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
		}

		public void raiseSalary(double byPercent) {
			this.salary = this.salary * (100.0 + byPercent) / 100.0;
		}
		
		public String toString() {
			return this.getClass().getSimpleName() + "[name=" + this.name + ",salary=" + this.salary
					+ ",hireDay=" + this.hireDay + "]";
		}
		
		public void writeData(DataOutputStream out) throws IOException {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(this.hireDay);
			DataIO.writeFixedLengthString(out, MAX_LENGTH, name);
			out.writeDouble(this.salary);
			out.writeInt(calendar.get(Calendar.YEAR));
			out.writeInt(calendar.get(Calendar.MONTH) + 1);
			out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));
			/*out.println(name + "|" + salary + "|" + calendar.get(Calendar.YEAR) + "|" 
					+ (calendar.get(Calendar.MONTH) + 1) + "|" + calendar.get(Calendar.DAY_OF_MONTH));*/
		}
		
		public void readData(DataInputStream in) throws IOException {
			name = DataIO.readFixedLengthString(in, MAX_LENGTH);
			salary = in.readDouble();
			int y = in.readInt();
			int m = in.readInt();
			int d = in.readInt();
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
	
	static class DataIO {

		public static void writeFixedLengthString(DataOutputStream out, int maxLength,
				String name) throws IOException {
			if (name.length() > maxLength)
				throw new RuntimeException();
			out.writeChars(name);
			for (int i = name.length(); i < maxLength; i++) {
				out.writeChar(0);
			}
		}

		public static String readFixedLengthString(DataInputStream in, int maxLength) throws IOException {
			char[] chars = new char[maxLength];
			int actualLength = 0;
			for (int i = 0; i < maxLength; i++) {
				char c = in.readChar();
				if (c != 0) {
					chars[i] = c;
					actualLength = i;
				}
				/*if (c == 0)
					break;*/
			}
			return String.valueOf(Arrays.copyOf(chars, actualLength + 1));
		}
		
	}
}



