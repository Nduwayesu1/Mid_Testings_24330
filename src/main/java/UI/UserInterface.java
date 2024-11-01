package UI;

import Dao.LocationDao;
import Dao.MembershipDao;
import Dao.Membership_typeDao;
import Dao.UserDao; // Ensure you have this DAO class to handle user operations
import model1.Enum.ELocation_type;
import model1.Enum.ERole; // Assuming you have an ERole enum
import model1.Enum.EStatus;
import model1.Location;
import model1.Membership;
import model1.Membership_type;
import model1.User; // Ensure you have the User class
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;

public class UserInterface {
    public static void main(String[] args) {
        LocationDao locationDao = new LocationDao();
        UserDao userDao = new UserDao(); // Initialize UserDao
        Membership_typeDao  membershipTypeDao= new Membership_typeDao();
        MembershipDao membershipDao=new MembershipDao();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Create Location");
            System.out.println("2. Search Province By Village");
            System.out.println("3. Create User");
            System.out.println("4. Search Province By ID: ");
            System.out.println("5. User Login");
            System.out.println("6. Create Membership Type");
            System.out.println("7. Create Membership");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    createLocation(scanner, locationDao);
                    break;
                case 2:
                    searchByVillage(scanner, locationDao);
                    break;
                case 3:
                    createUser(scanner, userDao);
                    break;
                case 4:
                    getLocationById(scanner, userDao);
                    break;

                case 5:
                    userLogin(scanner, userDao);
                    break;

                case 6:
                    create_membershipType(scanner,membershipTypeDao);
                    break;

                case 7:
                    createMembership(scanner,membershipDao);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    public static void createLocation(Scanner scanner, LocationDao locationDao) {
        try {
            System.out.print("Enter Location Code: ");
            String locationCode = scanner.nextLine();

            System.out.print("Enter Location Name: ");
            String locationName = scanner.nextLine();

            System.out.println("Select Location Type:");
            for (ELocation_type type : ELocation_type.values()) {
                System.out.println(type.ordinal() + ". " + type.name());
            }

            ELocation_type locationType = null;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Enter your choice (0-" + (ELocation_type.values().length - 1) + "): ");
                int typeChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (typeChoice >= 0 && typeChoice < ELocation_type.values().length) {
                    locationType = ELocation_type.values()[typeChoice];
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            // Handle parent location for nested locations
            Location parentLocation = null;
            if (locationType != ELocation_type.PROVINCE) {
                System.out.print("Enter Parent Location Code (or press Enter if none): ");
                String parentLocationCode = scanner.nextLine();

                if (!parentLocationCode.isEmpty()) {
                    parentLocation = locationDao.findByLocationCode(parentLocationCode); // Implement this method in LocationDao
                    if (parentLocation == null) {
                        System.out.println("Parent location not found. Please ensure it exists.");
                        return;
                    }
                }
            }

            Location location = new Location();
            location.setLocationCode(locationCode);
            location.setType(locationType);
            location.setLocationName(locationName);
            location.setParentLocation(parentLocation); // Set the parent location

            String savedLocation = locationDao.saveLocation(location);
            System.out.println("Location saved successfully: " + savedLocation);

        } catch (Exception e) {
            System.out.println("Error saving location: " + e.getMessage());
        }
    }

    public static void searchByVillage(Scanner scanner, LocationDao locationDao) {
        try {
            System.out.print("Enter Village Name: ");
            String villageName = scanner.nextLine();

            // Use the LocationDao to search for the province by village name
            String provinceName = locationDao.searchByVillages(villageName);

            if (provinceName != null) {
                System.out.println("The province for the village '" + villageName + "' is: " + provinceName);
            } else {
                System.out.println("No province found for the village '" + villageName + "'.");
            }
        } catch (Exception e) {
            System.out.println("Error searching for village: " + e.getMessage());
        }
    }

    public static void createUser(Scanner scanner, UserDao userDao) {
        try {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Username: ");
            String userName = scanner.nextLine();

            System.out.print("Enter Person ID: ");
            String personId = scanner.nextLine(); // Consider using this if needed in Person

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.print("Enter PhoneNumber: ");
            String phoneNumber = scanner.nextLine();

            System.out.println("Select User Role:");
            for (ERole role : ERole.values()) {
                System.out.println(role.ordinal() + ". " + role.name());
            }

            ERole userRole = null;
            boolean validChoice = false;

            while (!validChoice) {
                System.out.print("Enter your choice (0-" + (ERole.values().length - 1) + "): ");
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                if (roleChoice >= 0 && roleChoice < ERole.values().length) {
                    userRole = ERole.values()[roleChoice];
                    validChoice = true;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

            System.out.print("Enter Location Code: ");
            String locationCode = scanner.nextLine();
            Location location = userDao.findLocationByCode(locationCode);

            if (location == null) {
                System.out.println("Location not found. Please ensure it exists.");
                return;
            }

            // Create the User with all required fields
            User user = new User(personId, firstName, lastName, userName, password, phoneNumber, userRole, location);

            String savedUser = userDao.saveUser(user);
            System.out.println(savedUser);

        } catch (Exception e) {
            System.out.println("Error creating user: " + e.getMessage());
        }
    }

    public static void getLocationById(Scanner scanner, UserDao userDao) {
        try {
            System.out.print("Enter Person ID: ");
            String personId = scanner.nextLine();

            // Call the method in UserDao to get the location name
            String locationName = userDao.getLocationByPersonId(personId);

            System.out.println(locationName);
        } catch (Exception e) {
            System.out.println("Error retrieving location: " + e.getMessage());
        }
    }

    public static void userLogin(Scanner scanner, UserDao userDao) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            // Validate that the username is not empty
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                return; // Exit if the username is invalid
            }

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            // Validate that the password is not empty
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                return; // Exit if the password is invalid
            }

            // Call the authenticate method in UserDao
            User authenticatedUser = userDao.authenticateUser(username, password);

            // Check if the user was authenticated
            if (authenticatedUser != null) {
                System.out.println("Login successful! Welcome, " + authenticatedUser.getFirstName() + "!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            // Optionally log the error for further analysis
        }


    }


    public static void create_membershipType(Scanner scanner, Membership_typeDao membershipType) {
        try {
            System.out.print("Enter membership name: ");
            String membershipName = scanner.nextLine();

            System.out.print("Enter maximum number of books allowed: ");
            Integer maxBooks = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter price per day: ");
            Integer price = Integer.parseInt(scanner.nextLine());

            // Prompt for membership details
            System.out.print("Enter membership ID for association (leave empty for new membership): ");
            String membershipIdInput = scanner.nextLine();
            Membership membership = null;

            if (!membershipIdInput.isEmpty()) {
                // Assuming a method to retrieve membership by ID
                UUID membershipId = UUID.fromString(membershipIdInput);
                // Retrieve the membership from the database
                Membership_typeDao membershipTypeDao = new Membership_typeDao();
                membership = membershipTypeDao.findMembershipById(membershipId);

                if (membership == null) {
                    System.out.println("No membership found with the given ID.");
                    return;
                }
            }

            // Create a new Membership_type instance
            Membership_type newMembershipType = new Membership_type(
                    UUID.randomUUID(), membershipName, maxBooks, price, membership
            );

            // Create a MembershipTypeDao instance
            Membership_typeDao membershipTypeDao = new Membership_typeDao();

            // Save the new membership type
            membershipTypeDao.saveMembershipType(membershipName,maxBooks,price); // Pass the newMembershipType object

            // Confirm the membership type was created
            System.out.println("Membership type created successfully: " + newMembershipType.getMembershipName());
        } catch (Exception e) {
            System.out.println("Error during membership type creation: " + e.getMessage());
            // Optionally log the error for further analysis
        }
    }
    public static void createMembership(Scanner scanner, MembershipDao membershipDao) {
        try {
            System.out.print("Enter membership code: ");
            String membershipCode = scanner.nextLine();

            System.out.print("Enter expiring time (YYYY-MM-DD): ");
            LocalDate expiringTime = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter price per day: ");
            int pricePerDay = Integer.parseInt(scanner.nextLine());

            // Additional user input for status
            System.out.print("Enter status (e.g., ACTIVE, INACTIVE): ");
            EStatus status = EStatus.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter person ID for association: ");
            String userIdInput = scanner.nextLine().trim(); // Input from user

            // Remove the 0x prefix if present
            if (userIdInput.startsWith("0x")) {
                userIdInput = userIdInput.substring(2);
            }

            // Clean up the string: keep only valid hex characters
            userIdInput = userIdInput.replaceAll("[^0-9A-Fa-f]", "").trim();

            // Check if the cleaned input is valid (32 hex digits)
            if (userIdInput.length() != 32) {
                System.out.println("Invalid UUID format. Please enter a valid UUID string (32 hex digits).");
                return;
            }

            // Create the UUID from the cleaned string with hyphens
            UUID userId = UUID.fromString(
                    userIdInput.substring(0, 8) + "-" +
                            userIdInput.substring(8, 12) + "-" +
                            userIdInput.substring(12, 16) + "-" +
                            userIdInput.substring(16, 20) + "-" +
                            userIdInput.substring(20)
            );

            // Retrieve the user from the database (assuming a UserDao is available)
            UserDao userDao = new UserDao();
            User user = userDao.personId(userId);

            if (user == null) {
                System.out.println("No user found with the given ID.");
                return;
            }

            // Create a new Membership instance
            Membership newMembership = new Membership(
                    UUID.randomUUID(), // Generate a new UUID for membership
                    membershipCode,
                    expiringTime,
                    pricePerDay,
                    null, // Initially no types, can be added later
                    status,
                    user,
                    LocalDate.now() // Registration date as today
            );

            // Save the new membership using the DAO
            membershipDao.registerMembership(newMembership); // Pass the entire Membership object

            // Confirm the membership was created
            System.out.println("Membership created successfully: " + newMembership.getMembershipCode());
        } catch (Exception e) {
            System.out.println("Error during membership creation: " + e.getMessage());
            // Optionally log the error for further analysis
        }
    }



}
