package org.zerock.b02.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zerock.b02.dto.upload.UploadFileDTO;
import org.zerock.b02.dto.upload.UploadResultDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@RestController
//RestContoller : 메소드의 모든 리턴값은 바로 JSON이나 XML등으로 처리 (JSP,Thymeleaf로 전송 X)
@Log4j2
public class UpDownController {
    //파일 업로드와 파일을 보여주는 기능을 메소드로 처리 함

    @Value("${org.zerock.upload.path}") //import 시에 springframework 으로 시작하는 value, @Value는 application.properties 파일의 설정 정보 읽어서 변수의 값으로 사용 할 수 있음.
    private String uploadPath;// 파일 업로드하는 경로(uploadPath:/Users/hee/upload)

    @ApiOperation(value = "Upload POST",notes = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload",consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> upload(UploadFileDTO uploadFileDTO){

        log.info(uploadFileDTO);

        if(uploadFileDTO.getFiles() != null){

            final List<UploadResultDTO> list = new ArrayList<>();

            uploadFileDTO.getFiles().forEach(multipartFile -> {

                String originalName = multipartFile.getOriginalFilename();
                log.info(originalName);

                String uuid = UUID.randomUUID().toString();

                Path savePath = Paths.get(uploadPath, uuid+"_"+ originalName);

                boolean image = false;

                try {
                    multipartFile.transferTo(savePath);

                    //이미지 파일의 종류라면
                    if(Files.probeContentType(savePath).startsWith("image")){

                        image = true;

                        File thumbFile = new File(uploadPath, "s_" + uuid+"_"+ originalName);

                        Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200,200);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                list.add(UploadResultDTO.builder()
                        .uuid(uuid)
                        .fileName(originalName)
                        .img(image).build()
                );

            });//end each
            return list;
        }//end if
        return null;
    }

    //첨부파일 조회('/view/파일이름')
    @ApiOperation(value = "view 파일", notes = "GET 방식으로 첨부파일 조회")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName){
        //PathVariable : 호출하는 경로의 값을 직접 파라미터의 변수로 처리할 수 있는 방법 제공

        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName); //File.separator : / , resource : /Users/hee/upload/{fileName}
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try{
            headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath() ));
        } catch(Exception e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }


    //첨부파일 삭제
    @ApiOperation(value = "remove 파일",notes = "DELETE 방식으로 파일 삭제")
    @DeleteMapping("/remove/{fileName}")
    public Map<String,Boolean> removeFile(@PathVariable String fileName){

        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName); //resource : /Users/hee/upload/{fileName}
        String resourceName = resource.getFilename();

        Map<String,Boolean> resultMap = new HashMap<>();
        boolean removed = false;

        try{
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();

            //섬네일이 존재한다면
            if(contentType.startsWith("image")){
                File thumbnailFile = new File(uploadPath+File.separator +"s_" + fileName);
                thumbnailFile.delete();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        resultMap.put("result", removed);
        return resultMap;
    }
}
