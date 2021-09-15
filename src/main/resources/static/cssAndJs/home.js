Vue.component('overview', {
    props: ['title', 'image_url'],
    template:
        '<li class="list-group-item bg-light" >' +
        '<div class="row">' +
        '<div class="col-2">' +
        '<img v-bind:src="image_url" class="img-thumbnail" height="200" alt="thumbnail">' +
        '</div> ' +
        '<div class="col-10 d-flex align-items-center">' +
        '<a href="#animeinfo" class="h3 link-dark" data-bs-toggle="modal" data-bs-target="#animeinfo">{{title}}</a> ' +
        '</div>' +
        '</div>' +
        '</li>'
});
var app = new Vue({
    el: '#app',
    data: {
        serverUrl: "http://localhost:8080",
        query: "",
        searchResults: []
    },
    methods: {
        getSearchResult: function(query) {
            let xmlHttp = new XMLHttpRequest();
            // xmlHttp.onreadystatechange = function() {
            //     if (xmlHttp.readyState == 4 && xmlHttp.status == 200) {
            //         this.searchResults = JSON.parse(xmlHttp.responseText);
            //         console.log(this.searchResults);
            //     }
            // }
            xmlHttp.open("GET", this.serverUrl + "/api/v1/anime/search?query=" + query, false); // true for asynchronous
            console.log("Sending search request...\n");
            xmlHttp.send();
            if(xmlHttp.status === 200) {
                this.searchResults = JSON.parse(xmlHttp.responseText);
                console.log(this.searchResults);
            } else {
                console.log("Error message: " + xmlHttp.responseText);
            }
        }
    }
})