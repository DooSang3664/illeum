package com.ssafy.pjt.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author https://github.com/woobong/spring-boot-jpa-summernote-image-upload-example
 *
 */
public class UploadFileUtils {
	private UploadFileUtils() {
		throw new IllegalStateException("Utility class");
	}

	private static final Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);

	/** MultipartFile 객체에서 File 객체를 복사 생성 */
	public static File convert(String path, MultipartFile file) throws IOException {
		File uploadPathDir = new File(path);

		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		File convFile = new File(path + File.separator + file.getOriginalFilename());
		convFile.createNewFile(); // NOSONAR

		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			logger.info(e.toString());
		}
		return convFile;
	}

	/**
	 * @param filePath
	 * @param multipartFile
	 * @return 생성된 파일 명(유일한 값)
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String fileSave(String uploadPath, MultipartFile file) throws IOException {

		File uploadPathDir = new File(uploadPath);

		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		// 파일 중복명 처리
		String genId = UUID.randomUUID().toString();
		genId = genId.replace("-", "");

		String originalfileName = file.getOriginalFilename();
		String fileExtension = getExtension(originalfileName);
		String saveFileName = genId + "." + fileExtension;

		String savePath = calcPath(uploadPath);

		File target = new File(uploadPath + savePath, saveFileName);

		FileCopyUtils.copy(file.getBytes(), target);

		return makeFilePath(uploadPath, savePath, saveFileName);
	}

	/**
	 * @param filePath
	 * @param filename
	 * @param thumbnail
	 * @param multipartFile
	 * @return 생성된 파일 명(유일한 값)
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String fileSave(String uploadPath, String savePath, String filename, boolean thumbnail,
			MultipartFile file) throws IOException {

		File uploadPathDir = new File(uploadPath + savePath);

		if (!uploadPathDir.exists()) {
			uploadPathDir.mkdirs();
		}

		String originalfileName = file.getOriginalFilename();
		String fileExtension = getExtension(originalfileName);
		String saveFileName = filename + "." + fileExtension;

		File target = new File(uploadPath + savePath, saveFileName);
		FileCopyUtils.copy(file.getBytes(), target);

		if (thumbnail) {
			for (int size : new int[] { 16, 32, 64, 128, 256 }) {
				String thumbnailName = filename + "_" + size + "." + fileExtension;
				makeThumbnail(uploadPath, savePath, saveFileName, thumbnailName, size, size);
			}
		}

		return makeFilePath(uploadPath, savePath, saveFileName);
	}

	/**
	 * 파일이름으로부터 확장자를 반환
	 * 
	 * @param fileName 확장자를 포함한 파일 명
	 * @return 파일 이름에서 뒤의 확장자 이름만을 반환
	 */
	public static String getExtension(String fileName) {
		int dotPosition = fileName.lastIndexOf('.');

		if (-1 != dotPosition && fileName.length() - 1 > dotPosition) {
			return fileName.substring(dotPosition + 1);
		} else {
			return "";
		}
	}

	public static String getPath(String fullPath) {
		int separatePostion = fullPath.lastIndexOf('/');

		if (-1 != separatePostion && fullPath.length() - 1 > separatePostion) {
			return fullPath.substring(0, separatePostion);
		} else {
			return "";
		}
	}

	public static String getFileName(String fullPath) {
		int dotPosition = fullPath.lastIndexOf('.');
		int separatePostion = fullPath.lastIndexOf('/');

		if ((-1 != dotPosition && fullPath.length() - 1 > dotPosition)
				&& (-1 != separatePostion && fullPath.length() - 1 > separatePostion)) {
			return fullPath.substring(separatePostion + 1, dotPosition);
		}
		return "";
	}

	public static String getThumbnailFileName(String fullPath, int size) {
		String path = getPath(fullPath);
		String ext = getExtension(fullPath);
		String filename = getFileName(fullPath);
		return path + '/' + "s_" + filename + "_" + size + '.' + ext;
	}

	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();

		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH));
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));

		makeDir(uploadPath, yearPath, monthPath, datePath);

		logger.info(datePath);

		return datePath;
	}

	private static void makeDir(String uploadPath, String... paths) {

		logger.info(paths[paths.length - 1] + " : " + new File(paths[paths.length - 1]).exists());
		if (new File(paths[paths.length - 1]).exists()) {
			return;
		}

		for (String path : paths) {
			File dirPath = new File(uploadPath + path);

			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
		}
	}

	private static String makeFilePath(String uploadPath, String path, String fileName) {
		String filePath = uploadPath + path + File.separator + fileName;
		return filePath.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}


	private static BufferedImage resizeAndCrop(BufferedImage bufferedImage, Integer width, Integer height) {

		Mode mode = (double) width / (double) height >= (double) bufferedImage.getWidth()
				/ (double) bufferedImage.getHeight() ? Scalr.Mode.FIT_TO_WIDTH : Scalr.Mode.FIT_TO_HEIGHT;

		bufferedImage = Scalr.resize(bufferedImage, Scalr.Method.ULTRA_QUALITY, mode, width, height);

		int x = 0;
		int y = 0;

		if (mode == Scalr.Mode.FIT_TO_WIDTH) {
			y = (bufferedImage.getHeight() - height) / 2;
		} else if (mode == Scalr.Mode.FIT_TO_HEIGHT) {
			x = (bufferedImage.getWidth() - width) / 2;
		}

		bufferedImage = Scalr.crop(bufferedImage, x, y, width, height);

		return bufferedImage;
	}

	private static String makeThumbnail(String uploadPath, String path, String fileName, String saveName, int width,
			int height) throws IOException {

		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));

		BufferedImage destImg = resizeAndCrop(sourceImg, width, height);

		String thumbnailName = uploadPath + path + File.separator + "s_" + saveName;
		thumbnailName = thumbnailName.replace('\\', '/');
		File newFile = new File(thumbnailName);
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);

		ImageIO.write(destImg, formatName.toUpperCase(), newFile);

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '/');
	}
}
