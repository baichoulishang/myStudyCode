import sort.IndexMinPQ;
import utils.In;
import utils.StdOut;

import java.util.function.Predicate;

public class Multiway implements Predicate {

    // This class should not be instantiated.
    private Multiway() {
    }

    // merge together the sorted input streams and write the sorted result to standard output
    private static void merge(In[] streams) {
        int n = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<String>(n);
        for (int i = 0; i < n; i++)
            if (!streams[i].isEmpty())
                pq.insert(i, streams[i].readString());

        // Extract and print min and read next from its stream. 
        while (!pq.isEmpty()) {
            StdOut.print(pq.minKey() + " ");
            int i = pq.delMin();
            if (!streams[i].isEmpty())
                pq.insert(i, streams[i].readString());
        }
        StdOut.println();
    }


    public static void main(String[] args) {
        int n = args.length;
        In[] streams = new In[n];
        for (int i = 0; i < n; i++)
            streams[i] = new In(args[i]);
        merge(streams);
    }

    @Override
    public boolean test(Object o) {
        return false;
    }

    @Override
    public Predicate and(Predicate other) {
        return null;
    }
}
