import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static String[][] morningShift;  //? Declare seats as a class variable
    private static String[][] afternoonShift;  //? Declare seats as a class variable
    private static String[][] eveningShift;  //? Declare seats as a class variable
    private static String[] bookingHistory = new String[100];  //? Declare seats as a class variable
    private static String studentIdInput;
    private static int studentId = 0;

    private static String row;
    private static String col;

    static LocalDateTime localDateTime = LocalDateTime.now();
    static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    static String time = localDateTime.format(dateTimeFormatter);


    private static void show(String[][] display) {
        for (String[] row : display) {
            for (String seat : row) {
                System.out.print(seat + "\t");
            }
            System.out.println();
        }
    }


    private static String[][] createSeatsArray(int rows, int columns) {
        String[][] seats = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char rowChar = (char) ('A' + i);
                int seatNumber = j + 1;
                seats[i][j] = String.format("|%c-%d::AV|", rowChar, seatNumber);
            }
        }
        return seats;
    }
    public static void instruction() {
        System.out.println("--------------------------------");
        System.out.println("#  INSTRUCTION");
        System.out.println("#  Single : C-2");
        System.out.println("#  Multiple ( separated by comma ) : A-1,A-2");
    }

    static void showHistory() {
        if (bookingHistory.length == 0 || bookingHistory[0] == null) {
            System.out.println("---------------------------");
            System.out.println("No bookings yet.");
            System.out.println("---------------------------");
        } else {
            for (String history : bookingHistory) {
                if (history != null) {
                    String seats = history.substring(history.indexOf("[") + 1, history.indexOf("]"));
                    String hall = history.substring(history.indexOf("H"), history.indexOf("l") + 4);
                    String id = studentIdInput;
                    String date = history.substring(history.indexOf("l") + 9);

                    System.out.println("-------------------History-------------------------");
                    System.out.println("#HALL        #SEAT       #STU.ID         #CREATED AT");
                    System.out.println(String.format("%s       %s       %s       %s", hall,seats, id, date));
                    System.out.println("----------------------------------------------------");
                }
            }
        }
    }

    private static int regexInt(Scanner scanner, String text) {
        int userInput = 0;
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("-?\\d+");
            if(pattern.matcher(input).matches()){
                userInput = Integer.parseInt(input);
                isValid = true;
            } else {
                System.out.println("Invalid input. Please input number only!");
            }
        }
        return userInput;
    }
    private static String regexString(Scanner scanner, String text) {
        String validString = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("[a-zA-Z]+");
            if(pattern.matcher(input).matches()){
                validString = input;
                isValid = true;
            } else {
                System.out.println("Invalid input. Please input text only!");
            }
        }
        return validString;
    }
    private static String regexBook(Scanner scanner, String text) {
        String validString = "";
        boolean isValid = false;
        while (!isValid) {
            System.out.print(text);
            String input = scanner.nextLine();
            Pattern pattern = Pattern.compile("^[A-Z]-\\d+(,[A-Z]-\\d+)*$");
            if(pattern.matcher(input).matches()){
                validString = input;
                isValid = true;
            } else {
                System.out.println("Invalid input.");
            }
        }
        return validString;
    }

    public static void main(String[] args) {
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++" + "\n" +
                    "           CSTAD HALL BOOKING SYSTEM          " + "\n" +
                    "++++++++++++++++++++++++++++++++++++++++++++++++++");
            row = String.valueOf(regexInt(scanner,"> Config total rows in hall : "));
            col = String.valueOf(regexInt(scanner,"> Config total seats in hall : "));
                
        String options;
        morningShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
        afternoonShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
        eveningShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
        do {
            System.out.println("[[ Application Menu ]]");
            System.out.println("<A> Booking");
            System.out.println("<B> Hall");
            System.out.println("<C> Showtime");
            System.out.println("<D> Reboot Showtime");
            System.out.println("<E> History");
            System.out.println("<F> Exit");
            options = regexString(scanner,"Please select menu : ");
            switch (options.toLowerCase()) {
                case "a" -> {
                    String option;
                    System.out.println("-----------------Start Booking Process-----------------");
                    System.out.println("A) Morning (10:00AM - 12:00PM)");
                    System.out.println("B) Afternoon (03:00PM - 05:30PM)");
                    System.out.println("C) Night (07:00PM - 09:30PM)");
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
                    option = regexString(scanner,"Please select show time (A | B | C) : ");
                    switch (option.toLowerCase()) {
                        case "a" -> {
                            show(morningShift);
                            instruction();
                            String seatsToBookInput = regexBook(scanner, "Enter seat to book : ");
                            String[] seatsToBook = seatsToBookInput.split(",");
                            if (bookSeats(seatsToBook, morningShift, "A")) {
                                show(morningShift);
                            }
                        }
                        case "b" -> {
                            show(afternoonShift);
                            instruction();
                            String seatsToBookInput = regexBook(scanner, "Enter seat to book : ");
                            String[] seatsToBook = seatsToBookInput.split(",");
                            if (bookSeats(seatsToBook, afternoonShift, "B")) {
                               show(afternoonShift);
                            }
                        }
                        case "c" -> {
                            show(eveningShift);
                            instruction();
                            String seatsToBookInput = regexBook(scanner, "Enter seat to book : ");
                            String[] seatsToBook = seatsToBookInput.split(",");
                            if (bookSeats(seatsToBook, eveningShift, "C")) {
                                show(eveningShift);
                            }
                        }
                    }
                    System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
                }
                case "b" -> {
                    show(morningShift);
                    System.out.println("-------------------------------------------");
                    show(afternoonShift);
                    System.out.println("-------------------------------------------");
                    show(eveningShift);
                    System.out.println("-------------------------------------------");
                }
                case "c" -> {
                    System.out.println("-----------------Daily showtime of CSTAD Hall-----------------");
                    System.out.println("A) Morning (10:00AM - 12:00PM)");
                    System.out.println("B) Afternoon (03:00PM - 05:30PM)");
                    System.out.println("A) Night (07:00PM - 09:30PM)");
                    System.out.println("----------------------------------------------------------------");
                }
                case "d" -> {
                    System.out.println("Are you sure you want to reboot the hall? [Y/y] or [N/n] : ");
                    String opt = scanner.nextLine();
                    if (opt.equals("Y") || opt.equals("y")) {
                        morningShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
                        afternoonShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
                        eveningShift = createSeatsArray(Integer.parseInt(row), Integer.parseInt(col));
                        bookingHistory = new String[100];
                        studentId = 0;
                        System.out.println("------------------------------------");
                        System.out.println("    Hall rebooted successfully!    ");
                        System.out.println("------------------------------------");
                    }
                }
                case "e" -> {
                    showHistory();
                }
                case "f" -> {
                    System.out.print("Are you sure you want to Exit? [Y/y] or [N/n] : ");
                    String opt = scanner.nextLine();
                    if (opt.equals("Y") || opt.equals("y")) {
                        System.out.println("Good Bye");
                        System.exit(0);
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + options.toLowerCase());
            }
        } while (true);
    }

    private static boolean bookSeats(String[] seatCodes, String[][] halls, String hall) {
        var count = 0;
        // check to see if the seatCodes is multiple seats just skip the id
        for (String seatCode : seatCodes) {
            count++;
            String[] parts = seatCode.split("-");
            if (parts.length == 2) {
                char row = parts[0].charAt(0);
                int col = Integer.parseInt(parts[1]) - 1;

                if (isValidSeat(row, col) && halls[row - 'A'][col].contains("AV")) {
                        if (halls[row - 'A'][col].contains("BO")) {
                            System.out.println("Seat " + seatCode + " is not available.");
                            return false;
                        }
                        //input student id
                        if(count>1){
                            halls[row - 'A'][col] = halls[row - 'A'][col].replace("AV", "BO");
                            continue;
                        }
                        System.out.print("Enter Student ID: ");
                        studentIdInput = scanner.nextLine();
                        while (!studentIdInput.matches("[0-9]+")) {
                            System.out.println("Invalid input. Please try again");
                            System.out.print("Enter Student ID: ");
                            studentIdInput = scanner.nextLine();
                        }
                        //if already asked once, no need to ask again
                        System.out.print("Are you sure you want to book this seat? (Y/N)");
                        String confirm = scanner.nextLine().toUpperCase();
                        while (!confirm.matches("^[YNyn]$") || confirm.length() > 1) {
                            System.out.println("Invalid input. Please try again");
                            System.out.print("Are you sure you want to book this seat? (Y/N)");
                            confirm = scanner.nextLine().toUpperCase();
                        }
                        if (confirm.equals("Y")) {
                            hall = (hall.equalsIgnoreCase("A")?"Hall A":hall.equalsIgnoreCase("B")?"Hall B":hall.equalsIgnoreCase("C")?"Hall C":"");
                            halls[row - 'A'][col] = halls[row - 'A'][col].replace("AV", "BO");
                            bookingHistory[studentId] = Arrays.toString(seatCodes)+ hall + studentIdInput + " " + time;
                            ++studentId;
                            System.out.println("Seat booked successfully!");
                        } else {
                            System.out.println("Seat booking cancelled.");
                            return false;
                        }
                } else {
                    System.out.println("Seat " + seatCode + " is not available or invalid.");
                    return false;
                }
            } else {
                System.out.println("Invalid seat code: " + seatCode);
                return false;
            }

        }
        return true;


    }

    private static boolean isValidSeat(char row, int col) {
        return row >= 'A' && row < 'A' + morningShift.length && col >= 0 && col < morningShift[0].length;
    }
}
