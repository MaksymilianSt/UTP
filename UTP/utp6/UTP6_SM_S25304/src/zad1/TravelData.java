package zad1;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TravelData {
    private List<String> clients;

    public TravelData(File dataDir) {
        this.clients = readClients(dataDir);
    }

    private List<String> readClients(File file) {
        try {
            return Files.walk(file.toPath())
                    .filter(Files::isRegularFile)
                    .flatMap(f -> {
                        try {
                            return Files.readAllLines(f).stream();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public List<String> getOffersDescriptionsList(String locale, String dateFormat) {
       return clients.stream()
                .map(e->convert(e,locale,dateFormat))
                .collect(Collectors.toList());

    }

    private String convert(String line, String locale, String dateFormat) {
        String[] split = line.split("\t");
        String country = translateCountry(split[1], split[0], locale);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String dateFrom = split[2] ;
        String dateTo = split[3] ;

        String spot = ResourceBundle.getBundle("Spots", new Locale(locale)).getString(split[4]);
        String money= split[5] ;
        if(split[0].contains("pl") && locale.contains("en"))
            money= split[5].replace(",",".");
        else if(split[0].contains("en") && locale.contains("pl"))
            money = split[5].replace(".",",");

        return country + " " + dateFrom + " " + dateTo + " " +spot +" "+  money + " "+ split[6];

    }

    private String translateCountry(String country, String translateFrom, String translateTo) {
        Locale[] availableLocales = Locale.getAvailableLocales();

        if (translateFrom.contains("_"))
            translateFrom = translateFrom.split("_")[0];

        if (translateTo.contains("_"))
            translateTo = translateTo.split("_")[0];

        for (Locale loc : availableLocales) {
            if (loc.getDisplayCountry(new Locale(translateFrom)).equals(country))
                return loc.getDisplayCountry(new Locale(translateTo));
        }
        return country;
    }

}
