import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort {

    private static class QuickSortTask extends RecursiveAction {
        private final int[] arr;
        private final int left;
        private final int right;

        public QuickSortTask(int[] arr, int left, int right) {
            this.arr = arr;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (left >= right) {
                return;
            }

            int pivot = partition(arr, left, right);

            QuickSortTask leftTask = new QuickSortTask(arr, left, pivot - 1);
            QuickSortTask rightTask = new QuickSortTask(arr, pivot + 1, right);

            invokeAll(leftTask, rightTask);
        }

        private int partition(int[] arr, int left, int right) {
            int pivot = arr[right];
            int i = left - 1;

            for (int j = left; j < right; j++) {
                if (arr[j] < pivot) {
                    i++;
                    swap(arr, i, j);
                }
            }

            swap(arr, i + 1, right);
            return i + 1;
        }

        private void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {
        long startTime;
        long endTime;


        int[] largeArray1 = new int[7000000];
        
        for(int i = 7000000; i<0; i--){
            largeArray1[7000000-i] = i;
        }

        startTime = System.currentTimeMillis();

        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(new QuickSortTask(arr, 0, arr.length - 1));
        endTime = System.currentTimeMillis();
        System.out.println("Sorted array: "+(endTime-startTime));
    }
}
