#built using mc-build (https://github.com/mc-build/mc-build)

execute facing entity @a[distance=..40,sort=nearest,gamemode=!spectator] feet run tp @s ~ ~ ~ ~ 0
particle flash ~ ~2 ~ 0 0 0 0 1 force
playsound nyctocosm:sorcerer.voidbeam.aim master @a[distance=..50] ~ ~ ~ 5
execute at @s positioned ^ ^3 ^2.5 facing entity @a[distance=..40,sort=nearest,gamemode=!spectator] eyes run function sorcerer:attacks/voidbeam/summon