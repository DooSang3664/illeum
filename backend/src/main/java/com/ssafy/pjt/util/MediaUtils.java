package com.ssafy.pjt.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {
	private static Map<String, MediaType> mediaMap;
	private static final String VIDEO = "video";

	private MediaUtils() {
		throw new IllegalStateException("Utility class");
	}

	static {
		mediaMap = new HashMap<>();

		mediaMap.put("jpeg", MediaType.IMAGE_JPEG);
		mediaMap.put("jpg", MediaType.IMAGE_JPEG);
		mediaMap.put("gif", MediaType.IMAGE_GIF);
		mediaMap.put("png", MediaType.IMAGE_PNG);
		mediaMap.put("bmp", new MediaType("image", "bmp"));
		mediaMap.put("wav", new MediaType("audio", "x-wav"));
		mediaMap.put("mp3", new MediaType("audio", "mpeg"));
		mediaMap.put("avi", new MediaType(VIDEO, "x-msvideo"));
		mediaMap.put("mov", new MediaType(VIDEO, "quicktime"));
		mediaMap.put("mp4", new MediaType(VIDEO, "mp4"));
		mediaMap.put("mpeg", new MediaType(VIDEO, "mpeg"));
	}

	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toLowerCase());
	}

	public static boolean containsImageMediaType(String mediaType) {
		return mediaMap.values().contains(MediaType.valueOf(mediaType));
	}
}
