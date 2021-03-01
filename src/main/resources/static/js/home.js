(function () {
    let loading = false;
    let map = L.map('mapid').setView([0, 0], 3);
    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token=pk.eyJ1IjoicnlkYmVyZ2ZpbGlwIiwiYSI6ImNrbGt2eWh2ZDBrd3Eyb3A1YWlhMTV6d2cifQ.JkvA0XVrJNvRmbjnevr2DQ', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 18,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'pk.eyJ1IjoicnlkYmVyZ2ZpbGlwIiwiYSI6ImNrbGt2eWh2ZDBrd3Eyb3A1YWlhMTV6d2cifQ.JkvA0XVrJNvRmbjnevr2DQ'
    }).addTo(map);


    const setLocations = (title) => {
        return fetch(new URL(`http://localhost:8080/movie/${title}/filming-locations`))
          .then(response => {
            if(!response.ok) {
              throw Error(response.statusText);
            }
            return response.json();
          })
          .then(locations => {
            if (locations == null) return;
            locations.forEach(loc => {
            if (loc.locationLatLng == null) return;
            let latLong = loc.locationLatLng;
            let marker = L.marker([latLong.longitude, latLong.latitude]).addTo(map);
            marker.bindPopup(loc.address + " \n " + loc.trivia).openPopup();
            })
          })
    }

    const setPoster = (title) => {
       return fetch(new URL(`http://localhost:8080/movie/${title}/poster`))
          .then(response => {
            if(!response.ok) {
              throw Error(response.statusText);
            }
            return response.text();
          })
          .then(posterUrl => {
            let img = document.getElementById('poster');
            console.log(img)
            img.src = posterUrl;
          })
    }

    const getMovie = (title) => {
       return fetch(new URL(`http://localhost:8080/movie/${title}`))
          .then(response => {
            if(!response.ok) {
              throw Error(response.statusText);
            }
            return response.json();
          })
    }

    document.getElementById("titleInput").addEventListener("keyup", async function(event) {
      if (event.keyCode === 13) {
        event.preventDefault();

        map.eachLayer(function(layer){
            if (layer.options != null && layer.options.id == "mapbox/streets-v11") {
              console.log("Keeping map layer")
            } else {
              layer.remove();
            }
        });
        let title = document.getElementById("titleInput").value;
        document.getElementById("titleInput").value = "loading...";
        document.getElementById("titleInput").disabled = true;

        try {
          var movie = await getMovie(title);
          console.log(movie)
          await setLocations(movie.primaryTitle);
          document.getElementById("titleInput").value = movie.primaryTitle + " (" + movie.year + ")";
          await setPoster(movie.primaryTitle);

        } catch(e) {
          document.getElementById("titleInput").value = "not found :( ";
        } finally {
          document.getElementById("titleInput").disabled = false;
        }
      }
    });


})();






