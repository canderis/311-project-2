import org.junit.jupiter.api.Test;

public class WGraphTest {
	@Test
    public void extractLinksTest1() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			wc.V2V(1, 2, 7, 8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

      //  assertEquals(expected, actual);
    }
}
