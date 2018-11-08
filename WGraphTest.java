import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class WGraphTest {
	@Test
    public void v2vTest_noPath() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> out = wc.V2V(7, 8, 1, 2);

			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	@Test
    public void v2vTest_path() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> out = wc.V2V(1, 2, 7, 8);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	@Test
    public void v2sTest() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> S = new ArrayList<Integer>();
			S.add(7);
			S.add(8);
			ArrayList<Integer> out = wc.V2S(1, 2, S);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			ArrayList<Integer> out = wc.S2S(S1, S2);
			
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void v2sTest_noPath() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> S = new ArrayList<Integer>();
			S.add(1);
			S.add(2);
			ArrayList<Integer> out = wc.V2S(7, 8, S);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void s2sTest_noPath() {

        try {
			WGraph wc = new WGraph("testGraph.txt");
			ArrayList<Integer> S1 = new ArrayList<Integer>();
			S1.add(7);
			S1.add(8);
			ArrayList<Integer> S2 = new ArrayList<Integer>();
			S2.add(1);
			S2.add(2);
			ArrayList<Integer> out = wc.S2S(S1, S2);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	


	
	
	
	
	
	@Test
    public void v2vTest_noPath_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> out = wc.V2V(7, 8, 1, 2);

			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	@Test
    public void v2vTest_path_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> out = wc.V2V(1, 2, 7, 8);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	@Test
    public void v2sTest_path_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> S = new ArrayList<Integer>();
			S.add(7);
			S.add(8);
			ArrayList<Integer> out = wc.V2S(1, 2, S);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void s2sTest_path_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> S1 = new ArrayList<Integer>();
			S1.add(1);
			S1.add(2);
			ArrayList<Integer> S2 = new ArrayList<Integer>();
			S2.add(7);
			S2.add(8);
			ArrayList<Integer> out = wc.S2S(S1, S2);
			
			ArrayList<Integer> expected = new ArrayList<Integer>();
			expected.add(1);
			expected.add(2);
			expected.add(5);
			expected.add(6);
			expected.add(7);
			expected.add(8);
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void v2sTest_noPath_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> S = new ArrayList<Integer>();
			S.add(1);
			S.add(2);
			ArrayList<Integer> out = wc.V2S(7, 8, S);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@Test
    public void s2sTest_noPath_equalWeight() {

        try {
			WGraph wc = new WGraph("testGraph_equalWeights.txt");
			ArrayList<Integer> S1 = new ArrayList<Integer>();
			S1.add(7);
			S1.add(8);
			ArrayList<Integer> S2 = new ArrayList<Integer>();
			S2.add(1);
			S2.add(2);
			ArrayList<Integer> out = wc.S2S(S1, S2);
			ArrayList<Integer> expected = new ArrayList<Integer>();
			assertEquals(expected, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
}
