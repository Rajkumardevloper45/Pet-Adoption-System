package com.demo.project.dao.impl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.OwnerDao;
import com.demo.project.entity.Owner;
import com.demo.project.helper.HibernateHelper;

public class OwnerDaoImpl implements OwnerDao {

	private Session session;
	private Transaction t;
	private Scanner sc;

	public OwnerDaoImpl() {
		session = HibernateHelper.getSessionFactory().openSession();
		t = session.beginTransaction();
		sc = new Scanner(System.in);
	}

	@Override
	public void save() {
		try {
			System.out.println("Enter owner name:");
			String name = sc.nextLine();

			System.out.println("Enter owner address:");
			String address = sc.nextLine();

			Owner owner = new Owner();
			owner.setName(name);
			owner.setAddress(address);

			session.save(owner);
			t.commit();
			System.out.println("Owner saved successfully.");
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
			System.out.println("Enter owner ID to view:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			Owner owner = session.get(Owner.class, id);

			if (owner != null) {
				System.out.println("Owner Details:");
				System.out.println("ID: " + owner.getId());
				System.out.println("Name: " + owner.getName());
				System.out.println("Address: " + owner.getAddress());
			} else {
				System.out.println("No owner found with ID: " + id);
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
			System.out.println("Enter owner ID to edit:");
			Long id = sc.nextLong();
			sc.nextLine(); // Consume newline

			Owner owner = session.get(Owner.class, id);

			if (owner != null) {
				System.out.println("What details do you want to modify?");
				System.out.println("1. Name");
				System.out.println("2. Address");

				int choice = sc.nextInt();
				sc.nextLine(); // Consume newline

				switch (choice) {
				case 1:
					System.out.println("Enter new name:");
					String newName = sc.nextLine();
					owner.setName(newName);
					break;

				case 2:
					System.out.println("Enter new address:");
					String newAddress = sc.nextLine();
					owner.setAddress(newAddress);
					break;

				default:
					System.out.println("Invalid choice.");
					return;
				}

				session.update(owner);
				t.commit();
				System.out.println("Owner details updated successfully.");
			} else {
				System.out.println("No owner found with ID: " + id);
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
