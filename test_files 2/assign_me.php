<?php
include 'db_helper.php';
include 'simple_html_dom.php';

class Assignment{
	public $assignmentId;
	public $title;
	public $openDate;
	public $dueDate;
	public $instructions;
	public $classId;
}

class Site{
	public $siteId;
	public $siteTitle;
}

function siteJson(){
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/direct/site.json");
	$result = $_PLATFORM->secureWeb($url);
	
	/*$arr = json_decode($result['body']);
	$sitesArray = array();
	
	for($i = 0; $i < count($arr); $i++){
		$site = new Site();
		
		$site->siteId = $arr[$i]->entityId;
		$site->siteTitle = $arr[$i]->entityTitle;
		
		$sitesArray[$i] = $site;
	}*/
	
	header('Content-Type: application/json');
	echo json_encode($result);
}

function getAssignmentsByClass($classId){
	//$classIdList = json_decode($classIdList);
	//echo $classIdList;
	$allAssignments = array();//holds all class data
	
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/portal/site/".$classId);
	$result = $_PLATFORM->secureWeb($url);
	
	$html = str_get_html($result['body']);
	$selection = $html->find('li.selectedTool', 0)->plaintext;
	
	//iframe is not focused Assignments section
	if(trim($selection) != "Assignments"){
		$assignLink = $html->find('.icon-sakai-assignment-grades', 0)->href;//link to Assignments section
		//echo $assignmentLink;
		$url = "https://shepherd.cip.gatech.edu/proxy/?url=".urlencode($assignLink);
		$result = $_PLATFORM->secureWeb($url);
		$html = str_get_html($result['body']);	
	}

	$iframe = $html->find('iframe', 0)->src;
	$url = "https://shepherd.cip.gatech.edu/proxy/?url=".urlencode($iframe);
	$assignmentIframe = $_PLATFORM->secureWeb($url);	
		
	$html = str_get_html($assignmentIframe['body']);
	$numAssignments = count($html->find('tr')) - 1;
	$assignArray = array();//contains all assignment info for a single site
	//echo $numAssignments;
	
	for($i = 0; $i < $numAssignments; $i++){
		$assignment = new Assignment();//holds all information about a single assignment
		
		$assignment->classId = $classId;
	
		if($html->find('tr td[headers=title] h4 a') > 0){
			$singleAssignment = $html->find('tr td[headers=title] h4 a', $i)->href;
		
			$urlBits = parse_url($singleAssignment);
			$parts = explode('/', $urlBits['query']);//assignment id contained in $parts[4]
			
			//sometimes the link contains extra arguments that need to be separated out
			if(strstr($parts[4], '&') != false){
				$assignment->assignmentId = explode('&', $parts[4])[0];
			}
			else{
				$assignment->assignmentId = $parts[4];
			}
			$assignArray[$i] = $parts[4];
						
			$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/direct/assignment/".$assignment->assignmentId.".json");
			$assignmentsJson = $_PLATFORM->secureWeb($url);
			
			if(json_decode($assignmentsJson['body']) != null){
				$assignment->title = json_decode($assignmentsJson['body'])->content->title;
				$assignment->openDate = json_decode($assignmentsJson['body'])->openTime;
				$assignment->dueDate = json_decode($assignmentsJson['body'])->closeTime;
				$assignment->assignmentId = json_decode($assignmentsJson['body'])->content->id;
				$assignment->instructions = json_decode($assignmentsJson['body'])->content->instructions;
			}
			$assignArray[$i] = $assignment;//json_decode($assignmentsJson['body']);//json_decode($assignmentsJson['body']);//$assignment;//json_decode($assignmentsJson['body']);				
		}	
	}
	
	header('Content-Type: application/json');
	echo json_encode($assignArray);
}


/*function getAssignmentsByClass($classId){
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/direct/assignment/site/".$classId.".json");
	$result = $_PLATFORM->secureWeb($url);
	$assignments = array();
	
	if($result['body'] != null && json_decode($result['body'])->assignment_collection != null){
		$assignments = json_decode($result['body'])->assignment_collection;
	}		
	
	for($i = 0; $i < count($assignments); $i++){
		$assign = new Assignment();
		$assign->title = $assignments[$i]->content->title;
		$assign->openDate = $assignments[$i]->openTime;
		$assign->dueDate =$assignments[$i]->closeTime;
		$assign->assignmentId = $assignments[$i]->content->id;
		$assign->instructions = $assignments[$i]->content->instructions;
		$assign->classId = $classId;
		
		$assignments[$i] = $assign;
	}
	
	header('Content-Type: application/json');
	echo json_encode($assignments);//json_encode($result['body']);//json_encode($assignments);
}*/

function getDummyData(){
	echo "This is some dummy data";
}

function getUserInfo(){
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/direct/user/current.json");
	$result = $_PLATFORM->secureWeb($url);
	header('Content-Type: application/json');
	echo json_encode($result);
}

function getDatabaseSites($userId){
	$dbQuery = sprintf("SELECT * FROM `SITES` WHERE user_id == ".$userId);
    $result = getDBResultsArray($dbQuery);
    //header("Content-type: application/json");
    echo $result['body'];
}

function getDatabaseAnnouncements(){
	$dbQuery = sprintf("SELECT * FROM `ANNOUNCEMENTS`");
	$result = getDBResultsArray($dbQuery);
	header("Content-type: application/json");
    echo json_encode($result);
}

/*function getDatabaseSites(){
	$dbQuery = sprintf("SELECT * FROM `SITES`");
	$result = getDBResultsArray($dbQuery);
	header("Content-type: application/json");
    echo json_encode($result);
}*/

function getDatabaseAssignmentsByClass($classId) {
	$dbQuery = sprintf("SELECT `raw_json` FROM `ASSIGNMENTS-TEST` WHERE `class_id`==\"" + $classId + "\"");
	$result = getDBResultsArray($dbQuery);
	header("Content-type: application/json");
    echo json_encode($result);
}

function insertSites($postData){

	$postData = json_decode($postData);
	for($i = 0; $i < $postData; $i++){
		$dbQuery = sprintf("INSERT INTO `SITES` (`site_id`, `user_id`, `site_title`, `active`) VALUES ('" + $postData[$i]->site_id + "', '" + $postData[$i]->user_id + "', '" + $postData[$i]->site_title + "', '" + $postData[$i]->active + "')");
		$result = getDBResultsArray($dbQuery);
		//header("Content-type: application/json");
		//echo json_encode($result);
	}	
}

function getGradebookIframe($classId){
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/portal/site/".$classId);
	$result = $_PLATFORM->secureWeb($url);
	
	$html = str_get_html($result['body']);
	
	$gradesPageUrl = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".$html->find('.icon-sakai-gradebook-tool ', 0)->href;
	$result = $_PLATFORM->secureWeb($gradesPageUrl);
	
	$html = str_get_html($result['body']);
	
	$iframe = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".$html->find('iframe', 0)->src;
	$result = $_PLATFORM->secureWeb($iframe);
	
	echo $result['body'];
}

function getAnnouncements($days) {
    $response = t2Call('site.json?days=' . $days);
    $result = json_decode($response['body'], true);
    $sites_raw = $result['site_collection'];
    $sites = array();
    foreach ($sites_raw as $site) {
        if (!strcmp($site['props']['term'], "FALL 2013")) {
            $data = array(
                'title' => $site['title'],
                'id' => $site['id'],
                'announcements' => array()
            );
            $resp = t2Call('announcement/site/' . $site['id'] . '.json');
            $res = json_decode($resp['body'], true);
            $ann_raw = $res['announcement_collection'];
            $anns = array();
            foreach ($ann_raw as $ann) {
                $anns[] = array(
                    'title' => $ann['title'],
                    'createdOn' => $ann['createdOn'],
                    'body' => $ann['body']
                );
            }
            $data['announcements'] = $anns;
            $sites[] = $data;
        }
    }
    header('Content-Type: application/json');
    return $sites;
}

function getAnnouncementsByClass($classId) {
	global $_PLATFORM;
	$url = "https://shepherd.cip.gatech.edu/proxy/?separator=+&urls=".urlencode("https://pinch1.lms.gatech.edu/sakai-login-tool/container")."+".urlencode("https://pinch1.lms.gatech.edu/direct/announcement/site/".$classId."/site.json?n=10&d=100");
	$result = $_PLATFORM->secureWeb($url);
	header('Content-Type: application/json');
	echo $result['body'];
	foo($result);
}

function foo($param){
	echo "BRIAN IS A BOSS";
	echo $param;
}

?>
