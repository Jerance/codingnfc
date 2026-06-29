# CodingNFC

CodingNFC est une application Android développée en Kotlin qui permet de prendre une photo, d’analyser l’image via l’API Azure Computer Vision et d’afficher les objets détectés dans une liste.

## Fonctionnalités

- Prise de photo depuis l’application
- Analyse d’image pour détecter des objets
- Affichage des résultats dans une liste
- Stockage local des données avec PaperDB
- Navigation entre l’écran d’accueil et la liste des éléments

## Stack technique

- Kotlin
- Android SDK / AndroidX
- ViewModel + LiveData
- Retrofit + Gson
- Material Design
- Camera API avec FileProvider

## Structure du projet

- app/src/main/java/com/example/codingnfc : code principal de l’application
- app/src/main/java/com/example/codingnfc/features : écrans et vues (accueil, liste, détail)
- app/src/main/java/com/example/codingnfc/api : clients et interfaces Retrofit
- app/src/main/java/com/example/codingnfc/localstorage : stockage local
- app/src/main/res : ressources Android (layouts, images, chaînes, thèmes)

## Prérequis

- Android Studio
- JDK 8 ou supérieur
- SDK Android avec compileSdk 34
- Un émulateur Android ou un appareil physique

## Installation

1. Cloner ce dépôt
2. Ouvrir le projet dans Android Studio
3. Synchroniser Gradle
4. Lancer l’application sur un émulateur ou un appareil

## Exécution

Depuis la racine du projet :

```bash
./gradlew assembleDebug
```

Puis lancer l’application depuis Android Studio.

## Notes importantes

- L’application utilise une API de vision par ordinateur pour l’analyse des images.
- La clé d’abonnement Azure est actuellement définie dans le code. Pour un usage professionnel, il est recommandé de la déplacer dans une configuration sécurisée (variables d’environnement ou fichier local non versionné).
# app-mobile-android-scan
