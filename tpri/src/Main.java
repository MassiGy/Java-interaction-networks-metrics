package tpri;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.ui.view.Viewer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class Main {
    /*
    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph("Bazinga!");
        try {
            graph.read("/home/etudiant/gm213204/Documents/graphstream/TP1_ressources/mesamisimaginaires.dgs");
            graph.display(false);
            graph.getNode("Eric").neighborNodes().forEach(System.out::println);

            graph.nodes().forEach(n -> n.setAttribute("ui.style", "size: "+(n.getDegree()/4)+"px;"));
            graph.edges().forEach(e -> e.setAttribute("ui.class", e.getAttribute("reseau")));
            graph.setAttribute("ui.stylesheet", "url('/home/etudiant/gm213204/Documents/graphstream/TP1_ressources/styles.css')");

        }catch (Exception e) {}

    }
    */

    public static void dfs(Node from, Boolean forward, Consumer<Node> preVisitConsumer, Consumer<Node> posVisitConsumer, List<Node> visited) {


        visited.add(from);
        preVisitConsumer.accept(from);
        from.neighborNodes()
                .forEach(
                        n -> {
                            if (forward && !from.hasEdgeToward(n))
                                return;
                            if (!forward && from.hasEdgeToward(n))
                                return;

                            if (visited.contains(n))
                                return;

                            visited.add(n);

                            dfs(n, forward, preVisitConsumer, posVisitConsumer, visited);

                        }
                );
        posVisitConsumer.accept(from);
        return;
    }

    public static Map<String, List<Node>> connexeSubParts(Graph g) {
        // https://igm.univ-mlv.fr/ens/IR/IR2/2008-2009/Algorithmique/tp03.php

        Map<String, List<Node>> connexSubParts = new HashMap<>();
        List<Node> nodes = g.nodes().collect(Collectors.toList());


        for (int i = 0; i < nodes.size(); i++) {
            Node N = nodes.get(i);
            connexSubParts.put(N.getId(), new ArrayList<>());

            List<Node> accessibleByN = new ArrayList<>();
            dfs(N, true, _n -> {
            }, _n -> {
            }, accessibleByN);

            for (int j = 0; j < accessibleByN.size(); j++) {
                Node M = accessibleByN.get(j);
                List<Node> accessibleByM = new ArrayList<>();
                dfs(M, true, _n -> {
                }, _n -> {
                }, accessibleByM);

                if(accessibleByM.contains(N))
                    connexSubParts.get(N.getId()).add(M);
            }
        }



        System.out.println(connexSubParts);
        return connexSubParts;

    }


/*
        TODO: this does not work, fix it!

        Node orig = g.getNode(0);       // arbitrary choice
        List<List<Node>> connexSubParts = new ArrayList<>();

        List<Node> visitedPreOrder = new ArrayList<>();
        List<Node> visitedPostOrder = new ArrayList<>();

        dfs(orig, false, n -> {
        }, n-> {
            if(!visitedPostOrder.contains(n))
                visitedPostOrder.add(n);
        }, visitedPreOrder);

        //System.out.println(visitedPostOrder);
        List<Node> seen;
        for (int i = 0; i < visitedPostOrder.size(); i++) {
            seen = new ArrayList<>();


            connexSubParts.add(new ArrayList<Node>());

            Node n = visitedPostOrder.get(i);
            final  int _i = i;      // clojure capture
            dfs(n, true, _n->{
                connexSubParts.get(_i).add(_n);
            }, _n->{}, seen);
        }

        for (int i = 0; i < connexSubParts.size(); i++) {
            System.out.println(connexSubParts.get(i));
        }
*/


    public static void main(String args[]) {
        /*
        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new MultiGraph("mygraph!");
        try {
            // https://graphstream-project.org/doc/Advanced-Concepts/The-DGS-File-Format/
            graph.read("/home/etudiant/gm213204/Documents/graphstream/TP1_ressources/mygraph.dgs");
            graph.display();


            graph.addNode("TikTok");
            graph.addEdge("SnapchatTikTok", "Snapchat", "TikTok", true);

            graph.nodes().forEach(n -> n.setAttribute("ui.class", n.getId()));

            graph.nodes().forEach(n -> n.setAttribute("ui.label", n.getId()));
            graph.edges().forEach(e -> e.setAttribute("ui.label", e.getId()));

            graph.setAttribute("ui.stylesheet", "url('/home/etudiant/gm213204/Documents/graphstream/TP1_ressources/mygraphstyles.css')");

            dfs(graph, graph.getNode("Facebook"), true, n->System.out.print("<"+n.getId()), n->System.out.println(">"));

        }catch (Exception e) {}
        */

        System.setProperty("org.graphstream.ui", "swing");
        Graph graph = new SingleGraph("DGS Graph");
        FileSourceDGS dgs = new FileSourceDGS();
        dgs.addSink(graph);
        try {
            dgs.begin("/home/etudiant/gm213204/Documents/graphstream/TP1_ressources/tp2.dgs");
            while (dgs.nextStep()) {
            }
            dgs.end();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Viewer viewer = graph.display();
        viewer.disableAutoLayout();

        //dfs(graph.getNode("A"), true, n->System.out.print("Begin"+n.getId()+": "), n->System.out.print(" End"+n.getId()+"\n"), new ArrayList<>());
        //dfs(graph.getNode("I"), false, n->System.out.print("Begin"+n.getId()+": "), n->System.out.print(" End"+n.getId()+"\n"), new ArrayList<>());
        connexeSubParts(graph);

    }
}
