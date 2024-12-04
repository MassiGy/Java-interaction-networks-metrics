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
        this.infectedNodes = new ArrayList<>();
        infectionOf(susceptibleNodes.get(0));  // start with one infected individual

    }


    public List<Collection<Node>> simulatePropagation(int days) {
        List<Collection<Node>> result = new ArrayList<>(days);

        Collection<Node> infectedNodes;

        for (int i = 0; i < days; i++) {
            infectedNodes = new ArrayList<>(this.infectedNodes);        // create a snapshot
            result.add(infectedNodes);                                  // save snapshot
            this.infectedNodes.forEach(n -> interactWithNeighbors(n)); // simulate the interaction ( prepare the state for the next day )
        }

        result.add(new ArrayList<>(this.infectedNodes));
        return result;
    }



    private void interactWithNeighbors(Node node) {

        Iterator<Node> neighborsIt = node.getBreadthFirstIterator();
        while (neighborsIt.hasNext()) {
            Node neighbor = neighborsIt.next();
            if (this.susceptibleNodes.contains(neighbor)) {
                if (Math.random() < this.infectionProbability) {
                    infectionOf(neighbor);
                }
            }
        }
        if (this.infectedNodes.contains(node)) {
            if (Math.random() < this.recoveryProbability) {
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