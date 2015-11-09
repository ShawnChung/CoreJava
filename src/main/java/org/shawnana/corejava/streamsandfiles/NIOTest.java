package org.shawnana.corejava.streamsandfiles;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.zip.CRC32;

public class NIOTest {
	public static long checksumInputStream(String filename) throws IOException {
		InputStream in = new FileInputStream(filename);
		CRC32 crc = new CRC32();
		int c = in.read();
		while (c != -1) {
			crc.update(c);
			c = in.read();
		}
		return crc.getValue();
	}
	
	public static long checksumBufferedInputStream(String filename) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(filename));
		CRC32 crc = new CRC32();
		int c = in.read();
		while (c != -1) {
			crc.update(c);
			c = in.read();
		}
		return crc.getValue();
	}
	
	public static long checksumRandomAccessFile(String filename) throws IOException {
		RandomAccessFile in = new RandomAccessFile(new File(filename), "r");
		CRC32 crc = new CRC32();
		/*int c = in.read();
		while (c != -1) {
			crc.update(c);
			c = in.read();
		}*/
		long length = in.length();
		for (long l = 0; l < length; l++) {
			in.seek(l);
			crc.update(in.readByte());
		}
		return crc.getValue();
	}
	
	public static long checksumMappedFile(String filename) throws IOException {
		FileInputStream in = new FileInputStream(filename);
		FileChannel channel = in.getChannel();
		CRC32 crc = new CRC32();
		int length = (int) channel.size();
		MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);
		for (int i = 0; i < length; i++) {
			int c = buffer.get(i);
			crc.update(c);
		}
		return crc.getValue();
	}
	
	public static void main(String[] args) throws IOException {
		String filename = "C:\\Users\\Shawn\\Downloads\\apache-activemq-5.4.3-bin.zip";
		System.out.println("InputStream:");
		long start = System.currentTimeMillis();
		long crcValue = checksumInputStream(filename);
		long end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) / 1000.0 + "s");
		
		System.out.println("BufferedInputStream:");
		start = System.currentTimeMillis();
		crcValue = checksumBufferedInputStream(filename);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) / 1000.0 + "s");
		
		System.out.println("RandomAccessFile:");
		start = System.currentTimeMillis();
		crcValue = checksumRandomAccessFile(filename);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) / 1000.0 + "s");
		
		System.out.println("MappedFile:");
		start = System.currentTimeMillis();
		crcValue = checksumMappedFile(filename);
		end = System.currentTimeMillis();
		System.out.println(Long.toHexString(crcValue));
		System.out.println((end - start) / 1000.0 + "s");
	}
}
