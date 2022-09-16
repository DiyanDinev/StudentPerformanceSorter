package com.sps.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.sps.domain.StudentPerformance;

@Service
public class SortingService {
	
	
	/**
	 * 
	 * @param List of StudentPerformance
	 * @return Pair with the sorted List and a string with sort times
	 * 
	 * 
	 */
	public ImmutablePair<List<StudentPerformance>, String> bubbleSort(List<StudentPerformance> sp){
		StopWatch sw = new StopWatch();		
		sw.start();
		
		StudentPerformance spHolder = new StudentPerformance();
	    
		for(int i=0; i<sp.size() - 1; i++){
			for(int j= 1; j<sp.size() - i; j++){   //After we push the highest element at the end, we do not need to look at it
				if(sp.get(j-1).getPerformanceScore() <  sp.get(j).getPerformanceScore()){ // using < so that highest score is 1st and lowest last 
					
						//Update holder element
						spHolder.setPerformanceScore(sp.get(j).getPerformanceScore());
						spHolder.setStudentName(sp.get(j).getStudentName());
						
						//Swap 2nd object with 1st one
	                    sp.get(j).setPerformanceScore(sp.get(j-1).getPerformanceScore());
	                    sp.get(j).setStudentName(sp.get(j-1).getStudentName());
	                    
	                    //Swap 1st object with holder
	                    sp.get(j-1).setPerformanceScore(spHolder.getPerformanceScore());
	                    sp.get(j-1).setStudentName(spHolder.getStudentName());
	                }
	            }
		}
		
		sw.stop();
		
		return new ImmutablePair<List<StudentPerformance>, String>(sp, "Sorting time in nano seconds: " + sw.getLastTaskTimeNanos() + "  Sorting time in seconds: " + sw.getTotalTimeSeconds());
	}
	
	/**
	 * 
	 * @param List of StudentPerformance
	 * @return Pair with the sorted List and a string with sort times
	 * 
	 */
	public ImmutablePair<List<StudentPerformance>, String> heapSort(List<StudentPerformance> sp){
		StopWatch sw = new StopWatch();
		sw.start();
		StudentPerformance spHolder = new StudentPerformance();
		
	    for (int i = sp.size() / 2 - 1; i >= 0; i--) {
	    	heapify(sp, sp.size(), i);
	    }
	    
	    for (int i = sp.size() - 1; i >= 0; i--) {
	    	spHolder.setStudentPerformance(sp.get(0));
	    	
	    	sp.get(0).setStudentPerformance(sp.get(i));
	    	
	    	sp.get(i).setStudentPerformance(spHolder);
	    	
	        heapify(sp, i, 0);
	    }
	    
	    sw.stop();
	    
		return new ImmutablePair<List<StudentPerformance>, String>(sp, "Sorting time in nano seconds: " + sw.getLastTaskTimeNanos() + "  Sorting time in seconds: " + sw.getTotalTimeSeconds());
	}
	
	/**
	 * 
	 * @param List of StudentPerformance, size of list and index
	 * 
	 * Create heap nodes and swap them around
	 * 
	 */
	private static void heapify(List<StudentPerformance> sp, int length, int i) {
		StudentPerformance spHolder = new StudentPerformance();
	    int left = 2 * i + 1;
	    int right = 2 * i + 2;
	    int largest = i;
	    if (left < length && sp.get(left).getPerformanceScore() < sp.get(largest).getPerformanceScore()) {
	        largest = left;
	    }
	    if (right < length && sp.get(right).getPerformanceScore() < sp.get(largest).getPerformanceScore()) {
	        largest = right;
	    }
	    if (largest != i) {
	    	spHolder.setStudentPerformance(sp.get(i));
	    	
	    	sp.get(i).setStudentPerformance(sp.get(largest));
	    	
	    	sp.get(largest).setStudentPerformance(spHolder);

	        heapify(sp, length, largest);
	    }
	}
	
	/**
	 * 
	 * @param List of StudentPerformance
	 * @return Pair with the sorted List and a string with sort times
	 * 
	 */
	public ImmutablePair<List<StudentPerformance>, String> mergeSort(List<StudentPerformance> sp){
		StopWatch sw = new StopWatch();
		
		sw.start();
		sp = mergeSort(sp, sp.size());
		sw.stop();
		
		return new ImmutablePair<List<StudentPerformance>, String>(sp, "Sorting time in nano seconds: " + sw.getLastTaskTimeNanos() + "  Sorting time in seconds: " + sw.getTotalTimeSeconds());
	}
	
	/**
	 * 
	 * @param List of StudentPerformance, size of the list(so that we don't compute it a few times)
	 * @return sorted List
	 * 
	 * Mainly used to split the list into smaller list and then merges them by calling merge()
	 * 
	 */
	private List<StudentPerformance> mergeSort(List<StudentPerformance> sp, int size) {
		if (size < 2) { //stop the calls
			return sp;
		}
	    int mid = (int) Math.ceil(size / 2);
	    List<StudentPerformance> l = new ArrayList<StudentPerformance>();
	    List<StudentPerformance> r = new ArrayList<StudentPerformance>();

	    for (int i = 0; i < mid; i++) {
	    	l.add(sp.get(i));
	    }
	    for (int i = mid; i < size; i++) {
	    	r.add(sp.get(i));
	    }
	    mergeSort(l, mid);
	    mergeSort(r, size - mid);
	    
	    return merge(sp, l, r, mid, size - mid);
	}
	
	/**
	 * 
	 * @param List of StudentPerformance and the left and right splits of it, with the sizes of the left and right part
	 * @return sorted/merged List
	 * 
	 * Mainly used to merge the smaller lists into a sorted one
	 * 
	 */
	private List<StudentPerformance> merge(List<StudentPerformance> sp, List<StudentPerformance> l, List<StudentPerformance> r, int left, int right) {
		List<StudentPerformance> holderList = new ArrayList<StudentPerformance>();
		
		int i = 0, j = 0;
		
		while (i < left && j < right) {
			if (l.get(i).getPerformanceScore() >= r.get(j).getPerformanceScore()) {
				holderList.add(l.get(i++));
			} else {
				holderList.add(r.get(j++));
			}
		}
		
		while (i < left) {
			holderList.add(l.get(i++));
		}
		
		while (j < right) {
			holderList.add(r.get(j++));
		}
		
		sp.clear();
		sp.addAll(holderList);
		
		return sp;
	}
	
	
}
