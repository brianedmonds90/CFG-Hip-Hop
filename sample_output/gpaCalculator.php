<?php
$gradeA = array(3, 3, 4, 4);
$gradeB = array(3, 3, 3, 3);
$gradeC = array(4);

function gpaCalculator($gradeA, $gradeB, $gradeC) {
$totalHour = 0;
$totalCredit = 0;
foreach ($gradeA as $x) {
$totalHour += $x;
$totalCredit += getCredit('A') * $x;
}
foreach ($gradeB as $x) {
$totalHour += $x;
$totalCredit += getCredit('B') * $x;
}
foreach ($gradeC as $x) {
$totalHour += $x;
$totalCredit += getCredit('C') * $x;
}
$gpa = $totalCredit/$totalHour;
return $gpa;
}

function getCredit($var) {
if ($var == 'A')
return 4.0;
if ($var == 'B')
return 3.0;
if ($var == 'C')
return 2.0;
else
return 0.0;
}

$v1 = gpaCalculator($gradeA, $gradeB, $gradeC);
echo $v1;
echo "\n";
?>

