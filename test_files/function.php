<?php

function foo($c,$d){
	$y=6;
	$z = 1;
	while($c<=5)
  {

  		$x = $y+1;
  		$y = 2*$z;
  		echo "The number is: $c <br>";
  		if($d){
  		 $x = $y+$z;
  		}
  		$z = 1;
  		$c++;
  } 	
}

foo(0,3);
foo(1,1);
?>
