package org.tpri;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSource;
import org.graphstream.stream.file.FileSourceDGS;
import org.graphstream.stream.file.FileSourceEdge;
import org.graphstream.stream.file.FileSourceFactory;
import org.graphstream.ui.view.Viewer;

import java.io.IOException;


public class Main {




    public static void main(String args[])  {

            Graph graph = new DefaultGraph("g");
            FileSourceEdge fs= new FileSourceEdge();
            fs.addSink(graph);
            try {
                fs.readAll("src/tp-ri-ressource/com-dblp.ungraph.txt");
            } catch( IOException e) {
            } finally {
                fs.removeSink(graph);
            }



    }
}
