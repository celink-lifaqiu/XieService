package com.celink.xieservice.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import org.apache.log4j.Logger;

import com.celink.xieservice.utils.StringUtils;
import com.celink.xieservice.utils.application.ApplicationContextUtil;
import com.celink.xieservice.utils.listener.plugin.PropertiesLoader;


/**
 * @ClassName: FileUtil
 * @Description: TODO(文件操作工具类)
 * @author lifaqiu
 * @date 2014-1-20 上午9:54:39
 */
public class FileUtil {
	

	private final static Logger logger = Logger.getLogger(FileUtil.class);
	/**
	 * 文件读取缓冲区大小
	 */
	private static final int CACHE_SIZE = 1024;

	/**
	 * @param remoteUrl
	 *            远程下载地址
	 * @param folder
	 *            保存目录
	 * @param fileName
	 *            文件名
	 * @return 成功返回true，失败false
	 */
	public static boolean downloadRemoteFile(String remoteUrl, String folder,
			String fileName) {
		boolean downloadFinished = false;
		try {
			URL url = new URL(remoteUrl);
			InputStream is = url.openStream();
			File f = new File(folder);
			f.mkdirs();
			OutputStream os = new FileOutputStream(folder + fileName);
			int bytesRead = 0;
			byte[] buffer = new byte[2048];
			while ((bytesRead = is.read(buffer, 0, 2048)) != -1) {
				os.write(buffer, 0, bytesRead);
			}

			downloadFinished = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			downloadFinished = false;
		}
		return downloadFinished;
	}

	/**
	 * <p>
	 * 二进制数据写文件
	 * </p>
	 * 
	 * @param bytes
	 *            二进制数据
	 * @param filePath
	 *            文件生成目录
	 */
	public static void byteArrayToFile(byte[] bytes, String filePath)
			throws Exception {

		InputStream in = new ByteArrayInputStream(bytes);
		File destFile = new File(filePath);
		if (!destFile.getParentFile().exists()) {
			destFile.getParentFile().mkdirs();
		}
		destFile.createNewFile();
		OutputStream out = new FileOutputStream(destFile);
		byte[] cache = new byte[CACHE_SIZE];
		int nRead = 0;

		while ((nRead = in.read(cache)) != -1) {
			out.write(cache, 0, nRead);
			out.flush();
		}

		out.close();
		in.close();

	}

	/**
	 * <p>
	 * 文件转换为二进制数组
	 * </p>
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static byte[] fileToByte(String filePath) throws Exception {

		byte[] data = new byte[0];
		File file = new File(filePath);
		if (file.exists()) {
			if(file.isFile()) {
				FileInputStream in = new FileInputStream(file);
				ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
				byte[] cache = new byte[CACHE_SIZE];
				int nRead = 0;
				while ((nRead = in.read(cache)) != -1) {
					out.write(cache, 0, nRead);
					out.flush();
				}
				out.close();
				in.close();
				data = out.toByteArray();
			}
		}
		return data;
	}

	public static void deleteFile(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	/**
	 * byte数组转换成16进制字符串
	 * 
	 * @param src
	 * @return
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

	public static boolean isLegal(byte[] fileBytes) {
		boolean isLegal = false;
		try {

			if (null != fileBytes) {
				byte[] typeBytes = new byte[8];
				for (int i = 0; i < 8; i++) {
					typeBytes[i] = fileBytes[i];
				}
				// 获取文件头
				String fileTypeHexString = FileUtil.bytesToHexString(typeBytes);
				// 从配置文件加载允许的文件类型
				String allowTypes = PropertiesLoader.getInstance()
						.getStringProperty("file.allowTypes", "");
				if (!StringUtils.isEmpty(allowTypes)) {
					String[] atArray = allowTypes.split(",");

					for (String type : atArray) {
						if (fileTypeHexString.contains(type.toLowerCase())) {
							isLegal = true;
							break;
						}
					}

				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return isLegal;
	}

	/**
	 * 将文件读取为16进制String Read original File and transfer it into Hex String
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String readOriginal2Hex(String filePath) throws IOException {
		FileInputStream fin = new FileInputStream(new File(filePath));
		StringWriter sw = new StringWriter();

		int len = 1;
		byte[] temp = new byte[len];

		/* 16进制转化模块 */
		for (; (fin.read(temp, 0, len)) != -1;) {
			if (temp[0] > 0xf && temp[0] <= 0xff) {
				sw.write(Integer.toHexString(temp[0]));
			} else if (temp[0] >= 0x0 && temp[0] <= 0xf) {// 对于只有1位的16进制数前边补“0”
				sw.write("0" + Integer.toHexString(temp[0]));
			} else { // 对于int<0的位转化为16进制的特殊处理，因为Java没有Unsigned int，所以这个int可能为负数
				sw.write(Integer.toHexString(temp[0]).substring(6));
			}
		}

		return sw.toString();
	}

	public static String format(byte[] bt) {
		int line = 0;
		StringBuilder buf = new StringBuilder();
		for (byte d : bt) {
			if (line % 16 == 0)
				buf.append(String.format("%05x: ", line));
			buf.append(String.format("%02x  ", d));
			line++;
			if (line % 16 == 0)
				buf.append("\n");
		}
		buf.append("\n");
		return buf.toString();
	}

	/*
	 * 将16进制数字解码成字符串
	 */
	public static String decode(String bytes) {
		String hexString = "0123456789ABCDEF"; // 此处不可随意改动
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static String deUnicode(String content) {// 将16进制数转换为汉字
		String enUnicode = null;
		String deUnicode = null;
		for (int i = 0; i < content.length(); i++) {
			if (enUnicode == null) {
				enUnicode = String.valueOf(content.charAt(i));
			} else {
				enUnicode = enUnicode + content.charAt(i);
			}
			if (i % 4 == 3) {
				if (enUnicode != null) {
					if (deUnicode == null) {
						deUnicode = String.valueOf((char) Integer.valueOf(
								enUnicode, 16).intValue());
					} else {
						deUnicode = deUnicode
								+ String.valueOf((char) Integer.valueOf(
										enUnicode, 16).intValue());
					}
				}
				enUnicode = null;
			}

		}
		return deUnicode;
	}

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];

		targets[0] = (byte) (res & 0xff);// 最低位
		targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
		targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
		targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
		return targets;
	}
	
	
	 
	class UploadFile extends Thread{
		
		
		public UploadFile(int port1, String fileName1){
			port = port1;
			fileName = fileName1;
		}
	     
	    int port;
	    String fileName;     
	    /**
	     * @param args
	     */
	    @Override
	    public void run() {
	        try{
	            //服务器开启
	            ServerSocket serverSocket=new ServerSocket(port);
	            while(true){
	            	//等待客户端连接
	                Socket client=serverSocket.accept();
	                String filepath = ApplicationContextUtil.getContext().getResource("").getFile().getAbsolutePath() + File.separator + "fileTransfer" + File.separator + fileName;
					
                    File newFile=new File(filepath);               //创建文件对象
                    //打开文件输出流，准备将读取内容写入相应文件
                    BufferedOutputStream file_context_in_buf=new BufferedOutputStream(new FileOutputStream(newFile,false));
                    client.close(); //关闭socket
	            }
	        }
	        catch(Exception e){
	            e.printStackTrace();
	        }
	    }
	}
	
}
