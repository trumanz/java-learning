<html>


<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
<script>
	function myFunction() {
		document.getElementById("demo").innerHTML = "Paragraph changed.";
	}

	$(document).ready(function() {
		$("#btn1").click(function() {
			
		    $.get("api/", function(data, status){
		    	$("#p1").text(data)
		    })

		})
		
		$("#btn2").click(function() {
			
		    var jqxhr =  $.get("api/not_exists_uril_test", function(responseTxt, statusTxt, xhr){
		    	$("#p2").text("success")
		    }).fail(function(){
		    	$("#p2").text("GET failed")
		    })

		})
		
	  
		  
	});
</script>
</head>


<body>


<a href="D3.1.html">D3.1</a>
<a href="perf.html">perf</a>

	<h2>HeaderForTest-57216571327567421</h2>
	
	<p id="demo">A Paragraph</p>
	<button type="button" onclick="myFunction()">Change Paragraph</button>


    <p id="p1">Clink Restful Get to change this</p>
    <button id="btn1">Restful Get</button>
	
	 <p id="p2">Invalid Test</p>
    <button id="btn2">Invalid Restful Get</button>
	

</body>

</html>
