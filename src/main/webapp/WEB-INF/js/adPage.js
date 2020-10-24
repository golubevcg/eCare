function submitAd(){

    let textVal = $('#adText').val();

    console.log( textVal );
    let adText = {"adText" : textVal};

    $.ajax({
        type: 'POST',
        contentType: "application/json",
        url: '/adPage/submit/',
        data: JSON.stringify(adText),
        success(result){
            // console.log(result);
            // if(result.toString()==="true"){
            //     location.href = '/workerOffice';
            // }else{
            //     alert("Error, ad was not updated");
            // }
        }
    });

}