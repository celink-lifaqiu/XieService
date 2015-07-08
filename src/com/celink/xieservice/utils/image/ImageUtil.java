package com.celink.xieservice.utils.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.celink.xieservice.utils.StringUtils;
import com.celink.xieservice.utils.file.FileUtil;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
  * @ClassName: ImageUtil  
  * @Description: TODO(图片工具类)  
  * @author jiangWei 
  * @date 2014-10-14 下午3:15:36  
 */
public class ImageUtil {  
    /**
      * @Description: TODO(图片缩放)  
      * @param originalFile 原图片路径
      * @param resizedFile 生成新图片路径
      * @param newWidth 新文件宽高
      * @param quality 清晰读
      * @throws IOException   
      * @throws
     */
    public static void resize(File originalFile, File resizedFile,  
            int newWidth, float quality) throws IOException {  
  
        if (quality > 1) {  
            throw new IllegalArgumentException(  
                    "Quality has to be between 0 and 1");  
        }  
  
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());  
        Image i = ii.getImage();  
        Image resizedImage = null;  
  
        int iWidth = i.getWidth(null);  
        int iHeight = i.getHeight(null);  
  
        if (iWidth > iHeight) {  
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)  
                    / iWidth, Image.SCALE_SMOOTH);  
        } else {  
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,  
                    newWidth, Image.SCALE_SMOOTH);  
        }  
  
        // This code ensures that all the pixels in the image are loaded.  
        Image temp = new ImageIcon(resizedImage).getImage();  
  
        // Create the buffered image.  
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
  
        // Copy image to buffered image.  
        Graphics g = bufferedImage.createGraphics();  
  
        // Clear background and paint the image.  
        g.setColor(Color.white);  
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
        g.drawImage(temp, 0, 0, null);  
        g.dispose();  
  
        // Soften.  
        float softenFactor = 0.05f;  
        float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
        Kernel kernel = new Kernel(3, 3, softenArray);  
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
        bufferedImage = cOp.filter(bufferedImage, null);  
  
        // Write the jpeg to a file.  
        FileOutputStream out = new FileOutputStream(resizedFile);  
  
        // Encodes image as a JPEG data stream  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
  
        JPEGEncodeParam param = encoder  
                .getDefaultJPEGEncodeParam(bufferedImage);  
  
        param.setQuality(quality, true);  
  
        encoder.setJPEGEncodeParam(param);  
        encoder.encode(bufferedImage);  
        out.close();
        
    } // Example usage  
    public static String getImageStr(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		// String imgFile = "d:\\111.jpg";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 将字符串转为图片
	 * 
	 * @param imgStr
	 * @return
	 */
	public static boolean generateImage(String imgStr, String imgFile)
			throws Exception {// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = imgFile;// 新生成的图片
			out = new FileOutputStream(imgFilePath);
			out.write(b);
			
			return true;
		} catch (Exception e) {
			throw e;
		}finally{
			out.flush();
			out.close();
		}
	}
	/**
	 * @throws Exception 
	  * @Description: TODO(保存原图片和原图片的缩略图)  
	  * @param path 
	  * @param suffix
	  * @param iconBase64
	  * @return 返回小图片名（4850583372a248aeb0bef6f92297ebbd.jpg）大图片为（4850583372a248aeb0bef6f92297ebbd_big.jpg）
	  * @throws
	 */
	public static String saveImageBigAndlittle(String path,String suffix,String iconBase64) throws Exception{
		String uuid =StringUtils.getuuid();
		if(!suffix.matches("[a-z]+")){
			suffix = suffix.substring(1);	
		}
	 /*	String filePath =path+File.separator+uuid+"_big."+suffix;
		String filePathLittle =path+File.separator+uuid+"."+suffix;
		generateImage(iconBase64,filePath);
		File originalImage = new File(filePath);  
		BufferedImage image1 = ImageIO.read(originalImage);
		BufferedImage image2 = new ImageScale().imageZoomOut(image1, 80, 80, true);
		FileOutputStream out = new FileOutputStream(filePathLittle);
		ImageIO.write(image2,suffix,out);
        out.close();*/
		//保存原图片
		String filePathLittle =path+File.separator+uuid+"."+suffix;
		generateImage(iconBase64,filePathLittle);
		
	    //resize(originalImage, new File(filePathLittle),80, 1f);
	    return uuid+"."+suffix;
	}
	/**
	 * 
	  * @Description: TODO()  
	  * @param path 图片更路经（root/icons）
	  * @param suffix 后缀
	  * @param icon 二进制图片
	  * @return
	  * @throws Exception   
	  * @throws
	 */
	public static String saveImageBigAndlittle(String path,String suffix,byte []icon) throws Exception{
		if(!suffix.matches("[a-z]+")){
			suffix = suffix.substring(1);	
		}
		String uuid =StringUtils.getuuid();
		/*String fileName = uuid+"_big."+ suffix;
		String filePath = path  + "/" + fileName;
		FileUtil.byteArrayToFile(icon, filePath);
		String fileNameLittle=uuid+"."+ suffix;
		String filePathLittle= path  +"/"+ fileNameLittle;
		File originalImage = new File(filePath);  
		BufferedImage image1 = ImageIO.read(originalImage);
		BufferedImage image2 = new ImageScale().imageZoomOut(image1, 80, 80, true);
		FileOutputStream out = new FileOutputStream(filePathLittle);
		ImageIO.write(image2, suffix, out);
        out.close();*/
		String fileNameLittle =uuid+"."+suffix;
		FileUtil.byteArrayToFile(icon, path+"/"+fileNameLittle);
        return fileNameLittle;
	}
	
	
    public static void main(String[] args) throws Exception {  
         //File originalImage = new File("F:\\124.jpg");  
        // resize(originalImage, new File("F:\\1207-0.jpg"),80, 0.8f);  
        // resize(originalImage, new File("F:\\1207-1.jpg"),80, 1f); 
    	saveImageBigAndlittle("F:\\photo",".png",getImageStr("F:\\photo\\10.png"));
    }  
}  

