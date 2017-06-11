package question2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Dogs registry. Maintains comparable by name and breed dogs list, 
 * allows to add dogs, and search them by name and registration number.
 * @author Natalja Olefire
 *
 */
public class DogsRegistry implements Registry<Dog> {
	
	// Tree set, that guarantees that our dogs are always sorted by name, and then breed
	NavigableSet<NameBreedComparableDog> dogs = new TreeSet<NameBreedComparableDog>();
	
	// Supporting hash table, there dogs are available by registration numbers.
	// Used for really fast dog access by registration number. 
	Map<String, Dog> registrationNumberToDog = new HashMap<String, Dog>();

	public DogsRegistry() {
		super();
	}

	/**
	 * Creates new registry from the passed one
	 * @param registry registry, containing list of dogs
	 */
	public DogsRegistry(DogsRegistry registry) {
		super();
		this.dogs = new TreeSet<NameBreedComparableDog>(registry.dogs);
		this.registrationNumberToDog = new HashMap<String, Dog>(registry.registrationNumberToDog);
	}
	
	/**
	 * Creates new registry from the passed list of dogs
	 * @param dogs list of dogs
	 */
	public DogsRegistry(List<Dog> dogs) {
		super();
		for (Dog dog : dogs) {
			this.dogs.add(NameBreedComparableDog.fromDog(dog));
			this.registrationNumberToDog.put(dog.getRegistrationNumber(), dog);
		}
	}

	/*
	 * Method used for dog instance validation. If some of the fields are missed, 
	 * or parameter itself is null, corresponding message is returned. 
	 * If dog is valid, null is returned.
	 * @param dog
	 * @return null if dog is valid, or error message otherwise
	 */
	String validateDog(Dog dog) {
		if (dog == null)
			return "Dog cannot be null";
		if (dog.getName() == null || dog.getName().isEmpty())
			return "Dog's name cannot be empty";
		if (dog.getBreed() == null || dog.getBreed().isEmpty())
			return "Dog's breed cannot be empty";
		if (dog.getRegistrationNumber() == null || dog.getRegistrationNumber().isEmpty())
			return "Dog's registration number cannot be empty";
		return null;
	}
	
	/**
	 * Adds dog to both internal structures - sorted tree and hash map.
	 * Throws {@code IllegalArgumentException} if dog instance is not valid.
	 */
	@Override
	public void add(final Dog dog) {
		String error = validateDog(dog);
		if (error != null) {
			throw new IllegalArgumentException(error);
		}
		dogs.add(NameBreedComparableDog.fromDog(dog));
		registrationNumberToDog.put(dog.getRegistrationNumber(), dog);
	}

	/**
	 * Search dog by registration number - just take one from a hash map, 
	 * takes constant time.
	 */
	@Override
	public Dog searchByRegistrationNumber(final String number) {
		if (number == null || number.isEmpty())
			throw new IllegalArgumentException("There cannot be registered dog with null or empty registration number");
		return registrationNumberToDog.get(number);
	}

	/**
	 * Search dog by name - as we have {@code NavigableTree} as a data structure, 
	 * and this tree is ordered by name, we just get view to subtree for particular name in the tree, 
	 * and return dogs from this subtree (keeping sorting by name/breed)
	 */
	@Override
	public List<Dog> searchByName(final String name) {
		if (name == null || name.isEmpty())
			throw new IllegalArgumentException("There cannot be registered dog with null or empty name");
		NameBreedComparableDog from = NameBreedComparableDog.fromDog(new Dog(name, String.valueOf(Character.MIN_VALUE), String.valueOf(Character.MIN_VALUE)));
		NameBreedComparableDog to = NameBreedComparableDog.fromDog(new Dog(name, String.valueOf(Character.MAX_VALUE), String.valueOf(Character.MAX_VALUE))); 
		return dogs.subSet(from, to).stream().map(comparableDog -> comparableDog.getDog()).collect(Collectors.toList());
	}
}
