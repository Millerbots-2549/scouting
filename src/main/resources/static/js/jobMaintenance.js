let statusEnabled;

$(document).ready(function () {
    getCurrentStatus('status');
})
;

function updatePageStatus(status) {
    statusEnabled = status.ENABLED;
    if (statusEnabled) {
        enabled();
    } else {
        disabled();
    }
}

function disabled() {
    document.getElementById('statusBtn').innerHTML = 'Disabled';
    document.getElementById("statusBtn").className = 'btn btn-danger';
    document.getElementById('rankingBtn').disabled = true;
    document.getElementById('matchUpBtn').disabled = true;
}

function enabled() {
    document.getElementById('statusBtn').innerHTML = 'Enabled';
    document.getElementById("statusBtn").className = 'btn btn-success';
    document.getElementById('rankingBtn').disabled = false;
    document.getElementById('matchUpBtn').disabled = false;
}

function statusButtonClicked() {
    if (statusEnabled) {
        changeStatus('disable');
    } else {
        changeStatus('enable');
    }
}

function getCurrentStatus(action) {
    $.ajax(
        {
            type: 'GET',
            url: 'tba/' + action,
            dataType: 'json',
            success: function (status) {
                updatePageStatus(status);
            },
            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
                $("#message").text("There was an issue with your request!").show().fadeOut(5000);
            }
        });
}

function changeStatus(action) {
    $.ajax(
        {
            type: 'PUT',
            url: 'tba/' + action,
            dataType: 'json',
            success: function (status) {
                updatePageStatus(status);
            },
            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
                $("#message").text("There was an issue with your request!").show().fadeOut(5000);
            }
        });
}

function getMatchUps() {
    let count = getBlueAllianceData('tba/matchups');
    $('#matchUpResultDiv').html('The number of events collected was: ' + count);
}

function getRankings() {
    let count = getBlueAllianceData('tba/rankings');
    $('#rankingResultDiv').html('The number of rankings collected was: ' + count);
}

function getBlueAllianceData(url) {
    let count
    $.ajax(
        {
            type: 'GET',
            url: url,
            dataType: 'json',
            async: false,
            success: function (results) {
                count = results.eventCount;
            },
            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
                $("#message").text("There was an issue with your request!").show().fadeOut(5000);
                return 0;
            }
        });
    return count
}