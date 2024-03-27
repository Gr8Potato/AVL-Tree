package dendrologist;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Scanner;
// DISCLAIMER: I DO NOT SUPPORT PEOPLE PLAGIARIZING OUR CODE. I DO NOT TAKE RESPONSIBILITY FOR THE UNLAWFUL ACTIONS OF OTHERS.
/**
 * A testbed for an augmented implementation of an AVL tree
 * @author William Duncan & [REDACTED]
 * @see AVLTreeAPI
 * <pre>
 * Date: 10/19/2022
 * Instructor: Dr. Duncan
 * </pre>
 */

public class Dendrologist {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String usage = "Dendrologist <order-code> <command-file>\n";
        usage += "  <order-code>:\n";
        usage += "  0 ordered by increasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  -1 for reverse lexicographical order\n";
        usage += "  1 for lexicographical order\n";
        usage += "  -2 ordered by decreasing string\n";
        usage += "  2 ordered by increasing string\n";
        usage += "  -3 ordered by decreasing string length, primary key, and reverse lexicographical order, secondary key\n";
        usage += "  3 ordered by increasing string length, primary key, and lexicographical order, secondary key\n";
        if (args.length != 2) {
            System.out.println(usage);
            System.out.println("Args: " + args[0] + " File: " + args[1]);
            throw new IllegalArgumentException("There should be 2 command line arguments.");
        }

        Comparator<String> cmp;
        int order_code = Integer.parseInt(args[0]);
        switch (order_code) {
            case -3:
                cmp = (String t, String t1) -> {
                    if (t.length() > t1.length())
                        return -1;
                    if (t.length() < t1.length())
                        return 1;
                    return (-1) * t.compareTo(t1);
                };
                break;
            case -2:
                cmp = (String t, String t1) -> {
                    if (t.length() > t1.length())
                        return -1;
                    if (t.length() < t1.length())
                        return 1;
                    return 0;
                };
                break;
            case -1:
                cmp = (String t, String t1) -> {
                    return (-1) * t.compareTo(t1);
                };
                break;
            case 0:
                cmp = (String t, String t1) -> {
                    if (t.length() > t1.length())
                        return 1;
                    if (t.length() < t1.length())
                        return -1;
                    return (-1) * t.compareTo(t1);
                };
                break;
            case 1:
                cmp = (String t, String t1) -> {
                    return t.compareTo(t1);
                };
                break;
            case 2:
                cmp = (String t, String t1) -> {
                    if (t.length() > t1.length())
                        return 1;
                    if (t.length() < t1.length())
                        return -1;
                    return 0;
                };
                break;
            case 3:
                cmp = (String t, String t1) -> {
                    if (t.length() > t1.length())
                        return 1;
                    if (t.length() < t1.length())
                        return -1;
                    return t.compareTo(t1);
                };
                break;
            default:
                System.out.println(usage);
                throw new IllegalArgumentException("First argument not valid.");
        }
        Scanner scan;
        try {
            File input = new File(args[1]);
            scan = new Scanner(input);

        } catch (Exception e) {
            System.out.println(usage);
            throw new IOException("File not valid.");
        }

        AVLTree<String> words = new AVLTree<>(cmp);
        String str;

        while (scan.hasNextLine()) {
            str = scan.nextLine();
            String[] strings = str.split(" ");//str[0] is command str[1] is command paramter. It's seperated by spaces
            switch (strings[0]) {
                case "insert":
                    String inserted = strings[1];
                    words.insert(inserted);
                    System.out.println("Inserted: " + inserted);
                    break;
                case "stats":
                    System.out.printf("Stats: size = %d, height = %d, #full-nodes = %d, fibonacci? = %b%n", words.size(), words.height(), words.fullCount(), words.isFibonacci());
                    break;
                case "traverse":
                    System.out.println("In-Order Traversal:");
                    words.traverse((x) -> {
                        System.out.println(x.toString());
                        return null;
                    });
                    break;
                case "paths":
                    ArrayList<String> list = words.genPaths();
                    System.out.println("Root-to-Leaf Paths:" + list.size());
                    for (int i = 0; i < list.size(); i++) {
                        System.out.println(list.get(i));
                    }
                    break;
                case "delete":
                    String removed = strings[1];
                    if (words.inTree(removed)) {
                        System.out.println("Deleted: " + removed);
                        words.remove(removed);
                    } else {
                        System.out.println(removed + " is not in the tree");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Parsing Error.");
            }
        }
    }
}
