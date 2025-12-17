import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class currency_converter{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter base currency (example: USD, INR, EUR): ");
        String baseCurrency = sc.next().toUpperCase();

        System.out.print("Enter target currency (example: USD, INR, EUR): ");
        String targetCurrency = sc.next().toUpperCase();

        System.out.print("Enter amount to convert: ");
        double amount = sc.nextDouble();

        try {
            String apiUrl =
                "https://api.exchangerate-api.com/v4/latest/" + baseCurrency;

            URL url = new URL(apiUrl);
            HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader =
                new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String data = response.toString();
            int index = data.indexOf(targetCurrency);

            if (index == -1) {
                System.out.println("Invalid target currency.");
                return;
            }

            int start = data.indexOf(":", index) + 1;
            int end = data.indexOf(",", start);

            if (end == -1) {
                end = data.indexOf("}", start);
            }

            double rate =
                Double.parseDouble(data.substring(start, end));

            double convertedAmount = amount * rate;

            System.out.println("\n----- Conversion Result -----");
            System.out.println("Base Currency: " + baseCurrency);
            System.out.println("Target Currency: " + targetCurrency);
            System.out.println("Converted Amount: " + convertedAmount + " " + targetCurrency);

        } catch (Exception e) {
            System.out.println("Error fetching exchange rate.");
        }

        sc.close();
    }
}
