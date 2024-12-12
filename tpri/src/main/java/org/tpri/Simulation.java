package org.tpri;

import java.util.*;

import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

public class Simulation {

    double infectionProbability, recoveryProbability;
    List<Node> susceptibleNodes;
    List<Node> infectedNodes;
    Graph graph;

    Simulation(double infectionProbability, double recoveryProbability, Graph graph) {
        this.infectionProbability = infectionProbability;       // in our example it will be 1 per week (1/7)
        this.recoveryProbability = recoveryProbability;         // in our example it will be 1 per 15 days ( 2 times a month )
        this.graph = graph;                                     // this will be our network

    }


    public List<Collection<Node>> simulatePropagationSenario1(int days) {
        // Setup our simulation for the the first senario
        this.susceptibleNodes = new ArrayList<>(this.graph.nodes().toList());  // all nodes are susceptible initially
        for(Node n: this.susceptibleNodes) {
            n.setAttribute("infected", false);
        }
        System.out.println("epidemicThreshold(this.susceptibleNodes): "+ epidemicThreshold(this.susceptibleNodes));

        this.infectedNodes = new ArrayList<>();
        infectionOf(susceptibleNodes.get(0));  // start with one infected individual

        // start simulation
        List<Collection<Node>> result = new ArrayList<>(days);

        Collection<Node> infectedNodes;
        //System.out.printf("%-15s %-25s %-20s\n", "Day Number", "Susceptible Nodes Count", "Infected Nodes Count");

        for (int i = 0; i < days; i++) {
            infectedNodes = new ArrayList<>(this.infectedNodes);        // create a snapshot

            //System.out.printf("%-15d %-25d %-20d\n", i + 1, this.susceptibleNodes.size(), infectedNodes.size());
            System.out.printf("%-15d %-20d\n", i + 1, infectedNodes.size());
            // simulate the interaction ( prepare the state for the next day )
            for(Node n: infectedNodes) {
                interactWithNeighbors(n);
            }

            result.add(infectedNodes);                                  // save snapshot
        }

        result.add(new ArrayList<>(this.infectedNodes));
       // System.out.printf("%-15d %-25d %-20d\n", days, this.susceptibleNodes.size(), this.infectedNodes.size());
        System.out.printf("%-15d %-20d\n", days, this.infectedNodes.size());

        return result;
    }


    public List<Collection<Node>> simulatePropagationSenario2(int days) {

        // only 50% of our population will be susceptible, since the others are immuned with the anti-virus
       this.susceptibleNodes = Toolkit.randomNodeSet(graph, (graph.getNodeCount() / 2));
        for(Node n: this.susceptibleNodes) {
            n.setAttribute("infected", false);
        }
        System.out.println("epidemicThreshold(this.susceptibleNodes): "+ epidemicThreshold(this.susceptibleNodes));

        this.infectedNodes = new ArrayList<>();
        infectionOf(this.susceptibleNodes.get(0));  // start with one infected individual

        List<Collection<Node>> result = new ArrayList<>(days);

        Collection<Node> infectedNodes;
        //System.out.printf("%-15s %-25s %-20s\n", "Day Number", "Susceptible Nodes Count", "Infected Nodes Count");

        for (int i = 0; i < days; i++) {
            infectedNodes = new ArrayList<>(this.infectedNodes);        // create a snapshot


           // System.out.printf("%-15d %-25d %-20d\n", i + 1, this.susceptibleNodes.size(), infectedNodes.size());
            System.out.printf("%-15d %-20d\n", i + 1, infectedNodes.size());

            // simulate the interaction ( prepare the state for the next day )
            for(Node n: infectedNodes) {
                interactWithNeighbors(n);
            }

            result.add(infectedNodes);                                  // save snapshot
        }

        result.add(new ArrayList<>(this.infectedNodes));
      //  System.out.printf("%-15d %-25d %-20d\n", days, this.susceptibleNodes.size(), this.infectedNodes.size());
        System.out.printf("%-15d %-20d\n", days, this.infectedNodes.size());

        return result;
    }

    public List<Collection<Node>> simulatePropagationSenario3(int days) {

        // select 50% of our population as a random set into group0
        List<Node> group0 = Toolkit.randomNodeSet(graph, (graph.getNodeCount() / 2));

        // group1 will contain all the individuals that the members of group0
        // will convince of getting immuned using the anti-virus
        List<Node> group1 =  new ArrayList<>();
        for (Node n : group0) {
            Edge e = Toolkit.randomEdge(n);     // selectively immuned
            if (e != null) {
                Node u = e.getOpposite(n);
                group1.add(u);
            }
        }

        //  TP2: question n°3
        System.out.println("averageDegree(group0): "+averageDegree(group0));
        System.out.println("averageDegree(group1): "+averageDegree(group1));




        this.susceptibleNodes = new ArrayList<>(this.graph.nodes().toList());


        //System.out.println(this.susceptibleNodes.size());
        for(Node n: group1)
            this.susceptibleNodes.remove(n);      // remove the immuned group from our population
        //System.out.println(this.susceptibleNodes.size());

        for(Node n: this.susceptibleNodes) {
            n.setAttribute("infected", false);
        }
        System.out.println("epidemicThreshold(this.susceptibleNodes): "+ epidemicThreshold(this.susceptibleNodes));



        this.infectedNodes = new ArrayList<>();
        infectionOf(this.susceptibleNodes.get(0));  // start with one infected individual

        List<Collection<Node>> result = new ArrayList<>(days);

        Collection<Node> infectedNodes;
        //System.out.printf("%-15s %-25s %-20s\n", "Day Number", "Susceptible Nodes Count", "Infected Nodes Count");

        for (int i = 0; i < days; i++) {
            infectedNodes = new ArrayList<>(this.infectedNodes);        // create a snapshot


            //System.out.printf("%-15d %-25d %-20d\n", i + 1, this.susceptibleNodes.size(), infectedNodes.size());
            System.out.printf("%-15d %-20d\n", i + 1, infectedNodes.size());



            // simulate the interaction ( prepare the state for the next day )
            for(Node n: infectedNodes) {
                interactWithNeighbors(n);
            }

            result.add(infectedNodes);                                  // save snapshot
        }

        result.add(new ArrayList<>(this.infectedNodes));

        //System.out.printf("%-15d %-25d %-20d\n", days, this.susceptibleNodes.size(), this.infectedNodes.size());
        System.out.printf("%-15d %-20d\n", days, this.infectedNodes.size());

        return result;
    }


    private void interactWithNeighbors(Node node) {

        Iterator<Node> neighborsIt = node.getBreadthFirstIterator();
        while (neighborsIt.hasNext()) {
            Node neighbor = neighborsIt.next();

            if(neighbor.getAttribute("infected") == null)   // edge case
                continue;

            if ((boolean) neighbor.getAttribute("infected") ==  false) {
                if (Math.random() < this.infectionProbability) {
                    infectionOf(neighbor);
                }
            }
        }

        // maybe at the end of the day the current node will recover
        if ((boolean) node.getAttribute("infected") ==  true) {
            if (Math.random() < this.recoveryProbability) {
                recoveryOf(node);
            }
        }
    }

    private void infectionOf(Node node) {
        this.infectedNodes.add(node);
        node.setAttribute("infected", true);
        this.susceptibleNodes.remove(node);
    }

    private void recoveryOf(Node node) {
        this.infectedNodes.remove(node);
        node.setAttribute("infected", false);
        this.susceptibleNodes.add(node);
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
        return averageDegree(nodes)/averageDegreeExp2(nodes);
    }


}