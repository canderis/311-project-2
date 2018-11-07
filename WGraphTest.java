import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class WGraphTest {
	@Test
    public void v2vTest() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			wc.V2V(1, 2, 7, 8);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

      //  assertEquals(expected, actual);
    }
	
	@Test
    public void v2sTest() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> S = new ArrayList<Integer>();
			S.add(7);
			S.add(8);
			wc.V2S(1, 2, S);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

      //  assertEquals(expected, actual);
    }
	
	@Test
    public void s2sTest() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> S1 = new ArrayList<Integer>();
			S1.add(1);
			S1.add(2);
			ArrayList<Integer> S2 = new ArrayList<Integer>();
			S2.add(7);
			S2.add(8);
			wc.S2S(S1, S2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        

      //  assertEquals(expected, actual);
    }
	
}
