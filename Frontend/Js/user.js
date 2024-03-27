function saveUser(){
    let fname= $('#fname').val();
    let lname= $('#lname').val();
    let email= $('#email').val();
    let mobile= $('#mobile').val();
    let address= $('#address').val();
    let district= $('#district').val();
    let city= $('#city').val();
    let zip= $('#zip').val();
    let pass= $('#pass').val();

    $.ajax({
        method:"POST",
        contentType:"application/json",
        url:"http://localhost:8080/api/v1/user/saveUser",
        async:true,
        data:JSON.stringify({
            "userID":"",
            "userFname":fname,
            "userLname":lname,
            "userEmail":email,
            "userMobile":mobile,
            "userAddress":address,
            "city":city,
            "district":district,
            "zip":zip,
            "password":pass
        }),

        complete: function(xhr, status) {
            if (xhr.status === 202) {
                if (confirm("Data saved successfully. Do you want to redirect to login page?")) {
                    window.location.href = "login.html";
                }else{
                    window.location.href = "index.html";
                }
            }else{
                alert("Error!!!");
            }
        }
    })
}

function login() {
    let email = $('#email').val();
    let password = $('#password').val();

    $.ajax({
        method: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/v1/user/login",
        async: true,
        data: JSON.stringify({
            "userEmail": email,
            "password": password
        }),
        success: function(response) {
            if (response.code === "00") {
                console.log("Login successful:", response.message);
                let id = response.content.userID;
                let fname = response.content.userFname;
                let lname = response.content.userLname;
                let email = response.content.userEmail;
                let mobile = response.content.userMobile;
                let address = response.content.userAddress;
                let city = response.content.city;
                let district = response.content.district;
                let zip = response.content.zip;

                // Set the fname and lname as session storage variables
                sessionStorage.setItem("id",id);
                sessionStorage.setItem("fname", fname);
                sessionStorage.setItem("lname", lname);
                sessionStorage.setItem("email", email);
                sessionStorage.setItem("mobile", mobile);
                sessionStorage.setItem("address", address);
                sessionStorage.setItem("city", city);
                sessionStorage.setItem("district", district);
                sessionStorage.setItem("zip", zip);

                window.location.href = "userindex.html";
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

function logout() {
    fetch('http://localhost:8080/api/v1/user/logout', {
        method: 'POST',
        credentials: 'include',
    })
    .then(response => {
        if (response.ok) {
            // Clear session storage
            sessionStorage.clear();
            alert('Logout successful');
            window.location.href = 'index.html';
        } else {
            alert('Logout failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Logout failed');
    });
}
