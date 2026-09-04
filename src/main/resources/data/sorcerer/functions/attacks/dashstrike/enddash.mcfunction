#built using mc-build (https://github.com/mc-build/mc-build)

execute at @e[tag=dashtarget] run tp @s ~ ~ ~
kill @e[tag=dashtarget]
playsound nyctocosm:sorcerer.dashstrike.strike master @a[distance=..30] ~ ~ ~ 5
execute positioned ^ ^ ^1.5 as @a[distance=..2] run function sorcerer:attacks/dashstrike/hit