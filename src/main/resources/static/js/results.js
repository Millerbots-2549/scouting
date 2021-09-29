let eventId;
let teamId;

$(document).ready(function () {

    $.ajax(
        {
            type: "GET",
            url: "results/events",
            dataType: "json",
            success: function (events) {
                buildEvent(events);
            },
            error: function (response) {
                console.log(response);
            }
        });

    $('#event_name').on('change', function () {
        eventId = $(this).val();
        $.ajax(
            {
                type: "GET",
                url: "results/events/" + eventId + "/teams",
                dataType: "json",
                success: function (teams) {
                    $('#resultsDiv').html('');
                    buildTeamList(teams);
                },
                error: function (response) {
                    console.log(response);
                }
            });
    });

    $('#team_number').on('change', function () {
        teamId = $(this).val();
        $.ajax(
            {
                type: "GET",
                url: "results/events/" + eventId + "/teams/" + teamId,
                dataType: "json",
                cache: false,
                success: function (json) {
                    let allSurveyData = '';
                    let surveyObjs = json.surveys;
                    if (surveyObjs === undefined || surveyObjs.length === 0) {
                        allSurveyData += '<hr><h4>No Data Found</h4>';
                    } else {
                        for (let survey of surveyObjs) {
                            allSurveyData += buildSurveySection(survey)
                        }
                    }

                    $('#resultsDiv').html(allSurveyData);
                },
                error: function (response) {
                    console.log(response);
                }
            });
    });
})
;

function buildSurveySection(survey) {
    let surveyHtml = '';
    surveyHtml += '<hr><h2>' + survey.surveyName + '</h2><table id="surveyResults' + survey.surveyId + '" class="table table-responsive table-bordered table-striped">';
    surveyHtml += buildTableHeader(survey.questions);
    surveyHtml += buildTableBody(survey.questions);
    surveyHtml += '</table>';
    return surveyHtml
}

function buildTableHeader(questions) {
    let surveyHtml = '<thead class="thead-dark">';
    surveyHtml += buildHeaderRow(questions[0]);
    surveyHtml += '</thead>';
    return surveyHtml;
}

function buildTableBody(questions) {
    let surveyHtml = '<tbody>';
    surveyHtml += buildStudentNameRow(questions[0]);
    for (let question of questions) {
        surveyHtml += buildRow(question)
    }
    surveyHtml += '</tbody>';
    return surveyHtml;
}

function buildHeaderRow(question) {
    let responses = question.responses;
    let rowHtml = '<tr>';
    rowHtml += '<th>Question</th>';
    for (let response of responses) {
        rowHtml += '<th>Match ' + response.matchup + '</th>';
    }
    rowHtml += '<th>Summary</th>';
    rowHtml += '</tr>';
    return rowHtml;
}

function buildStudentNameRow(question) {
    let responses = question.responses;
    let rowHtml = '<tr>';
    rowHtml += '<td><I>Student</I></td>';
    for (let response of responses) {
        rowHtml += '<td><I>' + response.studentName + '</I></td>';
    }
    rowHtml += '<td></td>';
    rowHtml += '</tr>';
    return rowHtml;
}

function buildRow(question) {
    let responses = question.responses;
    let rowHtml = '<tr>';
    rowHtml += '<td>' + question.question + '</td>';
    for (let response of responses) {
        rowHtml += '<td>' + response.response + '</td>';
    }
    rowHtml += '<td class="table-warning">' + question.summary + '</td>';
    rowHtml += '</tr>';
    return rowHtml;
}

function buildEvent(eventsObj) {
    let eventOptions = '<option value="-1"></option>';

    for (let event of eventsObj) {
        eventOptions += '<option value="' + event.eventId + '">' + event.city + ' - ' + event.name + '</option>';
    }

    $('#event_name').html(eventOptions);
}

function buildTeamList(teamObjs) {
    let teamOptions = '<option value="-1"></option>';

    for (let team of teamObjs) {
        let selection = '[' + team.teamId + '] ' + team.name;
        teamOptions += '<option value="' + team.teamId + '">' + selection + '</option>';
    }

    $('#team_number').html(teamOptions);
}
