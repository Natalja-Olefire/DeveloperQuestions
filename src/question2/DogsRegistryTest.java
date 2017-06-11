package question2;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DogsRegistryTest {
	
	private DogsRegistry dogsRegistry = new DogsRegistry();
	private int initialSize = 0;
	
	
	@Before
	public void setup() {
		dogsRegistry.add(new Dog("Lucky", "Colli", "15"));
		dogsRegistry.add(new Dog("Lucky", "Terrier", "45"));
		dogsRegistry.add(new Dog("Lucky", "Husky", "29"));
		dogsRegistry.add(new Dog("Monkey", "Terrier", "65"));
		dogsRegistry.add(new Dog("Teddy", "Beagle", "34"));
		dogsRegistry.add(new Dog("Rocky", "Husky", "19"));
		dogsRegistry.add(new Dog("Princess", "Pekingese", "40"));
		dogsRegistry.add(new Dog("Princess", "Colli", "43"));
		dogsRegistry.add(new Dog("Princess", "Beagle", "45"));
		initialSize = dogsRegistry.dogs.size();
	}
	
	@After
	public void cleanup() {
		dogsRegistry.dogs.clear();
		initialSize = 0;
	}
	
	@Test
	public void testSortOrder() {
		NameBreedComparableDog[] dogs = dogsRegistry.dogs.toArray(new NameBreedComparableDog[0]);
		assertTrue("#0 dog has a reg. number 15", dogs[0].getRegistrationNumber().equals("15"));
		assertTrue("#1 dog has a reg. number 29", dogs[1].getRegistrationNumber().equals("29"));
		assertTrue("#2 dog has a reg. number 45", dogs[2].getRegistrationNumber().equals("45"));
		assertTrue("#5 dog has a reg. number 43", dogs[5].getRegistrationNumber().equals("43"));
		assertTrue("#8 dog has a reg. number 34", dogs[8].getRegistrationNumber().equals("34"));
	}
	
	@Test
	public void testAdd() {
		assertTrue("Dogs registry is ready for test", dogsRegistry.dogs.size() == initialSize);
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "28"));
		assertTrue("Dogs registry should be setup correctly before test", dogsRegistry.dogs.size() == initialSize + 1);
		NameBreedComparableDog[] dogs = dogsRegistry.dogs.toArray(new NameBreedComparableDog[0]);
		assertTrue("First dog is Buddy", "Buddy".equals(dogs[0].getName()));
	}

	@Test
	public void testAdd_SameNameAndBreed() {
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "89"));
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "87"));
		NameBreedComparableDog[] dogs = dogsRegistry.dogs.toArray(new NameBreedComparableDog[0]);
		assertTrue("#0 dog is Buddy with reg. number 87", "87".equals(dogs[0].getRegistrationNumber()));
		assertTrue("Dog with the same name/breed but different reg. number can be added", dogsRegistry.dogs.size() == initialSize + 2);
		assertTrue("#1 dog is Buddy with reg. number 89", "89".equals(dogs[1].getRegistrationNumber()));
	}
	
	@Test
	public void testAdd_SameNameBreedNumber() {
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "89"));
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "89"));
		dogsRegistry.add(new Dog("Buddy", "Chihuahua", "89"));
		NameBreedComparableDog[] dogs = dogsRegistry.dogs.toArray(new NameBreedComparableDog[0]);
		assertTrue("#0 dog is Buddy with reg. number 89", "89".equals(dogs[0].getRegistrationNumber()));
		assertTrue("Only one dog with the same name/breed/number can be added", dogsRegistry.dogs.size() == initialSize + 1);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testAdd_null() {
		dogsRegistry.add(null);
	}
	
	@Test
	public void testAdd_nullOrEmptyFields() {
		try {
			dogsRegistry.add(new Dog(null, "breed", "213"));
			fail("Dog's name cannot be null");
		} catch (IllegalArgumentException e) {
			// OK, next case
		}
		try {
			dogsRegistry.add(new Dog("", "breed", "213"));
			fail("Dog's name cannot be empty");
		} catch (IllegalArgumentException e) {
			// OK, next case
		}
		try {
			dogsRegistry.add(new Dog("name", null, "213"));
			fail("Dog's breed cannot be null");
		} catch (IllegalArgumentException e) {
			// OK, next case
		}
		try {
			dogsRegistry.add(new Dog("name", "breed", null));
			fail("Dog's breed cannot be null");
		} catch (IllegalArgumentException e) {
			// OK, next case
		}
		// OK, I'll skip couple of cases with empty fields
	}

	@Test
	public void testSearchByName_ManyFound() {
		List<Dog> found = dogsRegistry.searchByName("Princess");
		assertTrue("Should be found 3 dogs with the name Princess", found.size() == 3);
		assertTrue("First dog should have breed Beagle", "Beagle".equals(found.get(0).getBreed()));
		assertTrue("Second dog should have breed Colli", "Colli".equals(found.get(1).getBreed()));
	}
	
	@Test
	public void testSearchByName_CorrectOneFound() {
		List<Dog> found = dogsRegistry.searchByName("Teddy");
		assertTrue("Should be found 1 Teddy", found.size() == 1);
		assertTrue("Teddy's reg number is 34", "34".equals(found.get(0).getRegistrationNumber()));
		assertTrue("Teddy's breed is Beagle", "Beagle".equals(found.get(0).getBreed()));
	}
	
	@Test
	public void testSearchByName_NotFound() {
		List<Dog> found = dogsRegistry.searchByName("not added");
		assertTrue("Should be found nothing", found.size() == 0);
	}
	
	@Test
	public void testSearchByRegistrationNumber() {
		Dog found = dogsRegistry.searchByRegistrationNumber("34");
		assertTrue("Dog's reg number is 34", "34".equals(found.getRegistrationNumber()));
		assertTrue("Dog's name is Teddy", "Teddy".equals(found.getName()));
		assertTrue("Dog's breed is Beagle", "Beagle".equals(found.getBreed()));
		
	}
}