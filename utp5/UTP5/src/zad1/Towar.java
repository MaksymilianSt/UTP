package zad1;

 public class Towar{
    int id_towaru;
    double waga;
    public Towar(String line){
        String[] splitted = line.split(" ");
        this.id_towaru = Integer.valueOf(splitted[0]);
        this.waga = Double.valueOf(splitted[1]);
    }

    @Override
    public String toString() {
        return "Towar{" +
                "id_towaru=" + id_towaru +
                ", waga=" + waga +
                '}';
    }
}