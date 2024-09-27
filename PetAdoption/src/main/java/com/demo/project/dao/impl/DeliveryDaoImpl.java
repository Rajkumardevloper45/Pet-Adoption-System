package com.demo.project.dao.impl;


import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.project.dao.DeliveryDao;
import com.demo.project.entity.Delivery;
import com.demo.project.entity.Pet;
import com.demo.project.helper.HibernateHelper;

public class DeliveryDaoImpl implements DeliveryDao {

    private Session session;
    private Transaction t;
    private Scanner sc;

    public DeliveryDaoImpl() {
        session = HibernateHelper.getSessionFactory().openSession();
        t = session.beginTransaction();
        sc = new Scanner(System.in);
    }

    @Override
    public void save() {
        try {
            System.out.println("Enter delivery address:");
            String deliveryAddress = sc.nextLine();

            System.out.println("Enter delivery date (YYYY-MM-DD):");
            String deliveryDate = sc.nextLine();

            System.out.println("Enter pet ID associated with this delivery:");
            Long petId = sc.nextLong();
            sc.nextLine(); // Consume newline

            Pet pet = session.get(Pet.class, petId);
            if (pet == null) {
                System.out.println("No pet found with ID: " + petId);
                return;
            }

            Delivery delivery = new Delivery();
            delivery.setDeliveryAddress(deliveryAddress);
            delivery.setDeliveryDate(deliveryDate);
            delivery.setPet(pet);

            session.save(delivery);
            t.commit();
            System.out.println("Delivery saved successfully.");
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
            System.out.println("Enter delivery ID to view:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume newline

            Delivery delivery = session.get(Delivery.class, id);

            if (delivery != null) {
                System.out.println("Delivery Details:");
                System.out.println("ID: " + delivery.getId());
                System.out.println("Delivery Address: " + delivery.getDeliveryAddress());
                System.out.println("Delivery Date: " + delivery.getDeliveryDate());
                System.out.println("Pet ID: " + (delivery.getPet() != null ? delivery.getPet().getId() : "N/A"));
            } else {
                System.out.println("No delivery found with ID: " + id);
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
            System.out.println("Enter delivery ID to edit:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume newline

            Delivery delivery = session.get(Delivery.class, id);

            if (delivery != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Delivery Address");
                System.out.println("2. Delivery Date");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter new delivery address:");
                        String newAddress = sc.nextLine();
                        delivery.setDeliveryAddress(newAddress);
                        break;

                    case 2:
                        System.out.println("Enter new delivery date (YYYY-MM-DD):");
                        String newDate = sc.nextLine();
                        delivery.setDeliveryDate(newDate);
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                session.update(delivery);
                t.commit();
                System.out.println("Delivery details updated successfully.");
            } else {
                System.out.println("No delivery found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) t.rollback();
        } finally {
            session.close();
        }
    }
}
