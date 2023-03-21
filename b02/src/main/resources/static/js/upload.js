//첨부파일 서버에 업로드 하는 기능
async function uploadToServer (formObj) {
    console.log("upload to server......")
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });

    return response.data
}

//서어베 특정 파일을 삭제하는 기능
async function removeFileToServer(uuid, fileName){

    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response.data
}