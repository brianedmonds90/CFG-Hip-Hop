<?php
	include 'db_helper.php';
	
	function lastActivitiesForMuscle($user_id, $muscle_id) {
		
		$dbQuery = sprintf("SELECT * 
			FROM exercises_muscles em
			INNER JOIN exercises e ON e.id = em.exercise_id
			WHERE em.muscle_id = '%s'", 
			mysql_real_escape_string($muscle_id));
		$result = getDBResultsArray($dbQuery);
		
		for($i = 0; $i < count($result); $i++){
			$dbQuery = sprintf("SELECT a.weight, a.reps, a.workout_id
				FROM activities a
				WHERE a.user_id = '%s'
				AND a.exercise_id = '%s'",
					mysql_real_escape_string($user_id),
					mysql_real_escape_string($result[$i]['exercise_id']) );
			try {
				$data = getDBResultsArray($dbQuery, False);
				$result[$i]['data'] = $data;
			} catch (Exception $e) {
				$result[$i]['data'] = Array();
			}
		}
		
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function listWorkoutActivities($user_id, $workout_id) {
		$dbQuery = sprintf("SELECT 
			a.id AS activity_id, e.id AS exercise_id, 
			e.name AS exercise_name, 
			a.weight, 
			a.reps
			FROM `activities` a
			INNER JOIN exercises e on e.id = a.exercise_id
			WHERE a.`user_id` = '%s' AND a.`workout_id` = '%s'", 
				mysql_real_escape_string($user_id),
				mysql_real_escape_string($workout_id));
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}
	
	function getWorkoutActivity($user_id, $workout_id, $activity_id) {
		$dbQuery = sprintf("SELECT 
			a.id AS activity_id, e.id AS exercise_id, 
			e.name AS exercise_name, 
			a.weight, 
			a.reps
			FROM `activities` a
			INNER JOIN exercises e on e.id = a.exercise_id
			WHERE a.`user_id` = '%s' AND a.`workout_id` = '%s' AND a.`id` = '%s'", 
				mysql_real_escape_string($user_id),
				mysql_real_escape_string($workout_id),
				mysql_real_escape_string($activity_id));
		$result = getDBResultRecord($dbQuery);
		echo ($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}

	function addWorkoutActivity($user_id, $workout_id, $exercise_id, $weight, $reps) {
		$dbQuery = sprintf("INSERT INTO `activities`
			(`exercise_id`, `weight`, `reps`, `user_id`, `workout_id`) 
			VALUES ('%s', '%s', '%s', '%s', '%s')",
			mysql_real_escape_string($exercise_id),
			mysql_real_escape_string($weight),
			mysql_real_escape_string($reps),
			mysql_real_escape_string($user_id),
			mysql_real_escape_string($workout_id));
		$result = getDBResultInserted($dbQuery, 'activity_id');
		header("Content-type: application/json");
		echo json_encode($result);
	}


	function listActivities(){
		$dbQuery = sprintf("SELECT * FROM acitivites");
		$result = getDBResultsArray($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
	}

	function editActivity($id,$reps,$weight){
		var_dump($_GET);
		echo "ID: ".$id;
		echo "   REPS: ".$reps;
		echo "   Weight: ".$weight; 
		$dbQuery = sprintf("UPDATE `activities`
			SET  `reps` = '%s' , `weight` = '%s' WHERE `id` = '%s'",
			mysql_real_escape_string($reps),
			mysql_real_escape_string($weight),
			mysql_real_escape_string($id));
		$result = getDBResultAffected($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
		echo $dbQuery;
	}

	function deleteActivity($id){
		
		echo "Parameter: ".$id;
		$dbQuery = sprintf("DELETE FROM `activities` WHERE `id` = '%s'",
			mysql_real_escape_string($id));
		
		echo $dbQuery;
		$result = getDBResultAffected($dbQuery);
		header("Content-type: application/json");
		echo json_encode($result);
		echo $dbQuery;
	}

	function getActivity($id,$exercise_id,$user_id){
		if($id != 0 || $id != "0"){
			$dbQuery = sprintf("SELECT * FROM activities WHERE activities.id = '%s'",
				mysql_real_escape_string($id));
			$result = getDBResultsArray($dbQuery);
			header("Content-type: application/json");
			echo json_encode($result);
		}
		elseif($exercise_id == "" && $user_id != ""){
			$dbQuery = sprintf("SELECT * FROM activites	activites.user_id = '%s' ORDER BY activites.created_on", 
				mysql_real_escape_string($user_id));
			$result = getDBResultsArray($dbQuery);
			header("Content-type: application/json");
			echo json_encode($result);
		}
		elseif ($exercise_id != "" && $user_id == ""){
			$dbQuery = sprintf("SELECT * FROM activites WHERE activities.exercise_id = '%s' ORDER BY activites.created_on", 
				mysql_real_escape_string($exercise_id));
			$result = getDBResultsArray($dbQuery);
			header("Content-type: application/json");
			echo json_encode($result);
		}
		elseif ($exercise_id != "" && $user_id != ""){
			$dbQuery = sprintf("SELECT * FROM activites WHERE activities.exercise_id = '%s' AND
				activites.user_id = '%s' ORDER BY activites.created_on", 
				mysql_real_escape_string($exercise_id),
				mysql_real_escape_string($user_id));
			$result = getDBResultsArray($dbQuery);
			header("Content-type: application/json");
			echo json_encode($result);
		}
		else{
			header("Content-type: text/plain");
			echo json_encode("Please use appropriate parameters for this request");
		}
	}

?>
