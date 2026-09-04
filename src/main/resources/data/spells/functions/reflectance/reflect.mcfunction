#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s s.damage -= TEMP s.damage
scoreboard players reset TEMP s.damage
particle flash ~ ~ ~ 0.2 0.2 0.2 0 5 force
execute rotated ~ ~90 run function effects:shockwave/reflect
playsound nyctocosm:spells.impact.reflectance master @a[distance=..50] ~ ~ ~ 10