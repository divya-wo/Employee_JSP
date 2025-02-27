<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Employee Form</title></head>
<body>
	<c:if test="${not empty errorMessage}">
	        <p style="color: red;"><c:out value="${errorMessage}"/></p>
	</c:if>
    <c:if test="${emp != null}">
        <h2>Edit Employee</h2>
    </c:if>
    <c:if test="${emp == null}">
        <h2>Add New Employee</h2>
    </c:if>

    <!-- Edit Employee Form -->
    <c:if test="${emp != null}">
        <form action="EmployeeServlet" method="post">
            <!-- Hidden field for employee ID -->
            <input type="hidden" name="id" value="${emp.id}" />
            Name: <input type="text" name="name" value="${emp.name}" required="required"><br>
			Skills: 
			            <input type="checkbox" name="skills" value="Java" ${emp.skills.contains('Java') ? 'checked' : ''}> Java
			            <input type="checkbox" name="skills" value="Python" ${emp.skills.contains('Python') ? 'checked' : ''}> Python
			            <input type="checkbox" name="skills" value="C++" ${emp.skills.contains('C++') ? 'checked' : ''}> C++<br>
            Age: <input type="number" name="age" value="${emp.age}" required="required"><br>
            Salary: <input type="text" name="salary" value="${emp.salary}" required="required"><br>
            Birth Date: <input type="date" name="birthdate" value="${emp.birthDate}" required="required"><br>
            <input type="submit" value="Save Changes">
        </form>
    </c:if>

    <!-- Add New Employee Form -->
    <c:if test="${emp == null}">
        <form action="EmployeeServlet" method="post">
            <input type="hidden" name="id" value="0" /> <!-- New employee ID (0 for new) -->
            Name: <input type="text" name="name" required="required"><br>
			Skills: 
			<input type="checkbox" name="skills" value="Java"> Java
			<input type="checkbox" name="skills" value="Python"> Python
			<input type="checkbox" name="skills" value="C++"> C++ <br>
            Age: <input type="number" name="age" required="required"><br>
            Salary: <input type="number" name="salary" required="required"><br>
            Birth Date: <input type="date" name="birthdate" required="required"><br>
            <input type="submit" value="Add Employee">
        </form>
    </c:if>
</body>
</html>
