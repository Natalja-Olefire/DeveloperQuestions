package question2;

/**
 * Wrapper class that allows to effectively use wrapped Dog in Java collections. 
 * Can be compared by name, breed, and registration number,
 * supports equals and hash code
 * @author Natalja Olefire
 *
 */
public class NameBreedComparableDog implements Comparable<NameBreedComparableDog> {

	/**
	 * Wrapped dog
	 */
	Dog dog;
	
	/**
	 * Do not instantiate this class directly, use <code>fromDog(Dog dog)</code> method
	 */
	private NameBreedComparableDog(Dog dog) {
		super();
		this.dog = dog;
	}

	/**
	 * Simple factory for dog wrapper
	 * @param dog
	 * @return
	 */
	public static NameBreedComparableDog fromDog(Dog dog) {
		return new NameBreedComparableDog(dog);
	}
	
	@Override
	public int compareTo(NameBreedComparableDog another) {
		if(another == null)
			return -1;
		int nameCompared = this.dog.getName().compareToIgnoreCase(another.getName());
		if (nameCompared == 0) {
			int breedCompared = this.dog.getBreed().compareToIgnoreCase(another.getBreed()); 
			if (breedCompared == 0) {
				return this.dog.getRegistrationNumber().compareToIgnoreCase(another.getRegistrationNumber());
			} else {
				return breedCompared;
			}
		} else {
			return nameCompared;
		}
	}

	@Override
	public String toString() {
		return "NameBreedComparableDog [name=" + this.dog.getName() + ", breed=" + this.dog.getBreed()
				+ ", regNumber=" + this.dog.getRegistrationNumber() + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.dog.getBreed() == null) ? 0 : this.dog.getBreed().hashCode());
		result = prime * result + ((this.dog.getName() == null) ? 0 : this.dog.getName().hashCode());
		result = prime * result + ((this.dog.getRegistrationNumber() == null) ? 0 : this.dog.getRegistrationNumber().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NameBreedComparableDog other = (NameBreedComparableDog) obj;
		if (this.dog.getBreed() == null) {
			if (other.getBreed() != null)
				return false;
		} else if (!this.dog.getBreed().equals(other.getBreed()))
			return false;
		if (this.dog.getName() == null) {
			if (other.getName() != null)
				return false;
		} else if (!this.dog.getName().equals(other.getName()))
			return false;
		if (this.dog.getRegistrationNumber() == null) {
			if (other.getRegistrationNumber() != null)
				return false;
		} else if (!this.dog.getRegistrationNumber().equals(other.getRegistrationNumber()))
			return false;
		return true;
	}

	public Dog getDog() {
		return dog;
	}
	
	public String getName() {
		return dog.getName();
	}

	public String getBreed() {
		return dog.getBreed();
	}

	public String getRegistrationNumber() {
		return dog.getRegistrationNumber();
	}

}
