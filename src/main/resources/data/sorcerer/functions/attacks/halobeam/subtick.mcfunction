#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s ns.halobeamtimer 1
execute at @s[tag=!activated] rotated ~ -90 run function effects:sorcerer/halobeaminitial
execute at @s[tag=activated] rotated ~ -90 run function effects:sorcerer/halobeamfinal
execute as @s[scores={ns.halobeamtimer=..65}] at @s facing entity @a[distance=..30,sort=nearest,limit=1,gamemode=!spectator] eyes run tp @s ~ ~ ~ ~ ~
execute as @s[tag=activated,scores={ns.halobeamtimer=70..}] run function sorcerer:attacks/halobeam/shoot