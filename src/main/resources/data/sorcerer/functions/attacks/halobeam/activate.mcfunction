#built using mc-build (https://github.com/mc-build/mc-build)

tag @e[tag=halobeam] add activated
execute at @e[tag=halobeam] run particle flash ~ ~ ~ 0 0 0 0 1 force
execute positioned ~ ~2 ~ run particle flash ~ ~ ~ 0.4 0.4 0.4 0 5 force
playsound nyctocosm:sorcerer.halobeam.activate master @a[distance=..30] ~ ~ ~ 5