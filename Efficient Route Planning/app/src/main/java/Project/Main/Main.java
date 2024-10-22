package Project.Main;

import java.io.FileNotFoundException;
import java.io.InputStream;

import Project.Graphs.GraphBuilderResult;
import Project.Graphs.GraphBuilder;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        

        // String filePath = "Efficient Route Planning/app/src/main/resources/denmark.graph";
        // GraphBuilderResult result = GraphBuilder.buildGraphFromFile(filePath);

        InputStream inputStream = Main.class.getResourceAsStream("/denmark.graph");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource 'denmark.graph' not found in classpath");
        }
        GraphBuilderResult graphResult = GraphBuilder.buildGraphFromInputStream(inputStream);



    }


}
