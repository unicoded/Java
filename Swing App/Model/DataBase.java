package Model;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataBase {

	private List<Person> people;

	public DataBase() {
		people = new LinkedList<Person>();
	}

	public void AddPerson(Person person) {
		people.add(person);
	}
	
	public void removePerson(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		Person[] persons = people.toArray(new Person[people.size()]);

		oos.writeObject(persons);

		oos.close();
	}

	public void loadFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);

		try {
			Person[] persons = (Person[]) ois.readObject();

			people.clear();

			people.addAll(Arrays.asList(persons));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		ois.close();
	}
}
