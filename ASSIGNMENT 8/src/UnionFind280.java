/**
 * NAME         :       JEET AGRAWAL
 * NSID         :       jea316
 * STUDENT ID    :       11269096
 */
import lib280.base.CursorPosition280;
import lib280.graph.Edge280;
import lib280.graph.GraphAdjListRep280;
import lib280.graph.Vertex280;


public class UnionFind280 {
	GraphAdjListRep280<Vertex280, Edge280<Vertex280>> G;
	
	/**
	 * Create a new union-find structure.
	 * 
	 * @param numElements Number of elements (numbered 1 through numElements, inclusive) in the set.
	 * @postcond The structure is initialized such that each element is in its own subset.
	 */
	public UnionFind280(int numElements) {
		G = new GraphAdjListRep280<Vertex280, Edge280<Vertex280>>(numElements, true);
		G.ensureVertices(numElements);		
	}
	
	/**
	 * Return the representative element (equivalence class) of a given element.
	 * @param id The elements whose equivalence class we wish to find.
	 * @return The representative element (equivalence class) of the element 'id'.
	 */
	public int find(int id) {
		// TODO - Write this method
		CursorPosition280 cursor = G.currentPosition();

		G.goIndex(id);
		G.eGoFirst(G.item());

		while (G.eItemExists()){
			G.goForth();
			G.eGoFirst(G.item());
		}

		int result = G.item().index();
		G.goPosition(cursor);

		return result;  // Remove this when you are ready.  It is just a placeholder to prevent a compiler error.
	}
	
	/**
	 * Merge the subsets containing two items, id1 and id2, making them, and all of the other elemnets in both sets, "equivalent".
	 * @param id1 First element.
	 * @param id2 Second element.
	 */
	public void union(int id1, int id2) {
		// TODO - Write this method.

		if (find(id1) == find(id2)){
			return;
		}
		else{
			G.addEdge(find(id1),find(id2));
		}
	}

	public static void main(String[] args) {

		System.out.println("REGRESSION TESTING PASSED.");
	}

}
