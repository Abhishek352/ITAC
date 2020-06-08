/*window.addEventListener('DOMContentLoaded', function() {
	
	var showBtnId  = getUrlParameter('shw');
	if(typeof(document.getElementById(showBtnId)) != 'undefined' && document.getElementById(showBtnId) != null ){  document.getElementById(showBtnId).click()}
	
	if(document.querySelector('.accordion li')){
		document.querySelector('.accordion li:first-child').classList.remove('close');document.querySelector('.accordion li:first-child').classList.add('open');
	}
	if(typeof(document.getElementById('excepC_id')) != 'undefined' && document.getElementById('excepC_id') != null){
		if (document.getElementById('excepC_id').value == 0) {
			document.getElementById('excetionSection').style.display = 'none';
		} else {
			document.getElementById('excetionSection').style.display = 'block';
		}	
	}
	
	
	var accHD = document.querySelectorAll('.accordion li h3'),
		failAcch4 = document.querySelectorAll('div.failAccordian h4');
	for (i = 0; i < accHD.length; i++) {
		accHD[i].addEventListener('click', toggleItem, false);
	}
	for (i = 0; i < failAcch4.length; i++) {
		failAcch4[i].addEventListener('click', toggleItem, false);
	}
	function toggleItem() {var itemClass = this.parentNode.className, accItem = this.parentNode.parentNode.children;for (i = 0; i < accItem.length; i++) {accItem[i].className = 'close';}if (itemClass == 'close') {this.parentNode.className = 'open';}}
	
	
	
}, true);
*/
$(document).ready(function() {
	/*if(){
		 $('.b,.c').wrapAll('<div class="wrap"></div>');
	}
	$('#RegressionPack div.failAccordian>div').each(function(){
		$getId = $(this).attr('id').split('_')
		
		$('div[id="'+$getId[1]+'"]').wrapAll('<div class="'+$getId[1]+'_wrap"></div>');
		
	})*/
	
	
    $('a.dropdown-toggle').click(function(event) {
        event.preventDefault();
        var hRef = $(this).attr('href');
		if( $(this).parent().hasClass('active')== false){
			$('li div.config_div').slideUp(300);  
			$(this).next('div.config_div').slideDown(300);		
			$('#show_content>div').hide();
			$(hRef).slideDown('fast');
			$(this).closest('ul').children('li').removeClass('active').addClass('normal');
			$(this).parent().addClass('active').removeClass('normal');
		}
			if(hRef == '#HealthChecks'){
				$('#horiz_buttons .btn-group').hide();
			}else{
				$('#horiz_buttons .btn-group').show();
			}
			if(hRef == '#RegressionPack'){
				//$('.RC_brands>div:first-child').click();
			}
			if($('ul.accordion li:first-child').hasClass('open')){
				$('ul.accordion li.open').find('div.asset_Innerwrapper').slideDown();
			}
			
		
		
    }); /*end of dropdown-toggle*/
    $('.links a').click(function(event) {
        event.preventDefault();
        var getHrefSplit = $(this).attr('href').split('-'),
            getDivId = getHrefSplit[0],
            getPassFall = getHrefSplit[1];
			console.log(getPassFall);
        $('#global_assets').slideUp(300, function() {
			$('#horiz_buttons .btn-group').show();
			$('#'+getPassFall+'_all').click();
            $('a[href="' + getDivId + '"]').click();
            $('#show_records').show();
            $('#show_content').slideDown(200);
        });
		//console.log(getHrefSplit[2])
		if(getHrefSplit[2]!== undefined){
			$brnd = getHrefSplit[2];
			//console.log($brnd);
			$('.RC_brands>div.'+$brnd).click();
		}
		
    }); /*end of links anchor*/
	$('.links button').click(function(event) {
        event.preventDefault();
        var getHrefSplit = $(this).attr('id').split('-'),
            getDivId = getHrefSplit[0],
            getTheJour = getHrefSplit[1];
			console.log(getTheJour);
        $('#global_assets').slideUp(300, function() {
			$('#horiz_buttons .btn-group').hide();
			$('#show_'+ getTheJour).click();
            $('a[href="' + getDivId + '"]').click();
            $('#show_records').show();
            $('#show_content').slideDown(200);
        });
    }); /*end of links anchor*/
    $('a#backToGlobal').click(function(event) {
        event.preventDefault();
        $('#show_content').slideUp(300, function() {
            $('#show_records').hide();
            $('#global_assets').slideDown(200);
        });
    }); /*end of backToGlobal*/
	
	$('li>h3, div>h4').click(function() {
      $this = $(this);
      $target =  $this.next();
      if(!$this.parent().hasClass('close')){
         $this.parent().removeClass('open').addClass('close');
		 $this.parent().siblings().find('div.asset_Innerwrapper').slideUp();
		 $this.parent().siblings().find('div.accordionItemPanel').slideUp();
		 $this.parent().siblings().addClass('close').removeClass('open');
         $target.slideUp();
      }else{
		$this.parent('.close').siblings().addClass('close').removeClass('open');
		$this.parent().removeClass('close').addClass('open');
		$this.parent().siblings().find('div.asset_Innerwrapper').slideUp();
		$this.parent().siblings().find('div.accordionItemPanel').slideUp();
		$target.slideDown();
      }
	});
	
	
	$('#FindingSpecialCharacters div.asset_Innerwrapper').each(function(){
		if($(this).find('tr.pass_tr').length > 0 && 	$(this).find('tr.fail_tr').length > 0){	
			$(this).addClass('both_all');
		}else{
			if($(this).find('tr.fail_tr').length == 0){

				$(this).addClass('pass_all');
			}else{
				$(this).addClass('fail_all');
			}

		}
	});

	$('#AssetSizes div.asset_Innerwrapper, #PageComparison div.accordionItemPanel div.accordionItemPanel, #RegressionPack div.accordionItemPanel div.accordionItemPanel').each(function(){
		if($(this).find('tr.pass_tr').length > 0 && 	$(this).find('tr.fail_tr').length > 0){	
			$(this).addClass('both_all');
		}else{
			if($(this).find('tr.fail_tr').length == 0){

				$(this).addClass('pass_all');
			}else{
				$(this).addClass('fail_all');
			}

		}
	});
	$('#horiz_buttons button').click(function(){
		$this = $(this);
		$showId = $this.attr('id');
		$this.siblings().removeClass('active');
		$this.addClass('active');
		if($showId == 'show_all'){
			$('.pass_all, .fail_all').parent().show();
			$('div.both_all .fail_tr, div.both_all .pass_tr').show();
			//$('#AssetSizes .totalRecords').show();
		}else{
			if($showId == 'fail_all'){
				$('.fail_all').parent().show();				
				$('.pass_all').parent().hide();
				
				$('div.both_all .fail_tr').show();
				$('div.both_all .pass_tr').hide();
			}else{
				$('.fail_all').parent().hide();				
				$('.pass_all').parent().show();
				
				$('div.both_all .fail_tr').hide();
				$('div.both_all .pass_tr').show();
				
			}
		}
	})
	
	$('.hc_buttons button').click(function(){
		$this = $(this);
		$showId = $this.attr('id');
		var getIdSplit = $showId.split('_'),
            showHCDiv = getIdSplit[1];
		$this.siblings().removeClass('active');
		$this.addClass('active');
		$this.closest('div.report_wrapper').find('.toggleDiv').hide();
		console.log(showHCDiv);
		$('#'+ showHCDiv).show();
		
	});
	$('.RC_brands>div').click(function(){
		$('#RegressionPack li:first-child').addClass('open').removeClass('close');
		$getClass = $(this).children('span').attr('data-brand-type');
		$('#RegressionPack li>div.accordionItemPanel>div').not('div[class="'+$getClass+'"]').hide();
		$('#RegressionPack li>div.accordionItemPanel div[class="'+$getClass+'"]').show();
		console.log($getClass);
		$(this).parent().children('div').removeClass('active');
		$(this).addClass('active');
	});
	
	if($('div').hasClass('RC_brands')){
		$('.RC_brands>div:first-child').click();
	}	
	if($('div#HealthChecks').hasClass('report_wrapper')){
		$('#show_MortgageTool').click();
	}
	if($('div#RegressionPack').hasClass('report_wrapper')){
		$('#show_RegresPc').click();
	}
	/*var submenu = $('.submenu').hide();
    
  $('.open').click(function() {
      $this = $(this);
      $target =  $this.parent().next();
      
      if(!$this.hasClass('close')){
         $('.open').removeClass('close');
		 submenu.slideUp();
		 $this.addClass('close');
         $target.slideDown();
      }else{
         $target.slideUp();
         $this.removeClass('close');
      }
  });*/
}); /*end of Document ready*/



/*
function showByArg(button, theClass){
	document.querySelectorAll('.btn.btn-secondary').forEach(function(element) {
		element.classList.remove('active');
		button.classList.add('active')
	});
	document.querySelectorAll('.totalRecords').forEach(function(element) {
		if (theClass == 'show_all') {
			element.style.display = 'table-row';
		} else {
			element.style.display = 'none';
			if (element.classList.contains(theClass)) {
				element.style.display = 'table-row'
			}
		}
	})
}
function missing_assets_showByArg(button, theClass){
	document.querySelectorAll('.btn.btn-secondary').forEach(function(element) {
		element.classList.remove('active');
		button.classList.add('active')
	});
	document.querySelectorAll('.totalRecords').forEach(function(element, index) {
		if (theClass == 'show_all') {			
			element.parentNode.className = 'open';
			element.parentNode.removeAttribute('style');
		}else if(theClass == 'pass_all'){
			element.parentNode.className = 'close';
			element.parentNode.setAttribute('style', 'display:none !important');
			if (element.classList.contains(theClass)) {
				element.parentNode.className = 'open';
				element.parentNode.removeAttribute('style');
			}
		} else {
			element.parentNode.className = 'close';
			element.parentNode.setAttribute('style', 'display:none !important');
			if (element.classList.contains(theClass) || element.classList.contains('fail_all')) {
				console.log( element.classList);
				element.parentNode.className = 'open';
				element.parentNode.removeAttribute('style');
			}
		}
	})
}*/

function getUrlParameter(name){
	name = name.replace(/[\\[]/, '\\\\[').replace(/[\\]]/, '\\\\]');
	var regex = new RegExp('[\\\\?&]' + name + '=([^&#]*)');
	var results = regex.exec(location.search);
	return results === null ? '' : decodeURIComponent(results[1].replace(/\\+/g, ' '))
}


/*var vTabs = document.querySelectorAll('a.dropdown-toggle');
	for (i = 0; i < vTabs.length; i++) {
		vTabs[i].addEventListener('click', verticalAccordian, false);
	}
	
	function verticalAccordian(event){
		 event.preventDefault();
		var hRef = this.getAttribute('href').split('#'),
		tabItem = this.parentNode.parentNode.children;
		//console.log(hRef[1]);
		for (i = 0; i < tabItem.length; i++) {tabItem[i].className = 'normal';}
		if (this.parentNode.className == 'normal') {this.parentNode.className = 'active';}
		var configDiv = document.querySelectorAll('.components li div.config_div');
		for (i = 0; i < configDiv.length; i++) {configDiv[i].style.display = 'none';}
		this.parentNode.children[1].style.display='block';
		var contentDiv = document.querySelectorAll('#show_content>div');
		for (i = 0; i < contentDiv.length; i++) {contentDiv[i].style.display = 'none';}		
		document.getElementById(hRef[1]).style.display='block';
	}
	var goToTabs = document.querySelectorAll('.links a');
	for (i = 0; i < goToTabs.length; i++) {
		goToTabs[i].addEventListener('click', passFail, false);
	}
	function passFail(event){
		 event.preventDefault();
		var getHrefSplit = this.getAttribute('href').split('-'),
		 getDivId = getHrefSplit[0],
            getPassFall = getHrefSplit[1];
		//console.log(hRef[1]);
		document.getElementById('global_assets').style.display='none';
		document.querySelector('a[href="'+getDivId+'"]').click();
		document.getElementById('show_records').style.display='block';
		document.getElementById('show_content').style.display='block';
	}
	document.getElementById('backToGlobal').addEventListener('click', function(event){
		 event.preventDefault();
		document.getElementById('show_content').style.display='none';
		document.getElementById('show_records').style.display='none';
		document.getElementById('global_assets').style.display='block';
	}, false);*/