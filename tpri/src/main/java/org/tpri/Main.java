package org.tpri;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceEdge;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;
import org.graphstream.algorithm.Toolkit;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Main {


    public static void main(String args[]) {

        Graph graph = new DefaultGraph("g");
        FileSourceEdge fs = new FileSourceEdge();
        fs.addSink(graph);
        try {
            fs.readAll("/home/etudiant/gm213204/Documents/ri/m1-ri-tp/com-dblp.ungraph.txt");
        } catch (IOException e) {
        } finally {
            fs.removeSink(graph);
        }
        System.out.println("NodeCount: " + graph.getNodeCount());
        System.out.println("EdgeCount: " + graph.getEdgeCount());
        System.out.println("averageDegree: " + Toolkit.averageDegree(graph));
        System.out.println("averageClusteringCoefficient: " + Toolkit.averageClusteringCoefficient(graph));
        System.out.println("averageClusteringCoefficient in a similar  graph that is random: " + Toolkit.averageDegree(graph) / graph.getNodeCount());

        System.out.println("is the graph connected: " + Toolkit.isConnected(graph));

        /*
        int[] degreeDistribution = Toolkit.degreeDistribution(graph);
        for (int k = 0; k < degreeDistribution.length; k++) {
            if (degreeDistribution[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double)degreeDistribution[k] / graph.getNodeCount());
            }
        }
        */

        int nbNodes = 1000;

        List<Node> randomNodesSamp = Toolkit.randomNodeSet(graph, nbNodes);

        // build the histogram of the distances (distance distribution)
        int[] distancesHistogram = new int[22]; // 22 slots is sufficient since the Diameter (longest shortest path) is 21

        for (Node n : randomNodesSamp) {
            BreadthFirstIterator bfi = (BreadthFirstIterator) n.getBreadthFirstIterator();
            while (bfi.hasNext()) {
                distancesHistogram[bfi.getDepthOf(bfi.next())]++;
            }
        }

        int sum = 0;
        for (int i = 0; i < distancesHistogram.length; i++) {
            sum += distancesHistogram[i];
        }


        double avgDistance = 0;
        for (int i = 0; i < distancesHistogram.length; i++) {
            avgDistance += i * distancesHistogram[i];
        }
        System.out.println("avgDistance: " + avgDistance / (double) sum);


        /*
        // print out the distances histogram and use gnuplot to draw the distance
        // distribution.
        for (int i = 0; i < distancesHistogram.length; i++) {

            // we devide by (nbNodes*graph.getNodeCount()) since we used a sample of 1000 nodes taken from
            // a graph of n nodes where n = graph.getNodeCount(),
            // so we multiply nbNodes*graph.getNodeCount() since we calculated the distance between these nbNodes and all the nodes from the
            // rest of the graph
            System.out.printf(Locale.US, "%6d%20.8f%n", i, (double)distancesHistogram[i]/(nbNodes*graph.getNodeCount()));
        }

         */


    }
}
