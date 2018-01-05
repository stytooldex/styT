package nico.styTool;


public class DownloadInfo{
	
	//下载量
	public int amount;
	//下载项的影片图片
	public String picture;
	//文件总大小
	public int fileLength;
	//媒体名称
	public String mediaName;
	//任务名称
	public String taskName;
	//暂停、开始、等待下载标记，取值有downloading,waiting,pause,complete
	public int state;
	//视频下载地址
	public String url;
	//视频名称，用来定义存储地址
	public String fileName;
	//上一秒的下载量
	
	public int lastSize;
	//下载速度
	public int downSpeed;
	//上一秒下载的具体时间
	public Long lastTime = 0L;
	
	//用于上报的字段mid
	public String mid = null;
	//用于上报的字段hashId
	public String hashId = null;
	
	public String mType = null;
	
	public String durl = null;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public int getFileLength() {
		return fileLength;
	}
	public void setFileLength(int fileLength) {
		this.fileLength = fileLength;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public int getLastSize() {
		return lastSize;
	}
	public void setLastSize(int lastSize) {
		this.lastSize = lastSize;
	}
	public int getDownSpeed() {
		return downSpeed;
	}
	public void setDownSpeed(int downSpeed) {
		this.downSpeed = downSpeed;
	}
	public Long getLastTime() {
		return lastTime;
	}
	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getHashId() {
		return hashId;
	}
	public void setHashId(String hashId) {
		this.hashId = hashId;
	}
	public String getmType() {
		return mType;
	}
	public void setmType(String mType) {
		this.mType = mType;
	}
	public String getDurl() {
		return durl;
	}
	public void setDurl(String durl) {
		this.durl = durl;
	}

}
