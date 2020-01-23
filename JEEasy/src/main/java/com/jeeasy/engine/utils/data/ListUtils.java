package com.jeeasy.engine.utils.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jeeasy.engine.utils.data.models.EqualsInterface;
import com.jeeasy.engine.utils.data.models.IOInterface;

public class ListUtils {
	public static boolean listHasItems(List<?> list) {
		return (list != null && !list.isEmpty());
	}
	
	public static <T> List<T> newListOnNull(List<T> probablyNullList) {
		if (probablyNullList == null) {
			return new ArrayList<T>();
		}
		
		return probablyNullList;
	}
	
	public static <T> List<T> listFromIterator(Iterator<T> iterator) {
		ArrayList<T> list = new ArrayList<>();
		
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		
		return list;
	}
	
	public static <T> List<T> listFromIterable(Iterable<T> iterable) {
		return listFromIterator(iterable.iterator());
	}
	
	public static <T> List<T> copyList(List<T> list) {
		ArrayList<T> copy = new ArrayList<T>();
		
		for (T item : list) {
			copy.add(item);
		}
		
		return copy;
	}
	
	public static <T> void addListContentToList(List<T> source, List<T> destination) {
		for (T item : source) {
			destination.add(item);
		}
	}
	
	public static <T> List<T> removeEquals(List<T> list, EqualsInterface<T> filter) {
		List<T> copy = copyList(list);
		
		for (int x = 0; x < copy.size(); x++) {
			T item = copy.get(0);
			ArrayList<Integer> indexesForRemoval = new ArrayList<>();

			for (int y = (x + 1); y < copy.size(); y++) {
				if (filter.isEquals(item, copy.get(y))) {
					indexesForRemoval.add(y);
				}
			}
			
			if (!indexesForRemoval.isEmpty()) {
				x--; //Reset the X for loop
				
				for (int indexToRemove : indexesForRemoval) {
					copy.remove(indexToRemove);
				}
			}
		}
		
		return copy;
	}
	
	public static <T> boolean listContains(List<T> list, IOInterface<T, Boolean> processFunction) {
		for (T item : list) {
			if (BooleanUtils.isTrue(processFunction.output(item))) {
				return true;
			}
		}
		
		return false;
	}
	
	public static <T> List<T> filterOutItems(List<T> list, IOInterface<T, Boolean> filter) {
		List<T> copy = copyList(list);
		
		for (int x = 0; x < copy.size(); x++) {
			ArrayList<Integer> indexesForRemoval = new ArrayList<>();

			for (int y = (x + 1); y < copy.size(); y++) {
				if (BooleanUtils.isTrue(filter.output(copy.get(y)))) {
					indexesForRemoval.add(y);
				}
			}
			
			if (!indexesForRemoval.isEmpty()) {
				x--; //Reset the X for loop
				
				for (int indexToRemove : indexesForRemoval) {
					copy.remove(indexToRemove);
				}
			}
		}
		
		return copy;
	}
	
	public static <I, O> List<O> convertList(List<I> listToConvert, IOInterface<I, O> converter) {
		ArrayList<O> convertedList = new ArrayList<>();
		
		for (I item : listToConvert) {
			convertedList.add(converter.output(item));
		}
		
		return convertedList;
	}
}
