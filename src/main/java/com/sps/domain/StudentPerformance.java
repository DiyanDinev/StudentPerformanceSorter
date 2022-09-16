package com.sps.domain;

import com.opencsv.bean.CsvBindByPosition;

public class StudentPerformance {
	
	@CsvBindByPosition(position = 0)
	private String studentName;
	@CsvBindByPosition(position = 1)
	private double performanceScore;
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public double getPerformanceScore() {
		return performanceScore;
	}
	public void setPerformanceScore(double performanceScore) {
		this.performanceScore = performanceScore;
	}
	@Override
	public String toString() {
		return "StudentPerformance [studentName=" + studentName + ", performanceScore=" + performanceScore + "]";
	}
	
	//Makes it easier to swap objects
	public void setStudentPerformance(StudentPerformance sp) {
		this.studentName = sp.getStudentName();
		this.performanceScore = sp.getPerformanceScore();
	}

}
