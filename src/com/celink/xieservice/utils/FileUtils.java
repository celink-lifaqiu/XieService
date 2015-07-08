package com.celink.xieservice.utils;



import net.sf.jmimemagic.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.celink.xieservice.app.common.FileResult;

import javax.servlet.http.HttpServletRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: Yin Jian Feng
 * Date: 14-3-6
 * Time: 下午4:25
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    public static enum FILE_MESSAGE{SUCCESS, OUT_OF_SIZE, INVALID_FILE_TYPE}

    public static FileResult saveMultipartFile(HttpServletRequest request, MultipartFile multipartFile, String key){
        FileResult fileResult = new FileResult();
        try {
            if (isValidMultipartFile(multipartFile, key, fileResult)){
                String filePath = PropertiesUtils.getInstance("config").getValue("com.magic.file."+key);
                String relativeFilePath = filePath + File.separator + ((CommonsMultipartFile)multipartFile).getFileItem().getName();
                File file = new File(getRootFile() + File.separator + relativeFilePath);
                if (!file.exists()) file.mkdirs();
                multipartFile.transferTo(file);
                fileResult.setMessage(FILE_MESSAGE.SUCCESS);
                fileResult.setFilePath(relativeFilePath);

                StringBuffer urlbuf = request.getRequestURL();
                String uri = request.getRequestURI();
                String host = urlbuf.substring(0, urlbuf.length() - uri.length());
                relativeFilePath = relativeFilePath.replaceAll("\\\\", "/");
                fileResult.setUrl(host + request.getContextPath() + "/common/images/" + relativeFilePath);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileResult;
    }
    private static boolean isValidMultipartFile(MultipartFile multipartFile, String key, FileResult fileResult){
        PropertiesUtils propertiesUtils = PropertiesUtils.getInstance("config");
        String maxSize = propertiesUtils.getValue("com.magic.file."+key+".maxsize");
        String allowFileTypes = propertiesUtils.getValue("com.magic.file."+key+".allow_file_types");
        if (!"all".equals(allowFileTypes)){
            String[] allowFileTypeArr = allowFileTypes.split(",");
            String fileType = multipartFile.getContentType();
            if (allowFileTypeArr != null && allowFileTypeArr.length>0) {
                List<String> typeList = Arrays.asList(allowFileTypeArr);
                if (!typeList.contains(fileType)){
                    fileResult.setMessage(FILE_MESSAGE.INVALID_FILE_TYPE);
                    return false;
                }
            }
        }
        if (StringUtils.isNotEmpty(maxSize)){
            long size = multipartFile.getSize();
            long allowMaxSize = FileSizeUtil.formatFileSizeToBytes(maxSize);
            if(size > allowMaxSize){
                fileResult.setMessage(FILE_MESSAGE.OUT_OF_SIZE);
                return false;
            }
        }
        return true;
    }

    public static String getFileType(byte[] bytes) throws MagicParseException, MagicException, MagicMatchNotFoundException {
        if (bytes!=null&&bytes.length>0){
            Magic parser = new Magic() ;
            MagicMatch match = parser.getMagicMatch(bytes);
            return match.getMimeType();
        }
        return "";
    }

    public static String formatImageToBase64String(byte[] bytes) throws MagicParseException, MagicException, MagicMatchNotFoundException {
        if (bytes != null&&bytes.length>0) {
            String fileType = FileUtils.getFileType(bytes);
            String base64Str = Base64.encodeBase64String(bytes);
            return "data:"+fileType+";base64,"+base64Str;
        }
        return "";
    }

    public static String getRootFile(){
        PropertiesUtils appConfig = PropertiesUtils.getInstance("config");
        return appConfig.getValue("com.magic.file.root");
    }


    public static String zip(File inputFile) throws FileNotFoundException {
        return zip(null, inputFile);
    }
    public static String zip(String zipFileName, File inputFile) throws FileNotFoundException {
        if (StringUtils.isEmpty(zipFileName)){
            zipFileName = String.valueOf(DateUtils.getDate().getTime());
        }
        if (!zipFileName.endsWith(".zip")){
            zipFileName += ".zip";
        }

        File f = null;
        String path = Pattern.compile("[\\/]]").matcher(zipFileName).replaceAll(File.separator);
        int endIndex = path.lastIndexOf(File.separator);
        path = path.substring(0, endIndex);
        f = new File(path);

        ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(
            new FileOutputStream(zipFileName)
        ));

        return zipFileName;
    }

    private static void compress(ZipOutputStream zos, File f, String fileName) throws IOException {
        if (!f.isDirectory()){
            zos.putNextEntry(new ZipEntry(fileName));
            FileInputStream fis = new FileInputStream(f);
            inStream2outStream(fis, zos);
            zos.flush();
            fis.close();
            zos.closeEntry();
        }
    }
    private static int BUF_SIZE = 40480;
    private static void inStream2outStream(InputStream is, OutputStream os) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int bytesRead = 0;
        for (byte[] buffer = new byte[BUF_SIZE]; ((bytesRead = bis.read(buffer, 0, BUF_SIZE)) != -1);) {
            bos.write(buffer, 0, bytesRead); // 将流写入
        }
    }
    public static String imageDir = "images";
    public static String fileDir = "/Users/service/Documents/";
    public static String file = fileDir+imageDir+"/";
//    public static String fileDir = "D:/";
//    public static String file = fileDir+imageDir+"/";
    
    
    public static void writeImage(String imageName, String imageUrl) throws IOException, FileNotFoundException {
		//new一个URL对象  
        URL url = new URL(imageUrl);  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        byte[] data = readInputStream(inStream);  
        //new一个文件对象用来保存图片，默认保存当前工程根目录  
        File imageFile = new File(file+imageName+".jpg");
        //创建输出流  
        FileOutputStream outStream = new FileOutputStream(imageFile);  
        //写入数据  
        outStream.write(data);  
        //关闭输出流  
        outStream.close();  
	}

    public static byte[] readInputStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];  
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;  
        //使用一个输入流从buffer里把数据读取出来  
        while( (len=inStream.read(buffer)) != -1 ){  
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);  
        }  
        //关闭输入流  
        inStream.close();  
        //把outStream里的数据写入内存  
        return outStream.toByteArray();  
	}
    
    
}
