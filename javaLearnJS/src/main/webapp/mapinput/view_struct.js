function get_view_structure(){
	
	
	var name_input = {
			type : "input",
			name : "name",
			pattern : "\\d"
	};
	
	var age_input = {
			type : "input",
			name : "age",
			pattern : "\\d"
	};
	var phone_input = {
			type : "input",
			name : "phone",
			pattern : "\\d"
	};
	
	var address_input = {
			type : "input",
			name : "address",
			pattern : "\\d"
	};
	
	var contact = {
			type : "panel",
			name : "Contact",
			children : [phone_input, address_input]
	};
	
	var panel = {
		  type : "panel",
		  name : "Employee",
	      children : [ name_input, age_input , contact]
	}
	  
	return panel;
	
}