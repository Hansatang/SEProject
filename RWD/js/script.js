readXML();

function readXML() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            showData(this);
        }
    };
    xhttp.open("GET", "xml/projectList.xml", true);
    xhttp.send();
}

function showData(xml) {
    var x = xml.responseXML.getElementsByTagName("project");
    var listLength = x.length;

    var table = '<table id="table" class="center"><tr><th>Project Number</th><th>Project Name</th><th>Team Members</th></tr>';
    for (var i = 0; i < listLength; i++) {
        table +=
            "<tr onclick='showRequirements(" + i + ")'>" +
                "<td>" + x[i].getElementsByTagName("project_no")[0].childNodes[0].nodeValue + "</td>" +
                "<td>" + x[i].getElementsByTagName("project_name")[0].childNodes[0].nodeValue + "</td>" +
                "<td>" + x[i].getElementsByTagName("teamMembers")[0].childNodes[0].nodeValue + "</td>" +
            "</tr>" +
            "<tr>" +
                "<td id='requirement" + i + "' colspan='3' class='display'>" + " Requirement ID: <b>" + x[i].getElementsByTagName("requirement_id")[0].childNodes[0].nodeValue + "</b><br/>" +
                " Requirement status: <b>" + x[i].getElementsByTagName("requirement_status")[0].childNodes[0].nodeValue + "</b><br/>" +
             " Requirement deadline: <b>" + x[i].getElementsByTagName("requirement_deadline")[0].childNodes[0].nodeValue +"</td>"
            "</tr>";
    }
    table += "</table></div>";
    document.getElementById("tab").innerHTML = table;
}

function showRequirements(index){
    document.getElementById("requirement" + index).classList.toggle("display");
    console.log("");
}