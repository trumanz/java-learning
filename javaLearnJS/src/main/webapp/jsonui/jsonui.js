
console.log("This file running");



$(document).ready(function(){
	
	
	$.ajax({ 
        type: "GET",
        dataType: "json",
        url: "http://localhost:8080/javaLearnJS/api/json_schema/me",
        success: function(data){        
            var editor = new JSONEditor(document.getElementById('input_panel'),{
   	        schema: data 
   	        });
        }
    });
	
	 
	 
	   document.getElementById('input_panel').addEventListener('click',function() {
	        // Get the value from the editor
	        console.log(editor.getValue());
	      });
	
})
 