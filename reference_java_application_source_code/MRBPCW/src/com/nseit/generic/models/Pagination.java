package com.nseit.generic.models;

public class Pagination {

	private int page_size = 0;
	private int page_number = 0;
	private int total_records = 0;
	private int page_records = 0;
	private int start = 0;
	private int end = 0;
	private int total_pages = 0;

	public void setProperties(int count) {
		total_records = count;
		total_pages = (int) Math.ceil(((double) count / (double) page_size));
		start = (((page_size * page_number) - page_size)) + 1;
		if (page_number > 1) {
			end = (page_size + start) - 1;
		} else {
			end = page_size;
		}
		if (page_size == 0) {
			start = 0;
			end = count;
			total_pages = 1;
		}
	}

	public Pagination(int page_size, int page_number) {
		this.page_number = page_number;
		this.page_size = page_size;
	}

	public int getPage_size() {
		return page_size;
	}

	public void setPage_size(int page_size) {
		this.page_size = page_size;
	}

	public int getPage_number() {
		return page_number;
	}

	public void setPage_number(int page_number) {
		this.page_number = page_number;
	}

	public int getTotal_records() {
		return total_records;
	}

	public void setTotal_records(int total_records) {
		this.total_records = total_records;
	}

	public int getPage_records() {
		return page_records;
	}

	public void setPage_records(int page_records) {
		this.page_records = page_records;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getTotal_pages() {
		return total_pages;
	}

	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}
}
