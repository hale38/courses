/**
 * 1. We have added a directive with the name 'avatar' and handler of
 * avatarDirective to our angular app module
 */
angular.module('app', [])
  .controller('mainCtrl', mainCtrl)
  .directive('avatar', avatarDirective);

function mainCtrl ($scope) {

  $scope.users = [];

  $scope.addNew = function (user) {
    $scope.users.push({ 
      name: user.name,
      avatarUrl: user.url,
      email: user.email
    }); /* [1] */
    
    user.name = ''; /* [2] */
    user.url = '';
    user.email='';
  };
}

/**
 * 1. this defines the api of our avatar directive. This means we are
 * expecting a user property whose value should be interpreted as an object.
 * 2. This simply means we want this directive to be used as an element.
 * 3. You can see here we've moved the html that was in our template before
 * and give it as the template for this directive. This means wherever we use
 * <avatar /> this html will also be placed there.
 * 4. Here we are implementing the feature where if there is no user avatar url,
 * we go ahead and give it a default
 */
function avatarDirective () {
  return {
    scope: {
      user: '=' /* [1] */
    },
    restrict: 'E', /* [2] */
    replace: 'true',
    template: (
      '<div class="Avatar">' +
        '<img ng-src="{{user.avatarUrl}}" />' +
        '<h4>{{user.name}}</h4>' +
        '<h4>{{user.email}}' +
      '</div>'
    ), /* [3] */
    link: link
  };
  
  function link (scope) { /* [4] */
    if (!scope.user.avatarUrl) {
      scope.user.avatarUrl = 'https://www.drupal.org/files/issues/default-avatar.png';
    }
  }

}