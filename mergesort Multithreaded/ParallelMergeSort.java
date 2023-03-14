import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

// creating the parallel mergesort class
public class ParallelMergeSort {
    public static void main(String[] args) {
        long startTime;
        long endTime;


        int[] largeArray1 = new int[7000000];
        
        for(int i = 7000000; i<0; i--){
            largeArray1[7000000-i] = i;
        }

        startTime = System.currentTimeMillis();
        parallelMergeSort(largeArray1);
        endTime = System.currentTimeMillis();

        System.out.println(" Array Parallel = " + (endTime - startTime) + " ms");

        
    }
    // implementation of parallel merge method
    public static void parallelMergeSort(int[] array) {
        // creating the thread pool
        SortTask mainTask = new SortTask(array);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(mainTask);
    }
    // intialising the SortTask class and extending the RecursiveAction 
    private static class SortTask extends RecursiveAction {
        private int[] array;

        public SortTask(int[] array) {
            this.array = array;
        }
        @Override
        protected void compute() {
            if(array.length > 1) {
                int mid = array.length/2;
                int[] firstHalf = new int[mid];
                System.arraycopy(array, 0, firstHalf, 0, mid);

                int[] secondHalf = new int[array.length - mid];
                System.arraycopy(array, mid, secondHalf, 0, array.length - mid);

                SortTask firstHalfTask = new SortTask(firstHalf);
                SortTask secondHalfTask = new SortTask(secondHalf);
                invokeAll(firstHalfTask, secondHalfTask);

                merge(firstHalf, secondHalf, array);
            }
        }
    }
    // normal merge sort function
    public static void merge(int[] firstHalf, int[] secondHalf, int[] array) {
        int CIF = 0; 
        int CIS = 0; 
        int CIA = 0;


        while(CIF < firstHalf.length && CIS < secondHalf.length) {
            if(firstHalf[CIF] < secondHalf[CIS]) {
                array[CIA] = firstHalf[CIF];
                CIA++;
                CIF++;
            } else {
                array[CIA] = secondHalf[CIS];
                CIA++;
                CIS++;
            }
        }

        while(CIF < firstHalf.length) {
            array[CIA] = firstHalf[CIF];
            CIA++;
            CIF++;
        }

        while(CIS < secondHalf.length) {
            array[CIA] = secondHalf[CIS];
            CIA++;
            CIS++;
        }
    }
}
