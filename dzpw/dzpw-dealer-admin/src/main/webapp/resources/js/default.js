
$(document).ready(function() {

	init = function() {
		
		console.info("111");
		
		var $content = $('#content');
		$('#menu').children().each(function() {
			$(this).click(function(event) {
				
				var url = unescape($('>a', this).attr("href"));
				
				$.ajax({
					type: 'GET',
					url: url,
					cache: false,
					success: function(response){
						
						$content.html(response)
					}
				});
				
				event.preventDefault();
				return false;
			});
		});

	}
	
	init();
});