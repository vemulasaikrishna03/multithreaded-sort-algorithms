import threading
import random
import time

def quicksort(arr):
    if len(arr) <= 1:
        return arr
    pivot = arr.pop()
    left = []
    right = []
    for x in arr:
        if x <= pivot:
            left.append(x)
        else:
            right.append(x)
    return quicksort(left) + [pivot] + quicksort(right)

def parallel_quicksort(arr, num_threads):
    if num_threads <= 1:
        return quicksort(arr)
    if len(arr) <= 1:
        return arr
    pivot = arr.pop()
    left = []
    right = []
    for x in arr:
        if x <= pivot:
            left.append(x)
        else:
            right.append(x)
    threads = []
    if len(left) > 0:
        left_thread = threading.Thread(target=parallel_quicksort, args=(left, num_threads//2))
        threads.append(left_thread)
        left_thread.start()
    if len(right) > 0:
        right_thread = threading.Thread(target=parallel_quicksort, args=(right, num_threads//2))
        threads.append(right_thread)
        right_thread.start()
    for thread in threads:
        thread.join()
    return quicksort(left) + [pivot] + quicksort(right)


arr = [random.randint(0, 10000) for i in range(100000)]
start=time.time()
parallel_quicksort(arr, 4)
end=time.time()
p=end-start
print(p)


