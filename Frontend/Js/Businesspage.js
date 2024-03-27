// Utility function for making fetch requests
async function fetchData(url) {
    try {
      const response = await fetch(url);
      const data = await response.json();
      return data;
    } catch (error) {
      console.error('Error fetching data:', error);
      throw error;
    }
  }
  
  // Function to render business details
  function renderBusinessDetails(bpageDTO) {
    const businessNameElement = document.getElementById("businessName");
    if (businessNameElement) {
      businessNameElement.innerText = bpageDTO.businessName;
    }

    const ownerNameContainer = document.getElementById("ownerNameContainer");
    if (ownerNameContainer && bpageDTO.ownerName) {
        const ownerNameParagraph = document.createElement("p");
        ownerNameParagraph.textContent = bpageDTO.ownerName;
        ownerNameContainer.appendChild(ownerNameParagraph);
    }
  
    const businessAboutElement = document.getElementById("businessAbout");
    if (businessAboutElement) {
      businessAboutElement.innerText = bpageDTO.businessAbout;
    }
  
    const businessMobileElement = document.getElementById("businessMobile");
    if (businessMobileElement) {
      businessMobileElement.innerText = bpageDTO.businessMobile;
    }
  
    const addressContainer = document.getElementById("addressContainer");
    if (addressContainer && bpageDTO.businessAddress) {
        const addressParagraph = document.createElement("p");
        addressParagraph.textContent = bpageDTO.businessAddress;
        addressContainer.appendChild(addressParagraph);
    }
  }
  
  // Function to render business hours
  function renderBusinessHours(businessHours) {
    document.getElementById("monday-hours").innerText = businessHours["Monday"] || "";
    document.getElementById("tuesday-hours").innerText = businessHours["Tuesday"] || "";
    document.getElementById("wednesday-hours").innerText = businessHours["Wednesday"] || "";
    document.getElementById("thursday-hours").innerText = businessHours["Thursday"] || "";
    document.getElementById("friday-hours").innerText = businessHours["Friday"] || "";
    document.getElementById("saturday-hours").innerText = businessHours["Saturday"] || "";
    document.getElementById("sunday-hours").innerText = businessHours["Sunday"] || "";
  }
  
  // Function to render services
  function renderServices(serviceList) {
    const servicesContainer = document.querySelector('.Services');
    servicesContainer.innerHTML = '';
  
    serviceList.forEach(service => {
      const serviceDiv = document.createElement('div');
      serviceDiv.classList.add('services-inside');
  
      const nameDiv = document.createElement('div');
      nameDiv.classList.add('name');
      const nameText = document.createElement('p2');
      nameText.textContent = service.serviceName;
      nameDiv.appendChild(nameText);
  
      const chargesDiv = document.createElement('div');
      chargesDiv.classList.add('charges');
      const chargesText = document.createElement('p3');
      chargesText.textContent = service.servicePrice;
      chargesDiv.appendChild(chargesText);
  
      serviceDiv.appendChild(nameDiv);
      serviceDiv.appendChild(chargesDiv);
  
      servicesContainer.appendChild(serviceDiv);
    });
  }
  
  // Function to load business image
  function loadBusinessImage(imagePath) {
    const imageElement = document.getElementById('business-image');
    imageElement.src = imagePath;
  }
  
  // Main function
  window.onload = async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const businessId = urlParams.get('id');
  
    try {
      const businessData = await fetchData(`http://localhost:8080/api/v1/business/getBusiness/${businessId}`);
      if (businessData.code === "00") {
        renderBusinessDetails(businessData.content);
      } else {
        console.error("Error fetching business details:", businessData.messeage);
      }
    } catch (error) {
      console.error("Error fetching business details:", error);
    }
  
    try {
      const businessHoursData = await fetchData(`http://localhost:8080/api/v1/business/getBusinessHours/${businessId}`);
      if (businessHoursData.code === "00") {
        renderBusinessHours(businessHoursData.content.businessHours);
      } else {
        console.error("Error fetching business hours:", businessHoursData.messeage);
      }
    } catch (error) {
      console.error("Error fetching business hours:", error);
    }
  
    try {
      const servicesData = await fetchData(`http://localhost:8080/api/v1/service/getService/${businessId}`);
      if (servicesData.code === 'RSP_SUCCESS') {
        renderServices(servicesData.content);
      } else {
        renderServices(servicesData.content);
      }
    } catch (error) {
      console.error("Error fetching services:", error);
    }
  
    try {
      const imageData = await fetchData(`http://localhost:8080/api/v1/business/getBusinessImage/${businessId}`);
      if (imageData.code === 'RSP_SUCCESS') {
        loadBusinessImage(imageData.content.mainBannerImagePath);
      } else {
        loadBusinessImage(imageData.content.mainBannerImagePath);
      }
    } catch (error) {
      console.error("Error fetching business image:", error);
    }
  };