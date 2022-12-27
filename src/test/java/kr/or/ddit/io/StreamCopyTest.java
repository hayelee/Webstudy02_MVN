package kr.or.ddit.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class StreamCopyTest {
	private File targetFile;
	private File destFile;
	
	@Before
	public void setUp() {
		targetFile = new File("D:/contents/movies/target.mp4");
		destFile = new File("d:/target.mp4");
	}
	
//	@Test //2~4s
	public void copyTest1() throws IOException {
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
		){
			int tmp = -1;
			while((tmp=fis.read())!=-1) {
				fos.write(tmp);
			}
		}
	}
	
//	@Test //0.003s
	public void copyTest2() throws IOException {
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
		){
			byte[] buffer = new byte[1024];
			int length = -1;
			int count = 1;
			while((length=fis.read(buffer))!=-1) {
				if(count++ == 1) {
					Arrays.fill(buffer, (byte)0); // 영상의 앞부분이 짤림, 버퍼를 내가 가지고 있는 동안에는 그 안에 있는 내용을 임의로 변경 가능
				}
				fos.write(buffer, 0, length);
			}
		}
	}
	
	@Test //0.029s
	public void copyTest3() throws IOException {
		try(
				FileInputStream fis = new FileInputStream(targetFile);
				FileOutputStream fos = new FileOutputStream(destFile);
				
				BufferedInputStream bis = new BufferedInputStream(fis);
				BufferedOutputStream bos = new BufferedOutputStream(fos);
		){
			int tmp = -1;
			while((tmp=bis.read())!=-1) {
				bos.write(tmp);
			}
		}
	}
}
