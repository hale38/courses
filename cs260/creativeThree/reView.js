

var app = angular.module("reView", []);

app.controller('ctrl',function($scope){
//var bob = 
$scope.showInfo =false;
$scope.addAsset=false;
$scope.showImage=true;

$scope.assetList=[{title: 'bob', description: 'bobs burgers', source:'bob.jpg', approval:'false',comments:'No comments',hide:'false'},{title: 'livingRoom', description: 'livingroom draw over', source:'roughpaintover.jpg', approval:'false',comments:'Everyone Hates you for your talent',hide:'false'},{title: 'helmet materials', description: 'current materials for viking helmets', source:'helmets.jpg', approval:'false',comments:'Need more work',hide:'false'},{title: 'shield', description: 'viking dart board sheild', source:'dartboardWIP1.jpg', approval:'false',comments:'Dope',hide:'false'}];

$scope.assetAdd = function(){

	$scope.assetList.push({title:$scope.title, asset:{title:$scope.title,description:$scope.description,source:$scope.image,approval:'false',comments:'',hide:'false'}});
	$scope.title ='';
	$scope.description='';
	$scope.image='';

};

$scope.showAsset =function (event){
var title =event.currentTarget.value;
angular.forEach($scope.assetList,function(x){
                if (String (x.title) == String(title))
                	{
                                $scope.showInfo =true; 
                		console.log('here');
                		$scope.assetTitle = 'Title: '+ x.title;
                		$scope.assetDescription='Description: '+x.description;
                		$scope.comments='Comments: ' + x.comments;

                                $scope.ShowImage=true;
                                console.log(x.source);
                                $scope.imageSource= x.source;
                                //$scope.addComment;
                                //$scope.addApproval = "<button type = 'button' class = 'approvalButton' ng-click = 'approveAsset($event)'>"
                	};
                }); 
}

$scope.newAsset = function(){

}


//$scope.lastname = "Henson";
});