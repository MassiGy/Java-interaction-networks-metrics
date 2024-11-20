package org.tpri;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
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
        System.out.println("NodeCount: "+graph.getNodeCount());
        System.out.println("EdgeCount: "+graph.getEdgeCount());
        System.out.println("averageDegree: " + Toolkit.averageDegree(graph));
        System.out.println("averageClusteringCoefficient: " + Toolkit.averageClusteringCoefficient(graph));
        System.out.println("averageClusteringCoefficient in a similar  graph that is random: " +Toolkit.averageDegree(graph) /graph.getNodeCount());

        System.out.println("is the graph connected: "+ Toolkit.isConnected(graph));

        int[] dd = Toolkit.degreeDistribution(graph);
        for (int k = 0; k < dd.length; k++) {
            if (dd[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double)dd[k] / graph.getNodeCount());
            }
        }

    }
}
