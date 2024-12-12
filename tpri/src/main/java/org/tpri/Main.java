package org.tpri;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.BreadthFirstIterator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceEdge;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Main {


    public static Graph randomGen(int nodeCount, double averageDegree) {
        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator((int) Math.round(averageDegree));
        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < nodeCount; i++) {
            gen.nextEvents();
        }

        gen.end();
        return graph;
    }

    public static Graph randomPreferencielGraph(int nodeCount, double averageDegree) {
        Graph graph = new SingleGraph("Preferential");
        Generator gen = new BarabasiAlbertGenerator((int) Math.round(averageDegree));
        gen.addSink(graph);
        gen.begin();

        for (int i = 0; i < nodeCount; i++) {
            gen.nextEvents();
        }

        gen.end();
        return graph;
    }

    public static Graph graphFromFileSource(String absFilePath) {
        Graph graph = new DefaultGraph("g");
        FileSourceEdge fs = new FileSourceEdge();
        fs.addSink(graph);


        try {
            fs.readAll(absFilePath);
        } catch (IOException e) {
        } finally {
            fs.removeSink(graph);
        }

        return graph;
    }

    public static void doAnalysis(Graph graph) {
        System.out.println("NodeCount: " + graph.getNodeCount());
        System.out.println("EdgeCount: " + graph.getEdgeCount());
        System.out.println("averageDegree: " + Toolkit.averageDegree(graph));
        System.out.println("averageClusteringCoefficient: " + Toolkit.averageClusteringCoefficient(graph));
        System.out.println("averageClusteringCoefficient in a similar  graph that is random: " + Toolkit.averageDegree(graph) / graph.getNodeCount());

        System.out.println("is the graph connected: " + Toolkit.isConnected(graph));


        int[] degreeDistribution = Toolkit.degreeDistribution(graph);
        for (int k = 0; k < degreeDistribution.length; k++) {
            if (degreeDistribution[k] != 0) {
                System.out.printf(Locale.US, "%6d%20.8f%n", k, (double) degreeDistribution[k] / graph.getNodeCount());
            }
        }


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


        // print out the distances histogram and use gnuplot to draw the distance
        // distribution.
        for (int i = 0; i < distancesHistogram.length; i++) {

            // we devide by (nbNodes*graph.getNodeCount()) since we used a sample of 1000 nodes taken from
            // a graph of n nodes where n = graph.getNodeCount(),
            // so we multiply nbNodes*graph.getNodeCount() since we calculated the distance between these nbNodes and all the nodes from the
            // rest of the graph
            System.out.printf(Locale.US, "%6d%20.8f%n", i, (double) distancesHistogram[i] / (nbNodes * graph.getNodeCount()));
        }


    }




    public static void main(String args[]) {




        {
            Graph graph = graphFromFileSource("/home/etudiant/gm213204/Documents/ri/m1-ri-tp/com-dblp.ungraph.txt");
            //doAnalysis(graph);

            double infectionProbability = 1.0/7;
            double recoveryProbability = 1.0/15;
            int daysCount = 30*2;
            Simulation simulation;

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario1(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario2(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario3(daysCount);
        }



    /*
        {
            //In our graph we have:

            //- NodeCount: 317080
            //- EdgeCount: 1049866
            //- averageDegree: 6.62208890914917

            Graph graph = randomGen(317082, 6.62208890914917);
            //doAnalysis(graph);

            double infectionProbability = 1.0/7;
            double recoveryProbability = 1.0/15;
            int daysCount = 1;
            Simulation simulation;

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario1(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario2(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario3(daysCount);

        }
    */


/*
        {

            //In our graph we have:

            //- NodeCount: 317080
            //- EdgeCount: 1049866
            //- averageDegree: 6.62208890914917

            Graph graph = randomPreferencielGraph(3170, 6.62208890914917);
            //doAnalysis(graph);

            double infectionProbability = 1.0/7;
            double recoveryProbability = 1.0/15;
            int daysCount = 30 * 2;
            Simulation simulation;

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario1(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario2(daysCount);
            System.out.println("---------------------------------------");
            System.out.println("---------------------------------------");

            simulation = new Simulation(infectionProbability, recoveryProbability, graph);
            simulation.simulatePropagationSenario3(daysCount);
        }

*/

    }
}
