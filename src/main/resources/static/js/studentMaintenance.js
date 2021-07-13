$(function () {
    $("#message").text("").hide();

    $("#jsGrid").jsGrid({

        height: "auto",
        width: "100%",

        filtering: false,
        editing: true,
        inserting: true,
        sorting: true,
        paging: false,
        autoload: true,

        controller: {
            loadData: function (filter) {
                $("#message").text("").hide();
                return $.ajax({
                    type: "GET",
                    url: "students/",
                    data: filter,
                    error: function (xhr) {
                        $("#message").text("There was an issue with your request. " + xhr.responseJSON.errorMessage).show();
                    }
                });
            },

            insertItem: function (item) {
                $("#message").text("").hide();
                return $.ajax({
                    type: "POST",
                    url: "students/",
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify(item),
                    error: function (xhr) {
                        $("#message").text("There was an issue with your request. " + xhr.responseJSON.errorMessage).show();
                    }
                });
            },

            updateItem: function (item) {
                $("#message").text("").hide();
                return $.ajax({
                    type: "PUT",
                    url: "students/" + item.studentId,
                    contentType: 'application/json; charset=UTF-8',
                    data: JSON.stringify(item),
                    error: function (xhr) {
                        $("#message").text("There was an issue with your request. " + xhr.responseJSON.errorMessage).show();
                    }
                });
            },

            deleteItem: function (item) {
                $("#message").text("").hide();
                return $.ajax({
                    type: "DELETE",
                    url: "students/" + item.studentId,
                    error: function (xhr) {
                        $("#message").text("There was an issue with your request. " + xhr.responseJSON.errorMessage).show();
                    }
                });
            },
        },

        fields: [
            {title: "Id", name: "studentId", type: "number", visible: false},
            {title: "First Name", name: "firstName", type: "text", width: 100, validate: "required"},
            {title: "Last Name", name: "lastName", type: "text", width: 100, validate: "required"},
            {title: "User Name", name: "username", type: "text", width: 100, validate: "required"},
            {title: "Password", name: "password", type: "text", width: 100},
            {title: "Enabled", name: "enabled", type: "checkbox", width: 100},
            {title: "ROLE POWER USER", name: "rolePowerUser", type: "checkbox", width: 100},
            {title: "ROLE ADMIN", name: "roleAdmin", type: "checkbox", width: 100},
            {type: "control"}
        ]
    });

})
;
