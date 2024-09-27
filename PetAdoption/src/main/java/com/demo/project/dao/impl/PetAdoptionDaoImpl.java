package com.demo.project.dao.impl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.PetAdoptionDao;
import com.demo.project.entity.PetAdoption;
import com.demo.project.entity.Pet;
import com.demo.project.entity.Customer;
import com.demo.project.helper.HibernateHelper;

public class PetAdoptionDaoImpl implements PetAdoptionDao {

	private Session session;
	private Transaction t;
	private Scanner sc;

	public PetAdoptionDaoImpl() {
		session = HibernateHelper.getSessionFactory().openSession();
		t = session.beginTransaction();
		sc = new Scanner(System.in);
	}

	@Override
	public void save() {
		try {
			System.out.println("Enter pet ID for adoption:");
			Long petId = sc.nextLong();
			sc.nextLine(); // Consume newline

			System.out.println("Enter customer ID for adoption:");
			Long customerId = sc.nextLong();
			sc.nextLine(); // Consume newline

			System.out.println("Enter adoption date (YYYY-MM-DD):");
			String adoptionDate = sc.nextLine();

			Pet pet = session.get(Pet.class, petId);
			if (pet == null) {
				System.out.println("No pet found with ID: " + petId);
				return;
			}

			Customer customer = session.get(Customer.class, customerId);
			if (customer == null) {
				System.out.println("No customer found with ID: " + customerId);
				return;
			}

			PetAdoption petAdoption = new PetAdoption();
			petAdoption.setPet(pet);
			petAdoption.setCustomer(customer);
			petAdoption.setAdoptionDate(adoptionDate);

			session.save(petAdoption);
			t.commit();
			System.out.println("Pet adoption saved successfully.");
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
			System.out.println("Enter pet adoption ID to view:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			PetAdoption petAdoption = session.get(PetAdoption.class, id);

			if (petAdoption != null) {
				System.out.println("Pet Adoption Details:");
				System.out.println("ID: " + petAdoption.getId());
				System.out.println("Pet ID: " + (petAdoption.getPet() != null ? petAdoption.getPet().getId() : "N/A"));
				System.out.println("Customer ID: "
						+ (petAdoption.getCustomer() != null ? petAdoption.getCustomer().getId() : "N/A"));
				System.out.println("Adoption Date: " + petAdoption.getAdoptionDate());
			} else {
				System.out.println("No pet adoption found with ID: " + id);
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
			System.out.println("Enter pet adoption ID to edit:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			PetAdoption petAdoption = session.get(PetAdoption.class, id);

			if (petAdoption != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Pet ID");
				System.out.println("2. Customer ID");
				System.out.println("3. Adoption Date");

				int choice = sc.nextInt();
				sc.nextLine(); // Consume newline

				switch (choice) {
				case 1:
					System.out.println("Enter new pet ID:");
					Long newPetId = sc.nextLong();
					sc.nextLine(); // Consume newline

					Pet newPet = session.get(Pet.class, newPetId);
					if (newPet == null) {
						System.out.println("No pet found with ID: " + newPetId);
						return;
					}
					petAdoption.setPet(newPet);
					break;

				case 2:
					System.out.println("Enter new customer ID:");
					Long newCustomerId = sc.nextLong();
					sc.nextLine(); // Consume newline

					Customer newCustomer = session.get(Customer.class, newCustomerId);
					if (newCustomer == null) {
						System.out.println("No customer found with ID: " + newCustomerId);
						return;
					}
					petAdoption.setCustomer(newCustomer);
					break;

				case 3:
					System.out.println("Enter new adoption date (YYYY-MM-DD):");
					String newAdoptionDate = sc.nextLine();
					petAdoption.setAdoptionDate(newAdoptionDate);
					break;

				default:
					System.out.println("Invalid choice.");
					return;
				}

				session.update(petAdoption);
				t.commit();
				System.out.println("Pet adoption details updated successfully.");
			} else {
				System.out.println("No pet adoption found with ID: " + id);
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
