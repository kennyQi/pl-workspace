/*
 * @(#)FolderUtil.java 2011-1-21下午04:55:42
 * Copyright 2010 Palm Control, Inc. All rights reserved.
 */
package hg.jxc.admin.common;

import java.io.File;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 文件夹工具类
 * @author caoy
 * @date 2014-12-01
 */
public class FileUploadUtils {
	
//	public static boolean uploadImageFile(File srcFile, String destFileDirectory, Object destFileId, String destFileName, String listConfKey){
//		try {			
//			StringBuffer destFileDirectoryBuffer  = new StringBuffer(FileUploadUtils.getRootFolderDiskPath());
//			destFileDirectoryBuffer.append(destFileDirectory);
//			List<ImageState> stateList = (ArrayList<ImageState>) SpringApplicationContextUtil.getBean(listConfKey);
//
//			if(destFileId != null){
//				String destFileIdStr = destFileId.toString();
//				if(StringUtils.isNotEmpty(destFileIdStr)){
//					int leng = destFileIdStr.length() ;
//					if(leng > 2){
//						for (int i = 0; i < leng; i++) {
//							destFileDirectoryBuffer.append(File.separator);
//							destFileDirectoryBuffer.append(destFileIdStr.charAt(i));
//							if(i+1 != leng){
//								destFileDirectoryBuffer.append(destFileIdStr.charAt(i+1));
//								i+=1;
//							}
//						}
//					}else{
//						destFileDirectoryBuffer.append(File.separator);
//						destFileDirectoryBuffer.append(destFileIdStr);
//					}
//				}
//			}
//			
//			destFileDirectoryBuffer.append(File.separator);
//			
//			
//			
//			ImageState imagestate  = null;
//			for (int i = 0;stateList !=null && i < stateList.size(); i++) {
//				imagestate = stateList.get(i);
//
//				FileUtil.buildDir(destFileDirectoryBuffer.toString());//文件夹不存在则创建文件夹
//				FileUtils.copyFile(srcFile, new File(destFileDirectoryBuffer.toString()+destFileName));
//
//				destFileDirectoryBuffer.append(imagestate.getPreName());
//				destFileDirectoryBuffer.append(destFileName);
//				
//				Thumbnails.of(srcFile).size(imagestate.getWidth(), imagestate.getHight()).toFile(
//						destFileDirectoryBuffer.toString());
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return true;
//	}
	
	public static boolean uploadFile(File srcFile, String destFileDirectory, Object destFileId, String destFileName){
		try {			
			StringBuffer destFileDirectoryBuffer  = new StringBuffer(FileUploadUtils.getRootFolderDiskPath());
			destFileDirectoryBuffer.append(destFileDirectory);
			
			if(destFileId != null){
				String destFileIdStr = destFileId.toString();
				if(StringUtils.isNotEmpty(destFileIdStr)){
					int leng = destFileIdStr.length() ;
					if(leng > 2){
						for (int i = 0; i < leng; i++) {
							if(i+1==leng){
								destFileDirectoryBuffer.append(File.separator);
								destFileDirectoryBuffer.append(destFileIdStr.charAt(i));
							}else{
								destFileDirectoryBuffer.append(File.separator);
								destFileDirectoryBuffer.append(destFileIdStr.charAt(i));
								destFileDirectoryBuffer.append(destFileIdStr.charAt(i+1));
								i+=1;
							}
						}
					}else{
						destFileDirectoryBuffer.append(File.separator);
						destFileDirectoryBuffer.append(destFileIdStr);
					}
				}
			}
			
			destFileDirectoryBuffer.append(File.separator);
			destFileDirectoryBuffer.append(destFileName);
			
			FileUtils.copyFile(srcFile, new File(destFileDirectoryBuffer.toString()));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/**
	 * 取文件的硬盘目录
	 * @author sunju
	 * @creationDate. 2010-11-26 上午11:32:36 
	 * @param uploadType 上传类型
	 * @param srcFileId 源文件信息数据的id
	 * @return 工作目录
	 * @throws java.io.IOException
	 */
	public static String getFolderDiskPath(String uploadType, Object srcFileId) throws IOException {
		String uploadDiskPath = getRootFolderDiskPath();
		return new StringBuffer(uploadDiskPath).append(getFolderURL(uploadType, srcFileId)).toString();
	}
	/**
	 * 获得文件夹下的文件路径
	 * @author sunju
	 * @creationDate. 2011-1-21 下午05:03:09 
	 * @param uploadType 文件夹类型
	 * @param srcFileId 源文件Id
	 * @param preName 前置文件名
	 * @param fileName 文件名
	 * @return 文件夹下的文件路径
	 */
	public static String getFileURL(String uploadType, Object srcFileId, String preName, String fileName){
		/*
		 * 目前暂时不做文件是否存在判断
		 */
		String url = new StringBuffer().append(getFolderURL(uploadType, srcFileId)).append(preName).append(fileName).toString();
		return url;
		/*String uploadDiskPath = getRootFolderDiskPath();
		String diskPath = new StringBuffer(uploadDiskPath).append(url).toString();
		if (new File(diskPath).exists()) {
			return url;
		} else {
			return "";
		}*/
	}
	/**
	 * 获得文件夹下的文件数据
	 * @author sunju
	 * @creationDate. 2011-9-14 上午09:16:54 
	 * @param uploadType 文件夹类型
	 * @param srcFileId 源文件Id
	 * @param preName 前置文件名
	 * @param fileName 文件名
	 * @return 文件夹下的文件数据
	 */
	public static DataHandler getFileData(String uploadType, Object srcFileId, String preName, String fileName) {
		String url = new StringBuffer().append(getFolderURL(uploadType, srcFileId)).append(preName).append(fileName).toString();
		String uploadDiskPath = getRootFolderDiskPath();
		String diskPath = new StringBuffer(uploadDiskPath).append(url).toString();
		File file = new File(diskPath);
		if (file.exists()) {
			return new DataHandler(new FileDataSource(file));
		} else {
			return null;
		}
	}
	/**
	 * 获得文件夹路径
	 * @author sunju
	 * @creationDate. 2010-11-26 上午10:31:56 
	 * @param infoId 图片信息id
	 * @return 图片路径
	 */
	public static String getFolderPathById(Object infoId) {
		if (infoId == null) return "";
		String id = infoId.toString();
		StringBuffer path = new StringBuffer();
		while(id.length()>1) {
			path.append(id.substring(0, 2)).append("/");
			id = id.substring(2);
		}
		if (id.length() > 0)path.append(id).append("/");
		return path.toString();
	}
	// 获得根文件夹的硬盘目录
		private static String getRootFolderDiskPath() {
			String path = FileUploadUtils.class.getResource("/").getPath();
			int index = path.indexOf("/WEB-INF/classes/");
			if (index>-1) {		
				if("\\".equals(File.separator)){//windows环境
					if (path.startsWith("/")) {
						path = path.substring(1, index);
					} else {
						path = path.substring(0, index);
					}
				}else if("/".equals(File.separator)){//其他环境
					if (path.startsWith("/")) {
						path = path.substring(0, index);
					} else {
						path = "/" + path;
					}
				}
			}
			 
			return path;
		}
	// 获得文件夹路径
		public static String getFolderURL(String uploadType, Object destFileId) {
			if (destFileId != null) {
				StringBuffer path = new StringBuffer("");
				path.append(uploadType);
				String infoPath = getFolderPathById(destFileId);
				path.append("/").append(infoPath);
				return path.toString();
			}
			return "";
		}


}
