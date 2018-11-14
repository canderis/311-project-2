import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  public void writeReducedTest1() {
    String filename = "testImage2.txt";
    ImageProcessor imageProcessor = new ImageProcessor(filename);
    imageProcessor.writeReduced(1, "reduced_" + filename);

    String expected;
    String actual;

    try {
      expected = readFile("reduced_testImage2Expected.txt");
      actual = readFile("reduced_testImage2.txt");
      assertEquals(expected, actual);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void writeReducedTest2() {
    String filename = "testImage3.txt";
    ImageProcessor imageProcessor = new ImageProcessor(filename);
    imageProcessor.writeReduced(1, "reduced_" + filename);

    String expected;
    String actual;

    try {
      expected = readFile("reduced_testImage3Expected.txt");
      actual = readFile("reduced_testImage3.txt");
      assertEquals(expected, actual);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void writeReducedTest3() {
    String filename = "testImage4.txt";
    ImageProcessor imageProcessor = new ImageProcessor(filename);
    imageProcessor.writeReduced(2, "reduced_" + filename);

    String expected;
    String actual;

    try {
      expected = readFile("reduced_testImage4Expected.txt");
      actual = readFile("reduced_testImage4.txt");
      assertEquals(expected, actual);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String readFile(String path) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, StandardCharsets.UTF_8);
  }
}