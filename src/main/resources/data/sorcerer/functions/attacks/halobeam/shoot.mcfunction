#built using mc-build (https://github.com/mc-build/mc-build)

particle explosion ~ ~ ~ 0.3 0.3 0.3 0 5 force
particle flash ~ ~ ~ 0 0 0 0 1 force
execute at @s run function sorcerer:attacks/halobeam/raycast
playsound nyctocosm:sorcerer.halobeam.shoot master @a[distance=..30] ~ ~ ~ 5
kill @s