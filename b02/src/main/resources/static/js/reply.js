//async : 함수선언 시 사용. 해당함수가 비동기 처리를 위한 함수라는 것을 명시
//await : async함수 내에서 비동기 호출하는 부분에 사용


//reply.js -> 비동기 통신 담당 , Promise 반환하고 read.html에서 then() 처리
//read.html -> 화면 처리

async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`)

    console.log(result)
    console.log(result.data)

    return result.data;
}

//댓글 목록
async function getList({bno,page,size,goLast}){
    //bno : 현재 게시물 번호 , page : 페이지번호 , size : 페이지당 사이즈 ,
    // goLast : 마지막페이지 호출여부, 강제적으로 마지막 댓글 페이지 호출하기 위한 용도
    const result = await axios.get(`/replies/list/${bno}`,{params:{page,size}})

    console.log(result)

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})
    }
    return result.data //result의 data에 page, size, total, start, end, prev, next, dtoList의 정보 있음
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
