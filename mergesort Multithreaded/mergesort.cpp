#include <iostream>
#include <thread>
#include <vector>
#include <random>

using namespace std;

void merge(vector<int>& arr, int l, int m, int r) {
    int n1 = m - l + 1;
    int n2 = r - m;

    vector<int> L(n1);
    vector<int> R(n2);

    for (int i = 0; i < n1; i++) {
        L[i] = arr[l + i];
    }
    for (int j = 0; j < n2; j++) {
        R[j] = arr[m + 1 + j];
    }

    int i = 0, j = 0, k = l;

    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while (j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void merge_sort(vector<int>& arr, int l, int r) {
    if (l < r) {
        int m = l + (r - l) / 2;

        thread t1(merge_sort, ref(arr), l, m);
        thread t2(merge_sort, ref(arr), m + 1, r);

        t1.join();
        t2.join();

        merge(arr, l, m, r);
    }
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

    merge_sort(arr, 0, size- 1);

    for (int i = 0; i < n; i++) {
        cout << arr[i] << " ";
    }
    cout << endl;

    return 0;
}

