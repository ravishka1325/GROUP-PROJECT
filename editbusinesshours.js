const hoursDiv = document.getElementById('hours');
const editBtn = document.getElementById('editBtn');
const editForm = document.getElementById('editForm');
const saveBtn = document.getElementById('saveBtn');
const cancelBtn = document.getElementById('cancelBtn');
const dayInputs = document.querySelectorAll('#editForm input');

// Load business hours from localStorage or set default values
const businessHours = JSON.parse(localStorage.getItem('businessHours')) || {
    monday: '9:00 AM - 5:00 PM',
    tuesday: '9:00 AM - 5:00 PM',
    wednesday: '9:00 AM - 5:00 PM',
    thursday: '9:00 AM - 5:00 PM',
    friday: '9:00 AM - 5:00 PM',
    saturday: 'Closed',
    sunday: 'Closed'
};

// Display initial business hours
displayHours();

// Event listeners
editBtn.addEventListener('click', showEditForm);
saveBtn.addEventListener('click', saveHours);
cancelBtn.addEventListener('click', hideEditForm);

function displayHours() {
    let hoursHTML = '';
    for (const day in businessHours) {
        hoursHTML += `<p><span>${day.charAt(0).toUpperCase() + day.slice(1)}:</span><span>${businessHours[day]}</span></p>`;
    }
    hoursDiv.innerHTML = hoursHTML;
}

function showEditForm() {
    editForm.style.display = 'block';
    for (const input of dayInputs) {
        input.value = businessHours[input.id];
    }
}

function saveHours() {
    for (const input of dayInputs) {
        businessHours[input.id] = input.value;
    }
    localStorage.setItem('businessHours', JSON.stringify(businessHours));
    displayHours();
    hideEditForm();
}

function hideEditForm() {
    editForm.style.display = 'none';
}