$(document).ready(function () {

    var eventId;
    var surveyId;

    //get event data from database;
    $.ajax(
        {
            type: "GET",
            url: "events",
            dataType: "json",
            success: function (events) {
                build_event(events);
            },
            error: function (response) {
                console.log(response)
            }
        });

    $('#event_name').on('change', function () {
        eventId = $(this).val();
        $.ajax(
            {
                type: "GET",
                url: "events/" + eventId + "/surveys",
                dataType: "json",
                success: function (surveys) {
                    build_survey_list(surveys);
                },
                error: function (response) {
                    console.log(response)
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
            success: function (events) {
                build_matchups(events);
                build_students(); // separate ajax call
                $('#survey').html(build_survey(events.survey));
                $('#matchup_id').change(function () {
                    var key = val2key(this.value, events.matchups, 'matchupId');
                    build_teams(events.matchups[key].teamMatchups);
                });

                confirm_submit();
            },
            error: function (response) {
                console.log(response)
            }
        });
    });

    function confirm_submit() {

        $("form").submit(function (event) {

            if (isNumber($("#matchup_id").val())) {

                var messageConfirm = "You are submitting the result for Match No.: " + $('#matchup_id').val();

                messageConfirm += " and Team: " + $('#team_id option:selected').text() + ". Is this correct?";

                var userConfirm = confirm(messageConfirm);

                if (userConfirm === true) {
                    return;
                }
                else {
                    event.preventDefault();
                }

            }
            else {

                $("#message").text("Please select a Match No. and Team!").show().fadeOut(5000);

                event.preventDefault();

            }

        });

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
        var eventOptions = '<option value="-1">Select an event</option>';

        for (i in eventsObj) {
            var event = eventsObj[i];
            eventOptions += '<option value="' + event.eventId + '">' + event.city + ' - ' + event.name + '</option>';
        }

        $('#event_name').html(eventOptions);
    }


    function build_survey_list(surveysObj) {
        var surveyOptions = '<option value="-1">Select a survey</option>';

        for (i in surveysObj) {
            var survey = surveysObj[i];
            surveyOptions += '<option value="' + survey.surveyId + '">' + survey.name + '</option>';
        }

        $('#survey_name').html(surveyOptions);
    }

    function build_matchups(eventsObj) {

        // matchups dropdown
        var matchupsObj = eventsObj.matchups, matchups = [],
            matchupsOptions = '<option value="Select a Match No." selected>Select a Match No.</option>';

        // sort matchups
        for (i in matchupsObj) {

            matchups.push({
                sortBy: matchupsObj[i].matchupId,
                id: matchupsObj[i].matchupId,
                no: matchupsObj[i].matchNumber
            });

        }

        matchups.sort(sortNumerically);

        // build matchup options
        for (i in matchups) {

            matchupsOptions += '<option value="' + matchups[i].id + '">' + matchups[i].no + '</option>';

        }

        $('#matchup_id').html(matchupsOptions);

    }

    function build_teams(teamMatchupObj) {

        var teamOptions = '';

        for (i in teamMatchupObj) {
            var team = teamMatchupObj[i].team;
            teamOptions += '<option value="' + team.teamId + '">' + team.teamId + ' - ' + team.name + '</option>';
        }

        $('#team_id').html(teamOptions);

    }

    function build_students() {

        $.ajax(
            {
                type: "GET",
                url: "students",
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

        console.log(surveyObj.surveySections);

        var sectionObj, sectionHTML = '';

        for (i in surveyObj.surveySections) {

            //console.log(surveyObj.surveySections[i]);

            sectionObj = surveyObj.surveySections[i];

            sectionHTML += '<h2 class="section">' + sectionObj.name + '</h2>';

            sectionHTML += build_questions(surveyObj.surveySections[i].questions);

        }

        return sectionHTML;

    }

    function build_questions(questionsObj) {

        var questionObj, questionHTML = '';

        for (i in questionsObj) {

            console.log(questionsObj[i]);

            questionObj = questionsObj[i];

            questionHTML += '<div class="question">' + questionObj.question;

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

        return numeric;

    }

    function response_text(questionId) {

        var text = '';

        text += '<input name="questionId' + questionId + '" type="text" >';

        return text;

    }

    function response_choice(questionObj) {

        var values = questionObj.questionType.responseValues;

        var choice = '<select name="questionId' + questionObj.questionId + '">';

        for (i in values) {

            choice += '<option value="' + values[i].value + '"> ' + values[i].value + '</option>';

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

});