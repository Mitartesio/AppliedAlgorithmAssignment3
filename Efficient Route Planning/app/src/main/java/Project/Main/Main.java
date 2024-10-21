package Project.Main;

import java.io.FileNotFoundException;

import Project.Graphs.GraphBuilderResult;
import Project.Graphs.GraphBuilder;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        

        String filePath = "Efficient Route Planning/app/src/main/resources/denmark.graph";
        GraphBuilderResult result = GraphBuilder.buildGraphFromFile(filePath);




    }


}
