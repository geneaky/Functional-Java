import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class StreamTest {

  @Test
  public void stream() throws Exception {
    //given
    Stream<String> nameStream = Stream.of("alice", "bob", "charlie");
    List<String> names = nameStream.collect(Collectors.toList());
    System.out.println(names);

    String[] cityArray = new String[]{"San Jose", "Seoul", "Tokyo"};
    Stream<String> cityStream = Arrays.stream(cityArray);
    List<String> cities = cityStream.collect(Collectors.toList());
    System.out.println(cities);

    Set<Integer> numberSet = new HashSet<>(Arrays.asList(3,5,7));
    Stream<Integer> numberStream = numberSet.stream();
    List<Integer> numbers = numberStream.collect(Collectors.toList());
    System.out.println(numbers);
    //when
    //then
  }

}
