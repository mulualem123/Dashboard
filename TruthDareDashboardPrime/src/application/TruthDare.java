package application;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.lang.*;

public class TruthDare {
	
	private SimpleIntegerProperty id;
	private SimpleStringProperty question;
	private SimpleIntegerProperty age;
	private SimpleStringProperty gender;
	
	public TruthDare(int id, String question, int age, String gender) {
		this.id = new SimpleIntegerProperty(id);
		this.question = new SimpleStringProperty(question);
		this.age = new SimpleIntegerProperty(age);
		this.gender = new SimpleStringProperty(gender);
	}
	
	public int isId() {
		return id.get();
	}

	public void setId(int id) {
		this.id.set(id);
	}

	public String isQuestion() {
		return question.get();
	}

	public void setQuestion(String question) {
		this.question.set(question);
	}

	public int isAge() {
		return age.get();
	}

	public void setAge(int age) {
		this.age.set(age);
	}

	public String isGender() {
		return gender.get();
	}

	public void setGender(String gender) {
		this.gender.set(gender);
	}
	
	
	
	
}
