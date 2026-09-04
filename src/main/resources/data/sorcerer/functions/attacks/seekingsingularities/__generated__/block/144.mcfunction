#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned ~-0.25 ~-0.25 ~-0.25 as @a[dx=0,gamemode=!spectator] positioned ~-0.5 ~-0.5 ~-0.5 if entity @s[dx=0] run function sorcerer:attacks/seekingsingularities/hit
particle flash ~ ~ ~ 0 0 0 0 1
playsound nyctocosm:sorcerer.seekingsingularities.impact master @a[distance=..30] ~ ~ ~ 5
kill @s