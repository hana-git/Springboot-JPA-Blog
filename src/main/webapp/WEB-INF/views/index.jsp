<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ include file="layout/header.jsp" %>

<div class="container">

    <c:forEach var="board" items="${boards.content}">
        <div class="card m-2">
            <div class="card-body">
                <h4 class="card-title">${board.title}</h4>
                <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
            </div>
        </div>
    </c:forEach>

    <ul class="pagination justify-content-center">
        <c:choose>
            <c:when test="${boards.first}">
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number-1}">Previous</a></li>
            </c:otherwise>
        </c:choose>
        <c:choose>
            <c:when test="${boards.last}">
                <li class="page-item disabled"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
            </c:when>
            <c:otherwise>
                <li class="page-item"><a class="page-link" href="?page=${boards.number+1}">Next</a></li>
            </c:otherwise>
        </c:choose>
          </ul>

</div>

<%@ include file="layout/footer.jsp" %>

<%-- dummy data paging 예시
http://localhost:8000/dummy/user?page=0

"pageable": {
"sort": {
"sorted": true,
"unsorted": false,
"empty": false
},
"pageNumber": 0,
"pageSize": 2,
"offset": 0,
"paged": true,
"unpaged": false
},
"totalPages": 1,
"totalElements": 2,
"last": true,  --마지막 페이지 여부
"numberOfElements": 2,
"size": 2,
"number": 0,  --현재 페이지
"first": true,  --첫번째 페이지 여부

--%>
