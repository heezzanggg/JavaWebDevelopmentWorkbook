//async : 함수선언 시 사용. 해당함수가 비동기 처리를 위한 함수라는 것을 명시
//await : async함수 내에서 비동기 호출하는 부분에 사용

async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`)

    //console.log(result)

    return result;
}

//댓글 목록
async function getList({bno,page,size,goLast}){

    const result = await axios.get(`/replies/list/${bno}`,{params:{page,size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({bno:bno, page:lastPage, size:size})
    }
    return result.data
}

//댓글 추가
async function addReply(replyObj){
    console.log(replyObj)
    const response = await axios.post(`/replies/`,replyObj)
    console.log(response)
    return response.data
}

//댓글 조회
async function getReply(rno){
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

//댓글 수정
async function modifyReply(replyObj){
    console.log(replyObj.rno)
    const response = await axios.put(`/replies/${replyObj.rno}`,replyObj)
    return response.data
}

//댓글 삭제
async function removeReply(rno){
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}
