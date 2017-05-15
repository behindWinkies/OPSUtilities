package com.company.OpsUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class WriteMAMDeleteQuery {

    public static void main(String[] args) {

        //User input
        System.out.println("Please enter the path and filename (must be Excel 97), or 'q' to quit: ");
        Scanner reader = new Scanner(System.in);
        String inputFile = reader.next();

        //Quit the application
        if (inputFile.equalsIgnoreCase("q")) {
            System.out.println("Exiting...");
            System.exit(0);
        }

        //Create the query
        MamQuery newQuery;
        newQuery = new MamQuery(inputFile);

        try {
            //Create a a file to write to
            PrintWriter writer = new PrintWriter("ImageQuery.txt","UTF-8");
            String result = newQuery.getQuery();
            //If we can't find any such file, exit with a message to the user
            if (result.equals("File not found.")) {
                System.out.println(result);
            } else {
                writer.write(newQuery.getQuery());
                //Create the user's current directory
                System.out.println("The query has been created in " + System.getProperty("user.dir"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
