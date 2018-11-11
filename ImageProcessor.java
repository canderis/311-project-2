import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ImageProcessor {

  private String FName;
  private int[][][] M;
  private int height;
  private int width;
  private ArrayList<ArrayList<Integer>> I;

  public ImageProcessor(String FName) {
    this.FName = FName;

    // Read in from file to create image matrix M
    try {
      this.generateImageMatrix();
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Compute the importance matrix I
    I = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      I.add(new ArrayList<Integer>());
      for (int j = 0; j < width; j++) {
        I.get(i).add(importance(i, j));
      }
    }
  }

  public ArrayList<ArrayList<Integer>> getImportance() {
    return this.I;
  }

  public void writeReduced(int k, String FName) {

  }

  private void generateImageMatrix() throws Exception {
    Scanner in = new Scanner(new FileReader(this.FName));
    this.height = in.nextInt();
    this.width = in.nextInt();
    M = new int[this.height][this.width][3];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        for (int k = 0; k < 3; k++) {
          this.M[i][j][k] = in.nextInt();
        }
      }
    }
  }

  private int pDist(int[] p, int[] q) {
    return (int) Math.pow(p[0] - q[0], 2) +
           (int) Math.pow(p[1] - q[1], 2) +
           (int) Math.pow(p[2] - q[2], 2);
  }

  private int importance(int i, int j) {
    return xImportance(i, j) + yImportance(i, j);
  }

  private int yImportance(int i, int j) {
    int[] p;
    int[] q;

    if (i == 0) {
      p = this.M[height - 1][j];
      q = this.M[i + 1][j];
    } else if (i == this.height - 1) {
      p = this.M[i - 1][j];
      q = this.M[0][j];
    } else {
      p = this.M[i - 1][j];
      q = this.M[i + 1][j];
    }

    return pDist(p, q);
  }

  private int xImportance(int i, int j) {
    int[] p;
    int[] q;

    if (j == 0) {
      p = this.M[i][width - 1];
      q = this.M[i][j + 1];
    } else if (j == this.width - 1) {
      p = this.M[i][j - 1];
      q = this.M[i][0];
    } else {
      p = this.M[i][j - 1];
      q = this.M[i][j + 1];
    }

    return pDist(p, q);
  }
}