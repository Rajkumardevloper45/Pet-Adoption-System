package com.demo.project.dao.impl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.PetVarietyDao;
import com.demo.project.entity.PetVariety;
import com.demo.project.helper.HibernateHelper;

public class PetVarietyDaoImpl implements PetVarietyDao {

	private Session session;
	private Transaction t;
	private Scanner sc;

	public PetVarietyDaoImpl() {
		session = HibernateHelper.getSessionFactory().openSession();
		t = session.beginTransaction();
		sc = new Scanner(System.in);
	}

	@Override
	public void save() {
		try {
			System.out.println("Enter pet variety name:");
			String varietyName = sc.nextLine();

			PetVariety petVariety = new PetVariety();
			petVariety.setVarietyName(varietyName);

			session.save(petVariety);
			t.commit();
			System.out.println("Pet variety saved successfully.");
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
			System.out.println("Enter pet variety ID to view:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			PetVariety petVariety = session.get(PetVariety.class, id);

			if (petVariety != null) {
				System.out.println("Pet Variety Details:");
				System.out.println("ID: " + petVariety.getId());
				System.out.println("Variety Name: " + petVariety.getVarietyName());
				System.out.println("Associated Pets: " + petVariety.getPets());
			} else {
				System.out.println("No pet variety found with ID: " + id);
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
			System.out.println("Enter pet variety ID to edit:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			PetVariety petVariety = session.get(PetVariety.class, id);

			if (petVariety != null) {
				System.out.println("Enter new pet variety name:");
				String newVarietyName = sc.nextLine();
				petVariety.setVarietyName(newVarietyName);

				session.update(petVariety);
				t.commit();
				System.out.println("Pet variety details updated successfully.");
			} else {
				System.out.println("No pet variety found with ID: " + id);
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
