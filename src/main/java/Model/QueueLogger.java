package Model;

import javafx.util.Pair;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class QueueLogger implements Iterable<Pair<Integer,String>> {
    private LinkedList<Pair<Integer, String>> log;
    private LinkedList<Pair<Integer, String>> backup;
    private boolean backupNecessity;

    public QueueLogger(){
        log = new LinkedList<>();
        backup = new LinkedList<>(log);
        backupNecessity = true;

    }

    /**
     * Register a line in log.
     * @param instructionCode
     * @param line
     */
    public void register(Integer instructionCode, String line){
        log.add(new Pair<Integer, String>(instructionCode, line));
    }

    /**
     * Return a specified log line.
     * @param numLine
     * @return Specified log line (Instruction Code, Description).
     */
    public Pair<Integer, String> getLine(int numLine){
        return log.get(numLine);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Pair<Integer, String>> iterator() {
        //Si es la primera vez que se llama el iterador backapea.
        if (backupNecessity) backup = new LinkedList<>(log);
        backupNecessity = false;//Se quita la necesidad de backapear hasta que vuelva a empezar.
        Iterator<Pair<Integer, String>> iterator = new Iterator<Pair<Integer, String>>() {
            /**
             * Removes from the underlying collection the last element returned
             * by this iterator (optional operation).  This method can be called
             * only once per call to {@link #next}.  The behavior of an iterator
             * is unspecified if the underlying collection is modified while the
             * iteration is in progress in any way other than by calling this
             * method.
             *
             * @throws UnsupportedOperationException if the {@code remove}
             *                                       operation is not supported by this iterator
             * @throws IllegalStateException         if the {@code next} method has not
             *                                       yet been called, or the {@code remove} method has already
             *                                       been called after the last call to the {@code next}
             *                                       method
             * @implSpec The default implementation throws an instance of
             * {@link UnsupportedOperationException} and performs no other action.
             */
            @Override
            public void remove() {
                log.remove();
            }

            /**
             * Returns {@code true} if the iteration has more elements.
             * (In other words, returns {@code true} if {@link #next} would
             * return an element rather than throwing an exception.)
             *
             * @return {@code true} if the iteration has more elements
             */
            @Override
            public boolean hasNext() {
                boolean result = false;
                if (0 != log.size()){
                    result = true;
                } else {                                    //Como va a volver a empezar se inicia de nuevo la necesidad de backapear y se recupera el respaldo.
                    log = new LinkedList<>(backup);
                    backupNecessity = true;
                }
                return result;
            }

            /**
             * Returns the next element in the iteration.
             *
             * @return the next element in the iteration
             * @throws NoSuchElementException if the iteration has no more elements
             */
            @Override
            public Pair<Integer, String> next() {
                if (0 == log.size()){                       //Como se vuelve a empezar se setea la necesidad y se rescupera el respaldo.
                    log = new LinkedList<>(backup);
                    backupNecessity = true;
                    throw new NoSuchElementException();
                }
                Pair<Integer,String> result = log.getFirst();
                remove();
                return result;
            }
        };
        return iterator;
    }
}
