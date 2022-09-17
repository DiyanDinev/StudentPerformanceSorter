$('#downloadButton').on('click', function(){
	exportTableToCSV("SortedPerformace");
});

function exportTableToCSV(csvName) {
    var csv = [];
    var records = document.querySelectorAll("tr");
    
    for (var i = 0; i < records.length; i++) {
        var row = [], cols = records[i].querySelectorAll("td, th");     
        for (var j = 0; j < cols.length; j++){
			row.push(cols[j].innerText);
		} 
        csv.push(row.join(","));        
    }
    
    downloadCSV(csv.join("\n"), csvName);
}

function downloadCSV(csv, csvName) {
    var file = new Blob([csv], {type: "text/csv"});
    var dw = document.createElement("a");

    dw.download = csvName;
    dw.href = window.URL.createObjectURL(file);
    dw.style.display = "none";
    document.body.appendChild(dw);

    dw.click();
}