# Android Test

# Sujet

---

Créer une petite application qui affiche sur l’écran l’adresse de la position actuelle.

# Tâches

---

- Afficher au centre de l’écran 2 lignes dans la **1ère→ position actuelle** et dans la **2eme → l’adresse actuelle**
- Observer la position avec un intervalle d’une seconde
- Faire une reverse geocoding de la position actuelle toutes les 10 secondes avec [geokeo.com](http://geokeo.com/)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/33ec26f8-c45d-469d-bb5d-e53d272461ef/Untitled.png)

# Exigence fontionnelle

---

- Il ne faut pas faire le reverse-geocoding si la distance de la position actuelle ne dépasse pas 10m par rapport à la position précédente.

# Exigence technique

---

- Utiliser RxJava
- Utiliser Koin
- Utiliser Kotlin

# Évaluation

---

L’évaluation sera sur la base de:

- L’architecture de l’application (la séparation des différentes couches du projet...)
- L’utilisation du Rxjava
- Le respect des principes SOLID

# Les plus

---

L’utilisation de Jetpack Compose sera un plus.

# API Geokeo

*Attention: L’api est limité à 2500 requête par jour*

Clé api: 9da1fbafc8827213e41262a09afc3427

Endpoint: [https://geokeo.com/geocode/v1/reverse.php?lat=40.7484283080311&lng=-73.9856483067424&api=9da1fbafc8827213e41262a09afc3427](https://geokeo.com/geocode/v1/reverse.php?lat=40.7484283080311&lng=-73.9856483067424&api=9da1fbafc8827213e41262a09afc3427)

Réponse:

```json
{
  "results": [
    {
      "class": "office",
      "type": "yes",
      "address_components": {
        "name": "Empire State Building",
        "island": "Manhattan Island",
        "street": "5th Avenue",
        "neighbourhood": "Midtown South",
        "subdistrict": "Manhattan",
        "district": "New York County",
        "city": "New York City",
        "state": "New York",
        "postcode": "10018",
        "country": "United States Of America"
      },
      "formatted_address": "Empire State Building,Manhattan Island,5th Avenue,Midtown South,New York County,New York City,10018,United States Of America",
      "geometry": {
        "location": {
          "lat": "40.74843124430164",
          "lng": "-73.9856567114413"
        },
        "viewport": {
          "northeast": {
            "lat": "40.747922600363026",
            "lng": "-73.9864855"
          },
          "southwest": {
            "lat": "40.74894220036315",
            "lng": "-73.98482589999999"
          }
        }
      },
      "osmurl": "https://www.openstreetmap.org/search?query=40.74843124430164%2C-73.9856567114413#map=17/40.74843124430164/-73.9856567114413",
      "distance": "0.0010302984507433km"
    }
  ],
  "credits": "https://geokeo.com/credits.php",
  "status": "ok"
}
```

# Code Submit

Le code sera envoyé sur une repo, il est préférable que l’historique de git soit détaillé avec des commits qui decrive les étapes que vous avez faite pour faire le projet

L’invitation pour accéder au repo distant: [https://classroom.github.com/a/3Tekiv6T](https://classroom.github.com/a/3Tekiv6T)