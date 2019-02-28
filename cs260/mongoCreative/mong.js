angular.module('sticky', [])
.controller('MainCtrl', [ '$scope','$http',function($scope,$http){

$scope.addNote =function(){
  var div = "<div id='note' ondragstart='dragStart(event)' on-drag='onDrag($event)' on-release='onRelease($event)'>"+document.getElementById('formContent').value+"</div>"
    console.log('Hello, I am Inside' + div)
    document.getElementById("Notes").innerHTML += div;
}


$scope.onDrag=function(event)
{
   
}





}]);


/*
var onDrag =function(event)  {
        //console.log('Reporting : drag');
        console.log('dragging');
}

var onRelease = function(event)  {
console.log('done dragging');


    }
