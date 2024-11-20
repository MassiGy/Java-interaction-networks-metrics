package org.tpri;

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

    }
}
