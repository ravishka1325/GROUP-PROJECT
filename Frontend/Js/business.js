function saveBusiness(){
    let businessName= $('#businessName').val();
    let ownerName= $('#ownerName').val();
    let businessEmail= $('#businessEmail').val();
    let businessIndustryType= $('#businessIndustryType').val();
    let businessCity= $('#businessCity').val();
    let businessDistrict= $('#businessDistrict').val();
    let businessZip= $('#businessZip').val();
    let businessMobile= $('#businessMobile').val();
    let businessPassword= $('#businessPassword').val();

    $.ajax({
        method:"POST",
        contentType:"application/json",
        url:"http://localhost:8080/api/v1/business/saveBusiness",
        async:true,
        data:JSON.stringify({
            "businessId":"",
            "businessName":businessName,
            "ownerName":ownerName,
            "businessEmail":businessEmail,
            "businessIndustryType":businessIndustryType,
            "businessCity":businessCity,
            "businessDistrict":businessDistrict,
            "businessZip":businessZip,
            "businessMobile":businessMobile,
            "businessPassword":businessPassword
        }),

        complete: function(xhr, status) {
            if (xhr.status === 202) {
                if (confirm("Registration successful. Do you want to redirect to login page?")) {
                    window.location.href = "BusinessLogin.html";
                }else{
                    window.location.href = "index.html";
                }
            }else{
                alert("Error!!!");
            }
        }
    })
}

function businessLogin() {
    let email = $('#email').val();
    let password = $('#password').val();

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/v1/business/login",
        async: true,
        data: JSON.stringify({
            "businessEmail": email,
            "businessPassword": password
        }),
        success: function(response) {
            if (response.code === "00") {
                console.log("Login successful:", response.message);
                let businessId = response.content.businessId;
                let businessName = response.content.businessName;

                // Set the fname and lname as session storage variables
                sessionStorage.setItem("businessId", businessId);
                sessionStorage.setItem("businessName", businessName);

                window.location.href = "BusinessDashboard.html";
            } else {
                console.error("Login failed:", response.message);
                alert("Invalid email or password");
            }
        },
        error: function(xhr, status, error) {
            console.error("An error occurred:", error);
            alert("An error occurred while logging in. Please try again later.");
        }
    });
}

function Businesslogout() {
    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/v1/business/logout",
        async: true,
        success: function(response) {
            if (response.code === "00") {
                alert("Logout Successful");
                sessionStorage.clear();
                window.location.href = "index.html";
            } else {
                alert("Logout Successful");
                sessionStorage.clear();
                window.location.href = "index.html";
            }
        },
        error: function(xhr, status, error) {
            alert("Logout Successful");
                sessionStorage.clear();
                window.location.href = "index.html";
        }
    });
}

