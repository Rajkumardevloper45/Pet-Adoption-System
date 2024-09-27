package com.demo.project.dao.impl;

import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import jakarta.persistence.TypedQuery;
import com.demo.project.entity.Customer;
import com.demo.project.dao.*;
import com.demo.project.helper.HibernateHelper;

public class CustomerDaoImpl implements CustomerDao {

    private Session session;
    private Transaction t;
    private Scanner sc;

    public CustomerDaoImpl() {
        session = HibernateHelper.getSessionFactory().openSession();
        t = session.beginTransaction();
        sc = new Scanner(System.in);
    }

    @Override
    public void save() {
        try {
            System.out.println("Enter customer name:");
            String name = sc.nextLine();

            System.out.println("Enter customer contact details:");
            String contactDetails = sc.nextLine();

            Customer customer = new Customer();
            customer.setName(name);
            customer.setContactDetails(contactDetails);

            session.save(customer);
            t.commit();
            System.out.println("Customer saved successfully.");
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
            System.out.println("Enter customer ID to view:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume the newline

            Customer customer = session.get(Customer.class, id);

            if (customer != null) {
                System.out.println("Customer Details:");
                System.out.println("ID: " + customer.getId());
                System.out.println("Name: " + customer.getName());
                System.out.println("Contact Details: " + customer.getContactDetails());
            } else {
                System.out.println("No customer found with ID: " + id);
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
            System.out.println("Enter customer ID to edit:");
            Long id = sc.nextLong();
            sc.nextLine(); // Consume the newline

            Customer customer = session.get(Customer.class, id);

            if (customer != null) {
                System.out.println("What details do you want to modify?");
                System.out.println("1. Name");
                System.out.println("2. Contact Details");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        System.out.println("Enter new name:");
                        String newName = sc.nextLine();
                        customer.setName(newName);
                        break;

                    case 2:
                        System.out.println("Enter new contact details:");
                        String newContactDetails = sc.nextLine();
                        customer.setContactDetails(newContactDetails);
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        return;
                }

                session.update(customer);
                t.commit();
                System.out.println("Customer details updated successfully.");
            } else {
                System.out.println("No customer found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (t != null) t.rollback();
        } finally {
            session.close();
        }
    }
}
