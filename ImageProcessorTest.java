import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class ImageProcessorTest {

  @Test
  public void getImportanceTest() {
    ImageProcessor imageProcessor = new ImageProcessor("testImage.txt");
    ArrayList<ArrayList<Integer>> I = imageProcessor.getImportance();

    assertEquals(8981, (int) I.get(0).get(0));
    assertEquals(14067, (int) I.get(1).get(2));
    assertEquals(33355, (int) I.get(2).get(3));
  }

  @Test
  public void writeReducedTest() {
    ImageProcessor imageProcessor = new ImageProcessor("testImage.txt");
    imageProcessor.writeReduced(1, "reduced.txt");
  }
}