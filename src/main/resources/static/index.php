<!DOCTYPE html>
<html lang="en">
<head>
    <title>Millerbots Scouting</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>


</head>
<body>

<div class="jumbotron text-center">
    <h1>Washburn Millerbots Scouting System</h1>
    <p>Team 2549</p>
</div>

<form id="scouting" action="" method="post">

<div id="event_info">

	<label for="event_name">Event Name:</label>
	<span id="event_name"></span>

	<label for="matchup_id">Match No.:</label>
	<select id="matchup_id" name="matchupId"></select>
	
	<label for="team_id">Team ID:</label>
	<select id="team_id" name="teamId">
		<option value="Select a Match No." selected>Select a Match No.</option>
	</select>

	<label for="student_id">Student:</label>
	<select id="student_id" name="studentId"></select>

</div>

<div id="survey"></div>

<button id="submit">Submit</button><span id="message" style="color: red;"></span>

</form>

<script src="scouting.js"></script>

</body>
</html>
