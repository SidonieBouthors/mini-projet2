Mini Projet 2

François Théron et Sidonie Bouthors

ELEMENTS AJOUTES:

Class RPG SPRITE :
-
- Modification dans Sprites[][] extractSprite, ajustement pour que les images soient extraites de gauche a droite.
  car initialement c'etait de haut en bas.

- Ajout de méthode Sprites [] extractspritesWithoutAnim + un parametre lignToExtract qui permet d'extrait la ligne que l'on souhaite plus particulièrement dans l'image.
  (Ces modifications doivent etre changés si les sprites sheets n'ont pas le meme format)

Class Unit et Tank :
-
- Ajout de surcharge constructeur au regard du menu. Nous avions besoin des tanks pour leur "Image" plus que pour leur fonction attack etc. pour la création du menu.
  C'est pour cela que ici et dans Unit il existe un constructeur avec des paramètres différents + optimisé (par exemple pas charger le range alors que l'on ne s'en servira pas)

- Séparation du rayon d'attaque et du rayon de déplacement : ces deux attribut on été desolidarisé notamment pour permettre de programmer la logique des Rocket (qui avancent peu mais tirent loin)

Class Animated Player :
-
- Joueur particulier du menu, muni des ses propres animations. Il est totalement réutilisable autre part. C'est un GhostPlayer un peu plus spécifique.

Class Opening Menu :
-
- C'est une icwarsArea particulière étant donné qu'elle est fixe et qu'on ne veut pas qu'elle change selon les paramètres du jeu.
  Elle possède un animated player particulier muni de certaines interactions spécifiques a cette Area. Cette animated player doit transmettre des
  informations a l'area(choix de l'equipe jouée), pour faciliter leur communication mais aussi celle avec ICWars : c'est une classe imbriquée.

Class Rocket & Ambulance (Unit supplémentaires):
-
- Extension supplémentaire pour donner plus de contenu au jeu.
- Caractériques Rocket : (HP : 2, Rayon : 2, Rayon d'attaque : 5, Puissance d'attaque : 8)
- Caractéristiques Ambulance : (HP : 10, Rayon : 3, Rayon d'attaque/reparation : 2, Puissance d'attaque : 1, Puissance de réparation : 3)
- Note : Nous avions originellement fait que l'ambulance n'aie pas d'action attaque mais avons décidé d'ajouter une attaque faible pour eviter une possible situation ou le joueur n'a plus qu'une ambulance en fin de partie et ne peut alors plus faire grand chose.
- De plus l'IA choisira en priorité d'effectuer l'action reparation de l'ambulance, si cela est impossible elle essaira alors d'attaquer (ou wait si cela est a nouveau impossible)

Class Healing :
-
- Ajout de cette action supplémentaire correspondant a une capacité spéciale de l'ambulance.
- Le fonctionnement est analogue a celui de l'action Attack mais les unités alliées a distance sont trouvées a la place, et l'action les répare plutot que de leur enlever des HP.

Modification a la creation des Units:
-
- Les Units apparaissent en formation autour de la position initiale du curseur de leur joueur. Cela évite de devoir choisir leur position une a une et rend possible de changer la position initiale d'une equipe trés facilement (la position initiale des trois joueurs etant défini dans chaque aire)

Game Over :
-

- Ajout d'un Game Over a la fin du jeu



