package com.celink.xieservice.utils.file;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ByteUtil {
	/**
	 * 系统提供的数组拷贝方法arraycopy
	 * */
	public static byte[] sysCopy(List<byte[]> srcArrays) {
		int len = 0;
		for (byte[] srcArray : srcArrays) {
			len += srcArray.length;
		}
		byte[] destArray = new byte[len];
		int destLen = 0;
		for (byte[] srcArray : srcArrays) {
			System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
			destLen += srcArray.length;
		}
		return destArray;
	}

	/**
	 * 借助字节输出流ByteArrayOutputStream来实现字节数组的合并
	 * */
	public static byte[] streamCopy(List<byte[]> srcArrays) {
		byte[] destArray = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			for (byte[] srcArray : srcArrays) {
				bos.write(srcArray);
			}
			bos.flush();
			destArray = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
			}
		}
		return destArray;
	}

	public static byte[] intToByteArray(int i) throws Exception {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(buf);
		out.writeInt(i);
		byte[] b = buf.toByteArray();
		out.close();
		buf.close();
		return b;
	}
	
	public static byte[] longToByte(long number) { 
        long temp = number; 
        byte[] b = new byte[8]; 
        for (int i = 0; i < b.length; i++) { 
            b[i] = new Long(temp & 0xff).byteValue();// 将最低位保存在最低位 
            temp = temp >> 8; // 向右移8位 
        } 
        return b; 
    } 

	public static void main(String[] args) {
		List<byte[]> bytes = new ArrayList<byte[]>();
		byte[] byte1 = new byte[3];
		byte1[0] = 1;
		byte1[1] = 2;
		byte1[2] = 3;
		bytes.add(byte1);
		byte[] byte2 = new byte[3];
		byte2[0] = 4;
		byte2[1] = 5;
		byte2[2] = 6;
		bytes.add(byte2);
		byte[] byte3 = new byte[3];
		byte3[0] = 7;
		byte3[1] = 8;
		byte3[2] = 9;
		bytes.add(byte3);
		byte[] newByte = sysCopy(bytes);
		System.out.println("方法一:");
		for (int i = 0; i < newByte.length; i++) {
			System.out.print(newByte[i] + " ");
		}
		System.out.println();
		System.out.println("方法二:");
		newByte = streamCopy(bytes);
		for (int i = 0; i < newByte.length; i++) {
			System.out.print(newByte[i] + " ");
		}
	}

}
