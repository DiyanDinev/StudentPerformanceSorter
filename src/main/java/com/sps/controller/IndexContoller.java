package com.sps.controller;

import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.sps.domain.StudentPerformance;
import com.sps.service.SortingService;

@Controller
public class IndexContoller {
	
	@Autowired
	private SortingService sortingService;
	
	/**
	 * 
	 * Index page of the app
	 * 
	 */
	
	@RequestMapping(value ={"/", "/index"})
	String index() {
		return "index"; 
	}
	
	/**
	 * 
	 * @param model
	 * @param sort
	 * @param file
	 * @return Index page with some additional info to display depending on what happened during processing
	 * 
	 * Using OpenCSV library to turn the text csv file to our StudentPerformance object.
	 * [The input example suggested it is a csv format, so that is what I went with]
	 * 
	 * Check if file is empty first and throw error if it is. If its not empty try processing it with OpenCSV
	 * and then sort it with the chosen method returning sort time(not precise if we count the few initializations),
	 * the records and their number
	 * 
	 */
	
	@PostMapping(value = "/UploadFile", consumes = "multipart/form-data")
	String uploadFile(Model model, @RequestParam("sort") String sort, @RequestPart("filename") MultipartFile file) {
		if(file.isEmpty()) {
			model.addAttribute("error", "File has no records");
		} else {
			try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
				  
		    @SuppressWarnings({ "unchecked", "rawtypes" })
		    CsvToBean<StudentPerformance> csvToBean = new CsvToBeanBuilder(reader).withType(StudentPerformance.class).build();
		     
		    List<StudentPerformance> records = csvToBean.parse(); 
		            
		    ImmutablePair<List<StudentPerformance>, String> pair = sortAlgPicker(records, sort);
		                
		    model.addAttribute("sortingTime", pair.getRight());
		    model.addAttribute("numberOrRecords", pair.getLeft().size()); 
		    model.addAttribute("sortedRecords", pair.getLeft());

			} catch (Exception ex) {
				ex.printStackTrace();
				model.addAttribute("error", "Problem loading the file");
			}
		}
		
		return "index"; 
	}
	
	
	/**
	 * 
	 * @param records
	 * @param sort
	 * @return Pair with the List of sorted records and a String with the sort time
	 * 
	 * 
	 */
	private ImmutablePair<List<StudentPerformance>, String> sortAlgPicker(List<StudentPerformance> records,String sort){
        if(sort.equals("Bubble")) {
        	return sortingService.bubbleSort(records);
        } else if(sort.equals("Heap")) {          	
        	return sortingService.heapSort(records);
        } else if(sort.equals("Merge")) {
        	return sortingService.mergeSort(records);
        } else { //Default for when no method is recognised for some reason
        	return new ImmutablePair<List<StudentPerformance>, String>(records, sort);
        }	
	}

}
