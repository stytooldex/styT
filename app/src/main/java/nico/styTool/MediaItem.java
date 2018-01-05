package nico.styTool;

import java.io.Serializable;

/**
 * 
 * @author yanggf
 */
public class MediaItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 
	private String id;
	
	private String contentDisposition;
	
	private String mimetype;
	
	private String userAgent;
	
	private String name;
	
	private String url;
	
	private long contentLength;
	
	private String srcPath;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}
	
	
   
	
	
	
}

