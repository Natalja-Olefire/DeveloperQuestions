package question2;

public class Dog {
	private String name;
	private String breed;
	private String registrationNumber;

	public Dog(String name, String breed, String registrationNumber) {
		this.name = name;
		this.breed = breed;
		this.registrationNumber = registrationNumber;
	}

	public String getName() {
		return this.name;
	}

	public String getBreed() {
		return this.breed;
	}

	public String getRegistrationNumber() {
		return this.registrationNumber;
	}
}
