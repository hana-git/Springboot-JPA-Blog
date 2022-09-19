<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
    <form action="/auth/loginProc" method="post">
        <div class="form-group">
            <label for="keyword">검색어 :</label>
            <input type="text" name="keyword" class="form-control" placeholder="Enter keyword" id="keyword">
        </div>
        <button id="btn-search" class="btn btn-primary">검색</button>
    </form>

</div>

<%--<script src="/js/user.js"></script>--%>
<%@ include file="../layout/footer.jsp" %>


