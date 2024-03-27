
    const searchButton = document.getElementById('searchButton');
    searchButton.addEventListener('click', handleSearch);

    function handleSearch() {
        const searchQuery = document.getElementById('searchQuery').value;
        const searchLocation = document.getElementById('searchLocation').value;
    
        // Construct the URL with the search parameters
        const url = `http://localhost:8080/api/v1/business/search?query=${encodeURIComponent(searchQuery)}&location=${encodeURIComponent(searchLocation)}`;
    
        // Create a new XMLHttpRequest object
        const xhr = new XMLHttpRequest();
    
        // Configure the request
        xhr.open('GET', url, true);
    
        // Set the request header for JSON response
        xhr.setRequestHeader('Content-Type', 'application/json');
    
        // Handle the response
        xhr.onload = function() {
            if (xhr.status === 200) {
                const data = JSON.parse(xhr.responseText);
                const searchResults = data.content;
                const searchResultUrl = `SearchResult.html?searchResults=${encodeURIComponent(JSON.stringify(searchResults))}&query=${encodeURIComponent(searchQuery)}&location=${encodeURIComponent(searchLocation)}`;
                window.open(searchResultUrl, '_self'); 
            } else {
                // Handle non-200 status codes
                console.error('Error fetching search results:', xhr.statusText);
            }
        };
    
        // Handle network errors
        xhr.onerror = function() {
            console.error('Network error fetching search results');
        };
    
        // Send the request
        xhr.send();
    }
