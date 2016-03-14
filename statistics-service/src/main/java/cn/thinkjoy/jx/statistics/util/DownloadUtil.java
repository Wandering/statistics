package cn.thinkjoy.jx.statistics.util;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 文件下载工具类
 * @Author: qizai
 * @Version: V1.0.0
 */
public class DownloadUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(DownloadUtil.class);

	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	private static final int DEFAULT_DOWN_ERROR_CODE = 301;

	public static final String stream_contentType = "application/octet-stream";
	public static final String xls_contentType = "application/vnd.ms-excel";
	public static final String xlsx_contentType = "application/x-xls";
	public static final String doc_contentType = "application/msword";
	public static final String avi_contentType = "video/avi";
	public static final String pdf_contentType = "application/pdf";
	public static final String exe_contentType = "application/x-msdownload";
	public static final String apk_contentType = "application/vnd.android.package-archive";
	public static final String txt_contentType = "text/plain";
	private static final String XLS = ".xls";
	private static final String XLSX = ".xlsx";
	private static final String TXT = ".txt";
    private static final String CSV = ".csv";
	private static Map<String, String> fileNameFix = new LinkedHashMap<String, String>();
	static {
		fileNameFix.put("txt", txt_contentType);
		fileNameFix.put("apk", apk_contentType);
		fileNameFix.put("exe", exe_contentType);
		fileNameFix.put("pdf", pdf_contentType);
		fileNameFix.put("avi", avi_contentType);
		fileNameFix.put("doc", doc_contentType);
		fileNameFix.put("xls", xls_contentType);
		fileNameFix.put("xlsx", xlsx_contentType);
	}

	/**
	 * 根据文件后缀名获取响应格式
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getContentType(String fileName) {
		String fileFix = StringUtils.substringAfterLast(fileName, ".");
		String type = fileNameFix.get(StringUtils.lowerCase(fileFix));
		if (null != type) {
			return type;
		}
		return stream_contentType;
	}

	/**
	 * 文件下载
	 *
	 *            请求
	 * @param response
	 *            响应
	 * @param fileName
	 *            下载文件名
	 *            类型
	 * @param inputStream
	 */
	public static void download(HttpServletResponse response, String fileName,
			InputStream inputStream) {
		response.setContentType("text/html;charset=UTF-8");
		// request.setCharacterEncoding("UTF-8");
		if (null == inputStream) {
			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
			return;
		}
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			long fileLength = inputStream.available();
			response.setContentType(getContentType(fileName));
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("utf-8"), "ISO8859-1"));
			response.setHeader("Content-Length", String.valueOf(fileLength));

			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (UnsupportedEncodingException e) {
			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
		} catch (IOException e) {
			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(inputStream);
		}
		logger.warn("下载文件:{}.", fileName);
	}

	/** 客户端请求返回响应 */
	public static void download(HttpServletResponse response,
			byte[] responseBytes) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		// request.setCharacterEncoding("UTF-8");
		if (null == responseBytes) {
			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
			return;
		}
		long fileLength = responseBytes.length;
		response.setContentType(stream_contentType);
		response.setHeader("Content-Length", String.valueOf(fileLength));

		InputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new ByteArrayInputStream(responseBytes);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[DEFAULT_BUFFER_SIZE];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			// Exceptions.error(e);
			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
		} finally {
			IOUtils.closeQuietly(bis);
			IOUtils.closeQuietly(bos);
		}
	}

	/**
	 * 导出XLS文件
	 * 
	 * @param response
	 * @param fileName
	 *            自动在末尾添加.xls
	 * @param inputStream
	 */
	public static void downloadXls(HttpServletResponse response,
			String fileName, InputStream inputStream) {
		download(response, fileName + XLS, inputStream);
	}

	/**
	 * 导出2007版excel
	 * 
	 * @param response
	 * @param fileName
	 *            文件名，不包含文件类型后缀
	 * @param inputStream
	 */
	public static void downloadXlsx(HttpServletResponse response,
			String fileName, InputStream inputStream) {
		download(response, fileName + XLSX, inputStream);
	}

	/**
	 * 导出TXT文件
	 *
	 * @param response
	 * @param fileName
	 *            自动在末尾添加.txt
	 * @param inputStream
	 * @throws Exception
	 */
	public static void downloadTxt(HttpServletResponse response,
			String fileName, InputStream inputStream) {
		download(response, fileName + TXT, inputStream);
	}
    public static void downloadCsv(HttpServletResponse response,
                                   String fileName, InputStream inputStream) {
        download(response, fileName + CSV, inputStream);
    }

//	/**
//	 * 文件上传返回响应
//	 * 
//	 * @param response
//	 * @param map
//	 */
//	public static void responseText(HttpServletResponse response,
//			Map<String, ?> map) {
//		response.setContentType("text/plain");
//		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html;charset=utf-8");
//		try {
//			response.getWriter()
//					.write(JacksonUtil.nonEmptyMapper().toJson(map));
//		} catch (IOException e) {
//			response.setStatus(DEFAULT_DOWN_ERROR_CODE);
//		}
//	}

}
