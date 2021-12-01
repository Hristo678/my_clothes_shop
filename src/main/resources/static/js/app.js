$('#loadAllCars').click(() => {
    loadModels()
});

function loadModels(){
    $("#models-container").empty();
    console.log("Working...")

    fetch("http://localhost:8080/models/load/all").
    then(response => response.json()).
    then(json => json.forEach(model => {
        let tableRow = `<tr><td>${model.name}</td><td>${model.startYear}</td><img src = ${model.imageUrl} alt=""><td></td></tr>`
        $("#models-container").append(tableRow)
    }))
}

