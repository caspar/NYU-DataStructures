import java.util.ArrayList;
import java.io.IOException;

/**
 * @author Caspar Lant
 * @see github.com/caspar
 */
public class MostFrequentWord {

    private static BinarySearchTree<String> tree = new BinarySearchTree<String>();
    private static SortedLinkedList<String> list = new SortedLinkedList<String>();

    public static  ArrayList<String> words = new ArrayList<String>(); //check this syntax; justify its publicity

    private static String FILENAME; //final?
    public  static String OUT_NAME; //final?
    public  static int    CUTOFF;   //final?

    public static void main(String[] args) {
        try{
            FILENAME = args[0];
            CUTOFF = Integer.parseInt(args[1]);
            OUT_NAME = args[2];
        }catch(Exception oops){
            System.out.println("Please provide three parameters of correct type.\n`--help` for usage.");
            System.exit(0);
        }

        words = parse(FILENAME);

        //System.gc(); //garbage collector
        runBinarySearchTree();

        //System.gc(); //garbage collector
        //runLinkedList();

    }

    private static ArrayList<String> parse(String filename){
        FileParser parser;
        try{
            parser = new FileParser(filename);
        }catch(IOException message){
            System.out.println(message);
            System.exit(0);
            return null; // this is silly, but necessary for the next line to compile
                         // (Java doesn't see that the System.exit() call will prevent it
                         // from excecuting if parser has not been initialized)
        }
        return parser.getAllWords();
    }

    public static void runBinarySearchTree(){
        for (String word : words){
            tree.add(word);
        }
        tree.prune(CUTOFF);
        tree.traverse();
        return;
    }

    public static void runLinkedList(){
        for (String word : words){
            list.add(word);
        }
        list.prune(CUTOFF);
        list.traverse();
    }

}
