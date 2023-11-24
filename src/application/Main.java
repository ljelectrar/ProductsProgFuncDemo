package application;

import entities.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter with full file path:");
        String path = sc.nextLine();

        try(BufferedReader br = new BufferedReader(new FileReader(path))){

            List<Product> productList = new ArrayList<>();
            String line = br.readLine();

            while (line != null){
                String[] fields = line.split(", ");
                String name = fields[0];
                double price = Double.parseDouble(fields[1]);

                productList.add(new Product(name, price));

                line = br.readLine();
            }

            // Proceding with functional programming
            double avg = productList.stream()
                    .map(p -> p.getPrice())
                    .reduce(0.0, (x,y) -> x + y) / productList.size();
            System.out.println("Average price: " + String.format("%.2f", avg));

            Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());

            List<String> names = productList.stream()
                    .filter(p -> p.getPrice() < avg)
                    .map(p -> p.getProduct())
                    .sorted(comp.reversed())
                    .collect(Collectors.toList());

            names.forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}