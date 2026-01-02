import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {

    private static final String BASE_URL = "http://localhost:8080/api/cars";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.println("\n=== CAR MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Car");
            System.out.println("2. View Car by ID");
            System.out.println("3. View All Cars");
            System.out.println("4. Update Car");
            System.out.println("5. Delete Car");
            System.out.println("6. Fuel Up the Car");
            System.out.println("7. View Fuel Statistics");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            try {
                switch (choice) {
                    case 1 -> addCar();
                    case 2 -> viewCarById();
                    case 3 -> viewAllCars();
                    case 4 -> updateCar();
                    case 5 -> deleteCar();
                    case 6 -> fuelUp();
                    case 7 -> viewFuelStats();
                    case 8 -> {
                        System.out.println("👋 Goodbye!");
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
    }

    // ------------------------ ACTIONS ------------------------

    private static void addCar() throws Exception {
        System.out.print("Brand: ");
        String brand = scanner.nextLine();
        System.out.print("Model: ");
        String model = scanner.nextLine();
        System.out.print("Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        String payload = String.format(
                "{\"brand\":\"%s\",\"model\":\"%s\",\"year\":%d}",
                brand, model, year
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void viewCarById() throws Exception {
        System.out.print("Car ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    private static void viewAllCars() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }

    private static void updateCar() throws Exception {
        System.out.print("Car ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("New Brand: ");
        String brand = scanner.nextLine();
        System.out.print("New Model: ");
        String model = scanner.nextLine();
        System.out.print("New Year: ");
        int year = scanner.nextInt();
        scanner.nextLine();

        String payload = String.format(
                "{\"brand\":\"%s\",\"model\":\"%s\",\"year\":%d}",
                brand, model, year
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void deleteCar() throws Exception {
        System.out.print("Car ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void fuelUp() throws Exception {
        System.out.print("Car ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Liters: ");
        double liters = scanner.nextDouble();

        System.out.print("Odometer (km): ");
        double odometer = scanner.nextDouble();

        System.out.print("Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        String payload = String.format(
                "{\"liters\":%f,\"odometer\":%f,\"price\":%f}",
                liters, odometer, price
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id + "/fuel"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response: " + response.body());
    }

    private static void viewFuelStats() throws Exception {
        System.out.print("Car ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id + "/fuel/stats"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
