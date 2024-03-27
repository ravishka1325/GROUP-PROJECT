window.addEventListener('DOMContentLoaded', () => {
  const businessId = sessionStorage.getItem('businessId');
  if (businessId) {
    fetchServices(businessId);
  } else {
    console.error('BusinessID not found in session storage.');
  }
});

function fetchServices(businessId) {
  fetch(`http://localhost:8080/api/v1/service/getService/${businessId}`)
    .then(response => response.json())
    .then(data => {
      if (data.code === "RSP_SUCCESS") {
        console.log('Response Data:', data); // Add this line to inspect the response data
        const services = data.content;
        displayServices(services);
      } else {
        console.log('Response Data:', data); // Add this line to inspect the response data
        const services = data.content;
        displayServices(services);
        // Display error message to the user
      }
    })
    .catch(error => {
      console.error('Error:', error);
      // Display error message to the user
    });
}

function displayServices(services) {
  console.log('Services Array:', services); // Add this line to inspect the services array

  const servicesContainer = document.querySelector('main');
  if (!servicesContainer) {
    console.error('Main element not found in the HTML document.');
    return;
  }

  servicesContainer.innerHTML = '';

  if (services.length === 0) {
    servicesContainer.innerHTML = "<p>No services found for the provided Business ID.</p>";
  } else {
    const serviceBoxes = services.map(service => `
      <div class="box">
        <p>${service.serviceName}</h2>
        <p>Rs.${service.servicePrice}</p>
      </div>
    `);
    servicesContainer.insertAdjacentHTML('beforeend', serviceBoxes.join(''));
  }
}