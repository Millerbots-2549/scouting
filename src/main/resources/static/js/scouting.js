$(document).ready(function () {

    let eventId;
    let surveyId;
    let events;
    let matchupKey;

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
                dataType: "json",
                success: function (surveys) {
                    build_survey_list(surveys);

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
        let responses = [];

        $('.question').each(function () {
            let questionId = $(this).data("questionId");
            let response = get_response(questionId);

            responses.push({
                "questionId": questionId,
                "response": response,
                "teamMatchupId": $('#team_matchup_id').val()
            });
        });

        return responses;
    }

    function get_response(questionID) {
        let elem = $("[name='questionId" + questionID + "']");
        let response = elem.val();

        if (elem.attr('type') === 'checkbox') {
            response = elem.is(':checked') ? 'true' : 'false';
        }

        if (elem.attr('type') === 'radio') {
            response = elem.is(':checked') ? $("[name='questionId" + questionID + "']:checked").val() : '';
        }

        return response;
    }

    $("#submit").on('click', function () {
        if (confirm_submit()) {
            let responses = get_responses();
            submit_responses(responses);
        }
    });

    function submit_responses(responses) {
        $.ajax({
            url: 'responses',
            type: "POST",
            //dataType: "json",
            contentType: 'application/json',
            data: JSON.stringify(responses),
            success: function (result) {
                alert("Your match data has been submitted.");
                window.location = "index";
            },

            error: function (xhr, resp, text) {
                console.log(xhr, resp, text);
                $("#message").text("This team matchup may have already been submitted. Please verify and try again.").show();
            }
        })
    }

    function confirm_submit() {
        let matchNumber = $('#matchup_id option:selected').data("matchupNumber");
        let teamNumber = $('#team_matchup_id option:selected').data("teamId");

        if (isNumber(matchNumber) && isNumber(teamNumber)) {
            let messageConfirm;
            if (matchNumber === -1) {
                messageConfirm = "You are submitting the result for Pit scouting";
            } else {
                messageConfirm = "You are submitting the result for Match No.: " + matchNumber;
            }
            messageConfirm += " for Team: " + teamNumber + ". Is this correct?";
            return confirm(messageConfirm);
        } else {
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
        let x = a.sortBy.toLowerCase();
        let y = b.sortBy.toLowerCase();
        return x < y ? -1 : x > y ? 1 : 0;
    }

    // do a key lookup based on submitted value
    function val2key(val, array, property) {
        let key;
        for (key=0; key<array.length; key++) {
            // noinspection EqualityComparisonWithCoercionJS
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
        let eventOptions = '<option value="-1"></option>';

        for (let anEvent of eventsObj) {
            eventOptions += '<option value="' + anEvent.eventId + '">' + anEvent.city + ' - ' + anEvent.name + '</option>';
        }

        $('#event_name').html(eventOptions);
    }


    function build_survey_list(surveysObj) {
        let surveyOptions = '<option value="-1"></option>';

        for (let survey of surveysObj) {
            surveyOptions += '<option value="' + survey.surveyId + '">' + survey.name + '</option>';
        }

        $('#survey_name').html(surveyOptions);
    }

    function build_matchups(eventsObj) {
        // matchups dropdown
        let matchups = []
        let matchupsOptions = '<option value="Select a Match No" selected></option>';

        // sort matchups
        for (let matchup of eventsObj.matchups) {
            matchups.push({
                sortBy: matchup.matchNumber,
                id: matchup.matchupId,
                no: matchup.matchNumber
            });
        }
        matchups.sort(sortNumerically);

        // build matchup options
        for (let matchup of matchups) {
            let selectionText = matchup.no;
            if (matchup.no === -1) {
                selectionText = 'Pit'
            }
            matchupsOptions += '<option value="' + matchup.id + '" data-matchup-number="' + matchup.no + '">' + selectionText + '</option>';
        }

        $('#matchup_id').html(matchupsOptions);
    }

    function build_alliances(matchupObj) {
        let allianceOptions = '';
        allianceOptions += '<option value="1" selected></option>';

        if (matchupObj.type === 'pit') {
            allianceOptions += '<option value="pit">Pit</option>';
        } else {
            allianceOptions += '<option value="blue">Blue</option>';
            allianceOptions += '<option value="red">Red</option>';
        }
        $('#alliance').html(allianceOptions);
    }

    function build_teams(teamMatchupObjs, selectedAlliance) {
        let teams = [];
        for (let teamMatchup of teamMatchupObjs) {
            teams.push({
                sortBy: teamMatchup.allianceOrder,
                teamId: teamMatchup.team.teamId,
                name: teamMatchup.team.name,
                matchupId: teamMatchup.teamMatchupId,
                alliance: teamMatchup.alliance
            });
        }
        teams.sort(sortNumerically);

        let teamOptions = ''
        for (let team of teams) {
            if (selectedAlliance === team.alliance) {
                teamOptions += '<option value="';
                teamOptions += team.matchupId;
                teamOptions += '" data-team-id="';
                teamOptions += team.teamId;
                teamOptions += '">';
                teamOptions += team.sortBy;
                teamOptions += ' - ';
                teamOptions += team.teamId;
                teamOptions += ' - ';
                teamOptions += team.name;
                teamOptions += '</option>';
            }
        }

        $('#team_matchup_id').html(teamOptions);
    }

    function build_survey(surveyObj) {
        let sectionHTML = '';

        for (let sectionObj of surveyObj.surveySections) {
            sectionHTML += '<hr><h2 class="section">' + sectionObj.name + '</h2>';
            sectionHTML += build_questions(sectionObj.questions);
        }

        return sectionHTML;
    }

    function build_questions(questionsObj) {
        let questionHTML = '';

        for (let questionObj of questionsObj) {
            let elementId = 'questionId' + questionObj.questionId;
            questionHTML += '<div class="question row" data-question-id="' + questionObj.questionId + '">';
            questionHTML += '<label class="label label-default" for="' + elementId + '">' + questionObj.question + '</label>';
            questionHTML += build_response(questionObj, elementId);
            questionHTML += '</div>';
        }

        return questionHTML;
    }

    function build_response(questionObj, elementId) {
        let questionType = questionObj.questionType.description;

        switch (questionType) {

            case 'boolean':
                return response_boolean(elementId);

            case 'numeric':
                return response_numeric(elementId);

            case 'choice':
                return response_choice(questionObj, elementId);

            case 'radio':
                return response_radio(questionObj, elementId);

            case 'text':
                return response_text(questionObj.questionId, elementId);

            default:
                return '';

        }

    }

    function response_boolean(elementId) {
        let checkbox = '';
        checkbox += '<input id="' + elementId + '" name="' + elementId + '" type="checkbox">';
        return checkbox;
    }

    function response_numeric(elementId) {
        let numeric = '';
        numeric += '<input id="' + elementId + '" name="' + elementId + '" type="number" min="0" max="100">';
        numeric += '<button type="button" class="btn btn-primary increment"><i class="fas fa-plus"></i></button>';
        numeric += '<button type="button" class="btn btn-primary decrement"><i class="fas fa-minus"></i></button>';
        return numeric;

    }

    $(this).on('click', 'button.increment', function () {
        let input = $(this).closest('button').prev('input');
        input.get(0).value++;
    });

    $(this).on('click', 'button.decrement', function () {
        let input = $(this).closest('button').prev().prev('input');
        input.get(0).value--;
    });

    function response_text(questionId, elementId) {
        let text = '';
        text += '<textarea';
        text += ' id="' + elementId + '"';
        text += ' name="' + elementId + '"';
        text += ' rows="2" cols="50" maxlength="400"></textarea>';
        return text;
    }

    function response_choice(questionObj, elementId) {

        let values = questionObj.questionType.responseValues;
        let choice = '<select id="' + elementId + '" name="' + elementId + '">';

        for (let aValue of values) {
            if (aValue.isDefault) {
                choice += '<option selected="selected" value="' + aValue.value + '"> ' + aValue.value + '</option>';
            } else {
                choice += '<option value="' + aValue.value + '"> ' + aValue.value + '</option>';
            }
        }

        choice += '</select>';
        return choice;

    }

    function response_radio(questionObj, elementId) {
        let values = questionObj.questionType.responseValues;
        let radio = '';

        for (let aValue of values) {
            radio += '<input id="' + elementId + '" name="' + elementId + '" type="radio" value="' + aValue.value + '"> ' + aValue.value;
        }

        return radio;
    }

})
;
