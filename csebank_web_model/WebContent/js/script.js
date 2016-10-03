$(document).ready(function(){
	var check;
	$('#nav-menu-button').on('click',function(){
		if($('#myNavbar').hasClass('collapse')){
			$('#myNavbar').removeClass('collapse');
			check =true;
		}
		else if(check = true){
			$('#myNavbar').addClass('collapse in');
		}
		
	});
	
	
	
});



