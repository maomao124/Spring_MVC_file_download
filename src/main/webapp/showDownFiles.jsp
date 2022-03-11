<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  Project name(项目名称)：Spring_MVC_file_download
  File name(文件名): showDownFiles
  Author(作者）: mao
  Author QQ：1296193245
  GitHub：https://github.com/maomao124/
  Date(创建日期)： 2022/3/11
  Time(创建时间)： 18:26
  Description(描述)： 无
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <td>被下载的文件名</td>
    </tr>
    <!--遍历 model中的 files-->
    <c:forEach items="${files}" var="filename">
        <tr>
            <td>
                <a href="${pageContext.request.contextPath}/down?filename=${filename}">${filename}</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
