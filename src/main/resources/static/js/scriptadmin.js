const toggleSidebar=()=>
{
	if($('.sidebar').is(":visible"))
	{
		$('.sidebar').css("display","none");
		$('.fas').css("margin-left","0%");
	}
	else
	{
		$('.sidebar').css("display","block");
		
	}
};
