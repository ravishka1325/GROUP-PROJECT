$(document).ready(function() {
    var businessId = sessionStorage.getItem("businessId");

    $.ajax({
        url: 'http://localhost:8080/api/v1/business/getBusinessImage/' + businessId,
        type: 'GET',
        success: function(response) {
            if (response.code === "00") {
                var imagePath = response.content.mainBannerImagePath;
                $('#image-label').css('background-image', 'url(' + imagePath + ')');
            } else {
                alert('Error: ' + response.message);
            }
        },
        error: function(xhr, status, error) {
            alert('Error:', error);
        }
    });
});


$(document).ready(function() {
    $('#image-file').change(function() {
        var file = this.files[0];
        if (file) {
            var formData = new FormData();
            formData.append('image', file);
            var businessId = sessionStorage.getItem("businessId");
            formData.append('businessId', businessId);

            $.ajax({
                url: 'http://localhost:8080/api/v1/business/uploadBusinessImage',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false, 
                success: function(response) {
                    alert('Image uploaded successfully');
                   
                },
                error: function(xhr, status, error) {
                    alert('Error uploading image:', error);
                    
                }
            });
        }
    });
});