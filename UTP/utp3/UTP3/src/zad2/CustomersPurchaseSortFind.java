/**
 * @author Stachnik Maksymilian S25304
 */

package zad2;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomersPurchaseSortFind {
    private List<Purchase> customers;
    public CustomersPurchaseSortFind(){
        customers = new ArrayList<>();
    }
    public void readFile(String path) {
        try {
            fetchCustomersFromFile(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Purchase convertLineIntoPurchase(String line) {
        String[] splitedLine = line.split(";");
        return new Purchase(splitedLine[0], splitedLine[1], splitedLine[2], Double.valueOf(splitedLine[3]), Double.valueOf(splitedLine[4]));
    }

    private void fetchCustomersFromFile(String path) throws FileNotFoundException, IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line ;
        while ((line = bufferedReader.readLine()) != null)
            customers.add(convertLineIntoPurchase(line));
    }

    public void showSortedBy(String sortedBy) {
        switch (sortedBy) {
            case "Nazwiska" -> showByName(sortByName());
            case "Koszty" -> showByPrice(sortByPrice());
        }

    }

    private void showByName(List<Purchase> toShow) {
        System.out.println("Nazwiska");
        toShow.stream()
                .forEach(System.out::println);
    }

    private void showByPrice(List<Purchase> toShow) {
        System.out.println("Koszty");
        toShow.stream()
                .map(e -> e + e.getCostText())
                .forEach(System.out::println);
    }

    private List<Purchase> sortByName() {
        return customers.stream()
                .sorted(Comparator.comparing(Purchase::getCustomerName).thenComparing(Purchase::getId))
                .collect(Collectors.toList());

    }

    private List<Purchase> sortByPrice() {
        return customers.stream()
                .sorted(Comparator.comparing(Purchase::getCost).reversed().thenComparing(Purchase::getId))
                .collect(Collectors.toList());

    }
    public void showPurchaseFor(String id){
        System.out.println("Klient "+ id);
        customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .forEach(System.out::println);
    }


}
