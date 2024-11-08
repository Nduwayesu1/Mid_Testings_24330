package UI;

import Dao.*;
import model1.*;
import model1.Enum.EBook_status;
import model1.Enum.ELocation_type;
import model1.Enum.ERole;
import model1.Enum.EStatus;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.UUID;

public class UserInterface {
    public static void main(String[] args) {
        LocationDao locationDao = new LocationDao();
        UserDao userDao = new UserDao(); // Initialize UserDao
        Membership_typeDao membershipTypeDao = new Membership_typeDao();
        MembershipDao membershipDao = new MembershipDao();
        BookDao bookDao = new BookDao();
        ShelfDao shelfDao = new ShelfDao();
        RoomDao roomDao= new RoomDao();
        BorrowerDao  borrowerDa= new BorrowerDao();
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
            System.out.println("8. Create Book");
            System.out.println("9. Create Shelf :");
            System.out.println("10 .Create Room :");
            System.out.println("11. Borrowing Book :");
            System.out.println("12. Check Number of Books :");
            System.out.println("13. Find Room with Fewest Books :");
            System.out.println("14.Find Province By person ID :");
            System.out.println("15. Return Book : ");
            System.out.println("16.Number Of Books in Room :");
            System.out.println("17.Get province By Id: ");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

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
                    create_membershipType(scanner, membershipTypeDao);
                    break;

                case 7:
                    createMembership(scanner, membershipDao);
                    break;

                case 8:
                    createBook(scanner, bookDao, shelfDao);
                    break;

                case 9:
                      createShelf(scanner,shelfDao,roomDao);
                      break;
                case 10:
                    createRoom(scanner,roomDao);
                    break;
                case 11:
                    initiateBorrowing(scanner);
                    break;
                case 12:
                    checkNumberOfBooks(bookDao);
                    break;
                case 13:
                    String room = roomDao.getRoomWithFewestBooks();
                    if (room != null) {
                        System.out.println("Room with the fewest books:");
                        System.out.println(room);
                    } else {
                        System.out.println("No room found or an error occurred.");
                    }
                    break;
                case 14:
                    displayProvinceByPersonId();
                    break;
                case 15:
                    promptAndProcessLateFee();
                    break;
                case 16:
                    numberOfBooks_inRoom();
                    break;
                case 17:
                    getUserProvinceNameFromInput();
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
                    parentLocation = locationDao.findByLocationCode(parentLocationCode);
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
            String personId = scanner.nextLine();

            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            System.out.print("Enter Phone Number: ");
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

            // Hash the password before creating the User
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Create the User with all required fields
            User user = new User(personId, firstName, lastName, userName, phoneNumber, hashedPassword, userRole, location);

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
            System.out.print("Enter user ID: ");
            String userIdInput = scanner.nextLine();

            // Validate that the user ID is not empty
            if (userIdInput.isEmpty()) {
                System.out.println("User ID cannot be empty.");
                return;
            }

            // Validate that the user ID can be parsed into a UUID
            UUID userId;
            try {
                // Parse the input into a UUID
                userId = UUID.fromString(userIdInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid User ID format. Please enter a valid UUID.");
                return;
            }

            // Prompt for the password
            System.out.print("Enter Username: ");
            String username = scanner.nextLine(); // Get the password input

            // Validate that the password is not empty
            if (username.isEmpty()) {
                System.out.println("Password cannot be empty.");
                return; // Exit if the password is invalid
            }

            // Authenticate the user with the plaintext password
            String authResult = userDao.authenticateUser(userId, username);
            System.out.println(authResult); // Print the result of authentication

        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
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
          String result= membershipTypeDao.saveMembershipType(membershipName, maxBooks, price,membership); // Pass the newMembershipType object
          System.out.println(result);

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
            System.out.print("Enter Membership status ");
            EStatus status = EStatus.valueOf(scanner.nextLine());

//            System.out.print("Enter person ID for association: ");
//            UUID userIdInput = UUID.fromString(scanner.nextLine().trim()); // Input from user



            System.out.print("Enter User ID: ");
            UUID userId = UUID.fromString(scanner.nextLine().trim()); // Input from user

            // Create the UUID from the cleaned string with hyphens


            // Retrieve the user from the database (assuming a UserDao is available)
            UserDao userDao = new UserDao();
            User user = userDao.getUserId(userId);

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

    public static void createBook(Scanner scanner, BookDao bookDao, ShelfDao shelfDao) {
        try {

            System.out.print("Enter book title: ");
            String title = scanner.nextLine();

            System.out.print("Enter ISBN code: ");
            String isbnCode = scanner.nextLine();

            System.out.print("Enter edition: ");
            int edition = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter publication year (YYYY-MM-DD): ");
            LocalDate publicationYear = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter publisher name: ");
            String publisherName = scanner.nextLine();

            System.out.print("Enter Book status  ");
            EBook_status status = EBook_status.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter shelf ID: ");
            String shelfIdInput = scanner.nextLine().trim();

            // Validate and convert shelf ID to UUID
            UUID shelfId = UUID.fromString(shelfIdInput);

            // Retrieve the shelf from the database
            Shelf shelf = shelfDao.findShelfById(shelfId);
            if (shelf == null) {
                System.out.println("No shelf found with the given ID.");
                return;
            }

            // Create a new Book instance
            Book newBook = new Book(
                    UUID.randomUUID(), // Generate a new UUID for the book
                    status,
                    edition,
                    isbnCode,
                    publicationYear,
                    publisherName,
                    shelf,
                    title
            );

            // Save the new book using the DAO
            bookDao.saveBook(newBook); // Pass the entire Book object

            // Confirm the book was created
            System.out.println("Book created successfully: " + newBook.getTitle());
        } catch (Exception e) {
            System.out.println("Error during book creation: " + e.getMessage());
            // Optionally log the error for further analysis
        }


    }


    public static void createShelf(Scanner scanner, ShelfDao shelfDao, RoomDao roomDao) {
        try {
            System.out.print("Enter book category: ");
            String bookCategory = scanner.nextLine();

            System.out.print("Enter initial stock: ");
            int initialStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter available stock: ");
            int availableStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter borrowed number: ");
            int borrowedNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter room ID: ");
            String roomIdInput = scanner.nextLine().trim();

            UUID roomId = UUID.fromString(roomIdInput);
            Room room = roomDao.findRoomById(roomId);
            if (room == null) {
                System.out.println("No room found with the given ID.");
                return;
            }

            Shelf newShelf = new Shelf();
            newShelf.setShelfId(UUID.randomUUID());
            newShelf.setBookCategory(bookCategory);
            newShelf.setInitialStock(initialStock);
            newShelf.setAvailableStock(availableStock);
            newShelf.setBorrowedNumber(borrowedNumber);
            newShelf.setRoom(room);

            String result = shelfDao.saveShelf(newShelf);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error during shelf creation: " + e.getMessage());
        }

    }

    public static void createRoom(Scanner scanner, RoomDao roomDao) {
        try {
            System.out.print("Enter room code: ");
            String roomCode = scanner.nextLine();

            // Create a new Room instance
            Room newRoom = new Room();
//            newRoom.setRoomId(UUID.randomUUID()); // Generate a new UUID for the room
            newRoom.setRoomCode(roomCode);

            // Save the new room using the DAO
            String result = roomDao.saveRoom(newRoom); // Pass the entire Room object
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error during room creation: " + e.getMessage());
        }
    }



    public static void initiateBorrowing(Scanner scanner) {
        try {
            // Get the user ID (reader)
            System.out.print("Enter user ID (reader): ");
            String userIdInput = scanner.nextLine().trim();
            UUID userId = UUID.fromString(userIdInput);

            // Get the book ID
            System.out.print("Enter book ID: ");
            String bookIdInput = scanner.nextLine().trim();
            UUID bookId = UUID.fromString(bookIdInput);

            // Get the pickup date
            System.out.print("Enter pickup date (YYYY-MM-DD): ");
            LocalDate pickupDate;
            try {
                pickupDate = LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                return;
            }

            // Call the borrowing method in BorrowerDao
            BorrowerDao borrowerDao =new BorrowerDao();
            String result = borrowerDao.borrowBook(bookId, userId, pickupDate);
            System.out.println(result);

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error during borrowing process: " + e.getMessage());
        }
    }

    public static void checkNumberOfBooks(BookDao bookDao) {
        try {
            int totalBooks = bookDao.getTotalNumberOfBooks();
            System.out.println("Total number of books in the system: " + totalBooks);
        } catch (Exception e) {
            System.out.println("Error retrieving book count: " + e.getMessage());
        }
    }

    public static void numberOfBooks_inRoom() {
        Scanner scanner = new Scanner(System.in);

        // Prompt user for Room ID input
        System.out.print("Please enter the Room ID (UUID format): ");
        String roomIdInput = scanner.nextLine();

        try {
            // Parse the user input into a UUID
            UUID roomId = UUID.fromString(roomIdInput);

            // Call the method to get the number of books
            RoomDao  room= new RoomDao();
            long bookCount = room.findNumberOfBooksInRoom(roomId);

            // Display the result
            System.out.println("Total number of books in the room: " + bookCount);

        } catch (IllegalArgumentException e) {
            // Handle invalid UUID input
            System.out.println("Invalid Room ID format. Please provide a valid UUID.");
        }
    }

    public static void displayProvinceByPersonId() {
        UserDao userDao = new UserDao();
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for person ID input
        System.out.print("Enter your Person ID: ");
        String personId = scanner.nextLine();

        // Get the province name based on the person ID
        String provinceName = userDao.getProvinceByPersonId(personId);

        // Display the province name or error message
        if (provinceName != null) {
            System.out.println("Province Name: " + provinceName);
        } else {
            System.out.println("No province found for the given Person ID.");
        }
    }



    public static void promptAndProcessLateFee() {
        FeeService feeService=new FeeService();
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter User ID (UUID format): ");
            UUID userId = UUID.fromString(scanner.nextLine());

            System.out.print("Enter Book ID (UUID format): ");
            UUID bookId = UUID.fromString(scanner.nextLine());

            System.out.print("Enter Return Date (yyyy-MM-dd): ");
            LocalDate returnDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            System.out.print("Enter Daily Late Fee: ");
            double dailyLateFee = Double.parseDouble(scanner.nextLine());

           String result= feeService.calculateAndUpdateLateFee(userId, bookId, returnDate, dailyLateFee);

            System.out.println(result);

        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    public static void getUserProvinceNameFromInput() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter User ID: ");
            String userIdInput = scanner.nextLine();  // Take input from user
            UUID userId = UUID.fromString(userIdInput);  // Convert input to UUID

            // Call getUserProvinceName and print result
            LocationDao  locationDao=new LocationDao();
            String provinceName = locationDao.getUserProvinceName(userId);
            System.out.println("Province: " + provinceName);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format. Please enter a valid User ID.");
        } finally {
            scanner.close();  // Close the scanner
        }
    }

}