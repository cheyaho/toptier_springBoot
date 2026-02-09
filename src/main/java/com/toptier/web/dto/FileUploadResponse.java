package com.toptier.web.dto;

import java.util.Map;

public record FileUploadResponse(
    boolean uploaded,
    String url,
    String originalFileName,
    Map<String, String> error
) {
    public static FileUploadResponse success(String url, String originalFileName) {
        return new FileUploadResponse(true, url, originalFileName, null);
    }

    public static FileUploadResponse failure(String errorMessage) {
        return new FileUploadResponse(false, null, null, Map.of("message", errorMessage));
    }
}
