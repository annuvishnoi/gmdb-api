# GMDB REST API
## API Specifications
**Resource Summary**
| URI                                                | HTTP Method |   HTTP Status   | Description                                           |
|----------------------------------------------------|-------------|-----------------|-------------------------------------------------------|
| gmdbapp.com/api/movies             | GET         |   200 OK		 | Return the list of all movies   |
| gmdbapp.com/api/movies             | GET         |   204 NO_CONTENT		 | No body in the response   |
| gmdbapp.com/api/movies/{title}             | GET         |   200 OK		 | Return detail of movie with required title   |
| gmdbapp.com/api/movies/{title}             | GET         |   404 NOT_FOUND		 | Return error message when movie is not found |
| gmdbapp.com/api/movies/{title}             | PUT         |   200 OK		 | Return the movie detail with rating |

| herobook.com/api/heroes/{heroName}?role={roleName}   | GET         |   200 OK        | Return the details for that hero                    |
| herobook.com/api/heroes/{heroName}?role={roleName}    | GET         |   400 BAD REQUEST  | Returns error message when hero is not found         |
| herobook.com/api/villains?role={roleName}               | GET         |   200 OK		 | Return the list of name of villains when role is visitor   |
| herobook.com/api/villains/{villainName}?role={roleName} | GET         |   200 OK   | Return the details for that villains                   |
| herobook.com/api/villains/{villainName}?role={roleName} | GET       |   400 BAD REQUEST | Returns error message when villains is not found   |
| herobook.com/api/favorites?role={roleName} | POST       |   201 CREATED | Creates an empty favorites list   |
| herobook.com/api/favorites/{favoritesId}/heroes/{heroName}?role={roleName} | PUT       |   200 OK | Returns updated favorites list  |
| herobook.com/api/favorites/{favoritesId}?role={roleName} | GET      |   200 OK | Returns requested favorites list  |
| herobook.com/api/favorites/{favoritesId}/heroes/{heroName}?role={roleName} | DELETE       |   200 OK | Returns updated favorites list  |

---
**GET /api/movies**

Response Body:
```json
{
	"data": [
  {
    "title": "The Avengers",
    "director": "Joss Whedon",
    "actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth",
    "release": "2012",
    "description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.",
    "rating": {
	  "star":4,
	  "review": "awesome movie. | It's terrible! | I love it."
	}
  },
  {
    "title": "Superman Returns",
    "director": "Bryan Singer",
    "actors": "Brandon Routh, Kate Bosworth, Kevin Spacey, James Marsden",
    "release": "2006",
    "description": "Superman returns to Earth after spending five years in space examining his homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world.",
    "rating": null
  },
  {
    "title": "Steel",
    "director": "Kenneth Johnson",
    "actors": "Shaquille O'Neal, Annabeth Gish, Judd Nelson, Richard Roundtree",
    "release": "1997",
    "description": "A scientist for the military turns himself into a cartoon-like superhero when a version of one of his own weapons is being used against enemies.",
    "rating": null
  },
  {
    "title": "Unbreakable",
    "director": "M. Night Shyamalan",
    "actors": "Bruce Willis, Samuel L. Jackson, Robin Wright, Spencer Treat Clark",
    "release": "2000",
    "description": "A man learns something extraordinary about himself after a devastating accident.",
    "rating": null
  },
  {
    "title": "Rocketeer",
    "director": "Jay Light",
    "actors": "Christopher Coakley",
    "release": "2012",
    "description": "N/A",
    "rating": null
  },
  {
    "title": "The Incredibles",
    "director": "Brad Bird",
    "actors": "Craig T. Nelson, Holly Hunter, Samuel L. Jackson, Jason Lee",
    "release": "2004",
    "description": "A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
    "rating": null
  },
  {
    "title": "The Lego Batman Movie",
    "director": "Chris McKay",
    "actors": "Will Arnett, Michael Cera, Rosario Dawson, Ralph Fiennes",
    "release": "2017",
    "description": "A cooler-than-ever Bruce Wayne must deal with the usual suspects as they plan to rule Gotham City, while discovering that he has accidentally adopted a teenage orphan who wishes to become his sidekick.",
    "rating": null
  }
]
}
```

---

**GET /api/heroes/{heroName}**

Response Body:
```json
{
	"data":{
		"image" : "string",
		"realName" : "string",
		"heroName" : "string",
		"height" : "string",
		"weight" : "string",
		"specialPower" : "string",
		"intelligence" : "string",
		"strength" : "string",
		"power" : "string",
		"speed" : "string",
		"agility" : "string",
		"description" : "string",
		"story" : "string"
	},
	"errorMessages":[
		"Hero not found",
		"errors"
	]	
}
```

---

**GET /api/villains?role={roleName}**

Response Body:
```json
{
	"data":["Green Goblin", "Kilvish", "Thanos"],
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```

---

**GET /api/villains/{villainName}**

Response Body:
```json
{
	"data":{
		"image" : "string",
		"realName" : "string",
		"villainName" : "string",
		"archRival" : "string",
		"height" : "string",
		"weight" : "string",
		"specialPower" : "string",
		"intelligence" : "string",
		"strength" : "string",
		"power" : "string",
		"speed" : "string",
		"agility" : "string",
		"description" : "string",
		"story" : "string"
	},
	"errorMessages":[
		"Villain not found",
		"errors"
	]	
}
```
#### To Be Implemented

---

**POST /api/favorites?role={roleName}**

Validation: 
 - Roles allowed to create favorites 
   - Fan

Response Body:
```json
{
	"data":{
		"favoritesId":"id",
		"favorites" :[]
	},
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```

---

**PUT /api/favorites/{favoritesId}/heroes/{heroName}?role={roleName}**

Validation: 
 - Roles allowed to create favorites 
   - Fan

Response Body:
```json
{
	"data":{
		"favoritesId":"id",
		"favorites" :["heroName"]
	},
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```

---

**GET /api/favorites/{favoritesId}?role={roleName}**

Validation: 
 - Roles allowed to create favorites 
   - Fan

Response Body:
```json
{
	"data":{
		"favoritesId":"id",
		"favorites" :["Superman","Batman"]
	},
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```

---

**DELETE /api/favorites/{favoritesId}/heroes/{heroName}?role={roleName}**

Validation: 
 - Roles allowed to create favorites 
   - Fan

Response Body:
```json
{
	"data":{
		"favoritesId":"id",
		"favorites" :["Batman"]
	},
	"errorMessages":[
		"errors",
		"errors"
	]		
}
```