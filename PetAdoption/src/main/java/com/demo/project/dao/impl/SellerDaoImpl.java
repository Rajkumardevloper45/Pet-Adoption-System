package com.demo.project.dao.impl;

import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.SellerDao;
import com.demo.project.entity.Pet;
import com.demo.project.entity.Seller;
import com.demo.project.helper.HibernateHelper;

public class SellerDaoImpl implements SellerDao {

    private Session session;
    private Transaction t;
    private Scanner sc;

    public SellerDaoImpl() {
        session = HibernateHelper.getSessionFactory().openSession();
        t = session.beginTransaction();
        sc = new Scanner(System.in);
    }

    @Override
    public void save() {
        try {
            System.out.println("Enter seller name:");
            String name = sc.nextLine();

            System.out.println("Enter seller contact details:");
            String contactDetails = sc.nextLine();

            System.out.println("Enter pet ID associated with this seller:");
            Long petId = sc.nextLong();
            sc.nextLine(); // Consume newline

            Pet pet = session.get(Pet.class, petId);
            if (pet == null) {
                System.out.println("No pet found with ID: " + petId);
                return;
            }

            Seller seller = new Seller();
            seller.setName(name);
            seller.setContactDetails(contactDetails);
            seller.setPet(pet);

            session.save(seller);
            t.commit();
            System.out.println("Seller saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) t.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void view() {
        try {
            System.out.println("Enter seller ID to view:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume newline

            Seller seller = session.get(Seller.class, id);

            if (seller != null) {
                System.out.println("Seller Details:");
                System.out.println("ID: " + seller.getId());
                System.out.println("Name: " + seller.getName());
                System.out.println("Contact Details: " + seller.getContactDetails());
                System.out.println("Pet ID: " + (seller.getPet() != null ? seller.getPet().getId() : "N/A"));
            } else {
                System.out.println("No seller found with ID: " + id);
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
            System.out.println("Enter seller ID to edit:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume newline

            Seller seller = session.get(Seller.class, id);

            if (seller != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Name");
                System.out.println("2. Contact Details");
                System.out.println("3. Pet ID");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter new name:");
                        String newName = sc.nextLine();
                        seller.setName(newName);
                        break;

                    case 2:
                        System.out.println("Enter new contact details:");
                        String newContactDetails = sc.nextLine();
                        seller.setContactDetails(newContactDetails);
                        break;

                    case 3:
                        System.out.println("Enter new pet ID:");
                        Long newPetId = sc.nextLong();
                        sc.nextLine(); // Consume newline
                        Pet newPet = session.get(Pet.class, newPetId);
                        if (newPet != null) {
                            seller.setPet(newPet);
                        } else {
                            System.out.println("No pet found with ID: " + newPetId);
                        }
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                session.update(seller);
                t.commit();
                System.out.println("Seller details updated successfully.");
            } else {
                System.out.println("No seller found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) t.rollback();
        } finally {
            session.close();
        }
    }
}
