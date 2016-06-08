package com.birdhill.common.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.util.*;
 
@Component("fileUtils")
public class FileUtils {
    private static final String filePath = "D:\\storage\\upload\\";
     
    public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map, HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
         
        String boardIdx = (String)map.get("idx");
         
        File file = new File(filePath);
        if(file.exists() == false){
            file.mkdirs();
        }
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                file = new File(filePath + storedFileName);
                multipartFile.transferTo(file);
                 
                listMap = new HashMap<String,Object>();
                listMap.put("board_idx", boardIdx);
                listMap.put("origin_file_name", originalFileName);
                listMap.put("stored_file_name", storedFileName);
                listMap.put("file_size", multipartFile.getSize());
                list.add(listMap);
            }
        }
        return list;
    }
    
    public List<Map<String, Object>> parseUpdateFileInfo(Map<String, Object> map, HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
         
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
         
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map<String, Object> listMap = null; 
         
        String boardIdx = (String)map.get("idx");
        String requestName = null;
        String idx = null;
         
        while(iterator.hasNext()){
            multipartFile = multipartHttpServletRequest.getFile(iterator.next());
            if(multipartFile.isEmpty() == false){
                originalFileName = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName = CommonUtils.getRandomString() + originalFileExtension;
                 
                multipartFile.transferTo(new File(filePath + storedFileName));
                 
                listMap = new HashMap<String,Object>();
                listMap.put("IS_NEW", "Y");
                listMap.put("board_idx", boardIdx);
                listMap.put("origin_file_name", originalFileName);
                listMap.put("stored_file_name", storedFileName);
                listMap.put("file_size", multipartFile.getSize());
                list.add(listMap);
            }
            else{
                requestName = multipartFile.getName();
                idx = "idx_"+requestName.substring(requestName.indexOf("_")+1);
                if(map.containsKey(idx) == true && map.get(idx) != null){
                    listMap = new HashMap<String,Object>();
                    listMap.put("IS_NEW", "N");
                    listMap.put("file_idx", map.get(idx));
                    list.add(listMap);
                }
            }
        }
        return list;
    }


    // image to byte[] array
    public static byte [] ImageToByte(String path) throws Exception {
        // FileInputStream fis = new FileInputStream(file);
        URL url = null;
        InputStream fis = null;
        ByteArrayOutputStream bos = null;
        byte[] buf = null;


        //url = new URL("http://cms.olleh.com/FIME_M/2015MAIN/H01/12_01.jpg");
        url = new URL("http://cms.olleh.com/FILE_M/2015MAIN/H01/411_01.jpg");

        fis = url.openStream();
        System.out.println(fis.toString());
        bos = new ByteArrayOutputStream();
        buf = new byte[1024];

        try{
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
                System.out.println(buf.toString());
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = bos.toByteArray();

        return bytes;

    }
}
