package com.benchmarking;

import com.benchmarking.insert.*;
import com.benchmarking.replace.*;
import com.benchmarking.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please provide two arguments: <operation> <mode>");
            System.exit(1);
        }

        String operation = args[0].toLowerCase();
        String mode = args[1].toLowerCase();

        // Validate the operation argument
        if (!isValidOperation(operation)) {
            System.out.println("Invalid operation. Supported operations: insert, replace, update, util, report");
            System.exit(1);
        }

        // Validate the mode argument
        if (!isValidMode(mode)) {
            System.out.println("Invalid mode. Supported modes: single, bulk, drop, ping, rename");
            System.exit(1);
        }

        // Execute the corresponding behavior based on the provided arguments
        if (operation.equals("insert") && mode.equals("single")) {
            SingleInsertThreaded.main(args);
        } else if (operation.equals("insert") && mode.equals("bulk")) {
            BulkInsertThreaded.main(args);
        } else if (operation.equals("replace") && mode.equals("single")) {
            SingleReplaceOneThreaded.main(args);
        } else if (operation.equals("replace") && mode.equals("bulk")) {
            BulkReplaceOneThreaded.main(args);
        } else if (operation.equals("update") && mode.equals("single")) {
            // Execute update behavior
        } else if (operation.equals("report") && mode.equals("bulk")) {
            // Execute report behavior
        } else if (operation.equals("util") && mode.equals("drop")) {
            Drop.main(args);
        } else if (operation.equals("util") && mode.equals("ping")) {
            Ping.main(args);
        } else if (operation.equals("util") && mode.equals("rename")) {
            Rename.main(args);
        } else {
            System.out.println("Invalid combination of operation and mode.");
        }
    }

    private static boolean isValidOperation(String operation) {
        String[] validOperations = {"insert", "replace", "update", "util", "report"};
        for (String validOp : validOperations) {
            if (validOp.equals(operation)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isValidMode(String mode) {
        String[] validModes = {"single", "bulk", "drop", "ping", "rename"};
        for (String validMd : validModes) {
            if (validMd.equals(mode)) {
                return true;
            }
        }
        return false;
    }
}
