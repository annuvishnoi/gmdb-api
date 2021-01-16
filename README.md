# GMDB REST API
## API Specifications
**Resource Summary**
| URI                                                | HTTP Method |   HTTP Status   | Description                                           |
|----------------------------------------------------|-------------|-----------------|-------------------------------------------------------|
| gmdbapp.com/api/movies             | GET         |   200 OK		 | Return the list of all movies   |
| gmdbapp.com/api/movies             | GET         |   204 NO_CONTENT		 | No response body returned   |
| gmdbapp.com/api/movies/{title}             | GET         |   200 OK		 | Return detail of movie with required title   |
| gmdbapp.com/api/movies/{title}             | GET         |   404 NOT_FOUND		 | Return error message when movie is not found |
| gmdbapp.com/api/movies/{title}             | PUT         |   200 OK		 | Return the movie detail with rating |
| gmdbapp.com/api/movies/{title}             | PUT         |   400 BAD_REQUEST		 | Return error message when star is not submitted as part of review |


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
                "rating": [],
                "averageRating": null
             },
             {
                "title": "Superman Returns",
                "director": "Bryan Singer",
                "actors": "Brandon Routh, Kate Bosworth, Kevin Spacey, James Marsden",
                "release": "2006",
                "description": "Superman returns to Earth after spending five years in space examining his homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world.",
                "rating": [],
                "averageRating": null
             },
             {
                "title": "Steel",
                "director": "Kenneth Johnson",
                "actors": "Shaquille O'Neal, Annabeth Gish, Judd Nelson, Richard Roundtree",
                "release": "1997",
                "description": "A scientist for the military turns himself into a cartoon-like superhero when a version of one of his own weapons is being used against enemies.",
                "rating": [],
                "averageRating": null
             },
             {
                "title": "Unbreakable",
                "director": "M. Night Shyamalan",
                "actors": "Bruce Willis, Samuel L. Jackson, Robin Wright, Spencer Treat Clark",
                "release": "2000",
                "description": "A man learns something extraordinary about himself after a devastating accident.",
                "rating": [],
                "averageRating": null
             },
             {
                "title": "Rocketeer",
                "director": "Jay Light",
                "actors": "Christopher Coakley",
                "release": "2012",
                "description": "N/A",
                "rating": [],
                "averageRating": null
             },
             {
                "title": "The Incredibles",
                "director": "Brad Bird",
                "actors": "Craig T. Nelson, Holly Hunter, Samuel L. Jackson, Jason Lee",
                "release": "2004",
                "description": "A family of undercover superheroes, while trying to live the quiet suburban life, are forced into action to save the world.",
                "rating": [],
                "averageRating": null
             },
             {
                "title": "The Lego Batman Movie",
                "director": "Chris McKay",
                "actors": "Will Arnett, Michael Cera, Rosario Dawson, Ralph Fiennes",
                "release": "2017",
                "description": "A cooler-than-ever Bruce Wayne must deal with the usual suspects as they plan to rule Gotham City, while discovering that he has accidentally adopted a teenage orphan who wishes to become his sidekick.",
                "rating": [],
                "averageRating": null
             }
    ]
  
}
```

---

**GET /api/movies/{title}**

Response Body of 200:
```json
{
	"data": {
       "title": "The Lego Batman Movie",
       "director": "Chris McKay",
       "actors": "Will Arnett, Michael Cera, Rosario Dawson, Ralph Fiennes",
       "release": "2017",
       "description": "A cooler-than-ever Bruce Wayne must deal with the usual suspects as they plan to rule Gotham City, while discovering that he has accidentally adopted a teenage orphan who wishes to become his sidekick.",
       "rating": [
          {  "star": 3,
             "reviewDetail": "alright movie" 
             
          },
          {  "star": 5,
             "reviewDetail": "great"

          }
       ],
       "averageRating": 4.0
    }
}
```
Response Body of 404:

```json
{
   "errorMsg": "Superman Again doesn't exist"
}
```
---

**PUT /api/movies/{title}**

Request Body:

```json
{
   "star": 5,
   "reviewDetail": "some comment"
}

```


Response Body:
```json
{
   "data": {
      "title": "The Lego Batman Movie",
      "director": "Chris McKay",
      "actors": "Will Arnett, Michael Cera, Rosario Dawson, Ralph Fiennes",
      "release": "2017",
      "description": "A cooler-than-ever Bruce Wayne must deal with the usual suspects as they plan to rule Gotham City, while discovering that he has accidentally adopted a teenage orphan who wishes to become his sidekick.",
      "rating": [
         {  "star": 3,
            "reviewDetail": "alright movie"

         },
         {  "star": 5,
            "reviewDetail": "great"

         }
      ],
      "averageRating": 4.0
   }
}
```

Response Body of 400:

```json
{
   "errorMsg": "Please enter star for your rating."
}
```

---
