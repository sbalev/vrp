# VRP

## Le problème de tournées de véhicules

Les problèmes de tournées de véhicules (Vehicle Routing Problems, VRP) sont des problèmes d'optimisation combinatoire difficiles. Il y a plusieurs classes de VRP, nous allons considérer une version simplifiée.

Une entreprise doit distribuer `n` colis identiques à `n` clients. Initialement les colis se trouvent dans un entrepôt. Pour ce faire, l'entreprise dispose d'un nombre suffisant de véhicules identiques, chacun de capacité de `k` colis. Chaque véhicule part de l'entrepôt, sert un certain nombre de clients (au plus `k`) et revient dans l'entrepôt. Le but est de planifier les tournées des véhicules en servant tous les clients et en minimisant la distance totale parcourue par les véhicules.

## Kit de démarrage

Ce projet contient quelques classes Java utiles : représentation d'une instance et d'une solution, générateur d'instances à partir d'un réseau routier. Les classes utilisent la bibliothèque [GraphStream](http://graphstream-project.org/). Le projet contient également quelques instances de test (fichiers `lh_xxx_yyy.dgs`) où `xxx` est le nombre de clients et `yyy` est la capacité des véhicules.

## Travail demandé

Dans un premier temps le travail consiste à proposer, implémenter, tester et comparer différents algorithmes gloutons de construction de solution initiale. Un tel algorithme très simple est déjà implementé (`GreedySolution.java`). Ensuite proposer différentes transformations élémentaires et structures de voisinage. Faire une analyse comparative de ces structures et tester le fonctionnement de l'algorithme de recherche locale sur elles.

Par la suite, on se servira de ces résultats pour développer un algorithme de parcours itératif (GRASP, recuit simulé, recherche tabou, ...) de résolution de VRP.
