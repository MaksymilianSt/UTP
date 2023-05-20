/**
 *
 *  @author Stachnik Maksymilian S25304
 *
 */

package zad1;



import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 =Arrays.asList(1, 7, 9, 11, 12);/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test1(src1));

    List<String> src2 =Arrays.asList("a", "zzzz", "vvvvvvv" ); /*<-- tu dopisać inicjację elementów listy */
    System.out.println(test2(src2));

    List<Integer> src4 = Arrays.asList(
        Integer.MAX_VALUE+1,
        Integer.MIN_VALUE+2,
        0b10000000_00000000_00000000_00000000+4,
        0x80_00_00_00,
        020_000_000_000+5);
    System.out.println(test4(src4));

    }

  public List<Integer> test1(List<Integer> src) {
    Selector sel = new Selector<Integer>() {
      @Override
      public boolean select(Integer integer) {
        return integer < 10;
      }
    };
    /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
    Mapper map = new Mapper<Integer,Integer>() {
      @Override
      public Integer map(Integer integer) {
        return integer + 10;
      }
    };

    /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return   /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */  ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public List<Integer> test2(List<String> src) {
    Selector sel = new Selector<String>() {
      @Override
      public boolean select(String s) {
        return s.length() > 3;
      }
    };

    /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
    Mapper map = new Mapper<String,Integer>() {
      @Override
      public Integer map(String s) {
        return s.length() + 10;
      }
    };

    /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
    return   /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */  ListCreator.collectFrom(src).when(sel).mapEvery(map);
  }

  public List<String> test4(List<Integer> src) {
    /*    Podczas tworzenia poniższch selektorów oraz maperów nie używaj lambda wyrażeń. */
    Selector negativeSelector;/*<-- zwraca true tylko jeśli przekazany argument jest ujemny. Przykład: 100 -> false */
    negativeSelector = new Selector<Integer>() {
      @Override
      public boolean select(Integer integer) {

        return integer < 0;
      }
    };
    Mapper absoluteMapper;  /*<-- zwraca wartość bezwzględną dowolnej przekazanej mu liczby.   Przykład: -224 -> 224 */
    absoluteMapper = new Mapper<Integer,Integer>() {
      @Override
      public Integer map(Integer integer) {

        return integer < 0 ? -integer : integer;

      }
    };
    Selector evenSelector;/*<-- zwraca true tylko jeśli przekazana mu liczba jest parzysta.  Przykład: 100 -> true */
    evenSelector = new Selector<Integer>() {
      @Override
      public boolean select(Integer integer) {
        return integer % 2 == 0;
      }
    };
    Mapper reverseMapper;  /*<-- zwraca przekazaną mu liczbę w postaci odwróconego Stringa.  Przykład: 204 -> "402"  */
    reverseMapper = new Mapper<Integer,String>() {
      @Override
      public String map(Integer integer) {
        String s = String.valueOf(integer);
        String reverse = "";
        for (int i = s.length() - 1; i >= 0; i--) {
          if(!(s.charAt(i) == '-'))
            reverse += s.charAt(i);
        }

        return reverse;
      }
    };

    /* zwrot wyniku uzyskanego przez wywołanie statycznej metody klasy ListCreator */
    return ListCreator.collectFrom(src)
            .when(negativeSelector)
            .mapEvery(absoluteMapper)
            .when(evenSelector)
            .mapEvery(reverseMapper);

  }

  public static void main(String[] args) {
    new Main();
  }
}
