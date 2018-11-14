import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
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
  }

  public ArrayList<ArrayList<Integer>> getImportance() {
    this.computeImportance();
    return this.I;
  }

  public void writeReduced(int k, String FName) {
    for (int cuts = 0; cuts < k; cuts++) {
      // Compute the importance matrix I
      this.computeImportance();

      // Generate a file that is a graph representation of the importance matrix
      this.graphFromImportance();

      // Find the shortest path between the first and last row (the min-cost vertical cut)
      this.performCut();
    }

    this.writeReducedToFile(FName);
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

  private void computeImportance() {
    I = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      I.add(new ArrayList<Integer>());
      for (int j = 0; j < width; j++) {
        I.get(i).add(importance(i, j));
      }
    }
  }

  private void graphFromImportance() {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream("temp_importanceAsGraph.txt"), StandardCharsets.UTF_8))) {

      int numVertices = (this.height + 1) * this.width;
      int numEdges = 0;
      int edgesToAdd;

      for (int col = 0; col < this.width; col++) {
        boolean isBorderColumn = col == 0 || col == this.width - 1;
        edgesToAdd = isBorderColumn ? 2 : 3;
        numEdges += this.height * edgesToAdd;
      }

      writer.write(numVertices + "\n");
      writer.write(numEdges + "\n");

      for (int col = 0; col < this.width; col++) {
        for (int row = 0; row < this.height; row++) {
          writer.write(row + " " + col + " " + (row + 1) + " " + col + " " + this.I.get(row).get(col) + "\n");

          if (col == 0) {
            writer.write(row + " " + col + " " + (row + 1) + " " + (col + 1) + " " + this.I.get(row).get(col) +  "\n");
          } else if (col == width - 1) {
            writer.write(row + " " + col + " " + (row + 1) + " " + (col - 1) + " " + this.I.get(row).get(col) + "\n");
          } else {
            writer.write(row + " " + col + " " + (row + 1) + " " + (col + 1) + " " + this.I.get(row).get(col) + "\n");
            writer.write(row + " " + col + " " + (row + 1) + " " + (col - 1) + " " + this.I.get(row).get(col) + "\n");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void performCut() {
    WGraph graph = null;

    try {
      graph = new WGraph("temp_importanceAsGraph.txt");
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<Integer> from = new ArrayList<>();
    ArrayList<Integer> to = new ArrayList<>();

    for (int m = 0; m < this.width; m++) {
      from.add(0);
      from.add(m);
      to.add(this.height - 1);
      to.add(m);
    }

    ArrayList<Integer> minCostVerticalCut = graph.S2S(from, to);

    // Remove the pixels from the image
    for (int i = 0; i < minCostVerticalCut.size(); i += 2) {
      int x = minCostVerticalCut.get(i);
      int y = minCostVerticalCut.get(i + 1);

      this.M[x][y] = null;
    }

    // Remove nulls and build new M
    int[][][] tempM = new int[this.height][this.width - 1][3];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0, m = 0; j < this.width; j++,  m++) {
        if (M[i][j] != null) {
          for (int p = 0; p < 3; p++) {
            tempM[i][m][p] = this.M[i][j][p];
          }
        } else {
          m--;
        }
      }
    }

    this.M = tempM;
    this.width = this.width - 1;
  }

  private void writeReducedToFile(String FName) {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
        new FileOutputStream(FName), StandardCharsets.UTF_8))) {

      writer.write(this.height + "\n");
      writer.write(this.width + "\n");

      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          writer.write(this.M[i][j][0] + " " + this.M[i][j][1] + " " + this.M[i][j][2]);
          if (j != this.width - 1) writer.write(" ");
        }
        writer.write("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
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

  private int pDist(int[] p, int[] q) {
    return (int) Math.pow(p[0] - q[0], 2) +
        (int) Math.pow(p[1] - q[1], 2) +
        (int) Math.pow(p[2] - q[2], 2);
  }
}