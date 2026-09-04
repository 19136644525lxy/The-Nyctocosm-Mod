#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned as @e[tag=tonymarker,limit=1] rotated as @s run tp @s ~ ~ ~ ~ ~
execute at @s facing entity @a[sort=nearest,limit=1,distance=..20] eyes run tp @s ~ ~ ~ ~ ~
scoreboard players remove @s timer 1
execute if score @s timer matches ..0 run function nyctocosm:tony/remove