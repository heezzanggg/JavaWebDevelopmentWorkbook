package org.zerock.b02.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDTO {
    //업로드 결과의 반환처리

    private String uuid;

    private String fileName;

    private boolean img;

    public String getLink(){ //JSON으로 처리될 때는 link라는 속성으로 자동 처리됨

        if(img){
            return "s_" + uuid + "_" + fileName; //이미지인 경우 섬네일
        }else{
            return uuid + "_" + fileName;
        }
    }
}
