package com.example.utils;

import com.example.entity.dto.Diary;
import com.example.entity.SortOption;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomSortUtil {

    public void sort(List<Diary> list, SortOption sortOption) {
        if (list.size() < 10) {
            insertionSort(list, sortOption);
        } else {
            quickSort(list, 0, list.size() - 1, sortOption);
        }
    }

    private void insertionSort(List<Diary> list, SortOption sortOption) {
        for (int i = 1; i < list.size(); i++) {
            Diary key = list.get(i);
            int j = i - 1;
            while (j >= 0 && compare(list.get(j), key, sortOption) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private void quickSort(List<Diary> list, int low, int high, SortOption sortOption) {
        if (low < high) {
            int pi = partition(list, low, high, sortOption);
            quickSort(list, low, pi - 1, sortOption);
            quickSort(list, pi + 1, high, sortOption);
        }
    }

    private int partition(List<Diary> list, int low, int high, SortOption sortOption) {
        // 使用三数取中法选择基准元素
        int mid = low + (high - low) / 2;
        if (compare(list.get(low), list.get(mid), sortOption) > 0) {
            swap(list, low, mid);
        }
        if (compare(list.get(low), list.get(high), sortOption) > 0) {
            swap(list, low, high);
        }
        if (compare(list.get(mid), list.get(high), sortOption) > 0) {
            swap(list, mid, high);
        }
        Diary pivot = list.get(mid);
        swap(list, mid, high - 1); // 将基准元素放在high-1位置
        int i = low;
        int j = high - 1;
        while (true) {
            while (compare(list.get(++i), pivot, sortOption) < 0) ;
            while (compare(list.get(--j), pivot, sortOption) > 0) ;
            if (i >= j) {
                break;
            }
            swap(list, i, j);
        }
        swap(list, i, high - 1); // 将基准元素放回正确位置
        return i;
    }

    private void swap(List<Diary> list, int i, int j) {
        Diary temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private int compare(Diary d1, Diary d2, SortOption sortOption) {
        if (sortOption == SortOption.RATING) {
            return Double.compare(d2.getRating(), d1.getRating());
        } else {
            return Integer.compare(d2.getPopularity(), d1.getPopularity());
        }
    }
}
