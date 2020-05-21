package org.java.upload.web;


import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.java.upload.pojo.FileVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class UploadController {

    //获得上传的文件地址
    @Value("${file.up.path}")
    private String path;


    /**
     * 如果上传，要把文件的存储位置返回
     * @param file
     * @return
     * 网关访问地址: http://api.his.com/api/upload/img
     */
    @RequestMapping("/img")
    public ResponseEntity<FileVo> up(MultipartFile file){

        //获得上传的文件名
        String fname = file.getOriginalFilename();

        System.out.println("上传的文件名是:"+fname);

        //获得文件的扩展名（类型名）
        String type = fname.substring(fname.lastIndexOf(".")+1);

        //在指定路径中，产生一个指定名称的新文件(空文件)
        File newFile = new File(path,fname);

        //判断目录是否存在，不存在，就创建目录
        if(!newFile.getParentFile().exists()){
            newFile.getParentFile().mkdirs();//创建目录
        }

        //将上传的文件，写入到新文件中
        try {
            file.transferTo(newFile);

            ClientGlobal.initByProperties("fastdfs-client.properties");

            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);

            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", fname);//文件名
            String fileId = client.upload_file1(newFile.getAbsolutePath(), type, metaList);
            //返回的fileId即为文件存储在fastdfs服务器中的位置,要访问该资源：  http://fastdfs服务器的ip/fileId
            String address = "http://img.his.com/"+fileId;

            System.out.println("访问地址:"+address);

            FileVo fileVo = new FileVo();
            fileVo.setCode(0);
            fileVo.setMsg("上传成功");
            fileVo.setData(address);//设置文件的存储位置

            return ResponseEntity.status(HttpStatus.CREATED).body(fileVo);

        } catch (Exception ex) {
            ex.printStackTrace();
            FileVo fileVo = new FileVo();
            fileVo.setCode(1);
            fileVo.setMsg("上传失败");
            return ResponseEntity.badRequest().body(fileVo);
        }
    }
}
