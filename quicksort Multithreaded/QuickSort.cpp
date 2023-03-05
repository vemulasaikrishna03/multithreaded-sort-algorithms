#include <iostream>
#include <thread>
#include <vector>
#include <algorithm>

void quicksort(std::vector<int>& arr, int left, int right) {
    if (left >= right) {
        return;
    }

    int pivot = arr[right];
    int i = left - 1;

    for (int j = left; j < right; j++) {
        if (arr[j] < pivot) {
            i++;
            std::swap(arr[i], arr[j]);
        }
    }

    std::swap(arr[i + 1], arr[right]);
    pivot = i + 1;

    std::thread left_thread(quicksort, std::ref(arr), left, pivot - 1);
    std::thread right_thread(quicksort, std::ref(arr), pivot + 1, right);

    left_thread.join();
    right_thread.join();
}

int main() {
   const int size = 10000;
    const int range_min = 0;
    const int range_max = 100;

    random_device rd;
    mt19937 gen(rd());
    uniform_int_distribution<> dis(range_min, range_max);

    std::vector<int> arr(size);
    for (int i = 0; i < size; i++) {
        arr[i] = dis(gen);
    }


    quicksort(arr, 0, size - 1);


    return 0;
}
