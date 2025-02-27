<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Employee List</title>
</head>
<body>
	<script>
				window.onload = function () {
				        const urlParams = new URLSearchParams(window.location.search);
				        const message = urlParams.get("message");
				        if (message === "deleted") {
				            alert("Record deleted successfully!");
				        } else if (message === "failed") {
				            alert("Failed to delete record!");
				        }else if (message === "updated") {
								 alert("Record updated successfully!");
						}else if (message === "updatefailed") {
								 alert("Updation failed please try again");
						}
				    };
		    function confirmDelete(id) {
		        if (confirm("Are you sure you want to delete this employee?")) {
		            window.location.href = "EmployeeServlet?action=delete&id=" + id;
		        }
		    }
			function confirmEdit(id) {
				if (confirm("Are you sure you want to edit this employee?")) {
					 window.location.href = "EmployeeServlet?action=edit&id=" + id;
				}
			}
	</script>

    <h2>Employee List</h2>
    <table border="1">
        <tr>
            <th>ID</th><th>Name</th><th>Skills</th><th>Age</th><th>Salary</th><th>Birth Date</th><th>Edit</th><th>Delete</th>
        </tr>
        <c:forEach var="employee" items="${employees}">
			<tr>
			    <td>${employee.id}</td>
			    <td>${employee.name}</td>
				<td>${employee.skills}</td>
			    <td>${employee.age}</td>
			    <td>${employee.salary}</td>
			    <td>${employee.birthDate}</td>
			    <td>
			        <button onclick="confirmEdit(${employee.id})">
			            Edit
			        </button>
			    </td>
			    <td>
			        <button onclick="confirmDelete(${employee.id})">
			            Delete
			        </button>
			    </td>
			</tr>
        </c:forEach>
    </table>
    <a href="form.jsp">Add Employee Data</a>
</body>
</html>
