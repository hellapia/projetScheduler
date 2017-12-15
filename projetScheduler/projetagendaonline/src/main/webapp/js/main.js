function deleteTask(id) {
    var requeteidTask = new XMLHttpRequest();
    requeteidTask.open("GET","webservices/deleteTask/"+id,true);
    requeteidTask.responseType="text";
    requeteidTask.send();
    requeteidTask.onload = function () {
        var resp = this.response
        console.log(resp);
        if (resp == "true"){
            var ligne = document.getElementById("ligne"+id);
            document.getElementById("tabBody").removeChild(ligne);
        }

    }

}

window.onload=function() {
    var tab = document.querySelectorAll(".fa-trash");
    for (i = 0; i < tab.length; i++) {
        tab[i].onclick = function () {
            var id = this.getAttribute("id");
            var r = confirm("Es-tu sur de vouloir supprimer cette tâche ?");
            if (r == true) {
                deleteTask(id);
            }
        }
    }
};

