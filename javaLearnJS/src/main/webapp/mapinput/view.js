
console.log("This file running");


function createInputDiv(input){
	
	var input_div = document.createElement('div');
	input_div.id = input.name;

	//var parap = document.createElement("p"); 
	//parap.appendChild(document.createTextNode(input.name));
	
	var input = document.createElement("input");
	input.type = "text";
	input.id = input.name;
	
	//input_div.appendChild(parap);
	input_div.appendChild(input);
	
	return input_div
}

var createPanelDiv = function(panel){
	var panel_div = document.createElement('div');
	console.log("createPanelDiv")
	console.log(panel)
 	//$("<h2>" + panel.name + "</h2>").appendTo(panel_div);
	
	var tbl = document.createElement('table');
    tbl.style.width = '120%';
    tbl.setAttribute('border', '1');
    tbl.style.border="1px solid red";
    //tbl.css("border","1px solid #000");
    var tbdy = document.createElement('tbody');
	
	if(panel.hasOwnProperty('children')) {
		for(var i = 0; i < panel.children.length; i++) {
			var tr = document.createElement('tr');
 			var child = panel.children[i]
  			var new_div = null
 			switch(child.type){
 			  case 'input':
 				new_div = createInputDiv(child);
 				break;
 			  case 'panel':
 				new_div = createPanelDiv(child);
 				break;
 			  default:
 				console.log("Wrong"); 
 			}
 			var td1 = tr.insertCell();
 			td1.appendChild(document.createTextNode(child.name));
 			var td2 = tr.insertCell();
 			td2.appendChild(new_div);
			panel_div.appendChild(tr);
			 	
		}
	}
 	return panel_div;
}

function appendView(div, view){
	
	if(view.type == "panel"){
		panel_div = createPanelDiv(view)
		div.appendChild(panel_div);
		return
	}
 
}
 
$(document).ready(function(){
	var view_struct = get_view_structure();
	//console.log(view_struct)
	
	
	
	 //input_div = $('#input_panel')
	 var input_div = document.getElementById('input_panel')
	 appendView(input_div, view_struct)
	 
	
})
 