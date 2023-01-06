package kr.or.ddit.mvc.multipart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Part -> MultipartFile
 */
public class MultipartHttpServletRequest extends HttpServletRequestWrapper{

	private Map<String, List<MultipartFile>> fileMap; // map의 key -> part의 name, value -> part (List로 바꾸면서 이름은 하나인데 여러개를 담을 수 있음)
	public MultipartHttpServletRequest(HttpServletRequest request) throws IOException, ServletException {
		super(request);
		parseRequest(request);
	}

	private void parseRequest(HttpServletRequest request) throws IOException, ServletException {
		fileMap = new LinkedHashMap<>();
		request.getParts().stream()
					.filter((p)->p.getContentType()!=null) // 이게 null이면 그냥 String
					.forEach((p)->{
						String partName = p.getName();
						MultipartFile file = new StandardServletMultipartFile(p);
						List<MultipartFile> fileList = Optional.ofNullable(fileMap.get(partName))
																.orElse(new ArrayList<>()); // 절대 null일리가 없어!
						fileList.add(file);
						fileMap.put(partName, fileList);
					});
	}

	public Map<String, List<MultipartFile>> getFileMap() {
		return fileMap;
	}
	
	public MultipartFile getFile(String name){
		List<MultipartFile> files = fileMap.get(name);
		if(files!=null && !files.isEmpty())
			return files.get(0);
		else return null;
	}
	public List<MultipartFile> getFiles(String name){
		return fileMap.get(name);
	}
	public Enumeration<String> getfilenames(){
		Iterator<String> names = fileMap.keySet().iterator(); //Iterator와 Enumeration는 같은 접근방식을 가지고 있다!
		return new Enumeration<String>() {

			@Override
			public boolean hasMoreElements() { // Iterator-has next
				return names.hasNext();
			}

			@Override
			public String nextElement() { // Iterator-next
				return names.next();
			}
			
		};
	}
}
