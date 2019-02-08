$(document).ready(function () {

    var eventId;
    var surveyId;
    var events;
    var matchupKey;

    //get event data from database;
    $.ajax(
        {
            type: "GET",
            url: "events",
            //url: "events.json",
            dataType: "json",
            success: function (events) {
                build_event(events);
            },
            error: function (response) {
                console.log(response);
            }
        });

    $('#event_name').on('change', function () {
        eventId = $(this).val();
        surveyId = null;
        events = null;
        matchupKey = null;
        $.ajax(
            {
                type: "GET",
                url: "events/" + eventId + "/surveys",
                //url: "surveys.json",
                dataType: "json",
                success: function (surveys) {
                    build_survey_list(surveys);
                    build_students(); // separate ajax call

                    // When we change events we need to clear out teams and matches.
                    $('#team_matchup_id').html('<option value="Select a Match No" selected></option>');
                    $('#matchup_id').html('<option value="Select a Match No" selected></option>');
                    $('#alliance').html('<option value="1" selected></option>');
                },
                error: function (response) {
                    console.log(response);
                }
            });
    });

	$('#survey_name').on('change', function () {
        // get the selected value
        // make ajax call to all event data for {eventId}/surveys/{surveyId}
        surveyId = $(this).val();

        $.ajax({
            dataType: "json",
            url: "events/" + eventId + "/surveys/" + surveyId,
            cache: false,
            success: function (json) {
                events = json;
                // when the survey changes need to clear out teams. matches will be rebuilt
                $('#team_matchup_id').html('<option value="Select a Match No" selected></option>');
                $('#alliance').html('<option value="1" selected></option>');
                build_matchups(events);
                $('#survey').html(build_survey(events.survey));
                confirm_submit();
            },
            error: function (response) {
                events = null;
                console.log(response);
            }
        });
    });

    $('#matchup_id').on('change', function () {
        matchupKey = val2key(this.value, events.matchups, 'matchupId');
        $('#team_matchup_id').html('<option value="Select a Match No" selected></option>');
        build_alliances(events.matchups[matchupKey]);
    });

    $('#alliance').on('change', function () {
        build_teams(events.matchups[matchupKey].teamMatchups, this.value);
    });

    function get_responses() {

        var responses = [];

        $('.question').each(function () {

            var questionId = $(this).data("questionId");

            var response = get_response(questionId);

            responses.push({
                "questionId": questionId,
                "response": response,
                "studentId": $('#student_id').val(),
                "teamMatchupId": $('#team_matchup_id').val()
            });
        });

        return responses;
    }

    function get_response(questionID) {

        var elem = $("[name='questionId" + questionID + "']");

        var response = elem.val();

        if (elem.attr('type') == 'checkbox') {

            response = elem.is(':checked') ? 'true' : 'false';

        }

        if (elem.attr('type') == 'radio') {

            response = elem.is(':checked') ? $("[name='questionId" + questionID + "']:checked").val() : '';

        }

        return response;

    }

    $("#submit").on('click', function () {

        event.preventDefault();

        if (confirm_submit()) {

            var responses = get_responses();

            //console.log(JSON.stringify(responses));

            submit_responses(responses);

        }

        return;

    });

    function submit_responses(responses) {

        $.ajax({
            url: 'responses',
            type: "POST",
            contentType: 'application/json',
            //dataType : 'json',
            data: JSON.stringify(responses),
            success: function (result) {
                //console.log("success")
                //console.log(result);

                alert("Your match data has been submitted.");

                window.location = "index.html";

            },
            error: function (xhr, resp, text) {

                console.log(xhr, resp, text);

                $("#message").text("This team matchup may have already been submitted. Please verify and try again.").show();

            }
        })

    }

    function confirm_submit() {

        var matchNumber = $('#matchup_id option:selected').data("matchupNumber");
        var teamNumber = $('#team_matchup_id option:selected').data("teamId");

        if (isNumber(matchNumber) && isNumber(teamNumber)) {
            var messageConfirm;
            if (matchNumber == '-1') {
                messageConfirm = "You are submitting the result for Pit scouting";
            } else {
                messageConfirm = "You are submitting the result for Match No.: " + matchNumber;
            }
            messageConfirm += " for Team: " + teamNumber + ". Is this correct?";
            return confirm(messageConfirm);
        }
        else {
            $("#message").text("Please select a Match No. and Team!").show().fadeOut(5000);
            return false;
        }
    }

    // sort objects by id property
    function sortNumerically(a, b) {
        return a.sortBy - b.sortBy;
    }

    // sort objects by alpha property
    function sortAlpha(a, b) {
        var x = a.sortBy.toLowerCase();
        var y = b.sortBy.toLowerCase();
        return x < y ? -1 : x > y ? 1 : 0;
    }

    // do a key lookup based on submitted value
    function val2key(val, array, property) {
        for (var key in array) {
            if (array[key][property] == val) {
                return key;
            }
        }
        return false;
    }

    // test value if is numeric
    function isNumber(n) {
        return !isNaN(parseFloat(n)) && isFinite(n);
    }

    function build_event(eventsObj) {
        var eventOptions = '<option value="-1"></option>';

        for (i in eventsObj) {
            var event = eventsObj[i];
            eventOptions += '<option value="' + event.eventId + '">' + event.city + ' - ' + event.name + '</option>';
        }

        $('#event_name').html(eventOptions);
    }


    function build_survey_list(surveysObj) {
        var surveyOptions = '<option value="-1"></option>';

        for (i in surveysObj) {
            var survey = surveysObj[i];
            surveyOptions += '<option value="' + survey.surveyId + '">' + survey.name + '</option>';
        }

        $('#survey_name').html(surveyOptions);
    }

    function build_matchups(eventsObj) {

        // matchups dropdown
        var matchupsObj = eventsObj.matchups, matchups = [],
            matchupsOptions = '<option value="Select a Match No" selected></option>';

        // sort matchups
        for (i in matchupsObj) {
            matchups.push({
                sortBy: matchupsObj[i].matchNumber,
                id: matchupsObj[i].matchupId,
                no: matchupsObj[i].matchNumber
            });
        }

        matchups.sort(sortNumerically);

        // build matchup options
        for (i in matchups) {
            var selectionOption = matchups[i].no;
            if (selectionOption == '-1') {
                selectionOption = 'Pit'
            }
            matchupsOptions += '<option value="' + matchups[i].id + '" data-matchup-number="' + matchups[i].no + '">' + selectionOption + '</option>';
        }

        $('#matchup_id').html(matchupsOptions);
    }

    function build_alliances(matchupObj) {

        var allianceOptions = '';

        allianceOptions += '<option value="1" selected></option>';

        if (matchupObj.type == 'pit') {
            allianceOptions += '<option value="pit">Pit</option>';
        } else {
            allianceOptions += '<option value="blue">Blue</option>';
            allianceOptions += '<option value="red">Red</option>';
        }
        $('#alliance').html(allianceOptions);
    }

    function build_teams(teamMatchupObj, selectedAlliance) {
        var teamOptions = '', teams = [];

        for (i in teamMatchupObj) {
            teams.push({
                sortBy: teamMatchupObj[i].team.teamId,
                teamId: teamMatchupObj[i].team.teamId,
                name: teamMatchupObj[i].team.name,
                matchupId: teamMatchupObj[i].teamMatchupId,
                alliance: teamMatchupObj[i].alliance
            });
        }

        teams.sort(sortNumerically);

        for (i in teams) {
            if (selectedAlliance === teams[i].alliance) {
                teamOptions += '<option value="' + teams[i].matchupId + '" data-team-id="' + teams[i].teamId + '">' + teams[i].teamId + ' - ' + teams[i].name + '</option>';
            }
        }

        $('#team_matchup_id').html(teamOptions);
    }

    function build_students() {

        $.ajax(
            {
                type: "GET",
                url: "students",
                //url: "students.json",
                dataType: "json",
                success: function (json) {

                    var studentsObj = json, students = [], studentsOptions = '';

                    // sort students
                    for (i in studentsObj) {
                        students.push({
                            sortBy: studentsObj[i].lastName,
                            id: studentsObj[i].studentId,
                            last: studentsObj[i].lastName,
                            first: studentsObj[i].firstName
                        });

                    }

                    students.sort(sortAlpha);

                    // build students drop down
                    for (i in students) {

                        studentsOptions += '<option value="' + students[i].id + '">' + students[i].last + ', ' + students[i].first + '</option>';

                    }

                    $('#student_id').html(studentsOptions);

                },
                error: function (response) {
                    console.log(response)
                }

            });

    }

    function build_survey(surveyObj) {

        //console.log(surveyObj);
        //console.log(surveyObj.surveySections);

        var sectionObj, sectionHTML = '';

        for (i in surveyObj.surveySections) {

            //console.log(surveyObj.surveySections[i]);

            sectionObj = surveyObj.surveySections[i];

            sectionHTML += '<hr><h2 class="section">' + sectionObj.name + '</h2>';

            sectionHTML += build_questions(surveyObj.surveySections[i].questions);

        }

        return sectionHTML;

    }

    function build_questions(questionsObj) {

        var questionObj, questionHTML = '';

        for (i in questionsObj) {

            //console.log(questionsObj[i]);

            questionObj = questionsObj[i];

            questionHTML += '<div class="question row" data-question-id="' + questionObj.questionId + '">' + questionObj.question;

            questionHTML += build_response(questionObj);

            questionHTML += '</div>';
        }

        return questionHTML;

    }

    function build_response(questionObj) {

        var questionType = questionObj.questionType.description;

        switch (questionType) {

            case 'boolean':

                return response_boolean(questionObj.questionId);

                break;

            case 'numeric':

                return response_numeric(questionObj.questionId);

                break;

            case 'choice':

                return response_choice(questionObj);

                break;

            case 'radio':

                return response_radio(questionObj);

                break;

            case 'text':

                return response_text(questionObj.questionId);

                break;

            default:

                return '';

        }

    }

    function response_boolean(questionId) {

        var checkbox = '';

        checkbox += '<input name="questionId' + questionId + '" type="checkbox">';

        return checkbox;

    }

    function response_numeric(questionId) {

        var numeric = '';

        numeric += '<input name="questionId' + questionId + '" type="number" min="0" max="100">';
		numeric += '<button type="button" class="btn btn-primary increment"><i class="fas fa-plus"></i></button>';
		numeric += '<button type="button" class="btn btn-primary decrement"><i class="fas fa-minus"></i></button>';

        return numeric;

    }

	$(this).on('click','button.increment',function(){

		var input = $(this).closest('button').prev('input');

		input.get(0).value++;

    });

	$(this).on('click','button.decrement',function(){

		var input = $(this).closest('button').prev().prev('input');

		input.get(0).value--;

    });

    function response_text(questionId) {

        var text = '';

        //text += '<input name="questionId' + questionId + '" type="text" maxlength="400">';

        text += '<textarea name="questionId' + questionId + '" rows="2" cols="50" maxlength="400"/>';

        return text;

    }

    function response_choice(questionObj) {

        var values = questionObj.questionType.responseValues;

        var choice = '<select name="questionId' + questionObj.questionId + '">';

        for (i in values) {
            if (values[i].isDefault) {
                choice += '<option selected="selected" value="' + values[i].value + '"> ' + values[i].value + '</option>';
            } else {

                choice += '<option value="' + values[i].value + '"> ' + values[i].value + '</option>';
            }

        }

        choice += '</select>';

        return choice;

    }

    function response_radio(questionObj) {

        var values = questionObj.questionType.responseValues;

        var radio = '';

        for (i in values) {

            radio += '<input name="questionId' + questionObj.questionId + '" type="radio" value="' + values[i].value + '"> ' + values[i].value;

        }

        return radio;

    }

})
;
