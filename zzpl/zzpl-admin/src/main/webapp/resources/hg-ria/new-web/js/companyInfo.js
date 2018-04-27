$(function(){
        var $companyInfo = $('#companyInfo');
        var $companyInfo_edit = $('#companyInfo_edit');
        var $edit_companyInfo = $('#edit_companyInfo');
        $edit_companyInfo.on('click',function(){
            $companyInfo.hide();
            var companyInfo_data = [];
            $('#companyInfo .td_r').each(function(i){
               companyInfo_data.push($('#companyInfo .td_r').eq(i).html());
            })
            $('#companyInfo_edit .td_r input').each(function(i){
               $('#companyInfo_edit .td_r input').eq(i).val(companyInfo_data[i]); 
            })
            var $companyDiscription = $('#companyDiscription');
            var $companyDiscription_edit = $('#companyDiscription_edit');
            $companyDiscription_edit.html($companyDiscription.html());
            $companyInfo_edit.show();
        })
    })