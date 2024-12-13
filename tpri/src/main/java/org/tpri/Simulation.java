package org.tpri;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.*;

public class Simulation {

    double infectionProbability, recoveryProbability;
    Set<Node> susceptibleNodes;
    Set<Node> infectedNodes;
    Graph graph;


    Simulation(double infectionProbability, double recoveryProbability, Graph graph) {
        this.infectionProbability = infectionProbability;       // in our example it will be 1 per week (1/7)
        this.recoveryProbability = recoveryProbability;         // in our example it will be 1 per 15 days ( 2 times a month )
        this.graph = graph;                                     // this will be our network
    }

    public static double averageDegree(Collection<Node> nodes) {
        int sum = 0;
        for (Node n : nodes)
            sum += n.getDegree();
        return (double) sum / nodes.size();
    }

    public static double averageDegreeExp2(Collection<Node> nodes) {
        int sum = 0;
        for (Node n : nodes)
            sum += n.getDegree() * n.getDegree();
        return (double) sum / nodes.size();
    }

    public static double epidemicThreshold(Collection<Node> nodes) {
        return averageDegree(nodes) / averageDegreeExp2(nodes);
    }

    public void simulatePropagationSenario1(int days) {
        int nodeCount = graph.getNodeCount();

        // Setup our simulation for the the first senario
        this.susceptibleNodes = new HashSet<>(this.graph.nodes().toList());  // all nodes are susceptible initially
        System.out.println("epidemicThreshold(this.susceptibleNodes): " + epidemicThreshold(this.susceptibleNodes));

        this.infectedNodes = new HashSet<>();
        infectionOf(graph.nodes().limit(1).toList().get(0));  // start with one infected individual


        Collection<Node> infected;
        for (int i = 0; i < days; i++) {
            infected = new ArrayList<>(this.infectedNodes);

            infected.forEach(this::interactWithNeighbors);

            System.out.printf("%-15d %-3.8f\n", i + 1, (double) infected.size() / nodeCount);
        }
    }

    public void simulatePropagationSenario2(int days) {
        int nodeCount = graph.getNodeCount();

        // only 50% of our population will be susceptible, since the others are immuned with the anti-virus
        this.susceptibleNodes = new HashSet<>(Toolkit.randomNodeSet(graph, (graph.getNodeCount() / 2)));
        System.out.println("epidemicThreshold(this.susceptibleNodes): " + epidemicThreshold(this.susceptibleNodes));

        this.infectedNodes = new HashSet<>();
        infectionOf(graph.nodes().limit(1).toList().get(0));  // start with one infected individual

        Collection<Node> infected;
        for (int i = 0; i < days; i++) {
            infected = new ArrayList<>(this.infectedNodes);

            infected.forEach(this::interactWithNeighbors);

            System.out.printf("%-15d %-3.8f\n", i + 1, (double) infected.size() / nodeCount);
        }
    }

    public void simulatePropagationSenario3(int days) {
        int nodeCount = graph.getNodeCount();

        // select 50% of our population as a random set into group0
        List<Node> group0 = Toolkit.randomNodeSet(graph, (graph.getNodeCount() / 2));

        // group1 will contain all the individuals that the members of group0
        // will convince of getting immuned using the anti-virus
        List<Node> group1 = new ArrayList<>();
        for (Node n : group0) {
            Edge e = Toolkit.randomEdge(n);     // selectively immuned
            if (e != null) {
                Node u = e.getOpposite(n);
                group1.add(u);
            }
        }

        //  TP2: question n°3
        System.out.println("averageDegree(group0): " + averageDegree(group0));
        System.out.println("averageDegree(group1): " + averageDegree(group1));

        this.susceptibleNodes = new HashSet<>(this.graph.nodes().toList());
        this.susceptibleNodes.removeAll(group1);
        System.out.println("epidemicThreshold(this.susceptibleNodes): " + epidemicThreshold(this.susceptibleNodes));

        this.infectedNodes = new HashSet<>();
        infectionOf(graph.nodes().limit(1).toList().get(0));  // start with one infected individual

        Collection<Node> infected;
        for (int i = 0; i < days; i++) {
            infected = new ArrayList<>(this.infectedNodes);

            infected.forEach(this::interactWithNeighbors);

            System.out.printf("%-15d %-3.8f\n", i + 1, (double) infected.size() / nodeCount);
        }
    }

    private void interactWithNeighbors(Node node) {

        Iterator<Node> neighborsIt = node.getBreadthFirstIterator();
        while (neighborsIt.hasNext()) {
            Node neighbor = neighborsIt.next();

            if (!this.infectedNodes.contains(neighbor)) {
                if (Math.random() > this.infectionProbability) {
                    infectionOf(neighbor);
                }
            }
        }
        // maybe at the end of the day the current node will recover
        if ((boolean) this.infectedNodes.contains(node)) {
            if (Math.random() > this.recoveryProbability) {
                recoveryOf(node);
            }
        }
    }

    private void infectionOf(Node node) {
        this.infectedNodes.add(node);
        this.susceptibleNodes.remove(node);
    }
    private void recoveryOf(Node node) {
        this.infectedNodes.remove(node);
        this.susceptibleNodes.add(node);
    }
}