package zad1;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
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
                .map(e -> convert(e, locale, dateFormat))
                .collect(Collectors.toList());

    }
    public List<String> getOffersDescriptionsList() {
        String dateFormat = "yyyy-MM-dd";;
        return clients.stream()
                .map(e -> convert(e,  dateFormat))
                .collect(Collectors.toList());

    }

    private String convert(String line, String locale, String dateFormat) {
        String[] split = line.split("\t");
        String country = translateCountry(split[1], split[0], locale);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String dateFrom = convertDate(split[2], dateFormat);
        String dateTo = convertDate(split[3], dateFormat);

        String spot = ResourceBundle.getBundle("Spots", new Locale(locale)).getString(split[4]);
        String money = convertMoney(split[5], split[0], locale);
        return country + " " + dateFrom + " " + dateTo + " " + spot + " " + money + " " + split[6];

    }
    public String translateRow(String translateTo,String ...columns){
        String dateFormat = "yyyy-MM-dd";
        String country = translateCountry(columns[1], columns[0], translateTo);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String dateFrom = convertDate(columns[2], dateFormat);
        String dateTo = convertDate(columns[3], dateFormat);


        String spot = ResourceBundle.getBundle("Spots", new Locale(translateTo)).getString(columns[4]);

        String money = convertMoney2(columns[5], columns[0], translateTo);

        return  columns[0]+ "\t" + country + "\t" + dateFrom + "\t" + dateTo + "\t" + spot + "\t" + money + "\t" + columns[6];


    }
    private String convert(String line,  String dateFormat) {

        String[] split = line.split("\t");
        String locale = split[0];
        String country = translateCountry(split[1], split[0], locale);

        String dateFrom = convertDate(split[2], dateFormat);
        String dateTo = convertDate(split[3], dateFormat);

        String spot = ResourceBundle.getBundle("Spots", new Locale(locale)).getString(split[4]);
        String money = convertMoney2(split[5], split[0], locale);
        return locale + "\t" + country + "\t" + dateFrom + "\t" + dateTo + "\t" + spot + "\t" + money + "\t" + split[6];

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

    private String convertDate(String date, String pattern) {
        SimpleDateFormat dt = new SimpleDateFormat(pattern);
        Date parse = null;
        try {
            parse = dt.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return dt.format(parse);
    }

    private String convertMoney(String money, String convertFrom, String convertTo) {
        DecimalFormat df = (DecimalFormat)
                NumberFormat.getInstance(new Locale((convertFrom.substring(0, 2))));
        Number moneyAsNumber = null;


        try {
            moneyAsNumber = df.parse(money);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String converted = convertTo.contains("pl") ? DecimalFormat.getInstance(Locale.FRANCE).format(moneyAsNumber) :
                DecimalFormat.getInstance(Locale.US).format(moneyAsNumber);

        return converted;
    }
    private static String convertMoney2(String money, String convertFrom, String convertTo) {
        Locale fromLocale = new Locale(convertFrom);
        Locale toLocale = new Locale(convertTo);

        NumberFormat fromFormat = NumberFormat.getInstance(fromLocale);
        NumberFormat toFormat = NumberFormat.getInstance(toLocale);

        Number moneyAsNumber = null;

        try {
            moneyAsNumber = fromFormat.parse(money);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String converted = toFormat.format(moneyAsNumber);

        return converted;
    }

}
