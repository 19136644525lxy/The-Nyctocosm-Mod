#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players reset @s ns.voidmeteortimer
tag @s add activated
execute rotated ~ 0 run function effects:shockwave/transmittance_first
particle flash ~ ~ ~ 0 0 0 0 1 force
playsound nyctocosm:sorcerer.voidmeteor.tear master @a[distance=..50] ~ ~ ~ 5