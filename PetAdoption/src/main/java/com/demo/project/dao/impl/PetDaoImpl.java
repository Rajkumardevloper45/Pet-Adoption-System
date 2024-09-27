package com.demo.project.dao.impl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.PetDao;
import com.demo.project.entity.Pet;
import com.demo.project.entity.Owner;
import com.demo.project.entity.PetVariety;
import com.demo.project.helper.HibernateHelper;

public class PetDaoImpl implements PetDao {

	private Session session;
	private Transaction t;
	private Scanner sc;

	public PetDaoImpl() {
		session = HibernateHelper.getSessionFactory().openSession();
		t = session.beginTransaction();
		sc = new Scanner(System.in);
	}

	@Override
	public void save() {
		try {
			System.out.println("Enter pet name:");
			String name = sc.nextLine();

			System.out.println("Enter pet age:");
			String age = sc.nextLine();

			System.out.println("Enter owner ID associated with this pet:");
			Long ownerId = sc.nextLong();
			sc.nextLine(); // Consume newline

			System.out.println("Enter pet variety ID associated with this pet:");
			Long petVarietyId = sc.nextLong();
			sc.nextLine(); // Consume newline

			Owner owner = session.get(Owner.class, ownerId);
			if (owner == null) {
				System.out.println("No owner found with ID: " + ownerId);
				return;
			}

			PetVariety petVariety = session.get(PetVariety.class, petVarietyId);
			if (petVariety == null) {
				System.out.println("No pet variety found with ID: " + petVarietyId);
				return;
			}

			Pet pet = new Pet();
			pet.setName(name);
			pet.setAge(age);
			pet.setOwner(owner);
			pet.setPetVariety(petVariety);

			session.save(pet);
			t.commit();
			System.out.println("Pet saved successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null)
				t.rollback();
		} finally {
			session.close();
		}
	}

	@Override
	public void view() {
		try {
			System.out.println("Enter pet ID to view:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			Pet pet = session.get(Pet.class, id);

			if (pet != null) {
				System.out.println("Pet Details:");
				System.out.println("ID: " + pet.getId());
				System.out.println("Name: " + pet.getName());
				System.out.println("Age: " + pet.getAge());
				System.out.println("Owner ID: " + (pet.getOwner() != null ? pet.getOwner().getId() : "N/A"));
				System.out.println(
						"Pet Variety ID: " + (pet.getPetVariety() != null ? pet.getPetVariety().getId() : "N/A"));
			} else {
				System.out.println("No pet found with ID: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	@Override
	public void edit() {
		try {
			System.out.println("Enter pet ID to edit:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			Pet pet = session.get(Pet.class, id);

			if (pet != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Name");
				System.out.println("2. Age");
				System.out.println("3. Owner ID");
				System.out.println("4. Pet Variety ID");

				int choice = sc.nextInt();
				sc.nextLine(); // Consume newline

				switch (choice) {
				case 1:
					System.out.println("Enter new name:");
					String newName = sc.nextLine();
					pet.setName(newName);
					break;

				case 2:
					System.out.println("Enter new age:");
					String newAge = sc.nextLine();
					pet.setAge(newAge);
					break;

				case 3:
					System.out.println("Enter new owner ID:");
					Long newOwnerId = sc.nextLong();
					sc.nextLine(); // Consume newline

					Owner newOwner = session.get(Owner.class, newOwnerId);
					if (newOwner == null) {
						System.out.println("No owner found with ID: " + newOwnerId);
						return;
					}
					pet.setOwner(newOwner);
					break;

				case 4:
					System.out.println("Enter new pet variety ID:");
					Long newPetVarietyId = sc.nextLong();
					sc.nextLine(); // Consume newline

					PetVariety newPetVariety = session.get(PetVariety.class, newPetVarietyId);
					if (newPetVariety == null) {
						System.out.println("No pet variety found with ID: " + newPetVarietyId);
						return;
					}
					pet.setPetVariety(newPetVariety);
					break;

				default:
					System.out.println("Invalid choice.");
					return;
				}

				session.update(pet);
				t.commit();
				System.out.println("Pet details updated successfully.");
			} else {
				System.out.println("No pet found with ID: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (t != null)
				t.rollback();
		} finally {
			session.close();
		}
	}
}
