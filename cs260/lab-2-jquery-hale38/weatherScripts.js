$(document).ready(function() {
$( "#cityField" ).keyup(function() {
  var url = "http://bioresearch.byu.edu/cs260/jquery/getcity.cgi?q="+$("#cityField").val();
 
  $.getJSON(url,function(data) {
    var everything;
    everything = "<ul>";
    $.each(data, function(i,item) {
      everything += "<li> "+data[i].city;
    });
    everything += "</ul>";
    $("#txtHint").html(everything);
  })
  .done(function() { console.log('getJSON request succeeded!'); })
  .fail(function(jqXHR, textStatus, errorThrown) { 
    console.log('getJSON request failed! ' + textStatus); 
    console.log("incoming "+jqXHR.responseText);
  })
  .always(function() { console.log('getJSON request ended!');
  });
});
$("#weatherButton").click(function(e){
  var value = $("#cityField").val();
  $("#displayCity").val(value);
  e.preventDefault();

  var myurl= "https://api.wunderground.com/api/5868f80067d0e354/geolookup/conditions/q/UT/";
  myurl += value;
  myurl += ".json";
  console.log(myurl);

    $.ajax({
    url : myurl,
    dataType : "json",
    success : function(parsed_json) {
      var location = parsed_json['location']['city'];
      var temp_string = parsed_json['current_observation']['temperature_string'];
      var current_weather = parsed_json['current_observation']['weather'];
      everything = "<ul>";
      everything += "<li>Location: "+location;
      everything += "<li>Temperature: "+temp_string;
      everything += "<li>Weather: "+current_weather;
      everything += "</ul>";
      $("#weather").html(everything);
    }
  });
});
$("#searchButton").click(function(e)
{
  var myurl="https://api.stackexchange.com/2.2/search?order=desc&sort=activity&intitle=";
  myurl+=$("#stackField").val();
  myurl+="&site=stackoverflow"
  console.log(myurl);
  e.preventDefault();
  
  $.ajax({
    url : myurl,
    dataType : "json",
  
    success : function(parsed_json) {
      
      //alert (parsed_json)
        results ="<ul style = 'list-style:none;'>";
      $.each (parsed_json['items'], function(index)
      {
        var title = parsed_json['items'][index]['title'];
        var link = parsed_json['items'][index]['link'];
        results+="<li> <a href="+ link +">"+title+"</a>";
      });
      
    results+="</ul>";
    $("#searchResults").html(results);
 
    }
  });
});
});



