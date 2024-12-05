package org.tpri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.graphstream.graph.Node;

public class SimulationModeleSIS {

    double infectionProbability, recoveryProbability;
    List<Node> susceptibleNodes;
    List<Node> infectedNodes;

    SimulationModeleSIS(double infectionProbability, double recoveryProbability, List<Node> susceptibleNodes) {
        this.infectionProbability = infectionProbability;       // in our example it will be 1 per week (1/7)
        this.recoveryProbability = recoveryProbability;         // in our example it will be 1 per 15 days ( 2 times a month )

        this.susceptibleNodes = new ArrayList<>(susceptibleNodes);  // all nodes are susceptible initially
        for(Node n: this.susceptibleNodes) {
            n.setAttribute("infected", false);
        }

        this.infectedNodes = new ArrayList<>();

        infectionOf(susceptibleNodes.get(0));  // start with one infected individual
    }


    public List<Collection<Node>> simulatePropagation(int days) {
        List<Collection<Node>> result = new ArrayList<>(days);

        Collection<Node> infectedNodes;
        System.out.printf("%-15s %-25s %-20s\n", "Day Number", "Susceptible Nodes Count", "Infected Nodes Count");

        for (int i = 0; i < days; i++) {
            infectedNodes = new ArrayList<>(this.infectedNodes);        // create a snapshot


            System.out.printf("%-15d %-25d %-20d\n", i + 1, this.susceptibleNodes.size(), infectedNodes.size());

            // simulate the interaction ( prepare the state for the next day )
            for(Node n: infectedNodes) {
                interactWithNeighbors(n);
            }

            result.add(infectedNodes);                                  // save snapshot
        }

        result.add(new ArrayList<>(this.infectedNodes));
        System.out.printf("%-15d %-25d %-20d\n", days, this.susceptibleNodes.size(), this.infectedNodes.size());

        return result;
    }




    /*
    public List<Collection<Node>> propagation(int days) {
        List<Collection<Node>> result = new ArrayList<>(days);
        System.out.printf("%-15s %-25s %-20s\n", "Day Number", "Susceptible Nodes Count", "Infected Nodes Count");
        for (int i = 0; i < days; i++){
            System.out.printf("%-15d %-25d %-20d\n", i + 1, this.susceptibleNodes.size(), infectedNodes.size());
            result.add(nextDay());
        }

        result.add(new ArrayList<>(this.infectedNodes));
        System.out.printf("%-15d %-25d %-20d\n", days, this.susceptibleNodes.size(), this.infectedNodes.size());
        return result;
    }
    */


    private Collection<Node> nextDay() {
        Collection<Node> infectedNodes = new ArrayList<>(this.infectedNodes);

        infectedNodes.forEach(this::interactWithNeighbors);
        return infectedNodes;
    }



    private void interactWithNeighbors(Node node) {

        Iterator<Node> neighborsIt = node.getBreadthFirstIterator();
        while (neighborsIt.hasNext()) {
            Node neighbor = neighborsIt.next();
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

}