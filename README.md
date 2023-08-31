### Weather Forecast App

Purpose of this app is to demonstrate how we can configure UI that is shown to the user based on our response. For this taske we generated three different responses by using https://designer.mocky.io/ service

Views that user can see on home page are: 
    1. Current weather on the user's location
    2. Hourly weather on the user's location for next 24 hours
    3. 7 days weather on the user's location 

In the mock api these views are called sections and one of the responses look like this:
```
{
    "sections": [
        {
            "id":4,
            "type":"daily_forecast",
            "background":"#111111",
            "title":"Daily1",
            "show_temperature":false,
            "show_wcode_icon":true,
            "show_wcode_label":true,
            "show_min_temp":true,
            "show_max_temp":true,
            "show_time":true
        },
        {
            "id": 2,
            "type":"hourly_forecast",
            "background":"#fbfbf0",
            "title":null,
            "show_temperature": true,
            "show_wcode_icon": true,
            "show_wcode_label": true,
            "show_min_temp": false,
            "show_max_temp": false,
            "show_time": true
        },
        {
            "id": 1,
            "type":"current_weather",
            "background":"#f0f0f0",
            "title":"TodaysWeather",
            "show_temperature":true,
            "show_wcode_icon":true,
            "show_wcode_label":true,
            "show_min_temp":false,
            "show_max_temp":false,
            "show_time":true
        },
        {
            "id":3,
            "type":"daily_forecast",
            "background":null,
            "title":"Daily2",
            "show_temperature":false,
            "show_wcode_icon":false,
            "show_wcode_label":false,
            "show_min_temp":true,
            "show_max_temp":true,
            "show_time":true
        }
    ]
}
```

This example api demonstrate how client can configure diferent views (sections) with different options on or off (show temperature, show weather icon or just temperature. In this mocked API:
 1. type - give us info what kind of a section (view) we should show to the user. Here is one enum with 3 different values: daily_forecast,  current_weather and hourly_forecast
 2. background - if we want to have specific background for some section. if not we will used the one defined in the app
 3. title - title of a section. If we don't set it field will be hidden on UI element that represents that section
 4. show_temperature - should we show temperature to the user
 5. show_temperature - if we want to shot temperature to the user in that view
 6. show_wcode_icon - should we generate icon and show for the weather code (sunny icon, thunderstorm etc)
 7. show_wcode_label - should we generate and show descriptive label for weather
 8. show_min_temp and show_max_temp - show min and max temperatures in format min / max (or just one of them)
 9. show_time - should we show time for the user for that specific weather condition (for example if type is daily we will show day to the user)

For getting weather info we are using https://open-meteo.com/ free api. Because those two are on the different services solution is to get sections first and based on that response to get weather info. After both responses are successfull items for the UI are built (DisplayItems) and they were sent to the view model. Based on the type of the display items (for this, in the soulution section type has been used) and WeatherAdapter is built. 

Ideal solution for this kind of problem is to have both responses on one API point in one larger JSON response that would look like:
```
{
	"sections": [
	    {
	        "id":4,
	        "type":"daily_forecast",
	        "data_object_id": "object_id_1",
	        ...
	    },
	    {
	        "id": 2,
            "type":"hourly_forecast",
            "data_object_id": "object_id_2",
            ...
	    },
	    {
            "id": 1,
            "type":"current_weather",
            "data_object_id": "object_id_3",
	         ...
	    },
	    {
	        "id":3,
	        "type":"daily_forecast",
	        "data_object_id": "object_id_4",
	        ...
	    }
    ],
	"data_objects": [
	    {
	        "id":"object_id_1",
	        "temperature": 12,
	        "time": "12-12-2023"
	        "weakly": { ... }
	        "daily": { ... },
	        "current": { ... },
	        "unit": "C",
	    },
	    {
	        "id":"object_id_2",
	        "temperature": 12,
	        "time": "12-12-2023"
	        "weakly": { ... }
	        "daily": { ... },
	        "current": { ... },
	        "unit": "C",
	    },
	    {
	        "id":"object_id_3",
	        "temperature": 12,
	        "time": "12-12-2023"
	        "weakly": { ... }
	        "daily": { ... },
	        "current": { ... },
	        "unit": "C",
	    },
	    {
	        "id":"object_id_4",
	        "temperature": 12,
	        "time": "12-12-2023"
	        "weakly": { ... }
	        "daily": { ... },
	        "current": { ... },
	        "unit": "C",
	    }
	]
}
```

First part sections would look nearly the same, only difference would be data_object_id that is actually id of the item from data_objects list that we would used to display data to the user. Now when we build our DisplayItems we would iterate trough list of sections and based on this id, filter only that one item from data_object and build one DisplayItem

### Locations

When you install the app, it will ask for permission to access your last known location.
If you allow it to, it will automatically show the weather at your last known location
(it might take some time for it to detect your location). If you don't allow it it wont show anything
