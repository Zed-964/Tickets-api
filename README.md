# Tickets API
Tickets API est une application Spring Boot destinée à gérer les réservations de billets pour des événements. 
Elle offre une interface RESTful pour interagir avec les tickets de l'utilisateur qui l'appelle. 
Elle permet aussi le management des offres d'un événement.

## Fonctionnalités principales
Gestion des offres : Ajout, consultation, modification et suppression d'offres.

Gestion des tickets : Réservation, et consultation de tickets pour des événements.

## Technologies utilisées au sein du projet
- Spring Data JPA : Framework de mapping objet-relationnel pour la gestion des données.
- Spring Boot : Framework Java pour la création d'applications Web.
- Spring Security : Framework de sécurité pour la gestion de l'authentification et des autorisations.
- PostgreSQL : Système de gestion de base de données relationnelle.
- Swagger : Outil de documentation et d'exploration des API.
- React : Framework du front utilisé.
- Keycloak : Solution open source de gestion des identités et des accès.

## Configuration requise
- Java 21
- Gradle
- Docker
- Keycloak (pour la gestion de l'authentification)

Installation et démarrage
Cloner le dépôt GitHub :

Copier le code
git clone https://github.com/Zed-964/Tickets-api.git
Importer le projet dans votre IDE préféré (IntelliJ IDEA, Eclipse, etc.).

Configurer la base de données PostgreSQL en modifiant les paramètres dans le fichier application.properties. 

Démarrer un Keycloak et configurer les clients, les utilisateurs et les rôles selon les besoins du projet.
Modifier les variables : RESOURCE_ID dans la classe GenericConstants ainsi que URI_KEYCLOAK dans le fichier application.properties.
Le role dans les @PreAuthorize des endpoints des offres est également à modifier pour accéder aux endpoints.
> [!WARNING]
> La configuration keycloak ne sera fournis par mesure de sécurité, ainsi que très complexe et lourdes à mettre en place surtout dans ce cas avec un proxy de configurer.
> Je vous renvoie sur le site officiel de keycloak pour le lancer en mode développement. Url : https://www.keycloak.org/getting-started/getting-started-docker

> [!WARNING]
> L'api nécessite un token JWT pour fonctionner, elle vérifie la validité du JWT auprès de keycloak donc il faudra forcément mettre en place en keycloak et le configurer et l'appeler pour générer des tokens valides et ainsi utiliser l'api

Lancer l'API avec la configuration Spring boot application ou via le fichier docker compose après avoir builder le projet 
> [!WARNING]
> Les tests unitaires des controllers peuvent être en erreur lors du build si l'url de keycloak n'est pas lancé sur le même port que celui configuré dans la méthode : getTokenKeycloak() dans la classe TestUtils

## Compiler et exécuter l'application :
En utilisant Gradle, faire un clean puis un build
java -jar build/libs/tickets-api-1.0.0.jar
Accéder à l'interface Swagger pour explorer et tester l'API :
http://localhost:8080/api/v1/swagger-ui/index.html


## Documentation
Documentation de l'API : Détails sur les endpoints et les opérations disponibles.

### Authentification sur le site

L'inscription et l'authentification auprès de Keycloak suivent le même schéma, la seule spécificité est que le formulaire est différent :
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Keycloak
    participant Bdd_kc
    
Client ->> Front: Accède à la page d'authentification
Front ->> Proxy: Accède à la page d'authentification
Proxy --> Client: GET /realms/jo-tickets-distribution/protocol/openid-connect/auth?client_id=tickets-front&redirect_uri=https%3A%2F%2Fenzolouail.fr%2Flogin%2Fresult&response_type=code&scope=openid&state=${state}
Client ->> Keycloak: Rediriger sur le formulaire de connexion de keycloak
loop Tant que les identifiants sont non valides
    Client ->> Keycloak: Rentrer ses identifiants de connexion puis les valide
    Keycloak ->> Bdd_kc: Est ce que cet utilisateur existe
    alt Utilisateur existe pas
        Keycloak -->> Client: MSG erreur: Identifiant invalide
    else 
        Keycloak -->> Proxy: Redirection sur le site avec un code pour générer un token JWT de l'utilisateur
        Proxy -->> Client: Redirection sur le front
    end
end
Front ->> Proxy: Génère moi un token et un refresh token pour l'utilisateur qui vient de se connecter
Proxy ->> Keycloak: POST /realms/jo-tickets-distribution/protocol/openid-connect/token <br/>-Body: <br/>grant_type=authorization_code <br/>client_id={Client keycloak} <br/>code={code que fournit keycloak plus haut dans la cinématique} <br/>redirect_uri=https://enzolouail.fr/login/result
Keycloak -->> Proxy: Token JWT + refresh token
Proxy --> Front: Token JWT + refresh token
Front --> CLient: MSG: Authentification réussie
```
Un appel à keycloak pour obtenir un nouveau token est éffectué toutes les 4 minutes sur le front
POST /realms/jo-tickets-distribution/protocol/openid-connect/token <br/>-Body: <br/>grant_type=refresh_token <br/>client_id={Client keycloak} <br/>refreshtoekn={Refresh token de keycloak}


### GET Offers : 
Endpoint : /api/vi/offers

Consulter la liste des offres, ouvert à tous les clients du site, ne nécessite pas de token pour y avoir accès.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Bdd_TK
Client ->> Front: Donne moi la liste des offres    
Front ->> Proxy: Get offers 
Proxy ->> Back: GET /api/v1/offers
Back ->> Bdd_TK: SELECT * FROM {table offre}
Bdd_TK -->> Back: Liste d'offre
Back -->> Proxy: Code 200 : Liste d'offre
Proxy -->> Front: Code 200 : Liste d'offre
Front -->> Client: Affichage de la liste d'offre 
```

### POST Offer : Création d'une offre
Endpoint : /api/vi/offers

Création d'une nouvelle offre par un administrateur, pour qu'elle remonte dans la liste des offres disponibles au client.

La cinématique débute quand l'utilisateur est déja connecter avec un compte administrateur et se situe sur la page de ses informations de compte et clique sur le bouton pour accéder à la page d'administration.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Keycloak
    participant Bdd_TK
    
Client ->> Front: Affiche moi la page administrateur
Front -->> Client: Page d'administration des offres
Client ->> Front: Ajoute moi une nouvelle offre avec ces infos :<br/> {Nom de l'offre} {description } {Nombre de ticket} {Prix}
Front ->> Proxy: Post offer <br/>-Body: {OfferSimple} <br/>-Headers: Authorization Bearer {JWT}
Proxy ->> Back: POST /api/v1/offers <br/>-Body: {OfferSimple} <br/>-Headers: Authorization Bearer {JWT}
Back ->> Keycloak: GET /realms/jo-tickets-distribution/protocol/openid-connect/certs <br/>-Headers: Authorization Bearer {JWT}
alt Verification Token JWT pas valide
    Keycloak -->> Back: KO c'est pas valide
    Back -->> Proxy: code 401
    Proxy -->> Front: code 401
    Front -->> Client: MSG erreur: pas authentifié
else JWT valide
    Keycloak -->> Back: OK c'est valide
    Back ->> Back: Vérification du role {role} présent dans le JWT
    alt Role de l'admnistrateur non présent
        Back -->> Proxy: code 403
        Proxy -->> Front: code 403
        Front -->> Client: MSG erreur: Pas les droits nécessaires
    else Role présent
        Back ->> Bdd_TK: INSERT INTO {table offer} VALUES {Offer}
        Bdd_TK -->> Back: Insertion réussi
        Back ->> Bdd_TK: SELECT * FROM {table offre} WHERE uuid = {Offer uuid}
        Bdd_TK -->> Back: Offer
        Back -->> Proxy: Code 201: Offer
        Proxy -->> Front: Code 201: Offer
        Front -->> Client: MSG offre créer ! 
        Front -->> Client: Page d'administration des offres à jour avec la nouvelle offre.
    end
end
```

### PUT Offer : Mise à jour d'une offre
Endpoint : /api/vi/offers/{offerUuid}

Mise à jour d'une offre par un administrateur, pour qu'elle remonte modifiée dans la liste des offres disponibles au client.

La cinématique débute quand l'utilisateur est déja connecter avec un compte administrateur et se situe sur la page de ses informations de compte et clique sur le bouton pour accéder à la page d'administration.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Keycloak
    participant Bdd_TK

Client ->> Front: Affiche moi la page administrateur
Front -->> Client: Page d'administration des offres
Client ->> Front: Edite moi cette offre avec ces infos :<br/> {Uuid} {Nom de l'offre} {description } {Nombre de ticket} {Prix}
Front ->> Proxy: Put offer <br/>-Body: {Offer} <br/>-Headers: Authorization Bearer {JWT}
Proxy ->> Back: PUT /api/v1/offers/{offerUuid} <br/>-Body: {Offer} <br/>-Headers: Authorization Bearer {JWT}
Back ->> Keycloak: GET /realms/jo-tickets-distribution/protocol/openid-connect/certs <br/>-Headers: Authorization Bearer {JWT}
alt Verification Token JWT pas valide
    Keycloak -->> Back: KO c'est pas valide
    Back -->> Proxy: code 401
    Proxy -->> Front: code 401
    Front -->> Client: MSG erreur: pas authentifié
else JWT valide
    Keycloak -->> Back: OK c'est valide
    Back ->> Back: Vérification du role {role} présent dans le JWT
    alt Role de l'admnistrateur non présent
        Back -->> Proxy: code 403
        Proxy -->> Front: code 403
        Front -->> Client: MSG erreur: Pas les droits nécessaires
    else Role présent
        Back ->> Bdd_TK: UPDATE {table offer} VALUES {Offer} WHERE uuid = {Offer uuid}
        Bdd_TK -->> Back: Update réussi
        Back ->> Bdd_TK: SELECT * FROM {table offre} WHERE uuid = {Offer uuid}
        Bdd_TK -->> Back: Offer
        Back -->> Proxy: Code 200: Offer
        Proxy -->> Front: Code 200: Offer
        Front -->> Client: MSG offre mis à jour !
        Front -->> Client: Page d'administration des offres à jour avec l'offre modifié.
    end
end
```

### DELETE Offer : Suppression d'une offre
Endpoint : /api/vi/offers/{offerUuid}

Suppression d'une offre par un administrateur, pour qu'elle ne remonte plus dans la liste des offres disponibles au client.

La cinématique débute quand l'utilisateur est déja connecter avec un compte administrateur et se situe sur la page de ses informations de compte et clique sur le bouton pour accéder à la page d'administration.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Keycloak
    participant Bdd_TK

Client ->> Front: Affiche moi la page administrateur
Front -->> Client: Page d'administration des offres
Client ->> Front: Supprime moi cette offre
Front ->> Proxy: DELETE offer <br/>-Headers: Authorization Bearer {JWT}
Proxy ->> Back: DELETE /api/v1/offers/{offerUuid} <br/>-Headers: Authorization Bearer {JWT}
Back ->> Keycloak: GET /realms/jo-tickets-distribution/protocol/openid-connect/certs <br/>-Headers: Authorization Bearer {JWT}
alt Verification Token JWT pas valide
    Keycloak -->> Back: KO c'est pas valide
    Back -->> Proxy: code 401
    Proxy -->> Front: code 401
    Front -->> Client: MSG erreur: pas authentifié
else JWT valide
    Keycloak -->> Back: OK c'est valide
    Back ->> Back: Vérification du role {role} présent dans le JWT
    alt Role de l'admnistrateur non présent
        Back -->> Proxy: code 403
        Proxy -->> Front: code 403
        Front -->> Client: MSG erreur: Pas les droits nécessaires
    else Role présent
        Back ->> Bdd_TK: DELETE {table offer} VALUES {Offer} WHERE uuid = {Offer uuid}
        Bdd_TK -->> Back: Delete réussi
        Back -->> Proxy: Code 204
        Proxy -->> Front: Code 204
        Front -->> Client: MSG offre supprimé !
        Front -->> Client: Page d'administration des offres à jour avec l'offre supprimé en moins.
    end
end
```

### POST Ticket : Réservation et payement d'un ticket
Endpoint : /api/vi/tickets/payment

Réservation de ticket par un utilisateur, pour générer son QR code spécifique pour chaque ticket.

La cinématique débute quand le client est déja connecter avec un compte utilisateur et se situe sur la page des offres et clique sur le bouton de réserver d'une offre.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Keycloak
    participant Mock_Stripe
    participant Bdd_TK

Client ->> Front: Clique sur réserver une offre
Front ->> Front: Ajoute une offre à son panier
Client ->> Front: Clique pour accéder à son panier
Front -->> Client: Redirige sur le panier de l'utilisateur
Client ->> Front: Remplies les informations des différents tickets réservés et valide
Front ->> Front: Vérification que tous les ticket sont bien remplis 

Front ->> Proxy: POST tickets <br/>-Headers: Authorization Bearer {JWT}
Proxy ->> Back: POST /api/v1/tickets/payment <br/>-Headers: Authorization Bearer {JWT}
Back ->> Keycloak: GET /realms/jo-tickets-distribution/protocol/openid-connect/certs <br/>-Headers: Authorization Bearer {JWT}
alt Verification Token JWT pas valide
    Keycloak -->> Back: KO c'est pas valide
    Back -->> Proxy: code 401
    Proxy -->> Front: code 401
    Front -->> Client: MSG erreur: pas authentifié
else JWT valide
    Keycloak -->> Back: OK c'est valide
    Back ->> Bdd_TK: SELECT * FROM {table user} WHERE uuid = {uuid de l'utilisateur connecté}
    alt Si l'utilisateur n'existe pas 
        Back ->> Bdd_TK: INSERT INTO {table user} VALUES {uuid et le mail de l'utilisateur (qu'on trouve dans le token)}
        Bdd_TK -->> Back: Insertion OK
    end
    Back ->> Bdd_TK: INSERT INTO {table ticket} VALUES {Ticket + uuid de l'utilisateur connecté}
    Bdd_TK -->> Back: Insertion OK
    Back ->> Mock_Stripe: Mock simulant un payement (renvoie toujours ok dans notre cas)
    alt Si le payement invalide 
        Back ->> Bdd_TK: DELETE {table ticket} WHERE uuid = {Uuid ticket}
        Bdd_TK -->> Back: Suppression OK
        Back -->> Proxy: Code 400: Payment invalid
        Proxy -->> Front: Code 400: Payement invalid
        Front -->> Client: MSG erreur: Le payement n'à pas pus aboutir
    end
    loop Pour chaque ticket qui viennent d'être géneré
        Back ->> Bdd_TK: SELECT * FROM {table ticket} WHERE uuid = {Uuid ticket} 
        Bdd_TK -->> Back: Ticket 
    end
    Back -->> Proxy: Code 201: List Ticket
    Proxy -->> Front: Code 201: List Ticket
    Front -->> Client: MSG: Payement bien éffectuer
    Front -->> Client: Redirection sur la partie des tickets de l'utilisateur
end
```

### GET Tickets : Consultation des tickets de l'utilisateur connecté 
Endpoint : /api/vi/tickets/me

Consultation des tickets de l'utilisateur connecté, les tickets auront chacun leur QR code spécifique.

La cinématique débute quand le client est déja connecter avec un compte utilisateur et se situe sur la page d'accueil du site.
```mermaid
sequenceDiagram
    actor Client
    participant Front
    participant Proxy
    participant Back
    participant Keycloak
    participant Bdd_TK

Client ->> Front: Clique sur le bouton de ses tickets
Front -->> Client: Page de consultation des ses tickets
Front ->> Proxy: GET my tickets <br/>-Headers: Authorization Bearer {JWT}
Proxy ->> Back: GET /api/v1/tickets/me <br/>-Headers: Authorization Bearer {JWT}
Back ->> Keycloak: GET /realms/jo-tickets-distribution/protocol/openid-connect/certs <br/>-Headers: Authorization Bearer {JWT}
alt Verification Token JWT pas valide
    Keycloak -->> Back: KO c'est pas valide
    Back -->> Proxy: code 401
    Proxy -->> Front: code 401
    Front -->> Client: MSG erreur: pas authentifié
else JWT valide
    Keycloak -->> Back: OK c'est valide
    Back ->> Bdd_TK: SELECT * FROM {table ticket} WHERE uuidUser = {Uuid de l'utilisateur connecté}
    Bdd_TK -->> Back: List ticket
    Back -->> Proxy: Code 200: List ticket
    Proxy -->> Front: Code 200: List ticket
    Front ->> Front: Génère les QR code pour chaque ticket
    Front -->> Client: List des tickets avec leur QR code associé
end
```