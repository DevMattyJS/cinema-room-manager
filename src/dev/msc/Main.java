package dev.msc;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        char[][] cinemaHall = createCinemaHall(scanner);

        boolean exit = false;
        while(!exit) {
            printMenu();
            int choice = scanner.nextInt();
            switch(choice) {
                case 1:
                    printSeatingArrangement(cinemaHall);
                    break;
                case 2:
                    buyATicket(cinemaHall, scanner);
                    break;
                case 3:
                    printStatistics(cinemaHall);
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    printMenu();
            }
        }
    }

    public static char[][] createCinemaHall(Scanner scanner) {

        System.out.println("Enter the number of rows: ");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row: ");
        int seatsInRow = scanner.nextInt();
        char[][] cinemaHall = new char[rows][seatsInRow];

        for (int i = 0; i < cinemaHall.length; i++) {
            Arrays.fill(cinemaHall[i],'S');
        }

        return cinemaHall;

    }

    public static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void printSeatingArrangement(char[][] cinemaHall) {

        // Prints cinema + seat numbers
        System.out.println("Cinema:");
        System.out.print("  ");
        for(int k = 1; k <= cinemaHall[0].length; k++) {
            System.out.print(k + " ");
        }
        System.out.println();

        // Prints row number + seats from cinemaHall 2D array
        for (int i = 0; i < cinemaHall.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < cinemaHall[i].length; j++) {
                System.out.print(cinemaHall[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printStatistics(char[][] cinemaHall) {

        int purchasedTickets = calculateTicketsSold(cinemaHall)[0];
        double purchasedTicketsPercentage = calculateTicketsSoldPercentage(cinemaHall);
        int currentIncome = calculateTicketsSold(cinemaHall)[1];
        int totalIncome = calculateMaxPossibleIncome(cinemaHall);

        System.out.printf("Number of purchased tickets: %d\n", purchasedTickets);
        System.out.printf("Percentage: %.2f%%\n", purchasedTicketsPercentage);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n\n", totalIncome);

    }

    public static void buyATicket(char[][] cinemaHall, Scanner scanner) {

        int rows = cinemaHall.length;
        int cols = cinemaHall[0].length;

        while (true) {
            System.out.println("Enter a row number: ");
            int rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row: ");
            int seatNumber = scanner.nextInt();

            if ((rowNumber > rows) || (seatNumber > cols)) {
                System.out.println("Wrong Input!");
                continue;
            }

            if(cinemaHall[rowNumber - 1][seatNumber - 1] == 'B') {
                System.out.println("That ticket has already been purchased!");
                continue;
            }

            int ticketPrice = calculateTicketPrice(cinemaHall, rowNumber);
            System.out.printf("Ticket price: $%d\n", ticketPrice);
            cinemaHall[rowNumber-1][seatNumber-1] = 'B';
            break;
        }

    }

    public static int calculateTicketPrice(char[][] cinemaHall, int rowNumber) {

        int rows = cinemaHall.length;
        int seats = cinemaHall[0].length;
        int seatsTotal = rows * seats;

        if((seatsTotal <= 60) || (rowNumber <= rows / 2)) {
            return 10;
        } else {
            return 8;
        }
    }

    // Return int array [numberOfSoldTickets, totalPriceForThem]
    public static int[] calculateTicketsSold(char[][] cinemaHall) {

        int soldTickets = 0;
        int ticketPrice = 0;
        int totalTicketPrice = 0;
        for (int i = 0; i < cinemaHall.length; i++) {
            for (int j = 0; j < cinemaHall[i].length; j++) {
                if (cinemaHall[i][j] == 'B') {
                    soldTickets++;
                    ticketPrice = calculateTicketPrice(cinemaHall, i + 1);
                    totalTicketPrice += ticketPrice;
                }
            }
        }

        return new int[]{soldTickets, totalTicketPrice};
    }

    public static double calculateTicketsSoldPercentage(char[][] cinemaHall) {

        int rows = cinemaHall.length;
        int cols = cinemaHall[0].length;
        double totalSeats = (double) rows * cols;
        int soldTicketsQty = calculateTicketsSold(cinemaHall)[0];

        return (soldTicketsQty / totalSeats) * 100;

    }

    public static int calculateMaxPossibleIncome(char[][] cinemaHall) {

        int rows = cinemaHall.length;
        int seats = cinemaHall[0].length;
        int seatsTotal = rows * seats;
        int totalIncome = 0;

        if (seatsTotal > 60) {
            int rowsFirstHalf = rows / 2;
            int restRows = rows - rowsFirstHalf;
            totalIncome = (rowsFirstHalf * seats * 10) + (restRows * seats * 8);
        } else {
            totalIncome = seatsTotal * 10;
        }

        return totalIncome;
    }

}
