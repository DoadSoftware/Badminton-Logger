var Store=0,home_count=0,away_count=0;
function processWaitingButtonSpinner(whatToProcess) 
{
	switch (whatToProcess) {
	case 'START_WAIT_TIMER': 
		$('.spinner-border').show();
		$(':button').prop('disabled', true);
		break;
	case 'END_WAIT_TIMER': 
		$('.spinner-border').hide();
		$(':button').prop('disabled', false);
		break;
	}
}
function reloadPage(whichPage)
{
	switch(whichPage){
	case 'INITIALISE':
		processUserSelection(document.getElementById('select_broadcaster'));
		processBadmintonProcedures('LOAD_MATCHES');
		break;
	case 'LOGGER':
		processBadmintonProcedures('LOAD_MATCH');
		break;
	}
}
function initialiseForm(whatToProcess, dataToProcess)
{
	switch (whatToProcess){
	
	/*case 'Points':
		dataToProcess.match.forEach(function(match,index,array){
			home_count == match.numberOfPoints
		});
		break;*/
	
	case 'home_st':
		document.getElementById('select_onstrike_player').selectedIndex = 0 ;
		document.getElementById('match_summary').innerHTML = $('#select_onstrike_player option:first').text() + ' is on Serve';
		processBadmintonProcedures('ON-STRIKE_PLAYER');	
		break;
	case 'away_st':
		document.getElementById('select_onstrike_player').selectedIndex = 1 ;
		document.getElementById('match_summary').innerHTML = $('#select_onstrike_player option:last').text() + ' is on Serve';
		processBadmintonProcedures('ON-STRIKE_PLAYER');	
		break;	
	
	case 'on_Strike':
		document.getElementById('match_summary').innerHTML = $('#select_onstrike_player option:selected').text() + ' is on Serve';		
		break;
	
	case 'RESET_SET_STATS': case 'RESET_ALL_STATS':
	
		var input_id;

		switch (whatToProcess){
		case 'RESET_ALL_STATS':
			$('#home_sets_count').val('0');
			$('#away_sets_count').val('0');
			break;
		}
		
		$('#home_scores_count').val('0');
		$('#away_scores_count').val('0');
		
		$('#home_Points').val('0');
		$('#home_FW').val('0');
		$('#home_FE').val('0');
		$('#home_BW').val('0');
		$('#home_BE').val('0');
		
		$('#away_Points').val('0');
		$('#away_FW').val('0');
		$('#away_FE').val('0');
		$('#away_BW').val('0');
		$('#away_BE').val('0');
        
        for(var i=1; i<=2; i++) {
	        for(var j=0; j<=4; j++) {
				if(i==1) {
					input_id = 'home';
				} else {
					input_id = 'away';
				}
	    		switch (j) {
	    		case 0:
	        		input_id = input_id + '_' + 'Points';
	        		break;
	    		case 1:
			    	stats_type = 'FW';
			    	break;
			    case 2:
			    	stats_type = 'FE';
			    	break;
			    case 3:
			    	stats_type = 'BW';
			    	break;
			    case 4:
			    	stats_type = 'BE';
			    	break
				}
				$('#' + input_id).val('0');
				if(document.getElementById('select_' + input_id)) {
	    			document.getElementById('select_' + input_id).selectedIndex = 3;
				}
			}
		}	
		break;
	}
}
function processUserSelection(whichInput)
{	
	switch ($(whichInput).attr('id')) {
	
	case 'select_onstrike_player':
		processBadmintonProcedures('ON-STRIKE_PLAYER');
		initialiseForm('on_Strike',null);
		break;
	
	case 'select_golden_points_player':
		processBadmintonProcedures('GOLDEN-POINTS_PLAYER');
		break;
		
	case 'start_set': 
		
		if(confirm('Starting set with ' + $('#select_onstrike_player option:selected').text() + ' on Serve') == false) {
			return false;
		}
		
		uploadFormDataToSessionObjects('START_SET');
		$('#logging_stats_table_body').find("button, select, input").prop('disabled',false);
		$('#logging_stats_div').find("input").prop('disabled',false);
		initialiseForm('RESET_SET_STATS',null);
		break;
	
	case 'end_set':
		Store = Store + 1 ;
		
		if(parseInt($('#home_scores_count').val()) > parseInt($('#away_scores_count').val())) {
			if(confirm('End set with ' + $('#select_onstrike_player option:first').text() + ' winning the set') == false) {
				return false;
			}
			$('#home_sets_count').val(parseInt($('#home_sets_count').val()) + 1);
			
		}else if(parseInt($('#away_scores_count').val()) > parseInt($('#home_scores_count').val())) {
			if(confirm('End set with ' + $('#select_onstrike_player option:last').text() + ' winning the set') == false) {
				return false;
			}
			$('#away_sets_count').val(parseInt($('#away_sets_count').val()) + 1);
		}
		switch(Store){
			case 1:
				document.getElementById('match_set_summary').innerHTML = 'SET: ' + parseInt($('#home_scores_count').val()) + '-' 
					+ parseInt($('#away_scores_count').val());
				break;
			case 2:
				document.getElementById('match_set_summary').innerHTML = document.getElementById('match_set_summary').innerHTML + ' , ' +
				parseInt($('#home_scores_count').val()) + '-' + parseInt($('#away_scores_count').val());
				break;
			case 3:
				document.getElementById('match_set_summary').innerHTML = document.getElementById('match_set_summary').innerHTML + ' , ' +
				parseInt($('#home_scores_count').val()) + '-' + parseInt($('#away_scores_count').val());
				break;
		}
		uploadFormDataToSessionObjects('END_SET')
		initialiseForm('RESET_SET_STATS',null);
		$('#logging_stats_table_body').find("button, select, input").prop('disabled',true);
		$('#logging_stats_div').find("input").prop('disabled',true);
		break;
	
	case 'reset_set':

		if(confirm('Do you wish to RESET the frame') == false) {
			return false;
		}
		initialiseForm('RESET_SET_STATS',null);
		uploadFormDataToSessionObjects('RESET_SET');
		document.getElementById('match_summary').innerHTML = 'Before start set first select the player who serve';
		$('#logging_stats_table_body').find("button, select, input").prop('disabled',true);
		$('#logging_stats_div').find("input").prop('disabled',true);
		break;

	case 'reset_all':

		if(confirm('Do you wish to RESET the entire match') == false) {
			return false;
		}
		initialiseForm('RESET_ALL_STATS',null);
		uploadFormDataToSessionObjects('RESET_ALL');
		document.getElementById('match_summary').innerHTML = 'Before start set first select the player who serve';
		document.getElementById('match_set_summary').innerHTML = " ";
		Store = 0;
		$('#logging_stats_table_body').find("button, select, input").prop('disabled',true);
		$('#logging_stats_div').find("input").prop('disabled',true);
		
		break;
	
	
	case 'select_broadcaster':
		switch ($('#select_broadcaster :selected').val().toUpperCase()) {
		case 'DOAD_IN_HOUSE_EVEREST':
			$('#vizPortNumber').attr('value','1980');
			$('label[for=vizScene], input#vizScene').hide();
			break;
		}
		break;
		
	case 'load_match_btn':
		if(checkEmpty($('#vizIPAddress'),'IP Address Blank') == false) {
			return false;
		}
      	document.initialise_form.submit();
		break;

	default:
	
		if($(whichInput).attr('id').includes('_btn') && $(whichInput).attr('id').split('_').length >= 4) {
    		
    		switch ($(whichInput).attr('id').split('_')[1]) {
    		case 'increment':
				
				switch ($(whichInput).attr('id').split('_')[2]) {
				case 'FW': case 'FE': case 'BW': case 'BE':
	    			
	    			$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
					parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) + 1
					);
	    			
	    			break;
				
	    		case 'Points': 
	    			if($('#golder_points_check_box').is(":checked")){
						
						$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
							parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) + 2
						);

						$('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val(
							parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val()) + 2
						);
						
						$('#' + $(whichInput).attr('id').split('_')[0] + '_golden').val(
							parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_golden').val()) + 1);
						
						document.getElementById("golder_points_check_box").checked = false;
						
					}else{
						
						//initialiseForm('Points',null);
						//alert('Hi');
						if($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val() < 15){
							$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) + 1
							);
	
							$('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val()) + 1
							);
						}
						
						if($(whichInput).attr('id').split('_')[0] == 'home'){
							initialiseForm('home_st',null);
						}else{
							initialiseForm('away_st',null);
						}
					}
	        		break;	
				}
				break;

    		case 'decrement':
    			
    			switch ($(whichInput).attr('id').split('_')[2]) {
					
				case 'FW': case 'FE': case 'BW': case 'BE':
					if($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()>0){
						$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
						parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) - 1
						);
					}
	    			break;
				
	    		case 'Points': 
	    			if($('#golder_points_check_box').is(":checked")){
						if($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()>0){
							$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) - 2
							);
								
							$('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val()) - 2
							);
							
							$('#' + $(whichInput).attr('id').split('_')[0] + '_golden').val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_golden').val()) - 1);
							
							document.getElementById("golder_points_check_box").checked = false;
						}
						
						
						
					}else{
						
						if($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()>0){
							$('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_' + $(whichInput).attr('id').split('_')[2]).val()) - 1
							);
								
							$('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val(
								parseInt($('#' + $(whichInput).attr('id').split('_')[0] + '_scores_count').val()) - 1
							);
						}
						
					}
	        		
	        		break;	
				}
				break;

			}
			uploadFormDataToSessionObjects('SAVE_STATS');
			break;
		}	
	}
}
function uploadFormDataToSessionObjects(whatToProcess)
{
	var formData = new FormData();
	var url_path;

	switch(whatToProcess.toUpperCase()) {
	case 'SAVE_STATS':
		url_path = 'save_stats_data';
		$('input, select, textarea').each(
			function(index){ 
				formData.append($(this).attr('id'),document.getElementById($(this).attr('id')).value);  
			}
		);
		break;
	case 'START_SET': case 'END_SET': case 'RESET_SET': case 'RESET_ALL':
		url_path = whatToProcess.toLowerCase();
		switch(whatToProcess.toUpperCase()) {
		case 'END_SET':
			formData.append('home_sets_count',document.getElementById('home_sets_count').value);  
			formData.append('away_sets_count',document.getElementById('away_sets_count').value);  
			break;
		}
		break;
	}
	
	$.ajax({    
		headers: {'X-CSRF-TOKEN': $('meta[name="_csrf"]').attr('content')},
        url : url_path,     
        data : formData,
        cache: false,
        contentType: false,
        processData: false,
        type: 'POST',     
        success : function(response) {

        	switch(whatToProcess.toUpperCase()) {
        	case 'SAVE_STATS':
        		break;
        	}
        	
        },    
        error : function(e) {    
       	 	console.log('Error occured in uploadFormDataToSessionObjects with error description = ' + e);     
        }    
    });		
	
}
function processBadmintonProcedures(whatToProcess)
{
	var valueToProcess;
	
	switch(whatToProcess.toUpperCase()) {
	case 'READ-DATABASE-AND-POPULATE':
		valueToProcess = $('#database_file_timestamp').val();
		break;
		
	case 'ON-STRIKE_PLAYER':
		valueToProcess = $('#select_onstrike_player option:selected').val() ;
		break;
		
	case 'GOLDEN-POINTS_PLAYER':
		valueToProcess = $('#select_golden_points_player option:selected').val() ;
		break;
	}
	
	
	$.ajax({    
        type : 'Get',     
        url : 'processBadmintonProcedures.html',     
        data : 'whatToProcess=' + whatToProcess + '&valueToProcess=' + valueToProcess, 
        dataType : 'json',
        success : function(data) {
        	switch(whatToProcess) {
			case 'READ-DATABASE-AND-POPULATE':
				if(data){
					if($('#database_file_timestamp').val() != data.database_file_timestamp) {
						document.getElementById('database_file_timestamp').value = data.database_file_timestamp;
						addItemsToList('LOAD_MATCH',data);
					}
				}
				break;
			case 'LOAD_MATCHES': case 'LOAD_MATCH':
				addItemsToList(whatToProcess,data)
	        	switch(whatToProcess) {
				case 'LOAD_MATCH':
					$('#logging_stats_table_body').find("button, select, input").prop('disabled',true);
					$('#logging_stats_div').find("input").prop('disabled',true);
					break;
	        	}
				break;
        	}
			processWaitingButtonSpinner('END_WAIT_TIMER');
	    },    
	    error : function(e) {    
	  	 	console.log('Error occured in ' + whatToProcess + ' with error description = ' + e);     
	    }    
	});
}
function addItemsToList(whatToProcess, dataToProcess){

	var option,list_option,table,tr,th,thead,tbody,row,div,text;
	
	switch (whatToProcess) {
	
	case 'LOAD_MATCHES':

		$('#selectedMatch').empty();
		list_option = document.getElementById('selectedMatch');
		dataToProcess.forEach(function(match,index,array){
			option = document.createElement('option');
			option.value = match.matchId + '.xml';
				
			for(var i=1; i <= match.homePlayers.length,i <= match.awayPlayers.length;i++){
				if(i==1){
					option.innerHTML = match.homePlayers[match.homePlayers.length-i].full_name + ' v ' + 
					match.awayPlayers[match.awayPlayers.length-i].full_name + ' (' + match.numberOfSets + ')';
				}
				else if(i==2){
					j=i-1;
					option.innerHTML = match.homePlayers[match.homePlayers.length-j].full_name +'/'+match.homePlayers[match.homePlayers.length-i].full_name+ ' v ' + 
					match.awayPlayers[match.awayPlayers.length-j].full_name +'/'+ match.awayPlayers[match.awayPlayers.length-i].full_name + ' (' + match.numberOfSets + ')';
				}
			}
			list_option.append(option);
		});
		break;
	
	case 'LOAD_MATCH':
		
		$('#logging_stats_div').empty();

   		div = document.createElement('div');
		div.style = 'text-align:center;';
		
		option = document.createElement('button');
		option.innerHTML = 'Start Set';
		option.id = 'start_set';
		option.style = 'width:130px;';
		option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		option = document.createElement('button');
		option.innerHTML = 'End Set';
		option.id = 'end_set';
		option.style = 'width:130px;margin-left:5%;';
		option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		option = document.createElement('button');
		option.innerHTML = 'Reset Set';
		option.id = 'reset_set';
		option.style = 'width:130px;margin-left:5%;';
		option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		option = document.createElement('button');
		option.innerHTML = 'Reset All';
		option.id = 'reset_all';
		option.style = 'width:130px;margin-left:5%;';
		option.setAttribute('onclick','processUserSelection(this);');
		div.appendChild(option);

		document.getElementById('logging_stats_div').appendChild(div);
		
       
        for(var i=1; i<=2; i++) {
	   		div = document.createElement('div');
			div.style = 'text-align:center;';
	   		switch(i) {
			case 1:
				text = 'sets';
				break;
			case 2:
				text = 'scores';
				break;
			}
	        for(var j=1; j<=3; j++) {
				option=document.createElement('label');
				option.style = 'width:150px';
				switch(j) {
				case 1: case 3:
					select=document.createElement('input');
					select.type = "text";
					select.style = 'width:5%;text-align:center;';
					if(j == 1){
						select.id = 'home_' + text + '_count';
					}
					else{
						select.id = 'away_' + text + '_count';
					}
					if(!select.value) {
						select.value = '0';
					}	
					break;
				case 2:
					option.innerHTML = text.toUpperCase();
					break;
				}
				div.appendChild(select);
				div.appendChild(option);
			}
			document.getElementById('logging_stats_div').appendChild(div);
		}
		
		option = document.createElement('h6');
		option.innerHTML = 'Match No: ' + dataToProcess.match.matchId;
		option.style = 'text-align:center';
		document.getElementById('logging_stats_div').appendChild(option);
		
		option = document.createElement('h6');
		option.id = 'match_summary';
		option.innerHTML = 'Before start set first select the player who serve';
		option.style = 'text-align:center';
		document.getElementById('logging_stats_div').appendChild(option);
		
		option = document.createElement('h6');
		option.id = 'match_set_summary';
		option.style = 'text-align:center';
		document.getElementById('logging_stats_div').appendChild(option);
		
		option = document.createElement('select');
		option.id = 'select_onstrike_player';
		option.style = 'width:175px;';
		
		list_option = document.createElement('option');
		for(var i=1; i <= dataToProcess.match.homePlayers.length;i++){
			if(i==1){
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name;
			}
			else if(i==2){
				a=i-1;
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId + "/" + 
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].playerId;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name + "/" +
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name;
			}else if(i==3){
				a=i-1;
				b=i-2;
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].playerId +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-b].playerId ;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-b].full_name ;
			}
		}
		option.appendChild(list_option);
		
		list_option = document.createElement('option');
		for(var i=1; i <= dataToProcess.match.homePlayers.length;i++){
			if(i==1){
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name;
			}
			else if(i==2){
				a=i-1;
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId + "/" + 
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].playerId;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name + "/" +
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name;
			}else if(i==3){
				a=i-1;
				b=i-2;
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].playerId +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-b].playerId ;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-b].full_name ;
			}
		}
		option.setAttribute('onclick','processUserSelection(this);');
		option.appendChild(list_option);
		
		text = document.createElement('label');
		text.innerHTML = 'Select Serve: '
		text.htmlFor = option.id;
		document.getElementById('logging_stats_div').appendChild(text).appendChild(option);
		
		option = document.createElement('select');
		option.id = 'select_golden_points_player';
		option.style = 'width:175px;';
		
		list_option = document.createElement('option');
		for(var i=1; i <= dataToProcess.match.homePlayers.length;i++){
			if(i==1){
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name;
			}
			else if(i==2){
				a=i-1;
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId + "/" + 
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].playerId;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name + "/" +
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name;
			}else if(i==3){
				a=i-1;
				b=i-2;
				list_option.value = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].playerId +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].playerId +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-b].playerId ;
				list_option.text = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name +'/'+
					dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-b].full_name ;
			}
		}
		option.appendChild(list_option);
		
		list_option = document.createElement('option');
		for(var i=1; i <= dataToProcess.match.homePlayers.length;i++){
			if(i==1){
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name;
			}
			else if(i==2){
				a=i-1;
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId + "/" + 
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].playerId;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name + "/" +
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name;
			}else if(i==3){
				a=i-1;
				b=i-2;
				list_option.value = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].playerId +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].playerId +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-b].playerId ;
				list_option.text = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name +'/'+
					dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-b].full_name ;
			}
		}
		option.setAttribute('onclick','processUserSelection(this);');
		option.appendChild(list_option);
		
		text = document.createElement('label');
		text.innerHTML = 'Choose who select Golden Points: '
		text.htmlFor = option.id;
		text.style = 'width:250px;';
		document.getElementById('logging_stats_div').appendChild(text).appendChild(option);
		
		checkbox_option = document.createElement('input');
		checkbox_option.id = "golder_points_check_box";
		checkbox_option.type = "checkbox";
		
		label = document.createElement('label')
		label.htmlFor = checkbox_option.id;
		label.style = 'width:200px;margin-top:2%;';
		label.appendChild(document.createTextNode('Golden Points '));
		document.getElementById('logging_stats_div').appendChild(label).appendChild(checkbox_option);
		
		table = document.createElement('table');
		table.setAttribute('class', 'table table-bordered');
		tr = document.createElement('tr');
		for (var j = 1; j <= 3; j++) {
		    th = document.createElement('th'); //column
		    switch (j) {
			case 1:
			     for(var i=1; i <= dataToProcess.match.homePlayers.length;i++){
					if(i==1){
						th.innerHTML = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name + "<br>" +'(' +
							dataToProcess.match.homeTeam.fullname + ')';
					}
					else if(i==2){
						a=i-1;
						th.innerHTML = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name +'/'+
							dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name + "<br>" +'(' +
							dataToProcess.match.homeTeam.fullname + ')';
					}else if(i==3){
						a=i-1;
						b=i-2;
						th.innerHTML = dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-i].full_name +'/'+
							dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-a].full_name +'/'+
							dataToProcess.match.homePlayers[dataToProcess.match.homePlayers.length-b].full_name + "<br>" +'(' +
							dataToProcess.match.homeTeam.fullname + ')';
					}
				}
				break;
			case 2:
				if(dataToProcess.match.categoryId == 0){
					switch(dataToProcess.match.superMatch){
						case 1:
						th.innerHTML = th.innerHTML + "<br>" +'Super Match';
						if(dataToProcess.match.trumpMatch == 1){
							th.innerHTML = th.innerHTML + "<br>" +'Trump Match';
						}
						break;
					}
				}else{
					switch(dataToProcess.match.categoryId){
					case 1: case 2: case 3: case 4:
						if(dataToProcess.match.trumpHomeMatch == 1 || dataToProcess.match.trumpAwayMatch == 1){
							th.innerHTML = 'Trump Match';
							
							switch(dataToProcess.match.categoryId){
								case 1:
									th.innerHTML = 'Women Single' +  "<br>" + th.innerHTML;
									break;
								case 2:
									th.innerHTML = 'Men Doubles' +  "<br>" + th.innerHTML;
									break;
								case 3:
									th.innerHTML = 'Men Singles' +  "<br>" + th.innerHTML;
									break;
								case 4:
									th.innerHTML = 'Mixed Doubles' +  "<br>" + th.innerHTML;
									break;
							}
						}else{
							switch(dataToProcess.match.categoryId){
								case 1:
									th.innerHTML = 'Women Single';
									break;
								case 2:
									th.innerHTML = 'Men Doubles';
									break;
								case 3:
									th.innerHTML = 'Men Singles';
									break;
								case 4:
									th.innerHTML = 'Mixed Doubles';
									break;
							}
						}
						break;	
					}	
				}
			    th.innerHTML = th.innerHTML + "<br>" +'Detail'; 
				break;
			case 3:
			    for(var i=1;i <= dataToProcess.match.awayPlayers.length;i++){
					if(i==1){
						th.innerHTML = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name + "<br>" +'(' +
							dataToProcess.match.awayTeam.fullname + ')';
					}
					else if(i==2){
						a=i-1;
						th.innerHTML = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name +'/'+ 
							dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name + "<br>" +'(' +
							dataToProcess.match.awayTeam.fullname + ')';
					}else if(i==3){
						a=i-1;
						b=i-2;
						th.innerHTML = dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-i].full_name +'/'+
							dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-a].full_name +'/'+
							dataToProcess.match.awayPlayers[dataToProcess.match.awayPlayers.length-b].full_name + "<br>" +'(' +
							dataToProcess.match.awayTeam.fullname + ')';
					}
				} 
				break;
			}
			th.style='color:#008cff;text-align:center;';
		    tr.appendChild(th);
		}
		thead = document.createElement('thead');
		thead.appendChild(tr);
		table.appendChild(thead);

		tbody = document.createElement('tbody');
		tbody.id = 'logging_stats_table_body';

        var home_or_away = '', stats_type= '';
        for(var i=0; i<=4; i++) {
            row = tbody.insertRow(tbody.rows.length);
    		switch (i) {
    		case 0:
        		stats_type = 'Points';
        		break;
    		case 1:
		    	stats_type = 'FW';
		    	break;
		    case 2:
		    	stats_type = 'FE';
		    	break;
		    case 3:
		    	stats_type = 'BW';
		    	break;
		    case 4:
		    	stats_type = 'BE';
		    	break
			}
    		for(var j=0; j<=2; j++) {
        		div = document.createElement('div');
   				div.style = 'text-align:center;';
				switch (j) {
				case 0: case 2:
	    			switch (j) {
	    			case 0: //home
						home_or_away = 'home';
	    				break;
	    			case 2: //away
						home_or_away = 'away';
	    				break;
					}
					for(var k=0; k<=2; k++) {
						switch (k) {
						case 0: case 2:
		        			option = document.createElement('input');
		    				option.type = "button";
		    				if (k == 0) {
		        				option.id = home_or_away + "_" + 'increment' + "_" + stats_type + '_btn';
		        				option.value="+";
		        				option.setAttribute('onclick','processUserSelection(this);');
		        				}
		    				else {
								option.id = home_or_away + "_" + "decrement" + "_" + stats_type + '_btn';
								option.value="-";
								option.setAttribute('onclick','processUserSelection(this);');
								break;
							}
		    				option.style = 'text-align:center;';
							break;
						case 1: 
		        			option = document.createElement('input');
		    				option.type = "text";
		    				option.id = home_or_away + "_" + stats_type;
		    				option.style = 'width:25%;text-align:center;';
		    				break;
						}
						dataToProcess.stats.forEach(function(item) {
							if(option.name.toUpperCase().includes(item.statType)) {
								switch(j) {
								case 0:
									option.value = item.homeStatCount;
									break;
								case 2:
									option.value = item.awayStatCount;
									break;
								}
							}
						});			
							if(!option.value) {
								option.value = '0';
							}			
			div.appendChild(option);
				    }	
				    break;
				case 1:
					option=document.createElement('label');
    				option.style = 'width:150px;text-align:center;';
					switch(i){
					case 0:
				    	option.innerHTML = 'Points';
				    	break;
					case 1:
				    	option.innerHTML = 'Forehand Winner';
				    	break;
				   case 2:
				    	option.innerHTML = 'Forehand Errors';
				    	break;
				    case 3:
				    	option.innerHTML = 'Backhand Winner';
				    	break;
				    case 4:
				    	option.innerHTML = 'Backhand Errors';
				    	break;
					}
					div.appendChild(option);
					break;		
			    }
			    row.insertCell(j).appendChild(div);
	     	 }	 
	    }
		table.appendChild(tbody);
		
		document.getElementById('logging_stats_div').appendChild(table);
		document.getElementById('logging_stats_div').style.display = '';

		break;
		
	}
}
function checkEmpty(inputBox,textToShow) {

	var name = $(inputBox).attr('id');
	
	document.getElementById(name + '-validation').innerHTML = '';
	document.getElementById(name + '-validation').style.display = 'none';
	$(inputBox).css('border','');
	if(document.getElementById(name).value.trim() == '') {
		$(inputBox).css('border','#E11E26 2px solid');
		document.getElementById(name + '-validation').innerHTML = textToShow + ' required';
		document.getElementById(name + '-validation').style.display = '';
		document.getElementById(name).focus({preventScroll:false});
		return false;
	}
	return true;	
}