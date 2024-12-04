package Project.Graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Vertex {
    private int vertexIndex;
    private int rank;
    private boolean contracted;
    private List<Edge> edges;
    private long id;
    private float coordinateX,coordinateY;
    
    public Vertex(int vertexIndex, long vertexID,float coordinateX,float coordinateY){
        this.vertexIndex = vertexIndex;
        this.coordinateX=coordinateX;
        this.coordinateY=coordinateY;

        this.rank = -1; // Default rank
        this.contracted = false; // Initially not contracted
        this.edges = new ArrayList<>();
        this.id=vertexID;

    }

    public int getVertexIndex() {
        return vertexIndex;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isContracted() {
        return contracted;
    }

    public void contract() {
        this.contracted = true;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public void removeEdge(Edge edge){
        edges.remove(edge);
    }

    public void removeAllEdgesFromVertex(Vertex v) {
        Iterator<Edge> iterator = v.edges.iterator();
        while (iterator.hasNext()) {
            iterator.next().setWeight(Double.POSITIVE_INFINITY); // Move to the next edge
        }
    }

    public void restoreVertex(Vertex v){
        for (Edge edge : v.edges) {
            edge.restoreWeight();
        }
    }


}
