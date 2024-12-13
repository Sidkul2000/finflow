package com.java.project.FinFlow.services;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import com.java.project.FinFlow.utils.utils;
import com.java.project.FinFlow.models.Categories;
import com.java.project.FinFlow.mapper.mappers;

import java.util.Collections;
import java.util.List;

public class RecordService {
	
	private HttpClient httpClient;
	private String baseUrl = "http://localhost:8080";

    public RecordService() {
        this.httpClient = HttpClient.newHttpClient();
    }
    
	public CompletableFuture<String> getAllRecordsByUserId(Long userId) {
		String url = baseUrl + "/record/all?userId=" + userId;
		        
		return utils.sendRequest(url, "GET", null)
		        	.thenApply(HttpResponse::body);
		        
	}
	
	public CompletableFuture<List<Categories>> getCategories() {
		String url = baseUrl + "/category/all";
		        
		return utils.sendRequest(url, "GET", null)
		        	.thenApply(HttpResponse::body)
		        	.thenApply(json -> {
						try {
							return mappers.parseCategories(json);
						} catch (Exception e) {
							System.out.println("Error in parsing mapper cetgories.");
							e.printStackTrace();
						}
						return Collections.emptyList();
					});
		        
	}
	
	public CompletableFuture<String> getFilteredRecordsByUserId(Long userId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
	
		String url = buildQueryUrl(baseUrl + "/record", userId, categoryId, fromDate, toDate);
		System.out.println("getFilteredRecordsByUserId - url: " + url);
		return utils.sendRequest(url, "GET", null)
					.thenApply(HttpResponse::body);
	}
	
	public CompletableFuture<String> updateRecord(String recordId, Double amount, Long selectedCategoryId) {
		String url = baseUrl + "/record/" + recordId + "?";
		if (amount != null) {
			url = url + "amount=" + amount;
		}
		if (selectedCategoryId != null && selectedCategoryId != -1) {
			url = url + "&categoryId=" + selectedCategoryId;
		}
		return utils.sendRequest(url, "PUT", null)
				    .thenApply(HttpResponse::body);
	}
	
	public CompletableFuture<String> deleteRecord(String recordId) {
		String url = baseUrl + "/record/" + recordId;
		System.out.println("delete record url: " + url);
		return utils.sendRequest(url, "DELETE", null)
				    .thenApply(HttpResponse::body);
	}
	
	private String buildQueryUrl(String baseUrl, Long userId, Long categoryId, LocalDate fromDate, LocalDate toDate) {
	    StringBuilder urlBuilder = new StringBuilder(baseUrl);
	    urlBuilder.append("?userId=").append(userId);

	    if (categoryId != null) {
	        urlBuilder.append("&categoryId=").append(categoryId);
	    }
	    if (fromDate != null) {
	        urlBuilder.append("&fromDate=").append(fromDate);
	    }
	    if (toDate != null) {
	        urlBuilder.append("&toDate=").append(toDate);
	    }
	    return urlBuilder.toString();
	}

}
