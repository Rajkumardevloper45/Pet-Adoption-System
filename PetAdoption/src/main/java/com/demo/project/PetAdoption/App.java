package com.demo.project.PetAdoption;

import java.util.Scanner;

import com.demo.project.dao.AdminDao;
import com.demo.project.dao.CustomerDao;
import com.demo.project.dao.OwnerDao;
import com.demo.project.dao.DeliveryDao;
import com.demo.project.dao.PetAdoptionDao;
import com.demo.project.dao.PetDao;
import com.demo.project.dao.PetVarietyDao;
import com.demo.project.dao.SellerDao;
import com.demo.project.dao.impl.AdminDaoImpl;
import com.demo.project.dao.impl.CustomerDaoImpl;
import com.demo.project.dao.impl.OwnerDaoImpl;
import com.demo.project.dao.impl.DeliveryDaoImpl;
import com.demo.project.dao.impl.PetAdoptionDaoImpl;
import com.demo.project.dao.impl.PetDaoImpl;
import com.demo.project.dao.impl.PetVarietyDaoImpl;
import com.demo.project.dao.impl.SellerDaoImpl;

public class App {

    private static Scanner sc = new Scanner(System.in);
    private static AdminDao adminDao = new AdminDaoImpl();

    public static void main(String[] args) {

        boolean loggedIn = false;

        // Admin Login
        while (!loggedIn) {
            System.out.print("Enter admin username: ");
            String username = sc.nextLine();

            System.out.print("Enter admin password: ");
            String password = sc.nextLine();

            loggedIn = adminDao.login(username, password);

            if (!loggedIn) {
                System.out.println("Invalid username or password. Please try again.");
            }
        }

        System.out.println("Login successful" );
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Manage Customer");
            System.out.println("2. Manage Owner");
            System.out.println("3. Manage Seller");
            System.out.println("4. Manage Delivery");
            System.out.println("5. Manage Pet");
            System.out.println("6. Manage Pet Adoption");
            System.out.println("7. Manage Pet Variety");
            System.out.println("8. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageCustomer();
                    break;
                case 2:
                	manageOwner();
                	break;
                    
                case 3:
                    manageSeller();
                    break;
                case 4:
                    manageDelivery();
                    break;
                case 5:
                    managePet();
                    break;
                case 6:
                    managePetAdoption();
                    break;
                case 7:
                    managePetVariety();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageCustomer() {
        CustomerDao customerDao = new CustomerDaoImpl();
        System.out.println("Choose an operation for Customer:");
        System.out.println("1. Save Customer");
        System.out.println("2. View Customer");
        System.out.println("3. Edit Customer");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                customerDao.save();
                break;
            case 2:
                customerDao.view();
                break;
            case 3:
                customerDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void manageOwner() {
        OwnerDao ownerDao = new OwnerDaoImpl();
        System.out.println("Choose an operation for Owner:");
        System.out.println("1. Save Owner");
        System.out.println("2. View Owner");
        System.out.println("3. Edit Owner");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                ownerDao.save();
                break;
            case 2:
                ownerDao.view();
                break;
            case 3:
                ownerDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void manageSeller() {
        SellerDao sellerDao = new SellerDaoImpl();
        System.out.println("Choose an operation for Seller:");
        System.out.println("1. Save Seller");
        System.out.println("2. View Seller");
        System.out.println("3. Edit Seller");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                sellerDao.save();
                break;
            case 2:
                sellerDao.view();
                break;
            case 3:
                sellerDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void manageDelivery() {
        DeliveryDao deliveryDao = new DeliveryDaoImpl();
        System.out.println("Choose an operation for Delivery:");
        System.out.println("1. Save Delivery");
        System.out.println("2. View Delivery");
        System.out.println("3. Edit Delivery");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                deliveryDao.save();
                break;
            case 2:
                deliveryDao.view();
                break;
            case 3:
                deliveryDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void managePet() {
        PetDao petDao = new PetDaoImpl();
        System.out.println("Choose an operation for Pet:");
        System.out.println("1. Save Pet");
        System.out.println("2. View Pet");
        System.out.println("3. Edit Pet");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                petDao.save();
                break;
            case 2:
                petDao.view();
                break;
            case 3:
                petDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void managePetAdoption() {
        PetAdoptionDao petAdoptionDao = new PetAdoptionDaoImpl();
        System.out.println("Choose an operation for Pet Adoption:");
        System.out.println("1. Save Pet Adoption");
        System.out.println("2. View Pet Adoption");
        System.out.println("3. Edit Pet Adoption");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                petAdoptionDao.save();
                break;
            case 2:
                petAdoptionDao.view();
                break;
            case 3:
                petAdoptionDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void managePetVariety() {
        PetVarietyDao petVarietyDao = new PetVarietyDaoImpl();
        System.out.println("Choose an operation for Pet Variety:");
        System.out.println("1. Save Pet Variety");
        System.out.println("2. View Pet Variety");
        System.out.println("3. Edit Pet Variety");

        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                petVarietyDao.save();
                break;
            case 2:
                petVarietyDao.view();
                break;
            case 3:
                petVarietyDao.edit();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
