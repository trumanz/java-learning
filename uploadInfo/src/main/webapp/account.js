

function writeAllData(divId){
	var request = $.ajax({
		url : "/uploadInfo/api/allAccount",
		method : "GET",
		contentType : "application/json; charset=utf-8",	
	});
  
    request.success(function(data) {
		console.log("done" + JSON.stringify(data));
		$("#alldata").empty()
		
		$("#alldata").append('<table stype="width:100%" >')
		    $("#alldata").append("<tr>")
			$("#alldata").append("<th>Gerrit Account ID</th>");
			$("#alldata").append("<th>Gerrit Username</th>");
			$("#alldata").append("<th>Gerrit Email Address</th>");
			$("#alldata").append("<th>EMC User Name</th>");
			$("#alldata").append("<th>EMC Email Address</th>");
			$("#alldata").append("</tr>")
		for(var i = 0 ; i < data.length; i++){
			$("#alldata").append("<tr>")
			$("#alldata").append("<td>" + data[i].accountID + "</td>");
			$("#alldata").append("<td>" + data[i].currentUsername + "</td>");
			$("#alldata").append("<td>" + data[i].currentEmail + "</td>");
			$("#alldata").append("<td>" + data[i].emcID + "</td>");
			$("#alldata").append("<td>" + data[i].emcEmail + "</td>");
			$("#alldata").append("</tr>")
		}
		
		$("#alldata").append("</table>")
	});
    
    request.error(function(jqxHR) {
		$("#alldata").empty().append("<p>" + JSON.stringify(jqxHR) + "</p>");
	});
    
	
	
}