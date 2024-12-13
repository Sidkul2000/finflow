package com.java.project.FinFlow.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

public class utils {
	
	private static final HttpClient httpClient = HttpClient.newHttpClient();
	
	public static CompletableFuture<HttpResponse<String>> sendRequest(String url, String method, String body) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                                                 .uri(URI.create(url))
                                                 .header("Accept", "application/json");

        switch (method.toUpperCase()) {
            case "GET":
                builder.GET();
                break;
            case "POST":
                builder.POST(BodyPublishers.ofString(body != null ? body : ""));
                break;
            case "PUT":
                builder.PUT(BodyPublishers.ofString(body != null ? body : ""));
                break;
            case "DELETE":
                if (body != null && !body.isEmpty()) {
                    builder.method("DELETE", BodyPublishers.ofString(body));
                } else {
                    builder.DELETE();
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        HttpRequest request = builder.build();

        return httpClient.sendAsync(request, BodyHandlers.ofString());
    }
}
