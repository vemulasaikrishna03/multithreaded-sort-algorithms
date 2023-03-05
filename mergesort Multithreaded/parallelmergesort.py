import threading
import random
import time

def mergesort(arr):
    if len(arr) <= 1:
        return arr

    mid = len(arr) // 2
    left = arr[:mid]
    right = arr[mid:]

    left = mergesort(left)
    right = mergesort(right)

    return merge(left, right)

def merge(left, right):
    result = []
    i = 0
    j = 0

    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            result.append(left[i])
            i += 1
        else:
            result.append(right[j])
            j += 1

    result += left[i:]
    result += right[j:]
    return result

def parallel_mergesort(arr):
    if len(arr) <= 1:
        return arr

    mid = len(arr) // 2
    left = arr[:mid]
    right = arr[mid:]

    left_thread = threading.Thread(target=parallel_mergesort, args=(left,))
    right_thread = threading.Thread(target=parallel_mergesort, args=(right,))

    left_thread.start()
    right_thread.start()

    left_thread.join()
    right_thread.join()

    return merge(left, right)

arr = [random.randint(0, 10000) for i in range(10000)]
start=time.time()
print(start)
parallel_mergesort(arr)
end=time.time()
print(end-start)