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
                    url: "events/",
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
                    url: "events/",
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
                    url: "events/" + item.eventId,
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
                    url: "events/" + item.eventId,
                    error: function (xhr) {
                        $("#message").text("There was an issue with your request. " + xhr.responseJSON.errorMessage).show();
                    }
                });
            },
        },

        fields: [
            {title: "Id", name: "eventId", type: "number", visible: false},
            {title: "Event Name", name: "name", type: "text", width: 200, validate: "required"},
            {title: "City", name: "city", type: "text", width: 100},
            {title: "State", name: "state", type: "text", width: 50},
            {title: "Event Key", name: "eventKey", type: "text", width: 50},
            {title: "Start Date", name: "startDate", type: "text", width: 100, validate: "required"},
            {title: "End Date", name: "endDate", type: "text", width: 100, validate: "required"},
            {type: "control"}
        ]
    });

})
;
